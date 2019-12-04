# REST

## What does REST stand for?

```
REST代表REpresentational State Transfer(表述性状态转移），是一种架构风格，允许用户在给定一组约束的情况下访问和操作Web资源的文本表示。
REST可扩展性，简单性，可移植性和可靠性较好。
```



## What is a resource?

```
可以命名的任何信息都可以是资源：
- 文档或图像，
- 临时服务（例如“XXX地方的今天天气”），
- 其他资源的集合，
- 非虚拟对象（例如人）等等。

任何可能是作者超文本引用目标的概念都必须符合资源的定义。资源是一组实体的概念映射，而不是与任何特定时间点的映射相对应的实体。
```



## What does CRUD mean?

```
Create，Read，Update和Delete。简记为增删改查。
```



## Is REST secure? What can you do to secure it?

```
REST架构风格本身并未规定任何特定的安全解决方案，但建议使用一个或多个中间件使用分层系统风格。安全是中间件的责任。

虽然安全性（例如基本身份验证）将使除了知道登录信息的人之外的所有人都无法使用REST服务，但是客户端和服务之间传递的消息仍然可以被任何能够拦截它们的人读取。
为了保护传输中的消息，可以使用加密，例如使用HTTPS协议。
```



## Is REST scalable and/or interoperable?

```
1.scalable(可伸缩性)
REST架构风格的无状态，可缓存性和分层系统约束允许扩展REST Web服务。
- 无状态
确保请求可以由服务集群中的任何节点处理，而不必考虑服务器端状态。
- 可缓存性
允许从缓存的信息创建响应，而不必将请求转到实际服务，提高了网络效率并减少了服务的负载。
- 分层系统
允许引入诸如负载均衡器之类的中介，而无需客户端就向服务发送请求而必须修改其行为。然后，负载均衡器可以在多个服务实例之间分配请求，增加了服务的请求处理能力。

2.interoperable(互操作性)
REST架构风格的以下元素提高了互操作性：
- REST服务可以支持传输到客户端的资源表示的不同格式，并允许客户端指定它希望接收数据的格式。
常见的格式是XML，JSON，HTML，它们都是促进互操作性的格式。
- REST资源通常使用URI来标识，这些URI不依赖于任何特定语言或实现。
- REST架构风格允许对资源进行固定的操作集。
```



## Which HTTP methods does REST use?

| Operation | HTTP method |
| --------- | ----------- |
| Create    | POST        |
| Read      | GET         |
| Update    | PUT/PATCH   |
| Delete    | DELETE      |

## What is an HttpMessageConverter?

```
HttpMessageConverter接口指定可以执行以下转换的转换器的属性：
- 将HttpInputMessage转换为指定类型的对象。
- 将对象转换为HttpOutputMessage。

此接口有许多实现可执行特定转换。以下是一些示例：
- AtomFeedHttpMessageConverter
转换为Atom订阅源，或从Atom订阅源转换。
- ByteArrayHttpMessageConverter
转换为字节数组，或从字节数组转换。
- FormHttpMessageConverter
转换为HTML表单，或从HTML表单转换。
- Jaxb2RootElementHttpMessageConverter
读取使用JAXB2注释@XmlRootElement和@XmlType注释的类，并编写使用@XmlRootElement注释的类。
- MappingJackson2HttpMessageConverter
使用Jackson 2.x转换为JSON，或从JSON转换。
```



## Is REST normally stateless?

```
是。
```



## What does @RequestMapping do?

```
@RequestMapping注释将控制器中的方法标记为将被调用以处理与@RequestMapping注释中的配置匹配的请求的方法。
它的特化注解有：@GetMapping，@PostMapping，@PutMapping，@DeleteMapping，@PatchMapping。
```



## Is @Controller a stereotype? Is @RestController a stereotype?

```
@Controller是构造型注解。
@RestController是@Controller注解的特化，也是构造型注解。
```



### What is a stereotype annotation? What does that mean?

```
构造型注解是应用于履行某种不同角色的类的注释。
（核心）Spring框架提供以下构造型注释，这些都可以在org.springframework.stereotype包中找到：
- @Component
- @Controller
- @Indexed
- @Repository
- @Service
其他Spring项目提供了额外的构造型注释。
```



## What is the difference between @Controller and @RestController?

```
@RestController注释，使用了@Controller和@ResponseBody注释进行注释。
在类级别应用时，@ResponsePody注释适用于处理Web请求的控制器中的所有方法。
```



## When do you need @ResponseBody?

```
@ResponseBody注释可以应用于控制器类或控制器处理程序方法。
它导致响应由HttpMessageConverter处理的控制器方法结果的序列化结果创建。当您希望Web响应主体包含控制器方法生成的结果时，这非常有用，
就像实现后端服务（例如REST服务）时的情况一样。
```



## What are the HTTP status return codes for a successful GET, POST, PUT or DELETE operation?

```
- 1xx
信息。已收到请求并继续处理。
- 2xx
成功。该请求已成功接收，理解和接受。
- 3xx
重定向。需要采取进一步行动来完成请求。
- 4xx
客户端错误。请求不正确或无法处理。
- 5xx
服务器错误。服务器无法处理看似有效的请求。
```

以下是属于成功的HTTP响应状态代码：

| HTTP response status code 2xx | Meaning                       |
| ----------------------------- | ----------------------------- |
| 200                           | OK                            |
| 201                           | Created                       |
| 202                           | Accepted                      |
| 203                           | Non-Authoritative Information |
| 204                           | No Content                    |
| 205                           | Reset Content                 |
| 206                           | Partial Content               |



## When do you need @ResponseStatus?

```
@ResponseStatus注释也可用于注释异常类，以便在控制器处理程序方法中处理请求期间抛出类型的异常时指定要返回的HTTP响应状态和原因，而不是默认的服务器内部错误（500）。
- @ResponseStatus注释可以应用于控制器处理程序方法，以覆盖原始响应状态信息。
在注释中，可以指定HTTP响应状态代码和原因字符串。
- @ResponseStatus注释也可以在控制器类的类级别应用，在这种情况下，它将应用于类中的所有控制器处理程序方法。
```



## Where do you need @ResponseBody? What about @RequestBody? Try not to get these muddled up!

```
@ResponseBody：
当Web响应主体要包含控制器方法生成的结果时会使用它。

@RequestBody：
@RequestBody注释只能应用于方法的参数。更具体地，控制器处理程序方法的参数。当Web请求body绑定到控制器处理程序方法的参数时使用它。常见于POST请求。
```



## If you saw example Controller code, would you understand what it is doing? Could you tell if it was annotated correctly?

```

```



## Do you need Spring MVC in your classpath?

```
spring-web模块必须存在于类路径中。
```



## What Spring Boot starter would you use for a Spring REST application?

```
Spring Boot Web Starter
```



## What are the advantages of the RestTemplate?

```
RestTemplate实现了一个同步HTTP客户端，可以简化发送请求并强制执行RESTful原则。
RestTemplate的一些优点是：
- 提供更高级别的API来执行HTTP请求。
允许使用最少的代码发送HTTP请求。
- 允许轻松选择要使用的基础HTTP客户端库。
- 支持URI模板。
- 自动编码URI模板。
例如，URI中的空格字符将使用percent-encoding替换为％20。
- 支持自动检测内容类型。
- 支持对象和HTTP消息之间的自动转换。
- 支持轻松自定义可用于检测内容类型和处理对象与HTTP消息之间转换的HTTP消息转换器。
- 允许轻松自定义响应错误。
可以在RestTemplate上注册自定义ResponseErrorHandler。
- 允许轻松自定义URI模板处理。
这是从URI模板创建URI的过程。
- 提供方便发送常见HTTP请求类型的方法，并提供允许在发送请求时增加详细信息的方法。
前一种方法类型的示例有：delete，getForObject，getForEntity，headForHeaders，postForObject和put。
后一种类型的方法都称为execute但具有不同的参数。
```



## If you saw an example using RestTemplate would you understand what it is doing?

```

```

