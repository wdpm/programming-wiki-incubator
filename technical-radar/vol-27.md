# vol 27

## 本期主题

### 机器学习的主流化

**联邦学习**等技术能够使 ML 模型为敏感信息提供隐私保障。**TinyML** 可以让模型在资源受限的设备上执行，将推理转移到边缘，这既可以释放资源，又可以提高敏感数据的隐私性。**Feature Stores** 为应用程序开发的模型—视图—控制器设计模式提供了类似的好处，使得数据整理、模型训练和推理之间的分离更为清晰。诸如 **Stable Diffusion** 之类的公开可用模型既突出了机器学习的惊人能力，也突出了对源数据和道德的关注。

### “平台即产品”的力量

像任何好的产品一样，平台需要持续的支持。它必须发展并适应开发者不断变动的需求。

### 将数据所有权移至边缘节点

我们都知道，任何形式的中心化都会导致限制、瓶颈、和不必要的暴露。基于无冲突复制数据类型（CRDTs）的研究，使得数据应用可以不使用中心化的数据库，这种本地优先的软件 / 应用技术鼓励开发者们去考虑围绕点对点的数据构建应用而不是使用中心化的数据库。

### 移动端也应该模块化

随着移动应用的成熟，它们通常会变得越来越大，有时会发展为所谓的超级应用程序。这些应用包含许多服务，完全可以视为一个个平台。虽然有些应用没有那么大，但多年来积累的很多功能通常也可以分解为各种模块，许多公司发现移动应用也能从同样的模块化方法中受益。



## 技术

### 采纳

- 生产路径图。常见的技术是基于[价值流图](https://en.wikipedia.org/wiki/Value-stream_mapping)，尽管有很多具有同等价值的[流程图](https://caroli.org/en/path-to-production/)变种。这项活动经常会让参与者眼界大开，因为识别出了延迟，风险和不一致的地方，并且可以用可视化的陈述来持续改进构建和部署的流程。
- 团队的认知负载。建议关注团队的设计和互动方式，可以让你理解团队构建、测试和维护其服务的难易程度。可以使用一个[模板](https://github.com/TeamTopologies/Team-Cognitive-Load-Assessment)来评估团队的认知负载，该模板基于高效能团队模式（Team Topologies）作者的想法。
- [威胁建模](https://www.owasp.org/index.php/Category:Threat_Modeling)。在任何软件的整个生命周期中，由于外部事件以及需求和架构的调整，可能会出现新的威胁，而现有的威胁将继续发展。这意味着威胁建模需要定期重复，重复的频率视情况而定。

### 试验

- [BERT](https://arxiv.org/abs/1810.04805)。来自变换器的双向编码器表征量。 BERT 凭借优秀的文档以及活跃的社区支持，更加通俗易懂。
- [组件视觉回归测试](https://www.thoughtworks.com/cn/radar/tools/visual-regression-testing-tools)。随着 React 和 Vue 等基于组件的框架逐渐兴起，组件视觉回归测试也越来越流行。通过和 Vite 以及 webpack 的模块热替换（HMR）功能共同使用，还可以视之为测试驱动开发应用于前端开发领域的范式转变。
- 设计令牌。当面临如何跨多种尺寸设备和平台一致地使用设计系统的挑战时，Salesforce 的团队提出了[设计令牌](https://medium.com/salesforce-ux/living-design-system-3ab1f2280ef7#.r26jko9u3))这个概念。令牌将诸如颜色和字体等值存储在一个中心位置。这使得将选项与决策分开成为可能，并且显著改善了团队之间的协作。随着 [Tailwind CSS](https://www.thoughtworks.com/cn/radar/languages-and-frameworks/tailwind-css) 和 [Style Dictionary](https://amzn.github.io/style-dictionary/#/) 等工具的引入，设计令牌将被更频繁地使用。
- 用于测试收发邮件的虚拟邮箱服务。**使用真实电子邮箱的测试账户，或使用 SMTP（简单邮件传输协议）服务器进行测试仍然是较为常见的软件测试方法**。然而，使用真实的服务进行测试会带来测试邮件将被发送到真实的人的风险，会使自动集成测试变得复杂。我们可以使用虚拟 SMTP 服务器来测试邮件发送，它记录了发送电子邮件的请求，但并没有实际发送。在这个领域存在多个开源工具，比如 [fake-smtp-server](https://github.com/gessnerfl/fake-smtp-server)，它提供了 Web UI 来展示邮件，便于可视化测试；以及 [mountebank](https://www.mbtest.org/)，它提供了 REST API 来获取已发送的邮件，便于集成测试。
- 联邦学习。联邦学习是一个去中心化的技术，它使模型可以在大量不同来源的数据集上训练，并让数据保持在远端，例如用户的设备上。
- 增量开发人员平台。[团队拓扑学](https://teamtopologies.com/book)建议在任何特定阶段，都努力去实现一个所谓的“最小可行平台”，这个平台的第一版甚至可以是一系列的 wiki 文档。下一个增量可以通过提供模版或允许成员创建请求来提高服务水平。进一步的增量可以引入自服务 API，但前提是有价值。
- 移动微前端。建议遵循良好、整洁的应用程序设计，当规模扩大为多个团队时采纳模块化，仅当模块与业务领域高度对齐时才采用微前端架构。
- CI/CD 流水线的可观测性。复杂的流水线会在运行太慢或遭受非确定性影响时给开发者带来摩擦，进而减少关键反馈循环并且阻碍开发者效率。此
  外，它们作为关键部署基础设施的角色在快速部署周期中产生了压力点，就像一些组织在应对最近的 log4shell漏洞时所发生的那样。
- SLSA。软件工件供应链层级，又称 [SLSA](https://slsa.dev/)（读作“salsa”），是一个由联盟组织策划的，为组织机构提供防范供应链攻击的指南集。该框架衍生于一个 Google多年来一直使用的内部指南。值得称赞的是，SLSA 并没有承诺提供“银弹”，即仅使用工具确保供应链安全的方法，而是提供了一个基于成熟度模型的具体威胁和实践的清单。
- 软件物料清单。在保障系统安全性的压力不变且总体安全威胁不减的情况下，一个机器可读的软件物料清单（SBOM）可以帮助团队掌握他们所依赖的库中的安全问题。

### 评估

- 碳效能作为软件架构的特性。在构建软件碳足迹方面，建议对碳效能作为软件架构的特性进行评估。一个重视碳效能的架构会将碳效率作为架构设计和基础设施选型的考虑因素，以最大限度地减少能源消耗，进而实现减少碳排放。
- CUPID。Daniel Terhorst-North 最近尝试为好代码创建了一个类似于检查表的东西。他认为与其拘泥于像 SOLID 这样一套规则，不如使用一组特性作为目标。他设计出了名为 [CUPID](https://dannorth.net/2022/02/10/cupid-for-joyful-coding/) 的特性组。
- GitHub 推送保护。[GitHub 推送保护](https://docs.github.com/en/enterprise-cloud@latest/code-security/secret-scanning/protecting-pushes-with-secret-scanning)将密钥泄露检测提前到了开发工作流程中，如果更改被推送的时候检测到有机密，则直接拒绝这次推送。
- 本地优先应用程序。[本地优先应用程序](https://www.inkandswitch.com/local-first/#towards-a-better-future)，是一组即能够实现多人协作，且可以使数据所有权本地化的软件设计原则。它优先使用本地存储和本地网络，而不是远程数据中心或云服务器。无冲突复制数据类型（CRDT）和点对点（P2P）网络等技术有可能成为实现本地优先软件的基础技术。
- 指标库。[指标库](https://blog.transform.co/data-talks/what-is-a-metrics-store-why-your-data-team-should-define-business-metrics-in-code/)，有时被称作无头商业智能（BI），是一种将指标的定义与其在报表和可视化中的使用相解耦的中间层。在传统意义上讲，指标定义在商业智能（BI）工具的上下文中。
- 服务器端驱动 UI。服务器端驱动 UI 将渲染分离到移动应用程序的一个通用容器中，而每个视图的结构和数据由服务器提供。这意味着对于过去那些需要经历一次应用商店发布之旅的修改，现在只需要简单改变服务器发送的响应数据即可实现。
- SLI 和 SLO 定义为代码。自从谷歌首次将服务质量指标（SLIs）和服务质量目标（SLO）作为其网站可靠性工程（SRE）实践的一部分推广以来，Datadog、Honeycomb 和 Dynatrace 等观察性工具开始将 SLO 监控纳入其工具。
- 模型测试的合成数据。在验证机器学习模型判别能力的过程里，合成数据虽然尚不能取代真实数据，但也有相当广泛的使用场景。例如，合成数据可以用于预防小概率事件下模型彻底失效，或者在不暴露个人隐私信息的前提下对数据流水线进行测试。
- [TinyML](https://towardsdatascience.com/an-introduction-to-tinyml-4617f314aa79)。可以通过一种方式创建模型，使它们能够在小型、低成本和低
  功耗设备上运行。
- 可验证凭证。[W3C 标准](https://www.w3.org/TR/vc-data-model/)以凭证持有者为中心，这与我们使用物理凭证时的经验相似：用户可以将其可验证凭证放在自己的数字钱包中，并在任何时候向任何人展示，而无需得到凭证发行者的许可。例如，在零知识证明技术的支持下，你可以在不透露你的生日情况下构建一个可验证凭证来证明你是一个成年人。

### 暂缓

- 没有“使用原生的远程工作方法”的卫星式工人。例如，如果团队中在同一地点工作的人一起参加会议，他们仍然应该在各自的笔记本电脑上参与数字协作或会议聊天。团队需要意识到排斥他们的卫星工人和创造孤岛和排斥感的风险。
- 默认选择 SPA。**SPA 会招致传统基于服务器的网站所不具备的复杂性：譬如搜索引擎优化，浏览历史管理，网站分析，首页加载时间等**。事实上，许多网站都会受益于服务端逻辑的简洁性。
- 肤浅的云原生。“云原生”一词最初用于描述最大限度利用公有云托管的架构。例如**由许多小型、无状态和协作流程组成的分布式架构，以及具有高度自动化的构建、测试和部署能力的系统**。然而，我们注意到越来越多肤浅的云原生设计，简单地大量使用云供应商提供的专有服务，而没有注意到应用程序其实是大单体、脆弱且笨重的。



## 平台

### 采纳

- Backstage[。Backstage](https://backstage.io/)是由Spotify开发的一款能提升整个组织内的软件资产发现的开源开发者门户平台。它使用了存放在代码库中的 Markdown TechDocs 来追踪每个服务，这很好地平衡了中心化发现和分布式资产所有权的需求。
- Delta Lake。[Delta Lake](https://delta.io/) 是由 Databricks 实现的开源存储层，旨在将 ACID 事务处理引入到大数据处理中。

### 试验

- AWS 数据库迁移服务。使用 [AWS 数据库迁移服务](https://aws.amazon.com/dms/)（DMS）将数据迁移到 AWS 或从 AWS 迁移数据。
- Colima。[Colima](https://github.com/abiosoft/colima) 正在成为 Docker Desktop 的一个流行的开源替代方案。它通过 Lima VM 的方式提供 Docker 容器运行时，在 macOS 上配置 Docker CLI 并处理端口转发和卷挂载。Colima 使用 containerd 作为容器运行时，这也是大多数托管 Kubernetes 服务采用的容器运行时，这一方案提升了开发与生产环境的一致性。
- Databricks Photon。自 Databricks 9.1 LTS（长期支持版）开始，新运行时 [Databricks Photon](https://www.databricks.com/product/photon) 开始可用，它是一个使用 C++ 重新从底层构建的替代品。
- [DataHub](https://github.com/linkedin/datahub)。一个通过可扩展的元数据系统实现数据可发现性的下一代平台。与爬取和拉取元数据不同，DataHub 采用了基于推送的模式。
- [DataOps.live](https://www.dataops.live/)。DataOps.live 是一个可在 Snowflake 中实现环境自动化的数据平台。受 DevOps 实践的启发，DataOps.live通过采用持续集成和持续交付（CI/CD）、自动化测试、可观测性和代码管理，让你可以像对待任何其他 web 平台一样对待数据平台。
- eBPF。Linux 内核在多年前就内置了扩展的伯克利数据包过滤器（[eBPF](https://ebpf.io/)），它是一个可以将过滤器附加到特定套接字能力的虚拟机。但是 eBPF 的能力远远超出了包过滤的范围，它允许在内核中的不同点位触发自定义脚本，并且开销非常小。
- Feast。[Feast](https://github.com/feast-dev/feast) 是一个用于机器学习的开源 Feature Store。它具有几个有用的特性，包括生成时间点（point-in-time）正确的特征集以避免在训练时将容易出错的未来特征值泄露给模型，以及支持流数据源与批数据源。
- Monte Carlo。[Monte Carlo](https://www.montecarlodata.com/) 是一个数据可观测性平台。它使用机器学习模型，可以推断和学习数据，识别问题并在出现问题时通知用户。它使我们的团队能够跨 ETL 流水线、数据湖、数据仓库和商业智能（BI）报告维护数据质量。
- Retool。低代码平台 [Retool](https://retool.com/)，用于数据的查询和可视化。
- Seldon Core。[Seldon Core](https://github.com/SeldonIO/seldon-core) 是一个在 Kubernetes 集群上打包、部署、监控和管理机器学习模型的开源平台。它针对一些机器学习框架提供开箱即用的支持，你可以很容易的通过各种预打包的推理服务器、定制的推理服务器、以及语言包装器将你的模型容器化。
- Teleport。[Teleport](https://gravitational.com/teleport/) 是访问零信任网络基础设施的工具。传统的设置需要复杂的策略或跳板机来限制对关键资源的访问，然而，Teleport 通过统一的访问平面和取代了跳板机、VPN 或共享凭证的细粒度授权控制来简化了这些设置。
- VictoriaMetrics。[VictoriaMetrics](https://victoriametrics.com/) 尤其适用于运行由 Kubernetes 托管的微服务架构，它的 operator 使团队可以轻松地以自助服务的方式实现自我监控。建议也同时关注其他高性能的、Prometheus 兼容的时间序列数据库，例如 Cortex 或 Thanos。

### 评估

- Bun。[Bun](https://github.com/oven-sh/bun) 是一个新的 JavaScript 运行时，与 Node.js 或 Deno 相似。不同于 Node.js 或 Deno 的是，Bun 使用 WebKit 的 JavaScriptCore 构建，而不是 Chrome 的 V8 引擎。Bun 被设计为 Node.js 的直接替代品，它是一个单独的二进制文件（使用 Zig 编写），能够充当 JavaScript 和 TypeScript 的打包器、转译器（transpiler）与包管理器。
- Databricks Unity Catalog。[Databricks Unity Catalog](https://www.databricks.com/product/unity-catalog) 是一种可以用于 lakehouse 中文件、表或者机器学习模型等资产的数据治理方案。
- Dragonfly。[Dragonfly](https://github.com/dragonflydb/dragonfly)是一个可兼容Redis和Memcached API的新型内存数据库。它利用Linux新有的io_uring API实现I/O，并在多线程、无共享架构的基础上实现新型的算法和数据结构。
- Edge Impulse。TinyML 是在带有板载传感器的小型设备上运行经过训练的模型，在不经过云端的情况下做出决策或提取特征的实践。[Edge Impulse](https://www.edgeimpulse.com/) 是一个端到端托管平台，用于开发运行在如微控制器等小型边缘设备上的优化模型。
- GCP Vertex AI。[GCP Vertex AI](https://cloud.google.com/vertex-ai) 是一个统一的人工智能平台，它让团队可以构建、部署、并扩缩机器学习（ML）模型。
- Gradient。[Gradient](https://www.paperspace.com/gradient) 是一个用于构建、部署和运行机器学习应用的平台，非常类似于谷歌的 Colab。用户可以从模板创建 Notebook，以快速搭建 PyTorch 或 TensorFlow 或像 Stable Diffusion 这样的应用。
- IAM Roles Anywhere。[IAM Roles Anywhere](https://docs.aws.amazon.com/rolesanywhere/latest/userguide/introduction.html) 是 AWS 的一项新服务，可让你在 IAM 中为运行在 AWS 之外的服务器、容器和应用程序等工作负载获取临时的安全凭证。
- Keptn。[Keptn](https://keptn.sh/) 是用于交付和运维的控制平台，依赖于 CloudEvents 进行插桩。就像在 CI/CD 流水线观测技术中提到的，Keptn 将其编排可视化为 traces。
- OpenMetadata。[OpenMetadata](https://github.com/open-metadata/OpenMetadata#what-is-openmetadata) 是一个致力于使用开放标准进行元数据管理的平台。简单的架构、聚焦于自动化的部署、和在数据可发现性的重点关注能提升开发体验。
- OrioleDB。[OrioleDB](https://github.com/orioledb/orioledb/) 是一个为 PostgreSQL 打造的全新存储引擎。尽管
  其现有多个选项用来适应现代硬件，但因其存储引擎最初是为机械硬盘设计，达成优化目标的过程可能困难并且繁琐。OrioleDB 为了解决这些问题，实现了显式支持固态硬盘和非易失性随机存取存储器（NVRAM）的云原生存储引擎。想要试用该引擎，用户首先需要给当前的表访问方法安装增强补丁，然后以 PostgreSQL 扩展的形式安装 OrioleDB。

### 暂缓

-



## 工具

### 采纳

- [Great Expectations](https://docs.greatexpectations.io/en/latest/)。允许用户搭建用于标记数据流水线中的异常或质量问题的内置控件。正如单元测试在构建流水线中运行一样，Great Expectations 在数据流水线的执行过程中进行断言。
- k6。性能测试的首选工具。[k6](https://k6.io/) 很容易与可视化工具集成，如 Grafana 和
  New Relic，这有助于调试基础设施和应用程序。

### 试验

- Apache Superset。[Apache Superset](https://superset.apache.org/) 是一个很棒的 BI（Business Intelligence，商业智能）工具，用于与大型数据湖和数据仓库一起进行数据探索和可视化。它支持多种数据源，包括 AWS Redshift，BigQuery，Azure MS SQL，Snowflake和 ClickHouse。
- AWS Backup 文件库锁定。[AWS Backup 文件库锁定](https://docs.aws.amazon.com/aws-backup/latest/devguide/vault-lock.html)强制应用保留和删除策略，甚至防止包括拥有管理员权限的用户修改或删除备份文件。
- [AWS Control Tower](https://aws.amazon.com/controltower)。多团队的账户管理在 AWS 中是一个挑战，特别是设置和管控方面。
- Clumio Protect。用 [Clumio Protect](https://clumio.com/products/protect/) 备份了 AWS 数据，特别是针对 S3。
- [Cruft](https://cruft.github.io/cruft/)。自从我们第一次定义微服务以来，就一直在谈论定制化服务模板。如果组织着手创建一个可以独立但一致地开发、构建、部署和操作的微服务集合，那么为团队提供一个符合标准的坚实起点是有意义的。然而，这种方法一个持久的问题是，**随着时间的推移，模板会随着技术和业务需求的变化而发展，基于旧版本模板的项目就会过时了**。改造模版从而改进已建立的项目成为一个重大的痛点。Cruft 试图通过提供工具来解决这个问题，以识别和修补本地项目与主模板库的当前版本之间的差异。它将 Cookiecutter 模板引擎与 git 哈希值相结合，以识别和应用模板的变化。可以把它看作是项目模板的一个包管理器。
- Excalidraw。[Excalidraw](https://excalidraw.com/) 是一个简单却强大的在线绘图工具。可以产出低保真样式的图表，这让人想起在同地协作时绘制的白板图表。
- Hadolint。[Hadolint](https://github.com/hadolint/hadolint) 有助于发现 Dockerfile 中的常见问题。它可以快速、准确地发现问题且具有良好的使用文档。它会在第一时间解释如何解决问题以及为什么它是一个问题，从而促使Dockerfile 作者趋向好的实践。
- Kaniko。当前大多数的 CI/CD 流水线工具和平台都是以容器作为运行时构建的。使用 [Kaniko](https://github.com/GoogleContainerTools/kaniko) 从这些基于容器的流水线中构建容器镜像，这是一种趋势的一部分，即不再将 Docker 容器运行时作为业界标准。
- [Kusto 查询语言](https://learn.microsoft.com/en-us/azure/data-explorer/kusto/query/)。Kusto 查询语言（KQL）是一个对SQL 语言进行改进的工具。KQL 是由 Azure 创建的语言，它给关系型查询语言带来了模块、封装、组合、复用、扩展、和动态化的能力。你可以将一个查询语句导入渲染操作符并立刻看到生成的图表。
- Spectral。[Spectral](https://stoplight.io/open-source/spectral/) 是一个强调 OpenAPI 和 AsyncAPI 的 JSON/YAML 代码静态检查工具（linter）。在设计和实现 API 或进行事件驱动的协作时，它所提供的全面且开箱即用的规则可以帮助开发者省去很多麻烦。
- Styra Declarative Authorization Service。[Styra Declarative Authorization Service](https://www.styra.com/styra-das/)（DAS 声明式授权服务）是一个用来规模化管理 Open PolicyAgent（OPA）的治理和自动化工具。
- 监视项目构建的 [xbar](https://github.com/matryer/xbar)。监视项目构建。

### 评估

- Clasp。[Clasp](https://github.com/google/clasp) 使你至少可以在 Apps Script 代码里应用一些 Continuous Delivery的实践。
- [Databricks Overwatch](https://databrickslabs.github.io/overwatch/)。让团队能分析 Databricks 负载运行时的各种包括成本、治理、和性能在内的多种运营指标，并且支持运行假设性的试验。
- dbtvault。Data Vault 2.0 是一种数据建模方法和设计模式，相对于其他流行的建模方法，它的目的是进一步提高数据仓库的灵活性。Data Vault 2.0 可以应用于任何数据存储中，例如 Snowflake 或 Databricks。当实现 Data Vault仓库时，dbt 的 [dbtvault](https://www.data-vault.co.uk/dbtvault/) 包是一个有用的工具。dbtvault 提供了一组 jinja 模版，用于生成和执行填充 Data Vault 仓库所需的 ETL 脚本。
- git-together。[git-together](https://github.com/kejadlen/git-together) 使用 Rust 编写，简化了结对编程时代码提交的属性配置。通过将 git-together 设置为 git 的别名，git-together 允许您在 git config 中添加扩展配置以捕获提交者的信息，并以每个提交者的首字母为别名。
- [Harness 云服务成本管理](https://harness.io/products/cloud-cost)。Harness 云服务成本管理是一款为三个主要的云服务提供商及其托管的 Kubernetes 集群提供服务的商业工具，用来帮助其可视化及管理云服务成本。
- [Infracost](https://infracost.io/)。之前提过将运行成本实现为架构适应度函数，而 Infracost 是一个旨在在 Terraform pull request 中可视化成本权衡的工具。它是一个开源软件，在 macOS、Linux、Windows 和 Docker 均可访问，开箱即用支持 AWS、GCP 和微软 Azure 的定价。
- [Karpenter](https://karpenter.sh/)。开源的 Kubernetes Operator 自动扩缩器 Karpenter，它内建了更多智能：可以分析当前的工作负载和 Pod调度约束，以自动选择合适的实例类型，然后根据需要启动或停止它。Karpenter 是一个具有如 Crossplane等工具精神的 operator，可以在集群外提供云资源。
- Mizu。[Mizu](https://github.com/up9inc/mizu/tree/main) 是一个 Kubernetes 的 API 流量查看器。Mizu 不需要增加监测工具或改动代码，而是在 Kubernetes 集群的节点层面上注入一个容器作为 DaemonSet，做一些类似 tcpdump 的操作。Mizu 可以实时监测多种协议（REST、gRPC、Kafka、AMQP 和 Redis）下的所有 API 通信，可以作为一个调试工具来使用。
- [Soda Core](https://www.soda.io/core)。之前讨论过 Great Expectations，而Soda Core 作为另一种解决方案，其关键区别在于数据校验可以通过一种称为 SodaCL的 DSL 来表示，而非 Python 函数。
- [Teller](https://github.com/tellerops/teller)。Teller 是一款面向开发人员的开源通用密钥管理器，可确保应用程序启动时，环境变量可以被正确地配置。它本身并非一个存放私密信息的保险库（vault），而是一个可连接多种源的 CLI 工具，从云密钥提供者到诸如 HashiCorp Vault 的第三方解决方案再到本地环境文件。
- [Xcode Cloud](https://developer.apple.com/xcode-cloud/)。Xcode Cloud 是一个集成在 Xcode 中的 CI/CD 工具，用于开发、测试和部署苹果应用程序。它为熟悉 Xcode、App Store Connect 和 Test Flight 的苹果开发人员提供了一体化的体验。它的优势在于简化流水线配置和创建配置文件和证书。

### 暂缓

- 在线格式化或代码解析服务。将 JavaScript、JSON 或类似代码片段粘贴到一个未知的网站很容易造成安全和隐私问题，并可能在不知不觉中将个人数据导出到不同的信息管辖区。



## 语言和框架

### 采纳

- io-ts。[io-ts](https://gcanti.github.io/io-ts/) 通过提供编码和解码函数，在编译时类型检查和运行时消耗外部数据之间架起桥梁。
- Kotest[。Kotest](https://kotest.io/) 是 Kotlin 生态中的一个独立测试工具。Kotest 的主要优点是它提供了丰富的测试风格来搭建测试套件，其中还有一套全面的匹配器，可以帮助你使用优雅的内部领域专用语言（DSL）编写表达式测试用例。
- [NestJS](https://nestjs.com/)。在需要使用 Node.js 构建后端应用程序的某些场景下，NestJS 是一个很好的选择。它使开发人员能够创建可测试、可扩展、松耦合且易于维护的企业级应用程序。
- React Query。[React Query](https://react-query-v3.tanstack.com/) 通常被描述为 React 缺失的数据获取库。React Query 提供了一种基于 hooks 的方式，它与现有的基于 promise 机制的异步数据获取库协同工作，如 axios、Fetch 和 GraphQL。作为应用程序开发人员，你只需要传递一个解析数据的函数，其余的事情可以留给框架完成。它的开发者工具能帮助刚接触此框架的开发人员理解其工作原理，遗憾的是，其开发者工具尚不支持 React Native。对于 React Native，你可以使用第三方开发者工具插件 Flipper。
- Swift Package Manager。通过 [SwiftPM](https://github.com/apple/swift-package-manager)，依赖包的创建者和使用者的操作流程都得到了极大的简化。
- Yjs。无冲突复制数据类型（CRDT）算法被证明能够在对等节点中自动地分发与合并修改而不产生冲突。[Yjs](https://yjs.dev/) 是一个精心优化的 CRDT 实现，能够在面对大型数据集和数百万个修改时将内存开销保持在合理的水平。它也能绑定到流行的文本编辑器上，这一点显著地降低了构建协作工具的成本。

### 试验

- [Azure Bicep](https://docs.microsoft.com/en-us/azure/azure-resource-manager/bicep/overview?tabs=bicep)。Azure Bicep 是一种使用声明式语法的领域特定语言（DSL），主要面向那些喜欢使用比 JSON 更自然的语言来编写基础设施代码的人。
- Camunda。[Camunda](https://camunda.com/) 提供的工作流和决策引擎可以作为库集成到用户
  的 Java 代码中。这使得测试、版本化和重构工作流变得更容易，缓解了其他低代码工作流引擎的一些缺点。
- Gradle Kotlin DSL。 它为使用 Gradle 构建脚本的Android 工程增加了对 Kotlin 脚本的支持，以替代 Groovy。用 Kotlin 替换 Groovy 的目的是在 IDE 中为重构与更简便地编辑提供更好的支持，以及最终产出更易于阅读和维护的代码。对已经正在使用 Kotlin 的团队而言，这也意味着使用一门熟悉的语言处理构建。
- [Jetpack Media3](https://developer.android.com/jetpack/androidx/releases/media3)。现如今安卓拥有多个媒体 API：Jetpack Media（也被称为 MediaCompat），Jetpack Media2 和 ExoPlayer。然而，这些库都是分别开发的，它们的目的不同但是功能重叠。这就导致安卓开发者在编码的时候不仅需要斟酌类库的选型，当使用的特性来自于多个库的时候，还需要编写适配器或者兼容代码。Jetpack Media3 是从现有 API 中选取通用的功能：包括 UI、播放和媒体会话处理，然后将它们合并和改进成一个新的 API。
- [Ladle](https://ladle.dev/)。Storybook 变得越来越像一个庞然大物。如果你真正关心的是隔离和测试你的 React UI组件，那么 Ladle 是一个替代品。Ladle 支持大多数的 Storybook API（MDX 文件暂时不支持），且可以作为
  Storybook 的替代品。它是轻量级的，与 Vite 有更好的集成。
- [Moshi](https://github.com/square/moshi)。很多使用Kotlin语言的团队在寻找替代GSON这样基于Java的JSON处理工具。刚出现不久的Moshi已经成为了许多此类团队的首选方案。
- Svelte。[Svelte](https://svelte.dev/) 不是通过使用虚拟DOM 和浏览器优化技巧来优化 DOM 更新，而是将你的代码编译成无框架的 JavaScript 代码，像做外科手术一
  样更新 DOM。除了性能优势，它还有友好的学习曲线和编写更少代码的维护优势。Svelte 本身只是组件框架，但 SvelteKit 增加了可以构建完整 Web 应用程序的功能。

### 评估

- [Aleph.js](https://alephjs.org/docs)。考虑到 Deno 是由 Node 原本的开发者所创建的新的服务器端运行时，这意味着 Aleph.js 处在一个更先进的平台上，能够解决 Node 相关的很多缺陷和问语言和框架问题。
- Astro。[Astro](https://astro.build/) 是最新推出的开源，多页面响应的应用程序框架，它可以在服务器上渲染页面并尽可能减少通过网络发送的 JavaScript 数量。尽管 Astro 鼓励只发送 HTML，但它仍然支持在适当的时候选择用您选用的前端 JavaScript 框架编写的活动组件。
- BentoML。[BentoML](https://github.com/bentoml/BentoML) 是一个 Python 优先的框架，用于在生产环境中规模化部署机器学习模型。它提供的模型与环境无关；所有模型属性、源代码和依赖包都封装在称为 Bento 的自包含格式中。您可以将 BentoML 视为机器学习模型的 Docker：它使用预编程 API 生成可立即部署的虚拟机镜像并包含相应的功能使测试这些映像变得十分简单。
- [Carbon Aware SDK](https://github.com/Green-Software-Foundation/carbon-aware-sdk)。更高效的软件只需要更少的电力和服务器，从而减少发电与制造服务器所带来的碳排放。另一个策略是使应用程序具有碳意识。这是因为同样的工作负载并不总是具有相同的碳足迹。借助 Carbon Aware SDK，软件工程师们可以查询数据源来发现对于给定的工作负载而言碳密集度更低的选项，然后将它移动到不同的位置或是在不同的时间运行它。
- Cloudscape。[Cloudscape](https://cloudscape.design/) 是一款开源的设计系统，它不仅有一套丰富的组件集，还有 35 种交互和内容展示模式。
- Connect。[Connect](https://connect.build/) 是一系列用于构建与浏览器和 gRPC 兼容的 HTTP API 的库。与 gRPC 类似，您编写协议缓冲区的架构并实现其应用程序逻辑，然后 Connect 生成代码来处理编组、路由、压缩和内容类型协商。
- 跨设备 SDK。随着智能设备持续融入我们的生活，我们开始看到跨越多个设备的新用例出现。典型的例子是我们在手机上开始阅读一则文本但是更喜欢在平板电脑上读完它。Apple 不久前已经开始将此类功能引入到它自己的 SDK 中了，现在 Google 也发布了其[跨设备 SDK](https://developer.android.com/guide/topics/connectivity/cross-device-sdk/overview) 的首个预览版本。
- [Cypress 组件测试](https://docs.cypress.io/guides/component-testing/writing-your-first-component-test)。Cypress 组件测试提供了一个组件测试工作台，以快速构建和测试 UI 组件。你可以用编写端到端（E2E）UI 测试的相同 API 来编写组件视觉回归测试。
- JobRunr。[JobRunr](https://www.jobrunr.io/) 是一个可以替代 Quartz 调度器的 Java 后台任务执行库。它内置的用于监控和调度后台任务的仪表盘操作简便。
- Million。[Million](https://github.com/aidenybai/million) 是一个新的虚拟 DOM JS 库。与 Svelte 类似，它使用编译器 Vite，来创建具有卓越渲染性能的小型JS 库。Million 库作为单个 NPM 包提供，其中包含了多个模块，包括 router，jsx-runtime 和一个用于保证React 兼容性的模块以创建单页应用程序。
- Soketi。[Soketi](https://github.com/soketi/soketi) 是一个开源的 WebSockets 服务器。如果你的应用程序兼容 [Pusher](https://pusher.com/) 协议，你可以直接接入 Soketi，因为它完全遵循了 [Pusher 协议版本 7](https://pusher.com/docs/channels/library_auth_reference/pusher-websockets-protocol/#version-7-2017-11)。
- Stable Diffusion。OpenAI 的 [DALL·E](https://openai.com/blog/dall-e/) 可以[从文本提示创建图像](http://wearemtm.com/2022/07/22/dall-e-2-creating-photorealistic-images-from-text/)，这吸引了所有人的注意。现在，[Stable Diffusion](https://stability.ai/blog/stable-diffusion-public-release) 也可以提供相同的功能。更重要的是，它是开源的。任何可以使用性能强劲的显卡的人都可以对模型进行试验，而任何拥有够足够计算资源的人都可以自己重新创建模型。结果很震撼，但也带来了重大的问题。例如，该模型使用[互联网爬虫](https://laion.ai/blog/laion-5b/)获得的图像—文本对进行训练，因此它带有社会偏见，这意味着它可能会产生非法、令人不安，或至少不受欢迎的内容。虽然 Stable Diffusion 现在有基于 AI 的[安全分类器](https://huggingface.co/CompVis/stable-diffusion-safety-checker)，但由于它是开源的，使用者可以禁用这一分类器。还有，艺术家们注意到，在特定的提示词下，模型可以模仿他们的艺术风格。因此引发了对于能够模仿艺术家的人工智能的伦理和法律影响的疑问。
- 合成数据保险库。[合成数据保险库](https://github.com/sdv-dev/SDV)是一个数据生成工具库，它可以学习数据集的分布，以生成与源数据具有相同格式和统计属性的合成数据。

### 暂缓

- [Carbon](https://github.com/carbon-language/carbon-lang)。**正如在过去几十年的时间里软件工程师们所表现的那样，写出安全且没有错误的 C++ 代码是一件极其困难且耗时的事情**。以今天的视角来看，推荐项目组去关注一下 Rust 和 Go 而不是等着Carbon 的到来而推迟移植项目。