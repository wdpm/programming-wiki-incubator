package io.github.wdpm.algorithms.consistenthash;

import java.util.TreeMap;

/**
 * SortArrayMap和TreeMap的性能简单测试。
 *
 * @author evan
 * @date 2020/5/6
 */
public class MicroBenchmark {
    private static final int COUNT = 1000000;

    public static void main(String[] args) {
        testSortArrayMap();
        testTreeMap();
    }

    private static void testSortArrayMap() {
        SortArrayMap map   = new SortArrayMap();
        long         start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            int scale = (int) (Math.random() * 100);
            map.add((long) (i + scale), "127.0.0." + i);
        }
        map.sort();
        long end = System.currentTimeMillis();
        System.out.println("SortArrayMap use time: " + (end - start));
        System.out.println(map.size());
    }

    private static void testTreeMap() {
        TreeMap<Long, String> map   = new TreeMap<>();
        long                  start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            int scale = (int) (Math.random() * 100);
            map.put((long) (i + scale), "127.0.0." + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("TreeMap use time: " + (end - start));
        System.out.println(map.size());
    }
}

// SortArrayMap use time: 436
// 1000000
// TreeMap use time: 352
// 633838