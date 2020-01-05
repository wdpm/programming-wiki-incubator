# 文章投票网站设计

## 文章投票
两个有序集合来有序地存储文章：
- 第一个有序集合的成员为文章ID，分值为文章的发布时间；
```bash
article:123456 2019-12-23
```
- 第二个有序集合的成员为文章ID，分值为文章的评分。
```bash
article:123456 100.78
```

为防止用户对同一篇文章多次投票，需要为每篇文章记录一个已投票用户名单。
使用一个集合来存储所有已投票用户的ID
```bash
voted:123456
------------
user:1
user:2
user:3
```

当用户给文章123456投票时，需要执行
- 文章123456的已投票用户名单增加一个user:XXX记录；
- 提升文章123456的评分。

## 发布+获取文章

1. 生成自增文章ID
2. 使用hash保存文章，key为article:ID，value为一个含有多个K-V对的对象。

## 取出评分高的文章
```
start=(page-1)* size_per_page
end=start+size_per_page-1
```
1. 根据范围index获取文章id
2. 然后根据文章id获取对应文章的详细信息

## 文章分组
使用set类型创建分组，每个分组里面保存的是文章id。
```
article = 'article:' + article_id
for group in to_add:
    conn.sadd('group:' + group, article)
for group in to_remove:
    conn.srem('group:' + group, article)
```
```
group:programming
-----------------
article:1
article:2
article:3
```