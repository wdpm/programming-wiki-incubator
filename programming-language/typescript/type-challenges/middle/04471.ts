type Zip<T extends any[], U extends any[]> = T extends [infer F, ...infer R extends any[]]
    ? U extends [infer S, ...infer Q extends any[]]
        ? [[F, S], ...Zip<R, Q>]
        : []
    : [];


type exp = Zip<[1, 2], [true, false]> // expected to be [[1, true], [2, false]]