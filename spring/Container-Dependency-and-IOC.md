# Container, Dependency, and IOC
> Spring Professional Certification Study Guide
> COMPLETED: MAY 2019

容器，依赖，控制反转

## What is dependency injection and what are the advantages?
```
依赖注入：
在程序中按照我们的要求注入两个对象之间的依赖关系。

优点：
1.降低应用中的对象之间的依赖度。
2.每个对象可以使用独立的mock实现来单元测试，易于测试。
3.降低应用耦合度。
4.提高代码重用度，因为每个依赖体现为独立的bean。
5.提高组件的逻辑抽象度。bean在spring中的意义就是组件。

体现方式(补充）：
一般注入类型定义为interface类型，易于解耦。
1.类构造器注入
2.setter方法注入。

Ref：https://www.dineshonjava.com/dependency-injection-in-spring/
```

##  What is an interface and what are the advantages of making use of them in Java?
  ###  Why are they recommended for Spring beans?
```
接口：
Interface是java语言中一种类型，可以定义方法签名，但不实现；可以定义public static final成员属性；不能被实例化。
对于它的用户类，要么去实现（implement）所有声明的方法，要么将自身也声明为抽象类。

优点：
1.实现完全抽象
2.实现多重继承
3.实现松耦合
4.打破复杂设计并降低对象之间的依赖关系。

Ref：https://www.quora.com/What-is-an-interface-and-what-are-the-advantages-of-making-use-of-them-in-Java

在 spring beans 中使用interface可以降低耦合，是一种良好的设计。
```
##  What is meant by "application-context"?
```
应用上下文是spring核心容器之一，它的配置由ApplicationContext接口的具体实现加载。
ApplicationContext用于向应用程序提供配置信息，运行时只读，可以按需重新加载。
```

##  How are you going to create a new instance of an ApplicationContext?

  ```
五种类别：
1.AnnotationConfigApplicationContext：基于Java的配置类加载
2.AnnotationConfigWebApplicationContext：基于Java的配置类加载
3.ClassPathXMLApplicationContext：从类路径的xml文件加载
4.FileSystemXMLApplicationContext：从文件系统中的xml文件加载
5.XMLWebApplicationContext：从Web应用中的xml文件加载（/WEB-INF/...）
  ```

## Can you describe the lifeCycle of a Spring Bean in an ApplicationContext?

  ```
1. Spring instantiates the bean.
spring实例化bean。

2. Spring injects values and bean references into the bean’s properties.
spring注入值和bean引用到bean的属性中。

3. If the bean implements BeanNameAware, Spring passes the bean’s ID to the set- BeanName() method.
若该bean实现了BeanNameAware接口，spring传递bean ID到setBeanName()方法。

4. If the bean implements BeanFactoryAware, Spring calls the setBeanFactory() method, passing in the bean factory itself.
若该bean实现了BeanFactoryAware接口，spring传递这个bean factory参数并调用setBeanFactory方法。

5. If the bean implements ApplicationContextAware, Spring calls the set- ApplicationContext() method, passing in a reference to the enclosing application context.
若该bean实现了ApplicationContextAware接口，spring传递一个到该封闭的应用上下文的引用参数并调用setApplicationContext()方法。

6. If the bean implements the BeanPostProcessor interface, Spring calls its post- ProcessBeforeInitialization() method.
若该bean实现了BeanPostProcessor接口，spring调用postProcessBeforeInitialization()方法。

7. If the bean implements the InitializingBean interface, Spring calls its after- PropertiesSet() method. Similarly, if the bean was declared with an initmethod, then the specified initialization method is called.
若该bean实现了InitializingBean接口，spring调用afterPropertiesSet()方法。如果该bean定义了init方法，该初始化方法也会被调用。

8. If the bean implements BeanPostProcessor, Spring calls its postProcess- AfterInitialization() method.
若该bean实现了BeanPostProcessor，spring在调用AfterInitialization之后会调用postProcess方法。

9. At this point, the bean is ready to be used by the application and remains in the application context until the application context is destroyed.
这时，该bean已经可用，保留在应用上下文中，直到应用上下文被销毁。

10. If the bean implements the DisposableBean interface, Spring calls its destroy() method. Likewise, if the bean was declared with a destroy-method, the specified method is called.
若该bean实现了DisposableBean接口，spring调用它的destroy方法。如果有自定义的销毁方法，那么该销毁方法也会被调用。

总体来讲：实例化；注入依赖属性；如果实现了某些接口，那么就调用对应方法；初始化；销毁。

Ref:https://stackoverflow.com/questions/48670503/life-cycle-of-a-spring-bean
  ```

## How are you going to create an ApplicationContext in an integration test?

  ```java
可通过@ContextConfiguration或者@WebApplicationContext标注。

@RunWith(SpringRunner.class)
// ApplicationContext will be loaded from AppConfig and TestConfig
@ContextConfiguration(classes = {AppConfig.class, TestConfig.class}) 
public class MyTest {
// class body...
}

Ref:http://springcertified.com/2018/12/01/how-are-you-going-to-create-an-applicationcontext-in-an-integration-test-test/
  ```

## What is the preferred way to close an application context? Does Spring Boot do this for you?

  ```java
ContextLoaderListener 初始化/销毁应用上下文。当关闭server时，contextDestroyed被调用，其中会调用closeWebApplicationContext(event.getServletContext());从而调用：
  if ((this.context instanceof ConfigurableWebApplicationContext)) {
      ((ConfigurableWebApplicationContext)this.context).close();
    }
可以看到本质上，也是调用context.close()方法来关闭应用上下文。

Ref:https://stackoverflow.com/questions/29169916/how-correctly-close-the-applicationcontext-in-spring
  ```

  ```java
关于如何关闭应用上下文，也可以调用 static SpringApplication.exit(ApplicationContext, ExitCodeGenerator...) 方法：

ConfigurableApplicationContext ctx = SpringApplication.run(Example.class, args);
// ...determine it's time to stop...
int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
    @Override
    public int getExitCode() {
        // no errors
        return 0;
    }
});
System.exit(exitCode);

Ref：https://stackoverflow.com/questions/22944144/programmatically-shut-down-spring-boot-application
  ```

  TODO: Does Spring Boot do this for you?

## Can you describe:
  ### Dependency injection using Java configuration?

   ```java
  @Configuration
  public class AppConfig {
  
      @Bean("client1")
      public Client getClient1(Service service1) {
          return new ClientImpl(service1);
      }
  
      @Bean("service1")
      public Service getService1() {
          return new ServiceImpl1();
      }
  }
  
  java配置关键在于使用@Configuration和@Bean注解。
  相对于xml配置和注解配置，java配置的类不受Spring注解@Component, @Service, @Autowired的污染，且可以轻松导航。
  
  Ref：https://www.codejava.net/frameworks/spring/spring-dependency-injection-example-with-java-config
   ```

  ### Dependency injection using annotations (@Autowired)?

    ```
    @Autowired on Properties
    @Autowired on Setters
    @Autowired on Constructors
    @Autowired and Optional Dependencies(required = false)
    Autowiring by @Qualifier
    ```

  ### Component scanning, Stereotypes?

    ```
    Stereotype annotation:
    构造型注释: @Component，@Controller，@Service和@Repository注释。
    
    @Component  - 最通用的构造型注释。 其他注释是@Component注释的特化。 当更具体的注释不适合您的目的时，请使用它。
    @Controller  - 将类标记为带注释的控制器。 此类的方法将用于处理传入的http请求。
    @Service  - 将类标记为服务层的成员。
    @Repository  - 将类标记为存储库层的成员。 从此类抛出的所有异常都将被包装并转换为Springs DataAccessException。
    ```
      
    ```
    组件扫描:
    Spring可以自动检测您的构造型类并实例化这些类中定义的bean。 要允许自动检测，需要在@Configuration类上添加@ComponentScan注释。
    
    默认情况下，从定义了@ComponentScan注释的包中搜索组件。 如果要扫描其他包中的组件，可以使用@ComponentScan（“other.package”）定义基本包。
    
    Ref：http://springcertified.com/2018/12/05/can-you-describe-component-scanning-stereotypes-and-meta-annotations/
    ```

  ### Scopes for Spring beans? What is the default scope?

    ```
    1. singleton(default*)
    Scopes a single bean definition to a single object instance per Spring IoC container.
    单例模式：根据Spring IoC容器将单个bean定义范围限定为单个对象实例。
    -> Spring Ioc容器中只会存在一个共享Bean实例。
    
    2. prototype
    Scopes a single bean definition to any number of object instances.
    原型模式：将单个bean定义范围限定为任意数量的对象实例。
    -> 每次请求都会产生一个新的bean实例，new了一个新的对象实例。
    
    3. request
    Scopes a single bean definition to the lifecycle of a single HTTP request; 
    that is each and every HTTP request will have its own instance of a bean created off the back of a single bean definition. 
    Only valid in the context of a web-aware Spring ApplicationContext.
    将单个bean定义范围限定为单个HTTP请求的生命周期; 每个HTTP请求都有自己的bean实例，它是在单个bean定义的后面创建的。 仅在Web感知Spring ApplicationContext的上下文中有效。
    
    4. session
    Scopes a single bean definition to the lifecycle of a HTTP Session. Only valid in the context of a web-aware Spring ApplicationContext.
    将单个bean定义范围限定为HTTP会话的生命周期。仅在Web感知Spring ApplicationContext的上下文中有效。
    
    5. global session
    Scopes a single bean definition to the lifecycle of a global HTTP Session. Typically only valid when used in a portlet context. Only valid in the context of a web-aware Spring ApplicationContext.
    将单个bean定义范围限定为全局HTTP会话的生命周期。 通常仅在portlet上下文中使用时有效。 仅在Web感知Spring ApplicationContext的上下文中有效。
    
    Ref:https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch04s04.html
    ```

## Are beans lazily or eagerly instantiated by default? How do you alter this behavior?

  ```java
默认为eagerly实例化。可通过以下配置改为lazy实例化：

@Bean
@Lazy
public UserService userService() { 
// ...
}

<bean class="com.example.UserService" 
name="userService" 
lazy-init="true" 
/>    
  ```

## What is a property source? How would you use @PropertySource?

  ```java
property source是property的集合。一个property可以看成一个key-value对。
property source一般可通过app.properties文件定义。
key1=value1

属性值可通过Environment object访问:
ApplicationContext ctx = new GenericApplicationContext();
Environment env = ctx.getEnvironment();
String value1 = env.getProperty("key1");

或者通过 @Value 注解访问：
@Value("$key1}")
private String value1;

Property sources的搜索顺序:
1.ServletConfig parameters (if applicable — for example, in case of a DispatcherServlet context)
2.ServletContext parameters (web.xml context-param entries)
3.JNDI environment variables (java:comp/env/ entries)
4.JVM system properties (-D command-line arguments)
5.JVM system environment (operating system environment variables)
  
也可以通过@PropertySource注解使用：
@Configuration
@PropertySource("classpath:app.properties")
public class AppConfig {
    @Autowired
    Environment env;

    @Bean
    public TestBean testBean() {
        TestBean testBean = new TestBean();
        testBean.setName(env.getProperty("testbean.name"));
        return testBean;
    }
}
  ```

##  What is a BeanFactoryPostProcessor and what is it used for? When is it invoked?

  ```
定义：BeanFactoryPostProcessor是在ApplicationContext中注册的一种特殊对象。 
目的：在bean实例化之前改变BeanFactory。 这意味着，BeanFactoryPostProcessor对原始bean定义进行操作，而不是对bean实例进行操作。
调用时机：在bean实例化之前。
  ```

  ### Why would you define a static @Bean method?

  ```java
由于BeanFactoryPostProcessor正在对原始bean定义进行操作，因此必须在从配置加载其他bean定义之后但在实例化之前，对其进行实例化和执行。 Spring内部注册BeanFactoryPostProcessor也作为bean。 为了确保它在这么早的阶段就绪，你应该通过静态bean方法注册它：
    
@Configuration
public class AppConfig {

    @Bean
    public static ApiV2Updater apiV2Updater() {
    	return new ApiV2Updater();
    }
}
    
Ref：http://springcertified.com/2018/12/09/what-is-a-beanfactorypostprocessor-and-what-is-it-used-for-when-is-it-invoked-why-would-you-define-a-static-bean-method/
  ```

  ### What is a ProperySourcesPlaceholderConfigurer used for?

    PropertySourcesPlaceholderConfigurer是BeanFactoryPostProcessor的Springs内部实现。 主要目的是使用从属性源加载的适当值来解析bean定义属性值和@Value注释中的${...}占位符。
    
    Ref: http://springcertified.com/2018/12/10/what-is-a-properysourcesplaceholderconfigurer-used-for/

##  What is a BeanPostProcessor and how is it different to a BeanFactoryPostProcessor? What do they do? When are they called?

```java
BeanPostProcessor是一个接口，包含两个方法。目的是在实例化后，但在它们暴露给应用程序的其余部分之前处理由ApplicationContext创建的bean。 

public interface BeanPostProcessor {
/**
* Method executed after all bean dependencies are set (required and optional),
* but before any initialization callback execution.
*/
Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

/**
* Method executed after all initialization callbacks, just before the bean
* is exposed to the rest of the application by the ApplicationContext
*/
Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}

区别：
BeanFactoryPostProcessor  - 当bean尚未实例化时，对原始bean定义进行操作。
BeanPostProcessor  - 对已经实例化的bean进行操作，所有依赖项（必需和可选）已由ApplicationContext设置。

执行时间：
第一种方法在执行初始化回调之前执行，第二种方法在初始化回调完成之后执行。

Ref:http://springcertified.com/2018/12/20/what-is-a-beanpostprocessor-and-how-is-it-different-to-a-beanfactorypostprocessor-what-do-they-do-when-are-they-called/
```

  ![](assets\conf-lifecycle.png)

### What is an initialization method and how is it declared on a Spring bean?

```java
初始化方法是一种特殊方法，在Bean初始化阶段由ApplicationContext调用。

public UserService {

    public UserService() {
    // normal constructor
    }

    public void doInitialization() { 
    //... perform some init steps 
    }
}

<bean name="userService" 
class="com.example.UserService" 
init-method="doInitialization" 
/>

或者：
@Bean(initMethod = "doInitialization")
public void userService() {
return new UserService();
}
```

### What is a destroy method, how is it declared and when is it called?

```java
如果要在销毁bean时执行一些清理代码，可以配置所谓的destroy方法。

public UserService {

    public UserService() {
    // normal constructor
    }
    
    public void doCleanup() { 
    //... perform some cleanup steps 
    }
}


<bean name="userService" 
class="com.example.UserService" 
destroy-method="doCleanup" 
/>

或者：
@Bean(destroyMethod = "doCleanup")
public void userService() {
return new UserService();
}          
```

### Consider how you enable JSR-250 annotations like @PostConstruct and @PreDestroy? When/how will they get called?

```java
可以使用@PostConstruct和@PreDestroy注释定义初始化和销毁回调。 这些注释不是Spring框架的一部分，但它们直接在Java语言中定义为JSR-250规范请求的一部分。

Spring在CommonAnnotationBeanPostProcessor的帮助下识别这些注释。 这个bean后处理器的目的是找到用@PostConstruct和@PreDestroy注释注释的bean方法，并在ApplicationContext中注册所需的回调。

public class UserService {

public UserService() {}

@PostConstruct
protected void initialize() {
// initialization code
}

@PreDestroy
protected void destroy() {
// destroy code
}
}
      
Ref:http://springcertified.com/2018/12/26/consider-how-you-enable-jsr-250-annotations-like-postconstruct-and-predestroy-when-how-will-they-get-called/
```

### How else can you define an initialization or destruction method for a Spring bean?

```java
之前，我们介绍了初始化和销毁通过init/destroy方法和@PostConstruct/@PreDestroy注释定义的回调。
如何注册初始化和销毁回调的第三个选项是实现InitializingBean和DisposableBean接口。

//Using InitializingBean interface
public class UserService implements InitializingBean {

    public UserService() {

    @Override
    public void afterPropertiesSet() throws Exception {
    // initialization code
    }
}

//Using DisposableBean interface(请记住，在原型范围的bean上不会触发销毁回调。)
public class UserService implements DisposableBean {
      
    public UserService() { }

    @Override
    public void destroy() throws Exception {
    // destroy code        
    }
}

Ref:http://springcertified.com/2018/12/26/how-else-can-you-define-an-initialization-or-destruction-method-for-a-spring-bean/
```

## What does component-scanning do?

```java
组件扫描会自动检测类路径发现的依赖项候选项。 在扫描过程中，Spring会搜索使用构造型注释注释的类。 
这些注释是@Component，@Controller，@Service和@Repository。 类路径扫描与bean自动装配一起可以替换显式XML配置。

//Enabling component scanning in Java configuration
@Configuration
@ComponentScan("com.example")
public class Configuration {...}

//Enabling component scanning in XML configuration
<context:component-scan base-package="com.example"/>

Ref:http://springcertified.com/2018/12/29/what-does-component-scanning-do/
```

## What is the behavior of the annotation @Autowired with regards to field injection,constructor injection and method injection?

```
@Autowired尝试按类型查找匹配的bean并将其注入注释的位置 - 可能是构造函数，方法（不仅是setter，但是通常是setter）和field。

如果没有找到bean，将抛出异常; 要跳过这个，您可以将@Autowired的必需属性设置为false，在这种情况下，您必须注意可能的空值相关的异常。

如果有歧义，有两种方法：
@Primary注释，用于在接口的不同实现中标识“优选”bean
@Qualifier注释直接指示首选bean。
```

## What do you have to do, if you would like to inject something into a private field? How does this impact testing?

  ```
  TODO:注入依赖到私有属性被认为不是一个好主意。需要手动注入依赖到测试？
  ```

## How does the @Qualifier annotation complement the use of @Autowired?

```java
@Component
@Qualifier("cat") 
public Cat implements Animal {}

@Component
@Qualifier("dog") 
public Dog implements Animal {}


@Component
public Test {

	private Animal animal;

    @Autowired
    public test(@Qualifier("cat") Animal animal){
    	this.animal = animal;
    }

}
```

## What is a proxy object and what are the two different types of proxies Spring can create?

  ```java
代理对象是替换应该在单例bean中注入的bean的对象，但它们本身是懒惰的可实例化的。
  
两个类别:
1.interface类型：JDK动态代理
2.class类型：CGLIB
  
Ref:https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch08s06.html
  ```

### What are the limitations of these proxies (per type)?

```
如果要代理的目标对象实现至少一个接口，则将使用JDK动态代理。目标类型实现的所有接口都将被代理。如果目标对象未实现任何接口，则将创建CGLIB代理。

如果要强制使用CGLIB代理（例如，代理为目标对象定义的每个方法，而不仅仅是那些由其接口实现的方法），您可以这样做。但是，有一些问题需要考虑：
- 无法advise final方法，因为它们无法覆盖。
- 您将在类路径上需要CGLIB 2二进制文件，而JDK可以使用动态代理。 Spring会在需要CGLIB时,并且在类路径中找不到CGLIB库类时自动发出警告。
- 代理对象的构造函数将被调用两次。这是CGLIB代理模型的自然结果，其中为每个代理对象生成子类。对于每个代理实例，创建两个对象：实际代理对象和实现advice的子类实例。使用JDK代理时不会出现此行为。通常，两次调用代理类型的构造函数不是问题，因为通常只有赋值发生，并且构造函数中没有实现真正的逻辑。

Ref：https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch08s06.html
```

### What is the power of a proxy object and where are the disadvantages?

```
代理是围绕bean方法添加功能的好方法。 您可以在方法的不同时间定义一些事情，但您也可以通过使用@DeclareParents注释为bean声明新的“parent”来向bean添加新方法。

缺点：
1.代理只能从外部工作...如果在内部你有method1（）调用method2（）而不能拦截对method2（）的调用，因为它不是从bean外部调用的。
2.Proxied对象必须由Spring容器实例化，而不是由new关键字实例化。
3.代理不可序列化。

Ref：https://codingideas.blog/core-spring-4-2-study-guide-answers-part-1-container-dependency-and-ioc#What_is_the_power_of_a_proxy_object_and_where_are_the_disadvantages
```

## What does the @Bean annotation do?

```
将某个方法返回的对象标记为可被spring扫描的通用组件。
```

## What is the default bean id if you only use @Bean? How can you override this?

```java
@Bean
public MyBean myBean() {
    // instantiate and configure MyBean obj
    return obj;
}

默认bean id为方法名，示例中为myBean。可以通过@Bean("yourBeanName")覆盖。

Ref：https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html
```

## Why are you not allowed to annotate a final class with @Configuration？

```
Spring为使用@Configuration类注释的类创建动态代理。 Spring使用CGLIB扩展您的类以创建代理。因此，配置类不能是final的。因为final修饰的类不能被扩展。

Ref:https://stackoverflow.com/questions/29074270/why-in-spring-i-am-not-allowed-to-annotate-a-final-class-with-configuration
```

### How do @Configuration annotated classes support singleton beans?

```
Spring容器通过子类化带@Configuration注释的类来支持单例bean并覆盖类中的@Bean注释方法。 
调用@Bean注释的方法被拦截:
- 如果bean是单例bean而没有实例单例bean存在，允许调用继续到@Bean注释方法，以便创建bean的实例。
- 如果单例bean的实例已经存在，则存在返回实例（并且不允许调用继续到@Bean注释方法）。
```

### Why can’t @Bean methods be final either?

```
TODO:
```

## How do you configure profiles? What are possible use cases where they might be useful?

```
Bean定义配置文件是一种允许根据不同的条件需要注册不同bean的机制。 这些条件的一些例子是：
- 测试和开发
某些bean只能在运行测试时创建。 开发时，内存中将使用数据库，但是在部署时使用常规数据库。
- 性能监控。
- 针对不同市场，客户等的应用程序定制
```

## Can you use @Bean together with @Profile?

```java
@Profile({"dev", "qa"})
@Configuration
public class MyConfigurationClass {..}

表示MyConfigurationClass在dev或qa配置下会激活，注册里面的beans。

@Profile注释可以应用于以下位置：
- 在@Configuration类的类级别。
- 在@Component注释或使用其他等价@Component注释的类中的类级别
- 在@Bean注释的方法。
- 自定义注释中的类型级别。在创建自定义注释时充当元注释。

不属于任何profile的bean始终被创建。

可以使用以下选项之一激活一个或多个配置文件：
- 创建Spring应用程序上下文时，对active配置文件进行编程注册。
ctx.getEnvironment().setActiveProfiles("dev1", "dev2");

- 使用spring.profiles.active属性
java -Dspring.profiles.active=dev1,dev2 -jar app.jar
```

## Can you use @Component together with @Profile?

```
是的。
```

## How many profiles can you have?

```
Spring框架（在类ActiveProfilesUtils中）使用int来迭代激活的配置文件数组，表示最大数量为2^32 -  1个配置文件。
```

## How do you inject scalar/literal values into Spring beans?

```java
使用@Value注解。值可以来自环境变量，属性文件，Spring bean等。

@Component
public class MyBeanClass {
  @Value("${personservice.retry-count:3}")
  protected int personServiceRetryCount;  
示例中personservice.retry-count 默认值为3。

@Component
public class MyBeanClass {
  @Value("#{ T(java.lang.Math).random() * 50.0 }")
  protected double randomNumber;  
#前缀表示为SpEL表达式。

@Value注释可以应用于：
- 属性
- 方法。通常是setter方法
- 构造方法的参数。
- 注释的定义。创建自定义注释。
```

## What is @Value used for?

```
@Value注释可用于：
•设置bean字段，方法参数和构造函数参数的值（默认值）。
•将属性值、环境变量值、其他Spring bean的值，或者评估表达式并将结果注入bean字段，方法参数和构造函数参数。
```

## What is Spring Expression Language (SpEL for short)?

```
Spring Expression Language是一种用于不同Spring产品的表达式语言，不限于在Spring框架中。

SpEL支持：
- 字面量表达式
- 属性，数组，列表和map
- 方法调用
- 操作符
- 变量
- 用户自定义的函数
- 在bean factory引用 spring bean
- 集合选择表达式。例如，#theMap.?[key％2 == 0]
- 集合投影
- 表达式模板
```

## What is the Environment abstraction in Spring?

```
1.非Web Spring应用程序中环境包含配置文件和属性。
非Web应用程序的环境中，有两个默认属性源。 第一个包含JVM系统属性，第二个包含系统环境变量。

2.基于servlet的Spring Web应用程序中环境包含配置文件和属性。
属性包含：JVM系统属性，系统环境变量，servlet配置属性，servlet context 参数，JNDI属性。

StandardEnvironment类是非Web应用程序的基本具体环境实现。 StandardServletEnvironment和StandardReactiveWebEnvironment类都是StandardEnvironment的子类。
```

## Where can properties in the environment come from – there are many sources for

properties – check the documentation if not sure. Spring Boot adds even more.

| Property Source                                  | Environment                |
| ------------------------------------------------ | -------------------------- |
| JVM system properties                            | StandardEnvironment        |
| System environment variables                     | StandardEnvironment        |
| Servlet configuration properties (ServletConfig) | StandardServletEnvironment |
| Servlet context parameters (ServletContext)      | StandardServletEnvironment |
| JNDI properties                                  | StandardServletEnvironment |
| Command line properties                          | n/a                        |
| Application configuration (properties file)      | n/a                        |
| Server ports                                     | n/a                        |
| Management server                                | n/a                        |

## What can you reference using SpEL?

```java
1.Static methods and static properties/fields.
T(se.ivankrizsan.spring.MyBeanClass).myStaticMethod()
T(se.ivankrizsan.spring.MyBeanClass).myClassVariable

2.Properties and methods in Spring beans.
@mySuperComponent.injectedValue
@mySuperComponent.toString()

3.Properties and methods in Java objects.
#javaObject.firstName
#javaObject.firstAndLastName()

4.(JVM) System properties.
@systemProperties['os.name']

5.System environment properties.
@systemEnvironment['KOTLIN_HOME']

6.Spring application environment.
@environment['defaultProfiles'][0]
```

## What is the difference between $ and # in @Value expressions?

```
@Value注释中的表达式有两种类型：
1.表达式以$开头。
此类表达式引用应用程序环境中的属性名称。这些表达式由PropertySourcesPlaceholderConfigurer Spring bean评估创建bean,并且只能在@Value注释中使用。

2.以＃开头的表达式。
Spring表达式语言表达式由SpEL表达式解析器解析并进行评估。
```

