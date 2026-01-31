//所谓字母算式，就是用字母表示的算式，规则是相同字母对应相同数字，
// 不同字母对应不同数字，并且第一位字母的对应数字不能是0

//譬如给定算式We×love ＝ CodeIQ，则可以对应填上下面这些数字以使之成立。
//W ＝ 7, e ＝ 4, l ＝ 3, o ＝ 8, v ＝ 0, C ＝ 2, d ＝ 1, I ＝ 9, Q ＝ 6

//求使下面这个字母算式成立的解法有多少种？
//READ ＋ WRITE ＋ TALK ＝ SKILL

//分析：
// 使用一个set收集所有数字，排除重复
// 对set中的每一个数字进行[0,9]的随机赋值，注意R W T S 不能为0，计算结果进行判断即可
package io.github.wdpm.primarylevel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Q13TODO {
    public static void main(String[] args) {
        String rawString = "READ+WRITE+TALK=SKILL";
//        \P{Alpha} matches any non-alphabetic character (as opposed to \p{Alpha},
//        which matches any alphabetic character).
//        + indicates that we should split on any continuous string of such characters
        String[] split = rawString.split("\\P{Alpha}+");
        HashSet<String> wordSet = new HashSet<>();
        HashSet<String> nonZeroWordSet = new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] strings = s.split("");
            String nonZeroWord = strings[0];

            nonZeroWordSet.add(nonZeroWord);
            wordSet.addAll(Arrays.asList(strings));
        }
        HashMap<String, Integer[]> wordValueMap = new HashMap<>();

        //init value range
        for (String word : wordSet) {
            if (nonZeroWordSet.contains(word)) {
                wordValueMap.put(word, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
            } else {
                wordValueMap.put(word, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
            }
        }

//        for (Map.Entry<String, Integer[]> entry : wordValueMap.entrySet()) {
//           System.out.println(entry.getKey()+":"+ Arrays.toString(entry.getValue()));
//        }

//        A:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//        R:[1, 2, 3, 4, 5, 6, 7, 8, 9]
//        S:[1, 2, 3, 4, 5, 6, 7, 8, 9]
//        D:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//        T:[1, 2, 3, 4, 5, 6, 7, 8, 9]
//        E:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//        W:[1, 2, 3, 4, 5, 6, 7, 8, 9]
//        I:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//        K:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//        L:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        //todo 这是一个全排列的问题 PermutationIterator
    }
}
