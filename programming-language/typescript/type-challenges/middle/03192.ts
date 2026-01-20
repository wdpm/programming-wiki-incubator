type Reverse<T extends any[]> = T extends [infer First, ...infer Rest]
    ? [...Reverse<Rest>, First]
    : T;

type a = Reverse<['a', 'b']> // ['b', 'a']
type b = Reverse<['a', 'b', 'c']> // ['c', 'b', 'a']