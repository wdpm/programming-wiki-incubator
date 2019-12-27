# ThreadLocalRandom

ThreadLocalRandom 来自 JDK 7，是一个随机数生成器。

## Random类的局限

```java
Random random = new Random();
for (int i = 0; i < 10; i++) {
	System.out.println(random.nextInt(5));//[0,5)
}
```

查看`java.util.Random#nextInt(int)`源码

```java
    public int nextInt(int bound) {
        if (bound <= 0)
            throw new IllegalArgumentException(BadBound);
            
        //根据老种子计算新种子
        int r = next(31);
        
        // 计算新种子计算随机数
        return r;
    }
```

`java.util.Random#next`

```java
    protected int next(int bits) {
        long oldseed, nextseed;
        AtomicLong seed = this.seed;
        do {
            //获取旧种子
            oldseed = seed.get();
            //根据旧种子生成新种子
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (!seed.compareAndSet(oldseed, nextseed));//CAS自旋，某时刻只有一个线程成功
        return (int)(nextseed >>> (48 - bits));
    }
```

所以，传统Random类的实现会产生多线程下的自旋重试，降低并发性能。

## ThreadLocalRandom的诞生

使用示例

```java
ThreadLocalRandom random = ThreadLocalRandom.current();
for (int i = 0; i < 10; i++) {
	System.out.println(random.nextInt(5));
}
```

Random的局限在于：多个线程竞争同一个原子性变量（种子）。

ThreadLocalRandom：每个线程都维护一个自身的本地种子，避免竞争。

## ThreadLocalRandom源码解读

```java
public class ThreadLocalRandom extends Random
```

ThreadLocalRandom继承自Random类，并重写部分nextInt()方法。

ThreadLocalRandom的种子放于具体的调用线程Thread的ThreadLocalRandomSeed变量中。这点跟ThreadLocal的原理很类似。

当调用ThreadLocalRandom.current()时，初始化调用线程的ThreadLocalRandomSeed变量。

> java.lang.Thread#threadLocalRandomSeed

```java
/** The current seed for a ThreadLocalRandom */
@sun.misc.Contended("tlr")
long threadLocalRandomSeed;
```

threadLocalRandomSeed只是一个普通的long变量，因为已经是线程级别，不需要考虑原子化。

---

> java.util.concurrent.ThreadLocalRandom#seeder

```java
/**
* The next seed for default constructors.
*/
private static final AtomicLong seeder = new AtomicLong(initialSeed());
```

> java.util.concurrent.ThreadLocalRandom#probeGenerator

```java
/** Generates per-thread initialization/probe field */
private static final AtomicInteger probeGenerator = new AtomicInteger();
```

seeder和probeGenerator都是原子性变量，表示种子和探针生成器。

---

> java.util.concurrent.ThreadLocalRandom#instance

```java
/** The common ThreadLocalRandom */
static final ThreadLocalRandom instance = new ThreadLocalRandom();
```

instance是ThreadLocalRandom一个静态实例，内部实现只调用了一些它的方法。

---

### Unsafe

> java.util.concurrent.ThreadLocalRandom#UNSAFE

```java
    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long SEED;
    private static final long PROBE;
    private static final long SECONDARY;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> tk = Thread.class;
            SEED = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomSeed"));
            PROBE = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomProbe"));
            SECONDARY = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomSecondarySeed"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
```

static代码块的逻辑是：利用反射机制获取Thread类中某些变量threadLocalRandomXXX的偏移量。

### ThreadLocalRandom current()

```java
    public static ThreadLocalRandom current() {
        if (UNSAFE.getInt(Thread.currentThread(), PROBE) == 0)//1
            localInit();//2
        return instance;//3
    }
```

//1: 判断当前线程中PROBE的值是否为0

//2: 如果PROBE的值为0，初始化当前线程的种子变量

//3: 如果PROBE的值不为0，直接返回instance实例（ThreadLocalRandom一个静态实例）。

> java.util.concurrent.ThreadLocalRandom#localInit

```java
    static final void localInit() {
        int p = probeGenerator.addAndGet(PROBE_INCREMENT);
        int probe = (p == 0) ? 1 : p; // skip 0
        long seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
        Thread t = Thread.currentThread();
        UNSAFE.putLong(t, SEED, seed);
        UNSAFE.putInt(t, PROBE, probe);
    }
```

根据probe计算seed，然后使用UNSAFE.put方法设置到当前线程。

---

> java.util.concurrent.ThreadLocalRandom#nextInt(int)

```java
    public int nextInt(int bound) {
        if (bound <= 0)
            throw new IllegalArgumentException(BadBound);
        int r = mix32(nextSeed());
        int m = bound - 1;
        if ((bound & m) == 0) // power of two
            r &= m;
        else { // reject over-represented candidates
            for (int u = r >>> 1;
                 u + m - (r = u % bound) < 0;
                 u = mix32(nextSeed()) >>> 1)
                ;
        }
        return r;
    }
```

这个方法和Random中的实现很类似。

---

> java.util.concurrent.ThreadLocalRandom#nextSeed

```java
   private static final long GAMMA = 0x9e3779b97f4a7c15L; 

   final long nextSeed() {
        Thread t; long r; // read and update per-thread seed
        UNSAFE.putLong(t = Thread.currentThread(), SEED,
                       r = UNSAFE.getLong(t, SEED) + GAMMA);
        return r;
    }
```

` r = UNSAFE.getLong(t, SEED) + GAMMA`获取旧种子，加上GAMMA作为新种子。

