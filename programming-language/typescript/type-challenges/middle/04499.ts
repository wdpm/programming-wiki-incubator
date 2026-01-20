type Chunk<
    T extends unknown[],
    N extends number,
    R extends unknown[] = []
> = T extends [infer F, ...infer Rest]
    ? R['length'] extends N // R代表当前切割的块，判断长度是否达到N，
        ? [R, ...Chunk<T, N>] // 是，那就切割其他的部分
        : Chunk<Rest, N, [...R, F]> // 否，那就
    : R extends []
        ? []
        : [R];

// Chunk<[2,3], 2, [...[], 1]>
// Chunk<[3], 2, [1,2]>
// R['length'] extends 2
// [[1,2], ...Chunk<[3], 2>]
// 对于 ...Chunk<[3], 2>这部分
// Chunk<[], 2, [..[],3]>

// 走这个分支
// R extends []
//         ? []
//         : [R];
// [R] => [[3]]

// [[1,2], ...[[3]]] => [[1,2],[3]]

type exp1 = Chunk<[1, 2, 3], 2> // expected to be [[1, 2], [3]]
type exp2 = Chunk<[1, 2, 3], 4> // expected to be [[1, 2, 3]]
type exp3 = Chunk<[1, 2, 3], 1> // expected to be [[1], [2], [3]]