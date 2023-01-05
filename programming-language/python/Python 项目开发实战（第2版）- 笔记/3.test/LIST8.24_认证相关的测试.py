@wsgify
def myapp(request):
    if not request.remote_user:
        return HTTPFound(location="/login")

    return "OK"

def _makeOne():
    from webtest import TestApp
    return TestApp(myapp)

def _callAUT(url, params={}, method="GET", remote_user=None):
    extra_environ = {'REMOTE_USER': remote_user}
    if method == "GET":
        return _makeOne().get(
           url, params=params, extra_environ=extra_environ)
    elif method == "POST":
        return _makeOne().post(
           url, params=params, extra_environ=extra_environ)

def test_with_login():
    result = _callAUT('/', remote_user='dummy')
    assert result.body == 'OK'


def test_without_login():
    result = _callAUT('/')
    assert result.location == "/login"
