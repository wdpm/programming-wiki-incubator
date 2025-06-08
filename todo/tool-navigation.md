# Technology Choice
## 编程相关

- CSS 框架
  - 原生
    - https://getuikit.com/docs/javascript UIKit，原生 UI组件效果实现参考。
    - [MDUI](https://github.com/zdhxiong/mdui)
    - https://mantine.dev/
    - https://github.com/haiilo/catalyst A framework-agnostic design system and component library based on web components and SCSS.

  - based on Tailwind CSS
    - https://tailwindui.com/templates/catalyst App UI参考
    - https://github.com/wdpm/catalyist-ui-kit 一个付费模版

  - base on web component
    - https://github.com/shoelace-style/shoelace 复杂度不高，可以阅读源码。挑一些有趣的组件研究。

  - based on React
    - https://pro.chakra-ui.com/ web常见UI blocks参考。

  - Vue
    - [Vue Material](https://vuematerial.io/)
    - iView UI
    - [PrimeVue](https://primevue.org/) 基于Vue的功能性组件库。质量很高，组件丰富，功能强大。
    - [naive-ui](https://www.naiveui.com/) 一个界面优雅的UI组件库，从文档来看作者是个有趣、自娱自乐的人。
    - [Muse-UI](http://www.muse-ui.org/)
    - [vuetify](https://vuetifyjs.com/)
    - https://reka-ui.com/docs/overview/introduction
    - https://www.shadcn-vue.com/docs/introduction.html not a library,just copy and paste
    - ~~element ui~~
    - ~~element ui - plus~~
    - ~~Quasar~~

- 前端实用库
  - 语义化版本发布
    - [semantic-release](https://github.com/semantic-release/semantic-release) 推荐。
    - [release-please]() 另一个选择

  - API 文档生成
    - [JSDoc](https://jsdoc.app/) - JSDoc 3 is an API documentation generator for JavaScript, similar to Javadoc or phpDocumentor
    - [TSDoc](https://tsdoc.org/) - TSDoc 是一项标准化 TypeScript 代码中使用的文档注释的提议。
    - [~~typedoc~~](https://typedoc.org/) - TypeDoc 是开箱即用的 TS 注释的文档生成工具。界面很一般。
  
  - 函数功能增强
    - lodash => underscore的后继者
    - ~~underscore~~
  - 视频播放器
    - [flv.js](https://github.com/bilibili/flv.js)           
    - [shaka-player](https://github.com/google/shaka-player) 
    - [DPlayer](https://github.com/DIYgod/DPlayer)  一个古老的 html5 视频播放器封装。可以读一下源码。                                              
  - JavaScript语法高亮
    - Prism.js
    - MathJax
    - highlight.js
  - 3D library
    - three.js
  - Animation
    - animation.css
    - hprogress 进度条
  - 图表
    - chart.js
    - echart.js
    - D3.js
    - [Historical-ranking-data-visualization-based-on-d3.js](https://github.com/Jannchie/Historical-ranking-data-visualization-based-on-d3.js)
    - wordcloud | https://www.jasondavies.com/wordcloud/
  - Rich text editor
    - Alex-D/Trumbowyg
    - summernote/summernote
  - Markdown editor
    - bh-lay  / mditor 
    - lepture  / editor
    - [sparksuite / simplemde-markdown-editor](https://github.com/sparksuite/simplemde-markdown-editor)
  - 其他
    - clipboard.js 复制粘贴
    - https://github.com/hakimel/reveal.js 在线幻灯片
    - Prettier 统一代码格式化
    - editorconfig | https://editorconfig.org/ 统一代码格式化

- 数据库建模
  - [dbdiagram.io](https://dbdiagram.io/) 
  - MySQL  Workbench
  - 数据库虚拟数据生成: https://www.mockaroo.com/

- API相关

  - Swagger
  - [YApi](https://github.com/ymfe/yapi) 是一个可本地部署的、打通前后端及QA的、可视化的接口管理平台。

- 日志管理

  - Java
    - Slf4j + Log4j2/Logback => Elasticsearch + Logstash + Kibana

- 权限管理

  - Java
    - Spring Security
    - Apache Shiro

- 项目构建

  - Java
    - Maven
    - Ant
    - Gradle

- HTTPS证书 

  - mkcert
  - https://letsencrypt.org/
  - [certbot](https://github.com/certbot/certbot) Certbot is EFF's tool to obtain certs from Let's Encrypt and (optionally)
auto-enable HTTPS on your server. It can also act as a client for any other CA that uses the ACME protocol.
    - [使用免费SSL证书让网站支持HTTPS访问](https://github.com/jaywcjlove/handbook/blob/master/docs/CentOS/使用免费SSL证书让网站支持HTTPS访问.md)

## 工具

- 代码质量检测
  - [codacy](https://www.codacy.com/pricing)
  - [codeclimate](https://codeclimate.com/)
  - [goreportcard](https://goreportcard.com/) - go 项目代码质量检测和评分。

- 笔记写作工具
  - [Typora](https://typora.io/#feature)
  - [obsidian](https://obsidian.md/) [中文帮助](https://publish.obsidian.md/help-zh)

- 本地化协作翻译平台
  - [poeditor](https://poeditor.com/)

- 文本编辑器
  - GUI
    - VS Code
    - Notepad ++

  - simple text editor
    - Nano
    - Vim
    - gedit

- Terminal & shell
  - Windows Terminal Preview + Oh my Posh
  - Bash + Oh my Zsh
  - Fish
  - Zsh

- API 调试
  - ~~postman~~ 
    - 特点：云账户同步，功能强大
    - 缺点：体积臃肿、启动缓慢、商业化严重
  - ~~bruno~~
    - 缺点：界面朴素、功能较少。
  - ~~insomina~~
    - 没有特别突出的优点和缺点
  - Hoppscotch
    - 特点：体积小巧、启动迅速、云账号同步、界面优雅。
    - 缺点：功能较少。
  
- 原型设计
  - AzureRP
  - Pencil Project
  - [FigmaChina](https://figmachina.com/)

- 虚拟机
  
  - VMWare Workstation
  - Virtual Box

- 画图

  - ProcessOn
  - Xmind  ZEN 
  - [draw.io](https://www.draw.io/) 

- 图片处理

  - 压缩  [TinyPNG](https://tinypng.com/)   
  - 无损放大 [bigjpg.com](https://bigjpg.com/)
  - 图片裁剪  [iloveimg.com](https://www.iloveimg.com/)

- 在线演示
  - [slidev](https://github.com/slidevjs/slidev) - 基于前端技术的演示幻灯片。
  - ~~slides.com~~  在线幻灯片，低代码GUI编辑，收费。
  - [carbon.now.sh](https://carbon.now.sh/) 代码高亮
  - Chalk.ist 官网 https://kutt.appinn.com/8a8RRs
  - https://asciinema.org/ Record and share your terminal sessions, the right way
  - 代码截图静态展示 https://carbon.now.sh/
  - terminal录制动态展示 terminalizer https://github.com/faressoft/terminalizer

- 数据库管理
  - Syslog
  
- 代码评审
  - https://kypso.io/

- API 查询
  - https://devdocs.io/

- 网站监控
  - UpTimeRobot https://uptimerobot.com/

- Web 假数据填充

  - [Lorem Ipsum](https://cn.lipsum.com/) 
  - [JSONPlaceholder](https://jsonplaceholder.typicode.com/)
  - http://placehold.it/150x150   

- Web 服务器压力测试

  - http_load 
  - webbench 
  - apache  ab 
  - [siege](http://www.joedog.org/)

- 不同语言生态下的工具库 utils

  - JavaScript 生态的 lodash、underscore、ramda、fp-ts、date-fns、day.js
  - Java 生态的 Hutool
  - Python 生态的 Pydash