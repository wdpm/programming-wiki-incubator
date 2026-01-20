// Implement RemoveIndexSignature<T> , exclude the index signature from object types.
type Foo = {
    [key: string]: any
    foo(): void
}

type RemoveIndexSignature<T> = {
    [K in keyof T as K extends string | number | symbol
        ? string extends K
            ? never
            : number extends K
                ? never
                : symbol extends K
                    ? never
                    : K
        : never]: T[K];
};


type A = RemoveIndexSignature<Foo> // expected { foo(): void }