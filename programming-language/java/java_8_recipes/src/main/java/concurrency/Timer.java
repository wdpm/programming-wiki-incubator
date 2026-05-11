package concurrency;

import java.util.function.Supplier;

public class Timer {

    public static <R> R time(Supplier<R> block) {
        long start = System.nanoTime();

        R result = block.get();

        long end = System.nanoTime();
        double elapsedTime = (end - start) / 1e9;
        System.out.printf("Elapsed time: %ssec%n", elapsedTime);
        return result;
    }
}
