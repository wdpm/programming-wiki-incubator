package io.github.wdpm.concurrency.testingconcurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * TimedPutTakeTest
 * <p/>
 * Testing with a barrier-based timer
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedPutTakeTest extends PutTakeTest {
    private BarrierTimer timer = new BarrierTimer();

    public TimedPutTakeTest(int cap, int pairs, int trials) {
        super(cap, pairs, trials);
        barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
    }

    public void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new PutTakeTest.Producer());
                pool.execute(new PutTakeTest.Consumer());
            }
            barrier.await();
            barrier.await();
            /**
             * 所有任务执行完成之后的时间统计
             */
            long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
            System.out.print("Throughput: " + nsPerItem + " ns/item");
            // System.out.println(putSum.get() == takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        int tpt = 100000; // trials per thread
        for (int cap = 10; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
                System.out.print("Pairs: " + pairs + "\t");
                t.test();
                System.out.print("\t");
                Thread.sleep(1000);
                t.test();
                System.out.println();
                Thread.sleep(1000);
            }
        }
        PutTakeTest.pool.shutdown();
    }
}
/*
Capacity: 10
Pairs: 1	Throughput: 1141 ns/item	Throughput: 1623 ns/item
Pairs: 2	Throughput: 1853 ns/item	Throughput: 1810 ns/item
Pairs: 4	Throughput: 1691 ns/item	Throughput: 1668 ns/item
Pairs: 8	Throughput: 1479 ns/item	Throughput: 1641 ns/item
Pairs: 16	Throughput: 1528 ns/item	Throughput: 1550 ns/item
Pairs: 32	Throughput: 1876 ns/item	Throughput: 2032 ns/item
Pairs: 64	Throughput: 2034 ns/item	Throughput: 2016 ns/item
Pairs: 128	Throughput: 2004 ns/item	Throughput: 2094 ns/item
Capacity: 100
Pairs: 1	Throughput: 656 ns/item	Throughput: 569 ns/item
Pairs: 2	Throughput: 525 ns/item	Throughput: 431 ns/item
Pairs: 4	Throughput: 338 ns/item	Throughput: 393 ns/item
Pairs: 8	Throughput: 245 ns/item	Throughput: 251 ns/item
Pairs: 16	Throughput: 285 ns/item	Throughput: 288 ns/item
Pairs: 32	Throughput: 324 ns/item	Throughput: 325 ns/item
Pairs: 64	Throughput: 362 ns/item	Throughput: 464 ns/item
Pairs: 128	Throughput: 476 ns/item	Throughput: 475 ns/item
Capacity: 1000
Pairs: 1	Throughput: 347 ns/item	Throughput: 427 ns/item
Pairs: 2	Throughput: 228 ns/item	Throughput: 342 ns/item
Pairs: 4	Throughput: 235 ns/item	Throughput: 262 ns/item
Pairs: 8	Throughput: 200 ns/item	Throughput: 267 ns/item
Pairs: 16	Throughput: 207 ns/item	Throughput: 207 ns/item
Pairs: 32	Throughput: 199 ns/item	Throughput: 201 ns/item
Pairs: 64	Throughput: 197 ns/item	Throughput: 213 ns/item
Pairs: 128	Throughput: 270 ns/item	Throughput: 287 ns/item
 */
