def print_string(s):
    # 在常规应用代码中，不建议使用assert断言
    assert isinstance(s, str), 's must be string'
    print(s)


print_string(3)
print_string('foo')


def print_string(s):
    if not isinstance(s, str):
        # 大胆抛出错误，不要怂
        raise TypeError('s must be string')
    print(s)
