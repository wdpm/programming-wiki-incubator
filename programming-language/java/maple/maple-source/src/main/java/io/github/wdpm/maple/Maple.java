package io.github.wdpm.maple;

import io.github.wdpm.maple.render.JspRender;
import io.github.wdpm.maple.render.Render;
import io.github.wdpm.maple.route.RouteManger;
import io.github.wdpm.maple.servlet.wrapper.Request;
import io.github.wdpm.maple.servlet.wrapper.Response;

import java.lang.reflect.Method;

/**
 * 全局Maple对象
 * <ol>
 *     <li>路由管理</li>
 *     <li>配置管理</li>
 *     <li>初始化操作</li>
 * </ol>
 *
 * @author evan
 * @date 2020/4/22
 */
public class Maple {

    private RouteManger routeManger;

    private ConfigLoader configLoader;

    private boolean initialized;

    private Render render;

    public Maple() {
        routeManger = new RouteManger();
        configLoader = new ConfigLoader();
        render = new JspRender();
    }

    public static Maple getInstance() {
        return MapleHolder.instance;
    }

    private static class MapleHolder {
        private static final Maple instance = new Maple();
    }

    public Maple loadConf(String conf) {
        configLoader.load(conf);
        return this;
    }

    public String getConf(String name) {
        return configLoader.getConf(name);
    }

    public Maple setConf(String name, String value) {
        configLoader.setConf(name, value);
        return this;
    }

    public Maple addRoutes(RouteManger routeManager) {
        getRouteManger().addRoute(routeManager.getRoutes());
        return this;
    }

    public Maple addRoute(String path, String methodName, Object controller) {
        try {
            // public Method getMethod(String name, Class<?>... parameterTypes)
            // parameterTypes参数是Class对象的数组，这些Class对象按声明的顺序标识方法的形式参数类型。
            // see @io.github.wdpm.maple.sample.controller.UserController.users
            // 例子：public void users(Request request, Response response)
            Method method = controller.getClass()
                                      .getMethod(methodName, Request.class, Response.class);
            getRouteManger().addRoute(path, method, controller);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return this;
    }

    //========getter and setters============//

    public RouteManger getRouteManger() {
        return routeManger;
    }

    public void setRouteManger(RouteManger routeManger) {
        this.routeManger = routeManger;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public Render getRender() {
        return render;
    }

    public void setRender(Render render) {
        this.render = render;
    }

}
