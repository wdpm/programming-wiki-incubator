# 如何管理多个Node版本

## ~~nvm~~

不再推荐。不能跨平台。

## fnm

在windows中卸载的步骤

```
fnm env
```

他会给出类似 `C:\Users\[your-name]\AppData\Roaming\npm`的路径，删除此路径即可。
此时fnm就不会和volta冲突，系统就能正常让volta接管Node版本的管理。

## Volta

推荐。支持项目级别的独立Node版本，也可以设置全局Node默认版本。

## fnm vs Volta

Volta 的侵入性比较强，使用了一种 "shims" 方法，总是劫持你的命令并将其路由到实际的底层二进制文件，
而 nvm 和 fnm 则使用 PATH 方法(symlinks)，因此你总是直接到达底层二进制文件。