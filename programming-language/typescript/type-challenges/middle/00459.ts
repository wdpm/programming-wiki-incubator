type Flatten<T extends any[]> = T extends [infer First, ...infer Rest]
    ? First extends any[] // 如果当前元素是数组
        ? [...Flatten<First>, ...Flatten<Rest>] // 递归展平数组元素
        : [First, ...Flatten<Rest>] // 否则保留元素并继续递归
    : []; // 如果是空数组，则返回空数组

type flatten = Flatten<[1, 2, [3, 4], [[[5]]]]> // [1, 2, 3, 4, 5]