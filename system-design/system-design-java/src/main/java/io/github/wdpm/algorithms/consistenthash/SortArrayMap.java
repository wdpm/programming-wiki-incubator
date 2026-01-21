package io.github.wdpm.algorithms.consistenthash;

import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;

/**
 * 有序的Map数组。一致哈希的简单实现。
 *
 * @author evan
 * @date 2020/5/6
 */
public class SortArrayMap {

    private Node[] serviceNodes;

    private static final int DEFAULT_SIZE = 10;

    private int size;//actual service nodes

    private static class Node {
        private Long    key;
        private String  value;
        private boolean isVirtual;

        public Node(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Node(Long key, String value, boolean isVirtual) {
            this.key = key;
            this.value = value;
            this.isVirtual = isVirtual;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                    .add("key=" + key)
                    .add("value='" + value + "'")
                    .add("isVirtual=" + isVirtual)
                    .toString();
        }
    }

    public SortArrayMap() {
        this.serviceNodes = new Node[DEFAULT_SIZE];
    }

    /**
     * insert one service node to the ring. after insertion, do sort
     *
     * @param key
     * @param value
     */
    public void add(Long key, String value) {
        ensureCapacity(size + 1);
        Node node = new Node(key, value);
        serviceNodes[size++] = node;
    }

    public void add(Long key, String value, boolean isVNode) {
        ensureCapacity(size + 1);
        Node node = new Node(key, value, isVNode);
        serviceNodes[size++] = node;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;// or serviceNodes==null
    }

    private void ensureCapacity(int size) {
        if (size > serviceNodes.length) {
            int oldCapacity = serviceNodes.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            serviceNodes = Arrays.copyOf(serviceNodes, newCapacity);
        }
    }

    public void sort() {
        Arrays.sort(serviceNodes, 0, size, Comparator.comparing(o -> o.key));
    }

    public String firstNodeValue(Long key) {
        if (isEmpty()) return null;
        if (key == null) return null;

        // warning: linear search ~ O(n)
        // better improvement: use RedBlackTree for search
        for (Node node : serviceNodes) {
            if (node == null) continue;
            if (node.key >= key) {
                return node.value;
            }
        }

        //use first node as default
        return serviceNodes[0].value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SortArrayMap.class.getSimpleName() + "[", "]")
                .add("serviceNodes=" + Arrays.toString(serviceNodes))
                .add("size=" + size)
                .toString();
    }

    public void print() {
        for (Node serviceNode : serviceNodes) {
            System.out.println(serviceNode);
        }
    }

    public static void main(String[] args) {
        SortArrayMap sortArrayMap = new SortArrayMap();
        sortArrayMap.add(100L, "127.0.0.100");
        sortArrayMap.add(10L, "127.0.0.10");
        sortArrayMap.add(7L, "127.0.0.7");
        sortArrayMap.add(500L, "127.0.0.500");
        sortArrayMap.print();

        sortArrayMap.sort();

        String find189 = sortArrayMap.firstNodeValue(189L);
        String find3   = sortArrayMap.firstNodeValue(3L);
        String find600 = sortArrayMap.firstNodeValue(600L);
        System.out.println(find189);//500
        System.out.println(find3);//7
        System.out.println(find600);//first node 7 as default
    }
}
