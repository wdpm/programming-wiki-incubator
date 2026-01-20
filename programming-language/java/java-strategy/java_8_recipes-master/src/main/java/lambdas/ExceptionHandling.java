package lambdas;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class ExceptionHandling {
    private Random random = new Random();
    private Supplier<Integer> supplier = () -> random.nextInt(10);

    private Integer divide(Integer value, Integer factor) {
        try {
            return value / factor;
        } catch (ArithmeticException e) {
            return Integer.MAX_VALUE;
        }
    }

    public List<Integer> divUsingMethod(List<Integer> values, Integer factor) {
        return values.stream()
                .map(n -> divide(n, factor))
                //.map(this::divide)
                .collect(Collectors.toList());
    }

    public List<Integer> div(List<Integer> values, Integer factor) {
        return values.stream()
                //.map(n -> n / factor)
                .map(n -> {
                    try {
                        return n / factor;
                    } catch (ArithmeticException e) {
                        return Integer.MAX_VALUE;
                    }
                })
                .collect(Collectors.toList());
    }

    private String encodeString(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> encodeValuesUsingMethod(String... values) {
        return Arrays.stream(values)
                .map(this::encodeString)
                .collect(Collectors.toList());
    }

    public List<String> encodeValues(String... values) {
        return Arrays.stream(values)
                .map(s -> {
                    try {
                        return URLEncoder.encode(s, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }


    @SuppressWarnings("Convert2Lambda")
    public List<String> encodeValuesAnonInnerClass(String... values) {
        return Arrays.stream(values)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        try {
                            return URLEncoder.encode(s, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            return "";
                        }
                    }
                })
                .collect(Collectors.toList());
    }

    private static <T, R, E extends Exception> Function<T, R> wrapper(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public List<String> encodeValuesWithWrapper(String... values) {
        return Arrays.stream(values)
                .map(wrapper(s -> URLEncoder.encode(s, "UTF-8")))
                //.map(wrapper(this::encodeToUTF8))
                .collect(Collectors.toList());
    }

    private String encodeToUTF8(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }

    public void closedStreamDemo() {
        IntStream stream = IntStream.of(3, 1, 4, 1, 5, 9);

        int sum = stream.sum();

        OptionalInt max = stream.max();
        System.out.println(max);
    }

    public void stats() {
        DoubleSummaryStatistics stats = DoubleStream.generate(Math::random)
                .limit(2_000_000)
                .summaryStatistics();

        System.out.println(stats);
        System.out.println(stats.getCount());
        System.out.println(stats.getSum());
        System.out.println(stats.getAverage());
        System.out.println(stats.getMax());
        System.out.println(stats.getMin());
    }

    public static void main(String[] args) {
        ExceptionHandling demo = new ExceptionHandling();

//        demo.stats();

        List<Integer> values = Arrays.asList(30, 10, 40, 10, 50, 90);
        List<Integer> scaled = demo.div(values, 10);
        System.out.println(scaled);

        scaled = demo.div(values, 0);
        System.out.println(scaled);

        scaled = demo.divUsingMethod(values, 0);
        System.out.println(scaled);
    }
}
