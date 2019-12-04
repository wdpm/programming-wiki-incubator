# Eureka

## Standalone Eureka Server
``application.yml``
```yaml
spring:
  application:
    name: register

server:
  port: 8100
  
eureka:
  instance:
    hostname: localhost
  server:
     enable-self-preservation: false
  client:
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://admin:admin123@localhost:8100/eureka/
```

## Two Peer Aware Eureka Servers
common ``application.yml``
```yaml
spring:
  profiles:
#    active: node1
    active: node2

eureka:
  server:
     enable-self-preservation: false 
  client:
    register-with-eureka: true # enable register to eureka
    fetch-registry: true # fetch register table info
...    
```
``application-node1.yml``
```yaml
server:
  port: 8100
eureka:
  instance:
    hostname:  node1
  client:
    serviceUrl:
      # register to node1
      defaultZone: http://admin:admin123@node1:8100/eureka/
```
``application-node2.yml``
```yaml
server:
  port: 8101
eureka:
  instance:
    hostname:  node2
  client:
    serviceUrl:
      # also register to node1
      defaultZone: http://admin:admin123@node1:8100/eureka/
```

```bash
nano /etc/hosts
# change 127.0.0.1 to your ip if needed
127.0.0.1 node1 node2
```

### Client Test
``bootstrap.yml``
```yaml
spring:
  application:
    name: test

server:
  port: 9999

eureka:
  client:
    register-with-eureka: true
    fetch-registry: false # if needed, change to true.
    service-url:
      defaultZone: http://admin:admin123@node1:8100/eureka/,http://admin:admin123@node2:8101/eureka/
```