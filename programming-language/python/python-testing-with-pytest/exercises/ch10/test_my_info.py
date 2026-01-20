from pathlib import Path
from unittest import mock

import my_info


# 1.
# write a test that uses mock and changes the return value
# of Path.home() to "/users/fake_user", and checks the return value of home_dir()

def test_my_home_returns_correct_value():
    with mock.patch.object(my_info, 'home_dir') as mock_home_dir:
        mock_home_dir.return_value = '/users/fake_user'

        value = my_info.home_dir()
        assert value == "/users/fake_user"


# 2.
# Write another test that also calls home_dir(), but instead of checking the
# value, just asserts that Path.home() is called by home_dir()

def test_my_home_is_called():
    # check to see if Path.home() was called
    with mock.patch.object(Path, 'home', autospec=True) as mock_path_home:
        my_info.home_dir()

    print()

    print(mock_path_home)
    # <MagicMock name='home' spec='classmethod' id='1783938983072'>

    mock_path_home.assert_called_once()
