# python basic

> python 3.5

## 变量和字符串

variable: ``str = 'some'``

string

- index(索引): ``str[0:4]``
- method
  - replace(替换)
  - find(查找)
  - format(格式化:占位赋值)

## 函数
- 函数定义
```python
def add(a, b):
    return a + b

# 默认参数值   
def add(a, b=2):
    return a + b    
```

## 运算符
- 数学运算符
> 假设a=20,b=10

|运算符|说明|举例|
|:---:|---|---|
|+|加|a+b==30|
|-|减|a-b==10|
|*|乘：两数相乘，或重复某字符串n次|a*b==200|
|/|除|a/b==2|
|%|取模：返回除法的余数|a%b==0|
|**|幂：返回a的b次方|a**b==10240000000000|
|//|取整除：返回商的整数部分|7//2==3,7.0//2.0==3.0|

- 比较运算符
``==``　``!=``　``<=``　``>=``　``>``　``<``　``<>``
> 注意：``!=``等价于``<>``

- 成员运算符(in/not in)
```
judge1 = 'w' in 'wdpm'  # True
judge2 = 'w' not in 'wdpm'  # False
```

- 身份运算符(is/is not)
```
a = 1
judge1 = a is 1 # True
judge2 = a is not 1 # False
```
当两个变量值一样时,is判断返回True
```
me = 'wdpm'
I = 'wdpm'
isSamePerson = me is I
```
> 补充:``0``,``[]``,``false``,``''``,``None``在bool()的结果均为False

- 布尔运算符

|运算符|说明|举例|
|:---:|---|---|
|not|取反|True-->False,False-->True|
|and|且||
|or|或||

## 条件判断
if,elif,else为关键字。以判断一个数的正负为例：
```python
def judgeRangeOfNumber():
    num = int(input('input a number:'))
    if num < 0:
        print('is negative')
    elif num == 0:
        print('is zero')
    else:
        print('is postive')


judgeRangeOfNumber()
```

## 循环
- for循环
```
for item in iterable:
    # do something
```
- while循环
```
while condition:
    # do something
```

## 数据结构
|类型|说明|举例|
|:---:|---|---|
|list|列表|[v1,v2,v3]|
|dict|字典|{k1:v1,k2:v2}|
|tuple|元组|(v1,v2,v3)|
|set|集合|{v1,v2,v3}|

- 列表(list)
> 1. 元素可变，可以增删改查；
> 2. 有序，只通过索引位置获取元素；
> 3. 元素种类可以任意。

增删改查:
```
list=['A','B','C']
list.append('D')   # add
print(list)        # ['A', 'B', 'C', 'D']
list.insert(4,'E') # add, also can use extend() to insert several elements
list[0]='change word'
list.remove('B')   # delete
print(list)        # ['change word', 'C', 'D', 'E']
```
- 字典(dict)
> 1. 逻辑上，键(key)不能重复，值(value)可以重复；
> 2. 键(key)不能修改，值(value)可以修改。

增删改查：
```
dict = {'a': 1, 'b': 2}
dict['c'] = 3 # add
dict.update({'d':5,'e':6}) # add
del dict['b'] # delete
dict['a'] =4  # update
print(dict)   # {'d': 5, 'e': 6, 'c': 3, 'a': 4}
```
> 注意:字典不能被切片,dict[0:1]会报错。

- 元组(tuple)

可理解为不可修改的列表，但没有列表的方法属性。
```
tuple=(1,2,3,4,5)
print(tuple.index(2)) # 1，值2的index为1
```

- 集合(set)

集合三大特性：确定性、互异性、无序性。
```
set_a={1,2,3,4}
set_a.add(5)
set_a.discard(4)
print(set_a) # {1, 2, 3, 5}
```
一些常用函数: sorted(排序),zip(压缩)。
推导式(解析式): ``list = [item | for item in iterable]``

## 类
- 魔术方法

``__init__``在创建实例时自动执行，不需要显式调用。

- 类继承
```python
class Animal:
    food = 'unknown'
    canFly = None

class Bird(Animal):
    food = 'insect'
    canFly = True
```
- 类属性和实例属性
1. 类属性被重新赋值，会影响到类属性的引用(实例属性)
2. 实例属性被重新赋值，不会影响到其他类属性的引用。
3. 类属性实例属性都具有相同名称，该属性的值为实例属性的值。

引用机制：就近原则。先查看实例属性列表，再查看类属性列表。
