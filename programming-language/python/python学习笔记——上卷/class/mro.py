class Y:
    n = "Y.n"

    def test(self): print("Y.test")


class X(Y): pass


class N: pass


class M(N):
    n = "M.n"

    def test(self):
        print("M.test")


class A(X, M): pass


print(A.__mro__)
# (A, X, Y, M, N, object)
print(A().n)
# 'Y.n'
print(A().test())
# Y.test

# >>> A.__bases__ = (M, X)
# >>> A.__mro__
# (A, M, N, X, Y, object)
# >>> A().test()
# M.test
