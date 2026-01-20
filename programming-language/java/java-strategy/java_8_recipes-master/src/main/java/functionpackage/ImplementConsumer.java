package functionpackage;

import java.util.function.IntConsumer;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ImplementConsumer {
    private static Logger log = Logger.getLogger(ImplementConsumer.class.getName());

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
                .forEach(n -> System.out.println(n));

        IntStream.of(3, 1, 4, 1, 5, 9)
                .forEach(System.out::println);
    }
}
