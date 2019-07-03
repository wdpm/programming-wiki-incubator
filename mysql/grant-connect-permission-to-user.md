# Grant connect permission to user
```bash
mysql> grant all privileges on *.* to 'evan'@'%' identified by 'omitted' with grant option;
Query OK, 0 rows affected, 1 warning (0.00 sec)

mysql> flush privileges;
Query OK, 0 rows affected (0.00 sec)
```
以上指令将MySQL所有数据库的所有权限授予 evan 用户，该用户的连接来源可以为任何ip(%的含义)。