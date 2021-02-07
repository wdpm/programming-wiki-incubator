# web learning path

这里给出了web开发必须了解的知识点，不会深入描述，仅会给出阅读链接。

## HTML
块级元素和行内元素
- https://developer.mozilla.org/en-US/docs/Web/HTML/Inline_elements
- https://developer.mozilla.org/en-US/docs/Web/HTML/Block-level_elements

Web Storage API
- https://developer.mozilla.org/zh-CN/docs/Web/API/Web_Storage_API

Cookie
- https://developer.mozilla.org/zh-TW/docs/Web/HTTP/Cookies
- https://developer.mozilla.org/zh-CN/docs/Web/API/Document/cookie

## CSS fundamental
层叠与继承
- https://developer.mozilla.org/zh-CN/docs/Learn/CSS/Building_blocks/Cascade_and_inheritance

CSS 基础盒模型介绍
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Box_Model/Introduction_to_the_CSS_box_model

布局和包含块
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/All_About_The_Containing_Block

块格式化上下文
- https://developer.mozilla.org/zh-CN/docs/Web/Guide/CSS/Block_formatting_context

font-size
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/font-size

line-height
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/line-height

CSS selectors
- https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors

display
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/display

position
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/position

float
- https://developer.mozilla.org/zh-CN/docs/CSS/float
- https://developer.mozilla.org/zh-CN/docs/Learn/CSS/CSS_layout/Floats

max-width
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/max-width

使用 CSS 弹性盒子
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Flexible_Box_Layout/Using_CSS_flexible_boxes
- https://cssreference.io/flexbox/

Grid layout 理论
- https://developer.mozilla.org/zh-CN/docs/Glossary/Grid_Lines
- https://cssreference.io/css-grid/

Grid layout 实践
- 利用CSS网格布局实现常用布局|https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Grid_Layout/Realizing_common_layouts_using_CSS_Grid_Layout

animation
- https://developer.mozilla.org/zh-CN/docs/Web/CSS/animation

CSS 3D Transform
- https://3dtransforms.desandro.com/

## CSS 模块化
OOCSS
- https://github.com/stubbornella/oocss/wiki

SMACSS
- smacss.com

BEM(√)
- en.bem.info/methodology

ITCSS
- https://www.creativebloq.com/web-design/manage-large-css-projects-itcss-101517528

inline styles or CSS in JS(√)
- 不使用class命名约定。
- 使用JS产生全局唯一的class名字，或者直接使用HTML style属性来应用CSS样式。

CSS module(√)
- 生成唯一ID后缀，来标记特定的样式。

## JavaScript
原始数据
- https://developer.mozilla.org/en-US/docs/Glossary/Primitive

JavaScript data types and data structures
- https://developer.mozilla.org/en-US/docs/Web/JavaScript/Data_structures

this
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Operators/this

Hoisting（变量提升）
- https://developer.mozilla.org/zh-CN/docs/Glossary/Hoisting

function
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Statements/function

闭包
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Closures

Prototype
- 对象原型|https://developer.mozilla.org/zh-CN/docs/Learn/JavaScript/Objects/Object_prototypes
- 继承与原型链|https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Inheritance_and_the_prototype_chain

Array
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Array

String
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/String

async和await:让异步编程更简单
- https://developer.mozilla.org/en-US/docs/Learn/JavaScript/Asynchronous/Async_await

对象浅复制和深复制
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Object/assign

正则表达式
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Guide/Regular_Expressions
- regex101 | https://regex101.com/
- regexper | https://regexper.com/

事件介绍
- https://developer.mozilla.org/zh-CN/docs/Learn/JavaScript/Building_blocks/Events

并发模型和事件循环
- 可视化|http://latentflip.com/loupe/
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/EventLoop

常用 Web API
- DOM | https://developer.mozilla.org/zh-CN/docs/Web/API/Document_Object_Model
- Navigator | https://developer.mozilla.org/zh-CN/docs/Web/API/Navigator
- History | https://developer.mozilla.org/zh-CN/docs/Web/API/History
- Window | https://developer.mozilla.org/zh-CN/docs/Web/API/Window
- Location | https://developer.mozilla.org/zh-CN/docs/Web/API/Location

高级技巧
- 函数柯里化（减少原函数参数数量）
- 记忆 memoization（缓存中间计算结果）：仅适用于确定性算法
- 惰性求值(call-by-need)
   > https://github.com/luobotang/simply-lazy/blob/4e5bac33886b85ec18955a755ae795a7a03148e8/index.js#L20
- 数组变异: 保存原型链方法引用，重新定义方法，覆盖原型链原方法。
- 函数变异：使用变量保存原函数引用，重新定义原函数，在新定义的函数中调用原函数。

Console（用于调试）
- https://developer.mozilla.org/zh-TW/docs/Web/API/Console

Web 安全
- XSS
- CSRF

Web 性能
- 缓存
  - 客户端缓存：service worker
  - 服务器缓存：通过 HTTP 报文协议控制
- 资源预加载
  - DNS-prefetch
  - resource prefetch
- 资源懒加载
  - 图片懒加载
- 文件压缩：代码压缩，网页资源压缩
- 保证JS不阻塞页面渲染，合理使用script标签的async（异步下载，下载完就执行）和defer(等待DOM渲染完成）属性
- 适当使用 SVG 或者 icon font
- 使用 srcset 提供浏览器更多的图片源选择，使用src作为fallback
- 背景图片使用 media-query 提供不同尺寸的下载源

## HTTP
HTTP 响应代码
- https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status

HTTP 消息
- https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Messages

典型的 HTTP 会话
- https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Session

HTTP 请求方法
- https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Methods

## Trends For 2020
- AI/Chatbots(Python,Tensorflow)
- Interactive Design/Motion UI
- Progress Web Apps (PWA)
- Responsive Web Design
- Mobile App Development
  - React Native
  - NativeScript
  - Ionic
- Desktop App Development(Electron)
- GraphQL
- TypeScript
