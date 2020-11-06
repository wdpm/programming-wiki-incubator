# XML配置

## properties
为占位符指定默认值
```xml
<properties resource="org/mybatis/example/config.properties">
  <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/>
</properties>
```

## settings
```xml
<settings>
  <!--映射器配置的任何缓存-->
  <setting name="cacheEnabled" value="true"/> 
  <!--当开启时，所有关联对象都会延迟加载-->
  <setting name="lazyLoadingEnabled" value="true"/>
  <!--是否允许单一语句返回多结果集（需要驱动支持）-->
  <setting name="multipleResultSetsEnabled" value="true"/>
  <!--使用列标签代替列名-->
  <setting name="useColumnLabel" value="true"/>
  <!--允许 JDBC 自动生成主键，需要驱动支持。为 true时强制使用自动生成主键-->
  <setting name="useGeneratedKeys" value="false"/>
  <!--如何自动映射列到字段或属性。NONE/PARTIAL/FULL  -->
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <!--发现自动映射目标未知列（或者未知属性类型）的行为-->
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <!--默认的执行器-->
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <!--驱动等待数据库响应的秒数-->
  <setting name="defaultStatementTimeout" value="25"/>
  <!--驱动的结果集获取数量（fetchSize）设置一个提示值-->
  <setting name="defaultFetchSize" value="100"/>
  <!--允许在嵌套语句中使用分页（RowBounds）。允许使用则设置为 false  -->
  <setting name="safeRowBoundsEnabled" value="false"/>
  <!--经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射-->
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <!--本地缓存机制作用域-->
  <setting name="localCacheScope" value="SESSION"/>
  <!--当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。NULL、VARCHAR 或 OTHER -->
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <!--指定哪个方法触发一次延迟加载-->
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

## 类型别名 alias
```xml
<typeAliases>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
</typeAliases>
```
常见的 Java 类型内建的相应的类型别名
```
别名	    映射的类型
_byte	    byte
_long	    long
_short	    short
_int	    int
_integer    int
_double	    double
_float	    float
_boolean    boolean
string	    String
byte	    Byte
long	    Long
short	    Short
int         Integer
integer	    Integer
double	    Double
float	    Float
boolean	    Boolean
date	    Date
decimal	    BigDecimal
bigdecimal  BigDecimal
object	    Object
map         Map
hashmap	    HashMap
list	    List
arraylist   ArrayList
collection  Collection
iterator    Iterator
```

## 类型处理器（typeHandlers）
MyBatis 在预处理语句中设置一个参数时，或者从结果集中取出一个值时，类型处理器将获取的值转换成 Java 类型。
例如:
```
BooleanTypeHandler	java.lang.Boolean, boolean	数据库兼容的 BOOLEAN
```
```java
// ExampleTypeHandler.java
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ExampleTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  }

}
```
```xml
<typeHandlers>
  <typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
</typeHandlers>
```

## 插件（plugins）
默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

使用插件只需实现 Interceptor 接口，并指定想要拦截的方法签名。

## 映射器（mappers）

路径：
- 相对于类路径的资源引用 
- 完全限定资源定位符（包括 file:///的URL）
- 类名
- 包名
```xml
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
  <package name="org.mybatis.builder"/>
</mappers>
```

