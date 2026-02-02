# IOC设计原理

IOC，简称控制反转，即转移控制权：你的控制权 -> IOC容器

## IOC的使用过程
1. IOC容器管理外部资源（组件）
2. 应用程序对象请求外部资源
3. IOC将外部资源注入到需要该资源的应用程序对象

参考Spring IOC的使用设计
```java
// App表示你的应用程序对象
public class App {

    // IOC 注入需要的AService类型的资源
    @Autowired
    private AService aService ;
}

// AService表示IOC托管的外部资源
@Service("AService")
public class AService {
}
```

## IOC容器的作用
- 管理对象：保存，删除，获取对象
- 支持注解注入

## IOC的实现
IOC的实现本质上是利用了Java类加载机制和反射机制。

### 类加载机制
Class文件由类装载器装载后，在JVM中形成一份描述Class结构的元信息对象，通过该元信息对象可以获知Class的结构信息：如构造函数、属性和方法等。
换言之，可以通过Java反射机制获取JVM中类的运行时描述信息。

```java
package io.github.wdpm.ioc;

import io.github.wdpm.ioc.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
  public static void main(String[] args) {
    try {
      // AppClassLoader
      ClassLoader loader = Thread.currentThread()
                                 .getContextClassLoader();
      Class<?> clazz = loader.loadClass("io.github.wdpm.ioc.model.User");

      // get non-args constructor
      Constructor<?> cons = clazz.getDeclaredConstructor((Class[]) null);
      User           user = (User) cons.newInstance();

      Method setName = clazz.getMethod("setName", String.class);
      setName.invoke(user, "abc");

      user.printName();
    } catch (ClassNotFoundException | NoSuchMethodException |
             InstantiationException | IllegalAccessException |
             InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
```
User 类
```java
package io.github.wdpm.ioc.model;


public class User {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String sayOK() {
        return "OK";
    }

    public void printName() {
        System.out.println(this.name);
    }
}
```
![](./images/class-loader-and-jvm.png)

每一个类在JVM中都拥有一个对应的java.lang.Class对象。Class对象是由JVM通过调用类装载器中的defineClass()方法自动构造的。

### 反射机制
- Constructor：类的构造函数反射类，通过Class#getConstructors()方法获得类的所有构造函数反射对象数组Constructor<?>[]。
- Method：类方法的反射类，通过Class#getDeclaredMethods()方法获取类的所有方法反射对象数组Method[]。
- Field：类的成员变量的反射类，通过Class#getDeclaredFields()方法获取类的成员变量反射对象数组Field[]
  - 通过Class#getDeclaredField(String name)可获取某个特定名称的成员变量反射对象。
  - Field类最主要的方法是set(Object obj, Object value)，obj表示操作的目标对象，通过value为目标对象的成员变量设置值。
  如果成员变量为基础类型，可以使用Field类中提供的带类型名的值设置方法，如setBoolean(Object obj, boolean value)等。
  - 在访问private、protected成员变量和方法时必须通过setAccessible(boolean access)方法取消Java语言检查。

下面实现容器。

Autowired标注类 `io/github/wdpm/ioc/annotation/Autowired.java`
```java
package io.github.wdpm.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

    /**
     * @return injected class type
     */
    Class<?> value() default Class.class;

    /**
     * @return bean name
     */
    String name() default "";

}
```

`io/github/wdpm/ioc/model/Location.java`
```java
package io.github.wdpm.ioc.model;


public class Location {

    public String country;

    public String city;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
```

`io/github/wdpm/ioc/util/ReflectUtil.java`
```java
package io.github.wdpm.ioc.util;


public class ReflectUtil {
    private ReflectUtil() {}

    public static Object newInstance(String className) {
        Object obj = null;
        try {
            obj = Class.forName(className).newInstance();
        } catch (Exception e) {}
        return obj;
    }
}
```

容器接口类 `io/github/wdpm/ioc/Container.java`
```java
package io.github.wdpm.ioc;

import java.util.Set;


interface Container {

    /**
     * get bean by clazz
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> clazz);

    /**
     * get bean by name
     *
     * @param name
     * @param <T>
     * @return
     */
    <T> T getBeanByName(String name);

    /**
     * register clazz as a bean
     *
     * @param clazz
     * @return
     */
    Object registerBean(Class<?> clazz);

    /**
     * register a named bean
     *
     * @param name
     * @param clazz
     * @return
     */
    Object registerBean(String name, Class<?> clazz);

    /**
     * remove bean by clazz
     *
     * @param clazz
     */
    void remove(Class<?> clazz);

    /**
     * remove a bean by name
     *
     * @param name
     */
    void removeByName(String name);

    /**
     * get all beans
     *
     * @return
     */
    Set<String> getBeanNames();

    /**
     * init
     */
    void initWired();

    int getBeanSize();
}
```

容器实现类 `io/github/wdpm/ioc/SimpleContainer.java`
```java
package io.github.wdpm.ioc;

import io.github.wdpm.ioc.annotation.Autowired;
import io.github.wdpm.ioc.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * scope 为 singleton 的 IOC 容器
 */
public class SimpleContainer implements Container {
    // com.xxx.Person : @52x2xa
    private Map<String, Object> beans;

    // name or className  : com.xxx.Person
    private Map<String, String> beanKeys;

    public SimpleContainer() {
        // CHM 保证线程安全
        beans    = new ConcurrentHashMap<>();
        beanKeys = new ConcurrentHashMap<>();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        String clazzName = clazz.getName();
        if (clazzName == null || clazzName.isEmpty()) {
            return null;
        }
        return (T) beans.get(clazzName);
    }

    @Override
    public <T> T getBeanByName(String name) {
        String clazzName = beanKeys.get(name);
        if (clazzName == null || clazzName.isEmpty()) {
            return null;
        }
        return (T) beans.get(clazzName);
    }

    /**
     * 注册 bean。
     *
     * @param clazz
     * @return
     */
    @Override
    public Object registerBean(Class<?> clazz) {
        String clazzName = clazz.getName();
        Object bean;
        // ensure singleton
        if (!beanKeys.containsKey(clazzName)) {
            beanKeys.put(clazzName, clazzName);
            bean = ReflectUtil.newInstance(clazzName);
            beans.put(clazzName, bean);
        } else {
            bean = beans.get(clazzName);
        }
        return bean;
    }

    /**
     * 注册 bean。
     *
     * @param name
     * @param clazz
     * @return
     */
    @Override
    public Object registerBean(String name, Class<?> clazz) {
        String clazzName = clazz.getName();
        Object bean;
        // ensure singleton
        if (!beanKeys.containsKey(name)) {
            beanKeys.put(name, clazzName);
            bean = ReflectUtil.newInstance(clazzName);
            beans.put(clazzName, bean);
        } else {
            String className = beanKeys.get(name);
            bean = beans.get(className);
        }
        return bean;
    }

    @Override
    public void remove(Class<?> clazz) {
        String className = clazz.getName();
        if (null != className && !className.isEmpty()) {
            beanKeys.remove(className);
            beans.remove(className);
        }
    }

    @Override
    public void removeByName(String name) {
        String className = beanKeys.get(name);
        if (null != className && !className.isEmpty()) {
            beanKeys.remove(name);
            beans.remove(className);
        }
    }

    @Override
    public Set<String> getBeanNames() {
        return beanKeys.keySet();
    }

    @Override
    public int getBeanSize() {
        return beanKeys.size();
    }

    /**
     * 将 beans 中的所有 bean 装配
     */
    @Override
    public void initWired() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object object = entry.getValue();
            inject(object);
        }
    }

    /**
     * fields inject
     *
     * @param object
     */
    public void inject(Object object) {
        try {
            // 获取一个类中定义的所有字段
            Field[] fields = object.getClass()
                                   .getDeclaredFields();
            for (Field field : fields) {
                // fields with @Autowired
                Autowired autoWired = field.getAnnotation(Autowired.class);

                if (null != autoWired) {
                    Object autoWiredField = null;
                    String name           = autoWired.name();
                    if (!name.isEmpty()) {
                        String className = beanKeys.get(name);
                        if (null != className && !className.isEmpty()) {
                            autoWiredField = beans.get(className);
                        }
                        // 因为以非空 name 形式注册的 bean，不可能获取不到
                        if (null == autoWiredField) {
                            throw new RuntimeException("Unable to load bean with name: " + name);
                        }
                    } else {
                        if (autoWired.value() == Class.class) {
                            autoWiredField = recursiveAssembly(field.getType());
                        } else {
                            // autoWired.value()!=Class.class, means it has set value
                            autoWiredField = this.getBean(autoWired.value());
                            if (null == autoWiredField) {
                                autoWiredField = recursiveAssembly(autoWired.value());
                            }
                        }
                    }

                    if (null == autoWiredField) {
                        throw new RuntimeException("Unable to load " + field.getType()
                                                                            .getCanonicalName());
                    }

                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    // 将 object 中的这个 field 字段，设置值为 autoWiredField 的值
                    field.set(object, autoWiredField);
                    // recover previous accessibility
                    field.setAccessible(accessible);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object recursiveAssembly(Class<?> clazz) {
        if (null != clazz) {
            return this.registerBean(clazz);
        }
        return null;
    }
}
```

测试类 `io/github/wdpm/ioc/SimpleContainerTest.java`
```java
package io.github.wdpm.ioc;

import io.github.wdpm.ioc.model.Location;
import io.github.wdpm.ioc.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SimpleContainerTest {

    private SimpleContainer sc;

    @BeforeEach
    void setUp() {
        sc = new SimpleContainer();
    }

    @AfterEach
    void tearDown() {
        sc = null;
    }

    @Test
    void getBean() {
        Object bean = sc.registerBean(User.class);
        assertNotNull(sc.getBean(User.class));
    }

    @Test
    void getBeanByName() {
        Object bean = sc.registerBean(User.class);
        User   user = sc.getBeanByName(User.class.getName());
        assertNotNull(user);
    }

    @Test
    void registerBean1() {
        Object bean = sc.registerBean(User.class);
        assertNotNull(bean);
    }

    @Test
    void registerBean2() {
        Object bean = sc.registerBean("user", User.class);
        assertNotNull(bean);
    }

    @Test
    void remove() {
        Object userBean = sc.registerBean(User.class);
        sc.remove(User.class);
        assertNull(sc.getBean(User.class));

        Object locationBean = sc.registerBean("location",Location.class);
        sc.removeByName("location");
        assertNull(sc.getBeanByName("location"));
    }

    @Test
    void removeByName() {
        Object      userBean     = sc.registerBean(User.class);
        Object      locationBean = sc.registerBean(Location.class);
        Set<String> beanNames    = sc.getBeanNames();
        assertTrue(beanNames.contains(userBean.getClass().getName()));
        assertTrue(beanNames.contains(locationBean.getClass().getName()));
    }

    @Test
    void getBeanNames() {
        Object      userBean     = sc.registerBean(User.class);
        Object      locationBean = sc.registerBean(Location.class);
        Set<String> beanNames    = sc.getBeanNames();
        assertTrue(beanNames.contains(userBean.getClass().getName()));
        assertTrue(beanNames.contains(locationBean.getClass().getName()));
    }

    @Test
    void initWired() {
        //
    }

    @Test
    void inject() {
        //
    }

    @Test
    void getBeanSize() {
        Object o = sc.registerBean(User.class);
        assertEquals(1, sc.getBeanSize());
        Object o1 = sc.registerBean(User.class);
        assertEquals(1, sc.getBeanSize());
    }

    @Test
    void singleton1() {
        Object o1 = sc.registerBean(User.class);
        assertNotNull(o1);
        Object o2 = sc.registerBean(User.class);
        assertNotNull(o2);
        assertSame(o1, o2);
    }

    @Test
    void singleton2() {
        Object o1 = sc.registerBean("user", User.class);
        assertNotNull(o1);
        Object o2 = sc.registerBean("user", User.class);
        assertNotNull(o2);
        assertSame(o1, o2);
    }
}
```

项目pom
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>io.github.wdpm</groupId>
    <artifactId>oh-my-ioc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>oh-my-ioc</name>
    <url>https://github.com/wdpm/oh-my-ioc</url>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.6.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```