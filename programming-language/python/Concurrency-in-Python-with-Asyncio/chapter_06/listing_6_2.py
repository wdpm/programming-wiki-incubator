from multiprocessing import Pool, cpu_count


def say_hello(name: str) -> str:
    return f'Hi there, {name}'


if __name__ == "__main__":
    print(cpu_count())
    with Pool() as process_pool:  # A
        hi_jeff = process_pool.apply(say_hello, args=('Jeff',))  # B
        hi_john = process_pool.apply(say_hello, args=('John',))
        print(hi_jeff)
        print(hi_john)

# 这里是串行阻塞执行模型