# LockSupport

JDK的工具类，作用是挂起和唤醒线程。

## 使用
```java
public class LockSupprtTest {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("t1 begin to park");

            LockSupport.park();//t1挂起自身

            System.out.println("t1 unpark");
        });

        t1.start();

        Thread.sleep(1000);

        System.out.println("main thread begin unpark t1");

        LockSupport.unpark(t1);//main线程调用unpark唤醒挂起的t1线程
    }
}
```
```
t1 begin to park
main thread begin unpark t1
t1 unpark
```

## API

### park()
除非有许可，否则禁用当前线程的线程调度。如果许可证可用，则将其消耗掉，并立即返回；否则，当前线程将被禁用，并在以下三种情况之一之前处于休眠：
- 其他线程unpark当前线程
- 其他线程interrupt当前线程
- 虚假地调用返回。
此方法不报告其中哪一个导致方法返回。调用者应重新检查导致线程首先停滞的条件。调用者还可以确定例如返回时线程的中断状态。

### unpark(Thread thread)
唤醒某个线程。

### parkNanos(long nanos)
nanos参数为超时时间

### park(Object blocker)
```java
public static void park(Object blocker) {
    Thread t = Thread.currentThread();
    setBlocker(t, blocker);
    UNSAFE.park(false, 0L);
    setBlocker(t, null);
}
    
private static void setBlocker(Thread t, Object arg) {
    // Even though volatile, hotspot doesn't need a write barrier here.
    UNSAFE.putObject(t, parkBlockerOffset, arg);
}
```
blocker参数的值实际上赋值给了parkBlockerOffset变量。