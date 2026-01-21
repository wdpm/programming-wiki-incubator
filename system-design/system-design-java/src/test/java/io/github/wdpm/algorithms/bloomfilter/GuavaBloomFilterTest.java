package io.github.wdpm.algorithms.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * guava bloom filter
 *
 * @author evan
 * @date 2020/5/8
 */
public class GuavaBloomFilterTest {

    @Test
    void shouldCorrectJudgeBillion() {
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                10000000,
                0.01);

        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }

        Assertions.assertFalse(filter.mightContain(400230340));
    }
}
