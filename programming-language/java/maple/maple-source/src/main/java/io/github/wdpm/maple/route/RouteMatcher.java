package io.github.wdpm.maple.route;

import io.github.wdpm.maple.util.PathUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

/**
 * 路由匹配器
 *
 * @author evan
 * @date 2020/4/22
 */
public class RouteMatcher {

    public static final Logger LOGGER = Logger.getLogger(RouteMatcher.class.getName());

    private List<Route> routes;

    public RouteMatcher() {
    }

    public RouteMatcher(List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    /**
     * 根据path查找对应Route对象
     *
     * @param path String
     * @return corresponding Route instance
     */
    public Route findRoute(String path) {
        String cleanPath = parsePath(path);
        Objects.requireNonNull(cleanPath);

        List<Route> matchRoutes = new ArrayList<>();
        for (Route route : getRoutes()) {
            if (matchesPath(route.getPath(), cleanPath)) {
                matchRoutes.add(route);
            }
        }

        giveMatch(path, matchRoutes);

        return matchRoutes.size() > 0 ? matchRoutes.get(0) : null;
    }

    private boolean matchesPath(String routePath, String pathToMatch) {
        // (?i) match case insensitive
        return pathToMatch.matches("(?i)" + routePath);
    }

    /**
     * 将matchRoutes按优先级排序，排序后第一个元素就是匹配值
     *
     * @param uri
     * @param routes
     */
    private void giveMatch(final String uri, List<Route> routes) {
        // Now just do nothing

        // go further: do best match for multi matched results
    }

    /**
     * 修复入参path的正规性
     *
     * @param path String
     * @return 正规化后的path
     */
    private String parsePath(String path) {
        String fixPath = PathUtil.fixPath(path);
        try {
            return new URI(fixPath).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
