import threading


class Singleton(object):
    _instance_lock = threading.Lock()  # 锁

    @classmethod
    def instance(cls, *args, **kwargs):
        with Singleton._instance_lock:  # 加锁
            if not hasattr(Singleton, '_instance'):
                Singleton._instance = Singleton()
            return Singleton._instance


def test():
    obj1 = Singleton.instance()
    obj2 = Singleton.instance()
    print(obj1, obj2)

if __name__ == '__main__':
    test()