# 优雅的Jedis

## try-with-resource 保护 Jedis 对象
```java
public static void main(String[] args) {
    try (Jedis jedis = getRedis()) {
        String ret = jedis.set("name", "foo");
    }
}
```