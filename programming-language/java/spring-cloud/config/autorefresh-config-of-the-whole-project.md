# Autorefresh config of the whole project

1.add ``spring cloud bus`` dependency to common module.
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

2.update ``application.yml`` of config module.
```yaml
spring:
  rabbitmq:
    host: 192.168.31.12
    port: 5672
    username: evan
    password: 123456
    virtualHost: /
    publisherConfirms: true
      
management:
  endpoints:
    web:
      exposure:
        include: refresh,health,info,bus-refresh
```

3.use postman to POST ``localhost:8102/actuator/bus-refresh``.
check config module logs:
```bash
Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext...
```
4.now we can use webhooks of git repo to enable autorefresh.
- visit git repo,``settings`` -> ``Webhooks`` -> ``Add webhook``
- enter the external ip of config module to ``Payload URL`` input.
> you can use ngrok to Intranet penetration if you just run in local pc.