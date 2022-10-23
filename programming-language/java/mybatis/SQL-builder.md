# SQL语句构建器

```java
// Anonymous inner class
public String deletePersonSql() {
  return new SQL() {{
    DELETE_FROM("PERSON");
    WHERE("ID = #{id}");
  }}.toString();
}

// Builder / Fluent style
public String insertPersonSql() {
  String sql = new SQL()
    .INSERT_INTO("PERSON")
    .VALUES("ID, FIRST_NAME", "#{id}, #{firstName}")
    .VALUES("LAST_NAME", "#{lastName}")
    .toString();
  return sql;
}
```

## 配置参数

- SELECT
- SELECT_DISTINCT
---
- FROM
---
- JOIN
- INNER_JOIN //同JOIN
- LEFT_OUTER_JOIN
- RIGHT_OUTER_JOIN
- ~~FULL_OUTER_JOIN~~
---
- OR
- AND
---
- GROUP_BY
- HAVING
- ORDER_BY
- LIMIT
- OFFSET
---
- FETCH_FIRST_ROWS_ONLY
- OFFSET_ROWS
---
- DELETE_FROM
- UPDATE
- INSERT_INTO
- VALUES
---
- INTO_COLUMNS
- INTO_VALUES
---
- ADD_ROW
---
