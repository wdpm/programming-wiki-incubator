type Reverse<T extends unknown[]> = T extends [infer F, ...infer R] ? [...Reverse<R>, F] : [];

type FlipArguments<T extends (...args: any[]) => any> = T extends (...args: infer P) => infer U
    ? (...args: Reverse<P>) => U
    : never;


type Flipped = FlipArguments<(arg0: string, arg1: number, arg2: boolean) => void>
// (arg0: boolean, arg1: number, arg2: string) => void