// 计算字符串的长度，类似于 String#length

type StringLength<S extends string, Count extends any[] = []> =
    S extends `${infer First}${infer Rest}`
        ? StringLength<Rest, [...Count, First]> // 每次递归计数
        : Count['length']; // 最终返回计数数组的长度
// StringLength<Rest, [...Count, First]> 每次递归都将首字符放入Count集合中，直到Rest为空
// 最后取Count数组的length属性

// 测试用例
type Length1 = StringLength<"">; // 0
type Length2 = StringLength<"hello">; // 5
type Length3 = StringLength<"typescript">; // 10