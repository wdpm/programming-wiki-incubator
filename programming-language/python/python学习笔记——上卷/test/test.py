# where is line_profiler
# https://github.com/rkern/line_profiler
# kernprof -l -v test.py

# where is memory_profiler
# https://github.com/pythonprofilers/memory_profiler
# python -m memory_profiler test.py

# measure memory usage
# https://github.com/pympler/pympler

@profile
def test():
    a = [1] * (10 ** 6)
    b = [2] * (2 * 10 ** 7)
    del b
    return a


if __name__ == '__main__':
    test()

