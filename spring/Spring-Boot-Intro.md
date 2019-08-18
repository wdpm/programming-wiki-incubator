# Spring Boot Intro

## What is Spring Boot?

```
Spring Boot是一个收集很多模块的项目。

- spring-boot-dependencies
包含Spring Boot使用的依赖项，Maven插件等版本，包括starter-modules使用的依赖项。包含依赖项的托管依赖项。
- spring-boot-starter-parent
父pom.xml文件，使用Maven为Spring Boot应用程序提供依赖性和插件管理。
- spring-boot-starters
所有Spring Boot启动器模块的父级。
- spring-boot-autoconfigure
包含Spring Boot中启动器的自动配置模块。
- spring-boot-actuator
允许监视和管理使用Spring Boot创建的应用程序。
- spring-boot-tools
与Spring Boot结合使用的工具，如Spring Boot Maven和Gradle插件等。
- spring-boot-devtools
开发Spring Boot应用程序时可以使用的开发人员工具。

其中，最重要的模块是：起步依赖和自动配置。
```



## What are the advantages of using Spring Boot?
## Why is it “opinionated”?
## What things affect what Spring Boot sets up?
## What is a Spring Boot starter POM? Why is it useful?
## Spring Boot supports both properties and YML files. Would you recognize and understand them if you saw them?
## Can you control logging with Spring Boot? How?
## Where does Spring Boot look for property file by default?
## How do you define profile specific property files?
## How do you access the properties defined in the property files?
## What properties do you have to define in order to configure external MySQL?
## How do you configure default schema and initial data?
## What is a fat far? How is it different from the original jar?
## What is the difference between an embedded container and a WAR?
## What embedded containers does Spring Boot support?