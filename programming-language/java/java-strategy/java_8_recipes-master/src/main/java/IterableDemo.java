import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IterableDemo {
    // @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9);
        // forEach loop
        for (Integer num : nums) {
            System.out.println(num);
        }

        // lambda expression
        nums.forEach(x -> System.out.println(x));

        // method reference
        nums.forEach(System.out::println);

        Set<Integer> sizes = Stream.of("this", "is", "a", "list", "of", "strings")
                .map(String::length)
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(sizes.getClass().getName());
        System.out.println("Even lengths: " + sizes);

        Map<String, Integer> map = Stream.of("this", "is", "a", "list", "of", "strings")
                .collect(Collectors.toMap(e -> e, String::length));
        System.out.println(map);

        map.forEach((k,v) -> System.out.println(k + ": " + v));

    }
}
