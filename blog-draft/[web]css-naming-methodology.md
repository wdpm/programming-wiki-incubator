# CSS 模块方法论

由于早期的CSS只有全局一个作用域，为了方便工程化开发和维护，于是催生了许多命名方法论。

## [OOCSS](https://github.com/stubbornella/oocss/wiki) 2009

例如： `.btn` 提供按钮的基本结构，`.grey-btn` 应用颜色和其他视觉效果。
它不推荐使用后代选择器，缺点是可能会产生大量的类。

## [BEM](en.bem.info/methodology) 2010

BEM 的核心思想是将具有不同角色的 CSS 类进行区分，例如：

```
.block
.block--modifier
.block__element
.block__element--modifier
```

BEM 命名约定有助于 CSS 作者遵循 OOCSS 原则，即使用由同等精确的类选择器组成的扁平选择器层次结构。它还有助于 OOCSS
作者避免使用深层后代选择器。
缺点是，类名也可能会变得很长。

## [SMACSS](smacss.com) 2011

将CSS划分为五个部分，base、layout、modules、state、themes，提供了比BEM更加简单的命名约定。它同样不鼓励使用后代选择器。

[SUITCSS](https://suitcss.github.io/) 2014
结合了类似 BEM 的类命名系统与 CSS 预处理器。提供了类似 Sass、Less 或 Stylus 的扩展 CSS 语法。CSS 类有五种格式：

- u-utilityName
- ComponentName
- ComponentName--modifierName
- ComponentName-elementName
- ComponentName.is-stateOfName

示例：

```
.MyComponent {}
.MyComponent.is-animating {}
.MyComponent--modifier {}

.MyComponent-part {}
.MyComponent-anotherPart {}
```

## [CSS in JS](https://cssinjs.org/) 2014

- [x] 不使用class命名约定。
- [x] 使用JS产生全局唯一的class名字，或者直接使用HTML style属性来应用CSS样式。

2014-2016，社区涌现了多种解决方案，主要分为两类：

- 运行时生成样式（如 Radium、Aphrodite）：在 JavaScript 运行时动态生成 CSS 并插入到 DOM。
- 编译时处理（如 CSS Modules）：通过构建工具（如 Webpack）在编译阶段生成唯一类名，实现样式隔离。

2017-2019，随着 React 生态的繁荣，两个库逐渐成为主流：

- styled-components（2016年发布）：
    - 采用模板字符串语法，支持完整的 CSS 特性（如伪类、媒体查询）。
    - 提供主题（Theme）支持，便于动态换肤。
- Emotion（2017年发布）：
    - 支持字符串和对象样式写法。
    - 提供更灵活的运行时和静态提取优化。

这一时期，CSS-in-JS 的核心优势得到认可：

- 组件化样式：样式与组件强绑定，避免全局污染。
- 动态样式：可通过 JavaScript 逻辑动态调整样式。
- 开发体验优化：支持自动前缀、主题管理等。

CSS-in-JS 的兴起源于前端组件化的需求，它通过 JavaScript 的动态能力弥补了传统 CSS 的不足，但也带来了性能和维护挑战。

- 性能问题：运行时样式计算可能拖慢首屏渲染。
- 可维护性：样式与逻辑耦合，可能增加代码复杂度。

对此，社区还在探索使用编译时优化、原子化CSS（例如Tailwind CSS）等方案来解决问题。
