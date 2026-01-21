# Exercises for Publishing Python Packages: Test, share, and automate your projects 🐍 📦 ⬆️

[![Documentation Status](https://readthedocs.org/projects/publishing-python-packages/badge/?version=latest)](https://publishing-python-packages.readthedocs.io/en/latest/?badge=latest)

Fork of 《Publishing Python Packages》book.

## 笔记

目录说明：

- .github
  - [x] issue template
  - [x] pr template
  - [x] contributing guideline
  - [x] workflow files
  - [x] dependabot.yml
- ch05

  初具模型，基础的 tox 的使用
- ch06

  重构解耦代码，模块化，SOLID 原则。增加 typecheck(mypy)、lint(flake8,[ruff](https://github.com/charliermarsh/ruff) )、format(black) 、测试
  hello 配置。
- ch08/
  - 增加 sphinx 文档构建配置。默认主题 alabaster 非常简陋。** 添加自定义 sphinx-rtd-theme 主题后，注意在 cfg 文件中添加对应依赖 **。
  - 托管到 read the docs 网站。注册 read the docs 账号，可以使用 github 互联。
    1. 保证 project 根目录下存在正确配置的.readthedocs.yaml
    2. import 特定的 github project；
    3. 填写元信息，勾选 advanced setting。
    4. 填写 Project Extra Details。
  - Running sphinx-apidoc on Read the Docs。配置对应 conf.py，利用 build doc 的生命周期的 builder-inited
  - update per pull-request:
    - visit https://readthedocs.org/dashboard/publishing-python-packages/edit/
    - click `Admin`,click `advanced setting`.
    - select `Build pull requests for this project`, then save.
    - push a change(must be pr, not push) to this repo to check if works as expected.
    - 配置文档查阅：https://docs.readthedocs.io/en/stable/pull-requests.html
    - 构造测试 pr 的步骤：在 main 分支创建一个 pr，拉取 pr 到本地，在本地做一些变更，例如更改文档。push 到 github，发起对 main 的合并 pr， 此时 pull request 页面就会触发
      readthedocs 的文档构建预览。可以点击查看效果。
  - sphinx 出来 rst 格式，也可以通过扩展支持 markdown 格式。也可以考虑 mkdocs
  - doctest（文档驱动的测试）/pytest（用例驱动的测试）
- ch09/
  - 关于依赖库版本和自身版本。
    - 不固定特定版本: package-a==1.0.0。
    - 限定上下界版本范围: package-a>=1.0.0,<2。
    - 限定下界: package-a>=1.0.0。
    - 不限范围: package-a。
  - 依赖库就是在寻找合适的交集。有时，会陷入依赖 diamond 地狱。[参阅](https://livebook.manning.com/book/publishing-python-packages/chapter-9/v-10)
  - 限制下界的意义：某个 package 在某个版本才引入某个你需要的功能，因此需要指定下界。
  - 限制上界的意义：一般是基于兼容性考虑，版本过高的库可能 API 大改，会破坏你已有的代码库。
  - 利用 github 的代码扫描服务（Code security and analysis）。
    - Dependabot alerts surface known security vulnerabilities in some dependency manifest files.
    - Dependabot security updates automatically keep your application up-to-date by updating dependencies in response to
      these alerts.
    - Dependabot version updates can also help keep dependencies updated.
    - Code scanning: scan your project code for vulnerabilities as well.
    - Secret scanning: scans your code for potentially leaked passwords, API keys, and so on to protect you from
      attackers who scrape and use the information.
- use [pre-commit](https://pre-commit.com/) hook in .pre-commit-config.yaml
  - pre-commit 一般是作用于 working --(`git add`)--> staged --(pre-commit 位于这里)--> commit 这个阶段。 如果文件变更还没有 staged（还没有 git
    add），那么 pre-commit 会先：
    - Stashing unstaged files.
    - do tasks.
    - Restore changes.

    如果文件更改都已经 git add，那么pre-commit不会执行git add，而是直接开始检测。 也就是说pre-commit检测的是working和staged
    阶段的代码，这就是pre-commit的含义，保证了进入commit阶段的变更，都是经过检测的。
  - pyupgrade for python syntax up to date
  - install: `pip install pre-commit`
  - setup git hooks: `pre-commit install` => .git\hooks\pre-commit
  - manually run all pre-commit hooks on a repository: `pre-commit run --all-files`
  - temporarily disabling hooks: `SKIP=[hook-id] prep-commit run`
  - run single hook: `pre-commit run [hook-id]`
  - uninstall pre-commit: docs: https://pre-commit.com/#pre-commit-uninstall
- git command:
  - git revert XXX :

    假设当前提交为意外的提交，想要撤销，可以使用 revert，但是会新增一个 commit 记录（表示撤销）。 审查需要保留撤销记录时可以使用， 其余时刻不建议使用。
  - git reset --soft vs --mixed vs --hard: https://stackoverflow.com/a/3528483

- 让 pre-commit 通过：
  - 当 pre-commit 定义的检测范围的文件格式混乱，此时 pre-commit 或者 git commit + run hooks（例如使用 IDE 勾选框 ) 都会无限失败。
  - 使用 tox 任务执行格式化，然后重试 commit，直到通过。
  - 这里会遇到 pre-commit 中例如 black 和 flake8 的配置问题。
  - flake8: `args: ['--config=ch09/first-python-package/setup.cfg', '--ignore=E303']` 可以读取配置。
  - black:
    ```bash
    black....................................................................Failed
    - hook id: black
    - files were modified by this hook
    
    reformatted ch08\first-python-package\docs\conf.py
    
    All done! \u2728 \U0001f370 \u2728
    1 file reformatted.
    
    [WARNING] Stashed changes conflicted with hook auto-fixes... Rolling back fixes...
    ```
    - 问题分析：black8 指定了 ch09 的配置，但是却检测了 ch08 的代码。
    - 参阅 [这个问题](https://stackoverflow.com/a/74046827) ，可以手动 git add 出现冲突的文件，之后再次检测。
    - 或者指定 black 排除的检测范围：在 pre-commit 中 1. 使用 exclude 排除部分文件夹。2. 使用 files 指定文件夹。

- ch10 的项目模板生成不实用，暂缓。

## alternatives

- dev env
  - [ ] virtualenv 时代的眼泪
  - [x] venv python 3.x 官方支持，推荐。

- build system backend:
  - [x] setuptools => 这个工具缺乏对自定义脚本的支持，但是对C扩展编译支持很成熟。
  - [ ] hatch => 支持定义脚本。目前对cython extensions的编译支持不成熟：https://github.com/pypa/hatch/issues/279。
  - [ ] flit
  - [ ] poetry => 流行度也很高。对标 hatch。
    - config example: https://github.com/commitizen-tools/commitizen/blob/master/pyproject.toml

- multi environments config:
  - [x] tox => 侧重于测试。较旧，兼容hatch后端，可以迁移。
  - [ ] hatch => 侧重于构建。和hatch backend完美兼容。

> migrate setuptools to hatch, migrate tox to hatch.
> - ~~https://hatch.pypa.io/latest/blog/2022/10/08/hatch-v160/#migration-script-improvements~~ (这个工具不靠谱，一堆报错)
> - https://hatch.pypa.io/latest/meta/faq/#tool-migration

## other enhancements

- [x] .editorconfig for general files formatting.
- [ ] markdown file linter：前置条件：迁移到hatch。hatch支持自定义scripts。
  - https://github.com/mkdocs/mkdocs/blob/c576f07d30e7f1e20ee2292c94dab3b585d9006c/pyproject.toml#L181
  - https://github.com/DavidAnson/markdownlint
- [x] commitizen in python ecosystem. 前端构建系统很喜欢使用这个类似的工具来管理 commit msg。
  - https://github.com/commitizen-tools/commitizen
  - install: `pip install -U commitizen`
  - init config: `cz init`
  - install hooks: `pre-commit install --hook-type commit-msg --hook-type pre-push`
  - test commit: `cz c`
  - temporarily check message: `cz check --message "chore: something"`
  - 现在，提交模式变为: `cz c` ，或者也可以使用之前的提交方式 `git commit -m "MSG"`，不需要理会 cz hooks.
  - 有关更多配置，参阅该项目 docs/ 。
- [ ] ~~local run github action through https://github.com/nektos/act~~
  - git clone actions 总是失败，而且无法正常设置git config的代理。
