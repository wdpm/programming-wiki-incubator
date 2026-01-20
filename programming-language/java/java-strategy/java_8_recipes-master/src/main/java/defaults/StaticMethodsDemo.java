package defaults;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StaticMethodsDemo {
    public static void main(String[] args) {
        List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore", "Dalton",
                "Brosnan", "Craig");

        // Sorted in natural order
        List<String> sorted = bonds.stream()
                .sorted(Comparator.naturalOrder())  // same as "sorted()"
                .collect(Collectors.toList());
        System.out.println(sorted);

        // Sorted in the reverse of the natural order
        sorted = bonds.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println(sorted);

        // Sorted by name, all lowercase
        sorted = bonds.stream()
                .sorted(Comparator.comparing(String::toLowerCase))
                .collect(Collectors.toList());
        System.out.println(sorted);

        // Sorted by length
        sorted = bonds.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println(sorted);

        // Sorted by length then natural order
        sorted = bonds.stream()
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
        System.out.println(sorted);
    }
}
