import bar_module
import foo_module


def test_foo():
    assert foo_module.foo() == "foo"


def test_bar():
    assert bar_module.bar() == "bar"


def test_baz():
    assert bar_module.baz() == "baz"
