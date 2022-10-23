# Sleuth with zipkin via sender web

Sleuth is for micro service tracking, it integrates Zipkin, one distributed tracking system.
Zipkin has server and client.

> https://spring.io/projects/spring-cloud-sleuth#overview
```
If spring-cloud-sleuth-zipkin is available then the app will generate and collect Zipkin-compatible traces via HTTP.
By default it sends them to a Zipkin collector service on localhost (port 9411).
Configure the location of the service using spring.zipkin.baseUrl.
```

## Install zipkin server
### By jar
```bash
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```
### Self build
1.create one module named ``zipkin``,add maven dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>io.zipkin.java</groupId>
        <artifactId>zipkin-server</artifactId>
        <version>2.8.4</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.java</groupId>
        <artifactId>zipkin-autoconfigure-ui</artifactId>
        <version>2.8.4</version>
    </dependency>
</dependencies>
```
2.main class add annotation ``@EnableZipkinServer``.

3.``application.yaml``
```yaml
server:
  port: 9411
management:
  metrics:
    web:
      server:
        auto-time-requests: false
```
4.start zipkin module. visit ``localhost:9411``

## Install zipkin client
1.add maven dependencies.
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
```

2.in test module,yaml add as follow:
```yaml
spring:
  zipkin:
    baseUrl: http://localhost:9411/
    sender:
      type: web # web,kafka,rabbit,null
  sleuth:
    sampler:
      probability : 1 # 100% sample
```

3.GET ``localhost:9999/test``,then visit ``localhost:9411`` to check.