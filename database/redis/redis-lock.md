# Redis 分布式锁的原子性考虑

在 Redis 旧版本，SETNX + EXPIRE 这个组合不是原子性操作。如果SETNX成功，EXPIRE因为程序或者网络问题中断而执行失败，那么可能
出现加了锁却没有释放的问题。

在 Redis 2.6+ 版本后 SET 命令可以添加 NX + EX/PX 参数来完成这个需求，并且保证原子性操作。举例：

```bash
SET lock_key unique_client_id NX EX 30  # 一键完成 SETNX + EXPIRE
```

- NX：仅当 key 不存在时设置（等效 SETNX）
- EX 30：设置 30 秒过期时间（替代 EXPIRE），过期时间一般应大于业务平均执行时间（如 2 倍）。

SET 命令的完整参数

| 参数    | 作用                       | 示例                              |
|:------|:-------------------------|:--------------------------------|
| `NX`  | 仅当 Key 不存在时设置（加锁）        | `SET lock_key uid NX EX 10`     |
| `XX`  | 仅当 Key 存在时设置（特殊场景用）      | `SET lock_key uid XX PX 5000`   |
| `EX`  | 设置过期时间（秒）                | `EX 30`                         |
| `PX`  | 设置过期时间（毫秒）               | `PX 30000`                      |
| `GET` | 返回 Key 的旧值（可选，用于某些乐观锁场景） | `SET lock_key uid NX EX 10 GET` |

对于简单的验证场景，SET 命令足够。但是对于复杂的场景，还是推荐使用lua脚本，更加强大，能够兼容复杂的验证逻辑。

- 释放锁时的条件判断
  需验证锁的持有者，避免误删其他客户端的锁。
- 复杂锁逻辑
  可重入锁、锁续期（WatchDog）、多键原子操作等。

通过 redis-cli --stat 观察锁 Key 的命中情况。

python脚本示例：

```python
import redis
import uuid

r = redis.Redis(host='localhost', port=6379)

def acquire_lock(lock_key, expire_time=30):
    client_id = str(uuid.uuid4())  # 唯一标识
    # 原子性加锁
    if r.set(lock_key, client_id, nx=True, ex=expire_time):
        # 启动续期线程（仅示例，生产环境需更健壮的线程管理）
        threading.Thread(
            target=renew_lock, 
            args=(lock_key, client_id, expire_time),
            daemon=True
        ).start()
        return client_id
    return None

def release_lock(lock_key, client_id):
    # 只有锁的持有者才能释放此锁
    script = """
    if redis.call("GET", KEYS[1]) == ARGV[1] then
        return redis.call("DEL", KEYS[1])
    else
        return 0
    end
    """
    return r.eval(script, 1, lock_key, client_id)

# 使用示例
lock_key = "resource_lock"
client_id = acquire_lock(lock_key)
if client_id:
    try:
        print("执行业务逻辑...")
    finally:
        release_lock(lock_key, client_id)
else:
    print("获取锁失败")
```

上面代码中 release_lock 方法的 Redis Lua 脚本的参数传递，遵循下面的规则。

在 Redis Lua 脚本中，**数组下标是从 1 开始的**，这是 Lua 语言的特性。

当通过 `EVAL` 或 `EVALSHA` 调用脚本时，参数按以下规则映射：

```lua
EVAL "脚本内容" <numkeys> <key1> <key2>... <arg1> <arg2>...
```

- `<numkeys>` 表示 `KEYS` 的数量，之后的参数依次分为：
    - 前 `<numkeys>` 个是 `KEYS`（即 `KEYS[1]`, `KEYS[2]`, ...）
    - 其余是 `ARGV`（即 `ARGV[1]`, `ARGV[2]`, ...）

设置锁过期时间的目的是避免程序崩溃后锁永久驻留。

假设因为意外原因 `release_lock(lock_key, client_id)`没有执行，这个锁后续也会自然过期。

## 锁续期

```python
def renew_lock(lock_key, client_id, expire_time, interval=10):
    """锁续期线程"""
    while True:
        time.sleep(interval)
        if r.get(lock_key) != client_id:  # 锁已释放或易主
            break
        r.expire(lock_key, expire_time)  # 续期
```

WatchDog 续期目的是防止业务未完成时锁提前过期。

## **清理残留锁**

可以后台运行一个专门用于清理残留锁的线程，定期扫描redis中不再需要的孤儿锁，执行删除。等价于

```bash
DEL resource_lock
```

监控报警目的是及时发现未释放的锁。

## 关于释放锁的考虑

假设acquire_lock时设置过期时间30s

理想情况下的安全性

1. 业务逻辑 <30s 完成
    - 锁未过期，`release_lock` 能正常删除锁。
    - **无风险**：完全符合预期。
2. 业务逻辑 >30s 但 WatchDog 续期成功
    - 锁被持续续期，`release_lock` 时锁仍有效。
    - **无风险**：续期机制保障了锁持有权。

绝对安全的锁需满足的条件

1. **加锁原子性**
    - 使用 `SET lock_key client_id NX EX 30`（一条命令完成 `SETNX` + `EXPIRE`）。
2. **锁续期机制（WatchDog）**
    - 后台线程定期检查业务状态，并在未完成时续期锁（如每 10 秒续期一次）。
3. **释放锁时的验证**
    - 仅允许锁的持有者（通过 `client_id` 验证）释放锁（需 Lua 脚本保证原子性）。
4. **合理的过期时间**

这个锁消失的情况只能是下面的情况：

- 锁之前的拥有者正常完成业务操作，然后释放
- 锁自然过期

唯一担忧的边缘情况可能是：**业务恰好执行非常耗时，锁续期机制又太过脆弱，没有续期成功，导致锁自然过期了，而别的线程抢到了这个锁。
**
此时业务逻辑就会发生混乱，导致无法预估的情况。那还有没有更加万无一失的兜底方案？

业务操作的幂等性设计或许可以。

## 业务操作的幂等性设计

```python
from functools import wraps
import redis

r = redis.Redis()

def idempotent(key_func, ttl=86400):
    def decorator(f):
        @wraps(f)
        def wrapped(*args, **kwargs):
            biz_id = key_func(*args, **kwargs)
            # 原子性检查 + 标记
            if not r.set(f"completed:{biz_id}", "1", nx=True, ex=ttl):
                print(f"业务 {biz_id} 已处理，跳过执行")
                return None
            try:
                return f(*args, **kwargs)
            except Exception as e:
                r.delete(f"completed:{biz_id}")  # 失败时清除标记
                raise e
        return wrapped
    return decorator

# 使用示例
@idempotent(key_func=lambda order_id: order_id, ttl=3600)
def process_order(order_id):
    print(f"处理订单 {order_id}...")
    return "成功"
```

本质上也是找一个外部存储，给特定操作一个标志位，表示正在处理或者已经处理，其他线程请勿打扰。

对比普通锁的区别

| 特性       | 分布式锁            | 幂等性设计                   |
|:---------|:----------------|:------------------------|
| **目标**   | 防止并发执行          | 防止重复执行                  |
| **实现**   | 互斥访问（如 Redis 锁） | 状态标记（如 `completed:123`） |
| **适用阶段** | 业务执行前           | 业务执行前/后均可               |
| **开销**   | 锁续期、锁检查和释放      | 仅一次读写 Redis             |

## 完整示例

```python
import redis
import uuid
import time
import threading
from functools import wraps

# Redis 连接
r = redis.Redis(host='localhost', port=6379)

# 分布式锁工具类
class DistributedLock:
    def __init__(self, lock_key, expire_time=30):
        self.lock_key = lock_key
        self.expire_time = expire_time
        self.client_id = str(uuid.uuid4())
        self.renew_thread = None

    def __enter__(self):
        """获取锁并启动续期线程"""
        if not self._acquire():
            raise Exception("获取锁失败")
        self._start_renewal()
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        """释放锁并停止续期"""
        self._stop_renewal()
        self._release()

    def _acquire(self):
        """原子化加锁"""
        return r.set(
            self.lock_key,
            self.client_id,
            nx=True,
            ex=self.expire_time
        )

    def _start_renewal(self):
        """启动锁续期线程（WatchDog）"""
        def renew():
            while getattr(threading.current_thread(), "do_run", True):
                time.sleep(self.expire_time / 3)  # 每 1/3 过期时间续期一次
                if not self._renew():
                    break  # 续期失败（锁已释放或易主）

        self.renew_thread = threading.Thread(target=renew, daemon=True)
        self.renew_thread.do_run = True
        self.renew_thread.start()

    def _renew(self):
        """续期锁（需验证持有者）"""
        script = """
        if redis.call("GET", KEYS[1]) == ARGV[1] then
            return redis.call("EXPIRE", KEYS[1], ARGV[2])
        end
        return 0
        """
        return r.eval(script, 1, self.lock_key, self.client_id, self.expire_time)

    def _stop_renewal(self):
        """停止续期线程"""
        if self.renew_thread:
            self.renew_thread.do_run = False
            self.renew_thread.join()

    def _release(self):
        """释放锁（需验证持有者）"""
        script = """
        if redis.call("GET", KEYS[1]) == ARGV[1] then
            return redis.call("DEL", KEYS[1])
        end
        return 0
        """
        r.eval(script, 1, self.lock_key, self.client_id)

# 业务幂等性装饰器
def idempotent(key_func):
    def decorator(f):
        @wraps(f)
        def wrapped(*args, **kwargs):
            # 生成唯一业务ID（如订单ID）
            biz_id = key_func(*args, **kwargs)
            # 检查是否已处理过
            if r.get(f"completed:{biz_id}"):
                print(f"业务 {biz_id} 已处理，跳过执行")
                return None
            try:
                result = f(*args, **kwargs)
                # 标记为已完成（设置24小时过期）
                r.set(f"completed:{biz_id}", "1", ex=86400)
                return result
            except Exception as e:
                raise e
        return wrapped
    return decorator

# 示例业务逻辑
@idempotent(key_func=lambda order_id: order_id)
def process_order(order_id):
    """处理订单（幂等方法）"""
    print(f"处理订单 {order_id}...")
    time.sleep(10)  # 模拟业务耗时
    return f"订单 {order_id} 处理完成"

# 使用示例
def main():
    order_id = "order_123"
    try:
        # 获取锁并执行业务
        with DistributedLock(f"lock:{order_id}", expire_time=30):
            result = process_order(order_id)
            print(result)
    except Exception as e:
        print(f"执行失败: {e}")

if __name__ == "__main__":
    main()
```

使用代码

```python
# 场景1：支付回调（幂等+锁）
@idempotent(key_func=lambda payment_id: payment_id)
def handle_payment(payment_id, amount):
    with DistributedLock(f"payment:{payment_id}"):
        if db.query("SELECT status FROM payments WHERE id = ?", payment_id) == "completed":
            return
        db.execute("UPDATE payments SET status = 'completed' WHERE id = ?", payment_id)

# 场景2：库存扣减（锁+数据库乐观锁）
def reduce_inventory(item_id, count):
    with DistributedLock(f"inventory:{item_id}"):
        stock = db.query("SELECT stock FROM inventory WHERE id = ?", item_id)
        if stock >= count:
            db.execute("UPDATE inventory SET stock = stock - ? WHERE id = ?", count, item_id)
```

## 扩展：一个简单的秒杀逻辑

在 Redis 中，可以使用 SPOP 命令来原子性地删除并返回 Set 中的一个随机元素，
这个操作是线程安全的，适用于秒杀场景中确保一个资格只能被一个用户获取的需求。

准备库存，0-99表示商品编号。

```
SADD seckill:stock 0 1 2 ... 99
```

```python
import redis

r = redis.Redis(host='localhost', port=6379)

def seckill(user_id):
    # 尝试原子性移除一个元素
    item = r.spop("seckill:stock")
    if item is None:
        return "秒杀失败，库存已售罄"
    else:
        # 记录用户抢到的资格（可存入另一个 Redis 结构，不要存常规RDB，否则又回到瓶颈）
        # 这里是简单用set保存user_id，实际上用map更好，能够记录相关数据，例如user A抢到了编号99的商品
        r.sadd("seckill:successful_users", user_id)
        return f"恭喜用户 {user_id} 抢到资格，商品ID: {item.decode()}"
```

## 总结

常见思维误区

- **误区1**：
  “用了锁就不需要幂等性” → 锁可能因超时被释放，业务仍可能重复执行。
- **误区2**：
  “幂等性可以替代锁” → 幂等性不解决并发中间状态问题（如库存查扣分离）。

一个关键的问题：

**对于某个特定的秒杀API接口，真的有必要同时使用幂等设计和分布式锁吗？**

有必要。幂等设计是为了防止用户A一个人抢多个单（不管他是恶意脚本刷取还是网络卡顿导致请求重叠），而分布式锁是用户A在第一道防线之后，和其他用户一起抢秒杀库存，此时分布式锁是为了防止库存超卖。例如秒杀商品是100个耳机，标号0-99。绝不能将某个耳机例如编号99的资格同时给予用户A，B，C。这就是防止超卖。

似乎可以抽象出一种设计原则：

1. **第一道防线**：业务层幂等（数据库唯一键、状态机、请求去重）。
2. **第二道防线**：分布式锁（仅对高并发关键路径使用，对特点API进行精准优化）。
3. **监控兜底**：日志 + 告警，及时发现异常竞争。

```python
# 伪代码
def business_operation():
    if not is_idempotent_checked():  # 幂等性优先
        return
    
    with distributed_lock():         # 加锁
            process()                # 真实业务逻辑
            mark_completed()         # 标记完成
```