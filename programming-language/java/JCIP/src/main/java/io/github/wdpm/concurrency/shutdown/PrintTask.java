package io.github.wdpm.concurrency.shutdown;

/**
 * Created by vonzhou on 2017/6/4.
 *
 * @modify evan
 */
public class PrintTask implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(String.format("[%s] task running ....", System.currentTimeMillis()));
        }
    }
}
