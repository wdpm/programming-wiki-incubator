# Data Management: JDBC, Transactions
## What is the difference between checked and unchecked exceptions?
```
已检异常：Java编译器需要在抛出此类异常的方法的签名中声明的异常。如果方法调用另一个在其方法签名中声明一个或多个已检查异常的方法，则调用方法必须捕获这些异常或在其方法签名中声明异常。
java.lang.Exception类及其子类都是已检异常。

未检异常：Java编译器不需要在方法签名中声明或者在调用可能抛出未经检查的异常的其他方法的方法中捕获的异常。java.lang.RuntimeException和RuntimeException的任何子类是未检异常。
```


### Why does Spring prefer unchecked exceptions?

```
已检查的异常会强制开发人员以try-catch块的形式实现错误处理，或者声明方法签名中的基础方法抛出的异常。
可能导致杂乱的代码和/或与底层方法的不必要的耦合。

未经检查的异常使开发人员可以自由选择在何处实现错误处理并删除与异常相关的任何耦合。
```



### What is the data access exception hierarchy?

```
数据访问异常层次结构是DataAccessException类及其在Spring Framework中的所有子类。
此异常层次结构中的所有异常都是未检异常。
数据访问异常层次结构的目的：使应用程序开发人员远离JDBC数据访问API的细节，例如来自不同供应商的数据库驱动程序。这使得更容易地在不同的JDBC数据访问API之间切换。
```



## How do you configure a DataSource in Spring? Which bean is very useful for development/test databases?

```java
javax.sql.DataSource接口是与SQL相关的所有数据源类的接口。核心Spring Framework包含以下所有实现此接口的根数据源类和接口：
- DelegatingDataSource
- AbstractDataSource
- SmartDataSource
- EmbeddedDatabase

1.DataSource in a standalone application:
@Bean  
public DataSource dataSource() {  
    final BasicDataSource theDataSource = new BasicDataSource();     
    theDataSource.setDriverClassName("org.hsqldb.jdbcDriver"); 		 
    theDataSource.setUrl("jdbc:hsqldb:hsql://localhost:1234/mydatabase");  
    theDataSource.setUsername("ivan"); theDataSource.setPassword("secret"); 
    return theDataSource; 
}
如果使用spring boot，那么可以通过简单配置：
spring.datasource.url= jdbc:hsqldb:hsql://localhost:1234/mydatabase spring.datasource.username=ivan 
spring.datasource.password=secret

2.DataSource in an application deployed to a server
@Bean 
public DataSource dataSource() {      
	final JndiDataSourceLookup theDataSourceLookup = new JndiDataSourceLookup();     	 final DataSource theDataSource = theDataSourceLookup.getDataSource("java:comp/env/jdbc/MyDatabase");     
    return theDataSource; 
}
如果使用spring boot，配置jndi-name即可：
spring.datasource.jndi-name=java:comp/env/jdbc/MyDatabase
```



## What is the Template design pattern and what is the JDBC template?

```
模板（方法）设计模式:定义了算法，并且算法的步骤被重构为具有受保护可见性的方法。定义算法的类可以为算法的不同步骤提供抽象方法，让子类定义所有步骤。或者，定义算法的类可以定义算法的不同步骤的默认实现，允许子类根据需要仅定制所选择的方法。

Spring JDBC template是简化JDBC使用的类。
- 减少执行JDBC操作所需的（样板）代码量。
- 处理异常。正确处理异常，确保关闭或释放资源。
- 转换异常。异常被转换为DataAccessException层次结构中的相应异常（未经检查的异常），这也是与供应商无关的。
- 避免常见错误例如，确保语句关闭属性并在执行JDBC操作后释放连接。
- 允许自定义核心功能。例如异常转换。
- 允许自定义每次使用功能。例如将结果集中的行映射到Java对象。使用回调完成。
创建和配置JdbcTemplate实例后，它们是线程安全的。
```



## What is a callback? 

```
回调是一段代码，或对一段代码的引用。该代码作为参数传递给方法，该方法在方法执行期间的某个时刻将调用作为参数传递的代码。
在Java中，回调可以是对实现某个接口的Java对象的引用，或者从Java 8开始，是一个lambda表达式。
```

### What are the three JdbcTemplate callback interfaces that can be used with queries? What is each used for? 

```
可以与查询一起使用以提取结果数据的三个回调接口是：
- ResultSetExtractor 
允许一次处理整个结果集，可能包含多行数据。适用于需要多行数据来创建保存查询结果数据的Java对象时。结果集提取器通常是无状态的。请注意，此接口中的extractData方法返回Java对象。
- RowCallbackHandler 
允许逐个处理结果集中的行，通常会累积某种类型的结果。行回调处理程序通常是有状态的，将累积的结果存储在实例变量中。请注意，此接口中的processRow方法具有void返回类型。
- RowMapper 
允许逐个处理结果集中的行并为每行创建Java对象。行映射器通常是无状态的。请注意，此接口中的mapRow方法返回Java对象。
```

## Can you execute a plain SQL statement with the JDBC template?

```
可以。
以下方法接受一个或多个SQL字符串作为参数。同一个名字的方法具有多个重载方式。
• batchUpdate
• execute 
• query 
• queryForList 
• queryForMap
• queryForObject 
• queryForRowSet
• update

https://docs.spring.io/spring/docs/5.0.7.RELEASE/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
```

## When does the JDBC template acquire (and release) a connection, for every method called or once per template? Why?

```
JdbcTemplate为每个调用的方法获取并释放数据库连接。
即，在执行手头操作之前立即获取连接，并且在操作完成之后立即释放连接，无论是成功还是抛出异常。

这样做的原因是为了避免长时间地保留资源（数据库连接）并创建尽可能少的数据库连接，因为创建连接可能是一项潜在的昂贵操作。使用数据库连接池时，会将连接返回到池以供其他人使用。
```



## How does the JdbcTemplate support generic queries? How does it return objects and lists/maps of objects?

```
JdbcTemplate包含七种不同的queryForList方法和三种不同的queryForMap方法。

queryForList方法都返回一个包含查询结果行的列表，有两种形式： 
- 一种将元素类型作为参数的类型。
这种类型的queryForList方法返回一个列表，其中包含由element type参数指定的类型的对象。
- 另一种没有任何元素类型参数的类型。
这种类型的queryForList方法返回一个包含带字符串键和Object值的映射的列表。每个映射都包含一行查询结果，其中列名称为键，列值为映射中的值。

所有queryForMap方法都应返回一行。生成的行将在包含字符串键和Object值的映射中返回。列名是映射条目的键，列值是值。
```



## What is a transaction? What is the difference between a local and a global transaction?

```
事务是由多个任务组成的操作，这些任务作为一个单元发生 - 要么执行所有任务，要么不执行任务。如果作为事务一部分的任务未成功完成，则事务中的其他任务将不会执行，或者对于已执行的任务，将被还原。
可靠的事务系统强制执行ACID原则：
•原子性
事务中的更改要么全部应用，要么不应用。“全有或全无”
•一致性
不违反任何完整性约束，例如数据库。
•隔离性
事务彼此隔离，不会相互影响。
•持久性
由于成功完成的事务而应用的更改是持久的。

全局事务允许事务跨越多个事务资源。例如，考虑跨越数据库更新操作以及将消息发布到消息代理队列的全局事务。1）如果数据库操作成功但发布到队列失败，则数据库操作将回滚（撤消）。
2）如果发布到队列成功但数据库操作失败，则发送到队列的消息将被回滚，因此不会出现在队列中。
直到两个操作都成功，数据库更新才会生效，并且消息可供队列使用。
请注意，跨越两个不同数据库上的操作的事务需要是全局事务。

本地事务是与单个资源相关联的事务，例如单个数据库或消息代理的队列，但不能同时在同一个事务中。
```



## Is a transaction a cross cutting concern? How is it implemented by Spring?

```
事务管理是一个跨领域的问题。在Spring框架中，声明式事务管理是使用Spring AOP实现的。
```



## How are you going to define a transaction in Spring?

```
在Spring应用程序中使用Spring事务管理需要以下两个步骤： 
- 声明PlatformTransactionManager bean。
选择实现此接口的类，该类为要使用的事务资源提供事务管理。一些示例是JmsTransactionManager（用于单个JMS连接工厂），JpaTransactionManager（用于单个JPA实体管理器工厂）。
- 如果使用注释驱动的事务管理，则将@EnableTransactionManagement注解应用于应用程序中的一个@Configuration类。

- 在应用程序代码中声明事务边界。
这可以使用以下一个或多个来完成： 
1.@Transactional注释 
2.Spring XML配置 
3.代码配置事务管理 
```



### What does @Transactional do? What is the PlatformTransactionManager?

```
@Transactional  
@Transactional注释用于声明式事务管理，可以应用于方法和类。此批注用于指定它注释的方法的事务属性，或者，如果在类级别应用，则指定类中的所有方法。
可以在@Transactional注释中配置以下内容： 
- isolation 
事务隔离级别。
- noRollbackFor 
异常类，它永远不会导致事务回滚。
- noRollbackForClassName 
永远不会导致事务回滚的异常类的名称。
- propagatoion 
事务传播。
- readOnly 
如果transaction是只读事务，则设置为true。
- timeout
事务超时。
- transactionManager
事务管理器Spring bean的名称。

Spring允许使用JPA javax.transaction.Transactional注释作为Spring @Transactional注释的替代，尽管它没有那么多的配置选项。

PlatformTransactionManager是可以在Spring框架的事务基础结构中使用的所有事务管理器的基本接口。事务管理器（实现此接口）可以由应用程序直接使用，但建议使用声明性事务或TransactionTemplate类。
PlatformTransactionManager接口包含以下方法：
•void commit（TransactionStatus）提交与TransactionStatus对象事务相关的事务。
•void rollback（TransactionStatus）回滚与TransactionStatus对象相关的事务。
•TransactionStatus getTransaction（TransactionDefinition）创建新事务并将其返回或返回当前活动事务，具体取决于事务传播的配置方式。
```



## Is the JDBC template able to participate in an existing transaction?

```
是。在使用声明式和程序化事务管理时，JdbcTemplate都能够参与现有事务。这是通过使用TransactionAwareDataSourceProxy包装DataSource来实现的。
```



## What is a transaction isolation level? How many do we have and how are they ordered?

```
数据库系统中的事务隔离：确定在事务提交之前，事务中的更改如何对访问数据库的其他用户和系统可见。
-> 事务可见性
较高的隔离级别减少甚至消除了在同时更新和访问数据库时可能出现的下述问题的机会。但缺点是多个用户和系统同时访问数据库的能力降低以及数据库服务器上系统资源的使用增加。
->隔离级别和并发访问速度的权衡。

有四个隔离级别，此处按降序排列：（S>RR>RC>RU)
- 可序列化（Serializable）：S
Serializable是最高的隔离级别，涉及与事务中的处理相关联的数据的读写锁定以及范围锁定（如果存在具有where clauses的选择查询）。锁定一直持续到事务结束。

- 可重复读取（Repeatable reads）：RR
在可重复读取隔离级别中，对与事务中的处理相关联的数据的读取和写入锁定保持到事务结束。
不使用范围锁定，因此可以进行幻像读取(phantom read)。
幻像读取的示例（按一定的查询条件：
1.事务A查询表T以获得多行数据。
where子句用于选择数据，例如年龄在20到50岁之间的所有人。
2.事务B将数据插入表T.
示例：插入一个新行，其中包含35岁人员的数据。
3.事务A再次查询表T的数据。
使用与步骤1中相同的查询，再次选择年龄范围为20至50岁的人。
结果包括交易B（年龄为35岁的人）插入表T的数据。幻像读取已经发生。
PS：可序列化隔离级别将锁定整个范围，年龄20到50，用于读取和写入其他事务 - 即所谓的范围锁定。

- 读取已提交（Read committed）：RC
在读提交的隔离级别中，对与事务中的处理相关联的数据的写锁保持到事务结束。
保持读锁定，但仅在与读锁定关联的选择语句完成之前。
在此隔离级别，可能发生幻像读取和不可重复读取。
不可重复读取的示例：
1.事务A查询表T以获取数据。
例如，查询具有特定名称的人的数据，包括人的地址。
2.事务B更新事务A读取的表T中的数据，并且提交事务B.
例如，更新了事务A查询数据的人的地址。
3.事务A查询表T以获取数据。
如果再次读取在步骤1中检索的人的数据，则事务A将看不到与步骤1中相同的地址，而是在步骤2中写入更新的地址。
PS：不可重复读取（Non-Repeatable Read）适用于单个行，但是Phantom Read是一系列满足给定查询过滤条件的记录。

- 读取未提交（Read uncommitted）：RU
读取未提交的隔离级别是最低隔离级别。在此隔离级别，可能会发生脏读(dirty read)。
脏读的示例：
1.事务A查询表T以获取数据。
例如，查询具有特定名称的人的数据，包括人的地址。
2.事务B更新事务A读取的表T中的数据。
【请注意，不需要提交事务B以便发生脏读。】
例如，更新了事务A查询数据的人的地址。
3.事务A查询表T以获取数据。
如果再次读取在步骤1中检索的人的数据，则事务A将看不到与步骤1中相同的地址，而是在步骤2中写入更新的地址。

关于上述事务隔离级别的区别：
https://stackoverflow.com/questions/4034976/difference-between-read-commit-and-repeatable-read 这个链接给出了较好的答案。
考虑以下事务：
BEGIN TRANSACTION;
SELECT * FROM T; 
WAITFOR DELAY '00:01:00' 
SELECT * FROM T; 
COMMIT;
这是一个很简单的事务：从表T查询所有记录，等待1分钟，再次从表T查询所有记录，提交。
- READ COMITTED：第二次查询可能会返回任何数据。因为可能存在一个并发的其他事务更新/删除之前已存在的记录，或者插入了新的记录数据。总之，第二次查询会看到最新的数据。
- REPEATABLE READ：第二次查询被保证能看到和第一次查询一样的数据。新的记录数据可能会被其他事务在这中间的一分钟内插入，但是已存在的记录行不能被删除或者更新。保证了重复读的结果一致。
- SERIALIZABLE：第二次查询被保证能看到和第一次查询一样的数据。已存在的记录行不能被更新或删除，新的记录数据也不允许被其他并发事务插入。
```



## What is @EnableTransactionManagement for?

```
@EnableTransactionManagement注释用于在应用程序中注释一个配置类，以便使用@Transactional注释启用注释驱动的事务管理。
使用@EnableTransactionManagement批注时注册的组件是：
- TransactionInterceptor
拦截对@Transactional方法的调用，根据需要创建新事务等。
- JDK代理或AspectJ建议。
此建议拦截使用@Transactional注释的方法（或位于使用@Transactional注释的类中的方法）。
 
@EnableTransactionmanagement注解具有以下三个可选元素：
- mode
允许选择应与事务一起使用的建议类型。
可能的值是AdviceMode.ASPECTJ和AdviceMode.PROXY，后者是默认值。
- order
当多个建议应用于连接点时，事务建议的优先级。
默认值为Ordered.LOWEST_PRECEDENCE。
- proxyTargetClass
为True时，将使用CGLIB代理；为false时，使用基于JDK接口的代理。（影响应用程序中所有Spring托管bean的代理！）。仅在mode元素为AdviceMode.PROXY时适用。
```



## What does transaction propagation mean?

```
当调用方法时，事务传播确定使用现有事务的方式，具体取决于方法上@Transactional注释中配置的事务传播。
在@Transactional注释中设置传播时，有七种不同的选项可用，所有注释都在传播枚举中定义： 
•MANDATORY
调用方法时必须存在现有事务，否则将抛出异常。
•NESTED
如果存在事务，则在嵌套事务中执行，否则将创建新事务。此事务传播模式未在所有事务管理器中实现。
•NEVER
方法在事务之外执行。如果事务存在则抛出异常。
•NOT_SUPPORTED
方法在事务之外执行。暂停任何现有事务。
•REQUIRED
方法将在当前事务中执行。如果不存在任何事务，则将创建一个事务。
•REQUIRES_NEW
创建将在其中执行方法的新事务。暂停任何现有事务 
•SUPPORTS
如果存在，方法将在当前事务中执行；或在交易之外（如果不存在）。
```



## What happens if one @Transactional annotated method is calling another @Transactional annotated method on the same object instance?

```
代理Spring bean的自调用有效地绕过了代理，因此也阻止了任何管理事务的事务拦截器。
因此，第二个方法，即从bean中的另一个方法调用的方法，将在与第一个相同的事务上下文中执行。
第二个方法的@Transactional注释中的任何配置都不会生效。
如果Spring事务管理与AspectJ一起使用，那么任何在非公共方法上使用@Transactional的事务配置都将受到遵守。
```



## Where can the @Transactional annotation be used? What is a typical usage if you put it at class level?

```
@Transactional注释可以在类和接口的类和方法级别上使用。
当使用Spring AOP代理时，只有public方法上的@Transactional注释会产生任何影响。 
将@Transactional注释应用于protected或private或package的方法不会导致错误，但不会提供所需的事务管理。
```



## What does declarative transaction management mean?

```
声明式事务管理意味着声明需要在事务上下文中执行的方法和这些方法的事务属性，而不是实现。这是使用注释或Spring XML配置完成的。
```



## What is the default rollback policy? How can you override it?

```
Spring事务管理的默认回滚策略是仅在抛出未经检查的异常时才进行自动回滚。
可以使用@Transactional注解的rollbackFor元素配置导致回滚的异常类型。
可以使用noRollbackFor元素配置不会导致回滚的异常类型。
```



## What is the default rollback policy in a JUnit test, 
when you use the @RunWith(SpringJUnit4ClassRunner.class) in JUnit 4 or @ExtendWith(SpringExtension.class) in JUnit 5, and annotate your @Test annotated method with @Transactional?

```
如果使用@Test注释的测试方法也使用@Transactional注释，那么测试方法将在事务中执行。
在完成测试方法后，这样的事务将自动回滚。这样做的原因是测试方法应该能够自己修改任何数据库的状态，或者调用修改数据库状态的方法，并在完成测试方法后还原这些更改。
可以使用@Rollback注释更改测试的回滚策略，并将值设置为false。
```



## Why is the term "unit of work" so important and why does JDBC AutoCommit violate this pattern?

```
工作单元描述了事务的原子性特征。它是“全有或全无”，即所有在事务中发生并且在事务范围内发生的操作必须全部成功执行或根本不执行。
JDBC AutoCommit将使每个单独的SQL语句在其自己的事务中执行，并在每个语句完成时提交事务。这使得无法执行由多个SQL语句组成的操作作为工作单元。
可以通过在JDBC连接上调用值为false的setAutoCommit方法来禁用JDBC AutoCommit。
```



## What do you need to do in Spring if you would like to work with JPA?

```
如果要在Spring应用程序中使用JPA，则需要执行以下步骤：
- 声明相应的依赖项。
在Maven应用程序中，这是通过在pom.xml文件中创建依赖项来完成的。
有问题的依赖项通常是ORM框架依赖项，数据库驱动程序依赖项和事务管理器依赖项。

- 以注释的形式实现具有映射元数据的实体类。
至少，实体类需要在类级别上使用@Entity注释进行注释，并且@Id注释需要注释作为实体主键的字段或属性。
可以使用orm.xml文件将映射元数据与实体类的实现分开。

- 定义EntityManagerFactory bean。
Spring框架中的JPA支持在创建EntityManagerFactoryBean时提供了三种选择：
1.LocalEntityManagerFactoryBean
在仅使用JPA进行持久性和集成测试的应用程序中使用此选项，这是最简单的选项。
2.使用JNDI获取EntityManagerFactory
如果应用程序在JavaEE服务器中运行，请使用此选项。
3.LocalContainerEntityManagerFactoryBean
为应用程序提供完整的JPA功能。

- 定义DataSource bean。

- 定义TransactionManager bean。
通常使用Spring Framework中的JpaTransactionManager类。

- 实现repository
更好的选择是使用Spring Data JPA，您只需要创建存储库接口。
```



## Are you able to participate in a given transaction in Spring while working with JPA?

```
是的。
Spring JpaTransactionManager支持在同一个事务中进行直接的DataSource访问，允许将不知道JPA的普通JDBC代码与使用JPA的代码混合。
如果要将Spring应用程序部署到JavaEE服务器，则可以在Spring应用程序中使用JtaTransactionManager。JtaTransactionManager将委托给JavaEE服务器的事务协调器。
```



## Which PlatformTransactionManager(s) can you use with JPA?

```
任何JTA事务管理器都可以与JPA一起使用，因为JTA事务是全局事务，它们可以跨越多个资源，例如数据库，队列等。因此，JPA持久性变成了可以涉及事务的这些资源中的另一个。
- 将JPA与单个实体管理器工厂一起使用时，建议使用Spring Framework JpaTransactionManager。这也是JPA实体管理工厂意识到的唯一事务管理器。
- 如果应用程序具有多个要成为事务的JPA实体管理器工厂，则需要JTA事务管理器。
```



## What do you have to configure to use JPA with Spring? How does Spring Boot make this easier?

```
Ref: What do you need to do in Spring if you would like to work with JPA?

Spring Boot提供了一个启动器模块：
•提供在Spring应用程序中使用JPA所需的一组默认依赖项。
•提供使用JPA所需的所有Spring bean。
通过在应用程序中声明具有相同名称的bean，可以轻松地自定义这些bean，这是Spring应用程序中的标准。
•提供与持久性和JPA相关的许多默认属性。
通过在应用程序属性文件中声明一个或多个新属性，可以轻松地自定义这些属性。
```

