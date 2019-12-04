# Ribbon

Spring Cloud Netflix Ribbon provide HTTP client and TCP client, enable to communicate between
services and load balance.

## maven dependency
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

## enable rest template load balance
```java
@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport{
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

## example
```java
public interface TestServiceRibbon {
    
    String test();
}
```
```java
@Service
public class TestServiceImplRibbon implements TestServiceRibbon {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String test() {
        return restTemplate.postForEntity("http://TEST/test",null,String.class).getBody();
    }

}
```
```java
@RequestMapping("ribbon")
@RestController
public class TestControllerRibbon {

    @Autowired
    TestServiceRibbon testServiceRibbon;

    @RequestMapping("test")
    private String test(){
        return testServiceRibbon.test();
    }
}

```
GET ``localhost:8203/ribbon/test``, the results are as expected: ``"message is：changed2"``.
The weakness of ribbon is this: ``restTemplate.postForEntity("http://TEST/test",null,String.class).getBody();``, seems like
a HTTP remote call.

## test ribbon load balance
edit ``test.yml`` in git repo.
- testNode1:
```yaml
server:
  port: 9998
```
- testNode2:
```yaml
server:
  port: 9999
```
start testNode1 and testNode2 module. GET ``localhost:8203/ribbon/test`` for several times.

It will print ``"port is：9999"`` or ``"port is：9998"`` in the log.