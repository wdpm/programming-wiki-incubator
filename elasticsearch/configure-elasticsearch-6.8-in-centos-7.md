# Configure Elasticsearch 6.8 in CentOS 7

## Enable journalctl logging
By default the Elasticsearch service doesn’t log information in the systemd journal. 
To enable journalctl logging, the --quiet option must be removed from the ExecStart command line in the elasticsearch.service file.
``` bash
nano /usr/lib/systemd/system/elasticsearch.service
```
```yaml
# remove --quiet
ExecStart=/usr/share/elasticsearch/bin/elasticsearch -p ${PID_DIR}/elasticsearch.pid --quiet

# restart Elasticsearch
systemctl daemon-reload
systemctl restart elasticsearch.service
```
Check if work as well:
```bash
journalctl --unit elasticsearch -f
```
## Directory layout of RPM

| Type        | Description                                                  | Default Location                   | Setting        |
| ----------- | ------------------------------------------------------------ | ---------------------------------- | -------------- |
| **home**    | Elasticsearch home directory or `$ES_HOME`                   | `/usr/share/elasticsearch`         |                |
| **bin**     | Binary scripts including `elasticsearch` to start a node and `elasticsearch-plugin` to install plugins | `/usr/share/elasticsearch/bin`     |                |
| **conf**    | Configuration files including `elasticsearch.yml`            | `/etc/elasticsearch`               | `ES_PATH_CONF` |
| **conf**    | Environment variables including heap size, file descriptors. | `/etc/sysconfig/elasticsearch`     |                |
| **data**    | The location of the data files of each index / shard allocated on the node. Can hold multiple locations. | `/var/lib/elasticsearch`           | `path.data`    |
| **logs**    | Log files location.                                          | `/var/log/elasticsearch`           | `path.logs`    |
| **plugins** | Plugin files location. Each plugin will be contained in a subdirectory. | `/usr/share/elasticsearch/plugins` |                |
| **repo**    | Shared file system repository locations. Can hold multiple locations. A file system repository can be placed in to any subdirectory of any directory specified here. | Not configured                     | `path.repo`    |


## Elasticsearch Configuration
Elasticsearch defaults to using `/etc/elasticsearch` for runtime configuration.

The ownership of this directory and all files in this directory are set to `root:elasticsearch` on package installation. 
```bash
[root@evan-server elasticsearch]# ll
total 36
-rw-rw---- 1 root elasticsearch   199 Aug 21 16:24 elasticsearch.keystore
-rw-rw---- 1 root elasticsearch  2864 Aug 21 16:39 elasticsearch.yml
-rw-rw---- 1 root elasticsearch  3685 Jul 24 23:33 jvm.options
-rw-rw---- 1 root elasticsearch 13085 Jul 24 23:33 log4j2.properties
-rw-rw---- 1 root elasticsearch   473 Jul 24 23:33 role_mapping.yml
-rw-rw---- 1 root elasticsearch   197 Jul 24 23:33 roles.yml
-rw-rw---- 1 root elasticsearch     0 Jul 24 23:33 users
-rw-rw---- 1 root elasticsearch     0 Jul 24 23:33 users_roles
```
## System Configuration
Its system configuration file locates in `/etc/sysconfig/elasticsearch`.

## Other Configuration
- increase max_map_count for elasticsearch.
> https://www.kernel.org/doc/Documentation/sysctl/vm.txt
```

==============================================================

max_map_count:

This file contains the maximum number of memory map areas a process
may have. Memory map areas are used as a side-effect of calling
malloc, directly by mmap, mprotect, and madvise, and also when loading
shared libraries.

While most applications need less than a thousand maps, certain
programs, particularly malloc debuggers, may consume lots of them,
e.g., up to one or two maps per allocation.

The default value is 65536.

=============================================================
```
```bash
echo 'vm.max_map_count=262144' >> /etc/sysctl.conf
sysctl -p
```

- update ``/etc/security/limits.conf``
```bash
nano /etc/security/limits.conf 
```
```
* hard nofile 65536
* soft nofile 65536
* soft nproc 4096
* hard nproc 4096
```
A soft limit is like a warning and hard limit is a real max limit. 


## Reference docs
- https://www.elastic.co/guide/en/elasticsearch/reference/6.8/rpm.html#install-rpm