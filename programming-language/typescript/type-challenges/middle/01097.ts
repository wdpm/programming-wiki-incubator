// 通过 [U] extends [T] 检查是否能将整体类型 U 分配给单个成员 T：
// a) 如果 U 能分配给 T，说明 T 不是联合类型。例如 [string] extends [string] 为真，返回false
// b) 如果 U 不能完全分配给 T，说明 T 是联合类型。例如 [string | number] extends [string]为假，返回true
type IsUnion<T, U = T> = T extends U ? ([U] extends [T] ? false : true) : never;

// 判断是否为联合类型
type case1 = IsUnion<string> // false
type case2 = IsUnion<string | number> // true
type case3 = IsUnion<[string | number]> // false