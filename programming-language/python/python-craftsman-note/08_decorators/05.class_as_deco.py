import time
from functools import update_wrapper,wraps

# 类的函数替换。和之前普通函数区别不大，因为类自带隔离闭包来保存变量/入参。
# (1) 第一次调用：_deco = timer(print_args=True) 实际上是在初始化一个 timer 实例。
# (2) 第二次调用：func = _deco(func) 是在调用 timer 实例，触发 __call__ 方法
class timer:
    """装饰器：打印函数耗时

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

# 类的实例替换（无参修饰），构造函数保存的是func原函数定义
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


# hello()

import functools


# 类的实例替换（有参修饰）
class DelayedStart:
    """在执行被装饰函数前，等待一段时间

    :param func: 被装饰的函数
    :param duration: 需要等待的秒数
    """

    # 1.把 func 参数以外的其他参数都定义为“仅限关键字参数”，从而更好地区分原始函数与装饰器的其他参数
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
    # 2.通过 partial 构建一个新的可调用对象，这个对象接收的唯一参数是待装饰函数 func，因此可以用作装饰器
    """装饰器：推迟某个函数的执行。同时提供 .eager_call 方法立即执行"""
    return functools.partial(DelayedStart, **kwargs)


@delayed_start(duration=2)
def hello():
    print("Hello, World.")


hello()
