# redis 哈希
## 数据结构
### 字典表
```c
/* This is our hash table structure. Every dictionary has two of this as we
 * implement incremental rehashing, for the old to the new table. */
typedef struct dictht {
    dictEntry **table; // 哈希表数组
    unsigned long size; // 哈希表数组的大小
    unsigned long sizemask;// 等于 size -1
    unsigned long used; // 哈希表已有节点数
} dictht;
```
### 字典项
```c
typedef struct dictEntry {
    void *key;
    union {
        void *val;
        uint64_t u64;
        int64_t s64;
        double d;
    } v;
    struct dictEntry *next;
} dictEntry;
```
注意这个v比较特别，可以是指针void *，也可以是uint64_t/int64_t/double

### 字典
```c
typedef struct dict {
    // 类型特定函数
    dictType *type;
    // 私有数据
    void *privdata;
    // 两个哈希表
    dictht ht[2];
    long rehashidx; /* rehashing not in progress if rehashidx == -1 */
    unsigned long iterators; /* number of iterators currently running */
} dict;
```
- ht[0] 为哈希表
- ht[1] 为rehash的临时哈希表

## 哈希算法
> redis-5.0.7/src/dict.c:476 *dictFind
```java
h = dictHashKey(d, key);

idx = h & d->ht[table].sizemask;// h & size-1= h % size
```

## 渐进rehash
> redis-5.0.7/src/dict.c:188 dictRehash

渐进rehash过程中
- 如果要查找一个key，先到ht[0]找，找不到的话就去ht[1]找
- 新添加的key/value一律保存到ht[1]

## 哈希表的负载因子
```
load_factor=ht[0].used/ht[0].size
```