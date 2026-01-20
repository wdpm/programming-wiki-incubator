# redis object
> redis-5.0.7/src/server.h:464
```c
/* A redis object, that is a type able to hold a string / list / set */

/* The actual Redis Object */
#define OBJ_STRING 0    /* String object. */
#define OBJ_LIST 1      /* List object. */
#define OBJ_SET 2       /* Set object. */
#define OBJ_ZSET 3      /* Sorted set object. */
#define OBJ_HASH 4      /* Hash object. */
```
> redis-5.0.7/src/server.h:615
```
typedef struct redisObject {
    unsigned type:4;//对象类型
    unsigned encoding:4;//编码方式
    unsigned lru:LRU_BITS; /* LRU time (relative to global lru_clock) or
                            * LFU data (least significant 8 bits frequency
                            * and most significant 16 bits access time). */
    int refcount;
    void *ptr;
} robj;
```

## 查看Value编码
使用OBJECT ENCODING 可以查看key对应的值得编码方式
```
>SET msg "hello"
>OBJECT ENCODING msg
"embstr"
```

### 对象的编码

|对应输出|编码常量|对应底层数据结构|
|---|---|---|
|"int"|REDIS_ENCODING_INT| long类型整数|
|"embstr"|REDIS_ENCODING_EMBSTR|embstr编码简单SDS|
|"raw"|REDIS_ENCODING_RAW|SDS|
|"ziplist"|REDIS_ENCODING_ZIPLIST|压缩列表|
|"quicklist"|OBJ_ENCODING_QUICKLIST|快速列表|
|"hashtable"|REDIS_ENCODING_HT|字典/哈希表|
|-|~~REDIS_ENCODING_LINKEDLIST~~|~~双端链表~~|
|"intset"|REDIS_ENCODING_INTSET|整数集合|
|"skiplist"|REDIS_ENCODING_SKIPLIST|跳跃表和字典|

> redis-5.0.7/src/object.c:740

### 不同类型和编码的对象

| 类型         | 编码                      | 对象                       |
| ------------ | ------------------------- | -------------------------- |
| REDIS_STRING | REDIS_ENCODING_INT        | 整数值实现的字符串         |
| REDIS_STRING | REDIS_ENCODING_EMBSTR     | embstr编码的SDS字符串      |
| REDIS_STRING | REDIS_ENCODING_RAW        | SDS                        |
| ~~REDIS_LIST~~   | ~~REDIS_ENCODING_ZIPLIST~~    | ~~压缩列表实现的list~~        |
| ~~REDIS_LIST~~   | ~~REDIS_ENCODING_LINKEDLIST~~ | ~~双端列表实现的list~~         |
| REDIS_LIST   | REDIS_ENCODING_QUICKLIST  | Redis 3.2+ 改为快速列表                 |
| REDIS_HASH   | REDIS_ENCODING_ZIPLIST    | 压缩列表实现的hash         |
| ~~REDIS_HASH~~  | ~~REDIS_ENCODING_HT~~   | ~~字典实现的hash~~     |
| REDIS_SET    | REDIS_ENCODING_INTSET     | 整数集合表示的set          |
| ~~REDIS_SET~~    | ~~REDIS_ENCODING_HT~~         | ~~字典表示的set~~              |
| REDIS_ZSET   | REDIS_ENCODING_ZIPLIST    | 压缩列表实现的有序集合     |
| REDIS_ZSET   | REDIS_ENCODING_SKIPLIST   | 跳跃表和字典实现的有序集合 |

> 源码对照 ：redis-5.0.7/src/object.c:193

## String的编码
```bash
127.0.0.1:6379> set msg "hello world"
OK
127.0.0.1:6379> object encoding msg
"embstr"
127.0.0.1:6379> set longStr "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"
OK
127.0.0.1:6379> object encoding longStr
"raw"
127.0.0.1:6379> set  num 1
OK
127.0.0.1:6379> object encoding num
"int"
```

## List的编码
~~列表对象的编码可以是ziplist或者linkedlist~~。Redis 3.2+使用quicklist。

```
127.0.0.1:6379> RPUSH list1 "hello" "world" "123"
(integer) 3
127.0.0.1:6379> object encoding list1
"quicklist"
```

## Hash的编码
哈希对象的编码可以是ziplist或者hashtable。

```bash
127.0.0.1:6379> hset book name "effective java"
(integer) 1
127.0.0.1:6379> object encoding book
"ziplist"
```

### key过长引起编码改变

```bash
127.0.0.1:6379> HSET book long_long_long_long_long_long_long_long_long_long_long_description "content"
(integer) 1
127.0.0.1:6379> object encoding book
"hashtable"
```

### value过长引起编码改变

```bash
127.0.0.1:6379> HSET book2 name "many string ... many string ... many string ... many string ... many"
(integer) 1
127.0.0.1:6379> object encoding book2
"hashtable"
```

### KV数量超512引起编码改变

```bash
127.0.0.1:6379> EVAL "for i=1, 512 do redis.call('HSET', KEYS[1], i, i)end" 1 "numbers"
(nil)
127.0.0.1:6379> hlen numbers
(integer) 512
127.0.0.1:6379> object encoding numbers
"ziplist"

127.0.0.1:6379> hset numbers "k" "v"
(integer) 1
127.0.0.1:6379> hlen numbers
(integer) 513
127.0.0.1:6379> object encoding numbers
"hashtable"
```

### 参数配置

- hash-max-ziplist-value

- hash-max-ziplist-entries


## Set的编码
集合的编码可以是inset或者hashtable。

满足以下条件时，使用intset编码
- 所有元素都是整数值
- 元素数量少于512个

否则，使用hashtable编码。
### 添加字符串元素引起编码改变

```bash
127.0.0.1:6379> sadd numbers 1 2 3
(integer) 3
127.0.0.1:6379> object encoding numbers
"intset"
127.0.0.1:6379> sadd numbers "str"
(integer) 1
127.0.0.1:6379> object encoding numbers
"hashtable"
```

### 集合数量过多引起编码改变

```bash
127.0.0.1:6379> EVAL "for i=1, 512 do redis.call('SADD', KEYS[1], i) end" 1 integers
(nil)
127.0.0.1:6379> scard integers
(integer) 512
127.0.0.1:6379> object encoding integers
"intset"

127.0.0.1:6379> sadd integers 513
(integer) 1
127.0.0.1:6379> object encoding integers
"hashtable"
```

### 参数配置

- set-max-intset-entries

## ZSet的编码
有序集合的编码可以是ziplist或者zskiplist。

满足以下条件时，使用ziplist编码
- 有序集合元素数量少于128个
- 元素成员长度都低于64 bytes

否则，使用skiplist编码

### 过多元素引发编码转换

```bash
127.0.0.1:6379> EVAL "for i=1, 128 do redis.call('ZADD', KEYS[1], i, i) end" 1 numbers
(nil)
127.0.0.1:6379> zcard numbers
(integer) 128
127.0.0.1:6379> object encoding numbers
"ziplist"

127.0.0.1:6379> zadd numbers 129 129
(integer) 1
127.0.0.1:6379> zcard numbers
(integer) 129
127.0.0.1:6379> object encoding numbers
"skiplist"
```

### 成员value过长引发编码转换

```bash
127.0.0.1:6379> zadd book 1.0 java
(integer) 1

127.0.0.1:6379> zadd book 9.9 gogogogogogoogogogogoogogogogogogoogogogoogogogoogogoogogoogogogogoogogogogoo
(integer) 1
127.0.0.1:6379> object encoding book
"skiplist"
```

### 参数配置
- zset-max-ziplist-value
- zset-max-ziplist-entries

## 小结
- Redis 有string、list、hash、set、zset五种类型的对象，每种类型的对象有多种编码方式。
- 服务器执行某些命令之前，先检查给定key对应的value类型能否执行该命令。
- Redis采用引用计数来GC
- 对象会记录自身最后一次访问的时间，用于计算对象的idle时间。

