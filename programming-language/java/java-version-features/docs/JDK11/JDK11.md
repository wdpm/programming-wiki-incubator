# JDK 11
参阅 https://openjdk.java.net/projects/jdk/11/

## 目录
TODO

### 可插拔模块化系统（Pluggable Java Module System）。
Java 11 引入了一种可插拔模块化系统，允许您加载只包含您所需内容的模块。
  下面是一个使用可插拔模块化系统的示例：
```java
module com.example.mymodule {
  exports com.example.mymodule.api;
  requires java.base;
}
```
### 即时（常量）编译器（GraalVM Native Image）

Java 11 引入了一种新的即时编译器，可以将 Java 应用程序编译为本机映像，
以提高应用程序的性能。

### 可选的链式调用（Optional.orElseThrow()）

Java 11 增加了一个新的 orElseThrow() 方法，可以方便地抛出异常，以处理 Optional 对象为空的情况。
  下面是一个使用 orElseThrow() 的示例：
```java
Optional<String> opt = Optional.ofNullable(null);
try {
  String value = opt.orElseThrow();
} catch (NoSuchElementException ex) {
  System.out.println("Caught expected exception:" + ex.getMessage());
}
```

### 可选的静态方法

Java 11 增加了一些可选的静态方法，如 of()和 empty()，可以方便地创建 Optional 对象。

```java
Optional<String> opt = Optional.of("Java 11");
System.out.println(opt.isPresent()); // true

Optional<String> empty = Optional.empty();
System.out.println(empty.isPresent()); // false
```

### 新的 HTTP 客户端 API
Java 11 引入了一个新的 HTTP 客户端 API，可以使用该 API 发送 HTTP 请求。

下面是一个使用新的 HTTP 客户端 API 的示例：

```java
HttpClient client = HttpClient.newBuilder().build();
HttpRequest request = HttpRequest.newBuilder()
  .uri(URI.create("https://example.com"))
  .GET()
  .build();
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());
```

### 单 Java 源文件直接运行
Java 11 允许在单个源文件中直接运行 Java 程序，而不需要编译和额外的命令行工具。

下面是一个简单的 Java 11 程序，该程序在单个源文件中直接运行：
```java
public class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello, World!");
  }
}
```
```bash
$ java HelloWorld.java
Hello, World!
```
### DiamondOperator<> enchancement
Java 11 对钻石操作符进行了加强，以更好地支持泛型。钻石操作符允许编译器推断泛型类型参数，从而减少了泛型代码的量。

在 Java 11 中，您可以在创建实例时使用钻石操作符，并且不需要显式指定类型参数：

```java
List<String> list = new ArrayList<>();
```

在早期版本的 Java 中，必须显式指定类型参数：

```java
List<String> list = new ArrayList<String>();
```

这种语法简化了泛型代码，使代码更简洁，更容易阅读和维护。


### Files 新方法
- Files.mismatch()
- Files.writeString()
- Files.readString()
- Files.delete()

Java 11 在 java.nio.file 包中的 Files 类中增加了一些新的方法。
这些方法为程序员提供了更方便、高效的方法来操作文件系统。

例如：

1. `Files.mismatch`：该方法比较两个文件，并返回第一个不匹配的字节位置。
```java
long mismatch = Files.mismatch(Path.of("file1.txt"), Path.of("file2.txt"));
if (mismatch >= 0) {
    System.out.println("Files are different at byte" + mismatch);
} else {
    System.out.println("Files are identical");
}
```

2. `Files.writeString`：该方法可以将字符串写入文件，无需打开和关闭文件。
```java
String text = "Hello, World!";
Files.writeString(Path.of("output.txt"), text, StandardCharsets.UTF_8);
```

3. `Files.readString`：该方法可以从文件读取字符串，无需打开和关闭文件。
```java
String text = Files.readString(Path.of("input.txt"), StandardCharsets.UTF_8);
System.out.println(text);
```

4. `Files.delete()`：用于删除文件。

它接受一个 Path 参数，指示要删除的文件。如果文件存在且删除成功，该方法将返回 void。这是一个示例代码：
```java
Path path = Path.of("file.txt");
Files.delete(path);
```  
请注意，如果文件不存在，则会抛出 NoSuchFileException 异常。

  
### 本地变量用于 Lambda 参数

Java 11 引入了使用本地变量作为 Lambda 参数的语法。
这意味着你可以在定义 Lambda 表达式时，使用 var 关键字声明参数类型，而不是明确指定类型。例如：

```java
BiFunction<String, String, String> concat = (var s1, var s2) -> s1 + s2;
String result = concat.apply("Hello,", "World!");
System.out.println(result);
```

这将输出：
```bash
Hello, World!
```

在这种情况下，编译器会自动推断出参数 s1 和 s2 的类型为 String，因为它们被用作字符串的拼接。

### String 新方法
- lines();
- isBlank();
- stripLeading();
- stripTrailing();
- strip();
- repeat();

Java 11 中，java.lang.String 类增加了一些新方法，其中一些如下：

1. `isBlank`：返回一个布尔值，指示该字符串是否为空白字符串。
```java
String str = " ";
System.out.println(str.isBlank()); // Output: true
```

2. `strip`：返回一个新字符串，该字符串将该字符串的开头和结尾的空格删除。

```java
String str = "Hello, World!";
System.out.println(str.strip()); // Output: "Hello, World!"
```
3. `lines`：返回一个字符串的流，其中包含该字符串按行分割的各个部分。
```java
String str = "First line\nSecond line\nThird line";
str.lines().forEach(System.out::println);
```

输出：
```bash
First line
Second line
Third line
```

4. `repeat`：返回一个新字符串，该字符串是该字符串的多次复制。
```java
String str = "Hello";
System.out.println(str.repeat(3)); // Output: "HelloHelloHello"
```

这是仅代表一部分 Java 11 中 java.lang.String 类中的新方法，更多详细信息请查看 Java 11 的官方文档。

### TimeUnit 新转化方法 TimeUnit.DAYS.convert

Java 11 中引入了一个新方法 convert，它在 java.util.concurrent.TimeUnit 类中定义。这个方法允许将给定的时间量从一个单位转换为另一个单位。

该方法有两个参数：

1. `sourceDuration`：源时间量
2. `sourceUnit`：源时间单位

以下是一个示例：

```java
long sourceDuration = 10;
TimeUnit sourceUnit = TimeUnit.HOURS;
long result = TimeUnit.DAYS.convert(sourceDuration, sourceUnit);
System.out.println(result); // Output: 0
```

这个例子将 10 个小时转换为天数，所以输出为 0。

### Nested Based Access Control

Java 11 引入了基于嵌套访问控制的功能，可以使用 private 和 private static 访问修饰符在内部类中访问嵌套类或内部类的私有成员。

以下是一个示例：

```java
public class Main {
    private static int x = 10;

    private static class Nested {
        private int y = 20;

        public void printValues() {
            System.out.println("x =" + Main.x);
            System.out.println("y =" + y);
        }
    }

    public static void main(String[] args) {
        Main.Nested nested = new Main.Nested();
        nested.printValues();
    }
}
```

输出：

```
x = 10
y = 20
```

在这个例子中，我们可以直接访问主类中的静态变量 x，并通过内部类访问内部类的实例变量 y。

### JEP 329: ChaCha20 and Poly1305 Cryptographic Algorithms

Java 11 增加了对两种安全加密算法的支持：ChaCha20 和 Poly1305。这两种算法是用于安全通信的高效加密算法，用于替代更加易被攻击的加密算法。

您可以参考这个示例代码 Chacha20Poly1305Example 。
  
### JEP 333: ZGC: A Scalable Low-Latency Garbage Collector (Experimental)

JEP 333: ZGC (Z Garbage Collector)是一个Java 11中的实验性特性，用于提高Java应用程序的内存管理能力。ZGC的主要目的是提供一个高效的垃圾收集器，在保证低延迟的同时，也能处理大量的内存。

ZGC在垃圾收集过程中，暂停时间很短，只有几毫秒，因此不会影响到Java应用程序的运行效率。同时，ZGC具有更高的内存效率，并且能够处理更大的内存堆。

总的来说，JEP 333: ZGC是一个非常有前途的特性，它可以帮助Java开发者提高应用程序的内存管理效率，并保证应用程序的高性能。然而，因为这是一个实验性特性，因此在生产环境中使用时，需要谨慎。