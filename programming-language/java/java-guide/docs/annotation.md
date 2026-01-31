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

## 利用反射处理注解
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
                                          .boxed().collect(Collectors.toList());
        trackUseCases(useCases, UseCaseClient.class);
    }

    // Found Use Case 1
    // Passwords must contain at least one numeric
    // Missing use case 2
}
```
UseCase uc = m.getAnnotation(UseCase.class); 用于获取某个方法m的注解信息。

> 利用反射处理注解的更复杂例子，参阅代码io.github.wdpm.annotation.persistent/

## 利用javac处理注解
> 编译时注解的处理

```java
@SupportedAnnotationTypes("io.github.wdpm.annotation.javac.Simple")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SimpleProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement t : annotations)
            System.out.println(t);

        for (Element el : roundEnv.getElementsAnnotatedWith(Simple.class))
            display(el);

        return false;
    }
```
- 继承AbstractProcessor 并重写process方法。
- Element el : roundEnv.getElementsAnnotatedWith(Simple.class) 以hardcode形式获取被Simple 注解标注的元素。
- 创建 `src\main\resources\META-INF\services\javax.annotation.processing.Processor` 文件，添加自定义的注解处理器类名
```
io.github.wdpm.annotation.javac.SimpleProcessor
```
- 运行javac测试
```bash
javac -processor io.github.wdpm.annotation.javac.SimpleProcessor SimpleTest.java
```
> 错误: 找不到注释处理程序 'io.github.wdpm.annotation.javac.SimpleProcessor'

### 应用
- lombok

## 基于注解的单元测试
- [实现@Unit](https://lingcoder.gitee.io/onjava8/#/book/23-Annotations?id=%e5%ae%9e%e7%8e%b0-unit)

