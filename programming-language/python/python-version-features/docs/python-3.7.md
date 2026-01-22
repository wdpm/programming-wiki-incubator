# python 3.7

## 新的语法特性

### PEP 563：延迟的标注求值

```python
class C:
  @classmethod
  def from_string(cls, source: str) -> C:
    ...

  def validate_b(self, obj: B) -> bool:
    ...


class B:
  ...
```

### async 和 await 现在是保留的关键字

## 新的库模块

### contextvars: PEP 567 -- 上下文变量

新的 contextvars 模块和一组 新的 C API 引入了对 上下文变量 的支持。 上下文变量在概念上类似于线程局部变量。 与 TLS 不同，上下文变量能正确地支持异步代码。

asyncio 和 decimal 已得到更新以使用和支持开箱即用的上下文变量。 特别是激活的 decimal 上下文现在将存储在上下文变量中，它允许十进制运算在异步代码中使用正确的上下文。

### dataclasses: PEP 557 -- 数据类

```python
@dataclass
class Point:
  x: float
  y: float
  z: float = 0.0


p = Point(1.5, 2.5)
print(p)  # produces "Point(x=1.5, y=2.5, z=0.0)"
```

### importlib.resources

新的 importlib.resources 模块提供了一些新的 API 和一个新的 ABC 用于访问、打开和读取包内的 资源。 资源基本上类似于包内的文件，但它们不一定是物理文件系统中实际的文件。

这个功能可以用于读取类似json、js、css等文件。

参考示例：src/3.7/my_package 以及 src/3.7/importlib_resources_demo.py 。

## 新的内置特性

### PEP 553, 新的 breakpoint() 函数

Python 3.7 包含了新的内置 breakpoint() 函数，作为一种简单方便地进入 Python 调试器的方式。

## 数据模型改进

### PEP 562, 自定义可访问的模块属性

Python 3.7 允许在模块上定义 __getattr__() 并且当以其他方式找不到某个模块属性时将会调用它。 在模块上定义 __dir__() 现在也是允许的。

一个典型的可能有用的例子是**已弃用模块属性和惰性加载**。

### PEP 560, typing 模块和泛型类型的核心支持

PEP 484 最初的设计方式使其不会向核心 CPython 解释器引入 任何 更改。 现在类型提示和 typing 模块已被社区广泛使用，因此这个限制已被取消。 这个 PEP 引入了两个特殊方法 __class_getitem__()
和 __mro_entries__，这些方法现在被 typing 中的大多数类和特殊构造所使用。

### dict 对象会保持插入时的顺序

这个特性 正式宣布 成为 Python 语言官方规范的一部分。

## 其他

其他亮点参考：https://docs.python.org/zh-cn/3/whatsnew/3.7.html#summary-release-highlights