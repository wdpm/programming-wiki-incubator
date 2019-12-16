# SQL性能

## 使用高效的查询

### 参数是子查询时，使用EXISTS 代替IN。

```sql
-- 慢
SELECT *
FROM Class_A
WHERE id IN (SELECT id FROM Class_B);
```

```sql
-- 快
SELECT *
FROM Class_A A
WHERE EXISTS
    (SELECT *
    FROM Class_B B
    WHERE A.id = B.id);
```

使用EXISTS 时更快的原因有以下两个。


- 如果连接列（id）上建立了索引，那么查询Class_B 时不用查实际的表，只需查索引。
- 如果使用EXISTS，那么只要查到一行数据满足条件就会终止查询，
  不用像使用IN 时一样扫描全表。在这一点上NOT EXISTS 也一样。

当IN 的参数是子查询时，数据库首先会执行子查询，将结果存储在一张临时的工作表里（内联视图），然后扫描整个视图。这种做法非常耗费资源。

使用EXISTS 的话，数据库不会生成临时的工作表。

### 参数是子查询时，使用连接代替IN。

```sql
-- 使用连接代替IN
SELECT A.id, A.name
FROM Class_A A INNER JOIN Class_B B
ON A.id = B.id;
```

## 避免排序

会进行排序的代表性的运算有下面这些。

- GROUP BY 子句
- ORDER BY 子句
- 聚合函数（SUM、COUNT、AVG、MAX、MIN）
- DISTINCT
- 集合运算符（UNION、INTERSECT、EXCEPT）
- 窗口函数（RANK、ROW_NUMBER 等）

### 灵活使用集合运算符的ALL

如果不在乎结果中是否有重复数据，或者事先知道不会有重复数据，
使用UNION ALL 代替UNION。

```sql
SELECT * FROM Class_A
UNION ALL
SELECT * FROM Class_B;
```

### 使用EXISTS 代替DISTINCT

```sql
SELECT DISTINCT I.item_no
FROM Items I INNER JOIN SalesHistory SH
ON I. item_no = SH. item_no;
```

被替换为

```sql
SELECT item_no
FROM Items I
WHERE EXISTS
    (SELECT *
    FROM SalesHistory SH
    WHERE I.item_no = SH.item_no);
```

这条语句在执行过程中不会进行排序。

### 极值函数中使用索引（MAX/MIN）

```sql
SELECT MAX(some_index)
FROM Items;
```

### 能写在WHERE 子句里的条件不写在HAVING 子句

在使用GROUP BY 子句聚合时会进行排序，如果事先通过WHERE 子句筛选出一部分行，就能减轻排序的负担。

在WHERE 子句的条件里可以使用索引。HAVING 子句是针对聚合后生成的视图进行筛选的，
但很多时候聚合后的视图都没有继承原表的索引结构。

### GROUP BY 和ORDER BY 子句中使用索引

## 正确利用索引

### 在索引字段上进行运算

```sql
WHERE col_1 * 1.1 > 100 -- 无法利用索引
WHERE col_1 > 100 / 1.1 -- 高效做法
```

使用索引时，条件表达式的左侧应该是原始字段。

## 减少中间表

### 灵活使用HAVING 子句

```sql
SELECT *
FROM (SELECT sale_date, MAX(quantity) AS max_qty
        FROM SalesHistory
        GROUP BY sale_date) TMP -- 中间表
WHERE max_qty >= 10;
```

```sql
SELECT sale_date, MAX(quantity)
FROM SalesHistory
GROUP BY sale_date
HAVING MAX(quantity) >= 10;
```

HAVING 子句和聚合操作同时执行，比起生成中间表后再执行的WHERE子句，效率会更高。

### 合理使用视图

视图的定义语句中包含以下运算的时，SQL 会非常低效。

- 聚合函数（AVG、COUNT、SUM、MIN、MAX）
- 集合运算符（UNION、INTERSECT、EXCEPT 等）

