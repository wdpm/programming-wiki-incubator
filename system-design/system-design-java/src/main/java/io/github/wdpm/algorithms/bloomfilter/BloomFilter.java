package io.github.wdpm.algorithms.bloomfilter;

/**
 * 简易 bloom filter
 *
 * @author evan
 * @date 2020/5/8
 */
public class BloomFilter {

    private int arraySize;

    private int[] array;

    public BloomFilter(int arraySize) {
        this.arraySize = arraySize;
        array = new int[arraySize];
    }

    /**
     * 写入数据
     *
     * @param key
     */
    public void add(String key) {
        int first  = hashCode1(key);
        int second = hashCode2(key);
        int third  = hashCode3(key);

        array[first % arraySize] = 1;
        array[second % arraySize] = 1;
        array[third % arraySize] = 1;

    }

    /**
     * 判断数据是否存在
     *
     * @param key
     * @return
     */
    public boolean mightContain(String key) {
        int first  = hashCode1(key);
        int second = hashCode2(key);
        int third  = hashCode3(key);

        int firstIndex = array[first % arraySize];
        if (firstIndex == 0) {
            return false;
        }

        int secondIndex = array[second % arraySize];
        if (secondIndex == 0) {
            return false;
        }

        int thirdIndex = array[third % arraySize];
        if (thirdIndex == 0) {
            return false;
        }

        return true;

    }

    /**
     * hash 算法1
     *
     * @param key
     * @return positive number or 0
     * @see String#hashCode()
     */
    private int hashCode1(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); ++i) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    /**
     * hash 算法2
     *
     * @param key
     * @return positive number or 0
     * @refer https://github.com/crossoverJie/JCSprout/blob/master/docs/algorithm/guava-bloom-filter.md
     */
    private int hashCode2(String key) {
        final int p    = 16777619;
        int       hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**
     * hash 算法3
     *
     * @param key
     * @return positive number or 0
     * @refer https://github.com/crossoverJie/JCSprout/blob/master/docs/algorithm/guava-bloom-filter.md
     */
    private int hashCode3(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }

    public static void main(String[] args) {
        long         start   = System.currentTimeMillis();
        final int   COUNT   = 1000000;
        BloomFilter filters = new BloomFilter(COUNT);
        for (int i = 0; i < COUNT; i++) {
            filters.add(i + "");
        }
        assert filters.mightContain(String.valueOf(5678));
        long end = System.currentTimeMillis();
        System.out.println("Time cost(ms): " + (end - start));
    }
}