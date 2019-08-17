# Spring Data JPA

## What is a Repository interface?

```java
一个接口，定义特定实体的所有逻辑读写操作，称为repository。
接口由一个或多个类实现，这些类提供每个接口方法的数据存储特定实现。

考虑一个简单的例子：
public interface BookRepository {       
    Book getBookById(Long id);      
    Book getBookByTitle(String title);    
    Book saveBook(Book b);          
    void deleteBook(Book b); 
}

public class BookRepositoryImpl implements BookRepository {          
	private EntityManager em;   

    public BookRepositoryImpl(EntityManager em) {            
    	this.em = em;       
    } 

	...override some methods
}
```

  

## How do you define a Repository interface? Why is it an interface not a class?

```java
public interface PersonRepository extends JpaRepository<Person, Long> { }

使用接口定义Spring Data存储库的第一个最明显的原因是通常不需要实现接口中定义的方法。
存储库被定义为接口，以便Spring Data能够使用JDK动态代理机制来创建代理对象,用来拦截对存储库的调用。
这还允许提供自定义基本存储库实现类，该类将充当应用程序中所有Spring Data存储库的（自定义）基类。
```



## What is the naming convention for finder methods in a Repository interface?

  ```
find(First[count])By[property expression][comparison operator][ordering operator]

- 始终以“find”开头。
- 可选地，可以在“find”之后添加“First”，以便仅检索第一个找到的实体。
当仅检索一个单个实体时，如果未找到匹配的实体，则finder方法将返回null。或者，可以声明方法的return-type使用Java Optional包装器来指示可能缺少结果。
如果在“First”之后提供计数，例如“findFirst10”，则首先找到的实体的计数数量将是结果。
- 可选属性表达式选择将用于选择要检索的实体/实体的托管实体的属性。
可以遍历属性，在这种情况下，可以将下划线添加到嵌套属性的单独名称中以避免歧义。如果要检查的属性是字符串类型，则可以在属性名称之后添加“IgnoreCase”以执行不区分大小写的比较。
可以使用“AND”或“OR”链接多个属性表达式。
- 可选的比较运算符可以创建选择一系列实体的finder方法。
一些比较运算符可用：LessThan，GreaterThan，Between，Like。
- 最后，可选的排序运算符允许在实体的属性上排序多个实体的列表。
这是通过添加“OrderBy”，属性表达式和“Asc”或“Desc”来实现的。

示例：findPersonByLastnameOrderBySocialsecuritynumberDesc  
查找具有提供的姓氏的人员，并按社会安全号码按降序排列。
  ```

  

## How are Spring Data repositories implemented by Spring at runtime?

  ```
对于Spring Data repository，将创建一个JDK动态代理，用于拦截对存储库的所有调用。
默认行为是将调用路由到默认存储库实现，该实现在Spring Data JPA中是SimpleJpaRepository类。可以自定义一个特定存储库类型的实现，也可以自定义用于所有存储库的实现。
- 在前一种情况下，自定义一种存储库类型，特定类型的代理将调用自定义实现中实现的任何方法，或者，如果自定义实现中不存在任何方法，则调用默认方法。
- 在后一种情况下，自定义应用于所有存储库类型，存储库的代理将调用自定义实现中实现的任何方法，或者，如果自定义实现中不存在任何方法，则调用默认方法。

补充阅读:https://stackoverflow.com/questions/38509882/how-are-spring-data-repositories-actually-implemented
  ```

  

## What is @Query used for?

  ```java
@Query注释允许指定与Spring Data JPA存储库方法一起使用的查询。
这允许自定义用于带注释的存储库方法的查询，或者提供将用于存储库方法的查询，该查询不遵循前面描述的查找器方法命名约定。
  
Example:
public interface PersonRepository extends JpaRepository<Person, Long> { 	      @Query("select p from Person p where p.emailAddress = ?1") 
 Person findByEmailAddress(String emailAddress); 
}
  ```

  