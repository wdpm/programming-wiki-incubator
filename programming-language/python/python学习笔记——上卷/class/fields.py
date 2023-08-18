class Foo:
    _name = 'foo'
    __age = 10


class Bar(Foo): pass


print(Foo()._Foo__age)

print(Bar()._name)
print(Bar().__age)  # not found


