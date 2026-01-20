# vol 7

> OCTOBER 2012



## what's new

- Mobile—As mobile is rapidly becoming the primary way that people access the internet, this needs to be factored in to new enterprise application strategy, product strategy and implementation – from “mobile first” design all the way through to a new breed of testing tools.

- Accessible Analytics—Big Data does not have to equal Big Budgets. A combination of open-source tooling and cloud-based infrastructure provides a more readily accessible analytics and visualization approach.
- Simple architectures—Simple continues to gain traction, including both techniques for building and composing applications, as well as infrastructure-based techniques to enable simple deployment, failover and recovery. This theme is a recurring one for us, but we have not yet seen the usage shifts we believe are necessary.
- Reproducible environments—Tools supporting the standardization, set-up automation and coordinated management of development, test and production environments for both internally hosted and public cloud environments feature prominently on this edition of the radar.
- Data persistence done right—As NoSQL databases are maturing and gaining acceptance, an understanding of the patterns for use (and abuse) becomes imperative

## tech

- 微服务
-  边际包含（ESI）进行页面整合
- 在DNS中的配置
- aggregates as documents
- work-in-progress limits（限制同时进行的任务数，清晰地分析目前团队的工作能力）
- **deployment and scripting test tools**, such as Pester for PowerShell and TOFT for Chef and Puppet
- 移动优先
- 响应式网页设计
- 高级分析（基于深度学习）
- 将**日志视为数据**并存储完整的日志
- 游击式的用户测试
- 远程可用性测试
- **语义监控**使用您的测试来持续评估您的应用
- **过程中的验收测试**挑战了测试代码和被测试的应用程序必须在不同的过程中运行的概念
- 我们建议**不要进行详尽的基于浏览器的测试**



### Tools

- 建议尝试Rake for Java 和 .Net项目
- 有两件事让Ant和Maven等基于XML的构建工具产生了疲劳：**太多的愤怒的尖括号和插件架构的粗糙性**。随着项目变得越来越复杂，插件架构严重限制了构建工具优雅成长的能力。我们觉得插件是错误的抽象层次，而更喜欢基于语言的工具，如Gradle和Rake。
- GemJars整合并简化了真正的多语言代码库的构建
- 不可改变的服务器（immutable servers），或 "凤凰服务器"，是企业寻求IaaS和PaaS的明智之举
- Jasmine paired with Node.js
- Zipkin是一个工具，对基于服务的系统的不同组件进行检测，并使用 "类似Firebug "的视图，对通过多个服务的逻辑请求进行可视化分解。
- Zucchini是一个测试框架，为iOS应用提供Cucumber风格的BDD测试。
- Apache Pig通过提供一种叫做Pig Latin的高级语言和一个执行运行时，简化了Hadoop的开发。
- 疯狂的蛋（crazy eggs）是一种更便宜的纯软件解决方案，它可以根据鼠标的移动产生热图。
- Graphite 系统监控
- Highcharts支持多种高度可配置的交互式图表类型，并且能够轻松渲染大型数据集
- D3
- 依赖结构矩阵
- 嵌入式Servlet容器
- 在线自动化性能测试 Locust
- SaaS性能测试工具



## platform

- 混合云结合了公共云和私人数据中心的最佳特点
- 开源的IaaS平台，如OpenStack或CloudStack
- 谷歌的BigQuery将数据分析带到了云端
- cloud的持续集成
- 移动支付系统
- MongoDB
- Neo4j
- Riak，分布式存储，无模式，与数据类型无关。
- Datomic是一个不可变的数据库服务器，具有迷人的交易和部署特性
- Couchbase
- Vert.x
- Calatrava是一个值得评估的移动应用开发方法
- Meteor.js是一个客户端和服务器端的JavaScript应用框架
- windows phone
- Singleton infrastructure belongs to misguided vendor-driven architectures of the past.



## 语言和框架

- JavaScript视为一种平台
- Require.js库
- Twitter Bootstrap
- Scratch、Alice和Kodu是依靠视觉环境和积木作为教学设备的编程语言
- Lua已经在各种行业中得到了大规模的应用
- 微框架 Sinatra
- Gremlin是一种由多个图形数据库支持的命令式图形遍历语言
- Jekyll代表了网络发布领域的框架的 "微观化"。虽然重点保持在做一件事上--以博客为特色的网站--尽可能地透明。
- RubyMotion
- HTML5用于离线应用
- AngularJS和Knockout应该被认为是这个领域目前的领跑者
- Backbone.js
- 接受网络的页面和基于请求的本质

