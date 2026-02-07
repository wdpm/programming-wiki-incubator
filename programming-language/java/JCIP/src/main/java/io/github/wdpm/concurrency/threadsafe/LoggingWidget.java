package io.github.wdpm.concurrency.threadsafe;

/**
 * @author ?
 * @date 2020/6/9
 */
public class LoggingWidget extends Widget {
    @Override
    public synchronized void doSomething() {
        System.out.println(toString() + ": call doSomething()");
        // 如果内部锁不可重入，这行永远拿不到widget的锁
        super.doSomething();
    }
}
