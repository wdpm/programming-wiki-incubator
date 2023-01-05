class MyView(object):
    someservice_cls = SomeService

    def __init__(self, request):
        self.request = request

    def index(self):
        s = self.someservice_cls()
        result = s.some_method(**self.request.params)
        self.render_context = dict(result=result)
        return render('index.html', self.render_context)
