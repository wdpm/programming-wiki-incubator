# redis 延迟队列

延时队列可以通过 zset(有序列表) 来实现。
- 将消息序列化成一个字符串作为 zset 的value，这个消息的到期处理时间作为score
- 用多个线程轮询 zset 获取到期的任务进行处理，多个线程是为了保障可用性，挂了一个线程还有其它线程可以继续处理。
- 因为有多个线程，需要考虑并发争抢任务，确保任务不能被多次执行。

Redis 的 zrem 方法是多线程多进程争抢任务的关键，返回值决定了当前实例有没有抢到任务。

## 代码示例
[RedisDelayingQueue](..\src\main\java\io\github\wdpm\redis\RedisDelayingQueue.java)