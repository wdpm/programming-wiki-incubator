# python 单例模式
单例模式（Singleton Pattern）是一种常用的软件设计模式，该模式的主要目的是确保某一个类只有一个实例存在。 主要目的在于节省计算资源。

常见场景：在创建一个config对象的时候，要获取里面的配置文件，但是其他类也需要该文件，会导致很多地方都创建实例化对象，占用内存资源，
此时可以在程序中配置只存在一个实例对象。集使用单例模式。

## python 单例实现的方式

1. 文件模块实现

```python
# file: singleton.py
class A(object):
    pass
```
```python
from singleton import A as a1
from singleton import A as a2

print(a1, id(a1))
print(a2, id(a2))
```

2. 通过 __new__ 魔术方法

```python
class singleton(object):
    def __init__(self):
        pass
    def __new__(cls, *args, **kwargs):
        if not hasattr(cls, '_instance'):
            singleton._instance = object.__new__(cls,args,kwargs)
        return singleton._instance
    
obj1 = singleton()
obj2 = singleton()
print(obj1, obj2)
```

3. 装饰器(decorator)嵌套函数

```python
def Singleton(cls, *args, **kargs):
    _instance = {}  # 创建dict
    def get_instance(*args, **kargs):
        if cls not in _instance:
            _instance[cls] = cls(*args, **kargs)
        return _instance[cls] 
    return get_instance  

@Singleton 
class Settings():
    def __init__(self):
        self.a = 'xxx'
        
s1 = Settings()
s2 = Settings()
print(s1, s2)
```

4. classmethod 类方法

这方法需要加锁，实现多线程安全问题。

```python
import time
import threading
class Singleton(object):
     _instance_lock = threading.Lock() # 锁
      
    @classmethod
    def instance(cls, *args, **kwargs):
        with Singleton._instance_lock: # 加锁
            if not hasattr(Singleton, '_instance'):
                Singleton._instance = Singleton(*args, **kwargs)
            return Singleton._instance
        
def test(arg):
    obj1 = Singleton.instance()
    obj2 = Singleton.instance()
    print(obj1, obj2)
```
