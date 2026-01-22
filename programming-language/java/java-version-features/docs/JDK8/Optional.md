# Optional

设计目的：支持能返回Optional对象的语法，未实现序列化接口。

## 采用防御式检查减少NPE

```java
if(A!=null){
  B b=A.getB();
  if(b!=null){
    ...
  }
}
```

## Optional 类方法

```java
java.uitl.Optional<T>
```

| 方法        | 描述                                                       |
| ----------- | ---------------------------------------------------------- |
| empty       | 空Optional实例                                             |
| filter      | 过滤                                                       |
| flatMap     | 值存在时，执行mapping函数调用                              |
| get         | 获取值，如果值不存在，抛出NoSuchElementException           |
| ifPresent   | 值存在就执行使用该值的方法调用                             |
| isPresent   | 值存在返回true，否则false                                  |
| map         | 值存在时，执行mapping函数调用                              |
| of          | 将值使用Optional封装返回                                   |
| ofNullable  | 将值使用Optional封装返回，允许封装null，返回空Optional对象 |
| orElse      | 值存在就返回，否则返回默认值                               |
| orElseGet   | 值存在就返回，否则返回由supplier接口生成的值               |
| orElseThrow | 值存在就返回，否则返回由supplier接口生成的异常             |