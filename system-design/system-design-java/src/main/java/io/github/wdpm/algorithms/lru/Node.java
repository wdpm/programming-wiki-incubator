package io.github.wdpm.algorithms.lru;

import java.util.StringJoiner;

/**
 * node type of double linked list
 *
 * @author evan
 * @date 2020/5/7
 */
public class Node<K, V> {

    private K          key;
    private V          value;
    private Node<K, V> prev;
    private Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                .add("key=" + key)
                .add("value=" + value)
                .toString();
    }
}
