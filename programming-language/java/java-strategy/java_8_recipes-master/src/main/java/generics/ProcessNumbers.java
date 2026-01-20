package generics;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Based on examples from the Java Tutorial
 */
public class ProcessNumbers {

    public static double sumListAsDoubles(List<? extends Number> numbers) {
        return numbers.stream()
                .mapToDouble(Number::doubleValue)
                .sum();
    }

    public static void printList(List<?> elements) {
        elements.forEach(System.out::println);
    }

    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<BigDecimal> bigDecimals = Arrays.asList(
                new BigDecimal("1.0"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0"),
                new BigDecimal("4.0"),
                new BigDecimal("5.0"));

        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");

        System.out.println(sumListAsDoubles(integers));
        System.out.println(sumListAsDoubles(doubles));
        System.out.println(sumListAsDoubles(bigDecimals));

        printList(integers);
        printList(strings);
    }
}
