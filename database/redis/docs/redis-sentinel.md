# redis 哨兵
## 主观下线
通过超时机制判断某个master是否为主观下线。

## 客观下线
当Sentinel将一个master判定为主观下线之后，为追求客观，它会向其他Sentinel进行询问。通过其他
Sentinel的回答，如果获得足够的已下线判定，那就会标记这个master为客观下线，执行故障转移。

## 故障转移
假设现在有master A， slave B C D
1. 在已下线master A从属下的slaves中，选一个作为新的master。假设选了B
2. 让已下线master A从属下的所有其他slaves C D，将新的master B作为master。
3. 将下线的 A 设置为 B的从服务器。当A 复活后，它会成为 B的从服务器。