// 需要从T集合排除U集合的元素
// 这段代码实现了一个自定义的工具类型，用于从一个联合类型中排除某些特定的类型
type MyExclude<T, U> = T extends U ? never : T

type Result = MyExclude<'a' | 'b' | 'c', 'a'> // 'b' | 'c'