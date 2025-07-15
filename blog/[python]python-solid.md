# Python SOLID

## SRP 单一职责

SRP：一个类应该尽可能小。

下面以一个爬虫程序进行说明。

```python
import sys
from typing import Iterable, TextIO

import requests
from lxml import etree


class Post:
    """HackerNew 上的条目

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


class HNTopPostsSpider:
    """ 抓取 HackerNews Top 内容条目

    :param fp: 存储抓取结果的目标文件对象
    :param limit: 限制条目数，默认为 5
    """

    items_url = 'https://news.ycombinator.com/'
    file_title = 'Top news on HN'

    # 注意：fp 入参应该在重构后去掉
    def __init__(self, fp: TextIO, limit: int = 5):
        self.fp = fp
        self.limit = limit

    # 注意：IO 逻辑不应该出现于 HNTopPostsSpider，即爬虫类不应该关心文件读写
    def write_to_file(self):
        """以纯文本格式将 Top 内容写入文件"""
        self.fp.write(f'# {self.file_title}\n\n')
        # enumerate 接收第二个参数，表示从这个数开始计数（默认为 0）
        for i, post in enumerate(self.fetch(), 1):
            self.fp.write(f'> TOP {i}: {post.title}\n')
            self.fp.write(f'> 分数：{post.points} 评论数：{post.comments_cnt}\n')
            self.fp.write(f'> 地址：{post.link}\n')
            self.fp.write('------\n')

    def fetch(self) -> Iterable[Post]:
        """ 从 HN 抓取 Top 内容

        :return: 可迭代的 Post 对象
        """
        resp = requests.get(self.items_url)

        html = etree.HTML(resp.text)
        items = html.xpath('//table[@class="itemlist"]/tr[@class="athing"]')
        for item in items[: self.limit]:
            node_title = item.xpath('./td[@class="title"]/a')[0]
            node_detail = item.getnext()
            points_text = node_detail.xpath('.//span[@class="score"]/text()')
            comments_text = node_detail.xpath('.//td/a[last()]/text()')[0]

            yield Post(
                title=node_title.text,
                link=node_title.get('href'),
                # 条目可能会没有评分
                points=points_text[0].split()[0] if points_text else '0',
                comments_cnt=comments_text.split()[0],
            )


def main():
    # 因为 HNTopPostsSpider 接收任何 file-like 的对象，所以我们可以把 sys.stdout 传进去
    # 实现往控制台标准输出打印的功能
    crawler = HNTopPostsSpider(sys.stdout)
    crawler.write_to_file()


if __name__ == '__main__':
    main()
```

- Post 是数据类，没有复杂逻辑，只有属性。
- HNTopPostsSpider 是爬虫类，fetch() 是爬虫核心实现逻辑。但是，这个类包含了 write_to_file 等文件 IO 逻辑。这里就是缺点。
- 注意：因为 HTML 文档结构变化的原因，上面的爬虫代码拿不到正常结果列表。

为了让代码职责更加集中，需要对上面代码进行重构。具体有两种思路：

- 将大类拆为小类
    - 大部分倾向于这种方式，因为类本身可以拥有属性（states）。
- 将大类拆为小函数
    - 一般情况下，不推荐小函数模式。因为函数很可能会参数爆炸，需要疯狂传参数。
    - 对于通用的工具函数，可以使用小函数。因为不依赖任何状态，纯函数实现效果不错。

### 大类拆成小类

```python
import sys
from typing import List, Optional, TextIO, Iterable

import requests
from lxml import etree


class Post:
    """HackerNew 上的条目

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


class HNTopPostsSpider:
    """ 抓取 HackerNews Top 内容条目

    :param limit: 限制条目数，默认为 5
    """

    items_url = 'https://news.ycombinator.com/'

    def __init__(self, limit: int = 5):
        self.limit = limit

    def fetch(self) -> Iterable[Post]:
        resp = requests.get(self.items_url)

        html = etree.HTML(resp.text)
        items = html.xpath('//tr[@id="bigbox"]//tr[contains(@class, "athing")]')
        for item in items[: self.limit]:
            # . 开头的路径：从当前节点开始相对定位。
            node_title = item.xpath('.//span[@class="titleline"]/a')[0]
            node_detail = item.getnext()
            points_text = node_detail.xpath('.//span[@class="score"]/text()')
            comments_text = node_detail.xpath('.//span[@class="subline"]/a[last()]/text()')[0]

            yield Post(
                title=node_title.text,
                link=node_title.get('href'),
                points=points_text[0].split()[0] if points_text else '0',
                comments_cnt=comments_text.split()[0],
            )


class PostsWriter:
    """负责将帖子列表写入到文件"""

    def __init__(self, fp: TextIO, title: str):
        self.fp = fp
        self.title = title

    def write(self, posts: List[Post]):
        self.fp.write(f'# {self.title}\n\n')
        # enumerate 接收第二个参数，表示从这个数开始计数（默认为 0，这里设置为从 1 开始计数）
        for i, post in enumerate(posts, 1):
            self.fp.write(f'> TOP {i}: {post.title}\n')
            self.fp.write(f'> 分数：{post.points} 评论数：{post.comments_cnt}\n')
            self.fp.write(f'> 地址：{post.link}\n')
            self.fp.write('------\n')


def get_hn_top_posts(fp: Optional[TextIO] = None):
    """ 获取 HackerNews 的 Top 内容，并将其写入文件中

    :param fp: 需要写入的文件，如未提供，将往标准输出打印
    """
    dest_fp = fp or sys.stdout
    writer = PostsWriter(dest_fp, title='Top news on HN')
    crawler = HNTopPostsSpider()
    writer.write(list(crawler.fetch()))


def main():
    get_hn_top_posts()


if __name__ == '__main__':
    main()

# Top news on HN
# 
# > TOP 1: Show HN: Refine – A Local Alternative to Grammarly
# > 分数：246 评论数：129
# > 地址：https://refine.sh
# ------
# > TOP 2: Bold Mission to Hunt for Aliens on Venus Is Happening
# > 分数：32 评论数：19
# > 地址：https://gizmodo.com/a-bold-mission-to-hunt-for-aliens-on-venus-is-actually-happening-2000627704
# ------
# > TOP 3: Show HN: Ten years of running every day, visualized
# > 分数：626 评论数：261
# > 地址：https://nodaysoff.run
# ------
# > TOP 4: Let's Learn x86-64 Assembly (2020)
# > 分数：319 评论数：74
# > 地址：https://gpfault.net/posts/asm-tut-0.txt.html
# ------
# > TOP 5: Apple's Browser Engine Ban Persists, Even Under the DMA
# > 分数：277 评论数：141
# > 地址：https://open-web-advocacy.org/blog/apples-browser-engine-ban-persists-even-under-the-dma/
# ------
```

- 这里将 HNTopPostsSpider 的文件 IO 逻辑分离到一个独立的类 PostsWriter，它负责 Post 的读写。
- 这里修复了 XPATH 的失效性问题，这个是爬虫很容易遇到的情况。目标服务器的内容在变化，而你的程序可能很久也不会去改变。
- XPATH 的 DOM 挑选尽量使用包含检测（contains），因为严格 == 检测兼容性很差，添加一个无关的类都会造成程序拿不到数据。

### 大类拆成小函数

```python
def write_posts_to_file(posts: List[Post], fp: TextIO, title: str):
    """负责将帖子列表写入文件"""
    fp.write(f'# {title}\n\n')
    for i, post in enumerate(posts, 1):
        fp.write(f'> TOP {i}: {post.title}\n')
        fp.write(f'> 分数：{post.points} 评论数：{post.comments_cnt}\n')
        fp.write(f'> 地址：{post.link}\n')
        fp.write('------\n')


def get_hn_top_posts(fp: Optional[TextIO] = None):
    """ 获取 HackerNews 的 Top 内容，并将其写入文件中

    :param fp: 需要写入的文件，如未提供，将往标准输出打印
    """
    dest_fp = fp or sys.stdout
    crawler = HNTopPostsSpider()
    write_posts_to_file(list(crawler.fetch()), dest_fp, title='Top news on HN')
```

这里，仅仅是将 PostsWriter 类实现换成纯函数实现。这种方式反映的是数据管道的处理和数据的流动。如果这个函数不太可能被他人复用，那么
这种方式有时反而会加剧认知负荷。因为 utils 函数一旦变多，找起来很麻烦。

## OCP 开闭原则

OCP：对扩展开放，对修改关闭。 解读：核心是找到代码中容易变化的地方，进行管理。

假设我只对 hackernews 中来自 Github 的帖子感兴趣，一个很直接的做法是在 fetch() 中过滤。

```python
class HNTopPostsSpider:
    """ 抓取 HackerNews Top 内容条目

    :param limit: 限制条目数，默认为 5
    """
    ...

    def fetch(self) -> Iterable[Post]:
        """从 HN 抓取 Top 内容"""
        resp = requests.get(self.items_url)

        html = etree.HTML(resp.text)
        items = html.xpath('//table[@class="itemlist"]/tr[@class="athing"]')
        counter = 0
        for item in items:
            if counter >= self.limit:
                break
            ...
            # 只关注来自 github.com 的内容
            parsed_link = parse.urlparse(link)
            if parsed_link.netloc == 'github.com':
                counter += 1
                yield Post(
                    title=node_title.text,
                    link=link,
                    points=points_text[0].split()[0] if points_text else '0',
                    comments_cnt=comments_text.split()[0],
                )
```

这种方式侵入性很强，而且过滤规则无法穷举，也无法预测将来的过滤需求。

有三种方式：

1. 子类继承 + 重写方法，这个方法就是容易变化的地方
    - => 父类定义通用过滤方法，子类重写过滤方法来实现自定义过滤规则
    - => 占用了宝贵的基类，适用于有明确 is-a 关系的情况。
2. 组合 / 依赖注入，这个依赖的字段就是容易变化的地方
    - => 定义一个依赖属性为 postFilter ，外部定制好后传入
    - => 适用于适合抽象为函数式接口（has-a）的情况。
3. 数据驱动，数据就是容易变化的地方
    - => 定义一个表示域名过滤的字段 filter_by_hosts: Optional[List[str]] = None，内部使用它来进行过滤
    - => 数据驱动

### 子类继承 + 重写方法

```python
class HNTopPostsSpider:
    """ 抓取 HackerNews Top 内容条目

    :param limit: 限制条目数，默认为 5
    """

    items_url = 'https://news.ycombinator.com/'

    def __init__(self, limit: int = 5):
        self.limit = limit

    def fetch(self) -> Iterable[Post]:
        resp = requests.get(self.items_url)

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
            if self.interested_in_post(post):
                counter += 1
                yield post

    def interested_in_post(self, post: Post) -> bool:
        """判断是否应该将帖子加入结果中"""
        return True
```

- 父类定义了 interested_in_post() 用于过滤，并提供一个兜底实现，全部返回 True，表示全部满足过滤要求。
- 子类可以覆盖这个过滤方法来定制自己的过滤规则。

```python
class GithubOnlyHNTopPostsSpider(HNTopPostsSpider):
    """只关心来自 Github 的内容"""

    def interested_in_post(self, post: Post) -> bool:
        parsed_link = parse.urlparse(post.link)
        return parsed_link.netloc == 'github.com'


class GithubBloomBergHNTopPostsSpider(HNTopPostsSpider):
    """只关心来自 Github/BloomBerg 的内容"""

    def interested_in_post(self, post: Post) -> bool:
        parsed_link = parse.urlparse(post.link)
        return parsed_link.netloc in ('github.com', 'bloomberg.com')
```

### 字段依赖注入

我们可以先定义好用于过滤的类实现。这里本质上类似于 Mixin。

```python
from abc import ABC, abstractmethod


class Post:
    ...


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


class GithubBloomPostFilter(PostFilter):
    def validate(self, post: Post) -> bool:
        parsed_link = parse.urlparse(post.link)
        return parsed_link.netloc in ('github.com', 'bloomberg.com')
```

同时我们需要修改 HNTopPostsSpider 的构造函数，新增一个字段把具体的 filter 实例传入。

```python
class HNTopPostsSpider:
    """ 抓取 HackerNews Top 内容条目

    :param limit: 限制条目数，默认为 5
    :param post_filter: 过滤结果条目的算法，默认为保留所有
    """

    items_url = 'https://news.ycombinator.com/'

    def __init__(self, limit: int = 5, post_filter: Optional[PostFilter] = None):
        self.limit = limit
        self.post_filter = post_filter or DefaultPostFilter()

    def fetch(self) -> Iterable[Post]:
        resp = requests.get(self.items_url)

        html = etree.HTML(resp.text)
        items = html.xpath('//table[@class="itemlist"]/tr[@class="athing"]')
        counter = 0
        for item in items:
            if counter >= self.limit:
                break

            post = ...
            
            # 使用测试方法来判断是否返回该帖子
            if self.post_filter.validate(post):
                counter += 1
                yield post
```

使用时

```python
crawler = HNTopPostsSpider(post_filter=GithubBloomPostFilter())
write_posts_to_file(list(crawler.fetch()), dest_fp, title='Top news on HN')
```

### 数据驱动

```python
class HNTopPostsSpider:
    """ 抓取 HackerNews Top 内容条目

    :param limit: 限制条目数，默认为 5
    :param filter_by_hosts: 过滤结果的站点列表，默认为 None，代表不过滤
    """

    items_url = 'https://news.ycombinator.com/'

    def __init__(self, limit: int = 5, filter_by_hosts: Optional[List[str]] = None):
        self.limit = limit
        self.filter_by_hosts = filter_by_hosts

    def fetch(self) -> Iterable[Post]:
        resp = requests.get(self.items_url)

        html = etree.HTML(resp.text)
        items = html.xpath('//table[@class="itemlist"]/tr[@class="athing"]')
        counter = 0
        for item in items:
            if counter >= self.limit:
                break

            post = ...
            # 判断链接是否符合过滤条件
            if self._check_link_from_hosts(post.link):
                counter += 1
                yield post

    def _check_link_from_hosts(self, link: str) -> True:
        """检查某链接是否属于所定义的站点"""
        if self.filter_by_hosts is None:
            return True
        parsed_link = parse.urlparse(link)
        return parsed_link.netloc in self.filter_by_hosts
```

- filter_by_hosts 是域名白名单列表
- 新增内部函数 _check_link_from_hosts() 用于执行过滤

这种方式灵活性不是很高，能完成的事情也很有限，但最明显的优点是能够提供一个简单的字段入口，供非编程专业人员来修改软件内部行为。
子类继承方式，和自定义 Filter 类实现，对于他们来说门槛都比较高。而让他们提供一个数组列表，显然简单得多。

使用方式
```python
hosts = ['github.com', 'bloomberg.com']
crawler = HNTopPostsSpider(filter_by_hosts=hosts)
posts = list(crawler.fetch())
```

## LSP 里式替换

解读：子类必须能够完全替代父类。

方法返回类型：LSP 要求子类方法的返回值类型与父类完全一致，或者返回父类结果类型的子类对象。

### 形状、矩形、正方形

```python
from abc import ABC, abstractmethod

class Shape(ABC):
    @abstractmethod
    def area(self) -> float:
        pass

class Rectangle(Shape):
    def __init__(self, width: float, height: float):
        self.width = width
        self.height = height
    
    def area(self) -> float:
        return self.width * self.height

class Square(Shape):
    def __init__(self, side: float):
        self.side = side
    
    def area(self) -> float:
        return self.side ** 2

# 使用父类 Shape 类型
def print_area(shape: Shape):
    print(f"Area: {shape.area()}")

# 子类完全可替换父类
print_area(Rectangle(3, 4))  # 输出: Area: 12
print_area(Square(5))        # 输出: Area: 25
```

- print_area 方法入参要求是宽泛的形状shape，实际入参可以是子类实例，例如Rectangle和Square。

### 反例：企鹅是鸟，但是不能飞

```python
class Bird:
    def fly(self):
        print("Flying...")

class Penguin(Bird):
    def fly(self):
        raise NotImplementedError("Penguins can't fly!")

def make_bird_fly(bird: Bird):
    bird.fly()

make_bird_fly(Bird())      # 正常
make_bird_fly(Penguin())   # 抛出异常，违反 LSP！
```

- `Penguin` 是 `Bird` 的子类，但无法飞行，破坏了父类 `fly()` 的行为约定。
- 改进：将 `Bird` 拆分为 `FlyingBird` 和 `NonFlyingBird`。让fly方法放于`FlyingBird` 中，同时让企鹅继承`NonFlyingBird`。

### 方法参数类型的逆变

```python
class Animal: pass
class Dog(Animal): pass

# 基础兽医（专治狗）
class BasicVet:
    def treat(self, animal: Dog):
        print("Treating a dog")

# 高级兽医（能治所有动物）
class AdvancedVet(BasicVet):
    def treat(self, animal: Animal):
        print("Treating any animal")

basic = BasicVet()
advanced = AdvancedVet()

basic.treat(Dog())        # 正常
advanced.treat(Dog())     # 正常
advanced.treat(Animal())  # 也正常，扩展了能力
```

- 子类方法的参数类型可以是父类参数类型的父类型（逆变），因为调用时实际传入的参数会更宽泛。

### 返回值类型的协变

```python
class Factory:
    def create(self) -> Animal:
        return Animal()

class DogFactory(Factory):
    def create(self) -> Dog:  # 返回值类型更具体（协变）
        return Dog()

factory = Factory()
dog_factory = DogFactory()

animal = factory.create()      # 返回 Animal
dog = dog_factory.create()    # 返回 Dog
```

- 子类方法的返回值类型可以是父类返回值类型的子类型（协变），客户端代码仍能按父类约定使用返回值。

### 类型安全总结

|          | 参数类型         | 返回值类型       |
| :------- | :--------------- | :--------------- |
| **规则** | 应逆变（更宽泛） | 应协变（更具体） |
| **记忆** | "参数要包容"     | "返回要精确"     |



## DIP 依赖反转

全称：Dependency Inversion Principle 依赖反转原则

解读：高层模块和低层模块都应该依赖于抽象，即依赖于接口。但是，这样多一个中间的抽象层。
- 高层和底层模块之间避免直接耦合，而是遵循接口契约。
- 底层可以更换实现，高层代码逻辑不会被破坏，因为它使用的是接口规定的能力，不关心实现细节。

在 Python 中，可以用 abc 模块来定义抽象类。例如：

```python
class HNWebPage(ABC):
    """抽象类：Hacker New 站点页面"""

    @abstractmethod
    def get_text(self) -> str:
        raise NotImplementedError()
```

除 abc 以外，也可以用 Protocol 等技术来完成依赖倒置。Protocol 比抽象类更接近传统的“接口”。

```python
class HNWebPage(Protocol):
    """协议：Hacker News 站点页面"""

    def get_text(self) -> str:
        ...
```

get_text() 定义一个接口的能力，能返回str就行，不关心这个page的具体实现。我们给出下面实现：

```python
class RemoteHNWebPage(HNWebPage):
    """远程页面，通过请求 HN 站点返回内容"""

    def __init__(self, url: str):
        self.url = url

    def get_text(self) -> str:
        resp = requests.get(self.url)
        return resp.text


class LocalHNWebPage(HNWebPage):
    """本地页面，根据本地文件返回页面内容

    :param path: 本地文件路径
    """
    def __init__(self, path: str):
        self.path = path

    def get_text(self) -> str:
        with open(self.path, 'r',encoding='utf-8') as fp:
            return fp.read()
```

利用构造函数的参数来注入具体的page实例

```python
class SiteSourceGrouper:
    """对 HN 页面的新闻来源站点进行分组统计"""

    def __init__(self, page: HNWebPage):
        self.page = page

    def get_groups(self) -> Dict[str, int]:
        """获取 (域名, 个数) 分组"""
        html = etree.HTML(self.page.get_text())
        elems = html.xpath('//table[@class="itemlist"]//span[@class="sitestr"]')

        groups = Counter()
        for elem in elems:
            groups.update([elem.text])
        return groups
```

```python
def main():
    page = RemoteHNWebPage(url="https://news.ycombinator.com/")
    grouper = SiteSourceGrouper(page).get_groups()
    for key, value in grouper.most_common(3):
        print(f'Site: {key} | Count: {value}')

    page = LocalHNWebPage(path="./static_hn.html")
    grouper = SiteSourceGrouper(page).get_groups()
    for key, value in grouper.most_common(3):
        print(f'Site: {key} | Count: {value}')
```

### 易于测试

```python
from hn_site_grouper_I import SiteSourceGrouper, RemoteHNWebPage, LocalHNWebPage
from collections import Counter

from unittest import mock


# hn_site_grouper.requests.get 指定是 mock requests 这个库的 get 方法
@mock.patch('hn_site_grouper.requests.get')
def test_grouper_returning_valid_type(mocked_get):
    """测试 get_groups 是否返回了正确类型"""
    # 这里使用本地内容来替换 requests 的实际 get 请求。
    with open('static_hn.html', 'r', encoding='utf-8') as fp:
        mocked_get.return_value.text = fp.read()
    hn_web_page = RemoteHNWebPage(url='http://example.com')
    grouper = SiteSourceGrouper(hn_web_page)

    result = grouper.get_groups()

    assert isinstance(result, Counter), "groups should be Counter instance"


def test_grouper_from_local():
    page = LocalHNWebPage(path="./static_hn.html")
    grouper = SiteSourceGrouper(page)

    result = grouper.get_groups()

    assert isinstance(result, Counter), "groups should be Counter instance"
```



## ISP 接口隔离

解读：接口设计时不应该包含任何它不需要的方法。接口设计要简单，不要过度设计，方法越少越好。

假如只关心内容，那么只定义一个方法

```python
class ContentOnlyHNWebPage(ABC):
    """抽象类：Hacker New 站点页面（仅提供内容）"""

    @abstractmethod
    def get_text(self) -> str:
        raise NotImplementedError()
```

假如还关心页面大小和页面生成时间，那么新增对应方法

```python
class HNWebPage(ABC):
    """抽象类：Hacker New 站点页面（含元数据）"""

    @abstractmethod
    def get_text(self) -> str:
        raise NotImplementedError()

    @abstractmethod
    def get_size(self) -> int:
        """获取页面大小"""
        raise NotImplementedError()

    @abstractmethod
    def get_generated_at(self) -> datetime.datetime:
        """获取页面生成时间"""
        raise NotImplementedError()
```

到了具体的实现，往往会添加一些内部实现的私有辅助方法

```python
class RemoteHNWebPage(HNWebPage):
    """远程页面，通过请求 HN 站点返回内容"""

    def __init__(self, url: str):
        self.url = url
        self._resp = None
        self._generated_at = None

    def get_text(self) -> str:
        """获取页面内容"""
        self._request_on_demand()
        return self._resp.text

    def get_size(self) -> int:
        """获取页面大小"""
        return len(self.get_text())

    def get_generated_at(self) -> datetime.datetime:
        """获取页面生成时间"""
        self._request_on_demand()
        return self._generated_at

    def _request_on_demand(self):
        """请求远程地址，并避免重复"""
        if self._resp is None:
            self._resp = requests.get(self.url)
            self._generated_at = datetime.datetime.now()
```

对于 LocalHNWebPage ：如果选择继承父类，就要对一些不关心的方法也提供实现（get_size 和 get_generated_at）

```python
class LocalHNWebPage(HNWebPage):
    """本地页面，根据本地文件返回页面内容"""

    def __init__(self, path: str):
        self.path = path

    def get_text(self) -> str:
        with open(self.path, 'r') as fp:
            return fp.read()

    # 对于local而言，实际上不需要get_size
    def get_size(self) -> int:
        return 0

    # 对于local而言，实际上不需要get_generated_at
    def get_generated_at(self) -> datetime.datetime:
        raise NotImplementedError("local web page can not provide generate_at info")
```

对于归档类

```python
class SiteAchiever:
    """将不同时间点的 HN 页面归档"""

    def save_page(self, page: HNWebPage):
        """将页面保存到后端数据库"""
        data = {
            "content": page.get_text(),
            "generated_at": page.get_generated_at(),
            "size": page.get_size(),
        }
        # 将 data 保存到数据库中
```

