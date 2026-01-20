package concurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ParallelDemo {

    public static int doubleIt(int n) {
        try {
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + " with n=" + n);
        } catch (InterruptedException ignore) {
        }
        return n * 2;
    }

    @SuppressWarnings("Convert2streamapi")
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(3, 1, 4, 1, 5, 9);

        // Non-functional, with shared mutable state
        int total = 0;
        for (int i : ints) {
            total += i;
        }
        System.out.println("Total = " + total);

        // Use Java 8 Streams
        total = 0; // local variable; not an attribute
//        IntStream.of(3, 1, 4, 1, 5, 9)
//                .forEach(n -> total += n); // not legal; total needs to be effectively final
//        System.out.println("Total = " + total);

        total = IntStream.of(3, 1, 4, 1, 5, 9, 2, 6)
                .sum();
        System.out.println("Total = " + total);

        Instant before = Instant.now();
        total = IntStream.of(3, 1, 4, 1, 5, 9)
                .parallel()
                .map(ParallelDemo::doubleIt)
                .sum();
        Instant after = Instant.now();
        Duration duration = Duration.between(before, after);
        System.out.println("Total of doubles = " + total);
        System.out.println("time = " + duration.toMillis() + " ms");
    }
}
