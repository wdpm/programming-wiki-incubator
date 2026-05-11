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

        // 如果有外层 join()：逐个调用时，所有 future 都已经完成 → 每个 join() 瞬间返回。异常处理语义更清晰。
        // 如果没有外层 join()：逐个调用时，可能遇到未完成的 future → 会阻塞等待
        CompletableFuture.allOf(completableFutures).join();

        Arrays.stream(completableFutures)
                .map(CompletableFuture::join)
                .forEach(System.out::println);
    }
}
