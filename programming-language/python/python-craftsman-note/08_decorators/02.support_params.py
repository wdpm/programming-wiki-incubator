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


@timer(print_args=True)
def random_sleep(a):
    """随机睡眠一小会儿"""
    time.sleep(random.random())


# @timer(print_args=True)
# def random_sleep(): ...

# step 1: _decorator = timer(print_args=True) 获得第一层内嵌，即decorator的定义，闭包此时含有参数print_args。
# step 2: random_sleep = _decorator(random_sleep) 执行decorator，即decorator(),获得第二层内嵌wrapper的定义
# step 3: 然后执行random_sleep，即random_sleep()

random_sleep(a=1)
