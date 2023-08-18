>>> import demo
>>> m = demo # 另一引用
>>> m.x
888
>>> !echo "x = 666" > demo.py # 修改源文件
>>> importlib.reload(demo) # 重载模块
>>> demo.x # 热更新成功
666
>>> m.x # 其他引用也同步更新
666
>>> demo is m
True

# 只对模块引用有效，但对成员引用无法更新，因为 reload 不会递归修改成员

>>> import demo
>>> x = demo.x # 引用另一模块的成员
>>> x
666
>>> !echo "x = 777" > demo.py # 修改源文件
>>> importlib.reload(demo) # 重载模块
>>> demo.x # 热更新成功
777
>>> x # 但对原成员引用无效
666
>>> demo.x is x
False
