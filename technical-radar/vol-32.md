# vol 32

## 本期主题

### 监督式智能编码代理

越来越多工具开始支持开发者直接在 IDE 内通过与 AI 聊天来驱动代码实现，这种方式也被称为 Agentic、“Prompt-to-code”或“基于对话的编程（Chat-oriented Programming, CHOP）。

### 可观测性的演进

众多用于监控和评估 LLM 性能的工具涌现，包括 Weights & Biases Weave、Arize Phoenix、Helicone 和 HumanLoop 。此外，AI 辅助可观测性 也成为一大趋势，这类工具借助 AI 提升分析和洞察能力。

### RAG 中的 “R”： 检索的进化

纠正型 RAG，可以根据反馈或启发式规则动态调整响应；融合型RAG，通过结合多种数据源和检索策略，提供更全面且稳健的响应；自助型 RAG，它完全避开传统的检索步骤，按需获取数据。此外，FastGraphRAG 通过生成可供人类浏览的图表，提升了数据的可理解性。

### 驾驭数据疆界

随着非结构化数据在企业中的日益普及和重要性，如何有效管理和包装这些数据，使其能够成功应用于 AI 技术、客户分析等领域，已成为当今企业发展的关键。



## 技术

### 采纳

- 数据产品思维。企业正在积极采用 数据产品思维 作为管理数据资产的标准实践。这一方法将数据视为具有自身生命周期、质量标准，并专注于满足消费者需求的“产品”。无论组织选择 数据网格 还是 Lakehouse 架构，我们现在将其推荐为数据管理的默认建议。

- Fuzz测试。即模糊测试，其目标是向软件系统输入各种无效数据并观察其行为。例如，对于一个 HTTP 端点，错误的请求通常应返回 4xx_ 错误，但 fuzz 测试往往会引发 5xx_ 错误甚至更严重的问题。在更多 AI 生成代码和 自满于 AI 生成的代码 的背景下，现在是采用 fuzz 测试的好时机，以确保代码的健壮性和安全性。
- 软件物料清单（SBOM）。SBOM 生态系统显著成熟，提供了强大的工具支持，并实现了与 CI/CD 系统的无缝集成。工具如 Syft、Trivy 和 Snyk 能够从源代码到容器镜像生成全面的 SBOM，同时支持漏洞扫描。FOSSA 和 Chainloop 等平台通过与开发工作流集成以及实施安全策略，进一步提升了安全风险管理能力。
- 威胁建模。**威胁建模是一组用于识别和分类潜在威胁的技术**，可广泛应用于各种场景，包括生成式 AI 应用 ，这些应用带来了独特的安全风险 。要想取得成效，威胁建模必须贯穿软件生命周期的各个阶段并定期执行，同时与其他安全实践相结合才能发挥最佳效果。

### 试验

- API 请求集合作为 API 产品的制品。**将 API 视为产品 意味着优先考虑开发者体验，不仅需要在 API 本身中融入合理和标准化的设计，还需要提供全面的文档以及流畅的入门体验**。虽然 OpenAPI（如 Swagger）规范可以有效记录 API 接口，但入门仍然是挑战。开发者需要快速获取可用的示例，包括预配置的身份验证和真实的测试数据。随着 API 客户端工具（例如 Postman、Bruno 和 Insomnia）的逐步成熟，建议将 API 请求集合做为 API 产品的制品。API 请求集合应经过精心设计，以引导开发者完成关键工作流程，帮助他们轻松理解 API 的领域语言和功能。为了保持请求集合的最新状态，我们建议将其存储在代码库中，并将其与 API 的发布流水线集成在一起。

- 架构建议流程。 这是一种去中心化的方法，任何人都可以做出架构决策，前提是他们向受影响的人和具有相关专业知识的人寻求建议。这种方法使团队能够在不牺牲架构质量的前提下优化工作流，无论是在小规模还是大规模环境中。像 **架构决策记录（ADR）** 和**建议论坛**这样的实践能够确保决策是经过充分信息支持的，同时赋予那些最接近实际工作的人员决策权。

- GraphRAG。它被描述为一个两步的流程：（1） 对文档进行分块，并使用基于大语言模型的分析构建知识图谱；（2） 通过嵌入检索的方式在查询时检索相关块，沿着知识图谱的边缘发现更多相关的分块，这些分块后续会被添加到增强提示中。这种方法可以提高大语言模型生成的响应数据的质量。在使用生成式 AI 理解遗留代码库的过程中，通过像抽象语法树和代码依赖这样的结构化信息去构建知识图谱。GraphRAG 模
  式正在获得更多的关注，像 Neo4j 的 [GraphRAG Python](https://neo4j.com/blog/news/graphrag-python-package/) 库这样的工具与架构正在不断出现以支持该模式。

- 按需特权访问管理（Just-in-time Privileged Access Management）。攻击者通常从低级访问权限开始，利用软件漏洞或配置错误获取管理员权限，尤其是在账号拥有过多或不必要权限时。另一个常被忽视的攻击面是静态特权（standing privileges）――即持续可用的特权访问。按需特权访问管理（Just-in-time Privileged Access Management）有效缓解了这一问题，通过仅在需要时授予访问权限，并在任务完成后立即撤销权限，从而最大限度地降低暴露风险。实际应用步骤参考：触发轻量化的审批流程，为用户分配临时角色并限制访问权限，**同时为每个角色强制设置生存时间（TTL）**，确保权限在任务完成后自动过期。

- 模型蒸馏。[Scaling laws](https://openai.com/index/scaling-laws-for-neural-language-models/) 是推动 AI 快速发展的关键原则之一，即更大的模型、更大的数据集和更多的计算资源能够带来更强大的 AI 系统。然而，消费级硬件和边缘设备往往缺乏运行大尺寸模型的能力，因此产生了对模型蒸馏的需求。

  模型蒸馏 将知识从一个更大、更强的模型（教师模型）转移到一个更小、更高效的模型（学生模型）。这一过程通常包括从教师模型生成一个样本数据集，并对学生模型进行微调，以捕获其统计特性。与通过移除参数来压缩模型的 剪枝 技术或 量化 不同，蒸馏旨在保留领域特定的知识，同时将精度损失降到最低。此外，蒸馏还可以与量化结合使用，以进一步优化模型。

  一个显著的例子是 Qwen/Llama 的 DeepSeek R1 蒸馏版本，它们在小模型中保留了强大的推理能力。随着蒸馏技术的日益成熟，它已不再局限于研究实验室，而是被广泛应用于从工业项目到个人项目的各类场景中。

- 提示工程（Prompt Engineering）。提示工程（Prompt Engineering） 是指为生成式 AI 模型设计与优化提示词（Prompt）的过程，其目标是生成高质量、上下文相关（Context-aware）的响应。这一过程通常包括针对特定任务或应用场景，精心构建清晰、具体且上下文相关的提示，以实现模型输出效果的最优化。

  **高级模型可能 消除 软件工程领域对提示工程的依赖**。但在实际场景中，传统提示 工程技术仍然是减少模型幻觉（Hallucinations）并提升输出质量的重要手段，特别是在考虑推理模型与普通 LLM 在响应时间和 Token 成本等因素存在显著差异的前提下。

  如何在**性能、响应速度与 Token 成本**之间找到最佳平衡，依然是充分发挥 LLM 效能的关键所在。

- 小语言模型（SLMs）。满血版 R1 拥有 6710 亿个参数，并且需要约 1342GB 的 VRAM 才能运行，这通常只能通过八块最先进的 NVIDIA GPU 组成的“迷你集群”来实现。然而，DeepSeek 也提供了“ 蒸馏版 ”，即 Qwen 和 Llama 等更小的开放权重模型，使其能力得以迁移，并能够在更普通的硬件上运行。

- 利用生成式AI理解遗留代码库。类似 [Sourcegraph Cody](https://sourcegraph.com/cody) 等工具，正在让开发者更轻松地导航和理解整个代码库。这些工具运用多种生成式 AI 技术提供上下文感知（Context-aware），极大地简化了对复杂遗留系统的分析与处理。

### 评估

- AI友好的代码设计。值得庆幸的是，面向人类的优秀软件设计同样能够为 AI 提供助力。比如，明确的命名可以为代码提供领域上下文和功能信息；模块化和抽象设计能够限制代码改动范围，使 AI 的工作上下文更易于处理；而 DRY（don’t repeat yourself）原则则能减少重复代码，让 AI 更容易确保行为一致性。到目前为止，最适合 AI 的设计模式依然与传统的软件设计最佳实践密切相关。
- AI驱动的UI测试。目前，该领域主要有几种不同的实现方式。一种方法是使用针对 UI 快照处理进行微调的多模态 LLM，这类工具允许测试脚本以自然语言编写，并能自主导航应用程序。例如，QA.tech 和 LambdaTest 的 KaneAI 就属于这一类别。另一种方法，则是像 Browser Use 这样，结合多模态基础模型与 Playwright，通过对网页结构的深入理解进行测试，而不是依赖于特定微调的模型。
- 能力边界作为理解系统故障的模型。[优雅扩展性理论](https://www.researchgate.net/publication/327427067_The_Theory_of_Graceful_Extensibility_Basic_rules_that_govern_adaptive_systems) 定义了适应性系统（包括构建和操作软件的社会技术系统）的基本规则。这一理论中的一个关键概念是能力边界（competence envelope） ―― 系统在面对失败时能够稳健运作的边界。当系统被推到能力边界之外时，它会变得脆弱，容易崩溃。该模型为理解系统故障提供了一个有价值的视角。
- 从LLMs获取结构化输出。这可以通过指示通用模型以特定格式响应，或者通过微调模型使其“原生”输出例如 JSON 的结构化数据来实现。OpenAI 现在支持结构化输出，允许开发人员提供 JSON Schema、pydantic 或 Zod 对象来约束模型响应。

### 暂缓

- AI 加速影子 IT（AI-accelerated Shadow IT）。一些无代码（No-code）工作流自动化平台已支持对 AI API（如 OpenAI 或 Anthropic）的集成，这使得用户可能倾向于将 AI 用作“胶带”，将此前难以实现的系统集成临时拼凑起来，例如通过 AI 将聊天消息转换为ERP 系统的 API 调用。
  这些迹象呈现出类似于电子表格（Spreadsheets）当年迅速扩散的特征：**虽然为企业关键流程提供了快速解决方案，但在长期运行后往往会造成规模更大的技术债（Tech Debt）**。如果不加管控，这种新型影子 IT 将导致未经治理的应用程序激增，安全隐患加剧，数据分散在不同系统内。建议企业对此风险保持警觉，并谨慎评估快速问题解决与长期技术稳定性之间的平衡与取舍。

- 自满于 AI 生成的代码。**AI 驱动的信心往往以牺牲批判性思维为代价**――这种模式在长期使用编码助手时表现得尤为明显。

- 本地编码助手。由于对代码机密性的担忧，许多组织对第三方 AI 编码助手保持谨慎态度。因此，许多开发者开始考虑使用 本地编码助手 ，即完全在本地机器上运行的 AI 工具，无需将代码发送到外部服务器。**然而，本地助手仍然落后于依赖更大型、更强大模型的云端助手。即使是在高端开发者设备上，较小的模型仍然存在能力上的局限性。**它们难以处理复杂的提示词，缺乏解决更大问题所需的上下文窗口，并且通常无法触发工具集成或函数调用。

  因此，建议在使用本地助手时保持较低的期望值，但也有一些功能在本地环境中是可行的。目前一些流行
  的 IDE 已将较小的模型嵌入其核心功能中，例如 Xcode 的预测代码补全和 JetBrains 的整行代码补全。此外，
  可在本地运行的大语言模型，如 Qwen Coder，为本地的行内建议和处理简单编码查询迈出了重要一步。

- 使用AI代替结对编程。一个问题：一个程序员可以选择与人工智能，而不是另外一个程序员，进行结对编程，从而达到同样的团队产出吗？把编码助手当做结对编程者忽略了结对编程的一个关键收益 : 它可以让团队而不只是个人变得更好。**在诸如将正在进行的工作的数量控制在低水平，减少团队交接与重复学习，让持续集成成为可能，或者改善集体代码所有权等等这些团队合作相关的层面，它没有带来什么好处。**

- 逆向ETL（Reverse ETL）。常规 ETL ( Extract-Transform-Load) 在传统数据架构中是很常见的，它将
  数据从事务处理系统传输到集中式分析系统（如数据仓库或数据湖）。尽管这种架构存在许多已被广泛记录的缺点，其中一些问题通过 数据网格 方法得到了缓解，但它在企业中仍然很常见。然而，一个令人担忧的趋势是，产品供应商正利用 Reverse ETL 的概念，将越来越多的业务逻辑转移到一个集中式的平台（即它们的产品）中。这种方法加剧了集中式数据架构所导致的许多问题。**过度依赖于将业务逻辑嵌入到一个庞大的中央**
  **数据平台中，会导致数据管理复杂性增加，并削弱领域团队对其数据的控制能力。**

- SAFe™。 SAFe™ （Scaled Agile Framework®）（规模化敏捷框架）正被广泛采用。同时，SAFe 过度标准化和阶段性门限的流程会造成阻碍，这可能助长信息孤岛，其自上而下的管控模式会在价值流中产生浪费，并抑制工程人才的创造力，还会限制团队的自主性和实验空间。

## 平台

### 采納

- [GitLab CI/CD](https://docs.gitlab.com/ee/ci/)。GitLab CI/CD 已发展为 GitLab 内部一个高度集成的系统，涵盖从代码集成、测试到部署和监控的所有环节。它支持复杂的工作流，包括多阶段流水线、缓存、并行执行和自动扩展运行器，非常适合大型项目和复杂流水线需求。
- Trino。Trino 是一个开源的分布式 SQL 查询引擎，专为大数据的交互式分析查询而设计。它针对本地和云端环境进行了优化，支持直接在数据驻留的位置进行查询，包括关系型数据库和各种专有数据存储（通过连接器）。Trino 还能够查询存储为 Parquet 等文件格式的数据，以及像 Apache Iceberg 这样的开放表格格式。其内置的查询联邦功能允许将多个数据源的数据作为一个逻辑表进行查询，非常适合需要聚合多种来源数据的分析工作负载。

### 试验

- [ABsmartly](https://absmartly.com/)。ABsmartly 是一款先进的 A/B 测试与实验平台，专为快速且可信的决策制定而设计。其核心亮点是 Group Sequential Testing （GST） 引擎，与传统 A/B 测试工具相比，可将测试结果的速度提升高达 80%。平台提供实时报告、深度数据分割以及通过 API 优先的方式实现的无缝全栈集成，支持在网页、移动端、微服务和机器学习模型中运行实验。
- [Dapr](https://dapr.io/)。Dapr 是一个便于移植的事件驱动运行时，帮助开发者轻松构建在云和边缘环境中运行的弹性应用，无论是无状态还是有状态的，并支持多种编程语言和开发框架。它的许多新特性包括任务调度、虚拟角色以及更为复杂的重试策略和可观察性组件。
- Grafana Alloy。前身为 Grafana Agent，Grafana Alloy 是一个开源的 OpenTelemetry Collector。Alloy 被设计为一个一体化的遥测数据收集器，**用于收集包括日志、指标和跟踪在内的所有遥测数据**。它支持常用的遥测数据格式，如OpenTelemetry、Prometheus 和 Datadog。
- [Grafana Loki](https://grafana.com/docs/loki/)。Grafana Loki 是一个受 Prometheus 启发的横向可扩展，高可用的多租户日志聚合系统。Loki 只对日志的元数据进行索引，并把它当做日志流的标签集，而日志数据本身则储存在像 S3, GCS 或 Azure Blob Storage 这样的块存储方案中。这样做的好处是 Loki 比竞争对手的运维复杂度更低，同时也降低了存储成本。正如你所期待的那样，它与 Grafana 和 Grafana Alloy 深度集成，即使其他的日志采集机制也被支持。
- [Grafana Tempo](https://github.com/grafana/tempo)。Grafana Tempo 是一个高可扩展的分布式追踪后端，支持诸如 OpenTelemetry 等开放标准。Tempo 专为成本效率设计，依赖对象存储进行长期追踪数据的保存，并支持追踪查询、基于 Span 的指标生成以及与日志和指标的关联。Tempo 默认使用 Apache Parquet 为基础的列式块格式，提高了查询性能，并使下游工具能够访问追踪数据。
- Railway。[Heroku](https://www.thoughtworks.com/cn/radar/platforms/heroku) 曾是许多开发者快速发布和部署应用程序的优秀选择。近年来，像 Vercel 这样更现代、轻量且易用的平台兴起，但是它们主要面向前端应用的部署。而在全栈部署领域的一个替代选择是 [Railway](https://railway.com/)，这是一个云端 PaaS 平台，简化了从 GitHub/Docker 部署到生产环境可观测性的整个流程。
- [Unblocked](https://getunblocked.com)。一款现成的 AI 团队助手。通过与代码库、企业文档平台、项目管理工具以及沟通工具的集成，Unblocked 能帮助解答关于复杂业务和技术概念、架构设计与实现以及操作流程的问题。这在处理大型或遗留系统时尤为有用。
- [Weights & Biases](https://wandb.ai/)。一个超越 LLM 系统跟踪的完整平台。Weave 支持创建系统评估、定义自定义指标、使用LLM 作为任务（如摘要）的评判工具，并保存数据集以捕捉不同行为进行分析。

### 评估

- [Arize Phoenix](https://github.com/Arize-ai/phoenix)。随着大语言模型和 AI agent 应用 的日益普及，LLM 可观测性的重要性不断凸显。之前，我们已经认识了 Langfuse和 Weights & Biases （W&B） 等平台。 Arize Phoenix 是该领域另一个值得关注的新兴平台。Phoenix 提供了诸如 LLM 链路追踪、评估和提示词管理等核心功能，并可与主流 LLM 提供商和开发框架无缝集成，以在低成本、低配置的情况下实现对 LLM 输出内容、延迟和 Token 消耗
  等指标的深度洞察。

- [Chainloop](https://github.com/chainloop-dev/chainloop)。Chainloop 是一个开源的软件供应链安全平台，帮助安全团队强制执行合规性要求，同时允许开发团队将安全合规无缝集成到 CI/CD 流水线中。它包括一个控制平面（Control Plane），作为安全策略的单一事实来源，以及一个 CLI，用于在 CI/CD 工作流 中运行声明（attestations）以确保合规性。安全团队定义 工作流契约（Workflow Contracts），明确需要收集哪些工件（如 SBOM 和漏洞报告）、存储位置以及如何评估合规性。

- [DeepSeek R1](https://github.com/deepseek-ai/DeepSeek-R1)。在一系列非推理模型的基础上，DeepSeek 的工程师设计并应用了多种方法来最大化硬件使用率。这些方法包括多头潜在注意力（Multi-Head Latent Attention, MLA）、专家混合（Mixture of Experts, MoE）门控、8 位浮点训练（FP8）以及底层 PTX 编程。这些方法结合其 高性能计算协同设计 方法使 DeepSeek-R1 在显著降低训练和推理成本的同时，达到与最先进模型（state-of-the-art）相媲美的表现。

  DeepSeek-R1-Zero 另一个显著创新在于 : 工程师们可以通过简单的强化学习（RL），无需监督微调（SFT）即可让非推理模型展现出推理能力。此外，所有的 DeepSeek 模型都为开放权重，即它们可以被自由获取，但训练代码和训练数据仍然为专有。

- Deno。由 Node.js 的发明者 Ryan Dahl 创建的 Deno ，旨在修正他认为 Node.js 存在的错误。Deno 具有更严格的沙盒机制、内置的依赖管理以及原生的 TypeScript 支持。许多开发者在TypeScript 项目中更偏爱 Deno，它更像是一个真正的 TypeScript 运行时和工具链，而不仅仅是 Node.js 的一个附加组件。
  Deno 2 版本 引入了对 Node.js 和 npm 库的兼容性支持，并推出了长期支持 （LTS） 版本及其他改进。鉴于 Node.js 和 npm 庞大的生态系统，这些变化有望进一步推动 Deno 的普及。
  **此外，Deno 的 标准库 已趋于稳定，有助于遏制 npm 生态中过多低价值软件包的泛滥**。Deno 提供的工具链和标准库，使 TypeScript 或 JavaScript 在服务器端开发中更具吸引力。然而，我们不应仅仅为了避免多语言编程而选择某个平台。

- [Graphiti](https://github.com/getzep/graphiti)。Graphiti 构建了动态且时间感知的知识图谱，能够捕捉不断演化的事实和关系。Graphiti 在图的边上维护了时间元数据，用以记录关系的生命周期。它能够将结构化和非结构化数据以离散的 Episodes 形式进行摄取，并支持基于时间、全文检索、语义和图算法的查询融合。

- [Helicone](https://www.helicone.ai/)。一个面向企业需求的托管 LLMOps 平台，专注于管理 LLM 成本、评估 ROI 和降低风险。作为一个开源且以开发者为中心的平台，Helicone 支持生产级 AI 应用，覆盖整个 LLM 生命周期的提示词实验、监控、调试和优化。它能够实时分析来自不同 LLM 提供商的成本、利用率、性能以及代理堆栈跟踪。

- [Humanloop](https://humanloop.com/)。Humanloop 是一个新兴的平台，致力于通过在关键决策点引入人类反馈，使 AI 系统更加可靠、适应性更强并且更符合用户需求。平台提供了用于人工标注、主动学习和人工参与的微调工具，同时支持根据业务需求对大语言模型进行评估。此外，它还帮助优化生成式 AI 解决方案的生命周期，以更高的成本效益实现更大的控制和效率。

- 模型上下文协议（MCP）。在提示生成中，最大的挑战之一是确保 AI 工具能够访问与任务相关的所有上下文信息。这些上下文信息通常已经存在于我们每天使用的系统中，如维基、问题追踪器、数据库或可观察性系统。AI 工具与这些信息源的无缝集成可以显著提高 AI 生成输出的质量。

- [Open WebUI](https://github.com/open-webui/open-webui)。Open WebUI 是一个开源的自托管 AI 平台，功能多样且强大。

- [pg_mooncake](https://github.com/Mooncake-Labs/pg_mooncake)。pg_mooncake 是一个 PostgreSQL 扩展，用于添加列式存储和向量化执行功能。其列存表可存储为 Iceberg或 Delta Lake 表格，数据可以存放在本地文件系统或兼容 S3 的云存储中。pg_mooncake 支持从 Parquet、CSV 文件甚至 Hugging Face 数据集加载数据，非常适合需要列式存储的重度数据分析场景。

- 推理模型（Reasoning Models）。推理模型通常通过强化学习（RL）或监督式微调（SFT）进行训练，增强了诸如**逐步思考（思维链）**、**探索替代方案（思维树）和 自我修正** 等能力。典型代表包括 OpenAI 的 o1 / o3 、 DeepSeek R1 和 Gemini 2.0 Flash Thinking 。这些模型应被视为与通用大型语言模型（LLM）不同的类别，而非简单的高级版本。

  这种能力提升伴随着代价。推理模型需要更长的响应时间和更高的 token 消耗，可以称为“ 更慢的AI ”。并非所有任务都值得采用这类模型。**对于文本摘要、 内容生成或快速响应聊天机器人等简单任务，通用 LLM 仍然是更好的选择。**建议在 STEM 领域、复杂问题解决和决策制定中使用推理模型――例如，将 LLM 用作评判者或通过推理模型显式的 CoT 输出来提高最终结果的可解释性。

- [Restate](https://restate.dev/)。它是一个持久化执行平台，类似于 [Temporal](https://www.thoughtworks.com/cn/radar/platforms/temporal) ，由 Apache Flink 的原始创始人开发。功能方面，它提供了将工作流作为代码、状态事件处理、Saga 模式和持久化状态机等特性。Restate 使用 Rust 编写，并作为单个二进制文件部署，利用分布式日志记录事件，并通过基于 Flexible Paxos 的虚拟共识算法来实现，这保证了在节点故障时的持久性。**但是在分布式系统中最好还是避免使用分布式事务。**

- [Supabase](https://supabase.com/)。是一个开源的 Firebase 替代方案，用于构建可扩展且安全的后端。提供了一整套集成服务，包括PostgreSQL 数据库、认证、即时 API、Edge Functions、实时订阅、存储以及向量嵌入。 目标是简化后端开发，使开发者能够专注于构建前端体验，同时利用开源技术的强大功能和灵活性。

-  [Synthesized](https://www.synthesized.io/)。一个可以屏蔽和子集化现有生产数据，或生成具有统计相关性的合成数据的平台。Synthesized 可直接集成到构建流水线中，并提供隐私屏蔽功能，通过不可逆的数据混淆技术（如哈希、随机化和分组）实现逐属性匿名化。此外，它还可以生成大量合成数据用于性能测试。

- Tonic.ai。Tonic.ai 属于一类日益增长的平台，专注于为开发、测试和 QA 环境生成真实且去标识化的合成数据。Tonic.ai 能生成结构化和非结构化数据，在保持生产数据统计属性的同时，通过差分隐私技术确保隐私和合规。其关键功能包括自动检测、分类和去除非结构化数据中的敏感信息，以及通过 TonicEphemeral 按需提供数据库环境。

- [turbopuffer](https://turbopuffer.com/)。turbopuffer 是一个无服务器的多租户搜索引擎，能够在对象存储上无缝集成向量搜索和全文搜索。通过将对象存储用作预写日志并保持查询节点的无状态化，它非常适合高规模的搜索工作负载。

  turbopuffer 专为性能和准确性而设计，开箱即可提供 [高召回率](https://turbopuffer.com/blog/native-filtering)，即使是复杂的基于过滤条件的查询也不例外。它将冷查询结果缓存到 NVMe SSD，并将经常访问的命名空间保存在内存中，从而实现对数十亿文档的低延迟搜索。

- VectorChord。[VectorChord](https://github.com/tensorchord/VectorChord) 是一个用于向量相似性搜索的 PostgreSQL 扩展，由 pgvecto.rs 的创建者开发，作为其继任者。采用 IVF（倒排文件索引）以及 [RaBitQ](https://arxiv.org/pdf/2405.12497) 量化技术，能够实现快速、可扩展且准确的向量搜索，同时显著降低计算需求。

### 暂缓

- Tyk hybrid API management。Tyk 在可观测性方面存在不足。此外，事故支持的响应速度似乎偏慢，仅通过工单和邮件进行沟通在这些紧急情况下并不理想。文档成熟度不足，在处理复杂场景和问题时，文档往往不能提供足够的指导。Tyk 生态系统中的其他产品似乎也不够成熟。



## 工具

### 采纳

- [Renovate](https://www.mend.io/renovate/)。虽然 Dependabot 仍然是 GitHub 仓库的一个安全默认选项，这里推荐评估 Renovate，因为它提供了更全面且可定制的方案。为了最大程度发挥Renovate 的优势，应配置它来监控并更新所有依赖项，包括工具、基础设施以及私有或内部托管的依赖项。
- [uv](https://github.com/astral-sh/uv)。uv 是一个由 Rust 编写的下一代 Python 包和项目管理工具，核心价值主张是“极快的速度”。在基准测试中，uv 的性能远超其他 Python 包管理工具，加速了构建和测试周期，显著提升了开发者体验。由于 uv 相对较新，将其移至 Adopt 阶段是一个大胆的决定。
- Vite。Vite 的影响力一直在提升。作为一个高性能的前端构建工具，Vite 提供了快速热重载的特性。它正在被众多前端框架采用并推荐为默认选择，比如 Vue, SvelteKit，以及最近 废弃了 create-react-app 的 React。

### 试验

- Claude Sonnet。Claude Sonnet 是一款擅长编码，写作，分析和视觉处理的先进语言模型。它可在浏览器，终端和大多数主流IDE 中使用，并支持与 GitHub Copilot 集成。它在从零开始的项目（greenfield projects）中表现尤为出色，特别是在协同软件设计和架构讨论方面。

- Cline。Cline 是一个开源的 VSCode 扩展程序，目前在监督型 软件工程代理（software engineering agents） 领域中是最强有力的竞争者之一。Cline 展现了在处理复杂开发任务方面的高级能力，尤其是在结合 Claude 3.5 Sonnet 时表现突出。它支持处理大型代码库、自动化无头浏览器测试，并能够主动修复错误。与基于云的解决方案不同，Cline 通过 本地存储数据增强了隐私保护。

- [Cursor](https://www.cursor.com/)。Cursor 这款以 AI 为核心的代码编辑器令人印象深刻，它在竞争激烈的 AI 代码辅助领域依然保持领先地位。其代码上下文编排功能十分高效，并支持多种模型，包括使用自定义 API 密钥的选项。

- [D2](https://github.com/terrastruct/d2)。D2 是一个开源的 图表即代码 工具，帮助用户通过文本创建和定制图表。它引入了 [D2 图表脚本语言](https://d2lang.com/tour/intro)，以声明式语法优先保证可读性而非紧凑性。D2 自带默认 主题，并使用与 Mermaid 相同的 布局引擎 。其轻量化的语法，尤其适用于软件文档和架构图的场景。

- [Databricks Delta Live Tables](https://www.databricks.com/product/data-engineering/dlt)。Delta Live Tables（DLT）在**简化和优化数据管道管理**方面持续展现出其价值，通过声明式方法支持实时流处理和批量处理。通过自动化复杂的数据工程任务（如手动检查点管理），DLT 减少了运营开销，同时确保了端到端系统的稳健性。

- [JSON Crack](https://marketplace.visualstudio.com/items?itemName=AykutSarac.jsoncrack-vscode)。它是一个用于在 Visual Studio Code 中将文本数据渲染为交互式图表的扩展工具。它实际上支持包括 YAML、TOML 和 XML 等多种格式。与 Mermaid 和 D2 不同，这些工具通过文本形式生成特定的可视化图表，而 JSON Crack 则是一个用于直观查看以文本格式存储数据的工具。其布局算法表现良好，并且支持选择性隐藏分支和节点，是探索数据集的绝佳选择。

- [MailSlurp](https://docs.mailslurp.com/)。MailSlurp 是一个邮件服务器和 SMS API 服务，提供用于创建收件箱和电话号码的 REST API，同时还支持直接在代码中验证电子邮件和消息。

- [Metabase](https://www.metabase.com/)。它是一款开源的分析和商业智能工具，允许用户从各种数据源（包括关系型数据库和 NoSQL 数据库）中可视化和分析数据。该工具帮助用户创建可视化和报告，将其组织到仪表板中，并轻松分享数据洞察。它还提供了 SDK，用于在 Web 应用程序中嵌入交互式仪表板，并能够匹配应用程序的主题和样式。

- [NeMo Guardrails](https://github.com/NVIDIA/NeMo-Guardrails)。NeMo Guardrails 是 NVIDIA 提供的一个易于使用的开源工具包，可帮助开发者为用于对话式应用的大型语言模型实施“护栏”。

- [Nyx](https://github.com/mooltiverse/nyx)。Nyx 是一个多功能的语义化版本发布工具，支持各种软件工程项目。它对编程语言无依赖，并兼容所有主流的持续集成和源代码管理平台，具备极高的适配性。尽管许多团队在 主干开发 中使用语义化版本管理，Nyx 还支持 Gitflow、OneFlow 和 GitHub Flow 等工作流。

- OpenRewrite。[OpenRewrite](https://github.com/openrewrite/rewrite) 是一个进行大规模代码重构的工具，尤其适用于基于规则的重构场景，例如迁移到广泛
  使用的库的新 API 版本，或对从相同模板创建的多个服务进行更新。除了对 Java 的强大支持外，OpenRewrite 还引入了对 JavaScript 等语言的支持。

- Plerion。[Plerion](https://www.plerion.com/) 是一个专注于 AWS 的云安全平台，通过与托管服务提供商集成，帮助发现云基础设施、服务器和应用程序中的风险、错误配置和漏洞。

- 软件工程代理（Software engineering agents）。指的是在 IDE 内的监督代理模式（supervised agentic modes），这些模式允许开发者通过聊天驱动实现，不仅可以修改多个文件中的代码，还能执行命令、运行测试并响应 IDE 反馈（如 linting 或编译错误）。该领域的领先工具包括 Cursor、Cline 和 Windsurf，而 GitHub Copilot 稍显落后，但正在快速追赶。这些代理模式的有效性**取决于所使用的模型（以 Claude’s Sonnet 系列为当前业界领先）以及工具与 IDE 集成的深度**。

  这些工作流具有吸引力且潜力巨大，并显著提高了编码速度。然而，保持问题范围小有助于开发者更
  好地审查 AI 生成的更改。

- [Tuple](https://tuple.app/)。Tuple 是一款专为远程结对编程优化的工具。关键的改进是增强了桌面共享功能，新增的隐私功能允许用户在共享屏幕时隐藏私人应用窗口（如短信），同时专注于共享工具（例如浏览器窗口）。
- [Turborepo](https://turbo.build/repo)。Turborepo 通过分析、缓存、并行化和优化构建任务，帮助管理大型 JavaScript 或 TypeScript monorepo，从而加速构建过程。

### 评估

- [AnythingLLM](https://github.com/Mintplex-Labs/anything-llm)。它是一个开源桌面应用程序，可以与大型文档或内容交互，支持开箱即用的大语言模型（LLMs）和向量数据库集成。它具备可插拔的嵌入模型架构，可以与大多数商业化 LLM 以及由 Ollama 管理的开源模型一起使用。除了支持 检索增强生成（RAG） 模式外，用户还可以创建和组织不同技能作为代理（agents）来执行自定义任务和工作流。

- Gemma Scope。机械解释性（Mechanistic Interpretability）――**理解大型语言模型的内部运行机制**――正在成为一个日益重要的领域。像 [Gemma Scope](https://deepmind.google/discover/blog/gemma-scope-helping-the-safety-community-shed-light-on-the-inner-workings-of-language-models/) 和开源库 [Mishax](https://github.com/google-deepmind/mishax) 这样的工具，为 Gemma2 系列开源模型提供了深入的洞察。

- Hurl。[Hurl](https://hurl.dev/) 是处理一系列 HTTP 请求的利器，这些请求可通过使用 Hurl 特定语法的纯文本文件定义。除了发送请求，Hurl 还可以验证响应数据，确保请求返回特定的 HTTP 状态码，使用 XPATH, JSONPath 或者正则表达式断言响应头和内容，以及提取相应数据到变量之中，以便在链式请求之中调用。

- [Jujutsu](https://github.com/jj-vcs/jj)。Jujutsu 提供了一个完整的 Git 替代方案，同时通过 使用 Git 仓库作为存储后端 保持兼容性。这使开发者能够继续使用现有的 Git 服务器和服务，同时受益于 Jujutsu 简化的工作流。Jujutsu 定位为“既简单又强大”，为不同经验水平的开发者提供易用性。其一大亮点是 [一流的冲突解决功能](https://jj-vcs.github.io/jj/latest/conflicts/) ，这一特性有潜
  力显著改善开发者的使用体验。

- kubenetmon。由 ClickHouse 构建并最近开源的 [kubenetmon](https://clickhouse.com/blog/kubenetmon-open-sourced)，旨在通过提供主要云提供商之间详尽的 Kubernetes 数据传输计量解决这一问题。

- Mergiraf。Mergiraf  通过查看语法树而不是将代码视为文本行来解决合并冲突。作为一个 git 合并驱动程序，它可以被配置为让 git 子命令（如 merge 和 cherry-pick）自动使用 Mergiraf，而不是默认的合并算法。

- ModernBERT。BERT（Bidirectional Encoder Representations from Transformers）的继任者 [ModernBERT](https://huggingface.co/blog/modernbert) 是一系列新一代的 encoder-only transformer 模型，专为广泛的自然语言处理（NLP）任务设计。作为可直接替代 BERT的升级版本，ModernBERT 不仅提升了性能和准确性，还解决了 BERT 的一些局限――特别是通过引入“交替注意力”（Alternating Attention）实现了对极长上下文长度的支持。

- [OpenRouter](https://openrouter.ai/)。OpenRouter 是一个统一的 API，可以用于访问多个大型语言模型（LLM）。它为 主流 LLM 提供商 提供了单一的集成点，简化了实验过程，降低了供应商锁定的风险，并通过将请求路由到最合适的模型来优化成本。像 Cline 和 Open WebUI 这样的流行工具都使用 OpenRouter 作为它们的端点。

- [Redactive](https://www.redactive.ai/)。Redactive 是一个企业级 AI 赋能平台，专为帮助受监管的组织安全地为 AI 应用（例如 AI 助手和协作工具）准备非结构化数据而设计。它可以与像 Confluence 这样的内容平台集成，创建用于 检索增强生成（RAG） 搜索的安全文本索引。通过仅提供实时数据并从源系统强制执行实时用户权限，Redactive 确保 AI 模型访问的是准确且授权的信息，而不会影响安全性。

- System Initiative。这款实验性工具为 DevOps 工作开辟了一条全新的激进方向。该工具背后富有创造性的思考，希望能够激励更多人突破基础设施即代码（Infrastructure-as-Code）的现状。

- [TabPFN](https://github.com/PriorLabs/TabPFN)。TabPFN 是一个基于 Transformer 的模型，专为在小规模表格数据集上实现快速而准确的分类而设计。它利用了上下文学习（In-Context Learning, ICL）， 直接从标注样本中进行预测，无需超参数调整或额外训练。TabPFN在数百万个合成数据集上预训练，因而能够很好地泛化到不同的数据分布，同时对缺失值和异常值具有较强的处理能力。它的优势包括高效处理异构数据以及对无信息特征的鲁棒性。

  **TabPFN 尤其适用于对速度和准确性要求较高的小规模应用场景**。然而，它在处理大规模数据集时面临扩展性挑战，并且在回归任务中能力有限。

- v0。 [v0](https://v0.dev/) 是由 Vercel 开发的一款 AI 工具，可根据截图、Figma 设计或简单的提示生成前端代码。它支持包括 React、Vue、shadcn 和 Tailwind 在内的多种前端框架。v0 为原型设计提供了一个稳固的工具，并可作为开发复杂应用程序的起点初始化代码。

- [Windsurf](https://codeium.com/windsurf)。Windsurf 是 Codeium 推出的 AI 编程助手，以其“代理型”（agentic）能力而闻名。类似于 Cursor 和 Cline，Windsurf 允许开发者通过 AI 聊天驱动实现代码的导航、修改以及命令的执行。它经常发布针对“代理模式” 的全新功能和集成。

- [YOLO](https://docs.ultralytics.com/models/yolo11/)。YOLO （You Only Look Once）系列由 Ultralytics 开发，并持续在推动计算机视觉模型领域的进步。其最新版本 YOLO11 在精度和效率方面比以前的版本有了显著提升。YOLO11 可以在 极少资源消耗下高速 运行图像分类任务，这使其适用于边缘设备的实时应用。

### 暂缓



## 语言和框架

### 采纳

- [OpenTelemetry](https://opentelemetry.io)。OpenTelemetry 正迅速成为可观察性领域的行业标准。随着 OpenTelemetry 协议 （OTLP） 规范的发布，行业内有了一个标准化的方式来处理追踪 （traces）、指标 （metrics） 和日志 （logs）。OTLP 已被诸如 Datadog 、New Relic 和 Grafana 等供应商采纳，帮助企业构建灵活的、与供应商无关的可观察性技术栈，避免被锁定在专有解决方案中。OTLP 支持 gzip 和 zstd压缩，大幅减少遥测数据的大小并降低带宽使用――这对于处理高遥测数据量的环境尤为关键。
- [React Hook Form](https://react-hook-form.com/)。可以将 React Hook Form 视为 Formik 的替代方案。由于默认使用非受控组件，它提供了显著更好的开箱即用的表现，特别是在大型表单上。

### 试验

- [Effect](https://effect.website/)。Effect 是一个 TypeScript 库，用于构建复杂的同步和异步程序。在 Web 开发中，常常需要为异步处理、并发、状态管理和错误处理等任务编写大量样板代码。Effect-TS 通过函数式编程的方法简化了这些流程。尽管传统方法如 Promise/try-catch 或 async/await 也能处理类似场景，但使用 Effect 对比传统方法显然更加具有优势。
- [Hasura GraphQL engine](https://github.com/hasura/graphql-engine)。**Hasura GraphQL engine 是一个通用的数据访问层**，可简化在不同数据源上构建、运行和管理高质量 API 的过程。它能够为各种数据库（包括 PostgreSQL、MongoDB 和 ClickHouse）及数据源即时生成 GraphQL API，使开发者能够快速且安全地获取所需数据。
- [LangGraph](https://github.com/langchain-ai/langgraph)。LangGraph 是一款面向基于 LLM 的 多 agent 应用构建的编排（Orchestration）框架。与抽象程度较高的 LangChain 相比，它提供了更底层的节点（Nodes）和边（Edges）等基本原语， 允许开发者精细地控制 agent 工作流、记忆管理与状态持久化。
- [MarkItDown](https://github.com/microsoft/markitdown)。MarkItDown 能将多种格式（PDF、HTML、PowerPoint、Word）转换为 Markdown，从而增强文本的可读性和上下文保留。由于 LLM 可以从 格式化提示 （如标题和章节）中获取上下文，Markdown 能够很好地保留结构以提升理解能力。
- [Module Federation](https://module-federation.io/guide/start/index.html)。Module Federation 允许在微前端之间指定共享模块并实现依赖的去重。随着 2.0 版本的发布，模块联邦已经可以独立于 webpack 工作。此更新引入了一些关键功能，包括联邦运行时（Federation Runtime）、全新的插件 API，以及对流行框架（如 React 和 Angular）及热门构建工具（如 Rspack 和 Vite）的支持。
- [Prisma ORM](https://www.prisma.io/orm)。Prisma ORM 是一个开源的数据库工具包，帮助 Node.js 和 Typescript 应用简化应用数据库操作。它提供了一种现代化的、类型安全的数据库访问方式，能够自动化数据库模式迁移，并提供直观的查询 API。与传统 ORM不同，Prisma ORM 使用纯 JavaScript 对象定义数据库类型，而无需依赖装饰器或类。

### 评估

- [.NET Aspire](https://learn.microsoft.com/zh-cn/dotnet/aspire/get-started/aspire-overview)。.NET Aspire 旨在简化开发者本地机器上分布式应用的编排工作。Aspire 允许您在本地开发环境中编排多个服务，包括多个 .NET 项目、依赖的数据库和 Docker 容器――这些都可以通过一条命令完成。
- Android XR SDK。Google 和三星与高通合作推出了 Android XR, 这是一款专门为扩展现实（Extended Reality, XR）设计的操作系统。它在未来计划支持智能眼镜以及其他智能设备。
- [Browser Use](https://github.com/browser-use/browser-use)。Browser Use 是一个开源的 Python 库，使基于 LLM 的 AI 代理能够使用网页浏览器并访问 Web 应用。它可以控制浏览器并执行包括页面导航、输入操作和文本提取在内的各种步骤。该库支持多标签页管理，能够在多个 Web 应用之间协调执行操作。
- [CrewAI](https://github.com/crewAIInc/crewAI)。CrewAI 是一个专为构建和管理 AI 代理而设计的平台，能让多个 AI 代理协同工作，共同完成复杂任务。可以将其理解为一群 AI 工作者组成的团队，每个成员都有自己的专长，并能齐心协力以达成共同目标。
- [ElysiaJs](https://elysiajs.com/)。ElysiaJS 是一个端到端类型安全的 TypeScript Web 框架，最初主要为 Bun 设计，但也兼容其他的 JavaScript运行环境。与 tRPC 等替代方案不同（这类方案会强制要求特定的 API 接口结构），ElysiaJS 不强制任何特定的API 接口结构。这使得开发者可以创建符合行业标准的 API，如 RESTful、JSON: API 或 OpenAPI，同时仍能提供端到端的类型安全。
- [FastGraphRAG](https://github.com/circlemind-ai/fast-graphrag)。FastGraphRAG 是 GraphRAG 的一个开源实现，专为高检索准确性和性能而设计。它利用 [个性化 PageRank](https://arxiv.org/abs/2006.11876) 限制图导航范围，仅关注图中与查询最相关的节点，从而提升检索准确性并改善大语言模型（LLM）的响应质量。
- [Gleam](https://gleam.run/)。Erlang/OTP 是一个强大的平台，用于构建高并发、可扩展且具备容错能力的分布式系统。传统上，其语言是动态类型的，而 Gleam 在语言层面引入了类型安全。Gleam 构建于 [BEAM](https://en.wikipedia.org/wiki/BEAM_(Erlang_virtual_machine)) 之上，将函数式编程的表达能力与编译时的类型安全相结合，从而减少运行时错误并提高可维护性。
- [GoFr](https://github.com/gofr-dev/gofr)。GoFr 是一个专为构建 Golang 微服务而设计的框架，抽象了常见的微服务功能，如日志记录、追踪、指标、配置管理和 Swagger API 文档生成。它支持多种数据库，处理数据库迁移，并且能够与 Kafka
  和 NATs 等消息代理进行 pub/sub 操作。此外，GoFr 还包括支持定时任务的 cron 作业功能。该框架旨在降低构建和维护微服务的复杂性，让开发者能够将更多精力集中于业务逻辑的编写，而非基础设施的管理。
- Java后量子密码学（Java post-quantum cryptography）。非对称加密――这一保障了多数现代通信安全的核心概念――依赖于数学上难以求解的问题。然而，今天使用的算法中的问题在量子计算机面前将变得容易解决，这推动了替代方案的研究。
- [Presidio](https://github.com/microsoft/presidio) 。Presidio 是一个数据保护 SDK，用于在结构化和非结构化文本中 识别 和 匿名化 敏感数据。它可以通过命名实体识别（NER）、正则表达式和基于规则的逻辑检测个人身份信息（PII），如信用卡号、姓名和位置等。
- [PydanticAI](https://ai.pydantic.dev/)。随着构建基于 LLM 的应用程序以及代理的技术的快速发展，构建和编排这种应用的框架往往难以跟上步伐，或者找到合适的长期适用的抽象。PydanticAI 是该领域的最新成员，旨在简化实现过程的同时， 避免不必要的复杂度。
- 资源受限应用中使用 Swift。自从 [Swift 6.0](https://www.swift.org/blog/announcing-swift-6/) 发布以来，这门语言已经超越了 Apple 生态系统的限制，通过对主要操作系统的改进支持，使在 资源受限应用中使用 Swift 变得更加可行。Swift 和 Rust 都基于 LLVM/Clang 编译器后端，这使得一方的技术进步也能惠及另一方。
- [Tamagui](https://github.com/tamagui/tamagui)。Tamagui 是一个用于高效共享 React Web 和 React Native 样式的库。它提供了一个 设计系统，包含可复用的已样式化和未样式化组件，可以在多个平台上无缝渲染。
- [torchtune](https://github.com/pytorch/torchtune)。torchtune 是一个专为 PyTorch 设计的库，用于编写、后训练以及实验性探索大语言模型。它支持单 GPU 和多 GPU 设置，通过 FSDP2 实现分布式训练。该库提供基于 YAML 的 recipes（配方），用于微调、推理、评估以及量化感知训练等任务。

### 暂缓

- Node 超载。 Node 超载 ：Node.js 经常被用在一些不合理的场景中，甚至在没有考虑其他替代方案的情况下就被选择了。尽管理解某些团队倾向于使用单一语言栈――即便要付出一些权衡的代价――但还是倡导倡导 多语言编程（Polyglot Programming） 。 当年 Node.js 因在 IO 密集型工作负载中的高效性而享有声誉，如今其他框架已经赶上，提供了更好的 API 和更优越的整体性能。另外，Node.js 从未适合计算密集型工作负载，这一局限性至今仍然是一个重大的挑战。

