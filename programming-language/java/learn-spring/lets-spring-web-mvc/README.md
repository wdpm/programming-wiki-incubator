# Spring MVC

## 启动
在当前模块目录执行，或者使用IDE的对应按钮jetty:run。
```bash
mvn jetty:run
```
访问:
- /order/new
- /test
- /json

## Java Web 应用目录方法论

经典的目录结构。
-  src/main/java：应用程序代码。
-  src/main/resources：文本文件、属性等。包含在类路径中，协助应用程序代码。
-  src/main/webapp/resources：公共资源，比如CSS、JavaScript、图像等。
-  src/main/webapp/WEB-INF：私有资源，servlet 容器可以访问它们。

其中，src/main/java目录粗粒度划分如下：
```
├─controller
├─dao
├─model
└─service
```
从客户端请求流向来观察，顺序为：request -> controller -> service -> dao。
- controller细分：RestController/ Controller
- dao: 一般是interface + 对应Impl
- model：或者称为domain领域层，可继续细分Entity，DTO，VO等
- service：一般是interface + 对应impl，引用DAO，但被Controller引用。

## 实例解析
```
├─java
│  └─io
│      └─github
│          └─wdpm
│              ├─controller
│              │      OrderController.java
│              │
│              ├─dao
│              │      FlavorDAO.java
│              │      FlavorDAOImpl.java
│              │      ToppingDAO.java
│              │      ToppingDAOImpl.java
│              │
│              ├─model
│              │      Flavor.java
│              │      Order.java
│              │      Topping.java
│              │
│              └─service
│                      OrderService.java
│                      OrderServiceImpl.java
│
└─webapp
    │  index.jsp
    │
    ├─resources
    └─WEB-INF
        │  spring-servlet.xml
        │  web.xml
        │
        └─views
                new-order.jsp
                order-created.jsp
```

## Spring 配置的演化
```
└─WEB-INF
    │  spring-servlet.xml
    │  web.xml
```
### xml 配置
- spring-servlet.xml
```xml
    <context:component-scan base-package="io.github.wdpm"/>

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <mvc:annotation-driven/>
```
定义了组件扫描，JstlView处理jsp视图层，mvc注解处理。

- web.xml
```
<servlet>
    <servlet-name>spring</servlet-name>
    <servlet-class>
        org.springframework.web.servlet.DispatcherServlet
    </servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/spring-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>spring</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```
优先级很高的DispatcherServlet，需要加载spring-servlet.xml完成mapping映射。

### Java 配置
- WebConfig.java 取代 spring-servlet.xml
- WebApplicationInitializer.java 取代 web.xml

[Spring MVC 解放xml配置](..\lets-spring-web-mvc-java-io.github.wdpm.config\README.md)


## 参考
- [configuring-jetty-container](https://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html#configuring-jetty-container)
