package io.github.wdpm.entrylevel;//轮盘的最大值

//欧式规则
//        0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6,
//        27, 13, 36, 11, 30, 8, 23, 10, 5, 24,
//        16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7,
//        28, 12, 35, 3, 26

//美式规则
//        0, 28, 9, 26, 30, 11, 7, 20, 32, 17, 5, 22, 34, 15, 3, 24, 36, 13, 1, 00, 27, 10,
//        25, 29, 12, 8, 19, 31, 18, 6, 21, 33, 16, 4, 23, 35, 14, 2

//举个例子，当n ＝ 3 时，按照欧式规则得到的和最大的组合是36,11, 30 这个组合，和为77；
//而美式规则下则是24, 36, 13 这个组合，得到的和为 73

//当2 ≤ n ≤ 36 时，求连续n 个数之和最大的情况，
// 并找出满足条件“欧式规则下的和小于美式规则下的和”的n 的个数。
public class Q10 {
    public static void main(String[] args) {
        int[] european = {0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36,
                11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9,
                22, 18, 29, 7, 28, 12, 35, 3, 26};
        int[] american = {0, 28, 9, 26, 30, 11, 7, 20, 32, 17, 5, 22, 34, 15,
                3, 24, 36, 13, 1, 0, 27, 10, 25, 29, 12, 8, 19, 31,
                18, 6, 21, 33, 16, 4, 23, 35, 14, 2};

        int n = 3;
        int max1 = sumMaxOfNNumber(european, n);
        int max2 = sumMaxOfNNumber(american, n);
        System.out.println(max1);//77
        System.out.println(max2);//73

        int cnt = 0;
        for (int i = 2; i <= 36; i++) {
            int e = sumMaxOfNNumber(european, i);
            int a = sumMaxOfNNumber(american, i);
            if (e < a) {
                cnt++;
            }
        }
        System.out.println("cnt: " + cnt);
    }

    private static int sumMaxOfNNumber(int[] nums, int n) {
        //todo check 2<=n<=36

        int sum = 0;
        //init sum
        for (int j = 0; j < n; j++) {
            sum += nums[j];
        }

        int ans = sum;
        int currentSum = sum;
        for (int i = 0; i < nums.length; i++) {
            // 新和=旧和-左边一个+右边一个
            int leftIndex = i;
//            System.out.println("leftIndex: " + leftIndex);
            int leftEle = nums[leftIndex];

            int rightIndex = n + i;
            int rightFinalIndex;
//            System.out.println("rightIndex: " + rightIndex);
            if (rightIndex >= nums.length) {//超过尾部，回头找
                rightFinalIndex = rightIndex % nums.length;
            } else {
                rightFinalIndex = rightIndex;
            }
            int rightEle = nums[rightFinalIndex];

            currentSum = currentSum - leftEle + rightEle;
            ans = Math.max(currentSum, ans);
        }

        return ans;
    }
}
