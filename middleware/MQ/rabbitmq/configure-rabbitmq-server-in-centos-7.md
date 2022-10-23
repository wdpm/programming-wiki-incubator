# Configure RabbitMQ Server in CentOS 7

## Verify configuration
The active configuration file can be verified by inspecting the RabbitMQ log file.
```bash
tail -f /var/log/rabbitmq/rabbit@evan-server.log
```
```bash
Starting RabbitMQ 3.7.16 on Erlang 22.0.7
Copyright (C) 2007-2019 Pivotal Software, Inc.
Licensed under the MPL.  See https://www.rabbitmq.com/
2019-07-13 00:34:27.544 [info] <0.219.0> 
node           : rabbit@evan-server
home dir       : /var/lib/rabbitmq
config file(s) : (none)
cookie hash    : C1dBsvWlvh+0uarWwmd9Xw==
log(s)         : /var/log/rabbitmq/rabbit@evan-server.log
            : /var/log/rabbitmq/rabbit@evan-server_upgrade.log
database dir   : /var/lib/rabbitmq/mnesia/rabbit@evan-server
```
Note that `config file(s) : (none)`, You can create config file manually.
```bash
nano  /etc/rabbitmq/rabbitmq.conf
# ...
```

## Enable remote visit
> Because account guest/guest can only visit from localhost.
```bash
[root@vmware0 ~]# rabbitmqctl add_user evan 123456
Adding user "evan" ...
[root@vmware0 ~]# rabbitmqctl set_user_tags evan administrator
Setting tags for user "evan" to [administrator] ...
[root@vmware0 ~]# rabbitmqctl set_permissions -p / evan ".*" ".*" ".*"
Setting permissions for user "evan" in vhost "/" ...
```

## Enable web management
```bash
rabbitmq-plugins enable rabbitmq_management
```
Then visit ``http://<your-host-ip>:15672/``

## Reference
- https://www.rabbitmq.com/configure.html