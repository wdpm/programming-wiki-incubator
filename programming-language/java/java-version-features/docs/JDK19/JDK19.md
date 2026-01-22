# JDK 19

## JEP 405: Record 模式匹配（预览）

继续加强 Record 模式匹配哦，JDK 19 中已经可以直接解构 record 实例的内部变量了：

```java
public class RecordEnhancement {
    public static void main(String[] args) {
        Object dog1 = new Dog("Dog", 1);
        if (dog1 instanceof Dog(String name, Integer age)) {
            System.out.println(name + ":" + age);
        }
    }

    record Dog(String name, Integer age) {
    }
}
```

运行时记得启用 preview 特性：`--enable-preview`。

## JEP 425: 虚拟线程 (预览)

千呼万唤始出来，Java 终于有协程了？

本质还是嫌弃 Thread 不够轻量，还想要更加轻量的协程，来压榨一切可以压榨的计算资源。

两种流派：

- 例如常用的 Tomcat 会为每次请求单独使用一个线程进行请求处理，同时限制处理请求的线程数量以防止线程过多而崩溃。这种属于多线程传统派。
- 放弃多 Thread，而是使用异步编程。这种属于激进的革新派。但异步编程堆栈难以跟踪，因为执行顺序交叉混乱，也因此 debug
  非常不直观。代码复杂度增加，不再是线性顺序流动了。

示例参考；io.github.wdpm.jdk19.VirtualThreadExample

## JEP 427: switch 模式匹配 (三次预览)

之前已经介绍了。

## JEP 422: Linux/RISC-V Port

目标是让 JDK 19+ 支持 RISC-V 指令架构。

## JEP 426: Vector API (四次孵化)

还没孵化出来。

## JEP 428: Structured Concurrency (孵化)

通过简化多线程编程并将在不同线程中运行的多个任务视为单个工作单元，简化错误处理和取消，提高可靠性并增强可观察性。

这点很好，JDK 多线程的 API 一直以来都比较繁琐，简化后对开发者会更加友好。隔壁有 python 和 Go 可以抄。