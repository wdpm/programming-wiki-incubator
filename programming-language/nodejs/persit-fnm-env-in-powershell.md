## 在 PowerShell 中持久化 fnm 环境设置

访问 https://nodejs.org/en/download/package-manager, 跟随 Node 官网的推荐下载方式：
```bash
# installs fnm (Fast Node Manager)
winget install Schniz.fnm
# configure fnm environment
fnm env --use-on-cd | Out-String | Invoke-Expression
# download and install Node.js
fnm use --install-if-missing 20
# verifies the right Node.js version is in the environment
node -v # should print `v20.18.0`
# verifies the right npm version is in the environment
npm -v # should print `10.8.2`
```

其中这句 `fnm env --use-on-cd | Out-String | Invoke-Expression` 的环境设置是易挥发的，一旦关闭了目前这个 terminal，环境设置就重置了。

为了持久化这个 fnm env 的设置，需要将这行代码写入当前 terminal 的 PROFILE 文件中：

```bash
# print current profile path
$PROFILE

# edit or create profile
notepad $PROFILE

# append this line to the profile above
fnm env --use-on-cd | Out-String | Invoke-Expression
```

重启 terminal 并输入 `node -v` 即可验证是否有效。