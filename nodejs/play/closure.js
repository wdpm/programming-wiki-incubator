function foo() {
    let x = 0;

    return function increment() {
        console.log(++x)
    }
}

let increment = foo();
// increment().x; can't visit x field
increment()
increment()

