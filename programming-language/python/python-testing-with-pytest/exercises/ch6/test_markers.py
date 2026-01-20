import pytest

pytestmark =pytest.mark.all

@pytest.mark.odd
def test_one():
    pass


@pytest.mark.odd
def test_two():
    pass

@pytest.mark.odd
def test_three():
    pass


@pytest.mark.testclass
class TestClass:
    def test_four(self):
        pass

    @pytest.mark.odd
    def test_five(self):
        pass


@pytest.mark.odd
@pytest.mark.parametrize("x", [6, 7])
def test_param(x):
    pass

# Run all the tests using the all marker.
# > pytest -m all

# Run the odd tests.
# > pytest -m odd

# Run the odd tests that are not marked with testclass.
# > pytest -m odd -k "not testclass"

# Run the odd tests that are parametrized. (Hint: Use both marker and keyword flags.)
# > pytest -m odd -k "parametrize"
# test_markers.py::test_param[6] PASSED                                                                                                                       [ 50%]
# test_markers.py::test_param[7] PASSED