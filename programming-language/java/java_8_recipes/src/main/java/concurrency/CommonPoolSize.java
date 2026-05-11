package concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class CommonPoolSize {
    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "16");
        long total = LongStream.rangeClosed(1, 3_000_000)
                .parallel()
                .sum();
        System.out.println("total = " + total);

        int poolSize = ForkJoinPool.commonPool().getPoolSize();
        System.out.println("Pool size: " + poolSize);
        System.out.println("Processors: " + Runtime.getRuntime().availableProcessors());

        ForkJoinPool pool = new ForkJoinPool(15);
        ForkJoinTask<Long> task = pool.submit(() -> LongStream.rangeClosed(1, 3_000_000)
                .parallel()
                .sum()
        );
        try {
            total = task.get();
            System.out.println("total = " + total);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
        poolSize = pool.getPoolSize();
        System.out.println("Pool size: " + poolSize);
    }
}
