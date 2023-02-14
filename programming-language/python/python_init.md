# python __init__

情况一：子类自动调用父类的方法：子类不重写__init__()方法，实例化子类后，会自动调用父类的__init__()的方法。

情况二：子类不自动调用父类的方法：子类重写__init__()方法，实例化子类后，将不会自动调用父类的__init__()的方法。

情况三：子类重写__init__()方法又需要调用父类的方法：使用super关键词：

```python
# super(子类，self).__init__(参数1，参数2，....)

class Son(Father):
    def __init__(self, name):   
        super(Son, self).__init__(name)
```
