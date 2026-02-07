package io.github.wdpm.concurrency.atomicvariable;

import io.github.wdpm.concurrency.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * LinkedQueue
 * <p/>
 * Insertion in the Michael-Scott nonblocking queue algorithm
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class LinkedQueue<E> {

    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }

    // 虚节点、或者称为哨兵节点
    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head
            = new AtomicReference<Node<E>>(dummy);
    private final AtomicReference<Node<E>> tail
            = new AtomicReference<Node<E>>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            // B D 都是帮忙推进tail指针
            if (curTail == tail.get()) {
                if (tailNext != null) {//A
                    // Queue in intermediate state, advance tail
                    tail.compareAndSet(curTail, tailNext);//B
                } else {
                    // quiescent 表示一般状态，就是非过渡的中间状态
                    // In quiescent state, try inserting new node
                    if (curTail.next.compareAndSet(null, newNode)) {//C
                        // Insertion succeeded, try advancing tail
                        tail.compareAndSet(curTail, newNode);//D
                        return true;
                    }
                }
            }
        }
    }
}