# vol 18

## 最新动态

- 浏览器增强，服务端式微

  WebAssembly的引入为web应用创建逻辑提供了新的语言选择，同时把处理过程更加推向金属侧（以及GPU）。越来越多像 CSS Grid Layout和CSS Modules这样的开发标准正在替换掉自定义的库。对
  更好用户体验的追求，正在持续地把功能装进浏览器里。

- 不断蔓延的云环境复杂性

  容器即服务（CaaS）

- 信任团队，但要验证

  理念正在由“永远不信任域外所有东西”以及“从不验证域内任何东西”转变为“信任但是需要验证域内外任何东西”——也就是可以假设和系统其它部分有本意良好的互动，但一定要在本地验证这份信任。类似Scout2的工具以及 BeyondCorp 这样的技术反映了关于信任更成熟的视。

- 物联网的发展

  物联网（IoT）生态系统持续稳步发展，关键成功因素包括安全和成熟的工程实践。

## 技术

- DOMAIN-SCOPED EVENT将在其发布的同一个领域内被消费，因此我们期望消费者能够访问特定的上下文、资料或引用，进而对事件进行处理。
- 身份管理。HOSTED IDENTITY MANAGEMENT AS A SERVICE （SaaS）。
- 组织们越来越习惯POLYCLOUD策略，不再把所有业务全“押在”一个服务供应商身上。

- 开发移动应用程序时，开发 HTTP 模拟器并将其编译到应用的二进制文件中进行测试——EMBEDDED MOBILE MOCKS，使得我们可以在断开连接的状态下测试移动应用程序，且无需外部依赖。该项技术可能需要根据移动应用程序使用的网络库以及对底层库的使用情况创建一个专属的库。

- GraphQL可以让客户端直接使用特定的查询语句去访问BFF以获取数
  据。

- 允许团队自己管理自己的配置，并使用INFRASTRUCTURE CONFIGURATIONS CANNER这种方式来确保配置的安全性。watcmen 和 cout2。

- JUPYTER FOR AUTOMATED TESTING。
- 通过跟踪请求头中传入的某个参数来LOG LEVEL PER REQUEST。使用跟踪框架（可能基于OpenTracing标准），你可以在一次事务中的多个服务之间传递一个相关的ID。

- SECURITY CHAOS ENGINEERING扩展了安全技术的范畴

- polycloud，使用多个云。避免供应商锁定。



## 平台

-  .NET Core是 .NET 应用开发的未来。

- Microsoft已经在稳健地改进AZURE。

- Headless CMS （Content Management Systems, 内容管理系统） 正在成为数字化平台的常见组件。[CONTENTFUL](http://www.contentful.com/)是一个现代化的 headless CMS。它有着“API 优先”的特点，及其CMS as Code的实现。它支持强大的内容建模原语代码和内容模型演化脚本，并允许将其视为其他数据存储的schema，并将演进式数据库设计实践应用到 CMS 开发中。

- [EMQ](http://emqtt.io)是一个可伸缩的开源多平台 MQTT代理。为了追求高性能，它使用Erlang/OTP语言编写，能处理数百万的并行连接。它能支持多种协议，包括MQTT、MQTT传感器网络、CoAP以及WebSockets，使其适用于物联网和移动设备。
- GKE （Google Kubernetes Engine）
- AWS FARGATE 最近进入了 “docker as a service”（docker 即服务）的领域

- 虽然我们依旧喜欢 Unity，但对GODOT这个该领域相对较新的工具也感到很兴奋。

- INTERLEDGER是一种连接不同帐本的协议。该协议使用连接器和加密机制（例如HTLC）在帐本之间路由安全支付的信息。
-  MONGOOSE OS为嵌入式软件开发人员弥补了一个引人关注的隔阂，即适合原型开发的 Arduino 固件与裸机微控制器原生 SDK 之间存在的隔阂

- TICK STACK是一个由开源组件组成的平台。使用它就可以轻松地收集、存储、绘制基于时间序列的数据（如度量和事件）来触发告警

- WEB BLUETOOTH能够直接从浏览器控制任意低功耗蓝牙设备

- 凭着能让Windows应用以容器的方式运行在基于Windows的环境中，WINDOWS CONTAINERS已经追赶上了容器世界的步伐

## 工具

- Appium是最受欢迎的移动端自动化测试框架之一。随着测试套件的扩大，在多个设备上并行运行测试的能力成了缩短反馈循环的关键。 APPIUM TEST DISTRIBUTION非常有效地解决了这个问题。

- 使用 BACKSTOPJS 来做 web 应用的可视化回归测试。作为可视化比较工具，它的可配置视窗和可调节容错能力可以很容易定位到细微差别

- 世界上有数不清的问题都可以用数学优化问题来表达，而其中可以用[凸问题](https://en.wikipedia.org/wiki/Convex_optimization)来描述的那部分常常能够得到有效解决。CVXPY便是一种针对凸优化问题所开发的开源Python嵌入式建模语言。

- HELM是Kubernetes的包管理器。共同定义某个应用的Kubernetes资源集合被打包成图表。这些图表可以描述单个资源，例如Redis pod，或者全栈的Web应用程序：HTTP服务器、数据库和缓存。Helm 默认带有一些精选的 Kubernetes应用，维护在官方的图表仓库里。想要为内部用途搭建私有的图表仓库也很容易。Helm有两个组件：一个是称为Helm的命令行工具，另一个是称为Tiller的集群组件。

- JUPYTER作为一个分析工具持续领跑。
- OpenResty通过 Nginx 模块，配以Lua作为扩展插件，Kong具备了强大和高性能的基础。

- [KOPS](http://github.com/kubernetes/kops)是一款用于创建和管理高可用生产环境Kubernetes集群的命令行工具。已经成为在AWS上管理Kubernetes 集群的首选工具，这不仅仅只是因为它快速增长的开源社区。它也可以在Google Cloud上安装、升级和管理 Kubernetes 集群。

- Patroni是一个基于 Python 的 PostgreSQL 控制器，利用分布式配置存储（例如etcd，ZooKeeper，Consul）管理PostgreSQL 集群的状态。同时它还支持流复制和同步复制模型，并提供了一组丰富的REST API，用于PostgreSQL集群的动态配置。

- 基于微服务的架构关键因素之一在于，服务是可以独立演进的。例如，当两个服务互相依赖时，对其中一个服务的测试通常会stub或mock另一个服务。我们知道[WIREMOCK](http://wiremock.org/)已经很久，但一直更倾向于使用[mountebank](https://thoughtworks.com/radar/tools/mountebank)。在过去的一年里，WireMock已迎头赶上，建议将其作为候选方案。

- 尽管npm（版本5）有最新的改进，但 Yarn 仍然是JavaScript包管理的首选工具。
- [ARCHUNIT](http://www.archunit.org/)是用来检查架构特征的Java测试库，比如包与类的依赖关系、注解验证、甚至层级一致性。它可以在你现有的测试方案中，以单元测试的方式运行，但目前只能用于Java架构。ArchUnit测试套件可以合并到CI（持续集成）环境或部署流水线，使我们很容易地以演进式架构的方式实现适应度函数。

- CFN-NAG工具可以对用于AWS的CloudFormation模板进行扫描，通过模式匹配的方式寻找可能不安全的基础设施。

- [CONDUIT](http://github.com/runconduit/conduit)是为Kubernetes打造的轻量级Service Mesh开源产品。Conduit 实践了 out-of-process 架构，数据层代理使用 Rust 语言实现，控制层则使用Go语言实现。

- DEPENDABOT提供了这种服务，它可以和你的Github代码仓库集成，并自动帮你检查项目依赖是否有最新的可用版本。 如果有需要，Dependabot 还可以创建Pull Request帮助你方便地对依赖进行升级。**通过使用CI服务器，你可以自动对升级后的依赖做兼容性测试，并将兼容的依赖升级自动合并到Master分支**。还有其他一些工具可供选择，比如针对JavaScript项目的Renovate。

- 在开发前端应用程序时，我们在之前的技术雷达中提到了Headless Chrome作为前段测试中PhantomJS的更好替代方案。

- NSP是一款命令行工具，用于识别Node.js应用程序中的依赖是否存在已知安全漏洞

- PARCEL是一款类似于Webpack或Browserify的应用打包器。我们在往期雷达中推荐过 Webpack，它现在仍然是款很好的工具。

- SCOUT2是一款用于AWS环境的安全审计工具。不必人工浏览所有网页，就可以获取到AWS环境的配置数据；它甚至还能生成一份攻击面报告。

- SENTRY是一款错误追踪工具，帮助实时监控并修复错误。像Sentry这样的错误追踪和管理工具，与类似ELKStack这种传统的日志解决方案有所不同，前者更关注发现、调查和修复错误

- API文档对消费者来说也至关重要，很多团队开始广泛使用Swagger。



## 语言和框架

- ASSERTJ是一个提供流式断言接口的Java库, 可以很容易在测试代码中表达测试的意图。

- ENZYME已经成为了测试React UI组件的事实标准。与其他基于快照的测试工具不同，Enzyme 可以进行无设备渲染的测试，速度更快，粒度更细。可以结合单元测试框架（如Jest）一起使用。

- KOTLIN的使用率得到了飞速增长，工具支持也突飞猛进。其流行的背后原因包括语法简洁、空指针安全、易于从Java迁移以及与其他JVM语言的互操作性。

- 一个GraphQL客户端 Apollo，作为React应用程序访问GraphQL数据的首选。[APOLLO](http://www.apollographql.com/client)项目也提供了服务端框架与 GraphQL 网关，但其
  客户端简化了 UI 组件与 GraphQL 后端数据绑定的问题。

- HYPERLEDGERCOMPOSER 构建于Fabric基础之上，加速了将想法实现为软件的过程。Composer 提供 DSLs 来建立业务资源模型、定义访问控制和构建业务网络。使用 Composer，可以在不搭建任何基础设施的情况下，仅通过浏览器来验证我们的想法。

- OPENZEPPELIN是一个能够帮助开发者用Solidity 语言构建安全智能合约的框架。

- FLUTTER是一个跨平台的框架，可以使用Dart语言编写原生 Mobile 应用。借助 Dart，它能够编译成原生代码，并直接和目标平台通讯，而不必借助桥接和上下文切换——这些可能导致框架中出现性能瓶颈，就像React Native或 Weex那样。Flutter的热重载（hot-reload）特性让人惊叹，它能在编码时为你提供超快的视觉反馈。

- [HYPERAPP](http://hyperapp.js.org/)因其极简的风格脱颖而出。 它占用的空间非常小（小于 1KB），但却涵盖了编写 Web 应用程序的所有基本功能。

- RASA是聊天机器人领域的新成员。 它并非使用简单的决策树，而是通过神经网络将用户意图和内部状态映射到回应上。Rasa 集成了自然语言处理解决方案（spaCy）。

- Reactor 实现了 Reactive Streams 规范，并且提供了两个不同的发布者API：Flux (0 到 N 个元素) 和 Mono （0 或 1 个元素），可以高
  效地对基于推送的流处理进行建模。Reactor 项目非常适合微服务架构，并且为HTTP、WebSockets、TCP和UDP等提供了支持背压（backpressure）的网络引擎。

- RIBS即路由器（Router）、交互器（Interactor）和构建器（Builder）的缩写， 是来自 Uber 的跨平台移动架构框架。RIBs的核心思想是将业务逻辑从视图树中分离出来，从而确保应用程序由业务逻辑驱动。

- SWIFTNIO与Netty类似，但却是用 Swift 编写。目前，它已经被 MacOS 和Ubuntu 所支持，并且实现了HTTP作为更高级别的协议。

- PyTorch，它是一种支持指令式编程风格的深度学习框架。 现在，通过在会话上下文之外执行建模语句，TENSORFLOW EAGER
  EXECUTION为TensorFlow实现了同样的指令式风格。

- TENSORFLOW LITE是TensorFlow Mobile的指定接班者。

- Troposphere 是一个Python 库，可以使用 Python 代码生成 JSON 格式的CloudFormation 描述。

- WEBASSEMBLY 将“浏览器作为代码执行环境”向前推进了一大步。它是一种二进制编译格式，几乎以原生速度跑在浏览器中，已经获得所有主流浏览器的支持并向后兼容。它拓展了编写前端功能的语言范围，早期集中在 C、C++和 Rust，并且也可以作为 LLVM 的编译目标。