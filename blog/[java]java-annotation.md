# 注解

- 注解在源代码级别保存信息而不是通过注释文字，使得代码更加规范、整洁、便于维护。
- 通过拓展 annotation API 或使用外部的字节码工具类库，可以拥有对源代码及字节码强大的检查与操作能力。

## 定义注解

### 标记注解

```java

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
}
```

不包含任何元素的注解称为标记注解，上例中的 @Test 就是标记注解。

### 一般注解

```java

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    int id();

    String description() default "no description";
}
```

## 利用反射处理注解 - 简单例子

> 运行时注解的处理，可能会有性能问题

```java
public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found Use Case " + uc.id() + "\n"
                        + uc.description());
                useCases.remove(Integer.valueOf(uc.id()));
            }
        }
        useCases.forEach(i -> System.out.println("Missing use case " + i));
    }

    public static void main(String[] args) {
        List<Integer> useCases = IntStream.range(1, 3)
                                          .boxed()
                                          .collect(Collectors.toList());
        trackUseCases(useCases, UseCaseClient.class);
    }

    // Found Use Case 1
    // Passwords must contain at least one numeric
    // Missing use case 2
}
```

UseCase uc = m.getAnnotation(UseCase.class); 用于获取某个方法 m 的注解信息。

上面涉及的相关代码如下：

UseCaseClient.java

```java
package io.github.wdpm.annotation;

public class UseCaseClient {
    @UseCase(id = 1, description = "Passwords must contain at least one numeric")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }
}
```

UseCase.java

```java
package io.github.wdpm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * UseCase 自定义注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    int id();

    String description() default "no description";
}
```

## 利用反射处理注解 - 复杂例子

Constraints.java

```java
package io.github.wdpm.annotation.persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
    boolean primaryKey() default false;

    boolean allowNull() default true;

    boolean unique() default false;
}
```

DBTable.java

```java
package io.github.wdpm.annotation.persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE) // Applies to classes only
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    String name() default "";
}
```

SQLInteger.java

```java
package io.github.wdpm.annotation.persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    String name() default "";

    Constraints constraints() default @Constraints;
}
```

SQLString.java

```java
package io.github.wdpm.annotation.persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
    int value() default 0;

    String name() default "";

    Constraints constraints() default @Constraints;
}
```

Uniqueness.java

```java
package io.github.wdpm.annotation.persistent;

public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = false);
}
```

使用示例
```java
package io.github.wdpm.annotation.persistent;

@DBTable(name = "member")
public class Member {
    @SQLString(30)
    String  firstName;

    @SQLString(50)
    String  lastName;

    @SQLInteger
    Integer age;

    @SQLString(value = 30, constraints = @Constraints(primaryKey = true))
    String reference;

    static int memberCount;

    @Override
    public String toString() {
        return reference;
    }

    public String getReference() {
        return reference;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }
}
```

简单测试
```java
package io.github.wdpm.annotation.persistent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用反射的运行时注解处理，被处理的注解必须为@Retention(RetentionPolicy.RUNTIME)。
 *
 * <p>
 * usage: args = io.github.wdpm.annotation.persistent.Member
 *
 */
public class TableCreator {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("0 arguments: annotated classes");
            System.exit(0);
        }
        for (String className : args) {
            Class<?> cl      = Class.forName(className);
            DBTable  dbTable = cl.getAnnotation(DBTable.class);
            if (dbTable == null) {
                System.out.println("No DBTable annotations in class " + className);
                continue;
            }

            // handle tableName
            String tableName = dbTable.name();
            // If the name is empty, use the Class name:
            if (tableName.length() < 1)
                tableName = cl.getName().toUpperCase();

            // handle field annotation
            List<String> columnDefs = new ArrayList<>();
            for (Field field : cl.getDeclaredFields()) {
                String       columnName;
                Annotation[] anns       = field.getDeclaredAnnotations();
                if (anns.length < 1)
                    continue; // Not a db table column

                // warning: here use anns[0] to check for simplicity of presentation
                // better practice: for loop anns array
                if (anns[0] instanceof SQLInteger) {
                    SQLInteger sInt = (SQLInteger) anns[0];
                    // Use field name if name not specified
                    if (sInt.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sInt.name();
                    columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
                }
                if (anns[0] instanceof SQLString) {
                    SQLString sString = (SQLString) anns[0];
                    // Use field name if name not specified.
                    if (sString.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sString.name();
                    columnDefs.add(columnName + " VARCHAR(" +
                            sString.value() + ")" +
                            getConstraints(sString.constraints()));
                }
            }

            // concat final SQL
            StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");;
            for (String columnDef : columnDefs)
                createCommand.append("\n ").append(columnDef).append(",");
            // Remove trailing comma
            String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";

            System.out.println("Table Creation SQL for " + className + " is:\n" + tableCreate);
        }
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}

//Table Creation SQL for io.github.wdpm.annotation.persistent.Member is:
// CREATE TABLE member(
//  FIRSTNAME VARCHAR(30),
//  LASTNAME VARCHAR(50),
//  AGE INT,
//  REFERENCE VARCHAR(30) PRIMARY KEY);
```

## 应用

- lombok

