# Thread

## Thread常用方法

- join() 

等待线程结束

- void interrupt() 

中断线程。如果当前线程正在运行，则只是设置中断标记，不会中断；如果当前线程处于非运行状态，则会抛出InterruptedException异常。

- boolean isInterrupted() 

获取线程中断状态。

```java
public boolean isInterrupted() {
    return isInterrupted(false);
}
```

- boolean interrupted() 

获取线程中断状态。如果当前已被中断，清除中断标记。

```java
public static boolean interrupted() {
    return currentThread().isInterrupted(true);
}
```

其中isInterrupted含参数的方法定义如下

```java
/**
* Tests if some Thread has been interrupted.  The interrupted state
* is reset or not based on the value of ClearInterrupted that is
* passed.
*/
private native boolean isInterrupted(boolean ClearInterrupted);
```

## 线程上下文

线程上下文切换的时机：当前线程CPU时间片使用完毕，或当前线程被其他线程中断。

### 死锁

死锁的四个条件

- **互斥**。即线程持有的资源是排他的，不是共享的。
- **请求并持有**。一个线程持有资源A，但是请求资源B，但是资源B被其他线程占用，因此这个线程阻塞，但是它也不释放资源A。
- **不可剥夺**。一个线程获取到的资源不会被别的线程强行抢走。
- **环路等待**。必然存在一个环形的资源等待链。R1->R2->...->Rn->R1。

死锁代码：

```java
public class DeadLock {
    private static Object resourceA=new Object();
    private static Object resourceB=new Object();
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + "get resourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "wait to get resourceB");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + "get resourceB");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread() + "get resourceB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "wait to get resourceA");
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread() + "get resourceA");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
```

```bash
Thread[Thread-0,5,main]get resourceA
Thread[Thread-1,5,main]get resourceB
Thread[Thread-0,5,main]wait to get resourceB
Thread[Thread-1,5,main]wait to get resourceA
```

线程1持有resourceA，并请求resourceB；线程2持有resourceB，并请求resourceA。造成了死锁。

避免死锁：将线程2获取资源的顺序改为和线程1获取资源的顺序保持一致。

```java
Thread thread2 = new Thread(() -> {
    synchronized (resourceA) {
        System.out.println(Thread.currentThread() + "get resourceA");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + "wait to get resourceB");
        synchronized (resourceB) {
            System.out.println(Thread.currentThread() + "get resourceB");
        }
    }
});
```

```bash
Thread[Thread-0,5,main]wait to get resourceB
Thread[Thread-0,5,main]get resourceB
Thread[Thread-1,5,main]get resourceA
Thread[Thread-1,5,main]wait to get resourceB
Thread[Thread-1,5,main]get resourceB
```

由于资源的有序分配，当线程1和线程2同时执行到`synchronized (resourceA)`时，只会有一个线程拿到资源A，假设线程1拿到，那么线程2会因为抢不到资源A而被阻塞。

于是，线程1能够顺利继续获取资源B，使用完之后，释放资源A和资源B。线程2接着重复线程1的操作。

## 守护线程与用户线程

线程可以使用setDaemon(true)方法设置为守护进程。

子线程的生命周期不受父线程影响。

当最后一个非守护线程结束时，JVM会正常退出。只要有一个用户线程没结束，JVM不会退出。

总结：**非守护线程不会影响JVM的退出逻辑**。