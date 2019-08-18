# Spring MVC and the Web Layer

## MVC is an abbreviation for a design pattern. What does it stand for and what is the idea behind it?

```
MVC是Model-View-Controller的缩写，用于分离具有用户界面的应用程序（例如Web应用程序）的关注点的设计模式。
MVC模式有三个主要部分:
1.Model
模型包含应用程序的当前数据和业务逻辑。
2.View
视图负责向用户显示应用程序的数据。用户与视图交互。
3.Controller 
Controller充当模型和视图之间的中介，接受来自视图的请求，向模型发出命令以操纵应用程序的数据，最后与视图交互以呈现结果。

使用MVC所期望的一些优点是：
- 重用不同视图的模型和控制器。
示例：一个视图用于将应用程序呈现为Web应用程序，另一个视图用于移动设备，两者都使用相同的模型和控制器。
- 减少模型，视图和控制器之间的耦合。
- 关注点分离。
提高可维护性和可扩展性。更容易地共享在多个开发人员之间开发应用程序的工作。
```



## What is the DispatcherServlet and what is it used for?

```
DispatcherServlet是一个实现前端控制器设计模式的servlet。
DispatcherServlet的职责包括： 
- 接收请求并将其委托给注册的处理程序。
如果在应用程序中只使用了一个DispatcherServlet，则此servlet将接收发送到应用程序的所有请求。
- 通过将视图名称映射到View实例来解析视图。
- 解决在处理程序映射或执行期间发生的异常。
最常见的是将异常映射到错误视图。

前端控制器设计模式允许集中应用于整个应用程序的事情，如安全性和错误处理。
Spring Web应用程序可以定义多个Dispatcher Servlet，每个servlet都有自己的命名空间，自己的Spring应用程序上下文以及自己的映射和处理程序集。
```



## What is a web application context? What extra scopes does it offer?

```
Web应用程序上下文由WebApplicationContext接口指定，是Web应用程序的Spring应用程序上下文。它具有常规Spring应用程序上下文的所有属性，
因为WebApplicationContext接口扩展了ApplicationContext接口，并添加了一个方法来检索Web应用程序的标准Servlet API ServletContext。

除了标准的Spring bean作用域singleton和prototype之外，Web应用程序上下文中还有三个可用的作用域：
```

| Scope       | Description                             |
| ----------- | --------------------------------------- |
| request     | Single bean instance per HTTP request   |
| session     | Single bean instance per HTTP session   |
| application | Single bean instance per ServletContext |



## What is the @Controller annotation used for?

```
@Controller注释是前面讨论过的@Component注释的特化。
它用于注释实现Web控制器的类，即MVC中的C.
这些类不需要扩展任何特定的父类或实现任何特定的接口。
```



## How is an incoming request mapped to a controller and mapped to a method?

```
要使请求映射到控制器类中的一个或多个方法，需要执行以下步骤：
- 启用组件扫描。
这样可以自动检测使用@Controller注释注释的类。
@SpringBootApplication注释本身注释了许多元注释，其中一个是@ComponentScan注释。
- 使用@EnableWebMvc注释一个配置类。
在Spring Boot应用程序中，有一个配置类实现WebMvcConfigurer接口就足够了。
- 实现使用@Controller注释的控制器类。
控制器类也可以使用@RequestMapping注释进行注释，在这种情况下，它将向URL添加一个部件，该部件将映射到控制器方法。
- 在控制器类中实现至少一个方法，并使用@RequestMapping注释该方法。
可以使用其中一个专用注释，例如@GetMapping。

向应用程序发出请求时：
1.应用程序的DispatcherServlet接收请求。
2.DispatcherServlet将请求映射到控制器中的方法。
DispatcherServlet包含实现HandlerMapping接口的类列表。
3.DispatcherServlet将请求分派给控制器。
4.执行控制器中的方法。
```



## What is the difference between @RequestMapping and @GetMapping?

```
@RequestMapping批注中如果未指定Method元素，则使用任何HTTP方法的请求将映射到带注释的方法。

@GetMapping注释是使用method = RequestMethod.GET的@RequestMapping注释的特化。因此，只有使用HTTP GET方法的请求才会映射到@GetMapping注释的方法。
```



## What is @RequestParam used for?

```java
将Http请求参数和控制器中对应方法的参数绑定。
@RequestMapping("/greeting") 
public String greeting(@RequestParam(name="name", required=false) String inName) { … }

http://localhost:8080/greeting?name=hello
```



##  What are the differences between @RequestParam and @PathVariable?

```
@RequestParam 映射查询参数到方法参数
@PathVariable 映射URL模板变量到方法参数
```



##  What are some of the parameter types for a controller method?

| Controller Method Argument Type                              | Gives Access To                                              |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| WebRequest                                                   | Request metadata such as context path, request parameters, user principal etc. |
| NativeWebRequest                                             | Extension of WebRequest that also allows access<br/>to native request and response objects. |
| javax.servlet.ServletRequest                                 | Request object. The Spring MultipartRequest<br/>type can also be used. |
| javax.servlet.ServletResponse                                | Response object.                                             |
| javax.servlet.http.HttpSession                               | HTTP session object. Will ensure that a session<br/>object exists and will create one if this is not the<br/>case. |
| javax.servlet.http.PushBuilder                               | Servlet 4.0 builder used to build push requests.             |
| java.security.Principal                                      | Currently authenticated user principal.                      |
| HttpMethod                                                   | Request HTTP method.                                         |
| java.util.Locale                                             | Locale of the current request.                               |
| java.util.TimeZone, java.time.ZoneId                         | Time zone associated with the current request.               |
| java.io.InputStream, java.io.Reader                          | Request body.                                                |
| java.io.OutputStream, java.io.Writer                         | Response body.                                               |
| `HttpEntity<B>`                                              | Request headers and body.                                    |
| java.util.Map, org.springframework.ui.Model,<br/>org.springframework.ui.ModelMap | Model that is used in controllers and exposed<br/>when the view is rendered. |
| RedirectAttributes                                           | Extends the Model interface allowing selection<br/>of attributes for redirect scenarios. |
| Errors                                                       | Data-binding and validation errors.                          |
| BindingResult                                                | Extends the Errors interface to allow registration<br/>of errors, application of a Validator and bindingspecific<br/>analysis and model building. |
| SessionStatus                                                | Session processing status; getting and setting.              |
| UriComponentsBuilder                                         | Builder for creating URI references. Supports<br/>URI templates. |

```
Ref:https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/web.html#mvc-ann-arguments
```

### What other annotations might you use on a controller method parameter? (You can ignore for handling annotations for this exam) 

| Controller Method Argument Annotation | Function                                                     |
| ------------------------------------- | ------------------------------------------------------------ |
| @PathVariable                         | URL路径模板 -> 处理程序方法参数                              |
| @MatrixVariable                       | URL中的name-value -> 处理程序方法参数                        |
| @RequestParam                         | 请求参数 -> 处理程序方法参数                                 |
| @RequestHeader                        | 请求header -> 处理程序方法参数                               |
| @CookieValue                          | HTTP cookie -> 处理程序方法参数                              |
| @RequestBody                          | 请求body -> 处理程序方法参数                                 |
| @RequestPart                          | “multipart/form-data”请求的一部分 -> 处理程序方法参数        |
| @ModelAttribute                       | 将命名的model属性绑定到处理程序方法参数或处理程序方法的返回值。 |
| @SessionAttributes                    | 使选定的model属性存储在请求之间的HTTP Servlet会话中。        |
| @SessionAttribute                     | 会话属性 -> 处理程序方法参数。                               |
| @RequestAttribute                     | 请求属性 -> 处理程序方法参数。                               |


 

## What are some of the valid return types of a controller method?

| Controller Method Return Type                                | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `HttpEntity<B>`                                              | 表示由headers和body组成的响应实体。                          |
| `ResponseEntity<B>`                                          | 扩展HttpEntity，添加HTTP状态代码。                           |
| HttpHeaders                                                  | 响应仅包含HTTP headers且没有body时使用。                     |
| String                                                       | 要呈现的视图的名称。一般配合视图模板引擎使用。               |
| View                                                         | 给定模型，请求和响应的对象，呈现视图。                       |
| java.util.Map<br/>org.springframework.ui.Model               | 包含要添加到模型的属性的Map或Model实例。                     |
| ModelAndView                                                 | 持有模型和视图的对象。                                       |
| void                                                         | - 当控制器方法通过写入输出流来处理响应或者使用@ResponseStatus注释该方法时使用。<br/>- 用于不返回响应主体的REST控制器方法。<br/>- 在HTML控制器方法中使用，选择默认视图名称。<br/>从控制器方法返回null会产生相同的结果。 |
| `DeferredResult<V>`<br/>alternatively<br/>`ListenableFuture<V>`,<br/>`java.util.concurrent.CompletionStage<V>`,<br/>`java.util.concurrent.CompletableFuture<V>` | 从另一个线程异步生成控制器方法的返回值。                     |
| `Callable<V>`                                                | 从Spring MVC托管线程异步生成控制器方法的返回值。             |
| ResponseBodyEmitter                                          | 异步处理允许将对象写入响应的请求。                           |
| SseEmitter                                                   | ResponseBodyEmitter的特化，允许发送服务器推送的事件。        |
| StreamingResponseBody                                        | 异步处理允许应用程序写入响应输出流的请求，绕过消息转换，而不阻塞servlet容器线程。 |
| Reactive types                                               | DeferredResult的替代方案，用于与Reactor，RxJava或类似方法一起使用。 |
| Any other return value type                                  | 默认情况下视为视图名称。简单类型返回未解析。                 |

下表列出了可应用于影响返回结果的控制器方法的注释：

| Controller Method Annotation | Description                                                  |
| ---------------------------- | ------------------------------------------------------------ |
| @ResponseBody                | 响应是由HttpMessageConverter处理的控制器方法结果的序列化结果创建的。 |
| @ModelAttribute              | 在注释中指定名称的模型属性将添加到模型中，其值为控制器方法的结果。主要用于将前端表单提交的属性绑定到后端class中。 |