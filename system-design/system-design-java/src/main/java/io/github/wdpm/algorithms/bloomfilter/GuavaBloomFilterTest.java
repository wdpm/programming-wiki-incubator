package io.github.wdpm.algorithms.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Assertions;

/**
 * 测试guava bloom filter的性能
 * <p>
 * -Xms64m -Xmx64m -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author evan
 * @date 2020/5/8
 */
public class GuavaBloomFilterTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        com.google.common.hash.BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                10000000,
                0.01);

        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }

        Assertions.assertFalse(filter.mightContain(400230340));
        long end = System.currentTimeMillis();
        System.out.println("Time use(ms)：" + (end - start));
    }
}


