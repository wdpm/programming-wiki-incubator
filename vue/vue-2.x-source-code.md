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
  
### 生命周期
### 指令
### 过滤器
### 最佳实践总结
