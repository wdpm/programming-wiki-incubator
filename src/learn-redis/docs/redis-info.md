# redis info指令
1. Server 服务器运行的环境参数
2. Clients 客户端相关信息
3. Memory 服务器运行内存统计数据
4. Persistence 持久化信息
5. Stats 通用统计数据
6. Replication 主从复制相关信息
7. CPU CPU 使用情况
8. Cluster 集群信息
9. KeySpace 键值对统计数量信息

- Redis 每秒执行多少次指令？
```
redis-cli info stats |grep ops
```

- Redis 连接了多少客户端？
```
redis-cli info clients
```

- 被拒绝的客户端连接次数
```
redis-cli info stats |grep reject
```

- Redis 内存占用多大 ?
```
redis-cli info memory | grep used | grep human
```

- 复制积压缓冲区多大？
```
redis-cli info replication |grep backlog
```
```
# 查看sync_partial_err变量的次数来决定是否需要扩大积压缓冲区，它表示主从半同步复制失败的次数
redis-cli info stats | grep sync
```