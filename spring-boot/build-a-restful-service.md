# build a restful service

@RestController 表明返回领域对象而不是一个视图，它是 @Controller and @ResponseBody 的速记。

Greeting对象必须转换为JSON。由于Spring的HTTP消息转换器支持，无需手动执行此转换。 
因为Jackson 2在 classpath 上，会自动选择MappingJackson2HttpMessageConverter将Greeting实例转换为JSON

```java
public class Greeting {
    private final long id;
    private final String content;
    //get/set
}
```
```java
@RestController
public class GreetingController {

    private final AtomicLong counter =new AtomicLong();
    private static final String template = "Hello, %s!";

    @RequestMapping(value = "/greeting",method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(name="name",defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),String.format(template,name));

    }
}
```

GET http://localhost:8080/greeting:
```json
{"id":1,"content":"Hello, World!"}
```

GET http://localhost:8080/greeting?name=User":
```json
{"id":2,"content":"Hello, User!"}
```