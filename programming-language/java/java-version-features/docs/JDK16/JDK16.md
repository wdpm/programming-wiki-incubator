# JDK 16

## JEP 347：启用 C++ 14 语言特性

具体请参考：https://openjdk.org/jeps/347

## JEP 357：从 Mercurial 迁移到 Git

一个开源社区治理的决策。

## JEP 369：迁移到 GitHub

免费的 github 托管是一个极好的选择。

## JEP 376：ZGC 并发线程堆栈处理

在之前，需要 GC 的时候，为了进行垃圾回收，需要所有的线程都暂停下来，这个暂停的时间我们成为 Stop The World。

而为了实现 STW 这个操作， JVM 需要为每个线程选择一个点停止运行，这个点就叫做安全点（Safepoints）。

这次改动让 ZGC 线程堆栈处理从安全点（Safepoints）移动到并发阶段。

更多阅读：https://openjdk.org/jeps/376

## JEP 380：Unix 域套接字通道

> https://openjdk.org/jeps/380

## JEP 386：移植 Alpine Linux

> Port the JDK to Alpine Linux, and to other Linux distributions that use musl as their primary C library, on both the
> x64 and AArch64 architectures,

> The Alpine Linux distribution is widely adopted in cloud deployments, microservices, and container environments due to
> its small image size. A Docker base image for Alpine Linux, for example, is less than 6 MB. Enabling Java to run
> out-of-the-box in such settings will allow Tomcat, Jetty, Spring, and other popular frameworks to work in such
> environments natively.

目标是让 JDK 的未来支持云原生环境和嵌入式开发，更小更轻量。

## JEP 387：更好的 Metaspace

Return unused HotSpot class-metadata (i.e., metaspace) memory to the operating system more promptly, reduce metaspace
footprint, and simplify the metaspace code in order to reduce maintenance costs.

需要注意这个 JVM 参数 Reclamation policy：`-XX:MetaspaceReclaimPolicy=(balanced|aggressive|none)`。

## JEP 388：移植 Windows/AArch64

这个主要是 Java 生态扩展的问题，抢蛋糕谁不想呢？

## JEP 389：外部连接器 API（孵化）

让 Java 代码可以调用由其他语言（比如 C ，C++）编写的编译后的机器代码，替换了之前的 JNI 形式。

具体操作：运行时需要添加 --add-modules jdk.incubator.foreign 参数来编译和运行 Java 代码。

1. 首先新建一个 hello 级别的 c 代码程序。
2. 执行命令生成 shared object(so)。注意：这里选择 64 位的 GCC 编译, 选择 [mingw](https://winlibs.com/)。

```bash
$ gcc -c -fPIC hello.c
$ gcc -shared -o hello.so hello.o
```

3. 新建 Java 测试代码：JEP389Example.java
4. 编译 Java 测试代码。注意：不要使用 IDE 的 terminal，它会混淆 modules，使用本地自带的 terminal。

```bash
javac --add-modules jdk.incubator.foreign JEP389Example.java
```

```bash
java --add-modules  jdk.incubator.foreign -Dforeign.restricted=permit JEP389Example.java
```

> 说明：C 代码编译时如果是 64 位，那么 JDK 也必须是 64 位，否则可能会发生不兼容。

最后结果:

```bash
 java --add-modules  jdk.incubator.foreign -D"foreign.restricted"=permit JEP389Example.java
WARNING: Using incubator modules: jdk.incubator.foreign
警告: 使用 incubating 模块: jdk.incubator.foreign
1 个警告
hello JEP389.
```

## JEP 390：基于值的类的警告

用于标识当前是是基于值的类：

```java

@jdk.internal.ValueBased
public final class Optional<T> {
    // ...
}
```

## JEP 392：打包工具

使用 jpackage 命令可以把 JAR 包打包成不同操作系统支持的软件格式。

```bash
jpackage --name myapp --input lib --main-jar main.jar --main-class myapp.Main
```

- Linux: deb and rpm
- macOS: pkg and- dmg
- Windows: msi and exe

不支持交叉编译。例如：windows 平台无法编译到 macOS 平台。

下面是一个例子：

```java
import javax.swing.*;
import java.awt.*;

public class JEP392 {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World Java Swing");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lblText = new JLabel("Hello JEP392!", SwingConstants.CENTER);
        frame.getContentPane().add(lblText);
        frame.pack();
        frame.setVisible(true);
    }
}
```

执行：

```bash
javac JEP392.java
java JEP392.java
jar cvfm JEP392.jar MANIFEST-JEP392.MF io/github/wdpm/jdk16/JEP392.class
```

务必确认这个 jar 可以运行：

```bash
java -jar .\JEP392.jar
```

开始打包：

```bash
C:\.jdks\jdk-16.0.2\bin\jpackage.exe -i . -n JEP392 --main-jar JEP392.jar --main-class JEP392
```

提示缺失依赖：

```
[21:42:44.509] 找不到 WiX 工具 (light.exe, candle.exe)
[21:42:44.510] 从 https://wixtoolset.org 下载 WiX 3.0 或更高版本，然后将其添加到 PATH。
错误：类型 [null] 无效或不受支持
```

因此，需要安装 WiX 工具 3.0+ 来修复依赖，记得添加相应的 /bin/ 到 PATH。修复后进行重试，会生成一个 exe 文件：JEP392-1.0.exe。

执行这个 exe 文件进行安装，在 windows 平台会安装到类似这个路径：`C:\Program Files\JEP392`.

```bash
Mode                 LastWriteTime         Length Name
----                 -------------         ------ ----
d-----          2023/2/9     21:54                app
d-----          2023/2/9     21:54                runtime
-a----          2023/2/9     21:53         427520 JEP392.exe
-a----          2023/2/9     21:53          25214 JEP392.ico
```

运行这个 JEP392.exe，无法运行。** 结论是 jpackage 非常难用。还不如直接使用 `java -jar` 快捷。**

## JEP 393：外部内存访问（第三次孵化）

此提案的目标如下：

- 通用：单个 API 应该能够对各种外部内存（如本机内存、持久内存、堆内存等）进行操作。
- 安全：无论操作何种内存，API 都不应该破坏 JVM 的安全性。
- 控制：可以自由的选择如何释放内存（显式、隐式等）。
- 可用：如果需要访问外部内存，API 应该是 sun.misc.Unsafe。

## JEP 394：instanceof 模式匹配

```java
if(obj instanceof String s){
        // Let pattern matching do the work!
        // 直接使用 s 变量
        }
```

## JEP 395：Records

record 是一种全新的类型，它本质上是一个 final 类，同时所有的属性都是 final 修饰，它会自动编译出 public get hashcode
、equals、toString 等方法，减少了代码编写量。

这个功能之前已经介绍。

## JEP 396：默认强封装 JDK 内部

此 JEP 将 --illegal-access 选项的默认模式从允许更改为拒绝。通过此更改，JDK 的内部包和
API（[关键内部 API](https://openjdk.java.net/jeps/260#Description) 除外）将不再默认打开。

该 JEP 的动机是阻止第三方库、框架和工具使用 JDK 的内部 API 和包，增加了安全性。

## JEP 397：Sealed Classes（密封类）预览

Java 15 尝试解决这个问题，引入了 sealed 类，被 sealed 修饰的类可以指定子类。这样这个类就只能被指定的类继承。

而且 sealed 修饰的类的机制具有传递性，它的子类必须使用指定的关键字进行修饰，且只能是 final、sealed、non-sealed 三者之一。