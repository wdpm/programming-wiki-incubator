# 高性能MySQL
>《高性能MySQL》阅读笔记

## 概述

- 四个事务隔离级别（RU，RC，RR，Serial）
- MYSQL事务由存储引擎实现。
- MYSQL默认为自动提交模式。
- MVCC是行级锁的变种，InnoDB的MVCC是通过在每行记录后面添加两个隐藏列来实现。行创建时间，行过期时间。
- MVCC只在RR，RC隔离级别下有效。因为RU总是读最新行，S读的每行都加锁。
- MyISAM是表级锁，不支持事务。
- InnoDB支持在线热备份。

## 基准测试

- 吞吐量：单位时间内的事务处理数。
- 集成式测试工具有：ab, http_load, JMeter
- MySQL BENCHMARK函数可以测试特定操作的执行速度。

## 性能剖析

- 无法测量就无法准确地优化。
- 基于执行时间的分析，基于等待时间的分析。执行慢，代表效率低；等待久，说明被阻塞。
- 慢查询日志。pt-query-digest工具加载日志，然后分析
- MySQL暴露内部信息主要通过SHOW命令。例如：SHOW ENGINE INNODB STATUS
  - SHOW PROFILES 配置
  - SHOW STATUS 状态
  - SHOW PROCESSLIST 过程列表
- Performance Schema表
- grep -c "关键字" 可以获取关键字的出现次数
- strace指令调查系统调用

## 数据类型优化

- 整数类型：TINYINT，SMALLINT，MEDIUMINT，INT，BIGINT。分别是1,2,3,4,8个字节。UNSIGNED表示整数，可以提升一倍的范围。
- 小数类型：DECIMAL，DOUBLE，FLOAT。FLOAT为4字节，DOUBLE为8字节。
- 字符串类型：VARCHAR，CHAR。VARCHAR是可变长字符串，CHAR是定长字符串。
- 二进制字符串类型：BINARY，VARBINARY
- 大数据字符串类型：BLOB，TEXT。BLOB是二进制方式，TEXT是字符方式。BLOB=SMALLBLOB，TEXT=SMALLTEXT。他们有其他家族。
- 枚举类型：ENUM("e1","e2",...)
- 日期和时间类型：DATETIME，格式为"2019-12-29 23:12:23"，表示范围是1001-9999年。
TIMESTAMP时间范围是1970-2038年，保存的是1970.1.1至今的秒数，使用4个字节。
- 位数据类型：BIT
- IP地址可以考虑使用32bit无符号整数保存，而不是VARCHAR(15)。INET_ATON()和INET_NTOA()函数之间转换。
- 范式是避免数据冗余，但会增加表连接查询。反范式会引入数据冗余，但会减少表连接查询。
- 汇总表：例如，每一个小时统计一下全站视频排名。因为实时的统计计算量太大。
- 缓存表：从基本表获取数据的表，逻辑上它的数据是冗余的。

- 创建影子表：
```sql
DROP TABLE IF EXISTS user_new,user_old;
CREATE TABLE user_new LIKE user;
RENAME TABLE user TO user_old,user_new TO user;
```

- 计数器表：
```sql
CREATE TABLE hit_counter (cnt int unsigned not null);
UPDATE hit_counter SET cnt=cnt+1;
```
优化为：
```sql
CREATE TABLE hit_counter (
slot tinyint unsigned not null,
cnt int unsigned not null);
UPDATE hit_counter SET cnt=cnt+1 where slot=RAND()*100;
```
创建100个slot，标号为1-100。记录时随机写入某个slot，统计总数时对所有slot求和。

- ON DUPLICATE KEY UPDATE为MySQL特有语法: 当insert已经存在的记录时，执行Update。

## 索引优化

- 索引作用于存储引擎层。
- 索引的实现是B+Tree。
- 索引的列的顺序很重要。
- 哈希索引只包含哈希值和行指针，不能用于排序。Memory引擎支持哈希索引。
- SELECT id from url where url="http://www.example.com"，url这一列如果采用B+tree作为索引，可能内容会非常大。
  - 可以采取url_crc校验码来辅助查询，缺点是需要手动或者利用触发器维护CRC值。
  - 可以借助/usr/share/dict/words做一个CRC冲突实验。
  - 要避免冲突，可使用：SELECT id from url where url="http://www.example.com" and url_crc=CRC32("http://www.example.com")
- 全文索引：文本中的关键字查找。
- 高效索引的策略：索引列独立不加任何修饰；使用索引的前缀部分而不是整个索引列，因为有时某列太长；最左前缀。
- EXPLAIN [SQL语句] 可以查看查询优化的细节。
- 聚簇索引：在一个结构中保存了B-Tree索引和数据行。
- 根据SQL代码中where的列来创建索引，是一种常规的做法。
- 当索引的列顺序和order by子句的顺序完全一致时，排序将极大优化。
- 考虑删除冗余和重复索引，不会使用到的索引。
- 假设有这么一个索引(sex,country)。查询时不区分性别，那么可以使用sex IN("m","f")来利用索引。
- 尽可能将范围查询的列放于索引的后面，例如age。

## 查询优化

- 衡量查询开销的三个指标：响应时间，扫描行数，返回行数。
- 多对多关系，一般会引入中间表。例如，actor，film_actor，film
- 一个查询的过程：客户端发送SQL，查询缓存，查询优化处理，查询执行引擎，返回结果给客户端。
- 列值转换：SELECT SUM(IF(COLOR='RED',1,0)) AS red,SUM(IF(COLOR='BLUE',1,0)) AS blue FROM items
- 优化关联查询：确保ON子句上的列是索引。
- 优化子查询：尽量使用关联查询替换。
- 优化LIMIT分页：先获取要分割的ID集合/范围，根据ID去select相应的行数。
  - SELECT * FROM USER WHERE USERID < 230000 ORDER BY USERID DESC LIMIT 100;
- 分表:将一个表分解成多个具有独立存储空间的子表，每个表对应三个文件，MYD数据文件，.MYI索引文件，.frm表结构文件。
- 分区:将数据分段划分在多个位置存放。分区后，表面上还是一张表，但数据散列到多个位置。
- EXPLAIN SELECT * FROM <view_name>;可以查看视图使用的是合并还是临时表算法。
- 视图：简化几个表的复杂查询。
- 物化视图的概念：将VIEW结果保存于一个可以查看的表中，定期从原始表同步数据。
- 字符集和校对规则：字符集是字节到字符的映射，校对规则是一个字符集的排序方法。
- 外键约束：可以在应用程序做，因为数据库来做可能额外消耗过大。
- 存储过程：一般不使用，迁移性较差，增加部署复杂度。
- 触发器：在执行UPDATE，INSERT，DELETE时，执行一些特化的操作。尽量避免使用，迁移性较差，增加部署复杂度。

## TODO
阅读进度：第八章。