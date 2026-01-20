package io.github.wdpm.redis.bloom;

import io.rebloom.client.Client;

/**
 * Either:
 * <li>A Redis Enterprise Software database with the RedisBloom module enabled
 * <li>A Redis Cloud Pro database with the RedisBloom module enabled
 *
 * <p>
 * refer:
 * <li>- https://docs.redislabs.com/latest/modules/redisbloom/redisbloom-quickstart/
 * <li>- https://github.com/RedisBloom/JRedisBloom
 *
 * @author evan
 * @date 2020/6/9
 */
public class jrebloomTest {
    public static void main(String[] args) {
        Client client = new Client("192.168.137.12", 6378);
        client.add("simpleBloom", "Mark");
        // Does "Mark" now exist?
        boolean exists = client.exists("simpleBloom", "Mark");// true
        System.out.println(exists);
        boolean exists1 = client.exists("simpleBloom", "Farnsworth");// False
        System.out.println(exists1);
    }
}
