# vol 24

## 本期主图

### 加速产品上市的平台团队

“构建一步到位的大平台”，可能要过数年后才能交付价值。本着“一旦建好，就有人用”的初衷，到头来可能却是巨大的浪费。相反，使用产品思维，有助于根据产
品客户的需求，来弄清每个内部平台所应提供的服务。

### 整合的便利性盖过单项最优方案

工件库、源码控制、CI/CD 流水线、wiki 以及类似的工具，开发团队通常会手工挑选这些工具并按需拼接在一起。总之，需要权衡的是，拥有合并的工具栈可以为开发人员带来更多的便利和更少的工作，但这套工具集却很少能代表最佳的解决方案。

### 识别架构耦合上下文

架构中的耦合涉及许多重要的考虑事项 ：事物如何连接、理解每个问题域中固有的语义耦合、事物间如何相互调用、如何保证事务性正常工作（有时还会与其他棘手的特性，如可伸缩性结合在一起）。除单体系统之外，如果没有某种级别的耦合，软件也就无从谈起 ; 在现代架构中，找到正确的折衷方法来确定耦合的类型和级别是一项关键技能。



## 技术

### 采纳

- API 扩张与收缩。人们通常会采用复杂的版本控制和破坏性的修改来实现 API 升级，而其实简单的扩张再收缩就可以满足需求。例如，在想要弃用 API 中的某个元素时，先添加一个不包含这个元素的 API，然后在消费者都切换到新的 API 之后，再删除该元素以及包含该元素的旧 API。这种方式确实需要 API 消费者的一些协调和可见性，可能需要通过消费者驱动的契约测试之类的技术来实现。
- 机器学习的持续交付（CD4ML）。将机器学习的持续交付（CD4ML）作为所有部署在生产环境中的机器学习解决方案的默认起点。
- 设计体系。以一致的风格交付可访问且可用的产品是一个挑战。设计体系定
  义了一组设计模式，组件库，以及良好的设计和工程实践，以确保数字产品的一致性。在过去的公司风格指南基础上，设计体系提供了易于查找和使用的共享库和文档。设计体系已经成为跨团队和学科产品研发时的标准方法，**它可以使团队更加专注于解决围绕产品本身的战略挑战，而无需在每次需要新的视觉组件时都重复造轮**子。
- 平台工程产品团队。创建平台时，至关重要的是要明确定义可以从中受益的客户和产品，而不是凭空建立。
- 服务帐户轮换方法。强烈建议组织在确实需要使用云服务帐户的时侯确保这些云服务帐户的凭证能得到轮换。轮换是安全性的三个 R 之一。

### 试验

- 云沙箱。这样的方式可以使基础设施即代码实践得到更好的强制应用，并为开发人员提供沙箱环境良好的适应过程。缺点也很明显，因为假定开发人员将完全依赖于云环境的可用性，可能会减慢开发者获得反馈的速度。
- Contextual bandits。Contextual bandits 是一类非常适用于解决探索 / 利用权衡问题的强化学习算法。该算法以赌场中的“老虎机”命名，通过探索不
  同的选择，学习有关预期结果的更多信息，并通过利用表现良好的选项来平衡该结果。
- Distroless Docker images，Distroless Docker images 通过放弃完整的操作系统发行版来减少内存占用和依赖关系，它还可以减少安全扫描噪声和应用程序攻击面。
- Ethical Explorer。EthicalExplorer 可以在这里免费下载，并可折叠为卡片来引发讨论，这些卡片上有针对技术上的若干种“危险区”的一些开放性问题，包括监视（使用我们产品或服务的人是否可能追踪或识别其他用户信息？）、虚假信息、排异排外、算法歧视、沉迷上瘾、数据控制、安全攻击以及权限过大。

- 假设驱动的遗留系统改造。不同于面向标准的 backlog 工作，在这一方法下，团队提出可度量的预期技术成果，并共同建立一组对问题的假设。然后，团队根据优先级在有限时长内进行迭代试验，来验证或推翻每个假设。
- 轻量级的 RFCs 方法。RFCs 是一种用于收集上下文、设计和架构思想，并与团队协作，最终达成决策以及上下文和结果的技术。建议组织采用一种轻量级的 RFCs 方法，通过在多个团队中使用简单的标准化模板和版本控制来获取 RFCs。当未来的团队成员回过头来重新审视这些决策时，掌握这些信息将使他们受益无穷。
- 最简单的机器学习。许多机器学习问题并不需要 GPU 或神经网络。因此提倡在现有的计算平台上使用简单的工具和模型，以及少量的 Python 代码来实现一个最简单的机器学习。只有在能够证明它的必要性时，才使用这些复杂的工具。
- 单页应用（SPA）注入。[绞杀榕模式](https://martinfowler.com/bliki/StranglerFigApplication.html)常被用作遗留系统现代化改造的默认策略，这种策略下，新代码包围在旧代码周围，并慢慢地实现旧代码的能力来满足所需功能。与包围遗留系统不同，向旧应用 HTML 文档嵌入新的单页应用，并基于此逐渐扩展功能。只要用户能够容忍由于页面大小增加而带来的性能问题，新单页应用的框架就不需要与原有框架保持一致（例如，向原有的 AngularJS 应用嵌入新的 React 应用）。单页应用注入允许你逐渐迭代掉旧的单页应用，直到它被新应用完全替代掉。
- 团队的认知负担。
- 使用工具管理 Xcodeproj。建议不要手动合并修复 Xcodeproj 文件， 或是对其进行版本管理，而是使用工具管理 Xcodeproj ：YAML（XcodeGen，Struct），Ruby（Xcake）或是 Swift（Tuist）定义你的 Xcode 项目配置。
- UI/BFF 共享类型。在这种技术中，**一个类型定义不仅会被使用在前端请求返回的数据中，也会被使用在服务端用来满足这些查询**。由于这种做法会跨越流程边界产生不必要的紧密耦合，因此通常会对这种做法保持谨慎。但是，这种方法的好处胜过耦合带来的风险。

### 评估

- 限界低代码平台。仍然对它们更广泛的适用性深表怀疑。
- 去中心化身份。自主身份被称为去中心化身份，按照基于 IP 协议栈的信任标准，是一种“不依赖任何中心化权威并且永远不能被剥夺的任何人、组织或事物的终身可转移身份”。它在隐私方面的应用 ：客户健康应用、政府
  医疗基础设施和公司法律身份。如果想快速地应用去中心化身份，可以评估 Sovrin Network，Hyperledger Aries 和 Indy 等开源软件，以及去中心化身份和可验证凭证标准。
- 部署漂移提示器。部署漂移提示器使得部署在多个环境中的软件版本漂移能够被可视化。
- 同态加密。完全的[同态加密](https://en.wikipedia.org/wiki/Homomorphic_encryption)（HE）是指一类允许在加密数据上直接进行计算操作（如搜索和算数运算）的加密方法。同时计算的结果仍然以加密的形式存在，并且稍后可以对其进行解密和显示。
- Hotwire。Hotwire（跨端传送 HTML）是一项构建网页应用的技术。页面由组件组成，然而与现代单页应用不同，这些组件的 HTML 在服务端生成并“跨端”传送至浏览器。这和后端渲染有何区别？
- 微前端中的模块映射。当使用多个微前端来组建应用程序时，系统的某些部分需要确定加载哪些微前端以及从哪里加载它们。一直以来，我们要么通过构建定制化解决方案，要么采用 single-spa 之类的热门框架来解决这个问题。最近出现了一个新标准[模块映射](https://github.com/WICG/import-maps)，它对这两种方案都有所助益。使用微前端中的模块映射可以清晰地分离关注点。
- 开放应用程序模型。自从上次提到 OAM 以来，我们一直对其首个实现[KubeVela](https://kubevela.io/) 保 持 着 关 注。
- 关注隐私的网络分析。关注隐私的网络分析具有双重优势，无论是形式还是实际它都遵守了 GDPR 条例，与此同时也避免了引入具有侵入性的 Cookie 同意书。这项技术的一个实现便是 [Plausible](https://plausible.io/)。
- Remote mob programming。远程 mob 编程允许团队成员快速“蜂拥”在一个问题或者一小段代码周围，不用受物理限制而需要找到一个可以容纳多人的房间。团队可以在一个问题或者一段代码进行快速地协作，而无需连接到一个大型显示器，预定会议室或者找一个白板。
- 安全多方计算。安全多方计算的一个简单例子是[百万富翁问题](https://en.wikipedia.org/wiki/Yao%27s_Millionaires%27_problem) ：两个百万富翁想了解谁是最富有的人，但又不想透露给彼此他们的实际净资产，同时他们也都不信任第三方。安全多方计算的实施方法各不相同，可能包括秘密共享，遗忘传输，混淆电路或同态加密。

### 暂缓

- GitOps。我们发现对每个环境创建分支会导致环境之间的不一致。最终，合并特定环境的配置代码带来了许多问题，甚至导致这种方式被废弃。
- 分层的平台团队。由于康威定律，我们知道围绕业务能力组织平台功能团队是一种更有效的方式，它为团队提供能力的端到端的所有权，包括数据所有权。这有助于避免分层的平台团队的依赖管理问题，否则做任何事情的时候**前端团队都必须依赖业务逻辑团队，而业务逻辑团队又必须依赖数据团队**。这一种不断延伸的长责任连。
- 天真的密码复杂度要求。**很多组织内部要求密码必须包含符号、数字、大小写字母等等，用户往往会因为难以记忆会选择更加简单的密码**。影响密码强度的主要因素是密码的长度，因此用户应该选择更长的密码，最长为 64 个字符（包括空格）。这些密码会更安全，并且更易于记忆。
- 代码同行评审等同于 pull request。Pull requests 只是管理代码评审工作流的一种方式，我们敦促人们考虑其它的方法，特别是在需要对代码提供指导和反馈的情况下。
- 规模化敏捷框架（SAFe ™）。自上而下的管控会在价值流中产生浪费，阻碍工程人才的创造力，同时限制团队的自主性和试验。相较于衡量工作量
  并关注标准化的仪式，我们更加推荐采用一种更精益的，以价值驱动的方法和治理来帮助组织减少摩擦，例如[价值驱动的数字化转型](https://www.thoughtworks.com/books/edge)或者[团队认知负荷评估](https://www.thoughtworks.com/cn/radar/techniques/team-cognitive-load)，以识别团队的类型并确定他们应该如何更好地互动。
- 代码与流水线的所有权分离。延迟过大，令人不悦，效果很弱。
- 工单驱动的平台运营模式。平台化的终极目标之一便是将基于工单的流程降到最低，因为这些流程会在价值流中形成队列。**对官僚主义的过度依赖和信任的缺失是组织在面对这种基于工单的流程却不愿做出改变的原因之一**。在平台中加入更多的自动化校验和告警是帮助我们从工单审批流程中抽身的一种方法。



## 平台

### 采纳

NO

### 试验

- AWS 云开发工具包。使用主流编程语言而不是配置文件来进行开发，从而可以使用现有的工具、测试方法和技能。
- Backstage。[Backstage](https://backstage.io/) 是由 Spotify创建的开源开发者门户平台。它由软件模板、统一的基础设施工具和一致且集中的技术文档所构成。插件式架构为组织的基础设施生态系统，提供了可扩展性和适应性。
- Delta Lake。[Delta Lake](https://delta.io/) 是由 Databricks 实现的开源存储层，旨在将 ACID 事务处理引入到大数据处理中。
- Materialize。[Materialize](https://materialize.io/) 是一种流数据库。在无需复杂的数据管道的情况下，只须用标准 SQL 视图描述计算，然后将 Materialize 连接到数据流，就能实现增量计算。底层的差分数据流引擎能够运行增量计算，从而以最小的延迟，提供一致且准确的结果。可以将 Materialize与 Spring Cloud Stream 以及 Kafka 配合使用，从而在分布式事件驱动的系统中，查询事件流并分析结果。
- Snowflake。[Snowflake](https://www.snowflake.com/) 在时间旅行、零拷贝克隆、数据共享及其应用市场等功能方面，继续给人留下深刻印象。
- 可变字体。可变字体能避免查找和引用多个具有不同字重和样式的字体文件。这种方法使得所有变体样式都保存在一个字体文件中，并且可以使用属性来选择所需的样式和字重。

### 评估

- Apache Pinot。[Apache Pinot](https://pinot.apache.org/) 的操作相当复杂，并且具有许多需要控制的部件，但是**如果需要分析的数据量足够大，并且对查询要求低延**迟，则建议评估一下 Apache Pinot。
- Bit.dev。[Bit.dev](https://bit.dev/) 是一个云托管平台， 用于通过 [Bit](https://github.com/teambit/bit)抽取、模块化并重用 UI 组件。
- DataHub。LinkedIn 已 经 将WhereHows 进 化 为 [DataHub](https://github.com/linkedin/datahub)， 一 个 通 过
  可扩展的元数据系统实现数据可发现性的下一代平台。与爬取和拉取元数据不同，DataHub 采用了基于推送的模式。数据生态系统中各个组件，都可以通过 API 或者流（stream）向中心化的平台上发布数据。
- Feature Store。[Feature Store](https://www.featurestore.org/) 是一个服务于机器学习的数据平台，可以解决当前我们在特征工程中所遇到的一些关键问题。
- JuiceFS。[JuiceFS](https://github.com/juicedata/juicefs) 是基于 Redis 和对象存储而构建的开源分布式 POSIX 文件系统。
- 用 Kafka API 而非 Kafka。随着越来越多的企业开始运用事件**在微服务之间共享数据、收集分析数据或传输数据到数据湖**，Apache Kafka 已经成为支撑事件驱动架构的最受欢迎的平台。开发者是否会采用“用 Kafka API 而非 Kafka”的策略，抑或这只是 Kafka 的竞争对手试图将用户引诱到 Kafka 平台之外，还有待观察。
- NATS。[NATS](https://nats.io/about/) 旨在支持来自移动设备或 IoT 并通过互连系统的网络所传递的连续数据流，对此我们特别感兴趣。
- Opstrace。[Opstrace](https://opstrace.com/) 是一个用于实现系统可观测性的开源平台，旨在部署于用户自己的网络中。如果不使用像 Datadog 这样的商业解决方案
  （例如，由于成本或数据驻留地点的考虑），那么唯一的解决方案就是构建由开源工具组成的自己的平台。它 使 用 开 源 api 和 接 口， 如 Prometheus和 Grafana， 并 在 上 面 添 加 了 额 外 的 特性， 如 TLS 和 身 份 验 证。Opstrace 的 核心运行了一个 Cortex 集群， 提供可伸缩的 Prometheus API 和 Loki 日 志 集 群。
- [Pulumi](https://pulumi.io/)。如果基础设施完全是静态的，那么Terraform 就够用了。但是动态基础设施但定义，要求使用真正的编程语言。Pulumi允 许 以 TypeScript/ JavaScript、Python 和Go 语言（无需标记语言或模板）编写配置信息，这使其脱颖而出。Pulumi 专注于原生云架构，包括容器、无服务器函数和数据服务，并为 Kubernetes 提供了良好的支持。
- [Redpanda](https://github.com/vectorizedio/redpanda)。Redpanda 是一个数据流处理平台。Redpanda
  可以通过下述方法来简化操作，即仅发布一个二进制文件就可进行安装，同时无须诸如 ZooKeeper 这样的外部依赖。为了做到这一点，它实现了 Raft 协议，并进行了全面的测试，以验证其对该协议的实现是正确的。Redpanda 的一项功能（仅对企业客户可用）是使用嵌入式 WASM 引擎，来实现WebAssembly（WASM） 的内联转换。这允许开发人员使用他们所选择的编程语言，并将其编译为 WASM，来创建事件转换器。Redpanda 还大大减少了尾部延迟， 并提高了吞吐量。Redpanda 是一个令人兴奋的 Kafka 的替代品，值得评估。

### 暂缓

- Azure Machine Learning。Azure ML 承诺为数据科学家提供更多的便利。但是，最终它没有实现诺言。实际上，我们的数据科学家仍然认为，使用 Python 会更为容易。警惕云服务商画大饼！
- 自研基础设施即代码（IaC）产品。我们发现一些组织试图基于现有外部产品，创建自研基础设施即代码（IaC）产品。**他们低估了根据其需求不断演进这些自研解决方案而所需投入的工作量**。在有些情况下，构建在外部产品之上的抽象，甚至削弱了外部产品的原始功能。**建议要审慎地考虑这种方式。这会带来不可忽视的工作量，并且需要确立长期的产品愿景，才能达到预期的结果**。



## 工具

### 采纳

- Sentry。[Sentry](https://sentry.io/) 提供了一些便利的功能，比如错误分组，以及使用适当的
  参数定义错误过滤规则，可以极大地帮助处理来自终端用户设备的大量错误。

### 试验

- axe-core。开源可访问性（a11y）测试引擎 [axe-core](https://github.com/dequelabs/axe-core) 为团队成员提供了关于遵守无障碍规则的早期反馈。
- dbt。使用 [dbt](https://www.getdbt.com/) 完成 ELT 管道中转换部分的工作，使其更容易被数据消费者访问，而不是仅由数据工程师构建 ELT 管道。
- esbuild。[esbuild](https://github.com/evanw/esbuild) 是一个对打包速度进行了优化的 JavaScript 打包器，可以将打包时间减少 10 到 100 倍。它是用Golang 编写的，并且使用了更高效的方式进行解析、打印和生成 sourcemap，构建速度远远超过了 Webpack 和 Parcel 这样的工具。
- Flipper。[Flipper](https://github.com/facebook/flipper) 是可扩展的移动应用程序调试工具。它开箱即用，支持 profiling，交互式布局检查，日志查看器以及适用于 iOS，Android和 React Native 应用程序的网络检查器.
- [Great Expectations](https://docs.greatexpectations.io/en/latest/)。Great Expectations 这个框架可以搭建内置控件，来标记数据流水线中的异常或质量问题。正如单元测试在构建流水线中运行一样，Great Expectations 在执行数据流水线时也会进行断言。
- [k6](https://k6.io/)。关注开发人员体验并且很灵活的工具。
- [MLflow](https://mlflow.org/)。一款用于机器学习实验跟踪和生命周期管理的开源工具。
- OR-Tools。[OR-Tools](https://developers.google.com/optimization) 是用于解决组合优化问题的开源软件套件。这些优化问题有很多可能的解决方案，而 OR-Tools 之类的工具对于寻求最佳解决方案非常有帮助。你可以使用任何一种支持语言（Python，Java，C# 或 C++）对问题建模，然后从几种支持的开源或商业选项中选择解决方案。
- [Playwright](https://playwright.dev/)。编 写 Web UI测试。
- Prowler。[Prowler](https://github.com/toniblyx/prowler) 帮助团队扫描 AWS 基础设施配置，并根据扫描结果提高安全性。
- Pyright。许多类型注解被作为 Python 增强建议（PEP）而提出，Pyright 就是一个可以与这些注解协同工作的类型检查器。对标mypy解决方案。
- Redash。[Redash](https://redash.io/) 可以让普通开发人员以自助的方式创建仪表板，这对于
  查询产品指标非常有用，它缩短了反馈周期，并让整个团队专注于业务产出。
- Terratest。通过基础设施即代码的工具，例如 Terraform，你可以创
  建真实的基础设施组件（如服务器、防火墙或负载均衡器），在它们之上部署应用程序，并使用 [Terratest](https://github.com/gruntwork-io/terratest) 验证预期的行为。
- [Tuple](https://tuple.app/)。Tuple 是一个相对较新的远程结对编程工具，它希望能够填补 Slack 放弃 Screenhero后留下的市场空白。和类似 Zoom 这种通用的视频屏幕共享工具不同，Tuple 支持有两个鼠标光标的双侧控制 ；也和其他的选择比如Visual Studio Live Share 不同，它不需要受限于 IDE 内。Tuple 支持语音和视频通话、剪切板共享和相对其他工具较低的延迟 ；你还可以轻松的在另一方的屏幕上画画和擦除。
- [Why Did You Render](https://github.com/welldone-software/why-did-you-render)。一个能够帮助侦测组件重新渲染的库。

### 评估

- Buildah 和 Podman。尽管 Docker 已经成为容器化的默认选项，这个领域的新玩家还是吸引了我们的注意。Buildah 和 Podman 它们是在多个 Linux 发行版中使用 rootless 方法构建映像（Buildah）和运行容器（Podman）的互补项目。Podman 引入了一个无守护进程的引擎，来管理和运行容器，与 Docker相 比， 这是一个有趣的方法。
- GitHub Actions。GitHub Actions 为开发者提供了小步启动并可以轻松自定义的行为，并逐渐成为小型项目的默认选项。你需要自行判断何时项目会变得足够大或足够复杂，而需要使用有独立支持的流水线工具。
- [Graal 原生镜像](https://www.graalvm.org/reference-manual/native-image/)。Graal 原生镜像是一种以静态链接可执行文件或共享库的形式， 将 Java 代码编译为操作系统本机二进制代码的技术。由于它需要一个封闭的假设，即所有代码在编译时都是已知的，因此需要对诸如反射或动态类加载这样的特性进行额外的配置，因为不能在构建时仅从代码推断出类型。
- [HashiCorp Boundary](https://www.boundaryproject.io/)。在代理访问你的主机和服务的场景下，安全网络和身份管理的能力是不可或缺的，HashiCorp Boundary 将这些能力合并在一
  处，如果需要，还可以连接多种云服务和本地自行部署的资源。
- [imgcook](https://www.imgcook.com/)。它可以通过智能化技术把不同种类的视觉稿（Sketch/PSD/ 静态图片）一键生成前端代码。我们对于魔术代码生成一直十分谨慎，因为从长远看，生成的代码通常很难维护，imgcook 也不例外。但是如果你限定它用于特定的上下文，例如一次性活动广告页，这项技术值得一试。
- [Longhorn](https://longhorn.io/)。Longhorn 是 Kubernetes 的分布式块存储系统。Kubernetes 有很多持久性存储选项，但 Longhorn 与大多数存储选项不同，它是从头开始构建的，提供了增量快照和备份，从而减轻了在非云托管的 Kubernetes上运行复制存储的痛苦。
- [Operator 框架](https://operatorframework.io/)。Operator 可简化 Kubernetes operators 的构建和生命周期管理。Kubernetes operator 模 式 最 初 由 CoreOS 引入， 是一种使用 Kubernetes 原生能力来封装操作应用程序知识的方法 ；它包括要管理的资源和确保资源与其目标状态匹配的控制器代码。
- Recommender。[Recommender](https://cloud.google.com/recommender) 是谷歌云的一项服务， 可以分析现有资源，并根据实际使用量给出如何优化的建议。该服务包含了针对安全、计算使用以及节省成本等方面的一系列Recommender。
- [Remote - WSL](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-wsl)。Windows 中的代码编辑器虽然可以访问 WSL 文件系统，但并不能感知隔离的 Linux 环境。有了Remote - WSL 插件，Visual Studio Code 不仅可以感知 WSL，还允许开发者启动 Linux shell，并且支持在 Windows 下调试 WSL 里运行的二进制代码。在 WSL 支持中可以看到，Jetbrains 的 IntelliJ 也在这方面做出了稳步的改进。
- [Spectral](https://stoplight.io/open-source/spectral/)。它是关于 YAML 和 JSON 的 linter。由OpenAPI催化。
- [Yelp detect-secrets](https://github.com/Yelp/detect-secrets)。Yelp detect-secrets 是一个用于检测代码库中存储的密码的 Python 模块 ；它会扫描一个路径下所有的文件寻找密码。
- Zally。[Zally](https://github.com/zalando/zally) 是一个简便的基于 OpenAPI 的代码扫描工具。

### 暂缓

- AWS CodePipeline。一旦团队的需求超出简单的流水线范畴，此工具就会变得难以使用。



## 语言 & 框架

### 采纳

- Combine。Apple 以 [Combine](https://developer.apple.com/documentation/combine) 的形式推出了自己的反应式编程框架。对于仅支持 iOS 13 及更高版本的 App 而 言，Combine 已 经 成 为 默 认 的选择。它比 RxSwift 更容易学习， 并且与SwiftUI 集成得很好。
- [LeakCanary](http://github.com/square/leakcanary)。它的集成极其简单，同时提供能够清晰回溯内存泄漏原因的通知， 从而帮助侦测到Android 上恼人的泄漏问题。

### 试验

- Angular Testing Library。Testing Library 家族成员。
- [AWS Data Wrangler](https://github.com/awslabs/aws-data-wrangler)。AWS Data Wrangler 是一个开源库，可以将数据框连接到 AWS 数据相关的服务。
- Blazor。能够在前端使用 C#，也意味着可以共享代码和复用现有的库。
- [FastAPI](https://fastapi.tiangolo.com/)。这是一个使用 Python 3.6 或更新版本来构建 API 的现代、快速（高性能）的 Web 框架。其生态还包括了诸如用 OpenAPI来创建 API 文档这样的功能，使团队可以聚焦在业务功能并快速创建 REST API。
- [io-ts](https://gcanti.github.io/io-ts/)。当获取的数据（如调用后端服务返回的数据）与 TypeScript 类型定义不一致时，可能会导致运行时错误。一个叫做 io-ts 的库通过提供编码和
  解码的功能，帮我们弥补了外部数据在编译期类型检查和运行时数据消费之间的鸿沟。
- Kotlin Flow。Kotlin 协程的引入为 Kotlin 的创新打开了一扇大门――直接集成到协程库中的 [Kotlin Flow](https://kotlinlang.org/docs/flow.html) 就是其中之一。
- [LitElement](https://lit-element.polymer-project.org/)。作为 Polymer 项 目 的 一 部 分，LitElement 是一个简单的库，你可以使用它创建轻量级 Web 组件。
- Next.js。[Next.js](https://nextjs.org/) 是一个一站式零配置的框架，提供了包括简化的路由、采用Webpack 和 Babel 进行自动打包和编译、快速热重载等工作流工具。它默认提供服务器端渲染、搜索引擎优化和改善初始加载时间等功能，并支持增量静态生成模块代码。
- 按需分发模块。适用于 Android 系统的[按需分发模块](https://developer.android.com/codelabs/on-demand-dynamic-delivery#0)是一个框架，允许为适当结构的 App 下载和安装仅包含所需功能的量身定制的 APK。
- Streamlit。[Streamlit](https://www.streamlit.io/) 是开源的 Python 应用程序框架，供数据科学家使用，以构建交互式数据应用程序。调整机器学习模型需要花费大量时间，但是我们可以在 Streamlit 中快速构建独立的原型，并在实验循环中收集反馈，而不用在主程序（使用模型的应用程序）中反复调整。
- [SWR](https://github.com/vercel/swr)。在合适的情况下，使用 React Hooks 的库 SWR 可以达到代码整洁和性能大幅提升的效果。SWR 实现了更新的同时使用过期数据 （stale-while-revalidate，缩写即 SWR）的 HTTP 缓存策略。仅在应用程序应返回过期数据时才使用 SWR 缓存策略。需要注意的是，HTTP 要求以最新的数据缓存
  来响应请求，而只能在经过深思熟虑的情况下才允许返回过期的响应。
- [TrustKit](https://github.com/datatheorem/TrustKit)。它是iOS 平台上用于简化 SSL 公钥绑定的一个开源框架， 同样也支持了 Android 平台。

### 评估

- .NET 5。 .NET 5 意味着在将 .NET Core 和 .NET Framework 合并为单一平台方面迈出了重要一步。
- [bUnit](https://bunit.egilhansen.com/)。bUnit 是一款专门为 Blazor 量身打造的测试库，它可以在已有的单元测试框架（如NUnit、xUnit 或 MSUnit） 中 轻 松 地 创 建Blazor 组件测试。
- [Dagster](https://github.com/dagster-io/dagster)。Dagster 是一个用于机器学习、机器分析，以 及 纯 ETL 数 据 管 道 的 开 源 数 据 编 排 框架。与其他任务驱动框架不同，Dagster 知晓流经流水线的数据并可以提供类型安全性，
- Flutter for Web。到 目 前 为 止，Flutter 主 要 支 持 iOS 和Android 原生应用。但是 Flutter 团队的愿景是让 Flutter 支持在任何平台上构建应用。
  Flutter for Web 朝此方向迈出了一步。
- Jotai 和 Zustand。 [Jotai](https://github.com/pmndrs/jotai) 和 [Zustand](https://github.com/pmndrs/zustand)。它 们 都 是 React 的状态管理包，且目标都是小巧易用。Jotai 的作者提供了一个非常有用的[对照表](https://github.com/pmndrs/jotai/blob/master/docs/introduction/comparison.md)，可供你决策使用哪一个工具。
- [Kotlin Multiplatform Mobile](https://kotlinlang.org/docs/mobile/home.html)。KMM 是 JetBrains 提供的 SDK， 它利用了 Kotlin 的跨平台能力， 包含丰富的工具和特性，使整个构建跨平台移动应用的体验变得更加愉悦和高效。
- [LVGL](https://github.com/lvgl/lvgl)。作为一个开源的嵌入式图形库，LVGL 已经适配了主流的嵌入式平台， 如 NXP、STM32、PIC、Arduino 和 ESP32。
- React Hook Form。在使用 React，可以使用 [Formik](https://www.thoughtworks.com/cn/radar/languages-and-frameworks/formik) 来简化这个过程。但是现在可以评估 [React Hook Form](https://react-hook-form.com/) 作 为 潜 在 的 替 代 方 法。
- [River](https://riverml.xyz/dev/)。众多机器学习方法的核心皆在于从一组训练数据创建一个模型。一旦创建了模型，就可以反复使用它。然而世界并不是静止的，通常模型需要随着新数据的出现而改变。单纯地重新训练模型可能会非常缓慢和昂贵。增
  量学习解决了这个问题。
- [Webpack 5 模块联邦](https://webpack.js.org/concepts/module-federation/)。模块联邦允许共享模块规范，通过一次加载多个模块使用的代码，从而帮助消除微前端之间的重复依赖。它还能够区分本地模块和远程模块，远程模块实际上并不是构建的一部分，而是异步加载的。

### 暂缓

NO