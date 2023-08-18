a = [1, 2]

b = a

a = a + [3, 4]  # new object `a`

print('a: ', a)
print('b: ', b)

# inplace

a = [1, 2]

b = a

a += [3, 4]  # `a` inplace add

print('a: ', a)
print('b: ', b)
