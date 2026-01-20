package optionals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OptionalDemo {
    public static final Predicate<String> EVENS = s -> s.length() % 2 == 0;


    private List<String> strings = new ArrayList<>();

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public Optional<String> findFirst(Predicate<String> predicate) {
        return strings.stream()
                .filter(predicate)
                .findFirst();
    }

    public static <T> Optional<T> createOptionalTheHardWay(T value) {
        return value == null ? Optional.empty() : Optional.of(value);
    }

    public static <T> Optional<T> createOptionalTheEasyWay(T value) {
        return Optional.ofNullable(value);
    }

    public static void main(String[] args) {
        String first = Stream.of("this is a list of strings".split(" "))
                .filter(s -> s.length() > 10)
                .findFirst().orElse("No string satisfying predicate found");
        System.out.println(first);
        // System.out.println(first.orElseThrow(NoSuchElementException::new));

    }
}
