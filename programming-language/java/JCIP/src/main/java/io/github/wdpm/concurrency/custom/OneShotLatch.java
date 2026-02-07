package io.github.wdpm.concurrency.custom;

import io.github.wdpm.concurrency.annotations.ThreadSafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * OneShotLatch
 * <p/>
 * Binary latch using AbstractQueuedSynchronizer
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);// delegate to tryReleaseShared
    }

    public void await() throws InterruptedException {
        // 一开始，闭锁是关闭的
        // 任何调用await的线程都会阻塞，直到打开闭锁
        sync.acquireSharedInterruptibly(0);// delegate to tryAcquireShared
    }

    /**
     * 继承AQS，重写XXShared方法，表示这是一个共享同步器
     */
    private class Sync extends AbstractQueuedSynchronizer {
        protected int tryAcquireShared(int ignored) {
            // Succeed if latch is open (state == 1), else fail
            return (getState() == 1) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignored) {
            setState(1); // Latch is now open
            return true; // Other threads may now be able to acquire
        }
    }

    public static void main(String[] args) throws InterruptedException {
        OneShotLatch oneShotLatch = new OneShotLatch();
        new Thread(() -> {
            try {
                oneShotLatch.await();
                System.out.println("do something");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(3000);
        oneShotLatch.signal();
    }
}
