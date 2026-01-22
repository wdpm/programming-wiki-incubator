# JDK 10

JDK 10 是在 2018 年 3 月发布的版本，下面是一些主要特性：

1. Local-Variable Type Inference (JEP 286)

简化变量声明，允许编译器推断出变量的类型，比如：

```java
var list = new ArrayList<String>();
list.add("Hello");
list.add("Java");
list.forEach(System.out::println);
```

2. Consolidate the JDK Forest into a Single Repository (JEP 296)：JDK 源码合并，将原来分布在多个存储库中的代码统一到一个存储库中。

3. Parallel Full GC for G1 (JEP 307)：为 G1 垃圾收集器加入并行垃圾回收功能。

4. Application Class-Data Sharing (JEP 310)：应用程序类数据共享，允许不同的 JVM 实例共享相同的类数据，提高系统启动速度。

以上是 JDK 10 中的一些重要特性。