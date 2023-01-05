# coding: utf-8
from dateutil.relativedelta import relativedelta
from datetime import datetime, timedelta

def main():
    # 下个月最后一天=下下个月的前一天
    now_time = datetime.now()
    # 输出下个月最后一天
    last_day_of_next_month = \
        now_time + relativedelta(months=2, day=1, days=-1)
    # 输出结果
    print last_day_of_next_month.date()

if __name__ == '__main__':
    main()
