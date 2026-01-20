import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PalindromeCheckerTest {
    private List<String> palindromes = Arrays.asList(
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
                    assertTrue(forward.equals(backward));
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
}