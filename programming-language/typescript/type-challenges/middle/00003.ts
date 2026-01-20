interface Todo {
    title: string
    description: string
    completed: boolean
}

// 实现 TypeScript 的 Omit<T, K> 泛型, Omit 会创建一个省略 K 中字段的 T 对象。
// Key in keyof T 遍历对象 T 的所有键。
// Key extends K ? never : Key 这里排除了 K集合中的键
type MyOmit<T, K extends keyof T> = {
    [Key in keyof T as Key extends K ? never : Key]: T[Key];
};

type TodoPreview = MyOmit<Todo, 'description' | 'title'>

const todo: TodoPreview = {
    completed: false,
}