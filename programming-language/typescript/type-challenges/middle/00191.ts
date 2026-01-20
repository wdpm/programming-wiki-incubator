type Fn = (a: number, b: string) => number

// infer Args 取原函数参数
// infer ReturnType 取原函数返回值类型
// 拼接 (...args: [...Args, A]) => ReturnType
type AppendArgument<F extends (...args: any[]) => any, A> =
    F extends (...args: infer Args) => infer ReturnType
        ? (...args: [...Args, A]) => ReturnType
        : never;

type Result = AppendArgument<Fn, boolean>
// 期望是 (a: number, b: string, x: boolean) => number