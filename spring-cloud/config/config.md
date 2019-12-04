# Config

## Setup
``application.yml``
```yaml
server:
  port: 8102
  
spring:
  application:
    name: config
    config:
      server:
        git:
          uri: https://github.com/wdpm/SpringCloudDemo.git
          searchPaths: config
          username: ******
          password: ******
      label: master
  rabbitmq:
    host: 192.168.31.12
    port: 5672
    username: evan
    password: 123456
    virtualHost: /
    publisherConfirms: true
  security:
    user:
      name: admin
      password: admin
      
management:
  endpoints:
    web:
      exposure:
        include: refresh,health,info,bus-refresh
eureka:
  instance:
    prefer-ip-address: true
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://admin:admin123@localhost:8100/eureka/
```

- In github,create repo ``https://github.com/wdpm/SpringCloudDemo.git``
- In this repo, create ``config`` folder and upload ``test.yaml`` into it.
```yaml
spring:
  application:
    name: test
  rabbitmq:
    host: 192.168.31.12
    port: 5672
    username: evan
    password: 123456
    virtualHost: /
    publisherConfirms: true

server:
  port: 9999

eureka:
  instance:
    prefer-ip-address: true
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    healthcheck:
      enabled: true
    register-with-eureka: true
    fetch-registry: true
```
- Then start config server.

### Client Test
- In test module, update ``bootstrap.yml`` content as follow:
```yaml
spring:
  cloud:
    config:
      username: admin
      password: admin
      name: test
      label: master
      discovery:
        enabled: true
        serviceId: config # -> find CONFIG service
eureka:
  client:
    service-url:
      defaultZone: http://admin:admin123@localhost:8100/eureka/ # register to eureka
```

- run test module, check port is 9999.
- update ``test.yaml``
```yaml
server:
  port: 9999
```
- restart test module, check port is 9998.


