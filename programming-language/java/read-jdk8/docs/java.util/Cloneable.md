# Cloneable
```java
public interface Cloneable {}
```
- 一个类实现Cloneable接口，以向Object.clone()方法指示：该方法为该类的实例进行逐域复制是合法的。
- 实现此接口的类应使用公共方法重写Object.clone(protected方法)。