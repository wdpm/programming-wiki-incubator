# Spring boot in action study notes
- 自动配置
- 起步依赖
- 命令行界面
- Spring Boot Actuator

## Setup
### install spring-boot-cli

> in windows 10

下载: http://repo.spring.io/release/org/springframework/boot/spring-boot-cli/1.3.0.RELEASE/spring-boot-cli-1.3.0.RELEASE-bin.zip

解压文件，并添加 spring-1.3.0.RELEASE\bin 到系统环境变量PATH 中。
``` bash
$ spring --version
Spring CLI v1.3.0.RELEASE
```

### using spring initializr
- 通过Web界面使用: https://start.spring.io/
- 通过IntelliJ IDEA使用。

### install apache maven
下载: http://maven.apache.org/download.cgi

解压文件，并添加 apache-maven-3.6.1\bin 到系统环境变量PATH 中。
``` bash
$ mvn -v
Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-05T03:00:29+08:00)
Maven home: C:\Java\apache-maven-3.6.1
Java version: 1.8.0_211, vendor: Oracle Corporation, runtime: C:\Java\jdk1.8.0_211\jre
Default locale: zh_CN, platform encoding: GBK
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```
> 若 IDEA 控制台无法识别 mvn 指令，则需要以管理员权限重启 IDEA 。

## 自动配置中的条件化注解
| 条件化注解                      | 配置生效条件                                                 |
| ------------------------------- | ------------------------------------------------------------ |
| @ConditionalOnBean              | 配置了某个特定Bean                                           |
| @ConditionalOnMissingBean       | 没有配置特定的Bean                                           |
| @ConditionalOnClass             | Classpath里有指定的类                                        |
| @ConditionalOnMissingClass      | Classpath里缺少指定的类                                      |
| @ConditionalOnExpression        | 给定的Spring Expression Language（SpEL）表达式计算结果为true |
| @ConditionalOnJava              | Java的版本匹配特定值或者一个范围值                           |
| @ConditionalOnJndi              | 参数中给定的JNDI位置必须存在一个，如果没有给参数，则要有JNDI InitialContext |
| @ConditionalOnProperty          | 指定的配置属性要有一个明确的值                               |
| @ConditionalOnResource          | Classpath里有指定的资源                                      |
| @ConditionalOnWebApplication    | 这是一个Web应用程序                                          |
| @ConditionalOnNotWebApplication | 这不是一个Web应用程序                                        |

## 自定义配置（覆盖自动配置）
添加 Spring Security
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
重启后需要登录：
```
username: user
Using generated security password: 35aee3d6-ca16-4cde-978a-b986a6422f89
```
重写 WebSecurityConfigurerAdapter 部分方法
``` java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {}
}
```

### 配置 Bean

### 配置外部文件属性
Spring Boot能从多种属性源获得属性(序号越小，优先级越高)：
```
(1) 命令行参数
(2) java:comp/env里的JNDI属性
(3) JVM系统属性
(4) 操作系统环境变量
(5) 随机生成的带random.*前缀的属性（在设置其他属性时，可以引用它们，比如${random.long}）
(6) 应用程序以外的application.properties或者appliaction.yml文件
(7) 打包在应用程序内的application.properties或者appliaction.yml文件
(8) 通过@PropertySource标注的属性源
(9) 默认属性
```

application.properties和application.yml文件能放在以下四个位置(序号越小，优先级越高)：
```
(1) 外置，在相对于应用程序运行目录的/config子目录里。
(2) 外置，在应用程序运行的目录里。
(3) 内置，在config包内。
(4) 内置，在Classpath根目录。
```

1. 禁用模板cache
``` yml
# spring.thymeleaf.cache = false
spring.thymeleaf.cache（Thymeleaf）
spring.freemarker.cache（Freemarker）
spring.groovy.template.cache（Groovy模板）
spring.velocity.cache（Velocity）
```
禁用模板 cache 利于加快开发调试速度。

2. 配置嵌入式服务器
``` bash
keytool -keystore mykeys.jks -genkey -alias tomcat -keyalg RSA
```
``` yml
server:
  port: 8443
  ssl:
    key-store: C:\IdeaProjects\demo\src\main\resources\mykeys.jks
    key-store-password: letmein
    key-password: letmein
```
访问 https://localhost:8443 即可看到不安全的 HTTPS 连接警告。

3. 配置日志
Spring Boot 默认使用 Logback（http://logback.qos.ch）INFO级别 来记录日志。
如果决定使用Logback，则编辑 src/main/resources/logback.xml。

如果决定使用Log4j2，需要排除Logback，引入对应日志实现的起步依赖，。
- 排除 Logback
```
mvn dependency:tree --> tree.txt
```
根据上述指令分析该项目所有依赖，找出logback的引出源，并使用以下配置在 pom.xml 排除：
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
- 引出 log4j2
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```
编辑对应的 src/main/resources/log4j2.xml。

4. 配置数据源
``` yml
spring:
  datasource:
    url: jdbc:mysql://...
    username: username
    password: password
    driver-class-name: com.mysql.jdbc.Driver
```
如果Classpath里有Tomcat的连接池DataSource，就会使用这个连接池；
否则，Spring Boot会在Classpath里查找以下连接池：
- HikariCP
- Commons DBCP
- Commons DBCP 2

5. 读取自定义yml配置变量
新建 AmazonProperties.java
``` java
@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {
    private String associateId;

    public String getAssociateId() {
        return associateId;
    }

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }
}
```
```yml
amazon:
  associateId: wdpm
```
当 AmazonProperties 被装载为 bean 时，会使用 yml 变量进行初始化。

6. 多 profile yml配置文件
与 Profile 无关的属性放在 application.yml，与 profile 有关的放于 application-{profile}.yml。

### 定制 Error 页面
取决于错误视图解析时的视图解析器。
- 实现了Spring的View接口的Bean，其 ID为error（由Spring的BeanNameViewResolver所解析）。
- 如果配置了Thymeleaf，则有名为error.html的Thymeleaf模板。
- 如果配置了FreeMarker，则有名为error.ftl的FreeMarker模板。
- 如果配置了Velocity，则有名为error.vm的Velocity模板。
- 如果是用JSP视图，则有名为error.jsp的JSP模板。

## 测试
### 模拟 Spring MVC
``` java
// ascii code sorting
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MockMvcWebTests {

    @Autowired
    WebApplicationContext webContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
//        .apply(springSecurity())
                .build();
    }

    // disable spring-security
    @Test
    public void homePage_unauthenticatedUser() throws Exception {
        mockMvc.perform(get("https://localhost:8443/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/readingList"));
    }

    @Test
    public void initReadingListPage() throws Exception {
        mockMvc.perform(get("https://localhost:8443/readingList"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    public void postBook() throws Exception {
        // mock submit form
        mockMvc.perform(post("/readingList")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "BOOK TITLE")
                .param("author", "BOOK AUTHOR")
                .param("isbn", "1234567890")
                .param("description", "DESCRIPTION"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/readingList"));

        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setReader("readingList");
        expectedBook.setTitle("BOOK TITLE");
        expectedBook.setAuthor("BOOK AUTHOR");
        expectedBook.setIsbn("1234567890");
        expectedBook.setDescription("DESCRIPTION");

        // compare
        mockMvc.perform(get("/readingList"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books",
                        contains(samePropertyValuesAs(expectedBook))));
    }
}
```

## Spring Boot Actuator
引入 maven 依赖：
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
### REST API endpoint
| HTTP方法 | 路 径           | 描 述                                                        |
| -------- | --------------- | ------------------------------------------------------------ |
| GET      | /autoconfig     | 提供了一份自动配置报告，记录哪些自动配置条件通过了，哪些没通过 |
| GET      | /configprops    | 描述配置属性（包含默认值）如何注入Bean                       |
| GET      | /beans          | 描述应用程序上下文里全部的Bean，以及它们的关系               |
| GET      | /dump           | 获取线程活动的快照                                           |
| GET      | /env            | 获取全部环境属性                                             |
| GET      | /env/{name}     | 根据名称获取特定的环境属性值                                 |
| GET      | /health         | 报告应用程序的健康指标，这些值由HealthIndicator的实现类提供  |
| GET      | /info           | 获取应用程序的定制信息，这些信息由info打头的属性提供         |
| GET      | /mappings       | 描述全部的URI路径，以及它们和控制器（包含Actuator端点）的映射关系 |
| GET      | /metrics        | 报告各种应用程序度量信息，比如内存用量和HTTP请求计数         |
| GET      | /metrics/{name} | 报告指定名称的应用程序度量值                                 |
| POST     | /shutdown       | 关闭应用程序，要求endpoints.shutdown.enabled设置为true       |
| GET      | /trace          | 提供基本的HTTP请求跟踪信息（时间戳、HTTP头等）               |

/metrics 端点报告的度量值和计数器:
| 分 类      | 前 缀                                                  | 报告内容                                                   |
| ---------- | ------------------------------------------------------ | ------------------------------------------------------------ |
| 垃圾收集器 | gc.*                                                   | 已经发生过的垃圾收集次数，以及垃圾收集所耗费的时间，适用于标记-清理垃圾收集器和并行垃圾收集器（ 数据源自java.lang.management.GarbageCollectorMXBean） |
| 内存       | mem.*                                                  | 分配给应用程序的内存数量和空闲的内存数量（数据源自java.lang.Runtime） |
| 堆         | heap.*                                                 | 当前内存用量（数据源自java.lang.management.MemoryUsage）类加载器 classes.* JVM类加载器加载与卸载的类的数量（数据源自java.lang. management.ClassLoadingMXBean） |
| 系统       | processors、uptime instance.uptime、systemload.average | 系统信息，例如处理器数量（数据源自java.lang.Runtime）、运行时间（数据源自java.lang.management.RuntimeMXBean）、平均负载（数据源自java.lang.management.OperatingSystemMXBean） |
| 线程池     | threads.*                                              | 线程、守护线程的数量，以及JVM启动后的线程数量峰值（数据源自java.lang .management.ThreadMXBean） |
| 数据源     | datasource.*                                           | 数据源连接的数量（源自数据源的元数据，仅当Spring应用程序上下文里存在DataSource Bean的时候才会有这个信息） |
| Tomcat会话 | httpsessions.*                                         | Tomcat的活跃会话数和最大会话数（数据源自嵌入式Tomcat的Bean，仅在使用嵌入式Tomcat服务器运行应用程序时才有这个信息） |
| HTTP       | counter.status.* 、gauge.response.*                     | 多种应用程序服务HTTP请求的度量值与计数器                    |

### 自定义健康指示器
例如，监测一个依赖于外部 API 的服务。
``` java
@Component
public class AmazonHealth implements HealthIndicator {

  @Override
  public Health health() {
    
    try {
      RestTemplate rest = new RestTemplate();
      rest.getForObject("http://www.amazon.com", String.class);
      return Health.up().build();
    } catch (Exception e) {
      return Health.down().withDetail("reason", e.getMessage()).build();
    }    
  }
  
}
```
访问 /health 端点即可见到自定义指示器。

### 保护某些端点
``` java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ReaderRepository readerRepository;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/mgmt/**").access("hasRole('ADMIN')")
      ...  
  }
  
  @Override
  protected void configure(
              AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(new UserDetailsService() {
        ...
      })
      .and()
      .inMemoryAuthentication()
        .withUser("manager").password("123456").roles("ADMIN");
  }

}
```

## Spring Boot 开发者工具
- 自动重启：当Classpath里的文件发生变化时，自动重启运行中的应用程序。
- LiveReload支持：对资源的修改自动触发浏览器刷新。
- 远程开发：远程部署时支持自动重启和LiveReload。
- 默认的开发时属性值：为一些属性提供有意义的默认开发时属性值。

添加 maven 依赖:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```
IDEA 可能需要开启的设置:
1. File -> Settings -> Compiler: [?] Build project automatically
2. Ctrl + Shift + Alt + / 打开 Maintenance : [?] compiler.automake.allow.when.app.running 

### 自动重启
覆盖默认的重启排除目录：
``` yml
spring:
  devtools:
    restart:
      enabled: true
      exclude: /static/**,/templates/**
```
每次执行 mvn compile 之后则会自动重启更新。

> 这点上，自动重启比不上 JRebel 插件好用，因为 JRebel 一般不需要重启。

### LiveReload
``` yml
spring:
  devtools:
    livereload:
      enabled: true
```      
若使用 Chrome 浏览器，则需要到应用商店安装 LiveReload 插件并启用。

当修改 src\main\resources\templates\ 下的 Html 文件后，执行 mvn compile 后无需刷新浏览器即可看到修改效果。