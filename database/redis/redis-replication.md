# Redis 主从复制

环境：
- CentOS 7
- Redis 5.0.7

## 实践篇

### 建立主节点

> 参考单节点redis的安装过程: yum install redis

假设主节点IP为 192.168.31.12，端口为 6379。

### 建立从节点

假设从节点IP为 192.168.31.13，端口为 6379。

```bash
nano /etc/redis.conf
```

```bash
replicaof 192.168.31.12 6379
```

```bash
systemctl restart redis
```

### 确认主从节点集群正常

在主节点：

```bash
[root@vmware0 ~]# redis-cli
127.0.0.1:6379> INFO replication
# Replication
role:master
connected_slaves:1
slave0:ip=192.168.31.13,port=6379,state=online,offset=71,lag=0
master_repl_offset:71
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:2
repl_backlog_histlen:70
```

在从节点：

```bash
[root@vmware1 ~]# redis-cli
127.0.0.1:6379> INFO replication
# Replication
role:slave
master_host:192.168.31.12
master_port:6379
master_link_status:up
master_last_io_seconds_ago:7
master_sync_in_progress:0
slave_repl_offset:57
slave_priority:100
slave_read_only:1
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```

验证主节点数据会同步到从节点。

在主节点写入信息：

```bash
127.0.0.1:6379> set a 1
OK
```

在从节点获取信息：

```bash
127.0.0.1:6379> get a
"1"
```

### 保护Redis

开启密码验证。

1. 主节点开启密码

```bash
nano /etc/redis.conf
requirepass master_pass
systemctl restart redis
```

2. 从节点开启密码

```bash
nano /etc/redis.conf
masterauth master_pass
requirepass slave_pass
systemctl restart redis
```

3. 主节点开启AUTH

```bash
[root@vmware0 ~]# redis-cli
127.0.0.1:6379> AUTH master_pass
OK
```

4. 从节点开启AUTH

```bash
[root@vmware1 ~]# redis-cli
127.0.0.1:6379> AUTH slave_pass
OK
127.0.0.1:6379> INFO replication
# Replication
role:slave
master_host:192.168.31.12
master_port:6379
master_link_status:up
master_last_io_seconds_ago:8
master_sync_in_progress:0
slave_repl_offset:239
slave_priority:100
slave_read_only:1
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```

## 理论篇

主数据库执行完客户端请求的命令后会立即将命令在主数据库的执行结果返回给客户端，并异步地将命令同步给从数据库，不会等待从数据库接收到该命令后再返回给客户端。

这一特性保证了启用复制后主数据库的性能不会受到影响，但另一方面也会产生一个主从数据库数据不一致的时间窗口，当主数据库执行了一条写命令后，主数据库的数据已经发生变动，然而在主数据库将该命令传送给从数据库之前，如果两个数据库之间的网络连接断开，此时二者之间的数据就会不一致。从这个角度来看，主数据库无法得知某个命令最终同步给了多少个从数据库。

Redis 提供了两个选项来限制：

```
min-slaves-to-write 3
min-slaves-max-lag 10
```

- min-slaves-to-write表示只有当3个或3个以上的从数据库连接到主数据库时，主数据库才是可写的。
- min-slaves-max-lag 表示允许从数据库最长失去连接的时间。

---

通过复制可以实现读写分离，以提高服务器的负载能力。

主数据库只进行写操作，从数据库负责读操作。这种一主多从的结构适合读多写少的场景，而当单个主数据库不能满足需求时，需要使用集群。

---

手动通过从数据库数据恢复主数据库数据时，需要严格按照以下两步进行。

（1）在从数据库中使用 SLAVEOF NO ONE命令将从数据库提升成主数据库继续服务。

（2）启动之前崩溃的主数据库，然后使用SLAVEOF命令将其设置成新的主数据库的从数据库，将数据同步回来。

---

无硬盘复制的功能还在试验阶段，可以在配置文件中开启：repl-diskless-sync yes