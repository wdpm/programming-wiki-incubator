import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Primes {
    public boolean isPrime(int num) {
        int limit = (int) (Math.sqrt(num) + 1);
        return num == 2 || num > 1 && IntStream.range(2, limit)
                .noneMatch(divisor -> num % divisor == 0);
    }

    public int nextPrime(int num) {
        OptionalInt optionalInt = IntStream.iterate(num + 1, n -> n + 1)
                .filter(this::isPrime)
                .findFirst();

        return optionalInt.orElseThrow(IllegalArgumentException::new);
    }
}
