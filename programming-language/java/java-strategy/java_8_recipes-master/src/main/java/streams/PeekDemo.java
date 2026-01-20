package streams;

import java.util.logging.Logger;
import java.util.stream.IntStream;

public class PeekDemo {
    private Logger logger = Logger.getLogger(PeekDemo.class.getName());

    public int sumUpTo(int num) {
        return IntStream.rangeClosed(1, num)
                        .sum();
    }

    public int sumEachDoubleUpTo(int num) {
        return IntStream.rangeClosed(1, num)
//                        .map(n -> {
//                            System.out.println("The value of n before doubling is " + n);
//                            return n;
//                        })
                        .map(n -> n * 2)
                        .sum();
    }

    public int sumDoublesDivisibleBy3(int start, int end) {
        return IntStream.rangeClosed(start, end)
                        .peek(n -> System.out.printf("original: %d%n", n))
                        .map(n -> n * 2)
                        .peek(n -> System.out.printf("doubled : %d%n", n))
                        .filter(n -> n % 3 == 0)
                        .peek(n -> System.out.printf("filtered: %d%n", n))
                        .sum();
    }

}
