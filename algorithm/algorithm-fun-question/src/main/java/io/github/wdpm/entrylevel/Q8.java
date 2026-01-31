package io.github.wdpm.entrylevel;
//假设有一款不会反复清扫同一个地方的机器人，它只能前后左右移
//动。举个例子，如果第1 次向后移动，那么连续移动3 次时，就会有以
//下9 种情况（ 图6 ）。又因为第1 次移动可以是前后左右4 种情况，所以
//移动3 次时全部路径有9×4 ＝ 36 种

//求这个机器人移动12 次时，有多少种移动路径？

import java.util.Arrays;

//使用坐标表示前后左右的移动
//[0,0]表示最初的位置原点
//↑[0,1] [0,-1]↓,[1,0]→,[-1,0]←
public class Q8 {
    private static final int N = 12;
//    private static final int N = 1;//结果为4

    public static void main(String[] args) {
        int[][] origin = {{0, 0}};
        int move = move(origin);
        System.out.println(move);//324932
    }

    private static int move(int[][] origin) {
        if (origin.length == N + 1) {//means run N steps
            return 1;//表示一个路径
        }
        int cnt = 0;
        int[][] stepUnits = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] stepUnit : stepUnits) {
            int x = stepUnit[0];//x轴移动
            int y = stepUnit[1];//y轴移动
            int[] nextPos = {origin[origin.length - 1][0] + x, origin[origin.length - 1][1] + y};//(newX,newY)
            if (!hasPoint(origin, nextPos)) {//判断包含这个点
                cnt += move(appendPoint(origin, nextPos));//数组合并，将这个点加到末尾
            }
        }

        return cnt;
    }

    private static int[][] appendPoint(int[][] origin, int[] nextPos) {
        int[][] newInts = Arrays.copyOf(origin, origin.length + 1);
        newInts[origin.length] = nextPos;
        return newInts;
    }

    private static boolean hasPoint(int[][] origin, int[] nextPos) {
        for (int[] ints : origin) {
            if (ints[0] == nextPos[0] && ints[1] == nextPos[1]) {
                return true;
            }
        }
        return false;
    }

}
