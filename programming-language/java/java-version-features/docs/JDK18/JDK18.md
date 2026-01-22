# JDK 18

## JEP 400：默认 UTF-8 字符编码

JDK 17:

```bash
 java -XshowSettings:properties -version 2>&1 | findstr file.encoding
+ java -XshowSettings:properties -version 2>&1 | findstr file.encoding
    file.encoding = GBK
```

JDK 18+ 之后，就是 file.encoding = uft-8。

## JEP 408：简单的 Web 服务器

在 Java 18 中，提供了一个新命令 jwebserver，运行这个命令可以启动一个简单的 、最小化的静态 Web 服务器，它不支持 CGI 和
Servlet，一般用于测试、教育、演示等需求。

## JEP 413：Javadoc 中支持代码片段

从 Java 18 开始，可以使用 @snippet 来生成注释，且可以高亮某个代码片段。

- 可以使用正则来高亮某一段中的某些关键词。
- 可以使用正则表达式来替换某一段代码。

Javadoc 生成方式如下：

```bash
javadoc -h

# 使用 javadoc 命令生成 Javadoc 文档
# cd project ROOT folder: D:\Code\MyGithubProjects\java-version-features
javadoc -public -sourcepath ./src/main/java -subpackages io.github.wdpm.jdk18.javadoc -encoding utf-8 -charset utf-8 -d ./javadocout

# 使用 Java 18 的 jwebserver 把生成的 Javadoc 发布测试
jwebserver -d D:\Code\MyGithubProjects\java-version-features\javadocout
```

## JEP 416：使用方法句柄重新实现反射核心功能

Java 18 改进了 java.lang.reflect.Method、Constructor 的实现逻辑，使之性能更好，速度更快。这项改动不会改动相关 API
，这意味着开发中不需要改动反射相关代码，就可以体验到性能更好反射。

## JEP 417：Vector API（三次孵化）

你就继续孵吧，等你孵出来再说。

## JEP 418：互联网地址解析 SPI

```
InetAddress inetAddress = InetAddress.getByName("www.bilibili.com");
System.out.println(inetAddress.getHostAddress());
// 输出
// 106.14.229.49
```

## JEP 419：Foreign Function & Memory API ( 第二次孵化）

继续孵化。

## JEP 420：switch 表达式（二次孵化）

case 可以加入实例类型判断 `Triangle t` 以及附加条件判断 `(t.calculateArea() > 100)`：

```java
static void testTriangle(Shape s){
        switch(s){
        case Triangle t&&(t.calculateArea()>100)->
        System.out.println("Large triangle");
default ->
        System.out.println("A shape, possibly a small triangle");
        }
        }
```

```java
sealed interface S permits A, B, C {
}

final class A implements S {
}

final class B implements S {
}

record C(int i) implements S {
}  // Implicitly final

    static int testSealedExhaustive(S s) {
        return switch (s) {
            case A a -> 1;
            case B b -> 2;
            case C c -> 3;
        };
    }
```

## JEP 421：弃用删除相关

在未来将删除 Finalization，目前 Finalization 仍默认保持启用状态，但是可以手动禁用；在未来的版本中，将会默认禁用；在以后的版本中，它将被删除。
需要进行资源管理可以尝试 try-with-resources（TWR） 或者 java.lang.ref.Cleaner。