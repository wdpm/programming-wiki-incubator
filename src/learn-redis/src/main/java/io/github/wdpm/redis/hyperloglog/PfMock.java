package io.github.wdpm.redis.hyperloglog;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author evan
 * @date 2020/6/7
 */
public class PfMock {

    static class BitKeeper {
        private int maxBits;

        public void random() {
            long value = ThreadLocalRandom.current().nextLong(2L << 32);// 2^32
            int  bits  = lowZeros(value);
            if (bits > this.maxBits) {
                this.maxBits = bits;
            }
        }

        private int lowZeros(long value) {
            int i = 1;
            for (; i < 32; i++) {
                // 向右移动i位，然后向左移动i位，如果相等，代表右侧i位都是0，那就继续，否则，返回i-1
                if (value >> i << i != value) {
                    break;
                }
            }
            return i - 1;
        }
    }

    static class Experiment {
        private int       n;
        private BitKeeper keeper;

        public Experiment(int n) {
            this.n = n;
            this.keeper = new BitKeeper();
        }

        public void work() {
            for (int i = 0; i < n; i++) {
                this.keeper.random();
            }
        }

        public void debug() {
            System.out.printf("%d %.2f %d\n", this.n, Math.log(this.n) / Math.log(2), this.keeper.maxBits);
        }
    }

    public static void main(String[] args) {
        for (int i = 1000; i < 100000; i += 100) {
            Experiment exp = new Experiment(i);
            exp.work();
            exp.debug();
        }
    }
}
