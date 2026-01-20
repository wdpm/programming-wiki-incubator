package datetime;

import java.time.Month;

public class MonthMethods {
    public static void main(String[] args) {
        System.out.println("Days in Feb in a leap year: " +
                Month.FEBRUARY.length(true));
        System.out.println("Day of year for first day of Aug (leap year): " +
                Month.AUGUST.firstDayOfYear(true));
        System.out.println("Month.of(1): " + Month.of(1));
        System.out.println("Adding two months: " +
                Month.JANUARY.plus(2));
        System.out.println("Subtracting a month: " +
                Month.MARCH.minus(1));
    }
}
