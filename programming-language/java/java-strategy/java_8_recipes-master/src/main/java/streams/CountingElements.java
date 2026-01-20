package streams;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountingElements {
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public static void main(String[] args) {
        long count = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .count();
        System.out.printf("There are %d elements in the stream%n", count);

        count = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .collect(Collectors.counting());
        System.out.printf("There are %d elements in the stream%n", count);
    }
}
