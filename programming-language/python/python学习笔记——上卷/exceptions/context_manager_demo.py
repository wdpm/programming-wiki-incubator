import contextlib


@contextlib.contextmanager
def db_context(db):
    try:
        print(f"open: {db}")  # 初始化
        yield db + "_instance"
    finally:
        print(f"close: {db}")


def test(db):
    with db_context(db) as db_inst:
        print(f"exec: {db_inst}")  # 逻辑
        raise Exception("error")


#  @contextlib.contextmanager 背后的源码逻辑（精简）
class GeneratorContextManager:
    def __init__(self, func):
        self.gen = func()  # 执行原函数（db_context），返回生成器对象

    def __enter__(self):
        return next(self.gen)  # 启动生成器，返回 yield 结果作为导出变量

    # 实际上就是执行 yield 之前的用户代码
    def __exit__(self, typ, val, tb):
        if typ is None:
            next(self.gen)  # 继续执行 yield 后面的代码
        else:
            self.gen.throw(typ, val, tb)  # 如果有异常发生，则将异常抛回给生成器


if __name__ == '__main__':
    test("redis")
