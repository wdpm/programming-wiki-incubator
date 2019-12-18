# Redis 哨兵 (实践篇)

## 准备

**一个健壮的部署至少需要三个Sentinel实例**。

准备好一个主节点和两个从节点

- 主节点：192.168.31.12 6379
- 从节点node1: 192.168.31.13 6379
- 从节点node2: 192.168.31.14 6379

主节点

```bash
[root@k8s-master elasticsearch]# redis-cli
127.0.0.1:6379> INFO replication
# Replication
role:master
connected_slaves:2
slave0:ip=192.168.31.14,port=6379,state=online,offset=3417,lag=1
slave1:ip=192.168.31.13,port=6379,state=online,offset=3431,lag=0
...
```

从节点node1

```bash
[root@k8s-node1 ~]# redis-cli
127.0.0.1:6379> INFO replication
# Replication
role:slave
master_host:192.168.31.12
master_port:6379
master_link_status:up
master_last_io_seconds_ago:4
master_sync_in_progress:0
...
```

从节点node2

```bash
[root@k8s-node2 ~]# redis-cli
127.0.0.1:6379> INFO replication
# Replication
role:slave
master_host:192.168.31.12
master_port:6379
master_link_status:up
master_last_io_seconds_ago:6
master_sync_in_progress:0
...
```

## sentinel配置

假设Sentinel实例是端口分别为5000、5001、5002。

Redis主服务器端口为6379，副本服务器端口为6380。**这里不使用IPv4环回地址127.0.0.1**。 

`sentinel_on_master.conf`

```conf
protected-mode no
bind 0.0.0.0
port 5000
sentinel monitor mymaster 192.168.31.12 6379 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 60000
sentinel parallel-syncs mymaster 1
```

`sentinel_on_node1.conf`

```conf
protected-mode no
bind 0.0.0.0
port 5001
sentinel monitor mymaster 192.168.31.12 6379 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 60000
sentinel parallel-syncs mymaster 1
```

`sentinel_on_node2.conf`

```conf
protected-mode no
bind 0.0.0.0
port 5002
sentinel monitor mymaster 192.168.31.12 6379 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 60000
sentinel parallel-syncs mymaster 1
```

down-after-milliseconds 值是5000毫秒，即5秒。因此，在5秒内没有收到来自ping的任何答复，master就会被检测为失败。

## 启动sentinel

分别在master，node1，node2节点上启动sentinel。

```bash
redis-sentinel /etc/redis/sentinel_on_matser.conf
redis-sentinel /etc/redis/sentinel_on_node1.conf
redis-sentinel /etc/redis/sentinel_on_node2.conf
```

验证sentinel是否正常。

> 请注意redis sever需要设置protected-mode为no。

master的log

```bash
79918:X 19 Dec 2019 06:45:27.723 # Sentinel ID is 52a6791a66983975280514ccca07a51af6e3cbf1
79918:X 19 Dec 2019 06:45:27.723 # +monitor master mymaster 192.168.31.12 6379 quorum 2
79918:X 19 Dec 2019 06:59:21.313 * +slave slave 192.168.31.13:6379 192.168.31.13 6379 @ mymaster 192.168.31.12 6379
79918:X 19 Dec 2019 07:08:50.037 * +sentinel sentinel 63c93570bc9b557577299c1571cba677d1d129f4 192.168.31.13 5001 @ mymaster 192.168.31.12 6379
79918:X 19 Dec 2019 07:33:29.034 * +slave slave 192.168.31.14:6379 192.168.31.14 6379 @ mymaster 192.168.31.12 6379
79918:X 19 Dec 2019 07:34:11.537 * +sentinel sentinel 9817b34a4aacb6d136b34b3c215f15d9d483506f 192.168.31.14 5002 @ mymaster 192.168.31.12 6379
```

可以看出master拥有一个+monitor 以及两个+slave，两个+sentinel的信息。

---

node1的log

```bash
38725:X 18 Dec 2019 23:09:07.223 # Sentinel ID is 63c93570bc9b557577299c1571cba677d1d129f4
38725:X 18 Dec 2019 23:09:07.223 # +monitor master mymaster 192.168.31.12 6379 quorum 2
38725:X 18 Dec 2019 23:09:07.225 * +slave slave 192.168.31.13:6379 192.168.31.13 6379 @ mymaster 192.168.31.12 6379
38725:X 18 Dec 2019 23:09:07.674 * +sentinel sentinel 52a6791a66983975280514ccca07a51af6e3cbf1 192.168.31.12 5000 @ mymaster 192.168.31.12 6379
38725:X 18 Dec 2019 23:33:53.176 * +slave slave 192.168.31.14:6379 192.168.31.14 6379 @ mymaster 192.168.31.12 6379
38725:X 18 Dec 2019 23:34:30.776 * +sentinel sentinel 9817b34a4aacb6d136b34b3c215f15d9d483506f 192.168.31.14 5002 @ mymaster 192.168.31.12 6379
```

node1拥有一个+monitor 以及两个+slave，两个+sentinel的信息。

---

node2的log

```bash
40915:X 19 Dec 2019 07:34:18.615 # Sentinel ID is 9817b34a4aacb6d136b34b3c215f15d9d483506f
40915:X 19 Dec 2019 07:34:18.615 # +monitor master mymaster 192.168.31.12 6379 quorum 2
40915:X 19 Dec 2019 07:34:18.617 * +slave slave 192.168.31.13:6379 192.168.31.13 6379 @ mymaster 192.168.31.12 6379
40915:X 19 Dec 2019 07:34:18.618 * +slave slave 192.168.31.14:6379 192.168.31.14 6379 @ mymaster 192.168.31.12 6379
40915:X 19 Dec 2019 07:34:18.944 * +sentinel sentinel 52a6791a66983975280514ccca07a51af6e3cbf1 192.168.31.12 5000 @ mymaster 192.168.31.12 6379
40915:X 19 Dec 2019 07:34:19.005 * +sentinel sentinel 63c93570bc9b557577299c1571cba677d1d129f4 192.168.31.13 5001 @ mymaster 192.168.31.12 6379
```

node2拥有一个+monitor 以及两个+slave，两个+sentinel的信息。

---

一切sentinel都很正常。

## 查看主从状态

在master

```bash
[root@k8s-master ~]# redis-cli -p 5000
127.0.0.1:5000> sentinel master mymaster
```

```bash
127.0.0.1:5000> SENTINEL slaves mymaster
```

```bash
127.0.0.1:5000> SENTINEL sentinels mymaster
```

分别查看master，slaves，sentinels的信息。

## 测试故障转移

可以杀死我们的redis master，然后检查配置是否更改。

```bash
redis-cli -p 6379 DEBUG sleep 30
```

一段时间后，master变成了slave，

```bash
[root@k8s-master ~]# redis-cli
127.0.0.1:6379> INFO replication
# Replication
role:slave
master_host:192.168.31.14
master_port:6379
```

而之前的一个slave（node2）变成了新的master。

```bash
[root@k8s-node2 ~]# redis-cli
127.0.0.1:6379> INFO replication
# Replication
role:master
connected_slaves:2
slave0:ip=192.168.31.13,port=6379,state=online,offset=445418,lag=0
slave1:ip=192.168.31.12,port=6379,state=online,offset=445138,lag=1
```



## 哨兵配置解释

**默认情况下，Sentinels运行监听与TCP端口26379的连接**。

配置解释：

`sentinel monitor <master-group-name> <ip> <port> <quorum>`

quorum: 法定人数，**在主机不可访问情况下，这一事实达成共识的哨兵数量**。以便将主机真正标记为发生故障，并在可能的情况下最终启动故障转移过程。

例如，如果有5个Sentinel进程，并且给定主服务器的仲裁设置为2，则将发生以下情况：

- 如果两个Sentinel同时同意主机不可访问，则其中两个将尝试启动故障转移。 
- 如果总共至少有三个Sentinel可以访问，则故障转移将被授权并实际上开始。

 实际上，在故障期间，**如果大多数Sentinel进程无法进行对话，则Sentinel永远不会启动故障转移**。 

---

`sentinel <option_name> <master_name> <option_value>`

down-after-milliseconds: 指Sentinel开始认为已关闭的实例无法到达的时间（以毫秒为单位）（实例未答复我们的PING或它正在答复错误）。

parallel-syncs：设置可在故障转移后同时重新配置为使用新的主master的副本数。数字越小，故障转移过程完成所需的时间就越多。 

所有配置参数都可以在运行时使用SENTINEL SET命令进行修改。

---

> 如果遇见这句提示:
>
> WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
>
> 可以执行：
> echo "net.core.somaxconn=65535" >> /etc/sysctl.conf
> && sysctl -p



## 参考

- [1]  https://redis.io/topics/sentinel 