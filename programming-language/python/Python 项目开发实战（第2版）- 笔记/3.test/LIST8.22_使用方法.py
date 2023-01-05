def test_it():
    from webtest import TestApp
    import myproject.app
    app = TestApp(myproject.app)
    res = app.get('/')
    assert "Hello" in res
