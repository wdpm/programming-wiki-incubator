# coding: utf-8
from datetime import datetime, timedelta

def main():
    # 下个月最后一天=下下个月的前一天
    now_time = datetime.now()
    # 获取下下个月第一天的日期对象（考虑跨年问题）
    if now_time.month in [11, 12]:
        first_day_of_after_two_month = datetime(now_time.year + 1,
                                                now_time.month + 2 - 12,
                                                1)
    else:
        first_day_of_after_two_month = datetime(now_time.year,
                                                now_time.month + 2,
                                                1)
    # 输出下个月最后一天
    last_day_of_next_month = \
        first_day_of_after_two_month - timedelta(days=1)
    # 输出结果
    print last_day_of_next_month.date()

if __name__ == '__main__':
    main()
