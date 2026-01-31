package io.github.wdpm.entrylevel;
//假设要把长度为n 厘米的木棒切分为1 厘米长的小段，但是1 根木
//棒只能由1 人切分，当木棒被切分为3 段后，可以同时由3 个人分别切
//分木棒（ 图2 ）。
//求最多有m 个人时，最少要切分几次。譬如n ＝ 8，m ＝ 3 时如下
//图所示，切分4 次就可以了。

//12345678
//第一次后：1234 5678
//第二次后：12 34 56 78
//第三次后：1 2 3 4 5 6 78 （因为只有三个人，无法切四刀）
//第四次后：1 2 3 4 5 6 7 8

//也就是：
// a) 人数m>=当前块数时，可以切当前块数的次数
// b) 人数m<当前块数时，只能切m次

//求当n ＝ 20，m ＝ 3 时的最少切分次数。
//求当n ＝ 100，m ＝ 5 时的最少切分次数。
public class Q4 {
    public static void main(String[] args) {
        int cut = cut(20, 3, 1);
        System.out.println(cut);
        int cut1 = cut(100, 5, 1);
        System.out.println(cut1);
    }

    private static int cut(int n, int m, int current) {
        int count = 0;
        if (current >= n) {
            return 0;
        }
        if (m >= current) {
            int newCurrent = current * 2;
            count = 1 + cut(n, m, newCurrent);
        } else {
            int newCurrent = current + m;
            count = 1 + cut(n, m, newCurrent);
        }
        return count;
    }
}

//本题是深度优先搜索 DFS，也称为回溯法，当无法继续时（达到目标）时返回。