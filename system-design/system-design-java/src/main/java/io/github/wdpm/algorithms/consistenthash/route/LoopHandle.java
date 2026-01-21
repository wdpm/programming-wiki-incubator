package io.github.wdpm.algorithms.consistenthash.route;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author evan
 * @date 2020/5/6
 */
public class LoopHandle implements RouteHandle {
    private final AtomicLong   index      = new AtomicLong();
    private final List<String> serverList = new ArrayList<>();

    @Override
    public void registerRouteServer(List<String> values) {
        serverList.addAll(values);
    }

    @Override
    public String getServer(String key) {
        if (isServerEmpty()) {
            throw new IllegalStateException("Available server list is empty");
        }

        long position = index.getAndIncrement() % serverList.size();
        if (position < 0L) position = 0L;
        return serverList.get((int) position);
    }

    private boolean isServerEmpty() {
        return serverList.size() == 0;
    }
}
