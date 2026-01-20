interface Model {
    name: string;
    age: number;
    locations: string[] | null;
}

// T[K] extends undefined处理了value为undefined的情况
// 非undefined值时，转为 Required<T>[K]
type ObjectEntries<T extends object, K extends keyof T  = keyof T>
    = K extends K ? [K, T[K] extends undefined ? undefined : Required<T>[K]] : never

type modelEntries = ObjectEntries<Model> // ['name', string] | ['age', number] | ['locations', string[] | null];