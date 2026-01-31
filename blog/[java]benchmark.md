# 基准测试
## 计时工具类
```java
public class Timer {
    private long start = System.nanoTime();

    public long duration() {
        return NANOSECONDS.toMillis(System.nanoTime() - start);
    }

    public static long duration(Runnable test) {
        Timer timer = new Timer();
        test.run();
        return timer.duration();
    }
}
```
看上去，这个计时器非常完美，好像没什么问题。然而，事实却不是这样。很多因素都可能影响计时的结果。

### 依赖同一个资源
如果操作依赖于同一资源（下面的la），那么并行版本运行的速度会骤降，因为不同的进程会竞争相同的那个资源。

```java
public class BadMicroBenchmark {
    private static final int SIZE = 250_000_000;

    public static void main(String[] args) {
        try { // For machines with insufficient memory
            long[] la = new long[SIZE];
            System.out.println("setAll: " + Timer.duration(() -> Arrays.setAll(la, n -> n)));
            System.out.println("parallelSetAll: " + Timer.duration(() -> Arrays.parallelSetAll(la, n -> n)));
        } catch (OutOfMemoryError e) {
            System.out.println("Insufficient memory");
            System.exit(0);
        }
    }

    /*
     setAll: 213
     parallelSetAll: 215
    */

}
```
### JVM忘记预热
Java 虚拟机 Hotspot 也非常影响性能。如果没有预热，那么很可能得出错误的测试结果。

SplittableRandom 为并行算法设计，理论上会比Random 在 parallelSetAll() 中运行得更快。然而却不是这样。

```java
public class BadMicroBenchmark2 {
    static final int SIZE = 5_000_000;

    public static void main(String[] args) {
        long[] la = new long[SIZE];

        Random r = new Random();
        System.out.println("parallelSetAll: " + Timer.duration(() -> Arrays.parallelSetAll(la, n -> r.nextLong())));
        System.out.println("setAll: " + Timer.duration(() -> Arrays.setAll(la, n -> r.nextLong())));

        SplittableRandom sr = new SplittableRandom();
        System.out.println("parallelSetAll: " + Timer.duration(() -> Arrays.parallelSetAll(la, n -> sr.nextLong())));
        System.out.println("setAll: " + Timer.duration(() -> Arrays.setAll(la, n -> sr.nextLong())));
    }

    /*
     parallelSetAll: 963
     setAll: 100
     parallelSetAll: 69
     setAll: 14
    */
}
```

## JMH的诞生
Java 微基准测试系统是 Java Microbenchmarking Harness。

## 性能优化原则
- 一般情况下，不能因为性能抛弃代码可读性。除非是实现底层框架，性能要求很高，这时请加上必要的注释。
- 性能和代码工作量之间需要平衡考虑。
- 程序先运行起来，再考虑性能问题。
- 不要猜测性能瓶颈发生在哪。请使用剖析器。
- 显式设置实例为 null 表明触发GC，帮助垃圾收集器回收。
- static final 修饰的变量会被 JVM 优化从而提高程序的运行速度。程序常量应该声明 static final。

