import copy

items = [1, ['foo', 'bar'], 2, 3]

items_copy = copy.copy(items)

items[0] = 100
items[1].append('baz')

print(items)
# [100, ['foo', 'bar', 'baz'], 2, 3]

print(items_copy)
# expected: [1, ['foo', 'bar',], 2, 3]
# actual: [1, ['foo', 'bar', 'baz'], 2, 3]

# 数组发生了变化，这就是浅复制的缺点。也就是：如果复制的元素是一个对象，那么只复制引用，根本没有隔离。

items = [1, ['foo', 'bar'], 2, 3]
items_deepcopy = copy.deepcopy(items)

items[0] = 100
items[1].append('baz')

print(items)
# [100, ['foo', 'bar', 'baz'], 2, 3]

print(items_deepcopy)
# [1, ['foo', 'bar'], 2, 3]
# 这里 items_deepcopy 和之前的 items 一致。

