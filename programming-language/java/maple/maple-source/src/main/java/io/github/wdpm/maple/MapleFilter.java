package io.github.wdpm.maple;

import io.github.wdpm.maple.route.Route;
import io.github.wdpm.maple.route.RouteManger;
import io.github.wdpm.maple.route.RouteMatcher;
import io.github.wdpm.maple.servlet.wrapper.Request;
import io.github.wdpm.maple.servlet.wrapper.Response;
import io.github.wdpm.maple.util.PathUtil;
import io.github.wdpm.maple.util.ReflectUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * 过滤器
 * <ol>
 *     <li>接收用户请求</li>
 *     <li>查找路由</li>
 *     <li>找到即执行配置的方法</li>
 *     <li>找不到执行404</li>
 * </ol>
 *
 * @author evan
 * @date 2020/4/22
 */
public class MapleFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(MapleFilter.class.getName());

    private RouteMatcher routeMatcher = new RouteMatcher(new ArrayList<>());

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Maple maple = Maple.getInstance();
        if (!maple.isInitialized()) {
            /*
                <filter>
                    <filter-name>maple</filter-name>
                    <filter-class>io.github.wdpm.maple.MapleFilter</filter-class>
                    <init-param>
                        <param-name>bootstrap</param-name>
                        <param-value>io.github.wdpm.maple.sample.App</param-value>
                    </init-param>
                </filter>
             */
            String    className = filterConfig.getInitParameter("bootstrap");
            Bootstrap bootstrap = this.getBootstrap(className);
            bootstrap.init(maple);//see io.github.wdpm.maple.sample.App.init()

            RouteManger routeManger = maple.getRouteManger();
            // setup routeMatcher
            if (null != routeManger) {
                routeMatcher.setRoutes(routeManger.getRoutes());
            }
            // get servletContext instance
            servletContext = filterConfig.getServletContext();

            // set initialized flag
            maple.setInitialized(true);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(Const.DEFAULT_CHAR_SET);
        response.setCharacterEncoding(Const.DEFAULT_CHAR_SET);

        // extract uri from request
        String uri = PathUtil.getRelativePath(request);
        LOGGER.info("Request URI: " + uri);

        // get best matched route from uri
        Route route = routeMatcher.findRoute(uri);
        if (route != null) {
            handle(request, response, route);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }

    /**
     * 通过反射获取指定className的实例
     *
     * @param className
     * @return
     */
    private Bootstrap getBootstrap(String className) {
        if (null != className) {
            try {
                Class<?> clazz = Class.forName(className);
                return (Bootstrap) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("init bootstrap class error!");
    }

    private void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Route route) {

        Request  request  = new Request(httpServletRequest);
        Response response = new Response(httpServletResponse);
        // init current thread context
        // 每次请求-响应对应一个线程?
        MapleContext.initContext(servletContext, request, response);

        Object controller = route.getController();
        // 要执行的路由方法
        Method actionMethod = route.getAction();
        // 执行route方法
        executeMethod(controller, actionMethod, request, response);
    }

    private Object executeMethod(Object controller, Method method, Request request, Response response) {
        int len = method.getParameterTypes().length;
        method.setAccessible(true);
        if (len > 0) {
            Object[] args = getArgs(request, response, method.getParameterTypes());
            return ReflectUtil.invokeMethod(controller, method, args);
        } else {
            return ReflectUtil.invokeMethod(controller, method);
        }
    }

    /**
     * 转换参数类型。
     * <p>
     * 如果直接使用ReflectUtil.invokeMethod(controller, method, method.getParameterTypes());会抛出
     * java.lang.IllegalArgumentException: argument type mismatch
     * </p>
     */
    private Object[] getArgs(Request request, Response response, Class<?>[] params) {

        int      len  = params.length;
        Object[] args = new Object[len];

        for (int i = 0; i < len; i++) {
            Class<?> paramTypeClazz = params[i];
            if (paramTypeClazz.getName()
                              .equals(Request.class.getName())) {
                args[i] = request;
            }
            if (paramTypeClazz.getName()
                              .equals(Response.class.getName())) {
                args[i] = response;
            }
        }

        return args;
    }
}
