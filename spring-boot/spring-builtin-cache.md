# spring builtin cache

@EnableCaching注释触发一个后处理器，它检查每个Spring bean是否存在公共方法上的缓存注释。 
如果找到这样的注释，则自动创建代理以拦截方法调用并相应地处理缓存行为。

这个后处理器管理的注释可以是Cacheable，CachePut和CacheEvict。 Spring Boot会自动配置合适的CacheManager作为相关缓存的提供程序。

该示例不使用特定的缓存库，使用ConcurrentHashMap的简单fallback。 缓存抽象支持各种缓存库，完全符合JSR-107（JCache）。

## example

1.maven依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```
2.主类添加 ``@EnableCaching``注解

3.创建实体类 Book
```java
public class Book {

    private String isbn;
    private String title;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
    //get/set/toString
}
```
4.创建interface并简单实现
```java
public interface BookRepository {
    Book getByIsbn(String isbn);
}
```
```java
@Component
public class SimpleBookRepository implements BookRepository {
    @Override
    @Cacheable("books")
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Some book");
    }
    
    private void simulateSlowService() {
        try {
            long time = 3000L;//3s delay
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
```

## test
添加一个命令行运行组件
```java
@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final BookRepository bookRepository;

    @Autowired
    public AppRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(".... Fetching books");
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
    }
}
```
结果:
```
2019-12-04 17:21:33.023  INFO 17068 --- [           main] c.example.cachedatawithspring.AppRunner  : .... Fetching books
2019-12-04 17:21:36.036  INFO 17068 --- [           main] c.example.cachedatawithspring.AppRunner  : isbn-1234 -->Book[isbn='isbn-1234', title='Some book']
2019-12-04 17:21:39.036  INFO 17068 --- [           main] c.example.cachedatawithspring.AppRunner  : isbn-4567 -->Book[isbn='isbn-4567', title='Some book']
2019-12-04 17:21:39.036  INFO 17068 --- [           main] c.example.cachedatawithspring.AppRunner  : isbn-1234 -->Book[isbn='isbn-1234', title='Some book']
2019-12-04 17:21:39.037  INFO 17068 --- [           main] c.example.cachedatawithspring.AppRunner  : isbn-4567 -->Book[isbn='isbn-4567', title='Some book']
2019-12-04 17:21:39.037  INFO 17068 --- [           main] c.example.cachedatawithspring.AppRunner  : isbn-1234 -->Book[isbn='isbn-1234', title='Some book']
2019-12-04 17:21:39.037  INFO 17068 --- [           main] c.example.cachedatawithspring.AppRunner  : isbn-1234 -->Book[isbn='isbn-1234', title='Some book']
```
观察时间轴可以发现第一次获取 isbn-1234 和 isbn-4567 分别使用3s，后续请求使用了cache，基本不耗时返回。