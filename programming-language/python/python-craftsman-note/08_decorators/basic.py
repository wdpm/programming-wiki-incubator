import random
import time


def timer(print_args=False):
    """装饰器：打印函数耗时

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

# @timer(print_args=True)  => _decorator = timer(print_args=True) GET decorator
# def random_sleep(): ...  => random_sleep = _decorator(random_sleep) GET wrapper

@timer(print_args=True)
def random_sleep():
    """随机睡眠一小会儿"""
    time.sleep(random.random())


random_sleep()
