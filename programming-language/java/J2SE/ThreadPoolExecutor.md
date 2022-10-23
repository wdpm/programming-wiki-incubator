# ThreadPoolExecutor

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler)
```
Creates a new ThreadPoolExecutor with the given initial parameters.
Parameters:
- corePoolSize - the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set
- maximumPoolSize - the maximum number of threads to allow in the pool
- keepAliveTime - when the number of threads is greater than the core, this is the maximum time that excess idle threads
 will wait for new tasks before terminating.
- unit - the time unit for the keepAliveTime argument
- workQueue - the queue to use for holding tasks before they are executed. This queue will hold only the Runnable tasks
 submitted by the execute method.
- threadFactory - the factory to use when the executor creates a new thread
- handler - the handler to use when execution is blocked because the thread bounds and queue capacities are reached.