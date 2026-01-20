type Permutation<T, U = T> =
    [T] extends [never]
        ? [] // 如果 T 是 `never`，表示没有更多的元素，返回空元组
        : T extends U
            ? [T, ...Permutation<Exclude<U, T>>] // 取出一个 T，排列剩下的元素
            : never;

// 1. U = T 初始化U为T
// 2. T extends U :当 T extends U 时，T 会自动分发联合类型的每一个成员。
// 3. 每次选中一个 T，剩余的部分通过 Exclude<U, T> 递归处理。
// 举例：T选中'A'元素，剩下的元素就是 'B' | 'C'，后续的排列只需要处理'B' | 'C'的排列情况。这是减治的思路。
// 此时 ...Permutation<Exclude<U, T>> 表示的就是 'B' | 'C' 集合

type perm = Permutation<'A' | 'B' | 'C'>; // ['A', 'B', 'C'] | ['A', 'C', 'B'] | ['B', 'A', 'C'] | ['B', 'C', 'A'] | ['C', 'A', 'B'] | ['C', 'B', 'A']