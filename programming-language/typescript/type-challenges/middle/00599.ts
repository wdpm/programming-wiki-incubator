type foo = {
    name: string;
    age: string;
}

type coo = {
    age: number;
    sex: string
}

type Merge<F, S> = {
    [K in keyof F | keyof S]: K extends keyof S
        ? S[K] // 如果在第二个对象中存在属性，使用第二个对象的类型
        : K extends keyof F
            ? F[K] // 否则使用第一个对象的类型
            : never;
};

type Result = Merge<foo,coo>; // expected to be {name: string, age: number, sex: string}