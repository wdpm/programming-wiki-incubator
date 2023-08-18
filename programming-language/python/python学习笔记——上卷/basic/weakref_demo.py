import sys


class X:
    def __del__(self):
        print(id(self), "dead.")


a = X()
import weakref
print(sys.getrefcount(a))

w = weakref.ref(a)
print(w() is a)
print(sys.getrefcount(a))

del a
print(w() is None)
