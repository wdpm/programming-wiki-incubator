# Python 数据模型

## 利用 @total_ordering 简化类的比较

```python
class Square:
    """正方形

    :param length: 边长
    """

    def __init__(self, length):
        self.length = length

    def area(self):
        return self.length ** 2

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.length == other.length
        return False

    def __ne__(self, other):
        return not (self == other)

    def __lt__(self, other):
        if isinstance(other, self.__class__):
            return self.length < other.length
        return NotImplemented

    def __le__(self, other):
        return self.__lt__(other) or self.__eq__(other)

    def __gt__(self, other):
        if isinstance(other, self.__class__):
            return self.length > other.length
        return NotImplemented

    def __ge__(self, other):
        return self.__gt__(other) or self.__eq__(other)
```

- 一个正方形，核心属性是边长。
- **必须实现`__eq__`** ，不等关系`__ne__`是等于结果的取反。
- **必须实现 `__lt__`**，`__le__`是 `__lt__` or `__eq__` 的结果。
- `__gt__` 可以利用 `__le__` 的取反来实现，`__ge__` 可以利用`__gt__` or `__eq__`来实现。

简化实现

```python
from functools import total_ordering


@total_ordering
class Square:
    """正方形

    :param length: 边长
    """

    def __init__(self, length):
        self.length = length

    def area(self):
        return self.length ** 2

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.length == other.length
        return False

    def __lt__(self, other):
        if isinstance(other, self.__class__):
            return self.length < other.length
        return NotImplemented
```

## 字符串表示 `__str__` `__repr__` `__format__`

```python
class Person:
    """ 人

    :param name: 姓名
    :param age: 年龄
    :param favorite_color: 最喜欢的颜色
    """

    def __init__(self, name, age, favorite_color):
        self.name = name
        self.age = age
        self.favorite_color = favorite_color

    # str 注重人类可读性，用于打印
    def __str__(self):
        return self.name

    # repr 注重数据完整性，用于 debug
    def __repr__(self):
        return '{cls_name}(name={name!r}, age={age!r}, favorite_color={color!r})'.format(
            cls_name=self.__class__.__name__,
            name=self.name,
            age=self.age,
            color=self.favorite_color,
        )

    def __format__(self, format_spec):
        """ 定义对象在字符串格式化时的行为

        :param format_spec: 需要的格式，默认为 ''
        """
        if format_spec == 'verbose':
            return f'{self.name}({self.age})[{self.favorite_color}]'
        elif format_spec == 'simple':
            return f'{self.name}({self.age})'
        return self.name


p = Person('A', 20, 'red')
print(str(p))
# A
print(repr(p))
# Person(name='A', age=20, favorite_color='red')
print(format(p, 'verbose'))
# A(20)[red]
print(format(p, 'simple'))
# A(20)
print(f'{p:simple}')
# A(20)
```

## descriptor 描述符 `__set__` `__get__` `__set_name__`

```python
class IntegerField:
    """ 整型字段，只允许一定范围的整型值

    :param min_value: 允许的最小值
    :param max_value: 允许的最大值
    """

    def __init__(self, min_value, max_value):
        self.min_value = min_value
        self.max_value = max_value

    def __set_name__(self, owner, name):
        # 将绑定属性名保存在描述符对象中, 其中 owner 参数是描述符所在的类的名称
        # 对于 age = IntegerField(...) 来说，此处的 name 就是 "age"
        self._name = name

    def __get__(self, instance, owner=None):
        # owner 的值是类定义，instance的值取决于是否为实例访问clazz().XX
        if not instance:
            return self
        return instance.__dict__[self._name]

    def __set__(self, instance, value):
        value = self._validate_value(value)
        instance.__dict__[self._name] = value

    def _validate_value(self, value):
        """校验值是否为符合要求的整数"""
        try:
            value = int(value)
        except (TypeError, ValueError):
            raise ValueError(f'{self._name} is not a valid integer!')

        if not (self.min_value <= value <= self.max_value):
            raise ValueError(
                f'{self._name} must between {self.min_value} and {self.max_value}!'
            )
        return value


class Person:
    age = IntegerField(min_value=0, max_value=150)

    def __init__(self, name, age):
        self.name = name
        self.age = age


class Rectangle:
    width = IntegerField(min_value=1, max_value=10)
    height = IntegerField(min_value=1, max_value=5)

    def __init__(self, width, height):
        self.width = width
        self.height = height


p = Person('A', 20)
p.age = 200
# ValueError: age must between 0 and 150!


r = Rectangle(1, 2)
# r.width = 12
# ValueError: width must between 1 and 10!
# r.height = 8
# ValueError: height must between 1 and 5!
```

描述符“描述”了 Python 获取与设置一个类（实例）成员的底层原理。



## 相等性 `__hash__` `__eq__`

```python
class VisitRecord:
    """ 旅客记录

    - 当两条旅客记录的姓名与电话号码相同时，判定二者相等。
    """

    def __init__(self, first_name, last_name, phone_number, date_visited):
        self.first_name = first_name
        self.last_name = last_name
        self.phone_number = phone_number
        self.date_visited = date_visited

    def __hash__(self):
        return hash(self.comparable_fields)

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.comparable_fields == other.comparable_fields
        return False

    @property
    def comparable_fields(self):
        """获取用于比较对象的字段值"""
        return (self.first_name, self.last_name, self.phone_number)


def find_potential_customers_v3():
    return set(VisitRecord(**r) for r in users_visited_puket) - set(VisitRecord(**r) for r in users_visited_nz)
```

- comparable_fields 指定了可比较的属性为一个自定义的 tuple，这里可以挑选属性。
- `__hash__` 的实现是对这部分可比较的信息 comparable_fields 计算哈希值，`__eq__` 也依赖于 comparable_fields 的值。

如果使用dataclass库，那么代码可以简化。

```python
@dataclass(frozen=True)
class VisitRecordDC:
    first_name: str
    last_name: str
    phone_number: str
    date_visited: str = field(compare=False)
```

## 长度`__len__` 和项访问 `__getitem__`

```python
class Events:
    def __init__(self, events):
        self.events = events

    def is_empty(self):
        return not bool(self.events)

    def list_events_by_range(self, start, end):
        return self.events[start:end]

    def __len__(self):
        """自定义长度，将会被用来做布尔判断"""
        return len(self.events)

    def __getitem__(self, index):
        """自定义切片方法"""
        # 直接将 slice 切片访问透传给 events 处理
        return self.events[index]


events = Events(
    [
        'computer started',
        'os launched',
        'docker started',
        'os stopped',
    ]
)

if not events.is_empty():
    print(events.list_events_by_range(1, 3))

if events:
    print(events[1:3])

# ['os launched', 'docker started']
# ['os launched', 'docker started']
```

- `if events:` 布尔判断时，背后的依据是`__len__`的结果。
- `events[1:3]` 切片访问时，`def __getitem__(self, index):` 中 index 的值是  `slice(1, 3, None)`。

