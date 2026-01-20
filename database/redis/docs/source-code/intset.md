# intset
intset是set的底层实现之一。

## 数据结构
> redis-5.0.7/src/intset.h:35
```
typedef struct intset {
    uint32_t encoding;// 编码方式
    uint32_t length;  // set包含的元素数量
    int8_t contents[];// 元素数组
} intset;
```

## 升级整数集合
1. 根据新元素类型，扩展底层数组大小，为新元素分配空间
2. 将底层现有元素全部转化为与新元素相同的类型，放置正确位置，并保证底层数组有序性不变
3. 将新元素添加到底层数组

> 不支持降级整数集合