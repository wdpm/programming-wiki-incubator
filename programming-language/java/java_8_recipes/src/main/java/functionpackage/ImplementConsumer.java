package functionpackage;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ImplementConsumer {
    private static final Logger log = Logger.getLogger(ImplementConsumer.class.getName());

    @SuppressWarnings({"Convert2Lambda", "Convert2MethodRef"})
    public static void main(String[] args) {
        log.warning(() -> "Logging a message at warn level");
        IntStream.of(3, 1, 4, 1, 5, 9)
                .forEach(new IntConsumer() {
                    @Override
                    public void accept(int x) {
                        System.out.println(x);
                    }
                });

        IntStream.of(3, 1, 4, 1, 5, 9)
                .forEach(x -> System.out.println(x));

        IntStream.of(3, 1, 4, 1, 5, 9)
                .forEach(System.out::println);

        // In Java 8, must declare <String> on RHS
        // In Java 9+, can use diamond operator
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Stream.of("this", "is", "a", "list")
              .forEach(consumer);

        // Simpler
        consumer = s -> System.out.println(s);
        consumer = System.out::println;
    }
}
