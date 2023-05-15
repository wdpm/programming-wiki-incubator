# github

## documentation

- [Github 官方文档](https://docs.github.com/zh) - 按需粗略阅读一次。以及当作工具书来查询参考。

## github actions

- [lychee-action](https://github.com/lycheeverse/lychee-action) - 检测 md 中的 link 是否可访问，用于一些 awesome-? 的聚合 project。
- [read-file](https://github.com/marketplace/actions/read-file) - 读取 repo 下某一个路径的文件内容，可以用于提取 metadata。
  例如：为 mad-center 创建一个 dashboard，列出所有项目当前的开发状态以及其他信息。

## 管理多个本地 repos

本地一般有很多个 git repos，如果能有一个工具，可以快速检测出所有 git 仓库的当前 state，并输出概要描述信息，
那将非常有帮助，这可以减少记忆的心智负担。

下面是一些调研得到的方案。

> https://myrepos.branchable.com/ 这个网址的 "related software" 章节提供了很多选择。

- [] [git-status-all](https://github.com/reednj/git-status-all) - 基于 Ruby，非常简洁。
- [] [gr](https://github.com/mixu/gr) - 基于 Node。
- [] [~~multi-git-status~~](https://github.com/fboender/multi-git-status)
    - 基本不处理 edge cases，输出信息不正确。不推荐。
- [] [git-plus](https://github.com/tkrajina/git-plus)
- [] [gita](https://github.com/nosarthur/gita)
- https://stackoverflow.com/a/52016506 - bash script，推荐。