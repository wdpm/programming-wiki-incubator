# Executors

**newFixedThreadPool(int)**

固定大小的线程池，线程keepAliveTime为0。缓冲任务队列为LinkedBlockingQueue。

**newSingleThreadExecutor()**

大小为1的固定线程池，限制同时执行的task只有1个，缓冲任务队列为LinkedBlockingQueue。

**newCachedThreadPool()**

创建corePoolSize为0，最大线程数为整型的最大数，线程keepAliveTime为1分钟，缓存任务队列为SynchronousQueue的线程池。

**newScheduledThreadPool(int)**

创建corePoolSize为传入参数，最大线程数为整型的最大数，线程keepAliveTime为0，缓存任务队列为DelayedWorkQueue的线程池。

当要执行的Runnable或Callable的task加入时，ScheduledThreadPoolExecutor会将其放入内部的DelayedWorkQueue中，DelayedWorkQueue基于DelayQueue来实现；当有新的task加入时，DelayQueue会将其加入内部的数组对象中，并进行排序。

