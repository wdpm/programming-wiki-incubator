# Vue 2.x 源码解析
> 本文为《深入浅出Vue.js》读书笔记

## 目录
- 变化侦测
  - Object变化侦测
  - Array变化侦测
  - 变化侦测相关API的实现原理
- 虚拟DOM
  - VNode
  - patch补丁算法
- 模板编译原理
  - 模板编译
  - 解析器
  - 优化器
  - 代码生成器
- 整体流程
  - Vue 2.x 架构设计
  - 实例方法与全局API实现原理
  - 生命周期
  - 指令
  - 过滤器
  - 最佳实践总结

## 变化侦测
### Object变化侦测
- 通过 Object.defineProperty 将属性转换成 getter/setter 形式来追踪变化。读取数据时会触发 getter，修改数据时会触发 setter。
- 在 getter 中收集有哪些依赖读取数据。当 setter 被触发时，通知 getter 中收集的依赖数据发生了变化。
- 收集依赖需要为依赖找一个存储依赖的地方，为此创建了 Dep，用来收集依赖、删除依赖和向依赖发送消息等。所谓依赖，就是 Watcher。

### Array变化侦测
- Array 追踪变化的方式和 Object 不一样。它是通过方法来改变内容，所以通过创建拦截器去覆盖数组原型的方式来追踪变化。
- Array 收集依赖的方式和 Object 一样，都在 getter 中收集。但由于使用依赖的位置不同，数组要在拦截器中向依赖发消息，
所以依赖不能像 Object 那样保存在 defineReactive 中，而是把依赖保存在 Observer 实例上。

### 变化侦测相关API的实现原理
- `vm.$watch( expOrFn, callback, [options] )`

  用于观察一个表达式或 computed 函数在 Vue.js 实例上的变化。vm.$watch 是对 Watcher 的一种封装。

- `vm.$set( target, key, value )`

  在 object 上设置一个属性。使用 defineReactive 将新增属性转换成 getter/setter的形式。

- `vm.$delete( target, key )`

  删除对象的属性。使用 delete target[key] 删除，使用 ob.dep.notify() 进行通知。

## 虚拟DOM
流程：
```
模板 -> 渲染函数 ->[VNode -> 真实DOM -> 视图]
```
运作原理：使用状态生成虚拟节点，然后使用虚拟节点渲染视图。

### VNode
VNode 可理解成节点描述对象，描述了怎样去创建真实的 DOM 节点。
```javascript
export default class VNode {
  constructor (tag, data, children, text, elm, context, componentOptions, asyncFactory) {
     //...
  }
}
```
不同类型的 vNode 表示不同类型的真实 DOM 元素。

### patch补丁算法
对比新旧两个虚拟 DOM，增量更新视图。

## 模板编译原理
### 模板编译
将模板编译成渲染函数有三步骤：
1. 将模板解析成 AST，=> 对应解析器
2. 遍历 AST 标记静态节点，=> 对应优化器
3. 使用 AST 生成代码字符串。=> 对应代码生成器

### 解析器
- 生成 AST 的过程需要 HTML 解析器，当 HTML 解析器触发不同的钩子函数时，可以构建出不同的节点。
- 可以通过栈来得到当前正在构建的节点的父节点，然后将构建出的节点添加到父节点。
- HTML 解析器内部原理是一小段一小段地截取模板字符串，每截取一小段字符串，会根据
截取出来的字符串类型触发不同的钩子函数，直到模板字符串截空停止运行。
  
### 优化器

优化器的作用是在 AST 中找出静态子树并打上标记，有两个好处：
- 每次重新渲染时，不需要为静态子树创建新节点；
- 在虚拟 DOM 中打补丁的过程可以跳过。

优化器的内部实现其实主要分为两个步骤：
- (1) 在 AST 中找出所有静态节点并打上标记；
- (2) 在 AST 中找出所有静态根节点并打上标记

### 代码生成器
通过递归 AST 来生成字符串，先生成根节点，然后在子节点字符串生成后，将其
拼接在根节点的参数中，子节点的子节点拼接在子节点的参数中，一层一层往内层拼接，直到最后。

## 整体流程
### Vue 2.x 架构设计
```
├── scripts # 与构建相关的脚本和配置文件
├── dist # 构建后的文件
├── flow # Flow 的类型声明
├── packages # vue-server-renderer 和 vue-template-compiler，它们作为单独的
│ NPM 包发布
├── test # 所有的测试代码
├── src # 源代码
│ ├── compiler # 与模板编译相关的代码
│ ├── core # 通用的、与平台无关的运行时代码
│ │ ├── observer # 实现变化侦测的代码
│ │ ├── vdom # 实现虚拟 DOM 的代码
│ │ ├── instance # Vue.js 实例的构造函数和原型方法
│ │ ├── global-api # 全局 API 的代码
│ │ └── components # 通用的抽象组件
│ ├── server # 与服务端渲染相关的代码
│ ├── platforms # 特定平台代码
│ ├── sfc # 单文件组件（* .vue 文件）解析逻辑
│ └── shared # 整个项目的公用工具代码
└── types # TypeScript 类型定义
└── test # 类型定义测试
```
大体上可以分三部分：核心代码、跨平台相关与公用工具函数。

### 实例方法与全局API实现原理
- 数据相关的实例方法

  vm.$watch、vm.$set 和 vm.$delete，是在 stateMixin 中挂载到 Vue 的原型上的，代码如下：
  ```
  import {
    set,
    del
  } from '../observer/index'
  
  export function stateMixin (Vue) {
    Vue.prototype.$set = set
    Vue.prototype.$delete = del
    Vue.prototype.$watch = function (expOrFn, cb, options) {}
  }
  ```
  
- 事件相关的实例方法
  
  vm.$on、vm.$once、vm.$off 和 vm.$emit。这 4 个方法是在 eventsMixin 中挂载到 Vue 构造函数的 prototype 属性中的。
  
  - vm.$on 关键在于往数组保存函数: (vm._events[event] || (vm._events[event] = [])).push(fn)
  - vm.$off 关键在于设置对应的event属性为null: vm._events[event] = null
  - vm.$once 关键在于先调用 vm.$off 进行移除，然后手动调用对应fn
  - vm.$emit 关键在于 cbs[i].apply(vm, args)
 
- 与生命周期相关的实例方法

  与生命周期相关的实例方法有 4 个，分别是 vm.$mount、vm.$forceUpdate、vm.$nextTick和 vm.$destroy。 其实
  vm.$mount、vm.$nextTick和 vm.$destroy是挂载到 Vue.prototype 上实现的。$forceUpdate 通过 vm._watcher.update() 实现。
  
- 全局 API 的实现原理

  ```
  Vue.extend
  Vue.nextTick => JS事件循环机制
  Vue.set
  Vue.delete
  Vue.directive
  Vue.filter
  Vue.component
  Vue.use
  Vue.mixin
  Vue.compile
  Vue.version
  ```
  
  这些方法是通过直接设置Vue的属性来实现，注意不是prototype。
  
### 生命周期

4个阶段
```
              初始化            模板编译            挂载                       卸载
new Vue() beforeCreate created          beforeMount     mounted   beforeDestory  destoryed
```
- 初始化阶段，分别初始化实例属性、事件、provide/inject 及状态等，其中状态包含 props、methods、data、computed 与 watch。
- 已挂载阶段会持续追踪状态的变化，当数据（状态）发生变化时，Watcher 会通知虚拟 DOM 重新渲染视图。
- 在渲染视图前触发 beforeUpdate 钩子函数，渲染完毕后触发 updated 钩子函数。

### 指令
#### 内置指令
- v-if

  ```html
  <li v-if="has">if</li>
  <li v-else>else</li>
  ```
  ```js
  (has)
    ? _c('li',[_v("if")])
    : _c('li',[_v("else")])
  ```
- v-for
  ```html
  <li v-for="(item, index) in list">v-for {{index}}</li>
  ```
  ```js
  _l((list), function (item, index) {
    return _c('li', [
      _v("v-for " + _s(index))
    ])
  })
  ```
- v-on

  本质是 node.addEventListener 或者 (_target || target).removeEventListener

#### 自定义指令
更新节点时，除了更新节点的内容外，还会触发update 钩子函数。
因为标签上通常会绑定一些指令、事件或属性，这些内容也需要在更新节点时同步被更新。
因此，事件、指令、属性等相关处理逻辑只需要监听钩子函数，在钩子函数触发时执行相关处理逻辑即可实现功能。

指令的处理逻辑分别监听了 create、update 与 destroy。

#### 虚拟DOM钩子函数
虚拟 DOM 在渲染时会触发的所有钩子函数：
init、create、activate、insert、prepatch、update、postpatch、destroy、remove

### 过滤器
例子：
```html
{{ message | capitalize | suffix }}
```
代码：
```vue
filters: {
  capitalize: function (value) {
    if (!value) return ''
    value = value.toString()
    return value.charAt(0).toUpperCase() + value.slice(1)
  },
  
  suffix: function (value, symbol = '~') {
    if (!value) return ''
    return value + symbol
  }
}
```
编译结果：
```js
_s(_f("suffix")(_f("capitalize")(message)))
```

过滤器常用于格式化文本。

过滤器原理：在编译阶段将过滤器编译成函数调用，串联的过滤器编译后是一个嵌套的函数调用，
前一个过滤器函数的执行结果是后一个过滤器函数的参数。

编译后的 _f 函数是 resolveFilter 函数的别名，resolveFilter 作用是找到对应的过滤器。

### 最佳实践总结
####最佳实践
- 为列表渲染设置属性 key（主键）；=> 加速节点查询性能
- 在 v-if/v-if-else/v-else 中使用 key；=> 避免意料之外的副作用
- 如何解决路由切换组件不变的问题；
  - 路由导航守卫 beforeRouteUpdate, 在此定义每次切换路由时需要执行的逻辑。（推荐）
  - 在组件watch中观察$route 对象的变化。（带来依赖追踪的内存开销）
  - 为 router-view 组件添加属性 key= $route.fullPath 强制刷新。（非常浪费性能但是很有效）
- 如何为所有路由统一添加 query；
  - 使用全局守卫 beforeEach。（每次切换路由会切换两次，不是最佳做法）
  - 函数劫持。拦截 router.history.transitionTo 方法，在 vue-router 内部在切换路由之前将参数添加到 query 中。（危险）
- 区分 Vuex 与 props 的使用边界
  - 业务组件，使用 Vuex 维护状态。适用于父子组件间通信以及兄弟组件间的通信。
  - 通用组件，使用 props 以及事件进行父子组件间的通信。
- 避免 v-if 和 v-for 一起使用
  - 考虑使用计算属性，例如 v-for="user in activeUsers" 重构
- 为组件样式设置作用域
  - CSS规则是全局的，组件样式之间可能会污染。
  - 可通过 scoped 特性或 CSS Modules（基于 class 的类似 BEM 的策略）来设置组件样式作用域
  ```
  <template>
    <button :class="[$style.button, $style.buttonClose]">X</button>
  </template>

  <!-- 使用 CSS Modules-->
  <style module>
    ...
  ```
- 避免在 scoped 中使用元素选择器
  - 为了给样式设置作用域，Vue.js 会为元素添加一个独一无二的特性，例如 data-v-f3f3eg9
- 避免隐性的父子组件通信
  - 理想的 Vue.js 应用是“prop 向下传递，事件向上传递”，这样代码容易维护。慎用this.$parent。

#### 风格规范
- 单文件组件如何命名；
  - 文件名：MyComponent.vue 或者 my-component.vue
  - 基础组件名：BaseXX.vue, AppXXX.vue, VButton.vue
  - 单例组件名：TheHeading.vue
  - 紧密耦合的组件名：TodoList.vue，TodoListItem.vue，TodoListItemButton.vue
  - 组件名中的单词顺序：名词+动词。例如 SearchButtonClear.vue，SearchButtonRun.vue
  - 组件名为多个单词：todo-item，避免使用单个词语todo
  - 模板中的组件名大小写：
    ```
    <!-- 在单文件组件和字符串模板中 -->
    <MyComponent/>
    <!-- 在 DOM 模板中 -->
    <my-component></my-component>
    ```
- 自闭合组件；
  ```
  <!-- 在单文件组件、字符串模板和 JSX 中 -->
  <MyComponent/>
  
  <!-- 在 DOM 模板中 -->
  <my-component></my-component>
  ```
- prop 名的大小写；
  ```
  props: {
    greetingText: String
  }
  <WelcomeMessage greeting-text="hi"/>
  ```
- 多个特性的元素拆行格式化；
  ```
  <MyComponent
  foo="a"
  bar="b"
  baz="c"
  />
  ```
- 模板中只使用简单的表达式，复杂表达式逻辑重构到计算属性；
- 简单的计算属性，复杂计算属性可以拆成多个简单计算属性的组合；
  ```
  computed: {
    basePrice: function () {
      return this.manufactureCost / (1 - this.profitMargin)
    },
  
    discount: function () {
      return this.basePrice * (this.discountPercent || 0)
    },
  
    finalPrice: function () {
      return this.basePrice - this.discount
    }
  }
  ```
- 指令缩写（:表示 v-bind:、@表示 v-on:）要保持统一；
- 良好的代码顺序。