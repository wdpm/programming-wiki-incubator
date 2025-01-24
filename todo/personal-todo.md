# TODO

```
清理目标。

1.b 站收藏夹 -➡️ github 对应 mad 或者技术仓库，进行归档。

2. 电脑 D 盘。
Todo - 读书计划整理和归档。
Code/ - github 一切开源项目的整理。
```

## 导航页参考

- https://github.com/WebStackPage/WebStackPage.github.io 适用于 web links 的导航，不适用于本地软件
- https://github.com/Mereithhh/van-nav UI 设计简洁，适用于本地软件
- https://github.com/mengsixing/front-end-navigation web links 导航
- https://www.tominlab.com/wonderpen wonderpen 软件介绍页面

## Electron 应用参考

- [YourMusicStation](https://ymsv2.top/) v1 版本过旧，v2 版本并不开源，只能参考界面
- https://github.com/wdpm/electron-playground electron 9.x训练场，需要阅读app/build/playground目录的源码文件。

## 开源网站 UI 参考

- [一个 Bangumi 番组计划 web 客户端](https://github.com/Zebeqo/Bangumi) 基于现代 React +Next.js 技术栈

## 风格独特的网站

- [transmit](https://transmit.tailwindui.com/) 音乐分享界面，基于 tailwindcss，是付费魔板。但是自己可以复刻。
- [mikutap](https://aidn.jp/mikutap/) 一个 web 在线音游，键盘作为输入。
- [秘密花园](http://www.yini.org/club/garden.html) 一个 90 年代风格的旧网站，内容是一些诗集、怀旧歌曲、散文等。
- [出海去](https://chuhaiqu.club/) 一个黑黄为主体色调的网站。
- [A light in the woods](https://alightinthewoods.net/) 一个在森林地图漫游的探索网站，童话风格。

## linux

systemd learning

- 阅读：https://seb.jambor.dev/posts/systemd-by-example-part-1-minimization/
- 练习：https://systemd-by-example.com/

## 知识库/文档网站参考

- eagle https://docs-cn.eagle.cool/category/80-category
- biyi https://biyidev.com/download

## 工具

https://programming-idioms.org/about#about-block-cheatsheets 一个介绍编程语言语法的很好的总结网站
https://openalternative.co/ 开源替代
https://www.uicentral.fyi/ UI 框架选择对比

## tiny projects

- [] [github repos dashboard page]()：根据 github API 获取某个 user/org 下所有项目，可视化项目状态。
  ```
  研究这个模版样式，迁移到 mad-center 社区治理。
  列名（初步于拟定）：Name、stars/forks、最后更新时间 (last commit)，和维护状态。
  ```
  参考示例：
    - https://wangchujiang.com/
    - https://github.com/jaywcjlove

---

- [ ] 将 https://github.com/the-road-to-graphql/the-road-to-graphql 这个链接改成 vercel online ebook

  参考：
    - [the-road-to-react-online](https://the-road-to-react-online.vercel.app/manuscript/foreword.html)

---

- [ ] 探索最为简洁的轻量 md 渲染到页面的方案。主要应用于：organization introduction

    - jekyll：一本正经地使用传统的 jekyll 模式，似乎有点厚重。依次，同类技术例如 hexo、hugo 博客之类都不适合。
    - 文档知识库，例如 vitepress 这类也不合适。因为往往用不到这么多的页面。不过依旧厚重。
    - https://stackoverflow.com/questions/15214762/how-can-i-sync-documentation-with-github-pages main <-> gh-pages
      分支之间同步部分 md 文件，可行，但是依旧复杂。
    - zero-md：非常简约的方案。
        - https://github.com/zerodevx/zero-md
    - manally maintain a single index.html，文档内容也要在 html 中直接写，对于内容的编辑不方便，没有利用到 md 的优势。
    - https://github.com/susam/texme#render-markdown-and-latex 在 md 中为 latex 提供渲染支持。

---

- [ ] 后端管理系统 - 前端界面 https://github.com/xuejianxianzun/vue-shop 一个 vue2+element-ui 写的简易后台管理系统的前端部分。
    - 读一下这个项目的源码。稍微了解即可。

---

- [ ] CSS scroll animation
    - 【现在原生 CSS 可以实现滚动动画了】 https://www.bilibili.com/video/BV1xh4y1e7Un/

## blog template

### jekyll

- [mzlogin.github.io](https://github.com/mzlogin/mzlogin.github.io)
- [lxgw.github.io](https://github.com/lxgw/lxgw.github.io) - 这个是基于 mzlogin.github.io 修改而来。主题很简洁，可以收藏。

## 书籍或者教程

- https://github.com/rougier/numpy-100  => No.52
- http://learnwebgl.brown37.net/ => 5.3

## 探索项目源码

- [bee-ui](https://github.com/DWYW/bee-ui)

基于 Vue2.x，无 TS 语法支持，docs 文档系统使用 copy/paste 书写，实现简洁，支持覆盖默认主题 theme.less。

- [yuumi-ui-vue](https://github.com/DWYW/yuumi-ui-vue)

基于 Vue3.x，TS 语法，但是没有 TS 类型文件定义，docs 文档系统依旧是手动书写。

- [zLib-Web](https://github.com/Senkita/zLib-Web)

一个 zlib-searcher 的 Web UI。可以学习一下 react 18+ 的简单使用。

- [Bangumi](https://github.com/czy0729/Bangumi)

一个基于 React Native 的第三方 bangumi 客户端。

- [primevue](https://github.com/primefaces/primevue) 中使用 *.d.ts 来生成 options 参数文档。

- [CnGalWebSite.VueTrial](https://github.com/CnGal/CnGalWebSite.VueTrial)

- CnGal 前端网站的 Vue 3.x 实现。可以参考。

- WebGL 特效收藏
  ```
  https://oosmoxiecode.com/
  https://blog.oosmoxiecode.com/experiments/javascript-webgl/
  https://blog.oosmoxiecode.com/experiments/javascript-canvas/
  ```

## 前端 & 后端 &Node

- [搜集 React/Vue/Angular 组件库和后台管理平台模板](https://github.com/jaywcjlove/awesome-uikit) COLLECT
- [轻量高效的开源 JavaScript 插件和库★★★★★](https://github.com/jaywcjlove/handbook/blob/master/docs/JavaScript/SDK.md)
  COLLECT
- [使用 Node.js 制作命令行工具学习教程](https://github.com/jaywcjlove/wcj) READ

## 技术分享经验

- [Thoughtworks 洞见](https://insights.thoughtworks.cn/tag/featured/)
- https://tech.meituan.com 偶尔选读
- http://mysql.taobao.org/monthly/ 偶尔选读

## 图灵图书 (未读)

- 《机器学习实战》
- 《Unity 游戏设计与实现：南梦宫一线程序》
- 《通关！游戏设计之道（第 2 版）》
- 《图解服务器端网络架构》
- 《精通 Linux（第 2 版）》
- 《普思林顿微积分读本（修订版）》
- 《HTTPS 权威指南：在服务器和 Web...》
- 《统计学七支柱》
- 《深入 Java 虚拟机：JVM G1GC 的算法》
- 《深入理解 Python 特性》
- 《gRPC 与云原生应用开发》