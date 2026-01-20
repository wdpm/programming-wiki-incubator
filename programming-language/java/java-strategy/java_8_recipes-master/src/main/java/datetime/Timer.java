package datetime;

import java.time.Duration;
import java.time.Instant;

public class Timer {
    public static double getTiming(Instant start, Instant end) {
        return Duration.between(start, end).toMillis() / 1000.0;
    }

    public static void main(String[] args) {

        Instant start = Instant.now();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant end = Instant.now();
        System.out.println(getTiming(start, end) + " seconds");
    }
}
