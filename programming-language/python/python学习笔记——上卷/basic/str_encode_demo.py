s = "汉字"

print(len(s))
# 2

print(hex(ord("汉")))
# 0x6c49

print(chr(0x6c49))

print(ascii("汉字"))
# '\u6c49\u5b57'


long_str = """\
# 换行符、前导空格、空行都是组成内容
Beautiful is better than ugly.
Explicit is better than implicit.
Simple is better than complex.
"""

print(long_str)

# str slice
s = "0123456789"

slice_1 = s[0:2]
print(slice_1)

slice_1 = 'ab'
print(slice_1, s)
