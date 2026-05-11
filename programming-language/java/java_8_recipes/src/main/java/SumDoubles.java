import java.util.stream.Stream;

public class SumDoubles {
    public static void main(String[] args) {
        Integer sum = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .reduce(0, (acc, n) -> {
                    System.out.printf("acc=%d, n=%d%n", acc, n);
                    return acc + 2 * n;
                });

        System.out.printf("Sum = %s%n", sum);
    }
}
