# MyBatis 3.x

## Table of Content

- [Setup](/setup.md )
- [XML配置](xml-config.md )
- [XML 映射文件](xml-mapper.md )
- [动态SQL](dynamic-sql.md )
- [Mapper的 Java 注解]()
- [SQL语句构建器](SQL-builder.md )
- [日志](logging.md )
- mybatis generator

## Note

Spring Data JPA 使用以下其中之一的 persistentProvider:
- hibernate
- eclipseLink
- generic

Mybatis is NOT a JPA implementation. 不能成为Spring Data JPA的持久化底层实现。

连接池使用hikari cp或者druid

事务一律交给spring管理

- JPA Spec -> Spring Data JPA -> Hibernate(底层实现)
- Mybatis 

