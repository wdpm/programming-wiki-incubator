# 日志

Mybatis 内置日志工厂将日志交给以下其中一种作代理：

- SLF4J
- Apache Commons Logging
- Log4j 2
- Log4j
- JDK logging

使用第一个查找得到的工具（从上往下查找）。如果一个都未找到，日志功能被禁用。
```xml
<configuration>
  <settings>
    <setting name="logImpl" value="LOG4J"/>
  </settings>
</configuration>
```

logImpl 可选值：SLF4J、LOG4J、LOG4J2、JDK_LOGGING、COMMONS_LOGGING、STDOUT_LOGGING、NO_LOGGING

在代码中显式调用：
```
org.apache.ibatis.logging.LogFactory.useSlf4jLogging();
org.apache.ibatis.logging.LogFactory.useLog4JLogging();
org.apache.ibatis.logging.LogFactory.useJdkLogging();
org.apache.ibatis.logging.LogFactory.useCommonsLogging();
org.apache.ibatis.logging.LogFactory.useStdOutLogging();
```

## 日志配置

日志工具的本质都是设置：
- appenders 日志输出的目的地。
- loggers 引用appender，设置日志来源，以及对应的日志级别。
  - 日志来源的粒度：包，类，类方法，命名空间，命名空间的某方法
  - 对应的日志级别：TRACE，DEBUG，INFO，WARN，ERROR