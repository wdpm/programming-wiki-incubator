# Python 测试

## 概述

### 测试分类

- 单元测试

测试函数、方法等最小单元的测试。单元测试能明确看到输入和输出，测试内容是函数或方法的设计方案本身。
该部分要利用 mock 或 dummy，把测试对象的处理单独拿出来执行，看结果是否达到预期。

- 组件集成测试

这是集成多个函数或方法的输入输出的测试，测试时需要将多个测试对象组合在一起。由
单个测试对象构成的流程已在单元测试中测试完毕，所以不参与这一步测试。对象的前后处理
与单元测试一样要使用 mock 或 dummy。

- 功能单元测试

测试用户能看得到的功能。此时用户的输入项目以及数据库等外部系统为输入的来源。输
出则是向用户显示的结果、向数据库保存的内容以及对外部系统的调用。系统内部不使用 mock
和 dummy，而是全部使用正式的代码。不过，在对付某些异步调用之类的难以自动测试的部分
时，需要进行一定程度的置换。外部系统方面，要准备好虚拟的 SMTP 服务器或 Web API 服务
器，用以执行应用内的通信。

- 功能集成测试

集成各功能之间输入输出的测试。这里要尽可能不去直接查看数据库内的数据，比如可以
用引用类功能来显示更新类功能生成的数据。另外在与外部系统的联动方面，要借助开发专用
的 API 等模拟出正式运行时的结构，然后再进行测试。这部分测试要依赖于数据库以及外部服
务等诸多环境，难以自动执行，所以属于偏手动的测试。

- 系统测试

对需求的测试。测试成品是否最终满足了所有需求。在客户验收项目时进行。

- 非功能测试

对性能、安全等非功能方面进行的测试。借助压力测试软件进行正常 / 高峰 / 极限情况的测
试，通过 XSS、CSRF 以及注入式攻击等模拟攻击来验证系统的安全性及可靠性。

### 插件

- pytest-cov

coverage 是通用的测试覆盖率库。pytest-cov 是基于 coverage 库的插件，利用 coverage 的核心功能来收集覆盖率数据，将其集成到
pytest 测试框架中。
使用 pytest-cov 可以查看测试中都执行了哪些代码，利用它来推断哪些部分未被测试。
pytest-cov 可以通过 pip install pytest-cov 进行安装。安装完后用 --cov 选项指定要获取覆盖率的程序包。

- xunit

它和 JUnit 一样会将测试结果保存在特定格式的文件中。在与 Jenkins 等 CI 工具联动时会用
到它。它是 pytest 标配的插件，可以通过 --junit-xml 选项添加使用。

- pdb

它会在测试发生错误时自动执行 pdb（Python 的调试器）。可以通过 --pdb 选项添加使用

## 覆盖率

1. 语句覆盖：它要求被测程序的每一可执行语句在测试中尽可能都检验过；
2. 分支覆盖：要求程序中所有判定的分支尽可能得到检验；
3. 条件覆盖：当判定式中含有多个条件时，要求每个条件的取值均得到检验；

假设我们有下面一个待测试的类 calculator.py：计算器。
```python
def calculate(a, b, operation):
    """基础计算器函数（用于演示语句和分支覆盖）"""
    result = None  # 语句1

    if operation == "add":  # 判定1（分支）
        result = a + b  # 语句2
    elif operation == "subtract":  # 判定2（分支）
        result = a - b  # 语句3
    else:
        result = "Invalid operation"  # 语句4

    return result  # 语句5
```

### 语句覆盖
`test_calculator.py`
```python
import unittest
from calculator import calculate, advanced_calculate


# 1. 语句覆盖测试
class TestStatementCoverage(unittest.TestCase):
    def test_add(self):
        self.assertEqual(calculate(2, 3, "add"), 5)  # 覆盖语句1,2,5
```
注意，测试概念上，分支和语句是区分的。

### 分支覆盖
```python
# 2. 分支覆盖测试
class TestBranchCoverage(unittest.TestCase):
    def test_add(self):
        self.assertEqual(calculate(2, 3, "add"), 5)  # if分支

    def test_subtract(self):
        self.assertEqual(calculate(5, 3, "subtract"), 2)  # elif分支

    def test_invalid(self):
        self.assertEqual(calculate(1, 1, "multiply"), "Invalid operation")  # else分支
```
这里添加多个测试用例，完全覆盖了所有的if判定分支。

### 条件覆盖
在现实中，if判断分支有时会很复杂，例如混合多个boolean的计算结果（在 advanced_calculate 中体现）。
```python
def advanced_calculate(a, b, op1, op2):
    """高级计算函数（用于演示条件覆盖）"""
    if op1 == "add" and op2 == "positive":  # 条件1: op1; 条件2: op2
        return a + b if a > 0 and b > 0 else "Non-positive"  # 条件3: a>0; 条件4: b>0
    return "Default"
```
- `if op1 == "add" and op2 == "positive":`
- `if a > 0 and b > 0`

如果想要测试更加彻底和完善，那么需要覆盖所有的条件分支的组合。

```python
# 3. 条件覆盖测试
class TestConditionCoverage(unittest.TestCase):
    def test_condition1_true_true(self):
        self.assertEqual(advanced_calculate(1, 2, "add", "positive"), 3)  # T,T

    def test_condition1_false_any(self):
        self.assertEqual(advanced_calculate(1, 2, "subtract", "positive"), "Default")  # F,_

    def test_condition1_true_false(self):
        self.assertEqual(advanced_calculate(1, 2, "add", "negative"), "Default")  # T,F

    def test_condition2_false_any(self):
        self.assertEqual(advanced_calculate(-1, 2, "add", "positive"), "Non-positive")  # a=-1 => F,_

    def test_condition2_true_false(self):
        self.assertEqual(advanced_calculate(1, -2, "add", "positive"), "Non-positive")  # a=1,b=-2 => T,F 这里不考虑T,T
```

条件覆盖的精确分析。

第一层条件（op1 和 op2）

| op1 == "add" | op2 == "positive" | 测试用例                     |
| :----------- | :---------------- | :--------------------------- |
| True         | True              | `test_condition1_true_true`  |
| False        | Any               | `test_condition1_false_any`  |
| True         | False             | `test_condition1_true_false` |

第二层条件（a 和 b）

（仅在 `op1=="add" and op2=="positive"` 为 True 时生效）

| a > 0 | b > 0 | 测试用例                                 |
| :---- | :---- | :--------------------------------------- |
| True  | True  | `test_condition1_true_true` (a=1, b=2)   |
| False | any   | `test_condition2_false_any` (a=-1, b=2)  |
| True  | False | `test_condition2_true_false` (a=1, b=-2) |



### 观察覆盖率报告

`coverage run -m unittest test_calculator.py` 会在当前目录生成 .coverage 文件。

`coverage report -m ` 可以查看覆盖率的文本形式报告。

```
Name                 Stmts   Miss  Cover   Missing
--------------------------------------------------
calculator.py           12      0   100%
test_calculator.py      23      0   100%
--------------------------------------------------
TOTAL                   35      0   100%
```

`coverage html` 可以查看网页形式的详细报告。