// 解构展开T，拼接U
type Push<T extends readonly any[], U> = [...T, U];

type Result = Push<[1, 2], '3'> // [1, 2, '3']