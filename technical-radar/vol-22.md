# vol 22

## 最新动态

### 远程办公的迫切需求

一场全球瘟疫大流行迫使全世界的公司迅速从根本上改变了他们的工作方式，以此来保护一些生产力。

### X也是软件

再次强调**基础设施即代码**以及**流水线即代码**的重要性。

### 数据视角的成熟与扩展

将经久不衰的 工程实践与迭代中表现良好的工具组合结合起来，表明“机器学习也是软件。

我们对数据网格有极大兴趣，这是一种能在分布式系统中有效地服务和使用大规模分析数据的方法。

### k8s生态的大爆发

Lens 和 k9s 简化了集群管理，kind 有助于本地测试，Gloo 则是一种可选的 API 网关。Hydra 是为在 Kubernetes 上运行而优化了的OAuth 服务器，而 Argo CD 则使用 Kuberenetes 的原生预期状态管理功能，来实现一个持续交付服务器。这些发展表明，围绕 Kuber-
netes 完全可以建立一个支持性的生态系统。



## 技术

### 采纳

- 将产品管理思维应用于内部平台。不幸的是，一些不太成功的方式：团队在未经验证的假设、没有内部客户的情况下，打造出的平台犹如空中楼阁。这些平台尽管采用了激进的内部策略，但往往无法充分利用，还耗尽了组织的交付能力。

- 基础设施即代码。在现代云时代，构建基础设施的行为已经成为了将配置指令传递到云平台的重要组成部分，因此它变得非常重要。当我们说“即代码”时，是指我们在软件领域学到的所有良好实践都应应用于基础设施。**使用源代码控制，遵守 DRY 原则、模块化、可维护性以及使用自动化测试和部署**都是关键的实践。

- 微前端。与单体前端的权衡。
- 流水线即代码。流水线即代码技术强调，用于构建、测试和部
  署我们应用程序或基础设施的交付流水线配置，都应以代码形式展现。这些代码应置于版本控制系统中，并切分成包含自动化测试和
  部署的可复用组件。
- 务实的远程结对。考虑使用 Visual Studio Live Share 这样的的工具来实行高效、低延迟的协作。
- 最简特性开关。除非你需要 A/B 测试、金丝雀发布或将特性发布的职责交给业务人员，否则建议使用最简特性开关来代替不必要的复杂的特性。

### 试验

- 机器学习下的持续交付。机器学习下的持续交付（CD4ML）是一种可靠的端到端开发、部署和监控机器学习模型的技术。
- 道德偏见测试。例如，模型可以无意中被训练做出有利可图的信用决策，粗暴地排除处于不利地位的申请人。
- GraphQL 用于服务端资源聚合。使用这种模式时，微服务会持续发布明确定义的 RESTful API，而聚合服务或BFF（Backend for Frontends）模式则使用GraphQL 解析器集成其他服务资源。图的形状需由领域建模实践驱动，以确保在必要时（在每个限界上下文都是一个微服务的情况下）将统一语言局限在子图内。
- 移动微前端。
- 平台工程产品团队。当组织考虑组建这样一个团队的时候应当非
  常小心，避免无意中创建一个 独立的 Dev-Ops 团队，也不要把现有托管及运维设施重新打上平台的标签。
- 安全策略即代码。[Open Policy Agent](https://www.thoughtworks.com/cn/radar/tools/open-policy-agent-opa)。
- 半监督学习循环。
- NLP 的迁移学习。
- 使用原生的远程工作方法。使用例如 Visual Studio Live Share、
  MURAL 或 Jamboard 等工具，将在线工作坊和远程结对变成惯例，而不是偶尔为之。
- 零信任架构。

### 评估

- 数据网格。去中心化、互操作性以及数据消费者体验，是数据创新民主化的关键。
- 去中心化身份识别。虽然如 HTTP 之类的协议和如微服务或数据网格这样的的架构模式，使得去中心化的实现成为可能，但身份管理仍然保持着中心化的实现。然而，分布式账本技术（DLT）的出现使去中心化身份识别的概念成为可能。

- 声明式数据管道定义。开发人员发现了 Page Object 模式，后来许多行为驱动开发（BDD）框架都实现了步骤定义与步骤组合之间的分离。一些团队正在尝试为数据工程引入相同的思路。该领域的第一个开源工具⸺[A La Mode](https://github.com/binaryaffairs/a-la-mode)。
- DeepWalk 是一个有助于将机器学习应用于图的算法。
- 通过容器编排管理有状态系统。有些数据库不能为容器的编排提供原生支持⸺它们不期望被调度器终结并重新部署到另一台主机。因此建议在裸机或虚拟机（VM）上运行它们，而不是将其强行适配到容器平台上。
- 预检构建。类似 [Bors](https://bors.tech/) 这样的机器人程序，它将合并master 分支和删除迷你分支（当构建成功时）的工作自动化。

### 暂缓

- 云平移。在云上直接简单复制现有的架构、安全实践和 IT 运营模式。这种方式并未意识到云在敏捷性和数字创新方面的优势。云迁移需要有意地跨多个轴向云原生状态转变。
- 遗留系统迁移的功能一致性。需要进行用户调研，并采用现代产品开发实践，而不是简单地替换现有的系统。
- 用于业务分析的日志聚合。几年前，出现了新一代的日志聚合平台，该平台能够存储和搜索大量的日志数据，用来发掘运营数据中的趋势和洞见。但是，业务需求可能会很快超越这些工具的灵活性和可用性。
- Gitflow 的长期分支。
- 仅快照测试。



## 平台

### 采纳

- .NET Core。
- 如果正在构建和运行规模化的微服务架构，且已采用 Kubernetes，那么使用服务网格来管理所有架构切面，应当是一个默认选择。在众多服务网格实现中，Istio 是最主流的。

### 试验

- [Anka](https://ankadoc.bitbucket.io/) 是一组辅助 iOS 和 macOS 应用开发的工具，用于创建、管理、分发、构建和测试可复制的 macOS 虚拟环境。
- Argo CD。排查失效部署的故障原因、验证日志以及监控部署状态，我们建议尝试使用 Argo CD。
- 使用 Crowdin，在开发团队持续构建功能的同时，平台可以将需要翻译的文本转变为在线工作流。
- eBPF。随着 Kubernetes 的兴起，出现了基于 eBPF 脚本的新一代安全实施和检测工具，以降低大规模微服务部署的复杂性。

- Firebase。
- Hot Chocolate。Hot Chocolate 是用于 .NET（包括 .NET Core 和 .NET Classic）的 GraphQL 服务器。
- Hydra。并非每个人都需要自托管的 OAuth2 解决方案，但若需要，可关注 Hydra。这是一款完全符合规范的开源 OAuth2 服务器及 OpenID connect 提供方。Hydra 平台中无状态的服务器实例出一旦失效，平台会创建新的实例。
- OpenTelemetry 项目包含规范、库、代理和其他组件，以便从服务中捕获遥测信息，更好地观察、管理和调试服务。
- [Snowflake](https://www.snowflake.com) 已被证明是一个健壮的 SaaS 大数据存储、数仓或数据湖解决方案。

### 评估

- [Anthos](https://cloud.google.com/anthos) 通过一系列开源技术（例如 GKE、Service Mesh 和基于 Git 的配置管理系统），为实现混合云和多云策略，提供高级管理和控制平面（plane）。
- [Apache Pulsar](https://pulsar.apache.org/en/) 是一个开源的 pub-sub （发布-订阅）消息与流媒体平台，与 Apache Kafka 在同一领域展开竞争。

- Cosmos 发布了 Tendermint 和 CosmosSDK，以使开发人员可以定制独立的区块链。
- [Google BigQuery ML](https://cloud.google.com/bigquery-ml/docs)。。Google Big Query是 一 个 数 据 仓 库 ，能 针 对 数 据 分 析 场景，使用 SQL 进行大规模查询。Google BigQuery ML 扩展了此功能及其 SQL 接口，通过利用 BigQuery 数据集，来创建、训练和评估机器学习模型，并最终运行模型预测，以创建新的 BigQuery 数据集。

- [JupyterLab](https://jupyterlab.readthedocs.io/en/stable/getting_started/overview.html) 是 Jupyter 项目的下一代基于web 的用户操作界面。

- [Marquez](https://marquezproject.github.io/marquez/) 用于数据生态系统中元数据的采集和托管。它使用简单的数据模型，来表现诸如世袭（lineage)、上下游数据处理作业及其状态之类的元数据，并用灵活的标签标记数据集的属性。
- Matomo （前身为 Piwik）是一款开源的网站分析平台，可以完全控制对分析数据的访问。
- [MeiliSearch](https://github.com/meilisearch/MeiliSearch) 是一个快捷、易用且易部署的文本搜索引擎。如果数据量不大，无须进行分布式的文本搜索，但仍然想实现一个快捷的容错搜索引擎，建议评估MeiliSearch。
- Stratos 是Ultraleap 的底层触觉、传感器和软件平台。
- [Trillian](https://github.com/google/trillian) 是一种可加密验证的集中式数据存储。

### 暂缓

- Node 泛滥。经常听到这样的说法⸺应该使用 Node，只用一种编程语言，就能完成前后端的所有代码。多语言编程是一种更好的方法，尽管也将 JavaScript 作为一等语言。由于单线程的特点，Node.js 也从来就不是计算密集型任务的理想选择。



## 工具

### 采纳

- Cypress。端到端测试。

- Figma 将版本控制、协作设计和设计分享这些功能都集中到一个工具上。

### 试验

- [Dojo](https://github.com/kudulab/dojo) 旨在通过 Docker 镜像的版本化和发布来创建标准的开发环境，来解决“但它在我的机器上是好的”这类问题。

- 在可重复分析的版本化数据中，提到了 [DVC](https://dvc.org/)。时至今日，它已成为机器学习（ML）项目中管理实验的热门工具。

- 用于机器学习的实验跟踪工具。已经出现了 MLflow 这类的工具以及诸如Comet 和 Neptune 这样的平台，使得整个机器学习的工作流程变得更加的严谨和可重复。

- [Goss](https://github.com/aelsabbahy/goss) 是一个供应测试工具。对标 Serverspec。

- [Jaeger](https://github.com/jaegertracing/jaeger) 是一个开源的分布式追踪系统。类似于 Zipkin，它的灵感来自于谷歌的 Dapper论文，并且遵循 OpenTelemetry 规范。

- [k9s](https://k9scli.io/)，它为 kubectl 的所有功能都提供了一个交互界面。

- [kind](https://github.com/kubernetes-sigs/kind) 是一个用于在 Docker 容器节点中运行本地 Kubernetes 集群的工具。通过与 [kubetest](https://github.com/kubernetes/test-infra/tree/master/kubetest) 集成，kind 使 Kubernetes 中的 端 到 端 测 试 变 得 很 简 单。

- [mkcert](https://github.com/FiloSottile/mkcert) 是一个用于创建本地信任的开发证书的便捷工具。在本地开发环境中使用像example.net、localhost 或者 127.0.0.1 这样的主机，使用真实的 CA 签发的证书是不可能的。在这样的情况下，自签发的证书可能是唯一的选择。mkcert 可以生成自签发的证书，并把本地 CA 安装到系统根证书库中。

- [MURAL](https://www.mural.co/) 自诩为“视觉协作的数字工作空间”，并允许团队在基于白板和便利贴构建的共享工作空间内进行交互。

- Open Policy Agent（OPA）。OPA 提供了一套统一的框架和语言，用于声明，实施和控制云原生解决方案中各个组件的策略。

- Optimal Workshop。诸如首次点击，卡片排序，用户交互热力
  图等功能，都能帮助我们在验证产品原型的同时，优化网站导航和信息展示。

- [Phrase](https://phrase.com/) 的良好体验，它对所有关键用户群体都非常易用：翻译人员使用的是一个非常方便的浏览器 UI；管理者也可以在这个UI 上添加新的字段，并与其他团队的翻译进行同步；开发人员可以在本地或通过构建流水线来访问 Phrase。

- [ScoutSuite](https://github.com/nccgroup/ScoutSuite) 自动聚合环境的配置数据，并应用规则对环境进行审计。

- 视觉回归测试工具。[BackstopJS](https://www.thoughtworks.com/cn/radar/tools/backstopjs) 是一个很好的选择。

- Visual Studio Live Share。Live Share提供了良好的、低延迟的远程结对体验，并且所需的带宽比粗暴地共享整个桌面要少得多。更重要的是，开发人员可以在结对过程中使用他们各自的配置、扩展和快捷键。

### 评估

- [Apache Superset](https://superset.apache.org/) 是一个很棒的 BI（Busi-ness Intelligence）工具，用于与大型数据湖和数据仓库一起，进行数据探索和可视化。
- AsyncAPI。[OpenAPI](https://github.com/OAI)（以前被称为 Swagger）作为定义 RESTful API 的行业标准，对事件驱动是缺失的。而[AsyncAPI](https://www.asyncapi.com/) 旨在构建急需的事件驱动和异步 API 标准以及开发工具

- 一个支持动态特性开关的服务（切记简单的特性开关也是可行的）， [CongfigCat](https://configcat.com/)。
- [Gitpod](https://www.gitpod.io/) 通过为 Github 或 GitLab 仓库提供基于云的、现成的代码环境来解决项目的签出和构建问题。
- [Gloo](https://www.solo.io/products/gloo/) 是一个轻量级API 网关，使用 Envoy 作为其网关技术，同时为外部用户和应用程序提供附加价值，如 API 的内聚视图等。
- [Lens](https://k8slens.dev/) 试图通过一个集成环境查看集群的当前状态和工作负载，可视化集群指标，并通过内嵌的文本编辑器修改配置。
- [Manifold](https://github.com/uber/manifold) 是机器学习的一个与模型无关的可视化调试工具。模型开发人员通常会花费大量的时间用于迭代和改进现有模型，而不是创建一个新的模型。
- [Sizzy](https://sizzy.co/) 是一个 SaaS 解决方案，用于在一个浏览器窗口内展示多个视窗。应用会被同时渲染到所有的视窗中，并且对应用的交互也会同步到所有视窗中。不过，还是难以和chrome内置的工具的便利性抗衡。
- [Snowpack](https://www.snowpack.dev/) 是 JavaScript 构建工具领域中的一个有趣的新成员。与其他解决方案相比，Snowpack 的关键的改进是可以使用 React.js，Vue.js 和 Angular 等现代框架来构建应用程序，而无需打包器。

- 在基础设施即代码领域，Terraform 是管理云环境时一个显而易见的选择。现在又有了 [tfsec](https://github.com/liamg/tfsec)。它是一个静态分析工具，可以用来扫描 Terraform 模板并查找潜在的安全问题。



## 语言和框架

### 采纳

- React Hooks。React Hooks 引入了一种**管理状态逻辑**的新方法；鉴于 React 组件相比较类来说更接近于函数，Hooks 接受了这一点并**将状态传给函数**，而不是将函数作为方法传给带有状态的类。
- [React Testing Library](https://testing-library.com/)。这种思维源自于[测试库](https://testing-library.com/)，React Testing Library 是它的一部分，它还为像 Angular 和 Vue.js 这样的框架提供了一整套库。
- Vue.js。简明的 API 设计、职责清晰的指令及模块（一个模块对应一个文件的设定）和更简单的状态管理，都让 Vue.js 成为有力的竞争者。

### 试验

- CSS-in-JS。在使用 CSS-in-JS 技术的时候，除了它的种种优点，通常也会遭遇到一个缺点：运行时计算样式会导致[用户可感知延迟](https://calendar.perfplanet.com/2019/the-unseen-performance-costs-of-css-in-js-in-react-apps/)。通过[Linaria](https://linaria.now.sh/)，我们看到了一类新的在创建时就考虑到这个问题的框架。Linaria 引入了许多技术**把大部分性能开销转移到构建时间**。
- [Exposed](https://github.com/JetBrains/Exposed)。轻量级的对象关系映射器（ORM），有两种数据库访问方式：类型安全的内部 DSL 包装 SQL，以及数据访问对象（DAO）模式的实现。它具有一个成熟的ORM 所能被期待的功能，例如对多对多引用的处理，急切加载（eager loading）以及对跨实体联接的支持。方法实现无需代理，并且不依赖于反射，这无疑对性能有所帮助。
- [GraphQL Inspector](https://github.com/kamilkisiela/graphql-inspector)。比较两个GraphQL 模式之间的不同。
- Karate。[Karate](https://intuit.github.io/karate/) 是一种 API 测试框架，独特之处在于不依赖通用编程语言，而直接使用基于 Gherkin 的语法编写测试。Karate 使用一种领域特定语言，来描述基于 HTTP 的 API测试。
- Koin。[Koin](https://insert-koin.io/)是一个 Kotlin 框架，用于处理软件开发中的常规问题：依赖注入。对标Android生态的老对手，dagger。但是Koin简单性非常突出。
- NestJS。[NestJS](https://nestjs.com/) 是一个让 Node.js应用开发更安全、更不易出错的基于 TypeScript 优先风格的框架。NestJS 是一个有态度的框架。它的架构直接受 Angular 启发，符合 SOLID 原则，可以使用NestJS 构建 Node.js 微服务。
- [PyTorch](http://pytorch.org/)。对标Tensorflow，而且流行程度越来越强。
- Rust。持续观望，未来可期。
- Sarama。[Sarama](https://github.com/Shopify/sarama) 是 Apache Kafka 的 Go 客户端库。有两种类型的API⸺一种高层 API 用于轻松地生产和消费消息，另一种底层 API 用于控制网络上的字节。
- [SwiftUI](https://developer.apple.com/xcode/swiftui/)。这为开发人员提供了一个完全原生的替代品，以替代类似 React Native 或 Flutter 这样的反应式框架。

### 评估

- Clinic.js Bubbleprof。[Clinic.js Bubbleprof](https://clinicjs.org/bubbleprof/) 会可视化 Node.js 进程中的异
  步操作，绘制程序调用流中的延迟图。
- Deequ。[Deequ](https://github.com/awslabs/deequ)，这是一个用来为数据集编写类似单元测试的库。
- ERNIE。百度出品的NLP框架。它可以通过添加输出层的方法来进行微调，以创建多种 NLP 任务的当前最优模型。
- MediaPipe。[MediaPipe](https://github.com/google/mediapipe) 是一个用于构建多模态、多平台的机器学习流水线。
- Tailwind CSS。为了便于定制，它**仅提供了较低层次的 CSS 样式类来构建**
  **模块**，且没有自带任何多余的复杂样式。较低层次 CSS 样式类广泛的覆盖，使开发者不需要编写任何新的样式类或 CSS 样式。从长远来看，这将产出更易于维护的代码。这样来看Tailwind CSS 在可重用性和自定义创建可视化组件之间，提供了适当的平衡。
- [Tamer](https://github.com/laserdisc-io/tamer)。如果需要将关系数据库中的数据收集到Kafka 的 Topic 中，可以考虑使用 Tamer，它将自己标榜为“驯化的 Kafka JDBC数据源连接器。
- Wire。在Go生态，[Wire](https://github.com/google/wire) 是一种编译时依赖注入工具，可以同时生成代码并将组件连接在一起。Wire 没有额外的运行时开销，并且更易于推断静态依赖关系图。
- [XState](https://xstate.js.org/docs/)。这是一个简单的 JavaScript 和 TypeScript 框架，用于创建有限状态机并将其可视化为状态图。它与一些流行的响应式 JavaScript 框架（Vue.js，Ember.js，React.js 和 RxJS）集成，并且是基于 W3C 标准的有限状态机。Xstate 的 [visualizer](https://xstate.js.org/viz/)这个可视化工具很棒。

### 暂缓

- Enzyme。Enzyme 对于被测试组件内部的聚焦会导致脆弱的、无法维护的
  测试。再见 Enzyme ~。