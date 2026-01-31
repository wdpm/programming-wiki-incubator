package io.github.wdpm.concurrent;

/**
 * @author evan
 * @date 2020/4/14
 */
public class ThreadRunDemo {
    public static void main(String[] args) {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Thread name is: " + threadName);
        };
        task.run();//main
        Thread thread = new Thread(task);//Thread-0
        thread.start();
        System.out.println("Done!");

        // 可能发生的情况有：

        /*
        Thread name is: main
        Done!
        Thread name is: Thread-0
         */

        /*
        Thread name is: main
        Thread name is: Thread-0
        Done!
         */

        // 也就是Thread-0和Done！谁先谁后的顺序是不确定的。

    }
}
