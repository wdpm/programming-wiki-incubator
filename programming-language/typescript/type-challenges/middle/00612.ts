type KebabCase<S extends string> = S extends `${infer Head}${infer Tail}`
    ? Tail extends Uncapitalize<Tail>
        ? `${Uncapitalize<Head>}${KebabCase<Tail>}` // 如果 Tail 的首字母是小写，则直接追加处理。
        : `${Uncapitalize<Head>}-${KebabCase<Tail>}`// 如果 Tail 的首字母是大写，则插入 - 后将其转为小写。
    : S;

// to kebab-case
type FooBarBaz = KebabCase<"FooBarBaz">
const foobarbaz: FooBarBaz = "foo-bar-baz"

type DoNothing = KebabCase<"do-nothing">
const doNothing: DoNothing = "do-nothing"