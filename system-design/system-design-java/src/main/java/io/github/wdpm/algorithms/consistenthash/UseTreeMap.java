package io.github.wdpm.algorithms.consistenthash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 使用TreeMap实现一致哈希。
 *
 * @author evan
 * @date 2020/5/6
 */
public class UseTreeMap {
    public static void main(String[] args) {
        TreeMap<Long, String> treeMap = new TreeMap<>();
        treeMap.put(100L, "127.0.0.100");
        treeMap.put(10L, "127.0.0.10");
        treeMap.put(7L, "127.0.0.7");
        treeMap.put(500L, "127.0.0.500");
        System.out.println(treeMap);

        SortedMap<Long, String> result = treeMap.tailMap(19L);
        if (!result.isEmpty()) {
            System.out.println(result.get(result.firstKey()));
        } else {
            System.out.println("use default value.");
        }

    }
}

// {7=127.0.0.7, 10=127.0.0.10, 100=127.0.0.100, 500=127.0.0.500}
// 127.0.0.100
