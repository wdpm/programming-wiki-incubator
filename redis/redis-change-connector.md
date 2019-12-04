# redis change connector

## get connection
RedisConnection provides the core building block for Redis communication,
Active RedisConnection objects are created through RedisConnectionFactory.
```java
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
```

The easiest way to work with a RedisConnectionFactory is to configure the appropriate connector through the IoC container and inject it into the using class.
- Jedis Connector
- Lettuce Connector (Spring Boot 2.0+ use Lettuce by default)

If you keep using Lettuce Connector, it is out of box.

If you want to use Jedis Connector,you should change something.

1.update ``pom.xml``,exclude ``lettuce-core`` and add ``jedis``(must add proper version).
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <exclusions>
        <exclusion>
            <groupId>io.lettuce</groupId>
            <artifactId>lettuce-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.3</version>
</dependency>
```
2.add JedisConnectionFactory as one redisConnectionFactory implementation.
```java
@Configuration
class AppConfig {

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        //tweak settings
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setDatabase(0);
        config.setHostName("192.168.31.12");
        config.setPort(6379);
        return new JedisConnectionFactory(config);
    }
}
```