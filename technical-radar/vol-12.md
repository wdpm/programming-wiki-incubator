# vol 12

> MAY 2015



## 最新动态

- 架构创新。

  第一种云原生架构：微服务。持续交付。容器化和软件定义网络这类带来更多技术选择和能力的趋势能够得以延续。

- 微软新一波开放

  对开源项目提供主机服务的平台CodePlex。在Github上发布了 .Net平台和运行时的很大一部分作为开源项目。
  
- 企业中持续的安全挑战

  

## 技术

- 测试替身。测试替身和实际的API之间失去同步的风险。通过使用集成的合约测试。
- 消费者驱动的合约测试。消费端把它们的测试提供给API提供端—采用消费端驱动的合约。

- GraphViz（graphviz.org），一些可以输出SVG格式的工具，生成实时的，自动化的基础架构图（automated infrastructure diagram）

- 离线优先Web应用程序
- 敏捷开发极大的挑战了传统开发模型，通过与开发过程同时进行的持续需求发现，代替了预先的需求确定（瀑布流）。提倡企业组织依照产品而不是项目（products rather than projects）的思路进行思考

- 威胁建模：主要从防御的角度出发，帮助理解和识别潜在的威胁。当把用户故事变为“邪恶用户故事”。

- Flux 基于一个单向数据流，用户或外部事件对数据存储的修改会触发数据在渲染管道中向上流动。

- 越来越多的项目把 git（git-scm.com）作为一个轻量化的 CMS 来使用。

- 凤凰服务器（martinfowler.com/bliki/PhoenixServer.html）的想法现在已经被很好的建立并且在被应用到一些正确的问题上时带来了很多好处。而我们要部署的服务器所依赖的环境，如在某些情况下需要等待网络配置、负载均衡、防火墙端口等等情况，却已逐渐成为修改配置时的主要瓶颈和限制。凤凰环境可以支持为测试，开发，UAT等等配置一个全新的环境。它也可以简化对灾难恢复环境的配置。

- 根据反应型编程宣言(reactivemanifesto.org)，反应型架构（reactive
  architectures）主要基于通过一个网络下独立进程间的单向，异步的不可变事件流（可能具体实现为微服务）。

- 对于安全，传统的方式依赖于前期的需求规格以及最后阶段的验证。这种“**安全三明治**”的方式很难应用于敏捷团队，因为大部分设计都贯穿于整个过程，而且也没有持续交付所提供的自动化便利。公司或者组织应着眼于如何在整个敏捷开发周期中注入安全实践。这包括：正确评估当前威胁模型的级别以做前期设计；考虑何时将安全问题划分为独立的故事、验收标准、或全局的非功能性需求；在构建流水线中引入静态或动态的自动化安全测试；考虑如何将更深层次的测试，如渗透测试，引入到持续交付的发布过程中。

## 平台

- Apache Spark（spark.apache.org）一种快速和通用的大规模数据处理引擎。
- SQL-on-Hadoop这一趋势标志着一个重要的转折，它将 Hadoop 的定位从与数据库互补的批处理，转变为某种可以与之竞争的技术。

- Cloudera Impala（cloudera.com/content/cloudera/en/products-and-services/cdh/impala.html）是早期的SQL-on-Hadoop 平台之一。它是一个基于C++的，支持大规模并行处理的分布式查询引擎。

- TOTP与双阶段认证

- Apache Kylin (kylin.io)，是一个来自 eBay 公司的开源数据
  分析解决方案，它能够在超大数据集上进行基于SQL的多维度
  分析（OLAP）。

- CoreCLR (github.com/dotnet/coreclr) 和CoreFX(github.com/dotnet/corefx) 是 .NET 的核心平台和框架，最近被微软开源。

- Heroku用它的12要素应用模型改变了我们关于构建、部署、托管 Web 应用的方式。Deis (deis.io) 将 Heroku PaaS 模型封装到一个开源框架中，部署在可被托管在任何地方Docker容器中。

- OpenAM (forgerock.com/ products/open-identity-stack/
  openam) ，成为了一个支撑 OpenID 连接和 SAML 2.0的可
  扩展的开放源码平台。

- Spark (spark.io)是基于云的互联设备全栈解决方案。

- 时间序列数据库（TSDB）是一种针对时间序列数据的处理做了优化的系统。在许多开源和商业平台（比如OpenTSDB,InfluxDB, Druid, BlueFloodDB等等）的促进下，时间序列数据库技术发展迅猛。其中一些数据库产品还使用了类似Cassandra和HBase的分布式数据库作为他们的底层存储引擎。

- 推荐在未来的项目中使用嵌入式的应用服务器而不是传统的应用服务器。

- SPDY对HTTP/1.1的性能改进。新的 HTTP/2标准协议包含了很多 SPDY 中性能优化的关键特性。

## 工具

- Composer 包管理 for php
- Mountebank是一个轻量的测试工具，被用于对HTTP、HTTPS、SMTP和TCP进行模拟（Mock）和打桩（Stub）

- Postman（getpostman.com/features）是一个在Chrome中使用的REST客户端插件。

- Brighter（iancooper.github.io/Paramore/Brighter.html）是一个基于.Net的开源工具库，主要实现了命令调用模式。

- 支持DNS和基于HTTP发现机制的服务发现工具Consul（consul.io）持续让人印象深刻。Consul Template (github.com/hashicorp/
  consultemplate) 可以直接使用Consul的信息来填充配置文件，使得像用mod_proxy进行客户端负载均衡更加容易。

- Hamms（github.com/kevinburke/hamms）是一个有趣的开源工
  具，可以模拟一个行为损坏的HTTP服务器，触发一系列的失败，包括连接失败，或者响应缓慢，或者畸形的响应。

- Polly（github.com/michael-wolfenden/Polly）帮助构建基于微服务的系统。类似于Java生态的Hystrix。

- REST-assured（code.google.com/p/rest-assured）是一个用于测试和验证RESTful服务的Java DSL。

- ZED Attack Proxy (ZAP)（owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project）是一个OWASP的项目，允许你以自动化的方式探测已有站点的安全漏洞。

- Apache Kafka(kafka.apache.org)是一个开源消息框架，它支持基于有序的发布消息到许多独立的轻量级的消费方的架构风格。
- Blackbox（github.com/StackExchange/blackbox）是一个用于加密源代码仓库中特定文件的简单工具。一些其他的工具也可以考虑，如 git-crypt 和 Trousseau。

- Bokeh (bokeh.pydata.org)是可以让你创建像 D3.js 一样风格的交互式可视化的Python和JavaScript库，但是在处理大数据集或者流式的数据集时，具有更高的性能。
- Vega (trifacta.github.io/vega)是一种针对D3的声明式可视化语法，它接收服务器端生成的 JSON数据并将可视化描述转化为D3.js的代码。

- Gor (github.com/buger/gor)是一个开源工具,可以实时捕获线上HTTP请求，并在测试环境中重放这些HTTP请求，以帮助我们使用到这些产品环境数据来持续测试我们的系统。

- NaCl (nacl.cr.yp.to) 库（读作‘Salt’）提供了关于加密，解密
  和数字签名的一系列的功能。当前支持C和C++的库，关于Python的封装正在进行中。
- Origami (facebook.github.io/origami)是一款免费的用户原
  型设计工具，其对常用功能提供了大量的快捷键操作。

- Pdfmake (github.com/bpampuch/pdfmake)是一个可以
  在浏览器里直接生成和打印PDF文档的JavaScript库。
- 用PlantUML（plantuml.sourceforge.net）来创建这些图表，因为它让我们以清晰的文字形式来表达图表背后的意图，而不用再去摆弄那些“过度”的图形化工具。

- 一个Graphite的替代品：Prometheus (prometheus.io)。

- Quick (github.com/Quick/Quick)是一个针对Swift和Objective-C的测试框架 ，它和用来做测试验证的Nimble捆绑发布。它和rspec和jasmine具有相同的语法风格。
- Security Monkey (github.com/Netflix/
  security_monkey)是 Netflix Simian Army 工具系统中的一员，设计这套工具的初衷是为了确保系统是以有弹性的方式构建的。

## 语言和框架

- Nancy (nancyfx.org) 。围绕着小而垂直切片的架构和微服务，需要的仅仅是这样轻量级的部署选项和不拘小节的工具。

- React.js(facebook.github.io/react) 是一个 UI/View 框架。

- Spring Boot 
- ember.js
- Flight.js（flightjs.github.io），Flight是个用于构建组件的轻量级框架.

- Swift（developer.apple.com/swift）表现出非常好的前景。