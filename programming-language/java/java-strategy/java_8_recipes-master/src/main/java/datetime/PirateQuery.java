package datetime;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;

public class PirateQuery {
    public static long daysUntilPirateDay(TemporalAccessor temporal) {
        int day = temporal.get(ChronoField.DAY_OF_MONTH);
        int month = temporal.get(ChronoField.MONTH_OF_YEAR);
        int year = temporal.get(ChronoField.YEAR);
        LocalDate date = LocalDate.of(year, month, day);
        LocalDate tlapd = LocalDate.of(year, Month.SEPTEMBER, 19);
        if (date.isAfter(tlapd)) {
            tlapd = tlapd.plusYears(1);
        }
        return ChronoUnit.DAYS.between(date, tlapd);
    }
}
