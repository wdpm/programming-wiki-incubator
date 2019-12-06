# maven build modules
> https://stackoverflow.com/questions/1114026/maven-modules-building-a-single-specific-module

Suppose we have one project that has some nested module as follow:
```
Parent blog pom.xml contains 8 modules.
<modules>
    <module>register</module>
    <module>gateway</module>
    <module>config</module>
    <module>
         Parent client module has 6 sub modules.
            <modules>
                <module>user</module>
                <module>comment</module>
                <module>category</module>
                <module>search</module>
                <module>blogmgr</module>
                <module>public</module>
            </modules>
    </module>
    <module>common</module>
    <module>test</module>
    <module>mybatis-generator</module>
    <module>zipkin</module>
</modules>
```
For example:
- all sub module depend on blog module
- register module depend on common module.

So, if you want to build only register module, you should build:
```
1.blog module
2.common module
3.register nmodule
```

## build a specific sub module

Under project root folder
```bash
C:\IdeaProjects\SpringCloudDemo\blog>mvn install -pl register -am
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO]
[INFO] blog                                                               [pom]
[INFO] common                                                             [jar]
[INFO] register                                                           [jar]
[INFO]
[INFO] -------------------------< com.lynn.blog:blog >-------------------------
[INFO] Building blog 1.0-SNAPSHOT                                         [1/3]
[INFO] --------------------------------[ pom ]---------------------------------
...
[INFO] ------------------------< com.lynn.blog:common >------------------------
[INFO] Building common 1.0-SNAPSHOT                                       [2/3]
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
....
[INFO] -----------------------< com.lynn.blog:register >-----------------------
[INFO] Building register 1.0-SNAPSHOT                                     [3/3]
[INFO] --------------------------------[ jar ]---------------------------------
...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for blog 1.0-SNAPSHOT:
[INFO]
[INFO] blog ............................................... SUCCESS [  0.288 s]
[INFO] common ............................................. SUCCESS [  2.960 s]
[INFO] register ........................................... SUCCESS [  1.991 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
```bash
-pl, --projects
        Build specified reactor projects instead of all projects
-am, --also-make
        If project list is specified, also build projects required by the list
```

## build the whole project
Under project root folder
```bash
mvn install
```