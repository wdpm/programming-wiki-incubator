type MyEqual<X, Y> = (<T>() => T extends X ? 1 : 2) extends (<T>() => T extends Y ? 1 : 2)
    ? true
    : false

type LastIndexOf<T, U extends string | number> = T extends [
        ...infer Head,
        infer Tail
    ]
    ? MyEqual<Tail, U> extends true
        ? Head["length"]
        : LastIndexOf<Head, U>
    : -1

type Res1 = LastIndexOf<[1, 2, 3, 2, 1], 2> // 3
type Res2 = LastIndexOf<[0, 0, 0], 2> // -1