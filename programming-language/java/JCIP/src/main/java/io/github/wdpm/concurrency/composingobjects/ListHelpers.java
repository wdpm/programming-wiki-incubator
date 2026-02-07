package io.github.wdpm.concurrency.composingobjects;

import io.github.wdpm.concurrency.annotations.NotThreadSafe;
import io.github.wdpm.concurrency.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * ListHelder
 * <p/>
 * Examples of thread-safe and non-thread-safe implementations of
 * put-if-absent helper methods for List
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
class BadListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    // 同步行为发生于错误的锁上，这里其实需要同步的是list这个对象
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) list.add(x);
        return absent;
    }
}

@ThreadSafe
class GoodListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        // 正确地同步list对象
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}
