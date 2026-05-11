package streams;

import java.util.stream.IntStream;

public class LazyStreams {

    public static int multByTwo(int n) {
        System.out.println("Inside multByTwo with arg n = " + n +
                " and thread " + Thread.currentThread().getName());
        return n * 2;
    }

    public static boolean modByThree(int n) {
        System.out.println("Inside modByThree with n = " + n +
                " and thread " + Thread.currentThread().getName());
        return n % 3 == 0;
    }

    public static void main(String[] args) {
        // Find first even double between 200 and 400 divisible by 3
        int firstEvenDoubleDivBy3 = IntStream.range(100, 200)
                .filter(n -> n % 3 == 0)
                .map(n -> n * 2)
                .findFirst().orElse(0);
        System.out.println(firstEvenDoubleDivBy3);

        // Demonstrate laziness using print statements
        firstEvenDoubleDivBy3 = IntStream.range(100, 2_000_000)
                // .parallel()
                .filter(LazyStreams::modByThree)
                .map(LazyStreams::multByTwo)
                .findFirst().orElse(0);
        System.out.printf("First even divisible by 3 is %d%n", firstEvenDoubleDivBy3);
    }
}
