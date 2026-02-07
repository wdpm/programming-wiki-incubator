package io.github.wdpm.concurrency.testingconcurrent;

/**
 * BarrierTimer
 * <p/>
 * Barrier-based timer
 *
 * @author Brian Goetz and Tim Peierls
 */
public class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime, endTime;

    public synchronized void run() {
        long t = System.nanoTime();
        if (!started) {
            started = true;
            startTime = t;
        } else endTime = t;
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }

    public static void main(String[] args) throws InterruptedException {
        BarrierTimer barrierTimer = new BarrierTimer();
        barrierTimer.run();//set started
        Thread.sleep(3000);
        barrierTimer.run();//set end
        System.out.println(barrierTimer.getTime());
    }
}
