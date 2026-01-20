// 工具类型，用于检查一个元素是否已经存在于数组中
type Includes<T extends any[], U> =
    T extends [infer First, ...infer Rest]
        ? Equal<First, U> extends true
            ? true
            : Includes<Rest, U>
        : false;

// `Equal` 类型，用于精确比较两个类型是否相等
type Equal<X, Y> = (<T>() => T extends X ? 1 : 2) extends
    (<T>() => T extends Y ? 1 : 2)
    ? true
    : false;

// 主类型 `Unique`
type Unique<T extends any[], Result extends any[] = []> =
    T extends [infer First, ...infer Rest]
        ? Includes<Result, First> extends true
            ? Unique<Rest, Result> // 如果元素已存在，跳过
            : Unique<Rest, [...Result, First]> // 如果元素不存在，添加到结果
        : Result;

type Res = Unique<[1, 1, 2, 2, 3, 3]>; // expected to be [1, 2, 3]
type Res1 = Unique<[1, 2, 3, 4, 4, 5, 6, 7]>; // expected to be [1, 2, 3, 4, 5, 6, 7]
type Res2 = Unique<[1, "a", 2, "b", 2, "a"]>; // expected to be [1, "a", 2, "b"]
type Res3 = Unique<[string, number, 1, "a", 1, string, 2, "b", 2, number]>; // expected to be [string, number, 1, "a", 2, "b"]
type Res4 = Unique<[unknown, unknown, any, any, never, never]>; // expected to be [unknown, any, never]