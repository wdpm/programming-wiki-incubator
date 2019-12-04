# Use mybatis generator to generate code

## Setup
```
│  pom.xml
├─src
│  └─main
│      ├─java
│      │     CommentGenerator.java
│      │     PageModel.java
│      │     PaginationPlugin.java
│      │     SerializablePlugin.java
│      │     TypeGenerator.java
│      │     
│      │
│      └─resources
│              generator.properties
│              generatorConfig.xml
```
Under resources/, create ``generator.properties`` and ``generatorConfig.xml``
- ``generator.properties`` define some properties.
```properties
generator.jdbc.driver=com.mysql.jdbc.Driver
generator.jdbc.url=jdbc:mysql://192.168.31.12:3306/blog_db?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true&amp;useSSL=false
generator.jdbc.username=root
generator.jdbc.password=Root_2019
classPathEntry=C:/Users/evan/.m2/repository/mysql/mysql-connector-java/5.1.46/mysql-connector-java-5.1.46.jar
```
- ``generatorConfig.xml`` define mybatis config.
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration>
    <properties resource="generator.properties"/>
    <classPathEntry location="${classPathEntry}" />
    <context id="MysqlContext" targetRuntime="MyBatis3" defaultModelType="flat">
       ...
    </context>
</generatorConfiguration>
```
- create custom Generator and Plugin as follow:
```java
public class CommentGenerator extends DefaultCommentGenerator {
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        super.addFieldComment(field, introspectedTable, introspectedColumn);
        // do something
    }
}
```
```java
public class TypeGenerator extends JavaTypeResolverDefaultImpl {
    public TypeGenerator(){
        super();
        // do something
    }
}
```
```java
public class SerializablePlugin extends PluginAdapter {}
```

## Generate code
1.click idea IDE ``mybatis-generator``->``Lifecycle``->``compile`` button.Then you can get ``${project.basedir}\target\mybatis-generator.jar``.

2.edit ``pom.xml``
```xml
<plugins>
    <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>${mybatis.generator.version}</version>
<!--                ref: https://mybatis.org/generator/running/runningWithMaven.html # Classpath Issues-->
<!--                Add local jar dependencies.-->
        <dependencies>
            <dependency>
                <groupId>com.lynn.blog</groupId>
                <artifactId>mybatis-generator</artifactId>
                <version>1.0-SNAPSHOT</version>
                <scope>system</scope>
                <systemPath>${project.basedir}\target\mybatis-generator.jar</systemPath>
            </dependency>
        </dependencies>
        <configuration>
            <verbose>true</verbose>
            <overwrite>true</overwrite>
        </configuration>
    </plugin>
</plugins>
```

3.click idea IDE ``mybatis-generator``->``plugins``->``mybatis-generator``->``mybatis-generator:generate`` button.
If build success, code will located at ``com.XXX.XXX.pub``.
