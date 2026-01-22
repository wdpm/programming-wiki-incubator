# python 3.9

## 新的特性

### 字典合并与更新运算符

合并 (|) 与更新 (|=) 运算符已被加入内置的 dict 类。 它们为现有的 dict.update 和 {**d1, **d2}
字典合并方法提供了补充。

也就是说，现在有至少三种方法来合并dict对象。

- |、|=
- dict.update()
- {**dict1,**dict2}

### 新增用于移除前缀和后缀的字符串方法

增加了 str.removeprefix(prefix) 和 str.removesuffix(suffix) 用于方便地从字符串移除不需要的前缀或后缀。
也增加了 bytes, bytearray 以及 collections.UserString 的对应方法。 请参阅 PEP 616 了解详情。

### 标准多项集中的类型标注泛型

在类型标注中现在你可以使用内置多项集类型例如 list 和 dict 作为通用类型而不必从 typing 导入对应的大写形式类型名 (例如 List 和
Dict)。 标准库中的其他一些类型现在同样也是通用的，例如 queue.Queue。

示例:

```python
def greet_all(names: list[str]) -> None:
  for name in names:
    print("Hello", name)
```

详见 PEP 585。

## 新的解析器

Python 3.9 使用于基于 PEG 的新解析器替代 LL(1)。 新解析器的性能与旧解析器大致相当，但 PEG 在设计新语言特性时的形式化比 LL(1)
更灵活。 我们将在 Python 3.10 及之后版本中开始使用这种灵活性。

ast 模块会使用新解析器并会生成与旧解析器一致的 AST。

## 其他语言特性修改

https://docs.python.org/zh-cn/3/whatsnew/3.9.html#other-language-changes

## 新增模块

### zoneinfo

zoneinfo 模块为标准库引入了 IANA 时区数据库。 它添加了 zoneinfo.ZoneInfo，这是一个基于系统时区数据的实体
datetime.tzinfo 实现。

### graphlib

添加了新的 graphlib 模块，其中包含 graphlib.TopologicalSorter 类来提供图的拓扑排序功能。

## 模块改进

### asyncio

> https://docs.python.org/zh-cn/3/whatsnew/3.9.html#asyncio

### concurrent.futures

将新的 cancel_futures 形参添加到 concurrent.futures.Executor.shutdown()，可以取消尚未开始运行的所有挂起的
Future，而不必等待它们完成运行再关闭执行器。

从 ThreadPoolExecutor 和 ProcessPoolExecutor 中移除了守护线程。 这改善与子解释器的兼容性及它们在关闭进程时的可预测性。

现在 ProcessPoolExecutor 中的工作进程仅会在没有可重用的空闲工作进程时按需产生。 这优化了启动开销并减少了由空闲工作进程导致的 CPU
时间损失。

### datetime

datetime.date 的 isocalendar() 以及 datetime.datetime 的 isocalendar() 等方法现在将返回
namedtuple() 而不是 tuple。

### ipaddress

ipaddress 现在支持 IPv6 作用域地址（即带有 %<scope_id> 前缀的 IPv6 地址）。

IPv6 作用域地址可使用 ipaddress.IPv6Address 来解析。 作用域的区 ID 如果存在，可通过 scope_id 属性来获取。

从 Python 3.9.5 开始 ipaddress 模块不再接受 IPv4 地址字符串中有任何前缀的零。

### multiprocessing

multiprocessing.SimpleQueue 类新增了 close() 方法用来显式地关闭队列。

### typing

PEP 593 引入了一种 typing.Annotated 类型以使用上下文专属的元数据来装饰现有类型，并将新的 include_extras 形参添加到
typing.get_type_hints() 以在运行时访问元数据。

### xml

当把 xml.etree.ElementTree 序列化为 XML 文件时属性内部的空白字符现在将被保留。 不同的行结束符不会再被正规化为 "n"。
这是对于如何解读 XML 规范 2.11 节的相关讨论的最终结果。

