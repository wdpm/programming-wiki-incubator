const tree1 = {
    val: 1,
    left: null,
    right: {
        val: 2,
        left: {
            val: 3,
            left: null,
            right: null,
        },
        right: null,
    },
} as const

type InorderTraversal<T> = T extends {
        val: infer V;
        left: infer L;
        right: infer R
    }
    ? [...InorderTraversal<L>, V, ...InorderTraversal<R>]
    : [];


type A = InorderTraversal<typeof tree1> // [1, 3, 2]