# install vue-cli in windows10
``` bash
OS info: windows 10
Node: 8.0.0
NPM: 5.0.0
```

For example, install vue-cli 2.9.2 version.
``` bash
$ npm i -g vue-cli@2.9.2
$ vue init webpack my-project
```
## run in localhost
```bash
npm i
npm run dev
```
if you meet this error:
``` bash
ChromeDriver installation failed Error with http(s) request: Error: read ECONNRESET
```
You can use taobao cdn to fix this network problem:
```bash
npm install chromedriver --chromedriver_cdnurl=http://cdn.npm.taobao.org/dist/chromedriver
```