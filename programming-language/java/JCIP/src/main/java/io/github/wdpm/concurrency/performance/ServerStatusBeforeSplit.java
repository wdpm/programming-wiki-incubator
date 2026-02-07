package io.github.wdpm.concurrency.performance;

import io.github.wdpm.concurrency.annotations.GuardedBy;
import io.github.wdpm.concurrency.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * ServerStatusBeforeSplit
 * <p/>
 * Candidate for lock splitting
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class ServerStatusBeforeSplit {
    @GuardedBy("this")
    public final Set<String> users;
    @GuardedBy("this")
    public final Set<String> queries;

    public ServerStatusBeforeSplit() {
        users = new HashSet<String>();
        queries = new HashSet<String>();
    }

    // 这里同步了this，其实可以只同步users
    public synchronized void addUser(String u) {
        users.add(u);
    }

    // 这里同步了this，其实可以只同步queries
    public synchronized void addQuery(String q) {
        queries.add(q);
    }

    //...
    public synchronized void removeUser(String u) {
        users.remove(u);
    }

    //...
    public synchronized void removeQuery(String q) {
        queries.remove(q);
    }
}
