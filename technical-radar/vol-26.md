# vol 26

## 本期主题

### 离奇集市：开源软件不断变化的经济学

商业化的尝试呈现出当前生态系统巨大的经济复杂性。例如，Elastic 为了要求从中获利的云服务提供商做出回馈，更改了许可证，这导致AWS在2021年9月将Elasticsearch复刻为OpenSearch。这表明商业开源软件要守护竞争的护城河有多么困难（同样的问题也适用于免费的闭源软件，因为 Docker 一直在努力寻找合适的商业模式，我们目睹了一些公司在探索 Docker Desktop 的替代品）。有时，权力动态会反过来：由于 Facebook 资助了开源软件 Presto，Presto的贡献者得以保留 IP（知识产权），并在他们离开公司后将其重新命名为 Trino，这实际上得益于 Facebook 的投资。

### 软件供应链的创新

众所周知的重大安全事故——Equifax 数据泄露、SolarWinds 攻击、Log4J 远程零日漏洞攻击等等，都是由于软件供应链的管理不善造成的。开发团队现在意识到，可靠的工程实践也包括项目依赖项的验证和管理。

### 为什么开发者们总热衷于在 React 中实现状态管理？

一种常见的模式：**一个基础的框架变得流行起来，紧接大量工具的浮现**
**创建出一个针对常见缺陷和改进的生态系统，最后以几个流行工具的巩固而结束**。

### 对主数据编目永无止境的追求

几乎只要企业在收集数字数据，它就一直在努力将其合理化并编目到一个单一的、自上而下的企业目录。然而一次又一次，这一直觉上吸引人的概念与大型组织中固有的复杂性、冗余性和模糊性的残酷现实背道而驰。例如 [Collibra](https://www.thoughtworks.com/zh-cn/radar/platforms/collibra) 和 [DataHub](https://www.thoughtworks.com/zh-cn/radar/platforms/datahub)。这些工具能够对跨仓库数据谱系和元数据提供
一致的、可发现的访问，但它们不断扩展的功能集也延伸到了数据治理、质量管理、发布等方面。
与这种趋势相反，远离自上而下的中心化数据管理，走向基于数据网格架构的联合治理和数据发现的趋势也在增长。



## 技术

### 采纳

- 交付核心四指标。为了度量软件交付的效能，越来越多的组织默认采用由 [DORA 研究项目](https://www.devops-research.com/)定义的交付核心四指标，即：更改前置时间、部署频率、平均恢复时间（MTTR）和变更失败率。
- 统一远程团队墙。统一远程团队墙是一项简单的技术，可以用虚拟的方式重新引入团队墙实践。

### 试验

- [数据网格](https://martinfowler.com/articles/data-monolith-to-mesh.html)。数据网格是一种面向分析和机器学习的技术方法，以去中心化的组织和技术方式分享、访问和管理数据。当前数据组织架构日渐复杂，数据使用案例激增以及数据源愈加多样化，面对当前的数据环境，数据网格希望创建一种社会技术方法，旨在规模化的获取数据中的价值。
- 生产就绪的定义。在一个实践“谁构建，谁运行”原则的组织中，生产就绪的定义（definition of production readiness DPR）是一个可以支持团队评估和准备新服务上线运营就绪情况的有用技术。
- 文档象限。[文档象限](https://documentation.divio.com/)是一种确保生成正确文档的便捷方式。该技术沿两个轴对文档进行分类：第一个轴与文档信息的性质有关——实践的或是理论的；第二个轴描述了文档的使用情境——学习或是工作。这样定义的四个象限中就可以放置和帮助人们理解诸如教程、操作指南或参考页面之类的文档。该分类系统不仅可以确保关键文档不会被遗漏，而且还可以指导内容的呈现方式。
- 重新思考远程站会。站会一词源于在每日同步会议期间站起来的想法，目的是缩短会议时间。只要它可以减少整体会议负担并提升团队凝聚力，就是成功的技巧。
- 服务器端驱动 UI。[服务器端驱动UI 将渲染分离到移动应用程序的一个通用容器中，而每个视图的结构和数据由服务器提供]()。这意味着对于过去那些需要经历一次应用商店发布之旅的修改，现在只需要简单改变服务器发送的响应数据即可实现。
- 软件物料清单。在保障系统安全性的压力不变且总体安全威胁不减的情况下，一个机器可读的软件物料清单（SBOM）可以帮助团队掌握他们所依赖的库中的安全问题。
- [策略性复刻](https://faustodelatog.wordpress.com/2020/10/16/tactical-forking/)。策略性复刻是一种有助于代码库重组或从单体代码库迁移到微服务的技术。通过这种技术，团队可以创建代码库的新分支，并使用它来处理和抽取某个特定的关注点或领域，同时删除不需要的代码。
- 团队的认知负载。使用[高效能团队模式（Team Topologies）](https://teamtopologies.com/book)的作者提供的评估方法，这种评估方法可以让你理解团队构建、测试和维护其服务的难易程度。
- 过渡架构。[过渡架构（transitional architecture）](https://martinfowler.com/articles/patterns-legacy-displacement/transitional-architecture.html)在替换遗留系统时是一种有用的做法。

### 评估

- CUPID。Daniel Terhorst-North 最近尝试为好代码创建了一个类似于检查表的东西。他认为与其拘泥于像 SOLID 这样一套规则，不如使用一组特性作为目标。他设计出了名为 [CUPID](https://dannorth.net/2022/02/10/cupid-for-joyful-coding/) 的特性组，来描述为了写出“令人愉悦”的代码，我们需要做出哪些努力。
- 包容性设计。大家常常直到软件发布之前，甚至是发布之后，才想起与无障碍性和包容性相关的要求。
- 非集群资源的 Operator 模式。非集群资源的 [Operator](https://www.thoughtworks.com/cn/radar/tools/kubernetes-operators) 模式可以利用自定义资源和在 Kubernetes 控制面板中实现的事件驱动调度机制，来管理与集群外部相关的活动。
- 无边车服务网格。[服务网格](https://www.thoughtworks.com/cn/radar/techniques/service-mesh)的通常实现形式为与每个服务实例一并部署的反向代理进程，即“边车（Sidecar）”。尽管这些“边车”属于轻量级进程，但每个新服务实例的创建都意味着一个“边车”的新增，采用服务网格的整体开销和运维复杂度也会随之增加。随着 eBPF 的发展，我们发现一种被称为[无边车服务网格](https://isovalent.com/blog/post/2021-12-08-ebpf-servicemesh)的模式能够将网格的功能安全地下沉到操作系统内核层，从而使得相同节点上的服务可以通过套接字透明地通信，而无需额外的代理。您可以通过 [Cilium 服务网格](https://github.com/cilium/cilium-service-mesh-beta)对上述模式进行尝试，并从“每个服务一个代理”的部署模式简化为“每个节点一个代理。
- SLSA。软件工件供应链层级，又称 [SLSA](https://slsa.dev)（读作“salsa”），是一个由联盟组织策划的，为组织机构提供防范供应链攻击的指南集。该框架衍生于一个 Google 多年来一直使用的内部指南。
- 流式数据仓库。一大批新工具正崭露头角，为日渐庞大的使用 SQL 进
  行熟练分析的开发者群体提供流处理应用方面的帮助。企业中基于 SQL 的流处理应用集合可以称为流式数据仓库。
- TinyML。可以通过一种方式创建模型，使它们能够在小型、低成本和低功耗设备上运行。这种技术被称作 [TinyML](https://towardsdatascience.com/an-introduction-to-tinyml-4617f314aa79)，它为在看上去不可行的情况下运行 ML 模型开辟了新的可能性。例如，在由电池供电的设备上，或者在受限或不稳定的网络环境中，TinyML 能够使模型运行在本地且不需要高昂的成本。

### 暂缓

- 面向编排的 Azure Data Factory。推荐使用其他开源编排工具（例如 [Airflow](https://www.thoughtworks.com/cn/radar/tools/airflow)）来处理复杂的数据流水线，而仅使用 Azure Data Factory 进行数据复制或数据快照。
- 混杂平台团队。平台工程产品团队只有关注那些清晰并且定义明确的（内部）产品，才能交付更好的产出。
- 测试环境中的生产数据。使用假数据是一种更安全的策略。现存的工具也能帮我们创建假数据。在某些场景，例如重现错误或训练特定的机器学习模型时，确实有复制生产数据特定元素的必要。但建议一定要谨慎行事。
- 默认选择SPA。团队搭建网站时会默认选择单页面应用（SPA）的普遍现象让我们担心人们甚至没意识到 SPA 原本只是一种架构风格时，就立即进行了项目的框架选型。**SPA 会招致传统基于服务器的网站所不具备的复杂性：搜索引擎优化，浏览历史管理，网站分析，首页加载时间等**。通常看不到团队去进行这种权衡分析，即使是在业务需求不能证明这种使用是合理的情况下，也盲目地接受了默认选择 SPA 的复杂性。事实上，我们已经开始注意到许多新的开发人员甚至都不知道有替代的方法，因为他们整个职业生涯都是在类似 React 这样的框架中度过的。我
  们相信，许多网站都会受益于服务端逻辑的简洁性。

## 平台

### 采纳

No

### 试验

- Azure DevOps。[Azure DevOps](https://azure.microsoft.com/en-us/services/devops/) 生态系统包含一组托管服务，包括托管 Git 代码仓库、构建和部署流水线、自动化测试工具、待办工作管理工具和构件仓库。
- [Azure Pipeline模板](https://docs.microsoft.com/en-us/azure/devops/pipelines/process/templates?view=azure-devops)。Azure Pipeline 模板提供了两种机制来对 Azure Pipeline 定义去重。通过“includes”模板，你可以引用一个模板使其像参数化的 C++ 宏一样内联展开，从而以一种简单的方式将各个阶段、任务和步骤的公共配置分解出来。通过“extends”模板，你可以定义一个具有公共流水线配置的外壳，结合所需模板检查机制，如果流水线没有扩展特定的模板，你可以拒绝构建以防止对流水线配置本身的恶意攻击。
- [CircleCI](http://circleci.com/)。其中 Orbs 和 executors 非常有用。Orbs 是可重复使用的代码片段，可用来自动化重复的流程，进而加快项目的配置，并使其易于与第三方工具集成。多种多样的 executor 为在 Docker、Linux、macOS 或 Windows 虚拟机中配置作业提供了灵活性。
- [Couchbase](https://www.couchbase.com/)。经历了持续的的改进，一个由相关工具以及商业产品组成的生态系统也围绕着它成长了起来。新增的产品套件包括 Couchbase Mobile 和 Couchbase Sync Gateway。这些功能协同工作，即使在设备由于网络不稳定而离线的时间段内也能够使数据保持最新。
- [eBPF](https://ebpf.io/)。Kubernetes 和服务网格技术（如 Istio）被普遍使用，它们采用“边车”（sidecars）来实现控制功能。有了诸如 Bumblebee 这样使 eBPF 程序的构建、运行和发布变得更加容易的新工具，eBPF 可以被看作是传统边车的替代品。
- GitHub Actions。GitHub Actions 以其在 GitHub 中的源代码旁直接创建构建工作流的便利性，结合使用 act 等开源工具在本地运行的能力，是一个利于团队刚开始开展工作以及新人上手的强有力选项。
- [GitLab CI/CD](https://docs.gitlab.com/ee/ci/)。本地部署的 GitLab 以及自托管运行器时，GitLab CI/CD 这种组合可以解决使用基于云的解决方案经常会遇到的授权问题。自托管运行器可以完全根据需求进行配置，并安装合适的操作系统以及依赖项，流水线的运行速度比使用云供应的运行器要快得多。
- Google BigQuery ML。[BigQuery ML](https://cloud.google.com/bigquery-ml/docs) 是一个有吸引力的选择，特别是当数据已经存储在 BigQuery中的时候。
- Google Cloud Dataflow。[Google Cloud Dataflow](https://cloud.google.com/dataflow/) 是一个基于云平台的数据处理服务，适用于批量处理和实时流数据处理的应用。
- Github Actions 中的可复用工作流。无需离开GitHub 这个平台，只需配置几行 YAML 文件，GitHub Actions 就能为你提供 CircleCI Orbs 或 Azure Pipeline Templates 在 CI 方面的灵活性。
- Sealed Secrets。[Sealed Secrets](https://github.com/bitnami-labs/sealed-secrets) 提供组合运算符和命令行实用程序，使用非对称密钥来对“机密”进行加密，以便仅在集群中用控制器将其解密。此过程可确保“机密”在 Kubernetes 用于部署的配置文件中不会泄漏。
- [VerneMQ](https://github.com/vernemq/vernemq)。之前评估过一些 MQTT 消息服务器，比如 Mosquitto 和 EMQ。与它们类似，VerneMQ 也基于 Erlang/OTP 开发，具有高度可扩展性。它可以在硬件上水平和垂直扩展，以支持大量并发客户端的发布和订阅，同时保持低延迟和容错性。

### 评估

- [actions-runner-controller](https://github.com/actions-runner-controller/actions-runner-controller)。ctions-runner-controller 是一种 Kubernetes 控制器，它在 Kubernetes 集群上为 GitHub Actions 运行
  自托管运行器。当你的 GitHub Actions 运行的作业需要访问 GitHub 云运行器主机无法访问的资源，或者依赖于某些特定的操作系统和环境而 GitHub 没有提供时，自托管运行器会很有帮助。
- Apache Iceberg。[Apache Iceberg](https://iceberg.apache.org/) 是一个面向超大的分析数据集的开放表格格式。
- [Blueboat](https://github.com/losfair/blueboat)。Blueboat 是一个无服务器 web 应用的多租户平台。它采用了V8 JavaScript 引擎，同时，出于安全和性能的考虑，使用 Rust 原生地实现了常用的网络应用程序库。Blueboat 可以被看作是 CloudFlare
  Workers 或 Deno Deploy 的替代品，但与它们之间存在一个重要区别——你必须操作和管理底层基础设施。
- Cloudflare Pages。Pages 只是众多基于 Git 的网站托管解决方案中的一个，但[Cloudflare Pages](https://pages.cloudflare.com/) 有一个大多数替代方案不具备的有用功能——持续预览。
- Colima。[Colima](https://github.com/abiosoft/colima) 正成为 Docker 桌面版的一个热门开放替代方案。它通过在 Lima VM 中配置 Docker 容器运行时环境，可以在 macOS 上配置 Docker CLI 并处理端口转发和挂载存储。
- [Collibra](https://www.collibra.com/us/en)。 SaaS 或自托管实例的部署灵活性，许多功能可以开箱即用，包括数据治理、数据血缘、数据质量和数据的可观测性。
- CycloneDX。[CycloneDX](https://cyclonedx.org/) 是一个用来描述机器可读的软件物料清单（SBOM）的标准。。CycloneDX 起源于 OWASP，它对旧的 SPDX 标准进行了改进，提供了更广泛的定义，不仅包含了本地机器依赖，还包含运行时服务依赖。
- Embeddinghub。[Embeddinghub](https://github.com/featureform/embeddinghub) 是一个与 Milvus 十分类似的，面向机器学习嵌入领域的向量数据库。它提供了开箱即用的近似最邻近运算、表分区、版本及访问控制等功能。
- Temporal。[Temporal](https://temporal.io/) 是一个用于开发长期运行工作流的平台，尤其适用于微服务架构。作为 Uber 开源项目（OOS）Cadence 的衍生项目，Temporal 对于长期运行的工作流采用了事件溯源（event-sourcing）模式，因此它们可以在进程或主机的崩溃后恢复。

### 暂缓



## 工具

### 采纳

- [tfsec](https://github.com/liamg/tfsec)。对于那些正在使用 Terraform 的项目，在需要检测潜在安全风险时，tfsec 已经迅速成为默认的静态分析工具。

### 试验

- AKHQ。[AKHQ](https://akhq.io/docs/#installation) 是 Apache Kafka 的图形用户界面（GUI），帮助你管理主题、主题数据、消费者组等。AKHQ 是用来监控 Kafka 集群实时状态的有效工具。
- cert-manager。[cert-manager](https://cert-manager.io/) 是一款在 Kubernetes 集群里管理 X.509 证书的工具。
- 云服务的碳足迹。[Cloud Carbon Footprint](https://www.cloudcarbonfootprint.org/)（CCF）是一款通过云 API 来查看 AWS、GCP、Azure 云平台上碳排放的可视化工具。
- Conftest。[Conftest](https://github.com/open-policy-agent/conftest) 是一款针对结构化配置数据编写测试的工具。它依赖于开放策略代理中 Rego 语言，能够为Kubernetes 配置、Tekton 的流水线定义、甚至 Terraform 计划编写测试。
- kube-score。[kube-score](https://github.com/zegl/kube-score) 是一款针对 Kubernetes 对象定义，进行代码静态检查的工具。它的输出是一份建议列表，里面包含了如何提升你的应用程序安全性及弹性的相关建议。它有一份包含了最佳实践的预定义检查，比如以非root 权限运行容器，正确指定资源限制等。
- Lighthouse。[Lighthouse](https://developers.google.com/web/tools/lighthouse/) 是一个由 Google 编写的工具，可以评估 Web 应用和页面，以及从出色的开发实践中收集性能指标和洞见等信息。随着 Lighthouse CI 的引入，将 Lighthouse 纳入由不同工具管理的流水线，会变得比以往任何时候都容易。
- Metaflow。[Metaflow](https://github.com/Netflix/metaflow) 是一个对用户友好的 Python 库和后端服务，可以帮助数据科学家和工程师构建和管理可用于生产的数据处理、机器学习训练及推理的工作流。Metaflow 提供一系列 Python API，将代码组织为由步骤组成的有向图。每一个步骤都可以灵活配置，例如其所需的计算和存储资源。作为一个轻量级的全栈框架，Metaflow可以替代例如 [MLflow](https://www.thoughtworks.com/cn/radar/tools/mlflow) 这类更复杂的平台。
- Micrometer。[Micrometer](https://micrometer.io/) 是一个跨平台的库，用于 JVM 的指标检测，支持 Graphite、New Relic、CloudWatch 和许多其他集成。
- NUKE。[NUKE](https://nuke.build/) 是一个面向 .NET 的构建系统，也是传统的 MSBuild、Cake 以及 Fake 等自动化构建系统的替代品。
- Pactflow。在长时间使用 Pact 进行契约测试的过程中，我们目睹了规模化带来的复杂性。可以使用[Pactflow](https://pactflow.io/) 减少这种复杂性引发的后果。
- Podman。[Podman](https://github.com/containers/podman) 作为 Docker 的替代方案。与 Docker 不同的是，Podman 使用一个无守护引擎来管理和运行容器。此外，Podman 可以以普通用户身份运行而无需 root 权限，从而减少了攻击面。
- Sourcegraph。两个基于抽象语法树（AST）表征的代码搜索和替换工具，[Comby](https://www.thoughtworks.com/cn/radar/tools/comby) 和[Sourcegraph](https://about.sourcegraph.com/)。
- Syft。使用软件物料清单（SBOM）是改善“供应链安全”的关键要素之一，因此在发布软件构件的同时，发布相应的SBOM 正变得越来越重要。[Syft](https://github.com/anchore/syft) 是一个致力于为容器镜像和文件系统生成 SBOM 的 CLI 工具和 Go 语言库。
- Volta。当同时在多个 JavaScript 代码库上工作时，我们需要使用不同版本的 Node 和其他 JavaScript 工具。对于 Node 而言，nvm 能够做到这一点，但有一个替代方案 Volta。与使用 nvm 相比，[Volta](https://volta.sh/) 有几个优点：它可以管理其他 JavaScript 工具，如 yarn；它具备一个基于项目绑定工具链某个版本的理念，这意味着开发人员可以简单使用给定代码目录中的工具，而不必担心需要手动切换工具版本——Volta 是通过使用路径中的 shims 来选择被绑定的版本。Volta 采用 Rust 编写，速度极快，以独立二进制文件进行分发，没有任何依赖。
- [Web Test Runner](https://modern-web.dev/docs/test-runner/overview/)。Web Test Runner 是一个针对 Web 应用的测试运行器。它的一个优势是可以在浏览器中运行测试（也可以无图形界面运行）。**它支持多种浏览器启动器——包括 Puppeteer，Playwright 和 Selenium，并且使用 Mocha 作为默认测试框架**。Web Test Runner 运行测试的速度非常快，在调试的时候能打开一个带 devtools 的浏览器窗口。它在内部采用了 Web Dev Server，我们可以利用其出色的插件 API，为测试套件添加自定义插件。

### 评估

- [CDKTF](https://www.terraform.io/cdktf)。由 AWS CDK 团队和 Hashicorp 合作开发的 Terraform 云开发工具包（CDKTF），让团队有可能使用多种不同的编程语言，包括 TypeScript 和 Java，去定义并配置基础设施。通过这种方法，它在 Terraform 生态系统中紧跟 Pulumi 的领先地位。
- Chrome Recorder panel[。Chrome Recorder panel](https://developer.chrome.com/docs/devtools/recorder/) 是 Google Chrome 97 的预览功能，允许简单地录制和回放用户旅程。这不是一个新想法，但它集成在 Chrome 浏览器中的方式能允许快速地创建、编辑和运行脚本。
- [Excalidraw](https://excalidraw.com/)。喜欢它生成的低保真图表样式，这让人联想到团队在同地协作时绘制的白板图表。你需要注意它默认的安全性，在你进行绘制时，任何拥有链接的人都可以看见图表。
- GitHub Codespaces。[Github Codespac](https://github.com/features/codespaces)e 允许开发者在云上创建开发环境，可以通过 IDE 访问它，就像在本地环境一样。类似的有Gitpod。Codespace 允许通过使用 dotfiles 文件来标准化配置环境， Codespace 能提供最高 32 核64GB 内存虚拟机的特性，这些虚拟机可以在 10 秒钟内启动，有可能提供比开发笔记本电脑更强大的环境。
- GoReleaser。[GoReleaser](https://github.com/goreleaser/goreleaser) 是一个通过多个库和通道来支持不同架构的 Go 项目自动化构建和发布的工具。可以在本地机器或者 CI 上运行该工具，它支持在多种 CI 服务上运行，从而最大限度降低安装和维护成本。GoReleaser 能够用于每个发布版本的构建、打包、发布和声明，并且支持不同的包格式、包库和源代码控制的组合。
- Grype。[Grype](https://github.com/anchore/grype) 是一个新的针对 Docker 和 OCI 镜像进行漏洞扫描的轻量级工具。它可以以二进制文件安装，能在镜像被推至仓库前对其进行扫描，而且不需要在你的构建服务器上运行 Docker 守护进程。
- [Infracost](https://infracost.io/)。该工具可以在 Terraform pull request 中可视化成本权衡。它是一个开源软件，在 macOS、Linux、Windows 和 Docker 均可访问，开箱即用支持 AWS、GCP 和微软 Azure 的定价。它还提供了一个公共 API，可以查询到当前的成本数据。
- jc。jq 命令是一个支持 JSON的 sed。而 [jc](https://kellyjonbrazil.github.io/jc/docs/) 命令执行的是与之相关的任务：它获取常见 Unix 命令的输出，并将输出解析为 JSON。jq 和 jc
  这两个命令一起为 Unix CLI 世界以及大量基于 JSON 工作的库和工具之间架起了一座桥梁。
- skopeo。[skopeo](https://github.com/containers/skopeo) 是一款可以对容器镜像和镜像仓库执行各种操作的命令行工具。它的大部分操作都不要求用户以 root角色执行，也不需要运行守护进程。它是 CI 流水线中的实用部分，在推广镜像时，我们可以用 skopeo 把镜像从一个注册表拷贝到另一个注册表。这样的操作比直接拉取和推送镜像更好，因为我们不需要在本地存储这些镜像。
- SQLFluff。[SQLFluff](https://docs.sqlfluff.com/en/stable/) 是一个 python 实现的跨 SQL 方言的 linter，提供了命令行界面（CLI），可以很容易地整合进 CI/CD 流水线。它会强制执行一套鲜明风格的标准来格式化代码，当然你也可以通过添加一个 dotfile 设定自己的代码规范。
- [Terraform Validator](https://github.com/GoogleCloudPlatform/terraform-validator)。对于使用谷歌云平台（GCP）的团队，可以使用 Terraform Validator 构建策略库，作为检查Terraform 配置的约束条件。
- Typesense。[Typesense](https://github.com/typesense/typesense) 是一个快速、容错的文本搜索引擎。在有大量数据的情形下，Elasticsearch 可能仍然是一个不错的选择，因为它提供了一个基于磁盘且可横向扩展的搜索解决方案。然而如果你正在构建一个对延迟敏感的搜索应用，并且搜索索引的尺寸可以容纳在内存中，那么 Typesense 会是一个强大的替代方案，你也可以考虑与[Meilisearch](https://www.thoughtworks.com/cn/radar/platforms/meilisearch) 等工具一起评估。

### 暂缓

No

## 语言和框架

### 采纳

- SwiftUI。对于在苹果生产的各种设备上实现用户界面来说，苹果在几年前推出 [SwiftUI](https://developer.apple.com/xcode/swiftui/) 是一个很大的进步。
- Testcontainers。[Testcontainers](https://www.testcontainers.org/) 是一个拥有多种语言版本的库，并且 docker 化了常见的测试依赖——包括了不同种类的数据库，队列技术，云服务和 UI 测试依赖（例如 web 浏览器），还具有按需运行自定义 Dockerfile 的能力。这个可编程的、轻量级的、一次性的容器库可以使功能测试更加可靠。

### 试验

- Bob。在使用 React Native 构建应用时，有时你会发现不得不创建自己的模块。[Bob](https://github.com/callstack/react-native-builder-bob) 提供了一个命令行界面来为不同的构建目标创建脚手架。这个脚手架并不限于核心功能，还可以选择性地包括示例代码、代码检查工具、构建流水线和其他功能。
- [Flutter-Unity widget](https://github.com/juicycleff/flutter-unity-view-widget)。Flutter 在构建跨平台移动应用方面越来越受欢迎，Unity 非常适合于构建增强现实（AR）和虚拟现实（VR）体验。而 Flutter-Unity widget 则允许开发者在 Flutter widget 内嵌入 Unity 应用。
- Kotest。[Kotest](https://kotest.io/)（原名 KotlinTest）是 Kotlin 生态中的一个独立测试工具。
- Swift 包管理器。[Swift Package Manager](https://github.com/apple/swift-package-manager)（SwiftPM）作为一个苹果的官方开源项目被推出，苹果也在 Xcode 中添加了对它的支持。之前许多开发团队仍在坚持继续使用 CocoaPods 和 Carthage，是因为许多软件包根本无法通过 SwiftPM 获得。如今大多数包已经被添加在了 SwiftPM 中，并且流程都被进一步地简化， SwiftPM越来越正统。
- Vowpal Wabbit。[Vowpal Wabbit](https://vowpalwabbit.org/) 是一个多用途的机器学习库。Vowpal Wabbit 最初是雅虎研究院于十多年前创建的，如今它依然在持续实现新的强化学习算法。

### 评估

- Android Gradle 插件 — Kotlin DSL。Android Gradle 插件 Kotlin DSL 增加了 Gradle 构建脚本对 Kotlin Script 的支持，让它成为除 Groovy 之外的另一种选择。用 Kotlin 代替 Groovy 的目的在于 Kotlin 能更好得支持重构，并且在 IDE 里编写它更加简便，最终能够产出更易于阅读和维护的代码。
- Azure Bicep。[Azure Bicep](https://docs.microsoft.com/en-us/azure/azure-resource-manager/bicep/overview?tabs=bicep) 是一种使用声明式语法的领域特定语言（DSL），主要面向那些喜欢使用比 JSON 更自然的语言来编写基础设施代码的人。它支持可重用参数化模板来实现模块化资源定义。
- [Capacitor](https://capacitorjs.com/)。React Native 经久不衰的人气和用处是毫无争议的。Capacitor 是以 PhoneGap 为起源，之后被重命名为 Apache Cordova 的系列工具的最新一代。Capacitor 将 Ionic 完全重写并让独立应用拥抱渐进式网页应用风格。
- Java 17。希望通过 LTS 版本的升级以及开发团队对语言的定期更新，能够使生产软件免于因为“更新成本太高”而一直困在 Java 的过时版本上。
- Jetpack Glance。借助构建在 Compose 运行时之上的 [Jetpack Glance](https://developer.android.com/jetpack/androidx/releases/glance)，开发人员可以使用类似的声明式 Kotlin API 来编写小部件。
- Jetpack Media3。如今安卓拥有多个媒体 API：Jetpack Media（也被称为 MediaCompat），Jetpack Media2 和 ExoPlayer。然而，这些库都是分别开发的，它们的目的不同但是功能重叠。这导致安卓开发者在编码的时候不仅需要斟酌类库的选型，当使用的特性来自于多个库的时候，还需要编写适配器或者兼容代码[。Jetpack Media3](https://developer.android.com/jetpack/androidx/releases/media3) 尝试去解决上述情况。
- MistQL。[MistQL](https://github.com/evinism/mistql) 是一个在类 JSON 结构上执行计算的小型领域特定语言。MistQL 最初的设计是用于在前端手动提取机器学习模型的特征，而如今它有了能够运行在浏览器端的 JavaScript 实现版本以及能够运行在服务器端的Python 实现版本。
- npm工作区。 npm 7 中加入了 [npm 工作区](https://docs.npmjs.com/cli/v8/using-npm/workspaces)来直接支持多包开发特性。将相关联的包集中管理可以让开发更加便利，比如你可以在一个代码仓库中存储多个相关的库。
- [Remix](https://remix.run/)。我们见证了浏览器从服务器端渲染到单页应用的变迁，而如今的 Web 开发似乎又回到了两者中间。Remix 就是这样一个例子。Remix 是一个全栈 JavaScript 框架，它并没有使用笨拙的静态构建，而是通过利用分布式系统和本地浏览器两者的特点一起来加快页面的加载速度。它分别对嵌套路由和页面加载进行了部分优化，这使得页面渲染看起来特别快。
- ShedLock。有很多分布式存储可以实现集群的锁定机制，ZooKeeper 和 Consul 等系统，以及 DynamoDB 或 Couchbase 等数据库都有必要的底层机制来管理集群内部的一致性。[ShedLock](https://github.com/lukas-krecan/ShedLock) 是一个小型类库，如果你正在尝试用 Java 来实现自己的定时任务，它可以使你的代码更方便地和上述工具集成。ShedLock 有获得和释放锁的 API，还有各种连接器，可以适配不同工具的锁。如果您正在编写自己的分布式任务，但是不想使用 Kubernetes 这种复杂的重量级平台，ShedLock 值得一试。
- SpiceDB。[SpiceDB](https://github.com/authzed/spicedb) 受谷歌 Zanzibar 启发，是一个用于管理应用程序权限的数据库系统。你可以通过 SpiceDB 创建一个数据模式以对你的权限需求进行建模，并使用客户端库将创建的模式应用到任何一个受支持的数据库中。
- [sqlc](https://github.com/kyleconroy/sqlc)。sqlc 是一个特别的编译器，可以根据 SQL 生成类型安全并且风格自然的 Go 代码。与其他基于对象关系映射（ORM）的方法不同，sqlc 允许你根据需要编写原生的 SQL。
- [可组合架构](https://github.com/pointfreeco/swift-composable-architecture#the-composable-architecture)。对于需要维护多种不同技术栈代码库的团队来说，如果他们对编写 iOS 应用没有太多专业知识时，他们就能从使用像 TCA 这样的“有态度”的框架中获取最大收益。
- WebAssembly。[WebAssembly（WASM）](http://webassembly.org/)是一项 W3C 标准，旨在为浏览器提供执行代码的能力。它是二进制的编码格式，其设计目标是可以发挥硬件的能力，让代码以接近原生的速度在浏览器中运行，目前WASM已被所有的主流浏览器支持并向下兼容。
- Zig。[Zig](https://ziglang.org/) 是一门新的语言，它与 C 语言共享了许多属性，但是具有更强的类型，更简便的内存分配，以及对命名空间和众多其他特性的支持。

### 暂缓

-