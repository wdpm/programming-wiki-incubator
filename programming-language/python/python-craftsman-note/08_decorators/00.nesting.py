def foo():
    def bar():
        def baz():
            return 1
        return baz

    return bar

# foo() => bar
# foo()()  => bar() => baz
# foo()()() => baz() => 1

print(foo()()())