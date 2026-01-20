package datetime;

import java.time.LocalDate;
import java.time.Month;

public class AddingAndSubtracting {
    public static void main(String[] args) {
        LocalDate groundHogDay = LocalDate.of(2017, Month.FEBRUARY, 2);
        System.out.println(groundHogDay.plusDays(3));
        System.out.println(groundHogDay.plusWeeks(5));
        System.out.println(groundHogDay.plusMonths(7));
        System.out.println(groundHogDay.plusYears(2));

        System.out.println(groundHogDay.minusDays(3));
        System.out.println(groundHogDay.minusWeeks(5));
        System.out.println(groundHogDay.minusMonths(7));
        System.out.println(groundHogDay.minusYears(2));
    }
}
