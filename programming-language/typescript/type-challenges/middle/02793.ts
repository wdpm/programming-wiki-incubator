interface Todo {
    readonly title: string
    readonly description: string
    readonly completed: boolean
}

// The -readonly modifier removes the readonly attribute from all properties.
type Mutable<T> = {
    -readonly [P in keyof T]: T[P];
};

type MutableTodo = Mutable<Todo> // { title: string; description: string; completed: boolean; }
