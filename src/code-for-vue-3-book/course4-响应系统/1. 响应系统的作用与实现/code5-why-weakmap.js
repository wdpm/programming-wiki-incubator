const map = new Map();
const weakmap = new WeakMap();

(function () {
  const foo = {foo: 1};
  const bar = {bar: 2};

  map.set(foo, 1);
  weakmap.set(bar, 2);
})()

// 对于weakmap，一旦 key 被垃圾回收器回收，那么对应的键和值就访问不到了

console.log(map)
console.log(weakmap)
//Map(1) { { foo: 1 } => 1 }
//WeakMap { <items unknown> }