# redis 布隆过滤器

大数据去重，空间节省率高，有一定的误判概率。可认为是不精确的 set 结构。
- 当布隆过滤器说某个值存在时，这个值可能不存在，虽然大概率是存在；
- 当它说不存在时，那就肯定不存在。

## 安装
```bash
docker pull redislabs/rebloom 
docker run -p6379:6379 redislabs/rebloom 
redis-cli
```

## 命令
- bf.add 添加元素
- bf.exists 查询元素是否存在
- bf.madd 一次添加多个元素
- bf.mexists 一次查询多个元素
- bf.reserve 显式创建自定义参数的布隆过滤器
  - bf.reserve有三个参数，分别是 key, error_rate和initial_size。
  - 错误率越低，需要空间越大。initial_size 表示预计放入的元素数量，当实际数量超出这个数值时，误判率会上升。
  - 如果不使用 bf.reserve，默认的error_rate是 0.01，默认的initial_size是 100。

## 基本使用
[BloomTest](..\src\main\java\io\github\wdpm\redis\bloom\BloomTest.java)

## 测量误判率
随机出一堆字符串，分为 2 组：
- 将其中一组塞入布隆过滤器
- 然后再判断另外一组的字符串存在与否，取误判的个数和字符串总量一半的百分比作为误判率。

## 原理
向布隆过滤器中添加 key 时
- 使用多个 hash 函数对 key 进行 hash 算得一个整数索引值然后对位数组长度进行取模运算得到位置，每个 hash 函数都会算得一个不同的位置。
- 把位数组的这几个位置都置为 1。

## 空间占用估计
布隆过滤器有两个参数，第一个是预计元素的数量 n，第二个是错误率 f。
根据这两个输入得到两个输出，第一个输出是位数组的长度 l，也就是需要的存储空间大小 (bit)，第二个输出是 hash 函数的最佳数量 k。

公式
```
f=0.6185^(l/n) # ^ 表示次方计算，也就是 math.pow => 先求出l
k=0.7*(l/n) # 约等于 => 求出k
```
- 位数组相对越长 (l/n)，错误率 f 越低。
- 位数组相对越长 (l/n)，hash 函数需要的最佳数量k也越多。
> 在线工具 https://krisives.github.io/bloom-calculator/

## 应用
- 爬虫对网页链接URL去重
- 数据库查询：内存中的布隆过滤器过滤掉大量不存在的 row 请求，然后再去磁盘进行查询
- 邮箱系统的垃圾邮件过滤功能