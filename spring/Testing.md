# Testing

## Do you use Spring in a unit test?

```
单元测试测试一个功能单元，一个类的方法，一个类或整个模块。测试的单元在其预期的环境之外进行单独测试。除了其他方面，Spring框架不用于在单元测试中执行依赖注入。
相反，任何依赖项通常都被mock或stub替换，mock或stub是在测试类中以编程方式创建的，并使用setter-methods在测试中的实例上设置。
因此，Spring不常用于单元测试。
```



## What type of tests typically use Spring?

```
集成测试是将几个软件模块组合在一起并进行整体测试时的测试。在这样的测试中，组件之间的关系是重要的。
当使用Spring框架进行依赖注入时，依赖注入框架使用的配置文件或类也需要是正确的，因此应该进行测试。
因此集成测试应该使用Spring依赖注入。
```



## How can you create a shared application context in a JUnit integration test?

```
@ContextConfiguration
```



## When and where do you use @Transactional in testing?

```
@Transactional注释可用于更改某些事务资源（例如数据库）的测试，该资源将恢复到运行测试之前的状态。
- 注释可以在方法级别应用，在这种情况下，只有注释的测试方法将在其自己的事务中运行。
- 注释可以在类级别应用，在这种情况下，类中的所有测试方法都将在其自己的事务中执行。
默认情况下，执行测试方法的事务将在测试完成执行后回滚。
```



## How are mock frameworks such as Mockito or EasyMock used?

```
Mockito和EasyMock允许动态创建模拟对象，可用于模拟系统外部或受信任的测试类的协作者。
模拟对象类似于存根，因为它在调用对象上的方法时产生预定的结果。此外，模拟对象还可以验证它是否按预期使用，例如验证方法调用序列，提供给方法的参数等。
模拟对象优于存根，因为它们是动态创建的，仅适用于特定的测试场景。模拟对象通常在执行测试方法之前在测试方法或测试类中创建。
```



## How is @ContextConfiguration used?

> “@ContextConfiguration defines class-level metadata that is used to determine how to load and configure an ApplicationContext for integration tests.“

@ContextConfiguration 有以下可选的元素：

| Optional Element Name | Description                                                  |
| --------------------- | ------------------------------------------------------------ |
| classes               | @Configuration classes to create application context from. Default: {} |
| inheritInitializers   | Whether initializers from test superclasses should be inherited. Default: true |
| inheritLocations      | Whether locations or classes (@Configuration) from test superclasses should be inherited.<br/>Default: true |
| initializers          | Classes implementing the ApplicationContextInitializer interface that will be invoked to initialize the application context.<br/>Default: {} |
| loader                | Classes implementing the ContextLoader interface that will be used to load the application context. |
| locations             | Locations of XML configuration files to create application context from. Default: {} |
| name                  | Name of the context hierarchy level represented by this configuration. |
| value                 | Alias for locations.                                         |

- 创建Spring应用程序上下文的配置可以是@Configuration类（classes元素）或XML配置文件（locations元素）。
- 可以通过指定实现ApplicationContextInitializer接口的一个或多个类（initializers元素）来自定义应用程序上下文的初始化。
    初始化程序也可以用作指定@Configuration类或XML配置的替代方法。
- 可以通过指定实现ContextLoader接口的一个或多个类来自定义应用程序上下文加载。

## How does Spring Boot simplify writing tests?

```
Spring Boot提供一些简化编写测试的功能包括：
- Spring Boot有一个名为spring-boot-starter-test的入门模块，它添加了以下测试范围的依赖项，这些依赖项在编写测试时很有用：
JUnit，Spring Test， Spring Boot Test，AssertJ，Hamcrest，Mockito，JSONassert和JsonPath。
- Spring Boot提供@MockBean和@SpyBean注释，允许创建Mockito模拟和spy bean并将它们添加到Spring应用程序上下文中。
- Spring Boot提供了一个注释@SpringBootTest，允许运行基于Spring Boot的测试，并提供与Spring TestContext框架相比的其他功能。
- Spring Boot提供@WebMvcTest和@WebFluxTest注解，可以创建仅测试Spring MVC或WebFlux组件的测试，而无需加载整个应用程序上下文。
- 在测试Spring Boot Web应用程序时，提供模拟Web环境或嵌入式服务器（如果需要）。
- Spring Boot有一个名为spring-boot-test-autoconfigure的启动器模块，其中包含许多注释。
例如，可以选择在为测试创建应用程序上下文时选择要加载哪些自动配置类以及哪些不加载，从而避免加载测试的所有自动配置类。
- 自动配置与可在Spring Boot应用程序中使用的多种技术相关的测试。
一些例子是：JPA，JDBC，MongoDB，Neo4J和Redis。
```



## What does @SpringBootTest do? How does it interact with @SpringBootApplication and @SpringBootConfiguration?

```
在注释基于Spring Boot运行测试的测试类时，@SpringBootTest注释提供了以下特殊功能：
1.使用SpringBootContextLoader作为默认的ContextLoader。
前提是没有使用@ContextConfiguration注解指定其他ContextLoader。
2.如果测试类中没有嵌套的@Configuration，并且@SpringBootTest注释中未指定显式的@Configuration类，则搜索@SpringBootConfiguration。
3.允许使用@SpringBootTest注解的properties属性定义自定义环境属性。
4.使用@SpringBootTest注解的webEnvironment元素为不同的Web环境模式提供支持。
可以使用以下Web环境模式：
- DEFINED_PORT（创建Web应用程序上下文而不定义端口），
- MOCK（使用模拟servlet环境创建Web应用程序上下文或响应Web应用程序上下文），
- NONE（创建常规应用程序上下文）， 
- RANDOM_PORT（创建Web应用程序上下文和在随机端口上侦听的常规服务器）。
5.注册TestRestTemplate和/或WebTestClient bean，以便在Web服务器的Web测试中使用。
```

