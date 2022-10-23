# setup shadowsocks server in ubuntu 16

> bandwagon vps

## 安装Ubuntu16.04

Client Area -> kiwiVM Control Panel -> Install new OS -> 选择Ubuntu16.04

## 建立shadowsocks

安装shadowsocks

```bash
apt update
apt install python-pip
pip install shadowsocks
```

配置shadowsocks账号

```bash
nano /etc/shadowsocks.json
```
```json
{
    "server":"your_server_ip",
    "server_port":443,
    "local_address": "127.0.0.1",
    "local_port":1080,
    "password":"your_password",
    "timeout":600,
    "method":"aes-256-cfb"
}
```

启动ssserver服务

赋予`/etc/shadowsocks.json`文件可执行权限，以及安装必要的加密软件：

```bash
sudo chmod 755 /etc/shadowsocks.json
sudo apt install python–m2crypto
```

后台守护方式运行

```bash
sudo ssserver -c /etc/shadowsocks.json -d start
```

将ssserver配置为开机启动

```bash
nano /etc/rc.local
# 在末尾添加
/usr/local/bin/ssserver –c /etc/shadowsocks.json
```

## 安装bbr

最简单的方式是一键脚本：

```bash
wget -N --no-check-certificate https://softs.fun/Bash/bbr.sh && chmod +x bbr.sh && bash bbr.sh
```

```bash
# 启动BBR
bash bbr.sh start
 
# 关闭BBR
bash bbr.sh stop
 
# 查看BBR状态
bash bbr.sh status

# 升级BBR
# 重新执行脚本会检测最新内核和当前内核，对比版本
bash bbr.sh
```

> 来源：https://lighti.me/147.html
