# MVC设计原理
## 路由设计
### Route 路由对象
  - path 相对请求路径
  - method 方法名
  - controller method所在的控制器对象

作用：根据path在controller中查找对应的method。

举例：
```java
path:       /users
method:     users
controller: UserController 
```

### RouteManger 路由管理器 
持有所有Route对象，并提供添加Route对象的方法。
- addRoute(String path, Method action, Object controller)

### RouteMatcher 路由匹配器
持有所有Route对象，并提供根据path查找Route对象的方法。
- public Route findRoute(String path)

## 核心控制器设计 C
javax.servlet.Filter
```java
public interface Filter {
    void init(FilterConfig var1) throws ServletException;

    void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;

    void destroy();
}
```
```java
public class MapleFilter implements Filter
```
并重写这三个方法。
- void init(FilterConfig var1) 
  - 整个MVC的初始化逻辑
  - 初始化maple
  - 读取web.xml中bootstrap参数值，并使用反射机制初始化一个bootstrap
  - 初始化routMatcher
  - 获取servletContext
  - 设置初始化标记字段
- void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
  - 每个来自客户端请求的处理逻辑
  - 使用routeMatcher，根据request查找最佳匹配的route对象。
    - 如果可以找到匹配：使用ThreadLocal<MapleContext> 开启线程执行上下文，处理每一个独立的请求
    - 否则，传递给下一个请求链
- void destroy();
  - 销毁方法

## 全局配置设计 
新建一个类作为全局配置对象。Maple

- private RouteManger routeManger; //全局路由管理器
- private ConfigLoader configLoader;//一个HashMap，存放KV值
- private boolean initialized; //初始化完成的标记
- private Render render;//接口，表示渲染器类型

并提供一个重要方法，方便将Route类型的对象添加到routeManger中。
- public Maple addRoute(String path, String methodName, Object controller)

### 启动接口
```java
public interface Bootstrap {
    void init(Maple maple);
}
```
将来客户端使用时，将会这样：
```java
public class App implements Bootstrap {
    @Override
    public void init(Maple maple) {
      //客户端在这里添加自定义的控制器，初始化数据连接等。
    }
}
```

## 视图设计 V
封装一个渲染接口
```java
public interface Render {
    void render(String view, Writer writer);
}
```
具体的接口实现
```java
public class JspRender implements Render {
    public static final Logger LOGGER = Logger.getLogger(JspRender.class.getName());

    @Override
    public void render(String view, Writer writer) {
        String viewPath = this.getViewPath(view);
        // 在这里，获取当前线程执行环境中的servletRequest和servletResponse

        // 执行页面渲染逻辑
        servletRequest.getRequestDispatcher(viewPath).forward(servletRequest, servletResponse);
    }

    // users -> /WEB-INF/users.jsp
    private String getViewPath(String view) {
   
    }
}
```

## 模型层设计 M
对数据领域模型的CRUD。该示例代码使用了Sql2o这个库来演示。
- 初始化数据连接
- 封装通用的CRUD公共方法。