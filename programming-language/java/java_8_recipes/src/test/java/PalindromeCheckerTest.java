import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PalindromeCheckerTest {
    private final List<String> palindromes = Arrays.asList(
            "Madam, in Eden, I'm Adam",
            "Flee to me, remote elf!",
            "Go hang a salami; I'm a lasagna hog"
    );

    @Test
    public void isPalidromeUsingLambda() {
        palindromes.forEach(s -> {
                    StringBuilder sb = new StringBuilder();
                    for (char c : s.toCharArray()) {
                        if (Character.isLetter(c)) {
                            sb.append(c);
                        }
                    }
                    String forward = sb.toString().toLowerCase();
                    String backward = sb.reverse().toString().toLowerCase();
            assertEquals(forward, backward);
                }
        );
    }

    @Test
    public void isPalidromeUsingMethodRef() {
        assertTrue(
                palindromes.stream()
                        .allMatch(PalindromeChecker::checkPalindrome));

        assertFalse(
                PalindromeChecker.checkPalindrome("This is NOT a palindrome"));
    }

    List<String> palindromeProvider() {
        return palindromes;
    }

    @DisplayName("JUnit 5 parameterized test for palindromes")
    @ParameterizedTest(name = "{0} is a palindrome")
    @MethodSource("palindromeProvider")
    void palindromes(String candidate) {
        assertTrue(PalindromeChecker.checkPalindrome(candidate));
    }
}