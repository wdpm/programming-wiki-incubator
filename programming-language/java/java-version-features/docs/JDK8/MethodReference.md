# 方法引用

## 普通方法引用

```java
inventory.sort((Apple a1,Apple a2)-> a1.getWeight().compareTo(a2.getWeight()));
```

```java
// use java.util.Comparator.comparing
inventory.sort(comparing(Apple::getWeight));
```

> comparing比较器.reversed()支持逆序，.thenComparing()支持比较器链。

## 构造函数引用

假如有一个这样的Color类

```java
public class Color {
    public Color(Integer c1, Integer c2, Integer c3) { 
    }
}
```

下面使用方法引用构造函数

```java
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
```

```java
TriFunction<Integer,Integer,Integer,Color> colorFactory= Color::new;
```