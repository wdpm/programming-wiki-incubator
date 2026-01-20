package concurrency;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

// 在执行例 9-23 所示的代码时，如果没有调用 join 方法，程序将只打印
// “Running...”，而不会输出 Future 的结果，系统在任务完成前即告终止。
// 可以采用两种方案解决这个问题。一种方案是调用 get 或 join 方法，二者将阻塞调
// 用进程，直至检索到结果。另一种方案是为通用线程池设置一个超时时间（time-out
// period），告诉程序等待直至所有线程执行完毕
public class AwaitQuiesenceTest {
    private AwaitQuiesence aq = new AwaitQuiesence();

    @Test
    public void get() {
        try {
            CompletableFuture<Void> cf = aq.supplyThenAccept();
            cf.get();
            assertTrue(cf.isDone());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void join() {
        CompletableFuture<Void> cf = aq.supplyThenAccept();
        cf.join();
        assertTrue(cf.isDone());
    }

    @Test
    public void awaitQuiesence() {
        CompletableFuture<Void> cf = aq.supplyThenAccept();
        assertFalse(cf.isDone());

        ForkJoinPool.commonPool().awaitQuiescence(1, TimeUnit.SECONDS);
        assertTrue(cf.isDone());
    }
}