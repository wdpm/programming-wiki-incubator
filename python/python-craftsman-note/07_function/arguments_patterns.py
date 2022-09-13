def append_value(value, items=[]):
    """往 items 列表中追加内容，并返回列表"""
    items.append(value)
    return items

append_value(1)
arr = append_value(2)
print(arr)
# think why [1,2]
# 函数的默认参数只会初始化一次。

def append_value(value, items=None):
    # 在函数内部进行判断，保证参数默认每次都使用一个新的空列表
    if items is None:
        items = []
    items.append(value)
    return items