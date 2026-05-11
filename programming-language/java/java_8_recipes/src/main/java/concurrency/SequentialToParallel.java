package concurrency;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SequentialToParallel {
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public static void main(String[] args) {
        boolean parallel = Stream.of(3, 1, 4, 1, 5, 9)
                .isParallel();
        System.out.println(parallel);

        parallel = IntStream.iterate(1, n -> n + 1)
                .isParallel();
        System.out.println(parallel);

        parallel = DoubleStream.generate(Math::random)
                .isParallel();
        System.out.println(parallel);

        parallel = Arrays.asList(3, 1, 4, 1, 5, 9).stream()
                .isParallel();
        System.out.println(parallel);


        parallel = Stream.of(3, 1, 4, 1, 5, 9)
                .parallel()
                .isParallel();
        System.out.println(parallel);

        parallel = Arrays.asList(3, 1, 4, 1, 5, 9).parallelStream()
                .isParallel();
        System.out.println(parallel);
    }
}
