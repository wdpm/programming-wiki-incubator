const fn = (v: boolean) => {
    if (v)
        return 1
    else
        return 2
}

type MyReturnType<T extends (...args: any) => any> = T extends (...args: any) => infer P ? P : never;

type a = MyReturnType<typeof fn> // 应推导出 "1 | 2"