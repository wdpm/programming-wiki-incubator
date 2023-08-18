class A:
    def __init__(self, x):
        self.x = x

    def __getattr__(self, name):
        print(f"getattr: {name}")
        return self.__dict__.get(name)

    def __getattribute__(self, name):  # 返回结果，或引发 AttributeError
        print(f"getattribute: {name}")
        return object.__getattribute__(self, name)


o = A(1)
o.s

# getattribute: s        # __getattribute__拦截
# getattr: s             # object.__getattribute__找不到 s，触发__getattr__
# getattribute: __dict__ # __getattr__访问__dict__被再次拦截