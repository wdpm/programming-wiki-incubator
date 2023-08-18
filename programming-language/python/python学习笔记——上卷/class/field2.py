class A:
    data = "A.data"


class B(A):
    pass


# 实例o的dict空间没有data属性
o = B()
delattr(o, "data")
