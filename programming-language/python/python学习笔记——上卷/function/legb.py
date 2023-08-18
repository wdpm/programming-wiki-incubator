import builtins

builtins.B = "B"
G = "G"

# LEGB 在运行期动态地从多个位置按特定顺序查找变量

def enclosing():
    E = "E"

    def test():
        L = "L"
        print(L, E, G, B)

    return test

if __name__ == '__main__':
    enclosing()()
