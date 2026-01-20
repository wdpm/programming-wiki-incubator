// 只考虑递归，不考虑递归深度时
// type FlattenDepth<T extends any[]> = T extends [infer F, ...infer R]
//     ? F extends any[]
//         ? [...FlattenDepth<F>, ...FlattenDepth<R>]
//         : [F, ...FlattenDepth<R>]
//     : T

// S表示递归深度
type FlattenDepth<
    T extends any[],
    S extends number = 1,
    U extends any[] = []
> = U['length'] extends S
    ? T
    : T extends [infer F, ...infer R]
        ? F extends any[]
            ? [...FlattenDepth<F, S, [...U, 1]>, ...FlattenDepth<R, S, U>]
            : [F, ...FlattenDepth<R, S, U>]
        : T

// ...FlattenDepth<F, S, [...U, 1]> => ...FlattenDepth<[3,4],2,[...[], 1]> => ...[3,4] => 3,4

// ...FlattenDepth<R, S, U> => ...FlattenDepth<[[[5]]], 2, []> => ...FlattenDepth<[[5]], 2, [...[], 1]>
// => [5]

// Recursively flatten array up to depth times.
// 这里的递归深度是对每个数组元素而言的，如果元素是数组类型，那就最多进行depth次递归打平
type a = FlattenDepth<[1, 2, [3, 4], [[[5]]]], 2> // [1, 2, 3, 4, [5]]. flatten 2 times
type b = FlattenDepth<[1, 2, [3, 4], [[[5]]]]> // [1, 2, 3, 4, [[5]]]. Depth defaults to be 1