# nuxtjs-demo

## project directory structure
```
| nuxt.config.js //Nuxt.js 应用的个性化配置。
├─assets //资源目录 assets 用于组织未编译的静态资源如 LESS、SASS 或 JavaScript
├─components //组件目录 components 用于组织应用的 Vue.js 组件。这些组件不会有 asyncData 方法特性。
├─layouts //布局目录 layouts 用于组织应用的布局组件。
├─middleware //middleware 目录用于存放应用的中间件。
├─pages //页面目录 pages 用于组织应用的路由及视图。Nuxt.js 框架读取该目录下所有的 .vue 文件并自动生成对应的路由配置。
├─plugins //插件目录 plugins 用于组织那些需要在 根vue.js应用 实例化之前需要运行的 Javascript 插件。
├─static //静态文件目录 static 用于存放应用的静态文件，不会被 Webpack 进行编译处理。 该目录下的文件会映射至应用的根路径 / 下。
└─store //store 目录用于组织应用的 Vuex 文件。 在 store 目录下创建一个 index.js 文件可激活配置。
``` 
## Build Setup

``` bash
# install dependencies
$ npm run install

# serve with hot reload at localhost:3000
$ npm run dev

# build for production and launch server
$ npm run build
$ npm run start

# generate static project
$ npm run generate
```

For detailed explanation on how things work, checkout [Nuxt.js docs](https://nuxtjs.org).
