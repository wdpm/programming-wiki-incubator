type Fibonacci<T extends number, Prev extends any[] = [], Curr extends any[] = [1], Count extends any[] = [1]> =
    Count['length'] extends T
        ? Curr['length']
        : Fibonacci<T, Curr, [...Prev, ...Curr], [1, ...Count]>;

// N=1
// 初始调用：Fibonacci<2, [], [1], [1]>。
// 直接从 Curr['length'] 出口返回 1

// N=2
// 初始调用：Fibonacci<2, [], [1], [1]>。
// 第一次递归：Fibonacci<2, [1], [1], [1, 1]>。
// return 1

// 参数表示为第几个数字，从第一个开始数
// The sequence starts: 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, ...
type Result1 = Fibonacci<3> // 2
type Result1 = Fibonacci<4> // 3
type Result2 = Fibonacci<8> // 21