from multiprocessing import Process, Value, cpu_count


def increment_value(shared_int: Value):
    shared_int.value = shared_int.value + 1


if __name__ == '__main__':
    print(cpu_count())
    for _ in range(10000):
        integer = Value('i', 0)
        procs = [Process(target=increment_value, args=(integer,)),
                 Process(target=increment_value, args=(integer,))]

        [p.start() for p in procs]
        [p.join() for p in procs]
        print(integer.value)
        assert(integer.value == 2)

# 2
# 1
# Traceback (most recent call last):
#   File "D:\Code\OtherGithubProjects\concurrency-in-python-with-asyncio\chapter_06\listing_6_11.py", line 18, in <module>
#     assert(integer.value == 2)
# AssertionError

# 两个进程同时读取0，同时加1，同时写1。
# 引入lock来处理竞态条件。