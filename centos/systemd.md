# systemd

创建unit file，交给System Manager管理。

示例：在`/etc/systemd/system/` 下创建 `shadowsocks.service`
```bash
nano /etc/systemd/system/shadowsocks.service
```
```bash
[Unit]
Description=Shadowsocks

[Service]
TimeoutStartSec=0
ExecStart=/usr/bin/sslocal -c /etc/shadowsocks.json

[Install]
WantedBy=multi-user.target
```
```
systemctl status shadowsocks
systemctl start shadowsocks
systemctl stop shadowsocks
```