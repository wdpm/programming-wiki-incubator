package io.github.wdpm.concurrency.buildingblocks;

import java.util.concurrent.CountDownLatch;

/**
 * TestHarness
 * <p/>
 * Using CountDownLatch for starting and stopping threads in timing tests
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate   = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        // 5个线程准备就绪
        long start = System.nanoTime();
        // 打开开始阀门，线程开始赛跑运行，每个线程结束后都会将endGate减1
        startGate.countDown();
        // 等待endGate变为0
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        try {
            long useTime = new TestHarness().timeTasks(5, new SimpleTask());
            System.out.println("use time: " + useTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class SimpleTask implements Runnable {

        public void run() {
            System.out.println("task  running[" + Thread.currentThread() + "]");
        }
    }
}
