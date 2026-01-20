# JPA Named Queries

- JPQL：解析这些查询，然后发送到数据库。
- Native SQL：不会解析这些查询，直接将其发送到数据库。

## 创建 Named Queries
```
{EntityName}.{RepositoryMethodName}

// Examples

1. Book.findByIsbn
2. Book.findByPagesGreaterThan
```
> 如果使用EntityManager以编程方式执行命名查询，无需遵循这些命名约定。

### Using a Properties File
在 /src/main/resources/ 创建 META-INF 文件夹，创建 jpa-named-queries.properties
```
# find all books order by title descending
Book.findAllNamedFile=SELECT b FROM Book b ORDER BY b.title DESC
```

### Using the orm.xml File
- `<named-query/>` — for JPQL named query.
- `<named-native-query/>` — for native SQL named query. 
If your native name query returns an entity, you have to specify the entity class by using the result-class attribute.

`\src\main\resources\META-INF\orm.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings version="2.0" xmlns="http://java.sun.com/xml/ns/persistence/orm"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://java.sun.com/xml/ns/persistence/orm
        http://java.sun.com/xml/ns/persistence/orm_2_0.xsd ">

    <!--find all books order by pages descending-->
    <named-query name="Book.findAllXML">
        <query>SELECT b FROM Book b ORDER BY b.pages DESC</query>
    </named-query>

    <!--native SQL query to find a book by isbn-->
    <named-native-query name="Book.findByIsbnNativeXML"
                        result-class="io.github.wdpm.named.domain.Book">
        <query>SELECT * FROM book b WHERE b.isbn = :isbn</query>
    </named-native-query>

</entity-mappings>
```

### Using Annotations
- `@NamedQuery` —  for JPQL named query.
- `@NamedNativeQuery` — for native SQL named query.
  - query属性的值必须是有效的SQL语句，不是JPQL语句。 
  - 需要使用resultClass指示查询的返回类型。

```java
@NamedQueries({
        @NamedQuery(name = "Book.findAllJPQL",
                query = "SELECT b FROM Book b ORDER BY b.title DESC"),
        @NamedQuery(name = "Book.findByTitleJPQL",
                query = "SELECT b FROM Book b WHERE b.title = ?1"),
        @NamedQuery(name = "Book.findByTitleAndPagesGreaterThanJPQL",
                query = "SELECT b FROM Book b WHERE b.title = :title AND b.pages > :pages")})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Book.findAllNative",
                query = "SELECT * FROM book b ORDER BY b.title DESC",
                resultClass = Book.class),
        @NamedNativeQuery(name = "Book.findByIsbnNative",
                query = "SELECT * FROM book b WHERE b.isbn = :isbn",
                resultClass = Book.class)})
public class Book {...}
```

### 命名规范化-方法论
|分类|Properties file| XML file | Java Code|
|---|---|---|---|
|JPQL|Book.?NamedFile | Book.?XML| Book.?JPQL|
|Native SQL|Book.?NativeNamedFile |Book.?NativeXML|Book.?Native|

## 执行 Named Queries
### 通过 EntityManager
```java
@PersistenceContext
private EntityManager em;

Query q = em.createNamedQuery("Book.findByTitleJPQL");
q.setParameter(1, "Java 101");

// execute query
List<Book> books = q.getResultList();
```

### 通过 repository interface
- 遵循Spring Data JPA的命名约定: `{EntityName}.{RepositoryMethodName}`
- 在repository interface创建与命名查询同名的方法，传递正确的方法参数，并指定查询方法的返回类型。

```java
// list all books
List<Book> books = bookRepository.findAllXML();

// fetch a single book
Book book = bookRepository.findByIsbnNamedFile("145804");
```

## Sorting Query Results
- Static Sorting — Add an ORDER BY clause to your JPQL or native SQL query
- Dynamic Sorting — Add the special parameter Sort to your repository method

> dynamic sorting is currently not supported by Spring Data JPA named queries.
