def enclosing():
    a = "enclosing.a"

    # LEGB 作用域

    class A:
        a = "A.a"  # L --> A.__dict__

        @staticmethod
        def test():
            print("E.a =", a)  # E --> enclosing.a ③

        print("A.locals =", locals())  # L --> A.__dict__ ①
        print("A.a =", a)  # L --> A.__dict__ ②

    A().test()


enclosing()
# A.locals = {'__module__': '__main__', '__qualname__': 'enclosing.<locals>.A', 'a': 'A.a', 'test': <staticmethod(<function enclosing.<locals>.A.test at 0x000001B4F4D4EC20>)>}
# A.a = A.a
# E.a = enclosing.a
