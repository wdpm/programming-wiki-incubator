def is_last_of_month_now():
    return is_last_of_month(datetime.now())


from datetime import datetime


def test_is_last_of_month_now():
    # 获取时间部分使用 testfixtures 的 Replacer 和 test_date。
    with Replacer() as r:
        r.repace('util.datetime', test_date(2011, 11, 30))
        assert is_last_of_month_now()
