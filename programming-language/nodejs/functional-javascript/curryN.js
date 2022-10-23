function curryN(fn, n) {
    n = n || fn.length
    return function curriedN(arg) {
        if (n <= 1) return fn(arg)
        return curryN(fn.bind(this, arg), n - 1)
    }
}

function add3(one, two, three) {
    return one + two + three
}

let curryC = curryN(add3); // n=3
let curryB = curryC(1);// n=2
let curryA = curryB(2);// n=1
console.log(curryA(3)) // => 6
console.log(curryA(10)) // => 13

console.log(curryN(add3)(1)(2)(3)) // => 6