# vol 19

## 最新动态

- 粘性十足的平台

  排名：AWS、GCP、Azure。云提供商倾向于与用户建立尽可能高的粘性，以阻止他们使用其他提供商的服务。通常表现为其云服务会与其服务和工具套件紧密集成。

- 挥之不去的企业级应用反模式

  旧活新整。比如：用Kafka重新创建ESB的反模式、分层式微服务架构、Data-hungry packages、过度庞大的API网关、Low-code 平台和一些其他有害的旧实践。根本问题，是如何在隔离和耦合之间取得平衡：我们隔离组件，使其在技术角度便于管理。也需要协调组件，使其有助于解决业务问题。这就产生了某种形式的耦合。关键在于边界的识别和划分。

- 持之以恒的工程实践

  技术内卷加快。随着技术创新步伐加快，新技术的发展呈现出一种从爆发到沉淀不断循环的模式。每当能够颠覆我们对软件开发固有认知的新技术出现时，都会引起业界的争相追捧，容器化、响应式前端和机器学习都是风口浪尖的猪，就和当年的MySQL一样。这时技术处在爆发阶段。然而，只有在明确如何与长期以来的工程实践（持续交付、测试、协作等）相结合之后，新技术才能真正的发挥功效，并进入沉淀阶段，为下一次爆发性扩张打下坚实的基础。虽然表面看来技术创新是行业发展的唯一驱动力，但事实上，**创新与持之以恒的工程实践相结合才是我们不断进步的基础**。

- 软件开发生态系统中的变化在持续加速

## 技术

- 事件风暴（EVENT STORMING）够迅速识别问题领域中的关键概念，并用最好的方式与各方利益相关人制定解决方案。

- 快速反馈是我们构建软件的核心价值之一。使用金丝雀发布来鼓励对于新版本软件要尽早反馈，同时对选定用户做增量发布以降低风险。这是一种小步前进的方法。

- 大多数没有足够资源进行软件定制化开发的组织，通常会选择一些“开箱即用”的或基于SaaS平台的解决方案来直接满足需求。建议这些组织要设计出清晰的目标能力模型，然后使用被称为限界购买的策略(BOUNDED BUY)。

- 密钥粉碎（CRYPTO SHREDDING）是通过故意覆盖或删除用于保护该数据的加密密钥来使敏感数据无法读取的做法。

- 软件交付性能的四个关键指标（FOUR KEY METRICS）：前置时间，部署频率，平均恢复时间（MTTR）和变更失败百分比。

- 可观测性是运转分布式系统与微服务架构必不可少的一部分。我们强烈建议使用代码来配置可观测性生态系统，称为可观测性即代码，并且采取基础设施即代码的方式搭建其基础设施。

- 新的风险相称的供应商策略（RISK-COMMENSURATE VENDOR STRATEGY）正在兴起，鼓励在高度关键系统中维持其供应商的独立性，而那些相对不太重要的业务可以利用供应商提供的成熟解决方案。
- 建议团队将应用的运行成本纳入架构适应度函数（RUN COST AS ARCHITECTURE FITNESS FUNCTION）来考量，这意味
  着：追踪并权衡应用的运行成本与交付价值；当它们之间产
  生较大出入时，就需要考虑改进软件架构了。

- HashiCorp的Vault工具帮助你将密钥与应用程序分开管理。
- 安全混沌工程（SECURITY CHAOS ENGINEERING）的实用性

- 为了实现可重复性分析，数据和模型（包括算法方案，参数和超参数）需要进入版本控制。因为数据量的缘故，可重复性分析的版本化数据（VERSIONING DATA FOR REPRODUCIBLE ANALYTICS）比起版本化模型更加棘手。一些像DVC这样的工具采用类似git工作流的方式，让用户提交和推送数据文件到远程的云存储，从而实现数据的版本化。
- CHAOS KATAS是一项为基础设施和平台工程师提供技能培训和提升的技术。它将[Kata](https://en.wikipedia.org/wiki/Kata)的方法论与[Chaos Engineering](https://thoughtworks.com/radar/techniques/chaos-engineering)的相关技术（具体指在受控环境中模拟故障和停机）进行结合，对工程师进行系统化教学和培训。Kata是指触发受控故障的代码模式，它允许工程师发现问题，恢复故障，开展事后分析并找到根本原因。工程师通过重复执行Kata能帮助他们真正掌握新的技能。

- 当为我们的应用构建Docker镜像的时候，常常会考虑镜像的安全性和大小。通常情况下我们使用容器安全扫描工具来检测和修复常见的漏洞和风险 ，以及使用Alpine Linux来解决镜像大小和分发性能问题。一个由Google开发，用来解决容器安全性和大小问题的技术，名叫DISTROLESS DOCKER IMAGES。通过这项技术，镜像占用的空间只包含应用本身
  以及它的资源和语言运行时依赖，而不包含操作系统。

- 使用INFRASTRUCTURE CONFIGURATION SCANNER这种方式来确保配置的安全性。
- 随着大型组织向拥有和运营自己的微服务的更自主的团队过渡，他们如何在不依赖集中托管基础架构的情况下确保这些服务之间的必要一致性和兼容性？为了有效地协同工作，即使是自主的微服务也需要与一些组织标准保持一致。SERVICE MESH 提供一致的发现、安全性、跟踪、监控和故障处理，而无需共享API网关或ESB等设施。Linkerd 和 Istio 等开源项目将逐步成熟，这使得service mesh 更容易实现。

- 建议使用 [Ambari](https://ambari.apache.org/) 等工具来配置和管理有状态的Hadoop或Spark群集。

- 使用合理的多云策略，避免只使用通用云服务

- 更推荐根据业务能力来划分服务和团队。

- 微服务已成为现代云计算系统中的领先的架构模式，Kubernetes等平台简化了复杂的微服务系统的部署问题。请千万谨记，微服务是通过开发复杂度来换取运维复杂度，并需要自动化测试、持续交付和DevOps文化提供坚实的支撑。

- 经常看到在面向用户的工作流中使用请求 — 响应事件 (REQUEST-RESPONSE EVENTS IN USER-FACING
  WORKFLOWS)的系统设计。这样，要么UI被阻塞，要么用户就必须等页面收到响应消息后重新加载。这样做会在开发、测试和运维上所增加不必要的复杂度，远远超过了采用这种统一方式带来的好处，我们强烈建议直接使用同步HTTP请求来处理后端服务之间的同步通信，而不必改成事件驱动的设计。

## 平台

- APACHE ATLAS 是一款用于满足企业数据治理需求的元数据管理框架。Atlas支持元数据类型建模、数据资产分类、数据来源追踪和数据发现。**必须小心避免重蹈主数据管理的覆辙**。

- Headless CMS （Content Management Systems, 内容管理系统） 正在成为数字化平台的常见组件。CONTENTFUL是一个现代化的 headless CMS。

- GCP
- 按照传统的方式，我们需要反复为每个团队配置VPC、子网、安全组和网络访问控制列表，与此相比，我们更推崇Google的共享VPC （SHARED VPC）这一概念。共享VPC使组织、项目、VPC和子网成为网络配置中的一级实体。
- [TICK Stack](http://www.influxdata.com/time-series-platform/) 是一些开源组件的组合。这些组件组合成了一个平台，以便人们存储、可视化和监控诸如指标和事件这样的时间序列数据。

- 已取代 Visual Studio Team Services 的 AZURE DEVOPS服务，包括一组托管服务，如Git仓库、CI和CD流水线以及制品库等。
- CockroachDB是一个开源分布式数据库，其设计思路源自白皮书Spanner：谷歌的分布式数据库。它提供分布式事务和地理分区的功能，并支持SQL。使用Raft共识算法，所以这些数据总是能够保持同步。不像依赖TrueTime（用原子时钟进行线性化）的 Spanner，CockroachDB使用 NTP 进行时钟同步，并且提供序列化功能（作为默认隔离级别）。

- DEBEZIUM是一个change data capture (CDC)平台，可以将数据库的变更以流的形式传入 Kafka 主题中。CDC是一种流行的技术，具有多个使用场景，包括将数据复制到其他数据库中，为分析系统提供数据，从单块系统中提取微服务，以及令缓存数据无效等。

-  GLITCH 是一个在线协作开发环境，允许用户轻松复制和调整（或“重新混合”）现有的Web应用程序，或者创建自己的Web应用程序。

- 谷歌云数据流（GOOGLE CLOUD DATAFLOW）在传统的ETL场景中非常有用。它可以从数据源读取数据、转换数据并将转换后的数据存储到接收器中。

- GVISOR是一个容器内的user-space内核。它不允许应用程序访问主机内核的所有功能，只能访问主机内核的表层。不同于现有通过虚拟化硬件实现的沙盒技术 KVM 和 Xen 或者基于规则执行的方式实现的沙盒技术seccomp, SELinux和AppArmor ，gVisor通过拦截应用程序的系统调用这种特殊的方式来实现容器沙盒，并且不需要虚拟化硬件的转换就能够实现访问层内核。

- 在多数情况下，区块链不适合存储 blob 文件 (例如：图像，音频)，当人们开发 DApp 时，一种选择是将blob文件存放在一些链下的集中式数据存储中，这种做法通常会导致信任缺失，另一种选择是将它们存储在星际文件系统 IPFS上，这是一种内容可寻址、版本化、点对点的文件系统。

- 构建和运维微服务生态系统的一个老问题，是如何实现一
  些横切关注点，例如服务发现、服务到服务的安全性、原始
  服务到其它服务的安全性、可观测性（包括遥测和分布式跟
  踪）、滚动发布和系统韧性。Service mesh 以配置为代码的方式，在基础设施层实现了这些横切关注点。策略配置可以一致地应用于整个微服务生态系统。这些策略可以在 serivice mesh 流量内外（通过将 service mesh代理作为网关的方式）以及每个服务的流量（通过将相同的service mesh 代理作为 sidecar 容器的方式）上强制执行。[Istio](https://istio.io/)易于配置的操作模型令人惊叹。

- [KNATIVE](https://cloud.google.com/knative/)试图以开源无服务器平台的方式来解决核心业务问题。它良好地集成了流行的Kubernetes生态系统。

- [PULUMI](https://pulumi.io/) 是云基础设施自动化领域很有前途的新星。其优势在于，允许使用 TypeScript/JavaScript、Python 和 Go 语言（无需YAML配置）来编写配置。Pulumi 专注于云原生架构（包括容器、无服务器函数和数据服务），并为 Kubernetes 提供了良好的支持。

- Quorum最初由J.P. Morgan开发，其定位是“企业版的Ethereum”。与创建了新的以太坊虚拟机（EVM）的
  Hyperledger Burrow 节点不同，Quorum的代码源自以太
  坊官方客户端的一个分支，所以能与以太坊一起进化。

- RESIN.IO 是一个物联网（IoT）平台。虽然只做把容器部署
  到设备中这一件事，但它做得很好。

- [ROOK](https://rook.io/)是一款运行在Kubernetes集群中的开源云原生存
  储编排工具。与Ceph集成之后的Rook，能将文件、块和对
  象存储系统引入到Kubernetes集群中，并能与使用这些
  存储的其他应用和服务一起无缝地运行。

- [SPIFFE](https://spiffe.io/) 正在利用谷歌的LOAS，来将一种称为工作负载标识的关键云原生概念转化为现实。

- 数据饥饿软件包(DATA-HUNGRY PACKAGES)是一种解决方案，它需要将数据吸附到自身才能运行。

- 低代码平台（LOW-CODE PLATFORMS）使用图形化用户
  界面与图形化配置来创建应用程序。然而不幸的是，该平台
  的推广理念却是软件开发不再需要有经验的开发团队。这
  种理念忽视了这样的事实，即在创建高质量软件所需要的
  所有实践中，编写代码仅仅是其中的一小部分，而诸如控制
  源代码、测试和精心设计解决方案这些实践，也同样重要。



## 工具

- Azure Container Service Engine (ACS-ENGINE) 是用于 Azure Resource Manager (ARM) 的模版生成器。ace-engine 使用一个 JSON 文件进行集群配置，并生成 ARM所需的一系列文件。

- Archery 主要用于 Web 应用程序，可以轻松地将安全工具集成到构建与部署系统中。

- ARCHUNIT是一个基于 Java 的测试库，用于检查代码的
  结构特性，如包和类的依赖关系、注解验证，甚至还能检
  查代码分层是否一致。我们很喜欢 ArchUnit 的地方是，它
  可以在现有的测试环境中以单元测试的方式运行，尽管
  只支持基于 Java 的架构。

- Cypress 是一款很有用的工具，可以帮助开发者构建端到端测试，还可以将所有测试步骤保存为 MP4 视频，便于检查错误。

- Git-secrets 是防止将密码或其他敏感信息提交到 git 仓库的小工具，也可以在公开代码库之前使用其扫描全部历史提交。

- Firefox 无头模式(HEADLESS FIREFOX)与用于前端测试的 Chrome 无头模式一样成熟。

- LOCALSTACK 提供了各种 AWS 服务的本地 测试替身 实现，包括 S3 、 Kinesis 、Dynamodb 和 Lambda 等。

- [MERMAID](https://mermaidjs.github.io/) 使用类似 markdown 的标记语言来生成图表。

- Prettier 是一个颇有主张的 JavaScript 代码自动格式化工具（也在逐渐支持其它语言）。它通过强制实施自己主张的代码风格，增强了代码的一致性和可读性，并减少了开发人员在格式化上的工作量，以及团队在代码风格大讨论上浪费的工作量。

- [SNYK](https://snyk.io/) 可以查找、修复及监控 npm 、 Ruby 、 Python 、
  Scala 、 Golang 、 .NET 、 PHP 、 Java 与 Docker 依赖树中
  的漏洞。将 Snyk 加入构建流水线后，它会基于一个托管的
  漏洞数据库，持续地监控和测试你的库依赖树。

- DesignOps 这个领域的实践和工具也日渐成熟。使用一整套支持UI组件快速迭代的环境（也称为UI DEV ENVIRONMENTS），以专注于用户体验设计人员与开发
  人员之间的协作。目前可用的环境有：Storybook ，react-
  styleguidist，Compositor 及 MDX。

- Visual Studio Code 正逐渐成为基础设施开发组和前端开发组的首选工具。

- VS LIVE SHARE 是用于 Visual Studio Code 与 Visual Studio 的插件，提供实时合作编辑与调试代码、语音通话、共享终端和暴露本地端口等功能，能够减少远程结对编程时遇到的障碍。

- 构建、测试和部署移动应用，尤其是由一条流水线从代码仓库打通到应用商店的时候，会涉及许多复杂的步骤。 [Bitrise](https://www.bitrise.io/) 配置简单，并预置了一组完整的步骤，可以满足绝大多数移动应用开发所需。

- CODEFRESH 是类似于 CircleCI 与 Buildkite 的托管型持续集成服务器。它以容器为中心，并将 Dockerfile 文件和容器托管集群视为一等公民。

- 我们一直在寻找一些工具和技术，使大型组织内的交付团队能够独立于其他部门工作，同时满足安全与风险管控的要求。GRAFEAS 就是一个这样的工具。

- HEPTIO ARK 是用于 Kubernetes 集群和持久化卷的灾难恢复管理工具。

- JAEGER 是一个开源的分布式追踪系统。与 Zipkin 类似， Jaeger 的设计灵感来源于谷歌的 Dapper，并遵循 OpenTracing 。 Jaeger 的诞生晚于 Zipkin 但普及迅速。这是因为 Jaeger 支持更多种语言的客户端库，并且在Kubernetes 集群中安装它也很简单。

- [KUBE-BENCH](https://github.com/aquasecurity/kube-bench) 是一款基础设施配置扫描工具，基于 K8S的 CIS 评分自动检查 Kubernetes 配置，涵盖用户身份验证，权限控制和数据安全等方面。

- [OCELOT](http://threemammals.com/ocelot) 是基于.NET Core实现的轻量级API网关项目，
  它可以通过轻松的配置来实现路由转发、请求聚合、服务
  发现、认证授权、限流熔断、负载均衡等特性，它还集成了
  Service Fabric、Consul、Eureka等功能。

- [OPTIMAL WORKSHOP](https://www.optimalworkshop.com/) 是一套数字化的调研工具。它提供了首次点击、卡片分类等功能，可以帮助验证原型以及改进网站导航和信息展示。

- 从文本中提取出有意义的业务信息是一项关键技术。 [STANFORD CORENLP](https://stanfordnlp.github.io/CoreNLP/) 是一组基于 Java 的自然语言处理（NLP）工具集，支持英语、汉语和阿拉伯语等多种语言的命名实体识别、关系抽取、情感分析与文本分类，也提供了用于标记语料库和训练模型的工具。

- [TERRAGRUNT](https://github.com/gruntwork-io/terragrunt) 是 Terraform 的一个轻量级的封装，用来落地《 Terraform: Up and Running 》 书中主张的实践。我们发现 Terragrunt 很有帮助，因为它通过一些便利的特性来促进版本化模块和不同云环境的复用性，这些功能包括递归执行子目录下的代码。

- TESTCAFE 是一个基于 JavaScript 的自动化浏览器测试工具，可以使用JavaScript 或 TypeScript 编写测试，并在任何支持
  JavaScript 的浏览器中运行测试。

- [TRAEFIK](https://traefik.io/) 是一款开源的反向代理及负载均衡器，而非 NGINX 、HAProxy 这样的重量级产品。

- [WALLABY.JS](https://wallabyjs.com/) 是一款支持主流编辑器的商用扩展，可以持续运行 JavaScript 单元测试，并在代码旁高亮显示测试结果。

## 语言&框架

-  JEPSEN 提供了许多我们所需要的工具，来帮助
  我们验证协调任务调度程序的正确性，测试分布式数据库
  的最终一致性、 线性一致性 （Linearizability）和 可串行性
  （Serializability）。

- [MMKV](https://github.com/Tencent/MMKV) 是微信开发的开源框架，为移动应用提供高速的键值对存储。 它利用 iOS 的内存映射功能来避免直接保存修改，因此性能极高。

- [MOCKK](https://mockk.io/) 是用 Kotlin 编写的模拟库。它的核心理念是像 Coroutines 和 Lambda 表达式一样，为 Kotlin 提供一等公民级别的语言特性支持。不同于 Mockito 或PowerMock 的蹩脚封装，作为原生的开发库，它能帮助开发团队在测试 Kotlin 应用时编写干净、简洁的代码。

- TYPESCRIPT 是一门严谨的语言。
- [Apache Beam](https://beam.apache.org/) 是个开源的统一编程模型，用于定义和运行批量/流式的并行数据处理流水线。Beam 提供了可移植的 API 层，可以直接定义流水线，而不依赖于具体的执行引擎（也称为 runner）如 Apache Spark、Apache Flink或 Google Cloud Dataflow。

- 常常对业务流程模型和标记法（BPMN）工具持怀疑态度，因为它们普遍表现出低代码环境相关的缺点。然而 OSS BPMN 框架 [CAMUNDA](https://camunda.com/) 不仅提供了一些这方面的创新，还支持在 Java 代码中以库的方式直接集成它的工作流和决策引擎，从而简化了测试、版本管理和工作流重构方面的工作。

- [FLUTTER](https://flutter.io/)是一个跨平台的框架，可以使用Dart语言编写原生 Mobile 应用。借助 Dart，它能够编译成原生代码，并直接 和目标平台通讯，而不必借助桥接和上下文切换——这些可能导致框架中出现性能瓶颈，就像 React Native 或Weex 那样。

- Kotlin 语言已不仅仅适用于移动应用开发。新工具和框架
  的出现证明了其在 web 应用程序开发上的价值。[KTOR](https://ktor.io/) 就
  是其中之一。

- [NAMEKO](https://www.nameko.io/) 是一个超轻量级的微服务框架，也是Flask的 替代方案。与 Flask 不同的是，Nameko 只包含了WebSocket、HTTP、AMQP 支持等有限功能。它对可测试性的重视也得到我们的欣赏。如果你不需要 Flask 提供的模板等功能，那么 Nameko 值得一瞧。

- [POLLY.JS](https://netflix.github.io/pollyjs/) 是个用于测试 JavaScript 网站和应用程序的简单工具。它可以拦截和模拟 HTTP 交互，从而简单快速地测试 JavaScript 代码而无需启动其依赖的服务或组件。

- [PREDICTIONIO](http://predictionio.apache.org/) 是开源的机器学习服务框架。和所有其他智能应用类似，PredictionIO 由三个部分组成：数据收集和存储、模型训练以及模型部署和服务暴露。开发者只需要基于它提供的相应接口实现数据处理逻辑、模型算法和预测逻辑，无需在诸如存储数据以及训练模型之类的事情上投入额外精力。

-  [PUPPETEER](https://pptr.dev/) 可以通过单页应用程序驱动 Headless Chrome 模式，从而实现基于时间追踪的性能诊断等功能。

- 微软的 Q＃ 语言及其模拟器（本地32量子比特，Azure云上
  40量子比特）。

- Cucumber、RSpec 和 Jasmine 这些测试工具采用 Gherkin 语言和需求规范（Specification）来编写测试。在以上几个工具启发下诞生的测试框架 [SPEK](https://spekframework.org/) 能让开发团队把像行为驱动开发这样的成熟实践带到Kotlin 世界来。

- TROPOSPHERE 作为在AWS上定义基础设施即代码的方式。

- WEBASSEMBLY 将“浏览器作为代码执行环境”向前推进了一大步。

- Spring Framework 5 已发布一年有余，它采用了响应式流 —— 一套非阻塞背压（backpressure）式异步流式处理标准。在传统的 Spring MVC 模块之外，WEBFLUX 为在Spring 生态下编写 Web 应用提供了一个响应式替代品。经过一系列应用的试用，WebFlux 给我们的团队留下了深刻的印象，并汇报说这种响应式（函数式）实现增强了代码的可读性和系统的吞吐量。但他们也确实注意到，采用 WebFlux 需要在思维方式上做出一些重大转变，建议在WebFlux vs. Spring MVC 的技术选型中考虑这一点。