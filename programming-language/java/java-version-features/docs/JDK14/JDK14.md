# JDK 14

JDK 14 是 Java 开发工具包的一个版本，发布于 2020 年 03 月 17 日。下面是 JDK 14 中的一些新特性：

## Pattern Matching for instanceof（instanceof 的模式匹配）

Java 14 引入了模式匹配的语法，可以简化 if-else 语句中的类型判断。

```java
if(shape instanceof Circle c){
        System.out.println("This is a Circle with radius:"+c.getRadius());
        }else if(shape instanceof Rectangle r){
        System.out.println("This is a Rectangle with width:"+r.getWidth()+"and height:"+r.getHeight());
        }
```

## Records (Preview)

Java 14 引入了记录，它是一种不可变类型，用于快速创建简单的数据结构。

```java
record Point(int x, int y) {
}

    Point p = new Point(1, 2);
System.out.println(p.x());
        System.out.println(p.y());
```

## NullPointerException 的简化消息：

NullPointerException 的简化消息是通过 JEP 358 实现的。 
Java 14 在抛出 NullPointerException 时提供了更明确的消息，方便开发人员快速定位错误。

Before Java 14:

```
String name = jd.getBlog().getAuthor()
 
//Stacktrace
Exception in thread "main" java.lang.NullPointerException
    at NullPointerExample.main(NullPointerExample.java:5)
```

After Java 14:

```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "Blog.getAuthor()" 
because the return value of "Journaldev.getBlog()" is null
  at NullPointerExample.main(NullPointerExample.java:4)
```

## Switch Expressions（Switch 表达式）

Java 14 支持简化的 switch 语句，允许在 switch 表达式中使用表达式和多个值的匹配。

```java
int numLetters=switch(day){
        case MONDAY,FRIDAY,SUNDAY->6;
        case TUESDAY->7;
default ->-1;
        };
```

这是 JDK 14 中一些主要新特性的代码示例，如果您对某个特性更感兴趣，您可以查找更多相关代码示例。