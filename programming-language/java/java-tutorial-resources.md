# Java tutorial resources

- java point | https://www.javatpoint.com/

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