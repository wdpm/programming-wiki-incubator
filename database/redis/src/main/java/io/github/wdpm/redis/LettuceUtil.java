package io.github.wdpm.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;

/**
 * lettuce redis client
 *
 * <li>string -> set</li>
 * <li>hash -> hset</li>
 * <li>list -> lpush</li>
 * <li>set -> sadd</li>
 * <li>zset -> zadd</li>
 * <p>
 * for more, refer https://www.baeldung.com/java-redis-lettuce
 *
 * @author evan
 * @date 2020/6/9
 */
public class LettuceUtil {
    static RedisClient redisClient;

    static {
        RedisURI redisURI = RedisURI.create("192.168.137.12", 6379);
        redisClient = RedisClient.create(redisURI);
        redisClient.setDefaultTimeout(Duration.ofSeconds(20));
    }

    public static RedisClient getClient() {
        return redisClient;
    }

    public static void main(String[] args) {
        RedisClient                             client   = LettuceUtil.getClient();
        StatefulRedisConnection<String, String> connect  = client.connect();
        RedisCommands<String, String>           commands = connect.sync();
        commands.set("lettuce", 5.0 + "");
        String  value   = commands.get("lettuce");
        boolean exists  = exists(commands, "lettuce");
        boolean exists2 = exists(commands, "typo");
        System.out.println("value: " + value);
        System.out.println("lettuce exists?: " + exists);
        System.out.println("typo exists?: " + exists2);
        connect.close();
        client.shutdown();
    }

    public static boolean exists(RedisCommands<String, ?> commands, String key) {
        return commands.exists(key) > 0;
    }
}
