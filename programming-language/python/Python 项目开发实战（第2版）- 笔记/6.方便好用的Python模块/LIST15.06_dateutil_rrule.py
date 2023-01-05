# coding: utf-8
from datetime import datetime
from dateutil.rrule import rrule, DAILY, MO, WE

def main():
    # 生成rrule对象
    rrule_obj = rrule(DAILY,  # 每天
                      byweekday=(MO, WE),  # 周一、周三
                      dtstart=datetime(2012, 1, 1),  # 2012年1月1日起
                      until=datetime(2012, 2, 1))  # 2012年2月1日止
    # 逐个取出符合条件的日期对象并显示在屏幕上
    for dt in rrule_obj:
        print dt

if __name__ == '__main__':
    main()
