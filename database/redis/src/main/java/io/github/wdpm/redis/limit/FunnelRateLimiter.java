package io.github.wdpm.redis.limit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 单机版漏斗限流，线程不安全
 *
 * @author evan
 * @date 2020/6/7
 */
public class FunnelRateLimiter {
    static class Funnel {
        int   capacity;// 总容量
        float leakingRate;// 流水速率 quota/s
        int   leftQuota;//剩余配额
        long  leakingTs;//上一次漏水时间

        // 总容量=流水速率*流水时间+剩余配额
        // 流水时间=当前时间点-上一次漏水时间

        public Funnel(int capacity, float leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        void makeSpace() {
            long nowTs      = System.currentTimeMillis();
            long deltaTs    = nowTs - leakingTs;
            long toSeconds  = TimeUnit.MILLISECONDS.toSeconds(deltaTs);
            int  deltaQuota = (int) (toSeconds * leakingRate);
            if (deltaQuota < 0) { // 间隔时间太长，整数数字过大溢出
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            if (deltaQuota < 1) { // 腾出空间太小，最小单位是1
                return;
            }
            this.leftQuota += deltaQuota;
            this.leakingTs = nowTs;
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }
        }

        boolean watering(int quota) {
            makeSpace();
            if (this.leftQuota >= quota) {
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }
    }

    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        // todo refactor to get from redis
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        return funnel.watering(1); // 需要1个quota
    }

    public static void main(String[] args) {
        FunnelRateLimiter limiter = new FunnelRateLimiter();
        for (int i = 0; i < 20; i++) {
            // capacity: 10 ; leakingRate=5 quota/second
            boolean allowed = limiter.isActionAllowed("alice", "reply", 10, 5);
            if (allowed) {
                System.out.println(allowed);
            } else {
                try {
                    System.out.println("reach limit, waiting for 2 seconds...");
                    Thread.sleep(2000);// 5 * 2=10
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
