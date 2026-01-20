# redis 位图
位图就是bit []。Redis使用SDS来保存bit[]。
- bitcount 用来统计指定位置范围内 1 的个数。
- bitpos 用来查找指定范围内出现的第一个 0 或 1。
- bitfield 支持一次操作多个位。bitfield 有三个子指令，get/set/incrby，都可以对指定位片段读写，最多能处理 64 个连续的位。

## BITCOUNT
### 统计8bit的位数组中1的个数
利用查表法

|bit[]|1 count|
|---|---|
|00000000|0|
|00000001|1|
|...|...|

### 二进制位统计算法
variable-precision SWAR算法。BITCOUNT解决的问题，bit[]中的1的个数，成为“计算汉明重量”。
> TODO swar算法

swar算法一次可以计算32bit的汉民重量，是常数级别的操作。多次执行也是很快。

### Redis BITCOUNT的实现
- 如果未处理的二进制位超过128位，使用swar算法计算，直到少于128位。
- 使用查表法处理少于128位的情况。

## 应用
用户签到记录的功能实现。
- 通过 bitcount 统计用户一共签到了多少天。
- 通过 bitpos 指令查找用户从哪一天开始第一次签到。