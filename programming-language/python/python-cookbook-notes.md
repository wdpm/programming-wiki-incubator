pythonheapq 堆 优先级队列
from collections import OrderedDict 有序字典，3.7 dict也保留插入顺序
zip() 压缩函数
from collections import Counter 简单计数
operator 模块中的 itemgetter(用于字典) attrgetter(用于对象) 用于排序的sorted()中key
itertools.groupby()函数 根据字段分组
from collections import defaultdict 默认值的dict
filter 过滤
collections.namedtuple() 命名元组
s = sum(x * x for x in nums) 生成器表达式
from collections import ChainMap 多个字段合并成一个字典，修改映射的操作总是会作用在列出的第一个映射结构。读操作从左到右查找。

dict.update() 字段合并
----------------
任意分隔符拆分字符串 re.split()
在字符串的开头或结尾处做文本匹配 startswith() endswith() re.match()
利用 Shell 通配符做字符串匹配 from fnmatch import fnmatch, fnmatchcase ，或者 glob 模块
文本的任意位置查找 text.find('no') ，或首先用 re.compile()对模式进行编译，然后使用像match()、findall()或 finditer()这样的方法做匹配和搜索
文本替换 str.replace()，  re 模块中的 sub()函数/方法
不区分大小写的方式对文本做查找和替换 re.findall('python', text, flags=re.IGNORECASE)
re.compile()函数可接受标记re.DOTALL。这使得正则表达式中的句点(.)可以匹配所有的字符，也包括换行符。或者自定义正则  re.compile(r'/\*((?:.|\n)*?)\*/')
unicodedata.normalize()的第一个参数指定了字符串应该如何完成规范表示。NFC（全组成） NFD（组合字符）
从字符串中去掉不需要的字符 replace() 或者 strip() lstrip() rstrip() 或者高级的translate() 或者 结合 encode()和 decode()操作
对齐文本字符串 ljust() rjust() center()，或者format(text, '>20')
textwrap.fill(s, 70) 打印制定列宽的文本
html.escape()，from html.parser import HTMLParser，from xml.sax.saxutils import unescape 都有转义的方法
2.18 分词器，参阅 PyParsing 或 PLY 包
2.29 解析器
----------------
from decimal import Decimal 小数精确计算， 它的localcontext 也很方便
小数格式化输出 format(x, '0.1f')
bin()、oct()和 hex()函数 打印一个数值， format(2**32 -1234, 'b') 产生无符号位的数值表示， int('10011010010', 2)数值转化
从字节串中打包和解包大整数，应用: IP地址的保存和表示 int.from_bytes() x.to_bytes()
无穷大和 NaN：float('inf') float('nan')。唯一安全检测 NaN 的方法是使用 math.isnan()
分数的计算：fractions 模块
随机选择 random.choice() random.sample()，洗牌 random.shuffle() ，随机数 random.randint()，0-1随机数 random.random()
时间换算 datetime，或者 dateutil。dateutil 在处理有关月份的问题时能填补一些 datetime模块留下的空缺。from dateutil.relativedelta import relativedelta
dateutil 模块中的 relativedelta()函数对于计算类似“上周五是几月几号”这种问题比较有用。
datetime.strptime()方法支持许多格式化代码
一旦日期经过了本地化处理，它就可以转换为其他的时区。pyzt 模块主要用来本地化由 datetime 库创建的日期。
【为了不让我们的头炸掉，通常用来处理本地时间的方法是将所有的日期都转换为 UTC
（世界统一时间）时间，然后在所有的内部存储和处理中都使用 UTC 时间。】

---------------
实现__reversed__()方法，那么就可以在自定义的类上实现反向迭代
要对迭代器和生成器做切片操作，itertools.islice()函数是完美的选择。islice()产生的结果是一个迭代器，它可以产生出所需要的切
片元素，但这是通过访问并丢弃所有起始索引之前的元素来实现的。此外还有dropwhile()

heapq.merge(a, b) 合并多个序列

当出于某种原因需要模拟出一个普通文件时，这种情况下 StringIO 和 BytesIO 类是最为
适用的。例如，在单元测试中，可能会使用 StringIO 来创建一个文件型的对象，对象中
包含了测试用的数据。之后我们可将这个对象发送给一个可以接受普通文件的函数

 gzip 或 bz2 格式压缩

```
>>> f = open('sample.txt','w')
>>> f
<_io.TextIOWrapper name='sample.txt' mode='w' encoding='UTF-8'>
>>> f.buffer
<_io.BufferedWriter name='sample.txt'>
>>> f.buffer.raw
<_io.FileIO name='sample.txt' mode='wb'>
>>>
```

在这个例子中，io.TextIOWrapper 是一个文本处理层，它负责编码和解码 Unicode。而
io.BufferedWriter 是一个缓冲 I/O 层，负责处理二进制数据。最后，io.FileIO 是一个原
始文件，代表着操作系统底层的文件描述符

临时文件目录
with NamedTemporaryFile('w+t', delete=False) as f:
print('filename is:', f.name)

参阅：http://docs.python.org/3/library/tempfile.html）。

-------------

csv.reader() DictReader() 或者pandas csv数据统计

json对类的序列化 serialize_instance(obj) 、 unserialize_object(d):

xml、lxml库处理XML

创建XML不要手动str拼接，必须从数据结构来考虑数据的设计，否则不好维护。

6.12 内存视图的复杂例子（很难）

------------

如果不打算提供一个默认值，只是想编写代码来检测可选参数是否被赋予了某个特定
的值，那么可以采用下面的惯用手法：
_no_value = object()
def spam(a, b=_no_value):
    if b is _no_value:
    print('No b value supplied')
仔细区分不传递任何值和传递 None 之间的区别

初始化就绑定
>>> x = 10
>>> a = lambda y, x=x: x + y

运行时绑定
>>> x = 10
>>> a = lambda y, x: x + y


在毁掉函数中携带额外的状态，四种方式：类，闭包，协程send()函数，通过额外的参数在回调函数中携带状态，然后用 partial()来处理参数个数的问题。

--------

对于__repr__()，标准的做法是让它产生的字符串文本能够满足 eval(repr(x)) == x。如果
不可能办到或者说不希望有这种行为，那么通常就让它产生一段有帮助意义的文本，
并且以<和>括起来

让对象支持上下文管理协议，实现__enter__()和__exit__()，参阅  contextmanager 模块。

property 一般用来定义需要计算的属性。

super()函数的一种常见用途是调用父类的__init__()方法，确保父类被正确地初始化。

如果只是想访问某个特定的类中的一种属性，并对此做定制化
处理，那么最好不要编写描述符来实现。对于这个任务，用 property 属性方法来完成会
更加简单（见 8.6 节）。在需要大量重用代码的情况下，描述符会更加有用。

让属性具有惰性求值的能力

```
class lazyproperty:
    def __init__(self, func):
    	self.func = func
    
    def __get__(self, instance, cls):
        if instance is None:
        	return self
        else:
            value = self.func(instance)
            setattr(instance, self.func.__name__, value)
            return value
```

```
import math
class Circle:
    def __init__(self, radius):
    	self.radius = radius
    
    @lazyproperty
    def area(self):
        print('Computing area')
        return math.pi * self.radius ** 2	
```

本节讨论的技术有一个潜在的缺点，即，计算出的值在创建之后就变成可变的（mutable）了。

```
def lazyproperty(func):
	name = '_lazy_' + func.__name__
	
	@property
    def lazy(self):
        if hasattr(self, name):
        	return getattr(self, name)
        else:
            value = func(self)
            setattr(self, name, value)
            return value
    return lazy
```

这种方式的缺点就是所有的 get 操作都必须经由属性的 getter 函数来处理。这比直接在实例字典中查找相应的值要慢一些。=> 函数开销比dict的hash查找开销要高。

---

```python
class Structure:
    # Class variable that specifies expected fields
    _fields= []
    
    def __init__(self, *args):
        if len(args) != len(self._fields):
        	raise TypeError('Expected {} arguments'.format(len(self._fields)))
        # Set the arguments (alternate)
        self.__dict__.update(zip(self._fields,args))
```

这种技术的一个潜在缺点就是会影响到 IDE（集成开发环境）的文档和帮助功能。如果用户针对某个特定的类寻求帮助，那么所需的参数将不会以正常的形式来表述。

这些问题可以通过在__init__()函数中强制施行类型签名来解决，相关内容请参阅 9.16 节。

---

定义一个接口或抽象基类。

bisect 模块能够方便地让列表中的元素保持有序。bisect.insort()函数能够将元素插入到列表中且让列表仍然保持有序

类方法的明确性

```python
a = Date(2012, 12, 21) # Clear. A specific date.
b = Date()
# ??? What does this do?

# Class method version
c = Date.today()
# Clear. Today's date.
```

---

mixins 类一般没有状态，`__slots__`一般是()

实现带有状态的对象或状态机

- 直接修改实例的__class__属性
- 或者使用依赖注入模式

```python
# Alternative implementation
class State:
    def __init__(self):
    	self.new_state(State_A)
    def new_state(self, state):
    	self.__class__ = state
    def action(self, x):
    	raise NotImplementedError()

class State_A(State):
    def action(self, x):
    # Action for A
    ...
    self.new_state(State_B)
```

在环状数据结构中管理内存，

```
@parent.setter
def parent(self, node):
	self._parent = weakref.ref(node)	
```

弱引用就是一个指向对象的指针，但不会增加对象本身的引用计数。

---

创建缓存实例。

解决方案：使用一个和类本身分开的工厂函数。

```python
# The class in question
class Spam:
    def __init__(self, name):
        self.name = name

# Caching support
import weakref
_spam_cache = weakref.WeakValueDictionary()
def get_spam(name):
    if name not in _spam_cache:
        s = Spam(name)
        _spam_cache[name] = s
    else:
        s = _spam_cache[name]
    return s
```

或者

```python
# ------------------------最后的修正方案------------------------
class CachedSpamManager2:
    def __init__(self):
        self._cache = weakref.WeakValueDictionary()

    def get_spam(self, name):
        if name not in self._cache:
            temp = Spam3._new(name)  # Modified creation
            self._cache[name] = temp
        else:
            temp = self._cache[name]
        return temp

    def clear(self):
            self._cache.clear()

class Spam3:
    def __init__(self, *args, **kwargs):
        raise RuntimeError("Can't instantiate directly")

    # Alternate constructor
    @classmethod
    def _new(cls, name):
        self = cls.__new__(cls)
        self.name = name
        return self
```

---



## 元编程

- 函数修饰器

---

```python
>>> @somedecorator
>>> def add(x, y):
...     return x + y
...
>>> orig_add = add.__wrapped__
>>> orig_add(3, 4)
7
>>>
```

__wrapped__ 直接访问原函数。但是，如果有多个包装器，那么访问 `__wrapped__` 属性的行为是不可预知的，应该避免这样做。

---

可自定义属性的装饰器。

```python
from functools import wraps, partial
import logging
# Utility decorator to attach a function as an attribute of obj
def attach_wrapper(obj, func=None):
    if func is None:
        return partial(attach_wrapper, obj)
    setattr(obj, func.__name__, func)
    return func

def logged(level, name=None, message=None):
    '''
    Add logging to a function. level is the logging
    level, name is the logger name, and message is the
    log message. If name and message aren't specified,
    they default to the function's module and name.
    '''
    def decorate(func):
        logname = name if name else func.__module__
        log = logging.getLogger(logname)
        logmsg = message if message else func.__name__

        @wraps(func)
        def wrapper(*args, **kwargs):
            log.log(level, logmsg)
            return func(*args, **kwargs)

        # Attach setter functions
        @attach_wrapper(wrapper)
        def set_level(newlevel):
            nonlocal level
            level = newlevel

        @attach_wrapper(wrapper)
        def set_message(newmsg):
            nonlocal logmsg
            logmsg = newmsg

        return wrapper

    return decorate

# Example use
@logged(logging.DEBUG)
def add(x, y):
    return x + y

@logged(logging.CRITICAL, 'example')
def spam():
    print('Spam!')
```

```bash
>>> import logging
>>> logging.basicConfig(level=logging.DEBUG)
>>> add(2, 3)
DEBUG:__main__:add
5
>>> # Change the log message
>>> add.set_message('Add called')
>>> add(2, 3)
DEBUG:__main__:Add called
5
>>> # Change the log level
>>> add.set_level(logging.WARNING)
>>> add(2, 3)
WARNING:__main__:Add called
5
>>>
```

---

将装饰器定义为类

```python
import types
from functools import wraps

class Profiled:
    def __init__(self, func):
        # => self.__wrapped__被定义
        wraps(func)(self)
        self.ncalls = 0

    def __call__(self, *args, **kwargs):
        self.ncalls += 1
        return self.__wrapped__(*args, **kwargs)

    def __get__(self, instance, cls):
        if instance is None:
            return self
        else:
            # 将方法绑定到instance实例
            return types.MethodType(self, instance)
```

```python
@Profiled
def add(x, y):
    return x + y

class Spam:
    @Profiled
    def bar(self, x):
        print(self, x)
```

也可以这样实现：

```python
import types
from functools import wraps

def profiled(func):
    ncalls = 0
    @wraps(func)
    def wrapper(*args, **kwargs):
        nonlocal ncalls
        ncalls += 1
        return func(*args, **kwargs)
    wrapper.ncalls = lambda: ncalls
    return wrapper

# Example
@profiled
def add(x, y):
    return x + y
```



装饰器为被包装函数增加参数

```python
from functools import wraps
import inspect

def optional_debug(func):
    if 'debug' in inspect.getargspec(func).args:
        raise TypeError('debug argument already defined')

    @wraps(func)
    def wrapper(*args, debug=False, **kwargs):
        if debug:
            print('Calling', func.__name__)
        return func(*args, **kwargs)

    # 修复函数签名
    sig = inspect.signature(func)
    parms = list(sig.parameters.values())
    parms.append(inspect.Parameter('debug',
                inspect.Parameter.KEYWORD_ONLY,
                default=False))
    wrapper.__signature__ = sig.replace(parameters=parms)
    
    return wrapper
```

```bash
>>> @optional_debug
... def add(x,y):
...     return x+y
...
>>> print(inspect.signature(add))
(x, y, *, debug=False)
>>> add(2,3)
5
>>>
```



使用装饰器扩充类的功能

```python
def log_getattribute(cls):
    # Get the original implementation
    orig_getattribute = cls.__getattribute__

    # Make a new definition
    def new_getattribute(self, name):
        print('getting:', name)
        return orig_getattribute(self, name)

    # Attach to the class and return
    cls.__getattribute__ = new_getattribute
    return cls

# Example use
@log_getattribute
class A:
    def __init__(self,x):
        self.x = x
    def spam(self):
        pass
```



单例模式——基于元类的实现。

```python
class Singleton(type):
    def __init__(self, *args, **kwargs):
        self.__instance = None
        super().__init__(*args, **kwargs)

    def __call__(self, *args, **kwargs):
        if self.__instance is None:
            self.__instance = super().__call__(*args, **kwargs)
            return self.__instance
        else:
            return self.__instance

# Example
class Spam(metaclass=Singleton):
    def __init__(self):
        print('Creating Spam')
```

缓存模式的实现。是否缓存取决于 *args。

```python
import weakref

class Cached(type):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.__cache = weakref.WeakValueDictionary()

    def __call__(self, *args):
        if args in self.__cache:
            return self.__cache[args]
        else:
            obj = super().__call__(*args)
            self.__cache[args] = obj
            return obj

# Example
class Spam(metaclass=Cached):
    def __init__(self, name):
        print('Creating Spam({!r})'.format(name))
        self.name = name
```

单例模式——别扭的实现。全局变量。

```python
class _Spam:
    def __init__(self):
        print('Creating Spam')

_spam_instance = None

def Spam():
    global _spam_instance

    if _spam_instance is not None:
        return _spam_instance
    else:
        _spam_instance = _Spam()
        return _spam_instance
```



 [*](https://python3-cookbook.readthedocs.io/zh-cn/latest/c09/p16_enforce_argument_signature_on_args_kwargs.html#id1)args和**kwargs的强制参数签名

> 你有一个函数或方法，它使用*args和**kwargs作为参数，这样使得它比较通用， 但有时候你想检查传递进来的参数是不是某个你想要的类型。

```python
from inspect import Signature, Parameter

def make_sig(*names):
    parms = [Parameter(name, Parameter.POSITIONAL_OR_KEYWORD)
            for name in names]
    return Signature(parms)

class Structure:
    __signature__ = make_sig()
    def __init__(self, *args, **kwargs):
        bound_values = self.__signature__.bind(*args, **kwargs)
        for name, value in bound_values.arguments.items():
            setattr(self, name, value)

# Example use
class Stock(Structure):
    __signature__ = make_sig('name', 'shares', 'price')

class Point(Structure):
    __signature__ = make_sig('x', 'y')
```



在类上执行强制规约

```python
class NoMixedCaseMeta(type):
    def __new__(cls, clsname, bases, clsdict):
        for name in clsdict:
            if name.lower() != name:
                raise TypeError('Bad attribute name: ' + name)
        return super().__new__(cls, clsname, bases, clsdict)

class Root(metaclass=NoMixedCaseMeta):
    pass

class A(Root):
    def foo_bar(self): # Ok
        pass

class B(Root):
    def fooBar(self): # TypeError
        pass
```



属性类型检查

```python
def typed_property(name, expected_type):
    storage_name = '_' + name

    @property
    def prop(self):
        return getattr(self, storage_name)

    @prop.setter
    def prop(self, value):
        if not isinstance(value, expected_type):
            raise TypeError('{} must be a {}'.format(name, expected_type))
        setattr(self, storage_name, value)

    return prop

# Example use
class Person:
    name = typed_property('name', str)
    age = typed_property('age', int)

    def __init__(self, name, age):
        self.name = name
        self.age = age
```

更进一步

```python
from functools import partial

String = partial(typed_property, expected_type=str)
Integer = partial(typed_property, expected_type=int)

# Example:
class Person:
    name = String('name')
    age = Integer('age')

    def __init__(self, name, age):
        self.name = name
        self.age = age
```



## 模块与包

> 合并多个文件合并成一个单一的逻辑命名空间

```python
from mymodule.a import A
from mymodule.b import B
```

上面这张方式不好，应该为

```python
from mymodule import A, B
```

---

包命名空间是一种特殊的封装设计，为合并不同的目录的代码到一个共同的命名空间。



## 并发编程

> 线程间通信

使用哨兵对象。

```
# Object that signals shutdown
_sentinel = object()
```

使用条件变量

```
self._cv = threading.Condition()
```

使用Event

```python
from queue import Queue
from threading import Thread, Event

# A thread that produces data
def producer(out_q):
    while running:
        # Produce some data
        ...
        # Make an (data, event) pair and hand it to the consumer
        evt = Event()
        out_q.put((data, evt))
        ...
        # Wait for the consumer to process the item
        evt.wait()

# A thread that consumes data
def consumer(in_q):
    while True:
        # Get some data
        data, evt = in_q.get()
        # Process the data
        ...
        # Indicate completion
        evt.set()
```

使用线程队列有一个要注意的问题是，向队列中添加数据项时并不会复制此数据项，线程间通信实际上是在线程间传递对象引用。如果你担心对象的共享状态，那你最好只传递不可修改的数据结构（如：整型、字符串或者元组）或者一个对象的深拷贝。

`get()` 和 `put()` 方法都支持非阻塞方式和设定超时

```python
import queue
q = queue.Queue()

try:
    data = q.get(block=False)
except queue.Empty:
    ...

try:
    q.put(item, block=False)
except queue.Full:
    ...

try:
    data = q.get(timeout=5.0)
except queue.Empty:
    ...
```

---

> 关键部分加锁

对于i++问题，注意区分 `self._value_lock = threading.Lock()` 和 `_lock = threading.RLock() `的区别。



12.5-12.4 还没有读完。TODO



## 脚本编程与系统管理

