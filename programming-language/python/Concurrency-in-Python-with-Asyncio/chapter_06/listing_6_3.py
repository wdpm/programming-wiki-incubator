from multiprocessing import Pool


def say_hello(name: str) -> str:
    return f'Hi there, {name}'


if __name__ == "__main__":
    with Pool() as process_pool:
        hi_jeff = process_pool.apply_async(say_hello, args=('Jeff',))
        hi_john = process_pool.apply_async(say_hello, args=('John',))
        print(hi_jeff.get())
        print(hi_john.get())

# 异步运行模型，.get()方法会block。
# 因此如果 hi_jeff.get() 很慢，block很久，那么hi_john.get()就算神速也得等待上一个hi_jeff执行完毕。
# 于是，我们需要考虑如何尽快得到反馈。
# 理想的模型是：先运行完毕的就先反馈，运行慢的就继续运行。
