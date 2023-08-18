def singleton(cls):
    inst = None

    def wrap(*args, **kwargs):
        nonlocal inst
        if not inst: inst = cls(*args, **kwargs)
        return inst

    return wrap


# 非常优雅的单例模式
@singleton
class X: pass

if __name__ == '__main__':
    print(id(X()))
    print(id(X()))
