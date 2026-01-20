# redis Scan
> ~~简单暴力的指令 keys~~

scan 指令是一系列指令，除了可以遍历所有的 key 之外，还可以对指定的容器集合进行遍历。通过游标分步进行，不会阻塞线程。
- zscan 遍历 zset 集合元素
- hscan 遍历 hash 字典的元素
- sscan 遍历 set 集合的元素

## scan 命令
scan 参数提供了三个参数
- 第一个是 cursor 整数值
- 第二个是 key 的正则模式
- 第三个是遍历的 limit hint。

第一次遍历时，cursor 值为 0，将返回结果中第一个整数值作为下一次遍历的 cursor。一直遍历到返回的 cursor 值为 0 时结束。

```bash
127.0.0.1:6379> scan 0 match key99* count 1000
1) "13976"
2) 1) "key9911"
2) "key9974"
3) "key9994"
4) "key9910"
5) "key9907"
6) "key9989"
7) "key9971"
8) "key99"
9) "key9966"
10) "key992"
11) "key9903"
12) "key9905"
127.0.0.1:6379> scan 13976 match key99* count 1000
```

## 大key扫描
```bash
redis-cli -h 127.0.0.1 -p 7001 –-bigkeys -i 0.1
```
每隔 100 条 scan 指令就休眠 0.1s，ops 就不会剧烈抬升，但是扫描的时间会变长。