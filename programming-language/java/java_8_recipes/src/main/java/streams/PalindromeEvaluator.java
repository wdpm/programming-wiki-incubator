package streams;

import java.util.stream.Stream;

public class PalindromeEvaluator {
    public boolean isPalindrome(String s) {
        String forward = s.toLowerCase().codePoints()
                // .parallel() // whoa. Not worth it, though -- below minimum threshold
                .filter(Character::isLetterOrDigit)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        String backward = new StringBuilder(forward).reverse().toString();
        return forward.equals(backward);

//        StringBuilder sb = new StringBuilder();
//        for (char c : s.toCharArray()) {
//            if (Character.isLetterOrDigit(c)) {
//                sb.append(c);
//            }
//        }
//        String forward = sb.toString().toLowerCase();
//        String backward = sb.reverse().toString().toLowerCase();
//        return forward.equals(backward);
    }

    public static void main(String[] args) {
        PalindromeEvaluator eval = new PalindromeEvaluator();
        Stream.of("Madam, in Eden, I'm Adam",
                "Go hang a salami; I'm a lasagna hog",
                "A Santa pets rats as Pat taps a star step at NASA")
                .allMatch(eval::isPalindrome);

        Stream.of("This is NOT a palindrome")
                .noneMatch(eval::isPalindrome);
    }
}
