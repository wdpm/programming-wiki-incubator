# chakra-ui 研究

chakra-ui 底层实现依赖于 zig.js 和 ark-ui。其中 zig.js 基于状态机的概念。而 ark-ui 是一个 headless 的 UI 设计系统，用于
跨框架实现 UI 组件库。

- 使用 `as` 属性指示标签替换，使用 `asChild` 将组件的功能组合到其子元素上。背后实现是 `React.forwardRef`.
- 使用 data-state = "open" 以及 _open 这类属性定义简易的 CSS 动画。
- 依赖 https://github.com/pacocoursey/next-themes 来实现 light/dark mode
- Chakra UI components are client components because they rely on useState, useRef and useState

它具有四个方面的组件，分别是：

- 布局 layout
- 文字排版 typography
- 组件 components
- 工具 utilities

## 布局

### styled component[internal]

chakra.div 是 Chakra UI 基于 @emotion/styled 封装的一个样式化 div 元素。

简化的伪代码实现

```tsx
import {styled} from '@emotion/styled';
import {theme} from '@chakra-ui/react';

const chakraDiv = styled.div(
    props => ({
        // 1. 注入主题相关的样式
        ...useChakraStyles(props),
        // 2. 处理用户传入的样式 props
        ...parseStyleProps(props),
        // 3. 支持响应式值
        ...responsiveStyles(props),
    })
);

```

核心功能：

- 主题集成：自动接入 Chakra UI 的主题（如颜色、间距、断点等）
- 样式增强：支持所有 Chakra 的样式 props（如 bg="blue.500"、p={4}）
- 响应式语法：支持类似 {{ base: 'value', md: 'value' }} 的响应式写法
- 伪类 / 伪元素：通过 _hover、_before 等语法支持

### aspect ratio

Used to embed responsive videos and maps, etc

一般用于包装 video，image，map 元素，保持该元素的显示比例，避免诡异的画面拉伸。

```tsx
const AspectRatio = forwardRef((props, ref) => {
    const {ratio = 4 / 3, children, ...rest} = props

    return (
        <chakra.div
            ref={ref}
            position="relative"
            _before={{
                height: 0,
                content: `""`,
                display: "block",
                paddingBottom: `${(1 / ratio) * 100}%`,
            }}
            __css={{
                "& > *": {
                    position: "absolute",
                    top: 0,
                    right: 0,
                    bottom: 0,
                    left: 0,
                    width: "100%",
                    height: "100%",
                    overflow: "hidden",
                },
            }}
            {...rest}
        >
            {children}
        </chakra.div>
    )
})
```

- `_before` 使用 ::before 伪元素创建 padding 空间，其中 paddingBottom 的属性值计算是比例的关键。
- `& > *` 表示子元素会被绝对定位以填充整个容器

API 设计:

Props

- ratio 仅有一个比例属性即可。这个组件并没有事件逻辑。

### Bleed

Used to break an element from the boundaries of its container

Bleed 组件是 Chakra UI 中用于让子元素"溢出"父容器边界的实用组件：

核心功能

- 允许子元素突破父容器的内边距 (padding) 约束
- 通过负边距 (margin) 实现视觉上的"出血"效果
- 支持控制四个方向的溢出量

其中第二点负 margin 是实现的关键：

1. 获取父容器的 padding 或 spacing 值
2. 将这些值转换为负值
3. 应用到子元素的 margin 上

例如：

```tsx
<Box p={4}>
    <Bleed block="4">
        <Image src="example.jpg"/>
    </Bleed>
</Box>
```

因为负边距抵消了父容器的 padding，视觉效果是图片会向上和向下各超出父容器 1rem（假设主题中 4 = 1rem）

### Box

一个抽象的样式容器，称之为盒子。这样的概念在许多前端库中都有出现。 它作为所有组件的样式基础 ，为其他 Chakra 组件提供底层样式系统支持。
此外， 它替代原生 div 实现快速样式开发，无需编写 CSS 类名或 style 对象， 通过 props 直接控制样式。

使用示例：

```tsx
<Box
    bg="blue.500"         // 背景色（使用主题色）
    p={4}                 // 内边距（使用主题间距尺度）
    borderRadius="md"     // 圆角（使用主题定义）
    _hover={{bg: "red"}} // 悬停状态
>
    内容
</Box>
```

底层实现本质是将这些传入的 props 属性转化成最终的 CSS 样式。Box 组件体现了 Chakra UI "样式即 props" 的核心设计理念，是构建
Chakra 应用的最基础构建块。

从实现的抽象层级来说，这个组件是偏底层基础的。

### Center

居中功能的容器，实际的组件有：

```tsx
import {AbsoluteCenter, Center, Circle, Square} from "@chakra-ui/react"
```

绝对居中的常见实现有：

- 经典的 在 relative 父容器中进行 absolute 定位，然后偏移来居中。
- 或者 在 grid 布局中 place-items:center

### Container

Used to constrain a content's width to the current breakpoint, while keeping it fluid.

容器概念，核心作用是保持里面的内容宽度可以流式伸缩。

底层实现是 display:flex 的调节。

关键 API 有两个 props，分别是：

- sizes 用于指定固定的容器宽度。
- fluid 用于扩散宽度到充满父容器。

### Flex

Used to manage flex layouts

这个本质上也是 display:flex 的一些封装，然后提供一些常用的 prop 作为接口。

### Float

用于将元素锚定到容器的边缘。注意不是顾名思义的浮动。

位置 API：

```
"bottom-end",
"bottom-start",
"top-end",
"top-start",
"bottom-center",
"top-center",
"middle-center",
"middle-end",
"middle-start",
```

在底层实现时，通过将 `top-end` 切割成两个方向变量，根据这两个变量设置 position 和偏移值。

此外，还提供了 offsetX,offsetY,offset API 属性来进行边缘位置的轻微偏移。

### Grid

格子布局，支持两个轴。是对 display:grid 的语义封装。

### Group

视觉分组。分为合并（attached）和伸长（grow）两种模式。

- 合并：视觉上将两个 button 贴在一起
- 伸长：group 内部的子元素会尽量占满父容器空间。

### SimpleGrid

```tsx
import {SimpleGrid} from "@chakra-ui/react"
import {DecorativeBox} from "compositions/lib/decorative-box"

export const SimpleGridWithColumns = () => (
    <SimpleGrid columns={[2, null, 3]} gap="40px">
        <DecorativeBox height="20"/>
        <DecorativeBox height="20"/>
        <DecorativeBox height="20"/>
        <DecorativeBox height="20"/>
        <DecorativeBox height="20"/>
    </SimpleGrid>
)
```

`columns={[2, null, 3]}` 数组中的每个值对应不同的断点（breakpoint）：

| 数组索引 | 断点             | 实际含义             | 示例值 `[2, null, 3]` 的解释    |
|:-----|:---------------|:-----------------|:--------------------------|
| `0`  | `base`         | 默认 / 最小屏幕（移动端优先） | ** 所有屏幕 **：默认 2 列         |
| `1`  | `sm` (≥480px)  | 小屏幕              | `null` → ** 跳过此断点 **，继承前值 |
| `2`  | `md` (≥768px)  | 中等屏幕（平板）         | ≥768px 时变为 3 列            |
| `3`  | `lg` (≥992px)  | 大屏幕（桌面）          | 未指定 → 继承上一个有效值（3 列）       |
| `4`  | `xl` (≥1280px) | 超大屏幕             | 未指定 → 继承上一个有效值（3 列）       |

Auto-responsive：by using the `minChildWidth` prop. This uses css grid auto-fit and minmax() internally.

---

Column Span and gap

```tsx
import {GridItem, SimpleGrid} from "@chakra-ui/react"
import {DecorativeBox} from "compositions/lib/decorative-box"

export const SimpleGridWithColSpan = () => (
    <SimpleGrid columns={{base: 2, md: 4}} gap={{base: "24px", md: "40px"}}>
        <GridItem colSpan={{base: 1, md: 3}}>
            <DecorativeBox height="20">Column 1</DecorativeBox>
        </GridItem>
        <GridItem colSpan={{base: 1, md: 1}}>
            <DecorativeBox height="20">Column 2</DecorativeBox>
        </GridItem>
    </SimpleGrid>
)
```

留意 colSpan 和 gap 的设定值，应用于不同的屏幕宽度。

---

此外，Row and Column Gap 也可以单独设置。

```tsx
<SimpleGrid columns={2} columnGap="2" rowGap="4">
    <DecorativeBox height="20"/>
    <DecorativeBox height="20"/>
    <DecorativeBox height="20"/>
    <DecorativeBox height="20"/>
</SimpleGrid>
```

### Stack

Used to layout its children in a vertical or horizontal stack.

By default, Stack applies `flex-direction: column` and `gap: 8px` to its children.

- direction="row" 为水平排列。
- Use the HStack to create a horizontal stack and apply `align-items: center`;
- Use the VStack to create a vertical stack and align its children vertically.

此外，可以结合分割线来强调视觉效果上的分割

```tsx
import {Stack, StackSeparator} from "@chakra-ui/react"
import {DecorativeBox} from "compositions/lib/decorative-box"

const Demo = () => {
    return (
        <Stack separator={<StackSeparator/>}>
            <DecorativeBox h="20"/>
            <DecorativeBox h="20"/>
            <DecorativeBox h="20"/>
        </Stack>
    )
}

```

响应式方向

```tsx
import {Stack} from "@chakra-ui/react"
import {DecorativeBox} from "compositions/lib/decorative-box"

const Demo = () => {
    return (
        <Stack direction={{base: "column", md: "row"}} gap="10">
            <DecorativeBox boxSize="20"/>
            <DecorativeBox boxSize="20"/>
            <DecorativeBox boxSize="20"/>
        </Stack>
    )
}
```

### theme

局部主题。

```tsx
<Theme appearance="dark">
    <div/>
</Theme>
```

底层实现是 CSS 选择器，非常朴实的方式。

```tsx
<ThemeProvider theme={baseTheme}>
    <Box bg="blue.500"> {/* Uses baseTheme */}
        <ThemeProvider theme={nestedTheme}>
            <Box bg="red.500"> {/* Uses nestedTheme */}
        </ThemeProvider>
    </Box>
</ThemeProvider>
```

```css
/* Base theme styles */
.parent-theme .chakra-box {
    background: var(--chakra-colors-blue-500);
}

/* Nested theme override */
.parent-theme .nested-theme .chakra-box {
    background: var(--chakra-colors-red-500);
}
```

只需要覆盖特定的样式变量值（例子中是 background）即可更改主题。

### Wrap

Used to add space between elements and wraps automatically if there isn't enough space.

这个也是 flex 盒子的封装似乎，没有太大必要。

## 文字排版

### Blockquote

引用文本内容。

API 功能：cite、左侧竖条颜色、左侧图标支持、alignment 文本对齐方式、cite 的 avatar。

### Code

代码块显示。

- sizes 控制字体大小
- variants 值有 solid、outline、subtle、surface、plain 多种预设
- colorPalette 预设的颜色值。其实关于颜色的设置，最好是能够支持传入任意合法的颜色值。

### Em

Used to mark text for emphasis.

朴素无华的斜体。

### Heading

Used to render semantic HTML heading elements. 页面标题级别

sizes: 'xs' | 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl' | '4xl' | '5xl' | '6xl' | '7xl'

Highlight:

```tsx
<Highlight query="with speed" styles={{color: "teal.600"}}>
    Create accessible React apps with speed
</Highlight>
```

通过 query 设置需要差异化显示的文本部分。底层是 mark 标签，例如 `<mark class="chakra-mark css-254zz7">with speed</mark>`。、

可以通过 as 属性设置渲染的最终元素，例如 ` <Heading as="h1">Level 1</Heading>`

设置字重：`<Heading fontWeight="normal">Normal</Heading>`

也可以自定义一些属性。

- 自定义 heading 的 base 样式

```tsx
import {createSystem, defineRecipe} from "@chakra-ui/react"
import {defaultConfig} from "@chakra-ui/react"

const headingRecipe = defineRecipe({
    base: {
        fontWeight: "normal",
        textStyle: "4xl",
    },
})

const system = createSystem(defaultConfig, {
    theme: {
        recipes: {heading: headingRecipe},
    },
})
```

- 也可以定制文本 size 相关属性。但是看起来似乎挺麻烦的，要符合特定的格式，可能还不如暴力 override

```tsx
import {createSystem, defineRecipe} from "@chakra-ui/react"
import {defaultConfig} from "@chakra-ui/react"

const headingRecipe = defineRecipe({
    variants: {
        size: {
            custom: {
                fontSize: "100px",
                lineHeight: "100px",
                letterSpacing: "-2px",
            },
        },
    },
})

const system = createSystem(defaultConfig, {
    theme: {
        recipes: {heading: headingRecipe},
    },
})
```

```tsx
<Heading size="custom">I'm a custom size</Heading>
```

### highlight 部分文本

```tsx
<Highlight
    query="spotlight"
    styles={{px: "0.5", bg: "orange.subtle", color: "orange.fg"}}
>
    With the Highlight component, you can spotlight words.
</Highlight>
```

chakra 的 API 设计不是直接使用标签来包裹需要高亮的文本，如 `<span>spotlight</span>`。而是采用 query 属性来挑选，styles
是高亮文本的
样式设置。

此外，query 可以传递一个数组来支持多个文本高亮，例如 `query={["spotlight", "emphasize", "accentuate"]}`。

ignoreCase 这些选项可以设置匹配时的大小写问题。

### Kbd

Used to show key combinations for an action

可以被 code 组件替代。

### Link

链接文本。

variants: plain、underline。其中下划线样式比较常用。

colorPalette: 指定文本颜色

常见外部链接 icon 指示：

```tsx
<Link href="#">
    Visit Chakra UI <LuExternalLink/>
</Link>
```

可以结合框架的 router 来用，此时一般要使用 asChild 属性

```tsx
<ChakraLink asChild>
    <NextLink href="/about">Click here</NextLink>
</ChakraLink>
```

### Link Overlay

将 link 的点击区域扩散到父级容器。这种做法利于点击交互，但是弊端是此时整个点击区域的文本不能被选择。

示例：

```tsx
import {Heading, Link, LinkOverlay, Stack, Text} from "@chakra-ui/react"

const Demo = () => {
    return (
        <Stack position="relative">
            <Heading as="h4">Wanna try it out?</Heading>
            <Text color="fg.muted">
                This entire area is a link. Click it to see the effect.
            </Text>
            <LinkOverlay asChild href="#">
                <Link variant="underline">Click me</Link>
            </LinkOverlay>
        </Stack>
    )
}
```

此时，整个 stack 元素都是可以被点击跳转到 href 属性的。

### List

列表显示加强。主要是定制indicator的样式

### Mark

强调文本的样式。底层实现依旧是mark标签。

```tsx
import {For, Mark, Stack, Text} from "@chakra-ui/react"

const Demo = () => {
    return (
        <Stack gap="6">
            <For each={["subtle", "solid", "text", "plain"]}>
                {(variant) => (
                    <Text key={variant}>
                        The <Mark variant={variant}>design system</Mark> is a collection of
                        UI elements
                    </Text>
                )}
            </For>
        </Stack>
    )
}
```

个人觉得solid和subtle的样式应该有用武之地，后面两个不常用。

### Prose
用于格式化大片的HTML代码markdown文本。

### Text

底层是p标签。功能有 fontSize、fontWeights

Truncation: 单行长文本截断的实现
```
overflow: hidden;
text-overflow: ellipsis;
white-space: nowrap;
```

此外，还有多行文本截断：
```tsx
import { Flex, Text } from "@chakra-ui/react"

const Demo = () => {
  return (
    <Flex maxW="300px">
      <Text lineClamp="2">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim
        veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
        commodo consequat.
      </Text>
    </Flex>
  )
}
```
lineClamp="2" 表示最多到两行就开始截断。

## 组件

### Accordion

展开/收起内容显示的容器，常用于FAQ页面。

```tsx
<Accordion.Root>
  <Accordion.Item>
    <Accordion.ItemTrigger>
      <Accordion.ItemIndicator />
    </Accordion.ItemTrigger>
    <Accordion.ItemContent>
      <Accordion.ItemBody />
    </Accordion.ItemContent>
  </Accordion.Item>
</Accordion.Root>
```

功能有：
- with icon 支持icon
- multiple 支持同时展开多个item
- sizes 大小调整
- variants 可选值有 outline, subtle, enclosed or plain
- disable some item 禁用特定item
- with avatar 在indicator栏支持人物头像
- subtext 次要文本支持
- with action 动作按钮支持，这个不是很常用。

props 设计：

Root：

- **collapsible** 表示item在展开之后是否能折叠回去。
- **lazyMount** 懒挂载
- **multiple** 是否可以同时展开多个item
- **orientation** item的方向
- **unmountOnExit** 退出时是否卸载
- **onFocusChange** 聚焦改变是的回调
- **onValueChange** item值改变时的回调
- **value** 当前展开的item的值，为array类型。
- **disabled** 是否禁用全部item

Item：

- `value`*    The value of the accordion item
- `asChild`
- `disabled`   Whether the accordion item is disabled

### Action Bar

这个组件感觉不实用，跳过。

### Alert

提醒，用于传递系统的状态。

示例
```tsx
import { Alert } from "@chakra-ui/react"

const Demo = () => {
  return (
    <Alert.Root status="error">
      <Alert.Indicator />
      <Alert.Content>
        <Alert.Title>Invalid Fields</Alert.Title>
        <Alert.Description>
          Your form has some errors. Please fix them and try again.
        </Alert.Description>
      </Alert.Content>
    </Alert.Root>
  )
}
```
一共就三个部分，指示器、标题、描述。另外，state属性表示提醒的级别。

variants 变体有：
```tsx
<Alert.Root status="success" variant="subtle">
    <Alert.Indicator/>
    <Alert.Title>Data uploaded to the server. Fire on!</Alert.Title>
</Alert.Root>

<Alert.Root status="success" variant="solid">
    <Alert.Indicator/>
    <Alert.Title>Data uploaded to the server. Fire on!</Alert.Title>
</Alert.Root>
```
区分在于背景是否为深色，文本颜色都是相对取反。

还支持右侧close button来关闭这个alert。往root内部丢进去这个
```tsx
<CloseButton pos="relative" top="-2" insetEnd="-2" />
```

如果想要自定义左侧的icon，就往 Alert.Indicator 内部放入图标组件。     
```tsx
<Alert.Indicator>
    <LuAlarmClockPlus />
</Alert.Indicator>
```

关于颜色的设置，默认是取status prop的预设颜色，但是也可以自定义，通过 colorPalette="XXX" 值。

自包含封闭组件的实现。封闭指的是将自由度收窄，仅允许API传属性来样式化组件，不再允许自由组合组件内部的结构。
具体而言，外部只能通过有限的props（如title、icon、startElement/endElement）控制其内容，而无法直接修改内部子组件（如Alert.Indicator或Alert.Title）的结构或样式。

```tsx
import { Alert as ChakraAlert } from "@chakra-ui/react"
import * as React from "react"

export interface AlertProps extends Omit<ChakraAlert.RootProps, "title"> {
  startElement?: React.ReactNode
  endElement?: React.ReactNode
  title?: React.ReactNode
  icon?: React.ReactElement
}

export const AlertClosedComponent = React.forwardRef<
  HTMLDivElement,
  AlertProps
>(function Alert(props, ref) {
  const { title, children, icon, startElement, endElement, ...rest } = props
  return (
    <ChakraAlert.Root ref={ref} {...rest}>
      {startElement || <ChakraAlert.Indicator>{icon}</ChakraAlert.Indicator>}
      {children ? (
        <ChakraAlert.Content>
          <ChakraAlert.Title>{title}</ChakraAlert.Title>
          <ChakraAlert.Description>{children}</ChakraAlert.Description>
        </ChakraAlert.Content>
      ) : (
        <ChakraAlert.Title flex="1">{title}</ChakraAlert.Title>
      )}
      {endElement}
    </ChakraAlert.Root>
  )
})
```
