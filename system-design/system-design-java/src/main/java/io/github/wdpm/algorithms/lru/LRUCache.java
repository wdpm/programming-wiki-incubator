package io.github.wdpm.algorithms.lru;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * LRU cache
 *
 * @author evan
 * @date 2020/5/7
 */
public class LRUCache<K, V> {
    // key -> Node(key, val)
    private HashMap<K, Node> map;

    // Node(k1, v1) <-> Node(k2, v2)...
    private LinkedList cache;

    // max capacity
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        cache = new LinkedList();
    }

    public V get(K key) {
        if (!map.containsKey(key)) return null;

        V val = (V) map.get(key).getValue();
        put(key, val);

        return val;
    }

    public void put(K key, V val) {

        // create new node
        Node x = new Node(key, val);

        //key exists
        if (map.containsKey(key)) {
            // delete old node
            cache.remove(map.get(key));
        } else {//key not exists
            // if cache is full
            if (capacity == cache.size()) {
                // delete last node of this linked list
                Node last = (Node) cache.removeLast();
                // delete this key of hash map
                map.remove(last.getKey());
            }
        }
        // now, ensure has space for inserting new node
        // insert new node to head
        cache.addFirst(x);
        // update hash map
        map.put(key, x);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LRUCache.class.getSimpleName() + "[", "]")
                .add("map=" + map)
                .add("cache=" + cache)
                .add("capacity=" + capacity)
                .toString();
    }

    public void print() {
        for (Object o : cache) {
            System.out.println(o);
        }
    }

    public static void main(String[] args) {
        LRUCache<Object, Object> cache = new LRUCache<>(3);
        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);
        cache.print();
        // 3->2->1

        Object key5 = cache.get("key5");
        System.out.println(key5);
        // null

        Object key2 = cache.get("key2");
        cache.print();
        // 2->3->1

        cache.put("key8",8);
        cache.print();
        // 8->2->3
    }
}
