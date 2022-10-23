# LongAccumulator

```java
public class LongAccumulator extends Striped64 implements Serializable 
```

```java
   private final LongBinaryOperator function;
    private final long identity;

    /**
     * Creates a new instance using the given accumulator function
     * and identity element.
     * @param accumulatorFunction a side-effect-free function of two arguments
     * @param identity identity (initial value) for the accumulator function
     */
    public LongAccumulator(LongBinaryOperator accumulatorFunction,
                           long identity) {
        this.function = accumulatorFunction;
        base = this.identity = identity;
    }
```

LongAccumulator是LongAdder的泛化版。因为它可以支持自定义：

- accumulatorFunction：累加规则，不一定是相加，可以是其他运算。
- base：初始值，不一定为0，可以是其他。

## 使用例子

```java
public static void main(String[] args) {
    LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
        @Override
        public long applyAsLong(long left, long right) {
            return left * right;
        }
    },2);

    long initVal = longAccumulator.get();
    System.out.println(initVal);

    longAccumulator.accumulate(3);
    long res1 = longAccumulator.get();
    System.out.println(res1);
}
```

```
2
6
```