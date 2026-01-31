# 泛型

## 泛型擦除等价性
```java
public class GenericEraseEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2);
    }

    // true
}
```

## 泛型擦除丢失类型参数的信息
```java
public class GenericLostInformation {

    public static void main(String[] args) {
        List<A>                list  = new ArrayList<>();
        Map<A, B>              map   = new HashMap<>();
        Quark<B>               quark = new Quark<>();
        Particle<Long, Double> p     = new Particle<>();
        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(p.getClass().getTypeParameters()));
    }

    // [E]
    // [K, V]
    // [Q]
    // [Position, Momentum]
}

class A {
}

class B {
}

class Quark<Q> {
}

class Particle<Position, Momentum> {
}
```
在泛型代码内部，无法获取任何有关泛型参数类型的信息。只能看到类似于K V这种占位符。

Java 泛型使用擦除实现。在使用泛型时，任何具体的类型信息都被擦除，等价于在使用一个对象Object。
因此，`List<String>` 和 `List<Integer>` 在运行时实际上是相同的类型。都被擦除成原生类型 `List`。
