# int intern range: [-5,257)
# https://github.com/python/cpython/blob/v3.10.0/Objects/longobject.c
# please run the code follow in py console NOT IDE

e = -6
f = -6
print(e is f)
print(id(e), id(f))

a = -5
b = -5
print(a is b)
print(id(a), id(b))

c = 256
d = 256
print(c is d)
print(id(c), id(d))

a1 = 257
b1 = 257
print(a1 is b1)
print(id(a1), id(b1))

# e = -6
# f = -6
# print(e is f)
# print(id(e), id(f))

# a = -5
# b = -5
# print(a is b)
# print(id(a), id(b))

# c = 256
# d = 256
# print(c is d)
# print(id(c), id(d))

# a1 = 257
# b1 = 257
# print(a1 is b1)
# print(id(a1), id(b1))

# False
# 1581327643728 1581327638480

# True
# 1581280264240 1581280264240

# True
# 1581280272592 1581280272592

# False
# 1581327643184 1581327638928
