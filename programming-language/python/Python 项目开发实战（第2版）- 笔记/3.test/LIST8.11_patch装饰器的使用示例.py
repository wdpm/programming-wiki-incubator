@patch('myviews.SomeService')
def test_it(MockSomeService):
    mock_obj = MockSomeService.return_value
    mock_obj.something.return_value = 10
    from myviews import MyView
    result = MyView()
    assert result = 10
