# 用于阻止静态类的实例化，使用call进行拦截
class StaticClassMeta(type):
    def __call__(cls, *args, **kwargs):
        raise RuntimeError("can't create object for static class")


class X(metaclass=StaticClassMeta):
    @staticmethod
    def hello():
        print("hello")


try:
    X()
    # RuntimeError: can't create object for static class
except Exception as e:
    print(e)

X.hello()
