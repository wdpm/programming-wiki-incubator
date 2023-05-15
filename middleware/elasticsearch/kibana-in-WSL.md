# setup kibana in WSL


```bash
wget https://artifacts.elastic.co/downloads/kibana/kibana-7.15.0-linux-x86_64.tar.gz
tar -xzf kibana-7.15.0-linux-x86_64.tar.gz

cd kibana-7.15.0-linux-x86_64
nano config/kibana.yml
```

```yml
server.host: "0.0.0.0"
elasticsearch.hosts: ["http://localhost:9200"]
```
```bash
./bin/kibana --allow-root
```
这种方式有两个缺点：
- root权限启动
- 没有得到systemctl管理守护，缺乏开机自启机制。

root权限启动的弊端可以通过创建kibana用户和组来降级权限。

开机自启可以通过创建*.service文件来定义系统服务单元。

```bash
sudo nano /etc/systemd/system/kibana.service
```

模板：
```
[Unit]
Description=Kibana
After=syslog.target network.target remote-fs.target nss-lookup.target

[Service]
Type=simple
ExecStart=/home/<username>/kibana-7.15.0-linux-x86_64/bin/kibana
Restart=always
User=<username>
Group=<username>
WorkingDirectory=/home/<username>/kibana-7.15.0-linux-x86_64

[Install]
WantedBy=multi-user.target
```

实际例子：
```
[Unit]
Description=Kibana
After=syslog.target network.target remote-fs.target nss-lookup.target

[Service]
Type=simple
ExecStart=/root/kibana-7.15.0-linux-x86_64/bin/kibana --allow-root
Restart=always
User=root
Group=root
WorkingDirectory=/root/kibana-7.15.0-linux-x86_64

[Install]
WantedBy=multi-user.target
```

启动：
```bash
sudo systemctl daemon-reload
sudo systemctl enable kibana
sudo systemctl start kibana
sudo systemctl status kibana
```