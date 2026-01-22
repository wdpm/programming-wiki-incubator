package io.github.wdpm.jdk11;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Collection新方法
 *
 * @author evan
 * @date 2020/5/2
 */
public class CollectionNewMethod {
    public static void main(String[] args) {
        List<Object> emptyList = List.of();
        List<String> nums      = List.of("1", "2", "3");

        Map<String, String> emptyMap = Map.of();
        Map<String, String> map1     = Map.of("key1", "value1");
        Map<String, String> map2 = Map.ofEntries(
                Map.entry("k1", "v1"),
                Map.entry("k2", "v2"),
                Map.entry("k3", "v3")
        );

        Set<Object>  emptySet = Set.of();
        Set<Integer> set1     = Set.of(1, 2);
    }
}
