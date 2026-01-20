const foo = (arg1: string, arg2: number): void => {
}

// infer P 将捕获函数参数的元组类型（例如 [string, number]）。
// 如果 T 是一个有效的函数类型，则返回参数元组类型 P；否则返回 never。
type MyParameters<T extends (...args: any[]) => any> = T extends (...args: infer P) => any ? P : never;

type FunctionParamsType = MyParameters<typeof foo> // [string, number]