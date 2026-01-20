interface Todo {
    title: string
    description: string
    completed: boolean
}

// keyof T表示取T的key属性形成的集合
// K extends keyof T表示K是上面集合的子集
type MyPick<T, K extends keyof T> = {
    [key in K]: T[key]
}

type TodoPreview = MyPick<Todo, 'title' | 'completed'>

const todo: TodoPreview = {
    title: 'Clean room',
    completed: false,
}