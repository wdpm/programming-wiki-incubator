# XML 映射文件

## select
```xml
<select id="selectPerson" parameterType="int" resultType="hashmap">
  SELECT * FROM PERSON WHERE ID = #{id}
</select>
```

- id: 唯一标志
- parameterType：参数类型，例如int
- resultType：结果类型，可以是类的完全限定名或者别名。如果返回集合，应该设置为集合包含的类型。
- resultMap：外部 resultMap 的命名引用。
- flushCache: 为 true 后，只要语句被调用，会导致本地缓存和二级缓存被清空，默认值：false
- useCache: 为 true 后，会导致本条语句的结果被二级缓存，默认值：对 select 元素为 true
- statementType: STATEMENT，PREPARED 或 CALLABLE。默认值：PREPARED
- resultSetType：FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或 DEFAULT（unset）。默认unset
- resultSets：对多结果集适用。将列出语句返回结果集并给每个结果集一个名称，名称逗号分隔。
- databaseId: 据库厂商标识

## insert/update/delete
insert

使用自动生成的自增主键ID
```xml
<insert id="insertAuthor" useGeneratedKeys="true" keyProperty="id">
  insert into Author (username,password,email,bio)
  values (#{username},#{password},#{email},#{bio})
</insert>
```
多行插入
```xml
<insert id="insertAuthor" useGeneratedKeys="true" keyProperty="id">
  insert into Author (username, password, email, bio) values
  <foreach item="item" collection="list" separator=",">
    (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
  </foreach>
</insert>
```
update
```xml
<update id="updateAuthor">
  update Author set
    username = #{username},
    password = #{password},
    email = #{email},
    bio = #{bio}
  where id = #{id}
</update>
```
delete
```xml
<delete id="deleteAuthor">
  delete from Author where id = #{id}
</delete>
```

- id: 
- parameterType:
- flushCache:
- statementType: 
- useGeneratedKeys: (仅对insert和update）使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键
- databaseId: 据库厂商标识

## 自定义主键生成策略

使用 `<selectKey/>`

## 可重用的 SQL 代码段
```xml
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
```
```xml
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  from some_table t1
    cross join some_table t2
</select>
```
等价于：
```sql
  select
     t1.id,t1.username,t1.password,
     t2.id,t2.username,t2.password
  from some_table t1
    cross join some_table t2
```

## 参数处理
```xml
<insert id="insertUser" parameterType="User">
  insert into users (id, username, password)
  values (#{id}, #{username}, #{password})
</insert>
```
User类型对象的id、username 和 password 属性将被查找，它们的值传入预处理语句的参数中。
```
#{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
```

## 字符串替换
mapper接口添加此方法
```
@Select("select * from user where ${column} = #{value}")
User findByColumn(@Param("column") String column, @Param("value") String value);
```
其中 ${column} 会被直接替换，而 #{value} 会被使用 ? 预处理。
```
User userOfId1 = userMapper.findByColumn("id", 1L);
User userOfNameKid = userMapper.findByColumn("name", "kid");
User userOfEmail = userMapper.findByColumn("email", "noone@nowhere.com");
```
> 注意防止SQL注入攻击。

## 结果映射
resultMap 元素可以让你从 JDBC ResultSets 数据提取代码中解放。
```xml
<!-- mybatis-config.xml 中 -->
<typeAlias type="com.someapp.model.User" alias="User"/>

<!-- SQL 映射 XML 中 -->
<select id="selectUsers" resultType="User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```
MyBatis 自动创建一个 ResultMap，基于属性名来映射列到 JavaBean 的属性上。

如果列名和属性名没有精确匹配

第一种解法：可以在 SELECT 语句中对列使用别名来匹配标签。
```xml
<select id="selectUsers" resultType="User">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password     as "hashedPassword"
  from some_table
  where id = #{id}
</select>
```
第二种解法：
```xml
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="user_name"/>
  <result property="password" column="hashed_password"/>
</resultMap>
```
在引用语句中使用 resultMap 属性（注意去掉 resultType 属性）。
```xml
<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```

- constructor - 用于在实例化类时，注入结果到构造方法中
  - idArg - ID 参数；标记出作为 ID 的结果可以帮助提高整体性能
  - arg - 将被注入到构造方法的一个普通结果
- id – 标记出作为 ID 可以帮助提高性能
- result – 注入到字段或 JavaBean 属性的普通结果
- association – 一个复杂类型的关联；许多结果将包装成这种类型
  - 嵌套结果映射 – 关联本身可以是一个 resultMap 元素，或者从别处引用一个
- collection – 一个复杂类型的集合
  - 嵌套结果映射 – 集合本身可以是一个 resultMap 元素，或者从别处引用一个
- discriminator – 使用结果值来决定使用哪个 resultMap
  - case – 基于某些值的结果映射
    - 嵌套结果映射 – case 本身可以是一个 resultMap 元素，因此可以具有相同的结构和元素，或者从别处引用一个

### 构造方法
```java
public class User {
   public User(Integer id, String username, int age) { }
}
```
```xml
<constructor>
   <idArg column="id" javaType="int" name="id" />
   <arg column="age" javaType="_int" name="age" />
   <arg column="username" javaType="String" name="username" />
</constructor>
```
### 关联
> 本质就是表连接。

```xml
<association property="author" column="blog_author_id" javaType="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
</association>
```
有两种不同的方式：

1.嵌套 Select 查询：执行另外一个 SQL 映射语句来加载期望的复杂类型。

- column 数据库中的列名，或者是列的别名。
- select 用于加载复杂类型属性的映射语句的 ID
- fetchType	可选。有效值为 lazy 和 eager

```xml
<resultMap id="blogResult" type="Blog">
  <association property="author" column="author_id" javaType="Author" select="selectAuthor"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectAuthor" resultType="Author">
  SELECT * FROM AUTHOR WHERE ID = #{id}
</select>
```

加载记录列表后立刻遍历列表以获取嵌套的数据，会触发所有的延迟加载查询，性能依然很差。

2.嵌套结果映射：使用嵌套的结果映射来处理连接结果的重复子集。

- resultMap	结果映射的 ID
- columnPrefix	连接多个表时，可能要使用列前缀来避免在 ResultSet 中产生重复的列名
- notNullColumn	默认情况下，至少一个被映射到属性的列不为空时，子对象才会被创建。
- autoMapping	开启或者关闭自动映射

```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" column="blog_author_id" javaType="Author" resultMap="authorResult"/>
</resultMap>

<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```

### 集合
```xml
<collection property="posts" ofType="domain.blog.Post">
  <id property="id" column="post_id"/>
  <result property="subject" column="post_subject"/>
  <result property="body" column="post_body"/>
</collection>
```
一个博客（Blog）只有一个作者（Author)，一个博客有很多文章（Post)。

1.集合的嵌套 Select 查询

```xml
<resultMap id="blogResult" type="Blog">
  <collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectPostsForBlog" resultType="Post">
  SELECT * FROM POST WHERE BLOG_ID = #{id}
</select>
```

2.集合的嵌套结果映射
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <collection property="posts" ofType="Post" resultMap="blogPostResult" columnPrefix="post_"/>
</resultMap>

<resultMap id="blogPostResult" type="Post">
  <id property="id" column="id"/>
  <result property="subject" column="subject"/>
  <result property="body" column="body"/>
</resultMap>
```
> 注意 columnPrefix="post_"

### 鉴别器 discriminator 
类似 Java 语言中的 switch 语句。
```xml
<discriminator javaType="int" column="draft">
  <case value="1" resultType="DraftPost"/>
</discriminator>
```
draft=1时，为文章草稿。

## 自动映射
数据库列使用大写字母组成的单词命名，单词间用下划线分隔；Java 属性一般遵循驼峰命名法约定。
为了在这两种命名方式之间启用自动映射，需要将 mapUnderscoreToCamelCase 设置为 true。
```xml
<select id="selectUsers" resultMap="userResultMap">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password
  from some_table
  where id = #{id}
</select>
```
```xml
<resultMap id="userResultMap" type="User">
  <result property="password" column="hashed_password"/>
</resultMap>
```
id 和 userName 列将被自动映射，hashed_password 列将根据配置进行映射。

有三种自动映射等级：

- NONE - 禁用自动映射。仅对手动映射的属性进行映射。
- PARTIAL（默认） - 对除在内部定义了嵌套结果映射（也就是连接的属性）以外的属性进行映射
- FULL - 自动映射所有属性。

## 缓存
默认只启用本地会话缓存。要启用全局二级缓存，需要在SQL映射文件中添加：
```xml
<cache/>
```
- 映射语句文件中的所有 select 语句的结果将会被缓存。
- 映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。
- 缓存会使用LRU算法来清除不需要的缓存。其他可选策略：FIFO/SOFT/WEAK
- 缓存不会定时进行刷新（没有刷新间隔）。
- 缓存会保存列表或对象的 1024 个引用。
- 默认的缓存会被视为读/写缓存，获取到的对象并不是共享的。
- readOnly属性可被设为true或false。只读缓存会返回缓存对象的相同实例，因此这些对象不能被修改。
可读写缓存会（通过序列化）返回缓存对象的拷贝。 速度上慢，但是更安全，因此默认值是 false。