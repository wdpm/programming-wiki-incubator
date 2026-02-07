package io.github.wdpm.concurrency.buildingblocks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Memoizer2
 * <p/>
 * Replacing HashMap with ConcurrentHashMap
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            // 缺陷：可能存在两个线程A，B同时调用compute方法，造成重复计算。这是一个漏洞
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
