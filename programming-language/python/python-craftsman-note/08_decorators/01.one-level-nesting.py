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
    """随机睡眠一小会儿"""
    time.sleep(random.random())

# @cache
# def function():
#     pass

# 等价于====>

# def function():
#     ...
# function = cache(function)

# function()

# 此时的random_sleep已经是decorated的定义了。
random_sleep()

# 这里timer修饰有个弊端，因为不能传递参数