# maple-sample

## 如何运行
- step 1：执行maple_sample.sql导入数据
- step 2：在App类中修复数据库连接参数
- step 3：IDE 导入项目，放在Tomcat中或者直接使用Maven Jetty插件启动

```sh
# 展示JSP视图
http://localhost:9000/
# 控制器输出hello
http://localhost:9000/hello
# 展示html视图
http://localhost:9000/html
# CRUD
http://localhost:9000/users
```