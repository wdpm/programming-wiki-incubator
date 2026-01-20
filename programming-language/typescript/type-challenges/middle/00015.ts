type arr1 = ['a', 'b', 'c']
type arr2 = [3, 2, 1]

type Last<T extends readonly any[]> = T extends [...infer _, infer L] ? L : never;


type tail1 = Last<arr1> // 应推导出 'c'
type tail2 = Last<arr2> // 应推导出 1