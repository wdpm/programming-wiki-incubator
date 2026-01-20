type DropChar<S extends string, C extends string> =
    S extends `${infer Head}${C}${infer Tail}`
        ? DropChar<`${Head}${Tail}`, C>
        : S;

type Butterfly = DropChar<' b u t t e r f l y ! ', ' '> // 'butterfly!'