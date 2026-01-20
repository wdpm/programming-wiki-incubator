type IsTuple<T> =
    [T] extends [never] ?
        false :
        T extends readonly any[] ?
            number extends T['length'] ? false : true
            : false

// tuple必须满足形式如any[]同时没有length属性(元组的长度是固定的数值，不是number类型)，其他情况一律false

type case1 = IsTuple<[number]> // true
type case2 = IsTuple<readonly [number]> // true
type case3 = IsTuple<number[]> // false