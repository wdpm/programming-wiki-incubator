package io.github.wdpm.jdk11;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Time 转换
 *
 * @author evan
 * @date 2020/5/2
 */
public class TimeConvert {
    public static void main(String[] args) {
        long oneDay   = TimeUnit.DAYS.convert(Duration.ofHours(24));
        long isOneDay = TimeUnit.DAYS.convert(Duration.ofHours(25));
        System.out.println(oneDay == 1);
        System.out.println(isOneDay == 1);

        long oneMinute = TimeUnit.MINUTES.convert(Duration.ofSeconds(60));
        System.out.println(oneMinute == 1);
    }
}
