# Java tutorial resources

- [java-design-patterns](https://github.com/iluwatar/java-design-patterns) - 优秀的模式、原则、代码片段。基于Java语言描述。
- [JavaGuide](https://github.com/Snailclimb/JavaGuide) - 一份冗长的Java八股文。没有很细致的讲解，选读即可。

## source code you should read

ArrayList
LinkedList

HashMap: put/get/resize 数组table + entry, entry为单向链表。
HashSet

LinkedHashMap
LinkedHashSet

ConcurrentHashMap
- final V putVal(K key, V value, boolean onlyIfAbsent) 中依旧使用synchronized关键字，
但是，Synchronized 锁自从引入锁升级策略后，性能也有提升。

分析思路：
1. 底层数据结构
2. 常用public方法解析
3. 常用public方法用例测试