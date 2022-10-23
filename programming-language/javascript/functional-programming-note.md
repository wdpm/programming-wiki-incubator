## 《函数式轻量JS编程》笔记

一旦你学会了 map(..) 所做的事情，当你在任何程序中看到它时，你就可以几乎是立即发现并理解它。但你每次遇到 for 循环时，你就不得不阅读整个循环来搞懂它。for 循环的语法可能令人倍感亲切，但它所做的实质可不是这样；它不得不每次都被 读 一遍。

仅仅因为你 能 FP 某些东西，不意味着你 应当 FP 它

我们不应满足于此。我们应当在测试运行之前就 *知道* 它能够通过，而且我们有绝对的自信说为了其他读者（包括我们未来的自己）的利益，我们已经将所有的想法在代码中交代清楚了。

这就是轻量函数式 JavaScript 的核心。它的目标是学习如何高效地与我们的代码交流，而不必翻过符号或术语的大山来做到这一点。

如果你打算进行函数式编程，你就应当尽可能多地使用函数，而尽可能地避免过程。你所有的 function 都应当接收输入并返回输出。

元（arity）。元是函数声明中形式参数的数量。

我们都应当努力使用声明式的、自解释的代码。

隐含的函数输出在 FP 世界中有一个特殊名称：副作用（side effects）。而一个 没有副作用 的函数也有一个特殊名称：纯函数（pure function）。要点是，我们将尽一切可能优先使用纯函数并避免副作用。

函数的函数。

```jsx
function forEach(list,fn) {
    for (let v of list) {
        fn( v );
    }
}
```

当内部函数引用外部函数的一个变量时，这称为闭包（closure）。

连续的函数调用中指定输入的技术在 FP 中非常常见，而且拥有两种形式：局部应用（partial application）与柯里化（currying）。

闭包来记住函数值。

```jsx
function formatter(formatFn) {
    return function inner(str){
        return formatFn( str );
    };
}

var lower = formatter( function formatting(v){
    return v.toLowerCase();
} );
```

使用 identity(..) 当做判断函数，或者作为默认函数。

函数 `getOrder(data,cb)` 是函数 `ajax(url,data,cb)` 的一个 *局部应用*。局部应用严格地说是一个函数元的约减；记住，元是被期待的形式参数输入的数量。我们为了函数 `getOrder(..)` 而将原函数 `ajax(..)` 的元从 3 减至 2

```jsx
function partial(fn,...presetArgs) {
    return function partiallyApplied(...laterArgs){
        return fn( ...presetArgs, ...laterArgs );
    };
}
```

```jsx
[1,2,3,4,5].map( function adder(val){
    return add( 3, val );
} );
// [4,5,6,7,8]
```

不能直接向 map(..) 传递 add(..) 的原因是因为 add(..) 的签名与 map(..) 所期待的映射函数不符。这就是局部应用能帮到我们的地方：我们可以将 add(..) 的签名适配为某种吻合的东西。

```jsx
curriedSum = v1 => v2 => v3 => v4 => v5 => sum( v1, v2, v3, v4, v5 );
```

- 当参数在不同的时间与位置被声明时，柯里化与局部应用都允许你在时间/空间上进行分离（在你的整个代码库中），而传统函数调用要求所有的参数都被提前准备好

局部应用是一种降元（一个函数期待的实际参数个数）技术，它是通过创建一个已预设一部分参数的新函数来做到的。

柯里化是局部应用的一种特殊形式，它将元数降低为 1 —— 使用一个连续的函数调用链条，每个调用接收一个参数

懒惰求值

```jsx
function compose(...fns) {
    return fns.reverse().reduce( function reducer(fn1,fn2){
        return function composed(...args){
            return fn2( fn1( ...args ) );
        };
    } );
}
```

抽象是一种由程序员进行的处理，他们把一个名称与一个潜在的复杂程序片段联系起来，之后这个片段就可以用一个函数的目的来考虑，而不是以这个函数是如何实现的来考虑

函数组合是用于定义一个函数的模式，这个函数将一个函数调用的输出导入另一个函数调用，然后它的输出再导入另一个函数，以此类推。

因为 JS 函数只能返回一个值，所以这种模式实质上规定了在这个组合中的所有函数（也许除了第一个调用以外）都必须是一元的，仅从前一个函数的输出中接收一个作为输入

函数副作用。

```jsx
var x = 1;

foo();

console.log( x );

bar();

console.log( x );

baz();

console.log( x );
```

如果不能确定foo这些函数会不会修改x值，那么根本确定不了console.log的输出结果。

数学的幂等性。Math.abs(-2) 是 2，它的结果与 Math.abs(Math.abs(Math.abs(Math.abs(-2)))) 相同。

一个引用透明的纯函数 可以 被它的输出替换的概念不意味着它 就应当被 替换掉。

幂等性和纯函数，可以有效避免函数副作用。

当一个值改变的情况很少发生或不频繁，而且不太需要关心性能的时候，我推荐轻量级的解决方案，使用早先讨论的內建 Object.freeze(..)

闭包和对象是状态（以及与之关联的功能）的同构表现形式。

三个常见而且强大的列表操作：

- `map(..)`：将值投射到新列表中时将其变形。
- `filter(..)`：将值投射到新列表中时选择或排除它。
- `reduce(..)`：将一个列表中的值结合为另一个值（通常但不总是数组）。

其他几个可能在列表处理中非常有用的高级操作：`unique(..)`、`flatten(..)`、和 `merge(..)`。

- 一个数组上的 `map(..)` 对当前数组中的每一个值运行映射函数，将所有映射出来的值放入一个结果数组。
- 一个 Observable 上的 `map(..)` 为每一个值运行映射函数，无论它什么时候到来，并将所有映射出的值推送到输出 Observable。

换言之，如果对 FP 操作来说一个数组是一个急切的数据结构，那么一个 Observable 就是它对应的懒惰跨时段版本。

教条之上的平衡以及实用主义。

### **书籍**

一些你绝对应该读的 FP/JavaScript 书籍：

- [JavaScript Allongé](https://leanpub.com/javascript-allonge) by [Reg Braithwaite](https://twitter.com/raganwald)
- [Functional JavaScript](http://shop.oreilly.com/product/0636920028857.do) by [Michael Fogus](https://twitter.com/fogus)

### 库

附录 C 更深入地检视了这些库 和其他的一些库。

[https://github.com/getify/Functional-Light-JS/blob/1ed-zh-CN/manuscript/apC.md/#stuff-to-investigate](https://github.com/getify/Functional-Light-JS/blob/1ed-zh-CN/manuscript/apC.md/#stuff-to-investigate)