package io.github.wdpm.concurrency.atomicvariable;

import io.github.wdpm.concurrency.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ConcurrentStack
 * <p/>
 * Nonblocking stack using Treiber's algorithm
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class ConcurrentStack<E> {
    // ensure 可见性，类似volatile
    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

    // CAS 保证原子性修改
    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}
