package lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClosureVariables {

    private static boolean isEvenLength(String s) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        System.out.printf("%s with %s%n", Thread.currentThread().getName(), s);
        return s.length() % 2 == 0;
    }

    @SuppressWarnings({"Convert2streamapi", "UnusedAssignment", "unused"})
    public static void main(String[] args) {

        // Sum using loop (iterative and shared mutable state)
        int total = 0;
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5);
        for (int n : nums) {
            total += n;
        }
        System.out.printf("The total is %d%n", total);

        // Functional, but shared mutable state (won't compile)
        total = 0;
        nums.forEach(n -> {
                    // Can't modify "total"
                    // total += n;
                }
        );

        // Functional, no shared mutable state
        total = nums.stream()
                .mapToInt(Integer::valueOf)
                .sum();
        System.out.printf("The total is %d%n", total);

        // Find even-length strings
        List<String> strings = Arrays.asList("this", "is", "a",
                "list", "of", "strings");
        Predicate<String> evenlengths = (String s) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            System.out.println(Thread.currentThread().getName());
            return s.length() % 2 == 0;
        };


        // Side effects --> legal, but not safe
        List<String> evenLengths = new ArrayList<>();
        strings.stream()
                .filter(s -> s.length() % 2 == 0)
                .forEach(evenLengths::add);
        System.out.println("Using add: " + evenLengths);

        // No side-effects
        System.out.println("Before: " + strings);
        evenLengths = strings.stream()
                .filter(s -> s.length() % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("After: " + evenLengths);


        // No side-effects
        long start = System.nanoTime();
        List<String> evens = strings.parallelStream()
                .filter(ClosureVariables::isEvenLength)
                //.filter(s -> s.length() % 2 == 0)
                .collect(Collectors.toList());
        long end = System.nanoTime();
        System.out.println(evens);
        System.out.printf("Time: %s%s%n", (end - start) / 1e9, "sec");


    }
}
