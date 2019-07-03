# Create user and database in MySQL 5.7

```bash
/* CREATE NEW DATABASE */
mysql> create database mydb;
Query OK, 1 row affected (0.01 sec)

/* CREATE MYSQL USER FOR DATABASE */
mysql> create user 'evan'@'localhost' identified by 'omitted';
Query OK, 0 rows affected (0.00 sec)

/* GRANT Permission to User on Database */
mysql> grant all on mydb.* to 'evan'@'localhost';
Query OK, 0 rows affected (0.00 sec)

/* RELOAD PRIVILEGES */
mysql> flush privileges;
Query OK, 0 rows affected (0.00 sec)
```
