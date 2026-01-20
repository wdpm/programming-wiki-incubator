// type Trunc<T extends number | string> = `${T}` extends `${infer Int}.${infer _}`
//     ? Int extends ''
//         ? '0'
//         : Int
//     : `${T}` extends `-${infer Rest}`
//         ? Rest extends ''
//             ? `${T}`
//             : `-${Trunc<Rest>}`
//         : `${T}`;

type Trunc<T extends number | string> = `${T}` extends `-${infer Rest}`
    ? Rest extends ''
        ? `${T}`
        : `-${Trunc<Rest>}`
    : `${T}` extends `${infer Int}.${infer _}`
        ? Int extends ''
            ? '0'
            : Int
        : `${T}`;


import type {Equal, Expect} from '@type-challenges/utils'

type cases = [
    Expect<Equal<Trunc<0.1>, '0'>>,
    Expect<Equal<Trunc<0.2>, '0'>>,
    Expect<Equal<Trunc<1.234>, '1'>>,
    Expect<Equal<Trunc<12.345>, '12'>>,
    Expect<Equal<Trunc<-5.1>, '-5'>>,
    Expect<Equal<Trunc<'.3'>, '0'>>,
    Expect<Equal<Trunc<'1.234'>, '1'>>,
    Expect<Equal<Trunc<'-.3'>, '-0'>>,
    Expect<Equal<Trunc<'-10.234'>, '-10'>>,
    Expect<Equal<Trunc<10>, '10'>>,
]