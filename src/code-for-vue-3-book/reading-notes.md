# 读书笔记

## chapter 1 权衡

声明式代码的性能不优于命令式代码的性能。因为声明式更新需要额外附加 diff 的步骤，而命令式代码不关心。
也因为如此，命令式代码往往无法利用 diff 来进行脏值检测，从而减少不必要的更新。

声明式代码注重结果，用户侧的代码可读性更好。这是代码可读性和框架性能之间的权衡。

因此，最小化 diff 算法的性能负担，是一个关键的优化点。

### 虚拟 DOM 和 innerHTML 在创建页面时的对比

|- | 虚拟 DOM|innerHTML|
|---|---|---|
| 纯 JS | 创建 VNode| 拼接要渲染的 HTML 字符串 |
|DOM 运算 | 新建 DOM 元素 | 新建 DOM 元素 |

差距其实在于纯 JS 这一行的对比，因为 DOM 运算这行是必须的，都一样。

### 虚拟 DOM 和 innerHTML 在 update 页面时的对比

|- | 虚拟 DOM|innerHTML|
|---|---|---|
| 纯 JS | diff 成本 + 创建新的 VNode| 拼接要渲染的 HTML 字符串 |
|DOM 运算 | 必要的 DOM 更新 | 删除所有旧 DOM+ 新建所有新 DOM |

- 虚拟 DOM 因为维护了数据状态，所以这里可操作性很大，例如 diff，利用缓存。
- 而命令式传统做法因为无状态，所以都是全量删除和再次新建。从这里看出，这种做法对于大项目似乎傻乎乎的。

可以得知，纯 JS 这一行，虚拟 DOM 技术代价更大，此时 innerHTML 取胜。
但是在 DOM 运算这行，虚拟 DOM 从理论上碾压 innerHTML，因为虚拟 DOM 是局部更新，而 innerHTML 是死板的全量更新。

这里本质还是对比：diff 的代价更高，还是全量 DOM 渲染的的代价高？

### 运行时和编译时

- 纯运行时

code1.html 就是纯运行时，利用树形结构作为数据。但是此时缺点很明显，不能书写类似 HTML 模版的语法。

- 运行时 + 编译时

如果需要 HTML Template 的语法，又需要保留运行时，那就是运行时 + 编译时，这里编译指的是 HTML Template => 树形结构。

举例：

从
```html
<div>
    <span>hello world</span>
</div>
```
到
```js
const obj = {
  tag: 'div',
  children: [
    {tag: 'span', children: 'hello world'}
  ]
}
```
然后，编译模版的示例代码。
```js
const html = `
<div>
<span>hello world</span>
</div>
`
// 调用 Compiler 编译得到树型结构的数据对象
const obj = Compiler(html)
// 再调用 Render 进行渲染
Render(obj, document.body)
```
这是运行时再编译，而不是提前编译。当然，也可以考虑提前编译。这里的编译一般是指 HTML 模板编译。

- 纯编译时

上面的例子中，其实也可以编译成纯编译时的 js 代码。
```html
<div>
    <span>hello world</span>
</div>
```
=>
```js
const div= document.createElement('div')
const span= document.createElement('span')
span.innerText ='hello world'
div.appendchild(span)
document.body.appendchild(div)
```
此时，再也没有 VNode 的中间表示。性能最佳，回到了最初的命令式代码。纯编译时的代表框架是 Svelte。

## chapter 2 框架设计的考虑点

- 利用 console.warn 给予用户足够友好的开发提示。
- 在开发环境中为用户提供友好的警告信息的同时，不会增加生产环境代码的体积。利用 __DEV__ 变量和 Tree-Shaking 技术。
- 利用 `/*#__PURE__*/` 标签显示声明无副作用的函数调用。也就是纯函数。利于 tree-shaking 优化。
- 不同的构建配置需求，不同的构建产物。
- 特性开关，本质是一个 bool 值，控制是否打包某个属性到最终 prod 代码中。
- 优雅的错误处理。
    ```js
    // utils.js
    let handleError = null
    export default {
        foo(fn) {
          callWithErrorHandling(fn)
        },
        // 用户可以调用该函数注册统一的错误处理函数
        registerErrorHandler(fn) {
          handleError = fn
        }
    }
    function callWithErrorHandling(fn) {
        try {
            fn && fn()
        } catch (e) {
            // 将捕获到的错误传递给用户的错误处理程序
            handleError(e)
        }
    }
    ```
- typescript, 支持良好的类型提示。

## chapter 3 Vue3.x 的设计思路
- 类似 HTML 代码，来指定一种声明式 UI 的 API，例如点击事件，@click。
- 此外，还需要一种 JS 结构，来表示 VDOM。本质就是一个 js 对象，含义是上面声明式 UI 的翻译。

渲染器的 demo：创建节点，参考 course3-Vue3 的设计思路 /code1.html。

组件是什么？组件的实现一般是一个函数，调用执行后，能够返回一个虚拟 DOM。代码参考:course3-Vue3 的设计思路 /code2.html。
```js
const MyComponent = function () {
  return {
    tag: 'div',
    props: {
      onClick: ()=> alert('hello')
    },
    children: 'click me'
  }
}
```

当然，组件的定义也可以是一个对象，里面含有一个 render 方法。
```js
// MyComponent 是一个对象
const MyComponent = {
    render() {
        return {
            tag: 'div',
            props: {
              onClick: ()=> alert('hello')
            },
            children: 'click me'
        }
    }
}
```

此时，需要调整背后的渲染实现。

---
- 组件的实现依赖于渲染器，模板的编译依赖于编译器。
- 利用 flags 标志位来给予动态增量更新具体的提示。
```js
render() {
  return {
      tag: 'div',
      props: {
          id: 'foo',
          class: cls
      },
      patchFlags: 1 // 假设数字 1 代表 class 是动态的
      }
  }
```

## chapter 4 响应系统

响应式问题描述：
```js
const obj = {text: 'hello world'}
function effect() {
    // effect 函数的执行会读取 obj.text
    document.body.innerText = obj.text
}
```
当 obj.text 变更时，我们希望 effect 这个副作用函数也能感知到，同步变更。

我们希望找到一种方式，来获取某个 obj 属性的 get/set 调用。我们首先可以将副作用函数存储到“桶”中，集中管理。
当感知到 obj 的 set 操作 (有新值) 时，遍历这个桶的函数，然后播放变更即可。那么问题核心就是如何感知 obj 的 get/set 了。

以前，Vue2.0+ 使用的是 ES2015 的 Object.defineProperty()，现在 Vue3.x+ 使用了更加现代化的 Proxy 语法。

一个简单的例子：course4- 响应系统 /1. 响应系统的作用与实现 /code2.html。

其实响应式的关键在于：
- 当读取 GET 操作发生时，将副作用函数收集到“桶”中；
- 当设置操作 SET 发生时，从“桶”中取出副作用函数并执行。

这里涉及到了三方关系。target,property,effectFn。是一个关联的树层级结构。
> 问题的复现：code3.html
```
target1
   - property1
       - effectFn1
```
也就是这样一个数据结构来对应：WeakMap(target,Map(key, set()))

这里最外层使用了 WeakMap，而不是通常的 Map。这是在考虑内存 GC 和泄漏的前端安全问题。
一句话：对于 weakmap，如果 key 设置为 null 或者被回收内存，那么对于这个 item（不论 key 和对应的 value）都不能被访问。

code6.html 是一个较好的 demo。

---
上面的都是相对 obj.text 一个属性而言的，但是假如遇到三元运算符，那就可能涉及多个属性的访问了。
```js
effect(function effectFn() {
    document.body.innerText = obj.ok ? obj.text : 'not'
})
```
这里涉及了.ok 和.text 的读取。分情况讨论：
1. 如果 obj.ok 为 true，那么会继续读取 obj.text 的值。
2. 如果 obj.ok 为 false，那么不关心取 obj.text 的值，因为此时结果是 'not' 固定值。

如果一开始是 case 1, 然后是 case 2。那么此时不应该再触发不必要的更新。因为此时 obj.text 已经不关心了。可事实还是触发了。

为了解决这个问题，需要重新设计 effect 依赖收集问题。目标是保证每次设置 obj.ok 的值后，effect 依赖集合都是最小而必要的。

解决这个问题的思路：
- 每次副作用函数执行时，先把它从所有与之关联的依赖集合中删除。
- 当副作用函数执行完毕后，会重新建立联系，但在新的联系中不会包含遗留的副作用函数。

---

- 需要让 effect 支持嵌套层级的调用。
- 以及支持自增 ++ 操作，同时不能死循坏。

---
可调度，指的是当 trigger 动作触发副作用函数重新执行时，有能力决定副作用函数执行的时机、次数以及方式。

````js
effect(
    () => {
      console.log(obj.foo)
    },
    // options
    {
        // 调度器 scheduler 是一个函数
        scheduler(fn) {
        // ...
        }
    }
)
````


---
计算属性 computed 与 lazy.

---

watch 函数的实现，本质是 effect+scheduler。
```js
effect(() => {
    console.log(obj.foo)
}, {
    scheduler() {
    // 当 obj.foo 的值变化时，会执行 scheduler 调度函数
    }
})
```
scheduler 类似于回调函数，可以灵活延迟实际 fn 的执行。

---

副作用可排序，处理副作用过期的问题。

Vue.js 为 watch 的回调函数设计了第三个参数，即 onInvalidate。它是一个函数，用来注册过期回调。每当 watch 的
回调函数执行之前，会优先执行用户通过 onInvalidate 注册的过期回调。
** 这样，用户就有机会在过期回调中将上一次的副作用标记为“过期”，从而解决竞态问题。**

## chapter 5 非原始值的响应式方案

Proxy 的局限性：
- 只能代理对象，不能代理基本类型值。
- Proxy 只能够拦截对一个对象的基本操作。例如 obj.fn() 是无法代理的，因为这里涉及两个动作，读取 obj.fn 函数定义，然后调用。

---

合理地触发响应，假如某个 key 对应的值没有变化时，那就不应该触发更新，没有必要。

这里就是脏值检测。只有当值变脏之后，才触发更新。

---

深响应和浅响应。

- 深响应的实现：对 Reflect.get 返回的结果做一层包装，保证结果一律都是响应式。
- 浅响应的实现：使用一个参数 boolean 作为开关。

---

只读和浅只读。code10.html

---

代理数组。

代理 set 和 map 。

当使用 for...in 遍历对象时，我们只关心对象的键是否变化，而不关心值；但使用 forEach 遍历集合时，我们既关心键的变化，也关心值的变化。


----

## chapter 6 原始值的响应式方案

Vue 中 ref 的设计由来。

由于 Proxy 的代理目标必须是非原始值，所以我们没有任何手段拦截对原始值的操作，所以就想到将原始值包装为响应式对象。

由于“包裹对象”本质上与普通对象没有任何区别，因此为了区分 ref 与普通响应式对象，我们还为“包裹对象”定义了一个值为 true 的属性，即
__v_isRef，用它作为 ref 的标识。

ref 还能处理响应丢失问题，响应丢失是 obj 属性解构赋值语法引起的。

同时包裹对象带来了.value 的 API 侵入心智负担。为了解决这个问题，引用了 ref 自动拆包的机制。也是通过 proxy 内部实现作判断，然后处理。

## chapter 7 渲染器的设计

patch 函数：
```js
function patch(oldNode, newNode, container) {

}
```
```js
// 首次渲染 => create, oldNode =null, newNode =vnode1
renderer.render(vnode1, document.querySelector('#app'))
// 第二次渲染 => update, oldNode =vnode1, newNode =vnode2, 这里就需要复杂的 diff 设计了
renderer.render(vnode2, document.querySelector('#app'))
// 第三次渲染 => delete, oldNode =vnode2, newNode =null
renderer.render(null, document.querySelector('#app'))
```

VNode 这个抽象的中间层，表示平台无关的数据状态 1，为跨端提供了可能。

## chapter 8 挂载与更新

HTML Attributes 和 DOM Properties 的区别。
- class="foo" 对应的 DOM Properties 则是 el.className。另外，并不是所有 HTML Attributes 都有与之对应的 DOM Properties。
- 关键：HTML Attributes 的作用是设置与之对应的 DOM Properties 的初始值。

无论是使用 setAttribute 函数，还是直接设置元素的 DOM Properties，都存在缺陷。要彻底解决这个问题，只能做
特殊处理，即优先设置元素的 DOM Properties，但当值为空字符串时，要手动将值矫正为 true。

回顾一下，如何正确地把 vnode.props 中定义的属性设置到 DOM 元素上：
- 通过设置 el.property
- 通过设置 HTML attributes

vue 2 的不支持模本有多个 root 节点，被 vue3 的 Fragment 能力所解决。

## 简单 diff

- 找到可复用节点，patch 内容后，移动节点。
- 新增节点。新增节点的位置的参考依据是新 DOM 的位置顺序。
- 删除节点

Vue 中 key 属性的作用：就像虚拟节点的“身份证号”。在更新时，渲染器通过 key 属性找到可复用的节点，
然后尽可能地通过 DOM 移动操作来完成更新，避免过多地对 DOM 元素进行销毁和重建。

简单 Diff 算法的核心逻辑是，拿新的一组子节点中的节点 ** 去旧的一组子节点中寻找 ** 可复用的节点。
- 如果找到了，则记录该节点的位置索引。我们把这个位置索引称为最大索引。
- 在整个更新过程中，如果一个节点的索引值小于最大索引，则说明该节点对应的真实 DOM 元素需要移动。

## 双端 Diff

简单 Diff 算法的问题在于，它对 DOM 的移动操作并不是最优的。判断依据是 DOM 的移动次数，越少越好。

双端 diff 图解：类似一个沙漏的图案，一共 4 次比较。两个横线，两个斜线。
1. <-> 顶部
2. <-> 底部
3. 左上角 - 右下角
4. 右上角 - 左下角

双端 Diff 算法指的是，在新旧两组子节点的四个端点之间分别进行比较。

## 快速 diff

思路借鉴于文本diff。查找出公共的前缀和后缀部分，然后直接排除它们。剩下的就是差异部分。

1. 它先处理新旧两组子节点中相同的前置节点和相同的后置节点。
2. 当前置节点和后置节点全部处理完毕后，如果无法简单地通过挂载新节点或者卸载已经不存在的节点来完成更新，则需要根据节点的索引
关系，构造出一个最长递增子序列。
3. 最长递增子序列所指向的节点即为不需要移动的节点。

也就是它的思想还是尽可能减少移动DOM的次数。非常精准的diff。

## chapter 12 组件的实现原理

mountComponent() 函数内可以执行生命周期函数
- created
- beforeMount/mounted
- beforeUpdate/updated

---

由于可能存在多个同样的组件生命周期钩子，例如来自 mixins 中的生命周期钩子函数，通常需要将组件生命周期钩子序列化为一个数组。

在 Vue.js 3 中，没有定义在 MyComponent.props 选项中的props 数据将存储到 attrs 对象中。

每当在渲染函数或生命周期钩子中通过 this 来读取数据时，都会优先从组件的自身状态中读取，如果组件本身并没有对应的数据，
则再从 props 数据中读取。最后我们将渲染上下文作为渲染函数以及生命周期钩子的 this 值即可。
实际上，除了组件自身的数据以及 props 数据之外，完整的组件还包含 methods、computed 等选项中定义的数据和方法，这些内容
都应该在渲染上下文对象中处理。

---

Vue3.x setup()

setup 函数的第一个参数取得外部为组件传递的 props 数据对象。同时，setup 函数还接收第
二个参数 setupContext 对象，其中保存着与组件接口相关的数据和方法，如下所示。
- emit：一个函数，用来发射自定义事件。
- slots：组件接收到的插槽。
- attrs：当为组件传递 props 时，那些没有显式地声明为 props 的属性会存储到attrs 对象中。
- expose：一个函数，用来显式地对外暴露组件数据。在本书编写时，与 expose 相关的 API 设计仍然在讨论中，详情可以查看具体的 RFC 内容。

setup 函数的返回值可以是两种类型，如果返回函数，则将该函数作
为组件的渲染函数；如果返回数据对象，则将该对象暴露到渲染上下文中。

渲染上下文 renderContext 中 一次检测state -> props -> setupState 的属性

```js
set (t, k, v, r) {
    const { state, props } = t
    if (state && k in state) {
      state[k] = v
    } else if (k in props) {
        console.warn(`Attempting to mutate prop "${k}". Props
         readonly.`)
    } else if (setupState && k in setupState) {
        // 渲染上下文需要增加对 setupState 的支持
        setupState[k] = v
    } else {
      console.error('不存在')
    }
}
```

--- 

emit

当 emit 函数被调用时，根据约定对事件名称进行转换，以便能够在 props 数据对象中找到对应的事件处理函数。
最后，调用事件处理函数并透传参数即可。

emit 函数包含在 setupContext 对象中，可以通过 emit 函数发射组件的自定义事件。通过 v-on 指令为组件绑定的事件在经过编
译后，会以 onXxx 的形式存储到 props 对象中。当 emit 函数执行时，会在 props 对象中寻找对应的事件处理函数并执行它。

---

slots

插槽内容会被编译为插槽函数，插槽函数的返回值就是向槽位填充的内容。
<slot> 标签则会被编译为插槽函数的调用，通过执行对应的插槽函数，得到外部向槽位填充的内容（即虚拟DOM），最后将该内容渲染到槽位中。

插槽渲染函数例子：
```js
// 父组件的渲染函数
function render() {
    return {
        type: MyComponent,
        // 组件的 children 会被编译成一个对象
        children: {
            header() {
              return { type: 'h1', children: '我是标题' }
            },
            body() {
              return { type: 'section', children: '我是内容' }
            },
            footer() {
              return { type: 'p', children: '我是注脚' }
            }
        }
    }
}
```

插槽函数的调用：
```js
// MyComponent 组件模板的编译结果
function render() {
    return [
        {
            type: 'header',
            children: [this.$slots.header()]
        },
        {
            type: 'body',
            ldren: [this.$slots.body()]
        },
        {
            type: 'footer',
            children: [this.$slots.footer()]
        }
    ]
}
```

slots底层实现。`slots = vnode.children` 是关键。
```js
function mountComponent(vnode, container, anchor) {
// 省略部分代码

// 直接使用编译好的 vnode.children 对象作为 slots 对象即可
const slots = vnode.children || {}

// 将 slots 对象添加到 setupContext 中
const setupContext = { attrs, emit, slots }
}
```

--- 

注册生命周期

在 A 组件的 setup 函数中调用 onMounted 函数会将该钩子函数注册
到 A 组件上；而在 B 组件的 setup 函数中调用 onMounted 函数会将
钩子函数注册到 B 组件上，这是如何实现的呢？实际上，我们需要维
护一个变量 currentInstance。

```js
// 全局变量，存储当前正在被初始化的组件实例
let currentInstance = null
// 该方法接收组件实例作为参数，并将该实例设置为 currentInstance
function setCurrentInstance(instance) {
currentInstance = instance
}
```

```js
const instance ={
  // 定义一个数组来保存多个mounted生命周期函数
  mounted: [] 
}

// setup
const setupContext = { attrs, emit, slots }
// 在调用 setup 函数之前，设置当前组件实例
setCurrentInstance(instance)
// 执行 setup 函数
const setupResult = setup(shallowReadonly(instance.props), setupContext)
// 在 setup 函数执行完毕之后，重置当前组件实例
setCurrentInstance(null)
```

API 使用时，本质就是数组添加元素。
```js
function onMounted(fn) {
    if (currentInstance) {
        // 将生命周期函数添加到 instance.mounted 数组中
        currentInstance.mounted.push(fn)
    } else {
        console.error('onMounted 函数只能在 setup 中调用')
    }
}
```

底层mounted的执行。
```js
function mountComponent(vnode, container, anchor) {
    // 省略部分代码
    effect(() => {
        const subTree = render.call(renderContext, renderContext)
        if (!instance.isMounted) {
        // 省略部分代码
        // 遍历 instance.mounted 数组并逐个执行即可
        instance.mounted && instance.mounted.forEach(hook =>
        hook.call(renderContext))
        } else {
        // 省略部分代码
        }
        instance.subTree = subTree
        }, {
        scheduler: queueJob
    })
}
```

关键：需要在组件实例对象上添加 instance.mounted 数组。

## chapter 13 异步组件与函数式组件

异步组件设计考虑：

- 允许用户指定加载出错时要渲染的组件；
- 允许用户指定 Loading 组件，以及展示该组件的延迟时间；
- 允许用户设置加载组件的超时时长；
- 组件加载失败时，为用户提供重试的能力

Loading组件的考虑：为 Loading组件设置一个延迟展示的时间。例如，当超过 200ms 没有完成加载，
才展示 Loading 组件。这样，对于在 200ms 内能够完成加载的情况来说，就避免了闪烁问题的出现。

## chapter 14 内建组件和模块

KeepAlive 组件、Teleport 组件、Transition 组件。

KeepAlive 组件的实现需要渲染器层面的支持。因为被KeepAlive 的组件在卸载时，不能真的将其卸载，否则就无法维持
组件的当前状态了。正确的做法是，将被 KeepAlive 的组件从原容器搬运到另外一个隐藏的容器中，实现“假卸载”。
当被搬运到隐藏容器中的组件需要再次被“挂载”时，我们也不能执行真正的挂载逻辑，而应
该把该组件从隐藏容器中再搬运到原容器。这个过程对应到组件的生命周期，其实就是 activated 和 deactivated。

- match strategy 匹配策略
- cache size
- cache strategy 最新一次访问。

---

Teleport：跨越 DOM 层级完成渲染

主要用于解决 overlay 全局蒙层问题。

本质上就根据to属性，将元素挂载在body下。

---

Transition 过渡

核心原理：
- 当 DOM 元素被挂载时，将动效附加到该 DOM 元素上
- 当 DOM 元素被卸载时，不要立即卸载 DOM 元素，而是等到附加到该 DOM 元素上的动效执行完成后再卸载它。

```js
// 创建 class 为 box 的 DOM 元素
const el = document.createElement('div')
el.classList.add('box')

// 在 DOM 元素被添加到页面之前，将初始状态和运动过程定义到元素上
el.classList.add('enter-from') // 初始状态
el.classList.add('enter-active') // 运动过程

// 将元素添加到页面
document.body.appendChild(el)
```

动画实现就是：将 enter-from 类从 DOM 元素上移除，并将 enter-to这个类添加到 DOM 元素上。


```
            beforeEnter                   enter 
创建 DOM  -----------------> 挂载 DOM --------------> 
```

beforeEnter 阶段：添加 enter-from 和 enter-active类。
enter 阶段：在下一帧中移除 enter-from 类，添加 enter-to。
进场动效结束：移除 enter-to 和 enter-active 类。

Transition 组件的实现原理与为原生 DOM 添加过渡效果的原理类似，我们将过渡相关的钩子函数定义到虚拟节点的 vnode.transition 对象中。

## chapter 15 编译器核心技术概览
Vue.js 的模板和 JSX 都属于领域特定语言，它们的实现难度属于中、低级别。

```js
const templateAST = parse(template)
const jsAST = transform(templateAST)
const code = generate(jsAST)
```

例子：
```
<p>Vue</p>
```

1. 状态机始于“初始状态 1”。
2. 在“初始状态 1”下，读取模板的第一个字符 <，状态机会进入下一个状态，即“标签开始状态 2”。
3. 在“标签开始状态 2”下，读取下一个字符 p。由于字符 p 是字母，所以状态机会进入“标签名称状态 3”。
4. 在“标签名称状态 3”下，读取下一个字符 >，此时状态机会从“标签名称状态 3”迁移回“初始状态 1”，并记录在“标签名称状态”下产
生的标签名称 p。
5. 在“初始状态 1”下，读取下一个字符 V，此时状态机会进入“文本状态 4”。
6. 在“文本状态 4”下，继续读取后续字符，直到遇到字符 < 时，状态机会再次进入“标签开始状态 2”，并记录在“文本状态 4”下产生的
文本内容，即字符串“Vue”
7. 在“标签开始状态 2”下，读取下一个字符 /，状态机会进入“结束标签状态 5”。
8. 在“结束标签状态 5”下，读取下一个字符 p，状态机会进入“结束标签名称状态 6”。
9. 在“结束标签名称状态 6”下，读取最后一个字符 >，它是结束标签的闭合字符，于是状态机迁移回“初始状态 1”，
并记录在“结束标签名称状态 6”下生成的结束标签名称。

---

Context => 本质是一个全局变量，表示环境
 
- 在编写 React 应用时，我们可以使用 React.createContext 函
 数创建一个上下文对象，该上下文对象允许我们将数据通过组件
 树一层层地传递下去。无论组件树的层级有多深，只要组件在这
 棵组件树的层级内，那么它就能够访问上下文对象中的数据。
- 在编写 Vue.js 应用时，我们也可以通过 provide/inject 等能
 力，向一整棵组件树提供数据。这些数据可以称为上下文。
- 在编写 Koa 应用时，中间件函数接收的 context 参数也是一种上
 下文对象，所有中间件都可以通过 context 来访问相同的数据。

## chapter 16 解析器

这一章说明HTML代码的解析过程。

递归下降算法

- parseChildren 解析函数是整个状态机的核心，状态迁移操作都在该函数内完成。
- 在parseChildren 函数运行过程中，为了处理标签节点，会调用parseElement 解析函数，这会间接地调用 parseChildren 函数，
并产生一个新的状态机。
- 随着标签嵌套层次的增加，新的状态机会随着 parseChildren 函数被递归地调用而不断创建，这就是“递归下降”中“递归”二字的含义。
- 上级 parseChildren 函数的调用用于构造上级模板 AST 节点，被递归调用的下级 parseChildren 函数则用于构造下级模板 AST 节点。
- 最终，会构造出一棵树型结构的模板AST，这就是“递归下降”中“下降”二字的含义。

## chapter 17 编译优化

编译优化指的是编译器将模板编译为渲染函数的过程中，尽可能
多地提取关键信息，并以此指导生成最优代码的过程。

> 传统 Diff 算法的弊端

传统 Diff 算法无法避免新旧虚拟 DOM 树间无用的比较操作，是因为它在运行时得不到足够的关键信息，从而无法区分动态
内容和静态内容。换句话说，只要运行时能够区分动态内容和静态内容，即可实现极致的优化策略。

例子：
```html
<div>
    <div>foo</div>
    <p>{{ bar }}</p>
</div>
```
- foo 是static content
- bar 是dynamic content

传统VDOM
```js
const vnode = {
    tag: 'div',
    children: [
        { tag: 'div', children: 'foo' },
        { tag: 'p', children: ctx.bar },
    ]
}
```

优化后的VDOM：
```js
const vnode = {
    tag: 'div',
    children: [
        { tag: 'div', children: 'foo' },
        { tag: 'p', children: ctx.bar, patchFlag: 1 }, // 这是动态节点
    ]
}
```
patchFlag 的1值是int类型，代表着不同的标记类型。

```js
const vnode = {
    tag: 'div',
    children: [
        { tag: 'div', children: 'foo' },
        { tag: 'p', children: ctx.bar, patchFlag: PatchFlags.TEXT }// 这是动态节点
    ],
    // 将 children 中的动态节点提取到 dynamicChildren 数组中
    dynamicChildren: [
        // p 标签具有 patchFlag 属性，因此它是动态节点
        { tag: 'p', children: ctx.bar, patchFlag: PatchFlags.TEXT }
    ]
}
```

当渲染器在更新一个 Block 时，会忽略虚拟节点的 children 数组，而是直接找到该虚拟节点的 dynamicChildren 数
组，并只更新该数组中的动态节点。这样，在更新时就实现了跳过静态内容，只更新动态内容。

既然 Block 的好处这么多，那么什么情况下需要将一个普通的虚拟节点变成 Block 节点呢？实际上，当我们在编写模板代码的时候，
所有模板的根节点都会是一个 Block 节点，如下面的代码所示。

---

Block 树：
- v-if
- v-for
- Fragment

---

静态提升：props或者子节点的提升

当把纯静态的节点提升到渲染函数之外后，在渲染函数内只会持有对静态节点的引用。当响应式数据变化，并使得渲染函
数重新执行时，并不会重新创建静态的虚拟节点，从而避免了额外的性能开销。

---
预字符串化：

```js
const hoistStatic = createStaticVNode('<p></p><p></p><p>
</p>...20 个...<p></p>')
```

---

缓存内联事件处理函数

```js
<Comp @change="a + b" />

function render(ctx, cache) {
    return h(Comp, {
    // 将内联事件处理函数缓存到 cache 数组中
    onChange: cache[0] || (cache[0] = ($event) => (ctx.a +ctx.b))
    })
}
```

---

v-once

Vue.js 3 不仅会缓存内联事件处理函数，配合 v-once 还可实现对虚拟 DOM 的缓存。

```js
render(ctx, cache) {
    return (openBlock(), createBlock('div', null, [
        cache[1] || (
        setBlockTracking(-1), // 阻止这段 VNode 被 Block 收集
        cache[1] = h("div", null, ctx.foo, 1 /* TEXT */),
        setBlockTracking(1), // 恢复
        cache[1] // 整个表达式的值
    )
    ]))
}
```

- 避免组件更新时重新创建虚拟 DOM 带来的性能开销。因为虚拟DOM 被缓存了，所以更新时无须重新创建。
- 避免无用的 Diff 开销。这是因为被 v-once 标记的虚拟 DOM 树不会被父级 Block 节点收集。

---

小结：

Vue.js 3 的编译器会充分分析模板，提取关键信息并将其附着到对应的虚拟节点上。在运行时阶段，渲染器通过这些
关键信息执行“快捷路径”，从而提升性能。

## chapter 18 同构渲染

同构渲染分为首次渲染（即首次访问或刷新页面）以及非首次渲染。

同构渲染中的首次渲染与 SSR 的工作流程是一致的。当首次访问或者刷新页面时，整个页面的内容是在服务端完
成渲染的，浏览器最终得到的是渲染好的 HTML 页面。

假设浏览器已经接收到初次渲染的静态 HTML 页面，接下来浏览
器会解析并渲染该页面。在解析过程中，浏览器会发现 HTML 代码中
存在 <link> 和 <script> 标签，于是会从 CDN 或服务器获取相应
的资源，这一步与 CSR 一致。当 JavaScript 资源加载完毕后，会进行
激活操作，这里的激活就是我们在 Vue.js 中常说的 “hydration”。激活
包含两部分工作内容：

- Vue.js 在当前页面已经渲染的 DOM 元素以及 Vue.js 组件所渲染的
  虚拟 DOM 之间建立联系
- Vue.js 从 HTML 页面中提取由服务端序列化后发送过来的数据，
  用以初始化整个 Vue.js 应用程序
  
服务器端渲染时，props/data的数据不需要是响应式的，也不许render effect的设置，因为没有浏览器环境。

由于组件在服务端渲染时，不需要渲染真实 DOM 元素，所以无须创建并执行 render effect。这意味
着，组件的 beforeMount 以及 mounted 钩子不会被触发。而且，由于服务端渲染不存在数据变更后的重新渲染逻辑，所以
beforeUpdate 和 updated 钩子也不会在服务端执行。

由于浏览器在渲染了由服务端发送过来的 HTML 字符串之后，页面中已经存
在对应的 DOM 元素了，所以组件代码在客户端运行时，不需要再次创建相应的 DOM 元素。但是，组件代码在客户端运行时，仍然需要做两
件重要的事：
- 在页面中的 DOM 元素与虚拟节点对象之间建立联系；
- 为页面中的 DOM 元素添加事件绑定。

注意事项：条件引入依赖

```js
<script>
    let storage
    // 只有在非 SSR 下才引入 ./storage.js 模块
    if (!import.meta.env.SSR) {
        storage = import('./storage.js')
    }else{
        // 用于服务端
        storage = import('./storage-server.js')
    }
    export default {
      // ...
    }
</script>
```
---

独立实例 per reqeust

```js
import { createSSRApp } from 'vue'
import { renderToString } from '@vue/server-renderer'
import App from 'App.vue'

// 每个请求到来，都会执行一次 render 函数
async function render(url, manifest) {
    // 为当前请求创建应用实例
    const app = createSSRApp(App)
    const ctx = {}
    const html = await renderToString(app, ctx)
    return html
}
```

---

ClientOnly 组件包裹

```js
import { ref, onMounted, defineComponent } from 'vue'

export const ClientOnly = defineComponent({
    setup(_, { slots }) {
        // 标记变量，仅在客户端渲染时为 true
        const show = ref(false)
        // onMounted 钩子只会在客户端执行
        onMounted(() => {
          show.value = true
        })
        // 在服务端什么都不渲染，在客户端才会渲染 <ClientOnly> 组件的插槽内容
        return () => (show.value && slots.default ? slots.default(): null)
    }
})
```
---

小结：

在服务端渲染组件与渲染普通标签并没有本质区别。只需要通过执行组件的
render 函数，得到该组件所渲染的 subTree 并将其渲染为 HTML 字符串。