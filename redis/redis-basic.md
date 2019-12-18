# Redis 基础

## 数据结构类型

字符串(String)，哈希(Hash)，列表(List)，集合(Set)。

## 持久化选项

两种磁盘持久性选项： 

- RDB：以指定的时间间隔制作的数据集的时间点快照。 
- AOF：服务器执行的所有写操作的仅追加日志。 

每个选项都有其优缺点。为了最大程度地提高数据安全性，可以考虑同时运行两种持久性方法。

默认情况下RDB已启用，只需要设置AOF持久性即可。

```bash
nano /etc/redis.conf
```

```bash
appendonly yes
appendfsync everysec
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

