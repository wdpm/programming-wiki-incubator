type ReplaceAll<T extends string, From extends string, To extends string> =
    From extends '' // 如果 From 是空字符串，则不替换
        ? T
        : T extends `${infer Prefix}${From}${infer Suffix}` // 匹配 From 在字符串中的位置
            ? ReplaceAll<`${Prefix}${To}${Suffix}`, From, To> // 替换后递归处理剩余部分
            : T; // 如果没有匹配，返回原字符串

type replaced = ReplaceAll<'t y p e s', ' ', ''> // 期望是 'types'