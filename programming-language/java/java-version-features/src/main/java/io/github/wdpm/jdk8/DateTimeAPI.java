package io.github.wdpm.jdk8;

import java.time.*;

/**
 * Date-Time API，建议弃用java.util.Date和java.util.Calendar。吸收第三方库Joda-Time的精华思想。
 *
 * 知识点：
 * <li>Clock</li>
 * <li>LocalDate</li>
 * <li>LocalTime</li>
 * <li>LocalDateTime</li>
 * <li>ZonedDateTime</li>
 * <li>Duration</li>
 * <li>Period</li>
 *
 * @author evan
 * @since 2020/4/19
 */
public class DateTimeAPI {
    public static void main(String[] args) {
        // Get the system clock as UTC offset
        final Clock clock = Clock.system(ZoneId.of("Asia/Shanghai"));
        System.out.println(clock.instant());// 2020-04-19T09:20:16.182Z
        System.out.println(clock.millis());// 1587287522978

        // Get the local date and local time
        final LocalDate date          = LocalDate.now();
        final LocalDate dateFromClock = LocalDate.now(clock);
        System.out.println(date);//2020-04-19
        System.out.println(dateFromClock);//2020-04-19

        // Get the local date and local time
        final LocalTime time          = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now(clock);
        System.out.println(time);//17:20:16.198
        System.out.println(timeFromClock);//17:20:16.198

        // Get the local date/time
        final LocalDateTime datetime          = LocalDateTime.now();
        final LocalDateTime datetimeFromClock = LocalDateTime.now(clock);
        System.out.println(datetime);//2020-04-19T17:20:16.198
        System.out.println(datetimeFromClock);//2020-04-19T17:20:16.198

        // Get the zoned date/time
        final ZonedDateTime zonedDatetime          = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now(clock);
        final ZonedDateTime zonedDatetimeFromZone  = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDatetime);//2020-04-19T17:20:16.198+08:00[Asia/Shanghai]
        System.out.println(zonedDatetimeFromClock);//2020-04-19T17:20:16.198+08:00[Asia/Shanghai]
        System.out.println(zonedDatetimeFromZone);//2020-04-19T17:20:16.198+08:00[Asia/Shanghai]

        // Get duration between two dates
        final LocalDateTime from = LocalDateTime.of(2017, Month.APRIL, 16, 0, 0, 0);
        final LocalDateTime to   = LocalDateTime.of(2020, Month.APRIL, 16, 23, 59, 59);

        final Duration duration = Duration.between(from, to);
        System.out.println("Duration in days: " + duration.toDays());//1096
        System.out.println("Duration in hours: " + duration.toHours());
    }
}
