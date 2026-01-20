type NodeA = {
    type: "A"
    name: string
    flag: number
}

type NodeB = {
    type: "B"
    id: number
    flag: number
}

type NodeC = {
    type: "C"
    name: string
    flag: number
}

type Nodes = NodeA | NodeB | NodeC

type ReplaceKeys<T, K extends keyof any, R> = T extends any
    ? {
        [P in keyof T]: P extends K ? (P extends keyof R ? R[P] : never) : T[P]; // R[P] 表示key在K和R中都有定义
    }
    : never;

type ReplacedNodes = ReplaceKeys<
    Nodes,
    "name" | "flag",
    { name: number; flag: string }
> // {type: 'A', name: number, flag: string} | {type: 'B', id: number, flag: string} | {type: 'C', name: number, flag: string} // would replace name from string to number, replace flag from number to string.

type ReplacedNotExistKeys = ReplaceKeys<Nodes, "name", { aa: number }> // {type: 'A', name: never, flag: number} | NodeB | {type: 'C', name: never, flag: number} // would replace name to never