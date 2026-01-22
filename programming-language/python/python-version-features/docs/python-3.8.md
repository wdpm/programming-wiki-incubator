# python 3.8

## 新的特性

### 海象运算符 (:=)

新增的语法 := 可在表达式内部为变量赋值。

在这个示例中，赋值表达式可以避免调用 len() 两次:

```python
if (n := len(a)) > 10:
  print(f"List is too long ({n} elements, expected <= 10)")
```

### PEP570: 仅限位置形参

在下面的例子中，形参 a 和 b 为仅限位置形参，c 或 d 可以是位置形参或关键字形参，而 e 或 f 要求为关键字形参:

```python
def f(a, b, /, c, d, *, e, f):
  print(a, b, c, d, e, f)
```

### 用于已编译字节码文件的并行文件系统缓存

新增的 PYTHONPYCACHEPREFIX 设置 (也可使用 -X pycache_prefix)
可将隐式的字节码缓存配置为使用单独的并行文件系统树，而不是默认的每个源代码目录下的 __pycache__ 子目录

### f- 字符串支持 = 用于自动记录表达式和调试文档

```python
user = 'eric_idle'
member_since = date(1975, 7, 31)
f'{user=} {member_since=}'
```

这个 = 号可以简化代码的编写。

### 具有外部数据缓冲区的 pickle 协议 5

当使用 pickle 在 Python 进程间传输大量数据以充分发挥多核或多机处理的优势时，非常重要一点是通过减少内存拷贝来优化传输效率，并可能应用一些定制技巧例如针对特定数据的压缩。

pickle 协议 5 引入了对于外部缓冲区的支持，这样 PEP 3118 兼容的数据可以与主 pickle 流分开进行传输，这是由通信层来确定的。

这个方向的极致是 ** 零拷贝技术 ** 和用户态 DPDK（Data Plane Development Kit）。（别卷了别卷了）

## 其他语言特性更改

https://docs.python.org/zh-cn/3/whatsnew/3.8.html#other-language-changes

## 新增模块

新增的 importlib.metadata 模块提供了从第三方包读取元数据的（临时）支持。 例如，它可以提取一个已安装软件包的版本号、入口点列表等等。

这个功能用于在 runtime 时期检测依赖信息可能会有用。

## 改进的模块

### asyncio

现在：

```python
import asyncio


async def main():
  await asyncio.sleep(0)
  return 42


asyncio.run(main())
```

之前：

```python
import asyncio


async def main():
  await asyncio.sleep(0)
  return 42


loop = asyncio.new_event_loop()
asyncio.set_event_loop(loop)
try:
  loop.run_until_complete(main())
finally:
  asyncio.set_event_loop(None)
  loop.close()
```

可见，现在的语法简化了 loop 的管理，将 loop 的创建隐式放在内部实现中。

### collections

collections.namedtuple()的 _asdict() 方法现在将返回 dict 而不是 collections.OrderedDict。 此项更改是由于普通字典自 Python 3.7 起已保证具有确定的元素顺序。
如果还需要 OrderedDict 的额外特性，建议的解决方案是将结果转换为需要的类型: OrderedDict(nt._asdict())

### functools

functools.lru_cache() 现在可直接作为装饰器而不是作为返回装饰器的函数。 因此这两种写法现在都被支持:

```python
@lru_cache
def f(x):
  ...


@lru_cache(maxsize=256)
def f(x):
```

### itertools

itertools.accumulate() 函数增加了可选的 initial 关键字参数用来指定一个初始值:

```python
from itertools import accumulate

list(accumulate([10, 5, 30, 15], initial=1000))
```

### multiprocessing

添加了新的 multiprocessing.shared_memory 模块。

### os.path

返回布尔值结果的 os.path 函数例如 exists(), lexists(), isdir(), isfile(), islink(), 以及 ismount() 现在对于包含在 OS 层级无法表示的字符或字节的路径将会返回
False 而不是引发 ValueError 或其子类 UnicodeEncodeError 和 UnicodeDecodeError。

### pathlib.Path 

同样地，返回布尔值结果的 pathlib.Path 方法例如 exists(), is_dir(), is_file(), is_mount(), is_symlink(), is_block_device(),
is_char_device(), is_fifo(), is_socket() 现在对于包含在 OS 层级无法表示的字符或字节的路径将会返回 False 而不是引发 ValueError 或其子类 UnicodeEncodeError

### shutil
shutil.copytree() 现在接受新的 dirs_exist_ok 关键字参数。


### typing
typing 模块加入了一些新特性：

- 一个带有键专属类型的字典类型。 参见 PEP 589 和 typing.TypedDict。 TypedDict 只使用字符串作为键。
  默认情况下每个键都要求提供。 指定 "total=False" 以允许键作为可选项:

```python
class Location(TypedDict, total=False):
    lat_long: tuple
    grid_square: str
    xy_coordinate: tuple
```

- Literal 类型。 参见 PEP 586 和 typing.Literal。 Literal 类型指明一个形参或返回值被限定为一个或多个特定的字面值:

```python
def get_status(port: int) -> Literal['connected', 'disconnected']:
    ...
```
- "Final" 变量、函数、方法和类。 参见 PEP 591, typing.Final 和 typing.final()。
  final 限定符会指示静态类型检查器限制进行子类化、重载或重新赋值:

```python
pi: Final[float] = 3.1415926536
```