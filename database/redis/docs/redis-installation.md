# Redis 安装

> RPM方式安装redis 5.0+

```bash
yum -y install http://rpms.remirepo.net/enterprise/remi-release-7.rpm
yum --enablerepo=remi install redis
```

```bash
systemctl start redis
systemctl enable redis
```

## 配置

```bash
nano /etc/redis.conf

bind 0.0.0.0
protected-mode no

systemctl restart redis
```

