import time


def delayed_start(func=None, *, duration=1):
    """装饰器：在执行被装饰函数前，等待一段时间

    :param duration: 需要等待的秒数
    """
    def decorator(_func):
        def wrapper(*args, **kwargs):
            print(f'Wait for {duration} second before starting...')
            time.sleep(duration)
            return _func(*args, **kwargs)

        return wrapper

    # 2.当 func 为 None 时，代表使用方提供了关键字参数，比如
    # @delayed_start(duration=2)，或者为@delayed_start() 此时返回接收单个函数参数的内层子装饰器decorator
    if func is None:
        # 不需要降低包装深度，这里获得的是函数decorator的声明
        return decorator
    # 3.当位置参数 func 不为 None 时，代表使用方没提供关键字参数，直接用了无括号的 @delayed_start 调用方式，
    # 此时返回内层包装函数 wrapper
    else:
        # print(func)
        # 需要降低包装深度，这里获得的是函数wrapper的声明
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
