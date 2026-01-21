package io.github.wdpm.algorithms.lru;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 基于LinkedHashMap实现的LRU
 *
 * @author evan
 * @date 2020/5/7
 */
public class LRULinkedHashMap<K, V> {

    private int cacheSize;

    private LinkedHashMap cacheMap;

    public LRULinkedHashMap(int cacheSize) {
        this.cacheSize = cacheSize;

        cacheMap = new LinkedHashMap(16, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return cacheMap.size() > cacheSize;
            }
        };
    }

    public void put(K key, V value) {
        cacheMap.put(key, value);
    }

    public V get(K key) {
        return (V) cacheMap.get(key);
    }

    public void print() {
        cacheMap.forEach((key, value) -> System.out.println("key: " + key + " ;value=" + value));
    }

    public Collection<Map.Entry<K, V>> getAll() {
        return new ArrayList<Map.Entry<K, V>>(cacheMap.entrySet());
    }

    public static void main(String[] args) {
        LRULinkedHashMap<String, Integer> cache = new LRULinkedHashMap<>(3);
        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);
        cache.print();
        // 1->2->3

        Object key5 = cache.get("key5");
        System.out.println(key5);
        // null

        Object key2 = cache.get("key2");
        cache.print();
        // 1->3->2

        cache.put("key8", 8);
        cache.print();
        // 3->2->8
    }
}
