package datetime;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import static java.time.temporal.ChronoUnit.*;

public class DaysToElection {
    private static String pluralize(long num) {
        return num == 1 ? "" : "s";
    }

    public static void main(String[] args) {
        LocalDate electionDay = LocalDate.of(2020, Month.NOVEMBER, 3);
        LocalDate today = LocalDate.now();

        // Using "between"
        System.out.printf("%d days to go...%n", DAYS.between(today, electionDay));

        long years = YEARS.between(today, electionDay);
        long months = MONTHS.between(today.plusYears(years), electionDay);
        long days = DAYS.between(today.plusYears(years).plusMonths(months), electionDay);
        System.out.printf("%d year%s, %d month%s, and %d day%s%n",
                years,  pluralize(years),
                months, pluralize(months),
                days,   pluralize(days));

        // Using "until"
        Period until = today.until(electionDay);
        System.out.println(today.until(electionDay));

        years = until.getYears();
        months = until.getMonths();
        days = until.getDays();
        System.out.printf("%d year%s, %d month%s, and %d day%s%n",
                years,  pluralize(years),
                months, pluralize(months),
                days,   pluralize(days));
    }
}
