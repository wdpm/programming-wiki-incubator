# Python OOP

## 类继承与抽象类

```python
class Validator:
    """校验器基类，校验不同种类的数据是否符合要求"""

    def validate(self, value):
        raise NotImplementedError


# 继承，多态：重写方法实现
class NumberValidator(Validator):
    """校验输入值是否是合法数字"""

    def validate(self, value):
        ...
```

```python
from abc import ABC, abstractmethod


# 利用 ABC 实现抽象类
class Validator(ABC):
    """校验器抽象类"""

    @classmethod
    def __subclasshook__(cls, C):
        """任何提供了 validate 属性的类（这里没有区分属性和方法），都被当做是 Validator 的子类"""
        if any("validate" in Base.__dict__ for Base in C.__mro__):
            return True
        return NotImplemented

    @abstractmethod
    def validate(self, value):
        raise NotImplementedError


class StringValidator:
    def validate(self, value):
        ...


print(isinstance(StringValidator(), Validator))
```

## @property 将方法标记为属性

```python
import os


class FilePath:
    def __init__(self, path):
        self.path = path

    @property
    def basename(self):
        """获取文件名"""
        return self.path.rsplit(os.sep, 1)[-1]

    @basename.setter
    def basename(self, name):
        """修改当前路径里的文件名部分"""
        new_path = self.path.rsplit(os.sep, 1)[:-1] + [name]
        self.path = os.sep.join(new_path)

    @basename.deleter
    def basename(self):
        raise RuntimeError('Can not delete basename!')


file_path = FilePath(__file__)
print(file_path.basename)

file_path.basename = 'tmp.py'
print(file_path.basename)
```

## 鸭子类型

如果你有特定的方法，那么可以等效。下面的例子展示了 read 方法。

```python
class StringList:
    """用于保存多个字符串的数据类，实现了 read() 和可迭代接口"""

    def __init__(self, strings):
        self.strings = strings

    def read(self):
        return ''.join(self.strings)

    def __iter__(self):
        for s in self.strings:
            yield s


def count_vowels(fp):
    """统计某个文件中，包含元音字母(aeiou)的数量"""
    if not hasattr(fp, 'read'):
        raise TypeError('must provide a valid file object')

    VOWELS_LETTERS = {'a', 'e', 'i', 'o', 'u'}
    count = 0
    for line in fp:
        for char in line:
            if char.lower() in VOWELS_LETTERS:
                count += 1
    return count


from io import StringIO

print(count_vowels(StringIO('Hello, world!')))
print(count_vowels(StringList('Hello, world!')))
```

## Mixin

```python
class InfoDumperMixin:
    """Mixin：输出当前实例信息"""

    def dump_info(self):
        d = self.__dict__
        print("Number of members: {}".format(len(d)))
        print("Details:")
        for key, value in d.items():
            print(f'  - {key}: {value}')


class Person(InfoDumperMixin):
    def __init__(self, name, age):
        self.name = name
        self.age = age


person = Person('bob', 20)
person.dump_info()

```

- 本质：Mixin 是继承的一种应用方式，但强调功能复用而非层级关系。方法解析顺序（MRO）遵循继承规则。
- 优势：提供可插拔的功能，不关心继承链的语义，只关注功能组合。如上面的例子只使用了 __dict__ 来打印。

## MRO 解析顺序

```python
class A:
    def say(self):
        print("I'm A")


class B(A):
    pass


class C(A):
    def say(self):
        print("I'm C")


class D(C, B):
    pass


print(D.__mro__)
# (<class '__main__.D'>, <class '__main__.B'>, <class '__main__.C'>, <class '__main__.A'>, <class 'object'>)
# D → B → C → A → object
```

经典的菱形继承关系图。

下面做一个小实验。将 class D(B,C) 改成 class D(C, B)，此时 D 先继承 C，然后继承 B

```python
class D(C, B):
    pass


print(D.__mro__)
# (<class '__main__.D'>, <class '__main__.C'>, <class '__main__.B'>, <class '__main__.A'>, <class 'object'>
# D → C → B → A → object
```

### C3 算法的 `merge` 规则

Python 计算 MRO 的公式为：

```
L(D) = [D] + merge(L(B), L(C), [B, C])
```

其中：

- `L(B)` 是 `B` 的 MRO：`[B, A, object]`
- `L(C)` 是 `C` 的 MRO：`[C, A, object]`
- `[B, C]` 是 `D` 的 ** 直接父类声明顺序 **。

`merge` 的规则是：

1. 检查 ** 第一个列表的头部 **（第一个元素）。
2. 如果该头部 ** 不在其他列表的尾部 **（即不在除第一个列表外的其他列表的后续部分），则选择它。
3. 否则，跳过并检查下一个列表的头部。
4. 重复直到所有列表为空。

### 解析 `merge` 过程

初始状态

```
待合并的列表：
- L(B): [B, A, object]
- L(C): [C, A, object]
- [B, C]: [B, C]  # D 的直接父类顺序
```

步骤 1：选择 `B`

- 检查第一个列表 `L(B)` 的头部 `B`：
    - `B` ** 不在 ** `L(C)` 的尾部（`L(C)` 的尾部是 `[A, object]`）。
    - `B` ** 不在 ** `[B, C]` 的尾部（`[B, C]` 的尾部是 `[C]`）。
- ** 合法 **，选择 `B`。
- 移除已处理的 `B`：
    - `L(B)` 变为 `[A, object]`
    - `L(C): [C, A, object]` 保持不变。
    - `[B, C]` 变为 `[C]`（因为 `B` 被移除）。

---

现在是这样：

```
- [A, object]  # L(B) 剩余
- [C, A, object]  # L(C)
- [C]  # D 的直接父类列表剩余
```

步骤 2：选择 `C`

- 现在检查第一个列表 `[A, object]` 的头部 `A`：
    - `A` ** 在 ** `L(C)` 的尾部（`[A, object]`），所以不能选 `A`。
- 跳过 `[A, object]`，检查下一个列表 `[C, A, object]` 的头部 `C`：
    - `C` ** 不在 ** `[A, object]` 的尾部。
    - `C` ** 不在 ** `[C]` 的尾部（`[C]` 的尾部是 `[]`）。
- ** 合法 **，选择 `C`。
- 移除已处理的 `C`：
    - `L(C)` 变为 `[A, object]`
    - `[C]` 变为 `[]`（因为 `C` 被移除）。

此时只剩下

```
- [A, object]  # L(B)
- [A, object]  # L(C)
- []  # D 的直接父类列表已空
```

后续步骤这里就省略了。

### 参考

- [Python 2.3 方法解析顺序 — Python 3.13.5 文档](https://docs.python.org/zh-cn/3.13/howto/mro.html)
- [原始论文 A monotonic superclass linearization for Dylan](https://dl.acm.org/doi/10.1145/236337.236343)
- [**Python 源码 **：C3 实现位于 Python 源码的 `Objects/typeobject.c`（搜索
  `mro_implementation`）。](https://github.com/python/cpython/blob/main/Objects/typeobject.c)

## 依赖注入 DI

在不使用依赖注入时，我们可能会写出下面的代码。

```python
from enum import Enum, auto


class OutputType(int, Enum):
    FILE = auto()
    REDIS = auto()
    ES = auto()


class FancyLogger:
    """日志类：支持往文件、Redis、ES 等服务输出日志"""

    _redis_max_length = 1024

    def __init__(self, output_type=OutputType.FILE):
        self.output_type = output_type
        ...

    def log(self, message):
        """打印日志"""
        if self.output_type == OutputType.FILE:
            ...
        elif self.output_type == OutputType.REDIS:
            ...
        elif self.output_type == OutputType.ES:
            ...
        else:
            raise TypeError('output type invalid')

    def pre_process(self, message):
        """预处理日志"""
        # REDIS 对日志最大长度有限制，需要进行裁剪
        if self.output_type == OutputType.REDIS:
            return message[: self._redis_max_length]
```

这种代码依赖 if-else 判断来实现多态能力，分发到不同的输出中，代码是不够简洁的，而且扩展性很差。

IoC 控制反转，有人称之为依赖注入。指的是在属性、setter 或构造函数中将需要的东西注入类实例中。

```python
from enum import Enum, auto


class OutputType(int, Enum):
    FILE = auto()
    REDIS = auto()
    ES = auto()


class FileWriter:
    def write(self, message):
        ...


class RedisWriter:
    max_length = 1024

    def _pre_process(self, message):
        # REDIS 对日志最大长度有限制，需要进行裁剪
        return message[: self.max_length]

    def write(self, message):
        message = self._pre_process(message)
        ...


class EsWriter:
    def write(self, message):
        ...


class FancyLogger:
    """日志类：支持往文件、Redis、ES 等服务输出日志"""

    # dependency inject
    def __init__(self, output_writer=None):
        self._writer = output_writer or FileWriter()
        ...

    def log(self, message):
        self._writer.write(message)
```

- 在 FancyLogger 构造函数中，output_writer 就是多态注入的入口点，代表着各种不同的 writer 实例。log() 方法实现委托给了
  writer.write() 方法。
- 这种实现方法直观简洁，将来如果有新的 writer 也容易扩展。

## __new__ 魔术方法实现单例
```python
class AppConfig:
    """程序配置类，使用单例模式"""

    _instance = None

    def __new__(cls):
        if cls._instance is None:
            inst = super().__new__(cls)
            # 已省略：从外部配置文件读取配置
            ...
            cls._instance = inst
        return cls._instance

    def get_database(self):
        """读取数据库配置"""
        ...

    def reload(self):
        """重新读取配置文件，刷新配置"""
        ...
```