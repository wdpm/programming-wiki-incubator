# Iterable
```java
public interface Iterable<T> {

    Iterator<T> iterator();//返回迭代器

    default void forEach(Consumer<? super T> action) { // for each loop 的默认实现
        Objects.requireNonNull(action);// 非空检查
        for (T t : this) {
            action.accept(t);
        }
    }
    
    // splitable iterator可分割迭代器, 为支持并行迭代而引入
    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0); // spliterator的默认实现，具体细节先跳过
    }
}
```
