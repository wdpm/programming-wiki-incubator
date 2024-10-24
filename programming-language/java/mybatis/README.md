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

## cache mechanism of MyBatis
> https://tech.meituan.com/2018/01/19/mybatis-cache.html

一级缓存
- MyBatis一级缓存的生命周期和SqlSession一致。
- MyBatis一级缓存内部设计简单，是一个没有容量限定的HashMap。
- MyBatis的一级缓存最大范围是SqlSession内部，有多个SqlSession或者分布式的环境下，数据库写操作会引起脏数据，建议设定缓存级别为Statement。

二级缓存
- MyBatis的二级缓存相对于一级缓存来说，实现了SqlSession之间缓存数据的共享，同时粒度更加的细，能够到namespace级别，通过Cache接口实现类不同的组合，对Cache的可控性也更强。
- MyBatis在多表查询时，极大可能会出现脏数据，有设计上的缺陷，安全使用二级缓存的条件比较苛刻。
- 在分布式环境下，由于默认的MyBatis Cache实现都是基于本地的，分布式环境下必然会出现读取到脏数据，需要使用集中式缓存将MyBatis的Cache接口实现，有一定的开发成本，直接使用Redis、Memcached等分布式缓存可能成本更低，安全性也更高。