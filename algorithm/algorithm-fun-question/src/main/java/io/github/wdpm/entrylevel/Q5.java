package io.github.wdpm.entrylevel;
//用纸币兑换到10 日元、50 日元、100 日元和500 日元硬币的组合，且每种硬币的数量都足够多。
//兑换时，允许机器兑换出本次支付时用不到的硬币。只允许机器最多兑换出15 枚硬币。
//譬如用1000 日元纸币兑换时，就不能兑换出“100 枚10 日元硬币”的组合

//求兑换1000 日元纸币时会出现多少种组合？注意，不计硬币兑出的先后顺序

import java.util.*;

//本质上是组合问题
public class Q5 {
    public static void main(String[] args) {
        findCombinationV1();
    }

    private static void findCombinationV1() {
        //coin10 [0,15]
        //coin50 [0,15]
        //coin100 [0,10]
        //coin500 [0,2]
        //组合公式为 coin10 * 10+ coin50 * 50+coin100 * 10+coin500 * 2
        int combination = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(10, 15);//key为面值，value为限制次数
        map.put(50, 15);
        map.put(100, 10);
        map.put(500, 2);
        System.out.println(map.getClass());
        for (int coin10 = 0; coin10 <= map.get(10); coin10++) {
            for (int coin50 = 0; coin50 <= map.get(50); coin50++) {
                for (int coin100 = 0; coin100 <= map.get(100); coin100++) {
                    for (int coin500 = 0; coin500 <= map.get(500); coin500++) {
                        int total = coin10 * 10 + coin50 * 50 + coin100 * 100 + coin500 * 500;
                        int count = coin10 + coin50 + coin100 + coin500;
                        if (total == 1000 && count <= 15) {
                            combination++;
                            System.out.format(
                                    "coin10* %d,coin50* %d,coin100* %d,coin500* %d",
                                    coin10, coin50, coin100, coin500);
                            System.out.println();
                        }
                    }
                }

            }

        }
        System.out.println(combination);//7
    }
}
