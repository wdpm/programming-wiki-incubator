package io.github.wdpm.jdk19;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadExample {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(1000);
                    return i;
                });
            });
        } // executor.close() 会被自动调用

        // 提交了 10 万个虚拟线程，每个线程休眠 1 秒钟，耗时:8769ms
        // 可见虚拟线程极大增加程序的IO吞吐量，尽管这里有7.7s左右的线程开销。
        System.out.println("耗时:" + (System.currentTimeMillis() - start)+"ms");
    }
}


