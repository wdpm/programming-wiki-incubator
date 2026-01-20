package streams;

import java.util.function.Function;
import java.util.stream.Stream;

public class ConcatStreams {
    public static void main(String[] args) {
        Stream<String> first = Stream.of("a", "b", "c");
        Stream<String> second = Stream.of("X", "Y", "Z");

        Stream<String> both = Stream.concat(first, second);
        both.forEach(System.out::println);

        // Need to make new ones -- first and second are now closed
        first = Stream.of("a", "b", "c");
        second = Stream.of("X", "Y", "Z");
        Stream<String> third = Stream.of("alpha", "beta", "gamma");

        Stream<String> allThree = Stream.concat(Stream.concat(first, second), third);
        allThree.forEach(System.out::println);

        first = Stream.of("a", "b", "c");
        second = Stream.of("X", "Y", "Z");
        third = Stream.of("alpha", "beta", "gamma");
        Stream<String> fourth = Stream.empty();

        Stream<String> total = Stream.of(first, second, third, fourth)
                .reduce(Stream.empty(), Stream::concat);
        total.forEach(System.out::println);

        first = Stream.of("a", "b", "c");
        second = Stream.of("X", "Y", "Z");
        third = Stream.of("alpha", "beta", "gamma");
        fourth = Stream.empty();

        Stream.of(first, second, third, fourth)
                .flatMap(Function.identity())
                .forEach(System.out::println);

        first = Stream.of("a", "b", "c").parallel();
        second = Stream.of("X", "Y", "Z");
        third = Stream.of("alpha", "beta", "gamma");

        total = Stream.of(first, second, third)
                .reduce(Stream.empty(), Stream::concat);
        System.out.println(total.isParallel());

        first = Stream.of("a", "b", "c").parallel();
        second = Stream.of("X", "Y", "Z");
        third = Stream.of("alpha", "beta", "gamma");
        fourth = Stream.empty();

        total = Stream.of(first, second, third, fourth)
                .flatMap(Function.identity());
        System.out.println(total.isParallel());
    }
}
