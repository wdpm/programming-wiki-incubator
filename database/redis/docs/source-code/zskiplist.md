# zskiplist

```c
/* ZSETs use a specialized version of Skiplists */
typedef struct zskiplistNode {
    sds ele; // 字符串sds对象
    double score; // 分数
    struct zskiplistNode *backward; //后退指针，一次只能退一格
    struct zskiplistLevel {
        struct zskiplistNode *forward;//前进指针
        unsigned long span;//跨度
    } level[];
} zskiplistNode;

typedef struct zskiplist {
    struct zskiplistNode *header, *tail;// 头部，尾部
    unsigned long length;//表中节点数量
    int level;//表中层数最大的节点的层数
} zskiplist;

typedef struct zset {
    dict *dict;
    zskiplist *zsl;
} zset;
```
- zset结构中的zsl跳跃表按分值从小到大保存了所有集合元素，每个跳跃表节点都保存了一个集合元素
- zset结构中的dict字典为有序集合创建了一个从成员到分值的映射，字典中的每个键值对都保存了一个集合元素

## 为何zset需要同时使用skiplist和dict呢？
- dict以无序方式保存元素，有序性相关操作效率不高。可以从成员 -> 分值快速查找。
- 只使用skiplist的话，有序性相关操作效率很好，无法从成员 -> 分值的快速查找。

二者合一，就是zset。