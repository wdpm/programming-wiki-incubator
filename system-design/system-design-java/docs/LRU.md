# LRU
一种缓存驱逐(cache eviction)策略，当缓存满时将最近最少使用的元素删除。

## 实现原理
- 数据结构表示：double linked list + hash map(K,V) + size
  - hash map：使用K获取V，时间复杂度O(1)。
  - double linked list：hash map中的V就是Node类型。根据双向链表的性质，头部和尾部都可以较快访问。
- 特征：最近使用的元素会移动到头部，相反，最近最少使用的元素会逐步移动到末尾。

hash map查找快，但数据无顺序；链表有顺序，插入删除快，但查找慢。结合两者，形成哈希链表。

### get
当一个key被访问，有两种情况：
- 该key已存在于cache中，即缓存命中，委托到put(key,value)，然后返回该元素的value。
- 该key未存在于cache中，即缓存未命中，返回null。

### put
判断这个key对应的元素是否存在于hash map中。
- 已存在
  - 删除旧位置的这个元素，将这个元素添加链表头部，更新hash map。
- 未存在
  - 判断缓存是否已满
    - 缓存已满，删除链表最后一个节点，将这个元素添加链表头部，更新hash map。
    - 缓存未满，将这个元素添加到list头部，将这个元素添加链表头部，更新hash map。

> 实际代码可以优化此处冗余：将这个元素添加链表头部，更新hash map。

## 编码实现
- 基于LinkedHashMap的实现 [LRULinkedHashMap](..\src\main\java\io\github\wdpm\algorithms\lru\LRULinkedHashMap.java)
- 基于HashMap + LinkedList的实现 [LRUCache](..\src\main\java\io\github\wdpm\algorithms\lru\LRUCache.java)