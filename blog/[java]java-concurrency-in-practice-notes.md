# Lets java concurrency
代码位置：programming-language/java/JCIP

## 阅读 JCIP
8. [线程池的使用](docs/jcip/8.threadpools.md) 
9. [图形用户界面应用程序](docs/jcip/9.guitool.md)
10. [避免活跃性危险](docs/jcip/10.livenesshazards.md) 
11. [性能与可伸缩性](docs/jcip/11.performance.md) 
12. [并发程序的测试](docs/jcip/12.testingconcurrent.md) 
13. [显式锁](docs/jcip/13.explicitlocks.md) 
14. [构建自定义的同步工具](docs/jcip/14.custom.md) 
15. [原子变量与非阻塞同步机制](docs/jcip/15.atomicvariable.md) 
16. [Java内存模型](docs/jcip/16.jmm.md)

## 简介
- 线程安全的代码：管理state的访问，一般是可变、共享的状态。
    - 可变：变量的值可以更改
    - 共享：变量可以被多个线程访问
- 保证线程安全的方法
    - 不要跨线程共享变量
    - 使用不可变的state变量
    - 访问state变量时使用同步

## 线程安全性
- 类线程安全的定义：被多个线程访问时，类的行为依然正确。
- 线程安全的类已经封装了同步，客户端不需要自己提供。
- 无状态的对象永远线程安全。
- 为保证state一致性，需要在原子操作中更新相关联的state变量。
- java 强制原子性的内部锁机制：synchronized 块。
- 每个共享的state变量都需要唯一一个确定的锁来保护，维护者必须知道这个锁。
- 对于每一个涉及变量的不变约束，需要同一个锁保护其所有的变量。参阅 SynchronizedFactorizer

## 对象的共享
- 锁代表着：同步/互斥(原子性) + 内存可见性。保证一个线程对数值的写入，其他线程都可见。
- volatile仅仅代表：内存可见性。
- 线程封闭：如果数据只在单线程中被访问，就不要任何的同步。线程封闭保证了原子性。
- 如果确保单一线程写入一个共享volatile变量i，那么i++也就是线程安全的。因为单一线程保证原子性，volatile本身提供可见性。
- ThreadLocal 确保线程封闭：get总是返回当前线程通过set设置的最新值。
- 不可变对象的性质
    - 它的state在创建后无法修改
    - 所有filed都是final
    - 它被正确地创建（创建时this没有逃逸出去）
- 最佳实践：将field设置为private，除非你想暴露它；将field设置为final，除非它是可变的。
- 如果final指向可变对象，访问这些对象的state时依然需要同步。
- 安全发布的模式：对象的引用和对象的state必须同时对其他线程可见
    - 静态初始化器初始化对象的引用
    - 将该引用保存到volatile或者AtomicReference的field
    - 将该引用保存到正确对象的final域中
    - 或者，将该引用保存到由锁保护的域中
- 静态发布：`public static Holder holder = new Holder(32);` JVM在类的初始化阶段执行，由JVM内在的同步保证安全发布。
- 共享对象的有效策略
    - 线程限制：只能被占有它的线程修改
    - 共享只读：任何线程都不能修改它
    - 共享线程安全：一个线程安全的对象在内部进行同步
    - 被守护：一个被守护的对象只能通过特定的锁来访问。

## 对象的组合
- 设计线程安全的类
    - 确定对象state变量有哪些
    - 确定限制state变量的不变约束
    - 指定管理并发访问对象state的策略
- 对象的state取决于它的field。Counter 只有一个value，那么state就只有一个。
- 理解对象的不变约束和后验条件，才能保证线程安全。
- state依赖的操作：无法从空队列取元素，无法向满队列放元素。
- 将数据封装于对象内部，把对数据的访问限制于对象的方法上。
- ArrayList和HashMap不是线程安全，通过包装器工厂方法Collections.synchronizedList等可以
  保证线程安全。原理：利用修饰器模式将相关API方法实现为同步，请求转发到底层容器。
- 如果一个类有多个独立的线程安全的state变量组成，类也不包含state转换，
  那么可以将线程安全委托给这些变量。
- 客户端加锁必须保证使用的锁和对象X保护自身state的锁一样。
- 同步策略的文档化：为类的用户编写线程安全性担保的文档，为类的维护者编写类的同步策略的文档。

## 基础构建模块
- 同步容器的迭代器和ConcurrentModificationException：及时失败策略，察觉到被别人修改了，就果断报错。
    - 原理：使用modCount记录修改次数，迭代期间，如果发现该计数器数值变了，代表其他线程做出影响长度的修改，抛出CME。
- 迭代期间，对容器加锁的替代方法是复制容器。CopyOnWrite ?
- 同步容器对容器所有state进行串行访问，同步粒度过大，影响了并发性，也就是削弱了性能。
- JDK 5.0+引入并发容器，例如ConcurrentHashMap和CopyOnWriteArrayList，ConcurrentLinkedQueue
- JDK 6.0+引入ConcurrentSkipListMap和ConcurrentSkipListSet，作为对SortMap和SortSet的并发替代。
- JDK 7中，ConcurrentHashMap使用分段锁机制，每一段一个锁，支持有限数量的写线程并发修改。
    - 它的迭代器是弱一致性的，允许并发修改，可以（但不保证）能看到迭代器创建之后，容器的修改。
    - size的返回值是不精确的。
    - 具有原子操作：putIfAbsent，removeIfEqual，replaceIfEqual
    - 缺点：无法独占加锁。
- CopyOnWriteList：写时复制的迭代器返回的元素和迭代器创建时严格一致，不会考虑后续的修改。
- BlockingQueue 提供可阻塞的put和take方法，与定时的offer、poll是等价的。
- BlockingQueue 的实现有LinkedBlockingQueue、ArrayBlockingQueue和SynchronousQueue。
- 对象池拓展了连续的线程限制，将“对象”借给一个请求线程。
- 双端队列：JDK 6.0+新增Deque和Blocking1Queue。Deque是双端队列，实现为ArrayDeque和LinkedBlockingDeque。
- 工作窃取：一个消费者完成自身双端队列的任务后，可以去其他消费者的双端队列尾部偷任务。
- Thread 提供了中断机制。一个线程不能强迫其他线程停止正在做的事情，或者去做其他事情。
  A中断B，仅仅代表A要求B在达成某个方便停止的关键点时，停止正在做的事情。
- 一个方法可以抛出InterruptedException时，代表这是一个可阻塞的方法。
- 响应中断的策略
    - 向上级传递InterruptedException
    - 捕获InterruptedException，在当前线程调用interrupt()恢复中断。恢复中断是为了不吞掉这个中断，避免掩盖中断。
    - 注意，不应该捕获它，然后什么都不做。
- synchronizer是一个对象，根据本身state调节线程控制流。
    - 阻塞队列可以扮演sync角色；其他synchronizer包括Semaphore（信号量）、barrier（关卡），latch（闭锁）。
    - sync封装state，决定着线程在某个时间点汇聚时，是通过或者等待。
- 闭锁（latch），可以延迟线程进度到终止状态。开始阀门能同时释放所有工作者线程，结束阀门可以等待最后一个线程执行完毕。
- FutureTask有三个state：等待，运行和完成。
- 信号量管理一个许可集。许可数量通过构造函数传入初始化。acquire请求许可，release释放许可。
- 信号量可以将任何容器转化为有界的阻塞容器。参阅 BoundedHashSet
- 应该在文档中注解某个类是否为线程安全。

## 任务执行
- 大部分服务器都采用这种任务边界：独立的客户请求。
- 无限制创建线程的缺点：线程生命周期开销，资源消耗量，稳定性。
- 服务器高负载下的劣化策略：平缓劣化。
- 使用Executor来实现生产者-消费者的设计是很简单的。
- 使用线程池的WebServer。参阅 TaskExecutionWebServer
- 当看到 `new Thread(r).start();`这种代码时，请考虑使用Executor替代。
- 线程池
    - newFixedThreadPool：定长线程池，直到达到池的最大长度。
    - newCachedThreadPool：可缓存的线程池，池的长度不限制。
    - newSingleThreadPool：FIFO，单线程模型。
    - newScheduledThreadPool：定长线程池，支持定时或者周期性任务。
- Executor的生命周期
    - 运行 runing：创建后的状态就是运行
    - 关闭 shut down
        - shutdown()调用之后，平缓关闭：停止接受新任务，等待已提交任务和未完成的任务执行完毕。
        - shutdownNow() 调用之后，强制关闭：取消一切运行中任务和队列中任务。
        - 关闭后提交到ExecutorService的任务，会被拒绝处理器处理。
    - 终止 terminated：所有任务完成后，进入终止状态。可以
        - 调用awaitTermination等待ExecutorService到达终止
        - 也可以轮询isTerminated判断是否已经终止
        - 通常 shutdown会紧跟awaitTermination
- DelayQueue可以帮助构建定时任务。
- 一个Executor执行的任务的生命周期有四个：
    - 创建：不需要取消
    - 提交：可以被取消
    - 开始：只有它们响应中断，才能被取消
    - 完成：都已经生米煮成熟饭了，还取消个锤子？
- 场景：你向Executor提交了一个批处理任务，希望在它们完成后获得结果，为此你保存了
  与每个任务相关连的Future，使用get不断轮询检查是否已经完成。这样的确可行，
  然而有更好的方法：完成服务 CompletionService
- CompletionService 整合了Executor和BlockingQueue，ExecutorCompletionService是CompletionService接口的
  一个实现，将计算委托给Executor。参阅 ExecutorCompletionService.QueueingFuture
- `exec.invokeAll(tasks, time, unit);` 可以执行一系列future，同时限制timeout，要么在规定时间内返回正确结果，要么被取消。
  客户端可以通过get或者isCancelled来判断是哪一种情况。
- Executor执行器有助于解耦任务的提交和任务的不同执行策略。

## 取消与关闭
- 反例：不可靠的取消会把生产者置于阻塞的操作中。参阅 BrokenPrimeProducer。
- 中断是实现取消最明智的选择。
- 三个中断方法的区别
    - interrupt() 中断当前线程，设置中断状态
    - static interrupted() 仅仅清除目标线程的中断状态，并返回之前的值。这是清除中断状态的唯一方法。
    - isInterrupted() 测试此线程是否已被中断，不会清除目标线程的中断状态
- 阻塞库函数：Thread.sleep和Object.wait，对中断的表现是：清除中断状态，抛出InterruptedException
- 对中断最好的理解：中断仅仅意味着传递请求中断的消息，目标线程究竟如何响应中断，那是它的事情。
- 静态的 interrupted()方法，会清除中断状态。如果你调用了interrupted()，它返回true，
  那么你必须处理，除非你想掩盖这个中断。你可以抛出InterruptedException，或者再次调用interrupt来保存中断状态。
- 如果对中断的处理不仅仅是抛出InterruptedException，那么应该在捕获InterruptedException后，调用Interrupt保存中断状态。
- 每个线程都有自己的中断策略，所以不应该随意中断线程，除非你知道中断对这个线程意味着什么。
- 响应中断的策略
    - 传递异常，使你的方法也成为可中断的阻塞方法。throws InterruptedException
    - 保存中断状态，上层调用栈代码能够对其处理。interrupt()
    - 千万不要catch 中断，然后什么都不做，这种做法就是在掩盖错误。
- 只有实现了线程中断策略的代码才可以接受中断请求。
- 当Future.get抛出TimeoutException或者 ExecutionException时，如果你不再需要结果，请调用Future.cancel取消任务。
- 处理不可中断的阻塞
    - java.io Socket.I/O => 关闭底层 Socket，抛出SocketException
    - java.nio 的同步I/O => 关闭 InterruptibleChannel
    - Selector 的异步I/O => close()导致抛出ClosedSelectorException
    - 获得锁。一个线程等待内部锁，然而可能永远也拿不到这个锁。lockInterruptibly()
- 另一个保证生产者和消费者服务关闭的方式是使用致命药丸。一个可识别的对象，拿到时停止自己的工作。
    - 致命药丸只有在生产者和消费者数量已知的情况下使用。
- 长时间运行的应用程序中，所有线程都要给未捕获异常设置一个处理器UncaughtExceptionHandler，至少记录日志。参阅 UEHLogger
- 通过execute提交的任务，才能将它抛出的异常送给UncaughtExceptionHandler；submit提交的任务，抛出任务异常，都被认为是任务返回状态的一部分，
  封装于ExecutionException中。
- 避免使用finalizer，使用finally+显示close，或者Try-with-resource。

## 线程池的使用
- 线程饥饿死锁。如果一个线程池中一个任务A依赖于其他任务的执行，就可能产生死锁。
- 对于一个单线程的Executor，一个任务将另一个任务提交到相同的Executor中，并等待新提交任务的结果，
  就会发生死锁。代码参阅 ThreadDeadlock
    - 第二个任务排在第一个任务后面，等待第一个任务的完成。
    - 但是，第一个任务永远不会完成，因为它在等待第二个任务的完成。
- ThreadPoolExecutor的构造函数
  ```java
      public ThreadPoolExecutor(int corePoolSize,
                                int maximumPoolSize,
                                long keepAliveTime,
                                TimeUnit unit,
                                BlockingQueue<Runnable> workQueue,
                                ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {
  ```
- 第一第二参数：
    - newFixedThreadPool 设置了核心池和最大池的大小，池不会超时
    - newCachedThreadPool 设置了最大池为Integer.MAX_VALUE，核心池大小为0，超时为1min。
    - 其他组合，可以使用显式的ThreadPoolExecutor构造函数实现
- 第三第四参数：
    - 超时数值
    - 超时单位
- 第五个参数
    - 提供一个BlockingQueue来排队等待的任务。任务排队有3个方式：无限队列、有界队列、同步移交。
    - newFixedThreadPool和newSingleThreadPool默认使用无限的LinkedBlockingQueue。
    - 比较稳妥的资源管理策略是有界队列，但是队列满之后怎么办？饱和策略处理这个问题。
    - 先进先出的队列，使用LinkedBlockingQueue或者ArrayBlockingQueue
    - 优先级队列，使用PriorityBlockingQueue。任务实现Comparable接口或者Comparator
    - 同步移交队列，使用SynchronousQueue。
- 第6个参数：线程工厂
  ```java
  public interface ThreadFactory {
      Thread newThread(Runnable r);
  }
  ```
    - 简单实现：return new Thread(r);
- 第七个参数：饱和策略
    - AbortPolicy: 默认策略，抛出未检查的RejectedExecutionException
    - CallerRunsPolicy：调用者运行，这个任务在调用了execute的线程执行。
    - DiscardPolicy：简单抛弃这个任务
    - DiscardOldestPolicy：抛弃最旧的任务。最旧的含义：本应该接下来执行的任务。
        - 参阅 java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy
- 服务器过载的平缓劣化：从池线程->工作队列->应用程序->TCP层->用户，一层一层甩锅。
- 扩展 ThreadPoolExecutor： 它提供一个可重写的钩子：beforeExecute、afterExecute、terminate。例子参阅TimingThreadPool
- 并行算法：如果需要提交一个任务集合并等待完成，可以使用ExecutorService.invokeAll();当所有结果可用后，使用CompletionService获取结果。
  参阅 Renderer。

## 图形用户界面应用程序
- Future的cancel，并设置mayInterruptIfRunning为true，可以中断一个执行运行中任务的线程。
- 线程安全的数据模型。版本化数据模型，例如CopyOnWrite，这个迭代器遍历的是它创建时的那个容器。
- 分析数据模型：app包含表现域，应用域，这就是分拆模型设计。

## 避免活跃性危险
- 死锁经典问题：哲学家就餐。解决方案：如果你不能同时获取两个筷子，那就放弃你的一个筷子。
- 数据库解决死锁：某个事务发生死锁（通过正在等待的有向图中搜索循坏链路），选择一个牺牲者，叫他放弃这个事务。
  这个牺牲者释放的资源，就能打破死锁僵局。
- 所有线程以固定顺序获取锁，就不会出现锁顺序死锁的问题。
- 简单的锁顺序死锁例子。致命的拥抱：LeftRightDeadlock
- 当调用的方法不需要持有锁时，称为开放调用 Open Call。参阅 CooperatingNoDeadlock
- 活锁：线程活跃度失败的一种形式。没有被阻塞，不断重试相同的操作，却总是失败。
    - 随机等待一段时间再重试，或者放弃，都能有效避免活锁的发生。
- 开放调用，可以大大减少一个线程一次请求多个锁的情况。

## 性能与可伸缩性
- 先是正确性，然后才是性能。
- Amdahl定律：基于可并行化和串行化的组件的比例，程序理论上能加速多少。
- 分拆锁和分离锁的技术
    - 分拆锁：把一个锁分成多个。?例如全局锁this包含A，B域，但是我只想更改A，我没有必要同步this。
    - 分离锁：把一个锁分成多个锁。?
- 上下文切换：保存当前运行线程的执行上下文，重建新调入线程的执行上下文。
- 上下文切换的开销一般是5000-10000时钟周期，几ms。
- 自旋CAS适合短期的等待，挂起suspending适合长时间等待。
- 串行化会损害可伸缩性，上下文切换会损害性能。
- 并发程序中，对可伸缩性的主要威胁就是独占资源锁。于是诞生了ConcurrencyHashMap，放弃独占加锁，提高并发性能。
- 减少锁竞争的方式
    - 减少持有锁的时间
    - 减少请求锁的频率
    - 或者使用协调机制取代独占锁，允许更强并发性。
- 缩小锁的范围：快进快出。参阅 AttributeStore 和 BetterAttributeStore
- 减小锁的粒度：分拆锁（lock splitting）和分离锁（lock striping）。
  采用独立的锁，守护多个独立的state变量。
    - 分拆锁。参阅 ServerStatusBeforeSplit 和 ServerStatusAfterSplit
    - 分离锁。分拆锁被扩展，分成锁块的集合，归属独立。参阅 StripedMap。
        - JDK 7中ConcurrentHashMap 使用16个锁的array，每个锁保护Hash Bucket的1/16。Bucket N由第 (N mod 16) 锁守护。
          这样，ConcurrentHashMap最高支持16个并发的写操作。
        - 难点：当ConcurrentHashMap需要扩展，重排，放入更大的Bucket时，需要获取所有分离锁。
        - 难点：size如何计算？通常优化是插入和删除时更新一个独立的计数器。
          ConcurrentHashMap 通过枚举每个条目获得size，将这个值加入每一个条目，避免全局计数。~~条目~~？
- 独占锁的替代方案：在读多写少时，ReadWriteLock一般比独占锁，并发性更好。
- 现代处理的底层并发原语，CAS自旋。
- 同步的Map并发性差的原因：全局独占锁，一次只能一个线程访问Map。
- 可以使用非独占锁或者非阻塞锁来取代独占锁。

## 并发程序的测试
- 性能测试包括：吞吐量、响应性、可伸缩性。

## 显式锁
> 代码未读
- ReentrantLock实现了Lock接口，提供与synchronized相同的互斥和可见性保证。
    - 获得ReentrantLock的锁 = 进入 synchronized块
    - 释放ReentrantLock的锁 = 退出 synchronized块
- 锁必须在finally块释放
- 可定时和可轮询的锁，都由tryLock方法实现。
- ReentrantLock是高级工具：可定时、可轮询、可中断的锁获取，公平队列等。
- 互斥是保守的加锁策略，避免“写/写”，“写/读”的重叠，但是也滥杀了“读/读”这种无害的情况。
- 读锁和写锁的互动
    - 释放优先。写者释放锁，读者和写着都在队列中。应该选择哪一个？
    - 读者闯入。允许读者闯入到写者之前，可以提高并发性，但是却可能带来写者饥饿。
    - 重进入。读锁和写锁支持重入吗？
    - 降级。线程拥有写锁，它能够在不释放该锁的情况下获得读锁吗？
    - 升级。
- ReentrantReadWriteLock可以是公平或者非公平的。公平就是：先到先得。
- 采用读写锁封装普通的Map。参阅 ReadWriteMap

## 构建自定义的同步工具
- 状态依赖的可阻塞行为的结构
```
void blockingAction() throws InterruptedException{
    acquire lock on object state
    while(precondition does not hold){
        release lock
        wait until precondition might hold
        optionally fail if interrupted or timeout expires
        reacquire lock
    }
    perform action
    releaselock
}
```
- 如果状态依赖的操作在处理先验条件时失败，可以
    - 抛出异常，传给调用者
    - 或者返回错误状态，
    - 或者保持阻塞直到对象转入正确的状态。
- 将先验条件失败传给调用者。参阅 GrumpyBoundedBuffer
- 利用轮询和休眠实现拙劣的阻塞。参阅 SleepyBoundedBuffer
- 是否存在一种方式：保证某个条件为真时，线程可以及时地苏醒过来呢？条件队列的诞生。
- 条件队列的元素是等待相关条件的线程，不同于传统队列（元素是数据项）。
- 有限缓存使用条件队列。参阅 BoundedBuffer 和 ConditionBoundedBuffer
- 条件谓词。是先验条件的第一站，在一个操作和状态之间建立起依赖关系。
- 锁对象和条件队列对象，必须为同一个对象。
- 每条件队列-多条件谓词，非常常见，BoundedBuffer为“非满”和“非空”两个谓词使用了相同的条件队列。
- 状态依赖方法的规范模式
```java
void stateDependentMethod throws InterruptedExcetion{
     synchronized(lock){
          while(!conditionPredicate){
               lock.wait();
          }
     }
}
```
- 使用条件等待时
    - 永远设置一个条件谓词
    - 永远在wait前测试条件谓词，并且从wait中返回后再次测试。
    - 永远在循坏中调用wait
    - 检查条件谓词之后，开始执行被保护的逻辑之前，不要释放锁。
- 当你在等待一个条件，需要确保有人会在条件谓词变为true时通知你。
- 只有同时满足以下条件时，才能用单一的notify取代notifyAll。
    - 相同的等待者。只有一个条件谓词和条件队列相关。每个线程从wait返回后执行相同逻辑；并且
    - 一进一出。一个对条件变量的通知，至多只激活一个线程执行。
- 单一的通知和依据条件通知都是优化行为。
- 一个依赖于状态的class，要么完全将它的等待和通知协议告诉子class，要么完全阻止子class参与其中。
- 入口协议与出口协议
    - 入口协议：操作的条件谓词，例如queue未满。于是可以进行操作：put
    - 出口协议：涉及检查任何被操作改变的state变量，确认是否引起其他条件谓词的改变。如果是，请通知相关条件队列。
- AbstractQueueSynchronizer 采用出口协议。它没有让Synchronizer自己去执行通知，而是
  要求同步方法返回一个值，这个值说明：它的动作是否可能阻塞了一个或者多个等待线程。
- Condition接口提供了比内部条件队列更为丰富的特征集。每个锁可以有多个等待集。
- 注意：wait、notify和notifyAll在Condition中对应await、signal、signalAll
- 缓存为空，take阻塞，等待not-Empty；put向not-empty法信号，解除take引起的阻塞。
- AQS是构建锁和Synchronizer的基础框架。大量同步工具都使用了AQS。
- 基于AQS的Synchronizer进行的基本操作，是不同形式的获取和释放。
    - 管理一个关于状态信息的单一整数state。
    - ReentrantLock使用state来表示它的线程已经请求多少次锁
    - Semaphore 使用state表示剩余许可数
    - FutureTask使用state来表示任务的状态阶段，未开始、运行、完成、取消
    - Synchronizer可以有自己的状态变量，例如ReentrantLock保存锁的追踪信息，区分是重入锁还是竞争条件锁。
- AQS 锁的获取可能是独占的，例如ReentrantLock；也可以是非独占的，Semaphore和CountDownLatch。
- AQS 中国获取锁和释放锁的规范模式
```java
boolean acquire() throws InterruptedException{
    while(state does not permit acquire){
         if(blocking acquisition requested){
               enqueue current thread id not already queued
               block current thread
         }
         else
             return failure
    }
    possibly update synchronization state
    dequeue thread if it was queued
    return success
}

void release(){
    update synchronization state
    if (new state may permit a blocked thread to acquire)
        unblocke one or more queued threads
}
```
- 说明
    - 支持独占获取的 Synchronizer 应实现 tryAcquire、tryRelease和isHeldExclusively
    - 支持共享获取的 Synchronizer 应实现 tryAcquireShared 和 tryReleaseShared
    - AQS的acquire、acquireShared、release、releaseShared这些方法，会调用
      在 Synchronizer子类中这些版本的try-版本，以决定是否执行该操作。也就是说，将实现延迟到子类。
    - Synchronizer子类根据 acquire和release的语意，使用getState、setState以及
      compareAndSetState来检查并更新状态。通过返回值告知基类这次获取或者释放是否成功。
    - 举例：从tryAcquireShared返回一个负值，代表获取失败；0表示独占获取，正值表示非独占获取。
    - 举例：tryRelease和tryReleaseShared来说，如果可以解除一些阻塞线程的阻塞状态，就返回true
- 二元闭锁。参阅 OneShotLatch
- juc包下没有一个Synchronizer直接扩展AQS，而是委托给AQS私有内部类。
- juc包下的Synchronizer
    - ReentrantLock使用同步状态来持有锁获取的计数，还维护一个owner来持有当前拥有的线程的标记符。
    - 非公平的ReentrantLock中tryAcquire的实现
  > java.util.concurrent.locks.ReentrantLock.Sync.nonfairTryAcquire
  ```java
        @ReservedStackAccess
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
  ```
    - Semaphore使用AQS类型的同步状态来表示当前许可的数量。
  > java.util.concurrent.Semaphore.Sync.nonfairTryAcquireShared
  ```java
          final int nonfairTryAcquireShared(int acquires) {
              for (;;) {
                  int available = getState();
                  int remaining = available - acquires;
                  if (remaining < 0 ||
                      compareAndSetState(available, remaining))
                      return remaining;
              }
          }
  
          protected final boolean tryReleaseShared(int releases) {
              for (;;) {
                  int current = getState();
                  int next = current + releases;
                  if (next < current) // overflow
                      throw new Error("Maximum permit count exceeded");
                  if (compareAndSetState(current, next))
                      return true;
              }
          }
   ```
    - CountDownLatch和Semaphore类似。
  > java.util.concurrent.CountDownLatch.Sync.tryAcquireShared
  ```java
          protected int tryAcquireShared(int acquires) {
              return (getState() == 0) ? 1 : -1;
          }
  
          protected boolean tryReleaseShared(int releases) {
              // Decrement count; signal when transition to zero
              for (;;) {
                  int c = getState();
                  if (c == 0)
                      return false;
                  int nextc = c - 1;
                  if (compareAndSetState(c, nextc))
                      return nextc == 0;
              }
          }
  ```
    - FutureTask使用AQS的同步状态来表示任务的执行状态：运行、完成、取消
    - ReentrantReadWriteLock使用16位的状态为write lock计数，使用另一个16状态为read lock 计数。
- 总结：内部条件队列和内部锁，显式Condition和显式lock也是紧密联系。
- 显式Condition条件的高级之处：多等待集每锁、可中断或不可中断、公平或不公平、超时机制。

## 原子变量与非阻塞同步机制
- 独占锁是悲观锁。
- CAS：比较和交换。CAS有三个参数，分别是内存位置V，旧的期望值A，新值B。
    - 如果V的值为A，那么我就使用B赋值给V。
    - 否则，不修改，请返回V的当前值给我。
- 模拟CAS。参阅 SimulatedCAS
- CAS能够发现来自其他线程的干扰，不使用锁，也能原子化解决[读内存-修改-写内存]的问题。
  CAS提供原子性和可见性的保证。
- CAS 实现的非阻塞计数器。参阅 CasCounter
- 使用原子引用和CAS来构建并发Stack。参阅 ConcurrentStack
- ConcurrentLinkedQueue中的原子化更新器。
> java.util.concurrent.ConcurrentLinkedQueue.Node
```java
    static final class Node<E> {
        volatile E item;
        volatile Node<E> next;
```
- ABA问题。解决关键在于增加版本号。这样A->B->A，版本号是不一样。AtomicStampedReference和
  （以及同系列AtomicMarkableReference）提供了一对变量原子化的条件更新。

## Java内存模型
- CPU时钟频率渐入瓶颈，可以提升的只有硬件并发性。
- CPU会牺牲一致性，来换取性能的提升。一级缓存、二级缓存、三级缓存？
- JVM会在适当位置插入memory barriers，来解决JMM与底层平台存储模型之间的差异化。
- 冯诺依曼计算模型，是顺序化计算模型。参阅 wikipedia
- 指令重排序。
- JMM定义是通过action来描述的。变量读写、监视器加锁和释放锁、线程启动和join。
- JMM的偏序关系，成为happens-before。如果两个操作之间不存在偏序关系，那么JVM可以进行优化，即指令重排序。
- happens-before法则
    - ① 程序次序法则：线程中的每个动作A都happens-before于该线程中的每一个动作B，其中，在程序中，所有的动作B都能出现在A之后。
    - ② 监视器锁法则：对一个监视器锁的解锁 happens-before于每一个后续对同一监视器锁的加锁。
    - ③ volatile变量法则：对volatile域的写入操作happens-before于每一个后续对同一个域的读写操作。
    - ④ 线程启动法则：在一个线程里，对Thread.start的调用会happens-before于每个启动线程的动作。
    - ⑤ 线程终结法则：线程中的任何动作都happens-before于其他线程检测到这个线程已经终结、或者从Thread.join调用中成功返回，或Thread.isAlive返回false。
    - ⑥ 中断法则：一个线程调用另一个线程的interrupt happens-before于被中断的线程发现中断。
    - ⑦ 终结法则：一个对象的构造函数的结束happens-before于这个对象finalizer的开始。
    - ⑧ 传递性：如果A happens-before于B，且B happens-before于C，则A happens-before于C
  > 偏序集合中任意两个元素都可比时，该偏序集合满足全序关系。也就是都可以相互比较。
- 由类保证的happens-before法则。
    - 将一个元素放入一个线程安全的容器的操作 happens-before 另一个线程从该容器获得这个元素
    - 在CountDownLatch上的倒数操作 happens-before 线程从闭锁上的await方法返回
    - 释放semaphore许可的操作 happens-before 从该Semaphore上获得一个许可
    - Future表示的任务发生的操作 happens-before 从Future.get()中返回
    - 向Executor提交一个Runnable或Callable happens-before 任务开始执行
    - 一个线程到达CyclicBarrier或Exchanger的操作 happens-before 于相同关卡barriers或exchange点的其他线程被释放。
      如果CyclicBarrier使用一个关卡操作，到达关卡 happens-before 于关卡动作，关卡动作 happens-before 线程从关卡中释放。
- 除了不可变对象之外，使用被另一个线程初始化的对象，是不安全的。除非对象的发布 happens-before
  于对象的消费线程使用它。
- 初始化安全性保证：通过final域设置值，在构造函数完成后才发布可见。