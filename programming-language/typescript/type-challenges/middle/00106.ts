// T extends ` ${infer R}`：
// 如果 T 是以一个空格开头的字符串，R 会捕获剩余部分。
// 如果匹配成功，则递归调用 TrimLeft<R> 去掉下一层的空格。
// 如果匹配失败，表示字符串左侧已经没有空格，返回 T。
// Note: 空格的删除是逐个删除的。
type TrimLeft<T extends string> = T extends ` ${infer R}` ? TrimLeft<R> : T;

// 移除左侧空白
type trimed = TrimLeft<'  Hello World  '> // 应推导出 'Hello World  '