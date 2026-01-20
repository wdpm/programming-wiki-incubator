// StringToUnion 将字符串 T 分解为单个字符的联合类型
type StringToUnion<T extends string> = T extends `${infer First}${infer Rest}`
    ? First | StringToUnion<Rest>
    : never;

// Permutation 通过递归生成联合类型中所有字符的排列
type Permutation<T extends string, U extends string = T> = [T] extends [never]
    ? ''
    : T extends any
        ? `${T}${Permutation<Exclude<U, T>>}`
        : never;

// A
//   - 'BC'
//        -
// 'A'
// 'AB' 'AC'
// 'ABC'  'ACB'

// 不要忘记''空字符串
type AllCombinations<T extends string> = '' | Permutation<StringToUnion<T>>;


type AllCombinations_ABC = AllCombinations<'ABC'>;
// should be '' | 'A' | 'B' | 'C' | 'AB' | 'AC' | 'BA' | 'BC' | 'CA' | 'CB' | 'ABC' | 'ACB' | 'BAC' | 'BCA' | 'CAB' | 'CBA'

