# TypeScript 入门

## 简介
- TypeScript 为 JavaScript 添加了静态类型定义。
- TypeScript 是 JavaScript 的超集。
- TypeScript 优势在于类型推断。
- Typescript 通过 babel 或者 TypeScript compiler(tsc) 转译代码。

## 第一个例子
`hello.js`
```js
function greet(person: string, date: Date) {
    console.log(`Hello ${person}, today is ${date.toDateString()}!`);
}
greet("Maddison", new Date());
```
---
```bash
tsc hello.ts
```
```js
function greet(person, date) {
    console.log("Hello " + person + ", today is " + date.toDateString() + "!");
}
greet("Maddison", new Date());

```
---
```bash
tsc --target es2015 hello.ts
```
```js
function greet(person, date) {
    console.log("Hello " + person + ", today is " + date.toDateString() + "!");
}
greet("Maddison", new Date());

```
注意，转译后，参数类型会被擦除。`(person: string, date: Date)` => `(person, date)`

## 严格模式
- noImplicitAny 拒绝Any类型
- strictNullChecks 严格Null值检测

## 类型声明
接口声明
```
interface User {
  name: string;
  id: number;
}
```
对类使用接口声明
```
class UserAccount {
  name: string;
  id: number;

  constructor(name: string, id: number) {
    this.name = name;
    this.id = id;
  }
}

const user: User = new UserAccount("Murphy", 1);
```
定义方法
```
function getAdminUser(): User {
  //...
}

function deleteUser(user: User) {
  // ...
}
```
组合类型
```typescript
function getLength(obj: string | string[]) {
  return obj.length;
}
```

|Type	|Predicate|
|---|---|
|string	|typeof s === "string"|
|number	|typeof n === "number"|
|boolean	|typeof b === "boolean"|
|undefined|	typeof undefined === "undefined"|
|function	|typeof f === "function"|
|array	|Array.isArray(a)|

泛型
```typescript
interface Backpack<Type> {
  add: (obj: Type) => void;
  get: () => Type;
}
```
```typescript
// This line is a shortcut to tell TypeScript there is a
// constant called `backpack`, and to not worry about where it came from.
declare const backpack: Backpack<string>;

// object is a string
const object = backpack.get();
```
结构类型系统
```
interface Point {
  x: number;
  y: number;
}

function printPoint(p: Point) {
  console.log(`${p.x}, ${p.y}`);
}

// prints "12, 26"
const point = { x: 12, y: 26 };// point is not Point type
printPoint(point);
```

## JS类型与TS类型
| JS类型| 描述 |对应TS类型|
|---|---|---|
|Number|a double-precision IEEE 754 floating point.|number|
|String	|an immutable UTF-16 string.|string|
|BigInt|	integers in the arbitrary precision format.|bigint|
|Boolean|	true and false.|boolean|
|Symbol	|a unique value usually used as a key.|symbol|
|Null	|equivalent to the unit type.|null|
|Undefined|	also equivalent to the unit type.|undefined|
|Object	|similar to records.|object|

Other important TypeScript types

|Type	|Explanation|
|---|---|
|unknown|	the top type.|
|never	|the bottom type.|
|object |literal	eg { property: Type }|
|void	|a subtype of undefined intended for use as a return type.|
|T[]	|mutable arrays, also written `Array<T>`|
|[T, T]	|tuples, which are fixed-length but mutable|
|(t: T) => U|	functions|

## Unit types
例如，字符串“ foo”的类型为“ foo”
```
declare function pad(s: string, n: number, direction: "left" | "right"): string;
pad("hi", 10, "left");//ok

let s = "right"; // s is widen to string type!
pad("hi", 10, s); // error: 'string' is not assignable to '"left" | "right"'

let s: "left" | "right" = "right";// shorten s type range
pad("hi", 10, s);//ok
```

## 学习资源
- [x] handbook|https://www.typescriptlang.org/docs/handbook/intro.html