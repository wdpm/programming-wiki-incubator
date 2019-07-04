# Set default storage engine
```bash
nano /etc/my.cnf

# default engine
default_storage_engine=InnoDB

# restart mysqld
systemctl restart mysqld
```

change already existed table storage engine to innodb:
```bash
ALTER TABLE your_table ENGINE = INNODB;
```

## Reference
-  https://dev.mysql.com/doc/refman/5.7/en/storage-engine-setting.html