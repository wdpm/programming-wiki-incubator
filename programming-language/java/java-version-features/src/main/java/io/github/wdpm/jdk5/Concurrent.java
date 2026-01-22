package io.github.wdpm.jdk5;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发库
 *
 * <li>线程互斥工具类：Lock，ReadWriteLock，ReentrantLock</li>
 * <li>线程通信：Condition</li>
 * <li>线程池：ExecutorService</li>
 * <li>同步队列：ArrayBlockingQueue</li>
 * <li>同步集合：ConcurrentHashMap，CopyOnWriteArrayList</li>
 * <li>线程同步工具：Semaphore</li>
 *
 * @author evan
 * @since 2020/4/19
 */
public class Concurrent {

    public void reentrantLock() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println("some thing");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void condition() throws InterruptedException {
        ReentrantLock lock      = new ReentrantLock();
        Condition     condition = lock.newCondition();
        // do stuff
        condition.await(10, TimeUnit.SECONDS);
        System.out.println("done");
    }

    public void executorService() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(() -> {
            System.out.println("do stuff");
        });
        // wait for executorService finished.
    }

    public void blockingQueue() {
        Queue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        blockingQueue.add(1);
        blockingQueue.add(2);
        blockingQueue.add(3);
        Integer head = blockingQueue.peek();
    }

    public void concurrentHashMap() {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("foo", 1);
        concurrentHashMap.put("bar", 2);
    }

    public void copyOnWriteArrayList() {
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("1");
        copyOnWriteArrayList.add("2");
        copyOnWriteArrayList.add("3");
    }

    public void semaphore() {
        Semaphore semaphore = new Semaphore(3);
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread()
                                     .getName() + " do stuff");
            Thread.sleep(1000);
            semaphore.release();
            System.out.println(Thread.currentThread()
                                     .getName() + " die");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Concurrent concurrent = new Concurrent();
        concurrent.semaphore();
    }

}
