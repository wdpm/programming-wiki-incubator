package io.github.wdpm.concurrency.shutdown;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UEHLogger
 * <p/>
 * UncaughtExceptionHandler that logs the exception
 * <p>
 * 处理未捕获的异常
 */
public class UEHLogger implements Thread.UncaughtExceptionHandler {

    // refer java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new IllegalArgumentException("fake");
            }
        });

        thread.setUncaughtExceptionHandler(new UEHLogger());
        thread.start();

        // 主线程等待上面的线程抛异常
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
