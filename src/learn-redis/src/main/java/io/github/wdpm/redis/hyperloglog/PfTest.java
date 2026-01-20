package io.github.wdpm.redis.hyperloglog;

import io.github.wdpm.redis.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * HLL 精确度测试
 *
 * @author evan
 * @date 2020/6/7
 */
public class PfTest {
    public static void main(String[] args) {
        try (Jedis jedis = JedisUtil.getRedis()) {

            //clean dataset
            jedis.del("example");

            for (int i = 0; i < 1000; i++) {
                jedis.pfadd("example", "user" + i);
                long total = jedis.pfcount("example");
                if (total != i + 1) {
                    System.out.printf("%d %d\n", total, i + 1);
                    break;
                }
            }

            //clean dataset
            jedis.del("example");

            // 加入第 100 个元素时，结果开始出现了不一致
            // 99 100
        }
    }
}
