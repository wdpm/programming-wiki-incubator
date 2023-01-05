class DummyRequest(object):
    def __init__(self, params):
        self.params = params

class DummySomeService(object):
    def somemethod(self, **kwargs):
        return kwargs

class TestIt(unittest.TestCase):
    def test_it(self):
        request = DummyRequest(params={'a': 1})
        target = MyView(request)
        target.someservice_cls = DummySomeService
        result = target.index()
        self.assertEqual(target.render_context['result'], {'a': 1})
