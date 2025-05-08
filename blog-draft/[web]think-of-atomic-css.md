# 原子化 CSS 的思考

## 定义

> Atomic CSS is the approach to CSS architecture that favors small, single-purpose classes with names based on visual
> function.

## 发展

[Tailwind CSS](https://tailwindcss.com/) 是原子化 CSS的先驱之一。然而，它生成的 CSS 样式的数据量一般很大，这影响了dev模式下的开发体验。
虽然生成模式的构建会剪除没有使用的样式，但是无疑前面做了一些无用功。后来，Tailwind JIT 采用了预先扫描源代码的模式，试图改善这一问题。

[Windi CSS](https://cn.windicss.org/) 是从零开始编写的 Tailwind CSS 的替代方案，同样采用了预先扫描源代码的模式。它支持按需生成，不会一次生成所有的
CSS，而是只会生成代码中实际使用到的 CSS。

Tailwind CSS 一个明显的缺点是对于需要扩展的样式需要额外的配置。看下面这个例子：

```js
// tailwind.config.js
module.exports = {
    theme: {
        borderWidth: {
            DEFAULT: '1px',
            0: '0',
            2: '2px',
            3: '3px',
            4: '4px',
            6: '6px',
            8: '8px',
            10: '10px' // <-- here
        }
    }
}
```

## Uno CSS

Uno CSS试图在前辈们的基础上，更进一步。UnoCSS 是一个引擎，而非一款框架，它并未提供核心工具类，所有功能可以通过预设和内联配置提供。

### 动态规则

将这个

```js
rules: [
    ['m-1', {margin: '0.25rem'}]
]
```

缓存这个更加灵活的定义

```js
rules: [
    [/^m-(\d+)$/, ([, d]) => ({margin: `${d / 4}rem`})],
    [/^p-(\d+)$/, match => ({padding: `${match[1] / 4}rem`})],
]
```

### 可变修饰

下面这个示例演示了对class以及样式规则的修改：

```js
variants: [
    // 支持所有规则的 `hover:`
    {
        match: s => s.startsWith('hover:') ? s.slice(6) : null,
        selector: s => `${s}:hover`,
    },
    // 支持 `!` 前缀，使规则优先级更高
    {
        match: s => s.startsWith('!') ? s.slice(1) : null,
        rewrite: (entries) => {
            // 在所有 CSS 值中添加 ` !important`
            entries.forEach(e => e[1] += ' !important')
            return entries
        },
    }
]
```

### 属性化模式

将

```html

<button class="bg-blue-400 hover:bg-blue-500 text-sm text-white font-mono font-light py-2 px-4 rounded border-2 border-blue-200 dark:bg-blue-500 dark:hover:bg-blue-600">
    Button
</button>
```

变成

```html

<button
        bg="blue-400 hover:blue-500 dark:blue-500 dark:hover:blue-600"
        text="sm white"
        font="mono light"
        p="y-2 x-4"
        border="2 rounded blue-200"
>
    Button
</button>
```

### 纯 CSS 图标

示例

```html
<!-- A basic anchor icon from Phosphor icons -->
<div class="i-ph-anchor-simple-thin"/>
<!-- An orange alarm from Material Design Icons -->
<div class="i-mdi-alarm text-orange-400 hover:text-teal-400"/>
<!-- A large Vue logo -->
<div class="i-logos-vue text-3xl"/>
<!-- Sun in light mode, Moon in dark mode, from Carbon -->
<button class="i-carbon-sun dark:i-carbon-moon"/>
<!-- Twemoji of laugh, turns to tear on hovering -->
<div class="i-twemoji-grinning-face-with-smiling-eyes hover:i-twemoji-face-with-tears-of-joy"/>
```

这得益于 [Iconify](https://iconify.design/) 项目。

### CSS 作用域

[TailwindCSS Preflight](https://tailwindcss.com/docs/preflight) 的介绍：

> Built on top of [modern-normalize](https://github.com/sindresorhus/modern-normalize), Preflight is a set of base styles for Tailwind projects that are designed to smooth
> over cross-browser inconsistencies and make it easier for you to work within the constraints of your design system.

本质上还是 CSS Normalize/Reset那一套概念，目的在于改善了跨浏览器的样式一致性问题。

UnoCSS 采用了不支持预检的方案，将问题解决的主动权抛给上层框架，或者由用户自己把控。例如，如果使用 Vue 3，那就交给 Scoped CSS解决。

### 性能

UnoCSS 通过非常高效的字符串拼接来直接生成对应的 CSS 而非引入整个AST编译过程。

## references

- [重新构想原子化 CSS](https://antfu.me/posts/reimagine-atomic-css-zh) 