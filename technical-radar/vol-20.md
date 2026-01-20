# vol 20

## 最新动态

- 日新月异的数据形态

  十年前，数据指的是数据库。现在，数据形态有有NoSQL、时间序列、类似Spanner的SQL存储，聚合日志文件查询的时间流。

- 基础设施即代码

  docker以及k8s持续领跑

- Kotlin 方兴未艾

  蓬勃发展的kotlin生态

- 封装边界的泄漏

  随着多语言编程、基础设施即代码和一切皆服务技术的出现，我们不再需要将各种组件都组合到单一内聚的系统中。

  尽量避免将业务逻辑放于配置文件中，同时注意不要让编排系统主导系统。

## 技术

- （采纳）只需四个关键指标就能区分低绩效、中绩效和高绩效人员：前置时间、部署频率、平均修复时间(MTTR)和变更失败率。
- （采纳）微前端。和单体前端的抉择。
- （采纳）Opinionated and automated code formatting。对于JavaScript，Prettier
  一 直 在 争 取 我 们 的 一 票 支 持，类 似 的 还 有Python的Black，而且越来越多的语言开始内
  置这样的工具，比如：Golang，Elixir。此处的关键在于不要花费数小时来讨论要强制执行哪
  些规则，而是选择一个有态度的、最低可配置的自动工具，最好是把这个自动化过程配置到pre-commit hook中。
- （采纳）Polyglot programming。为工作选择正确的语言可以大幅提高生产效率。
- （采纳）Secrets as a service。将密码凭据与源代码分开管理，并且建议使用git-secrets和Talisman等工具，以免将密码凭据凭据存储在源代码中。
- （试验）Chaos Engineering 。使用混沌工程来改善并保证分布式系统的弹性。
- （试验）Container security scanning。容器漏洞的关注问题。
- （试验）Continuous delivery for machine learning (CD4ML)models。机 器 学 习 的 持 续 交 付 ( C D 4 M L )。机器学习模型的持续交付流水线有两个触发因素：(1) 模型结构的变动； (2) 训练与测试数据集的变动。要使其发挥作用，我们需要对数据集和模型源代码进行版本化。流水线通常包括用测试数据集来测试模型、按需使用H2O等工具来对模型作自动转换、以及将模型部署到生产环境以交付价值。
- （试验）Crypto shredding。密钥销毁，对于区块链这种不能销毁历史的系统而言，密钥销毁有助于保护让敏感数据不被泄露。
- （试验）Infrastructure configuration scanner。允许团队管理自己的配置，并使用基础设施配置扫描工具（Infrastructure configuration scanner），来确保配置的安全性和可靠性。有适用于AWS的prowler和适用于Kubernetes的kube-bench。
- （试验）Service mesh。它提供的跨功能能力无需共享API网关等资产或将很多依赖库纳入到每个服务中。一种典型的实施方法是，和每个服务的进程一起在一个单独的容器中部署轻量级反向代理进程（又称为Sidecar）。Sidecar会拦截每项服务的入站和出站流量，并且提供上述的跨功能能力。代表：Lstio或者Linkerd。
- （评估）Ethical OS。软件与道德约束的问题。
- （评估）Smart contracts。智能合约的不可变性。
- （评估）Transfer learning for NLP。在创建用于处理文本分类的系统时，NLP的迁移学习很有用。
- (评估）wardley mapping。在讨论组织内软件资产的演进时，绘制Wardley地图（Wardley mapping）是一个非常有趣的方法。
- （暂缓）Productionizing Jupyter Notebooks。建议对生产化Jupyter Notebooks保持谨慎，以克服机器学习在持续交付流程和自动化测试上的先天不足。

- （暂缓）Puncturing encapsulation with change data capture 。变更数据捕获(CDC)用于从系统中拉取数据库变更，并对该数据执行一些操作。其中最受欢迎的一种方式是，使用数据库的事务日志来识别变更，然后将这些变更直接发布到可由其他服务消费的事件总线中。该方法特别适用于将单体分解为微服务这样的情况，但用作微服务之间的一级集成时，这会导致封装性变弱，并将源服务的数据层泄露到事件契约中。之前谈论过领域范围事件和其他方法，这些方法强调了正确的领域事件建模的重要性。

- （暂缓）Release train。不推荐版本火车这种模式发布，容易引起赶工，质量降低。

- （暂缓）Templating in YAML。不要尝试在YAML模板化越走越深，YAML设计初就没有考虑到成为编程语言，它只是配置文件。如果需要强逻辑处理，推荐python。若没有API可用，则可以使用[Jsonnet](https://jsonnet.org/)之类的专用模板语言。



## 平台

- （采纳）Contentful。无头CMS，API优先，CMS即代码。支持强大的“内容建模原语即代码”和内容模型演化脚本，将演[进式数据库设计](http://martinfowler.com/articles/evodb.html)的实践应用于其中。

- （试验）AWS Fargate。它比AWSLambda更加强大，同时不用管理EC2实例或者Kubernetes集群。但是成本较高。

- （试验）EVM beyond Ethereum。以太坊虚拟机（EVM）和智能合约。

- （试验）InfluxDB。InfluxDB是时序数据库（TSDM）的理想选择。Influx 2.0 引入了Flux（一种查询和处理时序数据的脚本语言），承诺比InfluxQL更强大。
- （试验）Istio正逐渐成为将微服务生态系统付诸应用的标准基础设施。

- （试验）Kafka Streams。与Apache Spark和Alpakka Kafka等其他流处理平台不同，Kafka Streams非常适合不需要大规模分布和并行处理的场景。没有诸如集群调度程序等其他基础设施，它同样也可以工作。当必须严格按顺序处理数据且只能处理一次时，Kafka Streams尤其有用。KafkaStreams的一个特别用例，是构建change data capture（CDC) 。

- （试验）Nomad。尽管Kubernetes持续受到关注，但我们很喜欢Nomad的普适性。

- （评估）CloudEvents。云事件规范问题，具体能不能开花看发展情况。
- （评估）Cloudflare Workers。Cloudflare Workers采取了不同的方法来托管无服务器计算产品。

- （评估）Deno。N o d e . j s 发 明 者 Ryan Dahl编写 了 D e n o，旨在避免他所意识到的Node.js中所存在的缺陷。该平台提供严格的沙盒系统和内置依赖项与包管理功能，支持开箱即用的TypeScript。Deno使用Rust和V8构建而成。

- （评估）Hot Chocolate。GraphQL生态系统和社区正不断发展。Hot Chocolate是于.NET（包括新的core和原先的传统框架）的GraphQL服务器。

- （评估）Knative。Knative是基于Kubernetes的开源平台，用来运行FaaS工作负载。

- （评估）MinIO。凭借与S3兼容的API层，MinIO能够在多个云提供商中实现对象存储。

- （评估）Prophet消除了模型构建、维护和数据操作中一些单调乏味的工作，让人类分析师和专家能够专注于他们所擅长的领域。

- （评估）Quorum。Quorum是“企业版以太坊”，旨在提供网络许可、事务隐私以及更高的性能。
- （评估） SPIFFE。在为服务之间的端到端加密和双向认证提供开箱即用的解决方案方面，服务身份的SPIFFE标准化已成为重要一环。
- （评估）Tendermint。拜占庭容错(BFT)是加密货币和区块链系统的一个基本问题。在存在多个任意错误进程的情况下（其中还包括恶意欺诈），它要求整个系统针对单个数据值达成共识。

- （评估）TimescaleDB数据库支持快速写入，可实现对时间序列数据的优化查询。虽然尚未像InfluxDB一样功能齐全，但TimescaleDB的数据模型和查询功能可以作为备选方案。



## 工具

- （采纳）Cypress。关于Cypress、TestCafe和Puppeteer等“后Selenium”web UI测试工具的选择。借助Cypress可以很好地解决性能差、响应时间长、资源加载慢等常见问题。
- （采纳）Jupyter。用Jupyter notebooks进行数据分析和机器学习方面的探索和原型设计。
- （采纳）LocalStack。LocalStack帮助在本地测试云服务的功能。
- （采纳）Terraform。Terraform正在迅速成为通过声明式定义来创建和管理云基础设施的首选工具。经由Terraform实例化的那些服务器配置信息，通常会留给诸如Puppet、Chef或Ansible这样的工具来处理。
- （采纳）UI dev environments。可用的工具包括：Stor ybook、React Styleguidist、Compositor和MDX。这些工具既可以在组件库或设计系统的开发过程中单独使用，也可以将其嵌入到web应用程序中使用。通过使用这些工具，可以缩短UI反馈周期并改善UI工作的时间。
- （试验）AnyStatus。AnyStatus是一款轻量级Windows桌面应用，将不同来源的度量指标和事件汇总到一处。可以将该应用视为CCTray的加强版。
- （试验）AVA。AVA是Node.js的测试运行器。
- （试验）batect。解决排查“works on my machine”的问题。
- （试验）Elasticsearch LTR。LTR是指应用机器学习来对搜索引擎检索的文档进行排序。
- （试验）Helm。Helm是针对Kubernetes设计的一种包管理工具。
- （试验）InSpec。InSpec作为确保合规性与安全性问题的解决方案。
- （试验）Lottie。Lottie是适用于Android、iOS、web和Windows的库，用于解析通过BodyMovin导出的JSON格式的Adobe After Effects动画，并在移动端和web端本地渲染。
- （试验）Stolon。搭建高可用的PostgreSQL实例可能很棘手，它可以帮助加速PostgreSQL集群的设置。
- （试验）TestCafe。Cypress、TestCafe和Puppeteer等“后Selenium”web UI测试工具。
- （试验）Traefik是一款开源的反向代理及负载均衡器 。如 果 只 是 需 要 具 有 简 单 路 由 功 能 的 边缘代理，而非NGINX和HAProxy这样全功能的重量级产品，就可以考虑Traefik。
- （试验）Anka。如果要将DevOps工作流应用于iOS和macOS环境，该工具值得考虑。
- （试验）Cage。Cage使用Docker Compose v2配置文件格式，填补了Docker Compose的一些空白，如对多环境的支持，包括用于在本地开发机器上运行分布式应用的开发环境，用于运行集成测试的测试环境，以及生产环境。
- （评估）Cilium。iptables等传统Linux网络安全方法根据IP地址和TCP/UDP端口进行过滤。然而，在动态微服务环境中，这些IP地址经常变动。利用Linux中的[eBPF](http://www.brendangregg.com/ebpf.html)框架，Cilium通过以基于服务，pod或容器认证的方式插入安全可见性来提供具备API感知的网络和安全性，而不是IP地址识别。
- （评估）Detekt。Detekt是一个适用于Kotlin的静态代码分析工具。
- （评估）Flagr。特性开关是持续部署场景中的重要技术。[Flagr](https://github.com/checkr/flagr)将一个完整的特性开关作为一个服务分发到Docker容器之中。
- （评估）Gremlin。Gremlin是一个SaaS解决方案，可供组织进行混沌试验，以助其测试系统的弹性。它提供一系列能导致系统失效（包括资源、网络和状态失效）的攻击。
- （评估）Honeycomb。Honeycomb是一个可观测性工具，它从生产系统中提取出丰富的数据，并通过动态采样使其可管理。
- （评估）Humio。日志领域的新成员。日志管理领域已被Splunk和 ELK Stack主导，所以，有替代选择也是一件好事。
- （评估）Kubernetes Operators。Kubernetes Operator对降低复杂度起到关键作用。该框架提供了一套标准机制，为在Kubernetes集群中运行的软件包描述了自动化运维流程。
- （评估）OpenAPM。[OpenAPM](https://openapm.io)简化了可观测性工具的开源选择流程。它显示目前提供的按照组件角色分类的开源软件包，因此你可以交互地选择可兼容的组件。
- （评估）Taurus。[Taurus](https://gettaurus.org/)是一个通过Python实现的、简便的应用程序和服务性能测试工具。它封装了很多诸如Gatling和Locust的性能测试执行器。该工具能够通过命令行运行，可以很容易地被集成到持续交付流水线中。
- （评估）Terraform provider GoCD。Terraform provider GoCD可以让你使用Terraform（一款在基础设施即代码领域被广泛使用的成熟工具）来构建流水线。
- （评估）Terratest。使用Terraform来代码化配置云基础设施。Terratest是一个Golang库，用来简化基础设施代码的自动化测试编写。
- （暂缓）Handwritten CloudFormation。被Terraform替代。
  

## 语言和框架

- （采纳）Apollo。在构建使用GraphQL从 后 端 服 务 中 访 问 数 据 的 R e a c t 应 用时，Apollo已经成为首选库。虽然Apollo项目还提供服务器框架和GraphQL网关，但其客户端之所以引起我们的关注，是因为它简化了将UI组件绑定到和GraphQL后端提供的数据这一问题。

- （采纳）MockK。为Kotlin应用程序编写测试时，MockK首选模拟工具。喜欢使用该库是因为其对协程或lambda块等Kotlin语言特性提供一流支持。无需使用蹩脚的的Mockito或PowerMock包装器。
- （采纳）TypeScript。拥抱JS类型安全。
- （试验）Apache Beam。Apache Beam是一个开源的统一编程模型，用 于 定 义 和 执 行 数 据 并 行 处 理 流 水 线的 批 处 理 与 流 式 传 输 。Beam模型基于数据流模型，允许我们以优雅的方式表达逻辑，以便在批处理、窗口化批处理或流式传输之间轻松切换。
- （试验）Formik。表单操作在React中一直是一件比较繁琐复杂的事情，使用Formik可以极大的简化表单操作。
- （试验）HiveRunner。HiveRunner是适用于Apache Hadoop Hive查询的基于JUnit4的开源单元测试框架。
- （试验）joi。joi是一款针对JavaScript对象结构的描述语言与验证工具。
- （试验）Ktor。与其它支持Kotlin的Web框架相比，Ktor本身就是用Kotlin编写的，并运用了诸如协程等语言特性来支持异步非阻塞的实现。
- （试验）Laconia。L a co n i a 是 一 个 用 J a va S c r i pt 开 发 A W SLambda函数的框架。
- （试验）Puppeteer。
- （试验）Reactor。随着Spring生态系统开始采纳Reactor，它已成为Reactive Streams的主要实现方式。通过[R2DBC](http://r2dbc.io/)，获得了对RDBMS驱动程序的响应式支持，从而解决了响应式服务的一个缺陷。
- （试验）Resilience4j。Hystrix进入维护模式后，Resilience4j成为了Java生态系统中的默认选择。
- （试验）Room。Room是一个数据持久化库，用于在Android上访问SQLite。Room是Android Jetpack组件之一，旨在简化Android应用开发。
- （试验）Rust。Rust越来越受到欢迎。
- （试验）WebFlux。WebFlux是Spring Framework中的Reactive Streams实现。越来越多地使用反应式编程模型，工作在Spring生态系统上的团队对WebFlux的使用也与日俱增。
- （评估）Aeron。Aeron是一个高效且可靠的点对点消息传输 工 具 。它 通 过 一 些 媒 体 驱 动 程 序（ 包 括HTTP、UDP和TCP）提供重复的持久性消息日志。它还支持消息流的持久化存储，以供日后重放。
- （评估）Arrow。Arrow是适用于Kotlin的函数式编程库，是由两个现有流行库（kategory和funKTionale）合并而成。
- （评估）Chaos Toolkit。Chaos Toolkit可用来描述和运行基础架构上的可重复性试验，以了解架构在发生故障时的恢复能力。
- （评估）Dask。Dask是一个轻量级、高性能的调度程序，可以从一台笔记本电脑扩展到一个计算机集群。Dask可与NumPy、pandas和Scikit-learn结合使用，前景非常可观。
- （评估）Embark。不断看到有人使用Remix编写智能合约并手动部署其应用程序，缺乏自动化测试、源码控制管理或软件工件管理。通过宣传Truffle和Embark等工具来吸引人们对dapp工程实践的关注。

- （评估）fastai。fastai是一个开源Python库，能够简化对快速且准确的神经网络的训练。

- （评估）http4k。[http4k](https://www.http4k.org/)是用纯Kotlin编写的HTTP工具箱，用于提供和消费HTTP服务。

- （评估）Immer。Immutable.js等库填补了不可变性这一缺陷，但带来了新的问题，因为现在应用中存在两种对象和数组，库的版本和原生JavaScript版本。Immer（德语，意为始终）是一个微型库，能够以更加便捷的方式处理不可变状态。它基于写
  入时复制机制，提供尽可能少的API，**对原生的JavaScript对象和数组进行操作**。

- （评估）Karate。Karate是一个领域特定语言，用来描述基于HTTP的API测试。虽然该方法很有趣，可以为简单的测试创建非常易读的规范，但用于匹配和验证负载的专用语言可能会变得语法晦涩、难以理解。

- （评估）Micronaut。Micronaut是一个新的JVM框架，用于使用Java、Kotlin或Groovy构建微服务。内存占用空间小、启动时间短。它避免了依赖注入和代理生成时的运行时反射（这是传统框架的常见缺点），而改用在编译时执行依赖项注入的DI/AOP容器。

- （评估）Next.js。React的服务端渲染方案。

- （评估）Pose。Pose是一个简单的类似于CSS的动画库，适用于React.js、React Native和Vue.js框架。

- （评估）react-testing-library。由于其设计，Enzyme也很难跟上React的发展。这一切都推动我们将react-testing-library作为测试React应用程序的新框架进行评估。
- （评估）ReasonML。[ReasonML](https://reasonml.github.io/)是一个很有意思的基于C风格语法的新OCaml方言默认编译到JavaScript。

- （评估）Taiko。Taiko是一个node.js库，具有清晰简洁的API，可以帮助实现Chrome或Chromium浏览器自动化。
- （评估）Vapor。[Vapor](https://vapor.codes/)，这是一个适用于Swift的现代化web框架，最近备受推崇。