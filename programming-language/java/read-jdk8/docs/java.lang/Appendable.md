# Appendable
- 可以附加char序列和值的对象。Appendable接口必须由其实例旨在从Formatter接收格式化输出的任何类实现。
- 要附加的字符应为有效的Unicode字符。补充字符可能由多个16位char值组成。
- Appendable 对于多线程访问不一定是安全的。线程安全是扩展和实现此接口的类的责任。
- 由于此接口可以由具有不同错误处理样式的现有类实现，因此不能保证错误会传播到调用者。

```java
public interface Appendable {
    // 要追加的字符序列。 如果csq为null，则将四个字符“null”附加到此Appendable。
    Appendable append(CharSequence csq) throws IOException;

    // 当csq不为null时，以out.append(csq, start, end)形式调用，与调用out.append(csq.subSequence(start, end))完全相同。
    Appendable append(CharSequence csq, int start, int end) throws IOException;

    // 将指定字符追加到此Appendable。
    Appendable append(char c) throws IOException;
}
```
