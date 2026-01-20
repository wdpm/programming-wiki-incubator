# JPA Projection

## By defining interface DTOs
> Recommend
- [by defining interface DTOs](Projection-by-interface.md)

## By EntityManager
> [Here](src\main\java\io\github\wdpm\App.java)
```java
em.createNativeQuery(sql, Tuple.class).getResultList()
```
```java
em.createNativeQuery(sql).unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list()
```
```java
em.createNativeQuery(sql).unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(CustomerDetails.class)).list();
```
```java
em.createNativeQuery(sql).unwrap(NativeQuery.class).setResultTransformer(new CustomerDetailsTransformer()).list()
```