# vol 17

## 最新动态

- 崛起的中国开源软件市场

- 容器编排的首选：k8s

- 成为新常态的云技术

- 各方对区块链的信任稳步增

  区块链解决了“分布式信任”与“共享且不可篡改的账本”这些古老的问题。

  

## 技术

- 轻量级架构决策记录：捕获重要的架构决策及其上下文和结果。建议版本化这些信息，而不是wiki。这样记录的内容就可以和代码保持同步。
- 将产品管理思维应用于内部平台

- 架构师能够验证并维持一套自动化的可持续的架构标准，这是演进式架构的关键。
- [Eric Evans’s](http://dddcommunity.org/strategic-design)的自治气泡模式，为新应用程序的开发工作创建一个全新的环境，以避免遗留系统的纠缠。

- Netflix的（Chaos Monkey）混沌猴可以随机终止生产系统中的运行实例，并对结果进行度量，从而帮助验证系统在运行时对生产中断的应对能力。新的术语：混沌工程。更多参阅：[混沌工程原则](http://principlesofchaos.org/) 。
- DesignOps可以帮助组织不断地重新设计产品，而又无需在质量、服务连贯性和团队的自主性上妥协。DesignOps提倡创建并演进设计的基础
  设施，最大限度降低创造新的UI概念及其变体的工作量。典型代表：storybook。
- 前端单体：一个单一，庞大和杂乱无绪的浏览器应用。首选的（经过验证的）方法是将基于浏览器的代码拆分成微前端。

- CI和CD工具可以用来测试服务配置（如Chef的 cookbook，Puppet的模块，Ansible的playbook）、服务镜像构建（如Packer）、环境准备（Terraform，CloudFormation等）以及环境的集成。
- **基础设施即代码使用流水线**可以让错误在进入运维环境，包括开发和测试环境之前就被发现。
- 无服务架构不是银弹。一些特别的需求，必须做好能回退至容器化，
  甚至是实例化部署的准备。与此同时，无服务器架构的其他组件，比如后端即服务（Backend as a Service），几乎成为了默认的选择。

- 测试驱动开发，与自动化脚本来构建容器。将这两者结合，就是通过测试来驱动容器脚本的编写。借助[ServerSpec](http://serverspec.org/)和[Goss](https://github.com/aelsabbahy/goss)这样的框架，可以为独立的或编排的容器呈现预期的功能，并得到快速反馈。

- Splunk，Prometheus或ELK堆栈这样的工具让数据存储和后续处理变得更容易，从而获得运营洞见。
- 尽管基于算法的IT运维（Algorithmic IT Operations）有过度炒作的风险。但毫无疑问，机器学习算法的稳步提升将改变未来的数据中心运营中人类所起到的作用。

- Ethereum在去中心化应用

- 随着事件流（event streaming）平台（如Apache Kafka）的兴起，很多人将它们视为消息队列的高级形态，仅用来传输事件。事件流具有传统消息队列无法比拟的优势。令人更感兴趣的是，如何通过把平台（特别是Kafka）作为主要存储，把数据保存为不可变事件，从而**将事件流作为正确数据之源**。例如，以Event Sourcing方式设计的服务，可以使用Kafka作为事件存储工具（event store），其他服务可以消费这些事件。这一技术能够减少本地持久化和集成之间的重复工作。

- 多云策略则专注于使用每个云能提供的最好的服务。

- 服务啮合(SERVICE MESH)在服务发现、安全、跟踪、监控与故障处理方面提供了一致性，且不需要像API网关或ESB这样的共享资产。随着linkerd和Istio等开源项目的成熟，服务啮合的实现将更加容易。

- [3Rs企业安全](https://builttoadapt.io/the-three-r-s-of-enterprise-security-rotate-repave-and-repair-f64f6d6ba29d)：轮换、修复、重建，利用基础设施自动化和持续交付来消除攻击机会。

- 一些组织没有将Kafka的生态组件（如连接器、流处理器等）按产品和服务团队划分，而是将它们集中管制，这用KAFKA重现了ESB反模式。



## 平台

- k8s持续领跑
- .NET CORE

- 随着[Gatling](https://www.thoughtworks.com/radar/tools/gatling)和[Locust](https://www.thoughtworks.com/radar/tools/locust)等工具的日益成熟，压力测试变得越来越容易。[FLOOD IO](https://flood.io/)是一个基于SaaS的压力测试服务。它可以用来向数百台云端
  服务器分发测试脚本并在其上执行。

- GCP在可用地理区域和服务成熟度的扩展。竞争对手是 Amazon Web Services。

- [KEYCLOAK](http://www.keycloak.org/)是一个开源的身份和访问管理解决方案。它提供了单点登录、社交网络登录和一些开箱即用的标准协议——如OpenID Connect、OAuth 2.0 和 SAML。**由于在初始化和运行时需要通过 API 对其进行配置，因而必须编写脚本以确保部署是可重复的**。

- 超越游戏的UNITY，推出的针对iOS平台的ARKit 和针对安卓平台的 ARCore。
- 常与WhatsApp被相提并论的微信，在中国正在成为名副其实的商业平台。
- 尽管 Kubernetes 已成为容器编排工具的主角，但 Service Fabric 可以作为 .NET 应用程序的首选。

- CLOUD SPANNER是一个完全托管的关系型数据库服务。提供高可用性和强大的一致性的同时又不会对延迟做出妥协。

- COSMOS DB是微软于年初发布的全球分布式与多模型数据库服务。
- Google所收购的DIALOGFLOW（原名为API.ai），是一种”自然语言理解即服务”的平台。它在该领域中与Facebook的wit.ai以及Amazon Lex等平台展开了竞争。

- GKE（Google Container Engine）是一个托管的 Kubernetes解决方案，用来部署容器化应用程序。

- KAFKA STREAMS是一个用于构建流式应用的轻量级库。设计目标在于简化流式处理，让它像为异步服务设计的主流应用编程模型一样易于访问。在Kafka生产者端引入幂等性，并且使用新的事务API跨多个分区实现原子写入。
- 微软从他们的 OmniSharp 和 TypeScript 服务器项目中，提炼并引领了语言服务器协议（Language Server Protocol, LSP）的拟定。编辑器只要使用LSP 协议就可用于任何具备 LSP 兼容服务器的编程语言的开发。

- LORAWAN是一种低功耗广域网，专为低功耗、远距离和低比特率的通信场景而设计

- MAPD是一个支持SQL的运行在GPU 上的内存列式分析性数据库。我们对于数据库负载到底是I/O密集所致还是计算密集所致有过争论。不过GPU的并行能力，结合 VRAM 的充足带宽，在某些场景下非常有用。
- NETLIFY正是这样一个工具。用它可以创建静态网站内容，提交到GitHub，然后网站就能快速、轻松地上线可用了。
- 随着机器学习从试验性使用转向生产环境，需要一种可靠的方式来托管和部署这些可远程访问的模型，并能随着消费者数量的增加而进行扩展。TENSORFLOW SERVING通过将远程gRPC接口暴露给一个被导出来的模型，解决了上述部分问题。

- 凭借WINDOWS CONTAINERS，微软正在容器化的道路上奋起直追

- 过度庞大的API网关产品

## 工具

- 托管的 CI / CD工具——BUILDKITE

- CIRCLECI是一个持续集成引擎，既可以在SaaS云服务上使用，又可以私有部署使用。

- GOPASS是一个基于GPG和Git的团队密码管理解决方案。它的前身是pass，并在此基础上增加了诸如多用户密码管理、层级式密码存储、交互式查找、基于时间的一次性密码（TOTP）,以及二进制存储格式等功能。
- 针对前端测试的HEADLESS CHROME ，在此之前，这属于 PhantomJS 的地盘。但Headless Chrome正在迅速取代那种用 JavaScript 驱动 WebKit 引擎的方法。

- 高性能 JSON 编码/解码工具，源库JSONITER。
- PROMETHEUS主要支持基于“拉动”的HTTP模型，同时也能支持告警，这令其能够成为运维工具箱中经常得到使用的工具。对于Prometheus
  用户来说，Grafana已成为首选的仪表板可视化工具。，Prometheus在索引和搜索能力上能够作为ElasticStack很好的补充。

- APEX是一个能够轻松构建、部署和管理AWS Lambda 函数的工具

- ASSERTJ-SWAGGER 是一个 AssertJ 工具库，能够用来验证API的实现是否符合其契约规格。当 API 端点的实现发生了更改但未更新其 Swagger 规格，或未能发布更新后的文档时，就能通过使用 assertj-swagger 来捕获这些问题。
- CYPRESS能帮助开发人员轻易地构建端到端自动化测试，并且把测试的步骤录制在一个 MP4 文件里。开发者可以通过查看测试视频来修复测试，而不是在headless模式下去重现问题。
- FLOW是一个针对 Javascript 的静态类型检查工具，可以为整个代码库逐步增加类型检查。不同于通过定义另一种语言来实现静态类型检查的 Typescript 语言，Flow 可以被逐步添加到支持 ECMAScript 第5、第6 以及 第7版的已有Javascript 代码库中。

- 分析笔记本应用（analytics notebooks）的流行度在持续上升。python生态的JUPYTER持续领跑。
- Kong是一个由Mashape公司搭建和支持的开源API网关。Kong 所基于的 OpenResty通过其Nginx模块和用于扩展的Lua插件，为其强大和高效的功能奠定了基础。KONG API 网关只拥有更少的功能，但实现了关键的API网关功能，如流量控制、安全性、日志记录、监控和身份验证。
- [KOPS](https://github.com/kubernetes/kops)是用于在生产环境上创建和管理高可用性Kubernetes集群的命令行工具。
- 谷歌编写的LIGHTHOUSE工具可用于评估Web应用程序是否遵守Progressive Web App标准。
- RENDERTRON，即 Chrome的headless 渲染解决方案。它在一个 Docker 容器中封装了一个 headless 的 Chrome 实例，可以作为独立的HTTP服务器来部署。无法渲染JavaScript的爬虫机器人可以被路由到此服务器来进行渲染。

- SONOBUOY是一个以非破坏性的方式在任何 Kubernetes 群集上运行端到端“合规性测试”的诊断工具。

- 如果用Spring框架来实现Java服务，可以考虑用[SPRING CLOUD CONTRACT](http://cloud.spring.io/spring-cloud-contract/)来进行消费者驱动的契约测试。目前，该工具的生态系统支持根据契约来验证客户端的调用以及服务器端的实现。与Pact （一个开源的消费者驱动契约测试工具集）相比，它不支持契约代理，也不支持其它编程语言。不过它能与Spring生态系统完美集成，比如使用Spring Integration进行消息路由。

## 语言和框架

- Angular 移到了“试验”阶段，然而我们仍然会更倾向于选择React, Vue 或者Ember。

- ASSERTJ是一个提供流式断言接口的Java库，很容易在测试代码中传达测试的意图。一些团队默认选择使用它，而不是JUnit和Hamcrest组合。
- Flexbox提供了一维（one-dimensional）布局，CSS网格布局（CSS Grid Layout）提供了二维（two-dimensional）的基于网格的布局。

- 大多数大型CSS代码库都需要复杂的命名机制来避免全局命名空间中的冲突。CSS MODULES通过为每个CSS文件中的所有class创建局部作用域来解决这些问题。当这个文件被导入到一个JavaScript模块，其中的CSS class可以通过名称字符串来引用。然后，在构建工具（Webpack，Browserify 等）中，class名称被替换为自动生成的全局唯一字符串。

- Jest是一个“零配置”的前端测试工具，具有模拟和代码覆盖之类的开箱即用特性，主要用于React和其他JavaScript框架。
- 迅速发展的KOTLIN语言。在使用[Anko](https://github.com/Kotlin/anko)库开发Android应用时，已经尝到了空指针安全、数据类和易于构建DSL的甜头。

- 在spring-cloud-streams项目中，对Kafka Streams绑定的支持让采用Kafka和RabbitMQ通过连接器构建消息驱动的应用变得相对容易。
- ANDROID架构组件的发布

- 在移动增强现实中看到了很多令人激动的活动，其中大部分来自ARKIT（Apple提供的原生AR库）/ARCORE（Google提供的原生AR库）的加持。
- ATLAS和BEEHIVE是分别用于Android和iOS的模块化解决方案。
  它们能让多个团队工作于物理隔离的不同模块，并且从一个门面应用中重新组装或者动态加载这些模块。

- 比起追求“业务人员可以直接编辑业务规则”的错觉，Clara Rules 能够很好地驱动业务专家和开发人员之间的合作。
- CSS IN JS是一种用JavaScript编写CSS样式的技术，通过鼓励采用一种通用模式，编写样式以及应用样式的JavaScript组件，使样式和逻辑的关注点得到统一。诸如JSS，emotion和styled-components，依靠工具来将CSS-in-JS代码转化成独立的CSS样式表，从而适合在浏览器里运行。

- DIGDAG是一个在云中构建、运行、调度和监控复杂数据管道的工具。

- DRUID是一个具有丰富的监控特性的JDBC连接池。这是一个阿里巴巴开源的项目。

- ECHARTS是一个轻量级的图表库，对不同类型的图表和交互有丰富的支持。ECharts完全基于Canvas API。

- GOBOT是一个用于机器人、物理计算和物联网(IoT)的框架，它基于Go语言编写，并且支持多个平台。
- 一款可以检测Android和Java中令人讨厌的内存泄漏工具LEAKCANARY。
- PYTORCH是Lua机器学习框架Torch在Python语言下的完整重写版。比起Tensorflow，它还很新不够成熟，但在程序员的眼里它却很好用。
- SINGLE-SPA是一个JavaScript元框架，允许我们使用不同的框架构建微前端，而这些框架可以共存于单个应用中。一般不推荐在SPA中使用多个框架。。鉴于很多JavaScript框架都昙花一现，我们需要一个解决方案来应对未来框架的变化，以及在不影响整个应用的前提下进行局部尝试。
- 智能合约编程需要一种比交易处理脚本更具表现力的语言。Solidity是
  Ethereum平台的首选编程语言。
- TENSORFLOW MOBILE使开发人员可以将各种理解和分类技术融入其iOS或Android应用程序。

- [TRUFFLE](http://truffleframework.com/)是一个开发框架。它将现代化的 Web 开发体验带到了Ethereum平台。

- WEEX是一套跨平台移动应用开发方案，采用了Vue.js的组件化语法。

  