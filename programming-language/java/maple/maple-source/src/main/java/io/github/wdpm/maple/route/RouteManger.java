package io.github.wdpm.maple.route;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 路由管理器，保存/管理所有Route类型的对象
 *
 * @author evan
 * @date 2020/4/22
 */
public class RouteManger {
    private static final Logger LOGGER = Logger.getLogger(RouteManger.class.getName());

    private List<Route> routes = new ArrayList<>();

    public RouteManger() {
    }

    public void addRoute(List<Route> routes) {
        routes.addAll(routes);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    public void addRoute(String path, Method action, Object controller) {
        Route route = new Route();
        route.setPath(path);
        route.setAction(action);
        route.setController(controller);
        routes.add(route);

        LOGGER.info("Add Route：[" + path + "]");
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
