# vol 30

## 本期主题

### （或许）开源的许可证

一些知名工具，最近因为其维护者突然从开源模式切换到商业模式而受到了一些负面评价。当然，我们愿意为软件付费，并且认可对于额外功能使用商业许可证的常见模式。只是我们觉得，**当这种转变出现在一个受众广泛的工具的核心功能上，尤其是当一个生态系统已经围绕该工具发展起来时，这是有问题的。其次，另一个有趣的转变出现在一些自诩开源的软件上，其基本功能只有在消费者支付订阅费或其他费用后才能使用**。尽管这种商业模式以前就存在，但似乎在许多闪亮的新 AI 工具中被更多地利用了——它们提供了一些在细则之下过于隐藏的惊人功能。因此建议特别关注许可证问题。在使用中要提起警惕，并确保存储库中的所有文件都受到顶层许可证的覆盖。



### 人工智能助力软件开发团队

面向开发者的人工智能工具，如 GitHub Copilot， CodiumAI， aider 和 Continue，还探讨了如何在整个团队中全面使用 AI、以及如何利用人工智能改变软件开发的各个方面。



### 涌现的大语言架构模式

技术领域，“模式”因为能够为特定问题提供一个简洁的解决方案名称而受到欢迎。随着大语言模型（LLMs）的日益普及，我们开始看到支持常见上下文的特定架构模式不断涌现。

- NeMo Guardrails，它允许开发人员围绕 LLM 的使用建立治理政策。
- 像 Langfuse 这样的工具，它们能够更好地观察 LLM 的输出步骤，并知道如何处理（并验证）充斥着生成代码的臃肿代码库。
- 如何使用检索增强生成（RAG），这是我们偏爱的模式，以提高 LLM 输出的质量，在企业生态系统中尤其有效。



### 让 Pull Request（PR）更接近正确的持续集成

尽管 PR 的做法**最初是为了管理大规模分布式的开源团队和不可靠的贡献者**，然而目前已经发展成了同行评审（Peer Review，PR）的同义词，即使在紧密工作的小型团队也是如此。在这些情况下，许多开发者渴望从实践真正的 CI 中获得相同的流畅感。

存在减轻 PR 审查过程痛苦的工具，包括 gitStream 和 Github 合并队列。我们
还讨论了诸如 stacked diffs 之类的技术，这些技术通过使集成过程更精细化，有望与 CI 的核心原则保持一致。此外，我们还探讨了从 PR 中提取度量的方法，以识别软件交付过程中的低效和瓶颈。工具在这个领域会起到巨大帮助，因为整体趋势是朝向生成式 AI 编程的。随着 AI 编码助手的出现，编码吞吐量增加，导致倾向于创建更大的 PR。这给异步代码审查过程增加了更大的压力。



## 技术

### 采纳

- [检索增强生成（RAG](https://arxiv.org/abs/2005.11401v4)）。检索增强生成（Retrieval-augmented generation， RAG） 是一种提高大语言模型（LLM）生成响应质量的模式。

### 试验

- 自动生成 Backstage 实体描述符。[Backstage](https://www.thoughtworks.com/cn/radar/platforms/backstage) 是一个托管插件，在托管的同时提供管理构成平台生态系统资产目录的界面的 shell。任何由 Backstage 显示或管理的实体都在 catalog-info 文件中配置，其中包含状态、生命周期、依赖关系和 API 等其他细节的数据。
- 将传统 NLP 与 LLMs 相结合。将传统 NLP 与 LLMs 相结合 ，或者在将多种 NLP 与 LLMs 相结合，在实现用例并利用 LLMs 的实际需求能力的步骤方面有很大的潜力。传统的数据科学和 NLP 方法，例如文档聚类、主题识别和分类，甚至摘要生成，成本更低且可能更有效地解决你的使用案例问题的一部分。然后，在需要生成和总结较长文本，或将多个大型文档合并时，可以使用 LLMs，以利用其较高的注意力跨度和记忆力。
- 持续合规。持续合规 是一种实践，旨在确保软件开发过程以及相关技术一直遵守行业法规和安全标准，这一过程大量依赖自动化，人工操作可能会降低开发速度并引入错误。作为替代，组织可以自动化合规检查和审计。
- 边缘函数。通过内容交付网络（CDNs）进行去中心化代码执行的可用性和使用量正在增
  长。诸如 Cloudflare Workers 或 Amazon CloudFront Edge Functions 这样的服务提供了一种机制，在靠近客户地理位置的地方执行无服务器代码片段。 边缘函数不仅可以在边缘生成响应时提供更低的延迟，还可以在请求和响应从区域服务器出发和返回的途中，以特定位置的方式重写它们。
- 安全标兵。安全标兵指的是团队成员中对技术和非技术交付决策的安全后果持有批判性思维的人。安全标兵不是一个单独的职位，而是分配给现有团队成员的责任，这些成员需要由安全从业者进行培训指导。安全标兵通过传播知识，并作为开发团队和安全团队之间的桥梁，提高团队的安全意识。安全标兵所做的事情中一个非常好的活动示例是威胁建模，它帮助团队从一开始就将安全风险考虑在内。在团队中任命和培训安全标兵是一个很好的开始，但仅仅依赖标兵而没有来自领导层的适当投入可能会导致问题。
- Text to SQL。Text to SQL 是一种用于将自然语言查询转换为可以由数据库执行的 SQL 查询的技术。尽管大语言模型能够理解和转换自然语言，但在你自己的 schema 中创建准确的 SQL 仍然存在很大的挑战。为此可以引入 [Vanna](https://github.com/vanna-ai/vanna)，它是一个在 Python 中用于 SQL 生成的检索增强生成（RAG）开源框架。Vanna 分两步工作：1.使用数据定义语言语句（DDLs）和示范 SQL 描述你的结构，并为它们创建嵌入向量，2.然后再用自然语言提出问题。尽管 Vanna 可以与任何大语言模型协作，也推荐你评估 [NSQL](https://www.numbersstation.ai/post/introducing-nsql-open-source-sql-copilot-foundation-models)，它是一个用于 Text to SQL 任务的领域特定大语言模型。
- 追踪健康债务状况。通过将健康度评级与其他服务级目标（SLO）同等对待，并据此确定增强的优先级，而不是仅仅关注跟踪技术债务，可以体验到团队对其生态系统的改进。在瞬息万变的数字环境中，专注于跟踪系统的健康状况与债务 ，可为维护和增强系统提供结构化的循证战略。

### 评估

- 人工智能团队助理。你应该寻找创建 AI 团队助理 的方法来帮助创建“10 倍团队”，而不是一群孤立的 AI 辅助的 10 倍工程师。标准化的提示有助于在团队环境中使用已经达成共识的最佳实践，例如编写用户故事的技术和模板，或实施威胁建模等实践。除了提示之外，通过检索增强生成提供的知识源，可以从组织指南或行业特定的知识库中提供与上下文相关的信息。
- 对 LLM 对话进行图分析。对 LLM 对话进行图分析（graph analysis for LLM-backed chats） 。那些支持特定期望结果的聊天代理 — **如购物行为或成功解决客户问题 — 通常可以表示为一个期望的状态机**。通过将所有对话加载到一个场景中，你可以分析它实际所处的模式，并寻找与预期状态机的偏差。这有助于发现错误和进行产品改进。
- 基于大语言模型的 ChatOps。基于大语言模型的 ChatOps** 是通过聊天平台（主要是 Slack）应用大语言模型的新兴方式，允许工程师通过自然语言来构建、部署和操作软件。
- 大语言模型驱动的自主代理。随着像 Autogen 和 CrewAI 这样的框架的出现， LLM（大型语言模型）驱动的自主代理 正在从单一代理和静态多代理系统发展到更先进的阶段。这些框架允许用户定义具有特定角色的代理，分配任务，并使代理通过委派或对话合作完成这些任务。
- 使用 GenAI 理解遗留代码库。利用 GenAI 理解遗留代码库 。这在遗留代码库文档记录不完整、或者过时的文档具有误导性时尤其有用。例如，[Driver AI](https://www.driverai.com/) 或 [bloop](https://bloop.ai/) 使用了 RAG ，结合了语言智能、代码搜索与 LLMs，以帮助用户在代码库中定位自己的位置。更大的上下文窗口的新兴模型也将帮助这些技术更适配大型代码库。
- VISS。Zoom 最近开源了其漏洞影响评分系统（Vulnerability Impact Scoring System）—— [VISS](https://github.com/zoom/viss)。这个系统主要关注的是对安全措施的漏洞评分的优先级排序。VISS 与通用漏洞评分系统（CVSS）的不同之处在于，它不侧重于对最坏情况进行预测，而是试图从防御者的角度更客观地衡量漏洞的影响。这种基于行业上下文的优先级定制的评估方法是值得考虑的。

### 暂缓

- 广泛集成测试。这种测试指的是**需要所有运行时依赖项的实时版本的测试**。这样的测试显然是昂贵的，因为它需要具备所有必要基础设施、数据和服务的全功能测试环境。管理所有这些依赖项的正确版本需要大量的协调工作，这往往会拖慢发布周期。最后，测试本身通常是脆弱且无用的。
- 过度热衷使用大语言模型。当 LLM-based 的解决方案多少能够工作时，就迅速部署并转向下一个任务，这可能颇具诱惑力。尽管这些基于 LLM 的价值证明是有用的，但团队应该仔细考虑所使用的技术以及是否 LLM 真的是正确的最终阶段解决方案。许多 LLM 可以解决的问题——如
  情感分析或内容分类——传统的自然语言处理（NLP）可以更便宜、更容易地解决。
- 急于冲向大语言模型微调（fine-tune LLMs）。最常见误用是为了让 LLM 应用程序了解特定的知识、事实或组织的代码库进行微调。**在绝大多数场景下，使用检索增强生成（RAG）可以提供更好的解决方案和更优的投入产出比**。微调需要大量的计算资源和专家能力，并且比 RAG 面临更多敏感和专有数据挑战。此外当你没有足够的数据进行微调时，还有欠拟合（underfitting）的风险。又或者，当你拥有太多数据时（这倒不太常见），出现过拟合（overfitting）风险。总之达到你所需要任务专业性的正确平衡是比较困难的。
- 适用于 SSR 网络应用程序的 Web 组件。随着 Next.js 和 htmx 等框架逐步被采纳，服务端渲染（SSR）的使用越来越多。作为一种浏览器技术，在服务器上使用 web 组件并非易事。为了简化这一过程，许多框架有时甚至使用浏览器引擎来执行操作，但复杂性依然存在。有时开发人员需要绕过一些障碍并付出额外努力来整合前端组件和服务端组件。比开发者体验更糟糕的是用户体验：当自定义 web 组件需要在浏览器中加载和填充时，页面加载性能会受到影响，即使使用预渲染和谨慎调整组件，未样式化内容的闪现或一些布局移动几乎不可避免。



## 平台

### 采纳

- CloudEvents。[CloudEvents](https://cloudevents.io/) 是一种规范，用于以通用格式描述事件数据，以实现跨服务、平台和系统的互操作性。它提供了多种语言的 SDK，你可以将该规范嵌入到你的应用程序或工
  具链中。CloudEvents 由云原生计算基金会 （CNCF） 托管，现已成为一个毕业项目。

### 试验

- 云上 Arm。由于与传统基于 x86 的实例相比更具有成本和能源效率优势，[云上的 Arm 计算实例](https://www.arm.com/markets/computing-infrastructure/cloud-computing)变得越来越受欢迎。许多云服务提供商现在都提供基于 Arm 的实例，包括 AWS、Azure 和 GCP。 云上 Arm 的成本优势对于运行大型工作负载或需要扩展的企业特别有利。
- Azure Container Apps。[Azure Container Apps](https://azure.microsoft.com/en-us/products/container-apps) 是一个托管的 Kubernetes 应用平台，能够简化容器化工作负载的部署。与 Azure Kubernetes Service （AKS） 相比，运行容器化应用程序的操作和管理负担相对较少，但这是以牺牲一些灵活性和控制权为代价的，也是团队需要权衡的。
- Azure OpenAI Service。[Azure OpenAI Service](https://azure.microsoft.com/en-us/products/ai-services/openai-service/) 通过 REST API、Python SDK 和基于 Web 的界面提供对 OpenAI 的 GPT-4、GPT-3.5-Turbo、Embeddings、DALL-E 模型等的访问。这些模型可以适用于内容生成、提取摘要、语义搜索和将自然语言转换为代码等任务。
- [DataHub](https://datahubproject.io/)。在使用数据产品思维 构建产品时，数据血缘、数据可发现性、数据治理非常重要。DataHub在这些方面能提供非常有效的支持。
- 基础设施编排平台。组织内部的基础设施编排代码库频繁地成为维护和排查故障的时间黑洞。 基础设施编排平台 应运而生，试图将基础设施代码交付和部署工作流的各个方面变得标准化和产品化。其中包括构建一些工具，例如 Terragrunt、Terraspace；以及 IaC 工具供应商的服务，如 Terraform Cloud 和 Pulumi Cloud，以及与工具无关的平台和服务，如 env0 和 Spacelift。
- Pulumi。在基础设施即代码领域，工具仍在不断进化， Pulumi 也不例外。该平台最近新增了对 Java和 YAML 的支持，用于管理大规模基础设施，以及支持众多云配置和集成。
- Rancher Desktop。[Rancher Desktop](https://rancherdesktop.io/) 对那些不想放弃 Docker Desktop 提供的图形界面的用户很有吸引力。像 Colima 一样，Rancher Desktop 允许你选择 dockerd 或 containerd 作为底层容器运行时。选择直接使用 containerd 可以让你摆脱对 DockerCLI 的依赖，但 dockerd 选项也提供了与其他工具的兼容性，这样可以与运行时守护进程进行通信。
- Weights & Biases。[Weights & Biases](https://wandb.ai/) 是一个机器学习（ML）平台，它通过实验跟踪、数据集版本控制、模型性能可视化和模型管理来帮助更快地构建模型。

### 评估

- Bun。Bun 是一个新的 JavaScript 运行时，类似于 Node.js 或 Deno。与 Node.js 或 Deno 不同，Bun 使用WebKit 的 JavaScriptCore 而不是 Chrome 的 V8 引擎来构建。作为 Node.js 的替代品设计，Bun 是一个单一的二进制文件（用 Zig 编写），充当 JavaScript 和 TypeScript 应用程序的打包器、转译器和包管理器。
- [Chronosphere](https://chronosphere.io/)。在管理分布式架构时，考虑排序、索引和访问数据的成本与可观测性同样重要。Chronosphere 使用了独特的方法来管理成本、跟踪可观测数据的使用情况，以便组织可以考虑并权衡各种指标的成本价值。借助 Metrics Usage Analyzer，作为 Chronosphere Control Plane 的一部分，团队可以识别并排除他们很少（或从未）使用的指标，进而通过减少组织必须梳理的数据量来节省大量成本。
- DataOS。[DataOS](https://themoderndatacompany.com/dataos/) 是这样一款产品，它提供了从设计、构建、部署到演进数据产品的端到端生命周期管理。它提供标准化的声明式规范（用 YAML 编写），抽象了底层基础设施设置的复杂性，允许开发人员通过 CLI/API 轻松定义数据产品。它支持访问控制策略与 ABAC 以及数据策略用于过滤和脱敏数据。另一个值得注意的特性是它将数据联邦化到多种数据源的能力，这减少了数据重复和数据向中心地点的转移。
- Dify。[Dify](https://github.com/langgenius/dify) 是一个 UI 驱动的用于开发大语言模型应用程序的平台，它使原型设计更加容易访问。它支持用户使用提示词模板开发聊天和文本生成应用。
- Elasticsearch Relevance Engine。借助 Elasticsearch Relevance Engine（ESRE），成熟
  的全文搜索平台 Elasticsearch 支持了内置和自定义嵌入模型、向量搜索以及具有如倒数排序融合（Reciprocal Rank Fusion）等排名机制的混合搜索。
- FOCUS。云和 SaaS 计费数据，不同供应商之间存在许多不一致。 FinOps Open Cost and Usage Specification （FOCUS） 旨在通过提供包含一组专业术语的规范（和 FinOps framework 对齐）、一个模式和一组最低要求的计费数据来减少此类问题。
- Gemini Nano。Google 的 Gemini 是一系列基础性大语言模型，用于在从数据中心到手机等各种硬件上运行。其团队已经对Gemini Nano 做了特别对优化和简化，以便在移动芯片加速器上运行。它可以实现高质量的文本摘要、上下文智能回复和高级语法纠正等功能。
- HyperDX。[HyperDX](https://github.com/hyperdxio/hyperdx) 是一个开源的可观测性平台，整合了可观测性的三大支柱：日志、指标和追踪。在它的帮助下，可以实现端到端的关联，并且只需几次点击就能从浏览器会话回放跳转到日志和追踪信息。
- IcePanel。[IcePanel](https://icepanel.io/) 通过使用 C4 模型，促进了协作式的架构建模和图表绘制，使技术和业务利益相关者能够根据需要深入到所需的技术细节级别。它支持建模架构对象，这些对象的元数据和连接可以在多个图表中重用，并且能够将这些对象之间的交互 可视化出来。
- Langfuse。[Langfuse](https://langfuse.com/) 是一个用于观察、测试和监控大语言模型应用的工程平台。
- Qdrant。[Qdrant](https://github.com/qdrant/qdrant) 是一个使用 Rust 实现的开源向量数据库。 
- RISC-V 用于嵌入式。随着 Arm 架构继续扩大其影响力，对更新但不那么成熟的RISC-V 架构的兴趣也在增长。RISC-V 并没有在性能或效率方面带来突破 — 实际上，它的每瓦性能与 Arm 相
  似，且在绝对性能上无法与之竞争 — 但它是开源的、模块化的，且不依赖于单一公司。
- Tigerbeetle。[Tigerbeetle](https://github.com/tigerbeetle/tigerbeetle) 是一个用于财务会计的开源分布式数据库 。与其他数据库不同的是，它被设计成特定领域的状态机，以确保安全性和性能。集群中一个节点的状态通过 Viewstamped Replication 共识协议，以确定性顺序复制到其他节点。
- [WebTransport](https://developer.chrome.com/docs/capabilities/web-apis/webtransport)。WebTransport 是一种建立在 HTTP/3 之上的协议，提供了服务器与应用之间的双向通信。与其前身 WebSocket 相比，WebTransport 具有很多优势，包括更快的连接、更低的延迟，以及能够处理可靠且有序的数据流以及无序数据流（例如 UDP）。它可以在同一连接中处理多个流，而不会造成队头阻塞（head-of-line blocking），进而实现在复杂应用中更高效地通信。
- Zarf。[Zarf](https://github.com/defenseunicorns/zarf) 是一个用于离线和半连接 Kubernetes 环境的声明式软件包管理器。使用 Zarf，可以在连接到互联网时构建和配置应用程序；一旦创建完成，可以将其打包并发送到断开连接的环境以进行部署。
- ZITADEL。[ZITADEL](https://github.com/zitadel/zitadel) 是一个开源的用户身份管理工具，也是 Keycloak 的替代品之一。它非常轻量级（用 Golang 编写），拥有灵活的部署选项，并且易于配置和管理。它还是多租户的，能够提供全面的功能以构建安全且可扩展的认证系统，特别是对于 B2B 应用程序，并且具有内置的安全功能，如多因素认证和审计追踪。

### 暂缓

-



## 工具

### 采纳

- Conan。[Conan](https://conan.io/) 是一个用于 C/C++ 应用程序的开源依赖管理工具。它提供了直观的界面来定义、获取和管理依赖，使开发人员能够轻松地将第三方库集成到他们的项目中。

- Kaniko。[kaniko](https://github.com/GoogleContainerTools/kaniko) 是一种从容器或 Kubernetes 集群内的 Dockerfile 构建容器映像的工具。

  kaniko 不依赖于 Docker 守护程序，而是完全在用户空间中执行 Dockerfile 中的每个命令。这样可以在无法轻松或安全地运行 Docker 守护程序的环境中构建容器映像，例如标准 Kubernetes 集群。

- Karpenter。Kubernetes 的一个基本功能是水平自动伸缩：它能够在需要额外容量时启动新的 pods，并在负载减少时将它们关闭。然而，这只有在需要托管 pods 的节点已经存在时才有效。[Cluster Autoscaler](https://github.com/kubernetes/autoscaler/tree/master/cluster-autoscaler) 可以做一些基本的集群扩展，由 pod 故障触发，但它的灵活性有限；[Karpenter](https://karpenter.sh/) 是一个更智能的、开源的 Kubernetes Operator节点自动伸缩器：它分析当前工作负载和 pod 调度约束，选择适当的实例类型，然后根据需要启动或停止它。

### 试验

- [42Crunch API Conformance Scan](https://42crunch.com/api-conformance-scan/)。42Crunch API Conformance Scan 是一个动态测试工具，用**于识别 API 文档中记录的行为与其实际实现之间的差异**。此工具使用 OpenAPI 格式的 API 规格定义，概述了预期的功能和响应，并将其与 API 的实际行为进行比较。通过生成真实流量并与现场端点交互，该工具能够识别 API 承诺与其实际提供之间的任何差异。这为开发团队带来了很多好处。例如，它能在开发早期捕捉到不一致性，节省时间并防止问题进入生产环境。

- [actions-runner-controller](https://github.com/actions-runner-controller/actions-runner-controller)。actions-runner-controller 是一个 Kubernetes [控制器](https://kubernetes.io/docs/concepts/architecture/controller/)，用于操作 GitHub Actions 的[自托管运行器](https://docs.github.com/en/actions/hosting-your-own-runners)。在需要访问 GitHub cloud runner 无法访问的资源，或具有与 GitHub 提供的不同的特定操作系统和环境要求的情况下，自托管运行器非常有帮助。

- Android 模拟器容器。[Android Emulator Container](https://github.com/google/android-emulator-container-scripts) 通过消除因操作系统兼容性问题和系统依赖，以及设置多个 Android 版本的模拟器所带来的复杂性，使得 Android 应用测试变得更为简便。

- AWS CUDOS。应当将成本监控列为适应性函数。云服务商提供了各种监控云消费的服务，例如 AWS Cost Explorer 或 Google Cloud FinOps Hub。在 AWS 生态系统中，可以使用 [CUDOS（Cost and Usage Dashboards Operations Solution）](https://aws.amazon.com/cn/blogs/awsmarketplace/using-cudos-dashboard-visualizations-aws-marketplace-spend-visibility-optimization/)来监控大型母公司下不同业务部门或法律实体在 AWS Marketplace 的消费。

- aws-nuke。[aws-nuke](https://github.com/rebuy-de/aws-nuke) 是一个开源工具，解决了开发和沙箱 AWS 账户中积累未使用资源导致成本效率低下的常见挑战。该工具可以识别并删除 AWS 账户或区域内所有可删除的资源，除了默认或 AWS 管理的资源，本质上是要将环境重置为第一天的状态。

- Bruno。[Bruno](https://github.com/usebruno/bruno) 是 [Postman](https://www.thoughtworks.com/cn/radar/tools/postman) 和 [Insomnia](https://www.thoughtworks.com/cn/radar/tools/insomnia) 的开源桌面替代品，用于 API 的测试、开发和调试。它将测试集合保存在本地，因此可以使用 Git 或其他版本控制工具来进行协作。

- Develocity。[Develocity](https://gradle.com/develocity/) （之前是 Gradle Enterprise） 解决了大型软件项目长时间的构建和测试周期的痛点。它采用构建缓存和预测试选择来提升性能从而缩短开发人员在本地和 CI/CD 环境中的反馈循环。

- GitHub Copilot。AI 编码辅助市场的领先地位。

- Gradio。[Gradio](https://github.com/gradio-app/gradio) 是一个开源的 Python 库，它能帮助机器学习（ML）模型创建基于 web 的交互式界面。ML 模型上的图形 UI 能够帮助非技术受众更好地理解输入、约束和输出。Gradio 在生成式人工智能领域获得了大量关注，因为它让生成式模型更易于尝试和使用。

- [Gradle Version Catalog](https://docs.gradle.org/current/userguide/platforms.html)。Gradle 版本目录是 Gradle 构建工具的一个有用的功能，允许你集中管理构建文件中的依赖项。它在 Android 多模块项目中特别有用。你可以为这些依赖项创建一个中央版本目录，然后在 Android Studio的帮助下以一种类型安全的方式引用它，而不是在单独的构建文件中硬编码依赖项名称和版本号并管理升级。

- Maestro。[Maestro](https://maestro.mobile.dev/) 在测试移动应用程序中的复杂流程时非常有用。它易于学习和理解，并且可以很方便地集成到我们的开发工作流程中。Maestro 支持一系列移动平台，包括 iOS、Android、React Native 和 Flutter 应用。其声明式的 YAML 语法简化了复杂移动 UI 交互的自动化。

- Microsoft SBOM 工具。[Microsoft SBOM 工具](https://github.com/microsoft/sbom-tool) 是一个开源工具，用于生成与 SPDX 兼容的软件物料清单（SBOM）。这个 SBOM 工具支持多种流行的包管理器（包括 npm、pip 和 Gradle），所以可以与许多项目兼容。

- 开放策略代理 （OPA）。[Open Policy Agent （OPA](https://www.openpolicyagent.org/)）是一个统一的框架和语言，用于声明、执行和控制策略。它是定义分布式系统策略的一种常用方式。

- [Philips's self-hosted GitHub runner](https://github.com/philips-labs/terraform-aws-github-runner)。这是一个 Terraform 模块，可以在 AWS EC2 竞价实
  例（spot instance）上启动自定义运行器。该模块还创建了一组 Lambda 用于处理这些运行器的生命周期管理（扩展和缩减）。对于使用 Kubernetes 的团队来说，另一种选择是 actions-runner-controller。

- [Pop](https://pop.com/)。在不可避免要进行线上结对时，有很多工具可以选择，比如 Tuple， Visual Studio Live Share， Code With Me ，和通用的聊天及会议工具。 Pop （前身为 Screen）来源于 Screenhero 的创建者，支持多人屏幕分享、编写注释和进行高质量音频 / 视频通话。

- [Renovate](https://www.mend.io/renovate/)。作为软件构建过程的一部分，自动监控和更新依赖项已成为整个行业的标准实践。

  多年来，Dependabot 一直是这一实践的标准工具，但 Renovate 逐渐成为许多团队的第一选择。现 Renovate 更适合现代软件开发环境，其中可部署的系统不仅依赖于代码和库，还包括运行时工具、基础设施和第三方服务。除代码外，Renovate 还涵盖了对这些辅助工件的依赖性。

- Terrascan。[Terrascan](https://github.com/tenable/terrascan) 是一个用于基础设施即代码 （IaC） 的静态代码分析器，旨在云原生基础设施部署之前检测安全漏洞和合规问题。 它支持对 Terraform，Kubernetes （JSON/YAML），Helm，AWS CloudFormation， Azure Resource Manager， Dockerfiles 和 GitHub 进行扫描。

- Velero。[Velero](https://velero.io/) 是一个用于备份和恢复 Kubernetes 资源和持久卷的开源工具。它通过启用按需和计划备份，简化了灾难恢复和集群迁移。Velero 能更精细地控制哪些资源被备份，以及相关的备份/恢复流程。它依赖的是 Kubernetes API 而非更低层次的 etcd 。

### 评估

- aider。[aider](https://github.com/paul-gauthier/aider) 是一款开源的 AI 辅助编码工具。aider 并不直接与 IDE 集成，而是以 CLI 形式在终端启动。目前许多辅助编码工具只能读取代码，或者一次只能更改一个文件，而 aider 提供了一个聊天界面，并且有写权限来对多个文件的代码库进行访问。
- Akvorado。[Akvorado](https://github.com/akvorado/akvorado) 是一个开源的网络监控和分析工具。它可以捕获网络流量，支持 Netflow/IPFIX 和 sFlow 协议，为其添加接口名称和地理信息，然后将更新后的流量保存在 ClickHouse 中供未来分析使用。
- 百川 2。[百川 2](https://github.com/baichuan-inc/Baichuan2/blob/main/README_EN.md) 是新一代开源大型语言模型。 它采用了 2.6 万亿 Tokens 的高质量语料进行训练，在中文、英文和多语言基准测试上表现出色。 百川在医疗和法律等领域特定的数据集进行了针对性训练。
- Cargo Lambda。Rust 的高效和性能很适合用于无服务器计算，它还有函数无需运行时的优势，这些都使得它能快速启动工作。 但是使用 Rust 开发 lambda 函数的体验此前并不好，这一情况被 [Cargo Lambda](https://www.cargo-lambda.info/) 的出现改变了。作为 cargo 的子命令，它能集成到典型的 Rust 工作流中并且能允许你在开发机上运行和测试 AWS Lambda 函数而无需借助 Docker、虚拟机，或者其他工具。 
- Codium AI。[Codium AI](https://www.codium.ai/) 聚焦于使用 AI 生成代码测试。可以用于所有编程语言，但对常用技术栈如 JavaScript 和 Python 提供了更进阶的支持。
- Continue。[Continue]() 是一种用于 VS Code 和 JetBrains IDEs 的开源 AutoPilot 工具。它通过与 IDE 的直接集成，消除了从聊天界面复制 / 粘贴到大型语言模型的痛苦。
- [Fern Docs](https://buildwithfern.com/#docs)。Fern 可以自动从规范文件生成一个具有吸引力、可用文档的网站，这个文件能与 API 代码一起版本化。不过目前Fern 要求用户在一个专有配置文件中维护 API 信息，这点不够直接，期待它能在后续版本中能够直接送带注释的源代码生成文档。
- Granted。 [Granted](https://github.com/common-fate/granted) 作为一个命令行工具，简化了同时在浏览器中打开多个账户的操作，使账户切换变得更加流畅。它利用浏览器的原生功能来隔离多个身份，例如 Firefox 的 多账户容器 和 Chromium 的配置文件。
- LinearB。[LinearB](https://linearb.io/) 是一个旨在为工程领导者提供数据驱动洞察以实现持续改进的平台。它主要在三个关键领域发挥作用：基准测试、工作流自动化和投资。
- LLaVA。[LLaVA（Large Language and Vision Assistant）](https://github.com/haotian-liu/LLaVA) 是一个开源的大型多模态模型，它结合了视觉编码器和大语言模型，用于通用视觉和语言理解。LLaVA 在遵循指令方面的强大能力，使其成为多模态人工智能模型中的有力竞争者。
- Marimo。[Marimo](https://marimo.io/) 通过优先考虑复用性和交互性，为 python notebook 提供了新的体验。它解决了传统 notebook 如 Jupyter 中隐藏状态（hidden state）的挑战， 该问题可能导致不可预料的结果并阻碍可复用性。Marimo 通过将 notbook 存储为无隐藏状态的纯 python 文件和基于依赖关系（当变量改变时，所有受影响的 cell 会自动运行）的确定性执行顺序来解决这个问题。
- [Mixtral](https://docs.mistral.ai/models/#mixtral-8x7b)。Mixtral 是 Mistral 发布的开放权重大语言模型家族的一部分，它采用了稀疏混合专家架构。这个模型家族以 7B和 8x7B 参数大小的形式，提供原始预训练和微调版本。其大小、开放权重特性、基准测试中的性能以及 32,000个 token 的上下文长度，使其成为自托管大语言模型中一个非常耀眼的选择。
- [NeMo Guardrails](https://github.com/NVIDIA/NeMo-Guardrails)。NeMo Guardrails 是 NVIDIA 的一个易用开源的工具包，可以使开发人员在会话应用的大语言模型上实现一套防护措施。尽管大语言模型在构建交互式体验上有巨大的潜力，但他们在事实准确性、偏见和潜在的滥用方面上存在一些固有的局限性，因此有必要采取一些必要的保护措施。
- [Ollama](https://github.com/ollama/ollama)。Ollama 是一个在本机上运行并管理大语言模型的工具。。Ollama 支持多种 流行的模型 的下载和本地运行——包括 LLaMA-2，CodeLLaMA， Falcon 和 Mistral。你可以通过命令行、接口或者开发组件与模型交互执行任务。 Ollama目前看起来不错，能通过在本机运行大语言模型提升开发者体验。
- OpenTofu。[OpenTofu](https://opentofu.org/) 是对 Terraform 的一个分支，作为对 HashiCorp 最近模糊不清的许可证更改的回应。它是开源的，并已被 Linux Foundation 接受。
- QAnything。[QAnything](https://github.com/netease-youdao/QAnything/tree/master) 是一个问答界面的知识管理引擎，能够从包括 PDF、DOCX、PPTX、XLSX 和 MD 文件等在内的多种文件格式中总结和提取信息。出于数据安全考虑，QAnything 还
  支持离线安装。可以使用 QAnything 来构建团队知识库。
- [System Initiative](https://www.systeminit.com/)。近年来，几乎没有新兴工具能挑战 Terraform 作为基础设施即代码工具的主导地位。尽管出现了 Pulumi、CDK以及最近的 Wing 等替代方案，但 Terraform 的模块化、声明式范式已被证明是最持久流传的。所有这些方法都有一个共同的目标，**即模块化代码创建单一的基础设施**。
- Tetragon。[Tetragon](https://github.com/cilium/tetragon/) 是一个基于 eBPF 的开源安全可观测性和运行时强制执行工具。
- [Winglang](https://www.winglang.io/)。基础设施即代码方面，Winglang 采取了一种不同的方法来定义基础设施和运行时行为。它提供了高级抽象，覆盖了由 CloudFormation、Terraform、Pulumi 和 Kubernetes 等工具提供的平台特定功能。

### 暂缓

-



## 语言和框架

### 采纳

-

### 试验

- [Astro](https://astro.build/)。**可以使用 Astro 构建诸如博客和营销网站之类的内容驱动型网站**。Astro 是一个多页面应用程序框架，它在服务器上渲染 HTML 并最小化通过网络发送的 JavaScript。
- [DataComPy](https://github.com/capitalone/datacompy)。对数据帧进行比较是数据工程中的常见任务，常用于确保两个数据转换方法间没有显著的偏差或不一致。DataComPy 是一个用于比较 pandas， Spark 或其他格式 DataFrame 的工具。这个库不仅能比较 DataFrame 的一致性，还能在行和列上对不一致的地方给出细致的洞见。
- Pinia。[Pinia](https://pinia.vuejs.org/) 是一个在 Vue.js 中使用的存储库和状态管理框架。它使用声明性语法并提供了自己的状态管理 API。与Vuex 相比，Pinia 提供了一个更简单的 API，简化了使用流程，提供了组合式 API，并且在与 TypeScript 一起使用时具有可靠的类型推断支持。
- Ray。机器学习（ML）的工作负载正在变得越来越计算密集型。尽管用笔记本电脑开发训练模型很便利，但这样的单节点开发环境很难适应扩展需求。[Ray](https://github.com/ray-project/ray) AI 和 Python 代码从笔记本电脑扩展到集群的统一框架。它本质上是一个封装良好的分布式计算框架，集成了一系列 AI 库以简化 ML 的工作。通过与其他框架（例如，PyTorch 和TensorFlow 的集成，它可以用于构建大规模 ML 平台。

### 评估

- 安卓适应性。[Android Adaptability](https://developer.android.com/codelabs/adaptability-codelab) 是一套允许移动程序开发者根据动态的设备性能和散热情况
  进行调校的新类库集。安卓动态性能框架（ ADPF ） 集成了提供设备热量信息的 Thermal API、帮助安卓系统选择 CPU 的最佳运行点以及核心配置的 Hint API。
- Concrete ML。完全的**[同态加密](https://en.wikipedia.org/wiki/Homomorphic_encryption)** (HE)是指一类允许在加密数据上直接进行计算操作（如搜索和算数运算）的加密方法。同时计算的结果仍然以加密的形式存在，并且稍后可以对其进行解密和显示。虽然同态加密问题早在1978年就被提出来了，但直到2009年才出现解决方案。[Concrete ML](https://github.com/zama-ai/concrete-ml) 就是这样一个允许在隐私保护的环境下进行机器学习的开源工具。
- Crabviz。[Crabviz](https://github.com/chanhx/crabviz) 是一个用于创建调用图的 Visual Studio Code 插件。这些图表是交互式的，这在使用中等规模的代码库（例如微服务）时起到了很大的作用。它们按文件分组显示类型、方法、函数和接口，并显示函数调用关系和接口实现关系。
- Crux。[Crux](https://github.com/redbadger/crux) 是一个用 Rust 编写的开源跨平台应用开发框架。受到 Elm 架构的启发，Crux 将业务逻辑代码作为核心，UI 层则使用了原生框架（如 SwiftUI、Jetpack Compose 以及 React/Vue），或者是基于 WebAssembly 的框架（如 Yew）。
- Databricks Asset Bundles。Databricks 最近发布了 [Databricks Asset Bundles （DABs）](https://docs.databricks.com/en/dev-tools/bundles/index.html) 的 公开预览版，它包含在 Databricks CLI 0.205 及更高版本中，正成为打包 Databricks 资源进行源代码控制、测试和部署的官方推荐方式。DABs 可以用来取代 [dbx](https://dbx.readthedocs.io/en/latest/intro/)。
- Electric。[Electric](https://github.com/electric-sql/electric) 是一种用于移动和 Web 应用程序的本地优先同步框架。本地优先是一种开发范式，其中应用程序代码直接与嵌入式本地数据库对话，并通过双活数据库复制到中央数据库，在后台进行数据同步。使用 Electric，您可以将 SQLite 作为本地嵌入式选项，并将 PostgreSQL 作为中央存储。
- LiteLLM。[LiteLLM](https://github.com/BerriAI/litellm) 是一个库，通过 OpenAI API 格式 的标准化交互实现与各种大语言模型（LLM）提供商的 API 的无缝集成。它广泛支持各类提供商和模型，并具备一个用于完成、嵌入和图像生成功能的统一界面。LiteLLM 通过将输入转换为匹配每个提供商特定端点要求的方式，简化了集成。
- LLaMA-Factory。[LLaMA-Factory](https://github.com/hiyouga/LLaMA-Factory) 是一个开源的、易于使用的 LLMs 微调和训练框架。支持 LLaMA、BLOOM、Mistral、Baichuan、Qwen 和 ChatGLM，它使微调等复杂概念相对容易理解。
- MLX。[MLX](https://github.com/ml-explore/mlx) 是一个开源的数组框架，专为在苹果芯片上进行高效灵活的机器学习而设计。它使得数据科学家和机器学习工程师可以访问集成的 GPU，并在这个基础上选择最适合其需求的硬件。MLX 的设计灵感来自于诸如 NumPy、PyTorch 和 Jax 这样的框架。MLX 的特殊之处在于统一内存模型，它消除了 CPU 和 GPU 之间数据传输的成本，从而实现更高的执行速度。
- Mojo。[Mojo](https://www.modular.com/max/mojo) 是一种新的以人工智能为先的编程语言。它旨在通过将 Python 语法和生态系统与系统编程和元编程特性相结合，缩小研究和生产之间的差距。它是第一种利用新的 MLIR 编译器后端的语言，拥有零成本抽象、自动调优、及早销毁、尾调用优化和更好的单指令 / 多数据（SIMD）工学等炫酷功能。
- Otter。[Otter](https://github.com/maypok86/otter) 是一个在 Go 语言中非竞争式的缓存库。它具有出色的吞吐量并且能够巧妙地实现 S3-FIFO 算法，以获得良好的缓存命中率。Otter 还支持泛型，因此可以将任何可比较的类型用作键，并将任何类型用作值。
- Pkl。[Pkl](https://pkl-lang.org/main/current/index.html) —— 发音为Pickle —— 是一种嵌入式配置语言，为数据模板和验证提供丰富的支持。它可以从命令行使用、集成到构建管道中或嵌入到程序中。 Pkl 的规模从小到大、从简单到复杂、从临时到重复的配置任务。
- Rust for UI。大部分偏向于在前后端使用统一代码语言的人会选择 JavaScript 或 TypeScript 作为后端语言。然而，开发者现在也可以通过 WebAssembly 在浏览器里使用 Rust 代码，而这正
  在变得越来越常见。
- vLLM。[vLLM](https://github.com/vllm-project/vllm) 是一个具有高吞吐量和高效内存的大语言模型（LLM）推理和服务引擎，其原因在于它可以对传入请求进行连续批处理。它支持几种部署选项，包括使用 Ray 运行时进行分布式张量并行推理和服务部署，在云中使用 SkyPilot、NVIDIA Triton、Docker 和 LangChain 进行部署。
- Voyager。[Voyager](https://voyager.adriel.cafe/) 是为 Android 的 Jetpack Compose 构建的导航库。它支持多种导航类型，包括线性、底部表单、标签和嵌套，其屏幕模型与流行框架如 Koin 和 Hilt 集成。
- WGPU。[wgpu](https://wgpu.rs/) 是一个基于 WebGPU API 的 Rust 图形库，它以能够高效处理 GPU 上通用图形和计算任务的能力而闻名。wgpu 旨在填补由于淘汰旧的图形标准（如 OpenGL 和 WebGL）而留下的空白。它引入了一种现代的图形开发方法，既适用于本地应用程序，也适用于基于 Web 的项目。它与 WebAssembly 的集成进一步使得图形和计算应用程序能够在浏览器中运行。
- Zig。[Zig](https://ziglang.org/) 是一种新的编程语言，它与 C 语言有许多共同点，但具有更强的类型系统、更容易的内存分配以及对命名空间的支持等。Zig 的目标是提供一个非常简单的语言，具有直接明了的编译过程，最小化副作用，并提供可预测、易于追踪的执行。

### 暂缓

- [LangChain](https://www.langchain.com/)。虽然这个框架为构建大语言模型应用提供了一套强大的功能，但它使用起来很困难且过于复杂。随着 LangChain 试图发展并快速跟进最新变化，开发者越来越难以跟上这些概念和模式的变更。此外，它还存在 API 设计不一致且冗长的情况。或许，你更应该使用更轻量的专门框架进行实现。你还可以考虑其他框架，如 Semantic Kernel、Haystack 或 LiteLLM。