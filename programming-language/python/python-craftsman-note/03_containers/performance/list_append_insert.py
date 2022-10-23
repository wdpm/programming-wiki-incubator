def list_append():
    """不断往尾部追加"""
    l = []
    for i in range(5000):
        l.append(i)


def list_insert():
    """不断往头部插入"""
    l = []
    for i in range(5000):
        l.insert(0, i)


import timeit


# 默认执行 1000次
append_spent = timeit.timeit(
    setup='from __main__ import list_append',
    stmt='list_append()',
    number=1000,
)
print("list_append:", append_spent)

insert_spent = timeit.timeit(
    setup='from __main__ import list_insert',
    stmt='list_insert()',
    number=1000,
)
print("list_insert", insert_spent)

# list_append: 0.40022970002610236
# list_insert 5.803209199977573