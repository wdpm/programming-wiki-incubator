# handbook
> https://www.typescriptlang.org/docs/handbook/ at 2020.11.25

A reader who completes the walkthrough should be able to:

- Read and understand commonly-used TypeScript syntax and patterns
- Explain the effects of important compiler options
- Correctly predict type system behavior in most cases
- Write a .d.ts declaration for a simple function, object, or class

## basic types
### Boolean
```
let isDone: boolean = false;
```
### Number
```
let decimal: number = 6;
```
### String
```
let color: string = "blue";
```
### Array
```
let list: number[] = [1, 2, 3];
let list: Array<number> = [1, 2, 3];
```
### Tuple
```
let x: [string, number];
x = ["hello", 10]; // OK
```
### Enum
```
enum Color {
  Red,
  Green,
  Blue,
}
let c: Color = Color.Green;
```
### Unknown
```
let notSure: unknown = 4;
notSure = "maybe a string instead";

// OK, definitely a boolean
notSure = false;
```
### Any
```
declare function getValue(key: string): any;
// OK, return value of 'getValue' is not checked
const str: string = getValue("myString");
```
### Void
```
function warnUser(): void {
  console.log("This is my warning message");
}
```
```
let unusable: void = undefined;
// OK if `--strictNullChecks` is not given
unusable = null;
```
### Null and Undefined
```
// Not much else we can assign to these variables!
let u: undefined = undefined;
let n: null = null;
```
### Never
```
// Function returning never must not have a reachable end point
function error(message: string): never {
  throw new Error(message);
}
```
### Object
```
declare function create(o: object | null): void;

// OK
create({ prop: 0 });
create(null);
```
### Type assertions
> 类型断言就像其他语言中的类型转换一样，但是它不执行任何特殊的数据检查或重构
```
let someValue: unknown = "this is a string";

// 信我，someValue肯定是string类型没错，大胆使用.length属性
let strLength: number = (someValue as string).length;
```

## interfaces
### 例子
```
interface LabeledValue {
  label: string;
}

function printLabel(labeledObj: LabeledValue) {
  console.log(labeledObj.label);
}

let myObj = { size: 10, label: "Size 10 Object" };
printLabel(myObj);
```
### 可选属性
```typescript
interface SquareConfig {
  color?: string;
  width?: number;
}
```
### 只读属性
```typescript
interface Point {
  readonly x: number;
  readonly y: number;
}
```
> 变量使用const，而属性使用readonly。

### Excess Property Checks
```typescript
interface SquareConfig {
  color?: string;
  width?: number;
  [propName: string]: any;
}
```
### Function Types
```typescript
interface SearchFunc {
  (source: string, subString: string): boolean;
}
```
```typescript
let mySearch: SearchFunc;

mySearch = function (source: string, subString: string) {
  let result = source.search(subString);
  return result > -1;
};
```
### Indexable Types
> 可索引类型
```typescript
interface StringArray {
  [index: number]: string;
}

let myArray: StringArray;
myArray = ["Bob", "Fred"];

let myStr: string = myArray[0];
```
> readonly property
```typescript
interface ReadonlyStringArray {
  readonly [index: number]: string;
}

let myArray: ReadonlyStringArray = ["Alice", "Bob"];
myArray[2] = "Mallory"; // error!
```

### Class Types
```typescript
interface ClockInterface {
  currentTime: Date;
}

class Clock implements ClockInterface {
  currentTime: Date = new Date();
  setTime(d: Date) {
    this.currentTime = d;
  }
  constructor(h: number, m: number) {}
}
```
```typescript
interface ClockConstructor {
  new (hour: number, minute: number): ClockInterface;
}

interface ClockInterface {
  tick(): void;
}

// 这个是实用函数，帮助创建instance
function createClock(
  ctor: ClockConstructor,
  hour: number,
  minute: number
): ClockInterface {
  return new ctor(hour, minute);
}

class DigitalClock implements ClockInterface {
  constructor(h: number, m: number) {}
  tick() {
    console.log("beep beep");
  }
}

class AnalogClock implements ClockInterface {
  constructor(h: number, m: number) {}
  tick() {
    console.log("tick tock");
  }
}

let digital = createClock(DigitalClock, 12, 17);
let analog = createClock(AnalogClock, 7, 32);
```
```typescript
interface ClockConstructor {
  new (hour: number, minute: number): ClockInterface;
}

interface ClockInterface {
  tick(): void;
}

//或者，使用类表达式
const Clock: ClockConstructor = class Clock implements ClockInterface {
  constructor(h: number, m: number) {}
  tick() {
    console.log("beep beep");
  }
};
```
### Extending Interfaces
> interface支持继承多个interfaces
```typescript
interface Shape {
  color: string;
}

interface PenStroke {
  penWidth: number;
}

interface Square extends Shape, PenStroke {
  sideLength: number;
}

let square = {} as Square;
square.color = "blue";
square.sideLength = 10;
square.penWidth = 5.0;
```
### Hybrid Types
```typescript
interface Counter {
  (start: number): string; // func
  interval: number; //property
  reset(): void;
}

function getCounter(): Counter {
  let counter = function (start: number) {} as Counter;
  counter.interval = 123;
  counter.reset = function () {};
  return counter;
}

let c = getCounter();
c(10);
c.reset();
c.interval = 5.0;
```
### Interfaces Extending Classes
> interfaces inherit even the private and protected members of a base class. 
> This means that when you create an interface that extends a class with private or protected members,
> that interface type can only be implemented by that class or a subclass of it.
```typescript
class Control {
  private state: any;
}

interface SelectableControl extends Control {
  select(): void;
}

class Button extends Control implements SelectableControl {
  select() {}
}

class TextBox extends Control {
  select() {}
}

class ImageControl implements SelectableControl {
//Class 'ImageControl' incorrectly implements interface 'SelectableControl'.
 // Types have separate declarations of a private property 'state'.
  private state: any;// 这里有问题，冲突
  select() {}
}
```
## Functions
```typescript
function add(x: number, y: number): number {
  return x + y;
}

let myAdd = function (x: number, y: number): number {
  return x + y;
};
```
### Writing the function type
```typescript
// (x: number, y: number) => number 是函数入参和返回值的定义
let myAdd: (x: number, y: number) => number = function (
  x: number,
  y: number
): number {
  return x + y;
};
```
### Inferring the types
```typescript
// myAdd has the full function type
let myAdd2: (baseValue: number, increment: number) => number = function (x, y) {
  return x + y;
};
```
### Optional and Default Parameters
```typescript
function buildName(firstName: string, lastName?: string) {
  if (lastName) return firstName + " " + lastName;
  else return firstName;
}

let result1 = buildName("Bob"); // works correctly now
let result2 = buildName("Bob", "Adams", "Sr."); // error, too many parameters
//Expected 1-2 arguments, but got 3.
let result3 = buildName("Bob", "Adams"); // ah, just right
```
```typescript
function buildName(firstName: string, lastName = "Smith") {
  return firstName + " " + lastName;
}

let result1 = buildName("Bob"); // works correctly now, returns "Bob Smith"
let result2 = buildName("Bob", undefined); // still works, also returns "Bob Smith"
```
### Rest Parameters
```typescript
function buildName(firstName: string, ...restOfName: string[]) {
  return firstName + " " + restOfName.join(" ");
}

// employeeName will be "Joseph Samuel Lucas MacKinzie"
let employeeName = buildName("Joseph", "Samuel", "Lucas", "MacKinzie");
```
### this and arrow function
```typescript
let deck = {
  suits: ["hearts", "spades", "clubs", "diamonds"],
  cards: Array(52),
  createCardPicker: function () {
    // NOTE: the line below is now an arrow function, allowing us to capture 'this' right here
    return () => {
      let pickedCard = Math.floor(Math.random() * 52);
      let pickedSuit = Math.floor(pickedCard / 13);

      // this refers to deck object
      return { suit: this.suits[pickedSuit], card: pickedCard % 13 };
    };
  },
};

let cardPicker = deck.createCardPicker();
let pickedCard = cardPicker();

alert("card: " + pickedCard.card + " of " + pickedCard.suit);
```
### Overloads
```typescript
let suits = ["hearts", "spades", "clubs", "diamonds"];

function pickCard(x: { suit: string; card: number }[]): number;
function pickCard(x: number): { suit: string; card: number };
function pickCard(x: any): any {
  // Check to see if we're working with an object/array
  // if so, they gave us the deck and we'll pick the card
  if (typeof x == "object") {
    let pickedCard = Math.floor(Math.random() * x.length);
    return pickedCard;
  }
  // Otherwise just let them pick the card
  else if (typeof x == "number") {
    let pickedSuit = Math.floor(x / 13);
    return { suit: suits[pickedSuit], card: x % 13 };
  }
}

let myDeck = [
  { suit: "diamonds", card: 2 },
  { suit: "spades", card: 10 },
  { suit: "hearts", card: 4 },
];

let pickedCard1 = myDeck[pickCard(myDeck)];
alert("card: " + pickedCard1.card + " of " + pickedCard1.suit);

let pickedCard2 = pickCard(15);
alert("card: " + pickedCard2.card + " of " + pickedCard2.suit);
```
function pickCard(x: any): any 不属于重载列表, 它只有两个重载: 
- function pickCard(x: { suit: string; card: number }[]): number;
- function pickCard(x: number): { suit: string; card: number };

## Literal Types
### Literal Narrowing
```typescript
// So, TypeScript sets the type to be "Hello World" not string
const helloWorld = "Hello World";

// On the other hand, a let can change, and so the compiler declares it a string
let hiWorld = "Hi World";
```
### String Literal Types
```typescript
type Easing = "ease-in" | "ease-out" | "ease-in-out";
```
### Numeric Literal Types
```typescript
function rollDice(): 1 | 2 | 3 | 4 | 5 | 6 {
  return (Math.floor(Math.random() * 6) + 1) as 1 | 2 | 3 | 4 | 5 | 6;
}

const result = rollDice();
```
```typescript
interface MapConfig {
  lng: number;
  lat: number;
  tileSize: 8 | 16 | 32;
}
```
### Boolean Literal Types
```typescript
interface ValidationSuccess {
  isValid: true;
  reason: null;
}

interface ValidationFailure {
  isValid: false;
  reason: string;
}

type ValidationResult = ValidationSuccess | ValidationFailure;
```

## Unions and Intersection Types

### Union Types
```typescript
/**
 * Takes a string and adds "padding" to the left.
 * If 'padding' is a string, then 'padding' is appended to the left side.
 * If 'padding' is a number, then that number of spaces is added to the left side.
 */
function padLeft(value: string, padding: any) {
  if (typeof padding === "number") {
    return Array(padding + 1).join(" ") + value;
  }
  if (typeof padding === "string") {
    return padding + value;
  }
  throw new Error(`Expected string or number, got '${typeof padding}'.`);
}

padLeft("Hello world", 4); // returns "    Hello world"
```
Problem
```typescript
// passes at compile time, fails at runtime.
let indentedString = padLeft("Hello world", true);
```
How to fix
```typescript
/**
 * Takes a string and adds "padding" to the left.
 * If 'padding' is a string, then 'padding' is appended to the left side.
 * If 'padding' is a number, then that number of spaces is added to the left side.
 */
function padLeft(value: string, padding: string | number) {
  // ...
}

let indentedString = padLeft("Hello world", true);
// Argument of type 'boolean' is not assignable to parameter of type 'string | number'.
```
### Unions with Common Fields
```typescript
interface Bird {
  fly(): void;
  layEggs(): void;
}

interface Fish {
  swim(): void;
  layEggs(): void;
}

declare function getSmallPet(): Fish | Bird;

let pet = getSmallPet();
pet.layEggs();

// Only available in one of the two possible types
pet.swim();//Error
```
### Discriminating Unions
```typescript
type NetworkLoadingState = {
  state: "loading";
};

type NetworkFailedState = {
  state: "failed";
  code: number;
};

type NetworkSuccessState = {
  state: "success";
  response: {
    title: string;
    duration: number;
    summary: string;
  };
};

// Create a type which represents only one of the above types
// but you aren't sure which it is yet.
type NetworkState =
  | NetworkLoadingState
  | NetworkFailedState
  | NetworkSuccessState;
```
Usage
```typescript
function logger(state: NetworkState): string {
  // Right now TypeScript does not know which of the three
  // potential types state could be.

  // Trying to access a property which isn't shared
  // across all types will raise an error
  state.code;
// Property 'code' does not exist on type 'NetworkState'.
//   Property 'code' does not exist on type 'NetworkLoadingState'.

  // By switching on state, TypeScript can narrow the union
  // down in code flow analysis
  switch (state.state) {
    case "loading":
      return "Downloading...";
    case "failed":
      // The type must be NetworkFailedState here,
      // so accessing the `code` field is safe
      return `Error ${state.code} downloading`;
    case "success":
      return `Downloaded ${state.response.title} - ${state.response.summary}`;
  }
}
```

### Intersection Types
如果您的网络请求具有一致的错误处理，可将错误处理分离为自己的类型，并与对应于单个响应类型的类型合并。
```typescript
interface ErrorHandling {
  success: boolean;
  error?: { message: string };
}

interface ArtworksData {
  artworks: { title: string }[];
}

interface ArtistsData {
  artists: { name: string }[];
}

// These interfaces are composed to have
// consistent error handling, and their own data.

type ArtworksResponse = ArtworksData & ErrorHandling;
type ArtistsResponse = ArtistsData & ErrorHandling;

const handleArtistsResponse = (response: ArtistsResponse) => {
  if (response.error) {
    console.error(response.error.message);
    return;
  }

  console.log(response.artists);
};
```

## Classes
```typescript
class Greeter {
  greeting: string;

  constructor(message: string) {
    this.greeting = message;
  }

  greet() {
    return "Hello, " + this.greeting;
  }
}

let greeter = new Greeter("world");
```
### Inheritance
```typescript
class Animal {
  move(distanceInMeters: number = 0) {
    console.log(`Animal moved ${distanceInMeters}m.`);
  }
}

class Dog extends Animal {
  bark() {
    console.log("Woof! Woof!");
  }
}

const dog = new Dog();
dog.bark();
dog.move(10);
dog.bark();
```

### Public, private, and protected modifiers
Public by default
```typescript
class Animal {
  public name: string;

  public constructor(theName: string) {
    this.name = theName;
  }

  public move(distanceInMeters: number) {
    console.log(`${this.name} moved ${distanceInMeters}m.`);
  }
}
```
### ECMAScript Private Fields
```typescript
class Animal {
  #name: string;
  constructor(theName: string) {
    this.#name = theName;
  }
}

new Animal("Cat").#name;
// Property '#name' is not accessible outside class 'Animal' because it has a private identifier.
```

### TypeScript’s private
```typescript
class Animal {
  private name: string;

  constructor(theName: string) {
    this.name = theName;
  }
}

new Animal("Cat").name;//error
```

### protected
```typescript
class Person {
  protected name: string;
  constructor(name: string) {
    this.name = name;
  }
}

class Employee extends Person {
  private department: string;

  constructor(name: string, department: string) {
    super(name);
    this.department = department;
  }

  public getElevatorPitch() {
    return `Hello, my name is ${this.name} and I work in ${this.department}.`;
  }
}

let howard = new Employee("Howard", "Sales");
console.log(howard.getElevatorPitch());

console.log(howard.name);
// Property 'name' is protected and only accessible within class 'Person' and its subclasses.
```
A constructor may also be marked protected. 
This means that the class cannot be instantiated outside of its containing class, but can be extended.
```typescript
class Person {
  protected name: string;
  protected constructor(theName: string) {
    this.name = theName;
  }
}

// Employee can extend Person
class Employee extends Person {
  private department: string;

  constructor(name: string, department: string) {
    super(name);
    this.department = department;
  }

  public getElevatorPitch() {
    return `Hello, my name is ${this.name} and I work in ${this.department}.`;
  }
}

let howard = new Employee("Howard", "Sales");//ok
let john = new Person("John");//error
// Constructor of class 'Person' is protected and only accessible within the class declaration.
```

### Readonly modifier
> Readonly properties must be initialized at their declaration or in the constructor.
```typescript
class Octopus {
  readonly name: string;
  readonly numberOfLegs: number = 8;

  constructor(theName: string) {
    this.name = theName;
  }
}

let dad = new Octopus("Man with the 8 strong legs");//ok
dad.name = "Man with the 3-piece suit";
// Cannot assign to 'name' because it is a read-only property.
```

### Parameter properties
```typescript
class Octopus {
  readonly numberOfLegs: number = 8;
  constructor(readonly name: string) {}
}

let dad = new Octopus("Man with the 8 strong legs");
dad.name;
```
compiled code
```js
"use strict";
class Octopus {
    constructor(name) {
        this.name = name;
        this.numberOfLegs = 8;
    }
}
let dad = new Octopus("Man with the 8 strong legs");
dad.name;
```
### Accessors
```typescript
const fullNameMaxLength = 10;

class Employee {
  private _fullName: string = "";

  get fullName(): string {
    return this._fullName;
  }

  set fullName(newName: string) {
    if (newName && newName.length > fullNameMaxLength) {
      throw new Error("fullName has a max length of " + fullNameMaxLength);
    }

    this._fullName = newName;
  }
}

let employee = new Employee();
employee.fullName = "Bob Smith";

if (employee.fullName) {
  console.log(employee.fullName);
}
```
### Static Properties
```typescript
class Grid {
  static origin = { x: 0, y: 0 };

  calculateDistanceFromOrigin(point: { x: number; y: number }) {
    let xDist = point.x - Grid.origin.x;
    let yDist = point.y - Grid.origin.y;
    return Math.sqrt(xDist * xDist + yDist * yDist) / this.scale;
  }

  constructor(public scale: number) {}
}

let grid1 = new Grid(1.0); // 1x scale
let grid2 = new Grid(5.0); // 5x scale

console.log(grid1.calculateDistanceFromOrigin({ x: 10, y: 10 }));
console.log(grid2.calculateDistanceFromOrigin({ x: 10, y: 10 }));
```
### Abstract Classes
```typescript
abstract class Department {
  constructor(public name: string) {}

  printName(): void {
    console.log("Department name: " + this.name);
  }

  abstract printMeeting(): void; // must be implemented in derived classes
}

class AccountingDepartment extends Department {
  constructor() {
    super("Accounting and Auditing"); // constructors in derived classes must call super()
  }

  printMeeting(): void {
    console.log("The Accounting Department meets each Monday at 10am.");
  }

  generateReports(): void {
    console.log("Generating accounting reports...");
  }
}

let department: Department; // ok to create a reference to an abstract type
department = new AccountingDepartment(); // ok to create and assign a non-abstract subclass
department.printName();
department.printMeeting();
department.generateReports();// error
// Property 'generateReports' does not exist on type 'Department'.
```
### Constructor functions
```typescript
class Greeter {
  static standardGreeting = "Hello, there";
  greeting: string;
  greet() {
    if (this.greeting) {
      return "Hello, " + this.greeting;
    } else {
      return Greeter.standardGreeting;
    }
  }
}

let greeter1: Greeter;
greeter1 = new Greeter(); // greeter1 is the type of Greeter instance 
console.log(greeter1.greet()); // "Hello, there"

// greeterMaker is the type of the Greeter class itself
let greeterMaker: typeof Greeter = Greeter;
greeterMaker.standardGreeting = "Hey there!";//!! change Greeter class static member

let greeter2: Greeter = new greeterMaker();
console.log(greeter2.greet()); // "Hey there!"

// It is also good to mention that changing static property is frowned upon, 
// here greeter3 has "Hey there!" instead of "Hello, there" on standardGreeting.
let greeter3: Greeter;
greeter3 = new Greeter();
console.log(greeter3.greet()); // "Hey there!"
```

### Using a class as an interface
```typescript
class Point {
  x: number;
  y: number;
}

// class Point as an interface
interface Point3d extends Point {
  z: number;
}

let point3d: Point3d = { x: 1, y: 2, z: 3 };
```

## Enums
### Numeric enums
> 数值枚举
```typescript
enum Direction {
  Up,//0
  Down,//1
  Left,//2
  Right,//3
}
```
```typescript
enum UserResponse {
  No = 0,
  Yes = 1,
}
```

### String enums
```typescript
enum Direction {
  Up = "UP",
  Down = "DOWN",
  Left = "LEFT",
  Right = "RIGHT",
}
```
### Heterogeneous enums
> 异构枚举合法，但是不知道你想干什么
```typescript
enum BooleanLikeHeterogeneousEnum {
  No = 0,
  Yes = "YES",
}
```
### Computed and constant members
```typescript
enum FileAccess {
  // constant members
  None,
  Read = 1 << 1,
  Write = 1 << 2,
  ReadWrite = Read | Write,
  // computed member
  G = "123".length,
}
```
### Union enums and enum member types
```typescript
enum ShapeKind {
  Circle,
  Square,
}

interface Circle {
  kind: ShapeKind.Circle;
  radius: number;
}

interface Square {
  kind: ShapeKind.Square;
  sideLength: number;
}
```
### Enums at runtime
```typescript
enum E {
  X,
  Y,
  Z,
}

function f(obj: { X: number }) {
  return obj.X;
}

// Works, since 'E' has a property named 'X' which is a number.
f(E);
```

### Enums at compile time
```typescript
enum LogLevel {
  ERROR,
  WARN,
  INFO,
  DEBUG,
}

/**
 * This is equivalent to:
 * type LogLevelStrings = 'ERROR' | 'WARN' | 'INFO' | 'DEBUG';
 */
type LogLevelStrings = keyof typeof LogLevel;

function printImportant(key: LogLevelStrings, message: string) {
  const num = LogLevel[key];
  if (num <= LogLevel.WARN) {
    console.log("Log level key is:", key);
    console.log("Log level value is:", num);
    console.log("Log level message is:", message);
  }
}
printImportant("ERROR", "This is a message");
```
```
[LOG]: "Log level key is:",  "ERROR" 
[LOG]: "Log level value is:",  0 
[LOG]: "Log level message is:",  "This is a message" 
```
### Reverse mappings
numeric enums members also get a reverse mapping from enum values to enum names
```typescript
enum Enum {
  A,
}

let a = Enum.A;// value
let nameOfA = Enum[a]; // "A" is name
```
compiled code
```js
"use strict";
var Enum;
(function (Enum) {
    Enum[Enum["A"] = 0] = "A";
})(Enum || (Enum = {}));
let a = Enum.A;//0
let nameOfA = Enum[a]; // Enum[0] => "A"
```
### const enums
```typescript
const enum Direction {
  Up,
  Down,
  Left,
  Right,
}

let directions = [
  Direction.Up,
  Direction.Down,
  Direction.Left,
  Direction.Right,
];
```
equals to
```js
"use strict";
let directions = [
    0 /* Up */,
    1 /* Down */,
    2 /* Left */,
    3 /* Right */,
];
```

### Ambient enums
> 问题来了： B的值是什么？
```typescript
declare enum Enum {
  A = 1,
  B,
  C = 2,
}
```
In contrast, an ambient (and non-const) enum member that does not have initializer is always considered computed.

## Generics
```typescript
function identity<T>(arg: T): T {
  return arg;
}
```
```typescript
let output = identity<string>("myString");
//       ^ = let output: string
```
### Working with Generic Type Variables
```typescript
function loggingIdentity<T>(arg: T[]): T[] {
  console.log(arg.length);
  return arg;
}
```

### Generic Types
```typescript
function identity<T>(arg: T): T {
  return arg;
}

let myIdentity: <T>(arg: T) => T = identity;
let myIdentity2: { <T>(arg: T): T } = identity;
```
```typescript
interface GenericIdentityFn {
  <T>(arg: T): T;
}

function identity<T>(arg: T): T {
  return arg;
}

let myIdentity: GenericIdentityFn = identity;
```
```typescript
interface GenericIdentityFn<T> {
  (arg: T): T;
}

function identity<T>(arg: T): T {
  return arg;
}

let myIdentity: GenericIdentityFn<number> = identity;
```

### Generic Classes
```typescript
class GenericNumber<T> {
  zeroValue: T;
  add: (x: T, y: T) => T;
}

let myGenericNumber = new GenericNumber<number>();
myGenericNumber.zeroValue = 0;
myGenericNumber.add = function (x, y) {
  return x + y;
};
```

### Generic Constraints
```typescript
interface Lengthwise {
  length: number;
}

function loggingIdentity<T extends Lengthwise>(arg: T): T {
  console.log(arg.length); // Now we know it has a .length property, so no more error
  return arg;
}
```

### Using Type Parameters in Generic Constraints
```typescript
function getProperty<T, K extends keyof T>(obj: T, key: K) {
  return obj[key];
}

let x = { a: 1, b: 2, c: 3, d: 4 };

getProperty(x, "a");// works
getProperty(x, "m");// not works
// Argument of type '"m"' is not assignable to parameter of type '"a" | "b" | "c" | "d"'.
```

### Using Class Types in Generics
```typescript
function create<T>(c: { new (): T }): T {
  return new c();
}
```