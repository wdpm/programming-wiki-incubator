# redis 可重入锁
- setnx 和 expire 组合在一起的原子指令，是分布式锁的关键。

## 代码实现
[RedisWithReentrantLock](..\src\main\java\io\github\wdpm\redis\RedisWithReentrantLock.java)

## 集群下的分布式锁
- redlock