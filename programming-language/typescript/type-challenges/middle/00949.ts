// { [key: string]: never } 更严格地表示一个完全没有属性的空对象，不能直接用{}表示空对象类型
type Falsy = 0 | '' | false | [] | { [key: string]: never };
type AnyOf<T extends readonly any[]> = T[number] extends Falsy ? false : true;

type Sample1 = AnyOf<[1, '', false, [], {}]> // expected to be true.
type Sample2 = AnyOf<[0, '', false, [], {}]> // expected to be false.