package io.github.wdpm.concurrency.custom;

import io.github.wdpm.concurrency.annotations.ThreadSafe;

/**
 * BoundedBuffer
 * <p/>
 * Bounded buffer using condition queues
 * <p>
 * 生产环境中，应该提供put、take的超时版本，使用Object.wait()的超时版本可以实现。
 * 这里使用了notifyAll()来通知，其实可以继续优化。参阅 ConditionBoundedBuffer
 * </p>
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    // CONDITION PREDICATE: not-full (!isFull())
    // CONDITION PREDICATE: not-empty (!isEmpty())
    public BoundedBuffer() {
        this(100);
    }

    public BoundedBuffer(int size) {
        super(size);
    }

    // BLOCKS-UNTIL: not-full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) wait();
        doPut(v);
        notifyAll();
    }

    // BLOCKS-UNTIL: not-full. timeout version
    public synchronized void put(V v, long timeoutMillis) throws InterruptedException {
        while (isFull()) wait(timeoutMillis);
        doPut(v);
        notifyAll();
    }

    // BLOCKS-UNTIL: not-empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) wait();
        V v = doTake();
        notifyAll();
        return v;
    }

    // BLOCKS-UNTIL: not-empty.timeout version
    public synchronized V take(long timeoutMillis) throws InterruptedException {
        while (isEmpty()) wait(timeoutMillis);
        V v = doTake();
        notifyAll();
        return v;
    }

    // BLOCKS-UNTIL: not-full
    // Alternate form of put() using conditional notification
    public synchronized void alternatePut(V v) throws InterruptedException {
        while (isFull()) wait();
        // 代码执行到这里，代表不为full，但是不知道是否为空。
        // 判断是否为空
        boolean wasEmpty = isEmpty();
        doPut(v);
        /**
         * 空 -> 非空
         * 条件通知，而不是每次put都通知。仅当上面判断为空是true时
         */
        if (wasEmpty) notifyAll();
    }
}
