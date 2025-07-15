# Python 修饰器

本文内容大部分来源于python 工匠一书。

## 一层嵌套：不支持参数

```python
import random
import time


def timer(func):
    """装饰器：打印函数耗时"""

    def decorated(*args, **kwargs):
        st = time.perf_counter()
        ret = func(*args, **kwargs)
        print('time cost: {} seconds'.format(time.perf_counter() - st))
        return ret

    return decorated


@timer
def random_sleep():
    time.sleep(random.random())


# @cache
# def function():
#     pass

# 等价于

# def function():
#     ...
# function = cache(function)
# function()

# 此时的 random_sleep 已经是 decorated 的定义了。
random_sleep()
```

这里 timer 修饰有个弊端，不能传递参数。timer 目前的实现中仅嵌套了一层函数。
为了让 timer 修饰支持参数调整，我们需要在内部实现中再套一层修饰。

## 两层嵌套：支持参数

```python
import random
import time


def timer(print_args=False):
    """ 装饰器：打印函数耗时

    :param print_args: 是否打印被方法名和参数，默认为 False
    """

    def decorator(func):
        def wrapper(*args, **kwargs):
            st = time.perf_counter()
            ret = func(*args, **kwargs)
            if print_args:
                print(f'"{func.__name__}", args: {args}, kwargs: {kwargs}')
            print('time cost: {} seconds'.format(time.perf_counter() - st))
            return ret

        return wrapper

    return decorator


@timer(print_args=True)
def random_sleep(a):
    """随机睡眠一小会儿"""
    time.sleep(random.random())


# @timer(print_args=True)
# def random_sleep(): ...

# step 1: _decorator = timer(print_args=True) 获得第一层内嵌，即 decorator 的定义，闭包此时记住了实参 print_args 的值。
# step 2: random_sleep = _decorator(random_sleep) 执行 decorator，即 decorator()，获得第二层内嵌 wrapper 的定义。
# step 3: 然后执行 random_sleep，即 random_sleep()

random_sleep(a=1)
# "random_sleep", args: (), kwargs: {'a': 1}
# time cost: 0.5474677999736741 seconds
```

注意，此时无法再使用 timer 这种形式，即使你不打算传参数也必须为 timer()。这里，我们已经解决了无法传参数的问题，但是还存在其他问题：
原函数属性已经丢失了。

```
print(random_sleep.__doc__)
# None
```

## 使用 @wraps(func) 来修复丢失的原函数属性

```python
import time
import random
from functools import wraps


def timer(func):
    """装饰器：记录并打印函数耗时"""

    @wraps(func)
    def decorated(*args, **kwargs):
        st = time.perf_counter()
        ret = func(*args, **kwargs)
        print('function took: {} seconds'.format(time.perf_counter() - st))
        return ret

    print('timer')
    return decorated


def calls_counter(func):
    """ 装饰器：记录函数被调用了多少次

    使用 `func.print_counter()` 可以打印统计到的信息
    """
    counter = 0

    @wraps(func)
    def decorated(*args, **kwargs):
        nonlocal counter
        counter += 1
        return func(*args, **kwargs)

    def print_counter():
        print(f'Counter: {counter}')

    decorated.print_counter = print_counter

    print('calls_counter')
    return decorated


@timer
@calls_counter
def random_sleep():
    """随机睡眠一小会"""
    time.sleep(random.random())


random_sleep()
random_sleep()
random_sleep.print_counter()
# calls_counter
# timer
# function took: 0.25683659996138886 seconds
# function took: 0.7253830999834463 seconds
# Counter: 2

# 文档属性也可以保留
print(random_sleep.__name__)
# random_sleep
print(random_sleep.__doc__)
# 随机睡眠一小会
```

- 修饰器的执行顺序是从内向外，因为 calls_counter 先打印，timer 后打印。
- 修饰过程分析：
    ```
    # random_sleep = calls_counter(random_sleep) #1
    # random_sleep = timer(random_sleep) #2
    ```
    - #1. 由 calls_counter 对函数进行包装，此时的 random_sleep 变成了新的包装函数，包含 print_counter 属性
    - #2. 使用 timer 包装后，random_sleep 变成了 timer 提供的包装函数，原包装函数额外的 print_counter 属性被自然地丢掉了。

要解决这个问题，需要在装饰器内包装函数时，保留原始函数的额外属性。functools 模块下的 wraps() 函数可以完成这件事情。
如果不使用 wraps 修饰，那么将会报错，感兴趣可以手动试下。 也就是说，wraps 可以补充被意外丢弃的函数的额外属性。

## 可选参数的实现，timer 等价于 timer()

前面我们提到了，在支持参数后，即使不想传参，也要使用 timer() 这种形式，不能再使用 timer 形式。为了使得 API
更加灵活，下面我们来兼容两种修饰模式。

```python
import time


def delayed_start(func=None, *, duration=1):
    """ 装饰器：在执行被装饰函数前，等待一段时间

    :param duration: 需要等待的秒数
    """

    def decorator(_func):
        def wrapper(*args, **kwargs):
            print(f'Wait for {duration} second before starting...')
            time.sleep(duration)
            return _func(*args, **kwargs)

        return wrapper

    # 2. 当 func 为 None 时，代表使用方提供了关键字参数，比如
    # @delayed_start(duration=2)，或者为 @delayed_start() 此时返回接收单个函数参数的内层子装饰器 decorator
    if func is None:
        # 不需要降低包装深度，这里获得的是函数 decorator 的声明
        return decorator
    # 3. 当位置参数 func 不为 None 时，代表使用方没提供关键字参数，直接用了无括号的 @delayed_start 调用方式，
    # 此时返回内层包装函数 wrapper
    else:
        # 需要降低包装深度，执行 decorator，获得更加内层的函数定义 wrapper 声明
        return decorator(func)


@delayed_start
def hello():
    print('Hello, World!')


hello()


@delayed_start(duration=3)
def hello():
    print('Hello, World!')


hello()


@delayed_start()
def hello():
    print('Hello, World!')


hello()
```

## 类实现的修饰器

类实现的装饰器可分为两种，一种是“函数替换”，另一种是“实例替换”。

### 类的函数替换（有参）

```python
import time
from functools import wraps


class timer:
    """ 装饰器：打印函数耗时
  
    :param print_args: 是否打印方法名和参数，默认为 False
    """

    def __init__(self, print_args):
        self.print_args = print_args

    def __call__(self, func):
        @wraps(func)
        def decorated(*args, **kwargs):
            st = time.perf_counter()
            ret = func(*args, **kwargs)
            if self.print_args:
                print(f'"{func.__name__}", args: {args}, kwargs: {kwargs}')
            print('time cost: {} seconds'.format(time.perf_counter() - st))
            return ret

        return decorated


@timer(print_args=True)
def hello():
    print("Hello, World.")


hello()
```

和之前普通函数区别不大，因为类自带隔离闭包来保存变量 / 入参。

- (1) 第一次调用：_deco = timer(print_args=True) 实际上是在初始化一个 timer 实例，对应 `__init__`。
- (2) 第二次调用：func = _deco(func) 是在调用 timer 实例，触发 __call__ 方法。

函数替换的关键在于：__call__ 方法将原函数 func 替换为内部函数 decorated，从而实现对原函数的包装（添加计时和参数打印功能）。

### 类的实例替换（无参）

```python
import time
from functools import update_wrapper


class DelayedStart:
    """在执行被装饰函数前，等待 1 秒钟"""

    def __init__(self, func):
        # (wrapper, wrapped)
        update_wrapper(self, func)
        self.func = func

    def __call__(self, *args, **kwargs):
        print('Wait for 1 second before starting...')
        time.sleep(1)
        return self.func(*args, **kwargs)

    def eager_call(self, *args, **kwargs):
        """跳过等待，立刻执行被装饰函数"""
        print('Call without delay')
        return self.func(*args, **kwargs)


@DelayedStart
def hello():
    print("Hello, World.")


hello()
```

这里 DelayedStart 是一个无参类装饰器：

- 实例化即装饰：
  - 当用 @DelayedStart 修饰 hello 时，DelayedStart 的 __init__ 方法会立即执行，并将原函数 hello 保存为实例属性 self.func。
  - 此时，hello 被替换为 DelayedStart 的实例对象（即 hello = DelayedStart(hello)）。
- 调用时触发 __call__：
  - 当调用 hello() 时，实际上是调用了 DelayedStart 实例的 __call__ 方法，从而插入延迟逻辑并执行原函数。

**关键点分析**

1. **实例替换函数**
   - 装饰后，`hello` 不再是原函数，而是一个 `DelayedStart` 类的实例。
2. **无参修饰器**
   - 不需要像之前的 `timer` 那样通过 `__init__` 接收额外参数，直接接收被装饰函数 `func`。
3. **`update_wrapper` 的作用**
   - 将被装饰函数 `hello` 的元信息（如 `__name__`、`__doc__`）复制到 `DelayedStart` 实例，避免因替换导致的元信息丢失。

### 对比「类的函数替换」（有参）

| 特性       | 类的实例替换（无参） | 类的函数替换（有参）           |
| :--------- | :------------------- | :----------------------------- |
| 装饰器参数 | 无（直接修饰函数）   | 有（通过 `__init__` 接收参数） |
| 被装饰对象 | 替换为类的实例       | 替换为包装函数（`decorated`）  |
| 核心逻辑   | 在 `__call__` 中实现 | 在 `__call__` 返回的函数中实现 |
| 典型用途   | 固定功能的装饰       | 参数化控制的装饰               |



### 类的实例替换（无参）

为了解决上面类的实例替换无法传参的问题，这里需要使用偏函数这个技巧。

```python
import time
from functools import update_wrapper
import functools


class DelayedStart:
    """ 在执行被装饰函数前，等待一段时间

    :param func: 被装饰的函数
    :param duration: 需要等待的秒数
    """

    # 1. 把 func 参数以外的其他参数都定义为“仅限关键字参数”，更好地区分原始函数与装饰器的其他参数
    def __init__(self, func, *, duration=1):
        update_wrapper(self, func)
        self.func = func
        self.duration = duration

    def __call__(self, *args, **kwargs):
        print(f'Wait for {self.duration} second before starting...')
        time.sleep(self.duration)
        return self.func(*args, **kwargs)

    def eager_call(self, *args, **kwargs):
        """跳过等待，立刻执行被装饰函数"""
        print('Call without delay')
        return self.func(*args, **kwargs)


def delayed_start(**kwargs):
    # 2. 返回的 partial 对象会记住 DelayedStart 目标函数 和 duration=2，等待接收 func（被装饰的函数）
    """装饰器：推迟某个函数的执行。同时提供 .eager_call 方法立即执行"""
    return functools.partial(DelayedStart, **kwargs)


@delayed_start(duration=2)
def hello():
    print("Hello, World.")


hello()
```

对偏函数的执行过程进行分析：

```
@delayed_start(duration=2)  
   ↓  
partial(DelayedStart, duration=2)  # 先记住 duration=2  
   ↓  
DelayedStart(hello, duration=2)    # 再接收 hello，构造实例  
   ↓  
hello() → 触发 __call__，延迟 2 秒 → 执行原函数  
```



## 解决类方法修饰器的兼容问题

```python
import random


def provide_number(min_num, max_num):
    """
    装饰器：随机生成一个在 [min_num, max_num] 范围的整数，
    并追加其为函数的第一个位置参数
    """

    def wrapper(func):
        def decorated(*args, **kwargs):
            num = random.randint(min_num, max_num)
            # 将 num 作为第一个参数追加后调用函数
            return func(num, *args, **kwargs)

        return decorated

    return wrapper


# pure function
@provide_number(1, 100)
def print_random_number(num):
    print(num)


class Foo:
    # class instance method
    @provide_number(1, 100)
    def print_random_number(self, num):
        print(num)

    # classmethod
    @provide_number(1, 100)
    @classmethod
    def print_random_number_v2(cls, num):
        print(num)


# 输出 1-100 的随机整数
print_random_number()

# 类实例方法，正常运行
Foo().print_random_number()

# 类方法，运行失败
# Foo.print_random_number_v2()
# TypeError: 'classmethod' object is not callable
```

为了使得类方法上的修饰器也能兼容运行，这里使用了 wrapt 工具库，并且减少了修饰器内部实现的嵌套层数。

```python
# 由于兼容类方法的问题而引入该库
import wrapt


def provide_number(min_num, max_num):
    @wrapt.decorator
    def wrapper(wrapped, instance, args, kwargs):
        # 参数含义：
        #
        # - wrapped：被装饰的函数或类方法
        # - instance：
        #   - 如果被装饰者为普通类方法，该值为类实例
        #   - 如果被装饰者为 classmethod 类方法，该值为类
        #   - 如果被装饰者为类/函数/静态方法，该值为 None
        #
        # - args：调用时的位置参数（注意没有 * 符号）
        # - kwargs：调用时的关键字参数（注意没有 ** 符号）
        #
        print(f'instance: {instance}')
        num = random.randint(min_num, max_num)
        # 无需关注 wrapped 是类方法或普通函数，直接在头部追加参数
        args = (num,) + args
        return wrapped(*args, **kwargs)

    return wrapper
```

再次运行，观察结果：

| 被修饰者   | 运行方式                     | instance参数打印                                      |
| ---------- | ---------------------------- | ----------------------------------------------------- |
| 普通函数   | print_random_number()        | instance: None                                        |
| 类实例方法 | Foo().print_random_number()  | instance: <__main__.Foo object at 0x000001CE8CF27A90> |
| 类方法     | Foo.print_random_number_v2() | instance: <class '__main__.Foo'>                      |



## 类修饰器 VS 元类

元类方式

```python
_validators = {}


class ValidatorMeta(type):
    """元类：将所有校验器类统一注册起来，方便后续使用"""

    # 注意：元类的 __new__ 方法会在创建类（声明）时被调用，而不是子类实例化时被调用。
    # (1) name：str，需要创建的类名。
    # (2) bases：Tuple[Type]，包含其他类的元组，代表类的所有基类。
    # (3) attrs：Dict[str, Any]，包含所有类成员（属性、方法）的字典
    def __new__(cls, name, bases, attrs):
        ret = super().__new__(cls, name, bases, attrs)
        _validators[attrs['name']] = ret
        return ret


class StringValidator(metaclass=ValidatorMeta):
    name = 'string'


class IntegerValidator(metaclass=ValidatorMeta):
    name = 'int'


print(_validators)
# {'string': <class '__main__.StringValidator'>, 'int': <class '__main__.IntegerValidator'>}
```

类修饰器方式

```python
_validators = {}


def register(cls):
    """装饰器：将所有校验器类统一注册起来，方便后续使用"""
    _validators[cls.name] = cls
    return cls


@register
class StringValidator:
    name = 'string'


@register
class IntegerValidator:
    name = 'int'


print(_validators)
# {'string': <class '__main__.StringValidator'>, 'int': <class '__main__.IntegerValidator'>}
```

类修饰器实现更加直观简单，容易理解。



## 区分装饰器模式

注意，修饰器不是装饰器模式，修饰器是以@decorator的形式来使用的，而装饰器模式（decorator pattern）是一种设计模式。

```python
class Numbers:
    """一个包含多个数字的简单类"""

    def __init__(self, numbers):
        self.numbers = numbers

    def get(self):
        return self.numbers


class EvenOnlyDecorator:
    """装饰器类：过滤所有偶数"""

    def __init__(self, decorated):
        self.decorated = decorated

    def get(self):
        return [num for num in self.decorated.get() if num % 2 == 0]


class GreaterThanDecorator:
    """装饰器类：过滤大于某个数"""

    def __init__(self, decorated, min_value):
        self.decorated = decorated
        self.min_value = min_value

    def get(self):
        return [num for num in self.decorated.get() if num > self.min_value]


obj = Numbers([42, 12, 13, 17, 18, 41, 32])
even_obj = EvenOnlyDecorator(obj)
print(even_obj.get())
gt_obj = GreaterThanDecorator(even_obj, min_value=30)
print(gt_obj.get())
```

上面的代码中，EvenOnlyDecorator 和 GreaterThanDecorator 都没有修改 Numbers 的实现，而是覆盖 get 方法，提供了自身逻辑语义的实现。

