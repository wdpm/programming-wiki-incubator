import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IteratingOverAMap {
    public static void main(String[] args) {
        String s = "this is a string with individual words of varying lengths " +
                "to be used in an example that parses it and adds them to a map " +
                "for later printing";
        String[] strings = s.split(" ");

        Map<Integer, Set<String>> stringMap = Stream.of(strings)
                .collect(Collectors.groupingBy(String::length, Collectors.toSet()));


        // Java 7 printing a map
        System.out.printf("len     words%n");
        for (Map.Entry<Integer, Set<String>> entry : stringMap.entrySet()) {
            System.out.printf("%2d: %s%n", entry.getKey(), entry.getValue());
        }

        // Java 8 printing a map
        System.out.printf("len     words%n");
        stringMap.forEach((len, stringSet) ->
                System.out.printf("%2d: %s%n", len, stringSet));
    }
}
