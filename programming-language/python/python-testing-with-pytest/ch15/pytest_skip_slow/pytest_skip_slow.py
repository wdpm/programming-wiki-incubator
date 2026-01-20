import pytest


def pytest_configure(config):
    config.addinivalue_line("markers", "slow: mark test as slow to run")


def pytest_addoption(parser):
    parser.addoption(
        # action="store_true" parameter tells pytest to store a true in the slow configuration
        # setting when the --slow flag is passed in, and false otherwise.
        "--slow", action="store_true", help="include tests marked slow"
    )


def pytest_collection_modifyitems(config, items):
    # if --slow flag is false, add a skip marker to any test that already includes the slow marker.
    # Then it will skip these slow items in runtime.
    if not config.getoption("--slow"):
        skip_slow = pytest.mark.skip(reason="need --slow option to run")
        for item in items:
            if item.get_closest_marker("slow"):
                item.add_marker(skip_slow)
