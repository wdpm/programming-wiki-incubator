# AtomicLong

## AtomicLong

作用是原子计数器。

> java.util.concurrent.atomic.AtomicLong

```java
public class AtomicLong extends Number implements java.io.Serializable
```

### 成员变量

```java
    private static final long serialVersionUID = 1927816293512124184L;

    // setup to use Unsafe.compareAndSwapLong for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();//1
    private static final long valueOffset;//2

    /**
     * Records whether the underlying JVM supports lockless
     * compareAndSwap for longs. While the Unsafe.compareAndSwapLong
     * method works in either case, some constructions should be
     * handled at Java level to avoid locking user-visible locks.
     */
    static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();//3

    /**
     * Returns whether underlying JVM supports lockless CompareAndSet
     * for longs. Called only once and cached in VM_SUPPORTS_LONG_CAS.
     */
    private static native boolean VMSupportsCS8();

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicLong.class.getDeclaredField("value"));//4
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile long value;//5
```

//1: 获取unsafe实例

//2: 定义偏移量valueOffset

//3: 判断VM是否支持CS8(无锁CAS)

//4: 获取value字段的偏移量，赋值给valueOffset

//5: 定义value

### 自增自减

```java
//自增，返回旧的值
public final long getAndIncrement() {
    return unsafe.getAndAddLong(this, valueOffset, 1L);
}

//自减，返回旧的值
public final long getAndDecrement() {
    return unsafe.getAndAddLong(this, valueOffset, -1L);
}

//自增，返回新的值
public final long incrementAndGet() {
    return unsafe.getAndAddLong(this, valueOffset, 1L) + 1L;
}

//自减，返回新的值
public final long decrementAndGet() {
    return unsafe.getAndAddLong(this, valueOffset, -1L) - 1L;
}
```

JDK 8，内部全部使用unsafe的方法。

### compareAndSet

```java
public final boolean compareAndSet(long expect, long update) {
    return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
}
```

如果value值等于expect，那么就使用update值更新它。

### 使用例子

```java
private static int[] ints1 = new int[]{0, 565, -132, 1313, -310, 0, -1, 4, 12};
private static int[] ints2 = new int[]{0, -565, -132, 1313, -310, 0, -1, -4, 12};
private static AtomicLong atomicLong = new AtomicLong();

public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
        for (int value : ints1) {
            if (value > 0) {
                atomicLong.incrementAndGet();
            }
        }
    });

    Thread t2 = new Thread(() -> {
        for (int value : ints2) {
            if (value > 0) {
                atomicLong.incrementAndGet();
            }
        }
    });

    t1.start();
    t2.start();

    t1.join();
    t2.join();

    System.out.println(atomicLong.get());
}
```

```
6
```

### AtomicLong的局限

高并发情况下，**多个线程竞争同一个原子变量value，但只有一个线程的CAS操作会成功**，失败的线程会不断自旋重试。



