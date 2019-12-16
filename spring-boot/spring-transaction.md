# Spring 事务管理
> 基于注解的Spring 声明式事务管理
```java
@Transactional
public class BookingService{
    
    // or some method
    @Transactional
    public void methodA(){
        
    }
    
}
```

如果没有显示创建bean，默认创建一个DataSourceTransactionManager。
它会拦截使用@Transactional注释的方法的组件。通过类路径扫描检测到BookingService。