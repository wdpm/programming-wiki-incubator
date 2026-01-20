type Test = -100;

// 字符字面量识别负号，取值
type Absolute<T extends number | string | bigint> =
    `${T}` extends `-${infer R}` ? R : `${T}`;

// 求绝对值
type Result = Absolute<Test>; // expected to be "100"