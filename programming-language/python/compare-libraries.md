# python库对比

## python standard libraries

| name                           | desc                                             |
|--------------------------------|--------------------------------------------------|
| logging                        | 日志                                               |
| datetime                       | 时间日期处理                                           |
| textwrap                       | 复杂文本的包装库，例如反缩进                                   |
| timeit                         | 衡量函数执行时间                                         |
| enum                           | 枚举基类                                             |
| collections.defaultdict        | 含有默认初始值的字典,可以给字典的默认行为加上变化                        |
| collections.abc.MutableMapping | 可变的字典                                            |
| collections.deque              | 双端队列                                             |
| copy                           | 浅复制copy和深复制deepcopy                              |
| collections.namedtuple         | 具名元组                                             |
| random                         | 随机数生成                                            |
| bisect                         | 二分法                                              |
| contextlib                     | @contextmanager 和 @asynccontextmanager decorator |
| itertools                      | 迭代工具库，笛卡儿积                                       |
| functools.partial              | 偏函数                                              |
| functools.wraps                | 修复函数嵌套时丢失原函数属性                                   |
| functools.lrucache             | 最近最少使用工具类                                        |
| abc.ABC                        | 抽象类                                              |
| collections.Counter            | 计数器统计dict                                        |
| functools.total_ordering       | 简化6个比较关系到2个方法实现                                  |
| dataclasses.dataclass          | 数据类，省去hash、eq、init方法                             |

## python thirty libraries

| name                                              | desc                        |
|---------------------------------------------------|-----------------------------|
| dis                                               | 反编译python代码，用于调试            |
| jinjia2                                           | 纯python的模版引擎，用于渲染长字符串       |
| sqlalchemy                                        | 著名的python ORM库，用于和常见关系数据库交互 |
| [loguru](https://github.com/Delgan/loguru)        | python日志库                   |
| isort                                             | import语句排序                  |
| black                                             | 强制格式化                       |
| flake8                                            | linter                      |
| [ruff](https://github.com/astral-sh/ruff)                                              | linter                      |
| pre-commit                                        | git hooks                   |
| mypy                                              | 类型加强                        |
| pytest                                            | 单元测试                        |
| [httptest](https://github.com/pdxjohnny/httptest) | 启发于go的httptest              |
| [tenacity](https://github.com/jd/tenacity)        | python 的重试库                 |
| [sympy](https://www.sympy.org/)                   | python的数学符号库                |
| [结巴](https://github.com/fxsjy/jieba)                  | 中文分词                        |
| https://github.com/lk-geimfari/mimesis                  | data faker/mock             |

## need to explore

网络测试模拟库
- VCR.py
- Responses
- HTTPretty
- Betamax