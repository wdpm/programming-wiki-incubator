class App:
    def __init__(self):
        self.routers = {}

    def route(self, url):  # 实现为带参数的装饰器，用于注册路由配置
        def register(fn):
            self.routers[url] = fn
            return fn

        return register


app = App()


@app.route("/")
def index(): pass


@app.route("/help")
def help(): pass

print(app.routers)
