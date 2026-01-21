package io.github.wdpm.algorithms.consistenthash;

import java.util.ArrayList;

/**
 * 使用SortArrayMap实现的一致哈希
 *
 * @author evan
 * @date 2020/5/6
 * @see io.github.wdpm.algorithms.consistenthash.AbstractConsistentHash
 * @see io.github.wdpm.algorithms.consistenthash.TreeMapConsistentHash
 */
public class SortArrayMapConsistentHash extends AbstractConsistentHash {

    public static final String       VNODE_PREFIX  = "vir";
    private             SortArrayMap sortArrayMap  = new SortArrayMap();
    public static final int          VIRTUAL_NODES = 2;

    @Override
    public void add(long key, String value) {

        // delegate virtual nodes handler in subclass for change
        // add virtual nodes
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            Long hash = super.hash(VNODE_PREFIX + key + i);
            sortArrayMap.add(hash, value, true);
        }

        // add real nodes
        sortArrayMap.add(key, value);
    }

    @Override
    public void sort() {
        sortArrayMap.sort();
    }

    @Override
    public String firstNodeValue(String key) {
        Long hash = super.hash(key);
        System.out.println("key: " + key + " ;hash: " + hash);
        return sortArrayMap.firstNodeValue(hash);
    }

    public static void main(String[] args) {
        SortArrayMapConsistentHash consistentHash = new SortArrayMapConsistentHash();
        ArrayList<String>          ips            = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ips.add("127.0.0." + i);
        }

        consistentHash.process(ips);
        System.out.println(consistentHash);

        // todo make firstNodeValue() return isVirtual info
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
