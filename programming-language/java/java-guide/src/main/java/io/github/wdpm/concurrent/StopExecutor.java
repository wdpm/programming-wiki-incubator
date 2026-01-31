package io.github.wdpm.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优雅地停止线程池
 *
 * @author evan
 * @date 2020/5/8
 */
public class StopExecutor {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                                                                 5,
                                                                 1,
                                                                 TimeUnit.MILLISECONDS,
                                                                 queue);
        poolExecutor.execute(() -> {
            System.out.println("running 1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        poolExecutor.execute(() -> {
            System.out.println("running 2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(1,
                                              TimeUnit.SECONDS)) {
            System.out.println("There are still some threads executing");
        }

        System.out.println("main thread over");
    }
}
