package datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Set;

public class JavaTimeDemos {
    public static void main(String[] args) {
        Instant now = Instant.now();
        System.out.printf("Instant.now(): %s%n", now);

        Instant then = now.plus(1, ChronoUnit.DAYS);
        Duration elapsed = Duration.between(now, then);
        System.out.printf("Duration after adding 1 day: %s%n", elapsed);
        System.out.printf("Duration in days: %d days%n", elapsed.toDays());
        System.out.printf("Duration in hours: %d hours%n", elapsed.toHours());

        then = now.plus(1, ChronoUnit.DAYS)
                .plus(1, ChronoUnit.HALF_DAYS)
                .plus(1, ChronoUnit.HOURS)
                .plus(10, ChronoUnit.MINUTES)
                .plusSeconds(10)
                .plusMillis(100)
                .plusNanos(100);
        System.out.printf("Adding units to now: %s%n", then);

        LocalDate date1 = LocalDate.now();
        System.out.printf("LocalDate.now() %s%n", date1);

        LocalDate groundHogDay =
                LocalDate.of(2018, Month.FEBRUARY, 2);
        System.out.printf("Groundhog Day, 2016: %s%n", groundHogDay);

        LocalDate firstDayofSpring =
                LocalDate.of(2018, Month.MARCH, 20);
        System.out.printf("First day of Spring, 2016: %s%n", firstDayofSpring);

        // Day of week available
        System.out.println("First day of spring this year falls on a " +
                firstDayofSpring.getDayOfWeek());

        // until with ChronoUnit gives days between
        long gap = groundHogDay.until(firstDayofSpring, ChronoUnit.DAYS);
        System.out.println("There are " + gap +
                " days between GroundHog Day and 1st day of Spring");
        System.out.printf("That's %d weeks and %d days%n", gap / 7, gap % 7);

        // Fun with time zones
        Set<String> availableTimeZones = ZoneId.getAvailableZoneIds();
        System.out.printf("Total number of time zones: %d%n",
                availableTimeZones.size());
        availableTimeZones.stream()
                .filter(name -> name.contains("America"))
                .forEach(System.out::println);

        ZonedDateTime missing = ZonedDateTime.of(
                LocalDate.of(2015, Month.MARCH, 8),
                LocalTime.of(2, 30),      // skipped by daylight savings
                ZoneId.of("America/Chicago"));
        System.out.println(missing);

        ZonedDateTime spring =
                ZonedDateTime.of(2015, 3, 20, 0, 0, 0, 0, ZoneId.systemDefault());

        System.out.println(
                DateTimeFormatter.RFC_1123_DATE_TIME.format(spring));
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(Locale.FRANCE);
        System.out.println(formatter.format(spring));

        LocalDateTime dateTime = LocalDateTime.of(2017, Month.JULY, 4, 13, 20, 10);
        ZonedDateTime nyc = dateTime.atZone(ZoneId.of("America/New_York"));
        System.out.println(nyc);

        ZonedDateTime london = nyc.withZoneSameInstant(ZoneId.of("Europe/London"));
        System.out.println(london);

    }
}
