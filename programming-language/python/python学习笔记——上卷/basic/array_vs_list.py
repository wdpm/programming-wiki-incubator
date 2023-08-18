import array

from memory_profiler import profile


@profile
def test_list():
    x = []
    x.extend(range(1000000))
    return x


@profile
def test_array():
    # https://docs.python.org/3/library/array.html#module-array
    x = array.array("l")  # "l" means signed long
    x.extend(range(1000000))
    return x


test_list()
test_array()

# >py -m memory_profiler array_vs_list.py
# Filename: array_vs_list.py
#
# Line #    Mem usage    Increment  Occurrences   Line Contents
# =============================================================
#      6     45.0 MiB     45.0 MiB           1   @profile
#      7                                         def test_list():
#      8     45.0 MiB      0.0 MiB           1       x = []
#      9     83.3 MiB     38.3 MiB           1       x.extend(range(1000000))
#     10     83.3 MiB      0.0 MiB           1       return x
#
#
# Filename: array_vs_list.py
#
# Line #    Mem usage    Increment  Occurrences   Line Contents
# =============================================================
#     13     45.6 MiB     45.6 MiB           1   @profile
#     14                                         def test_array():
#     15                                             # https://docs.python.org/3/library/array.html#module-array
#     16     45.6 MiB      0.0 MiB           1       x = array.array("l")  # "l" means signed long
#     17     51.0 MiB      5.4 MiB           1       x.extend(range(1000000))
#     18     51.0 MiB      0.0 MiB           1       return x
