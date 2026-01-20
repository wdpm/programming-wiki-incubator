# Spring JdbcTemplate

引入spring-boot-starter-jdbc, h2 database 和 spring-boot-starter-web 作为快速原型测试。

编辑 `src\main\resources\application.yml`，开启h2 web console。
```yml
spring:
  datasource:
    url: jdbc:h2:mem:mydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
    schema: classpath:IScream-schema.sql
    data:
    initialization-mode: always
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
  h2:
    console:
      path: "/h2-console"
      enabled: true
```
启动后留意控制台输出
```bash
H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:mydb'
```
访问 http://localhost:8080/h2-console/ 并填写正确的jdbc路径。

![](images\h2-console.png)

数据库设计

![](images\IScream_schema.png)

## 代码示例
Spring Boot支持H2，一种内存的关系数据库引擎，自动创建连接。App类实现了CommandLineRunner，将在加载应用程序上下文后执行run()方法。

[代码示例](\src\main\java\io\github\wdpm\App.java)

## 小结
- jdbc 比原生prepareStatement写法更简便。提供了许多工具方法，同时又完全保留原生SQL查询的灵活性。
```
jdbcTemplate.execute("SQL");//执行SQL
jdbcTemplate.batchUpdate("SQL", someEntityLists);//批量更新数据
jdbcTemplate.query("SQL")//查询
```
- jdbc不依赖JPA注解POJO来持久化Java对象。
- 多表插入，先插入主表，获取新增id，根据此id完成后续的外键约束，更新从表。
- 多表查询，
