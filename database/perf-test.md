
## AB
```
ab -n 1000 -c 100 http://127.0.0.1:8000/
```
- -n 1000: 总请求数（共发送 1000 个请求）
- -c 100: 并发数（同时保持 100 个连接）

结果示例：
```bash
Server Software:        Werkzeug/0.9.6
Server Hostname:        127.0.0.1
Server Port:            8000

Document Path:          /
Document Length:        7398 bytes

Concurrency Level:      100
Time taken for tests:   4.911 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      7554000 bytes
HTML transferred:       7398000 bytes
Requests per second:    203.61 [#/sec] (mean)
Time per request:       491.146 [ms] (mean)
Time per request:       4.911 [ms] (mean, across all concurrent requests)
Transfer rate:          1501.99 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.1      0       5
Processing:    19  466  82.5    489     497
Waiting:       19  466  82.5    489     497
Total:         24  467  81.5    489     497

Percentage of the requests served within a certain time (ms)
  50%    489
  66%    490
  75%    491
  80%    491
  90%    492
  95%    496
  98%    497
  99%    497
 100%    497 (longest request)
```

- SQlslap 是MySQL自带的性能测试工具命令
- https://github.com/akopytov/sysbench
- https://github.com/jackfrued/Python-100-Days/blob/master/Day91-100/93.MySQL%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96.md