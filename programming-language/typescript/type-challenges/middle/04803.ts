type TrimRight<S extends string> = S extends `${infer Rest} ` ? TrimRight<Rest> : S;

// Example test case:
type Trimed = TrimRight<'   Hello World    '>; // expected to be '   Hello World'
