# 阅读 Redis 源码的顺序参考
> 版本为 Redis 5.0.7

## 1.数据结构实现

| 文件                                                | 描述           |
| --------------------------------------------------- | -------------- |
| sds.h sds.c                                         | 简单动态字符串 |
| adlist.h adlist.c                                   | 双端链表       |
| dict.h dict.c                                       | 字典           |
| server.h server.c 中搜索 zskiplist，t_zset中搜索zsl | 跳跃表         |
| rax.h rax.c                                         | 基数树         |
| hyperloglog.c 中 hllhdr 结构                        | HyperLogLog    |

## 2.内存编码数据结构实现

| 文件                  | 描述                                    |
| --------------------- | --------------------------------------- |
| intset.h intset.c     | 整数集合                                |
| ziplist.h ziplist.c   | 压缩列表                                |
| quicklist.c           | A doubly linked list of ziplists        |
| listpack.h listpack.c | A lists of strings serialization format |

## 3.Redis 数据类型的实现

Redis 数据类型利用1&2来实现。

| 文件                         | 描述       |
| ---------------------------- | ---------- |
| object.c 重点                | 对象类型   |
| t_string.c                   | 字符串     |
| t_list.c                     | 列表       |
| t_hash.c                     | 散列       |
| t_set.c                      | 集合       |
| t_zset.c                     | 有序集合   |
| t_stream.c                   | stream结构 |
| hyperloglog.c 中 pf 开头函数 | HLL        |

## 4.数据库相关实现

| 文件                           | 描述      |
| ------------------------------ | --------- |
| server.c server.h 搜索 redisDb | 数据库    |
| notify.c                       | 通知功能  |
| rdb.h rdb.c                    | RDB持久化 |
| aof.h aof.c                    | AOF持久化 |

选读

| 文件                                | 描述                                   |
| ----------------------------------- | -------------------------------------- |
| db.c                                | C level DB API                         |
| server.h 中 pubsubPattern，pubsub.c | 发布订阅功能                           |
| server.h 中 multiState，multi.c     | 事务功能                               |
| sort.c                              | SORT command and helper functions      |
| pqsort.c  pqsort.h                  | 快速排序                               |
| bitops.c                            | 二进制位操作                           |
| bio.h bio.c                         | 后台 I/O ，将 I/O 放到子线程执行       |
| rio.h rio.c                         | simple stream-oriented I/O abstraction |
| lzf*                                | RDB 压缩算法                           |

## 5.客户端和服务器代码实现

| 文件              | 描述                                 |
| ----------------- | ------------------------------------ |
| redis-cli.c       | Redis CLI (command line interface)   |
| ae.c ae.h ae_*.c  | Redis 的事件处理器                   |
| networking.c      | Redis 的网络连接库                   |
| anet.c anet.h     | 异步网络框架，主要为 socket 库的包装 |
| config.c config.h | 服务器配置管理                       |

选读

| 文件                    | 描述         |
| ----------------------- | ------------ |
| scripting.c             | Lua 脚本功能 |
| slowlog.c               | 慢查询功能   |
| monitor.c               | 监视器功能   |
| crc16.c crc64.h crc64.c | crc检验和    |

## 6.多机功能实现

| 文件          | 描述     |
| ------------- | -------- |
| replication.c | 复制功能 |
| sentinel.c    | Sentinel |
| cluster.c     | 集群     |
| redis-trib.rb | 集群管理 |

