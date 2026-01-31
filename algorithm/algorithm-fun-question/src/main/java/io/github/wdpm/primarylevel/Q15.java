package io.github.wdpm.primarylevel;

import java.util.HashMap;
import java.util.Map;

public class Q15 {
    static Map<String, Integer> memoMap = new HashMap<>();

    public static void main(String[] args) {
        int N = 20;
        int maxSteps = 4;
        int move = moveByMemo(0, N, maxSteps);
        System.out.println(move);//141977
    }

    //当N很大时，考虑内存化
    private static int moveByMemo(int a, int b, int maxSteps) {
        String key = "[" + a + "," + b + "]";
        if (!memoMap.containsKey(key)) {
            if (a > b) {
                memoMap.put(key, 0);
                return 0;
            }
            if (a == b) {
                memoMap.put(key, 1);
                return 1;
            }

            int cnt = 0;
            for (int i = 1; i <= maxSteps; i++) {
                for (int j = 1; j <= maxSteps; j++) {
                    cnt += moveByMemo(a + i, b - j, maxSteps);
                }
            }
            return cnt;
        }

        return memoMap.get(key);
    }

    private static int move(int a, int b, int maxSteps) {
        if (a > b) return 0;
        if (a == b) return 1;

        int cnt = 0;
        for (int i = 1; i <= maxSteps; i++) {
            for (int j = 1; j <= maxSteps; j++) {
                cnt += move(a + i, b - j, maxSteps);
            }
        }
        return cnt;
    }
}
