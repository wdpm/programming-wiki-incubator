# JDK 17

## JEP 306： 恢复始终严格的浮点语义

strictfp 是 Java 中的一个关键字，它可以用在类、接口或者方法上，被 strictfp 修饰的部分中的 float 和
double 表达式会进行严格浮点计算。

```java
package io.github.wdpm.jdk17;

public class AlwaysStrictFloatingPointSemantics {
    public static void main(String[] args) {
        testStrictfp();
    }

    public strictfp static void testStrictfp() {
        float aFloat = 0.6666666666666666666f;
        double aDouble = 0.88888888888888888d;
        double sum = aFloat + aDouble;
        System.out.println("sum:" + sum);
    }
}
```

## JEP 356：增强的伪随机数生成器

JEP 356 是 Java Enhancement Proposal 的编号，它提出了一个关于在 Java 编程语言中增强随机数生成器（PRNG）功能的提议。PRNG
是一种生成随机数的算法，它通过简单的数学方法在数学上生成伪随机数。

JEP 356 的目的是通过引入一个新的随机数生成器（java.util.random.RandomGenerator）来提高随机数生成的灵活性和性能。
这个新的随机数生成器是一个更为灵活的随机数生成接口，允许开发人员使用自定义随机数生成器实现来生成随机数。

总的来说，JEP 356 对于 Java 开发人员来说是一个有益的改进，因为它提供了更多的选择（RandomGeneratorFactory）和更好的性能来生成随机数。

扩展阅读：[增强的伪随机数生成器](https://openjdk.java.net/jeps/356)

## JEP 382：使用新的 macOS 渲染库

macOS 为了提高图形的渲染性能，在 2018 年 9 月抛弃了之前的 OpenGL 渲染库 ，而使用了 Apple Metal 进行代替。Java 17 这次更新开始支持
Apple Metal，不过对于 API 没有任何改变，这一些都是内部修改。

## JEP 391：支持 macOS/AArch64 架构

起因是 Apple 在 2020 年 6 月的 WWDC 演讲中宣布，将开启一项长期的将 Macintosh 计算机系列从 x64 过度到 AArch64
的长期计划，因此需要尽快的让 JDK 支持 macOS/AArch64 。

## JEP 398：删除已弃用的 Applet API

applet 坟头草都三尺高了。Applet API 在 Java 9 时已经标记了废弃，Java 17 中将彻底删除。

## JEP 403：更强的 JDK 内部封装

在 Java 17 中使用 --illegal-access 选项将会得到一个命令已经移除的警告。

```bash
 java --illegal-access=warn
OpenJDK 64-Bit Server VM warning: Ignoring option --illegal-access=warn; support was removed in 17.0
```

## JEP 406：switch 的类型匹配（预览）

```
static String switchPatternMatch(Object o) {
    return switch (o) {
        case Integer i -> String.format("int %d", i);
        case Long l    -> String.format("long %d", l);
        case Double d  -> String.format("double %f", d);
        case String s  -> String.format("String %s", s);
        default        -> o.toString();
    };
}
```

也可以判断 null 值匹配了。

```
// Java 17
static void test(String s) {
    switch (s) {
        case null         -> System.out.println("Oops");
        case "Foo", "Bar" -> System.out.println("Great");
        default           -> System.out.println("Ok");
    }
}
```

## JEP 407：移除 RMI Activation

移除了在 JEP 385 中被标记废除的 RMI（Remote Method Invocation）Activation，但是 RMI 其他部分不会受影响。

这个特性估计用的人不多。

## JEP 409：密封类（Sealed Classes）

Sealed Classes 在 Java 15 中的 JEP 360 中提出，在 Java 16 中的 JEP 397 再次预览，现在 Java 17 中成为正式的功能。

## JEP 401：移除实验性的 AOT 和 JIT 编译器

在 Java 9 的 JEP 295 中，引入了实验性的提前编译 jaotc 工具，但是这个特性自从引入依赖用处都不太大，而且需要大量的维护工作，所以在
Java 17 中决定删除这个特性

## JEP 411：弃用 Security Manager

```java

@Deprecated(since = "17", forRemoval = true)
public class SecurityManager {
    // ...
}
```

## JEP 412：外部函数和内存 API （孵化）

新的 API 允许 Java 开发者与 JVM 之外的代码和数据进行交互，通过调用外部函数，可以在不使用 JNI 的情况下调用本地库。

这是一个孵化功能；需要添加 --add-modules jdk.incubator.foreign 来编译和运行 Java 代码。

## JEP 414：Vector API（二次孵化）

在 Java 16 中引入一个新的 API 来进行向量计算，它可以在运行时可靠的编译为支持的 CPU 架构，从而实现更优的计算能力。

现在 Java 17 中改进了 Vector API 性能，增强了例如对字符的操作、字节向量与布尔数组之间的相互转换等功能。

孵化阶段 API 都不稳定，因为这里不给出代码示例。

## JEP 415：指定上下文的反序列化过滤器

反序列化危险的一个原因是，有时候我们不好验证将要进行反序列化的内容是否存在风险，而传入的数据流可以自由引用对象，
很有可能这个数据流就是攻击者精心构造的恶意代码。

JEP 415 允许在反序列化时，通过一个过滤配置，来告知本次反序列化允许或者禁止操作的类，反序列化时碰到被禁止的类，则会反序列化失败。

参考：JEP415.java