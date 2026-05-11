package streams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamToIntStream {
    public static void main(String[] args) {
        Stream<Integer> ints = Stream.of(3, 1, 4, 1, 5, 9);

        IntStream intStream = ints.mapToInt(n -> n);
    }
}
