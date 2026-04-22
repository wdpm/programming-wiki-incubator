# 系统设计入门

> [donnemartin/system-design-primer](https://github.com/donnemartin/system-design-primer)

## CAP 理论

- CP 抛弃 A 可用性 => 你的业务需求需要原子读写
- AP 抛弃 C 一致性 => 可用性恢复，但是实时数据可能不一致。但是可以最终一致。

一致性根据强弱可以划分三类：

- 弱一致性：在写入之后，访问可能看到，也可能看不到（写入数据）。总之就是无法保证，只能尽力优化之让其能访问最新数据。
  弱一致性在 VoIP，视频聊天和实时多人游戏等真实用例中表现不错。
- 最终一致性：在写入后，访问最终能看到写入数据（通常在数毫秒内）。数据被异步复制。
  DNS 和 email 等系统使用的是此种方式。最终一致性在高可用性系统中效果不错。
- 强一致性：在写入后，访问立即可见。数据被同步复制。
  文件系统和关系型数据库（RDBMS）中使用的是此种方式。强一致性在需要记录的系统中运作良好。

## CDN

- CDN 推送
  当你服务器上内容发生变动时，推送到 CDN 接受新内容。直接推送给 CDN 并重写 URL 地址以指向你的内容的 CDN
  地址。你可以配置内容到期时间及何时更新。内容只有在更改或新增时才推送，流量最小化，但储存最大化。

- CDN 拉取

  当第一个用户请求该资源时，从服务器上拉取资源。你将内容留在自己的服务器上并重写 URL 指向 CDN 地址。直到内容被缓存在 CDN
  上为止，这样请求只会更慢，存活时间（TTL）决定缓存多久时间。CDN 拉取方式最小化 CDN 上的储存空间，但如果过期文件并在实际更改之前被拉取，则会导致冗余的流量。

高流量站点使用 CDN 拉取效果不错，因为只有最近请求的内容保存在 CDN 中，流量才能更平衡地分散。

## 负载均衡器

负载均衡器可以通过硬件（昂贵）或 HAProxy 等软件来实现。 增加的好处包括:

- SSL 终结 —— 解密传入的请求并加密服务器响应，这样后端服务器就不必再执行这些潜在高消耗运算了。不需要在每台服务器上安装
  X.509 证书。
- Session 留存 ─ 如果 Web 应用程序不追踪会话，发出 cookie 并将特定客户端的请求路由到同一实例。

通常会设置采用工作─备用 或 双工作模式的多个负载均衡器，以免发生单点故障。

- 四层负载均衡

  四层负载均衡根据监看传输层的信息来决定如何分发请求。通常，这会涉及来源，目标 IP
  地址和请求头中的端口，但不包括数据包（报文）内容。四层负载均衡执行网络地址转换（NAT）来向上游服务器转发网络数据包。

- 七层负载均衡器

  七层负载均衡器根据监控应用层来决定怎样分发请求。这会涉及请求头的内容，消息和
  cookie。七层负载均衡器终结网络流量，读取消息，做出负载均衡判定，然后传送给特定服务器。比如，一个七层负载均衡器能直接将视频流量连接到托管视频的服务器，同时将更敏感的用户账单流量引导到安全性更强的服务器。

以损失灵活性为代价，四层负载均衡比七层负载均衡花费更少时间和计算资源，虽然这对现代商用硬件的性能影响甚微。

## 反向代理（web 服务器）

好处包括：

1. 增加安全性 - 隐藏后端服务器的信息，屏蔽黑名单中的 IP，限制每个客户端的连接数。
2. 提高可扩展性和灵活性 - 客户端只能看到反向代理服务器的 IP，这使你可以增减服务器或者修改它们的配置。
3. 本地终结 SSL 会话 - 解密传入请求，加密服务器响应，这样后端服务器就不必完成这些潜在的高成本的操作。免除了在每个服务器上安装
   X.509 证书的需要。
4. 压缩 - 压缩服务器响应。
5. 缓存 - 直接返回命中的缓存结果。
6. 静态内容 - 直接提供静态内容。

---

问题：反向代理和负载均衡有什么区别和联系？

答：区别：反向代理侧重于 ** 代理 **（接收请求并转发），目标是隐藏后端、提供附加功能（缓存、加密）。负载均衡侧重于 ** 均衡 **
（分发请求），目标是分散压力、提高整体吞吐量和容错。联系：有时某个技术可以同时完成这两方面，例如 Nginx 可同时做反向代理和负载均衡。

## SQL 还是 NoSQL

选取 SQL 的原因:

- 结构化数据
- 严格的模式
- 关系型数据
- 需要复杂的联结操作
- 事务
- 清晰的扩展模式
- 既有资源更丰富：开发者、社区、代码库、工具等
- 通过索引进行查询非常快

选取 NoSQL 的原因：

- 半结构化数据
- 动态或灵活的模式
- 非关系型数据
- 不需要复杂的联结操作
- 存储 TB （甚至 PB）级别的数据
- 高数据密集的工作负载
- IOPS 高吞吐量

## 缓存更新模式

关键点在于如何处理两侧数据的不一致性问题，以及衍生出来的缓存更新机制。

### 1 旁路缓存 Cache Aside

当数据在缓存中未找到（缓存未命中）时，应用程序需要绕开缓存，直接从数据库中获取数据，然后再将数据放入缓存中。同样，当数据更新时，应用程序也需要同时更新数据库和缓存。

其中旁路缓存 Cache Aside 还细分为写策略和读策略。

在 Cache Aside（旁路缓存）模式中，** 读策略和写策略必须同时应用 **，** 不能二选一 **。原因如下：

- ** 读策略 **：处理缓存未命中时从数据库加载数据并回填缓存。
- ** 写策略 **：处理数据更新时同步更新数据库并删除（或更新）缓存。

如果只使用读策略而不使用写策略，数据更新后缓存不会失效，后续读请求将一直读到旧数据（脏数据）。如果只使用写策略而不使用读策略，缓存永远不会被填充，所有读请求都会穿透到数据库，缓存形同虚设。

** 读策略 **。

应用从存储器读写。缓存不和存储器直接交互，应用执行以下操作：

1. 在缓存中查找记录，如果所需数据不在缓存中
2. 从数据库中加载所需内容
3. 将查找到的结果存储到缓存中
4. 返回所需内容

应用层代码：

```python
def get_user(self, user_id):
    user = cache.get("user.{0}", user_id)
    if user is None:
        user = db.query("SELECT * FROM users WHERE user_id = {0}", user_id)
        
    key = "user.{0}".format(user_id)
    cache.set(key, json.dumps(user))
    return user
```

缓存的缺点：

- 请求的数据如果不在缓存中就需要经过额外步骤的处理，这会导致明显的延迟。
- 如果数据库中的数据更新了会导致缓存中的数据过时。需要通过设置 TTL 强制更新缓存或者直写模式来缓解这种情况。
- 当一个节点出现故障的时候，它将会被一个新的节点替代，这增加了延迟的时间。

---

** 写策略 **。

1. 先更新数据库, 再更新缓存。=> 更新缓存的请求可能不是按顺序到达的，此时数据不一致。

2. 先更新缓存, 再更新数据库 => 更新缓存的请求可能不是按顺序到达的，此时数据不一致。

3. 先删缓存, 再更新数据库 => 在读写并发的情况下会出现数据不一致的情况。

4. ** 先更新数据库, 再删缓存 **。推荐这个方式。

   ```python
   def set_user(self, user_id, values):
       # 1. 更新数据库
       db.query("UPDATE users SET name = {1} WHERE user_id = {0}", user_id, values['name'])
       # 2. 使缓存失效（删除缓存条目）
       cache.delete("user.{0}".format(user_id))
   ```

> 因为读请求比写请求更快，缓存的写入通常要远远快于数据库的写入，所以在实际中很难出现请求 A 已经更新了数据库并且删除了缓存，请求
> B 才更新完缓存的情况。

参考： https://zhuanlan.zhihu.com/p/677957865

### 2 写穿模式 Write-through

Write-through 本身不规定读策略，只关心写策略。

应用使用缓存作为主要的数据存储，将数据读写到缓存中，而缓存负责从数据库中读写数据。

1. 应用向缓存中添加 / 更新数据
2. 缓存同步地写入数据存储
3. 返回所需内容

应用层代码：

```python
set_user(12345, {"foo":"bar"})
```

缓存层代码：

```python
def set_user(user_id, values):
    # 写数据库
    user = db.query("UPDATE Users WHERE id = {0}", user_id, values)
    # 写缓存
    cache.set(user_id, user)
```

| 情况 | 数据库     | 缓存       | 结果                    |
|:---|:--------|:---------|:----------------------|
| 1  | 更新数据库成功 | 更新缓存失败   | 数据库是新值，缓存是旧值 → ** 脏读 ** |
| 2  | 更新数据库失败 | 更新缓存不会执行 | 数据一致（都没有变化）           |

Write-Through 模式存在不一致风险。生产环境中通常采用 **Cache Aside（先更新 DB，再删除缓存）** + ** 消息队列重试 ** 或 ** 订阅
binlog（使用 Canal、Debezium 等工具）** 来保证最终一致性。

在写穿模式中，** 应用层代码需要感知它正在与缓存层交互 **（比如调用的是缓存提供的 API），但 ** 不需要知道缓存如何与数据库同步 **
。由于存写操作所以直写模式整体是一种很慢的操作，但是读取刚写入的数据很快。相比读取数据，用户通常比较能接受更新数据时速度较慢。缓存中的数据不会过时。

写穿模式的缺点：

- 由于故障或者缩放而创建的新的节点，新的节点不会缓存，直到数据库更新为止。缓存模式 + 直写模式组合在一起可以缓解这个问题。
- 写入的大多数数据可能永远都不会被读取，用 TTL 可以最小化这种情况的出现。

注意点：

- 如果你的应用 ** 所有需要读取的数据都必然先被写入 **（例如用户个人资料、发帖内容等），那么纯 Write-through 可以正常工作。
- 但如果存在 ** 只读的预置数据 **（如系统配置、字典表）或者需要读取 ** 未曾写入的历史数据 **，就必须配合 **Read-through** 或 *
  *Cache Aside** 的读策略，在缓存未命中时加载数据库并填充缓存。

- 所以，即便是绿地项目，也建议完整实现读策略，除非你能保证所有读请求对应的数据都已被写操作预先填充。

### 3 写回模式 write-back

在写回模式中，应用执行以下操作：

1. 在缓存中增加或者更新条目。
2. 异步写入数据，提高写入性能。这一步非常脆弱，如果这步失败了，数据就不一致性了。

示例代码：

```python
import threading
import time
import random

class WriteBackCache:
    def __init__(self):
        self.cache = {}
        self.pending_writes = {}  # key -> (value, timestamp)
        self.flush_interval = 5   # 每 5 秒刷一次
        self._start_flusher()

    def _start_flusher(self):
        def flush_loop():
            while True:
                time.sleep(self.flush_interval)
                self._flush()
        threading.Thread(target=flush_loop, daemon=True).start()

    def _flush(self):
        # 异步写入数据库（模拟）
        items = list(self.pending_writes.items())
        for key, (value, _) in items:
            try:
                # 模拟数据库写入，可能失败
                if random.random() < 0.2:  # 20% 失败率
                    raise Exception("DB write failed")
                print(f"Flushed {key} = {value} to DB")
                # 这里执行真实的数据库写入逻辑
                del self.pending_writes[key]
            except Exception as e:
                print(f"Failed to flush {key}: {e}")

    def set(self, key, value):
        # 1. 更新缓存，这里目前是内存型缓存，可以替代为写外部缓存
        self.cache[key] = value
        # 2. 记录待异步写入
        self.pending_writes[key] = (value, time.time())
        print(f"Cached {key} = {value} (pending flush)")

    def get(self, key):
        return self.cache.get(key)

# 使用示例
cache = WriteBackCache()
cache.set("user:123", {"name": "Alice"})
cache.set("user:456", {"name": "Bob"})

# 立即读取，从缓存返回
print(cache.get("user:123"))  # {'name': 'Alice'}

# 等待一段时间，后台会尝试异步刷盘
time.sleep(6)  # 触发 flush
```

风险评估：

- ** 数据不一致窗口 **：写入缓存后，若后台刷盘失败且整个进程崩溃，缓存中的数据将永久丢失（因为未持久化到数据库）。

- 回写数据库这步依赖于定时运行的线程池，非常脆弱，需要仔细考虑健壮的线程管理策略。

- 实现写回模式比实现缓存模式或者直接模式更复杂。

### 4 Refresh-ahead 提前预测地刷新

你可以将缓存配置成在到期之前自动刷新最近访问过的内容。

如果缓存可以准确预测将来可能请求哪些数据，那么刷新可能会导致延迟与读取时间的降低。

刷新的缺点：

- 这个模式是否好用取决于能否准确预测哪些数据是热点数据。

- 如果不能准确预测到未来需要用到的数据，可能会导致性能不如不使用刷新。等于做无用功。

## 异步任务队列

例如，如果要发送一条推文，推文可能会马上出现在你的时间线上，但是可能需要一些时间才能将你的推文推送到你的所有关注者那里去。

- Redis 是一个令人满意的简单的消息代理，但是消息有可能会丢失。
- RabbitMQ 很受欢迎但是要求你适应「AMQP」协议并且管理你自己的节点。

Celery 支持调度，主要是用 Python 开发的。

背压通过限制队列大小，为队列中的作业保持高吞吐率和良好的响应时间。一旦队列填满，客户端将得到服务器忙或者
HTTP 503 状态码，以便稍后重试。

客户端可以在稍后时间重试该请求，也许是指数退避。

## RPC VS REST 调用模式对比

RPC 面向函数，调用时需要提供函数名和参数。REST 面向资源。

| 操作          | RPC                                                                       | REST                                             |
|-------------|---------------------------------------------------------------------------|--------------------------------------------------|
| 注册          | **POST** /signup                                                          | **POST** /persons                                |
| 注销          | **POST** /resign { "personid": "1234" }                                   | **DELETE** /persons/1234                         |
| 读取用户信息      | **GET** /readPerson?personid=1234                                         | **GET** /persons/1234                            |
| 读取用户物品列表    | **GET** /readUsersItemsList?personid=1234                                 | **GET** /persons/1234/items                      |
| 向用户物品列表添加一项 | **POST** /addItemToUsersItemsList { "personid": "1234"; "itemid": "456" } | **POST** /persons/1234/items { "itemid": "456" } |
| 更新一个物品      | **POST** /modifyItem { "itemid": "456"; "key": "value" }                  | **PUT** /items/456 { "key": "value" }            |
| 删除一个物品      | **POST** /removeItem { "itemid": "456" }                                  | **DELETE** /items/456                            |

## 2 的次方表

```bash
Power           Exact Value         Approx Value        Bytes
---------------------------------------------------------------
7                             128
8                             256
10                           1024   1 thousand           1 KB
16                         65,536                       64 KB
20                      1,048,576   1 million            1 MB
30                  1,073,741,824   1 billion            1 GB
32                  4,294,967,296                        4 GB
40              1,099,511,627,776   1 trillion           1 TB
```

## 延迟数

```bash
Latency Comparison Numbers
--------------------------
L1 cache reference                           0.5 ns
Branch mispredict                            5   ns
L2 cache reference                           7   ns                      14x L1 cache
Mutex lock/unlock                           25   ns
Main memory reference                      100   ns                      20x L2 cache, 200x L1 cache
Compress 1K bytes with Zippy            10,000   ns       10 us
Send 1 KB bytes over 1 Gbps network     10,000   ns       10 us
Read 4 KB randomly from SSD*           150,000   ns      150 us          ~1GB/sec SSD
Read 1 MB sequentially from memory     250,000   ns      250 us
Round trip within same datacenter      500,000   ns      500 us
Read 1 MB sequentially from SSD*     1,000,000   ns    1,000 us    1 ms  ~1GB/sec SSD, 4X memory
Disk seek                           10,000,000   ns   10,000 us   10 ms  20x datacenter roundtrip
Read 1 MB sequentially from 1 Gbps  10,000,000   ns   10,000 us   10 ms  40x memory, 10X SSD
Read 1 MB sequentially from disk    30,000,000   ns   30,000 us   30 ms 120x memory, 30X SSD
Send packet CA->Netherlands->CA    150,000,000   ns  150,000 us  150 ms

Notes
-----
1 ns = 10^-9 seconds
1 us = 10^-6 seconds = 1,000 ns
1 ms = 10^-3 seconds = 1,000 us = 1,000,000 ns
```

基于上述数字的指标：

- 从磁盘以 30 MB/s 的速度顺序读取
- 以 100 MB/s 从 1 Gbps 的以太网顺序读取
- 从 SSD 以 1 GB/s 的速度读取
- 以 4 GB/s 的速度从主存读取
- 每秒能绕地球 6-7 圈
- 数据中心内每秒有 2,000 次往返

## 扩展阅读

不要专注于以下文章的细节，专注于以下方面：

- 发现这些文章中的共同的原则、技术和模式。
- 学习每个组件解决哪些问题，什么情况下使用，什么情况下不适用

### 数据处理

| 类型              | 系统                                        | 引用                                                                                                                                             |
|-----------------|-------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| Data processing | **MapReduce** - Google 的分布式数据处理           | [research.google.com](http://static.googleusercontent.com/media/research.google.com/zh-CN/us/archive/mapreduce-osdi04.pdf)                     |
| Data processing | **Spark** - Databricks 的分布式数据处理           | [slideshare.net](http://www.slideshare.net/AGrishchenko/apache-spark-architecture)                                                             |
| Data processing | **Storm** - Twitter 的分布式数据处理              | [slideshare.net](http://www.slideshare.net/previa/storm-16094009)                                                                              |
|                 |                                           |                                                                                                                                                |
| Data store      | **Bigtable** - Google 的列式数据库              | [harvard.edu](http://www.read.seas.harvard.edu/~kohler/class/cs239-w08/chang06bigtable.pdf)                                                    |
| Data store      | **HBase** - Bigtable 的开源实现                | [slideshare.net](http://www.slideshare.net/alexbaranau/intro-to-hbase)                                                                         |
| Data store      | **Cassandra** - Facebook 的列式数据库           | [slideshare.net](http://www.slideshare.net/planetcassandra/cassandra-introduction-features-30103666)                                           |
| Data store      | **DynamoDB** - Amazon 的文档数据库              | [harvard.edu](http://www.read.seas.harvard.edu/~kohler/class/cs239-w08/decandia07dynamo.pdf)                                                   |
| Data store      | **MongoDB** - 文档数据库                       | [slideshare.net](http://www.slideshare.net/mdirolf/introduction-to-mongodb)                                                                    |
| Data store      | **Spanner** - Google 的全球分布数据库             | [research.google.com](http://research.google.com/archive/spanner-osdi2012.pdf)                                                                 |
| Data store      | **Memcached** - 分布式内存缓存系统                 | [slideshare.net](http://www.slideshare.net/oemebamo/introduction-to-memcached)                                                                 |
| Data store      | **Redis** - 能够持久化及具有值类型的分布式内存缓存系统         | [slideshare.net](http://www.slideshare.net/dvirsky/introduction-to-redis)                                                                      |
|                 |                                           |                                                                                                                                                |
| File system     | **Google File System (GFS)** - 分布式文件系统    | [research.google.com](http://static.googleusercontent.com/media/research.google.com/zh-CN/us/archive/gfs-sosp2003.pdf)                         |
| File system     | **Hadoop File System (HDFS)** - GFS 的开源实现 | [apache.org](https://hadoop.apache.org/docs/r1.2.1/hdfs_design.html)                                                                           |
|                 |                                           |                                                                                                                                                |
| Misc            | **Chubby** - Google 的分布式系统的低耦合锁服务         | [research.google.com](http://static.googleusercontent.com/external_content/untrusted_dlcp/research.google.com/en/us/archive/chubby-osdi06.pdf) |
| Misc            | **Dapper** - 分布式系统跟踪基础设施                  | [research.google.com](http://static.googleusercontent.com/media/research.google.com/en//pubs/archive/36356.pdf)                                |
| Misc            | **Kafka** - LinkedIn 的发布订阅消息系统            | [slideshare.net](http://www.slideshare.net/mumrah/kafka-talk-tri-hug)                                                                          |
| Misc            | **Zookeeper** - 集中的基础架构和协调服务              | [slideshare.net](http://www.slideshare.net/sauravhaloi/introduction-to-apache-zookeeper)                                                       |

### 公司系统架构

| Company        | Reference(s)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
|----------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Amazon         | [Amazon 的架构](http://highscalability.com/amazon-architecture)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Cinchcast      | [每天产生 1500 小时的音频](http://highscalability.com/blog/2012/7/16/cinchcast-architecture-producing-1500-hours-of-audio-every-d.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| DataSift       | [每秒实时挖掘 120000 条 tweet](http://highscalability.com/blog/2011/11/29/datasift-architecture-realtime-datamining-at-120000-tweets-p.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| DropBox        | [我们如何缩放 Dropbox](https://www.youtube.com/watch?v=PE4gwstWhmc)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| ESPN           | [每秒操作 100000 次](http://highscalability.com/blog/2013/11/4/espns-architecture-at-scale-operating-at-100000-duh-nuh-nuhs.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Google         | [Google 的架构](http://highscalability.com/google-architecture)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Instagram      | [1400 万用户，达到兆级别的照片存储](http://highscalability.com/blog/2011/12/6/instagram-architecture-14-million-users-terabytes-of-photos.html) [是什么在驱动 Instagram](http://instagram-engineering.tumblr.com/post/13649370142/what-powers-instagram-hundreds-of-instances)                                                                                                                                                                                                                                                                                                                                                              |
| Justin.tv      | [Justin.Tv 的直播广播架构](http://highscalability.com/blog/2010/3/16/justintvs-live-video-broadcasting-architecture.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| Facebook       | [Facebook 的可扩展 memcached](https://cs.uwaterloo.ca/~brecht/courses/854-Emerging-2014/readings/key-value/fb-memcached-nsdi-2013.pdf) [TAO: Facebook 社交图的分布式数据存储](https://cs.uwaterloo.ca/~brecht/courses/854-Emerging-2014/readings/data-store/tao-facebook-distributed-datastore-atc-2013.pdf) [Facebook 的图片存储](https://www.usenix.org/legacy/event/osdi10/tech/full_papers/Beaver.pdf)                                                                                                                                                                                                                                  |
| Flickr         | [Flickr 的架构](http://highscalability.com/flickr-architecture)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Mailbox        | [在 6 周内从 0 到 100 万用户](http://highscalability.com/blog/2013/6/18/scaling-mailbox-from-0-to-one-million-users-in-6-weeks-and-1.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| Pinterest      | [从零到每月数十亿的浏览量](http://highscalability.com/blog/2013/4/15/scaling-pinterest-from-0-to-10s-of-billions-of-page-views-a.html) [1800 万访问用户，10 倍增长，12 名员工](http://highscalability.com/blog/2012/5/21/pinterest-architecture-update-18-million-visitors-10x-growth.html)                                                                                                                                                                                                                                                                                                                                                      |
| Playfish       | [月用户量 5000 万并在不断增长](http://highscalability.com/blog/2010/9/21/playfishs-social-gaming-architecture-50-million-monthly-user.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| PlentyOfFish   | [PlentyOfFish 的架构](http://highscalability.com/plentyoffish-architecture)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| Salesforce     | [他们每天如何处理 13 亿笔交易](http://highscalability.com/blog/2013/9/23/salesforce-architecture-how-they-handle-13-billion-transacti.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| Stack Overflow | [Stack Overflow 的架构](http://highscalability.com/blog/2009/8/5/stack-overflow-architecture.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| TripAdvisor    | [40M 访问者，200M 页面浏览量，30TB 数据](http://highscalability.com/blog/2011/6/27/tripadvisor-architecture-40m-visitors-200m-dynamic-page-view.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Tumblr         | [每月 150 亿的浏览量](http://highscalability.com/blog/2012/2/13/tumblr-architecture-15-billion-page-views-a-month-and-harder.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| Twitter        | [Making Twitter 10000 percent faster](http://highscalability.com/scaling-twitter-making-twitter-10000-percent-faster) [每天使用 MySQL 存储 2.5 亿条 tweet](http://highscalability.com/blog/2011/12/19/how-twitter-stores-250-million-tweets-a-day-using-mysql.html) [150M 活跃用户，300K QPS，22 MB/S 的防火墙](http://highscalability.com/blog/2013/7/8/the-architecture-twitter-uses-to-deal-with-150m-active-users.html) [可扩展时间表](https://www.infoq.com/presentations/Twitter-Timeline-Scalability) [Twitter 的大小数据](https://www.youtube.com/watch?v=5cKTP36HVgI) [Twitter 的行为：规模超过 1 亿用户](https://www.youtube.com/watch?v=z8LU0Cj6BOU) |
| Uber           | [Uber 如何扩展自己的实时化市场](http://highscalability.com/blog/2015/9/14/how-uber-scales-their-real-time-market-platform.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| WhatsApp       | [Facebook 用 190 亿美元购买 WhatsApp 的架构](http://highscalability.com/blog/2014/2/26/the-whatsapp-architecture-facebook-bought-for-19-billion.html)                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| YouTube        | [YouTube 的可扩展性](https://www.youtube.com/watch?v=w5WVu624fY8) [YouTube 的架构](http://highscalability.com/youtube-architecture)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |

### 公司工程博客

- [Airbnb Engineering](http://nerds.airbnb.com/)
- [Atlassian Developers](https://developer.atlassian.com/blog/)
- [Autodesk Engineering](http://cloudengineering.autodesk.com/blog/)
- [AWS Blog](https://aws.amazon.com/blogs/aws/)
- [Bitly Engineering Blog](http://word.bitly.com/)
- [Box Blogs](https://www.box.com/blog/engineering/)
- [Cloudera Developer Blog](http://blog.cloudera.com/blog/)
- [Dropbox Tech Blog](https://tech.dropbox.com/)
- [Engineering at Quora](http://engineering.quora.com/)
- [Ebay Tech Blog](http://www.ebaytechblog.com/)
- [Evernote Tech Blog](https://blog.evernote.com/tech/)
- [Etsy Code as Craft](http://codeascraft.com/)
- [Facebook Engineering](https://www.facebook.com/Engineering)
- [Flickr Code](http://code.flickr.net/)
- [Foursquare Engineering Blog](http://engineering.foursquare.com/)
- [GitHub Engineering Blog](https://github.blog/category/engineering)
- [Google Research Blog](http://googleresearch.blogspot.com/)
- [Groupon Engineering Blog](https://engineering.groupon.com/)
- [Heroku Engineering Blog](https://engineering.heroku.com/)
- [Hubspot Engineering Blog](http://product.hubspot.com/blog/topic/engineering)
- [High Scalability](http://highscalability.com/)
- [Instagram Engineering](http://instagram-engineering.tumblr.com/)
- [Intel Software Blog](https://software.intel.com/en-us/blogs/)
- [Jane Street Tech Blog](https://blogs.janestreet.com/category/ocaml/)
- [LinkedIn Engineering](http://engineering.linkedin.com/blog)
- [Microsoft Engineering](https://engineering.microsoft.com/)
- [Microsoft Python Engineering](https://blogs.msdn.microsoft.com/pythonengineering/)
- [Netflix Tech Blog](http://techblog.netflix.com/)
- [Paypal Developer Blog](https://devblog.paypal.com/category/engineering/)
- [Pinterest Engineering Blog](http://engineering.pinterest.com/)
- [Quora Engineering](https://engineering.quora.com/)
- [Reddit Blog](http://www.redditblog.com/)
- [Salesforce Engineering Blog](https://developer.salesforce.com/blogs/engineering/)
- [Slack Engineering Blog](https://slack.engineering/)
- [Spotify Labs](https://labs.spotify.com/)
- [Twilio Engineering Blog](http://www.twilio.com/engineering)
- [Twitter Engineering](https://engineering.twitter.com/)
- [Uber Engineering Blog](http://eng.uber.com/)
- [Yahoo Engineering Blog](http://yahooeng.tumblr.com/)
- [Yelp Engineering Blog](http://engineeringblog.yelp.com/)
- [Zynga Engineering Blog](https://www.zynga.com/blogs/engineering)
- [Thoughtworks 洞见](https://insights.thoughtworks.cn/tag/featured/)
- [美团技术](https://tech.meituan.com) 
- [淘宝技术](http://mysql.taobao.org/monthly/)