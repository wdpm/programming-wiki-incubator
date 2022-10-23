# Gateway

1.add these dependencies to gateway module.
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
        <artifactId>common</artifactId>
        <groupId>com.lynn.blog</groupId>
        <version>1.0-SNAPSHOT</version>
        <!-- exclude all dependencies that have web dependency -->
        <exclusions>
            <exclusion>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-sleuth-zipkin</artifactId>
            </exclusion>
            <exclusion>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
            </exclusion>
<!--                don't exclude ribbon for load balance-->
<!--                <exclusion>-->
<!--                    <groupId>org.springframework.cloud</groupId>-->
<!--                    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>-->
<!--                </exclusion>-->
            <exclusion>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
```

2.setup gateway ``boostarp.yml``.
```yaml
spring:
  cloud:
    config:
      name: eurekaclient,gateway,rabbitmq
      label: master
      discovery:
        enabled: true
        serviceId: config
      username: admin
      password: admin

eureka:
  client:
    serviceUrl:
      defaultZone: http://admin:admin123@localhost:8100/eureka/
```

3.in git repo, create ``gateway.yml``.
```yaml
server:
  port: 8080

# http://gateway_host:gateway_port/serviceId/**
spring:
  application:
    name: gateway
  cloud:
    gateway:
      discovery: 
        locator:
          enabled: true

logging:
  level:
    org.springframework.cloud.gateway: trace
    org.springframework.http.server.reactive: debug
    org.springframework.web.reactive: debug
    reactor.ipc.netty: debug

feign:
  hystrix:
    enabled: true

hystrix:
  command:
    fallbackcmd:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 30000
```

4.run gateway module.visit ``localhost:8080/TEST/test``, will get expected response: ``message is：changed2``.
check logs:
```bash
Mapping [Exchange: GET http://localhost:8080/TEST/test] to Route{id='CompositeDiscoveryClient_TEST', uri=lb://TEST, 
order=0, predicate=org.springframework.cloud.gateway.support.ServerWebExchangeUtils$$Lambda$840/1399818762@748b4423,
gatewayFilters=[OrderedGatewayFilter{delegate=org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory$$Lambda$842/656056527@288faa32, order=1}]}
Sorted gatewayFilterFactories: [OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter@6e0d16a4}, order=-2147482648}, 
OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.NettyWriteResponseFilter@5d5d3a5c}, order=-1}, 
OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.ForwardPathFilter@4601047}, order=0}, 
OrderedGatewayFilter{delegate=org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory$$Lambda$842/656056527@288faa32, order=1}, 
OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter@7e18ced7}, order=10000}, 
OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.LoadBalancerClientFilter@22865072}, order=10100},
OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.WebsocketRoutingFilter@25e8e59}, order=2147483646},
GatewayFilterAdapter{delegate=com.lynn.blog.gateway.filter.ApiGlobalFilter@8077c97}, 
OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.NettyRoutingFilter@563317c1}, order=2147483647}, 
OrderedGatewayFilter{delegate=GatewayFilterAdapter{delegate=org.springframework.cloud.gateway.filter.ForwardRoutingFilter@305b43ca}, order=2147483647}]
RouteToRequestUrlFilter start
LoadBalancerClientFilter url before: lb://TEST:8080/test
```