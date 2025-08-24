第一部分：数据结构

## ch01 data-model

### 数据模型

- 使用 python 创建一个简单的数据类，包含一些工具函数。

扑克牌

```python
import collections

Card = collections.namedtuple('Card', ['rank', 'suit'])


class FrenchDeck:
    ranks = [str(n) for n in range(2, 11)] + list('JQKA')
    suits = 'spades diamonds clubs hearts'.split()

    def __init__(self):
        self._cards = [Card(rank, suit)
                       for suit in self.suits
                       for rank in self.ranks]

    def __len__(self):
        return len(self._cards)

    def __getitem__(self, position):
        return self._cards[position]


# [spades 2, spades 3,spades 4,spades 5,...,10,J,Q,K...spades A,...
# ...
# hearts 2, hearts 3,hearts 4,hearts 5,...,...hearts A]

suit_values = dict(spades=3, hearts=2, diamonds=1, clubs=0)


def spades_high(card):
    rank_value = FrenchDeck.ranks.index(card.rank)
    # For example: A spades: 12 * 4 + 3 = 48+3 = 51
    return rank_value * len(suit_values) + suit_values[card.suit]


deck = FrenchDeck()
for card in sorted(deck, key=spades_high):
    print(card)

# Card(rank='2', suit='clubs')
# Card(rank='2', suit='diamonds')
# Card(rank='2', suit='hearts')
# Card(rank='2', suit='spades')
# ...
# Card(rank='A', suit='clubs')
# Card(rank='A', suit='diamonds')
# Card(rank='A', suit='hearts')
# Card(rank='A', suit='spades')
```

空间点

```python
import math


class Vector:

    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y

    def __repr__(self):
        return f'Vector({self.x!r}, {self.y!r})'

    def __abs__(self):
        return math.hypot(self.x, self.y)

    def __bool__(self):
        return bool(abs(self))

    def __add__(self, other):
        x = self.x + other.x
        y = self.y + other.y
        return Vector(x, y)

    def __mul__(self, scalar):
        return Vector(self.x * scalar, self.y * scalar)
```

### doctest 文档测试

使用 doctest 模块来进行外部文档测试和内部嵌入文档测试。

```bash
python3 -m doctest frenchdeck.doctest -v
python3 -m doctest vector2d.py -v
```

这种测试方式过于脆弱，不如老老实实走 pytest 的方式。

### jupyter notebooks REPL

使用 jupyter notebooks 来进行 REPL 原型试验。 可行，但是更多时候是在 IDE 做实验。

## ch02 序列类型

常见的序列类型是数组，如果抽象层级更高的话，就泛化为序列 Sequence。

- 容器序列。举例：list、tuple 和 collections.deque。
- 扁平序列。举例：str、bytes 和 array.array。

内置的 memoryview 类是一种共享内存的序列类型，可在不复制字节的情况下处理数组的切片。经常用于性能敏感的操作。

## ch03 字典和集合

- dict 的初始化 {}、** 拆包
- dict 字典视图 .keys().values() .items()
- dict vs OrderedDict vs UserDict vs defaultdict vs TypedDict
- 实用类 Counter，用于统计词频。
- dict `__missing__` 可以自定义 d[k] 句法（调用 `__getitem__`）找不到键时的行为。

```python
import collections


class StrKeyDict(collections.UserDict):  # <1>
    def __missing__(self, key):  # <2>
        if isinstance(key, str):
            raise KeyError(key)
        return self[str(key)]

    def __contains__(self, key):
        return str(key) in self.data  # <3>

    def __setitem__(self, key, item):
        self.data[str(key)] = item  # <4>


d = StrKeyDict({'1': 'one'})

print(d[1])  # 输出 'one'（自动将 int 1 转为 str '1'查找）
print(d['1'])  # 输出 'one'
print(d[2])  # 抛出 KeyError: '2'（转换后仍不存在）
```

- collections.abc 模块中的抽象基类 Mapping 和 MutableMapping 定义标准接口，可在运行时检查类型。
- types 模块 中的 MappingProxyType 为映射包装一层不可变外壳，防止意外更改映射。
- Set 和 MutableSet 也有抽象基类。
- Set 的数学运算。

## ch04 Unicode 文本和字节序列

### 区分字符和字节

### 系统编码模式

```python
expressions = """
        locale.getpreferredencoding()
        type(my_file)
        my_file.encoding
        sys.stdout.isatty()
        sys.stdout.encoding
        sys.stdin.isatty()
        sys.stdin.encoding
        sys.stderr.isatty()
        sys.stderr.encoding
        sys.getdefaultencoding()
        sys.getfilesystemencoding()
    """

my_file = open('dummy', 'w')

for expression in expressions.split():
    value = eval(expression)
    print(f'{expression:>30} -> {value!r}')

#  locale.getpreferredencoding() -> 'cp936'
#                  type(my_file) -> <class '_io.TextIOWrapper'>
#               my_file.encoding -> 'cp936'
#            sys.stdout.isatty() -> False
#            sys.stdout.encoding -> 'utf-8'
#             sys.stdin.isatty() -> False
#             sys.stdin.encoding -> 'utf-8'
#            sys.stderr.isatty() -> False
#            sys.stderr.encoding -> 'utf-8'
#       sys.getdefaultencoding() -> 'utf-8'
#    sys.getfilesystemencoding() -> 'utf-8'
```

### “Unicode 三明治”原则。

尽早把输入的 bytes（例如读取文件得到）解码成 str。在 Unicode 三明治中，“肉饼”是程序的业务逻辑，在这里
只能处理 str 对象。。对输出来说，则要尽量晚地把 str 编码成 bytes。

### 规范化 unicode 字符

规范化 unicode 字符，使用 `from unicodedata import normalize`

```python
from unicodedata import normalize


def nfc_equal(str1, str2):
    return normalize('NFC', str1) == normalize('NFC', str2)


def fold_equal(str1, str2):
    return (normalize('NFC', str1).casefold() ==
            normalize('NFC', str2).casefold())
```

```bash
Using Normal Form C, case sensitive:

    >>> s1 = 'café'
    >>> s2 = 'cafe\u0301'
    >>> s1 == s2
    False
    >>> nfc_equal(s1, s2)
    True
    >>> nfc_equal('A', 'a')
    False

Using Normal Form C with case folding:

    >>> s3 = 'Straße'
    >>> s4 = 'strasse'
    >>> s3 == s4
    False
    >>> nfc_equal(s3, s4)
    False
    >>> fold_equal(s3, s4)
    True
    >>> fold_equal(s1, s2)
    True
    >>> fold_equal('A', 'a')
    True
```

### 奇奇怪怪的数字表示

```python
import unicodedata
import re

re_digit = re.compile(r'\d')

sample = '1\xbc\xb2\u0969\u136b\u216b\u2466\u2480\u3285'

for char in sample:
    print(f'U+{ord(char):04x}',  # <1>
          char.center(6),  # <2>
          're_dig' if re_digit.match(char) else '-',  # <3>
          'isdig' if char.isdigit() else '-',  # <4>
          'isnum' if char.isnumeric() else '-',  # <5>
          f'{unicodedata.numeric(char):5.2f}',  # <6>
          unicodedata.name(char),  # <7>
          sep='\t')

# U+0031	  1   	re_dig	isdig	isnum	 1.00	DIGIT ONE
# U+00bc	  ¼   	-	    -	    isnum	 0.25	VULGAR FRACTION ONE QUARTER
# U+00b2	  ²   	-	    isdig	isnum	 2.00	SUPERSCRIPT TWO
# U+0969	  ३   	re_dig	isdig	isnum	 3.00	DEVANAGARI DIGIT THREE
# U+136b	  ፫   	-	    isdig	isnum	 3.00	ETHIOPIC DIGIT THREE
# U+216b	  Ⅻ   	-	    -	    isnum	12.00	ROMAN NUMERAL TWELVE
# U+2466	  ⑦   	-	    isdig	isnum	 7.00	CIRCLED DIGIT SEVEN
# U+2480	  ⒀   	-	    -	    isnum	13.00	PARENTHESIZED NUMBER THIRTEEN
# U+3285	  ㊅   	-	    -	    isnum	 6.00	CIRCLED IDEOGRAPH SIX
```

- 支持 str 和 bytes 的双模式 API。

## ch05 数据类构建器

### 把数据处理的职责放回数据类

OOP 把行为和数据放在同一个代码单元（一个类）中。如果一个类使用广泛，但是自身没有什么重要的行为，那么整
个系统中可能遍布处理实例的代码，并出现在很多方法和函数中（有些甚至是重复的）。这样的系统对维护来说简直就是噩梦。鉴于此，
Martin Fowler 提出的重构方案才建议 ** 把职责放回数据类 ** 中。

### 数据类

数据类构建常见模式：collections.namedtuple、 typing.NamedTuple（可为各个字段添加类型注解） 和 dataclasses.dataclass。

- plain

```python
class DemoPlainClass:
    a: int  # <1>
    b: float = 1.1  # <2>
    c = 'spam'  # <3>
```

- collections.namedtuple

```python
from collections import namedtuple

DemoNamedTuple = namedtuple('DemoNamedTuple', ['a', 'b', 'c'])

# 设置默认值（namedtuple 本身不支持默认值，但可以通过 __defaults__ 或工厂函数模拟）
DemoNamedTuple.__new__.__defaults__ = (None, 1.1, 'spam')  # 默认值对应 b 和 c

obj = DemoNamedTuple(10)  # a=10, b=1.1 (默认), c='spam' (默认)
print(obj)  # 输出: DemoNamedTuple(a=10, b=1.1, c='spam')
```

- typing.NamedTuple

```python
import typing


class DemoNTClass(typing.NamedTuple):
    a: int  # <1>
    b: float = 1.1  # <2>
    c = 'spam'  # <3>
```

- dataclasses.dataclass

```python
from dataclasses import dataclass


@dataclass
class DemoDataClass:
    a: int  # <1>
    b: float = 1.1  # <2>
    c = 'spam'  # <3>
```

### 小结

- 类型提示在运行时没有作用，大胆添加类型提示，改善代码可读性。
- TypedDict 类似于 dict，只不过有类型提示。
- @dataclass 示例：都柏林核心模式，用于描述一个复杂的资源模型。
- 有几种情况适合使用没什么行为或者没有任何行为的数据类：1. 把数据类用作脚手架；2. 把数据类用作中间表述（例如要导出 JSON）。
- 对于被 @dataclass 修饰的数据类，在自定义的 `__repr__` 方法中可以使用 dataclasses.fields 迭代对应数据类实例的属性。

## ch06 对象引用、可变性和垃圾回收

- 每个 Python 对象都有标识、类型和值。只有对象的值不时变化。
- 区分浅拷贝和深拷贝。
- 简单的赋值不创建副本。
- 对 += 或 *= 所做的增量赋值来说，如果左边的变量绑定的是不可 变对象，则创建新对象；如果是可变对象，则就地修改。
- 为现有的变量赋予新值，不修改之前绑定的变量。这叫重新绑定： 现在变量绑定了其他对象。如果变量是之前那个对象的最后一个引
  用，则对象被当作垃圾回收。
- 函数的形参以别名的形式传递，这意味着函数可能会修改通过实参传入的可变对象。
- 函数的形参的默认值务必使用不可变类型。

下面是错误的写法

```python
class HauntedBus:
    """A bus model haunted by ghost passengers"""

    def __init__(self, passengers=[]):  # <1>
        self.passengers = passengers  # <2>

    def pick(self, name):
        self.passengers.append(name)  # <3>

    def drop(self, name):
        self.passengers.remove(name)
```

下面是正确的写法

```python
class TwilightBus:
    """A bus model that makes passengers vanish"""

    def __init__(self, passengers=None):
        if passengers is None:
            self.passengers = []  # <1>
        else:
            # self.passengers = passengers 直接引用外部列表存在潜在问题，应创建副本，避免共享外部对象
            self.passengers = list(passengers)  # <2>

    def pick(self, name):
        self.passengers.append(name)

    def drop(self, name):
        self.passengers.remove(name)  # <3>
```

- weakref 模块中 WeakValueDictionary、 WeakKeyDictionary 和 WeakSet 等有用的容器类。

---

第二部分：函数即对象

## ch07 函数是一等公民

下面是一个建模 HTML 标签的函数。

```python
"""
# tag::TAG_DEMO[]
>>> tag('br')  # <1>
'<br />'
>>> tag('p', 'hello')  # <2>
'<p>hello</p>'
>>> print(tag('p', 'hello', 'world'))
<p>hello</p>
<p>world</p>
>>> tag('p', 'hello', id=33)  # <3>
'<p id="33">hello</p>'
>>> print(tag('p', 'hello', 'world', class_='sidebar'))  # <4>
<p class="sidebar">hello</p>
<p class="sidebar">world</p>
>>> tag(content='testing', name="img")  # <5>
'<img content="testing" />'
>>> my_tag = {'name': 'img', 'title': 'Sunset Boulevard',
...           'src': 'sunset.jpg', 'class': 'framed'}
>>> tag(**my_tag)  # <6>
'<img class="framed" src="sunset.jpg" title="Sunset Boulevard" />'

# end::TAG_DEMO[]
"""


# tag::TAG_FUNC[]
def tag(name, *content, class_=None, **attrs):
    """Generate one or more HTML tags"""
    if class_ is not None:
        attrs['class'] = class_
    attr_pairs = (f' {attr}="{value}"' for attr, value
                  in sorted(attrs.items()))
    attr_str = ''.join(attr_pairs)
    if content:
        elements = (f'<{name}{attr_str}>{c}</{name}>'
                    for c in content)
        return '\n'.join(elements)
    else:
        return f'<{name}{attr_str} />'
# end::TAG_FUNC[]
```

小结：

- Python 函数具有一等地位，可以把函数赋值给变量、传给其他函数、存储在数据结构中，以及访问函数的属性。
- 高阶函数是函数式编程的重要组成部分，比如内 置函数 sorted、min 和 max，以及标准库中的 functools.partial。
- 现在已经不经常使用 map、 filter 和 reduce 等函数了，但还有列表推导式（以及类似的结构，例如生成器表达式），以及 sum、all 和
  any 等内置的归约函数。
- 自 Python 3.6 起，Python 有 9 种可调用对象，从 lambda 表达式创建的简单函数，到实现 __call__ 方法的类实例。生成器和协程也是可调用对象。
- 可调用对象都能通过内置函数 callable() 检测。
- 可调用对象支持丰富的形参声明句法，包括仅限关键字参数、仅限位置参数和注解。
- operator 模块中的函数以及 functools.partial 函数比功能受限的 lambda 表达式实现的函数更加强大。

## ch08 函数类型提示

### 例子

SupportsLessThan

```python
from typing import Protocol, Any


class SupportsLessThan(Protocol):  # <1>
    def __lt__(self, other: Any) -> bool: ...  # <2>
```

double

```python
from typing import TypeVar, Protocol

T = TypeVar('T')  # <1>


class Repeatable(Protocol):
    def __mul__(self: T, other: int) -> T: ...  # <2>


RT = TypeVar('RT', bound=Repeatable)  # <3>


def double(n: RT) -> RT:  # <4>
    return n * 2
```

### 函数参数

```python
import typing
from typing import Optional


def f(a: str, *b: int, **c: float) -> None:
    if typing.TYPE_CHECKING:
        # reveal_type(b)
        reveal_type(c)
    print(a, b, c)


def tag(
        name: str,
        /,
        *content: str,
        class_: Optional[str] = None,
        **attrs: str,
) -> str:
    return repr((name, content, class_, attrs))


f(a='1')
f('1', 2, 3, x=4, y=5)
print(tag('li', 'first', 'second', id='#123'))
print(tag('li', 'first', 'second', class_='menu', id='#123'))

# 1 (){}
# 1 (2, 3) {'x': 4, 'y': 5}
# ('li', ('first', 'second'), None, {'id': '#123'})
# ('li', ('first', 'second'), 'menu', {'id': '#123'})
```

### 罗马数字转换

```python
values_map = [
    (1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1),
    ('M', 'CM', 'D', 'CD', 'C', 'XC', 'L', 'XL', 'X', 'IX', 'V', 'IV', 'I')
]


def to_roman(arabic: int) -> str:
    """ Convert an integer to a Roman numeral. """
    if not 0 < arabic < 4000:
        raise ValueError('Argument must be between 1 and 3999')

    result = []
    for value, numeral in zip(*values_map):
        repeat = arabic // value
        result.append(numeral * repeat)
        arabic -= value * repeat
    return ''.join(result)


if __name__ == '__main__':
    print(to_roman(1456))
    # MCDLVI
```

### 小结

- 渐进式类型概念，其实是一种混合概念，综合了 Python 传统的鸭子类型和 Java、C++ 等静态类型语言的名义类型。
- 有些类型是特殊的实体。Any、Optional、Union 和 NoReturn 不关联内存中的实际对象，只存在于类型系统的抽象层面上。
- 参数化泛型和类型变量，为类型提示提供了更大的灵活性，而且不失类型安全性。
- 引入 Protocol 后，参数化泛型更具表现力。Protocol 使得静态鸭子类型成为可能。静态鸭子类型是 Python 内在的鸭子类型和
  名义类型之间的重要桥梁，令静态类型检查工具能捕获更多的 bug。
- 利用 Mypy 提供的魔法函数 reveal_type() 可以观察类型检查错误和推导的类型。
- 介绍了如何注解仅限位置参数和变长参数。
- 请不要听信类型布道者的话，认为所有 Python 代码都需要类型提示。

## ch09 装饰器和闭包

### clock 计时修饰器

朴素实现

```python
import time
import functools


def clock(func):
    @functools.wraps(func)
    def clocked(*args, **kwargs):
        t0 = time.perf_counter()
        result = func(*args, **kwargs)
        elapsed = time.perf_counter() - t0
        name = func.__name__
        arg_lst = [repr(arg) for arg in args]
        arg_lst.extend(f'{k}={v!r}' for k, v in kwargs.items())
        arg_str = ', '.join(arg_lst)
        print(f'[{elapsed:0.8f}s] {name}({arg_str}) -> {result!r}')
        return result

    return clocked
```

类实现

```python
import time

DEFAULT_FMT = '[{elapsed:0.8f}s] {name}({args}) -> {result}'


class clock:  # <1>

    def __init__(self, fmt=DEFAULT_FMT):  # <2>
        self.fmt = fmt

    def __call__(self, func):  # <3>
        def clocked(*_args):
            t0 = time.perf_counter()
            _result = func(*_args)  # <4>
            elapsed = time.perf_counter() - t0
            name = func.__name__
            args = ', '.join(repr(arg) for arg in _args)
            result = repr(_result)
            print(self.fmt.format(**locals()))
            return _result

        return clocked


if __name__ == '__main__':
    @clock()
    def snooze(seconds):
        time.sleep(seconds)


    for i in range(3):
        snooze(.123)
```

修饰器带参数

```python
import time

DEFAULT_FMT = '[{elapsed:0.8f}s] {name}({args}) -> {result}'


def clock(fmt=DEFAULT_FMT):  # <1>
    def decorate(func):  # <2>
        def clocked(*_args):  # <3>
            t0 = time.perf_counter()
            _result = func(*_args)  # <4>
            elapsed = time.perf_counter() - t0
            name = func.__name__
            args = ', '.join(repr(arg) for arg in _args)  # <5>
            result = repr(_result)  # <6>
            print(fmt.format(**locals()))  # <7>
            return _result  # <8>

        return clocked  # <9>

    return decorate  # <10>


if __name__ == '__main__':
    # @clock()  # <11>
    # def snooze(seconds):
    #     time.sleep(seconds)

    @clock('{name}: {elapsed}s')
    def snooze(seconds):
        time.sleep(seconds)


    for i in range(3):
        snooze(.123)
```

### 闭包变量

```python
"""
>>> avg = make_averager()
>>> avg(10)
10.0
>>> avg(11)
10.5
>>> avg(12)
11.0
>>> avg.__code__.co_varnames
('new_value', 'total')
>>> avg.__code__.co_freevars
('series',)
>>> avg.__closure__  # doctest: +ELLIPSIS
(<cell at 0x...: list object at 0x...>,)
>>> avg.__closure__[0].cell_contents
[10, 11, 12]
"""


def make_averager():
    series = []

    def averager(new_value):
        series.append(new_value)
        total = sum(series)
        return total / len(series)

    return averager
```

- co_varnames 是 averager 函数的内部变量 ('new_value', 'total')
- co_freevars 是 averager 函数的外部变量，即自由变量 series

上面的实现也可以换成 OOP 类实现

```python
class Averager:

    def __init__(self):
        self.series = []

    def __call__(self, new_value):
        self.series.append(new_value)
        total = sum(self.series)
        return total / len(self.series)
```

### 函数重载

```python
from functools import singledispatch
from collections import abc
import fractions
import decimal
import html
import numbers


@singledispatch  # <1>
def htmlize(obj: object) -> str:
    content = html.escape(repr(obj))
    return f'<pre>{content}</pre>'


@htmlize.register  # <2>
def _(text: str) -> str:  # <3>
    content = html.escape(text).replace('\n', '<br/>\n')
    return f'<p>{content}</p>'


@htmlize.register  # <4>
def _(seq: abc.Sequence) -> str:
    inner = '</li>\n<li>'.join(htmlize(item) for item in seq)
    return '<ul>\n<li>' + inner + '</li>\n</ul>'


@htmlize.register  # <5>
def _(n: numbers.Integral) -> str:
    return f'<pre>{n} (0x{n:x})</pre>'


@htmlize.register  # <6>
def _(n: bool) -> str:
    return f'<pre>{n}</pre>'


@htmlize.register(fractions.Fraction)  # <7>
def _(x) -> str:
    frac = fractions.Fraction(x)
    return f'<pre>{frac.numerator}/{frac.denominator}</pre>'


@htmlize.register(decimal.Decimal)  # <8>
@htmlize.register(float)
def _(x) -> str:
    frac = fractions.Fraction(x).limit_denominator()
    return f'<pre>{x} ({frac.numerator}/{frac.denominator})</pre>'
```

使用示例

```bash
>>> htmlize({1, 2, 3})  # <1>
'<pre>{1, 2, 3}</pre>'
>>> htmlize(abs)
'<pre>&lt;built-in function abs&gt;</pre>'
>>> htmlize('Heimlich & Co.\n- a game')  # <2>
'<p>Heimlich &amp; Co.<br/>\n- a game</p>'
>>> htmlize(42)  # <3>
'<pre>42 (0x2a)</pre>'
>>> print(htmlize(['alpha', 66, {3, 2, 1}]))  # <4>
<ul>
<li><p>alpha</p></li>
<li><pre>66 (0x42)</pre></li>
<li><pre>{1, 2, 3}</pre></li>
</ul>
>>> htmlize(True)  # <5>
'<pre>True</pre>'
>>> htmlize(fractions.Fraction(2, 3))  # <6>
'<pre>2/3</pre>'
>>> htmlize(2/3)   # <7>
'<pre>0.6666666666666666 (2/3)</pre>'
>>> htmlize(decimal.Decimal('0.02380952'))
'<pre>0.02380952 (1/42)</pre>'
```

### 装饰器的装饰顺序和执行顺序

```python
def first(f):
    print(f'apply first({f.__name__})')

    def inner1st(n):
        result = f(n)
        print(f'inner1({n}): called {f.__name__}({n}) -> {result}')
        return result

    return inner1st


def second(f):
    print(f'apply second({f.__name__})')

    def inner2nd(n):
        result = f(n)
        print(f'inner2({n}): called {f.__name__}({n}) -> {result}')
        return result

    return inner2nd


@first
@second
def double(n):
    return n * 2


print(double(3))
```

结果

```
apply second(double)
apply first(inner2nd)
inner2(3): called double(3) -> 6
inner1(3): called inner2nd(3) -> 6
6
```

小结：

- 装饰器可以实现“策略”设计模式。
- 真正理解装饰器，不仅需要区分导入时和运行时，还要理解变量作用域、闭包和 nonlocal 声明。
- 掌握闭包和 nonlocal 不仅对构建装饰器有帮助，在面向事件的 GUI 程序编程和基于回调处理异步 I/O 中也有用。
- 参数化装饰器基本上涉及至少两层嵌套函数，如果想使用 @functools.wraps 生成装饰器，为高级技术提供支持，则嵌套层级可能更深。
- 对更复杂的装饰器来说，基于类实现或许更易于理解和维护。
- functools 模块中强大的 @cache 和 @singledispatch 是标准库中的参数化装饰器。

## ch10 使用一等函数实现设计模式

### 订单折扣的不同策略

首先，我们实现用户、商品项、订单的建模。

```python
from abc import ABC, abstractmethod
import typing


class Customer(typing.NamedTuple):
    name: str
    fidelity: int


class LineItem:

    def __init__(self, product, quantity, price):
        self.product = product
        self.quantity = quantity
        self.price = price

    def total(self):
        return self.price * self.quantity


class Order:  # the Context

    def __init__(self, customer, cart, promotion=None):
        self.customer = customer
        self.cart = list(cart)
        self.promotion = promotion

    def total(self):
        if not hasattr(self, '__total'):
            self.__total = sum(item.total() for item in self.cart)
        return self.__total

    def due(self):
        if self.promotion is None:
            discount = 0
        else:
            discount = self.promotion.discount(self)
        return self.total() - discount

    def __repr__(self):
        return f'<Order total: {self.total():.2f} due: {self.due():.2f}>'
```

最后是折扣策略

```python
class Promotion(ABC):  # the Strategy: an abstract base class

    @abstractmethod
    def discount(self, order):
        """Return discount as a positive dollar amount"""


class FidelityPromo(Promotion):  # first Concrete Strategy
    """5% discount for customers with 1000 or more fidelity points"""

    def discount(self, order):
        return order.total() * .05 if order.customer.fidelity >= 1000 else 0


class BulkItemPromo(Promotion):  # second Concrete Strategy
    """10% discount for each LineItem with 20 or more units"""

    def discount(self, order):
        discount = 0
        for item in order.cart:
            if item.quantity >= 20:
                discount += item.total() * .1
        return discount


class LargeOrderPromo(Promotion):  # third Concrete Strategy
    """7% discount for orders with 10 or more distinct items"""

    def discount(self, order):
        distinct_items = {item.product for item in order.cart}
        if len(distinct_items) >= 10:
            return order.total() * .07
        return 0
```

示例

```bash
>>> joe = Customer('John Doe', 0)  # <1>
>>> ann = Customer('Ann Smith', 1100)
>>> cart = [LineItem('banana', 4, .5),  # <2>
...         LineItem('apple', 10, 1.5),
...         LineItem('watermelon', 5, 5.0)]
>>> Order(joe, cart, FidelityPromo())  # <3>
```

不同的折扣策略的实现，是通过依赖注入这种形式附加到订单中的。

### 订单折扣测试类

```python
from typing import List

import pytest  # type: ignore

from classic_strategy import Customer, LineItem, Order
from classic_strategy import FidelityPromo, BulkItemPromo, LargeOrderPromo


@pytest.fixture
def customer_fidelity_0() -> Customer:
    return Customer('John Doe', 0)


@pytest.fixture
def customer_fidelity_1100() -> Customer:
    return Customer('Ann Smith', 1100)


@pytest.fixture
def cart_plain() -> List[LineItem]:
    return [LineItem('banana', 4, .5),
            LineItem('apple', 10, 1.5),
            LineItem('watermelon', 5, 5.0)]


def test_fidelity_promo_no_discount(customer_fidelity_0, cart_plain) -> None:
    order = Order(customer_fidelity_0, cart_plain, FidelityPromo())
    assert order.total() == 42.0
    assert order.due() == 42.0


def test_fidelity_promo_with_discount(customer_fidelity_1100, cart_plain) -> None:
    order = Order(customer_fidelity_1100, cart_plain, FidelityPromo())
    assert order.total() == 42.0
    assert order.due() == 39.9


def test_bulk_item_promo_no_discount(customer_fidelity_0, cart_plain) -> None:
    order = Order(customer_fidelity_0, cart_plain, BulkItemPromo())
    assert order.total() == 42.0
    assert order.due() == 42.0


def test_bulk_item_promo_with_discount(customer_fidelity_0) -> None:
    cart = [LineItem('banana', 30, .5),
            LineItem('apple', 10, 1.5)]
    order = Order(customer_fidelity_0, cart, BulkItemPromo())
    assert order.total() == 30.0
    assert order.due() == 28.5


def test_large_order_promo_no_discount(customer_fidelity_0, cart_plain) -> None:
    order = Order(customer_fidelity_0, cart_plain, LargeOrderPromo())
    assert order.total() == 42.0
    assert order.due() == 42.0


def test_large_order_promo_with_discount(customer_fidelity_0) -> None:
    cart = [LineItem(str(item_code), 1, 1.0) for item_code in range(10)]
    order = Order(customer_fidelity_0, cart, LargeOrderPromo())
    assert order.total() == 10.0
    assert order.due() == 9.3
```

### 感知目标函数的几种方式

1. 直接 hardcode

```python
promos = [fidelity_promo, bulk_item_promo, large_order_promo]  # <1>


def best_promo(order):  # <2>
    """Select best discount available
    """
    return max(promo(order) for promo in promos)  # <3>
```

2. 从全局变量空间中提取

```python
promos = [globals()[name] for name in globals()  # <1>
          if name.endswith('_promo')  # <2>
          and name != 'best_promo']  # <3>


def best_promo(order):
    """Select best discount available
    """
    return max(promo(order) for promo in promos)  # <4>
```

3. inspect 从文件模块中提取

```python
promos = [func for name, func in
          inspect.getmembers(promotions, inspect.isfunction)]


def best_promo(order):
    """Select best discount available
    """
    return max(promo(order) for promo in promos)
```

4. 修饰器

```python
promos = []  # <1>


def promotion(promo_func):  # <2>
    promos.append(promo_func)
    return promo_func


@promotion  # <3>
def fidelity(order):
    """5% discount for customers with 1000 or more fidelity points"""
    return order.total() * .05 if order.customer.fidelity >= 1000 else 0


# ...
def best_promo(order):  # <4>
    """Select best discount available
    """
    return max(promo(order) for promo in promos)
```

### 定制折扣百分比

1. 利用嵌套函数（闭包）

```python
def fidelity_promo(percent):
    """discount for customers with 1000 or more fidelity points"""
    return lambda order: (order.total() * percent / 100
                          if order.customer.fidelity >= 1000 else 0)


def bulk_item_promo(percent):
    """discount for each LineItem with 20 or more units"""

    def discounter(order):
        discount = 0
        for item in order.cart:
            if item.quantity >= 20:
                discount += item.total() * percent / 100
        return discount

    return discounter


def large_order_promo(percent):
    """discount for orders with 10 or more distinct items"""

    def discounter(order):
        distinct_items = {item.product for item in order.cart}
        if len(distinct_items) >= 10:
            return order.total() * percent / 100
        return 0

    return discounter
```

2. 类实现，重写 `__call__` 方法

```python
class Promotion:
    """compute discount for order"""

    def __init__(self, percent):
        self.percent = percent

    def __call__(self, order):
        raise NotImplementedError("Subclass responsibility")


class FidelityPromo(Promotion):
    """discount for customers with 1000 or more fidelity points"""

    def __call__(self, order):
        if order.customer.fidelity >= 1000:
            return order.total() * self.percent / 100
        return 0


class BulkItemPromo(Promotion):
    """discount for each LineItem with 20 or more units"""

    def __call__(self, order):
        discount = 0
        for item in order.cart:
            if item.quantity >= 20:
                discount += item.total() * self.percent / 100
        return discount


class LargeOrderPromo(Promotion):
    """discount for orders with 10 or more distinct items"""

    def __call__(self, order):
        distinct_items = {item.product for item in order.cart}
        if len(distinct_items) >= 10:
            return order.total() * self.percent / 100
        return 0
```

例如，初始化 promo = FidelityPromo(5) 时把具体的折扣百分比传入，后续调用 promo(order) 即可。

### 类变量 VS 实例变量

|            | ** 类变量 (Class Variable)** | ** 实例变量 (Instance Variable)**     |
|:-----------|:--------------------------|:----------------------------------|
| ** 定义位置 ** | 直接在类中定义（无类型注解语法）          | 在 `NamedTuple` 或 `@dataclass` 中定义 |
| ** 归属 **   | 属于类本身                     | 属于类的实例（每个对象独立拥有）                  |
| ** 共享性 **  | 所有实例共享同一值                 | 每个实例有独立的值                         |
| ** 修改影响 ** | 修改后影响所有实例                 | 修改仅影响当前实例                         |

### 小结

- 有时，在 Python 中使用函数或可调用对象实现回调更加自然，这比模仿《设计模式》一书中的策略或命令模式要好。
- 有时，设计模式或 API 要求组件实现单方法接口，而该方法有一个很宽泛的名称，例如“execute” “run”或“do_it”。
- 在 Python 中，这些模式或 API 通常可以使用作为一等对象的函数实现，从而减少样板代码。

第三部分：类和协议

## ch11 Python 风格的对象

### Vector 类

```python
from array import array
import math


class Vector2d:
    __match_args__ = ('x', 'y')  # <1>
    __slots__ = ('__x', '__y')  # <2>

    typecode = 'd'

    def __init__(self, x, y):
        self.__x = float(x)
        self.__y = float(y)

    @property
    def x(self):
        return self.__x

    @property
    def y(self):
        return self.__y

    def __iter__(self):
        return (i for i in (self.x, self.y))

    def __repr__(self):
        class_name = type(self).__name__
        return '{}({!r}, {!r})'.format(class_name, *self)

    def __str__(self):
        return str(tuple(self))

    def __bytes__(self):
        return (bytes([ord(self.typecode)]) +
                bytes(array(self.typecode, self)))

    def __eq__(self, other):
        return tuple(self) == tuple(other)

    def __hash__(self):
        return hash((self.x, self.y))

    def __abs__(self):
        return math.hypot(self.x, self.y)

    def __bool__(self):
        return bool(abs(self))

    def angle(self):
        return math.atan2(self.y, self.x)

    def __format__(self, fmt_spec=''):
        if fmt_spec.endswith('p'):
            fmt_spec = fmt_spec[:-1]
            coords = (abs(self), self.angle())
            outer_fmt = '<{}, {}>'
        else:
            coords = self
            outer_fmt = '({}, {})'
        components = (format(c, fmt_spec) for c in coords)
        return outer_fmt.format(*components)

    @classmethod
    def frombytes(cls, octets):
        typecode = chr(octets[0])
        memv = memoryview(octets[1:]).cast(typecode)
        return cls(*memv)
```

- `__match_args__` 支持模式匹配（Python 3.10+ 的 match-case）
- `__slots__` 限制实例只能拥有 `__x` 和 `__y` 属性，节省内存（避免动态字典 `__dict__`）
- typecode 定义二进制编码类型。
- 私有属性 `__x` 避免子类属性冲突，使用 `@property` 暴露公共 API。
- `tuple(self)` 的行为取决于类是否实现了迭代协议（即 `__iter__` 方法），返回的结果也是一个元组。
- `__bytes__` 得到的字节是这种样子（示例）：`b'd\x00\x00\x00\x00\x00\x00\x08@\x00\x00\x00\x00\x00\x00\x10@'`
- `frombytes()` 方法中使用 memoryview 从二进制数据高效反序列化对象，避免中间拷贝，直接操作原始字节。

### 测量海量类实例占用的内存

```python
import importlib
import sys
#  only works in unix-like system
import resource

NUM_VECTORS = 10 ** 7

module = None
if len(sys.argv) == 2:
    module_name = sys.argv[1].replace('.py', '')
    module = importlib.import_module(module_name)
else:
    print(f'Usage: {sys.argv[0]} <vector-module-to-test>')

if module is None:
    print('Running test with built-in `complex`')
    cls = complex
else:
    fmt = 'Selected Vector2d type: {.__name__}.{.__name__}'
    print(fmt.format(module, module.Vector2d))
    cls = module.Vector2d

mem_init = resource.getrusage(resource.RUSAGE_SELF).ru_maxrss
print(f'Creating {NUM_VECTORS:,} {cls.__qualname__!r} instances')

vectors = [cls(3.0, 4.0) for i in range(NUM_VECTORS)]

# KB in unix-like system, Bytes in MacOS
mem_final = resource.getrusage(resource.RUSAGE_SELF).ru_maxrss
print(f'Initial RAM usage: {mem_init:14,}')
print(f'  Final RAM usage: {mem_final:14,}')
```

结果

```bash
Usage: mem_test.py <vector-module-to-test>
Running test with built-in `complex`
Creating 10,000,000 'complex' instances
Initial RAM usage:         10,272
Final RAM usage:        402,032
```

### 小结

- 要定义行为良好且符合 Python 风格的类，需要了解特殊方法和约定的结构。
- 符合 Python 风格的对象应该正好符合所需，而不是堆砌语言功能。
- 编写供其他程序员使用的库时，应该实现一些特殊方法。例如，`__eq__` 方法对业务需求可能没有必要，但是方便程序员测试。
- 字符串和字节序列表示形式的方法：`__repr__`、`__str__`、`__format__` 和 `__bytes__`。
- 把对象转换成数值的方法：`__abs__`、`__bool__` 和 `__hash__`。
- 支持测试和哈希的 `__eq__` 方法（外加 `__hash__`）。
- 为了转换成字节序列，本章实现了一个名为 Vector2d.frombytes() 的备选构造函数，frombytes 方法的实现借鉴了 array.array
  类中的同名方法。
- 在 `__format__ ` 方法中，要注意解析 format_spec。这就是传给内置函数 format(obj, format_spec) 的参数、f 字符串的代换字
  段 '{:«format_spec»}'，以及 str.format() 方法处理的字符串。
- 为了把 Vector2d 实例变成可哈希的，** 先让实例不可变 **，至少要把 x 和 y 设为私有属性，再以只读特性公开，以防意外修改。随后，实现
  `__hash__` 方法，使用推荐的 ** 异或运算符 ** 计算实例属性的哈希值。
- 使用 `__slots__` 属性可以节省内存。`__slots__` 属性有点棘手，因此仅当处理特别多的实例（数百万个，而不是几千个）时才建议使用。
  如果真有这么多的数量，使用 pandas 库或许是最好的选择。
- 通过访问实例属性（例如 self.typecode）覆盖类属性。我们先创建一个实例属性，然后创建子类，在类中覆盖类属性。
- 要构建符合 Python 风格的对象，就要观察真正的 Python 对象的行为。也就是向 python 标准库的代码风格学习。

## ch12 序列的特殊方法

### Vector 实现 __getitem__ 和 __len__

```python
from array import array
import operator


class Vector:
    typecode = 'd'

    def __init__(self, components):
        self._components = array(self.typecode, components)

    def __len__(self):
        return len(self._components)

    def __getitem__(self, key):
        if isinstance(key, slice):  # <1>
            cls = type(self)  # <2>
            return cls(self._components[key])  # <3>
        index = operator.index(key)  # <4>
        return self._components[index]  # <5>
```

### Vector 实现 __getattr__ 和 __setattr__

```python
__match_args__ = ('x', 'y', 'z', 't')  # <1>


def __getattr__(self, name):
    cls = type(self)  # <2>
    try:
        pos = cls.__match_args__.index(name)  # <3>
    except ValueError:  # <4>
        pos = -1
    if 0 <= pos < len(self._components):  # <5>
        return self._components[pos]
    msg = f'{cls.__name__!r} object has no attribute {name!r}'  # <6>
    raise AttributeError(msg)


def __setattr__(self, name, value):
    cls = type(self)
    if len(name) == 1:  # <1>
        if name in cls.__match_args__:  # <2>
            error = 'readonly attribute {attr_name!r}'
        elif name.islower():  # <3>
            error = "can't set attributes 'a' to 'z' in {cls_name!r}"
        else:
            error = ''  # <4>
        if error:  # <5>
            msg = error.format(cls_name=cls.__name__, attr_name=name)
            raise AttributeError(msg)
    super().__setattr__(name, value)  # <6>
```

### Vector 实现 __hash__ 和 __eq__

```python
def __hash__(self):
    hashes = (hash(x) for x in self)
    return functools.reduce(operator.xor, hashes, 0)


def __eq__(self, other):
    return (len(self) == len(other) and
            all(a == b for a, b in zip(self, other)))
```

### 角度

```python
def angle(self, n):
    r = math.hypot(*self[n:])  # 计算第 n 维及之后所有维度的联合长度
    a = math.atan2(r, self[n - 1])  # 计算当前平面的角度
    if (n == len(self) - 1)and (self[-1] < 0):
        return math.pi * 2 - a  # 处理最后一维负值的特殊情况，确保角度范围在 [0, 2π)
    else:
        return a


def angles(self):  # <3>
    return (self.angle(n) for n in range(1, len(self)))
```

数学原理:

(1) 极坐标与笛卡尔坐标转换

- 对于一个 2D 向量 (x, y)，其极坐标 (r,θ) 表示为：
    - 半径（长度）: r = hypot(x, y) = sqrt(x² + y²)
    - 角度: θ = atan2(y, x)

(2) 高维推广

- 在 N 维空间中，逐层分解计算角度：
    - 从最高维度开始，逐步投影到低维子空间。
    - 每一层的角度表示向量在该平面内的方向。

`def angles(self)` 的作用: 生成所有连续平面的角度序列。

示例:

- 对于 3D 向量 (x,y,z)，返回 [angle(1), angle(2)]:
    - angle(1): 在 (x, hypot(y,z)) 平面的角度。
    - angle(2): 在 (y, |z|) 平面的角度。

### 小结

- Vector 的行为之所以像序列，是因为它实现了 `__getitem__` 方法和 `__len__` 方法
- my_seq[a:b:c] 句法背后的原理：创建 slice(a, b, c) 对象，交给 `__getitem__ ` 方法处理
- 提供只读访问功能，使用的是 my_vec.x 这样的表示法，这个功能通过 `__getattr__` 方法实现
- 如果定义了 `__getattr__` 方法，一般也要定义 `__setattr__` 方法，这样才能避免行为不一致
- 实现 `__hash__` 方法特别适合使用 functools.reduce 函数，因为要把异或运算符 ^ 依次应用到各个分量的哈希值上，生成整个向量的聚合哈希值。

## ch13 接口、协议和抽象基类

### Protocol

randompick.py

```python
from typing import Protocol, runtime_checkable, Any


@runtime_checkable
class RandomPicker(Protocol):
    def pick(self) -> Any: ...
```

randompickload.py

```python
from typing import Protocol, runtime_checkable
from randompick import RandomPicker


@runtime_checkable  # <1>
class LoadableRandomPicker(RandomPicker, Protocol):  # <2>
    def load(self, Iterable) -> None: ...  # <3>
```

### Vector 类 v5

增加了下面的方法

```python
def __abs__(self) -> float:  # <1>
    return math.hypot(self.x, self.y)


def __complex__(self) -> complex:  # <2>
    return complex(self.x, self.y)


@classmethod
def fromcomplex(cls, datum: SupportsComplex) -> Vector2d:  # <3>
    c = complex(datum)  # <4>
    return cls(c.real, c.imag)
```

我们可以看一下 SupportsComplex 的定义

```python
@runtime_checkable
class SupportsComplex(Protocol):
    """An ABC with one abstract method __complex__."""
    __slots__ = ()

    @abstractmethod
    def __complex__(self) -> complex:
        pass
```

当 datum 为 SupportsComplex 类型，执行 complex(datum) 时会底层调用 `__complex__` 方法来转化数据。

### 小结

- 协议有一个共同的基本特征，即类不需要显式声明对任何特定协议的支持。** 只要实现了协议要求的方法，类就支持那个协议 **。
- 可以利用猴子补丁为类添加额外的方法，在运行时实现协议。
- 防御性编程，包括不显式使用 isinstance 或在 try/except 结构中使用 hasattr 检查结构类型，以及快速失败原则。
- 即使是不相关的类，只要提供了抽象基类定义的接口要求的方法，也能被特殊方法 `__subclasshook__` 识别，从而让抽象基类支持结构类型。
- 继续探讨静态鸭子类型。`@runtime_checkable` 装饰器也利用 `__subclasshook__` 方法 ** 在运行时支持结构类型 **。
- 静态协议最好结合静态类型检查工具使用，连同类型提示，实现更可靠的结构类型。
- SupportsFloat 这种形式为数值静态协议。
- 在现代的 Python 中，我们有 4 种互补的接口编程方法，它们各有优缺点。
    - 鸭子类型：无须 isinstance 检查，隐式行为匹配。这种就是 python 早期的类型约定。
    - 大鹅类型：使用 isinstance 检查，显式类继承。这种就是类似 Java 语言强类型继承的形式。
    - 静态鸭子类型：依赖 PEP 544 实现的 typing.Protocol 类型提示，无需继承，显式行为匹配。这种是现代 Python 类型的推荐写法。
    - 静态类型：依赖 PEP 484 实现的类型提示，需显式继承或类型声明。个人感觉这个是介于 python 早期类型和 PEP 544 之间的类型过渡。

## ch14 继承

### StrKeyDict

```python
# StrKeyDict always converts non-string keys to `str`
# 
# This is a variation of `strkeydict.StrKeyDict` implemented
# as a `dict` built-in subclass (instead of a `UserDict` subclass)


class StrKeyDict(dict):

    def __init__(self, iterable=None, **kwds):
        super().__init__()
        self.update(iterable, **kwds)

    def __missing__(self, key):
        if isinstance(key, str):
            raise KeyError(key)
        return self[str(key)]

    def __contains__(self, key):
        return key in self.keys()or str(key) in self.keys()

    def __setitem__(self, key, item):
        super().__setitem__(str(key), item)

    def get(self, key, default=None):
        try:
            return self[key]
        except KeyError:
            return default

    def update(self, iterable=None, **kwds):
        if iterable is not None:
            try:  # duck typing FTW!
                pairs = iterable.items()
            except AttributeError:
                pairs = iterable
            for key, value in pairs:
                self[key] = value
        if kwds:
            self.update(kwds)
```

### UpperCaseMixin

```python
# ``UpperDict`` behaves like a case-insensitive mapping`.

import collections


def _upper(key):  # <1>
    try:
        return key.upper()
    except AttributeError:
        return key


class UpperCaseMixin:  # <2>
    def __setitem__(self, key, item):
        super().__setitem__(_upper(key), item)

    def __getitem__(self, key):
        return super().__getitem__(_upper(key))

    def get(self, key, default=None):
        return super().get(_upper(key), default)

    def __contains__(self, key):
        return super().__contains__(_upper(key))


class UpperDict(UpperCaseMixin, collections.UserDict):  # <1>
    pass


class UpperCounter(UpperCaseMixin, collections.Counter):  # <2>
    """Specialized 'Counter' that uppercases string keys"""  # <3>
```

### 小结

- 子类化内置类型的问题：内置类型的原生方法使用 C 语言实现，不调用子类中覆盖的方法，唯有极少数例外。
- 当需要定制 list、dict 或 str 类型时，子类化 UserList、UserDict 或 UserString 更简单。
  这些类都在 collections 模块中定义，它们其实是对内置类型的包装，并把操作委托给内置类型——这是标准库中优先选择组合而不使用继承的
  3 个例子。
- 如果所需的行为与内置类型区别很大，更容易的做法是子类化 collections.abc 模块中相应的抽象基类，然后自己实现。
- 多重继承是把双刃剑。 `__mro__` 是父类的解析顺序，保证了继承方法的名称不会发生冲突。
- 内置函数 super()在多重继承下的行为有时令人意外。super() 的行为是为了支持混入类。
- 以不区分大小写的映射 UpperCaseMixin 为例，展开了对混入类的研究。
- Django 基于类的视图和 GUI 工具包 Tkinter 都充分利用了多重继承。 Tkinter 说明旧系统可能会把类层次结构搞得过度复杂。
- 现在的趋势是摒除继承（甚至是单一继承）。Go 是典型，没有“类”结构，通过封装字段的结构体构建类型，而且可以为结构体依附方法。
- Go 语言中的接口由编译器通过结构类型（静态鸭子类型）检查，这与 Python 3.8 引入的协议类型非常相似。
- Go 语言为构建类型和组合接口提供了特殊句法，不支持继承（甚至接口之间也不能继承）。
- 关于继承，最好的建议是：尽可能避免使用。而现实中，我们往往没有选择，因为我们使用的框架有自己的设计选择。

## ch15 类型提示进阶

### 正确注解内置函数 max

```python
from collections.abc import Callable, Iterable
from typing import Protocol, Any, TypeVar, overload, Union


class SupportsLessThan(Protocol):
    def __lt__(self, other: Any) -> bool: ...


T = TypeVar('T')
LT = TypeVar('LT', bound=SupportsLessThan)
DT = TypeVar('DT')

MISSING = object()
EMPTY_MSG = 'max() arg is an empty sequence'


@overload
def max(__arg1: LT, __arg2: LT, *args: LT, key: None = ...) -> LT:
    ...


@overload
def max(__arg1: T, __arg2: T, *args: T, key: Callable[[T], LT]) -> T:
    ...


@overload
def max(__iterable: Iterable[LT], *, key: None = ...) -> LT:
    ...


@overload
def max(__iterable: Iterable[T], *, key: Callable[[T], LT]) -> T:
    ...


@overload
def max(__iterable: Iterable[LT], *, key: None = ...,
        default: DT)-> Union[LT, DT]:
    ...


@overload
def max(__iterable: Iterable[T], *, key: Callable[[T], LT],
        default: DT)-> Union[T, DT]:
    ...


def max(first, *args, key=None, default=MISSING):
    if args:
        series = args
        candidate = first
    else:
        series = iter(first)
        try:
            candidate = next(series)
        except StopIteration:
            if default is not MISSING:
                return default
            raise ValueError(EMPTY_MSG) from None

    if key is None:
        for current in series:
            if candidate < current:
                candidate = current
    else:
        candidate_key = key(candidate)
        for current in series:
            current_key = key(current)
            if candidate_key < current_key:
                candidate = current
                candidate_key = current_key

    return candidate
```

### cast 示例

```python
import asyncio

from asyncio import StreamReader, StreamWriter

# tag::CAST_IMPORTS[]
from asyncio.trsock import TransportSocket
from typing import cast


# end::CAST_IMPORTS[]

async def handle_echo(reader: StreamReader, writer: StreamWriter) -> None:
    data = await reader.read(100)
    message = data.decode()
    addr = writer.get_extra_info('peername')

    print(f"Received {message!r} from {addr!r}")

    print(f"Send: {message!r}")
    writer.write(data)
    await writer.drain()

    print("Close the connection")
    writer.close()


async def main() -> None:
    server = await asyncio.start_server(
        handle_echo, '127.0.0.1', 8888)

    # tag::CAST_USE[]
    socket_list = cast(tuple[TransportSocket, ...], server.sockets)
    addr = socket_list[0].getsockname()
    # end::CAST_USE[]
    print(f'Serving on {addr}')

    async with server:
        await server.serve_forever()


asyncio.run(main())
```

### 不变类型：不兼容

```python
# tag::BEVERAGE_TYPES[]
from typing import TypeVar, Generic


class Beverage:  # <1>
    """Any beverage."""


class Juice(Beverage):
    """Any fruit juice."""


class OrangeJuice(Juice):
    """Delicious juice from Brazilian oranges."""


T = TypeVar('T')  # <2>


class BeverageDispenser(Generic[T]):  # <3>
    """A dispenser parameterized on the beverage type."""

    def __init__(self, beverage: T) -> None:
        self.beverage = beverage

    def dispense(self) -> T:
        return self.beverage


def install(dispenser: BeverageDispenser[Juice]) -> None:  # <4>
    """Install a fruit juice dispenser."""


# end::BEVERAGE_TYPES[]

################################################ exact type

# tag::INSTALL_JUICE_DISPENSER[]
juice_dispenser = BeverageDispenser(Juice())
install(juice_dispenser)
# end::INSTALL_JUICE_DISPENSER[]


################################################ variant dispenser

# tag::INSTALL_BEVERAGE_DISPENSER[]
beverage_dispenser = BeverageDispenser(Beverage())
install(beverage_dispenser)
## mypy: Argument 1 to "install" has
## incompatible type "BeverageDispenser[Beverage]"
##          expected "BeverageDispenser[Juice]"
# end::INSTALL_BEVERAGE_DISPENSER[]


################################################ variant dispenser

# tag::INSTALL_ORANGE_JUICE_DISPENSER[]
orange_juice_dispenser = BeverageDispenser(OrangeJuice())
install(orange_juice_dispenser)
## mypy: Argument 1 to "install" has
## incompatible type "BeverageDispenser[OrangeJuice]"
##          expected "BeverageDispenser[Juice]"
# end::INSTALL_ORANGE_JUICE_DISPENSER[]
```

- `BeverageDispenser(Generic[T])` 指向的 `T = TypeVar('T')` 定义的是不变类型，也是最为常见的类型，具有排他性。
- 由于 `install(dispenser: BeverageDispenser[Juice])` 函数的参数 dispenser 的泛型参数为 Juice，因此这里就可以确定，
  只有 `BeverageDispenser(Juice())` 这种实例可以被 install，其他类型都无法通过。

### 协变类型：向下兼容

```python
from typing import TypeVar, Generic


class Beverage:
    """Any beverage."""


class Juice(Beverage):
    """Any fruit juice."""


class OrangeJuice(Juice):
    """Delicious juice from Brazilian oranges."""


# tag::BEVERAGE_TYPES[]
T_co = TypeVar('T_co', covariant=True)  # <1>


class BeverageDispenser(Generic[T_co]):  # <2>
    def __init__(self, beverage: T_co) -> None:
        self.beverage = beverage

    def dispense(self) -> T_co:
        return self.beverage


def install(dispenser: BeverageDispenser[Juice]) -> None:  # <3>
    """Install a fruit juice dispenser."""


# end::BEVERAGE_TYPES[]

################################################ covariant dispenser

# tag::INSTALL_JUICE_DISPENSERS[]
juice_dispenser = BeverageDispenser(Juice())
install(juice_dispenser)

orange_juice_dispenser = BeverageDispenser(OrangeJuice())
install(orange_juice_dispenser)
# end::INSTALL_JUICE_DISPENSERS[]

################################################ more general dispenser

# tag::INSTALL_BEVERAGE_DISPENSER[]
beverage_dispenser = BeverageDispenser(Beverage())
install(beverage_dispenser)
## mypy: Argument 1 to "install" has
## incompatible type "BeverageDispenser[Beverage]"
##          expected "BeverageDispenser[Juice]"
# end::INSTALL_BEVERAGE_DISPENSER[]
```

- `T_co = TypeVar('T_co', covariant=True)` 定义协变类型，因此当具体类型替换 T 时，T 的子类也能正常工作。
- `install(dispenser: BeverageDispenser[Juice])` 函数的参数中，具体类型 Juice 替换了 T ，因此 Juice 的子类也能正常工作。
- 所以 BeverageDispenser(Juice()) 和 BeverageDispenser(OrangeJuice()) 都可以正常通过类型检查
- 而 BeverageDispenser(Beverage()) 则无法通过类型检查

### 逆变类型：向上兼容

```python
# tag::TRASH_TYPES[]
from typing import TypeVar, Generic


class Refuse:  # <1>
    """Any refuse."""


class Biodegradable(Refuse):
    """Biodegradable refuse."""


class Compostable(Biodegradable):
    """Compostable refuse."""


T_contra = TypeVar('T_contra', contravariant=True)  # <2>


class TrashCan(Generic[T_contra]):  # <3>
    def put(self, refuse: T_contra) -> None:
        """Store trash until dumped."""


def deploy(trash_can: TrashCan[Biodegradable]):
    """Deploy a trash can for biodegradable refuse."""


# end::TRASH_TYPES[]


################################################ contravariant trash can


# tag::DEPLOY_TRASH_CANS[]
bio_can: TrashCan[Biodegradable] = TrashCan()
deploy(bio_can)

trash_can: TrashCan[Refuse] = TrashCan()
deploy(trash_can)
# end::DEPLOY_TRASH_CANS[]


################################################ more specific trash can

# tag::DEPLOY_NOT_VALID[]
compost_can: TrashCan[Compostable] = TrashCan()
deploy(compost_can)
## mypy: Argument 1 to "deploy" has incompatible type "TrashCan[Compostable]" expected "TrashCan[Biodegradable]"
# end::DEPLOY_NOT_VALID[]
```

- `T_contra = TypeVar('T_contra', contravariant=True)` 定义逆变类型，因此 T_contra 能被具体类型和具体类型的父类型替换。
- 所以 TrashCan[Biodegradable] 和 TrashCan[Refuse] 都可以通过类型检查
- 而 TrashCan[Compostable] 属于容器子类型，在逆变环境下无法通过类型检查。

### TypeDict

```python
from typing import TypedDict


class BookDict(TypedDict):
    isbn: str
    title: str
    authors: list[str]
    pagecount: int
```

### RandomPopper

```python
import random
from typing import Any, Iterable, TYPE_CHECKING
from typing import Protocol, runtime_checkable


@runtime_checkable
class RandomPopper(Protocol):
    def pop_random(self) -> Any: ...


class SimplePopper:
    def __init__(self, items: Iterable) -> None:
        self._items = list(items)
        random.shuffle(self._items)

    def pop_random(self) -> Any:
        return self._items.pop()


def test_issubclass() -> None:
    assert issubclass(SimplePopper, RandomPopper)


def test_isinstance() -> None:
    popper: RandomPopper = SimplePopper([1])
    if TYPE_CHECKING:
        reveal_type(popper)
        # Revealed type is 'randompop.RandomPopper'
    assert isinstance(popper, RandomPopper)
```

### RandomPicker

```python
import random
from typing import Iterable, Generic, TypeVar, TYPE_CHECKING
from typing import Protocol, runtime_checkable

T_co = TypeVar('T_co', covariant=True)


@runtime_checkable
class RandomPicker(Protocol[T_co]):  # <2>
    def pick(self) -> T_co: ...  # <3>


class LottoPicker(Generic[T_co]):
    def __init__(self, items: Iterable[T_co]) -> None:
        self._items = list(items)
        random.shuffle(self._items)

    def pick(self) -> T_co:
        return self._items.pop()


def test_issubclass() -> None:
    assert issubclass(LottoPicker, RandomPicker)


def test_isinstance() -> None:
    popper: RandomPicker = LottoPicker[int]([1])
    if TYPE_CHECKING:
        reveal_type(popper)
        # Revealed type is 'generic_randompick.RandomPicker[Any]'
    assert isinstance(popper, LottoPicker)


def test_pick_type() -> None:
    balls = [1, 2, 3]
    popper = LottoPicker(balls)
    pick = popper.pick()
    assert pick in balls
    if TYPE_CHECKING:
        reveal_type(pick)
        # Revealed type is 'builtins.int'
```

### mysum

```python
import functools
import operator
from collections.abc import Iterable
from typing import overload, Union, TypeVar

T = TypeVar('T')
S = TypeVar('S')  # <1>


@overload
def sum(it: Iterable[T]) -> Union[T, int]: ...  # <2>


@overload
def sum(it: Iterable[T], /, start: S)-> Union[T, S]: ...  # <3>


def sum(it, /, start=0):  # <4>
    return functools.reduce(operator.add, it, start)
```

### 一个生成器例子中的类型

```python
from typing import Generator


# Generator[YieldType, SendType, ReturnType]

def gen_float_take_int()-> Generator[float, int, str]:
    received = yield -1.0
    while received:
        received = yield float(received)
    return 'Done'
```

对于 `Generator[float, int, str]`，解释如下：

- float：生成器 yield 出的值类型（每次生成一个浮点数）。
- int：生成器通过 send() 接收的值类型（外部传入一个整数）。
- str：生成器最终返回的值类型（return 字符串）。

可以采取下面的实验：

```python
gen = gen_float_take_int()  # 初始化生成器

# 启动生成器（必须先用 None 或 next() 激活）
value = next(gen)  # 生成器 yield -1.0，value = -1.0

# 发送数据并获取下一个值
value = gen.send(42)  # received = 42 → yield 42.0 → value = 42.0
value = gen.send(10)  # received = 10 → yield 10.0 → value = 10.0

# 终止生成器
try:
    gen.send(0)  # received = 0 → while 终止 → return 'Done'
except StopIteration as e:
    print(e.value)  # 输出: 'Done'
```

### 小结

- 使用多个重载的签名正确注解内置函数 max。
- TypedDict 不是类构建器，它只用于为值是字典的变量或参数添加类型提示，指明各个字符串键对应的值类型（把字典用作记录，通常是在处理
  JSON 数据时）。
- 为类型检查工具提供指引的 typing.cast 函数。
- 在运行时访问类型提示。使用 `typing.get_type_hints`，而不要直接读取 `__annotations__` 属性。
- 泛化的 4 个基本术语：泛型、形式类型参数、参数化类型和具体类型参数。
    - 泛型: 具有一个或多个类型变量的类型, LottoBlower[T] 和 abc.Mapping[KT, VT]
    - 形式类型参数: 泛型声明中出现的类型变量, abc.Mapping[KT, VT] 中的 KT 和 VT
    - 参数化类型: 使用具体类型参数声明的类型, LottoBlower[int] 和 abc.Mapping[str, float]
    - 具体类型参数: 声明参数化类型时为参数提供的具体类型, LottoBlower[int] 中的 int。
- 型变相关。以“现实中”的食堂饮料自动售货机和垃圾桶为例，讲解了不变类型、协变类型和逆变类型。
- 如何定义泛化静态协议。首先分析了标准库中的 typing.SupportsAbs 协议。然后“照葫芦画瓢”，定义了 RandomPicker 协议。

## ch16 运算符重载

### `__add__` 和 `__iadd__`

```python
from tombola import Tombola
from bingo import BingoCage


class AddableBingoCage(BingoCage):  # <1>

    def __add__(self, other):
        if isinstance(other, Tombola):  # <2>
            return AddableBingoCage(self.inspect() + other.inspect())
        else:
            return NotImplemented

    def __iadd__(self, other):
        if isinstance(other, Tombola):
            other_iterable = other.inspect()  # <3>
        else:
            try:
                other_iterable = iter(other)  # <4>
            except TypeError:  # <5>
                msg = ('right operand in += must be '
                       "'Tombola' or an iterable")
                raise TypeError(msg)
        self.load(other_iterable)  # <6>
        return self  # <7>
```

### decimal.localcontext()

```python
import decimal

if __name__ == '__main__':
    with decimal.localcontext() as ctx:
        ctx.prec = 40
        print('precision:', ctx.prec)
        one_third = decimal.Decimal('1') / decimal.Decimal('3')
        print('    one_third:', one_third)
        print('   +one_third:', +one_third)

    print('precision:', decimal.getcontext().prec)
    print('    one_third:', one_third)
    print('   +one_third:', +one_third)
```

```bash
precision: 40
    one_third: 0.3333333333333333333333333333333333333333
   +one_third: 0.3333333333333333333333333333333333333333
precision: 28
    one_third: 0.3333333333333333333333333333333333333333
   +one_third: 0.3333333333333333333333333333
```

### vector_v6.py `__add__` `__radd__`

```python
class Vector:
    # ...

    def __add__(self, other):
        try:
            pairs = itertools.zip_longest(self, other, fillvalue=0.0)
            return Vector(a + b for a, b in pairs)
        except TypeError:
            return NotImplemented

    def __radd__(self, other):
        return self + other
```

### vector_v7.py `__mul__` 系列方法

```python
class Vector:
    # ...

    def __mul__(self, scalar):
        try:
            factor = float(scalar)
        except TypeError:
            return NotImplemented
        return Vector(n * factor for n in self)

    def __rmul__(self, scalar):
        # 委托给 __mul__ 处理
        return self * scalar

    def __matmul__(self, other):
        if (isinstance(other, abc.Sized) and
                isinstance(other, abc.Iterable)):
            if len(self) == len(other):
                return sum(a * b for a, b in zip(self, other))
            else:
                raise ValueError('@ requires vectors of equal length.')
        else:
            return NotImplemented

    def __rmatmul__(self, other):
        return self @ other
```

- 当 Python 遇到表达式 a * b 时，调用逻辑如下：
    - 先尝试调用 `a.__mul__(b)`（即 a 的 `__mul__` 方法）。
    - 如果 `a.__mul__(b)` 返回 NotImplemented，则尝试调用 `b.__rmul__(a)`（即 b 的反向乘法方法）。
- `__mul__(self, scalar)` 对应 Vector 实例 v 作为左操作数，即 v * scalar
- `__rmul__(self, scalar)` 对应 Vector 实例 v 作为右操作数，即 scalar * v
- 乘法应满足 `a * b == b * a`，即乘法可交换性， 此时实现 `__rmul__` 才有意义。
- `__matmul__` 和 `__rmatmul__` 对应高维向量的矩阵乘法。

### vector_v8.py `__eq__`

```python
    def __eq__(self, other):


if isinstance(other, Vector):  # <1>
    return (len(self) == len(other) and
            all(a == b for a, b in zip(self, other)))
else:
    return NotImplemented  # <2>
```

### 小结

- Python 禁止重载内置类型的运算符，不过有几个例外（is、and、or 和 not）。
- 重载一元运算符，了解 `__neg__` 方法和 `__pos__` 方法。
- 重载中缀运算符，例如由 `__add__` 方法提供支持的 + 运算符。
- 一元运算符和中缀运算符的结果是新对象，不能修改操作数。为了支持其他类型，返回了特殊的 NotImplemented 值（不是异常），
  让解释器尝试对调操作数，然后调用运算符的反向特殊方法（例如 `__radd__`）。
- 如果操作数的类型不同，就要检测出不能处理的操作数。使用两种方式处理：一种是利用鸭子类型，直接尝试执行运算，如
  果有问题，就捕获 TypeError 异常；另一种是显式使用 isinstance 测试，`__mul__` 方法和 `__matmul__` 方法就是这样。
- 代码库应该利用鸭子类型，让用户能使用尽可能多的对象，
- 在重载运算符的特殊方法时，通常有效的方法是使用 isinstance 测试抽象基类，检查类型。
- 如果抽象基类实现了 `__subclasshook__`，只要对象提供了所需的方法，无须子类化或注册就能通过针对抽象基类的 isinstance 检查。
- 通过 `__eq__` 方法实现 ==，而且发现 Python 在 object 基类中通过 `__ne__` 方法为 != 提供了便利的实现。
- Python 比较对象的 ID 是比较对象最后的兜底做法。
- 增量赋值运算符。a += b 被当成 a = a + b 处理。这个操作始终会创建新对象，因此可变类型和不可变类型都适用。
- 对于可变对象，可以实现就地特殊方法，例如支持 += 的 `__iadd__` 方法，然后修改左侧操作数的值。
- 在可接受的类型方面，+ 比 += 严格。对于序列类型，+ 通常要求两个操作数属于同一类型，而 += 的右侧操作数往往可以是任何可迭代对象。

第四部分：控制流

## ch17 迭代器、生成器和经典协程

### fibonacci 的两种实现

```python
class Fibonacci:

    def __iter__(self):
        return FibonacciGenerator()


class FibonacciGenerator:

    def __init__(self):
        self.a = 0
        self.b = 1

    def __next__(self):
        result = self.a
        self.a, self.b = self.b, self.a + self.b
        return result

    def __iter__(self):
        return self


# for comparison, this is the usual implementation of a Fibonacci generator in Python:

def fibonacci():
    a, b = 0, 1
    while True:
        yield a
        a, b = b, a + b
```

- iter(iterable) → iterator
- iterator.`__next__()` → item

### 可迭代对象 (Iterable)

实现了 `__iter__()` 方法的对象

```python
class Sentence:
    def __init__(self, text):
        self.text = text
        self.words = RE_WORD.findall(text)

    def __repr__(self):
        return f'Sentence({reprlib.repr(self.text)})'

    def __iter__(self):  # <1>
        return SentenceIterator(self.words)  # <2>
```

### 迭代器 (Iterator)

实现了 `__iter__()` 和 `__next__()` 的对象

```python
class SentenceIterator:

    def __init__(self, words):
        self.words = words  # <3>
        self.index = 0  # <4>

    def __next__(self):
        try:
            word = self.words[self.index]  # <5>
        except IndexError:
            raise StopIteration()  # <6>
        self.index += 1  # <7>
        return word  # <8>

    def __iter__(self):  # <9>
        return self
```

可通过 next() 直接获取下一个元素。

### Sentence 的三种实现

第一种

```python
class Sentence:

    def __init__(self, text):
        self.text = text
        self.words = RE_WORD.findall(text)

    def __repr__(self):
        return 'Sentence(%s)' % reprlib.repr(self.text)

    def __iter__(self):
        for word in self.words:  # <1>
            yield word  # <2>
```

第二种

```python
    def __iter__(self):


for match in RE_WORD.finditer(self.text):  # <2>
    yield match.group()  # <3>
```

第三种

```python
    def __iter__(self):


return (match.group() for match in RE_WORD.finditer(self.text))
```

---

多文件的文档测试

```python
TARGET_GLOB = 'sentence*.py'


def main(argv):
    verbose = '-v' in argv
    for module_file_name in sorted(glob.glob(TARGET_GLOB)):
        module_name = module_file_name.replace('.py', '')
        module = importlib.import_module(module_name)
        try:
            cls = getattr(module, 'Sentence')
        except AttributeError:
            continue
        test(cls, verbose)
```

### 认识 Python 内置异常类的继承关系

```python
def tree(cls, level=0):
    yield cls.__name__, level
    for sub_cls in cls.__subclasses__():
        yield from tree(sub_cls, level + 1)


if __name__ == '__main__':
    for cls_name, level in tree(BaseException):
        indent = ' ' * 4 * level
        print(f'{indent}{cls_name}')
```

输出结果

```bash
BaseException
    Exception
        TypeError
        StopAsyncIteration
        StopIteration
        ImportError
            ModuleNotFoundError
            ZipImportError
        OSError
            ConnectionError
                BrokenPipeError
                ConnectionAbortedError
                ConnectionRefusedError
                ConnectionResetError
            BlockingIOError
            ChildProcessError
            FileExistsError
            FileNotFoundError
            IsADirectoryError
            NotADirectoryError
            InterruptedError
            PermissionError
            ProcessLookupError
            TimeoutError
            UnsupportedOperation
        EOFError
        RuntimeError
            RecursionError
            NotImplementedError
            _DeadlockError
        NameError
            UnboundLocalError
        AttributeError
        SyntaxError
            IndentationError
                TabError
        LookupError
            IndexError
            KeyError
            CodecRegistryError
        ValueError
            UnicodeError
                UnicodeEncodeError
                UnicodeDecodeError
                UnicodeTranslateError
            UnsupportedOperation
        AssertionError
        ArithmeticError
            FloatingPointError
            OverflowError
            ZeroDivisionError
        SystemError
            CodecRegistryError
        ReferenceError
        MemoryError
        BufferError
        Warning
            UserWarning
            EncodingWarning
            DeprecationWarning
            PendingDeprecationWarning
            SyntaxWarning
            RuntimeWarning
            FutureWarning
            ImportWarning
            UnicodeWarning
            BytesWarning
            ResourceWarning
    GeneratorExit
    SystemExit
    KeyboardInterrupt
```

### 树形结构的文本可视化

上面的 tree 结构仅仅依靠缩进来区分层级，不是特别直观。常见的显示优化是将缩进的空格替换为树形连接线。

为了实现这个功能，我们需要区分每一行中，当前行是否为最后一个 sibling 节点

```python
def tree(cls, level=0, last_sibling=True):
    yield cls, level, last_sibling
    subclasses = cls.__subclasses__()
    if subclasses:
        last = subclasses[-1]
    for sub_cls in subclasses:
        yield from tree(sub_cls, level + 1, sub_cls is last)
```

使用测试用例来理解更加直观

```python
def test_4_levels_3_leaves():
    class A: pass

    class B1(A): pass

    class B2(A): pass

    class C1(B1): pass

    class C2(B2): pass

    class D1(C1): pass

    class D2(C1): pass

    expected = [
        ('A', 0, True),
        ('B1', 1, False),
        ('C1', 2, True),
        ('D1', 3, False),
        ('D2', 3, True),
        ('B2', 1, True),
        ('C2', 2, True),
    ]

    result = [(cls.__name__, level, last)
              for cls, level, last in tree(A)]
    assert expected == result
```

理解了上面的基础后，就可以实现了

```python
# 	Unicode 符号
SP = '\N{SPACE}'
HLIN = '\N{BOX DRAWINGS LIGHT HORIZONTAL}' * 2 + SP  # ──
VLIN = '\N{BOX DRAWINGS LIGHT VERTICAL}' + SP * 3  # │
TEE = '\N{BOX DRAWINGS LIGHT VERTICAL AND RIGHT}' + HLIN  # ├──
ELBOW = '\N{BOX DRAWINGS LIGHT UP AND RIGHT}' + HLIN  # └──


def subclasses(cls):
    try:
        return cls.__subclasses__()
    except TypeError:  # handle the `type` type
        return cls.__subclasses__(cls)


def tree(cls, level=0, last_sibling=True):
    yield cls, level, last_sibling
    children = subclasses(cls)
    if children:
        last = children[-1]
        for child in children:
            yield from tree(child, level + 1, child is last)


def render_lines(tree_iter):
    cls, _, _ = next(tree_iter)  # 获取根节点
    yield cls.__name__  # 直接返回根节点类名（无前缀）
    prefix = ''

    for cls, level, last in tree_iter:
        prefix = prefix[:4 * (level - 1)]
        prefix = prefix.replace(TEE, VLIN).replace(ELBOW, SP * 4)
        prefix += ELBOW if last else TEE
        yield prefix + cls.__name__


def draw(cls):
    for line in render_lines(tree(cls)):
        print(line)


if __name__ == '__main__':
    # A
    # ├── B
    # │   └── C
    # └── D
    class A: pass


    class B(A): pass


    class C(B): pass


    class D(A): pass


    draw(A)
```

render_lines 函数中间过程分析：

- `prefix = prefix[:4 * (level - 1)]` => 含义是对于当前层级而言，取上一级的前缀
- `prefix.replace(TEE, VLIN).replace(ELBOW, SP * 4)` 符号替换逻辑：
    - a) 如果父节点是 ├── → 改为 │（表示后续还有兄弟节点）
    - b) 如果父节点是 └── → 改为空格（分支结束）
- 循环变量分析
    - B 取前缀'', 补充 TEE '├── '，最后为 ├──
    - C 取前缀'├── ' ，转化为 '│   ', 补充 ELBOW '└── ' ，最后为 '│ └── '
    - D 取前缀 ''，补充 ELBOW'└── '，最后为 └──

此外，利用 draw(BaseException) 既可以打印 python 异常层级结构。

```bash
BaseException
├── Exception
│   ├── TypeError
│   ├── StopAsyncIteration
│   ├── StopIteration
│   ├── ImportError
│   │   ├── ModuleNotFoundError
│   │   └── ZipImportError
│   ├── OSError
│   │   ├── ConnectionError
│   │   │   ├── BrokenPipeError
│   │   │   ├── ConnectionAbortedError
│   │   │   ├── ConnectionRefusedError
│   │   │   └── ConnectionResetError
│   │   ├── BlockingIOError
│   │   ├── ChildProcessError
│   │   ├── FileExistsError
│   │   ├── FileNotFoundError
│   │   ├── IsADirectoryError
│   │   ├── NotADirectoryError
│   │   ├── InterruptedError
│   │   ├── PermissionError
│   │   ├── ProcessLookupError
│   │   ├── TimeoutError
│   │   └── UnsupportedOperation
│   ├── EOFError
│   ├── RuntimeError
│   │   ├── RecursionError
│   │   ├── NotImplementedError
│   │   └── _DeadlockError
│   ├── NameError
│   │   └── UnboundLocalError
│   ├── AttributeError
│   ├── SyntaxError
│   │   └── IndentationError
│   │       └── TabError
│   ├── LookupError
│   │   ├── IndexError
│   │   ├── KeyError
│   │   └── CodecRegistryError
│   ├── ValueError
│   │   ├── UnicodeError
│   │   │   ├── UnicodeEncodeError
│   │   │   ├── UnicodeDecodeError
│   │   │   └── UnicodeTranslateError
│   │   └── UnsupportedOperation
│   ├── AssertionError
│   ├── ArithmeticError
│   │   ├── FloatingPointError
│   │   ├── OverflowError
│   │   └── ZeroDivisionError
│   ├── SystemError
│   │   └── CodecRegistryError
│   ├── ReferenceError
│   ├── MemoryError
│   ├── BufferError
│   └── Warning
│       ├── UserWarning
│       ├── EncodingWarning
│       ├── DeprecationWarning
│       ├── PendingDeprecationWarning
│       ├── SyntaxWarning
│       ├── RuntimeWarning
│       ├── FutureWarning
│       ├── ImportWarning
│       ├── UnicodeWarning
│       ├── BytesWarning
│       └── ResourceWarning
├── GeneratorExit
├── SystemExit
└── KeyboardInterrupt
```

### 一个平均数生成器

```python
from collections.abc import Generator
from typing import Union, NamedTuple


class Result(NamedTuple):  # <1>
    count: int  # type: ignore  # <2>
    average: float


class Sentinel:  # <3>
    def __repr__(self):
        return f'<Sentinel>'


STOP = Sentinel()  # <4>
SendType = Union[float, Sentinel]  # <5>


def averager2(verbose: bool = False) -> Generator[None, SendType, Result]:  # <1>
    total = 0.0
    count = 0
    average = 0.0
    while True:
        term = yield  # <2>
        if verbose:
            print('received:', term)
        if isinstance(term, Sentinel):  # <3>
            break
        total += term  # <4>
        count += 1
        average = total / count
    return Result(count, average)  # <5>
```

client 代码：

```python
def compute():
    res = yield from averager2(True)  # <1>
    print('computed:', res)  # <2>
    return res  # <3>


comp = compute()  # <4>
for v in [None, 10, 20, 30, STOP]:  # <5>
    try:
        comp.send(v)  # <6>
    except StopIteration as exc:  # <7>
        result = exc.value
```

输出

```
received: 10
received: 20
received: 30
received: <Sentinel>
computed: Result(count=3, average=20.0)
```

分析：

- comp = compute()  仅返回一个生成器对象，代码停留在函数入口，尚未执行 yield from。
- 首次调用 comp.send(None)： 生成器执行到 term = yield 后暂停，term 未被赋值。因此不会打印 `received: None`。
  这是 Python 生成器设计中的故意行为，确保协程的清晰状态管理。
- 首次 send(None) 是生成器的“启动信号”，不是实际数据传输。
- 末尾的 send(STOP) 是生成器的“停止信号”，也不是实际数据传输。
- yield from 的返回值 res，指的是 averager2 函数的  `return Result(count, average)`

### 小结

- 使用内置函数 iter()把类似序列的对象创建为迭代器；然后定义了一个含有 `__next__()` 方法的类，实现经典迭代器。
- 一个用于生成等差数列的生成器，可以利用 itertools 模块简化代码。
- 概述了标准库中多数通用的生成器函数。
- 通过 chain 和 tree 示例，以简单的生成器为背景，学习 yield from 表达式。
- Python 3.5 增加原生协程。
- 经典协程在实践中难以使用，不过它是原生协程的基础，而且 await 就源自 yield from 表达式。
- 介绍了 Iterable、Iterator 和 Generator 类型的类型提示。其中，Generator 带有可逆变的类型参数，这是比較少见的。

## ch18 with、match 和 else 块

这一章聚焦于代码中的模式匹配。

### LookingGlass

实现一个反向输出文本的管理器。

用类实现

```python
import sys


class LookingGlass:

    def __enter__(self):  # <1>
        self.original_write = sys.stdout.write  # <2>
        sys.stdout.write = self.reverse_write  # <3>
        return 'JABBERWOCKY'  # <4>

    def reverse_write(self, text):  # <5>
        self.original_write(text[::-1])

    def __exit__(self, exc_type, exc_value, traceback):  # <6>
        sys.stdout.write = self.original_write  # <7>
        if exc_type is ZeroDivisionError:  # <8>
            print('Please DO NOT divide by zero!')
            return True  # <9>
```

用 @contextlib.contextmanager 实现

```python
import contextlib
import sys


@contextlib.contextmanager  # <1>
def looking_glass():
    original_write = sys.stdout.write  # <2>

    def reverse_write(text):  # <3>
        original_write(text[::-1])

    sys.stdout.write = reverse_write  # <4>
    yield 'JABBERWOCKY'  # <5>
    sys.stdout.write = original_write  # <6>
```

```
>>> @looking_glass()
... def verse():
...     print('The time has come')
...
>>> verse()  # <1>
emoc sah emit ehT
```

如果想要添加异常处理，可以在内部函数中实现，然后返回自定义的结构体（例子中是 msg）

```python
@contextlib.contextmanager
def looking_glass():
    original_write = sys.stdout.write

    def reverse_write(text):
        original_write(text[::-1])

    sys.stdout.write = reverse_write
    msg = ''  # <1>
    try:
        yield 'JABBERWOCKY'
    except ZeroDivisionError:  # <2>
        msg = 'Please DO NOT divide by zero!'
    finally:
        sys.stdout.write = original_write  # <3>
        if msg:
            print(msg)  # <4>
```

### Scheme 解释器 demo

代码位于原仓库 `/18-with-match`，这里没有进一步探究。

### 小结

- 上下文管理器和 with 语句可以自动关闭打开的文件。
- 自己动手实现上下文管理器——在某个类中定义 `__enter__`/`__exit__` 方法，同时可以在 `__exit__` 方法中处理异常。
- with 能管理资源，还可以去掉常规的设置和清理代码。
- 标准库中 contextlib 模块里的函数。@contextmanager 装饰器能把包含一个 yield 语句的简单生成器变成上下文管理器。
- 研究了 Peter Norvig 编写的优雅的 lis.py。这是 Python 编写的一个 Scheme 解释器，随后又使用 match/case 重
  构了 evaluate 函数——对任何解释器来说都是核心。
- 若想理解 evaluate，就需要懂 Scheme，还要有一个 S 表达式解析器、一个简单的 REPL，并通过 collection.ChainMap 的子类
  Environment 构造嵌套作用域。
- 透过 lis.py 不仅能进一步探讨模式匹配，还能学习更多知识。
- 深入感悟 Python 自身的核心功能：为什么要有保留关键字，作用域规则运行机制，以及如何构建和使用闭包。

## ch19 Python 并发模型

### 旋转指针 - 线程版

```python
# spinner_thread.py

# credits: Adapted from Michele Simionato's
# multiprocessing example in the python-list:
# https://mail.python.org/pipermail/python-list/2009-February/675659.html

import itertools
import time
from threading import Thread, Event


def spin(msg: str, done: Event) -> None:  # <1>
    for char in itertools.cycle(r'\|/-'):  # <2>
        status = f'\r{char} {msg}'  # <3>
        print(status, end='', flush=True)
        if done.wait(.1):  # <4>
            break  # <5>
    blanks = ' ' * len(status)
    # 抹掉状态行的所有提示符，回到行首
    print(f'\r{blanks}\r', end='')  # <6>


def slow() -> int:
    time.sleep(3)  # <7>
    return 42


def supervisor() -> int:  # <1>
    done = Event()  # <2>
    spinner = Thread(target=spin, args=('thinking!', done))  # <3>
    print(f'spinner object: {spinner}')  # <4>
    spinner.start()  # <5>
    result = slow()  # <6>
    done.set()  # <7>
    spinner.join()  # <8>
    return result


def main() -> None:
    result = supervisor()  # <9>
    print(f'Answer: {result}')


if __name__ == '__main__':
    main()
```

- slow() 在主线程中阻塞 3 秒，模拟耗时操作。
- spinner 是运行在新建的线程上的，每 0.1 秒更新一次指示状态。
- done.set()是为了通知 done.wait(.1) 返回 True
- 尽管存在 GIL，但线程版能顺利完成工作，因为 Python 会定期中断线程，而且该示例只用到两个线程，一个执行计算密集型工
  作，另一个每秒仅更新动画 10 次。

为了更加明显，下面把 slow() 换成了质数检测函数，并提供一个很大的 n 来检测。

```python
import itertools
from threading import Thread, Event

from primes import is_prime


def spin(msg: str, done: Event) -> None:  # <1>
    for char in itertools.cycle(r'\|/-'):  # <2>
        status = f'\r{char} {msg}'  # <3>
        print(status, end='', flush=True)
        if done.wait(.1):  # <4>
            break  # <5>
    blanks = ' ' * len(status)
    print(f'\r{blanks}\r', end='')  # <6>


def check(n: int) -> int:
    return is_prime(n)


def supervisor(n: int) -> int:  # <1>
    done = Event()  # <2>
    # spinner 在另外一个线程
    # 思考为什么进度条可以显示，GIL 无效了吗？

    # 答案：GIL 依旧有效。
    # a) 运行 is_prime 的主线程每 5 毫秒中断一次，然后 spin 线程复苏，迭代一次 for 循环，显示 loading 指标
    # （直到在 done 事件上调用 wait 方法，释放 GIL）
    # b) 随后，主线程再次获得 GIL，is_prime 接着计算 5 毫秒

    # spin 函数迭代一次的速度很快，收到 done 事件后就释放 GIL，所以几乎没有争用 GIL。多数时候，GIL 由运行 is_prime 的主线程持有。
    # 如果有两个或以上线程都想占用大量 CPU 时间，那么程序运行速度要比顺序执行的代码更慢。
    spinner = Thread(target=spin, args=('thinking!', done))  # <3>
    print(f'spinner object: {spinner}')  # <4>
    spinner.start()  # <5>
    # check 在主线程
    result = check(n)  # <6>
    done.set()  # <7>
    spinner.join()  # <8>
    return result


def main() -> None:
    n = 5_000_111_000_222_021
    result = supervisor(n)  # <9>
    msg = 'is' if result else 'is not'
    print(f'{n:,} {msg} prime')


if __name__ == '__main__':
    main()
```

### 旋转指针 - 进程版

```python
from multiprocessing import Process, Event  # <1>
from multiprocessing import synchronize  # <2>


def spin(msg: str, done: synchronize.Event) -> None:
    ...


def supervisor() -> int:
    done = Event()
    spinner = Process(target=spin, args=('thinking!', done))
    print(f'spinner object: {spinner}')  # <5>
    spinner.start()
    result = slow()
    done.set()
    spinner.join()
    return result
```

- multiprocessing 版本能绕开 GIL，启动一个新进程处理动画，而主线程负责素数检测。

### 旋转指针 - 协程版

```python
# spinner_async.py

# credits: Example by Luciano Ramalho inspired by
# Michele Simionato's multiprocessing example in the python-list:
# https://mail.python.org/pipermail/python-list/2009-February/675659.html

import asyncio
import itertools


async def spin(msg: str) -> None:  # <1>
    for char in itertools.cycle(r'\|/-'):
        status = f'\r{char} {msg}'
        print(status, flush=True, end='')
        try:
            await asyncio.sleep(.1)  # <2>
        except asyncio.CancelledError:  # <3>
            break
    blanks = ' ' * len(status)
    print(f'\r{blanks}\r', end='')


async def slow() -> int:
    await asyncio.sleep(3)  # <4>
    return 42


async def supervisor() -> int:  # <3>
    spinner = asyncio.create_task(spin('thinking!'))  # <4>
    # spinner object: <Task pending name='Task-2' coro=<spin() running at path\to\spinner_async.py:11>>
    print(f'spinner object: {spinner}')  # <5>
    result = await slow()  # <6>
    spinner.cancel()  # <7>
    return result


def main() -> None:  # <1>
    result = asyncio.run(supervisor())  # <2>
    print(f'Answer: {result}')


if __name__ == '__main__':
    main()
```

- 这个例子，依旧可以观察到指针文本在转动，然后计算出 42 结果。
- 原因在于 slow()函数中使用的是 await asyncio.sleep(3) ，也就是兼容协程的延迟等待。
  如果换成 time.sleep(3) 就会阻塞其他协程，具体地，也就是阻塞指针转动的这个协程的正常运行。

### 质数检测 - 多进程

朴素实现

```python
def is_prime(n: int) -> bool:
    if n < 2:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False

    root = math.isqrt(n)
    for i in range(3, root + 1, 2):
        if n % i == 0:
            return False
    return True
```

换成多进程实现

```python
#!/usr/bin/env python3

"""
procs.py: shows that multiprocessing on a multicore machine
can be faster than sequential code for CPU-intensive work.
"""

import sys
from time import perf_counter
from typing import NamedTuple
from multiprocessing import Process, SimpleQueue, cpu_count  # <1>
from multiprocessing import queues  # <2>

from primes import is_prime, NUMBERS


class PrimeResult(NamedTuple):  # <3>
    n: int
    prime: bool
    elapsed: float


JobQueue = queues.SimpleQueue[int]  # <4>
ResultQueue = queues.SimpleQueue[PrimeResult]  # <5>


def check(n: int) -> PrimeResult:  # <6>
    t0 = perf_counter()
    res = is_prime(n)
    return PrimeResult(n, res, perf_counter() - t0)


def worker(jobs: JobQueue, results: ResultQueue) -> None:  # <7>
    # 某个 worker 遇到 0 则跳出循坏，并且放置一个标记结束的 PrimeResult
    while n := jobs.get():  # <8>
        results.put(check(n))  # <9>
    results.put(PrimeResult(0, False, 0.0))  # <10>


def start_jobs(
        procs: int, jobs: JobQueue, results: ResultQueue  # <11>
) -> None:
    for n in NUMBERS:
        jobs.put(n)  # <12>
    for _ in range(procs):
        proc = Process(target=worker, args=(jobs, results))  # <13>
        proc.start()  # <14>
        # 发送 0, 这不是需要计算的数据，而是一个给 report 的指示信号
        jobs.put(0)  # <15>


def report(procs: int, results: ResultQueue) -> int:  # <6>
    checked = 0
    procs_done = 0
    while procs_done < procs:  # <7>
        n, prime, elapsed = results.get()  # <8>
        if n == 0:  # <9>
            procs_done += 1
        else:
            checked += 1  # <10>
            label = 'P' if prime else ' '
            print(f'{n:16}  {label} {elapsed:9.6f}s')

    print(f'procs done: {procs_done}')
    print(f'checked: {checked}')
    return checked


def main() -> None:
    if len(sys.argv) < 2:  # <1>
        procs = cpu_count()
    else:
        procs = int(sys.argv[1])

    print(f'Checking {len(NUMBERS)} numbers with {procs} processes:')
    t0 = perf_counter()
    jobs: JobQueue = SimpleQueue()  # <2>
    results: ResultQueue = SimpleQueue()
    start_jobs(procs, jobs, results)  # <3>
    checked = report(procs, results)  # <4>
    elapsed = perf_counter() - t0
    print(f'{checked} checks in {elapsed:.2f}s')  # <5>


if __name__ == '__main__':
    main()
```

- start_jobs 逻辑：jobs 放置 N1,N2,N3,...NN；然后 proc1 启动，jobs 放置 0；proc2 启动，jobs 放置 0；...
- worker 计算逻辑；遇到非 0 数据就进行实际计算，遇到 0 就往 jobs 传递这个完成信号，然后自身退出。
- report 逻辑：通过约定的哨符 0 就累计已完成工作而退出的 procs 的数量，遇到非 0 就统计计算数据。
- 注意：这里 JobQueue 和 ResultQueue 对于 start_jobs 是全局的。如果 start_jobs 被多次调用，程序存在竟态条件。
  比较好的做法是将 JobQueue 和 ResultQueue 改为 start_jobs 的本地变量。此时每次 start_jobs 内部的队列就是独立维护的。

### 质数检测 - 协程版

如果你选用协程来处理 CPU 密集型工作，很可能你选择了错误的方法，协程适合 IO 密集型。因此这里不会选用 asyncio 来实现。

### 小结

- 使用 Python 的 3 种原生并发编程模型实现了旋转指针（spinner）脚本：
    - 使用 threading 包实现线程版；
    - 使用 multiprocessing 包实现进程版；
    - 使用 asyncio 包实现异步协程版。
- 通过一个实验探讨了 GIL 的真正影响：把旋转指针动画示例换成了素数检测的示例，观察由此产生的行为。
- 根据计算素数的示例，理解 multiprocessing 和 threading 的区别，证实了只有进程才能让 Python 从多核 CPU 中受益。
- asyncio 不善于处理 CPU 密集型函数，它们会阻塞事件循环。
- 对于 CPU 密集型计算，受 GIL 限制，线程版比顺序执行版的表现还要差。如果没有 GIL，其实线程版才是轻量高效的最佳选择。
- 数据科学和服务器端开发社区已经使用工业级解决方案绕开了 GIL，可以满足特定的需求。
- 支持 Python 服务器端应用程序弹性 伸缩的两个常用工具：WSGI 应用服务器和分布式任务队列。

## ch20 并发执行器 executors

### flags_common

```python
"""Utilities for second set of flag examples.
"""

import argparse
import string
import sys
import time
from collections import Counter
from enum import Enum
from pathlib import Path

DownloadStatus = Enum('DownloadStatus', 'OK NOT_FOUND ERROR')

POP20_CC = ('CN IN US ID BR PK NG BD RU JP '
            'MX PH VN ET EG DE IR TR CD FR').split()

DEFAULT_CONCUR_REQ = 1
MAX_CONCUR_REQ = 1

SERVERS = {
    'REMOTE': 'https://www.fluentpython.com/data/flags',
    'LOCAL': 'http://localhost:8000/flags',
    'DELAY': 'http://localhost:8001/flags',
    'ERROR': 'http://localhost:8002/flags',
}
DEFAULT_SERVER = 'LOCAL'

DEST_DIR = Path('downloaded')
COUNTRY_CODES_FILE = Path('country_codes.txt')


def save_flag(img: bytes, filename: str) -> None:
    (DEST_DIR / filename).write_bytes(img)


def initial_report(cc_list: list[str],
                   actual_req: int,
                   server_label: str) -> None:
    # 打印目标网址
    print(f'{server_label} site: {SERVERS[server_label]}')

    # 打印具体的目标国旗数量
    if len(cc_list) <= 10:
        cc_msg = ', '.join(cc_list)
    else:
        cc_msg = f'from {cc_list[0]} to {cc_list[-1]}'
    plural = 's' if len(cc_list) != 1 else ''
    print(f'Searching for {len(cc_list)} flag{plural}: {cc_msg}')

    # 打印并发连接数
    if actual_req == 1:
        print('1 connection will be used.')
    else:
        print(f'{actual_req} concurrent connections will be used.')


def final_report(cc_list: list[str],
                 counter: Counter[DownloadStatus],
                 start_time: float) -> None:
    elapsed = time.perf_counter() - start_time
    print('-' * 20)

    # 下载结果只有三种情形：成功 OK、找不到 NOT_FOUND、失败 ERROR
    plural = 's' if counter[DownloadStatus.OK] != 1 else ''
    print(f'{counter[DownloadStatus.OK]:3} flag{plural} downloaded.')

    if counter[DownloadStatus.NOT_FOUND]:
        print(f'{counter[DownloadStatus.NOT_FOUND]:3} not found.')

    if counter[DownloadStatus.ERROR]:
        plural = 's' if counter[DownloadStatus.ERROR] != 1 else ''
        print(f'{counter[DownloadStatus.ERROR]:3} error{plural}.')

    print(f'Elapsed time: {elapsed:.2f}s')


def expand_cc_args(every_cc: bool,
                   all_cc: bool,
                   cc_args: list[str],
                   limit: int)-> list[str]:
    codes: set[str] = set()
    A_Z = string.ascii_uppercase
    if every_cc:
        codes.update(a + b for a in A_Z for b in A_Z)
    elif all_cc:
        text = COUNTRY_CODES_FILE.read_text()
        codes.update(text.split())
    else:
        for cc in (c.upper() for c in cc_args):
            if len(cc) == 1 and cc in A_Z:
                codes.update(cc + c for c in A_Z)
            elif len(cc) == 2 and all(c in A_Z for c in cc):
                codes.add(cc)
            else:
                raise ValueError('*** Usage error: each CC argument '
                                 'must be A to Z or AA to ZZ.')
    return sorted(codes)[:limit]


def process_args(default_concur_req):
    server_options = ', '.join(sorted(SERVERS))
    parser = argparse.ArgumentParser(
        description='Download flags for country codes. '
                    'Default: top 20 countries by population.')
    parser.add_argument(
        'cc', metavar='CC', nargs='*',
        help='country code or 1st letter (eg. B for BA...BZ)')
    parser.add_argument(
        '-a', '--all', action='store_true',
        help='get all available flags (AD to ZW)')
    parser.add_argument(
        '-e', '--every', action='store_true',
        help='get flags for every possible code (AA...ZZ)')
    parser.add_argument(
        '-l', '--limit', metavar='N', type=int, help='limit to N first codes',
        default=sys.maxsize)
    parser.add_argument(
        '-m', '--max_req', metavar='CONCURRENT', type=int,
        default=default_concur_req,
        help=f'maximum concurrent requests (default={default_concur_req})')
    parser.add_argument(
        '-s', '--server', metavar='LABEL', default=DEFAULT_SERVER,
        help=f'Server to hit; one of {server_options} '
             f'(default={DEFAULT_SERVER})')
    parser.add_argument(
        '-v', '--verbose', action='store_true',
        help='output detailed progress info')
    args = parser.parse_args()
    if args.max_req < 1:
        print('*** Usage error: --max_req CONCURRENT must be >= 1')
        parser.print_usage()
        # "standard" exit status codes:
        # https://stackoverflow.com/questions/1101957/are-there-any-standard-exit-status-codes-in-linux/40484670#40484670
        sys.exit(2)  # command line usage error
    if args.limit < 1:
        print('*** Usage error: --limit N must be >= 1')
        parser.print_usage()
        sys.exit(2)  # command line usage error
    args.server = args.server.upper()
    if args.server not in SERVERS:
        print(f'*** Usage error: --server LABEL '
              f'must be one of {server_options}')
        parser.print_usage()
        sys.exit(2)  # command line usage error
    try:
        cc_list = expand_cc_args(args.every, args.all, args.cc, args.limit)
    except ValueError as exc:
        print(exc.args[0])
        parser.print_usage()
        sys.exit(2)  # command line usage error

    if not cc_list:
        cc_list = sorted(POP20_CC)[:args.limit]
    return args, cc_list


def main(download_many, default_concur_req, max_concur_req):
    args, cc_list = process_args(default_concur_req)
    actual_req = min(args.max_req, max_concur_req, len(cc_list))
    initial_report(cc_list, actual_req, args.server)
    base_url = SERVERS[args.server]
    DEST_DIR.mkdir(exist_ok=True)
    t0 = time.perf_counter()
    # download_many 这里必须满足签名要求
    counter = download_many(cc_list, base_url, args.verbose, actual_req)
    final_report(cc_list, counter, t0)
```

### 串行请求

```python
from collections import Counter
from http import HTTPStatus

import httpx
import tqdm  # type: ignore  # <1>

from flags2_common import main, save_flag, DownloadStatus  # <2>

DEFAULT_CONCUR_REQ = 1
MAX_CONCUR_REQ = 1


def get_flag(base_url: str, cc: str) -> bytes:
    url = f'{base_url}/{cc}/{cc}.gif'.lower()
    resp = httpx.get(url, timeout=3.1, follow_redirects=True)
    resp.raise_for_status()  # <3>
    return resp.content


def download_one(cc: str, base_url: str, verbose: bool = False) -> DownloadStatus:
    try:
        image = get_flag(base_url, cc)
    except httpx.HTTPStatusError as exc:  # <4>
        res = exc.response
        if res.status_code == HTTPStatus.NOT_FOUND:
            status = DownloadStatus.NOT_FOUND  # <5>
            msg = f'not found: {res.url}'
        else:
            raise  # <6>
    else:
        save_flag(image, f'{cc}.gif')
        status = DownloadStatus.OK
        msg = 'OK'

    if verbose:  # <7>
        print(cc, msg)

    return status


def download_many(cc_list: list[str],
                  base_url: str,
                  verbose: bool,
                  _unused_concur_req: int)-> Counter[DownloadStatus]:
    counter: Counter[DownloadStatus] = Counter()  # <1>
    cc_iter = sorted(cc_list)  # <2>
    if not verbose:
        cc_iter = tqdm.tqdm(cc_iter)  # <3>
    for cc in cc_iter:
        try:
            status = download_one(cc, base_url, verbose)  # <4>
        except httpx.HTTPStatusError as exc:  # <5>
            error_msg = 'HTTP error {resp.status_code} - {resp.reason_phrase}'
            error_msg = error_msg.format(resp=exc.response)
        except httpx.RequestError as exc:  # <6>
            error_msg = f'{exc} {type(exc)}'.strip()
        except KeyboardInterrupt:  # <7>
            break
        else:  # <8>
            error_msg = ''

        if error_msg:
            status = DownloadStatus.ERROR  # <9>
        counter[status] += 1  # <10>
        if verbose and error_msg:  # <11>
            print(f'{cc} error: {error_msg}')

    return counter  # <12>


if __name__ == '__main__':
    main(download_many, DEFAULT_CONCUR_REQ, MAX_CONCUR_REQ)
```

### 线程池

```python
def download_many(cc_list: list[str],
                  base_url: str,
                  verbose: bool,
                  concur_req: int)-> Counter[DownloadStatus]:
    counter: Counter[DownloadStatus] = Counter()
    with ThreadPoolExecutor(max_workers=concur_req) as executor:  # <4>
        # 构建一个字典，把各个 future 对象映射到 future 对象运行结束后可能有用的其他数据上。这里，在
        # to_do_map 中，我们把各个 future 对象映射到对应的国家代码上。
        # 这样，尽管 future 对象生成的结果顺序已经乱了，依然便于使用结果做后续处理
        to_do_map = {}  # <5>
        for cc in sorted(cc_list):  # <6>
            future = executor.submit(download_one, cc,
                                     base_url, verbose)  # <7>
            to_do_map[future] = cc  # <8>

        done_iter = as_completed(to_do_map)  # <9>
        if not verbose:
            # 因为 done_iter 没有长度，所以我们必须通过 total= 参数告诉 tqdm 函数预期的项数
            done_iter = tqdm.tqdm(done_iter, total=len(cc_list))  # <10>
        for future in done_iter:  # <11>
            try:
                status = future.result()  # <12>
            except httpx.HTTPStatusError as exc:  # <13>
                error_msg = 'HTTP error {resp.status_code} - {resp.reason_phrase}'
                error_msg = error_msg.format(resp=exc.response)
            except httpx.RequestError as exc:
                error_msg = f'{exc} {type(exc)}'.strip()
            except KeyboardInterrupt:
                break
            else:
                error_msg = ''

            if error_msg:
                status = DownloadStatus.ERROR
            counter[status] += 1
            if verbose and error_msg:
                cc = to_do_map[future]  # <14>
                print(f'{cc} error: {error_msg}')

    return counter
```

- 由 ThreadPoolExecutor(max_workers=concur_req) 控制并发度

### asyncio

```python
"""example:
> py flags2_asyncio.py -s REMOTE -al 100 -m 100

"""
import asyncio
from collections import Counter
from http import HTTPStatus
from pathlib import Path

import httpx
import tqdm  # type: ignore

from flags2_common import main, DownloadStatus, save_flag

# low concurrency default to avoid errors from remote site,
# such as 503 - Service Temporarily Unavailable
DEFAULT_CONCUR_REQ = 5
MAX_CONCUR_REQ = 1000


async def get_flag(client: httpx.AsyncClient,  # <1>
                   base_url: str,
                   cc: str) -> bytes:
    url = f'{base_url}/{cc}/{cc}.gif'.lower()
    resp = await client.get(url, timeout=3.1, follow_redirects=True)  # <2>
    resp.raise_for_status()
    return resp.content


async def download_one(client: httpx.AsyncClient,
                       cc: str,
                       base_url: str,
                       semaphore: asyncio.Semaphore,
                       verbose: bool) -> DownloadStatus:
    try:
        async with semaphore:  # <3>
            image = await get_flag(client, base_url, cc)
    except httpx.HTTPStatusError as exc:  # <4>
        res = exc.response
        if res.status_code == HTTPStatus.NOT_FOUND:
            status = DownloadStatus.NOT_FOUND
            msg = f'not found: {res.url}'
        else:
            raise
    else:
        await asyncio.to_thread(save_flag, image, f'{cc}.gif')  # <5>
        status = DownloadStatus.OK
        msg = 'OK'
    if verbose and msg:
        print(cc, msg)
    return status


async def supervisor(cc_list: list[str],
                     base_url: str,
                     verbose: bool,
                     concur_req: int) -> Counter[DownloadStatus]:  # <1>
    counter: Counter[DownloadStatus] = Counter()
    semaphore = asyncio.Semaphore(concur_req)  # <2>
    async with httpx.AsyncClient() as client:
        to_do = [download_one(client, cc, base_url, semaphore, verbose) for cc in sorted(cc_list)]  # <3>
        to_do_iter = asyncio.as_completed(to_do)  # <4>
        if not verbose:
            to_do_iter = tqdm.tqdm(to_do_iter, total=len(cc_list))  # <5>

        error: httpx.HTTPError | None = None  # <6>
        for coro in to_do_iter:  # <7>
            try:
                status = await coro  # <8>
            except httpx.HTTPStatusError as exc:
                error_msg = 'HTTP error {resp.status_code} - {resp.reason_phrase}'
                error_msg = error_msg.format(resp=exc.response)
                error = exc  # <9>
            except httpx.RequestError as exc:
                error_msg = f'{exc} {type(exc)}'.strip()
                error = exc  # <10>
            except KeyboardInterrupt:
                break

            if error:
                status = DownloadStatus.ERROR  # <11>
                if verbose:
                    url = str(error.request.url)  # <12>
                    cc = Path(url).stem.upper()  # <13>
                    print(f'{cc} error: {error_msg}')
            counter[status] += 1

    return counter


def download_many(cc_list: list[str],
                  base_url: str,
                  verbose: bool,
                  concur_req: int) -> Counter[DownloadStatus]:
    coro = supervisor(cc_list, base_url, verbose, concur_req)
    counts = asyncio.run(coro)  # <14>

    return counts


if __name__ == '__main__':
    main(download_many, DEFAULT_CONCUR_REQ, MAX_CONCUR_REQ)
```

关键点：

- download_one 使用 semaphore 信号量控制并发度

### 建立本地测试 server

```python
import contextlib
import os
import socket
import time
from functools import partial
from http import server, HTTPStatus
from http.server import ThreadingHTTPServer, SimpleHTTPRequestHandler
from random import random, uniform

MIN_DELAY = 0.5  # minimum delay for do_GET (seconds)
MAX_DELAY = 5.0  # maximum delay for do_GET (seconds)


class SlowHTTPRequestHandler(SimpleHTTPRequestHandler):
    """SlowHTTPRequestHandler adds delays and errors to test HTTP clients.

    The optional error_rate argument determines how often GET requests
    receive a 418 status code, "I'm a teapot".
    If error_rate is .15, there's a 15% probability of each GET request
    getting that error.
    When the server believes it is a teapot, it refuses requests to serve files.

    See: https://tools.ietf.org/html/rfc2324#section-2.3.2
    """

    def __init__(self, *args, error_rate=0.0, **kwargs):
        self.error_rate = error_rate
        super().__init__(*args, **kwargs)

    def do_GET(self):
        """Serve a GET request."""
        delay = uniform(MIN_DELAY, MAX_DELAY)
        cc = self.path[-6:-4].upper()
        print(f'{cc} delay: {delay:0.2}s')
        time.sleep(delay)
        if random() < self.error_rate:
            # HTTPStatus.IM_A_TEAPOT requires Python >= 3.9
            try:
                self.send_error(HTTPStatus.IM_A_TEAPOT, "I'm a Teapot")
            except BrokenPipeError as exc:
                print(f'{cc} *** BrokenPipeError: client closed')
        else:
            f = self.send_head()
            if f:
                try:
                    self.copyfile(f, self.wfile)
                except BrokenPipeError as exc:
                    print(f'{cc} *** BrokenPipeError: client closed')
                finally:
                    f.close()


# The code in the `if` block below, including comments, was copied
# and adapted from the `http.server` module of Python 3.9
# https://github.com/python/cpython/blob/master/Lib/http/server.py

if __name__ == '__main__':
    import argparse

    parser = argparse.ArgumentParser()
    parser.add_argument('--bind', '-b', metavar='ADDRESS',
                        help='Specify alternate bind address '
                             '[default: all interfaces]')
    parser.add_argument('--directory', '-d', default=os.getcwd(),
                        help='Specify alternative directory '
                             '[default:current directory]')
    parser.add_argument('--error-rate', '-e', metavar='PROBABILITY',
                        default=0.0, type=float,
                        help='Error rate; e.g. use .25 for 25%% probability '
                             '[default:0.0]')
    parser.add_argument('port', action='store',
                        default=8001, type=int,
                        nargs='?',
                        help='Specify alternate port [default: 8001]')
    args = parser.parse_args()
    handler_class = partial(SlowHTTPRequestHandler,
                            directory=args.directory,
                            error_rate=args.error_rate)


    # ensure dual-stack is not disabled; ref #38907
    class DualStackServer(ThreadingHTTPServer):
        def server_bind(self):
            # suppress exception when protocol is IPv4
            with contextlib.suppress(Exception):
                self.socket.setsockopt(socket.IPPROTO_IPV6, socket.IPV6_V6ONLY, 0)
            return super().server_bind()


    # test is a top-level function in http.server omitted from __all__
    server.test(  # type: ignore
        HandlerClass=handler_class,
        ServerClass=DualStackServer,
        port=args.port,
        bind=args.bind,
    )
```

- do_GET 中模拟了延迟和概率错误。其实实现不用这么麻烦，直接用常见的 Web 框架就行。

### 小结

- concurrent.futures.Future 或 asyncio.Future 类的实例，说明二者的共同点。
- 使用 Executor.submit 方法提交 future 对象，使用 concurrent.futures.as_completed 函数迭代运行结束的 future 对象。
- 借助 concurrent.futures.ProcessPoolExecutor 类使用多个进程，以此绕开 GIL，使用多个 CPU 核进行素数检查程序。
- 分析 concurrent.futures.ThreadPoolExecutor 类的运作方式。举例，创建几个任务，但是空闲几秒，什么也不做，只是显示带有时间戳的状态。
- 把 future 对象存储在字典中，提交时把 future 对象与相关的信息联系起来；这样，as_completed 迭代器产出 future
  对象后，就可以使用那些信息进行区分。

## ch21 异步编程

### charindex.py

```python
#!/usr/bin/env python

"""
Class ``InvertedIndex`` builds an inverted index mapping each word to
the set of Unicode characters which contain that word in their names.

Optional arguments to the constructor are ``first`` and ``last+1``
character codes to index, to make testing easier. In the examples
below, only the ASCII range was indexed.

The `entries` attribute is a `defaultdict` with uppercased single
words as keys::

    >>> idx = InvertedIndex(32, 128)
    >>> idx.entries['DOLLAR']
    {'$'}
    >>> sorted(idx.entries['SIGN'])
    ['#', '$', '%', '+', '<', '=', '>']
    >>> idx.entries['A'] & idx.entries['SMALL']
    {'a'}
    >>> idx.entries['BRILLIG']
    set()

The `.search()` method takes a string, uppercases it, splits it into
words, and returns the intersection of the entries for each word::

    >>> idx.search('capital a')
    {'A'}

"""

import sys
import unicodedata
from collections import defaultdict
from collections.abc import Iterator

STOP_CODE: int = sys.maxunicode + 1

Char = str
Index = defaultdict[str, set[Char]]


def tokenize(text: str) -> Iterator[str]:
    """return iterator of uppercased words"""
    for word in text.upper().replace('-', ' ').split():
        yield word


class InvertedIndex:
    entries: Index

    def __init__(self, start: int = 32, stop: int = STOP_CODE):
        entries: Index = defaultdict(set)
        for char in (chr(i) for i in range(start, stop)):
            name = unicodedata.name(char, '')
            if name:
                for word in tokenize(name):
                    entries[word].add(char)
        self.entries = entries

    def search(self, query: str) -> set[Char]:
        if words := list(tokenize(query)):
            found = self.entries[words[0]]
            return found.intersection(*(self.entries[w] for w in words[1:]))
        else:
            return set()


def format_results(chars: set[Char]) -> Iterator[str]:
    for char in sorted(chars):
        name = unicodedata.name(char)
        code = ord(char)
        yield f'U+{code:04X}\t{char}\t{name}'


def main(words: list[str]) -> None:
    if not words:
        print('Please give one or more words to search.')
        sys.exit(2)  # command line usage error
    index = InvertedIndex()
    chars = index.search(' '.join(words))
    for line in format_results(chars):
        print(line)
    print('─' * 66, f'{len(chars)} found')


if __name__ == '__main__':
    main(sys.argv[1:])
```

- InvertedIndex(32, 128) 可以自定义索引范围
- .entries['A'] 或者 idx.search('capital a') 可以返回特定的字符集合。

### TCP server 实现

```python
import asyncio
import functools
import sys
from asyncio.trsock import TransportSocket
from typing import cast

from charindex import InvertedIndex, format_results  # <1>

CRLF = b'\r\n'
PROMPT = b'?> '


async def finder(index: InvertedIndex,  # <2>
                 reader: asyncio.StreamReader,
                 writer: asyncio.StreamWriter) -> None:
    # 获取套接字连接的远程客户端地址
    client = writer.get_extra_info('peername')  # <3>
    while True:  # <4>
        writer.write(PROMPT)  # can't await!  # <5>
        await writer.drain()  # must await!  # <6>
        data = await reader.readline()  # <7>
        if not data:  # <8>
            break
        try:
            query = data.decode().strip()  # <9>
        except UnicodeDecodeError:  # <10>
            # 不识别的输入字符就 fallback 到空字符
            query = '\x00'
        print(f' From {client}: {query!r}')  # <11>
        if query:
            if ord(query[:1]) < 32:  # <12>
                break
            results = await search(query, index, writer)  # <13>
            print(f'   To {client}: {results} results.')  # <14>

    writer.close()  # <15>
    await writer.wait_closed()  # <16>
    print(f'Close {client}.')  # <17>


async def search(query: str,  # <1>
                 index: InvertedIndex,
                 writer: asyncio.StreamWriter) -> int:
    chars = index.search(query)  # <2>
    lines = (line.encode() + CRLF for line  # <3>
             in format_results(chars))
    writer.writelines(lines)  # <4>
    await writer.drain()  # <5>
    status_line = f'{"─" * 66} {len(chars)} found'  # <6>
    writer.write(status_line.encode() + CRLF)
    await writer.drain()
    return len(chars)


async def supervisor(index: InvertedIndex, host: str, port: int) -> None:
    # functools.partial(finder, index) 被改造成为一个 func，接受两个参数,reader and writer，使得满足这个函数入参的签名要求
    server = await asyncio.start_server(  # <1>
        functools.partial(finder, index),  # <2>
        host, port)  # <3>

    socket_list = cast(tuple[TransportSocket, ...], server.sockets)  # <4>
    addr = socket_list[0].getsockname()
    print(f'Serving on {addr}. Hit CTRL-C to stop.')  # <5>
    await server.serve_forever()  # <6>


def main(host: str = '127.0.0.1', port_arg: str = '2323'):
    port = int(port_arg)
    print('Building index.')
    index = InvertedIndex()  # <7>
    try:
        asyncio.run(supervisor(index, host, port))  # <8>
    except KeyboardInterrupt:  # <9>
        print('\nServer shut down.')


if __name__ == '__main__':
    main(*sys.argv[1:])
```

### web server 实现

```python
from pathlib import Path
from unicodedata import name

from fastapi import FastAPI
from fastapi.responses import HTMLResponse
from pydantic import BaseModel

from charindex import InvertedIndex

STATIC_PATH = Path(__file__).parent.absolute() / 'static'  # <1>

app = FastAPI(  # <2>
    title='Mojifinder Web',
    description='Search for Unicode characters by name.',
)


# pydantic 在运行时通过类型提示验证数据
class CharName(BaseModel):  # <3>
    char: str
    name: str


def init(app):  # <4>
    app.state.index = InvertedIndex()
    app.state.form = (STATIC_PATH / 'form.html').read_text()


init(app)  # <5>


@app.get('/search', response_model=list[CharName])  # <6>
async def search(q: str):  # <7>
    chars = sorted(app.state.index.search(q))
    return ({'char': c, 'name': name(c)} for c in chars)  # <8>


@app.get('/', response_class=HTMLResponse, include_in_schema=False)
def form():  # <9>
    return app.state.form

# no main function  # <10>
```

- 使用 fastapi 作为 web 服务器，pydantic 库来实现数据模型的验证。
- /static/form.html 是一个表单页面，填表执行 AJAX 查询，显示结果。

### 小结

- 不要阻塞事件循环，速度慢的任务应委托其他处理单元——从简单的线程到分布式任务队列。
- 异步的感染性：一旦编写第一个 async def，程序中将有越来越多的 async def、await、async with 和 async for，
  再想使用非异步库，难度可想而知。
- blogdom.py 为 DNS 探测示例，介绍了可异步调用对象概念。

第五部分：元编程

## ch22 动态属性和特性

### @property

第一种定义方式

```python
class LineItem:

    def __init__(self, description, weight, price):
        self.description = description
        self.weight = weight  # <1>
        self.price = price

    def subtotal(self):
        return self.weight * self.price

    @property  # <2>
    def weight(self):  # <3>
        return self.__weight  # <4>

    @weight.setter  # <5>
    def weight(self, value):
        if value > 0:
            self.__weight = value  # <6>
        else:
            raise ValueError('value must be > 0')  # <7>
```

第二种定义方式

```python
class LineItem:

    def __init__(self, description, weight, price):
        self.description = description
        self.weight = weight
        self.price = price

    def subtotal(self):
        return self.weight * self.price

    def get_weight(self):  # <1>
        return self.__weight

    def set_weight(self, value):  # <2>
        if value > 0:
            self.__weight = value
        else:
            raise ValueError('value must be > 0')

    weight = property(get_weight, set_weight)  # <3>
```

第三种定义方式：特性工厂函数。和第二种方式很像，只是封装成了函数，方便重用。

```python
def quantity(storage_name):  # <1>

    # instance means LineItem instance lineItem
    def qty_getter(instance):  # <2>
        return instance.__dict__[storage_name]  # <3>

    def qty_setter(instance, value):  # <4>
        if value > 0:
            instance.__dict__[storage_name] = value  # <5>
        else:
            raise ValueError('value must be > 0')

    return property(qty_getter, qty_setter)  # <6>


class LineItem:
    weight = quantity('weight')  # <1>
    price = quantity('price')  # <2>

    def __init__(self, description, weight, price):
        self.description = description
        self.weight = weight  # <3>
        self.price = price

    def subtotal(self):
        return self.weight * self.price  # <4>
```

### 使用 `__getattr__` 在读取属性时转换数据结构

```python
class FrozenJSON:
    """A read-only façade for navigating a JSON-like object
       using attribute notation
    """

    def __init__(self, mapping):
        self.__data = dict(mapping)  # <1>

    def __getattr__(self, name):  # <2>
        try:
            return getattr(self.__data, name)  # <3>
        except AttributeError:
            # 这一行中的 self.__data[name] 表达式可能抛出 KeyError 异常。理想情况下，应该处
            # 理这个异常，并抛出 AttributeError 异常，因为这才是 __getattr__ 方法应该抛出的异常种类
            return FrozenJSON.build(self.__data[name])  # <4>

    def __dir__(self):  # <5>
        return self.__data.keys()

    @classmethod
    def build(cls, obj):  # <6>
        if isinstance(obj, abc.Mapping):  # <7>
            return cls(obj)
        elif isinstance(obj, abc.MutableSequence):  # <8>
            return [cls.build(item) for item in obj]
        else:  # <9>
            return obj
```

### 使用 `__new__` 把类转换成对象工厂函数

```python
from collections import abc
import keyword


class FrozenJSON:
    """A read-only façade for navigating a JSON-like object
       using attribute notation
    """

    def __new__(cls, arg):  # <1>
        if isinstance(arg, abc.Mapping):
            return super().__new__(cls)  # <2>
        elif isinstance(arg, abc.MutableSequence):  # <3>
            return [cls(item) for item in arg]
        else:
            return arg

    def __init__(self, mapping):
        self.__data = {}
        for key, value in mapping.items():
            if keyword.iskeyword(key):
                key += '_'
            self.__data[key] = value

    def __getattr__(self, name):
        try:
            return getattr(self.__data, name)
        except AttributeError:
            return FrozenJSON(self.__data[name])  # <4>

    def __dir__(self):
        return self.__data.keys()
```

### cached property

```python
import json
import inspect

from functools import cached_property, cache

JSON_PATH = 'data/osconfeed.json'


class Record:
    __index = None

    def __init__(self, **kwargs):
        self.__dict__.update(kwargs)

    def __repr__(self):
        return f'<{self.__class__.__name__} serial={self.serial!r}>'

    @staticmethod
    def fetch(key):
        if Record.__index is None:
            Record.__index = load()
        return Record.__index[key]


class Event(Record):

    def __repr__(self):
        try:
            return f'<{self.__class__.__name__} {self.name!r}>'
        except AttributeError:
            return super().__repr__()

    @cached_property
    def venue(self):
        key = f'venue.{self.venue_serial}'
        return self.__class__.fetch(key)

    @property  # <1>
    @cache  # <2>
    def speakers(self):
        spkr_serials = self.__dict__['speakers']
        fetch = self.__class__.fetch
        return [fetch(f'speaker.{key}')
                for key in spkr_serials]


def load(path=JSON_PATH):
    records = {}
    with open(path) as fp:
        raw_data = json.load(fp)
    for collection, raw_records in raw_data['Schedule'].items():
        record_type = collection[:-1]
        cls_name = record_type.capitalize()
        cls = globals().get(cls_name, Record)
        if inspect.isclass(cls) and issubclass(cls, Record):
            factory = cls
        else:
            factory = Record
        for raw_record in raw_records:
            key = f'{record_type}.{raw_record["serial"]}'
            records[key] = factory(**raw_record)
    return records
```

### 小结

- FrozenJSON 类，可以把嵌套的字典和列表转换成嵌套的 FrozenJSON 实例和实例列表。
- FrozenJSON 类的代码展示了如何使用特殊方法 `__getattr__` 在读取属性时即时转换数据结构。
- FrozenJSON 类的最后一版展示了如何使用 `__new__` 构造方法把一个类转换成灵活的对象工厂函数。
- 第 1 版的 Record 类：使用传给 `__init__` 方法的关键字参数，调用 `self.__dict__.update(**kwargs)` 构建任意属性。
- 第 2 版增加了 Event 类，通过特性自动获取链接的记录。有些时候，计算特性的值需要缓存。
- @functools.cached_property 并不总是适用，采用替代方案：结合 @property 和 @functools.cache，把前者放在上方。
- 定义的 LineItem 类中有一个特性，以确保 weight 属性的值不能是对业务没有意义的负数或零。
- 创建了一个特性工厂函数，在不定义多个读值和设置值方法的前提下，对 weight 属性和 price 属性做相同的验证。
- 使用特性也可以处理删除（del）属性的操作，
- 概览 Python 核心语言为支持属性元编程而提供的重要的特殊属性、内置函数和特殊方法。

## ch23 属性描述符

### 去掉 storage_name 参数

```python
class Quantity:

    def __set_name__(self, owner, name):  # <1>
        self.storage_name = name  # <2>

    def __set__(self, instance, value):  # <3>
        if value > 0:
            instance.__dict__[self.storage_name] = value
        else:
            msg = f'{self.storage_name} must be > 0'
            raise ValueError(msg)

    # no __get__ needed  # <4>


class LineItem:
    weight = Quantity()  # <5>
    price = Quantity()

    def __init__(self, description, weight, price):
        self.description = description
        self.weight = weight
        self.price = price

    def subtotal(self):
        return self.weight * self.price
```

这里 Quantity 已经初具雏形，或许可以改成 PositiveQuantity 名称，表示正的数量。

### 子类化抽象描述符类以共享代码

也可以直接封装成工具类。

```python
import abc


class Validated(abc.ABC):

    def __set_name__(self, owner, name):
        self.storage_name = name

    def __set__(self, instance, value):
        value = self.validate(self.storage_name, value)  # <1>
        instance.__dict__[self.storage_name] = value  # <2>

    @abc.abstractmethod
    def validate(self, name, value):  # <3>
        """return validated value or raise ValueError"""


class Quantity(Validated):
    """a number greater than zero"""

    def validate(self, name, value):  # <1>
        if value <= 0:
            raise ValueError(f'{name} must be > 0')
        return value


class NonBlank(Validated):
    """a string with at least one non-space character"""

    def validate(self, name, value):
        value = value.strip()
        if not value:  # <2>
            raise ValueError(f'{name} cannot be blank')
        return value  # <3>
```

```python
import model_v5 as model  # <1>


class LineItem:
    description = model.NonBlank()  # <2>
    weight = model.Quantity()
    price = model.Quantity()

    def __init__(self, description, weight, price):
        self.description = description
        self.weight = weight
        self.price = price

    def subtotal(self):
        return self.weight * self.price
```

### 小结

- 描述符类的实例能用作托管类的属性。本章引入了几个特殊的术语，例如托管实例和储存属性。
- 去掉声明 Quantity 描述符所需的 storage_name 参数，因为那个参数多余且容易出错。采用的新
  方案是在 Quantity 中实现特殊方法 `__set_name__`，把托管特性的名称保存为 self.storage_name。
- 通过子类化抽象描述符类以共享代码，同时构建具有部分共通功能的专用描述符。
- 分析有或没有 `__set__` 方法时，描述符的行为有何不同，了解了覆盖型描述符和非覆盖型描述符之间的重要差异。
- 通过详细的测试，揭示描述符何时接管，以及何时被遮盖、被绕过或被覆盖。
- 非覆盖型描述符的一种具体类型：方法。
- 了解如何为描述符添加文档。
- 在 Python 3.6 为描述符协议添加特殊方法 `__set_name__` 之后，代码可以得到简化。

## ch24 类元编程

### 自定义元类 MetaBunch
```python
class MetaBunch(type):  # <1>
    def __new__(meta_cls, cls_name, bases, cls_dict):  # <2>

        defaults = {}  # <3>

        def __init__(self, **kwargs):  # <4>
            for name, default in defaults.items():  # <5>
                setattr(self, name, kwargs.pop(name, default))
            if kwargs:  # <6>
                extra = ', '.join(kwargs)
                raise AttributeError(f'No slots left for: {extra!r}')

        def __repr__(self):  # <7>
            rep = ', '.join(f'{name}={value!r}'
                            for name, default in defaults.items()
                            if (value := getattr(self, name)) != default)
            return f'{cls_name}({rep})'

        new_dict = dict(__slots__=[], __init__=__init__, __repr__=__repr__)  # <8>

        for name, value in cls_dict.items():  # <9>
            if name.startswith('__') and name.endswith('__'):  # <10>
                if name in new_dict:
                    raise AttributeError(f"Can't set {name!r} in {cls_name!r}")
                new_dict[name] = value
            else:  # <11>
                new_dict['__slots__'].append(name)
                defaults[name] = value
                
        # 在 __new__ 之前配置 __slots__
        return super().__new__(meta_cls, cls_name, bases, new_dict)  # <12>


class Bunch(metaclass=MetaBunch):  # <13>
    pass
```
一个测试：证明了不能自定义init方法。
```python
def test_dunder_forbidden():
    with pytest.raises(AttributeError) as exc:
        class Cat(Bunch):
            name = ''
            weight = 0

            def __init__(self):
                pass
    assert "Can't set '__init__' in 'Cat'" in str(exc.value)
```

### bulkfood 重构
修饰器。 
```python
import abc


class AutoStorage:
    __counter = 0

    def __init__(self):
        cls = self.__class__
        prefix = cls.__name__
        index = cls.__counter
        self.storage_name = '_{}#{}'.format(prefix, index)
        cls.__counter += 1

    def __get__(self, instance, owner):
        if instance is None:
            return self
        else:
            return getattr(instance, self.storage_name)

    def __set__(self, instance, value):
        setattr(instance, self.storage_name, value)


class Validated(abc.ABC, AutoStorage):

    def __set__(self, instance, value):
        value = self.validate(instance, value)
        super().__set__(instance, value)

    @abc.abstractmethod
    def validate(self, instance, value):
        """return validated value or raise ValueError"""


class Quantity(Validated):
    """a number greater than zero"""

    def validate(self, instance, value):
        if value <= 0:
            raise ValueError('value must be > 0')
        return value


class NonBlank(Validated):
    """a string with at least one non-space character"""

    def validate(self, instance, value):
        value = value.strip()
        if len(value) == 0:
            raise ValueError('value cannot be empty or blank')
        return value

def entity(cls):  # <1>
    for key, attr in cls.__dict__.items():  # <2>
        if isinstance(attr, Validated):  # <3>
            type_name = type(attr).__name__
            attr.storage_name = '_{}#{}'.format(type_name, key)  # <4>
    return cls  # <5>
```
```python
import model_v6 as model

@model.entity  # <1>
class LineItem:
    description = model.NonBlank()
    weight = model.Quantity()
    price = model.Quantity()
```
---

元类
```python
class EntityMeta(type):
    """Metaclass for business entities with validated fields"""

    def __init__(cls, name, bases, attr_dict):
        super().__init__(name, bases, attr_dict)  # <1>
        for key, attr in attr_dict.items():  # <2>
            if isinstance(attr, Validated):
                type_name = type(attr).__name__
                attr.storage_name = '_{}#{}'.format(type_name, key)

class Entity(metaclass=EntityMeta):  # <3>
    """Business entity with validated fields"""
```
```python
import model_v7 as model

class LineItem(model.Entity):  # <1>
    description = model.NonBlank()
    weight = model.Quantity()
    price = model.Quantity()
```

---

元类另一种实现。

```python
class AutoStorage:
    # 这里的计数器是storage_name名称默认实现所用到的变量，子类一般用唯一的key、ID字符串来替代
    __counter = 0

    def __init__(self):
        cls = self.__class__
        prefix = cls.__name__
        index = cls.__counter
        self.storage_name = '_{}#{}'.format(prefix, index)
        cls.__counter += 1

    def __get__(self, instance, owner):
        if instance is None:
            return self
        else:
            return getattr(instance, self.storage_name)

    def __set__(self, instance, value):
        setattr(instance, self.storage_name, value)

#  ...       

class EntityMeta(type):
    """Metaclass for business entities with validated fields"""

    @classmethod
    def __prepare__(cls, name, bases):
        return collections.OrderedDict()  # <1>

    def __init__(cls, name, bases, attr_dict):
        super().__init__(name, bases, attr_dict)
        cls._field_names = []  # <2>
        for key, attr in attr_dict.items():  # <3>
            if isinstance(attr, Validated):
                type_name = type(attr).__name__
                attr.storage_name = '_{}#{}'.format(type_name, key)
                cls._field_names.append(key)  # <4>


class Entity(metaclass=EntityMeta):
    """Business entity with validated fields"""

    @classmethod
    def field_names(cls):  # <5>
        for name in cls._field_names:
            yield name
```
文档测试
```bash
>>> raisins = LineItem('Golden raisins', 10, 6.95)
>>> dir(raisins)[:3]
['_NonBlank#description', '_Quantity#price', '_Quantity#weight']
>>> LineItem.description.storage_name
'_NonBlank#description'
>>> raisins.description
'Golden raisins'
>>> getattr(raisins, '_NonBlank#description')
'Golden raisins'
```

### AutoConst
```python
class WilyDict(dict):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.__next_value = 0

    def __missing__(self, key):
        if key.startswith('__') and key.endswith('__'):
            raise KeyError(key)
        self[key] = value = self.__next_value
        self.__next_value += 1
        return value

class AutoConstMeta(type):
    def __prepare__(name, bases, **kwargs):
        return WilyDict()

class AutoConst(metaclass=AutoConstMeta):
    pass
```
客户端用例
```python
from autoconst import AutoConst


class Flavor(AutoConst):
    banana
    coconut
    vanilla


print('Flavor.vanilla ==', Flavor.vanilla)
# Flavor.vanilla == 2
```
### 子类属性类型提示
第一种实现：初始化子类，使用Field实例
```
from collections.abc import Callable  # <1>
from typing import Any, NoReturn, get_type_hints


class Field:
    def __init__(self, name: str, constructor: Callable) -> None:  # <2>
        if not callable(constructor) or constructor is type(None):  # <3>
            raise TypeError(f'{name!r} type hint must be callable')
        self.name = name
        self.constructor = constructor

    def __set__(self, instance: Any, value: Any) -> None:
        if value is ...:  # <4>
            value = self.constructor()
        else:
            try:
                value = self.constructor(value)  # <5>
            except (TypeError, ValueError) as e:  # <6>
                type_name = self.constructor.__name__
                msg = f'{value!r} is not compatible with {self.name}:{type_name}'
                raise TypeError(msg) from e
        instance.__dict__[self.name] = value  # <7>


class Checked:
    @classmethod
    def _fields(cls) -> dict[str, type]:  # <1>
        return get_type_hints(cls)

    def __init_subclass__(subclass) -> None:  # <2>
        super().__init_subclass__()  # <3>
        for name, constructor in subclass._fields().items():  # <4>
            setattr(subclass, name, Field(name, constructor))  # <5>

    def __init__(self, **kwargs: Any) -> None:
        for name in self._fields():  # <6>
            value = kwargs.pop(name, ...)  # <7>
            setattr(self, name, value)  # <8>
        if kwargs:  # <9>
            self.__flag_unknown_attrs(*kwargs)  # <10>

    def __setattr__(self, name: str, value: Any) -> None:  # <1>
        if name in self._fields():  # <2>
            cls = self.__class__
            descriptor = getattr(cls, name)
            descriptor.__set__(self, value)  # <3>
        else:  # <4>
            self.__flag_unknown_attrs(name)

    def __flag_unknown_attrs(self, *names: str) -> NoReturn:  # <5>
        plural = 's' if len(names) > 1 else ''
        extra = ', '.join(f'{name!r}' for name in names)
        cls_name = repr(self.__class__.__name__)
        raise AttributeError(f'{cls_name} object has no attribute{plural} {extra}')

    def _asdict(self) -> dict[str, Any]:  # <6>
        return {
            name: getattr(self, name)
            for name, attr in self.__class__.__dict__.items()
            if isinstance(attr, Field)
        }

    def __repr__(self) -> str:  # <7>
        kwargs = ', '.join(
            f'{key}={value!r}' for key, value in self._asdict().items()
        )
        return f'{self.__class__.__name__}({kwargs})'
```
测试demo
```python
from checkedlib import Checked

class Movie(Checked):
    title: str
    year: int
    box_office: float


if __name__ == '__main__':
    movie = Movie(title='The Godfather', year=1972, box_office=137)
```
---
第二种实现：元类
```python
from collections.abc import Callable
from typing import Any, NoReturn, get_type_hints

class Field:
    def __init__(self, name: str, constructor: Callable) -> None:
        if not callable(constructor) or constructor is type(None):
            raise TypeError(f'{name!r} type hint must be callable')
        self.name = name
        self.storage_name = '_' + name  # <1>
        self.constructor = constructor

    def __get__(self, instance, owner=None):
        if instance is None:  # <2>
            return self
        return getattr(instance, self.storage_name)  # <3>

    def __set__(self, instance: Any, value: Any) -> None:
        if value is ...:
            value = self.constructor()
        else:
            try:
                value = self.constructor(value)
            except (TypeError, ValueError) as e:
                type_name = self.constructor.__name__
                msg = f'{value!r} is not compatible with {self.name}:{type_name}'
                raise TypeError(msg) from e
        setattr(instance, self.storage_name, value)  # <4>


class CheckedMeta(type):

    def __new__(meta_cls, cls_name, bases, cls_dict):  # <1>
        if '__slots__' not in cls_dict:  # <2>
            slots = []
            type_hints = cls_dict.get('__annotations__', {})  # <3>
            for name, constructor in type_hints.items():   # <4>
                field = Field(name, constructor)  # <5>
                cls_dict[name] = field  # <6>
                slots.append(field.storage_name)  # <7>

            cls_dict['__slots__'] = slots  # <8>

        return super().__new__(meta_cls, cls_name, bases, cls_dict)  # <9>

class Checked(metaclass=CheckedMeta):
    __slots__ = ()  # skip CheckedMeta.__new__ processing

    @classmethod
    def _fields(cls) -> dict[str, type]:
        return get_type_hints(cls)

    def __init__(self, **kwargs: Any) -> None:
        for name in self._fields():
            value = kwargs.pop(name, ...)
            setattr(self, name, value)
        if kwargs:
            self.__flag_unknown_attrs(*kwargs)

    def __flag_unknown_attrs(self, *names: str) -> NoReturn:
        plural = 's' if len(names) > 1 else ''
        extra = ', '.join(f'{name!r}' for name in names)
        cls_name = repr(self.__class__.__name__)
        raise AttributeError(f'{cls_name} object has no attribute{plural} {extra}')

    def _asdict(self) -> dict[str, Any]:
        return {
            name: getattr(self, name)
            for name, attr in self.__class__.__dict__.items()
            if isinstance(attr, Field)
        }

    def __repr__(self) -> str:
        kwargs = ', '.join(
            f'{key}={value!r}' for key, value in self._asdict().items()
        )
        return f'{self.__class__.__name__}({kwargs})'
```
---

第三种实现：函数作为修饰器
```python
from collections.abc import Callable  # <1>
from typing import Any, NoReturn, get_type_hints

class Field:
    def __init__(self, name: str, constructor: Callable) -> None:  # <2>
        if not callable(constructor) or constructor is type(None):
            raise TypeError(f'{name!r} type hint must be callable')
        self.name = name
        self.constructor = constructor

    def __set__(self, instance: Any, value: Any) -> None:  # <3>
        if value is ...:  # <4>
            value = self.constructor()
        else:
            try:
                value = self.constructor(value)  # <5>
            except (TypeError, ValueError) as e:
                type_name = self.constructor.__name__
                msg = (
                    f'{value!r} is not compatible with {self.name}:{type_name}'
                )
                raise TypeError(msg) from e
        instance.__dict__[self.name] = value  # <6>


def checked(cls: type) -> type:  # <1>
    for name, constructor in _fields(cls).items():    # <2>
        setattr(cls, name, Field(name, constructor))  # <3>

    cls._fields = classmethod(_fields)  # type: ignore  # <4>

    instance_methods = (  # <5>
        __init__,
        __repr__,
        __setattr__,
        _asdict,
        __flag_unknown_attrs,
    )
    for method in instance_methods:  # <6>
        setattr(cls, method.__name__, method)

    return cls  # <7>

def _fields(cls: type) -> dict[str, type]:
    return get_type_hints(cls)

def __init__(self: Any, **kwargs: Any) -> None:
    for name in self._fields():
        value = kwargs.pop(name, ...)
        setattr(self, name, value)
    if kwargs:
        self.__flag_unknown_attrs(*kwargs)

def __setattr__(self: Any, name: str, value: Any) -> None:
    if name in self._fields():
        cls = self.__class__
        descriptor = getattr(cls, name)
        descriptor.__set__(self, value)
    else:
        self.__flag_unknown_attrs(name)

def __flag_unknown_attrs(self: Any, *names: str) -> NoReturn:
    plural = 's' if len(names) > 1 else ''
    extra = ', '.join(f'{name!r}' for name in names)
    cls_name = repr(self.__class__.__name__)
    raise AttributeError(f'{cls_name} has no attribute{plural} {extra}')

def _asdict(self: Any) -> dict[str, Any]:
    return {
        name: getattr(self, name)
        for name, attr in self.__class__.__dict__.items()
        if isinstance(attr, Field)
    }

def __repr__(self: Any) -> str:
    kwargs = ', '.join(
        f'{key}={value!r}' for key, value in self._asdict().items()
    )
    return f'{self.__class__.__name__}({kwargs})'
```
### 求解时间实验1: evaldemo.py
分析定义、导入时、运行时的执行顺序。
```python
print('@ builderlib module start')

class Builder:  # <1>
    print('@ Builder body')

    def __init_subclass__(cls):  # <2>
        print(f'@ Builder.__init_subclass__({cls!r})')

        def inner_0(self):  # <3>
            print(f'@ SuperA.__init_subclass__:inner_0({self!r})')

        cls.method_a = inner_0

    def __init__(self):
        super().__init__()
        print(f'@ Builder.__init__({self!r})')


def deco(cls):  # <4>
    print(f'@ deco({cls!r})')

    def inner_1(self):  # <5>
        print(f'@ deco:inner_1({self!r})')

    cls.method_b = inner_1
    return cls  # <6>

class Descriptor:  # <1>
    print('@ Descriptor body')

    def __init__(self):  # <2>
        print(f'@ Descriptor.__init__({self!r})')

    def __set_name__(self, owner, name):  # <3>
        args = (self, owner, name)
        print(f'@ Descriptor.__set_name__{args!r}')

    def __set__(self, instance, value):  # <4>
        args = (self, instance, value)
        print(f'@ Descriptor.__set__{args!r}')

    def __repr__(self):
        return '<Descriptor instance>'


print('@ builderlib module end')
```
这个文件执行结果
```python
@ builderlib module start
@ Builder body
@ Descriptor body
@ builderlib module end
```

现在，新建一个文件evaldemo.py
```python
from builderlib import Builder, deco, Descriptor

print('# evaldemo module start')

@deco  # <1>
class Klass(Builder):  # <2>
    print('# Klass body')

    attr = Descriptor()  # <3>

    def __init__(self):
        super().__init__()
        print(f'# Klass.__init__({self!r})')

    def __repr__(self):
        return '<Klass instance>'


def main():  # <4>
    obj = Klass()
    obj.method_a()
    obj.method_b()
    obj.attr = 999

if __name__ == '__main__':
    main()

print('# evaldemo module end')
```
打印结果如下：
```
@ builderlib module start
@ Builder body
@ Descriptor body
@ builderlib module end
# evaldemo module start
# Klass body
@ Descriptor.__init__(<Descriptor instance>)
@ Descriptor.__set_name__(<Descriptor instance>, <class '__main__.Klass'>, 'attr')
@ Builder.__init_subclass__(<class '__main__.Klass'>)
@ deco(<class '__main__.Klass'>)
@ Builder.__init__(<Klass instance>)
# Klass.__init__(<Klass instance>)
@ SuperA.__init_subclass__:inner_0(<Klass instance>)
@ deco:inner_1(<Klass instance>)
@ Descriptor.__set__(<Descriptor instance>, <Klass instance>, 999)
# evaldemo module end
```
- 前四行是因为 `from builderlib import Builder, deco, Descriptor` 的导入
- `# evaldemo module start` 和 `# evaldemo module end`是当前文件执行头尾时机的打印。
- `# Klass body` 先打印说明按顺序处理类声明时的语句，第一句是 `print('# Klass body')`
- `attr = Descriptor()` 对应 `class Descriptor` 中
  - `print('@ Descriptor body')`，打印 `@ Descriptor.__init__(<Descriptor instance>)`
  - `__init__`方法中的 `print(f'@ Descriptor.__init__({self!r})')` 打印 `@ Descriptor.__set_name__(<Descriptor instance>, <class '__main__.Klass'>, 'attr')`
- 接着才是处理子类声明 class Klass(Builder) ，对应 class Builder 中的 `__init_subclass__(cls)`
  - `print(f'@ Builder.__init_subclass__({cls!r})')` 语句，打印 `@ Builder.__init_subclass__(<class '__main__.Klass'>)`
- 随后是类修饰器 @deco 
  - `print(f'@ deco({cls!r})')`，打印 `@ deco(<class '__main__.Klass'>)`
- obj = Klass() 是类实例化，对应父类构造函数执行和自身构造函数执行
  - `print(f'@ Builder.__init__({self!r})')`， 打印 `@ Builder.__init__(<Klass instance>)`
  - `print(f'# Klass.__init__({self!r})')`，打印 `# Klass.__init__(<Klass instance>)`
- 最后三行分别对应
  - obj.method_a() -> inner_0 -> `print(f'@ SuperA.__init_subclass__:inner_0({self!r})')`
  - obj.method_b() -> inner_1 -> `print(f'@ deco:inner_1({self!r})')`
  - obj.attr = 999 -> Descriptor.`__set__` -> `print(f'@ Descriptor.__set__{args!r}')`

### 求解时间实验2: evaldemo_meta.py
`metalib.py`
```python
print('% metalib module start')

import collections

class NosyDict(collections.UserDict):
    def __setitem__(self, key, value):
        args = (self, key, value)
        print(f'% NosyDict.__setitem__{args!r}')
        super().__setitem__(key, value)

    def __repr__(self):
        return '<NosyDict instance>'

# 编写元类时，采用以下约定命名特殊方法的参数很有用。
# 1.实例方法中的 self 换成 cls，因为得到的实例是类。
# 2.类方法中的 cls 换成 meta_cls，因为定义的类是元类。注意，即使没有 @classmethod 装饰器，__new__ 的行为也等同类方法

class MetaKlass(type):
    print('% MetaKlass body')

    @classmethod  # <1>
    def __prepare__(meta_cls, cls_name, bases):  # <2>
        args = (meta_cls, cls_name, bases)
        print(f'% MetaKlass.__prepare__{args!r}')
        return NosyDict()  # <3>

    def __new__(meta_cls, cls_name, bases, cls_dict):  # <4>
        args = (meta_cls, cls_name, bases, cls_dict)
        print(f'% MetaKlass.__new__{args!r}')
        def inner_2(self):
            print(f'% MetaKlass.__new__:inner_2({self!r})')

        cls = super().__new__(meta_cls, cls_name, bases, cls_dict.data)  # <5>

        cls.method_c = inner_2  # <6>

        return cls  # <7>

    def __repr__(cls):  # <8>
        cls_name = cls.__name__
        return f"<class {cls_name!r} built by MetaKlass>"

print('% metalib module end')
```
测试用例 evaldemo_meta.py
```python
from builderlib import Builder, deco, Descriptor
from metalib import MetaKlass  # <1>

print('# evaldemo_meta module start')

@deco
class Klass(Builder, metaclass=MetaKlass):  # <2>
    print('# Klass body')

    attr = Descriptor()

    def __init__(self):
        super().__init__()
        print(f'# Klass.__init__({self!r})')

    def __repr__(self):
        return '<Klass instance>'


def main():
    obj = Klass()
    # subclass
    obj.method_a()
    # decorator
    obj.method_b()
    # metaclass
    obj.method_c()  # <3>
    # descriptor
    obj.attr = 999


if __name__ == '__main__':
    main()

print('# evaldemo_meta module end')
```
输出结果
```bash
@ builderlib module start
@ Builder body
@ Descriptor body
@ builderlib module end
% metalib module start
% MetaKlass body
% metalib module end
# evaldemo_meta module start
% MetaKlass.__prepare__(<class 'metalib.MetaKlass'>, 'Klass', (<class 'builderlib.Builder'>,))
% NosyDict.__setitem__(<NosyDict instance>, '__module__', '__main__')
% NosyDict.__setitem__(<NosyDict instance>, '__qualname__', 'Klass')
# Klass body
@ Descriptor.__init__(<Descriptor instance>)
% NosyDict.__setitem__(<NosyDict instance>, 'attr', <Descriptor instance>)
% NosyDict.__setitem__(<NosyDict instance>, '__init__', <function Klass.__init__ at 0x000001F53D3F4280>)
% NosyDict.__setitem__(<NosyDict instance>, '__repr__', <function Klass.__repr__ at 0x000001F53D603640>)
% NosyDict.__setitem__(<NosyDict instance>, '__classcell__', <cell at 0x000001F53D3B7D00: empty>)
% MetaKlass.__new__(<class 'metalib.MetaKlass'>, 'Klass', (<class 'builderlib.Builder'>,), <NosyDict instance>)
@ Descriptor.__set_name__(<Descriptor instance>, <class 'Klass' built by MetaKlass>, 'attr')
@ Builder.__init_subclass__(<class 'Klass' built by MetaKlass>)
@ deco(<class 'Klass' built by MetaKlass>)
@ Builder.__init__(<Klass instance>)
# Klass.__init__(<Klass instance>)
@ SuperA.__init_subclass__:inner_0(<Klass instance>)
@ deco:inner_1(<Klass instance>)
% MetaKlass.__new__:inner_2(<Klass instance>)
@ Descriptor.__set__(<Descriptor instance>, <Klass instance>, 999)
# evaldemo_meta module end
```
- 这里只需要关注 % 前缀开头的行。
- `from metalib import MetaKlass`的导入导致了下面三行的输出
  - `% metalib module start`
  - `% MetaKlass body`
  - `% metalib module end`
- `MetaKlass.__prepare__` 中
  - `print(f'% MetaKlass.__prepare__{args!r}')` -> `% MetaKlass.__prepare__(<class 'metalib.MetaKlass'>, 'Klass', (<class 'builderlib.Builder'>,))`
  - 返回值中 return NosyDict() 会调用 NosyDict() 初始化，`NosyDict.__setitem__` 导致
    - `% NosyDict.__setitem__(<NosyDict instance>, '__module__', '__main__')`
    - `% NosyDict.__setitem__(<NosyDict instance>, '__qualname__', 'Klass')`
- 属性设置钩子
```
% NosyDict.__setitem__(<NosyDict instance>, 'attr', <Descriptor instance>)
% NosyDict.__setitem__(<NosyDict instance>, '__init__', <function Klass.__init__ at 0x000001F53D3F4280>)
% NosyDict.__setitem__(<NosyDict instance>, '__repr__', <function Klass.__repr__ at 0x000001F53D603640>)
% NosyDict.__setitem__(<NosyDict instance>, '__classcell__', <cell at 0x000001F53D3B7D00: empty>)
```
- 子类化导致 `% MetaKlass.__new__(<class 'metalib.MetaKlass'>, 'Klass', (<class 'builderlib.Builder'>,), <NosyDict instance>)`
- obj.method_c() -> MetaKlass.inner_2 -> `% MetaKlass.__new__:inner_2(<Klass instance>)`

### setattr
```python
class Foo:
    @property
    def bar(self):
        return self._bar

    @bar.setter
    def bar(self, value):
        self._bar = value

    def __setattr__(self, name, value):
        print(f'setting {name!r} to {value!r}')
        super().__setattr__(name, value)

o = Foo()
o.bar = 8
```
o.bar = 8 导致 `__setattr__('bar')` → `@bar.setter` → `__setattr__('_bar')`，因此打印两次。

### 设置 `__slots__` 的正确时机
不能在 `__init_subclass__` 中设置 `__slots__`
```python
class Wrong:

    def __init_subclass__(subclass):
        subclass.__slots__ = ('x', 'y')


class Klass0(Wrong):
    pass


o = Klass0()
o.z = 3
print('o.z = 3  # did not raise Attribute error because __slots__ was created too late')
```

可以在 `__new__` 或者 `__prepare__`，此时才是实例化类的时机，因为这些方法之后才是 `__init__` 初始化属性。
```python
class Correct1(type):

    def __new__(meta_cls, cls_name, bases, cls_dict):
        cls_dict['__slots__'] = ('x', 'y')
        return super().__new__(meta_cls, cls_name, bases, cls_dict)


class Klass1(metaclass=Correct1):
    pass

o = Klass1()
try:
    o.z = 3
except AttributeError as e:
    print('Raised as expected:', e)


class Correct2(type):
    def __prepare__(name, bases):
        return dict(__slots__=('x', 'y'))

class Klass2(metaclass=Correct2):
    pass

o = Klass2()
try:
    o.z = 3
except AttributeError as e:
    print('Raised as expected:', e)
```

### Enum枚举类模仿实现
```python
class WilyDict(dict):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.__next_value = 0

    def __missing__(self, key):
        if key.startswith('__') and key.endswith('__'):
            raise KeyError(key)
        self[key] = value = self.__next_value
        self.__next_value += 1
        return value


class MicroEnumMeta(type):
    def __prepare__(name, bases, **kwargs):
        return WilyDict()

    def __getitem__(cls, key):
        for k, v in cls.__dict__.items():
            if v == key:
                return k
        raise KeyError(key)


class MicroEnum(metaclass=MicroEnumMeta):
    pass
```
使用
```python
"""
Testing ``Flavor``::

    >>> Flavor.cocoa, Flavor.coconut, Flavor.vanilla
    (0, 1, 2)
    >>> Flavor[1]
    'coconut'

"""

from microenum import MicroEnum


class Flavor(MicroEnum):
    cocoa
    coconut
    vanilla
```

### 数据类工厂函数
```python
"""
record_factory: create simple classes just for holding data fields

# tag::RECORD_FACTORY_DEMO[]
    >>> Dog = record_factory('Dog', 'name weight owner')  # <1>
    >>> rex = Dog('Rex', 30, 'Bob')
    >>> rex  # <2>
    Dog(name='Rex', weight=30, owner='Bob')
    >>> name, weight, _ = rex  # <3>
    >>> name, weight
    ('Rex', 30)
    >>> "{2}'s dog weighs {1}kg".format(*rex)  # <4>
    "Bob's dog weighs 30kg"
    >>> rex.weight = 32  # <5>
    >>> rex
    Dog(name='Rex', weight=32, owner='Bob')
    >>> Dog.__mro__  # <6>
    (<class 'factories.Dog'>, <class 'object'>)

# end::RECORD_FACTORY_DEMO[]

The factory also accepts a list or tuple of identifiers:

    >>> Dog = record_factory('Dog', ['name', 'weight', 'owner'])
    >>> Dog.__slots__
    ('name', 'weight', 'owner')

"""


from typing import Union, Any
from collections.abc import Iterable, Iterator

FieldNames = Union[str, Iterable[str]]  # <1>

def record_factory(cls_name: str, field_names: FieldNames) -> type[tuple]:  # <2>

    slots = parse_identifiers(field_names)  # <3>

    def __init__(self, *args, **kwargs) -> None:  # <4>
        attrs = dict(zip(self.__slots__, args))
        attrs.update(kwargs)
        for name, value in attrs.items():
            setattr(self, name, value)

    def __iter__(self) -> Iterator[Any]:  # <5>
        for name in self.__slots__:
            yield getattr(self, name)

    def __repr__(self):  # <6>
        values = ', '.join(f'{name}={value!r}'
            for name, value in zip(self.__slots__, self))
        cls_name = self.__class__.__name__
        return f'{cls_name}({values})'

    cls_attrs = dict(  # <7>
        __slots__=slots,
        __init__=__init__,
        __iter__=__iter__,
        __repr__=__repr__,
    )

    return type(cls_name, (object,), cls_attrs)  # <8>


def parse_identifiers(names: FieldNames) -> tuple[str, ...]:
    if isinstance(names, str):
        names = names.replace(',', ' ').split()  # <9>
    if not all(s.isidentifier() for s in names):
        raise ValueError('names must all be valid identifiers')
    return tuple(names)
```

### 持久化 dblib 工具库
```python
# SQLite3 does not support parameterized table and field names,
# for CREATE TABLE and PRAGMA so we must use Python string formatting.
# Applying `check_identifier` to parameters prevents SQL injection.

import sqlite3
from typing import NamedTuple, Optional, Iterator, Any

DEFAULT_DB_PATH = ':memory:'
CONNECTION: Optional[sqlite3.Connection] = None


class NoConnection(Exception):
    """Call connect() to open connection."""


class SchemaMismatch(ValueError):
    """The table schema doesn't match the class."""

    def __init__(self, table_name):
        self.table_name = table_name


class NoSuchRecord(LookupError):
    """The given primary key does not exist."""

    def __init__(self, pk):
        self.pk = pk


class UnexpectedMultipleResults(Exception):
    """Query returned more than 1 row."""


SQLType = str

TypeMap = dict[type, SQLType]

SQL_TYPES: TypeMap = {
    int: 'INTEGER',
    str: 'TEXT',
    float: 'REAL',
    bytes: 'BLOB',
}


class ColumnSchema(NamedTuple):
    name: str
    sql_type: SQLType


FieldMap = dict[str, type]


def check_identifier(name: str) -> None:
    if not name.isidentifier():
        raise ValueError(f'{name!r} is not an identifier')


def connect(db_path: str = DEFAULT_DB_PATH) -> sqlite3.Connection:
    global CONNECTION
    CONNECTION = sqlite3.connect(db_path)
    CONNECTION.row_factory = sqlite3.Row
    return CONNECTION


def get_connection() -> sqlite3.Connection:
    if CONNECTION is None:
        raise NoConnection()
    return CONNECTION


def gen_columns_sql(fields: FieldMap) -> Iterator[ColumnSchema]:
    for name, py_type in fields.items():
        check_identifier(name)
        try:
            sql_type = SQL_TYPES[py_type]
        except KeyError as e:
            raise ValueError(f'type {py_type!r} is not supported') from e
        yield ColumnSchema(name, sql_type)


def make_schema_sql(table_name: str, fields: FieldMap) -> str:
    check_identifier(table_name)
    pk = 'pk INTEGER PRIMARY KEY,'
    spcs = ' ' * 4
    columns = ',\n    '.join(
        f'{field_name} {sql_type}'
        for field_name, sql_type in gen_columns_sql(fields)
    )
    return f'CREATE TABLE {table_name} (\n{spcs}{pk}\n{spcs}{columns}\n)'


def create_table(table_name: str, fields: FieldMap) -> None:
    con = get_connection()
    con.execute(make_schema_sql(table_name, fields))


def read_columns_sql(table_name: str) -> list[ColumnSchema]:
    check_identifier(table_name)
    con = get_connection()
    rows = con.execute(f'PRAGMA table_info({table_name!r})')
    return [ColumnSchema(r['name'], r['type']) for r in rows]


def valid_table(table_name: str, fields: FieldMap) -> bool:
    table_columns = read_columns_sql(table_name)
    return set(gen_columns_sql(fields)) <= set(table_columns)


def ensure_table(table_name: str, fields: FieldMap) -> None:
    table_columns = read_columns_sql(table_name)
    if len(table_columns) == 0:
        create_table(table_name, fields)
    if not valid_table(table_name, fields):
        raise SchemaMismatch(table_name)


def insert_record(table_name: str, data: dict[str, Any]) -> int:
    check_identifier(table_name)
    con = get_connection()
    placeholders = ', '.join(['?'] * len(data))
    sql = f'INSERT INTO {table_name} VALUES (NULL, {placeholders})'
    cursor = con.execute(sql, tuple(data.values()))
    pk = cursor.lastrowid
    con.commit()
    cursor.close()
    return pk


def fetch_record(table_name: str, pk: int) -> sqlite3.Row:
    check_identifier(table_name)
    con = get_connection()
    sql = f'SELECT * FROM {table_name} WHERE pk = ? LIMIT 2'
    result = list(con.execute(sql, (pk,)))
    if len(result) == 0:
        raise NoSuchRecord(pk)
    elif len(result) == 1:
        return result[0]
    else:
        raise UnexpectedMultipleResults()


def update_record(
    table_name: str, pk: int, data: dict[str, Any]
) -> tuple[str, tuple[Any, ...]]:
    check_identifier(table_name)
    con = get_connection()
    names = ', '.join(data.keys())
    placeholders = ', '.join(['?'] * len(data))
    values = tuple(data.values()) + (pk,)
    sql = f'UPDATE {table_name} SET ({names}) = ({placeholders}) WHERE pk = ?'
    con.execute(sql, values)
    con.commit()
    return sql, values


def delete_record(table_name: str, pk: int) -> sqlite3.Cursor:
    con = get_connection()
    check_identifier(table_name)
    sql = f'DELETE FROM {table_name} WHERE pk = ?'
    return con.execute(sql, (pk,))
```
测试类
```python
from textwrap import dedent

import pytest

from dblib import gen_columns_sql, make_schema_sql, connect, read_columns_sql
from dblib import ColumnSchema, insert_record, fetch_record, update_record
from dblib import NoSuchRecord, delete_record, valid_table


@pytest.fixture
def create_movies_sql():
    sql = '''
        CREATE TABLE movies (
            pk INTEGER PRIMARY KEY,
            title TEXT,
            revenue REAL
        )
        '''
    return dedent(sql).strip()


@pytest.mark.parametrize(
    'fields, expected',
    [
        (
            dict(title=str, awards=int),
            [('title', 'TEXT'), ('awards', 'INTEGER')],
        ),
        (
            dict(picture=bytes, score=float),
            [('picture', 'BLOB'), ('score', 'REAL')],
        ),
    ],
)
def test_gen_columns_sql(fields, expected):
    result = list(gen_columns_sql(fields))
    assert result == expected


def test_make_schema_sql(create_movies_sql):
    fields = dict(title=str, revenue=float)
    result = make_schema_sql('movies', fields)
    assert result == create_movies_sql


def test_read_columns_sql(create_movies_sql):
    expected = [
        ColumnSchema(name='pk', sql_type='INTEGER'),
        ColumnSchema(name='title', sql_type='TEXT'),
        ColumnSchema(name='revenue', sql_type='REAL'),
    ]
    with connect() as con:
        con.execute(create_movies_sql)
        result = read_columns_sql('movies')
    assert result == expected


def test_read_columns_sql_no_such_table(create_movies_sql):
    with connect() as con:
        con.execute(create_movies_sql)
        result = read_columns_sql('no_such_table')
    assert result == []


def test_insert_record(create_movies_sql):
    fields = dict(title='Frozen', revenue=1_290_000_000)
    with connect() as con:
        con.execute(create_movies_sql)
        for _ in range(3):
            result = insert_record('movies', fields)
    assert result == 3


def test_fetch_record(create_movies_sql):
    fields = dict(title='Frozen', revenue=1_290_000_000)
    with connect() as con:
        con.execute(create_movies_sql)
        pk = insert_record('movies', fields)
        row = fetch_record('movies', pk)
    assert tuple(row) == (1, 'Frozen', 1_290_000_000.0)


def test_fetch_record_no_such_pk(create_movies_sql):
    with connect() as con:
        con.execute(create_movies_sql)
        with pytest.raises(NoSuchRecord) as e:
            fetch_record('movies', 42)
        assert e.value.pk == 42


def test_update_record(create_movies_sql):
    fields = dict(title='Frozen', revenue=1_290_000_000)
    with connect() as con:
        con.execute(create_movies_sql)
        pk = insert_record('movies', fields)
        fields['revenue'] = 1_299_999_999
        sql, values = update_record('movies', pk, fields)
        row = fetch_record('movies', pk)
    assert sql == 'UPDATE movies SET (title, revenue) = (?, ?) WHERE pk = ?'
    assert values == ('Frozen', 1_299_999_999, 1)
    assert tuple(row) == (1, 'Frozen', 1_299_999_999.0)


def test_delete_record(create_movies_sql):
    fields = dict(title='Frozen', revenue=1_290_000_000)
    with connect() as con:
        con.execute(create_movies_sql)
        pk = insert_record('movies', fields)
        delete_record('movies', pk)
        with pytest.raises(NoSuchRecord) as e:
            fetch_record('movies', pk)
        assert e.value.pk == pk


def test_persistent_valid_table(create_movies_sql):
    fields = dict(title=str, revenue=float)

    with connect() as con:
        con.execute(create_movies_sql)
        con.commit()
        assert valid_table('movies', fields)


def test_persistent_valid_table_false(create_movies_sql):
    # year field not in movies_sql
    fields = dict(title=str, revenue=float, year=int)

    with connect() as con:
        con.execute(create_movies_sql)
        con.commit()
        assert not valid_table('movies', fields)
```

### 持久化工具库 persistlib
```python
"""
A ``Persistent`` class definition::

    >>> class Movie(Persistent):
    ...     title: str
    ...     year: int
    ...     box_office: float

Implemented behavior::

    >>> Movie._connect()  # doctest: +ELLIPSIS
    <sqlite3.Connection object at 0x...>
    >>> movie = Movie(title='The Godfather', year=1972, box_office=137)
    >>> movie.title
    'The Godfather'
    >>> movie.box_office
    137.0

Instances always have a ``._pk`` attribute, but it is ``None`` until the
object is saved::

    >>> movie._pk is None
    True
    >>> movie._save()
    1
    >>> movie._pk
    1

Delete the in-memory ``movie``, and fetch the record from the database,
using ``Movie[pk]``—item access on the class itself::

    >>> del movie
    >>> film = Movie[1]
    >>> film
    Movie(title='The Godfather', year=1972, box_office=137.0, _pk=1)

By default, the table name is the class name lowercased, with an appended
"s" for plural::

    >>> Movie._TABLE_NAME
    'movies'

If needed, a custom table name can be given as a keyword argument in the
class declaration::

    >>> class Aircraft(Persistent, table='aircraft'):
    ...     registration: str
    ...     model: str
    ...
    >>> Aircraft._TABLE_NAME
    'aircraft'

"""

from typing import Any, ClassVar, get_type_hints

import dblib as db


class Field:
    def __init__(self, name: str, py_type: type) -> None:
        self.name = name
        self.type = py_type

    def __set__(self, instance: 'Persistent', value: Any) -> None:
        try:
            value = self.type(value)
        except (TypeError, ValueError) as e:
            type_name = self.type.__name__
            msg = f'{value!r} is not compatible with {self.name}:{type_name}.'
            raise TypeError(msg) from e
        instance.__dict__[self.name] = value


class Persistent:
    _TABLE_NAME: ClassVar[str]
    _TABLE_READY: ClassVar[bool] = False

    @classmethod
    def _fields(cls) -> dict[str, type]:
        return {
            name: py_type
            for name, py_type in get_type_hints(cls).items()
            if not name.startswith('_')
        }

    def __init_subclass__(cls, *, table: str = '', **kwargs: Any):
        super().__init_subclass__(**kwargs)  # type:ignore
        cls._TABLE_NAME = table if table else cls.__name__.lower() + 's'
        for name, py_type in cls._fields().items():
            setattr(cls, name, Field(name, py_type))

    def __init__(self, *, _pk=None, **kwargs):
        field_names = self._asdict().keys()
        for name, arg in kwargs.items():
            if name not in field_names:
                msg = f'{self.__class__.__name__!r} has no attribute {name!r}'
                raise AttributeError(msg)
            setattr(self, name, arg)
        self._pk = _pk

    def __repr__(self) -> str:
        kwargs = ', '.join(
            f'{key}={value!r}' for key, value in self._asdict().items()
        )
        cls_name = self.__class__.__name__
        if self._pk is None:
            return f'{cls_name}({kwargs})'
        return f'{cls_name}({kwargs}, _pk={self._pk})'

    def _asdict(self) -> dict[str, Any]:
        return {
            name: getattr(self, name)
            for name, attr in self.__class__.__dict__.items()
            if isinstance(attr, Field)
        }


    # database methods

    @staticmethod
    def _connect(db_path: str = db.DEFAULT_DB_PATH):
        return db.connect(db_path)

    @classmethod
    def _ensure_table(cls) -> str:
        if not cls._TABLE_READY:
            db.ensure_table(cls._TABLE_NAME, cls._fields())
            cls._TABLE_READY = True
        return cls._TABLE_NAME

    def __class_getitem__(cls, pk: int) -> 'Persistent':
        field_names = ['_pk'] + list(cls._fields())
        values = db.fetch_record(cls._TABLE_NAME, pk)
        return cls(**dict(zip(field_names, values)))

    def _save(self) -> int:
        table = self.__class__._ensure_table()
        if self._pk is None:
            self._pk = db.insert_record(table, self._asdict())
        else:
            db.update_record(table, self._pk, self._asdict())
        return self._pk
```
测试类
```python
import pytest


from persistlib import Persistent


def test_field_descriptor_validation_type_error():
    class Cat(Persistent):
        name: str
        weight: float

    with pytest.raises(TypeError) as e:
        felix = Cat(name='Felix', weight=None)

    assert str(e.value) == 'None is not compatible with weight:float.'


def test_field_descriptor_validation_value_error():
    class Cat(Persistent):
        name: str
        weight: float

    with pytest.raises(TypeError) as e:
        felix = Cat(name='Felix', weight='half stone')

    assert str(e.value) == "'half stone' is not compatible with weight:float."


def test_constructor_attribute_error():
    class Cat(Persistent):
        name: str
        weight: float

    with pytest.raises(AttributeError) as e:
        felix = Cat(name='Felix', weight=3.2, age=7)

    assert str(e.value) == "'Cat' has no attribute 'age'"
```

### 小结

- 概述了类对象的属性，例如 `__qualname__` 方法和 `__subclasses__()` 方法。
- 内置函数 type 可以在运行时构造类。
- 介绍了特殊方法 `__init_subclass__`。
- 定义了第 1 版 Checked 基类，把子类中的属性类型提示替换为 Field 实例，借助构造函数在运行时限定属性的类型。
  使用 @checked 类装饰器可以实现同样的功能。
- 与 `__init_subclass__` 类似，类装饰器也可以为用户定义的类添加功能。
- `__init_subclass__` 和类装饰器均不能动态配置 `__slots__`，因为它们只在创建类之后发挥作用。
- 通过实验区分“导入时”和“运行时”：打印输出模块、描述符、类装饰器和 `__init_subclass__` 在 Python 代码中的执行顺序。
- 简要说明作为元类的 type，以及用户定义的元类如何通过 `__new__` 方法定制要构建的类。
- 自己定义了第一个元类，即使用 `__slots__` 的经典 MetaBunch 示例。
- 一个求解时间实验，表明元类的 `__prepare__` 和 `__new__` 方法均在 `__init_subclass__` 和类装饰器之前调用，这为定制类提供了机会。
- 定义 Checked 类构建器的第 3 版。用到了 Field 描述符，还配置了 `__slots__`。
- 介绍 João S. O. Bueno 提出的 AutoConst 构思：使用元类的 `__prepare__` 方法，返回一个实现了 `__missing__` 方法的映射。