package datetime;

import java.time.*;

public class NowFactoryMethod {
    public static void main(String[] args) {
        System.out.println("Instant.now():       " + Instant.now());
        System.out.println("LocalDate.now():     " + LocalDate.now());
        System.out.println("LocalTime.now():     " + LocalTime.now());
        System.out.println("LocalDateTime.now(): " + LocalDateTime.now());
        System.out.println("ZonedDateTime.now(): " + ZonedDateTime.now());

        Clock antarctica = Clock.system(ZoneId.of("Antarctica/South_Pole"));
        System.out.println(ZonedDateTime.now(antarctica));

        // Easier to do the Antarctica time this way (now() uses ZoneId directly)
        System.out.println(ZonedDateTime.now(ZoneId.of("Antarctica/South_Pole")));

        // Can create a Clock with an offset, though
        System.out.println(ZonedDateTime.now(
                Clock.offset(antarctica, Duration.ofHours(1))));

        // Other factory methods on Clock
        System.out.println(ZonedDateTime.now(Clock.systemDefaultZone()));
        System.out.println(ZonedDateTime.now(Clock.systemUTC()));

        // Clock that always returns the same instant
        Clock fixed = Clock.fixed(Instant.now(), ZoneId.of("Antarctica/South_Pole"));
        System.out.println(ZonedDateTime.now(fixed));
        System.out.println(ZonedDateTime.now(fixed));
        System.out.println(ZonedDateTime.now(fixed));
    }
}
