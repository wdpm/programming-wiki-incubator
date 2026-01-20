type tesla = ['tesla', 'model 3', 'model X', 'model Y']
type spaceX = ['FALCON 9', 'FALCON HEAVY', 'DRAGON', 'STARSHIP', 'HUMAN SPACEFLIGHT']

// 尝试判断是否有length属性，如果有，那么就是array-like类型，使用 infer 推导长度值到L变量中
type Length<T extends readonly any[]> = T extends { length: infer L }  ?  L : never;

type teslaLength = Length<tesla> // expected 4
type spaceXLength = Length<spaceX> // expected 5