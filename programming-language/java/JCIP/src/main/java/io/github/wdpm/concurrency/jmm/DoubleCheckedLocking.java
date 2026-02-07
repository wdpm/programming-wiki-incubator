package io.github.wdpm.concurrency.jmm;

import io.github.wdpm.concurrency.annotations.NotThreadSafe;

/**
 * DoubleCheckedLocking
 * <p/>
 * Double-checked-locking antipattern
 *
 * @author Brian Goetz and Tim Peierls
 */
@NotThreadSafe
public class DoubleCheckedLocking {
    private static volatile Resource resource;

    public static Resource getInstance() {
        if (resource == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (resource == null)
                    resource = new Resource();
            }
        }
        return resource;
    }

    static class Resource {

    }
}
