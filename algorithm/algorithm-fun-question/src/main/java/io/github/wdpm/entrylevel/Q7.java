package io.github.wdpm.entrylevel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//把年月日表示为YYYYMMDD 这样的8 位整数，然后把这个整数转换成
//二进制数并且逆序排列，再把得到的二进制数转换成十进制数，求与原日期一
//致的日期。求得的日期要在上一次东京奥运会（1964 年10 月10 日）到下一
//次东京奥运会（预定举办日期为2020 年7 月24 日）之间。
//例）日期为1966年7月13日时
//① YYYYMMDD 格式→ 19660713
//② 转换成二进制数→ 1001010111111111110101001
//③ 逆序排列→ 1001010111111111110101001
//④ 把逆序排列得到的二进制数转换成十进制数→ 19660713
//……回到1966 年7月13日（最初的日期）
public class Q7 {
    public static void main(String[] args) {
        LocalDate begin = LocalDate.of(1964, 10, 10);
        LocalDate end = LocalDate.of(2020, 7, 24);
        for (LocalDate date = begin; date.isBefore(end); date = date.plusDays(1)) {
//            https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
            //Patterns for Formatting and Parsing
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMdd");//D表示年单位计算的天数
            String original = formatter.format(date);//19660713
            String bin = Integer.toString(Integer.parseInt(original, 10), 2);
            StringBuilder binReverse = (new StringBuilder(bin)).reverse();
            String dest;
            try {
                dest = Integer.toString(Integer.parseInt(binReverse.toString(), 2), 10);
                if (original.equals(dest)) {
                    System.out.println(original);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}

//19660713
//19660905
//19770217
//19950617
//20020505
//20130201