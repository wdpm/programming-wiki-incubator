# vol 13

## 最新动态

- docker引爆容器生态

  把docker作为开发工具来管理依赖，云平台把容器作为管理单元

- 微服务受到追捧

  容器化devops，CI/CD工具的学习与教训

- js工具趋于平稳

  探索构建工具和包管理工具之间的最佳组合

- 安全是每个人的问题

  bug bounties，安全建模等。



## 技术

- 解耦部署和发布
- NoPSD运动，尽可能轻量的工具按需设计。
- 软件是一个可以持续演进的产品
- 威胁建模
- BEM的CSS命名规范
- 为不同的客户端设计不同的后台服务。设计一个可复用并且支持各个客户端的API。
- 使用docker来进行构建
- 事件风暴是快速建模的一种方式。
- Flux 与 React.js
- 幂等过滤器
- 使用NPM的简化方案
- **凤凰环境**能够支持测试、开发、UAT和灾难恢复所需的新环境准备
- **产品环境下的QA**只对那些已经执行并有一定程度持续交付实践的组织有价值
- **不变性**使得代码更容易编写、阅读和理解。“只积累”的数据存储。
- 数据湖泊是一种不可变的数据存储。这样的例子包括 HDFS，或者基于 Hadoop，Spark 或 Storm 处理框架之内的 HBase
- 托管IDE
- 不变量监控：对预期正常范围监控的补充
- 团队应该克制他们的互联网级规模的情结而倾向于简单可行的解决方案。
- 继续推荐精益方法，包括实验和持续改进实践的整合。



## 平台

- 双因子认证，和TOTP
- Mesos抽象出底层的计算资源。Apple 的 Siri 重新架构于 Mesos 之上
- Fastly，作为CDN市场中的一员。
- H2O 是一套非常有意思的开源工具包，用于预测分析
- 超文本传输协议严格传输安全策略(HSTS)是目前广泛被支持的策略
- 弹性容器服务(ECS)是AWS进军多主机Docker容器市场的切入点
- Ceph是一款可以运行在普通服务器集群上的用作对象存储，块存储和文件系统的存储平台
- k8s 和 Rancher 容器编排
- Mesosphere DCOS(数据中心操作系统)是建立在Mesos内核之上的统一资源调度平台

- 微软的 Nano server 400MB
- Presto 是一个开源的分布式SQL查询引擎，为运行交互式分析负载进行设计和优化。支持包括Hive，Cassandra，MySQL和PostgreSQL在内的各种数据源。
- “雄心勃勃”的API网关



## 工具

- Browsersync是一个免费的开源工具，能够通过同步多个移动设备或桌面浏览器上的手工浏览器测试来极大的降低跨浏览器测试的代价

- iOS以及OS X项目的依赖管理主要有两种方式：完全手工管理或使用CocoaPods实现全自动化管理。通过使用Carthage，一个新的中间地带成为了可能。

-  DockerToolbox取代了boot2docker

- Polly for .Net

- Sensu 允许一个实例把自己注册为一个特定的角色，然后在这个基础上监控它

- 作为一款开源的Linux系统诊断命令行工具，SysDig具有一些非常强大的功能。

- 限制雪花构建服务器。雪花服务器类似于宠物。它们是手工管理的服务器，经常更新和调整到位，从而形成独特的环境。 Phoenix服务器与牛类似。它们是始终从头开始构建的服务器，并且易于通过自动化过程重新创建（或“从灰烬中升起”）。

  不可变的基础设施几乎完全由牛或凤凰服务器制成，而可变基础设施允许一些（或许多）宠物或雪花服务器。

- Espresso 是一款安卓系统功能测试工具。

- Gauge是一个轻量级的跨平台的测试自动化工具。技术规格都由形式自由的Markdown写成。
- 采用HTTPS的最主要阻碍是申请证书的流程。Let’s Encrypt 是一个新的证书权威机构，免费提供证书，提供极其易用的命令行API。

- Pageify是一个Ruby库，用于为针对UI的自动化测试构建页面描述对象。

- SoundCloud 近日开源了监测和报警工具包，Prometheus。

- 强调一下RESTfulAPI建模语言(RAML)，与Swagger相比，更为轻量级的RAML将关注点由如何文档化现有的API移到了如何设计API上。

- Sleepy Puppy是Netflix公司近期开源的一款盲打XSS收集框架。
- Visual Studio Code是微软出品的一款免费的IDE编辑器
- Citrix远程桌面来开发



## 语言和框架

- ECMAScript 6标准

-  Swift成为开发苹果生态系统时的默认选择。

- Mustache或FreeMarker等模板框架都把代码和标记混杂在同一个文件中以实现复杂的动态内容。Enlive是基于Clojure的模板框架。

- 需要构建一个基于.Net的WebSockets服务器，可以使用SignalR。

- Spring Boot。DropWizard或者 Spark这种微框架。

- 构建于JVM平台的Axon框架

- Haskell，现在以Frege的形式出现在JVM平台
- HyperResource是一个构建RESTful API客户端的Ruby框架，推荐它的原因在于它**在Richardson REST成熟度模型中处于第三层**，具有更好的服务发现性（discoverability）和协议的自描述性（self-documenting）

- Material UI 为实现了谷歌的Material Design 语言的 React应用程序提供了可复用的组件。它填补了一个类似于 TwitterBootstrap 的一个空白区域。Elemental UI 也是值得研究的替代选择。
- OkHttp是来自Square的一个Java HTTP连接库。可作为Apache HttpClient的直接替代品。
- Facebook的React Native将React.js的开发模型引入给了IOS和安卓的开发
  者。React Native程序使用JavaScript语言开发，但是并不像其他的混合式开发框架一样（例如lonic），React Native给予了开发者在目标平台调用原生UI组件的能力。

- 基于微服务架构的系统要求我们更深刻地思考如何处理故障隔离和测试。**TLA+**是一种正式的语言规范，可以被用在这两种场景中。https://tlaplus.codeplex.com/

- Traveling Ruby 使得发布可移植的、开箱即用的，平台无关的 Ruby 二进制文件变成可能。