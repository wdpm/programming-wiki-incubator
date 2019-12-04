# Setup client public module
Public module depend on common module, but is one dependency for all client sub modules like blogmgr, comment, etc.

## Add maven dependencies
edit ``pom.xml``.
```xml
<dependencies>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>${druid.version}</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${spring.mybatis.version}</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <artifactId>common</artifactId>
        <groupId>com.lynn.blog</groupId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```
- druid: mysql datasource connection pool.
- mybatis: persistent layer framework.
- mysql-connector-java: mysql connection driver.
- spring-boot-starter-web: spring web dependencies.

## copy mybatis generator generated code
```
java/
domain/
  entity/
  ... # java entity files.
mapper/
  ... # java interface files
  
resources/
  mapper/
  ... # xml mapper files
```

## custom some common config
### enable RestTemplate load balance
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

### override OpenFeign configuration if you need
```java
@SpringBootConfiguration
public class MyFeignConfiguration {

    @Bean
    public Contract feignContract(){
        return new feign.Contract.Default();
    }
}
```

### change Jackson converter to FastJson
```java
@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport{

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig=new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(mediaTypeList);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }
}
```

### custom RequestMappingHandlerMapping
```java
@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport{

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        return handlerMapping;
    }
    
}
```
### create your base controller
```java
public abstract class BaseController {

    @ExceptionHandler
    public SingleResult doError(Exception exception) {
        if(StringUtils.isBlank(exception.getMessage())){
            return SingleResult.buildFailure();
        }
        return SingleResult.buildFailure(exception.getMessage());
    }

    protected void validate(BindingResult result){
        if(result.hasFieldErrors()){
            List<FieldError> errorList = result.getFieldErrors();
            errorList.stream().forEach(item -> Assert.isTrue(false,item.getDefaultMessage()));
        }
    }
}
```

