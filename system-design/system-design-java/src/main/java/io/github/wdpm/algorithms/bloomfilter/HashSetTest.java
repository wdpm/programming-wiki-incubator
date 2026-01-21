package io.github.wdpm.algorithms.bloomfilter;

import java.util.HashSet;

/**
 * HashSet测试1000W数据中查找一个数。
 *
 * <p>
 * VM options:
 * </p>
 *
 * <pre>
 * -Xms64m -Xmx64m -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError
 * </pre>
 *
 * @author evan
 * @date 2020/5/8
 */
public class HashSetTest {
    public static void main(String[] args) {
        long             start = System.currentTimeMillis();
        final int        COUNT = 10000000;
        HashSet<Integer> set   = new HashSet<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            set.add(i);
        }
        assert set.contains(5678);
        long end = System.currentTimeMillis();
        System.out.println("Time cost(ms): " + (end - start));
    }
}

// java.lang.OutOfMemoryError: Java heap space
// Dumping heap to java_pid17276.hprof ...
// Heap dump file created [1713324 bytes in 0.007 secs]
// Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
