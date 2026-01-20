import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ReduceProduct {
    public static void main(String[] args) {
        OptionalLong product = LongStream.rangeClosed(1, 5)
                .reduce((acc, n) -> acc * n);
        System.out.println(product);

        long productValue = LongStream.rangeClosed(1, 5)
                .reduce(1L, (acc, n) -> acc * n);


        System.out.println("Reduce default version");
        int sum1 = IntStream.rangeClosed(1, 10)
                .reduce(0, (acc, n) -> {
                    System.out.println("acc=" + acc + ", n=" + n);
                    return acc + 2 * n;
                });
        System.out.println(sum1);

        int sum = IntStream.rangeClosed(1, 10)
                .reduce(0, (acc, n) -> {
                    System.out.println("acc=" + acc + ", n=" + n);
                    return acc + n;
                });
        System.out.println(sum);

        int total = IntStream.rangeClosed(1, 10)
                .reduce(0, Integer::sum);

/*        long productOptional = LongStream.rangeClosed(1, 5)
                .reduce((acc, n) -> {
                    System.out.println("acc=" + acc + ", n=" + n);
                    return acc * n;
                }).orElse(0);
        System.out.println(productOptional);

        long prod = LongStream.rangeClosed(1, 5)
                .reduce(1, (acc, n) -> {
                    System.out.printf("acc=%d, n=%d%n", acc, n);
                    return acc * n * 2;
                });
        System.out.println(prod); */
    }
}
