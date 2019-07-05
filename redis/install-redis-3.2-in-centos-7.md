# Install redis 3.2 in CentOS 7
```bash
yum install -y redis
```

enable remote visit:
```bash
nano /etc/redis.conf

# bind 127.0.0.1
protected-mode no
```
> Or you can only change bind value to `0.0.0.0`

start redis server:
```bash
systemctl start redis
systemctl status redis
```