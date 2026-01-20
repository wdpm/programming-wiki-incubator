# redis GeoHash
问题：给定一个元素的坐标，计算这个坐标附近的其它元素。

## 关系型数据库的解决思路
![](./assets/geo-point-with-r.png)

以(x0，y0)作为原点，以半径r画出一个外围的矩形。
```
select id from positions where x0-r < x < x0+r and y0-r < y < y0+r
```
数据表在经纬度坐标加上双向复合索引 (x, y)，可以最大优化查询性能。

## GeoHash 算法
将二维的经纬度数据映射到一维整数，这样所有元素都挂载到一条线上，距离靠近的二维坐标映射到一维后的点之间距离也会很接近。

映射算法的实现：
- 将整个地球看成二维平面，划分成一系列正方形的方格，类似围棋棋盘。
- 所有的地图元素坐标都放置于唯一的方格中。方格越小，坐标越精确。
- 对这些方格进行整数编码，越是靠近的方格编码越是接近。
  - 切蛋糕法。设想一个正方形的蛋糕，二刀均分分成四块小正方形，分别标记为 00,01,10,11 四个二进制整数。
  - 然后对每一个小正方形继续用二刀法切割，这时每个小小正方形就可以使用 4bit 的二进制整数予以表示。
 
## Redis 的 Geo 指令
使用 Redis 进行 Geo 查询时，它的内部结构实际是一个 zset(skiplist)。
通过 zset 的 score 排序可以得到坐标附近的其它元素，通过将 score 还原成坐标值就可以得到元素的原始坐标。

```
geoadd company 116.48105 39.996794 A
geoadd company 116.489033 40.007669 B

geodist company A B km

# geohash 对二维坐标进行的一维映射有损，通过映射再还原回来的值会出现较小的差别。
geopos company A

geohash company A
```
> 访问 http://geohash.org/XXXX

```
# 范围 20 km以内最多 3 个元素按距离升序排列，不会排除自身
georadiusbymember company A 20 km count 3 asc
A
B
C
```

## 注意
如果数据量过大，可以对 Geo 数据进行拆分，按国家拆分、按省拆分，按市拆分，按区拆分。显著降低单个 zset 集合的大小。