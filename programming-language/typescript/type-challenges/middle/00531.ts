type Test = '123';

// First | StringToUnion<Rest> 取第一个，然后使用联合类型合并剩下的部分
type StringToUnion<T extends string> =
    T extends `${infer First}${infer Rest}`
        ? First | StringToUnion<Rest>
        : never

type Result = StringToUnion<Test>; // expected to be "1" | "2" | "3"