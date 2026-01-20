# JPA Query

- Create derived queries by referencing the name of the method.
- Use the `@Query` annotation to declare JPQL and native SQL queries.
- [Named Queries](.\NamingQueries.md)

## JPA 派生的方法名查询策略
```java
List<Person> findByAddressZipCode(ZipCode zipCode);
```
- 先拆分出AddressZipCode。
- 查看Person中是否有addressZipCode，有则结束。
- 否则，从右侧开始，拆分为 AddressZip 和 Code。
  - 在Person中查找是否有 addressZip 属性，有的话，则执行后续逻辑。
  - 如果没有 addressZip 属性，将拆分点左移，划分出 Address 和 ZipCode，查找Person中是否有 address，等等。

> - [派生的方法名查询策略 Reference Docs](https://docs.spring.io/spring-data/jpa/docs/2.3.0.RELEASE/reference/html/#io.github.wdpm.repositories.query-methods.query-property-expressions)
> - [Supported keywords inside method names](https://docs.spring.io/spring-data/jpa/docs/2.3.0.RELEASE/reference/html/#jpa.query-methods.query-creation)
> - Appendix C: Repository query keywords

## 表连接查询
- 如果返回结果类似 `List<Book>` 或者 `Book`，只需要修改SQL以及参数定义。
- 如果返回结果为每个匹配行具有多个实体，
```sql
SELECT o1, o2, o3 FROM EntityA o1, EntityB o2, EntityC o3 WHERE ...
```
那么结果集合是List<Object[3]>的形式。遍历list，转换Object对象，然后取值即可。
这种方式弊端明显，数据量中会包含并不需要的字段，存在冗余。一般不推荐使用。

- 如果返回结果是多个实体中的某些所需字段
```sql
SELECT o1.field1, o2.field1, o3.field1 FROM EntityA o1, EntityB o2, EntityC o3 WHERE ...
```
那么结果是List<Map<String,Object>形式。遍历list，每个map元素就是其中一个匹配行，map的key为所需字段，map的value为对应值。
