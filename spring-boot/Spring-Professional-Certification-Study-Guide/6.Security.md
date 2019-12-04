# Security

## What are authentication and authorization? Which must come first?

```
1.authentication：验证
在Spring Security中，身份验证过程包含以下步骤：
- 获取用户名和密码，将其合并到UsernamePasswordAuthenticationToken（Authentication接口的实例）的实例中。
- Token传递给AuthenticationManager实例进行验证。
- AuthenticationManager在成功验证后返回完全填充的Authentication实例。
- 通过调用SecurityContextHolder.getContext().setAuthentication(...)建立安全上下文，传入返回的身份验证对象。

2.authorization: 授权

先验证，再授权。
```



## Is security a cross cutting concern? How is it implemented internally?

```
是，安全是一个跨领域（横切关注）的问题。
安全性是应用程序的一个功能，不应该与业务逻辑直接关联。

实现原理：
Spring Security按以下两种方式实现，取决于要保护的内容： 
- 使用继承自AbstractSecurityInterceptor类的Spring AOP代理。
应用于使用Spring Security保护的对象的方法调用授权。
- Spring Security的Web基础结构完全基于servlet过滤器。
```

- Spring Security Web Infrastructure

![spring-security-web-infrastructure-overview](assets\conf-lifecycle.png)

1. 配置DelegatingFilterProxy类型的servlet过滤器。

2. DelegatingFilterProxy委托给FilterChainProxy。FilterChainProxy被定义为Spring bean，并将一个或多个SecurityFilterChain实例作为构造函数参数。

3. SecurityFilterChain将请求URL模式与（安全）过滤器列表相关联。 

- Spring Security核心组件

| Component Type        | Function                                                     |
| --------------------- | ------------------------------------------------------------ |
| SecurityContextHolder | 包含并提供对应用程序的SecurityContext的访问。默认行为是将SecurityContext与当前线程相关联。 |
| SecurityContext       | Spring Security中包含Authentication对象的默认和唯一的实现。还可以包含其他特定于请求的信息。 |
| Authentication        | 在授予请求后，表示身份验证请求或经过身份验证的主体的令牌。还包含应用程序中已授予经过身份验证的主体的权限。 |
| GrantedAuthority      | 表示授予经过身份验证的主体的权限。                           |
| UserDetails           | 保存用户信息，例如用户名，密码和用户权限。此信息用于在成功验证时创建Authentication对象。可以扩展为包含特定于应用程序的用户信息。 |
| UserDetailsService    | 给定用户名，此服务将检索有关用户的信息到一个UserDetails对象中。根据所使用的用户详细信息服务的实现，如果使用自定义实现，则可以将信息存储在数据库，内存或其他地方。 |

![](assets\Spring-Security-5-core-components-and-their-relationships.PNG)   

## What is the delegating filter proxy?

```
DelegatingFilterProxy类实现了javax.servlet.Filter接口，因此是一个servlet过滤器。

一个委托过滤器代理，委托给一个实现javax.servlet.Filter接口所需的Spring bean。
这种机制允许在web.xml中定义servlet过滤器（对于Servlet 2标准），后者从Spring应用程序上下文中查找命名bean并将过滤委托给Spring bean。

委托过滤器代理委托给的Spring bean，像一般Spring bean一样，生命周期由Spring容器处理。
默认情况下不会调用servlet过滤器生命周期方法init和destroy。
通过将targetFilterLifecycle属性设置为true，可以将委托过滤器代理配置为：在委托过滤器代理上调用相应方法时，会在Spring bean上调用这些方法。
```



## What is the security filter chain?

```
SecurityFilterChain将请求URL模式与（安全）过滤器列表相关联。

安全过滤器链有两个部分:请求匹配器和过滤器。
- Request Matcher
最常见的两个是MvcRequestMatcher和AntPathRequestMatcher。
- Filters
DefaultSecurityFilterChain类的构造函数采用可变数量的参数，第一个始终是请求匹配器。
其余参数是实现javax.servlet.Filter接口的所有过滤器。
安全过滤器链中过滤器的顺序很重要 - 必须按以下顺序声明过滤器（如果不需要，可以省略过滤器）：
• ChannelProcessingFilter 
• SecurityContextPersistenceFilter
• ConcurrentSessionFilter
• Any authentication filter.
  Such as UsernamePasswordAuthenticationFilter, CasAuthenticationFilter, BasicAuthenticationFilter.
• SecurityContextHolderAwareRequestFilter
• JaasApiIntegrationFilter
• RememberMeAuthenticationFilter 
• AnonymousAuthenticationFilter 
• ExceptionTranslationFilter 
• FilterSecurityInterceptor
```

## What is a security context?

```
实现SecurityContext接口的对象存储在SecurityContextHolder的实例中。
SecurityContextHolder类不仅保留对安全上下文的引用，还允许指定用于存储安全上下文的策略：
- 本地线程
安全上下文存储在线程局部变量中，仅可用于一个单个线程执行。
- 可继承的本地线程
跟上面的本地线程一样，额外地，由 包含对安全上下文的引用的线程局部变量的线程 创建的子线程 也将具有一个threadlocal变量，包含对相同安全上下文的引用。
- Global
在整个应用程序中，可以从任何线程使用安全上下文。

Authentication接口定义表示以下安全令牌的对象的属性：
• 一个身份验证请求
在用户已经通过身份验证之前（当用户尝试登录时）。
• 经过身份验证的主体
在用户通过身份验证管理器进行身份验证之后。

实现Authentication接口的对象中包含的基本属性包括：
• 授予主体的权限的集合。
• 用于验证用户身份的凭据。
可以是已经过验证匹配的登录名和密码。
• 详细信息
附加信息可能是特定于应用程序的，如果不使用则为null。
• Principal
• Authenticated flag
一个布尔值，指示主体是否已成功通过身份验证。
```



## What does the ** pattern in an antMatcher or mvcMatcher do?

```
可以在URL模式中使用两个通配符： 
• * 匹配通配符发生的级别上的任何路径。
示例：/services/* 匹配 /services/users 但不匹配/services/orders/123/。
• ** 匹配通配符级别上的任何路径以及下面的所有级别。
示例：/services/** 匹配 /services，/services/，/services/users,/services/orders 以及/ services/orders/123/等。
```



## Why is the usage of mvcMatcher recommended over antMatcher?

```
1.mvcMatcher 更加宽容，容错率更好。
antMatchers("/services") 只精确匹配 “/services”URL；
mvcMatchers("/services") 匹配 “/services”或 “/services/” 或 “/services.html” 或
“/services.abc”。
2.mvcMatchers API使用与@RequestMapping批注相同的匹配规则。
3.mvcMatchers API比antMatchers API更新。
```



## Does Spring Security support password hashing? What is salting?

```
password hashing:
- 密码哈希是计算密码哈希值的过程。
- 在Spring Security中，此过程称为密码编码，使用PasswordEncoder接口实现。

Salting:
- 在计算密码的哈希值时使用的盐是随机字节序列，它与明文密码结合使用以计算哈希值。
- salt与密码哈希值一起以明文形式存储，之后可在计算登录时用户提供的密码的哈希值时使用。
- salting的原因是为了避免始终为某个单词使用相同的哈希值，这样可以更容易地使用哈希值字典及其相应的密码来猜测密码。
```



## Why do you need method security? What type of object is typically secured at the method level (think of its purpose not its Java type).

```
除了使用servlet过滤器为Web资源提供安全性，Spring Security还支持方法级别的安全性，可以将安全性约束应用于Spring bean中的各个方法。
需要使用常规Spring应用程序中的@EnableGlobalMethodSecurity注解或响应式Spring应用程序中的@EnableReactiveMethodSecurity注解来显式启用方法级别的安全性。

方法安全性是Web应用程序中的附加安全级别，但也可以是不公开Web界面的应用程序中的唯一安全层。
方法级安全性通常应用于应用程序的服务层中的services。
```



## What do @PreAuthorized and @RolesAllowed do? What is the difference between them?

```
@PreAuthorize和@RolesAllowed注释，可以在单个方法或类级别上配置方法安全性。在后一种情况下，安全性约束将应用于类中的所有方法。

- @PreAuthorize  
@PreAuthorize注释允许使用SpEL为方法指定访问约束。在执行方法之前评估这些约束，如果不满足约束，则拒绝执行该方法。
为了启用@PreAuthorize，需要将@EnableGlobalMethodSecurity注解中的prePostEnabled属性设置为true：
@EnableGlobalMethodSecurity(prePostEnabled=true)
   
- @RolesAllowed  
@RolesAllowed注释源于JSR-250 Java安全标准。此注释比@PreAuthorize注释更受限制，因为它仅支持基于角色的安全性。
为了使用@RolesAllowed注释，包含此注释的库需要位于类路径上，因为它不是Spring Security的一部分。
此外，@EnableGlobalMethodSecurity注解的jsr250Enabled属性需要设置为true：
@EnableGlobalMethodSecurity(jsr250Enabled=true)   
```



### How are these annotations implemented?

```
使用Spring AOP代理完成方法级安全性。
```



### In which security annotation are you allowed to use SpEL?

下表显示了可以与Spring Security 5一起使用的安全注释中对Spring Expression Language的支持：

| Security Annotation | Has SpEL Support? |
| ------------------- | ----------------- |
| @PreAuthorize       | Y                 |
| @PostAuthorize      | Y                 |
| @PreFilter          | Y                 |
| @PostFilter         | Y                 |
| @Secured            | N                 |
| @RolesAllowed       | N                 |