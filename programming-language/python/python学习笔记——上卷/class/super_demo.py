class A:
    @classmethod
    def demo(cls): pass

    def test(self): print("A")


class B(A):
    def test(self): print("B")


class C(B):
    def test(self): print("C")


print(C.__mro__)
print(super(C, C).test)

# 第二个参数C是第一个参数B的子类型，因此它们公共基类是A
print(super(B, C).test)
