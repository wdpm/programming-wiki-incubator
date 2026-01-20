type MapTypes<T, R extends { mapFrom: any; mapTo: any }> = {
    [K in keyof T]: T[K] extends R['mapFrom']
        ? R extends { mapFrom: T[K] }
            ? R['mapTo']
            : never
        : T[K]
}
// R extends { mapFrom: any; mapTo: any } 限制了类型

// 为何需要 R extends { mapFrom: T[K] }？
// 因为 R 可能是联合类型（例如 { mapFrom: string; mapTo: number } | { mapFrom: Date; mapTo: string }），需要对联合类型逐一匹配：
//
// TypeScript 会对联合类型 R 的每个成员执行 R extends { mapFrom: T[K] }，找到符合的成员。
// 例如 { mapFrom: string, mapTo: boolean } extends { mapFrom: string }


/* _____________ Test Cases _____________ */
import type { Equal, Expect } from '@type-challenges/utils'

type cases = [
    Expect<Equal<MapTypes<{ stringToArray: string }, { mapFrom: string, mapTo: [] }>, { stringToArray: [] }>>,
    Expect<Equal<MapTypes<{ stringToNumber: string }, { mapFrom: string, mapTo: number }>, { stringToNumber: number }>>,
    Expect<Equal<MapTypes<{ stringToNumber: string, skipParsingMe: boolean }, { mapFrom: string, mapTo: number }>, { stringToNumber: number, skipParsingMe: boolean }>>,
    Expect<Equal<MapTypes<{ date: string }, { mapFrom: string, mapTo: Date } | { mapFrom: string, mapTo: null }>, { date: null | Date }>>,
    Expect<Equal<MapTypes<{ date: string }, { mapFrom: string, mapTo: Date | null }>, { date: null | Date }>>,
    Expect<Equal<MapTypes<{ fields: Record<string, boolean> }, { mapFrom: Record<string, boolean>, mapTo: string[] }>, { fields: string[] }>>,
    Expect<Equal<MapTypes<{ name: string }, { mapFrom: boolean, mapTo: never }>, { name: string }>>,
    Expect<Equal<MapTypes<{ name: string, date: Date }, { mapFrom: string, mapTo: boolean } | { mapFrom: Date, mapTo: string }>, { name: boolean, date: string }>>,
]