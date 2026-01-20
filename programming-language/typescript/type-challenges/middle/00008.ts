interface Todo {
    title: string
    description: string
    completed: boolean
}

// U集合的key是readonly的
// 在T集合中而且不在U集合中的键是自由可写的

// 特殊情况：如果未提供U，则应使所有属性都变为只读，就像普通的Readonly<T>一样。
// 其实之前已经实现了readonly和pick、omit，这里直接用
type MyReadonly2<T, U extends keyof T = keyof T> =
    Readonly<Pick<T, U>> & Omit<T, U>;


const todo: MyReadonly2<Todo, 'title' | 'description'> = {
    title: "Hey",
    description: "foobar",
    completed: false,
}

todo.title = "Hello" // Error: cannot reassign a readonly property
todo.description = "barFoo" // Error: cannot reassign a readonly property
todo.completed = true // OK