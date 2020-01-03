# nginx基础
- 配置在 /etc/nginx
- nginx -V 显示版本详细信息

## 基本配置篇
```bash
[root@master sbin]# nginx -t
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
```
默认情况下，nginx会在80端口分享 /usr/share/nginx/html 目录的内容。
```bash
[root@master ~]# ps aux|grep nginx
root      35916  0.0  0.0 116604  2228 ?        Ss   23:37   0:00 nginx: master process nginx
nginx     35917  0.0  0.0 119156  3504 ?        S    23:37   0:00 nginx: worker process
```
只有root用户才能绑定低于1024的端口。
主进程读取和执行nginx配置，绑定端口，运行工作进程。

nginx由事件驱动。每个工作进程都是单线程，并运行一个非阻塞事件循环，可以快速地处理请求。

`worker_connections`指令设置可以由每个工作进程打开的最大同时连接数。默认为每个工作进程512个连接。
`server_name` 指令用于确定哪个服务器用于处理传入的请求。当您想在同一接口和端口上托管多个域名时，该指令对于虚拟主机非常有用。

### 情景一：只更新nginx配置，不更新nginx主进程
```bash
kill -HUP `cat /var/run/nginx.pid`
```
重新加载nginx会启动新的工作进程，并杀死旧的工作进程。它能够完全优雅地完成此操作，而不会丢弃或杀死任何Web请求。
它通过启动新工作进程，停止向老工作进程发送流量来实现，并等待老工作进程完成所有正在处理的请求，然后杀死他们。

- TERM、INT 快速关机
- QUIT 优雅关机
- KILL 停止一个顽固的进程
- HUP 配置重载

### 情景二：部署nginx新版本，更新nginx主进程
```bash
cat /var/run/nginx.pid 
24484 # master PID
kill -USR2 24484 # sending USR2 to the old master process, start up a new master process and workers
kill -WINCH 24484 # kill the children of the old nginx master.
kill -QUIT 24484 # kill the old master
```

### 情景三：多主页文件的应用（计划维护）
```
server {
	listen *:80;
	root /usr/share/nginx/html;
	index maintenance.html index.html;
}
```
重载配置，维护完成后，删掉maintenance.html

### 情景四：URL附加
```
location / {
  root /var/www/html;
}
```
http://example.org/file.html will be resolved to /var/www/html/file.html.
```
location /foobar/ {
  root /data;
}
```
http://example.org/foobar/test.html will resolve to /data/foobar/test.html.

### 情景五：URL替换
在上面示例中，当我们使用root指令时，请求URI路径被附加到root指令。

有时，我们想替换而不是将URI附加到根路径。可以改用alias指令。它与root类似，除了替换指定的位置。
```
location /gifs/ {
  alias /data/images/;
}
```
http://example.org/gifs/business_cat.gif will be resolves to /data/images/business_cat.gif.

### 情景六：URL精确匹配
```
location = /foobar/ {
  ...
}
```

### 情景七：URL正则匹配
```
location ~ \.(gif|jpg)$ {
  ...
}
```

~表示大小写区分。~*表示大小写不区分。

### 情景八：跳过正则匹配
```bash
location ^~ /foobar/images {
  root /var/www/foobar;
}
location ~* \.(gif|jpg)$ {
  root /var/www/images;
}
```
当选择^~修饰符作为URI的最佳前缀时，它立即选择并跳过与正则表达式块的匹配。

|Modifier |Name |Description|
|---|---|---|
|(none) |Prefix| Matches on the prefix of a URI|
|= |Exact Match| Matches an exact URI|
|~ |Case-Sensitive Regular Exression |Matches a URI against a case-sensitive regular expression|
|~* |Case-Insensitive Regular Expression| Matches a URI against a case-insensitive regular expression|
|^~ |Non-Regular Expression Prefix| Matches a URI against a prefix and skips regular expression matching|

1. 检查精确匹配位置块。如果找到完全匹配的内容，搜索终止，并使用位置块。
2. 检查所有前缀位置块的最具体（最长）匹配的前缀。
  2.1 如果最佳匹配具有^~修饰符，则搜索终止，使用该块。
3. 按顺序检查每个正则表达式块。如果是正则表达式发生匹配，搜索终止并使用块。
4. 如果没有匹配正则表达式块，则在步骤2中确定的最佳前缀位置块被使用。

### 命名的位置块
```bash
location / {
  try_files maintenance.html index.html @foobar;
}
location @foobar {
  ...
}
```

### 虚拟主机
```bash
server {
	listen 80;
	server_name example.com;
}
server {
	listen 80 default_server;
	server_name foobar.com;
}
```
default_server 用于指定默认server

### Configuring SSL
```bash
server {
	listen 80;
	listen 443 ssl;
	server_name www.foobar.com;
	ssl_certificate www.foobar.com.crt;
	ssl_certificate_key www.foobar.com.key;
}
```
如果仅指定文件名或相对路径（certificate.crt），将使用配置目录/etc/nginx作为路径的前缀。

共享通配符证书
```bash
http {
	ssl_certificate star.example.com.crt;
	ssl_certificate_key star.example.com.crt;
	server {
		listen 80;
		listen 443 ssl;
		server_name www.example.com;
	}
	server {
		listen 80;
		listen 443 ssl;
		server_name billing.example.com;
	}
}
```