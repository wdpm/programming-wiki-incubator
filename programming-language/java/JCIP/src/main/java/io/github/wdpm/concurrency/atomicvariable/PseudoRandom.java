package io.github.wdpm.concurrency.atomicvariable;

public class PseudoRandom {
    private ThreadLocal<Integer> lastSeed = new ThreadLocal<Integer>();

    public int calculateNext(int seed) {
        lastSeed.set(seed);
        return lastSeed.get();
    }
}
