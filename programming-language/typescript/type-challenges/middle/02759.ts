interface User {
    name?: string
    age?: number
    address?: string
}
// makes properties in K required by -?
type RequiredByKeys<T, K extends keyof T = keyof T> = {
    [P in keyof T as P extends K ? P : never]-?: T[P];
} & {
    [P in keyof T as P extends K ? never : P]: T[P];
} extends infer O
    ? { [P in keyof O]: O[P] }
    : never;

type UserRequiredName = RequiredByKeys<User, 'name'> // { name: string; age?: number; address?: string }
