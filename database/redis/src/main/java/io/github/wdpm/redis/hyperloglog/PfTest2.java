package io.github.wdpm.redis.hyperloglog;

import io.github.wdpm.redis.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * HLL 精确度测试
 *
 * @author evan
 * @date 2020/6/7
 */
public class PfTest2 {
    public static void main(String[] args) {
        try (Jedis jedis = JedisUtil.getRedis()) {

            test(jedis);

            // 100000 99725

            // run again test
            test(jedis);

            // 100000 99725

            // 说明的确具有去重功能，统计不精确但是很接近
        }
    }

    private static void test(Jedis jedis) {
        for (int i = 0; i < 100000; i++) {
            jedis.pfadd("web", "user" + i);
        }

        long total = jedis.pfcount("web");
        System.out.printf("%d %d\n", 100000, total);
    }
}
