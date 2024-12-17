### **示例背景**

1. **方法 A**：调用方法 B，并决定事务的传播方式。
2. **方法 B**：包含写操作（比如向数据库插入一条记录），其事务传播方式由注解指定。

我们将模拟两种情况：

- **`REQUIRED`**：方法 B 加入方法 A 的事务。
- **`REQUIRES_NEW`**：方法 B 独立开启一个新事务。

------

### **代码实现**

#### 1. Service 类

```java
java复制代码@Service
public class TransactionDemoService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 方法 A：负责调用方法 B，并控制事务传播方式
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void methodARequired() {
        System.out.println("Method A starts with REQUIRED transaction");
        
        // 插入一条记录 (操作1)
        userRepository.save(new User("UserA1"));
        
        // 调用方法 B
        methodBRequired();
        
        // 模拟抛出异常
        if (true) {
            throw new RuntimeException("Exception in Method A (REQUIRED)");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void methodARequiresNew() {
        System.out.println("Method A starts with REQUIRES_NEW transaction");
        
        // 插入一条记录 (操作1)
        userRepository.save(new User("UserA2"));
        
        // 调用方法 B
        methodBRequiresNew();
        
        // 模拟抛出异常
        if (true) {
            throw new RuntimeException("Exception in Method A (REQUIRES_NEW)");
        }
    }

    /**
     * 方法 B：由方法 A 调用，插入另一条记录
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void methodBRequired() {
        System.out.println("Method B starts with REQUIRED transaction");
        userRepository.save(new User("UserB1"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void methodBRequiresNew() {
        System.out.println("Method B starts with REQUIRES_NEW transaction");
        userRepository.save(new User("UserB2"));
    }
}
```

------

#### 2. Repository 接口

使用 JPA 操作数据库：

```java
java复制代码@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

------

#### 3. 测试场景

```java
java复制代码@SpringBootTest
public class TransactionDemoTest {

    @Autowired
    private TransactionDemoService transactionDemoService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRequiredPropagation() {
        try {
            transactionDemoService.methodARequired();
        } catch (Exception e) {
            System.out.println("Caught exception in testRequiredPropagation");
        }

        // 检查数据库
        System.out.println("Users in DB after REQUIRED: " + userRepository.findAll());
    }

    @Test
    public void testRequiresNewPropagation() {
        try {
            transactionDemoService.methodARequiresNew();
        } catch (Exception e) {
            System.out.println("Caught exception in testRequiresNewPropagation");
        }

        // 检查数据库
        System.out.println("Users in DB after REQUIRES_NEW: " + userRepository.findAll());
    }
}
```

------

### **结果分析**

1. **`testRequiredPropagation`（方法 A 和方法 B 都是 `REQUIRED`）**

   - 方法 B 加入方法 A 的事务。
   - 方法 A 抛出异常时，整个事务回滚。
   - **数据库结果**：无数据被保存。

   **输出**：

   ```
   Method A starts with REQUIRED transaction
   Method B starts with REQUIRED transaction
   Caught exception in testRequiredPropagation
   Users in DB after REQUIRED: []
   ```

2. **`testRequiresNewPropagation`（方法 A 是 `REQUIRES_NEW`，方法 B 也是 `REQUIRES_NEW`）**

   - 方法 B 独立开启新事务，与方法 A 的事务无关。
   - 方法 A 抛出异常时，方法 B 的事务不会受影响。
   - **数据库结果**：方法 B 的插入操作被保存。

   **输出**：

   ```
   Method A starts with REQUIRES_NEW transaction
   Method B starts with REQUIRES_NEW transaction
   Caught exception in testRequiresNewPropagation
   Users in DB after REQUIRES_NEW: [UserB2]
   ```

------

### **总结**

| **场景**                                | **事务传播方式**         | **结果**                    |
| --------------------------------------- | ------------------------ | --------------------------- |
| **方法 A 和方法 B 都是 `REQUIRED`**     | 方法 B 加入方法 A 的事务 | 方法 A 异常导致所有操作回滚 |
| **方法 A 和方法 B 都是 `REQUIRES_NEW`** | 方法 B 独立事务          | 方法 B 提交，方法 A 回滚    |

使用 **`REQUIRED`** 时，适合共享事务的场景。使用 **`REQUIRES_NEW`** 时，适合需要独立提交的场景，例如日志记录或异步任务。

