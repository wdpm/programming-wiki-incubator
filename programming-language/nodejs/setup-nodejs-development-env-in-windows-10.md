# setup nodejs development env in windows10

## use nvm manager
visit https://github.com/coreybutler/nvm-windows/releases and download latest version

install specific node version
``` bash
# install node 8.0.0 version
$ nvm install 8.0.0

# check installation
$ node -v
v8.0.0

$ npm -v
5.0.0
```

config taobao registry[optional]
```bash
npm config set registry https://registry.npm.taobao.org
```

You can clean npm cache by this:
``` bash
npm cache clean --force
```

## use Volta
in windows os:
```bash
winget install Volta.Volta
```
install a specified node version:
```bash
volta install node@22
```