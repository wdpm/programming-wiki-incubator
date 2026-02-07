package io.github.wdpm.concurrency.threadsafe;

import io.github.wdpm.concurrency.annotations.NotThreadSafe;

/**
 * LazyInitRace
 * <p>
 * Race condition in lazy initialization 惰性初始化中的竞态条件
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null)
            instance = new ExpensiveObject();
        return instance;
    }
}

class ExpensiveObject { }

