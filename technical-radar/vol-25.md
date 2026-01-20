# vol 25

## 本期主题

### 适配到 Kafka，还是改编自 Kafka

一些工具允许使用更传统的 kafka 接口（例如 [ksqlDB](https://thoughtworks.com/zh-cn/radar/languages-and-frameworks/ksqldb)、[ConfluentKafka REST Proxy](https://thoughtworks.com/zh-cn/radar/platforms/confluent-kafka-rest-proxy) 和 Nakadi），而另一些则旨在提供额外的服务，如 GUI 前端界面和编排插件。

**Kafka 会成为异步发布 / 订阅大批量消息事实上的标准**。

### 便利背后的陷阱

随着软件系统变得越来越复杂，开发团队必须勤于创建与维护经过深思熟虑的架构和设计，而不是为了图方便而做出草率的决定。通常，考虑特定方法的可测试性，会使团队降低做出有问题决策的机会。如果不加合理设计，软件会逐渐趋于复杂。**精心设计，加上更为重要的持续治理**，可以确保进度压力或其他众多破坏力，不会导致团队为图一时之便而做出不正确的决策。

### 康威定律仍然有效

涉及效能的[团队拓扑](https://teamtopologies.com/book)以及得到越来越多认可的[团队认知负载](https://www.thoughtworks.com/cn/radar/techniques/team-cognitive-load)；以及围绕程序员生产力而开发的新框架 [SPACE](https://queue.acm.org/detail.cfm?id=3454124)。

### 避免过度使用巧妙的技术

许多软件界人士都欣赏针对复杂问题的巧妙解法，但其实这些巧妙的解决方案往往都源于其自身引入的偶然复杂度。

与其使用更多技术来解决问题，团队更应该分析根因，解决潜藏的本质复杂度并不断改进。例如**数据网格就解决了关于组织及技术的基本假设，这些假设通常会导致过度复杂的数据流水线及工具**。

### 雷达中平台相关条目变少

大多数公司已经选择好了云供应商，他们几乎都基于 Kubernetes 和 Kafka 实现了容器编排和高性能消息系统的标准化。

随着企业大规模地完成云迁移，行业或许已经进入了相对冷静期，等待着下一波颠覆式创新浪潮的到来。



## 技术

### 采纳

- 交付核心四指标。由 [DORA 研究](https://www.devops-research.com/)项目定义的交付核心四指标，即：更改前置时间、部署频率、平均修复时间（MTTR）和变更失败率。这项研究及其统计分析展示了高效能交付团队和这些指标的高度相关性，它们为衡量一个团队、甚至整个交付组织的表现提供了极佳的领先指标。
- 平台工程产品团队。**不要只将现有的内部团队重命名为“平台团队”，而同时保持工作方式和组织结构不变**。在考虑如何最好地组织平台团队时，推荐使用[团队拓扑](https://teamtopologies.com/)中的概念。平台工程产品团队是一种标准方法，也是高性能 IT 的重要推动者。
- 零信任架构。ZTA 是安全架构和策略的一个范式转变。它基于这样的假设：网络边界不再代表安全边界，同时不应仅仅根据用户或服务的物理或网络位置来予以盲目的信任。参考 [NIST ZTA](https://csrc.nist.gov/publications/detail/sp/800-207/final) 的出版物和谷歌关于 [BeyondProd](https://cloud.google.com/security/beyondprod) 的白皮书。

### 试验

- [CBOR](http://cbor.io/)/JSON 双重协议。[Borer](https://github.com/sirthias/borer) 是 CBOR 编码器 / 解码器的 Scala 实现，它允许互相通信的两端通过协商自由选择二进制或者传统 JSON 作为消息格式。同样的消息，既能够以普通文本在浏览器里显示，又能够表示为精简高效的二进制，这是非常实用的特性。
- 数据网格。现在，管理分析数据的各个方面，都被外部化到运营业务领域之外的数据团队和数据管理单体：数据湖和数据仓库。[数据网格](https://martinfowler.com/articles/data-monolith-to-mesh.html)用于消除分析数据和业务运营的二分法，是一种去中心化的社会技术方法。其目标是将分析数据的共享和使用嵌入运营业务的各个领域，并缩小运营和分析平台之间的差距。
- 遗留系统的活文档。团队在进行系统现代化改造时，时常受限于缺乏业务知识。**由于人员流动以及现有文档已经过时，代码成了唯一可靠的依据**。因此当我们接管遗留系统时，如何重新建立文档与代码间的关联，以及如何在团队中传播业务知识变得尤为重要。在实践中，我们会首先尝试**对代码进行简单的清理和安全的重构**，以此加深我们对业务的理解。在此过程中，我们需要**向代码添加注释**，以便随后自动生成活文档。根据生成的文档，我们可以**进一步将一些规范转换为可执行的高阶自动化测试**。反复执行此操作，最终获得一份与代码密切相关并且部分可执行的遗留系统的活文档。
- 移动微前端。当应用程序变得足够庞大且复杂时，有必要将其开发分布在多个团队中。如此一来，在保证团队自治的同时，还要将他们的工作集成到单个应用中是很有挑战的。过去 Atlas 和 Beehive 可以作为可行框架以简化多团队应用开发的集成问题。最近有团队亦使用 React Native 达成了此目标。
- 远程集体编程。
- 统一远程团队墙。统一远程团队墙是一项重新引入虚拟团队墙的简单技术。它能统一显示各种故事卡、任务、状态和进度，在团队中充当信息辐射器和信息枢纽。
- 团队的认知负载。使用[高效能团队模式（Team Topologies ）](https://teamtopologies.com/book)
  的作者提供的评估方法，可以让你理解团队构建、测试和维护其服务的难易程度。通过衡量团队的认知负载，能够为客户提供更好的建议，帮助他们改变团队架构，改进团队的互动方式。

### 评估

- AR 空间定位点。这一技术利用设备摄像头记录视觉图像，通过图像的特征点以及它们在 3D 空间中的相对位置来识别其在真实世界中的位置，然后在 AR 空间中创建相应的定位点。虽然空间定位点无法取代所有基于 GPS 和标记的定位点，但它们确实比大多数基于 GPS 的方案精度更高，也比基于标记的定位点更能适应不同的视角。Google 的 [Android 云定位点服务](https://developers.google.com/ar/develop/java/cloud-anchors/overview-android)。
- Hotwire。和单页应用（SPAs）不同，Hotwire 应用程序的绝大部分业务逻辑和界面导航都在服务器端运行，只有很少量的 JavaScript 在浏览器上运行。Hotwire 把 HTML 页面模块化为组件（称为 Turbo
  Frames），这些组件支持延迟加载，有相互独立的上下文，能够基于用户行为修改上下文从而更新 HTML 页面。**单页应用（SPAs）提供无可否认的用户响应能力，但把传统服务器端 Web 编程的简单性与现代浏览器工具相结合，为平衡开发人员效率和用户响应能力提供了一种令人耳目一新的方式**。
- 非集群资源的 Operator 模式。非集群资源的 Operator 模式利用自定义资源和在 Kubernetes 控制面板中实现的事件驱动调度机制，来管理与集群外部相关的活动。该技术建立在由 Kube 管理的云服务的思想之上，并将其扩展到其他活动，例如持续部署或者及时响应外部存储库的变化。
- 远程自发技术讨论。Slack 新增的 [Huddles](https://slack.com/help/articles/4402059015315-Start-a-huddle-in-a-channel-or-direct-message) 特性提供了类似 Discord 的持久化语音通话，供用户随时加入或退出。[Gather](https://www.gather.town/) 提供了一种有创意的方式，通过头像和视频模拟虚拟办公室。很多集成开发环境
  （IDE）为结对编程和调试提供了直接的协作功能：我们之前列举了 [Visual Studio Live Share](https://www.thoughtworks.com/cn/radar/tools/visual-studio-live-share)，并在本期当中包含了[JetBrains Code With Me](https://www.thoughtworks.com/cn/radar/tools/code-with-me)。
- 软件物料清单。不仅应该详细说明交付的组件，还应该详细说明用于交付软件的工具和框架。这一秩序有可能开启软件开发透明和开放的新时代。

### 暂缓

- Pull Request 等同于代码同行评审。一些组织觉得 pull request 等同于代码同行评审，认为唯一能够实现代码同行评审的方法就是 pull
  request。这种方法会造成严重的团队瓶颈，并且显著地降低反馈的质量，因为超负荷的评审人员会开始简单地拒绝 pull requests。
- 测试环境中的生产数据。使用假数据是一种更安全的策略。现存的工具也能帮我们创建假数据。在某些场景，**例如重现错误或训练特定的机器学习模型时，我们承认确实有复制生产数据特定元素的必要**。但一定要谨慎行事。



## 平台

### 采纳

-

### 试验

- Backstage。[Backstage](https://backstage.io/) 是一个由 Spotify 创建的开源开发者门户平台。它基于软件模板、统一的基础设施工具和一致且集中的技术文档。其插件式架构，使其在组织的基础设施生态系统中，具有可扩展性和适应性。
- ClickHouse。[ClickHouse](https://clickhouse.com/) 是用于实时分析的开源柱状在线分析处理（OLAP）数据库，已经发展成一个高性能且可线性扩展的分析数据库。它兼备高效查询处理引擎和数据压缩功能，使其适合在不进行预聚合的情况下，运行交互式查询。
- [Confluent Kafka REST Proxy](https://docs.confluent.io/3.0.0/kafka-rest/docs/index.html)。该代理允许遗留系统的生产者发送事件这一点很有价值，但经由它来创建事件的消费者时要多加小心，因为抽象会变得更为复杂。该代理不会改变 Kafka 消费者是有状态的这一事实，这意味着由 REST API 创建出的消费者实例，会与特定的代理
  相绑定。此外，需要进行 HTTP 调用来消费主题中的消息，会改变 Kafka 事件的标准语义。
- GitHub Actions。对于复杂工作流来说，GitHub Actions 还不算成熟的 CI/CD 工具。例如，**它不能重新触发工作流中单独的一个 job，不能在 composite action 中调用其他的 action，也不支持共享库**。还可以使用 [act](https://github.com/nektos/act) 在本地运行Github Actions。它的便利性和简单性毋庸置疑。
- K3s。[K3s](https://k3s.io/) 是一个轻量级的用于物联网和边缘计算的 Kubernetes 发行版。它可以带来与 Kubernetes 相当的优势，同时又降低了运营的开销。它的增强部分包括轻量级的存储后端（K3s 默认使用 sqlite3 作为存储后端，而非etcd）和一个单一且具有最少的操作系统依赖的二进制包，这些都使得 K3s 适用于资源受限的环境。
- [Mambu](https://www.mambu.com/)。它具备别具一格的工作流，同时使用 API 驱动的方法，来定制业务逻辑、流程和集成方式。
- [MirrorMaker 2.0](https://cwiki.apache.org/confluence/display/KAFKA/KIP-382%3A+MirrorMaker+2.0)。基于 Kafka Connect 框架构建的 MirrorMaker 2.0（也称为 MM2），弥补了以前 Kafka 复制工具中的许多短板。它可以成功地跨集群 geo-replicate 主题数据和元数据，包括偏移量、消费者组和授权命令行（authorization command lines，ACLs）。
- 用于 Kubernetes 的 OPA Gatekeeper。用于 [Kubernetes 的 OPA Gatekeeper](https://kubernetes.io/blog/2019/08/06/opa-gatekeeper-policy-and-governance-for-kubernetes/) 是为 Kubernetes 实现的一个可定制的准入 webhook。它可以确保所有的规则都会被 Open Policy Agent（OPA）执行。可以使用 Kubernetes 平台的这个扩展，来为集群添加一个安全层，以便通过提供一个自动化的治理机制，来确保所有的应用都符合定义好的规则。
- [Pulumi](https://pulumi.io/)。虽然 Terraform 是一个久经考验的常备选项，但其声明式的编程特质，却深受不足的抽象机制和有限的可测试性的困扰。如果基础设施完全是静态的，那么 Terraform 就够用了。但是动态基础设施的定义，则需要使用真正的编程语言。允许以TypeScript/JavaScript、Python 和 Go 语言编写配置信息（无需标记语言或模板）这一点，就使 Pulumi 脱颖而出。
- Sealed Secrets。[Sealed Secrets](https://github.com/bitnami-labs/sealed-secrets) 提供组合运算符和命令行实用程序，使用非对称密钥来对“机密”进行加密，以便仅在集群中用控制器将其解密。此过程可确保“机密”在 Kubernetes 用于部署的配置文件中不会泄漏。
- Vercel。[Vercel](https://vercel.com/) 是一个托管静态网站的云平台。它实现了开发、预览和发布 JAMstack 网站工作流程的无缝衔接。通过和 GitHub 集成，每个代码提交或 Pull Request，都可以触发一个新的带有预览链接的网站部署。
- Weights & Biases。使用机器学习（ML）平台 [Weights & Biases](https://wandb.ai/) 的实验跟踪、数据集版本控制、模型性能可视化和模型管理功能，能够更快地构建模型。

### 评估

- Azure 认知搜索。[Azure 认知搜索](https://docs.microsoft.com/en-us/azure/search/)为需要对异构内容进行文本检索的应用程序，提供搜索即服务的功能。它提供基于推送或基于拉取的 API，来上传图像。它能将图像、非结构化文本或结构化文档内容编入索引，也能有限地支持基于拉取的数据源类型。它能以 REST 和 .NET SDK 为基础，提供用来执行搜索查询的 API。
- Babashka。当前脚本多使用 bash 或 Python。Clojure 成为了这类工作令人兴奋的新选择。这要归功于 [Babashka](https://babashka.org/)，一个使用 GraalVM 实现的完整的 Clojure 运行时。Babashka附带的库已经可以涵盖大多数的脚本工具使用场景，并且它还支持加载更多的库。
- ExternalDNS。[ExternalDNS](https://github.com/kubernetes-sigs/external-dns) 将 Kubernetes 的 Ingress 和 Service 所对应的 DNS 记录，同步给外部的 DNS 提供商。这项工作原本由 kops dns-controller、Zalando’s Mate 或者 route53-kubernetes 来完成。由于人们更加青睐ExternalDNS，后两者目前已被弃用。该工具使得用户可以通过公共 DNS 服务器，来发现 Kubernetes 的内部资源，从而减少了因 Ingress 主机或者 service IP 地址发生改变，而需要更新DNS 时的一些人工步骤。
- Konga。用来管理 Kong API 网关的 [Konga](https://pantsel.github.io/konga/)，是一个开源 UI 平台。
- Milvus 2.0。[Milvus 2.0](https://github.com/milvus-io/milvus) 是一个云原生的开源向量数据库，用于检索和管理由机器学习模型和神经网络所生成的嵌入向量。它支持多种[向量索引](https://milvus.io/docs/index_selection.md)，以供对音频、视频、图像或其他任意非结构性数据的嵌入向量进行近似最近邻（ANN）搜索。
- [Thought Machine Vault](https://thoughtmachine.net/vault)。付费。这个产品旨在支持良好的软件工程实践，比如测试驱动开发、持续交付和基础设施即代码。开发者通过在 Vault 里用 Python 编写智能合约，来定义银行产品。
- [XTDB](https://www.xtdb.com/main/index.html)。具备双时态图查询的开源文档数据库 XTDB，每条记录都原生支持两个时间轴：valid time 时间轴（涉及事实发生时间），以及 transaction time 时间轴（涉及事实被数据库处理和记录的时间）。在很多场景中，支持双时态都是有益的，比如当进行用于执行时间感知查询的分析型用例的设计时，当审计事实的历史变化时，当支持分布式数据架构以保证时间点查询的全局一致性时（例如数据网格，当保持数据不变性时，等等。XTDB 以文档形式获取信息，而信息用可扩展数据表示法（EDN，是 Clojure 语言的一个子集）格式表示。

### 暂缓

-

## 工具

### 采纳

- fastlane。[fastlane](https://fastlane.tools/) 为代码签名提供了一个解决方案——[match](https://docs.fastlane.tools/actions/match)。它已被集成在 fastlane 顺滑的流程中，并且为管理团队的代码签名实现了一个新的[方法](https://codesigning.guide/)。这种方法并不将签名密钥缺省存入开发者 macOS 的钥匙串中，而是将密钥与证书存入一个Git 仓库中。

### 试验

- Airflow。尽管[Airflow](https://airflow.apache.org/) 仍然是被广泛使用的编排工具之一，但是还是鼓励你根据实际情况评估其他工具。例如，Prefect，[它的关键特性是支持动态的数据处理任务](https://www.thoughtworks.com/cn/radar/tools/prefect)，任务本身通过 Python 范型函数实现。如果你需要和 Kubernetes深度集成，那么可以考虑 [Argo](https://argoproj.github.io/)。如果你需要编排机器学习（ML）工作流，那么 [Kubeflow](https://www.thoughtworks.com/cn/radar/tools/kubeflow) 和 [MLflow](https://www.thoughtworks.com/cn/radar/tools/mlflow) 可能更合。
  适
- Batect。[Batect](https://batect.dev/) 被很多人作为配置本地开发和测试环境的默认方式。这个开源工具基于 Docker，让设置和分享构建环境变得特别简单。
- Berglas。[Berglas](https://github.com/GoogleCloudPlatform/berglas) 是一款用来管理 Google 云平台（GCP）上私密信息的工具。此前已经推荐过“密码即服务”作为在现代分布式架构中存储和分享私密信息的技术，GCP 为此提供了 [Secret Manager](https://cloud.google.com/secret-manager)，而 Berglas 与 SecretManager 配合使用效果良好。
- Contrast Security。[Contrast Security](https://www.contrastsecurity.com/) 提供一个包括了静态应用安全测试（static application security testing，SAST）、交互式应用安全测试（interactive application security testing，IAST）、开源扫描、运行时应用自保护（runtimeapplication self-protection 、RASP）等多种组件的安全平台。
- Dive。[Dive](https://github.com/wagoodman/dive) 是一个针对 Docker 镜像的分析工具，用于检查镜像文件中的每一层级、识别每一层上发生的变化。可以估测出镜像文件的镜像效率和已浪费空间，还能集成到持续集成（CI）流水线，根据效率得分或者已浪费空间让构建失败。
- Lens。Lens 被称为“Kubernetes 的IDE”，它让与 Kubernetes 集群的交互成为可能，而不需要你记住命令或显示文件结构。集群指标和部署负载的可视化工具可以节省时间，并减少维护 Kubernetes 集群的人力成本。Lens没有将维护 Kubernetes 的复杂工作隐藏在简单操作的 UI 界面之后，而是引入了管理员可以从命令行运行的工具。**对于正在运行的集群，要谨慎使用 UI 交互方式修改，我们通常倾向于将基础架构变更用代码实现，这样它们是可重复的、可测试的，并且不容易出现人为错误**。
- Nx。我们想要评论的是工具。在团队中，我们看到了纷纷舍弃Lerna，而强烈倾向于使用 [Nx](https://nx.dev/) 来管理基于 JavaScript 的 monorepos。
- Wav2Vec 2.0。[Wav2Vec 2.0](https://github.com/pytorch/fairseq/tree/main/examples/wav2vec) 是用于语音识别的自我监督学习框架。在这个框架里，模型被分为两个阶段进行训练。首先，它从使用未标记数据的自我监督模式开始，并尝试实现最佳的语音表达。然后它进行有监督的微调整，在此期间，已被标记的数据指导模型去预测特定的单词或音素。

### 评估

- cert-manager。[cert-manager](https://cert-manager.io/) 是一款在 Kubernetes 集群里管理 X.509 证书的工具。它将证书和签发者建模为最高级的资源类型，并将证书作为服务安全地提供给工作在 Kubernetes 集群上的开发人员和应用程序。得益于对 [Let's Encrypt](https://www.thoughtworks.com/cn/radar/tools/let-s-encrypt)，[HashiCorp Vault](https://www.thoughtworks.com/cn/radar/tools/hashicorp-vault) 和 Venafi 的内置支持，cert-manager 是一款值得评估的证书管理工具。
- Cloud Carbon Footprint。[Cloud Carbon Footprint](https://www.cloudcarbonfootprint.org/) 是一款新的开源工具，它通过云 API 提供了基于 AWS、GCP 和 Azure 使用情况的碳排放估算的可视化。
- [Code With Me](https://www.jetbrains.com/code-with-me/)。与其他远程协作工具如 VSCode Visual Studio Live Share 相比，Code With Me 为开发团队提供了更好的远程结对编程和协作体验。Code With Me 在邀请队友加入 IDE 项目并实时协作的能力值得探索。
- Comby。与jscodeshift类似，是使用抽象语法树表示进行搜索和替换代码的工具。[Comby](https://comby.dev/) 工具的独特之处，在于其简单的命令行界面，该命令行界面是根据 awk 和 sed 等 Unix 工具的精神设计的。虽然 Unix 命令基于操作匹配文本的正则表达式，但 Comby 使用特定于编程语言结构的模式语法，并在搜索之前解析代码。这有助于开发人员在大型代码库中搜索结构模式。
- Conftest。[Conftest](https://github.com/open-policy-agent/conftest) 是一款为结构化配置数据编写测试的工具。使用开放策略代理（OPA）中的策略定义语言 Rego，来为 Kubernetes 的配置文件、Tekton 的 pipeline 定义文件、甚至是 Terraform 的计划文件编写测试。
- Cosign。 [Cosign](https://github.com/sigstore/cosign) 用于容器签名及验证，不仅支持 Docker 和开放容器计划（OpenContainer Initiative，OCI）镜像，还支持可以存储在容器注册表中的其他类型镜像。之前介绍过功能类似的 Docker Notary。但 Notary v1 的问题在于需要维护单独的 Notary 服务器，而不能原生集成在容器注册表中。Cosign 将签名与镜像一起存储在注册表中，因此不存在这个问题。目前 Cosign 可以通过 Webhook 与
  GitHub actions 及 Kubernetes 集成，并可以进一步集成在流水线中。
- Crossplane。[Crossplane](https://crossplane.io/) 为我们提供了除 Terraform、CDK 或 Pulumi 这些常用方案之外的另一种选择。Crossplane为主要的云服务提供了一组预定义的 Provider，这些 Provider 涵盖最通用的配置服务。它并不是要试图成为一个通用的基础设施即代码（IaC）工具，而是要成为与 Kubernetes 部署工作相配套的工具。
- gopass。[gopass](https://www.gopass.pw/) 是一个基于 GPG 和 Git 的团队密码管理器。它以 pass 为基础，并添加了多项功能，包括交互式搜索和单个树中的多密码存储。
- Micoo。[Micoo](https://github.com/Mikuu/Micoo) 是拥挤的视觉回归测试工具赛道中又一新入竞争者，它通过提供 Docker镜像实现简单快捷的环境设置，还为 Node.js、Java 和 Python 提供了不同的客户端，以及 Cypress 插件，方便集成市面上大多数常见的前端 UI 自动化测试框架或解决方案。
- [mob](https://github.com/remotemobprogramming/mob)。对很多团队来说，远程结对编程已是常态，如果能拥有一个可以在结对或远程集体编程期间帮助无缝切换的工具会非常有效。mob 将所有版本控制的命令和工具隐藏在一个命令行界面后，这让人参与集体编程更加容易。
- 现代 Unix 命令。Unix 的哲学：应用程序应当“只做一件事，而且做好
  它”。[Modern Unix](https://github.com/ibraheemdev/modern-Unix) 仓库列举了其中的一些命令。
- [Mozilla Sops](https://github.com/mozilla/sops)。把密钥当作普通文本放到版本控制（通常是 Github）中是开发者最常犯的错误之一。在某些难以将误提交的密钥内容从遗留代码库移除的情况下，我们认为 Mozilla Sops 工具所提供的加密文本文件中的密钥功能是很有用的。过去也多次提到有类似功能的工具（Blackbox，git-crypt），而 Sops 因几项功能脱颖而出。比如，
  Sops 集成了 AWS KMS，GCP KMS，以及 Azure Key Vault 等全托管密钥存储服务，可将其作为加密密钥的来源。Sops 也支持跨平台使用，并且支持 PGP key。这使得用户可以在单个文件级别上对密钥进行细粒度的访问控制。同时，Sops 也以纯文本的方式留下了识别密钥，以便通过 git 来定位和比对文本中的原始密钥。
- [Operator Framework](https://operatorframework.io/)。Operator Framework 是一组开源工具，可简化构建和管理 Kubernetes operators 的生命周期。它通过
  Operator Lifecycle Manager 模块支持丰富的 operator 生命周期管理；通过 Operator SDK，支持多种语言，自行构建 operator 代码，并提供用于发布和共享 operator 的目录。
- Pactflow。[Pactflow](https://pactflow.io/) 可以管理那些使用 [Pact](https://github.com/pact-foundation) 编写的工作流程和持续部署测试代码，从而降低通往消费者驱动的契约测试的门槛。多个生产者和大量不同的消费者之间协作的复杂性往往会让人望而却步。
- Prefect。[Prefect](https://github.com/prefecthq/prefect) 是一款数据工作流管理工具，可以在数据管道中添加语义，如重试、动态映射、缓存和失败通知。你可以将 Python 函数标记为任务，并通过函数调用将它们串联起来构建数据流水线。
- [Proxyman](https://proxyman.io/)。当你在诊断令人讨厌的网络问题手忙脚乱时，它能帮你获得功能丰富的 HTTP 调试代理。对标 [Charles](https://www.charlesproxy.com/)。
- [Regula](https://regula.dev/)。和 Conftest 类似，Regula 以 Open Policy Agent 的 Rego 语言编写的规则，来检查基础设施代码的合法性，但它同时提供了一系列的基础类型来对基础设施配置进行特别的验证。
- [Sourcegraph](https://about.sourcegraph.com/)。一个基 于 抽 象 语 法 树 的 代 码 搜 索 工 具。与 开 源 的 Comby 相 比，Sourcegraph 是一个商业工具。Sourcegraph 特别适合在大型代码库中搜索、导航或交叉引用。可以通过 Sourcegraph 的网站访问云托管版本，进而搜索公开可用的开源存储库。
- [Telepresence](https://www.telepresence.io/)。开发人员可以用它将本地机器上运行的进程插入到远程 Kubernetes 集群中，这样本地进程可以访问远程集群的服务和特性，本地服务也可以临时替代集群中的服务。但是，请勿回避问题关键。例如，如果你是因为无法为本地开发设置所有必要的依赖而使用了 Telepresence，那应该去调查设置和架构的复杂度。如果它是你进行服务集成测试的唯一方法，那请参考**消费者驱动的契约测试**或者其他自动化的集成测试方式。
- Vite。之前介绍了esbuild，它通过编译为本机语言而非 JavaScript 来实现，显著地改善了性能。Vite 基于 esbuild 构建，相比其他工具带来了[重大改进](https://vitejs.dev/guide/why.html)。由两个部分组成：一个开发服务器，基于原生 ES 模块提供了丰富的内建功能，如极快的模块热更新（HMR）；以及一套构建指令，它使用 Rollup 打包你的代码。

### 暂缓

-

## 语言和框架

### 采纳

- Jetpack Compose。仿照苹果推出的 [SwiftUI](https://www.thoughtworks.com/cn/radar/languages-and-frameworks/swiftui)，Google 为现代 Android 应用提供了完全不同的全新用户界面构建方式 [Jetpack
  Compose](https://developer.android.com/jetpack/compose)。Compose 提供了更强大的工具和一组直观的 Kotlin API，这在多数情况下可以减少代码量，并且比起先定义静态 UI 再填充数据的传统方式，在应用运行时直接创建用户界面会更容易。
- React Hooks。React Hooks 引入了一种管理状态逻辑的新方法；鉴于 React 组件相比较类来说更接近于函数，**Hooks 接受了这一点并将状态传给函数，而不是将函数作为方法传给带有状态的类**。React 应用中状态管理的另一个主要内容是 Redux，某些时候 Redux 的复杂性并不值得。完全靠自己去引入这种实现很快会变得棘手；因此我们推荐考虑结合 React Context 以及 useContext 和 useReducer Hooks，并根据这篇[博客文章](https://blog.logrocket.com/guide-to-react-usereducer-hook/)中解释的路线来实现。

### 试验

- Arium。[Arium](https://github.com/thoughtworks/Arium) 是一个 Unity 3D 应用测试框架，以包装 Unity Test framework 而成，让您能够在多个平台上为 3D 应用编写功能测试。
- Chakra UI。[Chakra UI](https://chakra-ui.com/) 是专门为无障碍设计的一个 React.js 的 UI 组件库。深色模式以及与无障碍网页倡议（WAI-ARIA）的兼容。
- DoWhy。[DoWhy](https://github.com/Microsoft/dowhy) 是一个 Python 库，用于执行端到端的因果推理和分析。尽管利用当时存在的变量的相关性，机器学习模型可以根据事实数据进行预测。但在我们需要问如果和为什么问题的场景中，它们是不够的。
- [Gatsby](https://www.gatsbyjs.org/)。它已经相当成熟，是SSG领域的可靠选择。
- Jetpack Hilt。[Jetpack Hilt](https://developer.android.com/jetpack/androidx/releases/hilt) 提供了用于将 Hilt 与各种其他 AndroidX
  库（例如 WorkManager 和 Navigation）集成的扩展。它进一步扩展了 Hilt 的范围，为开发人员提供了一种将Dagger 依赖注入集成到 Android 应用程序中的标准方法。
- Kotlin Multiplatform Mobile。使用 [KMM](https://kotlinlang.org/docs/mobile/home.html) 时，您只需用 Kotlin 对业务逻辑和核心应用程序编写一份代码，然后即可被 Android 和 iOS 应用共享。仅在必要时（如利用原生 UI 元素），您才需要针对特定的平台编写代码，且这些特定代码保存在各个平台的不同视图中。
- lifelines。[lifelines](https://lifelines.readthedocs.io/en/latest/) 是一个用 Python 编写的进行生存分析的库。最初是为出生和死亡事件而开发的，现已演变成一个用来预测任何持续时间的完整的生存分析库。例如用来回答：这个人能活多久？
- Mock Service Worker。当测试 JavaScript 部分时，可以通过像 [Mountebank](https://www.thoughtworks.com/cn/radar/tools/mountebank) 这样的工具在网络层对其服务端进行打桩（stub）和模拟（mock）。[Mock Service Worker](https://mswjs.io/)则提供了另一种选择——在浏览器中拦截各种请求，简化了手工测试。和 Mountebank 一样，为了测试网络交互，Mock Service Worker 运行在浏览器外部的 Node.js 进程中。
- NgRx。[NgRx](https://ngrx.io/) 本质上是 Angular 的 Redux。它是一个使用 Angular 构建响应式应用的框架，可以进行状态管理并隔离副作用。强调了从 Redux 中了解到的一个权衡：**增加响应式状态管理就会增加复杂性，而只有大型应用才能利大于弊**。
- pydantic。[pydantic](https://pydantic-docs.helpmanual.io/) 用类型注解进行运行时数据验证和设置管理。当接收到 JSON 数据，并需要将其解析为复杂的 Python 结构时，pydantic 确保传入的数据与预期类型匹配，或在不匹配时报告错误。
- Quarkus。[Quarkus](https://quarkus.io/) 是为 OpenJDK HotSpot 和 GraalVM 量身定制的 Kubernetes 原生 Java 技术栈。使用 Quarkus 的同时也不得不做出一些妥协：在流水线上构建 Quarkus 需要将近 10 分钟；一些依赖注解和反射的功能（如 ORM 和序列化器）也受到了限制。这些妥协一部分是使用 GraalVM 造成的。
- React Native Reanimated 2.0。[Reanimated 2.0](https://docs.swmansion.com/react-native-reanimated/) 尝试重新构想如何在UI 线程中绘制动画；它允许我们使用 JavaScript 编写动画代码并在使用名为 animation worklets 的新 API的 UI 线程上运行它们。
- [React Query](https://react-query.tanstack.com/)。React Query 通常被描述为 React 缺失的数据获取库。数据获取，缓存，同步和服务器状态更新是很多 React应用中非常普遍的需求，但在程序中正确的实现却是众所周知的困难。React Query
  提供了一种基于 React Hooks 的简便直接的方案。
- Tailwind CSS。Tailwind CSS 使开发者不需要编写任何新的样式类或 CSS 样式。从长远来看，这将产出更易于维护的代码。Tailwind CSS 在可重用性和自定义创建可视化组件之间，提供了适当的平衡。
- [TensorFlow Lite](https://www.tensorflow.org/lite/)。将预训练模型集成到移动应用程序中的标准用法。
- Three.js。随着 WebGL API 标准的改进，以及对 WebXR 的支持，Three.js 成为了一个可以用来营造沉浸式体验的工具。与此同时，浏览器对 3D 渲染和 WebXR 设备 API 的支持也得到提升，使得 web 成为一个越来越有吸引力的 3D 内容平台。
- [ViewInspector](https://github.com/nalexn/ViewInspector)。更快地给 SwiftUI 编写单元测试，可以试试 ViewInspector 开源库，它利用 Swift 开放的反射 API 访问SwiftUI 创建的底层视图。因此，基于 ViewInspector 的测试只需要实例化一个 SwiftUI 视图，定位到需要测试的界面元素，就可以对元素进行断言测试，而像点击这种基本的交互也可以被测试到。
- [Vowpal Wabbit](https://vowpalwabbit.org/)。一个多功能的机器学习库。
- Zap。[Zap](https://github.com/uber-go/zap) 是一个用于 GoLang 的超高性能的结构化日志库，它比标准的日志实现和其他日志库更快。Zap 既有一个“漂亮”的记录器，提供了结构化和 printf 风格的接口，也有一个更快的记录器，仅提供了结构化的接口。

### 评估

- [Headless UI](https://headlessui.dev/)。Headless UI 是为 React.js 和 Vue.js 提供的无样式组件库，它和 Tailwind CSS 诞生于同一个团队。和其他自带默认样式的组件库不同，开发人员不需要订制或者调整默认样式就能开始使用。组件丰富的功能和完全的可访问性，再加上可以随意添加样式，开发人员能够更高效地专注于业务问题和用户体验。
- InsightFace。基于 PyTorch 及 MXNet 的 [InsightFace](https://github.com/deepinsight/insightface) 是开源的 2D 及 3D 深度人脸分析工具集。InsightFace 使用前沿且精确的方法进行人脸检测、人脸识别和人脸对齐。InsightFace 最吸引我们的是它为 ArcFace——用于检测两个图像相似性的前沿机器学习模型——提供了一个的最佳实现。带有 ArcFace 的 InsightFace 在 [LFW](http://vis-www.cs.umass.edu/lfw/) 数据集上获得
  了 99.83% 的准确率。
- Kats。 [Kats](https://github.com/facebookresearch/Kats) 是一个轻量级的时间序列分析框架。
- [ksqlDB](https://ksqldb.io/)。如果你正在使用 Apache Kafka 并构建流处理应用程序，ksqlDB 是一个不错的框架，用来以类似 SQL 的语句编写简单的应用程序。ksqlDB 并不是一个传统的 SQL 数据库。但它允许你在现有的 Kafka topics 上使用轻量级类似 SQL 的语句来构建新的 Kafka streams 或 tables。
- [Polars](https://github.com/pola-rs/polars)。Polars 是在 Rust 中实现的一种内存 DataFrame 库。Polars 是多线程、并行操作安全的。Polars 使用 Apache Arrow 格式作为内存模型，以高效实现分析操作，并实现与其他工具的互用性。如果您熟悉 Pandas，就可以快速上手 Polars 的 Python 绑定。
- PyTorch Geometric。[PyTorch Geometric](https://pytorch-geometric.readthedocs.io/en/latest/) 是 PyTorch 的几何深度学习扩展库。几何深度学习旨在构建能从非欧几里得数据（比如，图网络）中学习的神经网络。基于图网络的机器学习方法在社交网络建模和生物医药领域，特别是在药物发现领域，越来越受到人们关注。
- 乾坤。[乾坤](https://github.com/umijs/qiankun)是一个旨在为此提供开箱即用解决方案的 JavaScript 库。乾坤基于 single-spa，允许不同的框架共存于单一应用程序中。
- React Three Fiber。[React Three Fiber](https://github.com/pmndrs/react-three-fiber) 是一个将 React.js 组件和状态模型转译为由Three.js 渲染的 3D 对象的库。
- [Tauri](https://github.com/tauri-apps/tauri)。与捆绑 Chromium 的 Electron 不同，用 Tauri 构建的应用程序利用了底层的 WebView，即 MacOS 上的 WebKit，Windows 上的 WebView2 和 Linux 上的 WebKitGTK。利弊：一方面，你可以得到运行迅速而小巧的应用程序二进制文件；但另一方面，你需要验证不同系统中 WebView 的兼容性问题。
- Transloco。[Transloco](https://ngneat.github.io/transloco/) 是一个用来构建多语言应用程序的 Angular 库。由于翻译是在运行时按需加载的，Transloco 可以轻松地在 Web 浏览器中实现语言切换

### 暂缓

-