# vol 33

## 本期主题

### 监督式智能编码代理

越来越多工具开始支持开发者直接在 IDE 内通过与 AI 聊天来驱动代码实现。在此模式下， AI 辅助工具不再局限于回答问题或生成代码片段，而是可以主动导航并修改代码、更新测试、执行命令，甚至主动修复 lint 错误和编译问题。Cursor、Cline 和 Windsurf 正在 IDE 集成工具领域引领这一趋势，GitHub Copilot 也在持续进步。尽管目前在辅助代码便携上已有了不错的成果，但在代码审查过程中仍需要保持高度的警惕与审慎。

### 可观测性的演进

 LLM observability（大语言模型可观测性）是将 AI 应用于生产环境的关键环节。目前已有众多用于监控和评估 LLM 性能的工具的涌现，包括 Weights & Biases Weave、Arize Phoenix、Helicone 和 HumanLoop 。同时，OpenTelemetry 的广泛采用正在推动可观测性走向标准化，帮助团队摆脱对特定供应商的依赖。

### RAG 中的 “R”： 检索的进化

与 LLM 这一“黑箱”的关键交互之一是定制提示词的输入，以生成相关且有用的响应。纠正型 RAG，可以根据反馈或启发式规则动态调整响应；融合型RAG，通过结合多种数据源和检索策略，提供更全面且稳健的响应；以及自助型 RAG，它完全避开传统的检索步骤，按需获取数据。 FastGraphRAG，通过生成可供人类浏览的图表，提升了数据的可理解性。

### 驾驭数据疆界

如何有效管理和包装非结构化数据数据，使其能够成功应用于 AI 技术、客户分析等领域，已成为当今企业发展的关键。

## 技术

### 采纳

- 数据产品思维。将数据视为具有自身生命周期、质量标准，并专注于满足消费者需求的“产品”。无论组织选择 数据网格 还是 Lakehouse 架构，都是不错的数据管理的默认选择。
- Fuzz测试。模糊测试，即向软件系统输入各种无效数据并观察其行为。在更多 AI 生成代码和 自满于 AI 生成的代码 的背景下，现在是采用 fuzz 测试的好时机，以确保代码的健壮性和安全性。
- 软件物料清单（SBOM）。工具如 Syft、Trivy 和 Snyk 能够从源代码到容器镜像生成全面的 SBOM，同时支持漏洞扫描。FOSSA 和 Chainloop 等平台通过与开发工作流集成以及实施安全策略，提升了安全风险管理能力。尽管统一的 SBOM 标准仍在演化，但对 SPDX 和 CycloneDX 的广泛支持已显著降低了采用门槛。
- 威胁建模。威胁建模是一组用于识别和分类潜在威胁的技术，必须贯穿软件生命周期的各个阶段并定期执行，同时与其他安全实践相结合才能发挥最佳效果。

### 试验

- API 请求集合做为 API 产品的制品。不仅需要在 API 本身中融入合理和标准化的设计，还需要提供全面的文档以及流畅的入门体。API 请求集合应经过精心设计，以引导开发者完成关键工作流程，帮助他们轻松理解 API 的领域语言和功能。为了保持请求集合的最新状态，建议将其存储在代码库中，将其与 API 的发布流水线集成在一起。
- 架构建议流程。任何人都可以做出架构决策，前提是他们向受影响的人和具有相关专业知识的人寻求建议。像 架构决策记录（ADR） 和建议论坛这样的实践能够确保决策是经过充分信息支持的，同时赋予那些最接近实际工作的人员决策权。
- GraphRAG。两步流程：（1） 对文档进行分块，并使用基于大语言模型的分析构建知识图谱；（2） 通过嵌入检索的方式在查询时检索相关块，沿着知识图谱的边缘发现更多相关的分块，这些分块后续会被添加到增强提示中。
- 按需特权访问管理（Just-in-time Privileged Access Management）。仅在需要时授予访问权限，并在任务完成后立即撤销权限，从而最大限度地降低暴露风险。可以通过自动化工作流程实现这一模型：触发轻量化的审批流程，为用户分配临时角色并限制访问权限，同时为每个角色强制设置生存时间（TTL），确保权限在任务完成后自动过期，从而进一步减少特权滥用的风险。
- 模型蒸馏。Scaling laws 是推动 AI 快速发展的关键原则之一，即更大的模型、更大的数据集和更多的计算资源能够带来更强大的 AI 系统。模型蒸馏 将知识从一个更大、更强的模型（教师模型）转移到一个更小、更高效的模型（学生模型）。这一过程通常包括从教师模型生成一个样本数据集，并对学生模型进行微调，以捕获其统计特性。与通过移除参数来压缩模型的 剪枝 技术或 量化 不同，蒸馏旨在保留领域特定的知识，同时将精度损失降到最低。一个显著的例子是 Qwen/Llama 的 DeepSeek R1 蒸馏版本，它们在小模型中保留了强大的推理能力。
- 提示工程（Prompt Engineering）。提示工程（Prompt Engineering） 是指为生成式 AI 模型设计与优化提示词（Prompt）的过程，其目标是生成
  高质量、上下文相关（Context-aware）的响应。
- 小语言模型（SLMs）。满血版 R1 拥有 6710 亿个参数，并且需要约 1342GB 的 VRAM 才能运行，这通常只能通过八块最先进的 NVIDIA GPU 组成的“迷你集群”来实现。尽管小型版本在性能上有所折损，但相较于以往的小语言模型，依然实现了巨大的性能飞跃。
- 利用生成式AI理解遗留代码库。这些工具综合运用多种生成式 AI 技术提供上下文感知（Context-aware）的帮助，简化了对复杂遗留系统的分析与处理。

### 评估

- AI友好的代码设计。AI 编程助手在结构良好的代码库上表现得更好，因此 AI 友好的代码设计 对于代码的可维护性也很重要。明确的命名可以为代码提供领域上下文和功能信息；模块化和抽象设计能够限制代码改动范围，使 AI 的工作上下文更易于处理；而 DRY（don’t repeat yourself）原则则能减少重复代码，让 AI 更容易确保行为一致性。
- AI驱动的UI测试。利用 LLM 的能力来理解图形用户界面（GUI）。
- 能力边界作为理解系统故障的模型。[优雅扩展性理论](https://www.researchgate.net/publication/327427067_The_Theory_of_Graceful_Extensibility_Basic_rules_that_govern_adaptive_systems) 定义了适应性系统（包括构建和操作软件的社会技术系统）的基本规则。这一理论中的一个关键概念是能力边界（competence envelope） ―― 系统在面对失败时能够稳健运作的边界。
- 从LLMs获取结构化输出。通过指示通用模型以特定格式响应，或者通过微调模型使其“原生”输出例如 JSON 的结构化数据。这在函数调用、API 交互和外部集成中尤其有价值，因为这些场景中格式的准确性和一致性至关重要。

### 暂缓

- AI 加速影子 IT（AI-accelerated Shadow IT）。一些无代码（No-code）工作流自动化平台已支持对 AI API（如 OpenAI 或 Anthropic）的集成，这使得用户可能倾向于将 AI 用作“胶带”，将此前难以实现的系统集成临时拼凑起来。例如通过 AI 将聊天消息转换为ERP 系统的 API 调用，这在长期运行后往往会造成规模更大的技术债（Tech Debt）。如果不加管控，这种新型影子 IT 将导致未经治理的应用程序激增，安全隐患加剧，数据分散在不同系统内。
- 自满于 AI 生成的代码。AI 驱动的信心往往以牺牲批判性思维为代价――这种模式在长期使用编码助手时表现得尤为明显。而 [vibe coding](https://arstechnica.com/ai/2025/03/is-vibe-coding-with-ai-gnarly-or-reckless-maybe-some-of-both/) 的出现――即开发者在审查极少的情况下让 AI 生成代码――更是说明了人们对 AI 生成输出的信任正在增长。虽然独立开发者或许可以接受在个人项目中采用直觉式编码的弊端，但企业环境通常要求代码具备良好的可维护性和可靠性，而直觉式编码的解决方案可能难以满足这些标准。
- 本地编码助手。本地助手落后于依赖更大型、更强大模型的云端助手。难以处理复杂的提示词，缺乏解决更大问题所需的上下文窗口，并且通常无法触发工具集成或函数调用。除非你能接受较低的期望值。
- 使用AI代替结对编程。把编码助手当做结对编程者忽略了结对编程的一个关键收益 : **它可以让团队而不只是个人变得更好**。在帮助解决难题，学习新技术，引导新人，或者提高技术任务的效率从而让团队更关注战略性设计等这些方面，使用编程助手确实大有裨益。但在诸如将正在进行的工作的数量控制在低水平，减少团队交接与重复学习，让持续集成成为可能，或者改善集体代码所有权等等这些团队合作相关的层面，它没有带来什么好处。
- 逆向ETL（Reverse ETL）。常规 ETL 在传统数据架构中很常见，它将数据从事务处理系统传输到集中式分析系统（如数据仓库或数据湖）。在这种架构中，将数据从集中分析系统逆向回流到事务处理系统，在某些特定场景下有其合理性，例如，集中系统可以汇总来自多个来源的数据，或者在向数据网格迁移的过程中，作为一种 过渡架构 的一部分。然而，利用 Reverse ETL 的概念将越来越多的业务逻辑转移到一个集中式的平台（即它们的产品）这种做法并不明智。这种方法加剧了集中式数据架构所导致的许多问题。
- SAFe。 SAFe™ （Scaled Agile Framework®）（规模化敏捷框架）正被广泛采用。同时 SAFe 过度标准化和阶段性门限的流程会造成阻碍，这可能助长信息孤岛，其自上而下的管控模式会在价值流中产生浪费，并抑制工程人才的创造力，还会限制团队的自主性和实验空间。

## 平台

### 采纳

- GitLab CI/CD。支持复杂的工作流，包括多阶段流水线、缓存、并行执行和自动扩展运行器，非常适合大型项目和复杂流水线需求。
- Trino。Trino 是一个开源的分布式 SQL 查询引擎，专为大数据的交互式分析查询而设计。它针对本地和云端环境进行了优化，支持直接在数据驻留的位置进行查询，包括关系型数据库和各种专有数据存储（通过连接器）。

### 试验

- ABsmartly。ABsmartly 是一款先进的 A/B 测试与实验平台，专为快速且可信的决策制定而设计。核心亮点是 Group Sequential Testing （GST） 引擎，与传统 A/B 测试工具相比，可将测试结果的速度提升高达 80%。
- Dapr。它包括任务调度、虚拟角色以及更为复杂的重试策略和可观察性组件。
-  Grafana Alloy。Grafana Alloy 是一个开源的 OpenTelemetry Collector。Alloy 被设计为一个一体化的遥测数据收集器，用于收集包括日志、指标和跟踪在内的所有遥测数据。
- Grafana Loki。Grafana Loki 是一个受 Prometheus 启发的横向可扩展，高可用的多租户日志聚合系统。Loki 只对日志的元数据进行索引，并把它当做日志流的标签集，而日志数据本身则储存在像 S3, GCS 或 Azure Blob Storage 这样的块存储方案中。这样的好处是 Loki 比竞争对手的运维复杂度更低，同时也降低了存储成本。
- Grafana Tempo。Grafana Tempo 是一个高可扩展的分布式追踪后端，支持诸如 OpenTelemetry 等开放标准。Tempo 专为成本效率设计，依赖对象存储进行长期追踪数据的保存，并支持追踪查询、基于 Span 的指标生成以及与日志和指标的关联。
- Railway。近年来，像 Vercel 这样更现代、轻量且易用的平台兴起，但它们主要面向前端应用的部署。而在全栈部署领域的一个替代选择是 Railway，这是一个云端 PaaS 平台，简化了从 GitHub/Docker 部署到生产环境可观测性的整个流程。
- Unblocked。[Unblocked](https://getunblocked.com) 是一款现成的 AI 团队助手。通过与代码库、企业文档平台、项目管理工具以及沟通工具的集成，Unblocked 能帮助解答关于复杂业务和技术概念、架构设计与实现以及操作流程的问题。
- Weights & Biases。他们扩展了 Traces 并推出了 Weave，一个超越 LLM 系统跟踪的完整平台。Weave 支持创建系统评估、定义自定义指标、使用
  LLM 作为任务（如摘要）的评判工具，并保存数据集以捕捉不同行为进行分析。这有助于优化 LLM 组件，并在本地和全局层面跟踪性能。

### 评估

- Arize Phoenix。LLM 可观测性的重要性不断凸显。此前已了解过 Langfuse 和 Weights & Biases （W&B） 等平台。 Arize Phoenix 是该领域另一个值得关注的新兴平台。Phoenix 提供了诸如 LLM 链路追踪、评估和提示词管理等核心功能，并可与主流 LLM 提供商和开发框架无缝集成，以在低成本、低配置的情况下实现对 LLM 输出内容、延迟和 Token 消耗等指标的深度洞察。
- Chainloop。Chainloop 是一个开源的软件供应链安全平台，帮助安全团队强制执行合规性要求，同时允许开发团队将安全合规无缝集成到 CI/CD 流水线中。
- DeepSeek R1。DeepSeek-R1 是 DeepSeek 推出的第一代 推理模型 。在一系列非推理模型的基础上，DeepSeek 的工程师设计并应用了多种方法来最大化硬件使用率。这些方法包括多头潜在注意力（Multi-Head Latent Attention, MLA）、专家混合（Mixture of Experts, MoE）门控、8 位浮点训练（FP8）以及底层 PTX 编程。这些方法结合其 [高性能计算协同设计](https://martinfowler.com/articles/deepseek-papers.html#Deepseek-v3HpcCo-design) 方法使 DeepSeek-R1 在显著降低训练和推理成本的同时，达到与最先进模型（state-of-the-art）相媲美的表现。DeepSeek-R1-Zero 另一个显著创新在于 : 工程师们可以通过简单的强化学习（RL），无需监督微调（SFT）即可让非推理模型展现出推理能力。
- Deno。Deno 具有更严格的沙盒机制、内置的依赖管理以及原生的 TypeScript 支持。[Deno 2 版](https://deno.com/blog/v2.0)本 引入了对 Node.js 和 npm 库的兼容
  性支持，并推出了长期支持 （LTS） 版本及其他改进。Deno 的 [标准库](https://deno.com/blog/v2.0#the-standard-library-is-now-stable) 已趋于稳定，有助于遏制 npm 生态中过多低价值软件包的泛滥。
- Graphiti。[Graphiti](https://github.com/getzep/graphiti) 构建了动态且时间感知的知识图谱，能够捕捉不断演化的事实和关系。可以使用 GraphRAG 来挖掘数据之间的关系，从而提升检索与响应的准确性。在数据集不断变化的情况下，Graphiti 在图的边上维护了时间元数据，用以记录关系的生命周期。它能够将结构化和非结构化数据以离散的 Episodes 形式进行摄取，并支持基于时间、全文检索、语义和图算法的查询融合。 
- Helicone。[Helicone](https://www.helicone.ai/) 是一个面向企业需求的托管 LLMOps 平台，专注于管理 LLM 成本、评估 ROI 和降低风险。作为一个开源且以开发者为中心的平台，Helicone 支持生产级 AI 应用，覆盖整个 LLM 生命周期的提示词实验、监控、调试和优化。
- Humanloop。[Humanloop](https://humanloop.com/) 是一个新兴的平台，致力于通过在关键决策点引入人类反馈，使 AI 系统更加可靠、适应性更强并且更符合用户需求。平台提供了用于人工标注、主动学习和人工参与的微调工具，同时支持根据业务需求对大语言模型进行评估。此外，它还帮助优化生成式 AI 解决方案的生命周期，以更高的成本效益实现更大的控制和效率。
- 模型上下文协议（MCP）。在提示生成中，最大的挑战之一是确保 AI 工具能够访问与任务相关的所有上下文信息。这些上下文信息通常已经存在于我们每天使用的系统中，如维基、问题追踪器、数据库或可观察性系统。AI 工具与这些信息源的无缝集成可以显著提高 AI 生成输出的质量。它定义了 MCP 服务器和客户端，服务器访问数据源，客户端则集成并使用这些数据来增强提示。
- Open WebUI。[Open WebUI](https://github.com/open-webui/open-webui) 是一个开源的自托管 AI 平台，功能多样且强大。它支持兼容 OpenAI 的 API，并能够与OpenRouter 和 GroqCloud 等提供商集成。通过 Ollama，它可以完全离线运行，连接到本地或 自托管 的模型。
- pg_mooncake。[pg_mooncake](https://github.com/Mooncake-Labs/pg_mooncake) 是一个 PostgreSQL 扩展，用于添加列式存储和向量化执行功能。其列存表可存储为 Iceberg或 Delta Lake 表格，数据可以存放在本地文件系统或兼容 S3 的云存储中。
- 推理模型（Reasoning Models）。也被称为“思考模型”，在前沿数学和编码等 基准测试 中，它们已达到人类顶级水平的表现。推理模型通常通过强化学习（RL）或监督式微调（SFT）进行训练，增强了诸如逐步思考（思维链）、探索替代方案（思维树）和自我修正等能力。典型代表包括 OpenAI 的 o1 / o3 、 DeepSeek R1 和 Gemini 2.0 Flash Thinking 。然而，这些模型应被视为与通用大型语言模型（LLM）不同的类别，而非简单的高级版本。对于文本摘要、 内容生成或快速响应聊天机器人等简单任务，通用 LLM 仍然是更好的选择。建议在 STEM 领域、复杂问题解决和决策制定中使用推理模型。
- Restate。[Restate](https://restate.dev/) 是一个持久化执行平台，类似于 Temporal 。它提供
  了将工作流作为代码、状态事件处理、Saga 模式和持久化状态机等特性。Restate 使用 Rust 编写，并作为单个二进制文件部署，利用分布式日志记录事件，并通过基于 Flexible Paxos 的虚拟共识算法来实现，这保证了在节点故障时的持久性。注意，在分布式系统中最好避免使用分布式事务，因为这会带来额外的复杂性和不可避免的运维开销。
- [Supabase](https://supabase.com/) 是一个开源的 Firebase 替代方案，用于构建可扩展且安全的后端。它提供了一整套集成服务，包括 PostgreSQL 数据库、认证、即时 API、Edge Functions、实时订阅、存储以及向量嵌入。
- Synthesized。一个可以屏蔽和子集化现有生产数据，或生成具有统计相关性的合成数据的平台。
- Tonic.ai。专注于为开发、测试和 QA 环境生成真实且去标识化的合成数据。Tonic.ai 提供一套全面的工具，满足各种数据合成需求，这与 Synthetic Data Vault 更偏向于库的方式形成对比。
- turbopuffer。[turbopuffer](https://turbopuffer.com/) 是一个无服务器的多租户搜索引擎，能够在对象存储上无缝集成向量搜索和全文搜索。通过将对象存储用作预写日志并保持查询节点的无状态化，它非常适合高规模的搜索工作负载。
- VectorChord。VectorChord 是一个用于向量相似性搜索的 PostgreSQL 扩展，由 pgvecto.rs 的创建者开发，作为其继任者。它开源，与 pgvector 数据类型兼容，并且专为磁盘高效和高性能的向量搜索而设计。

### 暂缓

- Tyk hybrid API management。Tyk 的 AWS 托管环境中，控制平面发生的故障通常是通过内部发现的，而非由 Tyk 主动检测到，这暴露了 Tyk 在可观测性方面的潜在不足。事故支持的响应速度似乎偏慢，以及文档的成熟度不足。

## 工具

### 采纳

- Renovate。虽然 Dependabot 仍然是 GitHub 仓库的一个安全默认选项，这里依然推荐评估 Renovate，因为它提供了更全面且可定制的方案。为了减少开发者的工作量，可以考虑 自动合并依赖更新的 PR。
- uv。在基准测试中，uv 的性能远超其他 Python 包管理工具，加速了构建和测试周期。除了性能，uv 还提供了统一的工具集，有效取代了像 Poetry、pyenv 和 pipx 等工具。
- Vite。作为一个高性能的前端构建工具，Vite 提供了快速热重载的特性。它正在被众多前端框架采用并推荐为默认选择，比如 Vue, SvelteKit。

### 试验

- [Claude Sonnet](https://www.anthropic.com/news/claude-3-5-sonnet) 是一款擅长编码，写作，分析和视觉处理的先进语言模型。它可在浏览器，终端和大多数主流IDE 中使用，并支持与 GitHub Copilot 集成。尽管目前还难言有 AI 模型是“稳定”的编码助手，但 Claude Sonnet 已经是比较可靠的一个。
- Cline。[Cline](https://github.com/cline/cline) 是一个开源的 VSCode 扩展程序，目前在监督型 软件工程代理 领域中是最强有力的竞争者之一。Cline 展现了在处理复杂开发任务方面的高级能力，尤其是在结合 Claude 3.5 Sonnet 时表现突出。它支持处理大型代码库、自动化无头浏览器测试，并能够主动修复错误。
- [Cursor](https://www.cursor.com/)。一款以 AI 为核心的代码编辑器，其代码上下文编排功能十分高效，并支持多种模型，包括使用自定义 API 密钥的选项。Cursor 已推出创新的用户体验功能，并在聊天功能中集成了丰富的上下文提供者，例如 Git 差异对比（git diffs）、先前的 AI 对话、网络搜索、库文档以及 MCP 集成等。
- D2。[D2](https://github.com/terrastruct/d2) 是一个开源的 图表即代码 工具，帮助用户通过文本创建和定制图表。它引入了 D2 图表脚本语言，以简单的声明式语法优先保证可读性。自带默认 主题，并使用与 Mermaid 相同的 布局引擎 。其轻量化的语法，尤其适用于软件文档和架构图的场景。
- Databricks Delta Live Tables。[Delta Live Tables](https://www.databricks.com/product/data-engineering/dlt)（DLT）在简化和优化数据管道管理方面持续展现出其价值，通过声明式方法支持实时流处理和批量处理。通过自动化复杂的数据工程任务（如手动检查点管理），DLT 减少了运营开销，同时确保了端到端系统的稳健性。作为一种有主见的抽象层，DLT 自行管理表数据，并限制每次仅允许单个管道插入数据。流式表仅支持追加操作，这需要在设计中仔细考量。此外，删除 DLT 管道时会同时删除底层表和数据，可能带来一定的操作性问题。
- JSON Crack。[JSON Crack](https://marketplace.visualstudio.com/items?itemName=AykutSarac.jsoncrack-vscode) 是一个用于在 Visual Studio Code 中将文本数据渲染为交互式图表的扩展工具。尽管其名称中包含“JSON”，但它实际上支持包括 YAML、TOML 和 XML 在内的多种格式。
- [MailSlurp](https://docs.mailslurp.com/)。它是一个邮件服务器和 SMS API 服务，提供用于创建收件箱和电话号码的 REST API，同时还支持直接在代码中验证电子邮件和消
  息。其无代码的仪表板对于手动测试准备也非常有用。
- Metabase。Metabase 是一款开源的分析和商业智能工具，允许用户从各种数据源（包括关系型数据库和 NoSQL 数据库）中可视化和分析数据。该工具帮助用户创建可视化和报告，将其组织到仪表板中，并轻松分享数据洞察。
- NeMo Guardrail。是 NVIDIA 提供的一个易于使用的开源工具包，可帮助开发者为用于对话式应用的大型语言模型实施“护栏”，集中在扩展集成能力和加强安全性、数据管理及控制方面。
- Nyx。Nyx 是一个多功能的语义化版本发布工具，支持各种软件工程项目。它对编程语言无依赖，并兼容所有主流的持续集成和源代码管理平台。尽管许多团队在 主干开发 中使用语义化版本管理，Nyx 还支持 Gitflow、OneFlow 和 GitHub Flow 等工作流。在生产环境中，Nyx 可以自动生成变更日志，并且内置支持 Conventional Commits 规范。
- OpenRewrite。进行大规模代码重构的得力工具。除了对 Java 的强大支持外，OpenRewrite还引入了对 JavaScript 等语言的支持。OpenRewrite 内置丰富规则集（recipes），这些规则明确描述了需要执行的更改。其重构引擎、内置规则集以及构建工具插件均为开源软件。
- Plerion。是一个专注于 AWS 的云安全平台，通过与托管服务提供商集成，帮助发现云基础设施、服务器和应用程序的风险、错误配置和漏洞。
- 软件工程代理（Software engineering agents）。即在 IDE 内的监督代理模式（supervised agentic modes）。允许开发者通过聊天驱动实现，工具不仅可以修改多个文件中的代码，还能执行命令、运行测试并响应 IDE 反馈（如 linting 或编译错误）。这种方式有时被称为“面向聊天的编程（chat-oriented programming， CHOP）”或“从提示到代码（prompt-
  to-code）”，它让开发者保持控制的同时，将更多责任转移给 AI，这跟传统的自动补全类辅助工具有很大不同。
- Tuple。[Tuple](https://tuple.app/) 是一款专为远程结对编程优化的工具，最初设计是为了填补 Slack 的 Screenhero 停止服务后留下的空白。现在已支持 Windows 平台。一个关键改进是增强了桌面共享功能，新增的隐私功能允许用户在共享屏幕时隐藏私人应用窗口（如短信），同时专注于共享工具（例如浏览器窗口）。
- Turborepo。[Turborepo](https://turbo.build/repo) 通过分析、缓存、并行化和优化构建任务，帮助管理大型 JavaScript 或 TypeScript monorepo，从而加速构建过程。在大型 monorepo 中，项目之间通常存在相互依赖关系；每次更改时重新构建所有依赖项既低效又耗时，而 Turborepo 简化了这一过程。与 Nx 不同，Turborepo 的默认配置使用多个 package.json 文件（每个项目一个）。

### 评估

- AnythingLLM。AnythingLLM 是一个开源桌面应用程序，可以与大型文档或内容交互，支持开箱即用的大语言模型（LLMs）和向量数据库集成。它具备可插拔的嵌入模型架构，可以与大多数商业化 LLM 以及由 Ollama 管理的开源模型一起使用。除了支持 检索增强生成（RAG） 模式外，用户还可以创建和组织不同技能作为代理（agents）来执行自定义任务和工作流。
- Gemma Scope。机械解释性（Mechanistic Interpretability）――理解大型语言模型的内部运行机制――正在成为一个日益重要的领域。像 Gemma Scope 和开源库 Mishax 这样的工具，为 Gemma2 系列开源模型提供了深入的洞察。这些解释性工具在调试模型的意外行为、识别导致幻觉、偏见或其他失败案例的组件方面发挥了关键作用，并通过提供更深入的可见性来建立对模型的信任。
- Hurl。[Hurl](https://hurl.dev/) 是处理一系列 HTTP 请求的利器，这些请求可通过使用 Hurl 特定语法的纯文本文件定义。虽然像 Bruno 和 Postman 这样的专业工具提供了图形用户界面以及更丰富的功能，但 Hurl 更以简洁著称。
- Jujutsu。Jujutsu 提供了一个完整的 Git 替代方案，同时通过 [使用 Git 仓库作为存储后端](https://jj-vcs.github.io/jj/latest/git-compatibility/) 保持兼容性。这使开发者能够继续使用现有的 Git 服务器和服务，同时受益于 Jujutsu 简化的工作流。Jujutsu 将自己定位为“既
  简单又强大”，为不同经验水平的开发者提供易用性。
- kubenetmon。监控和理解与 Kubernetes 相关的网络流量是一项挑战，尤其是当您的基础设施跨多个可用区、区域或云时。开源的 kubenetmon，旨在通过提供主要云提供商之间详尽的 Kubernetes 数据传输计量解决这一问题。
- Mergiraf。Mergiraf 是一款新工具，通过查看语法树而不是将代码视为文本行来解决Git合并冲突。
- ModernBERT。BERT（Bidirectional Encoder Representations from Transformers）的继任者 [ModernBERT](https://huggingface.co/blog/modernbert) 是一系列新一代的 encoder-only transformer 模型，专为广泛的自然语言处理（NLP）任务设计。ModernBERT 不仅提升了性能和准确性，还解决了 BERT 的一些局限――特别是通过引入“交替注意力”（Alternating Attention）实现了对极长上下文长度的支持。
- OpenRouter。OpenRouter 是一个统一的 API，可以用于访问多个大型语言模型（LLM）。它为 主流 LLM 提供商 提供了单一的集成点，简化了实验过程，降低了供应商锁定的风险，并通过将请求路由到最合适的模型来优化成本。像Cline 和 Open WebUI 这样的流行工具都使用 OpenRouter 作为它们的端点。
- Redactive。Redactive 是一个企业级 AI 赋能平台，专为帮助受监管的组织安全地为 AI 应用（例如 AI 助手和协作工具）准备非结构化数据而设计。它可以与像 Confluence 这样的内容平台集成，创建用于 检索增强生成（RAG） 搜索的安全文本索引。
- System Initiative。这款实验性工具为 DevOps 工作开辟了一条全新的激进方向。希望它能够激励更多人突破基础设施即代码（Infrastructure-as-Code）的现状。
- TabPFN。[TabPFN](https://github.com/PriorLabs/TabPFN) 是一个基于 Transformer 的模型，专为在小规模表格数据集上实现快速而准确的分类而设计。它利用了上下文学习（In-Context Learning, ICL）， 直接从标注样本中进行预测，无需超参数调整或额外训练。TabPFN 在数百万个合成数据集上预训练，能够很好地泛化到不同的数据分布，同时对缺失值和异常值具有较强的处理能力。它的优势包括高效处理异构数据以及对无信息特征的鲁棒性。
- v0。[v0](https://v0.dev/) 是由 Vercel 开发的一款 AI 工具，可根据截图、Figma 设计或简单的提示生成前端代码。v0 为原型设计提供了一个稳固的工具，并可作为开发复杂应用程序的起点初始化代码。
- Windsurf。[Windsurf](https://codeium.com/windsurf) 是 Codeium 推出的 AI 编程助手，以其“代理型”（agentic）能力而闻名。类似于 Cursor 和 Cline，Windsurf 允许开发者通过 AI 聊天驱动实现代码的导航、修改以及命令的执行。它经常发布针对“代理模式”的全新功能和集成。
- YOLO。YOLO11 可以在 极少资源消耗下高速 运行图像分类任务，这使其适用于边缘设备的实时应用。此外，还可以执行姿势估计，物体检测，图像分割以及其他任务。

## 语言和框架

### 采纳

- [OpenTelemetry](https://opentelemetry.io)。OpenTelemetry 正迅速成为可观察性领域的行业标准。随着 OpenTelemetry 协议 （OTLP） 规范的发布，行业内有了一个标准化的方式来处理追踪 （traces）、指标 （metrics） 和日志 （logs）。这减少了在监控分布式解决方案和满足互操作性需求时的多重集成或主要代码重构的需要。随着 OpenTelemetry 扩展对日志和性能分析的支持，OTLP 为所有遥测数据提供了一个一致的传输格式，简化了应用的仪表化过程，使全栈可观察性对于微服务架构更加易于实现且具有可扩展性。
- React Hook Form。将 [React Hook Form](https://react-hook-form.com/) 视为 Formik 的替代方案。由于默认使用非受控组件，它提供了显著更好的开箱即用的表现，特别是在大型表单上。React Hook Form 很好地和各种基于 schema 的验证库（比如 Yup, Zod 等）进行了集成。你可以把React Hook Form 和像 shadcn 或者 AntD 这样的外部受控组件库一起使用。

### 试验

- Effect。[Effect](https://effect.website/) 是一个 TypeScript 库，用于构建复杂的同步和异步程序。在 Web 应用开发中，常常需要为异步处理、并发、状态管理和错误处理等任务编写大量样板代码。 Effect-TS 通过采用函数式编程的方法简化了
  这些流程。相比使用 fp-ts 进行函数式编程， Effect-TS 提供的抽象更贴近日常任务的需求，同时使代码更易于组合和测试。
- [Hasura GraphQL engine](https://github.com/hasura/graphql-engine)。Hasura GraphQL engine 是一个通用的数据访问层，可简化在不同数据源上构建、运行和管理高质量 API 的过程。它能够为各种数据库（包括 PostgreSQL、MongoDB 和 ClickHouse）及数据源即时生成 GraphQL API，使开发者能够快速且安全地获取所需数据。Hasura 在实现 服务端资源聚合 的 GraphQL 应用场景中非常易用，然而对于其强大的联合查询和统一模式管理功能，仍然保持 谨慎态度 。
- [LangGraph](https://github.com/langchain-ai/langgraph)。LangGraph 是一款面向基于 LLM 的 多 agent 应用构建的编排（Orchestration）框架。与抽象程度较高的LangChain 相比，它提供了更底层的节点（Nodes）和边（Edges）等基本原语， 允许开发者精细地控制 agent工作流、记忆管理与状态持久化。
- [MarkItDown](https://github.com/microsoft/markitdown)。MarkItDown 能将多种格式（PDF、HTML、PowerPoint、Word）转换为 Markdown，从而增强文本的可读性和上下文保留。由于 LLM 可以从 格式化提示 （如标题和章节）中获取上下文，Markdown 能够很好地保留结构以提升理解能力。
- [Module Federation](https://module-federation.io/guide/start/index.html)。Module Federation 允许在微前端之间指定共享模块并实现依赖的去重。随着 2.0 版本的发布，模块联邦已经发展到可以独立于 webpack 工作。此更新引入了一些关键功能，包括联邦运行时（Federation Runtime）、全新的插件 API，以及对流行框架（如 React 和 Angular）及热门构建工具（如 Rspack 和 Vite）的支持。
- [Prisma ORM](https://www.prisma.io/orm)。Prisma ORM 是一个开源的数据库工具包，帮助 Node.js 和 Typescript 应用简化应用数据库操作。它提供了一种类型安全的数据库访问方式，能够自动化数据库模式迁移，并提供直观的查询 API。与传统 ORM不同，Prisma ORM 使用纯 JavaScript 对象定义数据库类型，而无需依赖装饰器或类。

### 评估

- .NET Aspire。[.NET Aspire](https://learn.microsoft.com/zh-cn/dotnet/aspire/get-started/aspire-overview) 旨在简化开发者本地机器上分布式应用的编排工作。Aspire 允许您在本地开发环境中编排多个服务，包括多个 .NET 项目、依赖的数据库和 Docker 容器――所有这些都可以通过一条命令完成。
- Android XR SDK。这是一款专门为扩展现实（Extended Reality, XR）设计的操作系统。它在未来计划支持智能眼镜以及其他智能设备。大多数的安卓应用无需修改或者只需少量修改即可运行在该系统上，它的核心理念是从零构建全新的空间应用，或者将现有的应用“空间化”。
- Browser Use。一个开源的 Python 库，使基于 LLM 的 AI 代理能够使用网页浏览器并访问 Web 应用。
- CrewAI。CrewAI 是一个专为构建和管理 AI 代理而设计的平台，它能让多个 AI 代理协同工作，共同完成复杂任务。可以使用 CrewAI 去应对生产环境中出现的问题，例如自动验证促销码，调查交易失败的原因以及处理客户支持相关的请求。
- ElysiaJs。[ElysiaJS](https://elysiajs.com/) 是一个端到端类型安全的 TypeScript Web 框架，最初主要为 Bun 设计，但也兼容其他的 JavaScript 运行环境。与 tRPC 等替代方案不同（这类方案会强制要求特定的 API 接口结构），ElysiaJS 不强制任何特定的 API 接口结构。开发者可以创建符合行业标准的 API，如 RESTful、JSON: API 或 OpenAPI，同时仍能提供端到端的类型安全。
- FastGraphRAG。[FastGraphRAG](https://github.com/circlemind-ai/fast-graphrag) 是 GraphRAG 的一个开源实现，专为高检索准确性和性能而设计。它利用 个性化 PageRank 限制图导航范围，仅关注图中与查询最相关的节点，从而提升检索准确性并改善大语言模型（LLM）的响应质量。FastGraphRAG 还提供图形的可视化表示，帮助用户理解节点关系及其检索过程。该工具支持增量更新，非常适合处理动态和不断演变的数据集。
- Gleam。Erlang/OTP 是一个强大的平台，用于构建高并发、可扩展且具备容错能力的分布式系统。传统上其语言是动态类型的，而 Gleam 在语言层面引入了类型安全。Gleam 构建于 BEAM 之上，将函数式编程的表达能力与编译时的类型安全相结合，从而减少运行时错误并提高可维护性。
- GoFr。[GoFr](https://github.com/gofr-dev/gofr) 是一个专为构建 Golang 微服务而设计的框架，通过抽象常见的微服务功能（如日志记录、追踪、指标、配置管理和 Swagger API 文档生成）来简化开发工作。它支持多种数据库，处理数据库迁移，并且能够与 Kafka 和 NATs 等消息代理进行 pub/sub 操作。此外，GoFr 还包括支持定时任务的 cron 作业功能。
- Java后量子密码学（Java post-quantum cryptography）。Java 后量子密码学在 JDK 24 中迈出了第一步，引入了 JEP 496 和 JEP 497 ，分别实现了一种密钥封装机制以及一套数字签名算法。二者均基于标准并设计为可以抵挡未来可能的量子计算攻击。虽然来自 Open Quantum Safe 项目的 liboqs 已经提供了基于 C 语言的实现并提供了 JNI 封装，但原生 Java 版本实现的出现依旧鼓舞人心。
- Presidio。Presidio 是一个数据保护 SDK，用于在结构化和非结构化文本中 识别 和 匿名化 敏感数据。可以通过命名实体识别（NER）、正则表达式和基于规则的逻辑检测个人身份信息（PII），如信用卡号、姓名和位置等。
- PydanticAI。随着构建基于 LLM 的应用程序以及代理的技术的快速发展，构建和编排这种应用的框架往往难以跟上步伐，或者找到合适的长期适用的抽象。PydanticAI 是该领域的最新成员，旨在简化实现过程。
- 资源受限应用中使用 Swift。Swift 的日益普及，得益于其在性能与安全特性之间的良好平衡，包括强类型安全和自动引用计数（ARC）内存管理功能。虽然 Rust 的所有权模型提供了更强的内存安全保证，但 Swift 提供了一种不同的权衡方式，这种方式对某些开发者来说更加易于接受。Swift 和 Rust 都基于 LLVM/Clang 编译器后端，这使得一方的技术进步
  也能惠及另一方。凭借其将代码编译为优化机器码的能力、开源的开发模式以及不断扩展的跨平台支持，Swift正逐步成为应用范围更广的有力竞争者――远远超越了其最初的 iOS 根基。
- Tamagui。Tamagui 是一个用于高效共享 React Web 和 React Native 样式的库。它提供了一个 设计系统，包含可复用的已样式化和未样式化组件，可以在多个平台上无缝渲染。
-  torchtune。torchtune 是一个专为 PyTorch 设计的库，用于编写、后训练以及实验性探索大语言模型。它支持单 GPU 和多 GPU 设置，并通过 FSDP2 实现分布式训练。该库提供基于 YAML 的 recipes（配方），用于微调、推理、评估以及量化感知训练等任务。

### 暂缓

- Node 超载。Node.js 经常被用在一些不合理的场景中，甚至在没有考虑其他替代方案的情况下就被选择了。 Node.js 因在 IO 密集型工作负载中的高效性而享有盛誉，但如今其他框架已经赶上，提供了更好的 API 和更优越的整体性能。同时，Node.js 从未适合计算密集型工作负载，这一局限性至今仍然是一个重大的挑战。



