# vol 14

> APRIL ‘16

## 最新动态

- 开源软件，进入良性循坏的副产品

- PASS解惑

  现在的PaaS并不是最终态，它只是进化之路上的一个阶段。企业向
  云和PaaS迁移带来了很多好处，但同时也面临着许多困难和挑战，特别是在整体流水线设计和工具使用方面。

- Docker！ Docker！Docker！

  围绕Docker的生态圈的形成，使得Docker的应用已经超出了开发/测试环境而进入了生产环境。

  Docker容器已经被用作许多PaaS平台上的“伸缩单元”以及“数据中心OS”平台。

- 过度响应式

  过度使用基于事件的系统，会导致程序逻辑变得复杂，也使响应式编程变得难以理解。应该深入理解，并权衡利弊。



## 技术

- 威胁建模
- Bug bounties识别漏洞风险
- 把数据湖泊的使用只限制在报表、分析、或给其他数据集市提供数据方面。
- 响应式架构（reactive architectures）得到更多的采纳和应用。再次重申：基于异步消息传输的架构会增加架构的复杂度，系统也会变得更加难以理解。

- CSP内容安全策略。缓解XSS危害。
- OWASP’s ASVS（应用程序安全标准）

- **无服务器架构**致力于使用按需调用的短租计算资源代替一直运
  行着的虚拟机。在外部请求来到时才获取计算资源，而当请求
  结束后计算资源立即被释放。典型代表：Firebase和AWS Lambda。

  这个架构可以是一个JavaScript应用，它需要的静态资
  源放置在CDN或者S3上，动态的AJAX调用则由API Gateway或
  Lambda负责处理。虽然无服务器架构拥有显著的优点，它也
  有一些缺点：部署、管理和在服务间共享代码都会变得更加复
  杂，并且在本地或离线状态下的测试工作会变得更加困难。

- Unikernel是精简专属的库操作系统，它能够使用高级语言编译并直接运行在商用云平台虚拟机管理程序。

- 虚拟现实。至少有两款面向消费者的头戴式设备HTC Vive和Facebook
  公司的Oculus Rift投入市场。

- 建议各个团队分布式地管理自己独立的CI，这样企业的决策就不会依赖于单个CI实例，但前提是要定义对于它们的选择和配置的标准。

- 一个惊人的趋势Big Data envy。



## 平台

- 激动人心的docker。在运维方面，Docker对监控工具（Sensu，Prometheus，CAdvisor 等），编排工具（Kubernetes，Marathon等）以及自动化部署工具的支持反映了平台的日益成熟，也同时做好了用于生产环境的准备。

- 使用AWS Lambda并开始使用它试验无服务器架构。建议暂时使用Javascript或是Python进行相关的工作。

- Kubernetes正是Google为解决这类问题而推出的容器集群管理框架。

- LXC和Docker容器已经成为默认的Appamor配置。

- Pivotal Cloud Foundry仍然因其丰富的功能和良好的生态圈给我们留下了深刻的印象。

- 新兴的容器即服务（CaaS）十分活跃，提供了介于基础的IaaS（基础设施即服务）和教条式的PaaS（平台即服务）之间的新选择。Rancher和k8s依旧闪耀。

- Amazon API网关服务足够轻量级

- Bluetooth LE（低功耗蓝牙）的能耗比wifi更低
- Deflect是一款开源服务，帮助非政府组织（NGO）、行动主义者和独立媒体公司免于受到分布式拒绝服务攻击（DDoS）。与商业CDN类似，它**基于分布式反向代理缓存，隐藏真实服务器IP地址**。
- ESP8266WIFI微控制器受到硬件黑客的喜爱

- MemSQL是一个内存数据库，支持基于集群的纵向扩展，并提供基于SQL的查询语言。
- 用于云管理和调度的Nomad。Nomad主要卖点在于（但不限于）容器化的负载和多数据中心/地区的部署

- Realm是一款为移动设备设计的数据库，它通过自身的持久化引擎获得高性能。Realm的市场定位为SQLite和Core Data的替代者。

- 开源的能够自托管的Sandstorm成为了一个不错的选择。特别有意思的是其隔离方案，容器化思想应用到了每个文档，而不仅仅是每个应用。

- 谷歌的TensorFlow是一个可用于各种从研究到生产的开源机器学习平台。
- 将Consul——支持基于DNS和HTTP发现机制的服务发现工具——移动到了“采用”区域。它超越了其他的服务发现工具，为注册的服务提供了可定制的健康检查，并且确保不健康的实例能被相应的标记。

- 积极关注新的能够大规模捕获信息作为不变的事件序列的数据架构。开源消息框架Apache Kafka持续发力，为大量独立的、轻量级的消费端发布有序的事件提供了解决方案。
- Gauge是一个轻量级的跨平台测试自动化工具。技术规格由自由的Markdown语法写成，因此，测试用例可以用业务语言而不是使用通常的 ‘given-when-then’ 这种具有局限性的格式来描述。

- Let's Encrypt为那些寻求网站安全的用户提供了一种简单的方式获
  取和管理证书。

- Load Impact是一款基于SaaS平台的负载测试工具。这款工具
  可以模拟多达120万并发用户的访问量。此外，还有BlazeMeter。

- OWASP Dependency-check是一款自动识别代码中潜在安全问题的工
  具，可以检查代码中是否存在任何已知的漏洞，并持续更新现有的漏洞数据库。

- 自动化配置测试技术，其中Serverspec是一个实现这类测试的流行工具。

- Webpack与browserify。
- 在2015年中旬Zipkin迁移到了Github的openzipkin/zipkin组织。端到端地跟踪不同请求的延迟，Zipkin仍然是一个非常不错的选择。

- Apache Flink是新一代的可伸缩、分布式批处理和流处理平台。其核心是一个数据流引擎。事件时间、丰富的流窗口操作、容错能力以及一次性（exactly-once）语义。
- Gitrob可以帮助把暴露保密信息带来的破坏降到最低。它可以扫描一个组织的GitHub代码库，把所有可能包含敏感信息的，不应该被放在代码库的文件标识出来。

- Grasp这个小的JavaScript的命令行重构工具让人印象深刻。将JavaScript做为一等编程语言的运动。

- 在例如微服务这样拥有多个应用的环境或者微容器的环境中，之前通过文件或者环境变量存储机密信息的方式已经变得难以控制。HashiCorp Vault试图通过使用统一接口安全地访问机密信息来解决这个问题。

- 试图了解不同数据库和队列技术在不同场景下如何反应时，可以把[Jepsen](https://github.com/aphyr/jepsen)及其[博客](https://aphyr.com/tags/Jepsen)作为参考。使用这种在事务中包含客户端的方法来测试，给许多构建微服务的团队揭示了可能的错误模式。

- LambdaCD让团队能够使用Clojure语言定义持续交付流水线。

- APM领域的开源工具Pinpoint是一个值得研究的工具，可以使用它作为AppDynamics和Dynatrace的替代品

- Pitest是基于突变测试（mutation testing）技术的Java测试覆
  盖率分析工具。

- 使用机器人对Web资产的攻击正变得越来越复杂。Repsheet项目的目标正是识别这些攻击者和它们的行为。

- 对于软件开发来说，像CruiseControl和Jenkins这样的持续集成工
  具是非常有价值的，但是当你的构建流程变得更加复杂时，需要的就不仅是持续集成了，而是需要一个部署流水线。

  几个拥抱部署流水线的产品，包括ConcourseCI，LambdaCD，Spinnaker，Drone，以及GoCD。



## 语言和框架

- React.js 脱颖而出

- Spring Boot
- Swift现在已经是Apple生态圈中的首选开发语言

- Butterknife是为视图注入绑定字段和方法的类库。允许注入任意对象、视图和监听器，从而减少Android开发中的胶水代码保持代码整洁。

- 随着对Android应用需求的增加，Dagger提供了全静态、编译
  时依赖注入的框架。Dagger严格生成的实现和不依赖反射的方案解决了很多性能和开发问题。

- Dapper是基于.NET的极简轻量级ORM框架。

- Ember.js基于项目实践经验进一步开发支持，成为Javascript 应用框架领域的强力竞争者。正在从JQuery 或者原始的XHR转为使用Fetch API和Fetch polyfill技术来进行JavaScript远程调用。

- React Native在跨平台移动快速开发领域获得了持续的成功。

- Redux是一个强大成熟的工具，管理客户端应用程序状态。它使用Flux-style实现了一个松耦合的更容易推断的状态机架构。

- 在安卓应用开发的世界，Robolectric是一个单元测试框架。为我们提供了最佳选项来通过扩展或是直接使用安卓组件编写真正的单元测试，同时支持junit测试

- Alamofire是一个非常健壮的对程序员友好的处理JSON解码的类库。它是由Objective-C中类似的库AFNetworking的作者创建，AFNetworking曾经在Objective-C的时代被大量使用。

- 将Angular移回“评估”。React.js和Ember也有很不错的可选性。同时使用双向绑定与不一致状态管理模式会让代码变得过于复杂。
- Aurelia的作者Rob Eisenberg是Durandal之父，离开Angular2.0核心
  团队之后全力打造了**Aurelia**，被认为是下一代JavaScript客户端开发框架。

- Cylon.js这个Javascript库能够为机器人和物联网构建接口。
- Elixir是一款建立在Erlang虚拟机之上的动态编程语言。

- 作为Redux设计来源的Elm用一种编译型，强类型的函数式语言的方式来实现了React.js的组件化视图与响应式，同时拥有Redux的可预
  测化状态。Elm本身用Haskell编写，因此语法与Haskell相似。

- GraphQL做为一种远程接收对象图网络的协议，在近期获得了非常高的关注。响**应体的结构不取决于服务端，而是完全取决于客户端**。它将消费者解耦，并且强迫服务端遵守Postel法则。

- Facebook 的 Relay是一个支持到React.js无状态组件模型的Javascript框架。
- Immutable.js是一个提供很多永久不可变数据结构的JavaScript工具库，这样的数据结构在JavaScript虚拟机中非常高效。

- Recharts将D3图表库以很简洁和声明式的方式整合到React.js。

- 许多iOS开发人员使用[JSPatch](https://github.com/bang590/JSPatch)动态地给app打补丁。当一个启用了JSPatch的app运行时，程序会先加载一个JavaScript块（可能通过不安全的HTTP连接），然后与Objective-C应用代码通信建立连接，从而改变代码行为、修改bug。这样很方便，但是在APP中留一个monkey patch并不是一件好事，也不应该这样做。

  另外一种方式是使用React Native构建APP，然后使用AppHub或者CodePush来推送小的更新和功能。