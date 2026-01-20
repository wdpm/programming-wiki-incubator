// 使用any[]标志上界，然后解构即可
// readonly 是兼容tuple类型
type Concat<T extends readonly any[], U extends readonly any[]> =[...T, ...U];

type Result = Concat<[1], [2]> // expected to be [1, 2]