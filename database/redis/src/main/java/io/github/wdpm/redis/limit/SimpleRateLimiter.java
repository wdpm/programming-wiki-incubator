package io.github.wdpm.redis.limit;

import io.github.wdpm.redis.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * 基于滑动时间窗口的限流
 * <p>
 * 每一个行为到来时，都维护一次时间窗口。将时间窗口外的记录全部清理掉，只保留窗口内的记录。同时设置对应key的超时。
 * </p>
 *
 * @author evan
 * @date 2020/6/7
 */
public class SimpleRateLimiter {
    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
        // userId + actionKey 标记一个特定用户的特定请求
        String   key   = String.format("hist:%s:%s", userId, actionKey);
        long     nowTs = System.currentTimeMillis();
        Pipeline pipe  = jedis.pipelined();
        pipe.multi();
        // String key, double score, String member
        pipe.zadd(key, nowTs, "" + nowTs);
        // 移除时间窗口之前的行为记录[0,nowTs - period * 1000]
        // 剩下的都是时间窗口内[nowTs - period * 1000,?)
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
        // 获取窗口内的行为数量
        Response<Long> count = pipe.zcard(key);
        // 设置 zset 过期时间，避免冷用户持续占用内存
        // 过期时间应该等于时间窗口的长度，宽限 1s
        pipe.expire(key, period + 1);
        pipe.exec();
        pipe.close();
        return count.get() <= maxCount;
    }

    public static void main(String[] args) {
        Jedis             jedis   = JedisUtil.getRedis();
        SimpleRateLimiter limiter = new SimpleRateLimiter(jedis);
        for (int i = 0; i < 20; i++) {
            System.out.println(limiter.isActionAllowed("alice", "reply", 60, 5));
        }
    }
}
