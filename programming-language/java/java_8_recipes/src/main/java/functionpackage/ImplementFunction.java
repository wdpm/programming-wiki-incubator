package functionpackage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImplementFunction {
    @SuppressWarnings({"UnusedAssignment", "Convert2Lambda", "Anonymous2MethodRef"})
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
                "Zoë", "Jayne", "Simon", "River", "Shepherd Book");

        // anonymous inner class
        List<Integer> nameLengths = names.stream()
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        return s.length();
                    }
                })
                .collect(Collectors.toList());

        // lambda expression
        //noinspection Convert2MethodRef
        nameLengths = names.stream()
                .map(s -> s.length())
                .collect(Collectors.toList());

        // method reference
        nameLengths = names.stream()
                .map(String::length)
                .collect(Collectors.toList());

        System.out.printf("nameLengths = %s%n", nameLengths);
        // nameLengths == [3, 4, 6, 5, 3, 5, 5, 5, 13]
    }
}
