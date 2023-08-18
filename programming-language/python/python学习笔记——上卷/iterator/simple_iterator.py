class Data:
    def __init__(self, n):
        self.data = list(range(n))

    # is iterable
    def __iter__(self):
        # 系统自带的
        # return iter(self.data)
        # 自己实现
        return DataIter(self.data)

    def __getitem__(self, index):
        if index < 0 or index >= len(self.data):
            raise IndexError
        return self.data[index]


class DataIter:
    def __init__(self, data):
        self.data = data
        self.index = 0

    # is iterator
    def __next__(self):
        if not self.data or self.index >= len(self.data):
            raise StopIteration  # 抛出异常表示迭代结束
        d = self.data[self.index]  # 本次迭代返回数据
        self.index += 1  # 存储迭代进度
        return d

    # make it as an iterable
    def __iter__(self):
        return self
