valid_set = set(['apple', 30, 1.3, ('foo')])
# 可以成功初始化

invalid_set = set(['foo', [1, 2, 3]])
# >>>
# 报错：TypeError: unhashable type: 'list'