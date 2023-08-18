import types

o = types.SimpleNamespace(a = 1, b = "abc")
o.c = [1, 2]
print(o.__dict__)
# {'a': 1, 'b': 'abc', 'c': [1, 2]}