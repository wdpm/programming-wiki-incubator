package lambdas;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConsumerDemo {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(3, 1, 4, 1, 5, 9);
        // Anonymous inner class
        integers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("The value in the anon. inner class is " + integer);
            }
        });

        // Lambda expression
        integers.forEach(x -> System.out.println("The value of x is " + x));

        // Method reference
        integers.forEach(System.out::println);

        Set<Integer> nums = integers.stream()
                .map(n -> n * 2)               // Function to double the numbers
                .filter(n -> n % 3 == 0)       // Predicate to only return multiples of 3
                .collect(Collectors.toSet());  // Convert stream to a Set
        System.out.println(nums);

        Deque<Integer> collection = integers.stream()
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }
}
