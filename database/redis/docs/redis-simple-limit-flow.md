# redis 简单限流
简单的限流策略：系统要限定用户的某个行为在指定的时间内只能允许发生 N 次。

## 应用
- 控制流量，削弱高峰，缓解服务器负载
- 控制用户恶意行为

## 伪代码
```python
# 指定用户 user_id 的某个行为 action_key 在特定的时间内 period 只允许发生一定的次数 max_count
def is_action_allowed(user_id, action_key, period, max_count):
    return True

# 调用这个接口 , 一分钟内只允许最多回复 5 个帖子
can_reply = is_action_allowed("alice", "reply", 60, 5)
if can_reply:
    do_reply()
else:
    raise ActionThresholdOverflow()
```

## 解决方案
- 限流需求中存在一个滑动时间窗口，zset 数据结构的 score 值可以圈出这个时间窗口。我们只需要保留这个时间窗口，窗口之外的数据都可以忽略。
- zset 的 value 值只需要保证唯一性，用 uuid 会比较浪费空间，考虑使用毫秒时间戳。

---
- 用一个 zset 结构记录用户的行为历史，每一个行为都会作为 zset 中的一个 key 保存下来。同一个用户同一种行为用一个 zset 记录。
- 只需要保留时间窗口内的行为记录，如果用户是冷用户，滑动时间窗口内的行为是空记录，这个 zset 可以移除。
- 通过统计滑动窗口内的行为数量与阈值 max_count 进行比较就可以得出当前的行为是否允许。

## 缺点
**要记录时间窗口内所有的行为记录**，如果这个量很大，比如限定 60s 内操作不得超过 100w 次这样的参数，会消耗大量的存储空间。