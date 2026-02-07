package io.github.wdpm.concurrency.shutdown;

import java.util.concurrent.*;

import static io.github.wdpm.concurrency.buildingblocks.LaunderThrowable.launderThrowable;

/**
 * TimedRun
 * <p/>
 * Cancelling a task using Future
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun {
    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // task will be cancelled below
        } catch (ExecutionException e) {
            // exception thrown in task; rethrow
            throw launderThrowable(e.getCause());
        } finally {
            // Harmless if task already completed
            boolean res = task.cancel(true); // interrupt if running
            System.out.println("Future cancel result :" + res);
        }
    }

    public static void main(String[] args) {
        try {
            timedRun(new PrintTask(), 1L, TimeUnit.SECONDS);

            /**
             * 任务运行完毕，主线程主动关闭线程池。
             */
            taskExec.shutdown();
        } catch (InterruptedException e) {
            System.out.println("任务被取消!");
        }
    }
}
