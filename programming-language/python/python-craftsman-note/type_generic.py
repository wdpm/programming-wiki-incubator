from typing import TypeVar

T = TypeVar('T')


def func(value: T) -> T:
    return value


print(func(1))
print(func("a"))