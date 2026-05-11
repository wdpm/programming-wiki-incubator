package streams;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomStreams {
    public static final int LIMIT = 5;

    public static void main(String[] args) {
        DoubleSummaryStatistics stats =
                Stream.iterate(BigDecimal.ONE,
                        b -> b.add(BigDecimal.ONE))
                .limit(1_000_000)
                .mapToDouble(BigDecimal::doubleValue)
                .summaryStatistics();
        System.out.println(stats);

        BigDecimal total = Stream.iterate(BigDecimal.ONE, b -> b.add(BigDecimal.ONE))
                .limit(1_000_000)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Sum of 1,000,000 big decimals: " + total);

        Random r = new Random();
        r.ints(LIMIT)
                .sorted()
                .forEach(System.out::println);

        r.doubles(LIMIT, 0, 0.5)
                .sorted()
                .forEach(System.out::println);

        List<Long> longs = r.longs(LIMIT)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(longs);

        List<Integer> listOfInts = r.ints(LIMIT, 10, 20)
//                .collect(Collectors.toList());
                .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        System.out.println(listOfInts);

        SecureRandom sr = new SecureRandom();
        List<Integer> integers = sr.ints(LIMIT, 50, 100)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(integers);
    }
}
