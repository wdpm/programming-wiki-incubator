package io.github.wdpm.concurrency.custom;

import io.github.wdpm.concurrency.annotations.GuardedBy;
import io.github.wdpm.concurrency.annotations.ThreadSafe;

/**
 * ThreadGate
 * <p/>
 * Recloseable gate using wait and notifyAll 可重关闭的阀门
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class ThreadGate {
    // CONDITION-PREDICATE: opened-since(n) (isOpen || generation>n)
    @GuardedBy("this")
    private boolean isOpen;
    @GuardedBy("this")
    private int     generation;

    public synchronized void close() {
        isOpen = false;
    }

    /**
     * 阀门，打开时，代表可用状态
     * <p></p>
     * 只支持等待阀门的打开，只在open中notifyAll
     */
    public synchronized void open() {
        ++generation;//每次open都会递增计数器
        isOpen = true;
        notifyAll();
    }

    // 没有理解
    // BLOCKS-UNTIL: opened-since(generation on entry)
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation) wait();
    }
}
