import sys
from typing import TextIO, Iterable, List, Optional
from urllib import parse

import requests
from lxml import etree


class Post:
    """HN(https://news.ycombinator.com/) 上的条目

    :param title: 标题
    :param link: 链接
    :param points: 当前得分
    :param comments_cnt: 评论数
    """

    def __init__(self, title: str, link: str, points: str, comments_cnt: str):
        self.title = title
        self.link = link
        self.points = int(points)
        self.comments_cnt = int(comments_cnt)


from abc import ABC, abstractmethod


class PostFilter(ABC):
    """抽象类：定义如何过滤帖子结果"""

    @abstractmethod
    def validate(self, post: Post) -> bool:
        """判断帖子是否应该被保留"""


class DefaultPostFilter(PostFilter):
    """保留所有帖子"""

    def validate(self, post: Post) -> bool:
        return True


class GithubPostFilter(PostFilter):
    def validate(self, post: Post) -> bool:
        parsed_link = parse.urlparse(post.link)
        return parsed_link.netloc == 'github.com'


class GithubNBloomPostFilter(PostFilter):
    def validate(self, post: Post) -> bool:
        parsed_link = parse.urlparse(post.link)
        return parsed_link.netloc in ('github.com', 'bloomberg.com')


class HNTopPostsSpider:
    """抓取 HackerNews Top 内容条目

    :param limit: 限制条目数，默认为 5
    :param post_filter: 过滤结果条目的算法，默认为保留所有
    """

    items_url = 'https://news.ycombinator.com/'

    # Optional[PostFilter] 这里使用了PostFilter作为类型注解，如果没有抽象出PostFilter抽象类，这里填啥？
    def __init__(self, limit: int = 5, post_filter: Optional[PostFilter] = None):
        self.limit = limit
        self.post_filter = post_filter or DefaultPostFilter()

    def fetch(self) -> Iterable[Post]:
        resp = requests.get(self.items_url)

        # 使用 XPath 可以方便的从页面解析出你需要的内容，以下均为页面解析代码
        # 如果你对 xpath 不熟悉，可以忽略这些代码，直接跳到 yield Post() 部分
        html = etree.HTML(resp.text)
        items = html.xpath('//table[@class="itemlist"]/tr[@class="athing"]')
        counter = 0
        for item in items:
            if counter >= self.limit:
                break

            node_title = item.xpath('./td[@class="title"]/a')[0]
            node_detail = item.getnext()
            points_text = node_detail.xpath('.//span[@class="score"]/text()')
            comments_text = node_detail.xpath('.//td/a[last()]/text()')[0]
            link = node_title.get('href')

            post = Post(
                title=node_title.text,
                link=link,
                # 条目可能会没有评分
                points=points_text[0].split()[0] if points_text else '0',
                comments_cnt=comments_text.split()[0] if comments_text.endswith('comments') else '0',
            )
            # 使用测试方法来判断是否返回该帖子
            if self.post_filter.validate(post):
                counter += 1
                yield post


def write_posts_to_file(posts: List[Post], fp: TextIO, title: str):
    """负责将帖子列表写入文件"""
    fp.write(f'# {title}\n\n')
    for i, post in enumerate(posts, 1):
        fp.write(f'> TOP {i}: {post.title}\n')
        fp.write(f'> 分数：{post.points} 评论数：{post.comments_cnt}\n')
        fp.write(f'> 地址：{post.link}\n')
        fp.write('------\n')


def get_hn_top_posts(fp: Optional[TextIO] = None):
    """获取 HackerNews 的 Top 内容，并将其写入文件中

    :param fp: 需要写入的文件，如未提供，将往标准输出打印
    """
    dest_fp = fp or sys.stdout
    # crawler = HNTopPostsSpider()
    # crawler = HNTopPostsSpider(post_filter=GithubPostFilter())
    crawler = HNTopPostsSpider(post_filter=GithubNBloomPostFilter())
    write_posts_to_file(list(crawler.fetch()), dest_fp, title='Top news on HN')


def main():
    get_hn_top_posts()


if __name__ == '__main__':
    main()
