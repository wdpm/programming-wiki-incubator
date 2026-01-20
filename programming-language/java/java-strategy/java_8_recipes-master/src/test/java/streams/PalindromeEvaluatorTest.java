package streams;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PalindromeEvaluatorTest {
    private PalindromeEvaluator demo = new PalindromeEvaluator();

    @Test
    public void isPalindrome() {
        assertTrue(
                Stream.of("Madam, in Eden, I'm Adam",
                        "Go hang a salami; I'm a lasagna hog",
                        "Flee to me, remote elf!",
                        "A Santa pets rats as Pat taps a star step at NASA")
                        .allMatch(demo::isPalindrome));

        assertFalse(demo.isPalindrome("This is NOT a palindrome"));
    }
}