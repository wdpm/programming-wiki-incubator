package streams;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReduceDemo {
    @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        int sum = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> x + y).orElse(0);
        System.out.println(sum);

        sum = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> {
                    System.out.printf("x=%d, y=%d%n", x, y);
                    return x + y;
                }).orElse(0);
        System.out.println(sum);

        sum = IntStream.rangeClosed(1, 10)
                .reduce(Integer::sum).orElse(0);
//
//        sum = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//                .reduce(0, Integer::sum);
//
//
//        // Off-by-one error: first value doesn't get doubled
        int doubleSum = IntStream.rangeClosed(1, 10)
                .reduce((acc, n) -> acc + 2 * n).orElse(0);
        System.out.println(doubleSum);

        // off-by-one with print
        doubleSum = IntStream.rangeClosed(1, 10)
                .reduce((acc, n) -> {
                    System.out.printf("acc = %d, n = %d%n", acc, n);
                    return acc + 2 * n;
                }).orElse(0);
        System.out.println(doubleSum);

        // correct
        doubleSum = IntStream.rangeClosed(1, 10)
                .reduce(0, (acc, n) -> {
                    System.out.printf("Acc=%d, n=%d%n", acc, n);
                    return acc + 2 * n;
                });
        System.out.println(doubleSum);

        doubleSum = IntStream.rangeClosed(1, 10)
                .reduce(0, Integer::sum);
        System.out.println(doubleSum);

        BigDecimal total = Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE))
                .limit(10)
                .reduce(BigDecimal.ZERO, (acc, val) -> acc.add(val));
        System.out.println("The total is " + total);

        Integer max = Stream.of(3, 1, 4, 1, 5, 9)
                .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println("The max value is " + max);


        /* String example */
        // Inefficient, but works
        String s = Stream.of("this", "is", "a", "list")
                .reduce("", String::concat);
        System.out.println(s);

        // Better, but verbose
        s = Stream.of("this", "is", "a", "list")
                .collect(() -> new StringBuilder(),
                        (sb, str) -> sb.append(str),
                        (sb1, sb2) -> sb1.append(sb2))
                .toString();

        // Better and simpler
        s = Stream.of("this", "is", "a", "list")
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append)
                .toString();

        // Best (or at least simplest)
        s = Stream.of("this", "is", "a", "list")
                .collect(Collectors.joining());

        List<Book> books = Arrays.asList(
                new Book(1, "Modern Java Recipes"),
                new Book(2, "Making Java Groovy"),
                new Book(3, "Gradle Recipes for Android"));

        // Note: this is the HARD way; see AddCollectionToMap for easier ways
        SortedMap<Integer, Book> bookMap = books.stream()
                .reduce(new TreeMap<Integer, Book>(),  // identity for putAll
                        (map, book) -> {               // add a single book to map
                            map.put(book.getId(), book);
                            return map;
                        },
                        (map1, map2) -> {              // join multiple maps
                            map1.putAll(map2);
                            return map1;
                        });

        bookMap.forEach((k,v) -> System.out.println(k + ": " + v));

    }
}
