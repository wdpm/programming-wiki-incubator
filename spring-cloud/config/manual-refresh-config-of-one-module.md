# Manual refresh config of one module

1.add actuator dependency
add actuator in common module pom.xml.
```pom
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

2.add test code in test module.
```java
@RestController
@RefreshScope
public class TestController {

    @Value("${data.message}")
    private String message;

    @RequestMapping("test")
    public String test(){
        return "message is："+message;
    }
    
}
```

3.enable refresh endpoints under ``test.yml`` in git repo.
```yaml
data:
  message: 'test1'
  
management:
  endpoints:
    web:
      exposure:
        include: refresh,health,info
```

4.run test module, visit ``localhost:9998/test``, return message is: ``test1``.

5.update ``test.yml`` in git repo.
```yaml
data:
  message: 'changed'
```

6.use postman to POST ``http://localhost:9998/actuator/refresh``.
```
[
    "config.client.version",
    "data.message"
]
```

7.visit ``localhost:9998/test`` again, return message is: ``changed``.