package io.github.wdpm.primarylevel;
//求在计算平方根的时候，最早让0~9 的数字全部出现的最小整数。注意这里
//只求平方根为正数的情况，并且请分别求包含整数部分的情况和只看小数部分的
//情况。
//例） 2 的平方根：1.414213562373095048…
//（0 ~ 9 全部出现需要19位）

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Q12 {
    public static void main(String[] args) {
        int i = 1;
        while (true) {
            i++;
            double sqrt = Math.sqrt(i);
            String sqrtStr = String.valueOf(sqrt);
            String[] parts = sqrtStr.split("\\.");//. |需要转义

            // include integer part
            String integerPart = parts[0];
            String decimalPart = parts[1];
            String[] integers = integerPart.split("");
            int M = integers.length;
            String[] decimals = decimalPart.split("");
            int N = decimals.length;

            Set<String> set = new HashSet<>();
            int cnt = 0;
            for (int j = 0; j < M && cnt < 10; j++, cnt++) {
                set.add(integers[j]);
            }

            for (int j = 0; j < N && cnt < 10; j++, cnt++) {
                set.add(decimals[j]);
            }

            if (set.size() == 10) {
                System.out.println(i);
                break;
            }
        }

        int j = 1;
        while (true) {
            j++;
            double sqrt = Math.sqrt(j);
            String sqrtStr = String.valueOf(sqrt);
            String[] parts = sqrtStr.split("\\.");//. |需要转义
            // only check decimal part
            String decimalPart = parts[1];
            String[] split = decimalPart.split("");
            String[] strings = Arrays.copyOfRange(split, 0, 10);//只取小数部分前十个数字
            Set<String> decimals = new HashSet<>(Arrays.asList(strings));
            if (decimals.size() == 10) {
                System.out.println(j);
                break;
            }
        }
    }
}

//1362
//143