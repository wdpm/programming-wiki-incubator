# 函数式接口

函数式接口就是只定义一个抽象方法的接口。

## 示例

```java
// java.util.Comparator
public interface Comparator<T>{
    int compare(T o1,T o2);
}

// java.lang.Runable
public interface Runable{
    void run();
}

// java.util.concurrent.Callable
public interface Callable<V>{
    V call();
}
```

## 函数式接口

| 函数式接口        | 函数描述符     | 原始类型初始化                                               |
| ----------------- | -------------- | ------------------------------------------------------------ |
| Predicate<T>      | T->boolean     | IntPredicate,LongPredicate,DoublePredicate                   |
| Consumer<T>       | T->void        | IntConsumer,LongConsumer,DoubleConsumer                      |
| Function<T,R>     | T->R           | IntFunction<R>,IntToDoubleFunction,IntToLongFunction,<br />LongFunction<R>,LongToDoubleFunction,LongToIntFunction,<br />DoubleFunction<R>,ToIntFunction<T>,ToIntFunction<T>,<br />ToDoubleFunction<T>,ToLongFunction<T> |
| Supplier<T>       | ()->T          | BooleanSupplier,IntSupplier,LongSupplier,DoubleSupplier      |
| UnaryOperator<T>  | T->T           | IntUnaryOperator,LongUnaryOperator,DoubleUnaryOperator       |
| BinaryOperator<T> | (T,T)->T       | IntBinaryOperator,LongBinaryOperator,DoubleBinaryOperator    |
| BiPredicate<L,R>  | (L,R)->boolean |                                                              |
| BiConsumer        | (T,U)->void    | ObjIntConsumer<T>,ObjLongConsumer<T>,ObjDoubleConsumer<T>    |
| BiFunction<T,U,R>  | (T,U)->R       | ToIntBiFunction<T,U>,ToLongBiFunction<T,U>,ToDoubleBiFunction<T,U> |

## Lambda和函数式接口示例

| 案例         | Lambda例子                                                   | 对应函数式接口                  |
| ------------ | ------------------------------------------------------------ | ------------------------------- |
| 布尔表达式   | (List<String> list) -> list.empty()                          | Predicate<List<String>>         |
| 创建对象     | () -> new Apple(10)                                          | Supplier<Apple>                 |
| 消费对象     | (Apple a) -> System.out.println(a.getWeight())               | Consumer<Apple>                 |
| 从对象中提取 | (String s) -> s.length()                                     | Function<String,Integer>        |
| 合并两个值   | (int a,int b) -> a*b                                         | IntBinaryOperator               |
| 比较两个对象 | (Apple a1,Apple a2) -> a1.getWeight().compareTo(a2.getWeight()) | BiFunction<Apple,Apple,Integer> |

> 闭包：一个函数的实例，可以无限制地访问这个函数的非本地变量（自由变量）。