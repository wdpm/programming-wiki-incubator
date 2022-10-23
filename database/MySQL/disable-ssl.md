# disable ssl

> Caused by: javax.net.ssl.SSLException: Unsupported record version Unknown-0.0

- 原因: MySQL 5.7.26 默认启用了 SSL 连接。 
- 解决办法: 禁用MySQL Server SSL 连接认证，或者启用客户端非ssl连接。

## 禁用 MySQL Server SSL连接认证
先进入 MySQL shell查看当前ssl设置:
```bash
mysql> show global variables like '%ssl%';
+---------------+-----------------+
| Variable_name | Value           |
+---------------+-----------------+
| have_openssl  | YES             |
| have_ssl      | YES             |
| ssl_ca        | ca.pem          |
| ssl_capath    |                 |
| ssl_cert      | server-cert.pem |
| ssl_cipher    |                 |
| ssl_crl       |                 |
| ssl_crlpath   |                 |
| ssl_key       | server-key.pem  |
+---------------+-----------------+
9 rows in set (0.00 sec)
```
```
nano /etc/my.cnf

# skip ssl
skip_ssl

systemctl restart mysqld
```
再一次检验是否生效：
```bash
mysql> show global variables like '%ssl%';
+---------------+----------+
| Variable_name | Value    |
+---------------+----------+
| have_openssl  | DISABLED |
| have_ssl      | DISABLED |
| ssl_ca        |          |
| ssl_capath    |          |
| ssl_cert      |          |
| ssl_cipher    |          |
| ssl_crl       |          |
| ssl_crlpath   |          |
| ssl_key       |          |
+---------------+----------+
9 rows in set (0.00 sec)
```

## 启用客户端非 SSL 连接
spring.datasource.url = jdbc:mysql://IP:PORT/db_example?characterEncoding=utf8&useSSL=false

