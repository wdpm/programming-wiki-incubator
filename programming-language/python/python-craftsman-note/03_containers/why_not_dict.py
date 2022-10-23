# -*- coding: utf-8 -*-
class UpperDict(dict):
    """总是把 key 转为大写"""

    def __setitem__(self, key, value):
        super().__setitem__(key.upper(), value)


upper_dict = UpperDict({'key': 1})
print(upper_dict)
# {'key1': 1} constructor not work

upper_dict_2 = UpperDict()
upper_dict_2['foo'] = 2
print(upper_dict_2)
# {'FOO': 2} setitem work

upper_dict_2.update({'bar': 4})
print(upper_dict_2)
# {'FOO': 2, 'bar': 4} update not work
