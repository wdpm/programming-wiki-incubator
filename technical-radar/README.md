# 技术观测

> Observe technology, then follow the evolution of technology.

技术永远在发展着，回顾以前的技术历史浮沉，可以窥探未来技术发展的趋势。



## 技术观测源

- [综合] 技术雷达：一年2更。

## 展示形式

- Web Page


## 数据处理

1. 将技术雷达的 pdf 适当删减为更加简洁的md文件。
2. Markdown即数据（Markdown as Data）。使用脚本语言将md文件处理为AST，然后导出为容易被静态文档生成器消费的文档格式，例如json文件。
   - [ ] https://github.com/phpusr/markdown-tree-parser
3. 选择一种简洁、关注点集中的静态文档生成器，生成网站，并托管于类似于Github Pages的托管服务。