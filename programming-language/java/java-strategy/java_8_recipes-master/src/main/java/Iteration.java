import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Iteration {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(3, 1, 4, 1, 5, 9);

        // Implement Consumer using anonymous inner class
        integers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });

        // Full verbose form of lambda
        integers.forEach((Integer n) -> {
            System.out.println(n);
        });

        // Simplified form of lambda
        integers.forEach(n -> System.out.println(n));

        // Method reference
        integers.forEach(System.out::println);

        Map<Long, String> map = new HashMap<>();
        map.put(86L, "Don Adams (Maxwell Smart)");
        map.put(99L, "Barbara Feldon");
        map.put(13L, "David Ketchum");

        map.forEach((num, agent) ->
                System.out.printf("Agent %d, played by %s%n", num, agent));
    }
}
