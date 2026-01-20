package io.github.wdpm.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author evan
 * @date 2020/6/6
 */
public class JedisUtil {
    //address of your redis server
    private static final String  redisHost = "192.168.137.12";
    private static final Integer redisPort = 6379;

    //the jedis connection pool
    private static final JedisPool pool = new JedisPool(redisHost, redisPort);

    private JedisUtil() {
    }

    /**
     * must call Jedis.close() after using.
     *
     * @return
     */
    public static Jedis getRedis() {
        return pool.getResource();
    }

    public static void main(String[] args) {
        try (Jedis jedis = getRedis()) {
            String ret = jedis.set("name", "foo");
            System.out.println(ret);
        }
    }
}
