# nginx 反向代理
CGI(Common Gateway Interface) is just a fancy way of saying “the web server executes the script directly”. 

When you access script.php from the web server, it’s just running ./script.php and returning the output to the browser.
```
Web Server-------> CGI Program
```

FastCGI
```
                                 --------> CGI Program
                                |
Web Server----> FastCGI Server-----------> CGI Program
                                |
                                 --------> CGI Program
```

Forward Proxy
```
CGI Program-------
                 |
CGI Program--------------->Forward Proxy -------->Internet
                 |
CGI Program-------
```

Reverse Proxy
```
                                           -----> XX server
                                          |
Computer---> Internet---> Reverse Proxy---------> XX server
                                          |
                                           -----> XX server
```
## 配置
### 静态文件夹
```bash
http {
	upstream rails_app {
		server 127.0.0.1:3000;
	}
	server {
		listen *:80;
		root /path/to/application/public;
		
		location / {
			proxy_pass http://rails_app;
		}
		
		location /assets {
			expires max;
			add_header Cache-Control public;
		}
	}
}
```
/assets: fallback to /path/to/application/public/assets.

```bash
[root@master assets]# curl -I http://127.0.0.1/assets/hello.txt
HTTP/1.1 200 OK
Server: nginx/1.16.1
...
Expires: Thu, 31 Dec 2037 23:55:55 GMT
Cache-Control: max-age=315360000
Cache-Control: public
...
```

### try_files检测文件存在
```bash
server {
	listen *:80;
	root /path/to/application/public;
	location / {
		try_files $uri $uri/index.html @rails;
	}
	location @rails {
		proxy_pass http://rails_app;
	}
}
```
变量$uri包含网络请求的规范化的URI。

### 自定义error页面
```bash
server {
	listen *:80;
	root /path/to/application/public;
	location / {
		error_page 404 /404.html;
		error_page 500 502 503 504 /50x.html;
		try_files $uri $uri/index.html @rails;
	}
	location @rails {
		proxy_pass http://rails_app;
	}
}
```
### 添加headers到上游
- proxy_set_header指令，允许我们添加新headers到转发的请求中，传递到上游。
- $scheme变量，包含原始请求协议格式，http或者https。
```bash
server {
	listen *:80;
	root /path/to/application/public;
	location / {
		try_files $uri $uri/index.html @rails;
	}
	location @rails {
		proxy_set_header X-Forwarded-Proto $scheme;
		proxy_pass http://rails_app;
	}
}
```

默认设置是 proxy_set_header Host $proxy_host，示例中是 rails_app。

如果想要保留原始的请求host到上游的请求中，需要添加：proxy_set_header Host $host;

### 获取客户端真实IP
客户端IP为203.0.113.1，请求发送到负载均衡器192.0.2.9，负载均衡器转发请求到一个合适的后端server。
对于后端server来说，客户端IP是192.0.2.9。

nginx使用Real IP模块解决真实IP问题。确保 --with-http_realip_module 被启用。
启用Real IP后，将向请求中注入新的HTTP标头X-REAL-IP，包含原始客户端IP地址（203.0.113.1）。
```bash
http {
	real_ip_header X-Real-IP;
	set_real_ip_from 203.0.113.1;
	server {
		...
	}
}
```
> 类似的实现 proxy_set_header X-Real-IP $remote_addr;

### Websocket
Websocket RQ(over the same TCP connection)
```bash
GET /websocket HTTP/1.1
Host: www.example.org
Upgrade: websocket
Connection: Upgrade
```

Websocket RS(over the same TCP connection)
```bash
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
```

Basic WebSocket Example
```bash
location /chat {
	proxy_pass http://node_app;
	proxy_http_version 1.1;
	proxy_set_header Upgrade $http_upgrade;
	proxy_set_header Connection "upgrade";
}
```

Dynamically mapping Connection based on Upgrade
```bash
map $http_upgrade $connection_upgrade {
	'websocket' upgrade;
	default close;
}
location @node {
	proxy_pass http://node_app;
	proxy_http_version 1.1;
	proxy_set_header Upgrade $http_upgrade;
	proxy_set_header Connection $connection_upgrade;
}
```

- 当$http_upgrade等于“websocket”时，将$connection_upgrade设置为upgrade。
- 当$http_upgrade等于default时，将$connection_upgrade设置为close。

注意处理nginx proxy_read-timeout问题。