let obj1 = {a: 1}

// deep copy
let obj2 = JSON.parse(JSON.stringify(obj1));

obj1.b = 2;

console.log(obj1)
console.log(obj2)