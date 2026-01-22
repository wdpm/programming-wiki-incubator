# JDK 15

JDK 15 是在 2022 年 9 月 15 日发布的 Java 版本。主要新特性如下：

## Text Blocks

JDK 15引入了文本块，这是一个新的字符串字面量，它可以简化对于大量文本的字符串构建。

```java
String html = """
<html>
   <body>
      <p>Welcome to Java 15!</p>
   </body>
</html>
""";
System.out.println(html);
```

## Sealed Classes (Preview)

密封类可以限制类的继承，并给出一个明确的子类列表。

```java
sealed class Shape permits Circle, Square, Triangle { }

final class Circle extends Shape { }
final class Square extends Shape { }
final class Triangle extends Shape { }
```

## Pattern Matching for instanceof (Preview)

可以使用 instanceof 来匹配实例对象。

```java
public void doSomething(Object object) {
    if (object instanceof String str) {
        System.out.println("It's a string: " + str);
    }
}
```

## Records (Preview)

Records 是一个简单的数据类型，可以简化某些类型的实现，允许程序员声明一个类仅具有状态， 而不是行为。
```java
record Point(int x, int y) { }

Point p = new Point(1, 2);
System.out.println("x:" + p.x());
System.out.println("y:" + p.y());
```

## local enums and interfaces (Preview)

"Local Enums and Interfaces" 是 JDK 15 中的一个预览特性。这意味着，你可以在局部范围内定义枚举和接口，而不是只能在类级别或文件级别定义。

下面是一个示例代码，展示了如何在方法内定义局部枚举：

```java
public class LocalEnumExample {
    public static void main(String[] args) {
        // Defining a local enum inside a method
        enum DaysOfWeek {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
        DaysOfWeek today = DaysOfWeek.MONDAY;
        System.out.println("Today is: " + today);
    }
}
```

运行该代码会输出：

```
Today is: MONDAY
```
同样的，你可以在方法内定义局部接口，示例代码如下：

```java
public class LocalInterfaceExample {
    public static void main(String[] args) {
        // Defining a local interface inside a method
        interface Operate {
            int operate(int a, int b);
        }
        Operate addition = (a, b) -> a + b;
        System.out.println("Result of addition: " + addition.operate(10, 20));
    }
}
```
运行该代码会输出：

```
Result of addition: 30
```
请注意，局部枚举和局部接口仅在它们被声明的方法内可见，并且不能被其他方法或类引用。