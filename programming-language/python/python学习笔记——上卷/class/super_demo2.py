class A:
    def test(self):
        print("A.test")


class B(A):
    def test(self):
        print("B.test")
        # self.__class__.__base__.test(self)
        # 站在 B 的角度，self.__class__.__base__似乎指向 A
        # 但实际上，self 可能是 B 的子类实例，比如 C
        # 如此，self.__class__.__base__实际指向的依然是 B
        # 其结果就是导致 B.test 被递归
        super().test()


class C(B): pass

C().test()
# B.test
# A.test
