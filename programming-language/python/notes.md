## python __init__

- 情况一：子类不重写 __init__()方法 => 实例化子类后，会自动调用父类的 __init__() 的方法。
- 情况二：子类重写 __init__()方法，但在 __init__() 方法中不调用父类初始化的方法。=> 实例化子类后，将不会自动调用父类的 _
  _init__() 的方法。
- 情况三：子类重写 __init__() 方法又需要调用父类的方法，使用 super 关键词：

```python
class Son(Father):
    def __init__(self, name):
        # super(子类，self).__init__(参数 1，参数 2，....)
        super(Son, self).__init__(name)
```
## 其他

如果熟悉 C，那么可以直接编写动态链接库，然后用标准库自带的 ctypes 模块载入调
用。且 C 代码中无须引入 Python 类型，无须处理语言和解释器运行特征，其完全与编
写普通动态库无异。这是最简单且易维护的一种方式。

Cython 用 Python 语法编写基于 Python/C API 的标准扩展。
- 开发阶段用 setup.py 编译太过麻烦，建议用 pyximport 代替

def:
```
/*
* def add(x, y):
*   return x + y
*/
static PyObject *add(PyObject *self, PyObject *x, PyObject *y)
```

cdef:
```
/*
* cdef int c_add(int x, int y):
*   return x + y
*/
static int c_add(int x, int y)
```

cpdef:
```
/*
* cpdef int cp_add(int x, int y):
*   return x + y
*/
static int cp_add(int x, int y, ...)
static PyObject *cp_add(PyObject *self, int x, int y)
```

demo.pyx
```
cdef int _fib_cdef(int n):
    if n < 2: return n
    return _fib_cdef(n-2) + _fib_cdef(n-1)

# 因为 cdef 无法被模块外访问，所以用 def 包装一下
def fib_cdef(int n):
    return _fib_cdef(n)

cpdef fib_cpdef(int n):
    if n < 2: return n
    return fib_cpdef(n-2) + fib_cpdef(n-1)
```
需要将这个demo.pyx编译成二进制文件。=> 编写setup.py来编译（利用Cythonize）。
> [cl.exe not found]
> 在windows os，必须将 C:\Program Files (x86)\Microsoft Visual Studio\2017\Community\VC\Tools\MSVC\14.16.27023\bin\Hostx64\x64 添加到sys PATH，
> 如果系统报错路径太长，那就新建一个系统变量，以引用格式写入PATH。
> 
> [error: Microsoft Visual C++ 14.0 or greater is required. Get it with "Microsoft C++ Build Tools":]
> 解决方案：确定python版本(1933)，确定对应的MSVC版本(2022 17.3.4)，将特定的cl.exe路径加入系统变量，再次尝试。不一定能成功大。
```
py setup.py build_ext --inplace
py -c "import demo"
```

timeit简单测试
```
$ python -m timeit -s "import demo" "demo.fib(30)"
10 loops, best of 3: 152 msec per loop
$ python -m timeit -s "import demo" "demo.fib_int(30)"
10 loops, best of 3: 117 msec per loop
$ python -m timeit -s "import demo" "demo.fib_cdef(30)"
100 loops, best of 3: 5.29 msec per loop
$ python -m timeit -s "import demo" "demo.fib_cpdef(30)"
10 loops, best of 3: 29 msec per loop
```

---

method: 绑定了某个对象的function
```
>>> m = x.test.__get__(x, X)
>>> m
<bound method X.test of <X object at 0x1063e9ac8>>
>>> m.__self__, m.__func__
(<X at 0x1063e9ac8>, <function X.test>
```

---

不同于 global 运行期行为，nonlocal 要求在编译期绑定，所以目标变量须提前存在。

---

可利用链式字典设计多层次上下文（context）结构。

合理上下文类型，须具备两个基本特征。首先是继承，所有设置可被调用链的后续函数读取。
其次是修改仅针对当前和后续逻辑，不应向无关的父级传递。如此，链式字典查找次序本身
就是继承体现。而修改操作被限制在当前第一字典中，自然也不会影响父级字典的同名主键
设置。