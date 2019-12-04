# Sleuth with zipkin save tracking data

## prepare mysql database and schema

create database ``zipkin_db``, and run this SQL:
```
https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql
```

## start zipkin server
> https://github.com/openzipkin/zipkin/tree/2.4.6/zipkin-server#mysql-storage

```
* `MYSQL_DB`: The database to use. Defaults to "zipkin".
* `MYSQL_USER` and `MYSQL_PASS`: MySQL authentication, which defaults to empty string.
* `MYSQL_HOST`: Defaults to localhost
* `MYSQL_TCP_PORT`: Defaults to 3306
* `MYSQL_MAX_CONNECTIONS`: Maximum concurrent connections, defaults to 10
* `MYSQL_USE_SSL`: Requires `javax.net.ssl.trustStore` and `javax.net.ssl.trustStorePassword`, defaults to false.
```

```bash
java -jar zipkin.jar --zipkin.collector.rabbitmq.addresses=192.168.31.12 --zipkin.collector.rabbitmq.username=evan --zipkin.collector.rabbitmq.password=123456 --MYSQL_DB=zipkin_db --MYSQL_USER=root --MYSQL_PASS=Root_2019 --MYSQL_HOST=192.168.31.12 --MYSQL_TCP_PORT=3306 --MYSQL_USE_SSL=false --STORAGE_TYPE=mysql
```
## test
1.GET ``localhost:9999/test``.

2.check these tables of ``zipkin_db`` database in mysql .
```
zipkin_annotations
zipkin_dependencies
zipkin_spans
```