// 要使 If 类型正常工作，需要确保 T 的值明确为字面量 true 或 false，而非宽泛的 boolean 类型
// type If<T extends true | false, U, V> = T extends true ? U : V;
type If<C extends boolean, T, F> = C extends true ? T : F

type A = If<true, 'a', 'b'>  // expected to be 'a'
type B = If<false, 'a', 'b'> // expected to be 'b'