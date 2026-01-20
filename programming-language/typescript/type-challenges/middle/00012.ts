type Chainable<T = {}> = {
    option<K extends string, V>(
        // K extends keyof T ? never : K：防止重复定义相同的 key
        key: K extends keyof T ? never : K,
        value: V
    ): Chainable<T & Record<K, V>>; // 返回自身，来支持链式调用
    // Record<K, V> 创建了一个具有单个属性 K 的对象类型，其值类型为 V
    // T & Record<K, V> 代表之前的对象联合目前这个key-value组成新对象

    get(): T;
};


declare const config: Chainable

const result = config
    .option('foo', 123)
    .option('name', 'type-challenges')
    .option('bar', { value: 'Hello World' })
    .get()

// 期望 result 的类型是：
interface Result {
    foo: number
    name: string
    bar: {
        value: string
    }
}