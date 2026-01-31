package io.github.wdpm.entrylevel;

import java.util.ArrayList;
import java.util.List;
/*
有100 张写着数字1~100 的牌，并按顺序排列着。最开始所有牌都是背面朝上放置。某人从第2 张牌开始，隔1 张牌翻牌。然后第2,
4, 6, …, 100 张牌就会变成正面朝上。接下来，另一个人从第3 张牌开始，隔2 张牌翻牌（原本背面朝上的，翻转成正面朝上；
原本正面朝上的，翻转成背面朝上）。再接下来，又有一个人从第4 张牌开始，隔3 张牌翻牌。
像这样，从第n 张牌开始，每隔n－1 张牌翻牌，直到没有可翻动的牌为止
 */

//求当所有牌不再变动时，所有背面朝上的牌的数字。
public class Q3 {
    public static void main(String[] args) {
        Q3V1();
//        Q3V2();
    }

    private static void Q3V2() {
        int N = 100;
        for (int i = 1; i <= N; i++) {
            boolean flag = false;
            for (int j = 1; j <= i; j++) {
                if (i % j == 0) {
                    flag = !flag;
                }
            }
            if (flag) {
                System.out.print(i + " ");
            }
        }
    }

    private static void Q3V1() {
        int N = 100;
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(false);//false表示背面朝上
        }

        //外层循坏表示间隔 i=2, i<=100
        for (int i = 2; i <= N; i++) {
            int j = i - 1;
//            System.out.println("interval: "+i);
            //内层循坏表示从左到右翻牌
            while (j < list.size()) {
                list.set(j, !list.get(j));
                j += i;
            }
//            System.out.println(list);
        }

        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i)) {
                int number = i + 1;
                System.out.print(number + " ");//1 4 9 16 25 36 49 64 81 100
            }
        }
    }
}

// V2
//如果翻牌操作进行了奇数次，则最后是正面朝上；如果进行了偶数次，则最后是背面朝上。
//这个问题等价于“寻找被翻转次数为偶数的牌”。
//而翻牌操作的时机则是“翻牌间隔数字是这个数的约数时”，
//因此相当于寻找拥有偶数个“1以外的约数”的数字,奇数个“约数”的数字。
//只有完全平方数符合要求。
//例如 4约数有1 2 4, 2的时候翻成正面，4的时候返回反面。
//问题本质是求：1-100之间的完全平方数，利用约数个数是奇数个进行求解。
