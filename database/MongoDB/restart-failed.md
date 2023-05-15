# restart failed

可能是 /var/lib/mongodb 和 /var/log/mongodb 的owner权限的问题。
对于，mongo 6.0+, 将其设置回mongodb:mongodb

```bash
chown -R mongodb:mongodb /var/lib/mongodb
chown -R mongodb:mongodb /var/log/mongodb
```