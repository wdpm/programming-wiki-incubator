# Spring Boot annotations

@RestController 组合了 @Controller 和 @ResponseBody ,导致一个 web 请求返回数据而不是视图。

@SpringBootApplication
- @SpringBootConfiguration: 表示该类为一个 bean 的定义类。
- @EnableAutoConfiguration: 告诉Spring Boot根据classpath设置，其他bean和各种属性设置开始添加bean。
- @ComponentScan: 告诉Spring在该类所在包中寻找其他组件，配置和服务，允许它找到控制器。  
