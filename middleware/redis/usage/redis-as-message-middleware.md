# redis as message middleware

add maven dependency
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
edit ``application.yml``
```yaml
spring:
  redis:
    database: 0
    host: 192.168.31.12
    port: 6379
    password: # default is null
```
create one receiver class
```java
public class Receiver {
    private static  final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    public Receiver() {
    }

    public void receiveMessage(String msg){
        LOGGER.info("receiveMessage():Received <"+msg+">");
    }
}
```

define some bean in main class
```java
@Bean
Receiver receiver(){
    return new Receiver();
}

// StringRedisTemplate use RedisConnectionFactory
@Bean
StringRedisTemplate template(RedisConnectionFactory connectionFactory){
    return new StringRedisTemplate(connectionFactory);
}

// MessageListenerAdapter
@Bean
MessageListenerAdapter listenerAdapter(Receiver receiver){
    return new MessageListenerAdapter(receiver,"receiveMessage");
}

// RedisMessageListenerContainer use RedisConnectionFactory + MessageListenerAdapter
@Bean
RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(listenerAdapter,new PatternTopic("message"));
    return container;
}
```
in main method,get template and call ``convertAndSend`` to send message
```java
public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(DataWithRedisApplication.class, args);
    StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);

    LOGGER.info("Sending message...");
    template.convertAndSend("message","This is form Redis!");

    System.exit(0);
}
```
console output:
```java
2019-12-04 19:07:45.505  INFO 8156 --- [           main] c.e.d.DataWithRedisApplication           : Sending message...
2019-12-04 19:07:45.523  INFO 8156 --- [    container-2] com.example.datawithredis.Receiver       : receiveMessage():Received <This is form Redis!>
```