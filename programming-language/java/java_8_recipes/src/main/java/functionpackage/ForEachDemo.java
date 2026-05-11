package functionpackage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ForEachDemo {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("This", "is", "a", "list", "of", "strings");

        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        //noinspection Convert2MethodRef
        strings.forEach(s -> System.out.println(s));

        strings.forEach(System.out::println);

        Consumer<String> printer = System.out::println;
        strings.forEach(printer);
    }
}
