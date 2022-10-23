# setup ss client and privoxy in centos 7

## setup shadowsocks client
### 准备
```bash
yum -y install epel-release
yum -y install python-pip
pip install shadowsocks
```

### 配置 shadowsocks 客户端
```bash
nano etc/shadowsocks.json
```
```json
{
    "server":"your_server_ip",
    "server_port":your_server_port,
    "local_port":1080,
    "password":"your_server_password",
    "timeout":600,
    "method":"your_server_method"
}
```
### 配置 shadowsocks 为 service
```bash
nano /etc/systemd/system/shadowsocks.service
```
```conf
[Unit]
Description=Shadowsocks

[Service]
TimeoutStartSec=0
ExecStart=/usr/bin/sslocal -c /etc/shadowsocks.json

[Install]
WantedBy=multi-user.target
```

管理 shadowsocks service
```bash
systemctl enable shadowsocks
systemctl start shadowsocks
systemctl status shadowsocks
```

验证 shadowsocks客户端是否正常运行
```bash
curl --socks5 127.0.0.1:1080 http://httpbin.org/ip
```
如果显示类似如下IP则正常
```json
{
  "origin": "x.x.x.x, x.x.x.x"
}
```

## setup privoxy
```bash
yum -y install privoxy
```

管理 privoxy
```bash
systemctl enable privoxy
systemctl start privoxy
systemctl status privoxy
```
### 配置privoxy全局代理
```bash
nano /etc/privoxy/config
```
```bash
listen-address 127.0.0.1:8118 # 8118为默认端口，可以不改
forward-socks5t / 127.0.0.1:1080 . 
```

设置http/https代理 
```bash
nano /etc/profile
```
```bash
export http_proxy=http://127.0.0.1:8118
export https_proxy=http://127.0.0.1:8118
```

验证访问外网
```bash
curl -I www.google.com
```

### 配置 privoxy 局部代理

```bash
terminal的访问(受限于/etc/profile代理规则) -> 127.0.0.1:8118(受限于privoxy配置的规则) -> 127.0.0.1:1080(受限于shawsocks的规则) -> www.google.com
```
开启局部代理
```bash
#forward-socks5t / 127.0.0.1:1080 .
forward-socks5t *.k8s.io 127.0.0.1:1080 . 
forward-socks5t *.google.com 127.0.0.1:1080 . 
```