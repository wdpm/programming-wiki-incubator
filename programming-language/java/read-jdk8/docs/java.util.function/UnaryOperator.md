# UnaryOperator
```java
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {
    
    // 输入=输出，等价操作
    static <T> UnaryOperator<T> identity() {
        return t -> t;
    }
}
```