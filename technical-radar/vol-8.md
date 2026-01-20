# vol 8

>2013年5月



## what's new

- **Embracing falling boundaries**—Whether you like it or not, boundaries are falling down around you. We choose to embrace this by examining concepts like perimeterless enterprise, development environments in the cloud, and co-location by telepresence.
- **Applying proven practices to areas that somehow missed them**—We are not really sure why, but many in our industry have
  missed ideas like capturing client side javaScript errors, continuous delivery for mobile, database migrations for NoSQL, and
  frameworks for CSS.

- **Lightweight options for analytics**—Data science and analytics are not just for people with a PhD in the field. We highlight
  collaborative analytics and data science, where all developers understand the basics and work closely with experts
  when necessary.

- **Infrastructure as code**—Continuous delivery and DevOps have elevated our thinking about infrastructure.
  The implications of thinking about infrastructure as code and the need for new tools are still evolving.

## tech

- 协作分析和数据科学，所有的开发人员都使用基本的统计分析和工具来做出更好的决定
- 无边界的企业
- cloud开发环境
- 远程监控的共处环境
- 最小化应用配置
- 机器镜像作为构建工件
- 蓝绿部署是一种执行软件升级的模式

- 以前，像Chef和Puppet这样的工具缺乏对Windows的支持，导致大量的Powershell脚本来实现简单的基础设施自动化任务。随着技术的完善，Windows基础设施自动化变得极为可行。
- 建议在几乎所有情况下使用HTML5存储而不是cookies
- promises for asynchronous programming（从回调地狱这个坑跳到链式调用这个坑，真正的革新在于后续的async/await）
- 捕获客户端的JavaScript错误
- 为移动设备进行持续交付。TestFlight等服务允许你每天多次将原生应用部署到真实设备上
- 在移动网络上的移动测试
- NoSQL的数据库迁移
- 将性能测试作为一等公民来对待



## 工具

- 嵌入式Servlet容器
- D3
- 一些JavaScript框架采用了基于浏览器的模板，将更多的布局工作转移到客户端
- 通过将IObservables和IObservers与IEnumerables和IEnumerators放在同等地位，.NET的Rx允许开发者使用他们现有的LINQ（语言集成查询）操作符的知识来查询和组成异步操作和基于事件的代码，使用可观察事件流的共同基础抽象。微软还发布了RxJS，将反应式编程的好处带到了JavaScript
- 在开发的Windows特定解决方案 Octopus
- Puppet-librarian和Chef-librarian 使人们可以很容易地声明自己的模块依赖性，包括从这些社区网站拉入已知版本的代码。
- Hystrix是Netflix为JVM提供的一个库，它实现用于处理下游 failure 的模式，提供连接的实时监控，以及缓存和批处理机制，使服
  务间的依赖性更加有效。
- TestFlight和HockeyApp都允许你在没有应用商店的情况下管理移动应用程序的部署，使用户测试更容易。
- UIAutomator看起来是测试Android用户界面的最有前途的工具
- Logstash的出现是一种在源头解析和过滤日志，然后将它们转发到一个单一的聚合点的简单方法。
- Snowplow Analytics是一个开源的网络分析平台
- 与Ant和Maven等基于XML和插件的工具相比，Gradle和Rake等基于语言的构建工具长期以来一直提供更精细的抽象和更多的灵活性。



## 平台

- PostgreSQL正在扩展，成为SQL数据库的NoSQL选择
- MongoDB
- Redis
- Hadoop 2.0对HDFS和Map Reduce框架都进行了重大的重新架构
- ElasticSearch作为一个开源的搜索平台被逐渐采纳。它是一个基于Apache Lucene的可扩展、多租户和水平可扩展的搜索解决方案。
- Node.js是一个轻量级的网络容器，是开发微服务和作为移动和单页网络应用的服务器的有力选择。
- Zepto.js是一个轻量级的JavaScript库，主要基于JQuery。
- PhoneGap，现在更名为Apache Cordova，是一个让你使用HTML、CSS和JavaScript开发跨平台移动应用程序的平台。
- Rackspace云已经成为存储和计算领域的一个可行的竞争对手
- 开源的OpenStack项目正在积聚能量
- 手机的SMS和USSD作为用户接口
- Vumi是一个可扩展的开源信息传递引擎，在移动设备上通过节俭的方法推动对话
- 大型企业的解决方案不够敏捷。



## 语言和框架

- AngularJS、Knockout和Ember.js
- CoffeeScript来简化JavaScript代码库
- NPM
- 在.NET平台上，OWIN指定了一个开放的HTTP处理接口，将网络服务器和应用程序分离开来，就像Rack为Ruby做的那样
- Play Framework 2.1.1
- 用SASS、SCSS和LESS等CSS框架
- Twitter Bootstrap是一个流行的CSS框架