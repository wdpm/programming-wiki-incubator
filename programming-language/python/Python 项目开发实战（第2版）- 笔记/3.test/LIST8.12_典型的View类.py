class MyView(object):
    def __init__(self, request):
        self.request = request

    def index(self):
        s = SomeService()
        result = s.some_method(**self.request.params)
        return render('index.html', dict(result=result))
