# redis 过期策略

## 设置过期时间
- `EXPIRE <KEY> <TTL>` 设置生存时间为ttl s
- `PEXPIRE <KEY> <TTL>` 设置生存时间为ttl ms
- `EXPIREAT <KEY> <TIMESTAMP>` 设置生存时间为TIMESTAMP的秒数时间戳
- `PEXPIREAT <KEY> <TIMESTAMP>` 设置生存时间为TIMESTAMP的毫秒数时间戳

## 缓存雪崩
> 设想一个大型的 Redis 实例中所有的 key 在同一时间过期了，会出现怎样的结果？

策略：过期时间随机化。如果有大批量的 key 过期，给过期时间设置一个随机范围，不能全部在同一时间过期。

## key 的过期机制
定期扫描删除+访问时惰性删除

## 从库的过期策略
从库不会进行过期扫描。主库在 key 到期时，在 AOF 文件里增加一条 del 指令，同步到所有的从库，从库通过执行这条 del 指令来删除过期的 key。