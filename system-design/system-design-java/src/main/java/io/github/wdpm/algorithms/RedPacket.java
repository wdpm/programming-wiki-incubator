package io.github.wdpm.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * 红包算法
 *
 * @author evan
 * @date 2020/5/6
 */
public class RedPacket {
    /**
     * 红包最小值 1分
     */
    private static final int MIN_MONEY = 1;

    /**
     * 红包最大值 200 元 RMB
     */
    private static final int MAX_MONEY = 200 * 100;

    public enum SplitStatus {
        /**
         * 当前红包金额 < 红包最小值
         */
        REMAINING_MONEY_IS_LESS,
        /**
         * 当前红包金额 > 红包最大值
         */
        REMAINING_MONEY_IS_MORE,
        /**
         * 当前红包金额为正常值,即位于[MIN_MONEY,MAX_MONEY]
         */
        REMAINING_MONEY_IS_OK
    }

    /**
     * 最大的红包是平均值的 TIMES 倍，防止某一个分配的红包较大
     */
    private static final double TIMES = 2.1F;

    private int recursiveCount = 0;

    public List<Integer> splitRedPacket(int money, int count) {
        List<Integer> packets = new ArrayList<>();

        //金额检查，如果红包最大值 * 个数 < 总金额；则需要调大最大红包 MAX_MONEY
        if (MAX_MONEY * count <= money) {
            System.err.println("请调大最大红包金额 当前MAX_MONEY=[" + MAX_MONEY + "]");
            throw new IllegalArgumentException("money: " + money + "; count: " + count);
        }

        //计算最大红包
        int max = (int) ((money / count) * TIMES);
        max = Math.min(max, MAX_MONEY);

        for (int i = 0; i < count; i++) {
            int redPacket = randomRedPacket(money, MIN_MONEY, max, count - i);
            packets.add(redPacket);
            money -= redPacket;
        }

        return packets;
    }

    private int randomRedPacket(int totalMoney, int minMoney, int maxMoney, int count) {
        // 只有一个红包时，直接返回totalMoney
        if (count == 1) {
            return totalMoney;
        }

        // minMoney == maxMoney 时，每个红包都是minMoney
        if (minMoney == maxMoney) {
            return minMoney;
        }

        // totalMoney, totalMoney之中最小值作为maxMoney
        // 因为末期可能会出现 totalMoney < maxMoney 情形，这时最多只能分totalMoney
        maxMoney = Math.min(maxMoney, totalMoney);

        //在 minMoney到maxMoney 生成一个随机红包
        int redPacket = (int) (Math.random() * (maxMoney - minMoney) + minMoney);

        int         lastMoney = totalMoney - redPacket;
        SplitStatus status    = checkMoney(lastMoney, count - 1);

        //正常金额
        if (SplitStatus.REMAINING_MONEY_IS_OK.equals(status)) {
            return redPacket;
        }

        //如果生成当前红包后，造成剩余红包平均值过小，代表当前红包金额分配过大。重新分配，将当前红包金额变小
        if (SplitStatus.REMAINING_MONEY_IS_LESS.equals(status)) {
            recursiveCount++;
            System.out.println("recursiveCount==" + recursiveCount);
            return randomRedPacket(totalMoney, minMoney, redPacket, count);
        }

        //如果生成当前红包后，造成剩余红包平均值过大，代表当前红包金额分配过小。重新分配，将当前红包金额变大
        if (SplitStatus.REMAINING_MONEY_IS_MORE.equals(status)) {
            recursiveCount++;
            System.out.println("recursiveCount===" + recursiveCount);
            return randomRedPacket(totalMoney, redPacket, maxMoney, count);
        }

        return redPacket;
    }

    /**
     * 校验剩余的金额的平均值是否在 最小值和最大值这个范围内
     *
     * @param lastMoney remaining money
     * @param count     red packet count
     * @return @See #SplitStatus
     */
    private SplitStatus checkMoney(int lastMoney, int count) {
        double avg = lastMoney/count;
        if (avg < MIN_MONEY) {
            return SplitStatus.REMAINING_MONEY_IS_LESS;
        }

        if (avg > MAX_MONEY) {
            return SplitStatus.REMAINING_MONEY_IS_MORE;
        }

        return SplitStatus.REMAINING_MONEY_IS_OK;
    }


    public static void main(String[] args) {
        RedPacket     redPacket  = new RedPacket();
        int           money      = 20000;
        int           count      = 10;
        List<Integer> redPackets = redPacket.splitRedPacket(money, count);
        System.out.println(redPackets);

        int sum = 0;
        for (Integer red : redPackets) {
            sum += red;
        }
        assert sum == money;
    }

}
