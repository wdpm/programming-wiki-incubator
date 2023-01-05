# -*- coding: utf-8 -*-

def commonprefix(path_list):
    """
    返回路径的“`path_list`”中最长的公共前缀
    （依次判断路径名的每一个字符）

        >>> commonprefix(['/usr/bin/python', '/usr/local/bin/python'])
        '/usr/'
        >>> commonprefix(['/usr/bin/python'])
        '/usr/bin/python'

    如果“`path_list`”为空，则返回空字符串“''”

        >>> commonprefix([])
        ''

    请注意，由于每次只判断一个字符，
    所以可能返回非法路径

        >>> commonprefix(['/usr/local/bin/python', '/usr/local/bin/pylint'])
        '/usr/local/bin/py'
    """
    if not path_list: return ''
    s1 = min(path_list)
    s2 = max(path_list)
    for i, c in enumerate(s1):
        if c != s2[i]:
            return s1[:i]
    return s1
