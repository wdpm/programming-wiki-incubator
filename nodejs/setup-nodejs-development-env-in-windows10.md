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

## install node package
For example, install vue-cli 2.9.2 version.
``` bash
$ npm i -g vue-cli@2.9.2

$ vue init webpack my-project
```