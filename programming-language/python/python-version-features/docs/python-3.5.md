# python 3.5

> - https://docs.python.org/zh-cn/3/whatsnew/3.5.html
> - https://peps.python.org/pep-0478/#features-for-3-5

## 新的语法特性

### PEP 492 - "Coroutines with async and await syntax"

PEP 492 引入了 async 和 await 语法，可以创建协程，实现异步编程。

### PEP 465 - "A dedicated infix operator for matrix multiplication"

PEP 465 引入了矩阵乘法的符号（@），使矩阵乘法更简单易读。

### PEP 448 - "Additional Unpacking Generalizations"

PEP 448 允许更多的序列解包，并允许在解包时指定额外的标识符，以捕获序列中的剩余项。

## 新的库模块

### typing: PEP 484 —— 类型注解

类型注解。类似于 JavaScript 生态的 Typescript。

```python
def greeting(name: str) -> str:
  return 'Hello' + name
```

### zipapp: PEP 441 改进 Python ZIP 应用程序支持

The new zipapp module (specified in PEP 441) provides an API and command line tool for creating executable Python Zip
Applications.

With the new module, bundling your application is as simple as putting all the files, including a __main__.py file, into
a directory myapp and running:

```bash
python -m zipapp myapp
python myapp.pyz
```

更多可以参阅：https://docs.python.org/zh-cn/3/library/zipapp.html#module-zipapp

## 新的内置特性

### PEP 461, “%-formatting” for bytes and bytearray objects

百分比符号的格式化，用于 bytes 和 bytearray 对象。

## 其他

其他的发布亮点，参阅：https://docs.python.org/zh-cn/3/whatsnew/3.5.html#summary-release-highlights