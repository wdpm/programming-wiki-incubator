class DemoMeta(type):
    def __new__(meta, name, bases, attrs, **kwargs):
        print(kwargs)
        return type.__new__(meta, name, bases, attrs)

# 向元类传递参数，实现功能定制
class X(metaclass=DemoMeta, a=1, b="abc"):
    def test(self): pass


# {'a': 1, 'b': 'abc'}
