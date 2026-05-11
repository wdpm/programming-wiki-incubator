package lambdas;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CompositionDemo {
    private static int add2(int x) {
        return x + 2;
    }

    private static int mult3(int x) {
        return x * 3;
    }

    public static boolean isPerfect(int x) {
        return Math.sqrt(x) % 1 == 0;
    }

    public static boolean isPrime(int num) {
        int limit = (int) (Math.sqrt(num) + 1);
        return num == 2 || num > 1 && IntStream.range(2, limit)
                .noneMatch(divisor -> num % divisor == 0);
    }

    public static boolean isTriangular(int x) {
        double val = (Math.sqrt(8 * x + 1) - 1) / 2;
        return val % 1 == 0;
    }

    public static void main(String[] args) {

        Function<Integer, Integer> a2 = x -> x + 2;
        Function<Integer, Integer> m3 = x -> x * 3;
        Function<Integer, Integer> m3a2 = a2.compose(m3);
        Function<Integer, Integer> a2m3 = a2.andThen(m3);
        System.out.println("m3a2(1): " + m3a2.apply(1));
        System.out.println("a2m3(1): " + a2m3.apply(1));

        Function<Integer, Integer> mult3add2 = ((Function<Integer, Integer>) (x -> x + 2))
                .compose(x -> x * 3);
        System.out.println(mult3add2.apply(1));

        Function<Integer, Integer> add2mult3 = ((Function<Integer, Integer>) (x -> x + 2))
                .andThen(x -> x * 3);
        System.out.println(add2mult3.apply(1));

        mult3add2 = ((UnaryOperator<Integer>) CompositionDemo::add2).compose(CompositionDemo::mult3);
        add2mult3 = ((UnaryOperator<Integer>) CompositionDemo::add2).andThen(CompositionDemo::mult3);

        System.out.println(mult3add2.apply(1));
        System.out.println(add2mult3.apply(1));

        Function<Integer, String> plus2toString = a2.andThen(Object::toString);
        System.out.println(plus2toString.apply(1).getClass().getName());

        Function<String, Integer> parseThenAdd2 = a2.compose(Integer::parseInt);
        System.out.println(parseThenAdd2.apply("1"));

        Logger log = Logger.getLogger("my logger");
        Consumer<String> printer = System.out::println;
        Consumer<String> logger = log::info;

        Consumer<String> printThenLog = printer.andThen(logger);
        Stream.of("this", "is", "a", "stream", "of", "strings").forEach(printThenLog);

        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isDivBy5 = n -> n % 5 == 0;


    }
}
