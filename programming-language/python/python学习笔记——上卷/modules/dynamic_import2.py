import importlib
def test(name):
    m = importlib.import_module(name)
    print(m)

if __name__ == '__main__':
    test("json")