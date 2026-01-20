# vol 29

## 本期主题

### AI 辅助软件开发

许多代码辅助工具，如 GitHub Copilot、Tabnine 和 Codeium。

### 衡量生产力有多有效

如今幸运的是，业界已经不再使用代码行数作为产出衡量标准。

行业已经开始关注“工程效能”：我们不应该衡量生产力，而应该衡量我们知道对流程有贡献或有损害的事物。我们不应该专注于个体的活动，而应该关注系统中的浪费来源以及可以从经验上证明导致开发人员对“生产力”感知产生影响的条件。

真正的生产力衡量仍然难以捉摸。

### 众多大语言模型

目前的这类应用多使用类似聊天的界面进行交互，例如 ChatGPT 或 Google Bard。生态中的主要竞争者（例如 OpenAI 的 ChatGPT，Google Bard，Meta的 LLaMA 以及亚马逊的 Bedrock 等）在生态中占据重要地位。大语言模型的底层能力，包括更专业化和自行托管的能力，继续呈爆发性增长。

### 远程交付解决方案日臻成熟

远程工作提供了许多好处（包括更多样化的人才储备），但面对面交流的价值是显而易见的。团队不应中断重要的反馈循环，并且需要意识到在转向远程工作时所做的取舍。



## 技术

### 采纳

- 设计系统。设计系统定义了一系列的设计模式、组件库以及良好的设计和工程实践，以确保
  数字产品的一致性。设计系统从过去的企业风格指南演变而来，提供易于查找和使用的共享组件库和文档。设计系统已经成为跨团队和学科进行产品开发时的标准方法，每当需要新的视觉组件时，不用重新发明轮子，因此能够集中精力，专注解决产品本身的种种挑战。

  产品为中心的思维方式还要求组织思考是否应该允许和怎样向设计系统做出贡献，以及如何管理这些贡献——在这个话题上，推荐采用设计系统决策记录。维护一个良好的设计系统或组件库不光是技术工作，也同样是社交工作。

- 轻量级的 RFCs 方法。几乎所有数字原生和快速扩张的组织都使用 RFCs 来记录围绕设计、架构、技术和团队协作方式的决策。建议组织采用轻量级的 RFCs 方法。如果不限定范围并明确要点，这些文件往往会随着时间的推移而变得越来越长，类似于传统的解决方案架构文件一样最终被归档和遗忘。

### 试验

- 具有可访问性意识的组件测试设计。在测试验证元素时，通过 [ARIA](https://developer.mozilla.org/en-US/docs/Web/Accessibility/ARIA) 角色或者元素的其它语义化属性查找元素，而不采用元素的 test id 或 class 属性。像 Testing Library 的一些测试库甚至已经在文档中推荐了这一实践。
- 攻击路径分析。攻击路径分析是一种分析和评估潜在攻击路径的安全分析方式，黑客可能按照这些来自组织内系统网络的潜在攻击路径进行攻击。
- 自动合并依赖项更新 PR。Dependabot 等工具可以通过创建拉取请求（PR）来更新依赖项。不过，团队仍然需要制定工程纪律，以确保及时处理这些 PR，尤其是对长时间不活跃的应用程序或服务提交的 PR。
- 针对 FAIR 数据的数据产品思维。使用 [FAIR](https://en.wikipedia.org/wiki/FAIR_data)（可发现，可访问，可互通且可复用）原则进行数据运营。
- OIDC for GitHub Actions。通过使用 OpenID Connect（OIDC）等联合身份机制对流水线进行
  身份验证，以访问云服务。
- 使用 Terraform 创建监控和告警。建议团队除了云资源外，应使用 Terraform 创建监控和告
  警。这将实现更模块化的 IaC，更易于理解和维护。与所有 IaC 一样，同时使用多种方式进行配置变更，会带来不一致的风险。所以，建议禁用通过用户界面和 API 的方式处理配置变更，确保 Terraform 代码始终是唯一的真实生效的版本。
- ReAct 提示工程。ReAct 提示工程是一种用于提示大语言模型的方法，相较于思维链（CoT）等竞争方法，ReAct 旨在提高大语言模型的响应准确性。这一方法在一份 [2022 年的论文](https://arxiv.org/abs/2210.03629)中首次提出，其原理是将推理和行动结合起来（因此称为 ReAct）。这种方法有助于使大语言模型的响应更具解释性，相对于思维链减少了虚构性内容，从而提高了提示者获得他们想要的内容的机会。
- 检索增强生成。检索增强生成（RAG） 是一种结合预训练参数和非参数记忆的文本生成技术。它使你能够通过你的领域内特有的包含上下文的知识，来强化预训练模型中的现有知识。使用 RAG，你会先从非参数记忆中去检索相关文档集（一般是通过在向量数据库中的相似性搜索），再使用 LLM 中的参数记忆生成与检索出的文档一致的输出。RAG 对各种需要大量知识的 NLP 任务十分有用，包括问答，总结和故事生成。
- 基于风险的故障建模。基于风险的故障建模是一种用于了解系统发生故障的可能性、潜在影响和检测手段的方法。该方法首先识别可能的故障模式，然后对根本原因进行分析，并根据故障发生的可能性、影响的大小以及发现根本原因的概率给出评分。当跨职能团队在系统演进过程中迭代使用该方法时，效果最好。在安全方面，基于风险的故障建模可以作为威胁建模和攻击路径分析的有益补充。
- 大语言模型半结构化自然语言输入。将结构化输入与自然语言注释或标记结合使用，比仅使用自然语言或结构化输入产生更好的响应。在人工编写的代码中加入自然语言注释也会改善基于大语言模型的编码助手的输出质量。
- 追踪健康债务状况。在瞬息万变的数字环境中，专注于跟踪系统的健康状况与债务，可为维护和增强系统提供结构化的循证战略。
- 对告警规则的单元测试。Prometheus 等工具支持[对规则进行单元测试](https://prometheus.io/docs/prometheus/latest/configuration/unit_testing_rules/)。
- CI/CD 的零信任保护。强烈推荐为 CI/CD 流水线和基础设施引入零信任安全机制——尽可能少地信赖它们。1.如果可行，使用云供应商提供的联合身份校验机制，如 OIDC，来验证流水线，而不是赋予它们直接访问机密数据的权限。2.实行最小权限原则去最小化个人用户和执行器账户的权限，而不是使用具有无限访问权限的万能账户。3.使用一次性执行器替代重复使用执行器，来减少暴露先前任务的机密数据或在受到攻击的运行器上运行任务的风险。4.将执行代理和执行器上的软件更新到最新版本。5.像监控你的生产软件一样去监控你的 CI/CD 系统的完整性、保密性和可用性。

### 评估

- 通过依赖健康检查化解包幻觉风险。团队可以通过对依赖进行健康检查化解包幻觉风险：在选择依赖之前查看它的创建日期、下载数量、github 评论及星标数、贡献者数量、活动历史记录等。一些依赖健康检查可以在包存储仓库和 GitHub 上执行，而像 deps.dev 和 Snyk advisor 等工具也可以提供帮助。
- 设计系统决策记录。借鉴了用 ADR（架构决策记录）记录软件架构决策的思路，采用类似的格式，以设计系统决策记录来记录设计系统决策以及相应的依据、研究洞见和实验结果。
- GitOps。GitOps 是一项通过[控制回路](https://kubernetes.io/zh-cn/docs/concepts/architecture/controller/)模式进行应用部署的技术。Operator 能够将已部署的应用和配置（通常是 Git 仓库）保持同步。
- 大语言模型驱动的自主代理。这种方法显然仍处于早期发展阶段：自主代理通常存在高失败率和高昂的 AI 服务费用，前景不明。
- 平台编排。随着平台工程的广泛采纳，新一代的工具，超越了传统的平台即服务（PaaS）模型，为开发人员和平台团队之间提供了公开的合约。这个合约可能涉及在不同环境中提供云环境、数据库、监控、身份验证等功能。这些工具强制执行组织标准，同时允许开发人员通过配置自主访问多种环境。这些平台编排系统的案例包括 Kratix 和 Humanitec Platform Orchestrator。
- 自托管式大语言模型。大语言模型（LLMs）通常需要大量的 GPU 基础设施才能运行。对大语言模型进行量化可以减少内存需求，使高保真度模型可以在成本更低廉的硬件甚至是 CPU
  上运行。像 llama.cpp 这样的工作使大语言模型可以在包括树莓派、笔记本电脑和通用服务器在内的硬件上运行成为可能。

### 暂缓

- 忽略 OWASP 十大安全风险榜单。鉴于 OWASP 十大安全风险榜单的覆盖范围（Web 应用程序、API、LLM 及其他）、质量以及与持续变化的安全形势的相关性，建议团队不要忽略 OWASP 十大安全风险榜单。
- 用于服务端渲染（SSR）web 应用的 web 组件。必须在浏览器中加载和构建自定义 Web 组件
  时，页面加载性能会受到影响，即使在预渲染和精心调整组件的情况下，也几乎无法避免“无样式内容闪烁”或某些布局变化。



## 平台

### 采纳

- Colima。[Colima](https://github.com/abiosoft/colima) 是一个在 macOS 上替代 Docker Desktop 的方案。

### 试验

- CloudEvents。事件是事件驱动架构或无服务器应用中常见的机制。然而，生产者或云提供商通常以不同形式支持它们，这阻碍了跨平台和基础架构的互操作性。CloudEvents 是一个描述事件数据的通用格式的规范，旨在提供服务、平台和系统之间的互操作性。它提供了多种编程语言的 SDK，因此您可以将规范嵌入到应用程序或工具链中。CloudEvents 由云原生计算基金会
  （CNCF）托管，现作为孵化项目运行，已经获得了越来越多的行业关注。
- DataOps.live。DataOps.live 是一个自动化 Snowflake 环境的数据平台。受 DevOps 实践启发，DataOps.live 可以像在其他网络平台一样在数据平台中实施持续集成和持续交付（CI/CD），自动化测试，可观测性和代码管理。可以使用它来管理数据产品的全生命周期，包括代码和数据的开发、分支、部署。通过它的自动化环境管理，能够轻易建立、修改、自动销毁基于特征分支的环境。
- Google Cloud Vertex AI。通过 API 连接 AI 模型和实时数据或操作。 该平台已经发展到提供 GenAI 模型和集成支持。
- Immuta。一个数据安全平台。它的亮点包括能够将订阅和数据策略定义为代码、版本控制以及自动部署这些策略到更高的环境中。它基于属性的访问控制（ABAC） 允许我们将标签关联到数据源；如果用户与相同的标签关联，就会获得访问权限。
- Lokalise。[Lokalise](https://lokalise.com) 是一个全自动的本地化平台，它支持特定上下文的翻译。
- Orca。Orca 是一个专有的云安全平台，用于识别、优先级排序和修复安全风险和合规问题。它支持主流的云提供商和混合设置。Orca 拥有广泛的安全查询和规则，以持续监控已部署的工作负载，检测配置错误、漏洞和合规性问题。它支持云虚拟机、无服务器函数、容器以及已部署工作负载的 Kubernetes 上部署的应用。
- Trino。[Trino](https://trino.io/) 以前被称之为 PrestoSQL，是一个专为面向大数据交互式分析查询而设计的开源分布式 SQL 查询引擎。它可以在本地或者云上环境运行，并支持对 Hive、Cassandra、关系型数据库、甚至专有数据存储等多种不同的数据源进行查询。它支持基于密码的认证、LDAP 和 OAuth 的身份验证机制，同时具备在 catalog、schema 和 table 级别授予权限和访问控制的能力。
- Wiz。[Wiz](https://www.wiz.io/) 是日渐成熟的云安全平台领域里又一竞争者，它能让用户在一个平台上预防、检测和应对安全风险和威胁。Wiz 能对尚未部署到生产环境的构建产物（容器镜像、基础设施代码）以及生产工作负载（容器、虚拟机和云服务）的错误配置、漏洞和泄漏的机密数据进行检测并发出警报。

### 评估

- ActivityPub。[ActivityPub](https://www.w3.org/TR/activitypub/) 是一个用于分享诸如帖子、出版物和日期等信息的开放协议。它可以用来实现一个社交媒体平台，但其关键优势在于能够实现不同社交媒体平台之间的协同工作能力。
- Azure Container Apps。Azure 容器应用是一种 Kubernetes 命名空间托管服务。该服务通过消除 Kubernetes 集群和底层基础架构组件的复杂维护需求来简化容器化工作负载的部署，从而减轻了运维和管理负担。
- Azure OpenAI Service。它通过 REST API 、Python SDK 以及基于 Web 的界面提供对 OpenAI 的GPT-4、GPT-35-Turbo 和嵌入模型的访问。这些模型可以适应如内容生成、汇总、语义搜索和自然语言到代码的转换的任务，也可以通过少量学习和超参数的定制进行微调。
- [ChatGLM](https://github.com/THUDM/ChatGLM2-6B)。清华大学开发的 ChatGLM 是一个开放的双语语言模型，基于通用语言模型架构，针对中文会话进行了优化。由于中文在词语划分和语法方面较英语更为复杂，因此拥有一个针对中文进行优化的LLM 非常重要。
- Chroma。[Chroma](https://www.trychroma.com/) 是一个开源的向量存储和嵌入数据库，可用于增强由大语言模型（LLMs）驱动的应用程序。通过促进LLMs 中的领域知识的存储和利用，Chroma 弥补了 LLMs 通常缺乏内部存储器的不足。特别是在文本到文本应用中，Chroma 可以自动生成单词嵌入并分析它们与查询嵌入之间的相似性，从而大大简化操作。
- Kraftful。[Kraftful](https://www.kraftful.com/) 是一个自诩为产品构建者副驾驶的工具，现在已经处于领先地位。您可以将 30 多种用户反馈来源连接到这个平台，它可以分析数据并识别功能请求、常见投诉、用户喜欢的产品特点，甚至列出您的竞争对手。为了获取更多细节，您可以像向 ChatGPT 或 Google Bard 提问一样。一旦您确定了要从用户反馈中解决的问题，Kraftful 会基于所有基础数据（包括验收标准）为您生成用户故事，即使对经验丰富的产品经理和业务分析师来说它也是一个出色的助手。
- pgvector。[pgvector](https://github.com/pgvector/pgvector) 是一个用于 PostgreSQL 的开源向量相似性搜索插件。它能够在
  PostgreSQL 中搜索 embeddings，而无需仅为了相似性搜索而将数据转移到另一个存储中。
- Pinecone。[Pinecone](https://www.pinecone.io/) 是一个完全托管的、对开发人员友好的、云原生的向量数据库。
- wazero。 一个Go 编写的一个零依赖的 WebAssembly（WASM）运行时。

### 暂缓

-



## 工具

### 采纳

- [dbt](https://www.getdbt.com/)。一个  在ETL 工作流程中进行数据转换的首选工具。
- Mermaid。Mermaid 通过使用类似 Markdown 的标记语言来生成图表。Mermaid 添加
  了对更多图表和与源代码存储库、集成开发环境和知识管理工具集成的支持。
- Ruff。Ruff 是一个新的 Python linter。Ruff 有两个亮点：开箱即用的体验，以及性能。其中内置了 500 多条规则，可以轻松取代 Flake8 和它的许多插件。
- Snyk。[Snyk](https://snyk.io/) 提供静态应用程序安全测试（SAST）和软件组件分析（SCA）测试，以帮助您在软件开发生命周期中寻找、修复和监控安全问题。

### 试验

- AWS Control Tower。AWS Control Tower 内置了一个账户工厂，帮助自动化账户的配置流程，用于多团队的账户管理.
- Bloc。[Bloc](https://bloclibrary.dev) 是 Flutter 的一款响应式状态管理库。
- cdk-nag。cdk-nag 能够识别并报告 AWS CDK 应用程序或 CloudFormation 模板中的安全性和合规性问题。它附带了几个所谓的规则包：一个通用的 AWS 规则包，包括 AWS 认为的最佳实践检查，以及用于 HIPAA、NIST 和 PCI 合规性的规则包。
- Checkov。[Checkov](https://www.checkov.io/) 是一个专门用于基础设施即代码（laC）的静态安全扫描器。它支持多种基础设施语言，包括 Kubernetes 清单、Helm 图表、CloudFormation 模板和 Terraform。它可在 CI/CD 管道中轻松部署，防止各种云基础设施配置中出现潜在的安全漏洞。
- Chromatic。Chromatic 是一款可视化回归测试工具，可帮助捕捉网络应用程序中的用户界面回归。它的工作原理是拍摄用户界面组件的快照，并在组件发生变化时将其与之前的快照进行比较。Chromatic 是一种托管服务，可与流行工具的云代码托管服务集成。它建立在 Storybook 之上，进行组件级可视化回归测试。
- Cilium。Cilium 是一个为云原生环境如（Kubernetes 集群和其他容器编排平台）提供网络、安全性和可观察性的开源项目。Cilium 为路由或覆盖网络提供了一个第三层网络，并且还支持 L7 协议。通过将安全性从寻址中解耦，Cilium 可以作为一种新的网络保护层发挥重要作用。
- 云服务的碳足迹。[Cloud Carbon Footprint](https://www.cloudcarbonfootprint.org/)（CCF）是一款估算主要云服务提供商的云工作负载碳排放量的开源工具。它通过云平台的 API 查询资源使用数据，并使用多种（数据）来源跟踪碳排放情况。
- [容器结构测试](https://github.com/GoogleContainerTools/container-structure-test)。容器结构测试（CST）是由 Google 开发的一个工具，用于测试容器镜像的结构。CST 可以用于检查镜像文件系统中某个文件的存在或缺失，验证文件的内容，检查容器中发出的特定命令的输出或错误，并检查容器镜像的元数据（例如标签、入口点和命令），以确保符合 CIS Docker Benchmark 的规范。
- Devbox。Devbox 是一款基于终端的工具，具有便捷易用的界面，用于创建可重用，项目独立的开发环境，Devbox 利用Nix 软件包管理器，而无需使用虚拟机或容器。使用它消除不同项目的开发环境中 CLI 工具和自定义工具脚本的版本与配置不匹配的问题，以及标准化管理项目中不同语言包的提供。
- DX DevEx 360。[DX DevEx 360](https://getdx.com/products/devex360) 是一款基于调查的工具，它通过聚焦开发人员在日常工作中面临的阻力点，如代码审查流程、代码质量、深度工作能力等，寻找提高开发人员生产力的关键指标。
- GitHub Copilot。基于AI帮助，更快地编写代码。
- Insomnia。Insomnia 是一款专为 API 测试、开发和调试而设计的开源桌面应用程序。虽然 Insomnia 支持在线同步，但它可以让你离线保存 API 工作区数据。此外，建议留意其他各种形式的开发替代方案——从 GUI 工具（如即插即用的 Insomnia），到 CLI 工具（如 HTTPie），再到 IDE插件（如 IntelliJ HTTP 客户端插件）。
- IntelliJ HTTP 客户端插件。[IntelliJ HTTP 客户端插件](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html)允许开发人员在代码编辑器中创建、编辑和执行 HTTP 请求，从而简化了构建和使用API 的开发流程。
- KEDA。[KEDA](https://keda.sh/) 全称 Kubernetes Event-Driven Autoscaler，正如名字所展示的，它可以根据需要处理的事件数量来伸缩 Kubernetes 集群。根据我们的经验，相比采用 CPU 使用率等滞后指标，更加推荐使用**队列深度**等领先指标。
- Kubeconform。[Kubeconform](https://github.com/yannh/kubeconform) 是一个用来验证 Kubernetes 清单和自定义资源（CRD）的简化工具。它能很容易地在 CI/CD 流水线或本地机器中部署，通过在部署前验证资源，以减少潜在的错误。
- mob。[mob](https://github.com/remotemobprogramming/mob) 是一个用于远程结对编程或集体编程中无缝进行 git 交接的命令行工具。它将所有版本控制工具隐藏在一个命令行界面背后，这使参与集体编程会话变得更加简单。
- MobSF。[MobSF](https://github.com/MobSF/Mobile-Security-Framework-MobSF) 是一个开源的、自动化的静态和动态安全测试工具，用于检测 iOS 和 Android 移动应用程序中的安全漏洞。它扫描应用程序源代码和二进制文件，并提供有关漏洞的详细报告。
- Mocks Server。[Mocks Server](https://www.mocks-server.org/) 是一个基于 Node.js 的 API Mock 工具，它能够复制复杂的 API 响应、响应头和状态码。它的动态响应生成支持模拟多种场景，允许对 API 交互进行严格测试。Mock 可以描述为YAML 或 JSON，并通过 CLI、REST API 或 JavaScript 代码进行管理。Mocks Server 的功能包括请求匹配、代理和录制重现功能，这些功能有助于模拟真实的 API 交互。
- Prisma 运行时防护。[Prisma 运行时防护](https://docs.paloaltonetworks.com/prisma/prisma-cloud/prisma-cloud-admin-compute/runtime_defense)是 Prisma 云套件的一部分，为容器安全提供了一种新方法。它采用一种机制来建立容器预期行为模型，然后在运行期间发现异常时检测并阻止异常活动。Prisma 运行时防护监控容器进程、网络活动和文件系统，查找表明可能正在进行攻击的模式和变化，并根据配置的规则进行阻止。学习构成“正常”行为的模型是通过对 Docker 镜像的静态分析和预先配置的时间段内的动态行为分析构建的。
- Terratest。[Terratest](https://github.com/gruntwork-io/terratest) 是一个基础设施测试工具。它是一个 Golang 库，用来简化基础设施代码的自动化测试编写。通过基础设施即代码的工具，例如 Terraform，你可以创建真实的基础设施组件（如服务器、防火墙或负载均衡器），在它们之上部署应用程序，并使用 Terratest 验证预期的行为。
- Thanos。尽管 Prometheus 一直是自维护可观察性工具链中的一个可靠选择，但当监测指标在基数和总量上增长，以及开始需要高可用性设置时，许多管理现代云原生分布式系统的团队都会碰到其单节点的限制。Thanos 通过添加一些适用于大规模、长期和高可用性监控的功能来扩展 Prometheus。例如，它引入了一些组件将从Prometheus 实例中读取的数据存储到对象存储系统中，管理对象存储系统对数据的保留和压缩，并且横跨多个 Prometheus 实例做联合查询。
- Yalc。[Yalc](https://github.com/wclr/yalc) 是一个简易的本地 JavaScript 包管理库以及跨本地开发环境进行发布与包管理的工具。对于有一些限制的npm link 指令来说，Yalc 是一个更可靠的替代品。

### 评估

- ChatGPT。ChatGPT 可以为需求分析、架构设计或对遗留系统进行逆向工程等任务提供额
  外的视角和建议。**ChatGPT 最好作为一个流程的输入—— 例如起草一个故事的初稿或给出某项编码任务的框架——而不是一个生成“成熟”结果的工具**。提示工程本身就是一门艺术。
- Codeium。在 AI 编程辅助工具中，[Codeium](https://codeium.com/) 类似于 Tabnine，Codeium 也尝试去解决公司使
  用编程辅助工具最担心的问题：通过不使用无授权代码训练自己的模型，他们消除了部分开源代码许可证带来的担忧。
- GitHub 合并队列。推荐始终创建**短期开发分支**，这些分支通常会合并到主代码分支中，以确保主代码分支随时能进行部署。这种主干开发的实践与持续集成密切相关，并且在条件允许的情况下，可以实现**最快的反馈循环**和最高效的开发流程。有时，长期存在的特性分支和拉取请求必须被手动审查和批准，然后才能将它们合并到主分支中。在这些情况下，可以使用新的 GitHub 合并队列功能。它允许我们自动排队接收的拉取请求，并将它们合并到特殊分支中，按接收顺序进行排序。然后，我们可以选择自动执行我们自己的“合并检查”，以防止不兼容的提交。**这本质上是模拟主干开发（即使 PR 尚未合并到主代码分支中）**，允许开发人员在上下文中测试其功能，而无需等待批准拉取请求。**使用 GitHub 合并队列，即使你不能直接提交到主干，也可以获得主干开发的好处。**
- Google Bard。[Google Bard](https://bard.google.com/) 是由 Google AI 开发的聊天 AI 机器人，类似于 ChatGPT，并由 Pathways 语言模型（PaLM 2）支持。Bard 能生成文本、翻译语言、创作内容，回答问题，也能指导软件开发。尽管速度可能略慢，但开发人员应探索其潜在优点。虽然仍在开发中，翻译技术方面已取得不错成果。 
- Google Cloud 工作站。[Google Cloud 工作站](https://cloud.google.com/workstations?hl=zh-cn) 是 GCP 提供的云端开发环境（Cloud Development Environment，CDE），提供了完全托管的容器化开发环境，支持 SSH、HTTPS、VSCode、Jetbrains IDEs 等多种访问方式，使开发人员可以享受和连接本地环境一致的体验。它允许管理员将容器化开发环境纳入私有网络，并可以选择使其对外公开或仅在内部访问。
- [Gradio](https://github.com/gradio-app/gradio)。喜欢AI画图的人对这个单词应该不会感到陌生。Gradio 是一个开源的 Python 库，可以快速简单地为机器学习模型创建基于 Web 的交互式界面。基于机器学习模型的图形化界面，使得非技术受众能够更好地理解输入、约束和输出。Gradio 支持多种输入和输出类型，从文本到图像再到语音，并且它已经成为了快速原型设计和模型评估的首选工具。 Gradio 可以让您轻
  松地将您的演示托管到 Hugging Face，或者在本地运行它，然后允许他人通过带有“XXXXX.gradio.app”的URL 来访问您的远程演示。例如，著名的 DALL-E 迷你实验就是利用 Gradio 实现，并且托管在 Hugging Face Spaces。
- KWOK。[KWOK](https://github.com/kubernetes-sigs/kwok)（Kubernetes WithOut Kubelet）是一个模拟虚拟节点和 pod 生命周期的工具，旨在测试 Kubernetes 集群的控制面板。在没有显著大型集群的情况下，很难对自定义 Kubernetes 控制器和操作器进行压力测试。然而通过 KWOK，你可以在笔记本电脑上轻松设置一个拥有数千个节点的集群，而无需消耗大量 CPU 或内存资源。这种模拟支持节点类型和 pod 的不同配置，以测试各种场景和边缘情况。如果你需要一个真正的 Kubernetes
  集群来测试操作器和自定义资源定义（CRDs），推荐 [kind](https://github.com/kubernetes-sigs/kind) 或 k3s; 但如果你只需要模拟大量的虚拟节点，建议评估 KWOK。
- Llama 2。[Llama 2](https://ai.meta.com/llama/) 是一个来自 Meta 的强大的语言模型，可免费用于研究和商业用途。它既提供原始的预训练模型，也提供了经过微调的用于对话的 Llama-2-chat 和用于代码补全的 Code Llama。Llama 2 提供了多种尺寸的模型—— 7B、13B 和 70B，因此如果您想控制自己的数据，Llama 2 是自托管式大型语言模型的一个好选择。
- Maestro。[Maestro](https://maestro.mobile.dev/) 是一款新的跨平台移动端 UI 测试自动化工具，它内置了由网络因素导致的应用加载时长变化的容错能力。它使用声明式的 YAML 语法。Maestro 支持 iOS 和 Android原生应用、 React Native 和 Flutter 应用，能够自动化复杂的移动端 UI 交互（如点击、滚动和滑动）的各种功能。Maestro 以单个二进制文件进行发布，以解释器模式运行，并且通过连续模式等特性来简化新的测试的编写。尽管 Maestro 对 iOS 设备的特定功能还欠缺支持，但该工具正在迅速演进。
- Open-source LLMs for coding。如果您需要构建自己的编码辅助服务（比如受到高度监管的行业），可以考虑 [StarCoder](https://github.com/bigcode-project/starcoder) 和 [WizardCoder](https://github.com/nlpxucan/WizardLM/blob/main/WizardCoder/README.md)。StarCoder 使用由 BigCode 维护的大型数据集进行训练，而 Wizardcoder 是 Evol-Instruct 调整后的 StarCoder 模型。
- OpenCost。[OpenCost](https://www.opencost.io/) 是一个开源项目，用于监控基础设施成本，并以 Kubernetes 对象（Pod、容器、集群等）的粒度展示成本信息，涵盖各种集群内资源（CPU、GPU、RAM、存储、网络）的成本数据。它与多个云服务提供商的 API进行集成，以获取计费数据，并可配置用于本地 Kubernetes 集群的定价策略。
- OpenRewrite。[OpenRewrite](https://github.com/openrewrite/rewrite) 是一个代码智能工具。
- OrbStack。[OrbStack](https://orbstack.dev/) 是在 macOS 上运行 Docker 容器的一种方式。与 Docker Desktop 和 Colima相比，它更轻量、更快速并且更容易部署和使用。这个工具仍在开发阶段，目前功能较少，但其简洁和速度已经显示出了它的巨大潜力。
- Pixie。[Pixie](https://github.com/pixie-io/pixie) 是一个用于 Kubernetes 原生应用程序的可观察性工具。它通过利用 eBPF 从多个 数据源自动地采集遥测数据，以一种有趣的方式实现了可观察性。收集到的遥测数据被本地存储到每个节点上，并通过其控制平面 API 进行集中处理。
- Tabnine。[Tabnine](https://www.tabnine.com/) 是目前炙手可热的编程助手领域的有力竞争者之一。它提供了内嵌的代码补全建议，以及直接在 IDE 中进行对话的能力。

### 暂缓

-



## 语言和框架

### 采纳

- Playwright。通过使用 Chrome 开发者工具（DevTools）协议（CDP），Playwright 可以提供新功能并消除 WebDriver 中出现的许多问题。Playwright 的功能包括：内置自动等待，测试更可靠、更易于理解； 浏览器上下文，可让您测试跨标签页的持久会话是否正常工作；以及模拟通知、地理位置和黑暗模式设置的能力。和它功能定位类似的测试框架，还有 Cypress 和 Puppeteer。

### 试验

- .NET Minimal API。ASP.NET Core MVC 是一种用于构建托管 APIs 的 Web 应用程序的强大而灵活的方法。
- Ajv。[Ajv](https://ajv.js.org/) 是一个 JavaScript 库，用于根据使用 JSON Schema 定义的结构验证数据对象。对于验证复杂的数据类型，Ajv 既快速又灵活。它支持多种多样的 schema 特性，包括自定义关键字和格式。
- Armeria。[Armeria](https://armeria.dev/) 是一个用于构建微服务的开源框架。可以使用它来构建异步 API，利用服务装饰器来解决跨切面关注点，例如分布式跟踪或断路器。该框架支持 gRPC 和 REST 流量的端口复用以及其他巧妙的设计选择。
- AWS SAM。[AWS Serverless Application Model（SAM）](https://aws.amazon.com/serverless/sam/)是一款用于在 AWS 云基础设施上构建无服务器应用的开源框架。
- Dart。[Dart](https://dart.dev/) 是由 Google 开发的编程语言，支持构建跨平台的应用程序，包括 Web 浏览器、WebAssembly、桌面和移动应用。它的采纳是由 Flutter 的主导地位推动的。~~不写flutter的话谁用Dart啊。~~
- fast-check。[fast-check](https://github.com/dubzzz/fast-check) 是一个为 JavaScript 和 TypeScript 设计的基于属性的测试工具，能够自动生成测试数据，无需创建不同的测试即可仔细检查各种输入，这使得它更容易发现边缘场景。
- Kotlin with Spring。Kotlin 可以取代 Java，成为编写软件的主要语言。
- Mockery。[Mockery](https://github.com/vektra/mockery) 是一个成熟的 Golang 库， 它能够生成接口的 mock 实现，并模拟外部依赖的行为。通过类型安全的方法生成期望的调用，并通过灵活的方式 mock 返回值，它使得测试能够专注于业务逻辑，而无需担忧外部依赖的正确性。
- [Netflix DGS](https://www.thoughtworks.com/cn/radar/techniques/graphql-for-server-side-resource-aggregation)。大多时候，可以将 GraphQL 用于服务端资源聚合，并且使用多种技术实现服务端。对于使用 Spring Boot 开发的服务，可以使用 Netflix DGS。Netflix DGS 基于 graphql-java 开发，并提供了许多 Spring Boot 编程模型的特性与抽象。
- [OpenTelemetry](https://opentelemetry.io/)。它能够在多个服务和应用之间无缝地捕获、检测和管理遥测数据，从而改善观察堆栈。
- [Polars](https://github.com/pola-rs/polars)。Polars 是 Rust 实现的一个内存运行的 DataFrame 库。 与其他 DataFrame 库（如 Pandas）不同，Polars 是多线程、支持惰性求值、并且并行操作安全的。 Polars 使用 Apache Arrow 格式作为内存模型，以高效实现分析操作，并实现与其他工具的互用性。 如果您熟悉 Pandas，就可以快速上手 Polars 的 Python 绑定。
- Pushpin。Pushpin 是一种反向代理工具，充当处理长连接（如 WebSockets 和服务器发送事件）的后端服务器与客户端之间的中介。它提供了一种终止来自客户端的长连接的方法，意味着系统的其他部分可以从这项复杂工作中解放。它能有效管理大量持久连接，并自动将其分配给多个后端服务器，从而优化性能和可靠性。典型场景：使用 Pushpin 来处理移动设备的 WebSockets 实时通信。
- [Snowpark](https://docs.snowflake.com/en/developer-guide/snowpark/index)。它是一个引擎，能够将代码转换为 Snowflake 能够理解的 SQL。您在构建应用程序时无需将 Snowflake 中待处理的数据移动到您代码运行的地方。

### 评估

- 基准配置文件。不要与[安卓基准配置文件](https://developer.android.com/ndk/guides/graphics/android-baseline-profile)相混淆，[基准配置文件](https://developer.android.com/topic/performance/baselineprofiles/overview)是指导提前编译的安卓运行时配置文件。基准配置文件在发布前在开发机上创建，并随应用程序一起发布。因此，相较于依赖云配置文件（一种较早的相关技术），基准配置文件会更快可用。
- GGML。[GGML](https://github.com/ggerganov/ggml) 是一个机器学习的 C 语言库，它支持 CPU 推理。它定义了一种分布式大语言模型（LLMs）的二进制格式。
- GPTCache。[GPTCache](https://github.com/zilliztech/GPTCache) 是一个用于大型语言模型（LLM）的语义缓存库。在 LLM 前增设缓存层主要出于两种原因——通过减少外部 API 调用来提升整体性能，以及通过缓存近似响应来减少运营成本。不同于使用精确匹配的传统缓存方式，基于 LLM 的缓存解决方案需要对输入进行相似或相关匹配。 GPTCache 通过使用嵌入算法将输入转化为嵌入，再通过向量数据库对这些嵌入进行相似性搜索。
- 语法性别 API。借助 Android 14 中引入的[语法性别 API](https://developer.android.com/about/versions/14/features/grammatical-inflection)，可以正确地称呼用户。
- [htmx](https://htmx.org/)。与其他越来越复杂的预编译 JavaScript/TypeScript 框架不同，htmx 鼓励直接使用 HTML属性来实现诸如 AJAX、CSS transitions、WebSockets 和服务器发送事件等操作。
- Kotlin Kover。[Kotlin Kover](https://github.com/Kotlin/kotlinx-kover) 是一个专为 Kotlin 设计的代码覆盖率工具集。它支持 Kotlin JVM 、Multiplatform 和 Android 工程。代码覆盖率的重要性在于能够凸显未经测试的代码片段，从而增强软件的可靠性。
- [LangChain](https://github.com/hwchase17/langchain)。LangChain 是一个利用大语言模型（LLM）构建应用程序的框架。要构建实用的 LLM 应用，需要将其与用户或领域的特定数据结合起来，这些数据不属于训练数据的一部分。LangChain 利用提示管理、链式、代理 和文档加载器等功能填补了这一空白。如提示模板和文档加载器等组件的好处是可以加快产品上市速度。
- LlamaIndex。LlamaIndex 是一个旨在促进私有或领域特定数据与大语言模型（LLMs）集成的数据框架。它提供了从各种数据源获取数据的工具，包括 API、数据库和 PDF，然后将这些数据结构化为 LLMs 能够轻松消费的格式。
- [promptfoo](https://github.com/promptfoo/promptfoo)。promptfoo 是一款测试驱动的 prompt engineering。
- [Semantic Kernel](https://pypi.org/project/semantic-kernel/)。Semantic Kernel 是微软 Copilot 产品套件中的一个核心组件的开源版本。它是一个 Python 库，与 LangChain类似，它可以帮助你在大语言模型（LLMs）之上构建应用程序。Semantic Kernel 的核心概念是计划器，可以帮助你构建由 LLM 驱动的代理，可以为用户创建一个计划，然后在各种插件的帮助下逐步执行这个计划。
- Spring Modulith。微服务容易被误用和滥用，这通常是 microservice-envy（microservice envy）导致的。与其从头开始构建一个由单独部署的进程组成的新系统，我们通常建议**从一个精心设计的单体应用开始，并且仅当应用程序达到一定规模时，才将其分解为可单独部署的单元，此时微服务的好处才能超越分布式系统所固有的额外复杂性**。Spring Modulith 是一个框架，以一种使代码在适当时候更容易拆分成微服务的方式来组织代码。它提供了一种模块化代码的方法，使领域和限界上下文的逻辑概念与文件和包（package）结构的物理概念保持一致。这种对齐方式使得在必要时重构单体架构以及单独测试领域变得更加容易。

### 暂缓

-