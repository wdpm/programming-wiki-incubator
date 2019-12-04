# redis basic

## StringRedisTemplate
> StringRedisTemplate extends RedisTemplate<String, String>

```java
@Service
public class StringRedisService {

    private StringRedisTemplate template;

    @Autowired
    public StringRedisService(StringRedisTemplate template) {
        this.template = template;
    }

    public void setString(String key, String value) {
        template.opsForValue().set(key, value);
    }

    public String getString(String key) {
        return template.opsForValue().get(key);
    }

}
```

### Test
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataWithRedisApplicationTests {

    @Autowired
    StringRedisService stringRedisService;

    @Test
    public void testSetKV(){
      stringRedisService.setString("key1","value1");
    }

    @Test
    public void testGetByKey(){
        final String string = stringRedisService.getString("key1");
        System.out.println(string);
    }

}

```

## Serializers
When you use StringRedisTemplate,you don't need to custom serializers because string is primitive type.
But when you working with complex type like object,you must do it.

> https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis:serializer

Multiple implementations are available:

- ``JdkSerializationRedisSerializer``, which is used by default for RedisCache and RedisTemplate.
- ``StringRedisSerializer``.
- ``OxmSerializer`` for Object/XML mapping through Spring OXM support 
- ``Jackson2JsonRedisSerializer`` or ``GenericJackson2JsonRedisSerializer`` for storing data in JSON format.

## RedisTemplate

1.add maven dependency
```java
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.9</version>
</dependency>
```

2.under AppConfig,add this bean
```java
@Configuration
class AppConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // connection
        redisTemplate.setConnectionFactory(connectionFactory);

        // use Jackson2JsonRedisSerializer for value serialization
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //value
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        // use StringRedisSerializer for key serialization
        RedisSerializer redisSerializer = new StringRedisSerializer();
        //key
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```
we use:
- ``Jackson2JsonRedisSerializer`` for value serialization
- ``StringRedisSerializer`` for key serialization

3.create ``RedisService`` and ``User`` entity class
```java
@Service
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setObject(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
```
```java
public class User implements Serializable {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
```
### Test
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataWithRedisApplicationTests {

    @Autowired
    RedisService redisService;

    @Test
    public void testSetObject(){
        redisService.setObject("1",new User("user1","pass1"));
    }

    @Test
    public void testGetObject(){
        final User user = (User)redisService.getObject("1");
        System.out.println(user);
    }

}
```


