# Functional Javascript

## TOC
```
  "Higher Order Functions",
  "Basic: Map",
  "Basic: Filter",
  "Basic: Every Some",
  "Basic: Reduce",
  "Basic: Recursion",
  "Basic: Call",
  "Partial Application without Bind",
  "Partial Application with Bind",
  "Implement Map with Reduce",
  "Function Spies",
  "Blocking Event Loop",
  "Trampoline",
  "Async Loops",
  "Recursion",
  "Currying",
  "Function Call"
```

## Contents

### Higher Order Functions
```js
function repeat(operation, num) {
  if (num <= 0) return
  operation()
  return repeat(operation, --num)
}

module.exports = repeat
```

### Basic: Map
```js
module.exports = function doubleAll(numbers) {
  return numbers.map(function double(num) {
    return num * 2
  })
}
```

### Basic: Filter

takes an array of objects with '.message' properties and returns an array of messages that are less than < 50 characters long.
```js
module.exports = function getShortMessages(messages) {
  return messages.filter(function(item) {
    return item.message.length < 50
  }).map(function(item) {
    return item.message
  })
}
```

### Basic: Every Some

Return a function that takes a list of valid users,
and returns a function that returns true if all of the supplied users exist in the original list of users.
```js
module.exports = function checkUsersValid(goodUsers) {
  return function allUsersValid(submittedUsers) {
    return submittedUsers.every(function(submittedUser) {
      return goodUsers.some(function(goodUser) {
        return goodUser.id === submittedUser.id
      })
    })
  }
}
```
### Basic: Reduce

Given an Array of strings, use Array#reduce to create an object that contains the number of times each string occurred in the array.
```js
function countWords(arr) {
  return arr.reduce(function(countMap, word) {
    countMap[word] = ++countMap[word] || 1 // increment or initialize to 1
    return countMap
  }, {}) // second argument to reduce initialises countMap to {}
}

module.exports = countWords
```

### Basic: Recursion
```js
function reduce(arr, fn, initial) {
  return (function reduceOne(index, value) {
    if (index > arr.length - 1) return value // end condition
    return reduceOne(index + 1, fn(value, arr[index], index, arr)) // calculate & pass values to next step
  })(0, initial) // IIFE. kick off recursion with initial values
}

module.exports = reduce
```

### Basic: Call

```js
function duckCount() {
  return Array.prototype.slice.call(arguments).filter(function(obj) {
    return Object.prototype.hasOwnProperty.call(obj, 'quack')
  }).length
}

module.exports = duckCount
```

### Partial Application without Bind
```js
var slice = Array.prototype.slice

function logger(namespace) {
  return function() {
    console.log.apply(console, [namespace].concat(slice.call(arguments)))
  }
}

module.exports = logger
```

### Partial Application with Bind
```js
module.exports = function(namespace) {
  return console.log.bind(console, namespace)
}
```
```js
var info = logger('INFO:')
info('this is an info message')
// INFO: this is an info message

var warn = logger('WARN:')
warn('this is a warning message', 'with more info')
// WARN: this is a warning message with more info
```

### Implement Map with Reduce
```js
module.exports = function arrayMap(arr, fn, thisArg) {
  return arr.reduce(function(acc, item, index, arr) {
    acc.push(fn.call(thisArg, item, index, arr))
    return acc
  }, [])
}
```

### Function Spies

Create a spy that keeps track of how many times a function is called.
```js
function Spy(target, method) {
  var originalFunction = target[method]

  // use an object so we can pass by reference, not value
  // i.e. we can return result, but update count from this scope
  var result = {
    count: 0
  }

  // replace method with spy method
  target[method] = function() {
    result.count++ // track function was called
    return originalFunction.apply(this, arguments) // invoke original function
  }

  return result
}

module.exports = Spy
```
```js
var spy = Spy(console, 'error')

console.error('calling console.error')
console.error('calling console.error')
console.error('calling console.error')

console.log(spy.count) // 3
```

### Blocking Event Loop

Modify the recursive repeat function provided in the boilerplate, such that it does not block the event loop 
(i.e. Timers and IO handlers can fire). This necessarily requires repeat to be asynchronous.
```js
function repeat(operation, num) {
  if (num <= 0) return

  operation()

  // release control every 10 or so iterations.
  // 10 is arbitrary.
  if (num % 10 === 0) {
    setTimeout(function() {
      repeat(operation, --num)
    })
  } else {
    repeat(operation, --num)
  }
}

module.exports = repeat
```

### Trampoline
```js
function repeat(operation, num) {
  return function() {
    if (num <= 0) return
    operation()
    return repeat(operation, --num)
  }
}

function trampoline(fn) {
  while(fn && typeof fn === 'function') {
    fn = fn()
  }
}

module.exports = function(operation, num) {
  trampoline(function() {
    return repeat(operation, num)
  })
}
```

### Async Loops

The order of the results in done must be the same as they were specified in userIds
```js
function loadUsers(userIds, load, done) {
  var completed = 0
  var users = []
  userIds.forEach(function(id, index) {
    load(id, function(user) {
      users[index] = user
      if (++completed === userIds.length) return done(users)
    })
  })
}

module.exports = loadUsers
```

### Recursion
Example
```js
var loremIpsum = {
  "name": "lorem-ipsum",
  "version": "0.1.1",
  "dependencies": {
    "optimist": {
      "version": "0.3.7",
      "dependencies": {
        "wordwrap": {
          "version": "0.0.2"
        }
      }
    },
    "inflection": {
      "version": "1.2.6"
    }
  }
}

getDependencies(loremIpsum) // => [ 'inflection@1.2.6', 'optimist@0.3.7', 'wordwrap@0.0.2' ]
```
```js
function getDependencies(mod, result) {
  result = result || []
  var dependencies = mod && mod.dependencies || []
  Object.keys(dependencies).forEach(function(dep) {
    var key = dep + '@' + mod.dependencies[dep].version
    if (result.indexOf(key) === -1) result.push(key)
    getDependencies(mod.dependencies[dep], result)
  })
  return result.sort()
}

module.exports = getDependencies
```

### Currying

curryN will take two parameters:
- fn: The function we want to curry.
- n: Optional number of arguments to curry. If not supplied, curryN should use the fn's arity as the value for n.
> the fn's arity: 在逻辑、数学及计算机科学里，函数或运算的元数是指所需的参数或算子的数量。关系的元数则是指其对应之笛卡儿积的维度。

Example
```js
function add3(one, two, three) {
  return one + two + three
}

var curryC = curryN(add3)
var curryB = curryC(1)
var curryA = curryB(2)
console.log(curryA(3)) // => 6
console.log(curryA(10)) // => 13

console.log(curryN(add3)(1)(2)(3)) // => 6
```
```js
function curryN(fn, n) {
  n = n || fn.length
  return function curriedN(arg) {
    if (n <= 1) return fn(arg)
    return curryN(fn.bind(this, arg), n - 1)
  }
}

module.exports = curryN
```

### Function Call
```js
// Explained:
// The value of `this` in Function.call is the function
// that will be executed.
//
// Bind returns a new function with the value of `this` fixed
// to whatever was passed as its first argument.
//
// Every function 'inherits' from Function.prototype,
// thus every function, including call, apply and bind 
// have the methods call apply and bind.
//
// Function.prototype.call === Function.call
module.exports = Function.call.bind(Array.prototype.slice)
```

### basic inheritance with object create

```js
module.exports = function(User) {
  function BetterUser() {
    User.apply(this, arguments)
  }
  // fix constructor
  BetterUser.prototype = Object.create(User.prototype, {constructor: BetterUser})

  //override toString()
  BetterUser.prototype.toString = function() {
    return '[BetterUser: '+this.displayName()+']'
  }

  return BetterUser
}
```
### basic inheritance without object create
```js
module.exports = function(User) {
  function BetterUser() {
    User.apply(this, arguments)
  }

  // here
  function C() {}
  C.prototype = User.prototype
  BetterUser.prototype = new C()

  BetterUser.prototype.toString = function() {
    return '[BetterUser: '+this.displayName()+']'
  }

  return BetterUser
}
```

### prototypical inheritance by hand

We're going to implement a rough analog of JavaScript's prototypical inheritance by hand,
 to ensure we fully understand exactly how prototypical inheritance fits together.
 
- New(Apple, 1,2,3) == new Apple(1,2,3)
- obj.__PROTO__ == obj.proto || Object.getPrototypeOf(obj)
- Constructor.PROTOTYPE == Constructor.prototype

**Part 1: Lookup**
```js
var cat = {
  color: 'black'
}

var kitten = {
  size: 'small'
}

var otherKitten = {
  size: 'small',
  color: 'grey'
}

kitten.__PROTO__ = cat
otherKitten.__PROTO__ = cat

Lookup(kitten, 'color')  // => 'black'
Lookup(otherKitten, 'color')  // => 'grey'

Lookup(kitten, 'wings')  // => undefined

// changing properties on the prototype should
// affect any instances that inherit from it.
cat.color = 'blue'

Lookup(kitten, 'color')  // => 'blue'

// overridden properties should still work
Lookup(otherKitten, 'color')  // => 'grey'
```
**Part 2: Create**
```js
function Cat() {

}

Cat.PROTOTYPE.speak = function() {
  return 'Meow!'
}

function Kitten() {
  Cat.apply(this, arguments)
}

Kitten.PROTOTYPE = Create(Cat.PROTOTYPE)

var kitten = New(Kitten)
Lookup(kitten, 'speak')() // => 'Meow!'
```

**Finale: New**
```js
function Cat(color) {
  this.color = color
}

var blackCat = New(Cat, 'black')
blackCat.color // => black

var brownCat = New(Cat, 'brown')
brownCat.color // => brown
```
Result
```js
function Lookup(context, property) {
  if (!context) return undefined
  if (Object.prototype.hasOwnProperty.call(context, property)) return context[property]
  return Lookup(context.__PROTO__, property)
}

function Create(proto) {
  return {
    __PROTO__: proto
  }
}

function New(Type) {
  var obj = Create(Type.PROTOTYPE)
  var args = [].slice.call(arguments, 1) // remove Type arg
  var result = Type.apply(obj, args)
  if (typeof result !== 'undefined') return result
  return obj
}

module.exports = {
  Lookup: Lookup,
  Create: Create,
  New: New
}
```

