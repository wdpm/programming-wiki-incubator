package io.github.wdpm.algorithms.consistenthash;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.StringJoiner;
import java.util.TreeMap;

/**
 * 使用TreeMap实现的一致哈希
 *
 * @author evan
 * @date 2020/5/6
 * @see io.github.wdpm.algorithms.consistenthash.AbstractConsistentHash
 * @see io.github.wdpm.algorithms.consistenthash.SortArrayMapConsistentHash
 */
public class TreeMapConsistentHash extends AbstractConsistentHash {

    public static final String              VNODE_PREFIX  = "vir";
    private             TreeMap<Long, Node> treeMap       = new TreeMap<>();
    public static final int                 VIRTUAL_NODES = 2;

    private static class Node {
        private String  value;//ip
        private boolean isVirtual;

        public Node(String value, boolean isVirtual) {
            this.value = value;
            this.isVirtual = isVirtual;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isVirtual() {
            return isVirtual;
        }

        public void setVirtual(boolean virtual) {
            isVirtual = virtual;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                    .add("value='" + value + "'")
                    .add("isVirtual=" + isVirtual)
                    .toString();
        }
    }

    @Override
    public void add(long key, String value) {

        // delegate virtual nodes handler in subclass for change
        // add virtual nodes
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            Long hash = super.hash(VNODE_PREFIX + key + i);
            treeMap.put(hash, new Node(value, true));
        }

        // add real nodes
        treeMap.put(key, new Node(value, false));
    }

    @Override
    public void sort() {
        // treeMap don't need to sort
    }

    @Override
    public String firstNodeValue(String key) {
        Long hash = super.hash(key);
        // System.out.println("key: " + key + " ;hash: " + hash);
        SortedMap<Long, Node> tailMap = treeMap.tailMap(hash);
        if (!tailMap.isEmpty()) {
            return tailMap.get(tailMap.firstKey()).getValue();
        }
        return treeMap.firstEntry().getValue().getValue();
    }

    public static void main(String[] args) {
        TreeMapConsistentHash consistentHash = new TreeMapConsistentHash();
        ArrayList<String>     ips            = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ips.add("127.0.0." + i);
        }

        consistentHash.process(ips);
        System.out.println(consistentHash);

        String where1 = consistentHash.firstNodeValue("zhangsan");
        String where2 = consistentHash.firstNodeValue("wdpm");
        String where3 = consistentHash.firstNodeValue("abcdef");
        String where4 = consistentHash.firstNodeValue("123456");
        String where5 = consistentHash.firstNodeValue("helloworld");
        System.out.println(where1);
        System.out.println(where2);
        System.out.println(where3);
        System.out.println(where4);
        System.out.println(where5);
    }
}
