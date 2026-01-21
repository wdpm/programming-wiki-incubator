# commitizen

## bump version by git tag v?.?.?
.cz.toml
```toml
[tool.commitizen]
name = "cz_conventional_commits"
version = "0.0.2"
tag_format = "v$version"
```
此时已经有一个git tag v0.0.2。实际的tag格式必须和tag_format定义的值匹配。

接着，提交一个`feat: XXX`的功能commit，然后执行：
```bash
cz bump
```
结构如下：
```bash
bump: version 0.0.2 → 0.1.0
tag to create: v0.1.0
increment detected: MINOR

[main 4296200] bump: version 0.0.2 → 0.1.0
 3 files changed, 3 insertions(+), 3 deletions(-)

pyupgrade................................................................Passed
black....................................................................Passed
flake8...................................................................Passed
pyupgrade............................................(no files to check)Skipped
black................................................(no files to check)Skipped
flake8...............................................(no files to check)Skipped
commitizen check.........................................................Passed

Done!
```
可以看到版本跳到了0.1.0，符合Semantic Version规范的定义。

查看.cz.toml
```toml
[tool.commitizen]
name = "cz_conventional_commits"
version = "0.1.0"
tag_format = "v$version"
version_files = [
    "ch09/first-python-package/src/imppkg/__version__.py",
    "ch09/first-python-package/setup.cfg:version"
]
```
- 发现version已经sync:
- version_files数组中所有文件也会保持同步。

后续的版本迭代就不需要git 的tag了，当然也可以保持git tag 和 cz version一致。

默认情况下，bump version 不会触发对changelog文件的更新。如果需要更新，可以：
```toml
update_changelog_on_bump = true
```

## generate changelog
```bash
cz ch
```

## 自定义type list
问题：`cz c`显示的列表type中，没有chore类型，而schema规范中显示有。我希望在下拉列表中可以选择chore或自定义类型。

参阅： https://commitizen-tools.github.io/commitizen/customization/