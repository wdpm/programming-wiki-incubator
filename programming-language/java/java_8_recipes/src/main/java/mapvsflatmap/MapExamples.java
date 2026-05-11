package mapvsflatmap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapExamples {
    public static void main(String[] args) {
        List<Integer> sizes = Stream.of("This", "is", "a", "stream", "of", "strings")
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(sizes);

        List<String> names = Stream.of(new Person("Steve"),
                new Person("Tony"), new Person("Thor"),
                new Person("Natasha"), new Person("Bruce"),
                new Person("Clint"))
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println(names);

        Map<Integer, List<String>> map =
                Stream.of("This", "is", "a", "stream", "of", "strings")
                .collect(Collectors.groupingBy(String::length));

        map.forEach((k,v) -> {
            System.out.println(k + ": " + v);
        });
    }
}
