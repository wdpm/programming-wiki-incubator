package io.github.wdpm.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author evan
 * @date 2020/4/14
 */
public class SingleThreadExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Thread name is: " + threadName);
        });
        // Thread name is: pool-1-thread-1
    }
}
