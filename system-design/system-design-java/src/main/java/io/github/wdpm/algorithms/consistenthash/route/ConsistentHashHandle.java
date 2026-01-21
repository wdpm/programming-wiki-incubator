package io.github.wdpm.algorithms.consistenthash.route;

import io.github.wdpm.algorithms.consistenthash.AbstractConsistentHash;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/6
 */
public class ConsistentHashHandle implements RouteHandle {

    private AbstractConsistentHash hash;

    public void setHash(AbstractConsistentHash hash) {
        this.hash = hash;
    }

    @Override
    public void registerRouteServer(List<String> values) {
        hash.process(values);
    }

    @Override
    public String getServer(String key) {
        return hash.firstNodeValue(key);
    }
}
