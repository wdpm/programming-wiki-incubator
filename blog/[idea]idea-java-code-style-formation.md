# IDEA Java 代码格式化

AlignFieldsAndVariablesInColumns.java

```java
package io.github.wdpm.idea.tips;

/**
 * Editor -> Code Style -> Java -> wrapping and braces -> Group declarations
 * <br/>
 * <p>Align fields in columns</p>
 * <p>Align variables in columns</p>
 */
public class AlignFieldsAndVariablesInColumns {
    // Align fields in columns
    public  String str = "hello world";
    private int    a   = 1;

    public static void main(String[] args) {
        // Align variables in columns
        int     a    = 2;
        boolean flag = true;
    }
}
```

CommentCodeStyle.java

```java
package io.github.wdpm.idea.tips;

/**
 * Editor -> Code Style -> Java -> Code Generation:
 *
 * <p>×: line comment at first column</p>
 * <p>√: add space at comment start</p>
 * <p>×: block comment at first column</p>
 */
public class CommentCodeStyle {
    public static void main(String[] args) {
        // add space at comment start
    }
}
```

StreamStyle.java

```java
package io.github.wdpm.idea.tips;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Editor ->  Code Style -> Java -> Wrapping and Braces:
 * <pre>
 * - Chained method calls [Wrap always]
 *   - Wrap first call [ ]
 *   - Align when multiline [√]
 *   - 重新设置格式时，勾选【保持换行符】
 * </pre>
 */
public class StreamStyle {
    public List<Integer> evenNumbers(List<Integer> integers) {
        return integers.stream()
                       .filter(i -> i % 2 == 0)
                       .collect(Collectors.toList());
    }
}
```