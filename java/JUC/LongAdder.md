# LongAdder

## LongAdder

认清AtomicLong的短板原因：竞争同一个原子变量value。

改进思路：

1. 内部创建一个数组cells，维护多个cell变量。相当于增加竞争资源，减缓竞争激烈的程度。
2. 一个线程竞争某个cell变量失败后，可以尝试竞争其他cell变量。相当于增加成功率。
3. 返回值的处理=所有cell变量的value值+base值。

本质上属于**空间换取时间，因为增加了cells数组**。

```java
public class LongAdder extends Striped64 implements Serializable
```

### cell的构造函数

> java.util.concurrent.atomic.Striped64.Cell

```java
    @sun.misc.Contended static final class Cell {
        volatile long value;
        Cell(long x) { value = x; }
        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> ak = Cell.class;
                valueOffset = UNSAFE.objectFieldOffset
                    (ak.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }
```

value使用volatile保证内存可见性。

cas函数通过CAS操作保证被分配cell元素value值得原子性。

### sum()

> java.util.concurrent.atomic.LongAdder#sum

```java
    public long sum() {
        Cell[] as = cells; Cell a;
        long sum = base;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
            }
        }
        return sum;
    }
```

取cells数组的每一个元素值累加，加上base，就是最终sum的值。

这里并没有对cells加锁，所以不是原子性操作。

### reset()

> java.util.concurrent.atomic.LongAdder#reset

```java
    public void reset() {
        Cell[] as = cells; Cell a;
        base = 0L;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    a.value = 0L;
            }
        }
    }
```

base设置为0，cells每一个元素如果不为null，设置value为0。

### sumThenReset()

```java
    public long sumThenReset() {
        Cell[] as = cells; Cell a;
        long sum = base;
        base = 0L;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null) {
                    sum += a.value;
                    a.value = 0L;
                }
            }
        }
        return sum;
    }
```

等效于seset。同样，不保证多线程的下结果的正确性。

### add()

这个方法理解难度较高！

> java.util.concurrent.atomic.LongAdder#add

```java
    public void add(long x) {
        Cell[] as; long b, v; int m; Cell a;
        if ((as = cells) != null || !casBase(b = base, b + x)) {  //1
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||    //2
                (a = as[getProbe() & m]) == null ||        //3
                !(uncontended = a.cas(v = a.value, v + x)))  //4
                longAccumulate(x, null, uncontended);//5
        }
    }
```

//1: 如果cells不为空，或cas操作失败，则进入if语句块

//2: as为null，或as长度为0

//3: 计算当前线程应该访问cells数组的哪一个元素，判断为null

//4: 使用cas操作更新被分配的元素的值，返回结果为false的话就是更新失败

//5: 计算真正的累加值

### longAccumulate()

> java.util.concurrent.atomic.Striped64#longAccumulate

```java
    final void longAccumulate(long x, LongBinaryOperator fn,
                              boolean wasUncontended) {
        int h;
        if ((h = getProbe()) == 0) {
            ThreadLocalRandom.current(); // force initialization
            h = getProbe();
            wasUncontended = true;
        }
        boolean collide = false;                // True if last slot nonempty
        for (;;) {
            Cell[] as; Cell a; int n; long v;
            if ((as = cells) != null && (n = as.length) > 0) {
                ...
                else if (cellsBusy == 0 && casCellsBusy()) {
                    try {
                        if (cells == as) {      // Expand table unless stale
                            Cell[] rs = new Cell[n << 1];// (3)
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];
                            cells = rs;
                        }
                    } finally {
                        cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                h = advanceProbe(h);//(1)
            }
            //(2)
            else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
                boolean init = false;
                try {                           // Initialize table
                    if (cells == as) {
                        Cell[] rs = new Cell[2];
                        rs[h & 1] = new Cell(x);
                        cells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;
                }
                if (init)
                    break;
            }
            else if (casBase(v = base, ((fn == null) ? v + x :
                                        fn.applyAsLong(v, x))))
                break;                          // Fall back on using base
        }
    }
```

//(1)：重新计算probe的值，减少下次碰撞冲突的机会。

//(2):  数组初始化。一开始长度为2。

//(3)：数组cells扩容为2倍，并复制旧的值到新数组。

### 使用例子

```java
        LongAdder counter = new LongAdder();

        int count1 = ThreadLocalRandom.current().nextInt(10);
        int count2 = ThreadLocalRandom.current().nextInt(30);
        System.out.println(count1);
        System.out.println(count2);
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < count1; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < count2; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter.sum());
```

```
1
18
19
```