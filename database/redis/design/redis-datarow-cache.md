# 数据行缓存
假设有一个促销活动，网页上需要显示促销商品的库存数量。

由于每次读取关系型数据库性能低下，需要考虑使用缓存，以及缓存的更新策略。

## 缓存的数据结构
商品的缓存数据结构
```
inv:123
-------
{//一个json}
```

使用两个有序集合来记录何时对缓存进行更新：
- 第一个为调度（schedule）有序集合，成员为数据行ID，分值是一个时间戳，记录了何时将指定数据行缓存到Redis；
- 第二个为延时（delay）有序集合，成员为数据行ID，分值记录了指定数据行的缓存需要每隔多少秒更新一次。

## 缓存调度
```python
def schedule_row_cache(conn, row_id, delay):
    # 先设置数据行的延迟值。
    conn.zadd('delay:', row_id, delay) 
    # 立即缓存数据行。
    conn.zadd('schedule:', row_id, time.time()) 
```

## 执行缓存
```python
while not QUIT:
    # 尝试获取下一个需要被缓存的数据行以及该行的调度时间戳，
    # 命令会返回一个包含零个或一个元组（tuple）的列表。
    next = conn.zrange('schedule:', 0, 0, withscores=True) 
    now = time.time()
    if not next or next[0][1] > now:
        # 暂时没有行需要被缓存，休眠50毫秒后重试。
        time.sleep(.05) 
        continue

    row_id = next[0][0]
    # 获取下一次调度前的延迟时间。
    delay = conn.zscore('delay:', row_id)
    if delay <= 0:
        # 不必再缓存这个行，将它从缓存中移除。
        conn.zrem('delay:', row_id) 
        conn.zrem('schedule:', row_id)
        conn.delete('inv:' + row_id)
        continue

    # 读取数据行。
    row = Inventory.get(row_id)
    # 更新调度时间并设置缓存值。
    conn.zadd('schedule:', row_id, now + delay)         
    conn.set('inv:' + row_id, json.dumps(row.to_dict())) 
```
