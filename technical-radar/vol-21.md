# vol 21

## 最新动态

### 云服务商快速发布产品

警惕云服务的快速发展，应该对关键功能进行测试。

### 保护软件供应链

组织应该抵制冗长的人工检测和需要审批的象牙塔治理规则。

我们看到自动化不断提升的软件开发生态系统的自然演化：与自动测试、持续交付和基础设施即代码的持续集成，以及如今的自动化治理。云成本、依赖管理、架构结构等以往人工流程的自动化呈现出一种自然演进的态势；我们正在学习如何让软件交付中的所有重要方面自动化。

### 打开机器学习的黑匣子

尽管这些技术功能强大，但许多模型本质上令人费解。在选择机器学习模型时，数据科学家开始将可解释性视为第一要务。

### 软件开发是一项团队运动

特别不提倡“10倍工程师”的理念，更喜欢专注于创建和赋能“10倍团队”。



## 技术

### 采纳

- Container security scanning。将自动化扫描工具的运行作为部署流水线的一部分。

- Data integrity at the origin。强调数据源在源头处就保证完整的重要性。

- 微前端。一篇[介绍性的文章](https://martinfowler.com/articles/micro-frontends.html)，可以起到微前端参考文献的作用。

- Pipelines for infrastructure as code。CI/CD工具能够被用来测试服务器的配置（如Chef cookbooks，Puppet modules和Ansible playbooks），服务器的镜像构建（如[Packer](https://www.thoughtworks.com/cn/radar/tools/packer)），环境的生成（如Terraform，CloudFormation）和环境间的集成。
- Run cost as architecture fitness function。对于今天的组织来说，自动化评估、跟踪和预测云基础设施的运行成本是必要的。
- Testing using real device。真机测试重要性。

### 试验

- Automated machine learning（AutoML）。可以使用这些工具作为起点，生成基本有用的经过训练的模型，但还是鼓励寻找经验丰富的数据科学家来验证和完善最终的模型。
- Binary attestation。二进制鉴证就是一项实现部署时安全控制
  的技术，用密码学技术验证部署用的二进制镜像。
- Continuous delivery for machine learning （CD4ML）。CD4ML是将CD原理和实践引入ML应用程序的学科。它消除了从训练模型到部署生产环境的长周期。

- Data discoverability。数据发掘和数据分析。
- Dependency drift fitness function。依赖漂移适应度函数是一项引入了特定的演进式架构适应度函数的技术，它能随着时间推移追踪这些依赖，从而能够指出可能需要的工作，以及某个潜在问题是在好转还是恶化。
- Design systems。设计系统定义了一套设计模式、组件库以及良好的设计和工程实践的集合，以确保数字产品开发的一致性。
- Experiment tracking tools for machine learning。不同的”用于机器学习的实验跟踪工具”，以帮助研究人员有条理地进行实验，并跟踪这些实验结果。

- Explainability as a first-class model selection criterion。可解释的模型是一个允许我们说明决策是如何做出的模型。例如，一个决策树产生描述分类过程的推理链。

- Security policy as code。定义安全策略脚本、将其加入版本控制中、自动验证、自动部署并监测其性能。
- Sidecars for endpoint security。。端点安全性的边车(Sidecars for endpoint security)用于在每个组件的端点上实施安全控制，例如服务、数据存储和Kubernetes控制接口的API。
- Zhong Tai。中台的核心是提供封装业务模型的方法。它旨在帮助新型的小型企业提供一流的服务，而无需传统企业基础架构的成本，并使现有组织能够以惊人的速度将创新服务推向市场。

### 评估

- BERT。[BERT](https://arxiv.org/abs/1810.04805)代表来自变换器的双向编码器表征量。它是Google在2018年十月份提出的一种新的预训练表示方法。BERT通过获得各种自然语言处理（NLP）任务的最高水平结果，极大地改变了自然语言处理（NLP）的格局。BERT以及它的后继者会继续使NLP的迁移学习成为一个令人兴奋的领域，NLP的迁移学习可以大大减少处理文本分类的用户工作量。
- Data mesh。数据网格解决了传统集中式数据湖或数据平台体系结构的常见故障模式，它改变了数据湖及其前身数据仓库的集中式范式。
- Ethical bias testing。道德偏见测试的标准和实践.。
- Federated learning。联邦学习，让数据保留在用户的设备上，并
  完全控制在用户的手中，但最终会仍然可以组合成一个整体的训练数据集。
- JAMstack。集合了静态站点生成和利用第三方API进行客户端渲染的框架被称为JAMstack（JAM代表JavaScript，API和Markup），例如Gatsby.js。
- Privacy preserving record linkage （PPRL） using Bloom filter。（Bloom filter，一种节省空间的概率数据结构）建立保护隐私的记录连接（PPRL）是一种成熟的技术，它允许来自不同数据提供者进行概率记录链接，而不会公开私密的个人身份资料。
- Semi-supervised learning loops。半监督学习循环是一类迭代式的机器学习工作流，它们利用未标记数据中尚待发现的关系，来提升学习性能。这些技术通过不同方式组合标记和未标记的数据集，从而改进模型。

### 暂缓

- 10倍团队，而不是10倍工程师。
- Front-end integration via artifact。通过制品进行前端集成。这种模式意味着每一次修改都需要重建整个包。
- Lambda pinball。避免落入分布式单体应用的陷阱。
- Legacy migration feature parity。越来越多的组织需要替换陈旧的遗留系统，以适应其客户（内部和外部）的需求。一种反模式是遗留系统迁移的功能一致性，即保留旧版本同样功能的愿望。**旧系统往往会变得臃肿，包含了许多不被用户使用的功能。**应当进行用户调研，并采用现代产品开发实践，而不是简单地替换现有的系统。

## 平台

### 试验

- Apache Flink 被视为领先的流处理引擎。
- Apollo Auto。百度旗下的Apollo项目的目标，是成为自动驾驶产业里的安卓操作系统。

- GCP Pub/Sub。GCP Pub/Sub是谷歌云的事件流平台。
- Mongoose OS。Mongoose OS依然是首选的开源微控制器操作系统和嵌入式固件开发框架。
- ROS。ROS（Robot Operating System，机器人操作系统）这套程序库和工具集，能帮助软件开发人员创建机器人应用程序。

### 评估

- AWS Cloud Development Kit。Terraform使用配置文件，AWS CDK使用编程语言来定义云基础设施。

- Azure DevOps。Azure DevOps服务包括一组被托管的服务，如Git仓库、CI/CD流水线、自动化测试工具、待办列表管理工具和制品仓库。
- Azure Pipelines。该解决方案的亮点在于，能将脚本运行在Linux、MacOS和Windows代理（agent）上，而无须自行 管 理 虚拟机 。
- Crowdin。[Crowdin](https://crowdin.com)是少数几个同类平台中的一员，能简化项目本地化工作流程的。开发团队在持续构建功能的同时，Crowdin平
  台可以持续将待翻译文本，整合至在线工作流中。
- Crux。Crux是一个带有双时态图查询的开源文档数据库。许多数据库系统都是有时态的，这意味着它们可以帮助我们对事实及其所发生的时间进行建模。
- Delta Lake。Delta Lake是一个由Databricks开发的开源存储层，用于在大数据场景中引入事务处理。在使用Apache Spark时经常遇到的一个问题是缺少ACID事务。
- Fission。Kubernetes的无服务器生态系统正在增长。之前讨论过Knative，现在我们看到Fission获得了关注。Fission让开发人员可以专注于编写短期的函数并将它们映射到HTTP请求，而Kubernetes资源的自动化和其他工作则交给框架幕后处理。
- FoundationDB。FoundationDB是一个开源的多重模型数据库。被苹果公司收购三年后，于2018年4月进行了开源。
- GraalVM。[GraalVM](https://www.graalvm.org/)是一种由Oracle开发的通用虚 拟 机 ，用 于 运 行 基 于 J V M 的 语 言 ，JavaScript，Python，Ruby和R以及C/C++等其他基于LLVM的语言编写的应用程序。
- Hydra。[Hydra](https://www.ory.sh/hydra/)是一个完全兼容开源OAuth2认证服务器和OpenID
  Connect的服务提供方。
- Kuma。[Kuma](https://kuma.io/)是可用于Kubernetes、VMs和裸机环境的，与平台无关的服务网格。
- MicroK8s。为开发人员提供类似的本地体验变得越来越困难，我们发现[MicroK8s](https://microk8s.io/)非常有用。

- Oculus Quest。Oculus Quest成为首批面向大众消费市场的无需智能手机绑定或支持的VR一体机之一。

- ONNX。围绕神经网络的相关工具和框架的生态系统正在迅速地发，但是这些框架和工具之间的互通性也成为一个挑战。在机器
  学习领域，通常需要在一种工具中快速进行原型设计和训练，然后将其部署到其他工具中进行推理。开放神经网络交换格式[ONNX](https://onnx.ai/)的出现，就是为解决这一问题。

- Rootless containers。无根容器在社区被讨论了很长时间，它是开放容器运行时规范及其标准实现runc的一部分，而runc是Kubernetes的基础。现在，Docker 19.03将无根容器作为一个实验性特性引入。
- Snowflake。[Snowflake](https://www.snowflake.com)是一种全新的SQL数据仓库即服务解决方案，专为云平台而构建。
- Teleport。[Teleport](https://gravitational.com/teleport/)是用于远程访问云原生基础架构的安全网关。



## 工具

### 采纳

- Commitizen。[Commitizen](http://commitizen.github.io/cz-cli/)是一款提升GIT提交过程效率的小工具。它会提示你提供任何必要字段，还会恰当地格式化提交信息。
- ESLint。使用ESLint作为JavaScript的代码检查工具。
- React Styleguidist。[React Styleguidist](https://github.com/styleguidist/react-styleguidist)是React组件的开发环
  境。它提供带有热重载功能的开发服务器，并可以生成HTML样式指南以便与团队进行共享。
- Bitrise。[Bitrise](https://www.bitrise.io)这个专注移动应用的持续交付工具对于不需要集成后端流水线的团队来说会更有用。

### 试验

- Dependabot。与GitHub仓库集成，自动检查依赖的版本更新，并在必要时提交一个升级依赖的PR。
- Detekt。[Detek](https://github.com/arturbosch/detekt)t是用于Kotlin的静态代码分析工具。Dekekt可以基于高度可配置的规则集提供代码坏味道以及复杂度的检查报告。 
- Figma。[Figma](https://www.figma.com/)不仅具有与Sketch和Invision等设计程序相同的功能，而且还支持与其他人实时协作。
- Jib。Google的[Jib](https://github.com/GoogleContainerTools/jib)插件使用构建配置中的信息，将应用程序直接构建为Docker镜像，而不需要Dockerfile或Docker守护程序。Jib也针对镜像分层进行了优化，可以提升后续构建的速度。
- Loki。[Loki](https://loki.js.org)是一个配合Storybook使用的可视化回归工具。在UI开发环境中，只需几行配置，就可以使用Loki测试所有UI组件。
- [Trivy](https://github.com/aquasecurity/trivy)。一款用于容器的开源的漏洞扫描器。
- Twistlock。[Twistlock](https://www.twistlock.com/)是提供构建时和运行时安全漏洞检测和预防功能的商业产品，可以保护VM、容器调度程序和容器，以及应用程序依赖的各类注册中心和存储库。
- Yocto Project。越来越多强大的IOT设备选择运行Linux而不是运行一个定制的嵌入式系统。然而为了节省资源并减少攻击面，仍
  然有必要创建一个自定义的Linux版本，其中只包含运行设备程序的相关工具和依赖。在这种情况下可以考虑使用[Yocto](https://www.yoctoproject.org/)。

### 评估

- [Aplas](https://aplas.com/public) 是一个新的软件映射工具，可以用地图的形式可视化软件布局。
- [asdf-vm](https://asdf-vm.com)是一个按项目管理多语言运行时版本的命令行工具。它与Ruby的rvm和Node的nvm等其他命令行版本管理工具类似，但更可以通过可扩展的插件体系架构支持多语言。
- [AWSume](https://github.com/trek10inc/awsume)是一个方便的脚本，用于在命令行中管理AWS会话令牌及担任角色凭证。
- dbt。[dbt](https://www.getdbt.com)既是一个开源工具，也是一个商业化的SaaS产品，为数据分析师提供了简单高效的转换功能。
- [Docker Notary](https://docs.docker.com/notary/)是对镜像、文件及容器等资产进行签名的开源工具，用于验证资产的来源。
- Facets。不论是直接使用还是作为机器学习模型的训练输入，越来越多的重要决策源自于大数据集。因此了解数据中的差距、缺陷和潜在偏见十分重要。Google的Facets项目在此领域提供了两个有力工具：Facets Overview和Facets Dive。
- [Falco](https://falco.org/)是一个专注运行时安全的容器原生工具。它利用Sysdig的
Linux内核监控和系统调用性能数据，可以深度洞察系统的行为，进而帮助我们发现应用程序、容器、主机或者Kubernetes编排器
自身的异常行为。
- [In-toto](https://github.com/in-toto/in-toto)是一个框架，能够以密码学的方式验证软件制品生产路径上的每个组件和步骤。该项目可以与众多广泛使用的构建工具、容器审计工具和部署工具进行集成。
- Kubeflow。Kubeflow是Kubernetes Operators的创新应用。它提供了一种对机器学习工作流进行编码和版本控制的方法，使它可以更容易地从一个执行环境移植到另一个环境。
- [MemGuard](https://github.com/awnumar/memguard)可以作为软件安全区，在内存中存储敏感信息。
- [开放策略代理（OPA）](https://www.openpolicyagent.org/)。OPA可以使用Rego策略定义语言，以代码的方式进行细粒度的访问控制与灵活的策略定义。Rego在应用程序代码之外，以分布式且不干扰用户的方式实施策略。
- [Pumba](https://github.com/alexei-led/pumba)是Docker的混沌测试和网络仿真工具。
- [Skaffold](https://skaffold.dev/)——用于自动化本地开发工作流程的开源工具，同时也支持部署至Kubernetes。
- [What-If Tool （WIT）](https://pair-code.github.io/what-if-tool/)这个工具可帮助数据科学家深入研究模型的行为，并将各种功能和数据集对输出的影响进行可视化。
- Azure数据工厂（ADF）是Azure目前默认的用于编排数据处理流水线的产品，可以用于数据提取，在使用不同存储类型的自有产品或者Azure服务之间复制数据，以及执行数据转换逻辑。

## 语言和框架

### 试验

- A r r o w 是 适 用 于 K ot l i n 的 函 数 式 编 程库，是由两个现有流行库（kategor y和funKTionale）合并而成。

- Flutter。作 为 跨 平 台 框 架，它使用Dart语言编写原生移动应用。借助Dart，Flutter可以编译成平台原生代码并直接和目标平台通讯，从而避免了桥接和上下文切换。

- [jest-when](https://www.npmjs.com/package/jest-when)是一个轻量级的JavaScript库，通过匹配模拟函数调用的参数完善了Jest的功能。
- [Micronaut](https://micronaut.io/)是一个JVM框架，可以用来构建Java，Kotlin或者Groovy服务。它没有通过运行时反射来完成依赖注入（DI）和生成代理（传统框架的常见缺点），而是使用了DI/AOP容器在编译时执行依赖注入，因此具有内存占用小、启动时间短的特点。

- [React Hooks](https://reactjs.org/docs/hooks-intro.html)成为了流行的JavaScript框架。它无需编写类就可以使用状态和其他React功能，从而提供了一种比使用高阶组件或render-props更简洁的方法。
- React Testing Library。
- 使用带标签的模板文字[styled components](https://www.styled-components.com/)，可以将为React组件设置样式所需的CSS直接放入创建该组件的JavaScript代码中。这大大减轻了管理CSS的痛苦，并且不需要为避免CSS中的命名冲突而想尽办法。

- TensorFlow的2.0版本保持了其作为业界领先的机器学习框架的突出地位。
- [Fairseq](https://github.com/pytorch/fairseq)是Facebook AI Research的序列到序列建模工具套件，允许研究人员和开发人员训练定制模型以进行翻译、摘要、语言建模和其他NLP任务。
- [Flair](https://github.com/zalandoresearch/flair)是一个简单的基于Python的NLP框架。它让用户可以执行标准的NLP任务，例如命名实体识别（NER），词性标记（PoS），词义消歧和分类，并且在一系列NLP任务中都表现良好。

### 评估

- Gatsby.js是一个用于编写JAMstack架构风格网络应用的框架。应用的一部分在构建时生成并且以静态站点的形式进行部署。剩余的功能以渐进式网络应用的方式进行实现并运行在浏览器中。这些应用无需服务端代码即可运行。

- GraphQL。
- 在Kotlin生态系统中，[KotlinTes](https://github.com/kotlintest/kotlintest)t提供了基于属性的测试。它的关键优势在于提供了多种测试方法以构建测试套件。同时，它内置了一组全面的匹配器，使我们能用优雅的内部DSL编写富有表现力的测试。

- [NestJS](https://nestjs.com/)是使用TypeScript编写的服务器端框架 。通过集成Node. js社区的丰富 生 态，N e st J S 提 供 了 一 种 开 箱 即 用的 应 用 程 序 架 构 。
- 在使用HTML和相关技术来生产书籍和其他印刷品时，必须考虑分页问题。这包括页面计数器、页眉和页脚中的重复元素和分页符。[Paged.js](https://www.pagedmedia.org/paged-js/)是一个开源代码库，它为PagedMedia和Generated Content for PagedMedia CSS模块实现了一系列补充代码。
- 像Micronaut框架一样，[Quarkus](https://quarkus.io/)通过使用提前编译技术在编译时进行依赖注入，避免了反射造成的运行时成本。它还可以很好地和GraalVM的原生映像配合使用来进一步减少启动时间。
- [SwiftUI](https://developer.apple.com/cn/xcode/swiftui/)框架从近年来主导Web开发的React.js的世界中汲取了灵感，它利用视图模型中的不可变值和异步更新机制，构成了统一的反应式编程模型。这为开发人员提供了一个完全原生的替代品，以替代类似React Native或Flutter之类的反应式框架。
- Testcontainers是一个Java库，通过在测试中施行容器化的依赖管理来缓解创建可靠的环境来运行自动化测试这一问题。这对于扩展可重复的数据库实例或类似的基础结构特别有用，同时它也可以在Web浏览器中用于UI测试。利用此库提供的可编程、轻量且一次性的容器，集成测试会变得更加可靠。

### 暂缓

- 强烈感受到[Enzyme](https://airbnb.io/enzyme/)应该替换为React Testing Library来用于测试React界面组件。使用Enzyme的团队发现它对于被测试组件内部的聚焦会导致脆弱的、无法维护的测试。