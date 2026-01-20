package concurrency;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class AllOfDemo {
    private int getNextValue() {
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 42;
    }

    public CompletableFuture<Integer> getValue() {
        return CompletableFuture.supplyAsync(this::getNextValue);
    }

    public static void main(String[] args) {
        AllOfDemo demo = new AllOfDemo();
        CompletableFuture[] completableFutures = Stream.generate(demo::getValue)
                .limit(10)
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFutures).join();

        Arrays.stream(completableFutures)
                .map(CompletableFuture::join)
                .forEach(System.out::println);
    }
}
