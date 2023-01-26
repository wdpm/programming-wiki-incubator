# -*- coding: utf-8 -*-
import dis


def do_something(delta_seconds):
    # 如果时间已经过去11 天(或者更久)，不做任何事
    if delta_seconds < 11 * 24 * 3600:
        return
    ...


dis.dis(do_something)

#   7           0 LOAD_FAST                0 (delta_seconds)
#               2 LOAD_CONST               1 (950400)  <---注意这里的常量
#               4 COMPARE_OP               0 (<)
#               6 POP_JUMP_IF_FALSE        6 (to 12)
#
#   8           8 LOAD_CONST               0 (None)
#              10 RETURN_VALUE
#
#   9     >>   12 LOAD_CONST               0 (None)
#              14 RETURN_VALUE