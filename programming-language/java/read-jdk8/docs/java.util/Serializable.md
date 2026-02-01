# Serializable
```java
public interface Serializable {}
```
- 序列化接口没有方法或字段，仅用于标识可序列化的语义。
- 通过实现java.io.Serializable接口的类，可以启用类的可序列化性。可序列化类的所有子类型本身都是可序列化的。
- 反序列化期间，将使用该类的公共或受保护的无参数构造函数初始化非序列化类的字段。可序列化子类的字段将从流中恢复。

## 特殊处理
在序列化和反序列化过程中需要特殊处理的类必须实现具有以下确切签名的特殊方法：
```java
private void writeObject(java.io.ObjectOutputStream out)
  throws IOException
private void readObject(java.io.ObjectInputStream in)
  throws IOException, ClassNotFoundException;
private void readObjectNoData()
  throws ObjectStreamException;
```

- 可以调用out.defaultWriteObject来调用保存对象字段的默认机制。
通过使用writeObject方法将单个字段写入ObjectOutputStream或使用DataOutput支持的原始数据类型的方法来保存状态。

- 可以调用in.defaultReadObject来调用用于恢复对象的non-static和non-transient字段的默认机制。

- > The readObjectNoData method is responsible for initializing the state of the object for its particular class in the 
event that the serialization stream does not list the given class as a superclass of the object being deserialized.

## 替代
```java
ANY-ACCESS-MODIFIER Object writeReplace() throws ObjectStreamException;
ANY-ACCESS-MODIFIER Object readResolve() throws ObjectStreamException;
```

## serialVersionUID
```java
ANY-ACCESS-MODIFIER static final long serialVersionUID = ?;
```
- 强烈建议所有可序列化的类显式声明serialVersionUID
- 此外，推荐使用private修饰符定义serialVersionUID