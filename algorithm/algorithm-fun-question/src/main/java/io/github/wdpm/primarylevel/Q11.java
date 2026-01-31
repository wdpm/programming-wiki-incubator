package io.github.wdpm.primarylevel;
//如下例所示，用斐波那契数列中的每个数除以其
//数位上所有数字之和。请继续例中的计算，
// 求出后续5个最小的能整除的数。
//例） 2　 →　2÷2
//3　 →　3÷3
//5　 →　5÷5
//8　 →　8÷8
//21　 →　21÷3　 … 2＋ 1 ＝ 3，因而除以3
//144　→　144÷9 … 1 ＋ 4 ＋ 4 ＝ 9，因而除以9

import java.util.HashMap;

public class Q11 {
    //类级别初始化map变量
    static final HashMap<Integer, Long> map = new HashMap<>();

    public static void main(String[] args) {
//        V1(11);
        V2(11);
    }

    private static void V2(int cnt) {
        int times = 0;
        int n = 2;
        while (true) {
            long fib = fib(n);
            if (fib % sumOfDigits(fib) == 0) {
                System.out.println(fib);
                if (++times == cnt) {
                    break;
                }
            }
            n++;
        }
    }

    private static void V1(int cnt) {
        int times = 0;
        long a0 = 1;
        long a1 = 1;
        while (true) {
            long temp = a1;
            a1 = a0 + a1;// new a1= old a0 + old a1

            //分离a1所有位数上的值，求和
            int sum = sumOfDigits(a1);
            if (a1 % sum == 0) {
                System.out.println(a1);
                if (++times == cnt) {
                    break;
                }
            }
            a0 = temp;// new a0 = old a1
        }
    }

    //当n很大时，递归深度过大。执行无结果。考虑内存中缓存结果。
    private static long fib(int n) {
        if (map.get(n) == null) {
            if (n == 0 || n == 1) return 1;
            long res = fib(n - 2) + fib(n - 1);
            map.put(n, res);
            return res;
        }
        return map.get(n);
    }

    private static int sumOfDigits(long num) {
        int sum = 0;
        while (num > 0) {
            long reminder = num % 10;
            sum += reminder;
            num = num / 10;
        }
        return sum;
    }


}

//2
//3
//5
//8
//21
//144
//2584
//14930352
//86267571272
//498454011879264
//160500643816367088