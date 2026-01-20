package io.github.wdpm.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis reentrant lock
 *
 * @author evan
 * @date 2020/6/6
 */
public class RedisWithReentrantLock {

    // 使用线程本地变量
    private ThreadLocal<Map> lockers = new ThreadLocal<>();

    private Jedis jedis;

    public RedisWithReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }

    private boolean _lock(String key) {
        // setnx 就是分布式锁的关键，如果设置成功，则表示当前线程获得锁。
        return jedis.set(key, "", new SetParams()
                .ex(5)
                .nx()) != null;

    }

    private void _unlock(String key) {
        jedis.del(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }

    public boolean lock(String key) {
        Map<String, Integer> refs   = currentLockers();
        Integer              refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }

        boolean ret = _lock(key);
        if (!ret) {
            return false;
        }

        refs.put(key, 1);
        return true;

    }

    public boolean unlock(String key) {
        Map<String, Integer> refs   = currentLockers();
        Integer              refCnt = refs.get(key);
        if (refCnt == null) {
            return false;
        }
        refCnt -= 1;
        if (refCnt > 0) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this._unlock(key);
        }
        return true;
    }

    public static void main(String[] args) {
        Jedis                  jedis = JedisUtil.getRedis();
        RedisWithReentrantLock redis = new RedisWithReentrantLock(jedis);
        System.out.println(redis.lock("hello"));
        System.out.println(redis.lock("hello"));
        System.out.println(redis.unlock("hello"));
        System.out.println(redis.unlock("hello"));
    }
}
