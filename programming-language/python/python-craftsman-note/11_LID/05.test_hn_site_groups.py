from hn_site_grouper import SiteSourceGrouper as SiteSourceGrouperO

# from hn_site_grouper_D1 import SiteSourceGrouper, LocalHNWebPage

from collections import Counter


from unittest import mock


# def test_grouper_returning_valid_type():
#     """测试 get_groups 是否返回了正确类型"""
#     grouper = SiteSourceGrouperO('https://news.ycombinator.com/')
#     result = grouper.get_groups()
#     assert isinstance(result, Counter), "groups should be Counter instance"


# hn_site_grouper.requests.get 指定是mock requests这个库的get方法
@mock.patch('hn_site_grouper.requests.get')
def test_grouper_returning_valid_type(mocked_get):
    """测试 get_groups 是否返回了正确类型"""
    # 这里使用本地内容来替换requests的实际get请求。
    with open('static_hn.html', 'r',encoding='utf-8') as fp:
        mocked_get.return_value.text = fp.read()

    grouper = SiteSourceGrouperO('https://news.ycombinator.com/')
    result = grouper.get_groups()
    assert isinstance(result, Counter), "groups should be Counter instance"


def test_grouper_from_local():
    page = LocalHNWebPage(path="./static_hn.html")
    grouper = SiteSourceGrouper(page)
    result = grouper.get_groups()
    assert isinstance(result, Counter), "groups should be Counter instance"
