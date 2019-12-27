# ReentrantReadWriteLock

ReentrantReadWriteLock提供了读锁（ReadLock）和写锁（WriteLock），相比ReentrantLock只有一把锁，读写锁分离可以在读多写少的场景中提升读的性能。

## 概述

### 获得锁的机制

- **当调用读锁的lock方法**

如果没有线程持有写锁，就可获得读锁。

- **当调用写锁的lock方法**

如果没有线程持有读锁或写锁，可继续执行。如果有其他线程在读或在写，就会被阻塞，所以写的性能一般比较差。因为很多时候都至少有其他线程在读。

### 锁升降级机制

- 同一线程中，持有读锁后，不能直接调用写锁的lock方法
- 同一线程中，持有写锁后，可调用读锁的lock方法。之后如果调用写锁的unlock方法，当前锁将降级为读锁。

即读锁不能升为写锁，写锁可以降级为读锁。

### ReentrantReadWriteLock的性能提升

测试场景为同时启动1000个线程，其中900个进行读操作，100个进行写操作。

ReentrantReadWriteLock用时10s，ReentrantLock用时100s。

## 使用例子

## 源码解读

TODO

