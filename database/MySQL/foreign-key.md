# MySQL外键约束

作用：保证数据的完整性。

## 外键约束的概述
- 表A：(id,name,...,foreign_b_id)
- 表B：(id,name,...)

A为子表，B为父表。A的foreign_b_id引用了B的主键id。

对于MySQL而言，外键约束相关的操作为删除和更新。
当父表删除或更新时，可以采取restrict、cascade、no action、set null操作。

- cascade： 级联，当父表更新/删除，子表同步更新/删除
- set null：置空，当父表更新/删除，字表把外键字段设为null（如果该字段为Not NULL，会报错)
- restrict：当父表更新/删除，会检查子表中是否有该父表要更新/删除的记录，如果有，不允许父表更新/删除
- no action：同restrict

## 实践验证
user

|id|username|avatar|description|created_date|password|email|wechat_openid|phone_number|role|
|:---|:---|:---|:---|:---|:---|:---|:---|:---|:---|
|1|user1| | | | | | | |ADMIN|

post

|id|title|content|create_datetime|last_edit_datetime|user_id|
|:---|:---|:---|:---|:---|:---|
|1| | |2020-01-08 00:58:22|2020-01-08 00:58:29|1|

### delete restrict update restrict
假设post表存在 CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
![](assets/foreign-key-restrict.PNG)

```sql
DELETE  FROM user WHERE id = 1;
```
结果：
```bash
DELETE  FROM user WHERE id = 1;
> 1451 - Cannot delete or update a parent row: a foreign key constraint fails (`post_community`.`post`, CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`))
```
说明restrict约束下，当存在子表引用时，删除父表对应行失败。更新父表对应行的非主键字段，测试可以成功。

### delete cascade update no action
![](assets/foreign-key-delete-cascade-update-no-action.PNG)

```sql
DELETE  FROM user WHERE id = 1;
```
结果：user和post对应行都被删除。