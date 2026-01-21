package io.github.wdpm.algorithms.bloomfilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 测试 BloomFilters
 *
 * @author evan
 * @date 2020/5/8
 */
class BloomFilterTest {

    @Test
    void mightContain() {
        long         star         = System.currentTimeMillis();
        final int   COUNT        = 10000000;//改为100W会误判4亿
        BloomFilter bloomFilters = new BloomFilter(COUNT);
        for (int i = 0; i < COUNT; i++) {
            bloomFilters.add(i + "");
        }
        Assertions.assertTrue(bloomFilters.mightContain(1 + ""));
        Assertions.assertTrue(bloomFilters.mightContain(2 + ""));
        Assertions.assertTrue(bloomFilters.mightContain(3 + ""));
        Assertions.assertTrue(bloomFilters.mightContain(999999 + ""));
        Assertions.assertFalse(bloomFilters.mightContain(400230340 + ""));
        long end = System.currentTimeMillis();
        System.out.println("Time cost(ms)：" + (end - star));//673
    }
}