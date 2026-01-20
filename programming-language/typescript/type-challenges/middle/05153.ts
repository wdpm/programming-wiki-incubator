type Equal_<X, Y> =
    (<T>() => T extends X ? 1 : 2) extends
        (<T>() => T extends Y ? 1 : 2)
        ? true
        : false;

type IndexOf<
    T extends unknown[],
    U,
    Count extends unknown[] = []
> = T extends [infer First, ...infer Rest]
    ? Equal_<First, U> extends true
        ? Count['length']
        : IndexOf<Rest, U, [...Count, unknown]>
    : -1;

// Count['length'] 代表着匹配元素的index

type Res = IndexOf<[1, 2, 3], 2>; // expected to be 1
type Res1 = IndexOf<[2,6, 3,8,4,1,7, 3,9], 3>; // expected to be 2
type Res2 = IndexOf<[0, 0, 0], 2>; // expected to be -1