// impl equal type
// 利用函数参数的逆变特性（Function Parameter Contravariance）。
// <T>() => T extends X ? 1 : 2 是一个泛型函数签名，它可以看作是 "包装了类型 X 的行为"。
// 如果 T extends X 和 T extends Y 的行为完全一致，则说明 X 和 Y 是相等的类型。
type Equal<X, Y> =
    (<T>() => T extends X ? 1 : 2) extends
        (<T>() => T extends Y ? 1 : 2)
        ? true
        : false;

type Includes<T extends readonly any[], U> =
    T extends [infer First, ...infer Rest] // 拆分数组，取第一个元素 `First` 和剩余部分 `Rest`
        ? Equal<First, U> extends true       // 判断第一个元素是否等于 `U`
            ? true                            // 如果相等，返回 `true`
            : Includes<Rest, U>               // 否则递归检查剩余部分
        : false;                            // 如果数组为空，返回 `false`

type isPillarMen = Includes<['Kars', 'Esidisi', 'Wamuu', 'Santana'], 'Dio'> // expected to be `false`