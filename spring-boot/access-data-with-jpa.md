# access data with jpa

## example
添加maven依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- for the sake of demo-->
<dependency>-->
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

创建一个 model层的类 ``Customer``
```java
// Customer类使用@Entity注释，表明它是JPA实体。由于缺少@Table注释，因此假定此实体将映射到名为Customer的表
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    /**
     * The default constructor only exists for the sake of JPA.
     * You won’t use it directly, so it is designated as protected
     */
    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
```
创建一个接口，继承自CrudRepository
```java
public interface CustomerRepository extends CrudRepository<Customer,Long> {

    // Spring Data JPA creates an implementation on the fly when you run the application.
    List<Customer> findByLastName(String lastName);

    List<Customer> findByFirstName(String firstName);
}
```
其中，方法名需要符合一定的规范格式，可满足基本的CRUD需求。

## test
主类添加一个方法：
```java
@Bean
public CommandLineRunner run(CustomerRepository repository) {
    return (args) -> {
        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));

        // fetch all customers
        log.info("Customers found with findAll():");
        log.info("-------------------------------");
        for (Customer customer : repository.findAll()) {
            log.info(customer.toString());
        }
        log.info("");

        // fetch an individual customer by ID
        repository.findById(1L)
                .ifPresent(customer -> {
                    log.info("Customer found with findById(1L):");
                    log.info("--------------------------------");
                    log.info(customer.toString());
                    log.info("");
                });

        // fetch customers by last name
        log.info("Customer found with findByLastName('Bauer'):");
        log.info("--------------------------------------------");
        repository.findByLastName("Bauer").forEach(bauer -> {
            log.info(bauer.toString());
        });
        log.info("");

        // fetch customers by first name
        log.info("Customer found with findByFirstName('Chloe'):");
        log.info("--------------------------------------------");
        repository.findByFirstName("Chloe").forEach(chloe -> {
            log.info(chloe.toString());
        });
        log.info("");
    };
}
```