# Consumer
```java
@FunctionalInterface // 函数式接口标记
public interface Consumer<T> {

    void accept(T t); // 消费操作

    default Consumer<T> andThen(Consumer<? super T> after) { //在消费操作之后，执行后续操作
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```