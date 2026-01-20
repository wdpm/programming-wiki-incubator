type Replace<T extends string, From extends string, To extends string> =
    From extends '' // 如果 From 是空字符串，则不替换
        ? T
        : T extends `${infer Prefix}${From}${infer Suffix}` // 匹配 From 在字符串中的位置
            ? `${Prefix}${To}${Suffix}` // 将 From 替换为 To
            : T; // 如果没有匹配，返回原字符串

type replaced = Replace<'types are fun!', 'fun', 'awesome'> // 期望是 'types are awesome!'