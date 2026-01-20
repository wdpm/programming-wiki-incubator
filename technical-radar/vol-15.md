# vol 15

> NOV ‘16

## 最新动态

- 容器即进程，PaaS即机器，微服务架构即编程模式

  容器是一个独立进程，PaaS是一个公共部署目标，微服务架构作为一致的风格。

- 智能释放的力量

  通过框架进入到实用领域：如Nuance Mix和TensorFlow。

- 团队结构的全局影响

  商业公司倾向于产品思维高于项目运作；科技公司正在推行“谁构建，谁运行”玩法的自治团队。

  软件开发需解决的首要问题还是沟通。 全功能团队极利于改善传统组织的跨部门沟通，并减少人为产生的部门冲突。

- AR/VR渐入佳境

  创作和交付VR/AR内容应用的技能和能力，远远跟不上硬件发展的
  步伐，尤其是在企业应用领域

## 技术

- 消费者驱动的契约测试
- 流水线即代码。LambdaCD ，Drone，GoCD和Concourse等都是
  这一技术的应用案例。

- APIs当作产品
- 演进时架构。轻量级的架构决策记录是用来记录重要的架构决策、发生的上下文和对应产生的结果的一种技术。

- 无服务器架构是一种架构方法，使用即时请求、用后即销毁的
  短暂计算能力来取代长期运行的虚拟机。

- 即使已经使用了消费者驱动的契约测试——大部分的APIs都要支持无尽的活跃订阅端数目 —— 也许触及到了RESTful架构的极限。这些问题可以使用客户直接查询来解决。

-  容器docker安全检查。
- 微前端。在这种方法中，一个Web应用程序可以分解为页面和特性，每个特性由一个单独的团队从端到端对其负责。现存的很多种技术可以将这些应用特性组织在一起，从而提供一个统一内聚的用户体验。BFF - 后端服务前端的方法可以很好地应用。

- 通过模板化的URL、简单地暴露静态分层数据模型来开发一个服务，会导致实现出贫血REST。[《RESTAPI设计资源模型》](https://www.thoughtworks.com/insights/blog/rest-api-design-resource-modeling)中提供了更多关于如何有效地设计RESTAPI的建议。

- 避免大数据盲从。

- [Jepsen](https://thoughtworks.com/radar/tools/jepsen)能够在困难的情况下对数据库性能进行分析，并在各种NoSQL数据库中发现了大量 [bugs](https://aphyr.com/posts/284-call-me-maybe-mongodb) 。

- 重新强调云计算平移是应当避免的一个技术



## 平台

- HTTP严格安全传输（HSTS）

- 应用程序白名单已经被证明是减少网络入侵最有效的方法之一。

- Linux的安全模块。大多数Linux发行版已经内置SELinux或AppArmor这样的特性，像Grsecurity这些功能全面的工具也变得触手可及。

- 使用Apache Mesos管理分布式系统的集群资源

- Auth0提供的服务以其易于集成、广泛的协议和连接器支持，以及丰富的管理API尤其让人印象深刻

- Realm是一款为移动设备设计的数据库，它拥有高性能的持久化引擎，定位是SQLite和Core Data的替代。

- Unity已成为虚拟现实（VR）和增强现实（AR）应用开发平台的首选

- .NET Core是一款开源模块化产品，用于创建可部署到Windows、MacOS及Linux的应用程序
- Apache Flink是新一代批处理及流处理的分布式可扩展平台

- 亚马逊最近发布了AWS应用负载均衡器（ALB）

- Apache的Cassandra数据库是一个功能强大，可扩展的大数据解决方案，用于存储和处理海量数据，它的集群经常被分散部署在全球多个位置的数百个节点里。

- Electron是使用HTML，CSS和JavaScript等Web技术构建本地桌面客户端的实用框架。

- 区块链技术中，以太坊（Ethereum）取得了良好的进展，值得关注。

- 在HoloLens中，微软设计了第一个真正可用的AR头戴式设备

- IndiaStack是一组开放APIs，旨在将印度从过去的数据弱国转变为一个数据创新大国。

- Nuance Mix是Nuance公司推出的一个自然语言处理框架。

- 如果你打算开发一个VR应用程序和并尽可能多的适配目标设备，并且不想直接使用Unity或Unreal进行开发，那么OpenVR是目前最具体实用的解决方案

- Tarantool是通过对象实体实现数据库和缓存的开源NoSQL解决方案，并提供Lua语言编写应用程序逻辑的APIs。数据本身以
  MessagePack格式进行存储，并使用相同的协议在客户端和服
  务器之间进行通信。

- [wit.ai](https://wit.ai/)提供了一个SaaS平台，允许开发者使用自然语言处理（NLP）创建会话接口

## 工具

- Babel.js已经成为下一代JavaScript的默认解析器，得益于它重新组织的插件系统，围绕它的生态圈已经初步形成。

- 很多团队会利用工具， 如Graphite和Kibana，收集应用和业务特定的指标数据。Grafana可以轻松地从不同数据源中创建有用且优雅的监控面板。

- 镜像已成为现代部署流水线的主要部分，也有大量的工具和技
  术用于创建镜像。推荐[Packer](http://packer.io)。

- 针对测试金字塔塔尖的功能测试，越来越多的Android团队采
  用Espresso。它使用少量的核心API封装了大量复杂的内部实
  现，帮助实现简洁的测试代码，并快速和稳定地执行。

- fastlane是眼下常用的iOS和Android自动化构建工具，功能覆盖了移动应用从构建、测试、打包到发布整个流程。

- 对响应式页面在不同环境下的页面布局和样式的测试，基于Selenium实现的Galen语法简单，支持对不同屏幕尺寸的条件验证。

- JSONassert是一个很小的库，减少了处理JSON断言的代码，并提供了更友好的错误提示。

- [Pa11y](http://pa11y.org/)是一个自动化辅助功能测试工具，它通过命令行运行，
  可以与构建流水线集成。

- 在Terraform的助力下，可以仅仅通过一些声明式的定义，来管理云基础设施。通过Terrafrom生成基础设施配置后，通常会用Puppet、Chef或者Ansible这种配置部署工具去实施构建

- Android-x86是开源Android在x86平台下的实现。使用Android-x86替换模拟器进行独立的UI测试后显著的缩短了时间。

- axios，一个基于promises的 JavaScript HTTP客户端，并且认为比Fetch更好。

- Clojure.spec是Clojure内置的一个新特性，它允许开发人员将数据结构用类型和其他验证条件（例如允许的取值范围）进行封装。

- FBSnapshotTestCase能够自动化地获取用户界面组件的快照图片，将其存储并进行对比，从而可以让用户界面的完美程度精确到像素级别。

- Scikit-learn是一款日益流行的用Python语言编写的机器学习库。

  

## 语言和框架

- Ember.js
- Redux及其对更新状态的三个限制原则证明了自己在这个领域的价值

- 对于Elixir这门编程语言的兴趣正持续升温
- Immutable.js是一个的JavaScript工具库

- Phoenix是一个用Elixir写就的服务端web MVC框架

- 使用Quick和Nimble组合为IOS做单元测试

- ECMAScript 2017
- JuMP是使用Julia编程语言来进行数学优化的领域特定语言

- Rapidoid是一组web框架模块的集合，包括了一个基于Java NIO来全新实现的快速底层HTTP服务器。Rapidoid使用了堆外输入输出缓冲区、对象池和线程局部数据结构，令其比像Netty那样基于NIO的其他服务器更胜一筹。
- Redux范式以ReSwift的形式迈向了Swift领域。一旦应用状态及其改变被集中管理且标以通用名称，就会对代码的简单性和可读性带来很多真切的好处。
- Three.js
- Vue.js作为AngularJS的轻量级替代品占据了一席之地。

- WebRTC是一个浏览器之间进行实时通信的新兴标准，它使用常用的Web技术来支持视频。
- 建议抛弃Angular 1。