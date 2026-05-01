# shadcn/ui get started

## 自定义预设

前往 [create 页面](https://shadcn.nodejs.cn/create)：

- Base 选择 Base UI。
- Style 默认是 vega，但是组件内间距和字体较大。个人偏好紧凑的 Lyra，其中 Mira
  最为紧凑。参考 [shadcn/ui Component Styles: Vega, Nova, Maia, Lyra, and Mira](https://www.shadcnblocks.com/blog/shadcn-component-styles-vega-nova-maia-lyra-mira)。
- Base Color (基础色) 选择一个灰白色调的颜色
- Theme (主题) 一般而言，保持和 Base Color 一致。
- Radius 个人偏好选择 None 或者 Small，无法接受圆润的大边框半径。
- Menu Color (菜单特定选项)  Default 侧边栏或菜单的背景颜色模式。
- Menu Accent (界面特定选项)    Subtle 菜单高亮 / 强调色的风格。

调节完毕之后，复制预设值来初始化，例如：

```bash
npx shadcn@latest init --preset aw7WY6K
```

## components.json 配置

示例配置

```json
{
  "style": "new-york",
  "rsc": true,
  "tailwind": {
    "config": "",
    "css": "app/globals.css",
    "baseColor": "neutral",
    "cssVariables": true
  },
  "aliases": {
    "components": "@/components",
    "utils": "@/lib/utils",
    "ui": "@/components/ui",
    "lib": "@/lib",
    "hooks": "@/hooks"
  },
  "iconLibrary": "lucide"
}
```

## CSS 变量

当定义：

```css
--primary: oklch(0.205 0 0);
--primary-foreground: oklch(0.985 0 0);
```

时，以下组件的 `background` 颜色将为 `var(--primary)`，`foreground` 颜色将为 `var(--primary-foreground)`。

```html
<div className="bg-primary text-primary-foreground">Hello</div>
```

要添加新颜色，需要将它们添加到 CSS 文件中的 `:root` 和 `dark` 伪类下。然后，使用 `@theme inline` 指令将这些颜色作为 CSS 变量使用。

```css
:root {
  --warning: oklch(0.84 0.16 84);
  --warning-foreground: oklch(0.28 0.07 46);
}

.dark {
  --warning: oklch(0.41 0.11 46);
  --warning-foreground: oklch(0.99 0.02 95);
}

@theme inline {
  --color-warning: var(--warning);
  --color-warning-foreground: var(--warning-foreground);
}
```

使用示例：

```html
<div className="bg-warning text-warning-foreground" />
```

背后的原理：

Tailwind 通过扫描你的 CSS 中的 `@theme` 块，自动推断出所有可用的工具类。

推断规则如下：

| 你在 `@theme` 中写的      | Tailwind 自动生成的工具类前缀                                |
| :------------------------ | :----------------------------------------------------------- |
| `--color-warning: ...`    | `bg-warning`, `text-warning`, `border-warning`, `ring-warning`, ... |
| `--font-family-sans: ...` | `font-sans`                                                  |
| `--spacing-4: ...`        | `p-4`, `m-4`, `w-4`, `h-4`, `gap-4`, ...                     |
| `--radius-lg: ...`        | `rounded-lg`                                                 |

也就是说，**CSS 变量名里的 `--color-` 这个前缀，就是 Tailwind 的“触发器”**。它看到 `--color-`，就知道要生成所有与颜色相关的工具类。



## 夜间模式

[夜间模式 - shadcn 中文网](https://shadcn.nodejs.cn/docs/dark-mode/vite)