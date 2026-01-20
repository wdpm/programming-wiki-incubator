# redis 管道
管道是客户端技术。

客户端通过对管道中的指令列表改变读写顺序可以大幅节省IO 时间。

## 管道压力测试
```bash
[root@master ~]# redis-benchmark -t set -q
SET: 114155.25 requests per second

# use pipeline
# 选项-P参数，表示单个管道内并行的请求数量，P=2
redis-benchmark -t set -P 2 -q
redis-benchmark -t set -P 3 -q
...

[root@master ~]# redis-benchmark -t set -P 2000 -q
SET: 1754386.00 requests per second
```