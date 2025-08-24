# python cookbook notes

## 字典
- heapq 堆，优先级队列                                          |
- from collections import OrderedDict 有序字典，3.7 dict也保留插入顺序
- zip() 压缩函数
- from collections import Counter 简单计数
- operator 模块中的 itemgetter(用于字典) attrgetter(用于对象) 用于排序的sorted()中key
- itertools.groupby()函数 根据字段分组
- from collections import defaultdict 默认值的dict
- filter() 过滤
- collections.namedtuple() 命名元组
- s = sum(x * x for x in nums) 生成器表达式
- from collections import ChainMap 多个字典合并成一个字典，修改映射的操作总是会作用在列出的第一个映射结构。读操作从左到右查找。
- dict.update() 字段合并

## 文本处理
- 任意分隔符拆分字符串 re.split()
- 在字符串的开头或结尾处做文本匹配 startswith() endswith() re.match()
- 利用 Shell 通配符做字符串匹配 from fnmatch import fnmatch, fnmatchcase ，或者 glob 模块
- 文本的任意位置查找 text.find('no') ，或首先用 re.compile()对模式进行编译，然后使用像match()、findall()或 finditer()这样的方法做匹配和搜索
- 文本替换 str.replace()，re 模块中的 sub()函数/方法
- 不区分大小写的方式对文本做查找和替换 re.findall('python', text, flags=re.IGNORECASE)
- re.compile()函数可接受标记re.DOTALL。这使得正则表达式中的句点(.)可以匹配所有的字符，也包括换行符。或者自定义正则 re.compile(r'/\*((?:.|\n)*?)\*/')
- unicodedata.normalize()的第一个参数指定了字符串应该如何完成规范表示。NFC（全组成） NFD（组合字符）
- 从字符串中去掉不需要的字符 replace() 或者 strip() lstrip() rstrip()，或者高级的translate() 或者 结合 encode()和 decode()操作
- 对齐文本字符串 ljust() rjust() center()，或者format(text, '>20')
- textwrap.fill(s, 70) 打印制定列宽的文本
- html.escape()，from html.parser import HTMLParser，from xml.sax.saxutils import unescape 都有转义的方法
- 2.18 分词器，参阅 PyParsing 或 PLY 包
- 2.29 解析器

## 日期和数字
- from decimal import Decimal 小数精确计算，它的 localcontext 是一个上下文管理器，用于临时修改 Decimal 运算的上下文环境（如精度、舍入方式等）。
- 小数部分格式化输出 format(x, '0.1f')
- bin()、oct()和 hex()函数。打印一个数值，format(2**32 -1234, 'b') 产生无符号位的数值表示， int('10011010010', 2)数值转化
- 从字节串中打包和解包大整数，应用: IP地址的保存和表示 int.from_bytes() x.to_bytes()
    ```python
    import socket
    
    ip_str = "192.168.1.1"
    packed_ip = socket.inet_aton(ip_str)  # b'\xc0\xa8\x01\x01'
    ip_int = int.from_bytes(packed_ip, 'big')  # 3232235777
    
    unpacked_ip = ip_int.to_bytes(4, 'big')  # b'\xc0\xa8\x01\x01'
    restored_ip = socket.inet_ntoa(unpacked_ip)  # "192.168.1.1"
    ```
- 无穷大和 NaN：float('inf') float('nan')。唯一安全检测 NaN 的方法是使用 math.isnan()
- 分数的计算：fractions 模块
- 随机选择 random.choice() random.sample()，洗牌 random.shuffle() ，随机数 random.randint()，0-1随机数 random.random()
- 时间换算 datetime，或者 dateutil。dateutil 在处理有关月份的问题时能填补一些 datetime 模块留下的空缺。from dateutil.relativedelta import relativedelta
- dateutil 模块中的 relativedelta()函数对于计算类似“上周五是几月几号”这种问题比较有用。
- datetime.strptime() 方法支持许多格式化代码
- 一旦日期经过了本地化处理，它就可以转换为其他的时区。pyzt 模块主要用来本地化由 datetime 库创建的日期。
- 为了不让我们的头炸掉，通常用来处理本地时间的方法是将所有的日期都转换为 UTC （世界统一时间）时间，然后在所有的内部存储和处理中都使用 UTC 时间。

## 文件
- 实现__reversed__()方法，那么就可以在自定义的类上实现反向迭代
- 要对迭代器和生成器做切片操作，itertools.islice()函数是完美的选择。islice()产生的结果是一个迭代器，它可以产生出所需要的切片元素，
但这是通过访问并丢弃所有起始索引之前的元素来实现的。此外还有dropwhile()
- heapq.merge(a, b) 可以合并多个序列

- 当出于某种原因需要模拟出一个普通文件时，这种情况下 StringIO 和 BytesIO 类是最为适用的。
例如，在单元测试中，可能会使用 StringIO 来创建一个文件型的对象，对象中包含了测试用的数据。之后我们可将这个对象发送给一个可以接受普通文件的函数。

- gzip 或 bz2 格式压缩
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
在这个例子中，io.TextIOWrapper 是一个文本处理层，它负责编码和解码 Unicode。而 io.BufferedWriter 是一个缓冲 I/O 层，负责处理二进制数据。
最后，io.FileIO 是一个原始文件，代表着操作系统底层的文件描述符。

- 临时文件目录
```python
# 参阅：http://docs.python.org/3/library/tempfile.html）。
with NamedTemporaryFile('w+t', delete=False) as f:
    print('filename is:', f.name)
```

## 常见数据文件格式

- csv.reader() DictReader() 或者pandas csv数据统计
- json对类的序列化 serialize_instance(obj) 、 unserialize_object(d):
- xml、lxml库处理XML
- 创建XML不要手动str拼接，必须从数据结构来考虑数据的构造和表示，否则不好维护。
- 6.12 内存视图的复杂例子（很难）

## 函数技巧

- 如果不打算提供一个默认值，只是想编写代码来检测可选参数是否被赋予了某个特定的值，那么可以采用下面的惯用手法：
```python
_no_value = object()
def spam(a, b=_no_value):
    if b is _no_value:
        print('No b value supplied')
```
仔细区分不传递任何值和传递 None 之间的区别。

- 值绑定
```
初始化就绑定
>>> x = 10
>>> a = lambda y, x=x: x + y

运行时绑定
>>> x = 10
>>> a = lambda y, x: x + y
```
- 在回调函数中携带额外的状态，四种方式：类，闭包，协程send()函数，通过额外的参数在回调函数中携带状态，然后用 partial()来处理参数个数的问题。

- 对于__repr__()，标准的做法是让它产生的字符串文本能够满足 eval(repr(x)) == x。
如果不可能办到或者说不希望有这种行为，那么通常就让它产生一段有帮助意义的文本， 并且以<和>括起来。

- 让对象支持上下文管理协议，实现__enter__()和__exit__()，参阅  contextmanager 模块。

- property 一般用来定义需要计算的属性。

- super()函数的一种常见用途是调用父类的__init__()方法，确保父类被正确地初始化。

- 如果只是想访问某个特定的类中的一种属性，并对此做定制化处理，那么最好不要编写描述符来实现。对于这个任务，用 property 属性方法来完成会
更加简单（见 8.6 节）。在需要大量重用代码的情况下，描述符会更加有用。

- 让属性具有惰性求值的能力
```pyton
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
```python
import math
class Circle:
    def __init__(self, radius):
    	self.radius = radius
    
    @lazyproperty
    def area(self):
        print('Computing area')
        return math.pi * self.radius ** 2	
```

本节讨论的技术有一个潜在的缺点，即计算出的值在创建之后就变成可变的（mutable）了。

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
这种方式的缺点就是所有的 get 操作都必须经由属性的 getter 函数来处理。这比直接在实例字典中查找相应的值要慢一些。即函数开销比dict的hash查找开销要高。

- 严格的类初始化的位置参数

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

- bisect 模块能够方便地让列表中的元素保持有序。bisect.insort()函数能够将元素插入到列表中且让列表仍然保持有序

- 类方法的名称具有语义明确性
```python
a = Date(2012, 12, 21) # Clear. A specific date.
b = Date()
# ??? What does this do?

# Class method version
c = Date.today()
# Clear. Today's date.
```

- mixins 类一般没有状态，`__slots__`一般是()

- 实现带有状态的对象或状态机 。直接修改实例的__class__属性，同时使用依赖注入模式。
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

- 在环状数据结构中管理内存，
```python
@parent.setter
def parent(self, node):
	self._parent = weakref.ref(node)	
```
弱引用就是一个指向对象的指针，但不会增加对象本身的引用计数。

- 创建缓存实例。
```python
# ------------------------最后的修正方案------------------------
class CachedSpamManager2:
    def __init__(self):
        self._cache = weakref.WeakValueDictionary()

    # 唯一获取入口    
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

## 元编程

### 函数修饰器
```
>>> @somedecorator
>>> def add(x, y):
...     return x + y
...
>>> orig_add = add.__wrapped__
>>> orig_add(3, 4)
7
>>>
```
`__wrapped__` 直接访问原函数。但是，如果有多个包装器，那么访问 `__wrapped__` 属性的行为是不可预知的，应该避免这样做。

### 可自定义属性的装饰器。
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
用例
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

### 将装饰器定义为类
```python
import types
from functools import wraps

class Profiled:
    def __init__(self, func):
        # => self 会被添加 __wrapped__ 属性，指向原始函数 func
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

### 装饰器为被包装函数增加参数

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
    parms.append(inspect.Parameter('debug',inspect.Parameter.KEYWORD_ONLY,default=False))
    wrapper.__signature__ = sig.replace(parameters=parms)
    
    return wrapper
```
用例
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

### 使用装饰器扩充类的功能

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

@log_getattribute
class A:
    def __init__(self,x):
        self.x = x
    def spam(self):
        pass
```

### 单例模式——基于元类的实现

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

class Spam(metaclass=Singleton):
    def __init__(self):
        print('Creating Spam')
```

缓存模式的实现，是否缓存取决于 *args。

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

### 单例模式——别扭的实现，全局变量

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


### *args和**kwargs的强制参数签名
> https://python3-cookbook.readthedocs.io/zh-cn/latest/c09/p16_enforce_argument_signature_on_args_kwargs.html#id1

你有一个函数或方法，它使用*args和**kwargs作为参数，这样使得它比较通用，但有时候你想检查传递进来的参数是不是某个你想要的类型。

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
检验
```bash
>>> import inspect
>>> print(inspect.signature(Stock))
(name, shares, price)
```
注意Stock只是声明，没有实例化，但是 ` __signature__ ` 也生效了。

### 在类上执行强制规约

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

### 属性类型检查

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

包命名空间是一种特殊的封装设计，为合并不同的目录的代码到一个共同的命名空间。


## 并发编程

### 线程间通信

- 使用哨兵对象。

```
# Object that signals shutdown
_sentinel = object()
```

- 使用条件变量

```
self._cv = threading.Condition()
```

- 使用Event

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

使用线程队列有一个要注意的问题是，向队列中添加数据项时并不会复制此数据项，**线程间通信实际上是在线程间传递对象引用**。
如果你担心对象的共享状态，那你最好只传递不可修改的数据结构（如：整型、字符串或者元组）或者一个对象的深拷贝。

### 队列的get和set
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

### 关键部分加锁

对于i++问题，注意区分 `self._value_lock = threading.Lock()` 和 `_lock = threading.RLock() `的区别。

### 加锁机制

防止死锁的加锁机制，按升序规则获取锁，反序释放。

```python
import threading
from contextlib import contextmanager

# Thread-local state to stored information on locks already acquired
_local = threading.local()

@contextmanager
def acquire(*locks):
    # Sort locks by object identifier
    locks = sorted(locks, key=lambda x: id(x))

    # Make sure lock order of previously acquired locks is not violated
    acquired = getattr(_local,'acquired',[])
    if acquired and max(id(lock) for lock in acquired) >= id(locks[0]):
        raise RuntimeError('Lock Order Violation')

    # Acquire all of the locks
    acquired.extend(locks)
    _local.acquired = acquired

    try:
        for lock in locks:
            lock.acquire()
        yield
    finally:
        # Release locks in reverse order of acquisition
        for lock in reversed(locks):
            lock.release()
        del acquired[-len(locks):]
```

```python
import threading
x_lock = threading.Lock()
y_lock = threading.Lock()

def thread_1():
    while True:
        with acquire(x_lock, y_lock):
            print('Thread-1')

def thread_2():
    while True:
        with acquire(y_lock, x_lock):
            print('Thread-2')

t1 = threading.Thread(target=thread_1)
t1.daemon = True
t1.start()

t2 = threading.Thread(target=thread_2)
t2.daemon = True
t2.start()
```

上面的代码测试通过。但是不能类似下面的嵌套。

```python
import threading
x_lock = threading.Lock()
y_lock = threading.Lock()
# x id > y id

def thread_1():
    while True:
        with acquire(x_lock):
            with acquire(y_lock):
                print('Thread-1')

def thread_2():
    while True:
        with acquire(y_lock):
            with acquire(x_lock):
                print('Thread-2')

t1 = threading.Thread(target=thread_1)
t1.daemon = True
t1.start()

t2 = threading.Thread(target=thread_2)
t2.daemon = True
t2.start()
```

一个比较常用的死锁检测与恢复的方案是引入看门狗计数器。当线程正常运行的时候会每隔一段时间重置计数器，在没有发生死锁的情况下，一切都正常进行。
一旦发生死锁，由于无法重置计数器导致定时器超时，这时程序会通过重启自身恢复到正常状态。

避免死锁是另外一种解决死锁问题的方式，在进程获取锁的时候会严格按照对象id升序排列获取，经过数学证明，这样保证程序不会进入死锁状态。
避免死锁的主要思想是，单纯地按照对象id递增的顺序加锁不会产生循环依赖，而循环依赖是死锁的一个必要条件，从而避免程序进入死锁状态。

### 哲学家就餐问题

```python
import threading

# The philosopher thread
def philosopher(left, right):
    while True:
        with acquire(left,right):
             print(threading.currentThread(), 'eating')

# The chopsticks (represented by locks)
NSTICKS = 5
chopsticks = [threading.Lock() for n in range(NSTICKS)]

# Create all of the philosophers
for n in range(NSTICKS):
    t = threading.Thread(target=philosopher,
                         args=(chopsticks[n],chopsticks[(n+1) % NSTICKS]))
    t.start()
```

### 线程本地对象

保存线程的状态信息，使用线程本地对象。

```python
from socket import socket, AF_INET, SOCK_STREAM
import threading

class LazyConnection:
    def __init__(self, address, family=AF_INET, type=SOCK_STREAM):
        self.address = address
        self.family = AF_INET
        self.type = SOCK_STREAM
        self.local = threading.local()

    def __enter__(self):
        if hasattr(self.local, 'sock'):
            raise RuntimeError('Already connected')
        self.local.sock = socket(self.family, self.type)
        self.local.sock.connect(self.address)
        return self.local.sock

    def __exit__(self, exc_ty, exc_val, tb):
        self.local.sock.close()
        del self.local.sock
```

```python
from functools import partial
def test(conn):
    with conn as s:
        s.send(b'GET /index.html HTTP/1.0\r\n')
        s.send(b'Host: www.python.org\r\n')

        s.send(b'\r\n')
        resp = b''.join(iter(partial(s.recv, 8192), b''))

    print('Got {} bytes'.format(len(resp)))

if __name__ == '__main__':
    conn = LazyConnection(('www.python.org', 80))

    t1 = threading.Thread(target=test, args=(conn,))
    t2 = threading.Thread(target=test, args=(conn,))
    t1.start()
    t2.start()
    t1.join()
    t2.join()
```

这里，一个线程对应一个独立的网络连接，互不干扰。

### 线程池

创建一个线程池

1. 使用 `concurrent.futures.ThreadPoolExecutor`

```python
from socket import AF_INET, SOCK_STREAM, socket
from concurrent.futures import ThreadPoolExecutor

def echo_client(sock, client_addr):
    """
    Handle a client connection
    """
    print('Got connection from', client_addr)
    while True:
        msg = sock.recv(65536)
        if not msg:
            break
        sock.sendall(msg)
    print('Client closed connection')
    sock.close()

def echo_server(addr):
    pool = ThreadPoolExecutor(128)
    sock = socket(AF_INET, SOCK_STREAM)
    sock.bind(addr)
    sock.listen(5)
    while True:
        client_sock, client_addr = sock.accept()
        pool.submit(echo_client, client_sock, client_addr)

echo_server(('',15000))
```

2.如果想手动创建你自己的线程池， 通常可以使用一个Queue来轻松实现。

```python
from socket import socket, AF_INET, SOCK_STREAM
from threading import Thread
from queue import Queue

def echo_client(q):
    """
    Handle a client connection
    """
    sock, client_addr = q.get()
    print('Got connection from', client_addr)
    while True:
        msg = sock.recv(65536)
        if not msg:
            break
        sock.sendall(msg)
    print('Client closed connection')

    sock.close()

def echo_server(addr, nworkers):
    # Launch the client workers
    q = Queue()
    for n in range(nworkers):
        t = Thread(target=echo_client, args=(q,))
        t.daemon = True
        t.start()

    # Run the server
    sock = socket(AF_INET, SOCK_STREAM)
    sock.bind(addr)
    sock.listen(5)
    while True:
        client_sock, client_addr = sock.accept()
        q.put((client_sock, client_addr))

echo_server(('',15000), 128)
```

这个demo本质是硬编码了128个工作线程，一旦消耗完这个程序就没有后续处理能力了。

3.此外，应该避免编写线程数量可以无限制增长的程序。

```python
from threading import Thread
from socket import socket, AF_INET, SOCK_STREAM

def echo_client(sock, client_addr):
    """
    Handle a client connection
    """
    print('Got connection from', client_addr)
    while True:
        msg = sock.recv(65536)
        if not msg:
            break
        sock.sendall(msg)
    print('Client closed connection')
    sock.close()

def echo_server(addr, nworkers):
    sock = socket(AF_INET, SOCK_STREAM)
    sock.bind(addr)
    sock.listen(5)
    while True:
        client_sock, client_addr = sock.accept()
        t = Thread(target=echo_client, args=(client_sock, client_addr))
        t.daemon = True
        t.start()

echo_server(('',15000))
```

### 并行编程

```python
# findrobots.py

import gzip
import io
import glob
from concurrent import futures

def find_robots(filename):
    """
    Find all of the hosts that access robots.txt in a single log file

    """
    robots = set()
    with gzip.open(filename) as f:
        for line in io.TextIOWrapper(f,encoding='ascii'):
            fields = line.split()
            if fields[6] == '/robots.txt':
                robots.add(fields[0])
    return robots

def find_all_robots(logdir):
    """
    Find all hosts across and entire sequence of files
    """
    files = glob.glob(logdir+'/*.log.gz')
    all_robots = set()
    with futures.ProcessPoolExecutor() as pool:
        for robots in pool.map(find_robots, files):
            all_robots.update(robots)
    return all_robots

if __name__ == '__main__':
    robots = find_all_robots('logs')
    for ipaddr in robots:
        print(ipaddr)
```

### 并行任务的使用范式

```python
# A function that performs a lot of work
def work(x):
    ...
    return result

# Nonparallel code
results = map(work, data)

# Parallel implementation
with ProcessPoolExecutor() as pool:
    results = pool.map(work, data)
```

或者手动提交

```python
# Some function
def work(x):
    ...
    return result

with ProcessPoolExecutor() as pool:
    ...
    # Example of submitting work to the pool
    future_result = pool.submit(work, arg)

    # Obtaining the result (blocks until done)
    r = future_result.result()
```

如果不想阻塞，你还可以使用一个回调函数，例如：

```python
def when_done(r):
    print('Got:', r.result())

with ProcessPoolExecutor() as pool:
     future_result = pool.submit(work, arg)
     future_result.add_done_callback(when_done)
```

注意点：
- 这种并行处理技术只适用于那些可以被分解为互相独立部分的问题。
- 被提交的任务必须是简单函数形式。对于方法、闭包和其他类型的并行执行还不支持。
- 函数参数和返回值必须兼容pickle，因为要使用到进程间的通信，所有解释器之间的交换数据必须被序列化。
- 被提交的任务函数不应保留状态或有副作用。除了打印日志之类简单的事情。

### Actor 模式

```python
from queue import Queue
from threading import Thread, Event

# Sentinel used for shutdown
class ActorExit(Exception):
    pass

class Actor:
    def __init__(self):
        self._mailbox = Queue()

    def send(self, msg):
        '''
        Send a message to the actor
        '''
        self._mailbox.put(msg)

    def recv(self):
        '''
        Receive an incoming message
        '''
        msg = self._mailbox.get()
        if msg is ActorExit:
            raise ActorExit()
        return msg

    def close(self):
        '''
        Close the actor, thus shutting it down
        '''
        self.send(ActorExit)

    def start(self):
        '''
        Start concurrent execution
        '''
        self._terminated = Event()
        t = Thread(target=self._bootstrap)

        t.daemon = True
        t.start()

    def _bootstrap(self):
        try:
            self.run()
        except ActorExit:
            pass
        finally:
            self._terminated.set()

    def join(self):
        self._terminated.wait()

    def run(self):
        '''
        Run method to be implemented by the user
        '''
        while True:
            msg = self.recv()

# Sample ActorTask
class PrintActor(Actor):
    def run(self):
        while True:
            msg = self.recv()
            print('Got:', msg)

# Sample use
p = PrintActor()
p.start()
p.send('Hello')
p.send('World')
p.close()
p.join()
```

其他泛化版本

```python
class TaggedActor(Actor):
    def run(self):
        while True:
             tag, *payload = self.recv()
             getattr(self,'do_'+tag)(*payload)

    # Methods correponding to different message tags
    def do_A(self, x):
        print('Running A', x)

    def do_B(self, x, y):
        print('Running B', x, y)

# Example
a = TaggedActor()
a.start()
a.send(('A', 1))      # Invokes do_A(1)
a.send(('B', 2, 3))   # Invokes do_B(2,3)
a.close()
a.join()
```

作为另外一个例子，下面的actor允许在一个工作者中运行任意的函数， 并且通过一个特殊的Result对象返回结果：

```python
from threading import Event
class Result:
    def __init__(self):
        self._evt = Event()
        self._result = None

    def set_result(self, value):
        self._result = value
        self._evt.set()

    def result(self):
        self._evt.wait()
        return self._result

class Worker(Actor):
    def submit(self, func, *args, **kwargs):
        r = Result()
        self.send((func, args, kwargs, r))
        return r

    def run(self):
        while True:
            func, args, kwargs, r = self.recv()
            r.set_result(func(*args, **kwargs))

worker = Worker()
worker.start()
r = worker.submit(pow, 2, 3)
worker.close()
worker.join()
print(r.result())
```

### 实现消息发布/订阅模型

```python
from contextlib import contextmanager
from collections import defaultdict

class Exchange:
    def __init__(self):
        self._subscribers = set()

    def attach(self, task):
        self._subscribers.add(task)

    def detach(self, task):
        self._subscribers.remove(task)

    @contextmanager
    def subscribe(self, *tasks):
        for task in tasks:
            self.attach(task)
        try:
            yield
        finally:
            for task in tasks:
                self.detach(task)

    def send(self, msg):
        for subscriber in self._subscribers:
            subscriber.send(msg)

# Dictionary of all created exchanges
_exchanges = defaultdict(Exchange)

# Return the Exchange instance associated with a given name
def get_exchange(name):
    return _exchanges[name]

# Example of using the subscribe() method
exc = get_exchange('name')
with exc.subscribe(task_a, task_b):
     ...
     exc.send('msg1')
     exc.send('msg2')
     ...

# task_a and task_b detached here
```

### 生成器代替线程

```python
# Two simple generator functions
def countdown(n):
    while n > 0:
        print('T-minus', n)
        yield
        n -= 1
    print('Blastoff!')

def countup(n):
    x = 0
    while x < n:
        print('Counting up', x)
        yield
        x += 1
```

```python
from collections import deque

class TaskScheduler:
    def __init__(self):
        self._task_queue = deque()

    def new_task(self, task):
        """
        Admit a newly started task to the scheduler
        """
        self._task_queue.append(task)

    def run(self):
        """
        Run until there are no more tasks
        """
        while self._task_queue:
            task = self._task_queue.popleft()
            try:
                # Run until the next yield statement
                next(task)
                self._task_queue.append(task)
            except StopIteration:
                # Generator is no longer executing
                pass

# Example use
sched = TaskScheduler()
sched.new_task(countdown(10))
sched.new_task(countdown(5))
sched.new_task(countup(15))
sched.run()
```

```
T-minus 10
T-minus 5
Counting up 0
T-minus 9
T-minus 4
Counting up 1
T-minus 8
T-minus 3
Counting up 2
T-minus 7
T-minus 2
...
```
可以看到某个任务执行一次CPU时间片之后，就必须到队列末尾进行排队。任务是按批（3个任务为一次批处理）执行的。

原书中给出了一个更加高级的例子，演示了使用生成器来实现一个并发网络应用程序。这里省略。

### 多个线程队列轮询

```python
import queue
import socket
import os

class PollableQueue(queue.Queue):
    def __init__(self):
        super().__init__()
        # Create a pair of connected sockets
        if os.name == 'posix':
            self._putsocket, self._getsocket = socket.socketpair()
        else:
            # Compatibility on non-POSIX systems
            server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            server.bind(('127.0.0.1', 0))
            server.listen(1)
            self._putsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self._putsocket.connect(server.getsockname())
            self._getsocket, _ = server.accept()
            server.close()

    def fileno(self):
        return self._getsocket.fileno()

    def put(self, item):
        super().put(item)
        self._putsocket.send(b'x')

    def get(self):
        self._getsocket.recv(1)
        return super().get()
```

使用例子

```python
import select
import threading

def consumer(queues):
    '''
    Consumer that reads data on multiple queues simultaneously
    '''
    while True:
        can_read, _, _ = select.select(queues,[],[])
        for r in can_read:
            item = r.get()
            print('Got:', item)

q1 = PollableQueue()
q2 = PollableQueue()
q3 = PollableQueue()
t = threading.Thread(target=consumer, args=([q1,q2,q3],))
t.daemon = True
t.start()

# Feed data to the queues
q1.put(1)
q2.put(10)
q3.put('hello')
q2.put(15)
...
```

可行的做法

```python
import select

def event_loop(sockets, queues):
    while True:
        # polling with a timeout
        can_read, _, _ = select.select(sockets, [], [], 0.01)
        for r in can_read:
            handle_read(r)
        #可选，顺便轮询queues    
        for q in queues:
            if not q.empty():
                item = q.get()
                print('Got:', item)
```

### 在Unix系统启动守护进程

```python
#!/usr/bin/env python3
# daemon.py

import os
import sys

import atexit
import signal

def daemonize(pidfile, *, stdin='/dev/null',
                          stdout='/dev/null',
                          stderr='/dev/null'):

    if os.path.exists(pidfile):
        raise RuntimeError('Already running')

    # First fork (detaches from parent)
    try:
        if os.fork() > 0:
            raise SystemExit(0)   # Parent exit
    except OSError as e:
        raise RuntimeError('fork #1 failed.')

    os.chdir('/')
    os.umask(0)
    os.setsid()
    # Second fork (relinquish session leadership)
    try:
        if os.fork() > 0:
            raise SystemExit(0)
    except OSError as e:
        raise RuntimeError('fork #2 failed.')

    # Flush I/O buffers
    sys.stdout.flush()
    sys.stderr.flush()

    # Replace file descriptors for stdin, stdout, and stderr
    with open(stdin, 'rb', 0) as f:
        os.dup2(f.fileno(), sys.stdin.fileno())
    with open(stdout, 'ab', 0) as f:
        os.dup2(f.fileno(), sys.stdout.fileno())
    with open(stderr, 'ab', 0) as f:
        os.dup2(f.fileno(), sys.stderr.fileno())

    # Write the PID file
    with open(pidfile,'w') as f:
        print(os.getpid(),file=f)

    # Arrange to have the PID file removed on exit/signal
    atexit.register(lambda: os.remove(pidfile))

    # Signal handler for termination (required)
    def sigterm_handler(signo, frame):
        raise SystemExit(1)

    signal.signal(signal.SIGTERM, sigterm_handler)

def main():
    import time
    sys.stdout.write('Daemon started with pid {}\n'.format(os.getpid()))
    while True:
        sys.stdout.write('Daemon Alive! {}\n'.format(time.ctime()))
        time.sleep(10)

if __name__ == '__main__':
    PIDFILE = '/tmp/daemon.pid'

    if len(sys.argv) != 2:
        print('Usage: {} [start|stop]'.format(sys.argv[0]), file=sys.stderr)
        raise SystemExit(1)

    if sys.argv[1] == 'start':
        try:
            daemonize(PIDFILE,
                      stdout='/tmp/daemon.log',
                      stderr='/tmp/dameon.log')
        except RuntimeError as e:
            print(e, file=sys.stderr)
            raise SystemExit(1)

        main()

    elif sys.argv[1] == 'stop':
        if os.path.exists(PIDFILE):
            with open(PIDFILE) as f:
                os.kill(int(f.read()), signal.SIGTERM)
        else:
            print('Not running', file=sys.stderr)
            raise SystemExit(1)

    else:
        print('Unknown command {!r}'.format(sys.argv[1]), file=sys.stderr)
        raise SystemExit(1)
```

要启动这个守护进程，用户需要使用如下的命令：

```bash
bash % daemon.py start
bash % cat /tmp/daemon.pid
2882
bash % tail -f /tmp/daemon.log
Daemon started with pid 2882
Daemon Alive! Fri Oct 12 13:45:37 2012
Daemon Alive! Fri Oct 12 13:45:47 2012
...
```

## 脚本编程与系统管理

### 执行外部程序并获取结果
执行一个外部命令并以Python字符串的形式获取执行结果

```python
import subprocess
out_bytes = subprocess.check_output(['netstat','-a'])
```

使用 `check_output()` 函数是执行外部命令并获取其返回值的最简单方式。 
但是，如果你需要对子进程做更复杂的交互，比如给它发送输入，你得采用另外一种方法。这时候可直接使用 `subprocess.Popen` 类。

### 实用工具库

- `shutil` 模块有很多便捷的函数可以复制文件和目录。
- `configparser` 模块能被用来读取配置文件.ini。

### 实现一个计时器

```python
import time

class Timer:
    def __init__(self, func=time.perf_counter):
        self.elapsed = 0.0
        self._func = func
        self._start = None

    def start(self):
        if self._start is not None:
            raise RuntimeError('Already started')
        self._start = self._func()

    def stop(self):
        if self._start is None:
            raise RuntimeError('Not started')
        end = self._func()
        self.elapsed += end - self._start
        self._start = None

    def reset(self):
        self.elapsed = 0.0

    @property
    def running(self):
        return self._start is not None

    def __enter__(self):
        self.start()
        return self

    def __exit__(self, *args):
        self.stop()
```

上述代码中由 `Timer` 类记录的时间是钟表时间，并包含了所有休眠时间。 
如果你只想计算该进程所花费的CPU时间，应该使用 `time.process_time()` 来代替 func ：

```python
t = Timer(time.process_time)
with t:
    countdown(1000000)
print(t.elapsed)
```

### 限制内存和CPU使用量

限制CPU时间片
```python
import signal
import resource

def time_exceeded(signo, frame):
    print("Time's up!")
    raise SystemExit(1)

def set_max_runtime(seconds):
    # Install the signal handler and set a resource limit
    soft, hard = resource.getrlimit(resource.RLIMIT_CPU)
    resource.setrlimit(resource.RLIMIT_CPU, (seconds, hard))
    signal.signal(signal.SIGXCPU, time_exceeded)

if __name__ == '__main__':
    set_max_runtime(15)
    while True:
        pass
```

限制内存。RLIMIT_AS 是用于控制进程地址空间（Address Space）最大大小的资源限制常量。
```
def limit_memory(maxsize):
    soft, hard = resource.getrlimit(resource.RLIMIT_AS)
    # 设置虚拟内存上限为2GB（单位：字节）
    # maxsize = 2 * 1024**3  # 2GB
    resource.setrlimit(resource.RLIMIT_AS, (maxsize, hard))
```

## 第十四章：测试、调试和异常

### 测试stdout输出

```python
from io import StringIO
from unittest import TestCase
from unittest.mock import patch
import mymodule

class TestURLPrint(TestCase):
    def test_url_gets_to_stdout(self):
        protocol = 'http'
        host = 'www'
        domain = 'example.com'
        expected_url = '{}://{}.{}\n'.format(protocol, host, domain)

        with patch('sys.stdout', new=StringIO()) as fake_out:
            mymodule.urlprint(protocol, host, domain)
            self.assertEqual(fake_out.getvalue(), expected_url)
```
这里假设 mymodule.urlprint() 会往 sys.stdout 输出文本。

### 在单元测试中给对象打补丁

```python
# example.py
from urllib.request import urlopen
import csv

def dowprices():
    u = urlopen('http://finance.yahoo.com/d/quotes.csv?s=@^DJI&f=sl1')
    lines = (line.decode('utf-8') for line in u)
    rows = (row for row in csv.reader(lines) if len(row) == 2)
    prices = { name: float(price) for name, price in rows }
    return prices
```
测试代码
```python
import unittest
from unittest.mock import patch
import io
import example

sample_data = io.BytesIO(b'''\
"IBM",91.1\r
"AA",13.25\r
"MSFT",27.72\r
\r
''')

class Tests(unittest.TestCase):
    @patch('example.urlopen', return_value=sample_data)
    def test_dowprices(self, mock_urlopen):
        p = example.dowprices()
        self.assertTrue(mock_urlopen.called)
        self.assertEqual(p,{'IBM': 91.1,
                          'AA': 13.25,
                          'MSFT' : 27.72})

if __name__ == '__main__':
    unittest.main()
```

### 在单元测试中测试异常情况

```python
import unittest

def parse_int(s):
    return int(s)

class TestConversion(unittest.TestCase):
    def test_bad_int(self):
        self.assertRaises(ValueError, parse_int, 'N/A')
```

为了测试异常值，可以使用 `assertRaisesRegex()` 方法， 它可同时测试异常的存在以及通过正则式匹配异常的字符串表示:

```python
class TestConversion(unittest.TestCase):
    def test_bad_int(self):
        with self.assertRaisesRegex(ValueError, 'invalid literal .*'):
            r = parse_int('N/A')
```

或者使用`assertEqual()`

```python
import errno

class TestIO(unittest.TestCase):
    def test_file_not_found(self):
        try:
            f = open('/file/not/found')
        except IOError as e:
            self.assertEqual(e.errno, errno.ENOENT)
        else:
            self.fail('IOError not raised')
```

### 将测试输出用日志记录到文件

```python
import sys

def main(out=sys.stderr, verbosity=2):
    loader = unittest.TestLoader()
    suite = loader.loadTestsFromModule(sys.modules[__name__])
    unittest.TextTestRunner(out,verbosity=verbosity).run(suite)

if __name__ == '__main__':
    with open('testing.out', 'w') as f:
        main(f)
```

### 跳过某些测试

```python
import unittest
import os
import platform

class Tests(unittest.TestCase):
    def test_0(self):
        self.assertTrue(True)

    @unittest.skip('skipped test')
    def test_1(self):
        self.fail('should have failed!')

    @unittest.skipIf(os.name=='posix', 'Not supported on Unix')
    def test_2(self):
        import winreg

    @unittest.skipUnless(platform.system() == 'Darwin', 'Mac specific test')
    def test_3(self):
        self.assertTrue(True)

    @unittest.expectedFailure
    def test_4(self):
        self.assertEqual(2+2, 5)

if __name__ == '__main__':
    unittest.main()
```

### 自定义异常

自定义异常类应该总是继承自内置的 `Exception` 类， 或者是继承自那些本身就是从 `Exception` 继承而来的类。 
尽管所有类同时也继承自 `BaseException` ，但你不应该使用这个基类来定义新的异常。
`BaseException` 是为系统退出异常而保留的，比如 `KeyboardInterrupt` 或 `SystemExit` 以及其他那些会给应用发送信号而退出的异常。

```python
class CustomError(Exception):
    def __init__(self, message, status):
        super().__init__(message, status)
        self.message = message
        self.status = status
```

Exception的默认行为是接受所有传递的参数并将它们以元组形式存储在 `.args` 属性中。
很多其他函数库和部分Python库默认所有异常都必须有 `.args` 属性，因此如果你忽略了这一步，你会发现有些时候你定义的新异常不会按照期望运行。

### 捕获异常后抛出另外的异常

```bash
>>> def example():
...     try:
...             int('N/A')
...     except ValueError as e:
...             raise RuntimeError('A parsing error occurred') from e
```

使用raise from 维护完整的异常链。 如果，你想忽略掉异常链，可使用 `raise from None`:

```bash
>>> def example3():
...     try:
...         int('N/A')
...     except ValueError:
...         raise RuntimeError('A parsing error occurred') from None
```

### 重新抛出异常

```python
try:
   ...
except Exception as e:
   # Process exception information in some way
   ...

   # Propagate the exception
   raise
```

### 输出警告信息

```python
import warnings

def func(x, y, logfile=None, debug=False):
    if logfile is not None:
         warnings.warn('logfile argument deprecated', DeprecationWarning)
    ...
```

`warn()` 的参数是一个警告消息和一个警告类，警告类有如下几种：UserWarning, DeprecationWarning, SyntaxWarning, RuntimeWarning, ResourceWarning, 或 FutureWarning。

### 性能测试

用修饰器修饰函数

```python
# timethis.py

import time
from functools import wraps

def timethis(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        start = time.perf_counter()
        r = func(*args, **kwargs)
        end = time.perf_counter()
        print('{}.{} : {}'.format(func.__module__, func.__name__, end - start))
        return r
    return wrapper
```

修饰代码块

```python
from contextlib import contextmanager

@contextmanager
def timeblock(label):
    start = time.perf_counter()
    try:
        yield
    finally:
        end = time.perf_counter()
        print('{} : {}'.format(label, end - start))
```

下面是使用这个上下文管理器的例子：

```python
>>> with timeblock('counting'):
...     n = 10000000
...     while n > 0:
...             n -= 1
...
counting : 1.5551159381866455
>>>
```

或者使用timeit模块。

注意： 当执行性能测试的时候，需要注意的是你获取的结果都是近似值。`time.perf_counter()` 函数会在给定平台上获取最高精度的计时值。
不过，它仍然还是基于时钟时间，很多因素会影响到它的精确度，比如机器负载。如果你对于执行时间更感兴趣，使用 `time.process_time()` 来代替它。

## 第十五章：C语言扩展

使用ctypes访问C代码