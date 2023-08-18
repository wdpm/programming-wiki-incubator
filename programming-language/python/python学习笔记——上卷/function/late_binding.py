def make(n):
    x = []
    for i in range(n):
        x.append(lambda: print(i))

        # 消除闭包
        # x.append(lambda o = i: print(o))

    return x


a, b, c = make(3)
a()
b()
c()

print(a.__closure__)
print(b.__closure__)
print(c.__closure__)

# 2
# 2
# 2
# (<cell at 0x000001FFBF383FA0: int object at 0x000001FFBD3D0110>,)
# (<cell at 0x000001FFBF383FA0: int object at 0x000001FFBD3D0110>,)
# (<cell at 0x000001FFBF383FA0: int object at 0x000001FFBD3D0110>,)


# 1. make 创建并返回 3 个闭包函数，引用同一自由变量 i。
# 2. make 执行结束，i 等于 2。
# 3. 执行闭包函数，引用并输出 i 的值，自然都是 2