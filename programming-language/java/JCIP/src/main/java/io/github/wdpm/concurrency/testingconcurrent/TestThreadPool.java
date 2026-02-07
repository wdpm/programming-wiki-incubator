package io.github.wdpm.concurrency.testingconcurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * TestingThreadFactory
 * <p/>
 * Testing thread pool expansion
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TestThreadPool {

    private final TestingThreadFactory threadFactory = new TestingThreadFactory();

    @Test
    public void testPoolExpansion() throws InterruptedException {
        int             MAX_SIZE = 10;
        ExecutorService exec     = Executors.newFixedThreadPool(MAX_SIZE, threadFactory);

        // 10* 10 =100 tasks
        for (int i = 0; i < 10 * MAX_SIZE; i++)
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        // 给一点时间来给threadPool反应，例如增加线程。虽然做法有点诡异
        for (int i = 0; i < 20 && threadFactory.numCreated.get() < MAX_SIZE; i++)
            Thread.sleep(100);

        // 验证是否真的是固定大小的线程池
        System.out.println(threadFactory.numCreated.get());
        assertEquals(threadFactory.numCreated.get(), MAX_SIZE);
        exec.shutdownNow();
    }
}

class TestingThreadFactory implements ThreadFactory {
    /**
     * 线程工厂的全局统计信息
     */
    public final  AtomicInteger numCreated = new AtomicInteger();
    private final ThreadFactory factory    = Executors.defaultThreadFactory();

    public Thread newThread(Runnable r) {
        numCreated.incrementAndGet();
        return factory.newThread(r);
    }
}
