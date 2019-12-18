# redis 集群

## 实践篇

### 启动6个实例

创建一个`cluster-test`文件夹，里面分别创建六个子文件夹。

```bash
mkdir cluster-test
cd cluster-test
mkdir 7000 7001 7002 7003 7004 7005
```

在每一个子文件夹中，分别创建`redis.conf`配置文件，分别替换port的值。

```bash
port 7000 
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```

然后使用六个terminal开始6个redis-server实例

```bash
redis-server redis.conf
```

log打印如下

```bash
88680:C 19 Dec 2019 08:02:17.584 # Configuration loaded
88680:M 19 Dec 2019 08:02:17.585 * No cluster configuration found, I'm e7e1f0ddc6f2adfa18105f79698b0362b206f011
```

每个实例都有唯一ID。

### 使用redis-trib创建集群

```
yum install redis-trib -y
```

```bash
[root@k8s-master local]# redis-trib create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
>>> Creating cluster
>>> Performing hash slots allocation on 6 nodes...
Using 3 masters:
127.0.0.1:7000
127.0.0.1:7001
127.0.0.1:7002
Adding replica 127.0.0.1:7003 to 127.0.0.1:7000
Adding replica 127.0.0.1:7004 to 127.0.0.1:7001
Adding replica 127.0.0.1:7005 to 127.0.0.1:7002
M: 521f1849f4e20949841b1a552c98e1b39eb33be6 127.0.0.1:7000
   slots:0-5460 (5461 slots) master
M: 5c01811f99c083e73fa7f799dc5270e8b75f8318 127.0.0.1:7001
   slots:5461-10922 (5462 slots) master
M: f4d8cc2d3dbbd45a8668daec615421561c68bc06 127.0.0.1:7002
   slots:10923-16383 (5461 slots) master
S: 892f9fef725ffc8d935787bfba937b7551f11420 127.0.0.1:7003
   replicates 521f1849f4e20949841b1a552c98e1b39eb33be6
S: cdedd876adb1b6fbf5fd601eb2e691945e855327 127.0.0.1:7004
   replicates 5c01811f99c083e73fa7f799dc5270e8b75f8318
S: e7e1f0ddc6f2adfa18105f79698b0362b206f011 127.0.0.1:7005
   replicates f4d8cc2d3dbbd45a8668daec615421561c68bc06
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join....
>>> Performing Cluster Check (using node 127.0.0.1:7000)
M: 521f1849f4e20949841b1a552c98e1b39eb33be6 127.0.0.1:7000
   slots:0-5460 (5461 slots) master
   1 additional replica(s)
M: f4d8cc2d3dbbd45a8668daec615421561c68bc06 127.0.0.1:7002@17002
   slots:10923-16383 (5461 slots) master
   1 additional replica(s)
S: 892f9fef725ffc8d935787bfba937b7551f11420 127.0.0.1:7003@17003
   slots: (0 slots) slave
   replicates 521f1849f4e20949841b1a552c98e1b39eb33be6
S: e7e1f0ddc6f2adfa18105f79698b0362b206f011 127.0.0.1:7005@17005
   slots: (0 slots) slave
   replicates f4d8cc2d3dbbd45a8668daec615421561c68bc06
M: 5c01811f99c083e73fa7f799dc5270e8b75f8318 127.0.0.1:7001@17001
   slots:5461-10922 (5462 slots) master
   1 additional replica(s)
S: cdedd876adb1b6fbf5fd601eb2e691945e855327 127.0.0.1:7004@17004
   slots: (0 slots) slave
   replicates 5c01811f99c083e73fa7f799dc5270e8b75f8318
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

选项–replicas 1 表示希望为集群中的每个主节点创建一个从节点。

其他参数则是这个集群实例的地址列表，3个master，3个slave。

## 参考

- [1]  http://www.redis.cn/topics/cluster-tutorial.html 

