const tuple = ['tesla', 'model 3', 'model X', 'model Y'] as const

// tuple是只读类型，所以 readonly
// tuple的元素值可以是任意，所以 any[]
// tuple取元素值时是按照tuple[number]的形式来取，因此 K in T[number]
type TupleToObject<T extends readonly any[]> = {
    [K in T[number]]: K
}

type result = TupleToObject<typeof tuple>
// expected { 'tesla': 'tesla', 'model 3': 'model 3', 'model X': 'model X', 'model Y': 'model Y'}