# method 1
import sys

def test(name):
    exec(f"import {name}")
    m = sys.modules[name]
    print(m)


if __name__ == '__main__':
    test("json")