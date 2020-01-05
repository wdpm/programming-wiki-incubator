# cookie

## cookie的分类

|cookie 类型|优点 |缺点|
|---|---|---|
|签名 cookie |验证 cookie 所需的一切信息都存储在cookie 里面。cookie 可以包含额外的信息（additional infomation），并且对这些信息进行签名也很容易|正确地处理签名很难。很容易忘记对数据进行签名，或者忘记验证数据的签名，从而造成安全漏洞|
|令牌 cookie |添加信息非常容易。cookie 的体积非常小，因此移动终端和速度较慢的客户端可以更快地发送请求|需要在服务器中存储更多信息。如果使用的是关系数据库，那么载入和存储cookie的代价可能会很高|

## 登录会话cookie
使用hash存储登录cookie令牌与已登录用户之间的映射。

get
```python
# 尝试获取并返回令牌对应的用户
conn.hget('login:', token)
```

update
```python
def update_token(conn, token, user, item=None):
    timestamp = time.time()
    # 维持令牌与已登录用户之间的映射。
    conn.hset('login:', token, user)
    # 记录令牌最后一次出现的时间。
    conn.zadd('recent:', token, timestamp)
    if item:
        # 记录用户浏览过的商品。
        conn.zadd('viewed:' + token, item, timestamp)
        # 移除旧的记录，只保留用户最近浏览过的25个商品。
        conn.zremrangebyrank('viewed:' + token, 0, -26)
```

防止session内存爆炸
```python
QUIT = False
LIMIT = 10000000

def clean_sessions(conn):
    while not QUIT:
        size = conn.zcard('recent:')
        # 令牌数量未超过限制，休眠并在之后重新检查。
        if size <= LIMIT:
            time.sleep(1)
            continue

        #  获取需要移除的令牌ID。
        end_index = min(size - LIMIT, 100)
        tokens = conn.zrange('recent:', 0, end_index-1)

        # 为那些将要被删除的令牌构建键名。
        session_keys = []
        for token in tokens:
            session_keys.append('viewed:' + token)

        # 移除最旧的那些令牌。
        conn.delete(*session_keys)
        conn.hdel('login:', *tokens)
        conn.zrem('recent:', *tokens)
```

## 购物车cookie
购物车的数据结构：hash，key为商品ID，value为数量。
```
hash
--------
A:20
B:2
```
Add/Remove
```python
if count <= 0:
    # 从购物车里面移除指定的商品。
    conn.hrem('cart:' + session, item) 
else:
    # 将指定的商品添加到购物车。
    conn.hset('cart:' + session, item, count) 
```
这里的session表示的是登录会话的session，是一个token。

## 清理旧会话
清理旧会话时必须删掉对应购物车。
```python
def clean_full_sessions(conn):
    while not QUIT:
        size = conn.zcard('recent:')
        if size <= LIMIT:
            time.sleep(1)
            continue

        end_index = min(size - LIMIT, 100)
        sessions = conn.zrange('recent:', 0, end_index-1)

        session_keys = []
        for sess in sessions:
            session_keys.append('viewed:' + sess)
            session_keys.append('cart:' + sess)   # 添加这行，删除旧会话对应用户的购物车。

        conn.delete(*session_keys)
        conn.hdel('login:', *sessions)
        conn.zrem('recent:', *sessions)
```
