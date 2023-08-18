import dis


def make():
    x = 100

    def test(): print(x)

    return test

if __name__ == '__main__':
    dis.dis(make)

#   5           0 LOAD_CONST               1 (100)
#               2 STORE_DEREF              0 (x) <---------DEREF，注意不是FAST
#
#   7           4 LOAD_CLOSURE             0 (x) <---------闭包变量
#               6 BUILD_TUPLE              1
#               8 LOAD_CONST               2 (<code object test >)
#              10 LOAD_CONST               3 ('make.<locals>.test')
#              12 MAKE_FUNCTION            8 (closure) <---------这个函数在创建时包含了上面的自由变量x
#              14 STORE_FAST               0 (test)
#
#   9          16 LOAD_FAST                0 (test)
#              18 RETURN_VALUE


# f=make()
# dis.dis(f)

# Disassembly of <code object test at  line 7>:
#   7           0 LOAD_GLOBAL              0 (print)
#               2 LOAD_DEREF               0 (x)
#               4 CALL_FUNCTION            1
#               6 POP_TOP
#               8 LOAD_CONST               0 (None)
#              10 RETURN_VALUE