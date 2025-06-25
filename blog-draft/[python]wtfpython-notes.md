# wtfpython 阅读笔记

- 来源：[wtfpython-cn](https://github.com/leisurelicht/wtfpython-cn)
- 实验环境：python 3.10

## 字符串驻留

```python
a = "some_string"
print(id(a) == id("some" + "_" + "string"))
# True
```

会尝试使用已经存在的不可变字符串对象而不是每次都创建一个新对象。问题是哪些字符串对象会被重用呢？

下面是一些扩展例子：

```python
a = "some_string"
print(id(a) == id("some" + "_" + "string"))
# 字符串在编译时实现的被驻留
# True

b = ""
c = ""
print(id(b) == id(c))
# 空字符串一致
# True

b = "s"
c = "s"
print(id(b) == id(c))
# 常规字符串内容一致的被驻留
# True

b = "abc"
c = "".join(["a", "b", "c"])
print(id(b) == id(c))
# join 方式创建的是新字符串对象，编译器没法优化到运行时的对象
# False
```

## 隐式的链式操作

```python
r1 = False == False in [False]
print(r1)
# True
```

这里思考为何为 True 结果。 上面的操作等价于下面的代码：

```python
r2 = (False == False) and (False in [False])
print(r2)
# True

# a op1 b op2 c ... y opN z 就等于 a op1 b and b op2 c and ... y opN z
```

## is 和 == 区别

```python
a = 257
b = 257
print(a is b)
# True

c, d = [], []
print(c is d)
print(c == d)
# False
# True
```

- is 运算符检查两个运算对象是否引用自同一对象 (对比两个算对象的内存地址)。
- == 运算符比较两个运算对象的值是否相等。

上面是在 py 文件运行的结果。如果是在 REPL 环境，例如 python console，那么要留意小整数驻留。

```bash
PyDev console: starting.
Python 3.10.11 (tags/v3.10.11:7d4cc5a, Apr  5 2023, 00:38:17) [MSC v.1929 64 bit (AMD64)] on win32
>>> a = 257
>>> b = 257
>>> a is b
False
```

- REPL：严格遵循 -5 到 256 驻留，更大整数 is 比较为 False。
- .py 文件：常量折叠优化可能导致所有整数 is 比较为 True（依赖编译器优化）。

## dict 中相同的 key

```python
some_dict = {}
some_dict[5.5] = "JavaScript"
some_dict[5.0] = "Ruby"
some_dict[5] = "Python"

print(some_dict)
# {5.5: 'JavaScript', 5.0: 'Python'}
```

可以看到 Ruby 的值被覆盖掉了，说明对于 python dict 中的 key，5.0 和 5 被认为是一致的哈希寻址。

解释：Python 字典中键的唯一性是根据等价性，而不是同一性。 因此，即使 5、5.0 和 5 + 0j 是不同类型的不同对象，由于它们是相等的，它们不能都在同一个
dict（或 set）中。

但是，上面的 dict 还有一个小毛病，虽然 python 值覆盖了 Ruby，但是对应的 key 保持了原来的 5.0。这令人疑惑，为何值覆盖时为何不将
key 一起更新。

## 临时对象的内存重用

```python
class WTF:
    pass


# 1. 对象相等性比较
print(WTF() == WTF())  # False

# 2. 对象身份比较
print(WTF() is WTF())  # False

# 3. 哈希值比较
print(hash(WTF()) == hash(WTF()))  # True

# 4. 内存地址比较
print(id(WTF()) == id(WTF()))  # True
```

- WTF()== WTF() 返回 False
    - 默认情况下，自定义类的实例使用 object 的 __eq__ 方法
    - object.__eq__ 的实现相当于 is 比较（即比较内存地址）
    - 因为每次 WTF() 都会创建新对象，所以返回 False
- WTF()is WTF() 返回 False
    - is 运算符直接比较两个对象的内存地址
    - 每次调用 WTF() 都会创建新的独立对象
    - 因此它们的身份不同，返回 False
- hash(WTF()) == hash(WTF()) 返回 True
    - 默认情况下，自定义类的实例使用 object 的 __hash__ 方法
    - object.__hash__ 是基于对象的内存地址计算的（使用 id()）
    - 但是这里的关键点是：**Python 重用临时对象的内存地址 **
    - 第一个 WTF()创建后立即被丢弃，第二个 WTF() 可能重用相同的内存地址，因此它们的哈希值相同
- id(WTF()) == id(WTF()) 返回 True
    - 与哈希值的情况类似，第一个临时对象被创建后立即销毁，第二个可能重用相同的内存地址，因此 id() 返回的值可能相同
    - 这是 CPython 的内存管理优化行为

为了更加直观观察对象的生命周期，我们可以修改 WTF 类，打印生命周期。

```python
class WTF(object):
    def __init__(self): print("I")

    def __del__(self): print("D")
```

打印结果：

```
I
I
D
D
False
I
I
D
D
False
I
D
I
D
True
I
D
I
D
True
```

结论：Python 解释器（特别是 CPython）会对短暂存在的临时对象进行内存优化。当一个对象没有引用后立即被销毁，新创建的临时对象可能会重用相同的内存地址。
这才是导致上面打印结果不一致的根本原因。

## try...finally 的执行顺序

```python
def some_func():
    try:
        return 'from_try'
    finally:
        return 'from_finally'


def another_func():
    for _ in range(3):
        try:
            continue
        finally:
            print("Finally!")


def one_more_func():  # A gotcha!
    try:
        for i in range(3):
            try:
                1 / i
            except ZeroDivisionError:
                # Let's throw it here and handle it outside for loop
                raise ZeroDivisionError("A trivial divide by zero error")
            finally:
                print("Iteration", i)
                break
    except ZeroDivisionError as e:
        print("Zero division error occurred", e)


print(some_func())
# from_finally

another_func()
# Finally!
# Finally!
# Finally!

one_more_func()
# Iteration 0
```

- 当在 "try...finally" 语句的 try 中执行 return, break 或 continue 后, finally 子句依然会执行.
- 函数的返回值由最后执行的 return 语句决定. 由于 finally 子句一定会执行, 所以 finally 子句中的 return 将始终是最后执行的语句.
- 注意，** 如果 finally 子句执行 return 或 break 语句，临时保存的异常将被丢弃 **。

## for 表达式的副作用：赋值

```python
some_string = "wtf"
some_dict = {}
for i, some_dict[i] in enumerate(some_string):
    pass

print(some_dict)
# {0: 'w', 1: 't', 2: 'f'}
```

[Python 语法](https://docs.python.org/3/reference/grammar.html) 中对 for 的定义是:
`for_stmt: 'for' exprlist 'in' testlist ':' suite ['else' ':' suite]`
其中 exprlist 指分配目标. 这意味着对可迭代对象中的每一项都会执行类似 {exprlist} = {next_value} 的操作。

等效于：

```bash
>>> i, some_dict[i] = (0, 'w')
>>> i, some_dict[i] = (1, 't')
>>> i, some_dict[i] = (2, 'f')
>>> some_dict
```

这个副作用在平常开发中可能会经常忘记。

## 生成器表达式的惰性求值与执行时机

in 子句和 if 子句的不同执行时机。

```python
array = [1, 8, 15]
g = (x for x in array if array.count(x) > 0)
array = [2, 8, 22]

print(list(g))
# [8]
```

- in 子句在声明时执行, 而条件子句则是在运行时执行。
- 在 list(g) 运行时, array 已经被重新赋值为 [2, 8, 22], 而对于之前的 1, 8 和 15, 只有 count(8) 的结果是大于 0 的,
  所以生成器只会生成 8。
- 实际执行代码是 (x for x in [1,8,15] if [2, 8, 22].count(x) > 0 )

最外层表达式 vs 内层表达式的执行时机。

```python
array_3 = [1, 2, 3]
array_4 = [10, 20, 30]
gen = (i + j for i in array_3 for j in array_4)

array_3 = [4, 5, 6]
array_4 = [400, 500, 600]
print(list(gen))
# [401, 501, 601, 402, 502, 602, 403, 503, 603]
```

- 只有最外层的 for 表达式会立即计算，其他表达式会延迟到生成器运行。

## is not 是个单独的二元运算符

```
>>> 'something' is not None
True
>>> 'something' is (not None)
False
```

- 'something' is (not None) => 'something' is True => False

## 谨慎对待数组的乘法复制

```python
# 初始化一个变量 row
row = [""] * 3  # ['', '', '']
# 乘法创建一个变量 board
board = [row] * 3

board[0][0] = "X"
print(board)
# [['X', '', ''], ['X', '', ''], ['X', '', '']]

board = [[''] * 3 for _ in range(3)]
board[0][0] = "X"
print(board)
# [['X', '', ''], ['', '', ''], ['', '', '']]
```

- `board = [row]*3` 通过对 row 做乘法来初始化 board, 内存中的情况：每个元素 board[0], board[1] 和 board[2] 都和 row
  一样引用了同一列表。
- 不使用变量 row 生成 board 可以避免这种情况。

##

```python
funcs = []
results = []
for x in range(7):
    def some_func():
        return x


    funcs.append(some_func)
    results.append(some_func())  # 这里函数被执行了

funcs_results = [func() for func in funcs]
print(funcs_results)
# [6, 6, 6, 6, 6, 6, 6]
```

some_func 中的 x 是闭包变量，来自循环变量。因此当延迟执行时，x 取循坏变量最终的值 6。

## 谁是万物起源

```python
print(isinstance(type, object))
print(isinstance(object, type))
# True
# True

print(isinstance(type, type))
print(isinstance(object, object))
# True
# True

print(issubclass(type, object))
print(issubclass(object, type))
# True
# False
```

现象：

- type 和 object 互为实例。
- type 和 object 都是各自本身的实例。
- type 是 object 的子类，但是 object 不是 type 的子类。

解释：

- Python 中，一切皆对象，其中包括类及其对象（实例）。
- 元类是用于定义类的类。 type 被用作元类，它决定了如何构造类的实例。
- object 和 type 之间的关系（既是彼此的实例，也是它们自己的实例）存在于 Python 中，这是源于实现层级上的“作弊”行为。

## 子类继承关系能传递吗？

```python
from collections.abc import Hashable

print(issubclass(list, object))
print(issubclass(object, Hashable))
print(issubclass(list, Hashable))
# True
# True
# False
```

一般而言，如果 A 是 B 的子类, B 是 C 的子类, 那么 A 应该是 C 的子类。

- Python 中的子类关系并不一定是传递的. 任何人都可以在元类中随意定义 __subclasscheck__.
- 当 issubclass(cls, Hashable) 被调用时, 它只是在 cls 中寻找 __hash__ 方法或者从继承的父类中寻找 __hash__ 方法.
- 由于 object 是可散列的 (hashable), 但是 list 是不可散列的, 所以打破了这种传递关系.

## 方法的唯一性和相等性

我们先回顾一下函数 function 和方法 method 这两个概念的区别：

- 函数就是函数，入参，返回值，函数名，函数修饰符。
- 方法是绑定了对象的函数，这个对象就是函数上下文。

```python
class SomeClass:
    def method(self):
        pass

    @classmethod
    def classm(cls):
        pass

    @staticmethod
    def staticm():
        pass


print(SomeClass.classm)
# <bound method SomeClass.classm of <class '__main__.SomeClass'>>
print(SomeClass.classm is SomeClass.classm)
# 与函数不同，classmethod 在作为类属性访问时也会创建一个方法（在这种情况下，它们绑定类，而不是类的类型）。
# 所以 SomeClass.classm is SomeClass.classm 是假的。
print(SomeClass.classm == SomeClass.classm)
# True
# 缓存绑定的 classmethod 对象？

o1 = SomeClass()
o2 = SomeClass()
print(o1.method)
print(o2.method)
# <bound method SomeClass.method of <__main__.SomeClass object at 0x000001C67F3D7DF0>>
# <bound method SomeClass.method of <__main__.SomeClass object at 0x000001C67F3D7DC0>>
# 相同的方法，绑定到不同的对象
print(o1.method == o2.method)
print(o1.method is o2.method)
# False
# False

print(o1.method == o1.method)
print(o1.method is o1.method)
# True
# False
# 多次访问该属性，每次都会创建一个方法对象！ 因此，o1.method is o1.method 永远不会是真的
```

访问 classm or method 两次, 为 SomeClass 的同一个实例创建了相等但是不同的对象。

## all 函数

```python
print(all([]))
# True
print(all([[]]))
# False
print(all([[[]]]))
# True
```

all 函数的实现等价于：

```python
def all(iterable):
    for element in iterable:
        if not element:
            return False
    return True
```

not ([[]]) 为 False，因此不会进入 if 分支，因此返回结果为 True。

## 字符串与反斜杠

```python
print(r"\")
```

反斜杠转义了尾随引号，使解析器没有终止引号（因此产生了 SyntaxError）。

## not 和 == 运算符优先级

```python
x = True
y = False
print(not x == y)
print(x ==
not y)
```

- `not x == y` 等价于 not (x == y) => not False => True
- `x == not y` 等价于 (x == not) y，语法错误。

## boolean 是 int 的实例

```python
# 一个简单的例子, 统计下面可迭代对象中的布尔型值的个数和整型值的个数
mixed_list = [False, 1.0, "some_string", 3, True, [], False]
integers_found_so_far = 0
booleans_found_so_far = 0

for item in mixed_list:
    if isinstance(item, int):
        integers_found_so_far += 1
    elif isinstance(item, bool):
        booleans_found_so_far += 1

# [False, 3, True, False]
print(integers_found_so_far)
# 4
print(booleans_found_so_far)
# 0
```

## 类属性和实例属性

```python
class SomeClass:
    another_list = [5]

    def __init__(self, x):
        # += 运算符会在原地修改可变对象, 而不是创建新对象. 在这种情况下, 修改一个实例的属性会影响其他实例和类属性.
        self.another_list += [x]


obj = SomeClass(10)
print(obj.another_list)
# [5, 10]

obj2 = SomeClass(100)
print(obj2.another_list)
# [5, 10, 100]
# 可以看到 another_list 是共享的，obj2 的 another_list 使用了 obj 之前写入的结果
```

如果想要避免 += 导致的诡异副作用，可以采用下面的方法：

- 在 __init__ 中 `self.another_list = [5]`，每个实例初始化自己的列表。
- 在 __init__ 中 `self.another_list = self.another_list.copy(`)，复制类属性。
- 在 __init__ 中 `self.another_list = list(self.another_list)`，显式创建新列表，本质也是复制。

## 生成器中的 return

```python
def some_func(x):
    if x == 3:
        return ["wtf"]
    else:
        yield from range(x)


print(list(some_func(3)))
# []
```

- 从 Python 3.3 开始，可以在生成器中使用带有值的 return 语句, 生成器中的 return expr 会导致在退出生成器时引发
  StopIteration(expr)。
- 在 some_func(3) 例子中，return 语句在开始就引发了 StopIteration。 StopIteration 异常会在 list(...) 包装器和 for 循环中自动捕获。
  因此，以上片段产生的是一个空列表。
- 要从生成器 some_func 中获取 ["wtf"]，我们需要捕获 StopIteration 异常。

```python
try:
    next(some_func(3))
except StopIteration as e:
    some_string = e.value
    print(some_string)
    # ['wtf']
```

## Nan 的自反性

```python
x = float('nan')
print(x == x, [x] == [x])
# False True
print(x is x)
```

x == x 返回 False

- 这是 IEEE 754 浮点数标准的规定：NaN 不等于任何值，包括它自身。
- 所有编程语言（如 Python、Java、C++）都遵循这一规则。

[x] == [x] 返回 True

- 列表的 == 比较是逐元素进行的，但列表比较不会深入检查 NaN 的相等性。
- 它只比较两个列表是否是相同的结构（长度相同，对应位置的元素“值相等”）。
- 由于 x 是同一个对象（内存地址相同），[x] == [x] 返回 True。

## 不是说 tuple 不可变吗

```python
another_tuple = ([1, 2], [3, 4], [5, 6])
try:
    another_tuple[2] += [99, 999]
except Exception as e:
    print(e)
    # 'tuple' object does not support item assignment

print(another_tuple)
# ([1, 2], [3, 4], [5, 6, 99, 999])
```

对于不可变对象 tuple, += 并不是原子操作, 而是 extend 和 = 两个动作, 这里 = 操作虽然会抛出异常, 但 extend 操作已经修改成功了。

上面的这行 `another_tuple[2] += [99, 999]` 等价于：

```
another_tuple[2].extend([99, 999]) # 正常运行
another_tuple[2] = another_tuple[2] # 报错
```

最后结果就是 another_tuple[2] 的内容还是改变了。结论：不可变对象的元素是允许 in-place 修改内容的。

## 消失的变量 e

```python
e = 7
try:
    raise Exception()
except Exception as e:
    pass

print(e)
# NameError: name 'e' is not defined
```

代码：

```
except E as N:
    foo
```

等价于

```
except E as N:
    try:
        foo
    finally:
        del N
```

当使用 as 为目标分配异常的时候, 将在 except 子句的末尾清除该异常。因此需要避免使用 as 后面的变量名称来命名常规变量。

## 连续 = 赋值你整明白了吗

```python
a, b = a[b] = {}, 5
print(a)
# {5: ({...}, 5)}
print(a[b][0] is a)
# True
```

上面的代码等价于：

```python
a, b = {}, 5
a[b] = a, b
```

这下看懂了吧。

## 整型转字符串越界

```python
int("2" * 5432)
# ValueError: Exceeds the limit (4300) for integer string conversion: value has 5432 digits; use sys.set_int_max_str_digits() to increase the limit
```

## 引用计数器

```python
import sys


class SomeClass:
    def __del__(self):
        print("Deleted!")


a = SomeClass()
b = a

# 获取引用计数（需要减去 sys.getrefcount() 本身的临时引用）
ref_count = sys.getrefcount(a) - 1
print(f"引用计数: {ref_count}")
# 2
```

## global and nonlocal

```python
a = 1


def another_func():
    global a
    a += 1
    return a


def another_closure_func():
    a = 1

    def another_inner_func():
        nonlocal a
        a += 1
        return a

    return another_inner_func()


print(another_func())
print(another_closure_func())
```

- global and nonlocal 关键字告诉 Python 解释器，不要声明新变量，而是在相应的外部作用域中查找变量。
- 如果不写这些关键字，就会报错 `UnboundLocalError: local variable 'a' referenced before assignment`。

## 迭代列表时删除元素

```python
list_2 = [1, 2, 3, 4]
list_3 = [1, 2, 3, 4]

for idx, item in enumerate(list_2):
    list_2.remove(item)

for idx, item in enumerate(list_3[:]):
    list_3.remove(item)

print(list_2)
# [2, 4]
print(list_3)
# []
```

在迭代时修改原对象是一个很愚蠢的主意. 正确的做法是迭代对象的副本, list_3[:] 的做法是正确的。

list_2 为什么输出是 [2, 4]?

- 列表迭代是按索引进行的, 当从 list_2 或 list_4 中删除 1 时, 列表的内容就变成了 [2, 3, 4]. 剩余元素会依次位移， 2 的索引会变为
  0, 3 会变为 1.
- 由于下一次迭代将获取索引为 1 的元素 (即 3), 因此 2 将被彻底的跳过. 类似的情况会交替发生在列表中的每个元素上.
- 熟悉 Java 的也会发现，这就是并发修改异常，它们内部在迭代时都在维护一个隐藏的 index 偏移。

## zip 丢失元素

```python
numbers = list(range(7))
first_three, remaining = numbers[:3], numbers[3:]
print(first_three, remaining)
numbers_iter = iter(numbers)
list(zip(numbers_iter, first_three))
# [(0, 0), (1, 1), (2, 2)]
# so far so good, let's zip the remaining

print(list(zip(numbers_iter, remaining)))
# 结果是 [(4, 3), (5, 4), (6, 5)]
# 为何不是 [(3, 3), (4, 4), (5, 5), (6, 6)]
```

当任一可迭代对象用尽时，zip 迭代结果列表中的现有元素将被丢弃。这就是 numbers_iter 中的 3 被抛弃的原因。

正确的迭代方式为：zip 的第一个参数应当是有最少元素数量的那个。

```python
numbers = list(range(7))
first_three, remaining = numbers[:3], numbers[3:]

numbers_iter = iter(numbers)
print(list(zip(first_three, numbers_iter)))
# [(0, 0), (1, 1), (2, 2)]
print(list(zip(remaining, numbers_iter)))
# [(3, 3), (4, 4), (5, 5), (6, 6)
```

## 小心默认的可变参数

```python
def some_func(default_arg=[]):
    default_arg.append("some_string")
    return default_arg


print(some_func())
# ['some_string']
print(some_func())
# ['some_string', 'some_string']
```

避免可变参数导致的错误的常见做法是将 None 指定为参数的默认值, 然后检查是否有值传给对应的参数，按需初始化。

```python
def some_func(default_arg=None):
    if default_arg is None:
        default_arg = []

    default_arg.append("some_string")
    return default_arg
```

## += 等价于 extend 和 =

```python
a = [1, 2, 3, 4]
b = a
a = a + [5, 6, 7, 8]
print(a)
# [1, 2, 3, 4, 5, 6, 7, 8]
print(b)
# [1, 2, 3, 4]

a = [1, 2, 3, 4]
b = a
a += [5, 6, 7, 8]
print(a)
# [1, 2, 3, 4, 5, 6, 7, 8]
print(b)
# [1, 2, 3, 4, 5, 6, 7, 8]
```

- 表达式 a = a + [5,6,7,8] 会生成一个新列表, 并让 a 引用这个新列表, 同时保持 b 不变.
- 表达式 a += [5,6,7,8] 实际上是使用的是 "extend" 函数, 所以 a 和 b 仍然指向已被修改的同一列表.

## 忽略类作用域的名称解析

```python
x = 5


class SomeClass:
    x = 17
    y = [x for i in range(10)]


print(SomeClass.y[0])
# 5
```

## round 舍入误差

```python
print(round(len([1,2,3,4,5]) / 2))
# 2
```

从 Python 3.0 开始，round()使用 [ 银行进位法](https://en.wikipedia.org/wiki/Rounding#Rounding_half_to_even)，其中 0.5
小数四舍五入到最接近的偶数。

这是 IEEE 754 中描述的 0.5 分位舍入的推荐方法。然而，另一种方法（从零取整）大部分时间都是在学校教授的，所以银行进位法可能并不为人所知。
此外，一些最流行的编程语言（例如：JavaScript、Java、C/C++、Ruby、Rust）也不使用银行进位法。因此，这对 Python 来说还是比较特殊的，在四舍五入时可能会导致混淆。

## 你的递归返回值被忽略了

```python
def similar_recursive_func(a):
    if a == 0:
        return a
    a -= 1
    similar_recursive_func(a)
    return a


print(similar_recursive_func(5))
# 4
```
similar_recursive_func(a) 这行是递归，但是却没有 return 递归的结果。

## 通配符导入

通配符导入中，带有前导下划线的名称不会被导入。

```python
# File: module.py

def some_weird_name_func_():
    print("works!")

def _another_weird_name_func():
    print("works!")
```
```bash
>>> from module import *
>>> some_weird_name_func_()
"works!"
>>> _another_weird_name_func()
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
NameError: name '_another_weird_name_func' is not defined
```

## reversed(x) 返回的是迭代器

```python
x = 7, 8, 9
y = reversed(x)
print(sorted(y), sorted(y))
# [7, 8, 9] []
```

## import this
```
The Zen of Python, by Tim Peters

Beautiful is better than ugly.
优美胜于丑陋（Python 以编写优美的代码为目标）
Explicit is better than implicit.
明了胜于晦涩（优美的代码应当是明了的，命名规范，风格相似）
Simple is better than complex.
简洁胜于复杂（优美的代码应当是简洁的，不要有复杂的内部实现）
Complex is better than complicated.
复杂胜于凌乱（如果复杂不可避免，那代码间也不能有难懂的关系，要保持接口简洁）
Flat is better than nested.
扁平胜于嵌套（优美的代码应当是扁平的，不能有太多的嵌套）
Sparse is better than dense.
间隔胜于紧凑（优美的代码有适当的间隔，不要奢望一行代码解决问题）
Readability counts.
可读性很重要（优美的代码一定是可读的）
Special cases aren't special enough to break the rules.
没有特例特殊到需要违背这些规则（这些规则至高无上）
Although practicality beats purity.
尽管我们更倾向于实用性
Errors should never pass silently.
不要安静的包容所有错误
Unless explicitly silenced.
除非你确定需要这样做（精准地捕获异常，不写 except:pass 风格的代码）
In the face of ambiguity, refuse the temptation to guess.
拒绝诱惑你去猜测的暧昧事物
There should be one-- and preferably only one --obvious way to do it.
而是尽量找一种，最好是唯一一种明显的解决方案（如果不确定，就用穷举法）
Although that way may not be obvious at first unless you're Dutch.
虽然这并不容易，因为你不是 Python 之父（这里的 Dutch 是指 Guido ）
Now is better than never.
现在行动好过永远不行动
Although never is often better than *right* now.
尽管不行动要好过鲁莽行动
If the implementation is hard to explain, it's a bad idea.
如果你无法向人描述你的方案，那肯定不是一个好方案；
If the implementation is easy to explain, it may be a good idea.
如果你能轻松向人描述你的方案，那也许会是一个好方案（方案测评标准）
Namespaces are one honking great idea -- let's do more of those!
命名空间是一种绝妙的理念，我们应当多加利用（倡导与号召）
```

```python
import this
love = this
# (love is not True) or False
print(love is not True or False, love is love)
# True True
```
爱没有对错，爱就是爱。

## try...else

```python
try:
    pass
except:
    print("Exception occurred!!!")
else:
    print("Try block executed successfully...")
```

else 语句会在没有触发except的情况下执行。

## Inpinity
```python
infinity = float('infinity')
print(hash(infinity))
# 314159
print(hash(float('-inf')))
# -314159
```
infinity 的哈希值是 10⁵ x π.

## name mangling
```python
class Yo(object):
    def __init__(self):
        self.__honey = True
        self.bro = True


print(Yo()._Yo__honey)
# True
```

## 高效的字符串拼接

- 不要用 + 去生成过长的字符串, 在 Python 中, str 是不可变的, 在每次连接中你都要把左右两个字符串复制到新的字符串中。
- 更建议使用 .format. 或 % 语法 (但是, 对于短字符串, 它们比 + 稍慢一点).
- 如果你所需的内容已经以可迭代对象的形式提供了, 使用 ''.join(可迭代对象) 要快多了.


## 函數中能定义多少个变量
```python
import dis

exec("""
def f():
    """ + """
    """.join(["X" + str(x) + "=" + str(x) for x in range(65539)]))

f()


```
`"".join(["X"+str(x)+"=" + str(x) for x in range(65539)])`
- 生成一个包含65,539个赋值语句的列表
- 每个元素形式为"X0=0", "X1=1", ..., "X65538=65538"
- 用换行符和缩进(\n )连接所有这些赋值语句

上面的代码是正常通过的。