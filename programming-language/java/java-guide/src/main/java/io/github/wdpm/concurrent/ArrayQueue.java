package io.github.wdpm.concurrent;

/**
 * 数组队列
 *
 * @author evan
 * @date 2020/5/8
 */
public class ArrayQueue<T> {

    // item size
    private int size = 0;

    // real items
    private Object[] items;

    // not full lock
    private Object notFull = new Object();

    // not empty lock
    private Object notEmpty = new Object();

    // index when put data
    private int putIndex = 0;

    // index when get data
    private int getIndex = 0;

    public ArrayQueue(int initCapacity) {
        this.items = new Object[initCapacity];
    }

    public void put(T t) {
        // 同步full lock，如果队列已满，阻塞生产者put
        synchronized (notFull) {
            while (size == items.length) {
                try {
                    notFull.wait();//挂起full锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;//这里表示，消费者消费数据后，可以主动notify full，从而中断full的等待状态。
                }
            }
        }

        synchronized (notEmpty) {
            items[putIndex++] = t;
            size++;
            if (putIndex == items.length) {
                //reset put index
                putIndex = 0;
            }
            // 此时至少有一个data，可以通知消费者来消费
            notEmpty.notify();
        }
    }

    public T get() {
        // 同步empty锁，如果队列为空，阻塞消费者get
        synchronized (notEmpty) {
            while (size == 0) {
                try {
                    notEmpty.wait();//挂起empty锁
                } catch (InterruptedException e) {
                    return null;//why return null?
                }
            }
        }

        synchronized (notFull) {
            Object result = items[getIndex];//save result
            items[getIndex] = null;//GC
            size--;

            getIndex++;
            if (getIndex == items.length) {
                getIndex = 0;
            }

            // 此时队列必然不为满，通知生产者可以生产
            notFull.notify();

            return (T) result;
        }
    }

    public synchronized int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayQueue<String> queue = new ArrayQueue<>(299);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {//0...99
                queue.put(i + "");
            }
        });

        Thread t2 = new Thread(() -> {//100...199
            for (int i = 100; i < 200; i++) {
                queue.put(i + "");
            }
            System.out.println("t2: " + queue.get());
        });

        Thread t3 = new Thread(() -> {//200...299
            for (int i = 200; i < 300; i++) {
                queue.put(i + "");
            }
            System.out.println("t3: " + queue.get());
        });

        Thread t4 = new Thread(() -> {
            System.out.println("t4: " + queue.get());
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("main thread over");
        System.out.println(queue.size());//fixme: is a bug? sometime 296?
    }

}
