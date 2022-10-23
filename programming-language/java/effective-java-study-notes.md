# effective java study notes
Effective Java���İ棨��3�棩����ʼǡ�

## 1. ����ʹ�þ�̬����������췽��
``` java
// constructor method
Date date = new Date();

// static factory
Calendar calendar = Calendar.getInstance();
Integer number = Integer.valueOf("3");
```
�ŵ�:
- ������
- ����Ҫÿ�ε���ʱ������һ���¶���
- ���Է����䷵�����͵��κ������͵Ķ���
- ���ض��������Ը�����������Ĳ�ͬ����ͬ
- �ڱ�д�����÷�������ʱ�����صĶ�����಻��Ҫ����

ȱ��:
- û�й������ܱ������췽�����಻�ܱ����໯
- Java Doc�����Բ���

## 2. �����췽����������ʱʹ�� builder ģʽ
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

## 3.ʹ��˽�й��췽����ö��ʵ�� Singleton ����
���ֳ������������쵥�����������˹��췽��˽�к͵���������̬��Ա��
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
Ϊ�������л��ͷ����л�ʱ������ʵ������Ҫ��
``` java
// readResolve method to preserve singleton property
private Object readResolve() {
    // Return the one true Elvis and let the garbage collector
    // take care of the Elvis impersonator.
    return INSTANCE;
}
```

## 4.��˽�й��췽��ִ�з�ʵ����
``` java
// Noninstantiable utility class
public class UtilityClass {
    // Suppress default constructor for noninstantiability
    private UtilityClass() {
        throw new AssertionError();
    }
}
```
һ�����췽����������ã�ֱ�����쳣��
## 5. ����ע������Ӳ������Դ��hardwiring resources��
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
����Դ�򹤳����ݸ����췽��������ע�뼫�����ǿ�������ԡ��������ԺͿɲ����ԡ�

## 6. ���ⴴ������Ҫ�Ķ���
``` java
String s = new String("bikini"); // DON'T DO THIS!
String s = "bikini"; // DO THIS
```
����ʹ�û������Ͷ����ǰ�װ���ͣ�����ʹ�� long ������ Long ��

## 7. �������ڵĶ�������
``` java
public Object pop() {
    if (size == 0)
        throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null; // Eliminate obsolete reference
    return result;
}
```

## 8. ����ʹ�� Finalizer �� Cleaner ����
��Ҫ�� Java �е� Finalizer �� Cleaner ���Ƶ��ɵ� C ++���������ĵȼ��

## 9. ʹ�� try-with-resources ������ try-finally ���
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

## 10. ��д equals ����ʱ����ͨ��Լ��
Object �Ĺ淶���£� equals ����ʵ����һ���ȼ۹�ϵ
��equivalence relation��������������Щ����:
- �Է��ԣ� �����κηǿ����� x�� x.equals(x) ���뷵�� true��
- �Գ��ԣ� �����κηǿ����� x �� y������ҽ��� y.equals(x) ���� true ʱ x.equals(y) ���뷵�� true��
- �����ԣ� �����κηǿ����� x��y��z����� x.equals(y) ���� true�� y.equals(z) ���� true���� x.equals(z) ���뷵�� true��
- һ���ԣ� �����κηǿ����� x �� y������� equals �Ƚ���ʹ�õ���Ϣû���޸ģ��� x.equals(y) �Ķ�ε��ñ���ʼ�շ��� true ��ʼ�շ��� false��
- �ǿ��ԣ������κηǿ����� x�� x.equals(null) ���뷵�� false��
�������ԭ�� Liskov substitution principle��ָ�����κ����͵���Ҫ���Զ����������е������ͣ�����κ�Ϊ�������ͱ�д�ķ�����Ӧ������������ͬ�����á�

�ۺ������������Ǳ�д������ equals �������䷽��recipe����
1. ʹ�� == ������������Ƿ�Ϊ�ö�������á�����ǣ����� true��
2. ʹ�� instanceof ��������������Ƿ������ȷ�����͡� ������ǣ��򷵻� false�� ͨ������ȷ�������� equals �������ڵ��Ǹ��ࡣ ��ʱ�򣬸���ʵ����һЩ�ӿڡ� �����ʵ����һ���ӿڣ��ýӿڿ��ԸĽ� equals Լ��������ʵ�ֽӿڵ�����бȽϣ���ôʹ�ýӿڡ� ���Ͻӿڣ��� Set��List��Map �� Map.Entry�����д����ԡ�
3. ����ת��Ϊ��ȷ�����͡���Ϊת�������� instanceof ���Ѿ���������������϶���ɹ���
4. �������е�ÿ������Ҫ�������ԣ�����ò��������Ƿ���ö����Ӧ��������ƥ�䡣���������Щ���Գɹ������� true�����򷵻� false��������� 2 �е�������һ���ӿڣ���ô����ͨ���ӿڷ������ʲ���������;����������࣬�����ֱ�ӷ������ԣ���ȡ�������Եķ���Ȩ�ޡ�
��������Ϊ�� float �� double �Ļ������ͣ�ʹ�� == ��������бȽϣ����ڶ����������ԣ��ݹ�ص��� equals ���������� float �������͵����ԣ�ʹ�þ�̬ Float.compare(float, float) ���������� double �������͵����ԣ�ʹ�� Double.compare(double, double) ������Float.NaN��-0.0f �����Ƶ� double ���͵�ֵ��������Ҫ�� float �� double ���Խ�������Ĵ���

һ�����ӣ�
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
## 12. ʼ����д toString ����
``` java
// default
PhoneNumber@163b91
```
��ʱ��дtoString�����Ǳ�Ҫ�ģ�������ԡ�

## 13. ��������д clone ����
clone ������ͨ�ù淶�ܱ�����
���������ش˶���ĸ����� �����ƣ�copy������ȷ�к������ȡ���ڶ�����ࡣ һ���ǣ������κζ��� x����
��ʽ `x.clone() != x` ���� true������ `x.clone().getClass() == x.getClass()` Ҳ���� true�������ǲ���
���Ե�Ҫ�󣬵�ͨ������£� `x.clone().equals(x)` ���� true����Ȼ���Ҫ��Ҳ���Ǿ��Եġ�

ͨ�������ƹ�������ɹ��췽���򹤳��ṩ�� 
```java
new TreeSet<>(s);
```
��������һ�����������飬������� clone �������ơ�

## 14. ����ʵ�� Comparable �ӿ�
``` java
public interface Comparable<T> {
    int compareTo(T t);
}
```
- ʵ�������ȷ������ x �� y ������ sgn(x.compareTo(y)) == -sgn(y. compareTo(x)) ��
- ʵ�������ȷ���ù�ϵ�ǿɴ��ݵģ� (x. compareTo(y) > 0 && y.compareTo(z) > 0) ��ζ�� x.compareTo(z) > 0 ��
- �������е� z��ʵ�������ȷ�� x.compareTo(y) == 0 ��ζ�� sgn(x.compareTo(z)) == sgn(y.compareTo(z)) ��
- ǿ���Ƽ� (x.compareTo(y) == 0) == (x.equals(y)) �������Ǳ���ġ�

һ������:
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
�Ƚ� compareTo ������ʵ���е��ֶ�ֵʱ�������ʹ��"<"��">"����
���� �Ƽ�ʹ�ð�װ���еľ�̬ compare ������ Comparator �ӿ��еĹ���������

## 15. ʹ��ͳ�Ա�Ŀɷ�������С��
- private ���� ��Աֻ�����������Ķ������ڷ��ʡ�
- package-private ���� ��Ա���Դӱ������İ��е��κ����з��ʡ�����Ĭ�Ϸ��ʼ���
- protected ���� ��Ա���Դӱ���������������з��ʣ��Լ��������İ��е��κ��ࡣ
- public ���� ��Ա���Դ��κεط������ʡ�

�����ܵؼ��ٳ���Ԫ�صĿɷ����ԣ��ں���Χ�ڣ���������Ϊ�����Ĺ�����̬ final ����֮�⣬�����಻Ӧ���й������ԡ� ȷ�� `public static final` �������õĶ����ǲ��ɱ�ġ�

## 16. �ڹ�������ʹ�÷��ʷ��������ǹ�������
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

## 17. ��С���ɱ���
���ɱ����������ʵ�����ܱ��޸ĵ��࣬���� String��BigInteger�ࡣ
��������:
1. ���ṩ�޸Ķ���״̬�ķ�����Ҳ�� mutators����
2. ȷ������಻�ܱ��̳У�ͨ�� final ������,����ʹ��˽�й��췽�����ṩ��̬��������
3. ��������������Ϊ final��
4. ��������������Ϊ private��
5. ȷ�����κοɱ�����Ļ�����ʡ�
���ɱ���������̰߳�ȫ������Ҫͬ����
���ɱ������Ҫȱ���Ƕ���ÿ����ͬ��ֵ����Ҫһ�������Ķ���

## 18. ������ڼ̳�
������ ��B is-an A����ϵʱʹ�ü̳С�
���򣬿���ʹ����ϣ��Ȱ�װ�ࣩ��B ͨ������һ�� A ��˽��ʵ��������¶һ����ͬ��API��A ���� B ����Ҫ���� ��ֻ����ʵ��ϸ�ڡ�

## 19. Ҫô��Ƽ̳в��ṩ�ĵ�˵����Ҫô���ü̳�
������ܻ������ڸ����ʵ��ϸ�ڣ�������������ʵ�ַ����ı䣬������ܻ��𻵡�

## 20. �ӿ����ڳ�����
Java �����ֻ���������������ʵ�ֵ����ͣ��ӿںͳ����ࡣ
�����ඨ������ͣ�������ǳ���������ࡣ�� Java ֻ����һ�̳У��������ƺܴ󣻶�һ����ȴ����ʵ�ֶ���ӿڣ�����Էǳ��ߡ�
�����Ҫ����һ����Ҫ�Ľӿڣ�ǿ�ҿ����ṩһ���Ǽܵ�ʵ���ࣨ���ó����ࣩ��

## 21. Ϊ�����ƽӿ�
�� Java 8 �У������Ĭ�Ϸ�����default method����Ŀ��
������������ӵ����еĽӿڡ�

## 22. �ӿڽ�������������
��Ӧ��ʹ�ó����ӿ�������������Ӧʹ�ò���ʵ��������������������

## 23. ���νṹ���ڱ�ǩ��
��ǩ�����ӣ�
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
�������ӣ�
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

## 24. ֧��ʹ�þ�̬��Ա������ǷǾ�̬��
������Ƕ���ࣺ��̬��Ա�࣬�Ǿ�̬��Ա�࣬������;ֲ��ࡣ ����һ�����⣬ʣ�����ֶ�����Ϊ�ڲ��ࣨinner class����
��̬��Ա��ͷǾ�̬��Ա��֮���Ψһ�����Ǿ�̬��Ա�����������о��� `static` ���η���
�����������һ������Ҫ��������ʵ���ĳ�Ա�࣬�� `static` ���η��������������У�ʹ����Ϊһ����̬��Ա�࣬�����ǷǾ�̬�ĳ�Ա�ࡣ
�������Ǵ���С��������ʹ���������ѡ�������� lambda ���ʽ��������ѡ��

- ���һ��Ƕ�׵�����Ҫ��һ������֮��ɼ�������̫�������ܺܺõ���Ӧһ��������ʹ��һ����Ա�ࡣ 
- ���һ����Ա���ÿ��ʵ������Ҫһ����������ʵ�������ã�ʹ���Ϊ�Ǿ�̬��; ����ʹ�侲̬�� 
- ������������һ�������ڲ�������ֻ��Ҫ��һ���ط�����ʵ�������Ҵ���һ��Ԥ��������˵����������������ô������Ϊһ�������ࣻ���򣬰�����ɾֲ��ࡣ

## 25. ��Դ�ļ�����Ϊ����������
��Զ��Ҫ������������ӿڷ���һ��Դ�ļ��С�

## 26. ��Ҫʹ��ԭʼ����
һ�����ӿڣ�����������һ���������Ͳ�����type parameters ��������֮Ϊ��������ͽӿڡ����� `List<E>`��
���Ƽ�ʹ�� `List`������Ӧ�� ��ʾָ�� `E`������ `List<String>`��
- `Set<Object>` ��һ�����������ͣ���ʾһ�����԰����κ����Ͷ���ļ���;
- `Set<?>` ��һ��ͨ������ͣ���ʾһ��ֻ�ܰ���ĳЩδ֪���Ͷ���ļ���;
- `Set` ��һ��ԭʼ���ͣ������ڷ�������ϵͳ֮�С� 
- ǰ���������ǰ�ȫ�ģ����һ�����ǡ�

| ����                    |     ���ĺ���     |                             ���� |
| :---------------------- | :--------------: | -------------------------------: |
| Parameterized type      |    ����������    |                     List<String> |
| Actual type parameter   |   ʵ�����Ͳ���   |                           String |
| Generic type            |     ��������     |                          List<E> |
| Formal type parameter   |   ��ʽ���Ͳ���   |                                E |
| Unbounded wildcard type | ������ͨ������� |                          List<?> |
| Raw type                |     ԭʼ����     |                             List |
| Bounded type parameter  |   �������Ͳ���   |               <E extends Number> |
| Recursive type bound    |   �ݹ���������   |        <T extends Comparable<T>> |
| Bounded wildcard type   |  ����ͨ�������  |           List<? extends Number> |
| Generic method          |     ���ͷ���     | static <E> List<E> asList(E[] a) |
| Type token              |     ��������     |                     String.class |

## 27. �����Ǽ�龯��
����������ÿһ��δ�����ľ��档
��������������棬��������������Ĵ��������Ͱ�ȫ�ģ���ô����`@SuppressWarnings(��unchecked��)` ע�������ƾ��档

## 28. �б��������� 
������Э��;��廯��; �����ǲ���ģ����Ͳ����ġ�
�Ƽ�ʹ�� List ������ Array��

## 29. ���ȿ��Ƿ���
�������ͱ���Ҫ�ڿͻ��˴�����ǿ��ת�������͸���ȫ��������ʹ�á�

## 30. ����ʹ�÷��ͷ���
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

## 31. ʹ���޶�ͨ��������� API �������
���Ƿ���������סʹ������ͨ������ͣ�
PECS ���� producer-extends��consumer-super��
���һ�����������ʹ���һ�� T �����ߣ�ʹ�� <? extends T> ����������� T �����ߣ���ʹ�� <? super T> �� 
�� Stack ʾ���У� pushAll ������ src ��������ջʹ�õ� E ʵ������� src�ĺ�������Ϊ Iterable<? extends E> ��
popAll ������ dst �������� Stack �е� E ʵ������� dst �ĺ��������� C ollection <? super E>��

���ǣ����ԸĽ���������ӣ�
``` java
public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2)

public static <T extends Comparable<? super T>> T max(List<? extends T> list)
```

## 32. ����ؽ�Ϸ��ͺͿɱ����
``` java
// List as a typesafe alternative to a generic varargs parameter
static <T> List<T> flatten(List<List<? extends T>> lists) {
    List<T> result = new ArrayList<>();
    for (List<? extends T> list : lists)
        result.addAll(list);
    return result;
}
```

## 33. ���ȿ������Ͱ�ȫ���칹����
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
`Class<T>` ������ `Class<String>` �� `Class<Integer>`

## 34. ʹ��ö������������ͳ���
``` java
// The int enum pattern - severely deficient!
public static final int APPLE_FUJI = 0;
public static final int APPLE_PIPPIN = 1;
public static final int APPLE_GRANNY_SMITH = 2;
public static final int ORANGE_NAVEL = 0;
public static final int ORANGE_TEMPLE = 1;
public static final int ORANGE_BLOOD = 2;��

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

## 35. ʹ��ʵ�������������
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

## 36. ʹ�� EnumSet ���λ����
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

## 37. ʹ�� EnumMap �����������
ʹ����������������ܲ����ʣ����� EnumMap��
``` java
    // Using an EnumMap to associate data with an enum
Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle =new EnumMap<>(Plant.LifeCycle.class);

for(Plant.LifeCycle lc:Plant.LifeCycle.values())
    plantsByLifeCycle.put(lc,new HashSet<>());

for(Plant p:garden)
    plantsByLifeCycle.get(p.lifeCycle).add(p);

System.out.println(plantsByLifeCycle);
```

## 38. ʹ�ýӿ�ģ�����չ��ö��
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
��Ȼ���ܱ�д����չ��ö�����ͣ������Ա�дһ���ӿ������ʵ�ֽӿڵĻ�����ö�����ͣ�����������ģ�⡣

## 39. ע����������ģʽ
``` java
// naming pattern
tsetSafetyOverride

// anotation
@Test
```

## 40. ʼ��ʹ�� Override ע��
������ΪҪ��д����������ÿ������������ʹ�� Override ע�⡣

## 41. ʹ�ñ�ǽӿڶ�������
- ��ǽӿڶ�����һ���ɱ����ʵ��ʵ�ֵ����ͣ����ע���򲻻ᡣ
Java �����л�����ʹ�� Serializable ��ǽӿ���ָʾĳ�������ǿ����л��ġ�
Serializable �ӿ�ָʾʵ�����ʸ� ObjectOutputStream ����
- ��ǽӿڶ��ڱ��ע�����һ���ŵ��ǿ��Ը���ȷ�ض�λĿ�ꡣ
Set �ӿھ���һ�����޵ı�ǽӿڡ� ���������� Collection �����ͣ���������ӳ��� Collection ����ķ�����

## 42. lambda ���ʽ����������
``` java
// Lambda expression as function object (replaces anonymous class)
Collections.sort(words,
(s1, s2) -> Integer.compare(s1.length(), s2.length()));
```

## 43. ������������ lambda ���ʽ

## 44. ����ʹ�ñ�׼�ĺ���ʽ�ӿ�
| �ӿ�              | ����                | ʾ��                |
| ----------------- | ------------------- | ------------------- |
| UnaryOperator<T>  | T apply(T t)        | String::toLowerCase |
| BinaryOperator<T> | T apply(T t1, T t2) | BigInteger::add     |
| Predicate<T>      | boolean test(T t)   | Collection::isEmpty |
| Function<T,R>     | R apply(T t)        | Arrays::asList      |
| Supplier<T>       | T get()             | Instant::now        |
| Consumer<T>       | void accept(T t)    | System.out::println |

## 45. ����������ʹ�� Stream
�� Java 8 ������� Stream API���Լ�˳�����ִ���������������� �� API �ṩ�������ؼ��ĳ���
- ��(Stream)����ʾ���޻����޵�����Ԫ�����У�
- ���ܵ� (stream pipeline)����ʾ����ЩԪ�صĶ༶���㡣

## 46. ���ȿ��������޸����õĺ���
����Ҫ���ռ��������� toList �� toSet �� toMap �� groupingBy �� join ��

## 47. ����ʹ�� Collection ������ Stream ����Ϊ�����ķ�������

## 48. ����ʹ��������
Java �� 1996 �귢��ʱ�������˶��̵߳�֧�֣�����ͬ���� wait / notify ���ơ� 
Java 5 ������ java.util.concurrent ��⣬���в������Ϻ�ִ������ܡ�
Java 7 ������ fork-join ��������һ�����ڲ��зֽ�ĸ����ܿ�ܡ�
Java 8 ��������������ͨ����parallel �����ĵ������������л���

��֮��������Ҫ���Բ��л����ܵ����������г�ֵ����������������ּ������ȷ�Բ�������ٶȡ�

## 49. ��������Ч��
�� Java 7 ����ӵ� `Objects.requireNonNull` ��������ִ��null��⡣
Assert ���ԡ�
��֮��ÿ�α�д�������췽��ʱ����Ӧ�ÿ��Ƕ������������Щ���ƣ����ڷ����忪ͷʹ����ʽ�����ǿ��ִ����Щ���ơ�

## 50. ��Ҫʱ���з����Կ���
����������ӣ���Ϊ Date ���� �ɱ䡣
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
���뽫ÿ���ɱ�����ķ����Կ���Ӧ�õ����췽���С�
``` java
// Repaired constructor - makes defensive copies of parameters
public Period(Date start, Date end) {
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());
    if (this.start.compareTo(this.end) > 0)
        throw new IllegalArgumentException(this.start + " after " + this.end);
}
```

## 51. ��ϸ��Ʒ���ǩ��
- ��ϸѡ�񷽷������ơ�
- ��Ҫ���ֵ��ṩ����ķ�����
- ��������Ĳ����б�
- ���ڲ������ͣ�����ѡ��ӿڶ������ࡣ
- �벼���Ͳ�����ȣ�����ʹ������Ԫ��ö�����͡�
``` java
public enum TemperatureScale { FAHRENHEIT, CELSIUS }
```

## 52. ����������ʹ������
һ����ȫ�ͱ��صĲ�������Զ��Ҫ��������������ͬ�������������ء�
���ڹ��췽�����޷�ʹ�ò�ͬ�����ƣ���Ķ�����캯�����Ǳ����ء�

## 53. ����������ʹ�ÿɱ����
�ɱ�����������ȴ���һ�����飬���С���ڵ���λ�ô��ݵĲ���������Ȼ�󽫲���ֵ���������У�������鴫�ݸ�������

�����ܹؼ��������ʹ�ÿɱ����ʱҪС�ġ�ÿ�ε��ÿɱ�����������ᵼ���������ͳ�ʼ����
``` java
public void foo() { }
public void foo(int a1) { }
public void foo(int a1, int a2) { }
public void foo(int a1, int a2, int a3) { }
public void foo(int a1, int a2, int a3, int... rest) { }
```
## 54. ���ؿյ�����򼯺ϣ���Ҫ���� null
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
���������뼯�ϵ������ͬ�� ��Զ��Ҫ���� null�����Ƿ��س���Ϊ������顣

## 55. ���������ط��� Optional
�� Java 8 ֮ǰ����д���ض�������޷������κ�ֵ�ķ���ʱ�����Բ������ַ�����
- Ҫô�׳��쳣;
- Ҫô����null(���践�������Ƕ�������������)��

�� Java 8 �У��е����ַ�������д�����޷������κ�ֵ�ķ�����
Optional<T> ���ʾһ�����ɱ�������������԰���һ���� null �� T ���ã�Ҳ����ʲô����������
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
��֮����������Լ���д�ķ����������Ƿ���ֵ��������Ϊ�÷������û���ÿ�ε���ʱ�������ֿ����Ժ���Ҫ����ô����Ӧ�÷���һ�� Optional �ķ�����

## 56. Ϊ�����ѹ����� API Ԫ�ر�д�ĵ�ע��
- Ҫ��ȷ�ؼ�¼ API��������ÿ���������ࡢ�ӿڡ����췽������������������֮ǰ�����ĵ�ע�͡�
- �ĵ�ע����Դ��������ɵ��ĵ��ж�Ӧ���ǿɶ���ͨ��ԭ�� ���ȿ������ɵ��ĵ��Ŀɶ��ԡ�
- ���ӿ��е�������Ա���췽����Ӧ������ͬ�ĸ�Ҫ������
- ��¼�������ͻ򷽷�ʱ������ؼ�¼�������Ͳ�����
- �ڼ�¼ö������ʱ��һ��Ҫ��¼������
- ��Ϊע�����ͼ�¼�ĵ�ʱ��һ��Ҫ��¼�κγ�Ա��
- �������̬�����Ƿ��̰߳�ȫ����Ӧ�����ĵ����������̰߳�ȫ����

## 57. ��С���ֲ�������������
- ������С���ֲ��������������ǿ��ļ��������״�ʹ�õĵط���������
- ����ÿ���ֲ�����������Ӧ�ð���һ����ʼ������

## 58. for-each ѭ�����ڴ�ͳ for ѭ��

�����ֳ�����������㲻�ֱܷ�ʹ�� for-each ѭ��:
1. ������ˣ�Destructive filtering�����������Ҫ�������ϣ���ɾ��ָ��ѡԪ�أ�����Ҫʹ����ʽ���������Ա���Ե����� remove ������ 
ͨ������ʹ���� Java 8 ����ӵ� Collection ���е� removeIf ��������������ʽ������

2. ת�����������Ҫ����һ���б�����鲢�滻��Ԫ�صĲ��ֻ�ȫ��ֵ����ô��Ҫ�б�������������������滻Ԫ�ص�ֵ��

3. ���е������������Ҫ���еر���������ϣ���ô��Ҫ��ʽ�ؿ��Ƶ������������������Ա����е���������������������ͬ������)��

## 59. �˽Ⲣʹ�ÿ�
�� Java 7 ��ʼ����Ӧ����ʹ�� Random��
�ڴ��������£�ѡ�������������������� `ThreadLocalRandom` ��
���� fork ���ӳغͲ�������ʹ�� `SplittableRandom` ��

ÿ������Ա��Ӧ����Ϥ java.lang��java.util �� java.io �Ļ���֪ʶ�����Ӱ���

## 60. ����Ҫ��ȷ�𰸾�Ӧ����ʹ�� float �� double ����
�����κ���Ҫ��ȷ�𰸵ļ��㣬��Ҫʹ�� float �� double ���͡�
- ���ϣ��ϵͳ������ʮ����С���㣬���Ҳ����ⲻʹ�û������ʹ����Ĳ���ͳɱ�����ʹ�� BigDecimal ��
- �������������Ҫ�ģ��㲻�����Լ�����ʮ����С���㣬������ֵ����̫�󣬿���ʹ�� int �� long��

## 61. ���������������ڰ�װ��

## 62. ��ʹ���������͸�����ʱӦ����ʹ���ַ���

## 63. �����ַ��������������������
��Ҫʹ���ַ������Ӳ������ϲ�����ַ��������������޹ؽ�Ҫ������ʹ�� StringBuilder �� append ������

## 64. ͨ���ӿ����ö���
``` java
// Good - uses interface as type
Set<Son> sonSet = new LinkedHashSet<>();

// Bad - uses class as type!
LinkedHashSet<Son> sonSet = new LinkedHashSet<>();
```
������ں��ʵĽӿ����ͣ���ôӦ��ʹ�ýӿ�������������������ֵ���������ֶΡ�
���û�к��ʵĽӿڣ���ô�������ö�������ȫ���ʵģ��Ƽ�ʹ�����νṹ���ṩ���蹦�ܵ���ײ���ࡣ

## 65. �ӿ����ڷ���
���ķ������ java.lang.reflect �ṩ��������ı�̷��ʡ�����һ�� Class ��������Ի�� Constructor��Method �� Field ʵ����

��������ڱ���ʱ����δ֪���࣬��Ӧ�þ�����ֻʹ�÷���ʵ�������󣬲�ʹ���ڱ���ʱ��֪�Ľӿڻ�����ʶ���

## 66. ���������ر��ط���
Java ���ؽӿڣ�JNI������ Java ������ñ��ط�������Ҫ��3����;��
1. �ṩ���ض���ƽ̨����ʩ����ע�����ģ��ķ��ʡ�
2. �ṩ�����б��ش����ķ��ʣ������ṩ���������ݷ��ʡ�
3. ����ͨ���������Ա�дӦ�ó�����ע�����ܵĲ��֣���������ܡ�

## 67. ���������ؽ����Ż�
��ҪŬ��д��ĳ���ҪŬ��д�ó����ٶ���Ȼ����ߡ�

## 68. ���ر��㷺�Ͽɵ�����Լ��
����Լ����Ϊ���ࣺ�Ű���﷨��

## 69. ֻ����쳣������²�ʹ���쳣
������õ� API ��Ӧ��ǿ�����Ŀͻ���Ϊ�������Ŀ������̶�ʹ���쳣��

## 70. �Կɻָ������ʹ���ܼ��쳣���Ա�̴���ʹ������ʱ�쳣
Java ������������ṩ������ throwable��
1. �ܼ��쳣��checked exceptions��
2. ����ʱ�쳣��runtime exceptions��
3. ����errors��

��������������ܹ�����Ļָ��������У���Ӧ��ʹ���ܼ��쳣��
������ʱ�쳣��������̴���
��ʵ�ֵ����з��ܼ�� throwable ��Ӧ���� RuntimeExceptiond ���ࡣ

## 71. ���ⲻ��Ҫ��ʹ���ܼ��쳣

## 72. ����ʹ�ñ�׼���쳣
| �쳣                            | ʹ�ó���            |
| ---------------------- | ----------------------------- |
| IllegalArgumentException        | �� null �Ĳ���ֵ����ȷ         |
| IllegalStateException           | ���ʺϷ������õĶ���״̬        |
| NullPointerException            | �ڽ�ֹʹ�� null ������²���ֵΪ null |
| IndexOutOfBoundsExecption       | �±����ֵԽ��                        |
| ConcurrentModificationException | ��ֹ�����޸�ʱ����⵽����Ĳ����޸�  |
| UnsupportedOperationException   | ����֧���û�����ķ���              |

## 73. �׳�������Ӧ���쳣
- �쳣ת�� ��exception translation����
���߲��ʵ��Ӧ�ò���Ͳ���쳣��ͬʱ�׳����԰��ո߲������н��͵��쳣��
- �쳣����exception chaining����
�Ͳ���쳣�������߲���쳣���߲���쳣�ṩ���ʷ��� (Throwable ��
getCause ����������õͲ���쳣��

## 74. ÿ�������׳����쳣����Ҫ�����ĵ�
���� Javadoc �� ��throws ��ǩ�� ׼ȷ�ؼ�¼���׳�ÿ���쳣��������

## 75. ��ϸ����Ϣ�а���ʧ��һ������Ϣ
Ϊ�˲���ʧ�ܣ��쳣��ϸ����ϢӦ�ð������Ը��쳣�й��ס������в������ֶε�ֵ��

## 76. ����ʧ��ԭ����
ʧ�ܵķ�������Ӧ��ʹ���󱣳��ڱ�����֮ǰ��״̬��
��ȡʧ��ԭ���Ե����ַ�����
- �������㴦����̵�˳��ʹ���κο��ܻ�ʧ�ܵļ��㲿�ֶ��ڶ���״̬���޸�֮ǰ������
- �ڶ����һ����ʱ������ִ�в��������������֮��������ʱ�����еĽ�������������ݡ�
- ��дһ�λָ����� ��recovery code�������������ز��������з�����ʧ�ܣ��Լ������ع���������ʼ֮ǰ��״̬�ϡ�

## 77. ��Ҫ�����쳣
try-catch �����в�Ҫ���Զ��쳣�Ĵ���

## 78. ͬ�����ʹ���Ŀɱ�����
�ؼ��� synchronized ���Ա�֤��ͬһʱ�̣�ֻ��һ���߳̿���ִ��ĳһ������������ĳһ������顣
������Ա��ͬ���ĸ���������Ϊһ�ֻ��⣨ mutual exclusion ���ķ�ʽ��������һ������һ���߳��޸ĵ�ʱ�򣬿�����ֹ
��һ���̹߳۲쵽�����ڲ���һ�µ�״̬��
``` java
// Lock-free synchronization with java.util.concurrent.atomic
private static final Atomiclong nextSerialNum = new Atomiclong();
public static long generateSerialNumber() {
    return nextSerialNum.getAndIncrement();
}
```
������̹߳���ɱ����ݵ�ʱ��ÿ��������д���ݵ��̶߳�����ִ��ͬ����

## 79. �������ͬ��
CopyOnWriteArrayList �� ArrayList ��һ�ֱ��壬��ͨ�����¿��������ײ����飬������ʵ�����е�д������
�����ڲ�������Զ���Ķ�����˵�������Ҫ�������ٶ�Ҳ�ǳ��졣
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
ͨ����˵��Ӧ����ͬ�����������������ٵĹ���:
���������鹲�����ݣ�������Ҫת�����ݣ�Ȼ���ͷ�����

## 80. executor ��task �� stream �������߳�
``` java
ExecutorService exec = Executors.newSingleThreadExecutor();
exec.execute(runnable);
exec.shutdown();
```
�� Java 7 �У� Executor ��ܱ���չΪ֧�� fork-join ������Щ������ͨ��һ�ֳ��� fork-join �ص����� executor �������еġ�

## 81. ������������ wait �� notify
java.util.concurrent �и��߼��Ĺ��߷ֳ����ࣺ
1. Executor Framework 
2. �������ϣ�Concurrent Collection��
��������Ϊ��׼�ļ��Ͻӿڣ��� List ��Queue �� Map ���ṩ�˸����ܵĲ���ʵ�֡�Ϊ���ṩ�߲����ԣ���Щʵ�����ڲ��Լ�����ͬ����
3. ͬ������Synchronizer��
ͬ������Synchronizer����ʹ�߳��ܹ��ȴ���һ���̵߳Ķ�����������Э��������
��õ�ͬ������ CountDownLatch �� Semaphore ��

û���������´�����ʹ�� wait ������ notify ��������ʹ�У�Ҳ�Ǽ��ٵġ�

## 82. �ĵ�Ӧ�����̰߳�ȫ����
- ���ɱ�� �� ������ʵ���������ǳ���������Ҫ�ⲿͬ����ʾ������ String��Long �� BigInteger��
- �������̰߳�ȫ �� �����ʵ���ǿɱ�ģ����Ǹ�������㹻���ڲ�ͬ������������κ��ⲿͬ�����ɲ�����ʹ�ø����ʵ�������� AtomicLong �� ConcurrentHashMap��
- ���������̰߳�ȫ �� ���������̰߳�ȫ���ƣ�ֻ����Щ������Ҫ�ⲿͬ�����ܰ�ȫ����ʹ�á�ʾ������Collections.synchronized ��װ�����صļ��ϣ����������Ҫ�ⲿͬ����
- ���̰߳�ȫ �� �����ʵ���ǿɱ�ġ�Ҫ������ʹ�����ǣ��ͻ��˱���ʹ���ⲿͬ������Χÿ���������ã���������У������������Ӱ���ͨ�õļ���ʵ�֣����� ArrayList �� HashMap��
- �̶߳��� �� ��ʹÿ���������ö����ⲿͬ����Χ��������ڲ���ʹ��Ҳ�ǲ���ȫ�ġ��̶߳���ͨ���������ڲ�ͬ����������޸ľ�̬���ݶ����µġ�û���˹����д�̶߳����ࣻ����ͨ��������û�п��ǲ����Զ����µġ���������򷽷����̲߳�����ʱ��ͨ�������޸������á�

## 83. ����������ʹ���ӳٳ�ʼ��
���һ���ֶ�ֻ�����һС����ʵ���Ϸ��ʣ����ҳ�ʼ�����ֶεĴ��ۺܸߣ���ô��
�ٳ�ʼ��������ֵ�õġ�
1. ͬ��������: ��Ϊ�򵥵�ʵ��
``` java
// Lazy initialization of instance field - synchronized accessor
private FieldType field;

private synchronized FieldType getField() {
    if (field == null)
        field = computeFieldValue();
    return field;
}
```
2. lazy initialization holder class ģʽ: ��߾�̬�ֶε�����
``` java
// Lazy initialization holder class idiom for static fields
private static class FieldHolder {
    static final FieldType field = computeFieldValue();
}

private static FieldType getField() {
    return FieldHolder.field;
}
```
3. ˫�ؼ��ģʽ: ���ʵ���ֶε�����
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
4. �����ģʽ: ���������ظ���ʼ����ʵ���ֶ�
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
��Ϊ�����û�� �ڶ���ͬ����飨synchronized�������ܻᱻ���̲߳�����ʼ����

## 84. ��Ҫ�����̵߳�����
��Ҫ�����̵߳��������жϳ������ȷ�ԡ����ɵĳ���Ȳ���׳Ҳ������ֲ��

## 85. ����ѡ�� Java ���л����������

## 86. �ǳ�������ʵ�� Serializable
- ʵ�� Serializable �ӿڵ�һ����Ҫ�����ǣ�һ�����ʵ�ֱ����������ͻή�͸��ĸ���ʵ�ֵ�����ԡ�
��ͼʹ����ľɰ汾���л�ʵ������ʹ���°汾�����л�ʵ���Ŀͻ��ˣ���֮��Ȼ�����򽫻�ʧ�ܡ�
- ʵ�� Serializable �ӿڵĵڶ��������ǣ������˳��� bug �Ͱ�ȫ©���Ŀ����ԡ�
- ʵ�� Serializable �ӿڵĵ����������ǣ����������뷢������°汾��صĲ��Ը�����

## 87. ����ʹ���Զ�������л���ʽ
ֻ���ں�������������߼�״̬ʱ����ʹ��Ĭ�ϵ����л���ʽ���������һ���ʺ�����������Զ������л���ʽ��

## 88. �����Եı�д readObject ����

## 89. ����ʵ�����ƣ�ö���������� readResolve
readResolve ���Ա�֤Ψһ�������ԣ�
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

## 90. ���������л�����������л�ʵ��
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
������� writeReplace ������ӵ���Χ���С�
``` java
// writeReplace method for the serialization proxy pattern
private Object writeReplace() {
    return new SerializationProxy(this);
}
```
����Χ����������� readObject ����, ��ֹα��Υ������Լ��������ʾ����
``` java
// readObject method for the serialization proxy pattern
private void readObject(ObjectInputStream stream) throws InvalidObjectException {
    throw new InvalidObjectException("Proxy required");
}
```