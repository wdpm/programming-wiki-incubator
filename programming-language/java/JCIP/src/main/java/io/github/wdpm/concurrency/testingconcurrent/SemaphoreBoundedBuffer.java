package io.github.wdpm.concurrency.testingconcurrent;

import io.github.wdpm.concurrency.annotations.GuardedBy;
import io.github.wdpm.concurrency.annotations.ThreadSafe;

import java.util.concurrent.Semaphore;

/**
 * BoundedBuffer
 * <p/>
 * Bounded buffer using \Semaphore
 *
 * <li>不变约束：两个信号量的总和，一定等于缓存长度</li>
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SemaphoreBoundedBuffer<E> {
    // 表示已有项的数量
    private final Semaphore availableItems;
    // 表示剩余空间
    private final Semaphore availableSpaces;
    @GuardedBy("this")
    private final E[]       items;
    @GuardedBy("this")
    private       int       putPosition = 0, takePosition = 0;

    public SemaphoreBoundedBuffer(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();//剩余空间-1
        doInsert(x);
        availableItems.release();//已有项 +1
    }

    public E take() throws InterruptedException {
        availableItems.acquire();//已有项-1
        E item = doExtract();
        availableSpaces.release();//剩余空间+1
        return item;
    }

    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : i;
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }
}
