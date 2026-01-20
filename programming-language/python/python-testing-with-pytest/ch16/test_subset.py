import pytest

@pytest.fixture(params=["admin", "team_member", "visitor"])
def user(request):
    ...
    role = request.param
    print(f"\nLog in as {role}")
    yield role
    print(f"\nLog out {role}")


def test_everyone(user):
    ...


@pytest.mark.parametrize("user", ["admin"], indirect=["user"])
def test_just_admin(user):
    ...

# test_subset.py::test_everyone[admin] PASSED
# test_subset.py::test_everyone[team_member] PASSED
# test_subset.py::test_everyone[visitor] PASSED
# test_subset.py::test_just_admin[admin] PASSED


# This allows different tests to use the same fixture with different parameter values.