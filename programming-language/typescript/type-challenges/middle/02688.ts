// T extends `${U}${string}`关键在于这个前缀判断
type StartsWith<T extends string, U extends string> = T extends `${U}${string}` ? true : false;

type a = StartsWith<'abc', 'ac'> // expected to be false
type b = StartsWith<'abc', 'ab'> // expected to be true
type c = StartsWith<'abc', 'abcd'> // expected to be false