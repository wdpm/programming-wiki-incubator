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

        // first 是并行流（调用了 .parallel()）
        // second 和 third 是串行流
        // Stream.concat 方法的行为规则是：如果任意一个输入流是并行的，则返回的流也是并行的。
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

        // Stream.of(first, second, third, fourth) 创建的外层流是串行流（没有调用 .parallel()）
        // 因此 flatMap 返回的 total 也是串行流
        // 内层 first 虽然是并行流，但它是作为元素存在的，不影响外层流的并行属性
        System.out.println(Stream.of(first, second, third, fourth).isParallel());

        total = Stream.of(first, second, third, fourth)
                .flatMap(Function.identity());
        System.out.println(total.isParallel());
    }
}
