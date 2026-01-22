# JDK 9
> 参考原文 https://www.journaldev.com/13121/java-9-features-with-examples

## 目录
- Java 9 REPL (JShell)
- Factory Methods for Immutable List, Set, Map and Map.Entry
- Private methods in Interfaces
- Java 9 Module System
- Process API Improvements
- CompletableFuture API Improvements
- Reactive Streams
- Stream API Improvements
- Enhanced @Deprecated annotation
- HTTP 2 Client

## JShell
```bash
PS C:\Users\evan> jshell

jshell> int a=10
a ==> 10
```

## Factory Methods for Immutable List, Set, Map and Map.Entry

在Java SE 8和更早版本，可以使用unmodifiableXXX之类的Collections类实用方法来创建不可变的Collection对象。
```
Map<Integer,String> emptyMap = new HashMap<>();
Map<Integer,String> immutableEmptyMap = Collections.unmodifiableMap(emptyMap);
```
这种方式繁琐而冗长。

在Java 9
```
List immutableList = List.of();
List immutableList2 = List.of("one","two","three");

Map emptyImmutableMap = Map.of();
Map nonemptyImmutableMap = Map.of(1, "one", 2, "two", 3, "three");
```
## Private methods in Interfaces
```java
public interface Card{

  private Long createCardID(){
    // Method implementation goes here.
  }

  private static void displayCardDetails(){
    // Method implementation goes here.
  }

}
```
## Java 9 Module System
目标：将JDK，JRE，JAR等分成较小的模块，可以使用所需的任何模块。
变更：JDK 9源码目录新增 jmods 目录，去掉了 jre 目录、rt.jar、tools.jar。基础模块为 java.base。

例子：参考根目录下，com.hello 模块和com.hello.client模块。留意module-info.java文件内容。

## Process API Improvements
Two new interfaces in Process API:
- java.lang.ProcessHandle
- java.lang.ProcessHandle.Info

## CompletableFuture API Improvements
延时执行功能
```
Executor exe = CompletableFuture.delayedExecutor(50L, TimeUnit.SECONDS);
```

## Reactive Streams
- java.util.concurrent.Flow
- java.util.concurrent.Flow.Publisher
- java.util.concurrent.Flow.Subscriber
- java.util.concurrent.Flow.Processor

## Stream API Improvements

This takeWhile() takes a predicate as an argument and returns a Stream of the subset of the given Stream values until 
that Predicate returns false for the first time. If the first value does NOT satisfy that Predicate, it just returns an empty Stream.

```
jshell> Stream.of(1,2,3,4,5,6,7,8,9,10).takeWhile(i -> i < 5 )
                 .forEach(System.out::println);
1
2
3
4
```

## Enhanced @Deprecated annotation

They have added two methods to this Deprecated interface: 
`forRemoval` and `since` to serve this information.

## HTTP 2 Client

introduce a new HTTP 2 Client API under the “java.net.http” package
