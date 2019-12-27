# TODO

## goal
- [x] Thread
- [x] ThreadLocal
---
- [x] ~~Random~~
- [x] ThreadLocalRandom(解决Random多线程自旋重试，使用线程本地变量seed)
---
- [ ] AtomicInteger
- [ ] AtomicBoolean
- [x] AtomicLong(多个线程竞争同一个原子变量value)
- [x] LongAdder(内部维护cells数组，空间换时间)
- [x] LongAccumulator(LongAdder的泛化版，功能更自由)
---
- [ ] ConcurrentHashMap
- [x] CopyOnWriteArrayList
- [ ] CopyOnWriteArraySet
- [ ] ArrayBlockingQueue
- [ ] ThreadPoolExecutor
- [ ] Executors
- [ ] FutureTask
- [ ] Semaphore
- [ ] CountDownLatch
- [ ] CyclicBarrier
- [ ] ReentrantLock
- [ ] ReentrantReadWriteLock
- [ ] Condition