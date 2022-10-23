# Spring boot in action study notes
- �Զ�����
- ������
- �����н���
- Spring Boot Actuator

## Setup
### install spring-boot-cli

> in windows 10

����: http://repo.spring.io/release/org/springframework/boot/spring-boot-cli/1.3.0.RELEASE/spring-boot-cli-1.3.0.RELEASE-bin.zip

��ѹ�ļ�������� spring-1.3.0.RELEASE\bin ��ϵͳ��������PATH �С�
``` bash
$ spring --version
Spring CLI v1.3.0.RELEASE
```

### using spring initializr
- ͨ��Web����ʹ��: https://start.spring.io/
- ͨ��IntelliJ IDEAʹ�á�

### install apache maven
����: http://maven.apache.org/download.cgi

��ѹ�ļ�������� apache-maven-3.6.1\bin ��ϵͳ��������PATH �С�
``` bash
$ mvn -v
Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-05T03:00:29+08:00)
Maven home: C:\Java\apache-maven-3.6.1
Java version: 1.8.0_211, vendor: Oracle Corporation, runtime: C:\Java\jdk1.8.0_211\jre
Default locale: zh_CN, platform encoding: GBK
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```
> �� IDEA ����̨�޷�ʶ�� mvn ָ�����Ҫ�Թ���ԱȨ������ IDEA ��

## �Զ������е�������ע��
| ������ע��                      | ������Ч����                                                 |
| ------------------------------- | ------------------------------------------------------------ |
| @ConditionalOnBean              | ������ĳ���ض�Bean                                           |
| @ConditionalOnMissingBean       | û�������ض���Bean                                           |
| @ConditionalOnClass             | Classpath����ָ������                                        |
| @ConditionalOnMissingClass      | Classpath��ȱ��ָ������                                      |
| @ConditionalOnExpression        | ������Spring Expression Language��SpEL�����ʽ������Ϊtrue |
| @ConditionalOnJava              | Java�İ汾ƥ���ض�ֵ����һ����Χֵ                           |
| @ConditionalOnJndi              | �����и�����JNDIλ�ñ������һ�������û�и���������Ҫ��JNDI InitialContext |
| @ConditionalOnProperty          | ָ������������Ҫ��һ����ȷ��ֵ                               |
| @ConditionalOnResource          | Classpath����ָ������Դ                                      |
| @ConditionalOnWebApplication    | ����һ��WebӦ�ó���                                          |
| @ConditionalOnNotWebApplication | �ⲻ��һ��WebӦ�ó���                                        |

## �Զ������ã������Զ����ã�
��� Spring Security
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
��������Ҫ��¼��
```
username: user
Using generated security password: 35aee3d6-ca16-4cde-978a-b986a6422f89
```
��д WebSecurityConfigurerAdapter ���ַ���
``` java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {}
}
```

### ���� Bean

### �����ⲿ�ļ�����
Spring Boot�ܴӶ�������Դ�������(���ԽС�����ȼ�Խ��)��
```
(1) �����в���
(2) java:comp/env���JNDI����
(3) JVMϵͳ����
(4) ����ϵͳ��������
(5) ������ɵĴ�random.*ǰ׺�����ԣ���������������ʱ�������������ǣ�����${random.long}��
(6) Ӧ�ó��������application.properties����appliaction.yml�ļ�
(7) �����Ӧ�ó����ڵ�application.properties����appliaction.yml�ļ�
(8) ͨ��@PropertySource��ע������Դ
(9) Ĭ������
```

application.properties��application.yml�ļ��ܷ��������ĸ�λ��(���ԽС�����ȼ�Խ��)��
```
(1) ���ã��������Ӧ�ó�������Ŀ¼��/config��Ŀ¼�
(2) ���ã���Ӧ�ó������е�Ŀ¼�
(3) ���ã���config���ڡ�
(4) ���ã���Classpath��Ŀ¼��
```

1. ����ģ��cache
``` yml
# spring.thymeleaf.cache = false
spring.thymeleaf.cache��Thymeleaf��
spring.freemarker.cache��Freemarker��
spring.groovy.template.cache��Groovyģ�壩
spring.velocity.cache��Velocity��
```
����ģ�� cache ���ڼӿ쿪�������ٶȡ�

2. ����Ƕ��ʽ������
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
���� https://localhost:8443 ���ɿ�������ȫ�� HTTPS ���Ӿ��档

3. ������־
Spring Boot Ĭ��ʹ�� Logback��http://logback.qos.ch��INFO���� ����¼��־��
�������ʹ��Logback����༭ src/main/resources/logback.xml��

�������ʹ��Log4j2����Ҫ�ų�Logback�������Ӧ��־ʵ�ֵ�����������
- �ų� Logback
```
mvn dependency:tree --> tree.txt
```
��������ָ���������Ŀ�����������ҳ�logback������Դ����ʹ������������ pom.xml �ų���
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
- ���� log4j2
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```
�༭��Ӧ�� src/main/resources/log4j2.xml��

4. ��������Դ
``` yml
spring:
  datasource:
    url: jdbc:mysql://...
    username: username
    password: password
    driver-class-name: com.mysql.jdbc.Driver
```
���Classpath����Tomcat�����ӳ�DataSource���ͻ�ʹ��������ӳأ�
����Spring Boot����Classpath������������ӳأ�
- HikariCP
- Commons DBCP
- Commons DBCP 2

5. ��ȡ�Զ���yml���ñ���
�½� AmazonProperties.java
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
�� AmazonProperties ��װ��Ϊ bean ʱ����ʹ�� yml �������г�ʼ����

6. �� profile yml�����ļ�
�� Profile �޹ص����Է��� application.yml���� profile �йصķ��� application-{profile}.yml��

### ���� Error ҳ��
ȡ���ڴ�����ͼ����ʱ����ͼ��������
- ʵ����Spring��View�ӿڵ�Bean���� IDΪerror����Spring��BeanNameViewResolver����������
- ���������Thymeleaf��������Ϊerror.html��Thymeleafģ�塣
- ���������FreeMarker��������Ϊerror.ftl��FreeMarkerģ�塣
- ���������Velocity��������Ϊerror.vm��Velocityģ�塣
- �������JSP��ͼ��������Ϊerror.jsp��JSPģ�塣

## ����
### ģ�� Spring MVC
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
���� maven ������
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
### REST API endpoint
| HTTP���� | · ��           | �� ��                                                        |
| -------- | --------------- | ------------------------------------------------------------ |
| GET      | /autoconfig     | �ṩ��һ���Զ����ñ��棬��¼��Щ�Զ���������ͨ���ˣ���Щûͨ�� |
| GET      | /configprops    | �����������ԣ�����Ĭ��ֵ�����ע��Bean                       |
| GET      | /beans          | ����Ӧ�ó�����������ȫ����Bean���Լ����ǵĹ�ϵ               |
| GET      | /dump           | ��ȡ�̻߳�Ŀ���                                           |
| GET      | /env            | ��ȡȫ����������                                             |
| GET      | /env/{name}     | �������ƻ�ȡ�ض��Ļ�������ֵ                                 |
| GET      | /health         | ����Ӧ�ó���Ľ���ָ�꣬��Щֵ��HealthIndicator��ʵ�����ṩ  |
| GET      | /info           | ��ȡӦ�ó���Ķ�����Ϣ����Щ��Ϣ��info��ͷ�������ṩ         |
| GET      | /mappings       | ����ȫ����URI·�����Լ����ǺͿ�����������Actuator�˵㣩��ӳ���ϵ |
| GET      | /metrics        | �������Ӧ�ó��������Ϣ�������ڴ�������HTTP�������         |
| GET      | /metrics/{name} | ����ָ�����Ƶ�Ӧ�ó������ֵ                                 |
| POST     | /shutdown       | �ر�Ӧ�ó���Ҫ��endpoints.shutdown.enabled����Ϊtrue       |
| GET      | /trace          | �ṩ������HTTP���������Ϣ��ʱ�����HTTPͷ�ȣ�               |

/metrics �˵㱨��Ķ���ֵ�ͼ�����:
| �� ��      | ǰ ׺                                                  | ��������                                                   |
| ---------- | ------------------------------------------------------ | ------------------------------------------------------------ |
| �����ռ��� | gc.*                                                   | �Ѿ��������������ռ��������Լ������ռ����ķѵ�ʱ�䣬�����ڱ��-���������ռ����Ͳ��������ռ����� ����Դ��java.lang.management.GarbageCollectorMXBean�� |
| �ڴ�       | mem.*                                                  | �����Ӧ�ó�����ڴ������Ϳ��е��ڴ�����������Դ��java.lang.Runtime�� |
| ��         | heap.*                                                 | ��ǰ�ڴ�����������Դ��java.lang.management.MemoryUsage��������� classes.* JVM�������������ж�ص��������������Դ��java.lang. management.ClassLoadingMXBean�� |
| ϵͳ       | processors��uptime instance.uptime��systemload.average | ϵͳ��Ϣ�����紦��������������Դ��java.lang.Runtime��������ʱ�䣨����Դ��java.lang.management.RuntimeMXBean����ƽ�����أ�����Դ��java.lang.management.OperatingSystemMXBean�� |
| �̳߳�     | threads.*                                              | �̡߳��ػ��̵߳��������Լ�JVM��������߳�������ֵ������Դ��java.lang .management.ThreadMXBean�� |
| ����Դ     | datasource.*                                           | ����Դ���ӵ�������Դ������Դ��Ԫ���ݣ�����SpringӦ�ó��������������DataSource Bean��ʱ��Ż��������Ϣ�� |
| Tomcat�Ự | httpsessions.*                                         | Tomcat�Ļ�Ծ�Ự�������Ự��������Դ��Ƕ��ʽTomcat��Bean������ʹ��Ƕ��ʽTomcat����������Ӧ�ó���ʱ���������Ϣ�� |
| HTTP       | counter.status.* ��gauge.response.*                     | ����Ӧ�ó������HTTP����Ķ���ֵ�������                    |

### �Զ��彡��ָʾ��
���磬���һ���������ⲿ API �ķ���
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
���� /health �˵㼴�ɼ����Զ���ָʾ����

### ����ĳЩ�˵�
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

## Spring Boot �����߹���
- �Զ���������Classpath����ļ������仯ʱ���Զ����������е�Ӧ�ó���
- LiveReload֧�֣�����Դ���޸��Զ����������ˢ�¡�
- Զ�̿�����Զ�̲���ʱ֧���Զ�������LiveReload��
- Ĭ�ϵĿ���ʱ����ֵ��ΪһЩ�����ṩ�������Ĭ�Ͽ���ʱ����ֵ��

��� maven ����:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```
IDEA ������Ҫ����������:
1. File -> Settings -> Compiler: [?] Build project automatically
2. Ctrl + Shift + Alt + / �� Maintenance : [?] compiler.automake.allow.when.app.running 

### �Զ�����
����Ĭ�ϵ������ų�Ŀ¼��
``` yml
spring:
  devtools:
    restart:
      enabled: true
      exclude: /static/**,/templates/**
```
ÿ��ִ�� mvn compile ֮������Զ��������¡�

> ����ϣ��Զ������Ȳ��� JRebel ������ã���Ϊ JRebel һ�㲻��Ҫ������

### LiveReload
``` yml
spring:
  devtools:
    livereload:
      enabled: true
```      
��ʹ�� Chrome �����������Ҫ��Ӧ���̵갲װ LiveReload ��������á�

���޸� src\main\resources\templates\ �µ� Html �ļ���ִ�� mvn compile ������ˢ����������ɿ����޸�Ч����