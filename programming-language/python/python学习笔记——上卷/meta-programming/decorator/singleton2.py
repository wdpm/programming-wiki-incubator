class singleton(object):
    def __init__(self):
        pass

    def __new__(cls, *args, **kwargs):
        if not hasattr(cls, '_instance'):
            singleton._instance = object.__new__(cls)
        return singleton._instance


obj1 = singleton()
obj2 = singleton()
print(id(obj1), id(obj2))