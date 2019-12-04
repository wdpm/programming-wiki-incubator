# OpenFeign

Spring Cloud OpenFeign is a declarative http client.To use Feign create an interface and annotate it. 

Spring Cloud integrates Ribbon and Eureka, as well as Spring Cloud LoadBalancer to provide a load-balanced http client when using Feign.

## maven dependency
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

## main class add annotation
```java
@EnableFeignClients(basePackages = "<your-base-pkg>")
```

## example
interface
```java
@FeignClient(value = "test")
public interface TestServiceFeign {

    @RequestMapping("/test")
    String test();
}
```
> remember use //@SpringBootConfiguration to comment public module MyFeignConfiguration

``value = "test"`` means this interface is a openfeign http client named ``test``.Compare with ribbon before,here we don't
need to call HTTP service explicitly,only specify service name(``test``) instead.

controller
```java
@RequestMapping("feign")
@RestController
public class TestControllerFeign {

    @Autowired
    TestServiceFeign testServiceFeign;

    @RequestMapping("test")
    private String test(){
        return testServiceFeign.test();
    }
}

```

## integrate hystrix
make yaml has this setting:
```
feign:
  hystrix:
    enabled: true
```
update interface ``TestServiceFeign``
```java
@FeignClient(value = "test",fallback = TestServiceErrorFeign.class)
public interface TestServiceFeign {

    @GetMapping("/test")
    String test();
}
```

create fallback serviceImpl ``TestServiceErrorFeign``.
```java
@Component
public class TestServiceErrorFeign implements TestServiceFeign {

    @Override
    public String test() {
        return "TestServiceErrorFeign test() called.";
    }
}
```
ensure test module is running. GET ``localhost:8203/feign/test``,you will get correct response.

Then stop all the test module, GET ``localhost:8203/feign/test`` again.you will get:
```
"TestServiceErrorFeign test() called."
```