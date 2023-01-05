from webob.dec import wsgify

@wsgify
def myapp(request):
    c = int(request.cookies.get('count', "0"))
    request.response.set_cookie('count', str(c + 1))
    return "response %d" % c


def test_it():
    from webtest import TestApp
    app = TestApp(myapp)
    res = app.get('/')
    assert res.body == "response 0"

    res = app.get('/')
    assert res.body == "response 1"
