interface User {
    name: string
    age: number
    address: string
}

// When K is not provided, it should make all properties optional just like the normal Partial<T>
type PartialByKeys<T, K extends keyof T = keyof T> = {
    [P in keyof T as P extends K ? P : never]?: T[P];
} & {
    [P in keyof T as P extends K ? never : P]: T[P];
} extends infer O // Use extends infer O to normalize the resulting intersection into a single type.
    ? { [P in keyof O]: O[P] }
    : never;

type UserPartialName = PartialByKeys<User, 'name'> // { name?:string; age:number; address:string }