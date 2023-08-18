# 密封类：如果一个类A使用了这个类SealedClassMeta作为元类，那么A类不能被继承
class SealedClassMeta(type):
    types = set()

    def __init__(cls, name, bases, attrs):
        print(f'cls.types: {cls.types}; set(bases): {set(bases)}')
        if cls.types & set(bases):
            raise RuntimeError("can't inherit from sealed class")
        cls.types.add(cls)


# cls.types: set(); set(bases): set()
class A(metaclass=SealedClassMeta): pass

# cls.types: {<class '__main__.A'>}; set(bases): set()
class C(metaclass=SealedClassMeta): pass

# cls.types: {<class '__main__.C'>, <class '__main__.A'>}; set(bases): {<class '__main__.A'>}
class B(A): pass
# RuntimeError: can't inherit from sealed class
