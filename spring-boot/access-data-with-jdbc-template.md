# access data with jdbc template

Spring提供了一个名为JdbcTemplate的模板类，可以轻松使用SQL关系数据库和JDBC。

> 基于演示目的，使用H2

Spring Boot支持H2，一种内存中的关系数据库引擎，并自动创建连接。 
此Application类实现了Spring Boot的CommandLineRunner，意味着它将在加载应用程序上下文后执行run()方法。

## example

添加maven依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

新建一个model领域模型类``Customer``
```java
public class Customer {
    private long id;
    private String firstName;
    private String lastName;

    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // get/set/toString
}
```

## test
```java
@SpringBootApplication
public class UsingJdbcApplication implements CommandLineRunner {

    //...
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Stream.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[]{"Josh"},
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> log.info(customer.toString()));
    }
}
```
日志输出:
```bash
Inserting customer record for John Woo
Inserting customer record for Jeff Dean
Inserting customer record for Josh Bloch
Inserting customer record for Josh Long
Querying for customer records where first_name = 'Josh':
Customer[id='3',firstName='Josh',lastName='Bloch']
Customer[id='4',firstName='Josh',lastName='Long']
```
```
jdbcTemplate.execute("SQL");//执行SQL
jdbcTemplate.batchUpdate("SQL", someEntityLists);//批量更新数据
jdbcTemplate.query("SQL")//查询
```