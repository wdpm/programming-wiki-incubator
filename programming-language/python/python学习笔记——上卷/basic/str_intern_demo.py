import sys

s1 = "foo"
s2 = "foo"
print(s1 is s2)
print(id(s1), id(s2))

# True
# 1456858088304 1456858088304

print("__name__" is sys.intern("__name__"))

# run in py console
a = "hello, world!"
b = "hello, world!"
print(a is b)  # False
print(id(a), id(b))
print(a == b)  # True

#     This enters the string in the (global) table of interned strings whose
#     purpose is to speed up dictionary lookups. Return the string itself or
#     the previously interned string object with the same value.
print(sys.intern(a) is sys.intern("hello, world!"))

#  refer:  Objects/unicodeobject.c : PyUnicode_InternInPlace
