# spring mvc

method body的实现依赖于视图技术，例子中是Thymeleaf，用于执行HTML的服务器端渲染。 
Thymeleaf解析下面的greeting.html模板并评估th：text表达式以呈现控制器中设置的$ {name}参数的值。

## example

创建一个Controller（注意不是RestController），方法参数中加入Model变量，方法体中使用model设置属性。
```java
@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
```

``resources\templates\``目录下创建``greeting.html``
```html
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<p th:text="'Hello, ' + ${name} + '!'"></p>
</body>
</html>
```

## Test
GET http://localhost:8080/greeting
```
"Hello, World!"
```

GET http://localhost:8080/greeting?name=User
```
"Hello, User!"
```