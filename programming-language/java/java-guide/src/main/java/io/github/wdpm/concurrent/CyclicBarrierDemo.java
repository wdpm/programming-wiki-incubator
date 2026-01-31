package io.github.wdpm.concurrent;

import java.util.concurrent.CyclicBarrier;
import java.util.logging.Logger;

/**
 * CyclicBarrier 测试
 *
 * <li>初始化线程参与者。
 * <li>调用 await() 会让当前参与者线程进入等待。
 * <li>直到所有参与者都调用了 await() ，所有线程从 await() 返回继续后续逻辑
 *
 * @author evan
 * @date 2020/5/8
 */
public class CyclicBarrierDemo {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(CyclicBarrierDemo.class));

    public static void main(String[] args) {
        new CyclicBarrierDemo().cyclicBarrier();
    }

    private void cyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        new Thread(new MyThread("t1", cyclicBarrier, 0)).start();
        new Thread(new MyThread("t2", cyclicBarrier, 0)).start();
        new Thread(new MyThread("t3", cyclicBarrier, 3000)).start();

        LOGGER.info("main thread");
    }

    private static class MyThread implements Runnable {

        private String        name;
        private CyclicBarrier cyclicBarrier;
        private int           delayInMillis;

        public MyThread(String name, CyclicBarrier cyclicBarrier, int delayInMillis) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
            this.delayInMillis = delayInMillis;
        }

        @Override
        public void run() {
            LOGGER.info(name + " run");
            try {
                Thread.sleep(delayInMillis);
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOGGER.info(name + " end");
        }
    }
}

