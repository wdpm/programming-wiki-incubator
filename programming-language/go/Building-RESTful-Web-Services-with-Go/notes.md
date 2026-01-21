# notes

> https://github.com/mingrammer/go-web-framework-stars

## chapter 01

API test:

```bash
go install github.com/narenaryan/romanserver
# check GOPATH: bin/romanserver.exe
```

```http request
GET http://localhost:8000/roman_number/3
"III"
```

```http request
GET http://localhost:8000/roman_number/12
404 - Not Found
```

```http request
GET http://localhost:8000/roman_no_api/12
400 - Bad request
```

supervisor:

```bash
sudo apt-get install -y supervisor

# refer: /etc/supervisor/supervisord.conf
nano /etc/supervisor/conf.d/goproject.conf
```

```bash
root@DESKTOP-QLDBOG2:/etc/supervisor# supervisorctl
supervisor> help

default commands (type help <topic>):
=====================================
add    exit      open  reload  restart   start   tail
avail  fg        pid   remove  shutdown  status  update
clear  maintail  quit  reread  signal    stop    version
```

```bash
# supervisorctl reload
# supervisorctl status
romanserver                      RUNNING   pid 1140, uptime 0:07:00
```

最后的 gulp 自动化不实用，理解即可。

## chapter 02

强大的路由器 1

```bash
go get github.com/julienschmidt/httprouter
```

强大的路由器 2

```bash
go get -u github.com/gorilla/mux
```

## chapter 03

中间件与 RPC。

> Chapter03/cityAPI.go

```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8000/city -d '{"name":"New York","area":304}'

curl -H "Content-Type: application/json" -X POST http://localhost:8000/city -d '{"name":"Boston","area":89}'
```

> multipleMiddleware.go

```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8000/city -d '{"name":"Boston","area":89}'
```

```
2023/03/05 12:28:58 Currently in the check content type middleware
2023/03/05 12:28:58 Got Boston city with area of 89 sq miles!  
2023/03/05 12:28:58 Currently in the set server time middleware
```

Alice 中间件：简化中间件链式调用的 API 语法。参考：`Chapter03/multipleMiddlewareWithAlice.go`

```bash
go get github.com/justinas/alice
```

Gorilla loggedRouter：内置实用的 logging 日志格式

---
RPC:

自定义 RPC 代码只有在客户端和服务端都用 Go 写的时候才有用（C/S 同构）。为了让 RPC 服务端被多个服务消费，需要定义 JSON RPC over HTTP。
然后，任何其他编程语言都可以发送一个 JSON 字符串并得到 JSON 作为结果。

RPC 面向的是函数调用，区别于 REST 面向的是资源的操作。

> Chapter03/RPCServer.go 代码中注意 port 不要使用 1234，而是更大值的端口，避免权限错误。

JSONRPC:

```bash
curl -X POST http://localhost:12345/rpc \
 -H 'cache-control: no-cache' \
 -H 'content-type: application/json' \
 -d '{
 "method": "JSONServer.GiveBookDetail",
 "params": [{"Id": "1234"}],
 "id": "1"
}'
```

注意请求数据中的这部分：

```
 "params": [{"Id": "1234"}],
 "id": "1"
```

Id 1234 对应 args.Id：

```go
type Args struct {
Id string
}
```

"id": "1" 意义不明？经过测试，缺失这个 id 不会拿到正确的响应。

```bash
{"result":{"Id":"1234","Name":"In the sunburned country","Author":"Bill Bryson"},"error":null,"id":"1"}
```

## chapter 04

- go‑restful
- gin
- Revel

Make sure your GOPROXY setting value can be reached.

```bash
apt-get install sqlite3 libsqlite3-dev
go get github.com/emicklei/go-restful
go get github.com/mattn/go-sqlite3
```

### gin

```bash
go get gopkg.in/gin-gonic/gin.v1
```

go run `railAPIGin/main.go`:

POST

```bash
$ curl -X POST \
>  http://localhost:8000/v1/stations \
>  -H 'cache-control: no-cache' \
>  -H 'content-type: application/json' \
>  -d '{"name":"Brooklyn","opening_time":"8:12:00",
> "closing_time":"18:23:00"}'

{"result":{"id":1,"name":"Brooklyn","opening_time":"8:12:00","closing_time":"18:23:00"}}
```

GET

```bash
CURL -X GET "http://localhost:8000/v1/stations/1"
```

DELETE

```bash
CURL -X DELETE "http://localhost:8000/v1/stations/1"
```

Gin log

```bash
[GIN] 2023/03/05 - 17:59:25 | 200 |   24.046913ms |             ::1 | POST     "/v1/stations"
[GIN] 2023/03/05 - 18:02:31 | 200 |      3.8567ms |             ::1 | GET      "/v1/stations/1"
[GIN] 2023/03/05 - 18:02:57 | 200 |      6.2644ms |             ::1 | DELETE   "/v1/stations/1"
```

### Revel

Revel 是一个很厚重的 web 框架，对比 django。因此简介是无法了解这个框架的。

install revel:

```bash
go get github.com/revel/revel
go get github.com/revel/cmd/revel
```

run

```bash
revel run github.com/narenaryan/railAPIRevel
```

test api:

```bash
CURL -X GET "http://127.0.0.1:8000/v1/trains/1"
```

总结：

- 当您需要端到端时使用 Revel.go Web 应用程序（模板和 UI），
- 使用 Gin 快速创建 REST 服务，
- 并在 API 的性能至关重要时使用 go‑rest

## chapter 05

install mongodb org in wsl2 ubuntu22.04:
> https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-ubuntu/

```bash
go get gopkg.in/mgo.v2
```

Boosting the querying performance with indexing

利用索引加速数据查询。

创建 index:

```bash
db.movies.createIndex({year: 1})
```

```bash
db.movies.find({year: {$lt: 2010}}).explain("executionStats")
```

---

迷你电商购物网站系统。

## chapter 06

Working with Protocol Buffers and GRPC

- Protocol buffers introduction
- Format of the protocol buffers
- Compilation process of a protobuf
- GRPC, a modern RPC library
- Bidirectional streaming with GRPC

install proto3.3:

```bash
# Make sure you grab the latest version
curl -OL https://github.com/google/protobuf/releases/download/v3.3.0/protoc-3.3.0-linux-x86_64.zip
# Unzip
unzip protoc-3.3.0-linux-x86_64.zip -d protoc3
# Move only protoc* to /usr/bin/
sudo mv protoc3/bin/protoc /usr/bin/protoc
```

compile .proto example file

```bash
protoc --go_out=. *.proto
```

会遇到缺失 protoc-gen-go 的问题，解决办法：

```bash
go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest

# check your GOPATH, make sure have protoc-gen-go 
```

最后，关键一步，将你的 GOPATH 添加到系统变量（注意不是用户变量）。

然后使用 Chapter06/protobufs 例子代码进行测试。很容易生成 json 格式的字符串表示。

protobuf 比 json 的优势在于 protobuf 更加轻量，而且也能轻易地转化为 json 表示。

### GRPC

- JSON RPC => 专门传输 JSON 的 RPC 应用。
- Google RPC => 专门传输 protocol buffers 的 RPC 应用

```bash
go get google.golang.org/grpc
go get -u github.com/golang/protobuf/protoc-gen-go
```

GRPC 的优点：

- GRPC uses HTTP/2, which is a binary protocol => HTTP2
- Header compression is possible in HTTP/2, which means less overhead => 头部压缩
- We can multiplex many requests on one connection => 连接复用
- Usage of protobufs for strict typing of data => 强数据类型
- Streaming of requests or responses is possible instead of request/response transactions => 流化

```bash
#protoc -I datafiles/ datafiles/transaction.proto --go_out=plugins=grpc:datafiles

# not work because of compatibility: mustEmbedUnimplementedMoneyTransactionServer
protoc -I datafiles/ datafiles/transaction.proto --go-grpc_out=:datafiles
```

书中这行命令无法执行。解决方案有两个：

第一个：在编译时指定不强制 implement servers，放宽限制。

```bash
# work
protoc -I datafiles/ datafiles/transaction.proto --go-grpc_out=require_unimplemented_servers=false:datafiles
```

第二个：在 server side 明确指示方法不实现。

```
// server is used to create MoneyTransactionServer.
type server struct {
	pb.UnimplementedMoneyTransactionServer
}
```

关联源码：

```
// UnimplementedMoneyTransactionServer must be embedded to have forward compatible implementations.
type UnimplementedMoneyTransactionServer struct {
}
```

详细代码参考：Chapter06/grpc_example

---

### 双向消息交流

```
protoc --go_out=. *.proto
protoc -I datafiles/ datafiles/transaction.proto --go-grpc_out=require_unimplemented_servers=false:datafiles
```

## chapter 07

goal:

- Implementing a URL shortening service with PostgreSQL and a Base62 algorithm
- Exploring the JSON store in PostgreSQL
- Introducing gorm, a powerful ORM for Go
- Implementation of an e-commerce REST API

install postgreSQL in ubuntu
> https://learn.microsoft.com/en-us/windows/wsl/tutorials/wsl-database#install-postgresql

```bash
# optional: add repos or apt add key

sudo apt install postgresql postgresql-contrib
psql --version
sudo passwd postgres
# 123456
```

两种登陆 shell 的方式：

```bash
# switch user
sudo su - postgres
# then login
psql
```

---

```bash
sudo -u postgres psql
```

不建议直接使用 postgres 用户，更改它的密码（123456）。这里，新建 user

```bash
CREATE ROLE wdpm with LOGIN PASSWORD '123456';
ALTER USER wdpm CREATEDB CREATEROLE;
```

现在，需要设置，支持远程登录。

- 主要是设置 hba.conf （0.0.0.0.0 md5, 不要用 scram 算法）以及主 postgres.conf （localhost="*"）。

> https://www.cybertec-postgresql.com/en/postgresql-on-wsl2-for-windows-install-and-setup/

尝试登录：

```bash
wdpm  ~  ♥ 22:08  psql -U postgres -d postgres -h 172.20.218.221
用户 postgres 的口令：
psql (9.5.17, 服务器 14.7 (Ubuntu 14.7-0ubuntu0.22.04.1))
WARNING: psql major version 9.5, server major version 14.
         Some psql features might not work.
SSL 连接（协议：TLSv1.2，密码：ECDHE-RSA-AES256-GCM-SHA384，密钥位：256，压缩：关闭 )
输入 "help" 来获取帮助信息.
```

- 172.20.218.221 是 WSL2 ubuntu 的一个短暂的 IP。这个 IP 每次重启电脑都会改变。

---

PostgreSQL also allows JSON storage (called the JSON store) past version 9.2.

postgres 支持 JSON 形式保存。

## chapter 08

cli tool & grequests

github API

## chapter 09

- Introducing Go Kit, a microservice toolkit in Go
- Creating a REST API with Go Kit
- Adding logging to the API
- Adding instrumentation to the API

encryptService/, 对于 helpers 目录

1. 首先创建 models。定义数据结构
2. 然后定义 jsonutils，用于传输 RQ 时的编码，以及使用 RS 时的解码
3. implementations.go 是加密解密的实现
4. endpoints.go 是定义服务对外暴露的端口。

```bash
go run main.go
```

```bash
$ curl -X POST -d '{"key":"111023043350789514532147","text":"I am A Message"}' localhost:8080/encrypt
{"message":"8/+JCfTb+ibIjzQtmCo=","error":""}
```

```bash
curl -X POST -d '{"key":"111023043350789514532147","message":"8/+JCfTb+ibIjzQtmCo="}' localhost:8080/decrypt
{"text":"I am A Message","error":""}
```

---

添加 logging 后，再次测试 API，console 输出：

```bash
method=encrypt key=111023043350789514532147 text="I am A Message" output="8/+JCfTb+ibIjzQtmCo=" err=null took=0s
method=decrypt key=111023043350789514532147 message="8/+JCfTb+ibIjzQtmCo=" output="I am A Message" err=null took=0s
```

took=0s，打印了等于没打印。

---

instrumentation (metrics), 使用 "github.com/go-kit/kit/metrics" 来测量指标。

## chapter 10

nginx 的使用。似乎内容和之前有些重复，作者写的比较随意。

## chapter 11

API Gateway:

- authentication,
- logging,
- rate limiting

### Add api to Kong

```bash
Make a POST request from Postman to the Kong admin URL
http://localhost:8001/apis with these fields in the JSON body:

{
    "name": "myapi",
    "hosts": "server1",
    "upstream_url": "http://go-server:3000",
    "uris":["/api/v1"],
    "strip_uri": true,
    "preserve_host": false
}
```

### Add logging to Kong

docker exec -i -t kong /bin/bash
cat /tmp/file.log

```json
{
  "name": "Enable file logging plugin",
  "request": {
    "url": "localhost:8001/apis/myapi/plugins",
    "method": "POST",
    "header": [
      {
        "key": "Content-Type",
        "value": "application/json",
        "description": ""
      }
    ],
    "body": {
      "mode": "raw",
      "raw": "{\n\t\"name\": \"file-log\",\n\t\"config.path\":\"/tmp/file.log\"\n\n}"
    },
    "description": ""
  },
  "response": []
}
```

### API authentication in Kong

- API key-based authentication 基于一个 key
- OAuth2 authentication
- JWT authentication

```json
{
  "id": "664435b8-0f16-40c7-bc7f-32c69eb6c39c",
  "created_at": 1509214422000,
  "key": "89MH58EXzc4xHBO8WZB9axZ4uhZ1vW9d",
  <=====
  "consumer_id": "df024acb-5cbd-4e4d-b3ed-751287eafd36"
}
```

### API rate limiting in Kong

using Kong's rate-limiting plugin

```json
X-RateLimit-Limit-hour →5000
X-RateLimit-Remaining-hour →4990
```

## chapter 12

- How authentication works
- Session-based authentication in Go
- Introducing Redis to store user sessions
- Introduction to JSON Web Tokens (JWT)
- OAuth2 architecture and basics

### session-based

on app memory or external redis

redis info

```bash
root@DESKTOP-QLDBOG2:~# redis-cli -r 1 -i 1 info
# Server
redis_version:6.0.16
redis_git_sha1:00000000
redis_git_dirty:0
redis_build_id:a3fdef44459b3ad6
redis_mode:standalone
os:Linux 5.15.90.1-microsoft-standard-WSL2 x86_64
arch_bits:64
multiplexing_api:epoll
atomicvar_api:atomic-builtin
gcc_version:11.2.0
process_id:1063
run_id:99aba371bb04b49938b31aca1b3cce5c5c63b734
tcp_port:6379
uptime_in_seconds:488
uptime_in_days:0
hz:10
configured_hz:10
lru_clock:1303923
executable:/usr/bin/redis-server
config_file:/etc/redis/redis.conf
io_threads_active:0

# Clients
connected_clients:1
client_recent_max_input_buffer:8
client_recent_max_output_buffer:0
blocked_clients:0
tracking_clients:0
clients_in_timeout_table:0

# Memory
used_memory:873328
used_memory_human:852.86K
used_memory_rss:20111360
used_memory_rss_human:19.18M
used_memory_peak:934016
used_memory_peak_human:912.12K
used_memory_peak_perc:93.50%
used_memory_overhead:809800
used_memory_startup:809688
used_memory_dataset:63528
used_memory_dataset_perc:99.82%
allocator_allocated:1208048
allocator_active:1478656
allocator_resident:4878336
total_system_memory:8271101952
total_system_memory_human:7.70G
used_memory_lua:41984
used_memory_lua_human:41.00K
used_memory_scripts:0
used_memory_scripts_human:0B
number_of_cached_scripts:0
maxmemory:0
maxmemory_human:0B
maxmemory_policy:noeviction
allocator_frag_ratio:1.22
allocator_frag_bytes:270608
allocator_rss_ratio:3.30
allocator_rss_bytes:3399680
rss_overhead_ratio:4.12
rss_overhead_bytes:15233024
mem_fragmentation_ratio:24.83
mem_fragmentation_bytes:19301504
mem_not_counted_for_evict:0
mem_replication_backlog:0
mem_clients_slaves:0
mem_clients_normal:0
mem_aof_buffer:0
mem_allocator:jemalloc-5.2.1
active_defrag_running:0
lazyfree_pending_objects:0

# Persistence
loading:0
rdb_changes_since_last_save:5
rdb_bgsave_in_progress:0
rdb_last_save_time:1679025035
rdb_last_bgsave_status:ok
rdb_last_bgsave_time_sec:-1
rdb_current_bgsave_time_sec:-1
rdb_last_cow_size:0
aof_enabled:0
aof_rewrite_in_progress:0
aof_rewrite_scheduled:0
aof_last_rewrite_time_sec:-1
aof_current_rewrite_time_sec:-1
aof_last_bgrewrite_status:ok
aof_last_write_status:ok
aof_last_cow_size:0
module_fork_in_progress:0
module_fork_last_cow_size:0

# Stats
total_connections_received:4
total_commands_processed:27
instantaneous_ops_per_sec:0
total_net_input_bytes:585
total_net_output_bytes:100431
instantaneous_input_kbps:0.00
instantaneous_output_kbps:0.00
rejected_connections:0
sync_full:0
sync_partial_ok:0
sync_partial_err:0
expired_keys:0
expired_stale_perc:0.00
expired_time_cap_reached_count:0
expire_cycle_cpu_milliseconds:7
evicted_keys:0
keyspace_hits:2
keyspace_misses:0
pubsub_channels:0
pubsub_patterns:0
latest_fork_usec:0
migrate_cached_sockets:0
slave_expires_tracked_keys:0
active_defrag_hits:0
active_defrag_misses:0
active_defrag_key_hits:0
active_defrag_key_misses:0
tracking_total_keys:0
tracking_total_items:0
tracking_total_prefixes:0
unexpected_error_replies:0
total_reads_processed:32
total_writes_processed:28
io_threaded_reads_processed:0
io_threaded_writes_processed:0

# Replication
role:master
connected_slaves:0
master_replid:1900184a5c4c427b2ddfb53743bafae0d18bc432
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0

# CPU
used_cpu_sys:0.292458
used_cpu_user:0.335367
used_cpu_sys_children:0.000000
used_cpu_user_children:0.000000

# Modules

# Cluster
cluster_enabled:0

# Keyspace
db0:keys=2,expires=0,avg_ttl=0
```

### JWT

Format:
header + payload(claims) + signature => JWT

```go
signature = HMACSHA256(
base64UrlEncode(header) + "." +
base64UrlEncode(payload),
secret)
```

JWT 一个显著的优点是，不再需要在服务端维护一个 session 表，来记录用户登录的状态。
同时，明显的缺点就是 JWT 的负载信息会比 session 要略大。这是一个权衡点。

### OAuth

1. 获取 grant token
2. 获取 access token
3. 携带 access token 来访问 API

这本书代码目录 jwtAuth 中只实现了 2&3 步，省略了第一步的 grant 的获取。

小结：
- Authentication can be of two types: session-based or token-based.