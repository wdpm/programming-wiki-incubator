# ReentrantLock

ReentrantLock用于控制并发资源，和synchronized的效果差不多。CopyOnWriteArrayList中的写操作用的就是ReentrantLock。

## ReentrantLock源码解析
ReentrantLock()

根据构造函数参数创建公平锁和不公锁。

lock()

CAS自旋尝试获取锁，成功的话就更新state，不成功的话继续重试。

unlock()

NonfairSync和FairSync的实现相同：获取现在的state，state值减去释放的锁的个数。如果减出来后的值为0，则释放锁，并通知等待在Queue上的线程，并将等待在Queue上的第一个线程恢复，恢复的方法为：Unsafe.getUnsafe().unpark(thread)。

