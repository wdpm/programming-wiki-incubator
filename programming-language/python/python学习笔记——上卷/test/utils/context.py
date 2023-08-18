import cProfile, pstats, contextlib, time


@contextlib.contextmanager
def profile(sortby="cumtime", limit=10, timer=time.perf_counter):
    p = cProfile.Profile(timer)
    p.enable()
    try:
        yield
    finally:
        p.disable()
        s = pstats.Stats(p).sort_stats(sortby)
        s.print_stats(limit)


def sum(m):
    n = 0
    for i in range(m):
        n += i
    return n


@profile()
def main():
    for i in range(3):
        sum(1000000)

    n = 0
    for i in range(100000):
        n += i


if __name__ == '__main__':
    main()

#          8 function calls in 0.199 seconds
#
#    Ordered by: cumulative time
#
#    ncalls  tottime  percall  cumtime  percall filename:lineno(function)
#         1    0.008    0.008    0.199    0.199 D:\Code\MyLocalProjects\python3-study-note-part-1\test\context.py:23(main)
#         3    0.191    0.064    0.191    0.064 D:\Code\MyLocalProjects\python3-study-note-part-1\test\context.py:16(sum)
#         1    0.000    0.000    0.000    0.000 C:\Users\wdpm\AppData\Local\Programs\Python\Python310\lib\contextlib.py:139(__exit__)
#         1    0.000    0.000    0.000    0.000 {built-in method builtins.next}
#         1    0.000    0.000    0.000    0.000 D:\Code\MyLocalProjects\python3-study-note-part-1\test\context.py:4(profile)
#         1    0.000    0.000    0.000    0.000 {method 'disable' of '_lsprof.Profiler' objects}