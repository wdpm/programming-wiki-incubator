# setup
> 简单的查询使用Java注解,复杂的查询建议使用xml语句映射

1.创建mybatis-config.xml以及对应的config.properties, 定义数据库连接字段

2.在mybatis-config.xml中添加mappers，mapper指向格式为 xml/PostMapper.xml 的路径

3.编辑 xml/PostMapper.xml 文件，定义最基本的select测试方法
```xml
<mapper namespace="com.wdpm.postcommunity.mapper.PostMapper">
    <select id="selectTest" resultType="int">
        select 2
    </select>
</mapper>
```

4.测试代码中先根据mybatis-config.xml构造SqlSessionFactory实例，然后打开SqlSession实例
```java
String resource = "mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

try (SqlSession session = sqlSessionFactory.openSession()) {
  //
}
```

5.创建PostMapper接口，在PostMapper接口中定义接口方法int selectTest();

6.使用session.getMapper(PostMapper.class)获取对应的PostMapper实例, 调用selectTest方法
```java
try (SqlSession session = sqlSessionFactory.openSession()) {
    PostMapper mapper = session.getMapper(PostMapper.class);
    int res = mapper.selectTest();
    System.out.println(res);
}
```

总体上，就是AMapper接口方法会在AMapper.xml中定义实现。session获取AMapper实例，调用具体的实现，返回结果。

## 作用域（Scope）和生命周期

>依赖注入框架可以创建线程安全的、基于事务的 SqlSession 和映射器，并将它们注入到你的 bean 中，因此可以忽略它们的生命周期。 
如果对如何通过依赖注入框架来使用 MyBatis 感兴趣，可以研究一下 MyBatis-Spring 或 MyBatis-Guice 两个子项目。

### SqlSessionFactoryBuilder 
SqlSessionFactoryBuilder 有五个 build() 方法，都允许你从不同的资源中创建一个 SqlSessionFactory 实例。
```
SqlSessionFactory build(InputStream inputStream)
SqlSessionFactory build(InputStream inputStream, String environment)
SqlSessionFactory build(InputStream inputStream, Properties properties)
SqlSessionFactory build(InputStream inputStream, String env, Properties props)
SqlSessionFactory build(Configuration config)
```
一旦创建了 SqlSessionFactory，就不再需要。 最佳作用域是方法作用域（局部方法变量）。 

### SqlSessionFactory
```
SqlSession openSession()
SqlSession openSession(boolean autoCommit)
SqlSession openSession(Connection connection)
SqlSession openSession(TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType)
SqlSession openSession(ExecutorType execType, boolean autoCommit)
SqlSession openSession(ExecutorType execType, Connection connection)
Configuration getConfiguration();
```
默认的 openSession()方法没有参数，它会创建有如下特性的 SqlSession：

- 开启一个事务（不自动提交）。
- 将从由当前环境配置的 DataSource 实例中获取 Connection 对象。
- 事务隔离级别将会使用驱动或数据源的默认设置。
- 预处理语句不会被复用，也不会批量处理更新。

ExecutorType枚举类型定义了三个值:

- ExecutorType.SIMPLE：为每个语句创建一个新的预处理语句
- ExecutorType.REUSE：复用预处理语句
- ExecutorType.BATCH：批量执行所有更新语句

一旦创建就应该在应用运行期间一直存在，没有任何理由丢弃它。 
SqlSessionFactory不要重复创建多次。最佳作用域是应用作用域。建议使用单例模式或者静态单例模式。

### SqlSession

每个线程都应该有它自己的 SqlSession 实例，不能被共享，最佳作用域是请求或方法作用域：SqlSession 放在和 HTTP 请求对象相似的作用域中。
确保 SqlSession 关闭的标准模式：
```java
try (SqlSession session = sqlSessionFactory.openSession()) {
    //
}
```

- 执行语句方法
```
<T> T selectOne(String statement, Object parameter)
<E> List<E> selectList(String statement, Object parameter)
<T> Cursor<T> selectCursor(String statement, Object parameter)
<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey)
int insert(String statement, Object parameter)
int update(String statement, Object parameter)
int delete(String statement, Object parameter)
```
selectOne 必须返回一个对象或 null 值。如果不知道返回对象的数量，请使用 selectList 。

insert，update和delete方法返回的值指示该语句影响的行数。

-  select 高级版本
```
<E> List<E> selectList (String statement, Object parameter, RowBounds rowBounds)
<T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds)
<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowbounds)
void select (String statement, Object parameter, ResultHandler<T> handler)
void select (String statement, Object parameter, RowBounds rowBounds, ResultHandler<T> handler)
```
```
RowBounds rowBounds = new RowBounds(offset, limit);
```
RowBounds允许你限制返回行数的范围，ResultHandler提供自定义结果控制逻辑。

- 批量立即更新方法

当你设置 ExecutorType.BATCH 作为 ExecutorType 使用时可以采用:
```
List<BatchResult> flushStatements()
```
- 事务控制方法
如果已经设置了自动提交或正在使用外部事务管理器，没有任何效果。
```
void commit()
void commit(boolean force)
void rollback()
void rollback(boolean force)
```

- 本地缓存
Mybatis 两种缓存：本地缓存（local cache）和二级缓存（second level cache）。

当一个新 session 被创建，就会创建一个与之相关的本地缓存。在 session 执行过的查询语句都会保存在本地缓存。
默认情况下，本地缓存数据可在整个 session 的周期内使用。

### 映射器实例

映射器是一些由你创建的、绑定你映射的语句的接口。映射器接口的实例是从 SqlSession 中获得的。
映射器实例的最佳作用域是方法作用域，应该在调用它们的方法中被请求，用过之后丢弃。
```java
try (SqlSession session = sqlSessionFactory.openSession()) {
    BlogMapper mapper = session.getMapper(BlogMapper.class);
    // 你的应用逻辑代码
}
```