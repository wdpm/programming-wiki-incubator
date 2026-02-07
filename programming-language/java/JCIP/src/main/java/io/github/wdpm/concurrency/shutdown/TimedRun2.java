package io.github.wdpm.concurrency.shutdown;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.github.wdpm.concurrency.buildingblocks.LaunderThrowable.launderThrowable;
import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * TimedRun2
 * <p/>
 * Interrupting a task in a dedicated thread 在专门线程上中断任务
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun2 {
    private static final ScheduledExecutorService cancelExec = newScheduledThreadPool(1);

    public static void timedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        //这个类保存了Throwable 异常，提供继续抛出异常的方法
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null) throw launderThrowable(t);
            }
        }

        RethrowableTask task = new RethrowableTask();

        // new Thread() 表示专门的线程
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);

        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }
}
