package io.github.wdpm.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author evan
 * @date 2020/6/8
 */
interface CallWithJedis {
    void call(Jedis jedis);
}

public class RedisPool {
    //address of your redis server
    private static final String  redisHost = "192.168.137.12";
    private static final Integer redisPort = 6379;

    //the jedis connection pool
    private static final JedisPool pool = new JedisPool(redisHost, redisPort);

    public RedisPool() {
    }

    public void execute(CallWithJedis caller) {
        Jedis jedis = pool.getResource();
        // todo 连接池的大小以及超时参数配置
        try {
            caller.call(jedis);
        } catch (JedisConnectionException jce) {
            caller.call(jedis);// retry again
        } finally {
            jedis.close();
        }
    }

    public static void main(String[] args) {
        RedisPool redisPool = new RedisPool();
        // Holder 包装类绕过闭包对变量修改的限制
        RedisResultHolder<String> holder = new RedisResultHolder<>();
        redisPool.execute(jedis -> {
            String result = jedis.set("name", "foo");
            holder.setValue(result);
        });
        System.out.println(holder.getValue());
    }
}
