# setup nodejs development env in windows10
## install nvm-windows
visit https://github.com/coreybutler/nvm-windows/releases and download latest version

## install specific node version
``` bash
# install node 8.0.0 version
$ nvm install 8.0.0

# check installation
$ node -v
v8.0.0

$ npm -v
5.0.0
```

## config taobao registry[optional]
```bash
npm config set registry https://registry.npm.taobao.org
```