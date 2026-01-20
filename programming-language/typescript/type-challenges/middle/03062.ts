type Shift<T extends any[]> = T extends [infer First, ...infer Rest] ? Rest : never;

type Result = Shift<[3, 2, 1]> // [2, 1]