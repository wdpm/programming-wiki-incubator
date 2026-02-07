package io.github.wdpm.concurrency.threadpools;

import java.util.concurrent.*;

/**
 * ThreadDeadlock
 * <p/>
 * Task that deadlocks in a single-threaded Executor
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return "";
        }
    }

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception {
            Future<String> header, footer;
            // get header async
            header = exec.submit(new LoadFileTask("header.html"));
            // get footer async
            footer = exec.submit(new LoadFileTask("footer.html"));
            // render body sync
            String page = renderBody();

            // wait header/footer result and concat to page

            /** 子任务可能没有机会运行
             * Will deadlock -- task waiting for result of subtask
             * */
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
    }

    public void testRun() {
        Future<String> future = exec.submit(new RenderPageTask());
        try {
            // Note that can't print this line because of dead lock
            System.out.println("dead lock......" + future.get());

            // analysis:
            // 线程池为：RenderPageTask
            // 工作队列为：-> LoadFileTask(header) -> LoadFileTask(footer)
            // RenderPageTask在等待 LoadFileTask(header) / LoadFileTask(footer) 的完成，header.get() + page + footer.get()于是它阻塞了
            // 然而LoadFileTask(header) / LoadFileTask(footer)因为RenderPageTask的阻塞，无法完成，于是也阻塞了
            // 这就是线程死锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ThreadDeadlock().testRun();
    }
}
