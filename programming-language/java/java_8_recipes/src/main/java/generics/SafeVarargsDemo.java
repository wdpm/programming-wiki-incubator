package generics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SafeVarargsDemo {

    @SafeVarargs
    public static <T> List<T> createList(T... values) {
        return Arrays.stream(values)
                .collect(Collectors.toList());
    }


}
