package datetime;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class PaydayAdjusterTest {
    @Test
    public void payDay() {
        TemporalAdjuster adjuster = new PaydayAdjuster();
        // 当月1号到14号之间（包含1号和14号）的员工，他们的工资发放日会被调整到当月的14号。
        IntStream.rangeClosed(1, 14)
                .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
                .forEach(date ->
                    assertEquals(14, date.with(adjuster).getDayOfMonth()));

        // 当月15号到31号之间（包含15号和31号）的员工，他们的工资发放日会被调整到当月的最后一天（31号）
        IntStream.rangeClosed(15, 31)
                .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
                .forEach(date ->
                        assertEquals(31, date.with(adjuster).getDayOfMonth()));

        // 例子：
        // 如果一家公司规定 15号 是结算分界线：
        // 小王 在 7月10号 上班：他属于7月上半月入职，公司在 7月14号 就发给他10号-13号的工资。
        // 小李 在 7月20号 上班：他属于7月下半月入职，公司会让他等到 7月31号，到时候一次性发给他20号-31号的工资。
    }

    @Test
    public void payDayWithMethodRef() {
        IntStream.rangeClosed(1, 14)
                .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
                .forEach(date ->
                        assertEquals(14, date.with(Adjusters::adjustInto).getDayOfMonth()));

        IntStream.rangeClosed(15, 31)
                .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
                .forEach(date ->
                        assertEquals(31, date.with(Adjusters::adjustInto).getDayOfMonth()));
    }
}