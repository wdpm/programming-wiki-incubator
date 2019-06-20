# effective java study notes
## 1. 考虑使用静态工厂替代构造方法
``` java
// constructor method
Date date = new Date();

// static factory
Calendar calendar = Calendar.getInstance();
Integer number = Integer.valueOf("3");
```
优点:
- 有名字
- 不需要每次调用时都创建一个新对象
- 可以返回其返回类型的任何子类型的对象
- 返回对象的类可以根据输入参数的不同而不同
- 在编写包含该方法的类时，返回的对象的类不需要存在

缺点:
- 没有公共或受保护构造方法的类不能被子类化
- Java Doc中难以查找

## 2. 当构造方法参数过多时使用 builder 模式
```java
// many parameters in constructor
NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);

// javaBeans pattern
NutritionFacts cocaCola = new NutritionFacts();
cocaCola.setServingSize(240);
cocaCola.setXXX...


// builder pattern
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
.calories(100).sodium(35).carbohydrate(27).build();
```

## 3.使用私有构造方法或枚类实现 Singleton 属性
两种常见方法来构造单例，都采用了构造方法私有和导出公共静态成员。
``` java
// Singleton with public final field
public class Elvis {
  public static final Elvis INSTANCE = new Elvis();
  private Elvis() { ... }
  public void leaveTheBuilding() { ... }
}

// Singleton with static factory
public class Elvis {
  private static final Elvis INSTANCE = new Elvis();
  private Elvis() { ... }
  public static Elvis getInstance() { return INSTANCE; }
  public void leaveTheBuilding() { ... }
}
```
为避免序列化和反序列化时创建新实例，需要：
``` java
// readResolve method to preserve singleton property
private Object readResolve() {
    // Return the one true Elvis and let the garbage collector
    // take care of the Elvis impersonator.
    return INSTANCE;
}
```

## 4.用私有构造方法执行非实例化
``` java
// Noninstantiable utility class
public class UtilityClass {
    // Suppress default constructor for noninstantiability
    private UtilityClass() {
        throw new AssertionError();
    }
}
```
一旦构造方法被意外调用，直接抛异常。
## 5. 依赖注入优于硬连接资源（hardwiring resources）
```java
// Dependency injection provides flexibility and testability
public class SpellChecker {
    private final Lexicon dictionary;
    
    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }
    public boolean isValid(String word) { ... }
    public List<String> suggestions(String typo) { ... }
    }
```
将资源或工厂传递给构造方法。依赖注入极大地增强类的灵活性、可重用性和可测试性。

## 6. 避免创建不必要的对象
``` java
String s = new String("bikini"); // DON'T DO THIS!
String s = "bikini"; // DO THIS
```
优先使用基本类型而不是包装类型，例如使用 long 而不是 Long 。

## 7. 消除过期的对象引用
``` java
public Object pop() {
    if (size == 0)
        throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null; // Eliminate obsolete reference
    return result;
}
```

## 8. 避免使用 Finalizer 和 Cleaner 机制
不要把 Java 中的 Finalizer 或 Cleaner 机制当成的 C ++析构函数的等价物。

## 9. 使用 try-with-resources 语句替代 try-finally 语句
``` java
// try-with-resources on multiple resources - short and sweet
static void copy(String src, String dst) throws IOException {
    try (InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)) {
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = in.read(buf)) >= 0)
            out.write(buf, 0, n);
    }
}
```

## 10. 重写 equals 方法时遵守通用约定
Object 的规范如下： equals 方法实现了一个等价关系
（equivalence relation）。它有以下这些属性:
- 自反性： 对于任何非空引用 x， x.equals(x) 必须返回 true。
- 对称性： 对于任何非空引用 x 和 y，如果且仅当 y.equals(x) 返回 true 时 x.equals(y) 必须返回 true。
- 传递性： 对于任何非空引用 x、y、z，如果 x.equals(y) 返回 true， y.equals(z) 返回 true，则 x.equals(z) 必须返回 true。
- 一致性： 对于任何非空引用 x 和 y，如果在 equals 比较中使用的信息没有修改，则 x.equals(y) 的多次调用必须始终返回 true 或始终返回 false。
- 非空性：对于任何非空引用 x， x.equals(null) 必须返回 false。
里氏替代原则（ Liskov substitution principle）指出，任何类型的重要属性都适用于所有的子类型，因此任何为这种类型编写的方法都应该在其子类上同样适用。

综合起来，以下是编写高质量 equals 方法的配方（recipe）：
1. 使用 == 运算符检查参数是否为该对象的引用。如果是，返回 true。
2. 使用 instanceof 运算符来检查参数是否具有正确的类型。 如果不是，则返回 false。 通常，正确的类型是 equals 方法所在的那个类。 有时候，该类实现了一些接口。 如果类实现了一个接口，该接口可以改进 equals 约定以允许实现接口的类进行比较，那么使用接口。 集合接口（如 Set，List，Map 和 Map.Entry）具有此特性。
3. 参数转换为正确的类型。因为转换操作在 instanceof 中已经处理过，所以它肯定会成功。
4. 对于类中的每个“重要”的属性，请检查该参数属性是否与该对象对应的属性相匹配。如果所有这些测试成功，返回 true，否则返回 false。如果步骤 2 中的类型是一个接口，那么必须通过接口方法访问参数的属性;如果类型是类，则可以直接访问属性，这取决于属性的访问权限。
对于类型为非 float 或 double 的基本类型，使用 == 运算符进行比较；对于对象引用属性，递归地调用 equals 方法；对于 float 基本类型的属性，使用静态 Float.compare(float, float) 方法；对于 double 基本类型的属性，使用 Double.compare(double, double) 方法。Float.NaN，-0.0f 和类似的 double 类型的值，所以需要对 float 和 double 属性进行特殊的处理。

一个例子：
``` java
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNum = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }
}
```
## 12. 始终重写 toString 方法
``` java
// default
PhoneNumber@163b91
```
有时重写toString方法是必要的，方便调试。

## 13. 谨慎地重写 clone 方法
clone 方法的通用规范很薄弱：
创建并返回此对象的副本。 “复制（copy）”的确切含义可能取决于对象的类。 一般是，对于任何对象 x，表
达式 `x.clone() != x` 返回 true，并且 `x.clone().getClass() == x.getClass()` 也返回 true，但它们不是
绝对的要求，但通常情况下， `x.clone().equals(x)` 返回 true，当然这个要求也不是绝对的。

通常，复制功能最好由构造方法或工厂提供。 
```java
new TreeSet<>(s);
```
这个规则的一个例外是数组，它最好用 clone 方法复制。

## 14. 考虑实现 Comparable 接口
``` java
public interface Comparable<T> {
    int compareTo(T t);
}
```
- 实现类必须确保所有 x 和 y 都满足 sgn(x.compareTo(y)) == -sgn(y. compareTo(x)) 。
- 实现类必须确保该关系是可传递的： (x. compareTo(y) > 0 && y.compareTo(z) > 0) 意味着 x.compareTo(z) > 0 。
- 对于所有的 z，实现类必须确保 x.compareTo(y) == 0 意味着 sgn(x.compareTo(z)) == sgn(y.compareTo(z)) 。
- 强烈推荐 (x.compareTo(y) == 0) == (x.equals(y)) ，但不是必需的。

一个例子:
``` java
// BROKEN difference-based comparator - violates transitivity!
static Comparator<Object> hashCodeOrder = new Comparator<>() {
    public int compare(Object o1, Object o2) {
        return o1.hashCode() - o2.hashCode();
    }
};

// Comparator based on static compare method
static Comparator<Object> hashCodeOrder = new Comparator<>() {
    public int compare(Object o1, Object o2) {
        return Integer.compare(o1.hashCode(), o2.hashCode());
    }
};
```
比较 compareTo 方法的实现中的字段值时，请避免使用"<"和">"运算
符。 推荐使用包装类中的静态 compare 方法或 Comparator 接口中的构建方法。

## 15. 使类和成员的可访问性最小化
- private —— 成员只能在声明它的顶级类内访问。
- package-private —— 成员可以从被声明的包中的任何类中访问。这是默认访问级别。
- protected —— 成员可以从被声明的类的子类中访问，以及它声明的包中的任何类。
- public —— 成员可以从任何地方被访问。

尽可能地减少程序元素的可访问性（在合理范围内）。除了作为常量的公共静态 final 属性之外，公共类不应该有公共属性。 确保 `public static final` 属性引用的对象是不可变的。

## 16. 在公共类中使用访问方法而不是公共属性
``` java
// Degenerate classes like this should not be public!
class Point {
    public double x;
    public double y;
}

// Encapsulation of data by accessor methods and mutators
class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
```

## 17. 最小化可变性
不可变类既是它的实例不能被修改的类，例如 String，BigInteger类。
五条规则:
1. 不提供修改对象状态的方法（也称 mutators）。
2. 确保这个类不能被继承（通过 final 修饰类,或者使用私有构造方法并提供静态工厂）。
3. 把所有属性设置为 final。
4. 把所有属性设置为 private。
5. 确保对任何可变组件的互斥访问。
不可变对象本质上线程安全，不需要同步。
不可变类的主要缺点是对于每个不同的值都需要一个单独的对象。

## 18. 组合优于继承
当满足 “B is-an A”关系时使用继承。
否则，考虑使用组合（既包装类），B 通常包含一个 A 的私有实例，并暴露一个不同的API：A 不是 B 的重要部分 ，只是其实现细节。

## 19. 要么设计继承并提供文档说明，要么禁用继承
子类可能会依赖于父类的实现细节，并且如果父类的实现发生改变，子类可能会损坏。

## 20. 接口优于抽象类
Java 有两种机制来定义允许多个实现的类型：接口和抽象类。
抽象类定义的类型，类必须是抽象类的子类。但 Java 只允许单一继承，所以限制很大；而一个类却可以实现多个接口，灵活性非常高。
如果需要导出一个重要的接口，强烈考虑提供一个骨架的实现类（采用抽象类）。

## 21. 为后代设计接口
在 Java 8 中，添加了默认方法（default method），目的
是允许将方法添加到现有的接口。

## 22. 接口仅用来定义类型
不应该使用常量接口来导出常量，应使用不可实例化的类来导出常量。

## 23. 类层次结构优于标签类
标签类例子：
``` java
// Tagged class - vastly inferior to a class hierarchy!
class Figure {
    enum Shape {
        RECTANGLE, CIRCLE
    };

    // Tag field - the shape of this figure
    final Shape shape;
    // These fields are used only if shape is RECTANGLE
    double length;
    double width;
    // This field is used only if shape is CIRCLE
    double radius;

    // Constructor for circle
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // Constructor for rectangle
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        switch (shape) {
        case RECTANGLE:
            return length * width;
        case CIRCLE:
            return Math.PI * (radius * radius);
        default:
            throw new AssertionError(shape);
        }
    }
}
```
类层次例子：
```java
// Class hierarchy replacement for a tagged class
abstract class Figure {
    abstract double area();
}

class Circle extends Figure {
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

class Rectangle extends Figure {
    final double length;
    final double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}
```

## 24. 支持使用静态成员类而不是非静态类
有四种嵌套类：静态成员类，非静态成员类，匿名类和局部类。 除第一种以外，剩下三种都被称为内部类（inner class）。
静态成员类和非静态成员类之间的唯一区别是静态成员类在其声明中具有 `static` 修饰符。
如果你声明了一个不需要访问宿主实例的成员类，把 `static` 修饰符放在它的声明中，使它成为一个静态成员类，而不是非静态的成员类。
匿名类是创建小函数对象和处理对象的首选方法，但 lambda 表达式现在是首选。

- 如果一个嵌套的类需要在一个方法之外可见，或者太长而不能很好地适应一个方法，使用一个成员类。 
- 如果一个成员类的每个实例都需要一个对其宿主实例的引用，使其成为非静态的; 否则，使其静态。 
- 如果这个类属于一个方法内部，并且只需要从一个地方创建实例，并且存在一个预置类型来说明这个类的特征，那么把它作为一个匿名类；否则，把它变成局部类。

## 25. 将源文件限制为单个顶级类
永远不要将多个顶级类或接口放在一个源文件中。

## 26. 不要使用原始类型
一个类或接口，它的声明有一个或多个类型参数（type parameters ），被称之为泛型类或泛型接口。例如 `List<E>`。
不推荐使用 `List`，而是应该 显示指定 `E`，例如 `List<String>`。
- `Set<Object>` 是一个参数化类型，表示一个可以包含任何类型对象的集合;
- `Set<?>` 是一个通配符类型，表示一个只能包含某些未知类型对象的集合;
- `Set` 是一个原始类型，它不在泛型类型系统之列。 
- 前两个类型是安全的，最后一个不是。

| 术语                    |     中文含义     |                             举例 |
| :---------------------- | :--------------: | -------------------------------: |
| Parameterized type      |    参数化类型    |                     List<String> |
| Actual type parameter   |   实际类型参数   |                           String |
| Generic type            |     泛型类型     |                          List<E> |
| Formal type parameter   |   形式类型参数   |                                E |
| Unbounded wildcard type | 无限制通配符类型 |                          List<?> |
| Raw type                |     原始类型     |                             List |
| Bounded type parameter  |   限制类型参数   |               <E extends Number> |
| Recursive type bound    |   递归类型限制   |        <T extends Comparable<T>> |
| Bounded wildcard type   |  限制通配符类型  |           List<? extends Number> |
| Generic method          |     泛型方法     | static <E> List<E> asList(E[] a) |
| Type token              |     类型令牌     |                     String.class |

## 27. 消除非检查警告
尽可能消除每一个未经检查的警告。
如果不能消除警告，但坚信引发警告的代码是类型安全的，那么可用`@SuppressWarnings(“unchecked”)` 注解来抑制警告。

## 28. 列表优于数组 
数组是协变和具体化的; 泛型是不变的，类型擦除的。
推荐使用 List 而不是 Array。

## 29. 优先考虑泛型
泛型类型比需要在客户端代码中强制转换的类型更安全，更易于使用。

## 30. 优先使用泛型方法
``` java
// The element type in s1 may be different from s2
public static Set union(Set s1, Set s2) {
    Set result = new HashSet(s1);
    result.addAll(s2);
    return result;
}

// Generic method
public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
```
``` java
// Using a recursive type bound to express mutual comparability
public static <E extends Comparable<E>> E max(Collection<E> c);
```

## 31. 使用限定通配符来增加 API 的灵活性
助记符来帮助记住使用哪种通配符类型：
PECS 代表： producer-extends，consumer-super。
如果一个参数化类型代表一个 T 生产者，使用 <? extends T> ；如果它代表 T 消费者，则使用 <? super T> 。 
在 Stack 示例中， pushAll 方法的 src 参数生成栈使用的 E 实例，因此 src的合适类型为 Iterable<? extends E> ；
popAll 方法的 dst 参数消费 Stack 中的 E 实例，因此 dst 的合适类型是 C ollection <? super E>。

于是，可以改进上面的例子：
``` java
public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2)

public static <T extends Comparable<? super T>> T max(List<? extends T> list)
```

## 32. 合理地结合泛型和可变参数
``` java
// List as a typesafe alternative to a generic varargs parameter
static <T> List<T> flatten(List<List<? extends T>> lists) {
    List<T> result = new ArrayList<>();
    for (List<? extends T> list : lists)
        result.addAll(list);
    return result;
}
```

## 33. 优先考虑类型安全的异构容器
``` java
public class Favorites {
    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(Objects.requireNonNull(type), instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}
```
`Class<T>` 可以是 `Class<String>` 或 `Class<Integer>`

## 34. 使用枚举类型替代整型常量
``` java
// The int enum pattern - severely deficient!
public static final int APPLE_FUJI = 0;
public static final int APPLE_PIPPIN = 1;
public static final int APPLE_GRANNY_SMITH = 2;
public static final int ORANGE_NAVEL = 0;
public static final int ORANGE_TEMPLE = 1;
public static final int ORANGE_BLOOD = 2;。

// Use this
public enum Apple { FUJI, PIPPIN, GRANNY_SMITH }
public enum Orange { NAVEL, TEMPLE, BLOOD }

// Enum type with constant-specific class bodies and data
public enum Operation {
    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        public double apply(double x, double y) {
            return x / y;
        }
    };
    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);
}


public static void main(String[] args) {
    double x = Double.parseDouble(args[0]);
    double y = Double.parseDouble(args[1]);
    for (Operation op : Operation.values())
        System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y);
}
```

## 35. 使用实例属性替代序数
``` java
public enum Ensemble {
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5), SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8), NONET(9),
    DECTET(10), TRIPLE_QUARTET(12);
    private final int numberOfMusicians;

    Ensemble(int size) {
        this.numberOfMusicians = size;
    }

    public int numberOfMusicians() {
        return numberOfMusicians;
    }
}
```

## 36. 使用 EnumSet 替代位属性
``` java
// EnumSet - a modern replacement for bit fields
public class Text {
    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
    }

    // Any Set could be passed in, but EnumSet is clearly best
    public void applyStyles(Set<Style> styles) { ... }
}

text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
```

## 37. 使用 EnumMap 替代序数索引
使用序数来索引数组很不合适：改用 EnumMap。
``` java
    // Using an EnumMap to associate data with an enum
Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle =new EnumMap<>(Plant.LifeCycle.class);

for(Plant.LifeCycle lc:Plant.LifeCycle.values())
    plantsByLifeCycle.put(lc,new HashSet<>());

for(Plant p:garden)
    plantsByLifeCycle.get(p.lifeCycle).add(p);

System.out.println(plantsByLifeCycle);
```

## 38. 使用接口模拟可扩展的枚举
``` java
// Emulated extensible enum using an interface
public interface Operation {
    double apply(double x, double y);
}

public enum BasicOperation implements Operation {
    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    };
    
    private final String symbol;

    BasicOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
```
虽然不能编写可扩展的枚举类型，但可以编写一个接口来配合实现接口的基本的枚举类型，来对它进行模拟。

## 39. 注解优于命名模式
``` java
// naming pattern
tsetSafetyOverride

// anotation
@Test
```

## 40. 始终使用 Override 注解
在你认为要重写父类声明的每个方法声明上使用 Override 注解。

## 41. 使用标记接口定义类型
- 标记接口定义了一个由标记类实例实现的类型；标记注解则不会。
Java 的序列化机制使用 Serializable 标记接口来指示某个类型是可序列化的。
Serializable 接口指示实例有资格被 ObjectOutputStream 处理。
- 标记接口对于标记注解的另一个优点是可以更精确地定位目标。
Set 接口就是一个受限的标记接口。 它仅适用于 Collection 子类型，但不会添加超出 Collection 定义的方法。

## 42. lambda 表达式优于匿名类
``` java
// Lambda expression as function object (replaces anonymous class)
Collections.sort(words,
(s1, s2) -> Integer.compare(s1.length(), s2.length()));
```

## 43. 方法引用优于 lambda 表达式

## 44. 优先使用标准的函数式接口
| 接口              | 方法                | 示例                |
| ----------------- | ------------------- | ------------------- |
| UnaryOperator<T>  | T apply(T t)        | String::toLowerCase |
| BinaryOperator<T> | T apply(T t1, T t2) | BigInteger::add     |
| Predicate<T>      | boolean test(T t)   | Collection::isEmpty |
| Function<T,R>     | R apply(T t)        | Arrays::asList      |
| Supplier<T>       | T get()             | Instant::now        |
| Consumer<T>       | void accept(T t)    | System.out::println |

## 45. 明智审慎地使用 Stream
在 Java 8 中添加了 Stream API，以简化顺序或并行执行批量操作的任务。 该 API 提供了两个关键的抽象：
- 流(Stream)，表示有限或无限的数据元素序列，
- 流管道 (stream pipeline)，表示对这些元素的多级计算。

## 46. 优先考虑流中无副作用的函数
最重要的收集器工厂是 toList ， toSet ， toMap ， groupingBy 和 join 。

## 47. 优先使用 Collection 而不是 Stream 来作为方法的返回类型

## 48. 谨慎使用流并行
Java 于 1996 年发布时，内置了对线程的支持，包括同步和 wait / notify 机制。 
Java 5 引入了 java.util.concurrent 类库，带有并发集合和执行器框架。
Java 7 引入了 fork-join 包，这是一个用于并行分解的高性能框架。
Java 8 引入了流，可以通过对parallel 方法的单个调用来并行化。

总之，甚至不要尝试并行化流管道，除非你有充分的理由相信它将保持计算的正确性并提高其速度。

## 49. 检查参数有效性
在 Java 7 中添加的 `Objects.requireNonNull` 方法可以执行null检测。
Assert 断言。
总之，每次编写方法或构造方法时，都应该考虑对其参数存在哪些限制，并在方法体开头使用显式检查来强制执行这些限制。

## 50. 必要时进行防御性拷贝
有问题的例子，因为 Date 类型 可变。
``` java
// Broken "immutable" time period class
public final class Period {
    private final Date start;
    private final Date end;

    /**
     * @param start the beginning of the period
     * @param end   the end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException     if start or end is null
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(start + " after " + end;
        this.start = start;
        this.end = end;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }

}
```
``` java
// Attack the internals of a Period instance
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);
end.setYear(78); // Modifies internals of p!
```
必须将每个可变参数的防御性拷贝应用到构造方法中。
``` java
// Repaired constructor - makes defensive copies of parameters
public Period(Date start, Date end) {
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());
    if (this.start.compareTo(this.end) > 0)
        throw new IllegalArgumentException(this.start + " after " + this.end);
}
```

## 51. 仔细设计方法签名
- 仔细选择方法名名称。
- 不要过分地提供方便的方法。
- 避免过长的参数列表。
- 对于参数类型，优先选择接口而不是类。
- 与布尔型参数相比，优先使用两个元素枚举类型。
``` java
public enum TemperatureScale { FAHRENHEIT, CELSIUS }
```

## 52. 明智审慎地使用重载
一个安全和保守的策略是永远不要导出两个具有相同参数数量的重载。
对于构造方法，无法使用不同的名称：类的多个构造函数总是被重载。

## 53. 明智审慎地使用可变参数
可变参数机制首先创建一个数组，其大小是在调用位置传递的参数数量，然后将参数值放入数组中，最后将数组传递给方法。

在性能关键的情况下使用可变参数时要小心。每次调用可变参数方法都会导致数组分配和初始化。
``` java
public void foo() { }
public void foo(int a1) { }
public void foo(int a1, int a2) { }
public void foo(int a1, int a2, int a3) { }
public void foo(int a1, int a2, int a3, int... rest) { }
```
## 54. 返回空的数组或集合，不要返回 null
``` java
/**
 * @return a list containing all of the cheeses in the shop, or null if no
 *         cheeses are available for purchase.
 */
public List<Cheese> getCheeses() {
    return cheesesInStock.isEmpty() ? null : new ArrayList<>(cheesesInStock);
}

// The right way to return a possibly empty collection
public List<Cheese> getCheeses() {
    return new ArrayList<>(cheesesInStock);
}
```
数组的情况与集合的情况相同。 永远不要返回 null，而是返回长度为零的数组。

## 55. 明智审慎地返回 Optional
在 Java 8 之前，编写在特定情况下无法返回任何值的方法时，可以采用两种方法。
- 要么抛出异常;
- 要么返回null(假设返回类型是对象是引用类型)。

在 Java 8 中，有第三种方法来编写可能无法返回任何值的方法。
Optional<T> 类表示一个不可变的容器，它可以包含一个非 null 的 T 引用，也可以什么都不包含。
``` java
// Returns maximum value in collection as an Optional<E>
public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
    if (c.isEmpty())
        return Optional.empty();
    E result = null;
    for (E e : c)
        if (result == null || e.compareTo(result) > 0)
            result = Objects.requireNonNull(e);
    return Optional.of(result);
}
```
总之，如果发现自己编写的方法不能总是返回值，并且认为该方法的用户在每次调用时考虑这种可能性很重要，那么或许应该返回一个 Optional 的方法。

## 56. 为所有已公开的 API 元素编写文档注释
- 要正确地记录 API，必须在每个导出的类、接口、构造方法、方法和属性声明之前加上文档注释。
- 文档注释在源代码和生成的文档中都应该是可读的通用原则。 优先考虑生成的文档的可读性。
- 类或接口中的两个成员或构造方法不应具有相同的概要描述。
- 记录泛型类型或方法时，请务必记录所有类型参数。
- 在记录枚举类型时，一定要记录常量。
- 在为注解类型记录文档时，一定要记录任何成员。
- 无论类或静态方法是否线程安全，都应该在文档中描述其线程安全级别。

## 57. 最小化局部变量的作用域
- 用于最小化局部变量作用域的最强大的技术是在首次使用的地方声明它。
- 几乎每个局部变量声明都应该包含一个初始化器。

## 58. for-each 循环优于传统 for 循环

有三种常见的情况是你不能分别使用 for-each 循环:
1. 有损过滤（Destructive filtering）——如果需要遍历集合，并删除指定选元素，则需要使用显式迭代器，以便可以调用其 remove 方法。 
通常可以使用在 Java 8 中添加的 Collection 类中的 removeIf 方法，来避免显式遍历。

2. 转换——如果需要遍历一个列表或数组并替换其元素的部分或全部值，那么需要列表迭代器或数组索引来替换元素的值。

3. 并行迭代——如果需要并行地遍历多个集合，那么需要显式地控制迭代器或索引变量，以便所有迭代器或索引变量都可以同步进行)。

## 59. 了解并使用库
从 Java 7 开始，不应该再使用 Random。
在大多数情况下，选择的随机数生成器现在是 `ThreadLocalRandom` 。
对于 fork 连接池和并行流，使用 `SplittableRandom` 。

每个程序员都应该熟悉 java.lang、java.util 和 java.io 的基础知识及其子包。

## 60. 若需要精确答案就应避免使用 float 和 double 类型
对于任何需要精确答案的计算，不要使用 float 或 double 类型。
- 如果希望系统来处理十进制小数点，并且不介意不使用基本类型带来的不便和成本，请使用 BigDecimal 。
- 如果性能是最重要的，你不介意自己处理十进制小数点，而且数值不是太大，可以使用 int 或 long。

## 61. 基本数据类型优于包装类

## 62. 当使用其他类型更合适时应避免使用字符串

## 63. 当心字符串连接引起的性能问题
不要使用字符串连接操作符合并多个字符串，除非性能无关紧要。否则使用 StringBuilder 的 append 方法。

## 64. 通过接口引用对象
``` java
// Good - uses interface as type
Set<Son> sonSet = new LinkedHashSet<>();

// Bad - uses class as type!
LinkedHashSet<Son> sonSet = new LinkedHashSet<>();
```
如果存在合适的接口类型，那么应该使用接口类型声明参数、返回值、变量和字段。
如果没有合适的接口，那么用类引用对象是完全合适的，推荐使用类层次结构中提供所需功能的最底层的类。

## 65. 接口优于反射
核心反射机制 java.lang.reflect 提供对任意类的编程访问。给定一个 Class 对象，你可以获得 Constructor、Method 和 Field 实例。

如果必须在编译时处理未知的类，则应该尽可能只使用反射实例化对象，并使用在编译时已知的接口或超类访问对象。

## 66. 明智审慎地本地方法
Java 本地接口（JNI）允许 Java 程序调用本地方法，主要有3种用途：
1. 提供对特定于平台的设施（如注册中心）的访问。
2. 提供对现有本地代码库的访问，包括提供对遗留数据访问。
3. 可以通过本地语言编写应用程序中注重性能的部分，以提高性能。

## 67. 明智审慎地进行优化
不要努力写快的程序，要努力写好程序；速度自然会提高。

## 68. 遵守被广泛认可的命名约定
命名约定分为两类：排版和语法。

## 69. 只针对异常的情况下才使用异常
设计良好的 API 不应该强迫它的客户端为了正常的控制流程而使用异常。

## 70. 对可恢复的情况使用受检异常，对编程错误使用运行时异常
Java 程序设计语言提供了三种 throwable：
1. 受检异常（checked exceptions）
2. 运行时异常（runtime exceptions）
3. 错误（errors）

如果期望调用者能够合理的恢复程序运行，就应该使用受检异常。
用运行时异常来表明编程错误。
你实现的所有非受检的 throwable 都应该是 RuntimeExceptiond 子类。

## 71. 避免不必要的使用受检异常

## 72. 优先使用标准的异常
| 异常                            | 使用场合            |
| ---------------------- | ----------------------------- |
| IllegalArgumentException        | 非 null 的参数值不正确         |
| IllegalStateException           | 不适合方法调用的对象状态        |
| NullPointerException            | 在禁止使用 null 的情况下参数值为 null |
| IndexOutOfBoundsExecption       | 下标参数值越界                        |
| ConcurrentModificationException | 禁止并发修改时，检测到对象的并发修改  |
| UnsupportedOperationException   | 对象不支持用户请求的方法              |

## 73. 抛出与抽象对应的异常
- 异常转译 （exception translation）：
更高层的实现应该捕获低层的异常，同时抛出可以按照高层抽象进行解释的异常。
- 异常链（exception chaining）：
低层的异常被传到高层的异常，高层的异常提供访问方法 (Throwable 的
getCause 方法）来获得低层的异常。

## 74. 每个方法抛出的异常都需要创建文档
利用 Javadoc 的 ＠throws 标签， 准确地记录下抛出每个异常的条件。

## 75. 在细节消息中包含失败一捕获信息
为了捕获失败，异常的细节信息应该包含“对该异常有贡献”的所有参数和字段的值。

## 76. 保持失败原子性
失败的方法调用应该使对象保持在被调用之前的状态。
获取失败原子性的三种方法：
- 调整计算处理过程的顺序，使得任何可能会失败的计算部分都在对象状态被修改之前发生。
- 在对象的一份临时拷贝上执行操作，当操作完成之后再用临时拷贝中的结果代替对象的内容。
- 编写一段恢复代码 （recovery code），由它来拦截操作过程中发生的失败，以及便对象回滚到操作开始之前的状态上。

## 77. 不要忽略异常
try-catch 语句块中不要忽略对异常的处理。

## 78. 同步访问共享的可变数据
关键字 synchronized 可以保证在同一时刻，只有一个线程可以执行某一个方法，或者某一个代码块。
许多程序员把同步的概念仅仅理解为一种互斥（ mutual exclusion ）的方式，即，当一个对象被一个线程修改的时候，可以阻止
另一个线程观察到对象内部不一致的状态。
``` java
// Lock-free synchronization with java.util.concurrent.atomic
private static final Atomiclong nextSerialNum = new Atomiclong();
public static long generateSerialNumber() {
    return nextSerialNum.getAndIncrement();
}
```
当多个线程共享可变数据的时候，每个读或者写数据的线程都必须执行同步。

## 79. 避免过度同步
CopyOnWriteArrayList 是 ArrayList 的一种变体，它通过重新拷贝整个底层数组，在这里实现所有的写操作。
由于内部数组永远不改动，因此迭代不需要锁定，速度也非常快。
``` java
// Thread-safe observable set with CopyOnWriteArrayList
private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<>();

public void addObserver(SetObserver<E> observer) {
    observers.add(observer);
}

public Boolean removeObserver(SetObserver<E> observer) {
    return observers.remove(observer);
}

private void notifyElementAdded(E element) {
    for (SetObserver<E> observer : observers)
        observer.added(this, element);
}
```
通常来说，应该在同步区域内做尽可能少的工作:
获得锁，检查共享数据，根据需要转换数据，然后释放锁。

## 80. executor 、task 和 stream 优先于线程
``` java
ExecutorService exec = Executors.newSingleThreadExecutor();
exec.execute(runnable);
exec.shutdown();
```
在 Java 7 中， Executor 框架被扩展为支持 fork-join 任务，这些任务是通过一种称作 fork-join 池的特殊 executor 服务运行的。

## 81. 并发工具优于 wait 和 notify
java.util.concurrent 中更高级的工具分成三类：
1. Executor Framework 
2. 并发集合（Concurrent Collection）
并发集合为标准的集合接口（如 List 、Queue 和 Map ）提供了高性能的并发实现。为了提供高并发性，这些实现在内部自己管理同步。
3. 同步器（Synchronizer）
同步器（Synchronizer）是使线程能够等待另一个线程的对象，允许它们协调动作。
最常用的同步器是 CountDownLatch 和 Semaphore 。

没有理由在新代码中使用 wait 方法和 notify 方法，即使有，也是极少的。

## 82. 文档应包含线程安全属性
- 不可变的 — 这个类的实例看起来是常量。不需要外部同步。示例包括 String、Long 和 BigInteger。
- 无条件线程安全 — 该类的实例是可变的，但是该类具有足够的内部同步，因此无需任何外部同步即可并发地使用该类的实例。例如 AtomicLong 和 ConcurrentHashMap。
- 有条件的线程安全 — 与无条件线程安全类似，只是有些方法需要外部同步才能安全并发使用。示例包括Collections.synchronized 包装器返回的集合，其迭代器需要外部同步。
- 非线程安全 — 该类的实例是可变的。要并发地使用它们，客户端必须使用外部同步来包围每个方法调用（或调用序列）。这样的例子包括通用的集合实现，例如 ArrayList 和 HashMap。
- 线程对立 — 即使每个方法调用都被外部同步包围，该类对于并发使用也是不安全的。线程对立通常是由于在不同步的情况下修改静态数据而导致的。没有人故意编写线程对立类；此类通常是由于没有考虑并发性而导致的。当发现类或方法与线程不相容时，通常将其修复或弃用。

## 83. 明智审慎的使用延迟初始化
如果一个字段只在类的一小部分实例上访问，并且初始化该字段的代价很高，那么延
迟初始化可能是值得的。
1. 同步访问器: 最为简单的实现
``` java
// Lazy initialization of instance field - synchronized accessor
private FieldType field;

private synchronized FieldType getField() {
    if (field == null)
        field = computeFieldValue();
    return field;
}
```
2. lazy initialization holder class 模式: 提高静态字段的性能
``` java
// Lazy initialization holder class idiom for static fields
private static class FieldHolder {
    static final FieldType field = computeFieldValue();
}

private static FieldType getField() {
    return FieldHolder.field;
}
```
3. 双重检查模式: 提高实例字段的性能
``` java
// Double-check idiom for lazy initialization of instance fields
private volatile FieldType field;

private FieldType getField() {
    FieldType result = field;
    if (result == null) { // First check (no locking)
        synchronized (this) {
            if (field == null) // Second check (with locking)
                field = result = computeFieldValue();
        }
    }
    return result;
}
```
4. 单检查模式: 可以容忍重复初始化的实例字段
``` java
// Single-check idiom - can cause repeated initialization!
private volatile FieldType field;

private FieldType getField() {
    FieldType result = field;
    if (result == null)
        field = result = computeFieldValue();
    return result;
}
```
因为单检查没有 第二重同步检查（synchronized），可能会被多线程并发初始化。

## 84. 不要依赖线程调度器
不要依赖线程调度器来判断程序的正确性。生成的程序既不健壮也不可移植。

## 85. 优先选择 Java 序列化的替代方案

## 86. 非常谨慎地实现 Serializable
- 实现 Serializable 接口的一个主要代价是，一旦类的实现被发布，它就会降低更改该类实现的灵活性。
试图使用类的旧版本序列化实例，再使用新版本反序列化实例的客户端（反之亦然）程序将会失败。
- 实现 Serializable 接口的第二个代价是，增加了出现 bug 和安全漏洞的可能性。
- 实现 Serializable 接口的第三个代价是，它增加了与发布类的新版本相关的测试负担。

## 87. 考虑使用自定义的序列化形式
只有在合理描述对象的逻辑状态时，才使用默认的序列化形式；否则，设计一个适合描述对象的自定义序列化形式。

## 88. 保护性的编写 readObject 方法

## 89. 对于实例控制，枚举类型优于 readResolve
readResolve 可以保证唯一单例属性：
``` java
// readResolve for instance control - you can do better!
private Object readResolve() {
    // Return the one true Elvis and let the garbage collector
    // take care of the Elvis impersonator.
    return INSTANCE;
}
```
``` java
// Enum singleton - the preferred approach
public enum Elvis {
    INSTANCE;
    private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
```

## 90. 考虑用序列化代理代替序列化实例
``` java
// Serialization proxy for Period class
private static class SerializationProxy implements Serializable {
    private final Date start;
    private final Date end;

    SerializationProxy(Period p) {
        this.start = p.start;
        this.end = p.end;
    }

    private static final long serialVersionUID = 234098243823485285L; // Any number will do
}
```
将下面的 writeReplace 方法添加到外围类中。
``` java
// writeReplace method for the serialization proxy pattern
private Object writeReplace() {
    return new SerializationProxy(this);
}
```
在外围类中添加如下 readObject 方法, 防止伪造违反该类约束条件的示例。
``` java
// readObject method for the serialization proxy pattern
private void readObject(ObjectInputStream stream) throws InvalidObjectException {
    throw new InvalidObjectException("Proxy required");
}
```