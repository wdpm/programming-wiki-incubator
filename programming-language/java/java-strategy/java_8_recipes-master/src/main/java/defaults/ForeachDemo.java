package defaults;

import java.util.Arrays;
import java.util.List;

public class ForeachDemo {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("here", "are", "some", "strings");

        strings.forEach(System.out::println);
    }
}
