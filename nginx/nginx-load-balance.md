# nginx负载均衡
作用于应用层（HTTP)或者传输层（TCP）。
```
http {
	upstream backend {
		server 192.0.2.10;
		server 192.0.2.11;
	}
	server {
		listen 80;
		location / {
			proxy_pass http://backend;
		}
	}
}
```
默认为round-robin策略。

- 负载局衡器的负载均衡--DNS
```bash
> dig A example.com
; <<>> DiG 9.7.3-P3 <<>> A example.com
;; QUESTION SECTION:
;example.com. IN A
;; ANSWER SECTION:
example.com. 287 IN A 208.0.113.36
example.com. 287 IN A 208.0.113.34
example.com. 287 IN A 208.0.113.38
```
- 不同形式的server
```
upstream backend {
	# IP Address with Port
	server 192.0.2.10:443;
	
	# Hostname
	server app1.example.com;
	
	# Unix Socket
	server unix:/u/apps/my_app/current/tmp/unicorn.sock
}
```

## Health Checks
- max_fails 最大失败次数
```
upstream backend {
	server app1.example.com max_fails=2;
	server app2.example.com max_fails=100;
}
```
- fail_timeout 失败超时的时间
```
upstream backend {
	server app1.example.com max_fails=2 fail_timeout=5;
	server app2.example.com max_fails=100 fail_timeout=50;
}
```
- proxy_next_upstream 控制导致请求失败的错误条件，并增加max_fails计数器。
```bash
location / {
	proxy_next_upstream http_502 http_503 http_504;
	proxy_pass http://backend;
}
```

## 移除不可用的服务实例
```
upstream backend {
	server app1.example.com;
	server app2.example.com down;
}
```
作用和注释掉这行没太大的区别。

## Slow Start
```
upstream backend {
	server app1.example.com slow_start=60s;
	server app2.example.com;
}
```

## dynamic DNS Resolution
```
http {
	resolver 8.8.8.8;
	upstream backend {
		server loadbalancer.east.elb.amazonaws.com resolve;
	}
}
```

## 负载均衡策略
- Weighted Round Robin (Default)
- Least Connections
- IP Hash(sticky sessions) 如果增加或者删除server数量，该算法会失效。
建议使用down标记删除的server，但是增加server的话似乎没有解决方案，因为hash会变化。
- Hash。可以对$uri 进行hash

## C10K问题
1.提升nginx配置
```bash
worker_processes auto;
events {
	worker_connections 16384;
}
http {
	sendfile on;
	tcp_nopush on;
	keepalive_timeout 90;
	server {
		listen *:80 backlog=2048 reuseport;
		listen *:443 ssl backlog=2048 reuseport;
		ssl_session_cache shared:SSL:20m;
		ssl_session_timeout 10m;
		ssl_session_tickets on;
		ssl_stapling on;
		ssl_stapling_verify on;
		ssl_trusted_certificate /etc/nginx/cert/trustchain.crt;
		resolver 8.8.8.8 8.8.4.4 valid=300s;
	}
}
```
2.调整linux内核
```bash
nano /etc/sysctl.conf
# the size of the kernel queue for accepting new TCP connections
net.core.somaxconn=10000
```

## TCP Load Balancing
```
stream {
	server {
		listen *:80;
		proxy_pass backend;
	}
	upstream backend {
		server app01;
		server app02;
		server app03;
	}
}
```