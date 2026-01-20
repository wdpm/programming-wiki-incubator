interface Todo {
    title: string
    description: string
}

// 仅仅是添加了一个前缀readonly声明
type MyReadonly<T> = {
    readonly [K in keyof T]: T[K]
}

const todo: MyReadonly<Todo> = {
    title: "Hey",
    description: "foobar"
}

todo.title = "Hello" // Error: cannot reassign a readonly property
todo.description = "barFoo" // Error: cannot reassign a readonly property