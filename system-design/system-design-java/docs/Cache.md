# Cache
缓存是一种硬件或软件组件，用于存储数据，以便能够更快地满足将来对该数据的请求。

一般会将缓存数据放于内存型数据库中。
## Cache Types
- App Server Cache
- Distributed Cache(借助一致哈希算法)
- CDN

## Cache Invalidation
缓存失效机制，或者称为缓存写策略。

- 当数据在数据库被更新后，缓存的相应旧数据应该失效，然后同步最新的数据。
- 类型
  - Write Through Cache
    - 数据被写到缓cache，与数据被写到DB，并行或者串行进行，两者顺序也不重要。重要的是当两者I/O都完成时，才认为更新完成。
    - 优点：确保数据在DB存储中，并且在缓存中断时不会丢失。
    - 缺点：写数据将增加延迟，因为每次必须写入两个位置，cache和DB。
    - 适用场景：频繁写入和重新读取数据的应用程序。结果：略高的写入延迟，但较低的读取延迟。
  - Write Around Cache
    - 数据被写到DB，不写到cache。一旦确认到DB的I/O完成，即认为更新完成。
    - 优点：有助于降低cache的数据量。
    - 缺点：读取最近写入的数据将导致缓存未命中（因此延迟更高），因为这时只能从较慢的DB读取数据。
    - 适用场景：不经常重新读取最近写入的数据的应用程序。结果：较低的写入延迟，但较高的读取延迟。
  - Write Back Cache
    - 数据先被写到cache，然后确认I/O完成。然后，数据通常也会被异步地写入DB(这一步其实逻辑比较复杂)，但完成确认不会因此被阻止。
    - 优点：对于写密集型应用程序，低延迟和高吞吐量。
    - 缺点：存在数据丢失风险，因为在将数据持久化到DB之前，缓存可能会故障（因此会遭受数据丢失）。
    - 适用场景：混合负载，读/写具有相似的响应时间级别。可以添加复制写操作来减少数据丢失的可能性。

### 三种方案的抉择
- 能否容忍数据丢失？
  - 能。可以考虑Write Back。
  - 不能。写延迟和读延迟哪一个更重要？
    - 写延迟要较低。=> Write Around
    - 读延迟要较低。=> Write Through

## Cache Eviction Policies
缓存驱逐策略。
- FIFO *
- LIFO
- LRU(Least Recently Used) *
- MRU(Most Recently Used)
- LFU(Least Frequently Used)
- Random Replacement