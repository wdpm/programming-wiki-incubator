# -*- coding: utf-8 -*-


class A:
    def say(self):
        print("I'm A")


class B(A):
    pass


class C(A):
    def say(self):
        print("I'm C")


class D(B, C):
    pass

print(D.__mro__)
# (<class '__main__.D'>, <class '__main__.B'>, <class '__main__.C'>, <class '__main__.A'>, <class 'object'>)
# 注意 D -> B 之后是C，而不是B的父类A，诡异的python的MRO
