type Foo = {
    a: string;
    b: number;
}
type Bar = {
    a: string;
    c: boolean
}

// keyof (T | U) 含义是交集
// 并集减去交集
type Diff<T, U> = Omit<T & U, keyof (T | U)>;

type Result1 = Diff<Foo,Bar> // { b: number, c: boolean }
type Result2 = Diff<Bar,Foo> // { b: number, c: boolean }
