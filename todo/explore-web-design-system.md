## web UI 设计系统参考

- [atlassian](https://atlassian.design/)
- [Salesforce’s Lightning design system](https://www.lightningdesignsystem.com/2e1ef8501)
- [shopify web components](https://shopify.dev/docs/api/app-home/web-components)
- GitHub Primer https://primer.style/design/guides/introduction/
- reshape https://reshaped.so/
- IBM carbon https://carbondesignsystem.com/

What a Design System Typically Includes
- Design principles — the “why” behind design decisions
- Design tokens — colors, spacing, typography values as code variables
- Component library — coded, reusable UI elements
- Pattern library — recurring UX solutions and page layouts
- Style guide — visual and brand guidelines
- Documentation — usage guidelines, accessibility requirements, code examples
- Governance model — who maintains the system, how changes are proposed and approved

A Style Guide Typically Covers
- Color palette — primary, secondary, semantic (success, error, warning)
- Typography — font families, sizes, weights, line heights
- Spacing & layout — grid system, margins, padding conventions
- Iconography — icon style, sizes, usage rules
- Brand voice & tone — writing style for UI copy
- Logo usage — clear space, minimum sizes, color variants

Examples of UX Patterns 用户体验模式的示例
Authentication flow — login, registration, password reset
身份验证流程——登录、注册、密码重置
Search with filters — search bar + faceted filtering + results list
使用过滤器搜索 — 搜索栏 + 分面过滤 + 结果列表
Data table with actions — sortable table + bulk actions + pagination
带操作的数据表 — 可排序表 + 批量操作 + 分页
Onboarding sequence — progressive disclosure, tooltip tours, empty states
入门顺序——渐进式披露、工具提示浏览、空状态
Form validation — inline errors, success states, required field indicators
表单验证——内联错误、成功状态、必填字段指示符

组件库是可重用、编码的 UI 元素的集合 - 构成界面的原子构建块。在现代开发中，这些通常构建在 React、Vue、Angular 中或作为 Web 组件。

通过将生产代码组件直接引入设计工具来消除“两个事实来源”问题。设计人员不需要学习单独的系统 -
他们使用开发人员使用的相同组件进行设计，确保一致性并使采用自然。