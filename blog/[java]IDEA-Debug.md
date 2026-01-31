# IDEA Debug 技巧
## 行断点
![](assets/debug-line-breakpoint.png)
- 在某一行左侧单击，然后以debug模式运行main()

## 方法断点
![](assets/debug-method-breakpoint.png)
- 在某个方法定义的左侧单击，然后以debug模式运行该方法。
- debug会停留于该方法的第一行。

## 异常断点
新建一个自定义运行时异常CustomException
```java
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
设置IDEA异常断点

![](assets/click-exception-breakpoint.png)

- `+` Java Exception Breakpoints 根据名称搜索CustomException并确定。
- 以debug模式运行会抛出CustomException的方法。会发现将停留于throw这行。

![](assets/debug-java-exception-breakpoint.png)

## 断点调试工具栏
### Step Over(F8)
一步一步往下走，直接执行方法体

### Step Into(F7)
遇到方法，会进入方法体里面。不会进入官方类库的方法。

![](assets/debug-step-over-and-step-into.png)

### Force Step Into(Alt + Shift + F7)
遇到方法，会进入方法体里面。可以进入官方类库的方法。

![](assets/debug-force-step-into.png)

### Step Out(Shift + F8)
跳出整个方法体。执行到方法体外的下一行。

![](assets/debug-step-out.png)

### Drop Frame
回退一个Frame，即回退到进入时的方法。一个Frame最小单位应该是一个方法级别。

![](assets/debug-drop-frame.png)

### Run to Cursor(Alt + F9)
运行到光标处。

### Evaluate Expression
计算表达式的值。

![](assets/debug-expression.png)

## 条件断点
代码行左侧单击，然后右键。在condition框输入条件。

![](assets/debug-condition.png)

## 调试多线程
![](assets/debug-multi-threads.png)

- 请注意：使用resume program 按钮来让thread执行，同时保证suspend级别为thread。
## 中断调试
在调试过程中，不想执行当前断点后面的代码再退出。
![](assets/debug-terminal-stop.png)

第一次点击force return。

![](assets/debug-force-return.png)

再次右键，点击force return。

![](assets/debug-force-return-exit.png)

最后，点击红色□ 停止程序。

## 附录：代码片
`BasicDebug.java`
```java
package io.github.wdpm.idea.debug;


public class BasicDebug {
    public static void main(String[] args) {
        // printHelloWorld();
        playFire();
        int a = 1;
        int b = 2;
        int c = a + b;

        for (int i = 0; i < 10; i++) {
            print(i);
        }
    }

    private static void playFire() {
        System.out.println("准备木材");
        System.out.println("点火，危险");
    }

    public static void printHelloWorld() {
        System.out.println("Hello World.");
        System.out.println("Other thing...");
    }

    public static void print(int i) {
        System.out.println(i);
    }
}
```

`CustomException.java`
```java
package io.github.wdpm.idea.debug;


public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

`MultiTreadDebug.java`
```java
package io.github.wdpm.idea.debug;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MultiTreadDebug {
    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime()
                                         .availableProcessors();
        System.out.println(availableProcessors);

        ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);
        Runnable r1 = () -> {
            System.out.println("r1");
        };
        Runnable r2 = () -> {
            System.out.println("r2");
        };
        Runnable r3 = () -> {
            System.out.println("r3");
        };
        executorService.execute(r1);
        executorService.execute(r2);
        executorService.execute(r3);

        System.out.println("main thread");

        executorService.shutdown();
    }
}
```