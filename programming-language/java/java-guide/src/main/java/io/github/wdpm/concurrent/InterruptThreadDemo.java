package io.github.wdpm.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author evan
 * @date 2020/5/8
 */
public class InterruptThreadDemo implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " is running...");
        }
        System.out.println(Thread.currentThread().getName() + " exit.");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new InterruptThreadDemo(), "Thread A");
        thread.start();

        System.out.println("main thread is running.");

        TimeUnit.MILLISECONDS.sleep(1);
        thread.interrupt();
    }


}
