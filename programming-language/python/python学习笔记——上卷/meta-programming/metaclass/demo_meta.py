class DemoMeta(type):

    # prepare -> new -> init

    @classmethod
    def __prepare__(cls, name, bases):
        print(f"__prepare__: {name}")
        return {"__make__": "make in DemoMeta"}  # 定制名字空间

    def __new__(cls, name, bases, attrs):
        print(f"__new__: {name}, {bases}, {attrs}")
        return type.__new__(cls, name, bases, attrs)  # 创建并返回类型对象

    def __init__(self, name, bases, attrs):
        print(f"__init__: {self}")
        return type.__init__(self, name, bases, attrs)  # 初始化后，返回类型对象

    def __call__(cls, *args, **kwargs):
        print(f"__call__: {cls}, {args}, {kwargs}")
        return type.__call__(cls, *args, **kwargs)  # 调用类型对象创建实例过程，返回实例


def demo_meta(name, bases, attrs):
    print(f"{name}, {bases}, {attrs}")
    return type(name, bases, attrs)


class X(metaclass=DemoMeta):
    data = 100

    def __init__(self, x, y): pass

    def test(self): pass


# __prepare__: X

# __new__: X, (), {'__make__': 'make in DemoMeta', '__module__': '__main__', '__qualname__': 'X',
# 'data': 100, '__init__': <function X.__init__ at 0x0000023CBCAB0040>, 'test': <function X.test at 0x0000023CBCAB0160>}

# __init__: <class '__main__.X'>

o = X(1, 2)
# __call__: <class '__main__.X'>, (1, 2), {}
