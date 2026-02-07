package io.github.wdpm.maple.route;

import java.lang.reflect.Method;

/**
 * 路由对象，封装一个web 请求。
 *
 * 匹配在 path ，执行靠 method，该method定义在controller中
 *
 * @author evan
 * @date 2020/4/22
 */
public class Route {

    private String path;

    private Method action;

    private Object controller;

    public Route() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
