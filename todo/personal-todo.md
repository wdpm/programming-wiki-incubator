# TODO

```
清理目标。

1.b站收藏夹 -➡️ github对应mad或者技术仓库，进行归档。

2.电脑D盘。
AEProjects - AE相关教程整理，清理，归档。
Todo - 读书计划整理和归档。
Code/ - github一切开源项目的整理。
```


## tiny projects

- [ ] [github repos dashboard page]()：根据github API获取某个user/org下所有项目，可视化项目状态。
  ```
  研究这个模版样式，迁移到mad-center 社区治理。
  列名（初步于拟定）：Name、stars/forks、最后更新时间(last commit)，和维护状态。
  ```
  参考示例：
  - https://wangchujiang.com/
  - https://github.com/jaywcjlove
---
- [ ] 将 https://github.com/the-road-to-graphql/the-road-to-graphql 这个链接改成vercel online ebook
  
  参考：
  - [the-road-to-react-online](https://the-road-to-react-online.vercel.app/manuscript/foreword.html)
---
- [ ] 探索最为简洁的轻量md渲染到页面的方案。主要应用于：organization introduction

  - jekyll：一本正经地使用传统的jekyll模式，似乎有点厚重。依次，同类技术例如hexo、hugo博客之类都不适合。
  - 文档知识库，例如vitepress这类也不合适。因为往往用不到这么多的页面。不过依旧厚重。
  - https://stackoverflow.com/questions/15214762/how-can-i-sync-documentation-with-github-pages main <-> gh-pages 分支之间同步部分md文件，可行，但是依旧复杂。
  - zero-md：非常简约的方案。
    - https://github.com/zerodevx/zero-md 
    - https://zerodevx.github.io/zero-md/attributes-and-helpers/
  - manally maintain a single index.html，文档内容也要在html中直接写，对于内容的编辑不方便，没有利用到md的优势。
  - https://github.com/susam/texme#render-markdown-and-latex 在md中为latex 提供渲染支持。

---
- [ ] 后端管理系统-前端界面 https://github.com/xuejianxianzun/vue-shop 一个vue2+element-ui写的简易后台管理系统的前端部分。
  - 读一下这个项目的源码。稍微了解即可。

## blog template

### jekyll
- [mzlogin.github.io](https://github.com/mzlogin/mzlogin.github.io)
- [lxgw.github.io](https://github.com/lxgw/lxgw.github.io) - 这个是基于 mzlogin.github.io 修改而来。主题很简洁，可以收藏。

## 书籍或者教程

- https://github.com/rougier/numpy-100  => No.52
- http://learnwebgl.brown37.net/ => 5.3

## 探索项目源码

- [bee-ui](https://github.com/DWYW/bee-ui)

基于Vue2.x，无TS语法支持，docs文档系统使用copy/paste书写，实现简洁，支持覆盖默认主题theme.less。

- [yuumi-ui-vue](https://github.com/DWYW/yuumi-ui-vue)
 
基于Vue3.x，TS语法，但是没有TS类型文件定义，docs文档系统依旧是手动书写。

- [zLib-Web](https://github.com/Senkita/zLib-Web)

一个zlib-searcher的 Web UI。可以学习一下react 18+的简单使用。

- [Bangumi](https://github.com/czy0729/Bangumi)

一个基于React Native的第三方bangumi客户端。

- [primevue](https://github.com/primefaces/primevue) 中使用*.d.ts 来生成options参数文档。

- [CnGalWebSite.VueTrial](https://github.com/CnGal/CnGalWebSite.VueTrial)

- CnGal 前端网站的Vue 3.x 实现。可以参考。

- WebGL 特效收藏
  ```
  https://oosmoxiecode.com/
  https://blog.oosmoxiecode.com/experiments/javascript-webgl/
  https://blog.oosmoxiecode.com/experiments/javascript-canvas/
  ```


## 前端&后端&Node

- [搜集React/Vue/Angular组件库和后台管理平台模板](https://github.com/jaywcjlove/awesome-uikit) COLLECT
- [轻量高效的开源JavaScript插件和库★★★★★](https://github.com/jaywcjlove/handbook/blob/master/docs/JavaScript/SDK.md) COLLECT
- [使用Node.js制作命令行工具学习教程](https://github.com/jaywcjlove/wcj) READ

## 技术分享经验

- [Thoughtworks洞见](https://insights.thoughtworks.cn/tag/featured/)

## 图灵图书(未读)
- 《机器学习实战》
- 《Unity游戏设计与实现：南梦宫一线程序》
- 《通关！游戏设计之道（第2版）》
- 《图解服务器端网络架构》
- 《精通Linux（第2版）》
- 《普思林顿微积分读本（修订版）》
- 《HTTPS权威指南：在服务器和Web...》
- 《统计学七支柱》
- 《深入Java虚拟机：JVM G1GC的算法》
- 《深入理解Python特性》
- 《gRPC与云原生应用开发》