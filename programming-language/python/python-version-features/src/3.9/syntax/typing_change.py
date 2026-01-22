from typing import List


def greet_all(names: List[str]) -> None:
    for name in names:
        print("Hello", name)


def greet_all(names: list[str]) -> None:
    for name in names:
        print("Hello", name)