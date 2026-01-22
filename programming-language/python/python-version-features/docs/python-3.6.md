# python 3.6

- https://docs.python.org/zh-cn/3/whatsnew/3.6.html
- https://peps.python.org/pep-0494/

## 新的语法特性

### PEP 498, 格式化字符串字面值

f-string 格式化字符串。

### PEP 515, 数字字面值中的下划线。

PEP 515 增加了在数字字面值中使用下划线的能力以改善可读性。

### PEP 526 , 变量标注语法。

PEP 484 引入了函数形参类型标注即类型提示的标准。 这个 PEP 为 Python 添加了标注变量类型的语法，包括类变量和实例变量。

### PEP 525，异步生成器。

PEP 492 将对原生协程和 async / await 语法的支持引入到 Python 3.5 中。 但 Python 3.5 实现的一个明显限制是不可能在同一函数体中 同时使用 await 和 yield。 在 Python 3.6
中此限制已被解除，这样就就能够定义异步生成器:

```python
async def ticker(delay, to):
  """Yield numbers from 0 to *to* every *delay* seconds."""
  for i in range(to):
    yield i
    await asyncio.sleep(delay)
```

### PEP 530: 异步推导式

PEP 530 添加了对在列表、集合与字典推导式和生成器表达式中使用 async for 的支持:

```python
result = [i async for i in aiter() if i % 2]
```

此外，await 表达式也在所有种类的推导式中得到支持:

```python
result = [await fun() for fun in funcs if await condition()]
```

### PEP 487: 更简单的自定义类创建

现在可以在不使用元类的情况下自定义子类的创建。当一个新的子类被创建时将在基类上调用新的 __init_subclass__ 类方法。

```python
class PluginBase:
  subclasses = []

  def __init_subclass__(cls, **kwargs):
    super().__init_subclass__(**kwargs)
    cls.subclasses.append(cls)


class Plugin1(PluginBase):
  pass


class Plugin2(PluginBase):
  pass
```

---

PEP 487 扩展了描述器协议以包括新的可选方法 __set_name__()。 当创建一个新类时，这个新方法将在定义中包括的所有描述器上被调用，为它们提供对所定义类的引用以及在类命名中间中给予描述器的名称。
换句话说，描述器的实例现在能知道描述器在所有者类中的属性名称。

```python
class IntField:
  def __get__(self, instance, owner):
    return instance.__dict__[self.name]

  def __set__(self, instance, value):
    if not isinstance(value, int):
      raise ValueError(f'expecting integer in {self.name}')
    instance.__dict__[self.name] = value

  # this is the new initializer:
  def __set_name__(self, owner, name):
    self.name = name


class Model:
  int_field = IntField()
```

### PEP 519: 添加文件系统路径协议

处理历史遗留问题：File system paths have historically been represented as `str` or `bytes` objects.

The built-in open() function has been updated to accept `os.PathLike` objects. 这个决定和python一向的duck type原则一致。

代码例子：path_protocol.py

### PEP 495: 消除本地时间的歧义

> https://docs.python.org/zh-cn/3/whatsnew/3.6.html#pep-495-local-time-disambiguation

```python
u0 = datetime(2016, 11, 6, 4, tzinfo=timezone.utc)
for i in range(4):
  u = u0 + i * HOUR
  t = u.astimezone(Eastern)
  print(u.time(), 'UTC =', t.time(), t.tzname(), t.fold)
```

```bash
04:00:00 UTC = 00:00:00 EDT 0
05:00:00 UTC = 01:00:00 EDT 0
06:00:00 UTC = 01:00:00 EST 1
07:00:00 UTC = 02:00:00 EST 0
```

fold=1时表示有歧义。

## 其他

- PEP 529: 将Windows文件系统编码更改为UTF-8
- PEP 528: 将Windows控制台编码更改为UTF-8
- PEP 520: 保留类属性定义顺序
- PEP 468: 保留关键字参数顺序

## 新的库模块

### secrets: PEP 506 -- Python 标准库增加一个密码模块。

secrets 模块用于生成高度加密的随机数，适于管理密码、账户验证、安全凭据及机密数据。

最好用 secrets 替代 random 模块的默认伪随机数生成器，该生成器适用于建模和模拟，不宜用于安全与加密。