package io.github.wdpm.benchmark;

import java.util.concurrent.TimeUnit;

/**
 * 计时器
 *
 * @author evan
 * @date 2020/5/1
 */
public class Timer {
    private final long start = System.nanoTime();

    public long duration() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
    }

    public static long duration(Runnable test) {
        Timer timer = new Timer();
        test.run();
        return timer.duration();
    }
}