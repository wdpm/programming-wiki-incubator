type X = {
    x: {
        a: 1
        b: 'hi'
    }
    y: 'hey'
}

type Expected = {
    readonly x: {
        readonly a: 1
        readonly b: 'hi'
    }
    readonly y: 'hey'
}

// 可以假设在此挑战中我们仅处理对象，不考虑数组、函数、类等
type DeepReadonly<T> = T extends (...args: any[]) => any // 函数
    ? T
    : T extends { new(...args: any[]): any } // 类
        ? T
        : T extends ReadonlyArray<any> // 数组或元组
            ? { readonly [K in keyof T]: DeepReadonly<T[K]> }
            : T extends object // 对象
                ? { readonly [K in keyof T]: DeepReadonly<T[K]> }
                : T; // 原始类型


type Todo = DeepReadonly<X> // should be same as `Expected`