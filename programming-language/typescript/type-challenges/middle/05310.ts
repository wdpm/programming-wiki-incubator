type Join<T extends unknown[], D extends string | number =',', Result extends string = ''> =
    T extends [infer First extends string | number, ...infer Rest extends (string | number)[]]
        ? Rest extends []
            ? `${Result}${First}` // If no more elements, append the last item without a delimiter
            : Join<Rest, D, `${Result}${First}${D}`> // Add the current element and delimiter, continue
        : Result;

// Join<Rest, D, `${Result}${D}${First}`>

// Join<['p', 'p', 'l', 'e'], '-', 'a-`>
// Join<['p', 'l', 'e'], '-', 'a-p-`>
// ...
// Join<['e'], '-', 'a-p-p-l-`>
// Join<[], '-', 'a-p-p-l-e`>

import type { Equal, Expect } from '@type-challenges/utils'

type cases = [
    Expect<Equal<Join<['a', 'p', 'p', 'l', 'e'], '-'>, 'a-p-p-l-e'>>,
    Expect<Equal<Join<['Hello', 'World'], ' '>, 'Hello World'>>,
    Expect<Equal<Join<['2', '2', '2'], 1>, '21212'>>,
    Expect<Equal<Join<['o'], 'u'>, 'o'>>,
    Expect<Equal<Join<[], 'u'>, ''>>,
    Expect<Equal<Join<['1', '1', '1']>, '1,1,1'>>,
]
