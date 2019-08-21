# Install Elasticsearch 6.8 in CentOS7
## Install Elasticsearch with RPM
import elasticsearch GPG key:
```bash
rpm --import https://artifacts.elastic.co/GPG-KEY-elasticsearch
```

create a rpm repository:
```bash
nano /etc/yum.repos.d/elasticsearch.repo
```
```yaml
[elasticsearch-6.x]
name=Elasticsearch repository for 6.x packages
baseurl=https://artifacts.elastic.co/packages/6.x/yum
gpgcheck=1
gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
enabled=1
autorefresh=1
type=rpm-md
```

install elasticsearch:
```bash
sudo yum install elasticsearch
```

## Running Elasticsearch with systemd
```bash
sudo systemctl daemon-reload
sudo systemctl enable elasticsearch.service

sudo systemctl start elasticsearch.service
sudo systemctl stop elasticsearch.service
```
After starting elasticsearch.service.You should configure HTTP Network for external visiting.
```bash
nano /etc/elasticsearch/elasticsearch.yml
# or specific ip
network.host: 0.0.0.0 
```
Now, you can visit  http://your_ip:9200
```json
{
    "name": "Of8Qrql",
    "cluster_name": "elasticsearch",
    "cluster_uuid": "9EMcVIO-QI2aTIG29ejhcA",
    "version": {
        "number": "6.8.2",
        "build_flavor": "default",
        "build_type": "rpm",
        "build_hash": "b506955",
        "build_date": "2019-07-24T15:24:41.545295Z",
        "build_snapshot": false,
        "lucene_version": "7.7.0",
        "minimum_wire_compatibility_version": "5.6.0",
        "minimum_index_compatibility_version": "5.0.0"
    },
    "tagline": "You Know, for Search"
}
```

Log files locate in `/var/log/elasticsearch/`.

## Reference docs
- https://www.elastic.co/guide/en/elasticsearch/reference/6.8/rpm.html#rpm-check-running