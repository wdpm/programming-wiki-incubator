package io.github.wdpm.redis.transaction;

import io.github.wdpm.redis.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * redis 事务测试
 *
 * @author evan
 * @date 2020/6/8
 */
public class TransactionDemo {
    public static void main(String[] args) {
        Jedis  jedis  = JedisUtil.getRedis();
        String userId = "abc";
        String key    = keyFor(userId);
        jedis.setnx(key, String.valueOf(5)); //setnx 做初始化
        System.out.println(doubleAccount(jedis, userId));
        jedis.close();
    }

    public static int doubleAccount(Jedis jedis, String userId) {
        String key = keyFor(userId);
        while (true) {
            jedis.watch(key);// watch key
            int value = Integer.parseInt(jedis.get(key));
            value *= 2; // 加倍
            Transaction tx = jedis.multi();
            tx.set(key, String.valueOf(value));
            List<Object> res = tx.exec();
            if (res != null) {
                break; // 成功
            }
        }
        return Integer.parseInt(jedis.get(key)); // 重新获取余额
    }

    public static String keyFor(String userId) {
        return String.format("account_{}", userId);
    }
}
