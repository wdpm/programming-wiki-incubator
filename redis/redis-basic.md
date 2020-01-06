# Redis 基础

## 数据结构类型

5种数据结构类型，分别为STRING（字符串）、LIST（列表）、SET（集合）、HASH（散列）和ZSET（有序集合）。

CRUD
- String: set,get,del 整数，浮点，字符串
- List: lpush,rpush,lpop,rpop,lindex,lrange,ltrim
- Set: sadd,smembers,sismember,srem,scard
- Hash: hset,hdel,hget,hlen
- ZSet: zadd,zrem,
---

其他操作
- String: INCR/DECR/INCRBY/DECRBY/INCRBYFLOAT, APPEND/GETRANGE/SETRANGE
- List: blpop,brpop,rpoplpush,brpoplpush
- Set: sdiff,sinter,sunion
- Hash：hexists,hkeys,hvals,hincrby,hgetall
- ZSet：zcard,zcount,zscore,zincrby,zrevrank,
zrange/zrevrange,zrangbyscore/zrevrangebyscore,zinterscore/zunionscore
---

## 其他命令
- 排序 SORT
- 事务 MULTI EXEC WATCH/UNWATCH DISCARD
  - 先执行MULTI命令，输入想要在事务里面执行的命令，最后执行EXEC命令
- 处理过期时间  PERSIST TTL/EXPIRE(s) EXPIREAT PTTL/PEXPIRE(ms)

## 持久化选项

两种磁盘持久性选项： 

- RDB：以指定的时间间隔制作的数据集的时间点快照。
  - BGSAVE：Redis调用fork创建一个子进程，然后子进程负责将快照写入硬盘，而父进程则继续处理命令请求。
  - SAVE：在快照创建完毕之前将不再响应任何其他命令。
  - `save 60 10000`：当“60秒之内有10000次写入”被满足时，自动触发BGSAVE命令。
  - 只使用RDB来保存数据时，如果系统发生崩溃，将丢失最近一次生成快照之后更改的所有数据。
- AOF：服务器执行的所有写操作的仅追加日志。 
  - `appendfsync`: everysec/no/always
  - 考虑AOF文件体积不断增大的问题
  - BGREWRITEAOF命令会通过移除AOF文件中的冗余命令来重写（rewrite）AOF 文件。
  

每个选项都有其优缺点。为了最大程度地提高数据安全性，可考虑同时运行两种持久性方法。

默认情况下RDB已启用，只需要设置AOF持久性即可。

```bash
nano /etc/redis.conf
```

```bash
appendonly yes
appendfsync everysec
# 当AOF文件的体积大于64MB，且AOF文件体积比上一次重写后的体积大至少一倍（100%）的时，执行BGREWRITEAOF
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
```

```bash
systemctl restart redis
```

## 基本系统调整

持久性加大 overcommit memory 的配置参数

```bash
echo "vm.overcommit_memory = 1" >> /etc/sysctl.conf
sysctl -p
```

