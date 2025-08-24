# Python 类型提示

本文是《Mastering Advanced Python Typing Unlock the Secrets of Expert-Level Skills》一书的精简笔记。
此书信息密度过低，内容重复度极高，阅读体验并不流畅，3 分。

## 入门

### 优势: 利于复杂的分析

```python
def process_value(val: int | float) -> float:
    if isinstance(val, int):
        return float(val)
    return val
```

内部实现严格分离了对 int 或 float 入参的处理，保证了返回值一定是 float 类型。

### forward reference

```python
from __future__ import annotations


class TreeNode:
    def __init__(self, value: int, left: TreeNode | None = None, right: TreeNode | None = None):
        self.value = value
        self.left = left
        self.right = right
```

TreeNode 的定义。

### 字面量

```python
from typing import Literal


def move(direction: Literal['up', 'down', 'left', 'right']) -> None:
    # Logic to move a game character
    ...
```

这里字面量 direction 类似于枚举。

### 协议 Protocol

```python
from typing import Protocol


class SupportsClose(Protocol):
    def close(self) -> None: ...


def shutdown(resource: SupportsClose) -> None:
    resource.close()
```

协议 SupportsClose 定义了一个契约，任何符合该协议的类型必须实现 close 方法。

### NewType 领域特定类型

```python
from typing import NewType

UserID = NewType('UserID', int)


def get_user(user_id: UserID) -> dict[str, any]:
    # Database logic returning user information
    ...
```

这里定义了 UserID 类型，本质上是 int，但是语义上更加明确。

### TypeVar/Generic 泛型类型

```python
from __future__ import annotations

from typing import TypeVar, Generic, List

T = TypeVar('T')


class Stack(Generic[T]):
    def __init__(self) -> None:
        self._items: List[T] = []

    def push(self, item: T) -> None:
        self._items.append(item)

    def pop(self) -> T:
        return self._items.pop()

    def process_stack(stack: Stack[int]) -> int:
        stack.push(42)
        # stack.push('123')
        return stack.pop()
```

### 实施过程

先注解入参和返回类型，将错误限制在良好定义的边界。

下面以注释一格函数为例说明：

```python
import functools
import inspect
import typing


def enforce_types(func):
    sig = inspect.signature(func)
    hints = typing.get_type_hints(func)

    @functools.wraps(func)
    def wrapper(*args, **kwargs):
        bound = sig.bind(*args, **kwargs)
        bound.apply_defaults()
        for name, value in bound.arguments.items():
            if name in hints and not isinstance(value, hints[name]):
                raise TypeError(f"Argument '{name}' must be {hints[name]}, got {value}.")
            result = func(*args, **kwargs)
            if 'return' in hints and not isinstance(result, hints['return']):
                raise TypeError(f"Return value must be {hints['return']}, got {type}")
            return result

    return wrapper


@enforce_types
def compute_factorial(n: int) -> int:
    if n < 0:
        raise ValueError("Negative numbers are not allowed")

    result = 1
    for i in range(1, n + 1):
        result *= i
    return result


compute_factorial('foo')
# TypeError: Argument 'n' must be <class 'int'>, got foo.
```

@enforce_types 是在函数运行时进行定义类型和实际入参类型的对比，不匹配就抛出异常。

## 深入静态和动态类型

### TypedDict

```python
from typing import TypedDict, Literal


# TypedDict
# A simple typed namespace. At runtime, it is equivalent to a plain dict

class Config(TypedDict):
    mode: Literal['development', 'production']
    retries: int


def load_config(data: Config) -> None:
    if data['mode'] not in ('development', 'production'):
        raise ValueError("Invalid configuration mode")
    # Process configuration
```

静态类型检查器可以验证字典是否符合指定的模式.

TypedDict 还提供了处理可选键的灵活性

```python
from typing import TypedDict, Optional


class PartialUserProfile(TypedDict, total=False):
    username: str
    email: str
    age: int
    is_active: bool
    phone: Optional[str]


def update_profile(profile: PartialUserProfile) -> None:
    # The key 'phone' might not be present; type checkers are informed of its
    phone = profile.get('phone')
    if phone is not None:
        print(f"Updating contact phone to {phone}")
```

### 在运行时用额外信息注释目标函数

```python
def attach_metadata(**metadata):
    def decorator(func):
        for key, value in metadata.items():
            setattr(func, key, value)
        return func

    return decorator


@attach_metadata(author="Advanced Programmer", version="2.1")
def complex_computation(x, y):
    return x ** y


print(complex_computation.author)
print(complex_computation.version)
```

### method overload

```python
from typing import overload


@overload
def process(value: int) -> int:
    ...


@overload
def process(value: str) -> str:
    ...


def process(value):
    if isinstance(value, int):
        return value * 2
    elif isinstance(value, str):
        return value + value
    else:
        raise TypeError("Unsupported type")
```

### container types

```python
from typing import TypeVar, Iterable, List

T = TypeVar('T')


def to_list(iterable: Iterable[T]) -> List[T]:
    return list(iterable)


# This function correctly infers types based on its input.
ints = to_list([1, 2, 3])
strs = to_list(("a", "b", "c"))
```

### 既是文档，也是代码契约

```python
from typing import Protocol


class Serializer(Protocol):
    def serialize(self, obj: object) -> str:
        ...


class JsonSerializer:
    @staticmethod
    def serialize(obj: object) -> str:
        import json
        return json.dumps(obj)


class XmlSerializer:
    @staticmethod
    def serialize(obj: object) -> str:
        # Imagine an XML serialization implementation here.
        return "<xml></xml>"


def process_serialization(serializer: Serializer, data: object) -> str:
    return serializer.serialize(data)
```

### 确保每个动态加载的模块符合预期的接口

```python
from typing import Protocol, runtime_checkable


@runtime_checkable
class PluginInterface(Protocol):
    def execute(self, **kwargs) -> None:
        ...


def load_plugin(plugin_name: str) -> PluginInterface:
    import importlib
    module = importlib.import_module(plugin_name)
    plugin_class = getattr(module, "Plugin")
    plugin_instance = plugin_class()
    if not isinstance(plugin_instance, PluginInterface):
        raise TypeError("Loaded plugin does not conform to PluginInterface")
    return plugin_instance


# Usage: dynamically load and execute a plugin.
plugin = load_plugin("my_custom_plugin")
plugin.execute(config="example")
```

## 类型变量和泛型

### 类型变量细化

```python
U = TypeVar('U', bound=int)


def increment(value: U) -> U:
    return value + 1
```

一个更加复杂的例子

```python
from typing import Protocol, TypeVar


class SizedIterable(Protocol):
    def __iter__(self) -> object: ...

    def __len__(self) -> int: ...


T_iterable = TypeVar('T_iterable', bound=SizedIterable)


def process_collection(collection: T_iterable) -> int:
    # Performs an algorithm that relies on both iteration and size computation
    count = len(collection)
    for _ in collection:
        count -= 1
    return count
```

### Forward Reference 前向引用

```
from typing import Optional, Protocol, TypeVar, Generic


class Comparable(Protocol):
    def __lt__(self: 'Comparable', other: 'Comparable') -> bool:
        ...


TComparable = TypeVar('TComparable', bound=Comparable)
```

'Comparable' 是一个字符串前向引用，表示 "当前 Comparable 类"。本质上为延迟类型解析，避免循环依赖错误。

| 版本	          | 写法                                                    |
|--------------|-------------------------------------------------------|
| Python 3.6-  | 	必须用字符串前向引用（'ClassName'）                              |
| Python 3.7+	 | 可用 from `__future__` import annotations 直接写 ClassName |
| 通用写法	        | 字符串形式兼容所有版本                                           |

### 协议组合

```python
from typing import Protocol


class Serializable(Protocol):
    def serialize(self) -> str:
        ...


class Comparable(Protocol):
    def __lt__(self: 'Comparable', other: 'Comparable') -> bool:
        ...


class SerializableComparable(Serializable, Comparable, Protocol):
    ...
```

现在就能够使用这个组合协议

```python
SC = TypeVar('SC', bound=SerializableComparable)


def process_item(item: SC) -> str:
    # Processing involves both serializing and comparing
    serialized = item.serialize()
    # Further logic may involve comparisons with other items
    return serialized
```

### 协变和逆变

在 TypeVar 中使用 covariant=True 标志，将类型变量声明为协变。

```
from typing import TypeVar, Generic, Sequence

T_co = TypeVar('T_co', covariant=True)


class ReadOnlyCollection(Generic[T_co]):
    def __init__(self, items: Sequence[T_co]) -> None:
        self._items = list(items)

    def __iter__(self):
        return iter(self._items)

    def __getitem__(self, index: int) -> T_co:
        return self._items[index]

```

在上面的代码片段中，T_co 是协变的，因此如果 Cat 是 Animal 的子类型，那么
ReadOnlyCollection[Cat] 自动成为 ReadOnlyCollection[Animal] 的子类型。
协变对于提供源自泛型类型的输出值而不允许变更的 API 至关重要，从而保持类型的完整性。


---

> Contravariance, on the other hand, reverses the subtyping relationship. If type A is a
> subtype of type B, then a contravariant generic type Processor[B] would be a subtype of
> Processor[A].

```python
from typing import TypeVar, Generic

T_contra = TypeVar('T_contra', contravariant=True)


class Processor(Generic[T_contra]):
    def process(self, value: T_contra) -> None:
        raise NotImplementedError


def execute_processing(processor: Processor[object], value: object) -> None:
    processor.process(value)
```

在这个设计中，如果 Dog 被视为 Animal 的子类型，那么 Processor[Animal] 就是
Processor[Dog] 的子类型。 逆变有效地扩展了接受类型的范围，允许更多通用的输入处理程序。

---

```python
from typing import Callable, List, TypeVar, Generic

TEvent_co = TypeVar('TEvent_co', covariant=True)
THandler_contra = TypeVar('THandler_contra', contravariant=True)


class Event(Generic[TEvent_co]):
    def __init__(self, payload: TEvent_co) -> None:
        self.payload = payload


class EventDispatcher(Generic[THandler_contra]):
    def __init__(self) -> None:
        self._handlers: List[Callable[[THandler_contra], None]] = []

    def register_handler(self, handler: Callable[[THandler_contra], None]) -> None:
        self._handlers.append(handler)

    def dispatch(self, event: Event[THandler_contra]) -> None:
        for handler in self._handlers:
            handler(event.payload)
```

再看一个关于数据库操作的例子。

```python
from typing import Generic, TypeVar

Entity = TypeVar('Entity', covariant=True)
CommandEntity = TypeVar('CommandEntity', contravariant=True)


class QueryRepository(Generic[Entity]):
    def get(self, id: int) -> Entity:
        raise NotImplementedError


class CommandRepository(Generic[CommandEntity]):
    def update(self, entity: CommandEntity) -> None:
        raise NotImplementedError
```

- -> Entity: 协变，可以返回比 Entity 更加具体的子类。
- entity: CommandEntity：逆变，可以接受比 CommandEntity 更加宽泛的超类型作为输入参数。

## 高级类型提示技术

### type aliases

```python
from typing import Dict, Union

ConfigValue = Union[str, int, float, bool]
Configuration = Dict[str, ConfigValue]


def load_configuration() -> Configuration:
    # Implementation that loads configuration data
    return {"timeout": 30, "verbose": True, "hostname": "localhost"}
```

### typeGuard 类型收窄

```python
from typing import List, Any, Union
from typing_extensions import TypeGuard


def is_non_empty_str_list(lst: List[Any]) -> TypeGuard[List[str]]:
    return all(isinstance(item, str) and item for item in lst)


def process_list(lst: List[Any]) -> None:
    if is_non_empty_str_list(lst):
        # The type checker now treats 'lst' as List[str]
        for item in lst:
            print(item.upper())
    else:
        print("List does not consist solely of non-empty strings.")
```

- is_non_empty_str_list 如果返回 True，说明 `lst: List[Any]` 被收窄为 `List[str]` 类型，那么下面的
  for 循环，可以放心调用 item.upper() 。

### Final 强制执行不变性

```python
from typing_extensions import Final

PI: Final[float] = 3.14159
```

Final 适合那些数学常量的定义。

### 接受可变数量的位置和关键字参数

ParamSpec 是在 PEP 612 中引入的。

```python
from typing import Callable, TypeVar
from typing_extensions import ParamSpec

P = ParamSpec('P')
R = TypeVar('R')


def trace(func: Callable[P, R]) -> Callable[P, R]:
    def wrapper(*args: P.args, **kwargs: P.kwargs) -> R:
        result = func(*args, **kwargs)
        return result

    return wrapper


@trace
def multiply(a: int, b: int) -> int:
    return a * b


# The signature of 'multiply' is preserved, enabling full type checking.
result = multiply(3, 4)
```

ParamSpec 与 Concatenate 的结合允许通过附加参数来增强现有的参数列表。这在修饰器中非常常见。

```python
from typing import Callable, TypeVar

from typing_extensions import Concatenate, ParamSpec

P = ParamSpec('P')
R = TypeVar('R')


def add_logging(func: Callable[Concatenate[str, P], R]) -> Callable[P, R]:
    def wrapper(*args: P.args, **kwargs: P.kwargs) -> R:
        return func("LOG", *args, **kwargs)

    return wrapper


@add_logging
def process_data(prefix: str, data: list[int]) -> int:
    return sum(data)


# When 'process_data' is called, the first parameter is implicitly handled.
result = process_data([10, 20, 30])
```

## 使用协议进行结构子类型划分

### overload

```python
from typing import Protocol, overload, runtime_checkable, Any
import asyncio


@runtime_checkable
class AsyncHandler(Protocol):
    @overload
    async def handle(self, task_id: int) -> str:
        ...

    @overload
    async def handle(self, task_id: str) -> str:
        ...

    async def handle(self, task_id: Any) -> str:
        if isinstance(task_id, int):
            await asyncio.sleep(0.1)
            return f"Task {task_id} processed asynchronously"
        elif isinstance(task_id, str):
            await asyncio.sleep(0.2)
            return f"Task {task_id} processed with custom key"
        else:
            raise TypeError("Unsupported task identifier")
```

## 元编程

### 注入动态代码

```python
import functools
import inspect


def generate_helpers(fn):
    signature = inspect.signature(fn)

    @functools.wraps(fn)
    def wrapper(*args, **kwargs):
        # Pre-processing: generate helper functionality based on annotations.
        helpers = {}
        for name, parameter in signature.parameters.items():
            if parameter.annotation is int:
                helpers[name + "_helper"] = lambda x: x * 2  # Simplistic help
            # Insert helpers into function globals to be accessible during executi
            fn_globals = fn.__globals__
            fn_globals.update(helpers)
        return fn(*args, **kwargs)

    return wrapper


@generate_helpers
def compute(a: int, b: int) -> int:
    # The helper 'a_helper' is dynamically injected
    a_modified = a_helper(a)
    return a_modified + b


dynamic_result = compute(5, 3)
print(dynamic_result)
# 13
```

### 强制类属性初始化

```python
class TypedMeta(type):
    def __new__(cls, name, bases, namespace):
        annotations = namespace.get('__annotations__', {})
        for attr, typ in annotations.items():
            if attr not in namespace:
                raise TypeError(f"Missing initialization for annotated attribute {attr}.")
        new_class = super().__new__(cls, name, bases, namespace)
        return new_class


class Base(metaclass=TypedMeta):
    pass


class Example(Base):
    __annotations__ = {
        'value': int,
        'description': str,
    }
    value = 0
    description = "Default"


# The following class definition would raise a TypeError if an annotated attr is not initialized
class IncompleteExample(Base):
    __annotations__ = {'data': float}
    # data = None  # data must be defined appropriately
```

### augment_annotations

```python
def augment_annotations(cls, new_annotations: dict):
    if not hasattr(cls, '__annotations__'):
        cls.__annotations__ = {}
    cls.__annotations__.update(new_annotations)
    for attr, typ in new_annotations.items():
        if not hasattr(cls, attr):
            setattr(cls, attr, None)
    return cls


class BaseRecord:
    __annotations__ = {'id': int}
    id = 0


additional_types = {'created_at': str, 'updated_at': str}
augment_annotations(BaseRecord, additional_types)
print(BaseRecord.__annotations__)
```

### 反射技术实现类的自动装饰

```python
import functools
import inspect


def type_validator(fn):
    sig = inspect.signature(fn)

    @functools.wraps(fn)
    def wrapper(*args, **kwargs):
        bound = sig.bind(*args, **kwargs)

        for name, value in bound.arguments.items():
            expected = sig.parameters[name].annotation
            if expected != inspect.Parameter.empty and not isinstance(value, expected):
                raise TypeError(f"Argument '{name}' expected {expected}, got type {type(value)}")

        result = fn(*args, **kwargs)

        expected_return = sig.return_annotation
        if expected_return != inspect.Signature.empty and not isinstance(result, expected_return):
            raise TypeError(f"Return value expected {expected_return}, got {type(result)}")

        return result

    return wrapper


def apply_validator_to_class(cls):
    for name, attr in cls.__dict__.items():
        if callable(attr) and hasattr(attr, '__annotations__'):
            setattr(cls, name, type_validator(attr))
    return cls


@apply_validator_to_class
class Calculator:
    def add(self, x: int, y: int) -> int:
        return x + y


calc = Calculator()
result = calc.add(10, 20)

result = calc.add(10, '20')
# TypeError: Argument 'y' expected <class 'int'>, got type <class 'str'>
```

### AST 模块的运行时调试

```python
import ast


class TypeCheckTransformer(ast.NodeTransformer):
    def visit_FunctionDef(self, node):
        # 创建一个 print 语句节点
        msg = ast.Expr(
            value=ast.Call(
                func=ast.Name(id='print', ctx=ast.Load()),
                args=[ast.Constant(value=f"Entering function {node.name}")],  # Python 3.8+ 使用 ast.Constant
                keywords=[]
            ),
            lineno=node.lineno,  # 设置行号
            col_offset=node.col_offset  # 设置列号
        )
        node.body.insert(0, msg)
        return node


source_code = '''
def multiply(a: int, b: int) -> int:
    return a * b
'''

tree = ast.parse(source_code)
transformed_tree = TypeCheckTransformer().visit(tree)

# 需要先调用 ast.fix_missing_locations 来填充缺失的位置信息
ast.fix_missing_locations(transformed_tree)

# 临时调试	filename="<transformed_ast>"	清晰标识 AST 生成代码
# 库 / 框架开发	filename=original_path + "[ast]"	保留原始引用信息
compiled_code = compile(transformed_tree, filename="<ast>", mode="exec")
namespace = {}
exec(compiled_code, namespace)
result = namespace['multiply'](3, 4)
print(result)  # 输出: Entering function multiply\n12
```

反射提供了热交换代码组件的机制，而不需要重启应用，这种能力是高可用系统所必需的。

## 和其他类型系统的互操作性

```cpp
#include <type_traits>
#include <pybind11/pybind11.h>
namespace py = pybind11;

template<typename T>
T safeDivide(T numerator, T denominator) {
    static_assert(std::is_arithmetic<T>::value, "T must be arithmetic");
    if (denominator == 0) {
        throw std::invalid_argument("Division by zero");
    }
    return numerator / denominator;
}

PYBIND11_MODULE(math_ext, m) {
    m.def("safe_divide", &safeDivide<double>, "Divide two numbers safely",
    py::arg("numerator"), py::arg("denominator"));
}
```

### 使用外部函数接口（FFI）进行类型识别

当与静态类型语言编译的库交互时，Python 开发人员通常在 ctypes 和 cffi 之间选择。

```cpp
from cffi import FFI
ffi = FFI()

ffi.cdef("""
    typedef struct {
        int rows;
        int cols;
        double *data;
    } Matrix;
    
    Matrix* create_matrix(int rows, int cols);
    void delete_matrix(Matrix* mat);
    Matrix* multiply_matrices(const Matrix* A, const Matrix* B);
""")

C = ffi.dlopen("libmatrix.so")

def create_matrix(rows: int, cols: int):
    ptr = C.create_matrix(rows, cols)
    if ptr == ffi.NULL:
        raise MemoryError("Failed to create matrix")
    return ptr
    
def multiply(A: any, B: any):
    result = C.multiply_matrices(A, B)
    if result == ffi.NULL:
        raise RuntimeError("Matrix multiplication failed")
    return result
```

要注意内存的释放。

```python
from contextlib import contextmanager


@contextmanager
def managed_matrix(rows: int, cols: int):
    mat = create_matrix(rows, cols)
    try:
        yield mat
    finally:
        C.delete_matrix(mat)


# Usage in advanced data processing scenarios.
with managed_matrix(3, 3) as m:
    # Perform operations with m; type contract is enforced by the managed wrap
    pass
```

这种方法封装了资源管理，耦合了分配和释放防止内存泄漏的操作。

### 在序列化和反序列化中嵌入显式类型标记

```python
import json
from dataclasses import dataclass, asdict, is_dataclass
from typing import Any, Dict


@dataclass
class SuccessResponse:
    result: str


@dataclass
class ErrorResponse:
    error: str


def polymorphic_encoder(obj: Any) -> Any:
    if is_dataclass(obj):
        data = asdict(obj)
        if isinstance(obj, SuccessResponse):
            data['__type__'] = 'SuccessResponse'
        elif isinstance(obj, ErrorResponse):
            data['__type__'] = 'ErrorResponse'
        return data

    raise TypeError(f"Object of type {obj.__class__.__name__} is not JSON serializable.")


def polymorphic_decoder(data: Dict[str, Any]) -> Any:
    type_id = data.get('__type__')
    if type_id == 'SuccessResponse':
        data.pop('__type__')
        return SuccessResponse(**data)
    elif type_id == 'ErrorResponse':
        data.pop('__type__')
        return ErrorResponse(**data)
    return data


success = SuccessResponse(result="Operation completed")
json_poly = json.dumps(success, default=polymorphic_encoder)
decoded_poly = json.loads(json_poly, object_hook=polymorphic_decoder)
print(decoded_poly)
```

第三方库 pydantic 提供了基于严格类型执行的验证和序列化模型的强大解决方案。

```python
from pydantic import BaseModel, ValidationError
from typing import List


class ConfigurationModel(BaseModel):
    timeout: int
    verbose: bool
    parameters: List[float]


conf_data = {
    "timeout": 30,
    "verbose": True,
    "parameters": [1.0, 3.14, 2.71]
}

try:
    config = ConfigurationModel(**conf_data)
    json_data = config.model_dump_json()
    config_reloaded = ConfigurationModel.parse_raw(json_data)
    print(config_reloaded)
except ValidationError as e:
    print(f"Validation error: {e}")
```

### 与 TypeScript 和 JavaScript 的互操作性

- REST API
- WebSocket API
- WASM

```python
import jsonschema
from pydantic import BaseModel


class FinancialUpdate(BaseModel):
    ticker: str
    price: float
    volume: int


update_schema = FinancialUpdate.model_json_schema()


def validate_update(data: dict) -> FinancialUpdate:
    jsonschema.validate(data, update_schema)
    return FinancialUpdate(**data)
```

使用 jsonschema 库验证 TS 模型和入参 dict 匹配。

---

版本化的 JSON 编码策略

```python
from dataclasses import is_dataclass, asdict


def versioned_encoder(obj: any) -> any:
    if is_dataclass(obj):
        data = asdict(obj)
        data['__type__'] = obj.__class__.__name__
        data['version'] = 1
        return data
    raise TypeError(f"Cannot serialize object of type {obj.__class__.__name__}")


def versioned_decoder(data: dict) -> any:
    if '__type__' in data:
        version = data.get('version', 1)
        type_name = data.pop('__type__')
        if type_name == 'Configuration' and version == 1:
            return Configuration(**data)
    return data
```

## 设计模式和 typing 策略

### 命令模式

```python
from typing import Any, Protocol


class Command(Protocol):
    def execute(self, *args: Any, **kwargs: Any) -> Any: ...


class Invoker:
    def __init__(self) -> None:
        self._commands: list[Command] = []

    def register(self, command: Command) -> None:
        self._commands.append(command)

    def run(self, *args: Any, **kwargs: Any) -> None:
        for command in self._commands:
            command.execute(*args, **kwargs)


class ConcreteCommand:
    def execute(self, operand1: int, operand2: int) -> int:
        return operand1 + operand2


# Usage with static type verification:
invoker = Invoker()
invoker.register(ConcreteCommand())
result = invoker._commands[0].execute(3, 4)
print(result)
```

在此上下文中，变量参数列表（*args 和 **kwargs）的精确注释, 鼓励设计统一的命令接口，确保执行的一致性。
Python 的动态特性传统上使严格接口契约的保证变得复杂。然而，使用类型提示将这些约束转化为可验证的。

### 单例模式

使用 `__new__` 实现

```python
from __future__ import annotations
from typing import Optional, Type, TypeVar, cast
import threading

T = TypeVar('T', bound='SingletonBase')


class SingletonBase:
    _instance: Optional[SingletonBase] = None
    _lock: threading.Lock = threading.Lock()

    def __new__(cls: Type[T], *args: object, **kwargs: object) -> T:
        if cls._instance is None:
            with cls._lock:
                if cls._instance is None:
                    cls._instance = super().__new__(cls)
        return cast(T, cls._instance)


class ConfigManager(SingletonBase):
    def __init__(self) -> None:
        self.settings: dict[str, object] = {}

    def load(self, config: dict[str, object]) -> None:
        self.settings.update(config)


# Usage
config_manager1: ConfigManager = ConfigManager()
config_manager2: ConfigManager = ConfigManager()
assert config_manager1 is config_manager2
```

使用元类实现

```python
from typing import Any, Dict, Type, TypeVar, cast

T = TypeVar('T')


class SingletonMeta(type):
    _instances: Dict[Type[Any], Any] = {}

    def __call__(cls: Type[T], *args: object, **kwargs: object) -> T:
        if cls not in cls._instances:
            instance: T = super().__call__(*args, **kwargs)
            cls._instances[cls] = instance
        return cast(T, cls._instances[cls])


class Logger(metaclass=SingletonMeta):
    def __init__(self) -> None:
        self.logs: list[str] = []

    def log(self, message: str) -> None:
        self.logs.append(message)


# Usage
logger1: Logger = Logger()
logger2: Logger = Logger()
assert logger1 is logger2
```

### 工厂模式

```python
from typing import Callable, Generic, TypeVar, Protocol

T = TypeVar('T')


class Product(Protocol):
    def operation(self) -> str: ...


class ConcreteProductA:
    def operation(self) -> str:
        return "Result of ConcreteProductA"


class ConcreteProductB:
    def operation(self) -> str:
        return "Result of ConcreteProductB"


class AbstractFactory(Protocol[T]):
    def create(self) -> T: ...


class ConcreteFactoryA:
    def create(self) -> ConcreteProductA:
        return ConcreteProductA()


class ConcreteFactoryB:
    def create(self) -> ConcreteProductB:
        return ConcreteProductB()


def client(factory: AbstractFactory[T]) -> T:
    product: T = factory.create()
    result = product.operation()
    return product


# Client code with static type checking:
product_a: ConcreteProductA = client(ConcreteFactoryA())
product_b: ConcreteProductB = client(ConcreteFactoryB())
```

### 观察者模式

```python
from __future__ import annotations

from typing import Protocol, List, runtime_checkable


@runtime_checkable
class Observer(Protocol):
    def update(self, data: dict[str, object]) -> None:
        ...


class Subject:
    def __init__(self) -> None:
        self._observers: List[Observer] = []

    def attach(self, observer: Observer) -> None:
        if observer not in self._observers:
            self._observers.append(observer)

    def detach(self, observer: Observer) -> None:
        if observer in self._observers:
            self._observers.remove(observer)

    def notify(self, data: dict[str, object]) -> None:
        for observer in self._observers:
            if isinstance(observer, Observer):
                observer.update(data)


class ConcreteObserver:
    def update(self, data: dict[str, object]) -> None:
        # Process the update with explicit type assumptions.
        print(f"Observer received update: {data}")


# Example execution code with static type verification.
subject = Subject()
observer_instance = ConcreteObserver()
subject.attach(observer_instance)
subject.notify({"key": "value", "status": True})
```

### Generics 高级模式用例

```python
from __future__ import annotations

from typing import Protocol, TypeVar, Generic, List

ElementType = TypeVar('ElementType')


class GenericVisitor(Protocol, Generic[ElementType]):
    def visit(self, element: ElementType) -> None:
        ...


class Element(Protocol):
    def accept(self, visitor: GenericVisitor[Element]) -> None:
        ...


class ConcreteElement:
    def __init__(self, value: int) -> None:
        self.value: int = value

    def accept(self, visitor: GenericVisitor[ConcreteElement]) -> None:
        visitor.visit(self)


class SumVisitor(GenericVisitor[ConcreteElement]):
    def __init__(self) -> None:
        self.total: int = 0

    def visit(self, element: ConcreteElement) -> None:
        self.total += element.value


elements: List[ConcreteElement] = [ConcreteElement(1), ConcreteElement(2), ConcreteElement(3)]
visitor: SumVisitor = SumVisitor()
for element in elements:
    element.accept(visitor)
print(f"Total sum: {visitor.total}")
```

## 性能考虑和优化

### 条件类型检查

```python
import os
from typeguard import typechecked


def check_enabled():
    return os.getenv("ENABLE_RUNTIME_CHECKS", "False") == "True"


def conditional_typecheck(func):
    if check_enabled():
        return typechecked(func)
    return func


@conditional_typecheck
def process_numbers(a: int, b: int) -> int:
    return a + b
```

### 分离函数

```python
def _core_compute(x, y):
    return x + y


def compute(x: int, y: int) -> int:
    # External type validation, possibly executed only in debug mode
    assert isinstance(x, int) and isinstance(y, int), "Type error"
    return _core_compute(x, y)
```

这种分离允许关键的内部函数在不影响性能的情况下执行。

### 仅在执行静态分析时导入重模块

```python
from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from some_slow_library import ExpensiveClass


def process_instance(instance):
    # No runtime overhead from importing ExpensiveClass
    return instance.compute()
```

### 暴力移除运行时的类型标注

```python
def purge_annotations(module):
    for attr in dir(module):
        obj = getattr(module, attr)
        if hasattr(obj, "__annotations__"):
            obj.__annotations__ = {}


# Usage after module initialization
import mymodule

purge_annotations(mymodule)
```

这一做法必须谨慎使用，因为它消除了在运行时反射或调试中可能需要的类型元数据。

### profile 分析器

```python
import cProfile, pstats


def profile_function():
    # Call functions that invoke dynamic type checks or annotation resolutions
    result = calculate_sum(3, 4)
    return result


profiler = cProfile.Profile()
profiler.run('profile_function()')
stats = pstats.Stats(profiler)
stats.sort_stats('cumtime').print_stats(10)
```

### 用 pyi 存根文件隔离实现代码

使用存根文件进行类型注解进一步将类型元数据与执行的代码隔离开来。

```python
# File: control_loop.py
def update_control(input_signal, setpoint):
    # Core control logic optimized for performance
    return input_signal - setpoint


# File: control_loop.pyi
def update_control(input_signal: float, setpoint: float) -> float: ...
```

这种分离允许静态分析器和编译器在开发过程中强制执行类型契约，同时使性能关键的运行时代码免受额外的类型信息处理带来的负担。

即允许开发工具执行静态类型检查，而生产代码保持精简。

关于 stub 文件的自动生成，可以探索 pyannotate 库。

### 良好的文档

好的代码文档可以给将来的开发人员理解目前代码设计的动机。

```python
def critical_routine(data):
    """
    Critical routine for processing high-frequency data.
    Note: Type checking is disabled for performance. Data integrity
    is assumed to be validated upstream.
    """
    # Implementation optimized for speed.
    return sum(data)
```