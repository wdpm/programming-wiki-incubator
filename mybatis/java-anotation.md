# Mapper的 Java 注解

- @CacheNamespace 为给定的命名空间配置缓存
- @Property 指定参数值或占位值
- @CacheNamespaceRef 参照另外一个命名空间的缓存
- @ConstructorArgs 收集一组结果传递给一个对象的构造方法。属性有：value，形式参数数组。
- @Arg 单参数构造方法，是 ConstructorArgs 集合的一部分。
- @TypeDiscriminator 一组实例值被用来决定结果映射。cases 属性是实例数组。
- @Results 结果映射的列表
- @Result 在列和属性或字段之间的单独映射。
- @One 复杂类型的单独属性。
- @Many 复杂类型的集合属性。
- @MapKey 用在返回值为 Map 的方法上，将存放对象的 List 转化为 key 值为对象的某一属性的 Map。
- @Options 提供访问大范围的交换和配置选项的入口。
- @Insert 被执行的 SQL 语句。
- @Update
- @Delete
- @Select
- @InsertProvider 构建动态 SQL。
- @UpdateProvider
- @DeleteProvider
- @SelectProvider
- @Param 在映射方法的参数上为参数取名。
- @SelectKey 同 `<selectKey>` 标签，用于 @Insert 或 @InsertProvider 或 @Update 或 @UpdateProvider 注解的方法上
如果指定了 @SelectKey ，会忽略由 @Options 注解所设置的生成主键或设置（configuration）属性。
- @ResultMap 给 @Select 或者 @SelectProvider 提供在 XML 映射中的 `<resultMap>` 的id
- @ResultType 在使用结果处理器的情况下使用，仅在方法返回类型是 void 的情况下生效。
- @Flush 定义在 Mapper 接口中的方法能够调用 SqlSession#flushStatements() 方法。

## 例子
1.使用 @SelectKey 注解在插入前读取数据库序列的值
```
@Insert("insert into table3 (id, name) values(#{nameId}, #{name})")
@SelectKey(statement="call next value for TestSequence", keyProperty="nameId", before=true, resultType=int.class)
int insertTable3(Name name);
```

2.使用 @Flush 注解调用 SqlSession#flushStatements()：
```
@Flush
List<BatchResult> flush();
```
3.指定 @Result 的 id 属性来命名结果集
```
@Results(id = "userResult", value = {
  @Result(property = "id", column = "uid", id = true),
  @Result(property = "firstName", column = "first_name"),
  @Result(property = "lastName", column = "last_name")
})
@Select("select * from users where id = #{id}")
User getUserById(Integer id);
```
4.多参数使用 @SqlProvider 注解
```java
@SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersByName")
List<User> getUsersByName(
    @Param("name") String name, @Param("orderByColumn") String orderByColumn);

class UserSqlBuilder {

  // If not use @Param, you should be define same arguments with mapper method
  public static String buildGetUsersByName(
      final String name, final String orderByColumn) {
    return new SQL(){{
      SELECT("*");
      FROM("users");
      WHERE("name like #{name} || '%'");
      ORDER_BY(orderByColumn);
    }}.toString();
  }
  
}
```
或者继承 ProviderMethodResolver
```java
@SelectProvider(UserSqlProvider.class)
List<User> getUsersByName(String name);

// Implements the ProviderMethodResolver on your provider class
class UserSqlProvider implements ProviderMethodResolver {
  // In default implementation, it will resolve a method that method name is matched with mapper method
  public static String getUsersByName(final String name) {
    return new SQL(){{
      SELECT("*");
      FROM("users");
      if (name != null) {
        WHERE("name like #{value} || '%'");
      }
      ORDER_BY("id");
    }}.toString();
  }
}
```