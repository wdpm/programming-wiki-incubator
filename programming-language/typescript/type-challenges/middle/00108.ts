type TrimLeft<T extends string> = T extends `${' ' | '\n' | '\t'}${infer R}` ? TrimLeft<R> : T;
type TrimRight<T extends string> = T extends `${infer L}${' ' | '\n' | '\t'}` ? TrimRight<L> : T;
type Trim<T extends string> = TrimRight<TrimLeft<T>>;


type trimed = Trim<'  Hello World  '> // expected to be 'Hello World'