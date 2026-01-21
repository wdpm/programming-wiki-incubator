package io.github.wdpm.algorithms.consistenthash.route;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/6
 */
public interface RouteHandle {

    void registerRouteServer(List<String> values);

    String getServer(String key);
}