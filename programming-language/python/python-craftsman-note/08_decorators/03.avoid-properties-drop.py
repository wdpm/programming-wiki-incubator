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

    return decorated


def calls_counter(func):
    """装饰器：记录函数被调用了多少次

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
    return decorated


@timer
@calls_counter
def random_sleep():
    """随机睡眠一小会"""

    time.sleep(random.random())

# 对于多个修饰，先执行最靠近的那个，然后是远处的。对于上面的例子，先是timer,然后是calls_counter

random_sleep()
random_sleep()
random_sleep.print_counter()
# AttributeError: 'function' object has no attribute 'print_counter'

# 原因分析：
# random_sleep = calls_counter(random_sleep) #1
# random_sleep = timer(random_sleep) #2

# 1，由 calls_counter 对函数进行包装，此时的 random_sleep 变成了新的包装函数，包含 print_counter 属性
# 2 使用 timer 包装后，random_sleep 变成了 timer 提供的包装函数，原包装函数额外的 print_counter 属性被自然地丢掉了
# 要解决这个问题，需要在装饰器内包装函数时，保留原始函数的额外属性。functools 模块下的 wraps() 函数可以完成这件事情。
# 将上面的@wraps(func)打开注释即可修复。

# 也就是说，wraps可以补充被意外丢弃的函数的额外属性。

# 文档属性也可以保留
print(random_sleep.__name__)
# random_sleep
print(random_sleep.__doc__)
# 随机睡眠一小会