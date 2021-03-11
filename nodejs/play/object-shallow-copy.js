let obj1 = {a: 1}

// only copy reference
let obj2 = obj1;

obj1.b = 2;

console.log(obj1)
console.log(obj2)