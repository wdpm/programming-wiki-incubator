package io.github.wdpm.redis.hyperloglog;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author evan
 * @date 2020/6/7
 */
public class PfMock2 {
    static class BitKeeper {
        private int maxBits;

        public void random(long value) {
            int bits = lowZeros(value);
            if (bits > this.maxBits) {
                this.maxBits = bits;
            }
        }

        private int lowZeros(long value) {
            int i = 1;
            for (; i < 32; i++) {
                if (value >> i << i != value) {
                    break;
                }
            }
            return i - 1;
        }
    }

    static class Experiment {
        private int         n;
        private int         k;// 表示k个BitKeeper
        private BitKeeper[] keepers;

        public Experiment(int n) {
            this(n, 1024);
        }

        public Experiment(int n, int k) {
            this.n = n;
            this.k = k;
            this.keepers = new BitKeeper[k];
            for (int i = 0; i < k; i++) {
                this.keepers[i] = new BitKeeper();
            }
        }

        /**
         * 随机选取一个BitKeeper进行random计算
         */
        public void work() {
            for (int i = 0; i < this.n; i++) {
                long m = ThreadLocalRandom.current().nextLong(1L << 32);
                // (m & 0xfff0000) >> 16 表示取m的高16位
                // 如果高16位一致，那么它们会选择同一个BitKeeper
                BitKeeper keeper = keepers[(int) (((m & 0xfff0000) >> 16) % keepers.length)];
                keeper.random(m);
            }
        }

        public double estimate() {
            double sumbitsInverse = 0.0;
            for (BitKeeper keeper : keepers) {
                // 避免除以0的错误
                if (keeper.maxBits == 0) {
                    continue;
                }
                sumbitsInverse += 1.0 / (float) keeper.maxBits;
            }
            double avgBits = (float) keepers.length / sumbitsInverse;
            return Math.pow(2, avgBits) * this.k;//根据桶的数量对估计值进行放大
        }
    }

    public static void main(String[] args) {
        for (int i = 100000; i < 1000000; i += 100000) {
            Experiment exp = new Experiment(i);
            exp.work();
            double est = exp.estimate();
            System.out.printf("%d %.2f %.2f\n", i, est, Math.abs(est - i) / i);
        }
    }
}

// 100000 90648.93 0.09
// 200000 178884.07 0.11
// 300000 314325.66 0.05
// 400000 387345.44 0.03
// 500000 477598.75 0.04
// 600000 611007.41 0.02
// 700000 726801.06 0.04
// 800000 763134.88 0.05
// 900000 864527.08 0.04
