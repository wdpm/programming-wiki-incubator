package io.github.wdpm.concurrency.introduction;


import io.github.wdpm.concurrency.annotations.NotThreadSafe;

/**
 * UnsafeSequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        // value++ 分三步：读值，+1，写值
        return value++;
    }
}
