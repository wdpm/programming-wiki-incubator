package concurrency;

import java.util.concurrent.CompletableFuture;

public class AwaitQuiesence {
    private String sleepThenReturnString() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        return "42";
    }

    public CompletableFuture<Void> supplyThenAccept() {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(this::sleepThenReturnString)
                .thenApply(Integer::parseInt)
                .thenApply(x -> 2 * x)
                .thenAccept(System.out::println);
        System.out.println("Running...");
        return completableFuture;
    }

    public static void main(String[] args) {
        AwaitQuiesence aq = new AwaitQuiesence();
        CompletableFuture<Void> cf = aq.supplyThenAccept();
        cf.join();
    }
}