# Sleuth with zipkin via sender rabbit

> https://github.com/openzipkin/zipkin/tree/2.4.6/zipkin-server

## Install zipkin server
download ``zipkin-server-2.12.9-exec.jar``,rename to ``zipkin.jar``.
```bash
# bug: can't auto create zipkin queue in rabbitmq
# java -jar zipkin.jar --rabbitmq.addresses=192.168.31.12  --rabbitmq.username=evan --rabbitmq.password=123456

java -jar zipkin.jar --zipkin.collector.rabbitmq.addresses=192.168.31.12 --zipkin.collector.rabbitmq.username=evan --zipkin.collector.rabbitmq.password=123456
```

## Install zipkin client
1. in test module,make sure has this dependency:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
</dependency>
```

2.update ``eurekaclient.yaml``
```yaml
spring:
  zipkin:
#    baseUrl: http://localhost:9411/
    sender:
      type: rabbit
  sleuth:
    sampler:
      probability : 1
```
change ``sender.type`` to rabbit and comment ``baseUrl``,because now use rabbitmq to collect.

3.start test module.visit ``http://192.168.31.12:15672/#/queues/``,you can see ``zipkin`` queue has been created.

4.GET ``localhost:9999/test``,and then visit ``http://localhost:9411/`` to check tracking info.

## save trcki