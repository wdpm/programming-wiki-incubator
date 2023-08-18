from collections.abc import Hashable

print(issubclass(list, Hashable))
print(issubclass(set, Hashable))
print(issubclass(dict, Hashable))

print(issubclass(int, Hashable))
print(issubclass(str, Hashable))
print(issubclass(tuple, Hashable))
print(issubclass(bool, Hashable))
print(issubclass(frozenset, Hashable))

hash([1, 2, {3, 5}])
# TypeError: unhashable type: 'list'
