interface Cat {
    type: 'cat'
    breeds: 'Abyssinian' | 'Shorthair' | 'Curl' | 'Bengal'
}

interface Dog {
    type: 'dog'
    breeds: 'Hound' | 'Brittany' | 'Bulldog' | 'Boxer'
    color: 'brown' | 'white' | 'black'
}

// 如果 Cat 满足 { type: 'dog' }，返回 Cat；否则返回 never。
// 如果 Dog 满足 { type: 'dog' }，返回 Dog；否则返回 never。
type LookUp<T, U> = T extends { type: U } ? T : never;


type MyDog = LookUp<Cat | Dog, 'dog'> // expected to be `Dog`