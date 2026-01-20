import java.util.Arrays;
import java.util.List;

public class Printing {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");

        strings.forEach(System.out::println);
        strings.forEach(s -> System.out.println("The word is " + s));
    }
}
