# vol 16



## 最新动态

- 会话式用户界面和自然语言处理

- 智能即服务

  “云计算之战”逐渐从存储和计算能力向认知能力转变

- 开发者体验成为新的差异化竞争优势

  将内部基础设施作为一种产品，令其具有足够的吸引力来与外部产品进行竞争；专注于自助服务系统；理解所开发的 API 的“开发者人机工程学”（developer ergonomics）；对遗留系统进行封装；以及对开发者的“持续用户共情研究”（ongoing empathetic user research）的投入。

- 平台的崛起

- 盛行的PYTHON



## 技术

- 将 API 当做产品（APIs as a product）

- 从代码中解耦秘密信息的管理。 git-crypt 和Blackbox 这样的工具。或者HashiCorp vault 持续集成服务器和配置管理工具。
- 封装遗留系统。采用虚拟机镜像或 Docker 容器来创建遗留系统及其配置的镜像。

- 渐进式 Web 应用 (PROGRESSIVE WEB APPLICATIONS）

- 使用 INVISION 和 SKETCH 进行原型设计

- 无服务器架构这种方法通过短暂存在的计算能力来替代长期运行的虚拟机。

- 会话感知 API 是后端为前端服务模式的示例，前端是语音聊天平台。
- 社会化代码分析通过将开发人员的行为与代码的结构化分析结合，丰富了我们对代码质量的理解。

- 不要为所有团队创建单一持续集成实例。

- 企业集成测试环境。

- 回到 SOAP 在企业软件行业中风光的日子，从 WSDL 规范生成客户端代码的做法是一个被认可的，甚至鼓励的做法。不幸的是，这种方式产生的代码经常很复杂、不可测试、难以修改而且经常频繁出现跨平台实现不可工作的情况。

  随着 REST 出现，使用宽容读者模式用于演进那些需要提出和处理字段的 API 客户端会更好。

  一种令人不安的老习惯正在复苏，开发人员使用Swagger 或 RAML 编写 API 规范生成程序代码，这种实践可以被称之为基于规范的代码生成。尽管这样的工具对于驱动 API 的设计和提取文档非常有用，但得警惕直接**从这些规范生成客户端代码的代价是难以维护和测试**。

## 平台

-  LINUX 安全模块（LSM）
- AMAZON API GATEWAY 
- 随着单体应用被更复杂的 （微）服务生态系统所取代，跨越多个服务的请求追踪正成为常态。
- [OPENTRACING](http://opentracing.io/) 正在迅速成为分布式追踪的事实上的标准

- 伴随着近期聊天机器人和语音平台的涌现，类似 API.AI 等工具和平台激增。
- 云端图片理解以服务的方式提供对机器学习能力的访问，像 Amazon Rekognition ， Microsoft Computer Vision API ，以及 Google Cloud Vision API。
- 以太坊
- 超级账本（HYPERLEDGER）是一种围绕区块链技术构建的平台。
- KAFKA STREAMS 是一个构建“流应用”（streaming applications）的轻量级库。它的设计目的是将流处理简化得更加易于访问，从而作为异步服务的主流编程模型。
- Keycloak 是一个开源的身份和访问管理解决方案

- PLATFORMIO 为物联网开发提供了具有跨平台构建、依赖库管理和良好的 IDE 集成的生态系统。
- TANGO 是一种用于手机的新型硬件传感器技术

- WEBVR 是一组可让你通过浏览器访问 VR 设备的实验性 JavaScript API。此项技术以及相关补充工具，例如 Three.js ， A-Frame ， ReactVR ，Argon.js 和 Awe.js 都能够为浏览器带来 AR 体验

## 工具

- FASTLANE 作为自动化 iOS 和 Android 应用的发布流程的工具。

- AIRFLOW 是一个用来通过编程创建、调度和监控数据流水线的工具

- MSBuild 自从2005年推出以来一直是 .NET生态系统中的主要构建系统。CAKE 和 FAKE 是两个备选方案。

- SCIKIT-LEARN 

- Serverless Framework 为 JavaScript, Python, Java 和 C# 语言提供了项目模板，并拥有有一个活跃的社区为其贡献扩展插件

- AMAZON REKOGNITION 是我们在这次雷达其他地方提到的云端图像理解工具之一。

- INSPEC 是受 Serverspec 启发而开发出来的基础架构测试工具

- MOLECULE 旨在帮助开发和测试 Ansible 的 Role 。通过在虚拟机或容器上为正在运行的 Ansible Role 的测试构建脚手架，我们无需再手工创建这些测试环境。
-  Spacemacs 可以作为进入 Emacs 平台的敲门砖，它拥有新的键盘用户界面，简化的定制层以及 Emacs 软件包的分发管理。
- SPACY 是一个 Python 编写的自然语言处理（NLP）库。

- SPINNAKER 将集群管理和烘培镜像部署当作头等功能来实现。

- Testinfra 的目标是成为 Serverspec 在Python中的等价物，并且作为 Pytest测试引擎的插件来使用

- YARN 是一个新的包管理工具，可替换现有 npm 客户端的机制，同时兼容 npm 注册表。 如果使用 npm 客户端，根据依赖库的不同安装顺序，它会在 node_modules下得到一个不同的树结构。



## 语言&框架

- python 3 成熟
- 分布式系统经常利用多线程、基于事件的通信和非阻塞 I/O来提高整体系统效率。这些编程技术提出了诸如低级线程、同步、线程安全、并发数据结构和非阻塞 I/O 等挑战。开源的 **REACTIVEX** 库优雅地解决了这些问题。

- AVRO 是一个数据序列化的框架。它通过将 schema 与消息内容共同存放的方式来鼓励 schema 演进。生产者可以编辑字段名称，添加新字段或删除现有字段，而 Avro 确保客户端可以继续消费消息

- NIGHTWATCH 是一个框架，它支持用 JavaScript 创建基于浏览器的自动化验收测试、并用 Node.js 运行这些测试。
- Vue.js
- Angular 2 是 AngularJS 的重写而不是演进，从AngularJS 切换到 Angular 2 与从 AngularJS 切换到另一个框架没有太大的不同。

- CAFFE 是一个用于深度学习的开源库
- INSTANA 是拥挤的应用程序性能管理领域中的又一个新成员

- KERAS 是一个用 Python 编写的用于搭建神经网络的高阶接口

- KNET.JL 是土耳其 Koc 大学基于 Julia 实现的深度学习框架

- Kotlin 是 JetBrains 的一个开源且基于JVM的语言
- POSTCSS 是一个基于 Node.js 的 JavaScript 框架，能够操作基于抽象语法树的 CSS 文件。
- 团队构建微服务系统时需要考虑服务协调技术（coordination techniques），如服务发现、负载均衡、熔断和健康检查。。 SPRING CLOUD 项目为开发者提供了一系列工具，以便他们可以在所熟悉的 Spring 技术栈下使用这些服务协调技术。 这些工具支持 Consul ，ZooKeeper 和 Netflix 全栈开源软件技术栈。