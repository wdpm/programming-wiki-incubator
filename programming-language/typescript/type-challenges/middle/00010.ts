type Arr = ['1', '2', '3']

// T[number] 是 TypeScript 中用于提取数组或元组所有元素类型的方式。
// 它会遍历 T 的每个元素，并将它们的类型组合成一个联合类型。
type TupleToUnion<T extends readonly any[]> = T[number];

type Test = TupleToUnion<Arr> // expected to be '1' | '2' | '3'