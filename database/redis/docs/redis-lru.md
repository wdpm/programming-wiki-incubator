# redis LRU

当实际内存超出 maxmemory 时，Redis 提供了几种可选策略 (maxmemory-policy) 来让用户自己决定该如何腾出新的空间以继续提供读写服务。
- noeviction 不会继续服务写请求 (DEL 请求可以继续服务)，读请求可以继续进行。这样保证不会丢失数据，但是会让线上的业务不能持续进行。这是默认的淘汰策略。
- volatile-lru 尝试淘汰设置了过期时间的 key，最少使用的 key 优先被淘汰。没有设置过期时间的 key 不会被淘汰，这样保证需要持久化的数据不会突然丢失。
- volatile-ttl 跟上面一样，除了淘汰的策略不是 LRU，而是 key 的剩余寿命 ttl 的值，ttl 越小越优先被淘汰。
- volatile-random 跟上面一样，不过淘汰的 key 是过期 key 集合中随机的 key。
- allkeys-lru 区别于 volatile-lru，这个策略要淘汰的 key 对象是全体的 key 集合，而不只是过期的 key 集合。这意味着没有设置过期时间的 key 也会被淘汰。
- allkeys-random 跟上面一样，不过淘汰的策略是随机的 key。
- volatile-xxx 策略只会针对带过期时间的 key 进行淘汰，allkeys-xxx 策略会对所有的 key 进行淘汰。
  - 如果只是拿 Redis 做缓存，应该使用 allkeys-xxx，客户端写缓存时不必携带过期时间。
  - 如果还想同时使用 Redis 的持久化，那使用 volatile-xxx 策略，可以保留没有设置过期时间的 key，它们是永久的 key 不会被 LRU 算法淘汰。

## LRU 算法
链表+Hash

## 近似 LRU 算法
Redis 为实现近似 LRU 算法，它给每个 key 增加了一个额外的小字段，这个字段的长度是 24 个 bit，也就是最后一次被访问的时间戳。

当执行写操作时，发现内存超出 maxmemory，就会执行一次 LRU 淘汰算法。
- 随机采样出 5 个 key，淘汰掉最旧的 key，如果淘汰后内存还是超出 maxmemory，那就继续随机采样淘汰，直到内存低于 maxmemory 为止。
- 如何采样看 maxmemory-policy 配置，如果是 allkeys 就从所有的 key 字典中随机，如果是 volatile 就从带过期时间的 key 字典中随机。
- 每次采样多少个 key 看的是 maxmemory_samples 的配置，默认为 5