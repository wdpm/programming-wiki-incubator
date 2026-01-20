type NumberToArray<T extends number, S extends unknown[] = []> = S['length'] extends T ? S : NumberToArray<T, [...S, 1]>
type Minus<T extends number> = NumberToArray<T> extends [...infer Pre, 1] ? Pre['length'] : T

// 判断第一项是否等于0，等于则直接返回false
// 进入第二个判断，则第一个数字不为0，如果第二个数字为0，则返回true
// 进入最后一个，如果第一和第二项数字都不为0，全部减一，再进行循环
type GreaterThan<T extends number, S extends number> = T extends 0 ? false : S extends 0 ? true : GreaterThan<Minus<T>, Minus<S>>

// 弊端：NumberToArray构建大元组时必定内存耗尽。

// 题目要求
// A > B 时返回 true
// A <= B 时返回 false
GreaterThan<2, 1> //should be true
GreaterThan<1, 1> //should be false
GreaterThan<10, 100> //should be false
GreaterThan<111, 11> //should be true