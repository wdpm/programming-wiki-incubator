package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UsingCollect {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList(
                "this", "is", "a", "list", "of", "strings");

        // Side effects --> not what we want
        List<String> evenLengths = new ArrayList<>();
        strings.stream()
                .filter(s -> s.length() % 2 == 0)
                .forEach(evenLengths::add);
        System.out.println(evenLengths);

        // No side-effects
        List<String> evens = strings.stream()
                .filter(s -> s.length() % 2 == 0)
                .collect(Collectors.toList());
        System.out.println(evens);
    }
}
