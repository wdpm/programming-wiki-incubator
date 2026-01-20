# redis 事务
## 事务范式
```
begin();
try {
    command1();
    command2();
    ....
    commit();
} catch(Exception e) {
    rollback();
}
```
Redis 在形式上分别是multi/exec/discard。

所有的指令在 exec 之前不执行，缓存在服务器的一个事务队列中，服务器一旦收到exec 指令，才开执行整个事务队列，
执行完毕后一次性返回所有指令的运行结果。因为Redis 的单线程特性，可以保证他们能得到「原子性」执行。

## 原子性？
```bash
> multi
OK
> set books iamastring
QUEUED
> incr books
QUEUED
> set poorman iamdesperate
QUEUED
> exec
1) OK
2) (error) ERR value is not an integer or out of range
3) OK
> get books
"iamastring"
> get poorman
"iamdesperate
```

事务在遇到指令执行失败后，后面的指令还继续执行，所以poorman 的值能继续得到设置。
所以，Redis 的事务不能算「原子性」，仅仅是满足了事务的「隔离性」。

## discard(丢弃)
```bash
> get books
(nil)
> multi
OK
> incr books
QUEUED
> incr books
QUEUED
> discard
OK
> get books
(nil)
```
discard 指令，用于丢弃事务缓存队列中的所有指令，在 exec 执行之前。

## watch
```
while True:
    do_watch()
    commands()
    multi()
    send_commands()
    try:
        exec()
        break
    except WatchError:
        continue # 重试
```
```bash
> watch books
OK
> incr books # 被修改了
(integer) 1
> multi
OK
> incr books
QUEUED
> exec # 事务执行失败
(nil)
```