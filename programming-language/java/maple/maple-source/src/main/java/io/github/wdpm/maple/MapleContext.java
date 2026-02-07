package io.github.wdpm.maple;


import io.github.wdpm.maple.servlet.wrapper.Request;
import io.github.wdpm.maple.servlet.wrapper.Response;

import javax.servlet.ServletContext;

/**
 * 当前线程执行上下文
 *
 * @author evan
 */
public final class MapleContext {

    private static final ThreadLocal<MapleContext> CONTEXT = new ThreadLocal<MapleContext>();

    private ServletContext servletContext;
    private Request        request;
    private Response       response;

    private MapleContext() {
    }

    public static MapleContext getCurrentContext() {
        return CONTEXT.get();
    }

    public static void initContext(ServletContext servletContext, Request request, Response response) {
        MapleContext mapleContext = new MapleContext();
        mapleContext.setServletContext(servletContext)
                    .setRequest(request)
                    .setResponse(response);
        CONTEXT.set(mapleContext);
    }

    public static void remove() {
        CONTEXT.remove();
    }

    //==========getters and setters===============//


    public ServletContext getServletContext() {
        return servletContext;
    }

    public MapleContext setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        return this;
    }

    public Request getRequest() {
        return request;
    }

    public MapleContext setRequest(Request request) {
        this.request = request;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public MapleContext setResponse(Response response) {
        this.response = response;
        return this;
    }
}
