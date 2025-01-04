# Install MySQL 5.7 in CentOS 7

## Steps for a Fresh Installation of MySQL
### Adding the MySQL Yum Repository
update yum repo:
```bash
yum localinstall https://dev.mysql.com/get/mysql57-community-release-el7-9.noarch.rpm
```
> alternatives: http://repo.mysql.com/mysql-community-release-el7-{version-number}.noarch.rpm

then you can check what is available by:
```bash
yum repolist enabled | grep mysql
```
The console output will look like this:
```bash
mysql-connectors-community/x86_64 MySQL Connectors Community                 108
mysql-tools-community/x86_64      MySQL Tools Community                       90
mysql57-community/x86_64          MySQL 5.7 Community Server                 347
```
> you can check `/etc/yum.repos.d/mysql-community.repo` to explore more details.

### Install MySQL
```bash
yum install mysql-community-server
```
check MySQL version :
```bash
# mysql -V
mysql  Ver 14.14 Distrib 5.7.26, for Linux (x86_64) using  EditLine wrapper
```

### Start MySQL server
```bash
# systemctl start mysqld
# systemctl status mysqld
● mysqld.service - MySQL Server
   Loaded: loaded (/usr/lib/systemd/system/mysqld.service; enabled; vendor preset: disabled)
   Active: active (running) since Thu 2019-07-04 00:42:05 CST; 4s ago
     Docs: man:mysqld(8)
           http://dev.mysql.com/doc/refman/en/using-systemd.html
  Process: 20858 ExecStart=/usr/sbin/mysqld --daemonize --pid-file=/var/run/mysqld/mysqld.pid $MYSQLD_OPTS (code=exited, status=0/SUCCESS)
  Process: 20835 ExecStartPre=/usr/bin/mysqld_pre_systemd (code=exited, status=0/SUCCESS)
 Main PID: 20861 (mysqld)
    Tasks: 27
   CGroup: /system.slice/mysqld.service
           └─20861 /usr/sbin/mysqld --daemonize --pid-file=/var/run/mysqld/mysqld.pid

Jul 04 00:42:03 evan-server systemd[1]: Starting MySQL Server...
Jul 04 00:42:05 evan-server systemd[1]: Started MySQL Server.
```
A superuser account 'root'@'localhost is created. 
A password for the root user is set and stored in the log file. To reveal it by:
```bash
# sudo grep 'temporary password' /var/log/mysqld.log
```
You can change the root password after logging in with the generated, temporary password and set a custom password for the root account:
```bash
mysql -uroot -p
ALTER USER 'root'@'localhost' IDENTIFIED BY '<your_root_password>';
grant all privileges on *.* to 'root'@'%' identified by '<your_root_password>' with grant option;
```

For MySQL 8.0+

```bash
sudo mysql -u root -p

# change root password
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';

# change the host of root from localhost to %
UPDATE mysql.user SET host='%' WHERE user='root' AND host='localhost';
FLUSH PRIVILEGES;
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';

# root 用户不仅可以执行所有操作，还可以将这些权限分配给其他用户
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
```

## Reference
- https://dev.mysql.com/doc/refman/5.7/en/linux-installation-yum-repo.html
- https://tecadmin.net/install-mysql-5-7-centos-rhel/

