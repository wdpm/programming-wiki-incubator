package lambdas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgorithmsTest {
    @Test
    public void testFactorial() {
        assertAll(
                () -> assertEquals(BigInteger.ONE, Algorithms.factorial(0)),
                () -> assertEquals(BigInteger.ONE, Algorithms.factorial(1)),
                () -> assertEquals(BigInteger.valueOf(2), Algorithms.factorial(2)),
                () -> assertEquals(BigInteger.valueOf(6), Algorithms.factorial(3)),
                () -> assertEquals(BigInteger.valueOf(24), Algorithms.factorial(4)),
                () -> assertEquals(BigInteger.valueOf(120), Algorithms.factorial(5)));
        System.out.println("factorial(50000) has " +
                Algorithms.factorial(50000).toString().length() + " digits");
    }

    @ParameterizedTest
    @CsvSource({"0, 1", "1, 1", "2, 2",
        "3, 6", "4, 24", "5, 120"})
    public void testFactorialParameterized(long num, int expected) {
        assertEquals(BigInteger.valueOf(expected), Algorithms.factorial(num));
    }
}