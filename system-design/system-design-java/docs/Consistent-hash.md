# 一致性哈希

## 原理
- 构造一个 0 ~ 2^32-1 大小的环。注意int类型范围不够大，long可以满足。
- 服务节点 hash 之后将自身存放到环中的下标中。
- 数据根据自身的某些特征属性 hash 之后也定位到这个环中。
- 从数据开始，顺时针查找最近的一个节点，就是这次路由的服务节点。
- 说明：如果服务节点的个数太小，导致环中数据分布不均匀，可以引入虚拟节点VNode。
- 说明：增加/删除服务节点之后，影响的是位于该服务节点上游的小部分数据节点。

## 代码实现
使用一种数据结构来模拟这个环。
- 考虑有序数组，数组元素存放的是服务节点的信息。
- 为了查找的高效性，要求能够根据服务节点的key来进行数组排序。这个key可以选用服务节点IP的部分特征来构造。

下面是两种不同的实现方式

- [SortArrayMap](..\src\main\java\io\github\wdpm\algorithms\consistenthash\SortArrayMap.java)
- [使用 TreeMap](..\src\main\java\io\github\wdpm\algorithms\consistenthash\UseTreeMap.java)

上面的代码直接 hardcode 了 key 的hash值。下面将补充以下几点：
- hash(key)函数
- 虚拟节点

### hash(key)函数
```java
protected Long hash(String key) {
    MessageDigest md5;
    try {
        md5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("MD5 not supported.", e);
    }
    md5.reset();
    byte[] keyBytes;
    keyBytes = key.getBytes(StandardCharsets.UTF_8);
    md5.update(keyBytes);

    byte[] digest = md5.digest();
    long hashCode = ((long) (digest[3] & 0xFF) << 24)
            | ((long) (digest[2] & 0xFF) << 16)
            | ((long) (digest[1] & 0xFF) << 8)
            | ((long) (digest[0] & 0xFF));
    return hashCode & 0xffffffffL;
}
```
将 digest[3] digest[2] digest[1] digest[0]分别放于高位31-24位，然后作或操作。
```
0 = {SortArrayMap$Node@722}  "Node[key=571962464, value='127.0.0.8']"
1 = {SortArrayMap$Node@723}  "Node[key=963396011, value='127.0.0.2']"
2 = {SortArrayMap$Node@724}  "Node[key=989064218, value='127.0.0.0']"
3 = {SortArrayMap$Node@725} "Node[key=1129187476, value='127.0.0.3']"
4 = {SortArrayMap$Node@726} "Node[key=1299589365, value='127.0.0.1']"
5 = {SortArrayMap$Node@727} "Node[key=1934069592, value='127.0.0.4']"
6 = {SortArrayMap$Node@728} "Node[key=2403843223, value='127.0.0.6']"
7 = {SortArrayMap$Node@729} "Node[key=2686629659, value='127.0.0.7']"
8 = {SortArrayMap$Node@730} "Node[key=2772066280, value='127.0.0.9']"
9 = {SortArrayMap$Node@731} "Node[key=3860753828, value='127.0.0.5']"
```
```
key: zhangsan ;hash: 1 33486337    => 127.0.0.8
key: wdpm ;hash: 31 93854114       => 127.0.0.5
key: abcdef ;hash: 3 91121896      => 127.0.0.8
key: 123456 ;hash: 9 70722017      => 127.0.0.0
key: helloworld ;hash: 23 65808380 => 127.0.0.6
```

### 虚拟节点
```
0 = {SortArrayMap$Node@745} "Node[key=132254036, value='127.0.0.6', isVirtual=true]"
1 = {SortArrayMap$Node@746} "Node[key=192733553, value='127.0.0.2', isVirtual=true]"
2 = {SortArrayMap$Node@747} "Node[key=281255555, value='127.0.0.7', isVirtual=true]"
3 = {SortArrayMap$Node@748} "Node[key=571962464, value='127.0.0.8', isVirtual=false]"
4 = {SortArrayMap$Node@749} "Node[key=702307089, value='127.0.0.1', isVirtual=true]"
5 = {SortArrayMap$Node@750} "Node[key=963396011, value='127.0.0.2', isVirtual=false]"
6 = {SortArrayMap$Node@751} "Node[key=989064218, value='127.0.0.0', isVirtual=false]"
7 = {SortArrayMap$Node@752} "Node[key=1129187476, value='127.0.0.3', isVirtual=false]"
8 = {SortArrayMap$Node@753} "Node[key=1299589365, value='127.0.0.1', isVirtual=false]"
9 = {SortArrayMap$Node@754} "Node[key=1502221165, value='127.0.0.3', isVirtual=true]"
10 = {SortArrayMap$Node@755} "Node[key=1515764336, value='127.0.0.0', isVirtual=true]"
11 = {SortArrayMap$Node@756} "Node[key=1930853568, value='127.0.0.0', isVirtual=true]"
12 = {SortArrayMap$Node@757} "Node[key=1934069592, value='127.0.0.4', isVirtual=false]"
13 = {SortArrayMap$Node@758} "Node[key=2158478544, value='127.0.0.4', isVirtual=true]"
14 = {SortArrayMap$Node@759} "Node[key=2403843223, value='127.0.0.6', isVirtual=false]"
15 = {SortArrayMap$Node@760} "Node[key=2427157733, value='127.0.0.8', isVirtual=true]"
16 = {SortArrayMap$Node@761} "Node[key=2432007843, value='127.0.0.8', isVirtual=true]"
17 = {SortArrayMap$Node@762} "Node[key=2582697899, value='127.0.0.9', isVirtual=true]"
18 = {SortArrayMap$Node@763} "Node[key=2686629659, value='127.0.0.7', isVirtual=false]"
19 = {SortArrayMap$Node@764} "Node[key=2712990575, value='127.0.0.9', isVirtual=true]"
20 = {SortArrayMap$Node@765} "Node[key=2758157524, value='127.0.0.3', isVirtual=true]"
21 = {SortArrayMap$Node@766} "Node[key=2772066280, value='127.0.0.9', isVirtual=false]"
22 = {SortArrayMap$Node@767} "Node[key=3013513018, value='127.0.0.2', isVirtual=true]"
23 = {SortArrayMap$Node@768} "Node[key=3085973799, value='127.0.0.6', isVirtual=true]"
24 = {SortArrayMap$Node@740} "Node[key=3302652273, value='127.0.0.5', isVirtual=true]"
25 = {SortArrayMap$Node@769} "Node[key=3535498047, value='127.0.0.1', isVirtual=true]"
26 = {SortArrayMap$Node@770} "Node[key=3652784020, value='127.0.0.4', isVirtual=true]"
27 = {SortArrayMap$Node@771} "Node[key=3860753828, value='127.0.0.5', isVirtual=false]"
28 = {SortArrayMap$Node@772} "Node[key=4101489027, value='127.0.0.7', isVirtual=true]"
29 = {SortArrayMap$Node@773} "Node[key=4238597795, value='127.0.0.5', isVirtual=true]"
```
```
key: zhangsan ;hash: 133486337
key: wdpm ;hash: 3193854114
key: abcdef ;hash: 391121896
key: 123456 ;hash: 970722017
key: helloworld ;hash: 2365808380
127.0.0.2
127.0.0.5
127.0.0.8
127.0.0.0
127.0.0.6
```
可以看到有数据节点已经落于虚拟节点中。例如 `key: zhangsan ;hash: 133486337` 落于:
```
1 = {SortArrayMap$Node@746} "Node[key=192733553, value='127.0.0.2', isVirtual=true]"
```

代码实现参考：
- [AbstractConsistentHash](..\src\main\java\io\github\wdpm\algorithms\consistenthash\AbstractConsistentHash.java)
- [SortArrayMapConsistentHash](..\src\main\java\io\github\wdpm\algorithms\consistenthash\SortArrayMapConsistentHash.java)
- [TreeMapConsistentHash](..\src\main\java\io\github\wdpm\algorithms\consistenthash\TreeMapConsistentHash.java)

> TreeMapConsistentHash 没有实现虚拟节点标记位。

## 路由使用
- 参阅 [io.github.wdpm.algorithms.consistenthash.route](..\src\main\java\io\github\wdpm\algorithms\consistenthash\route)

