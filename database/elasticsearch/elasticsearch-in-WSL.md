# setup elasticsearch in WSL

要在 WSL 上安装 Elasticsearch，可以按照以下步骤进行：

打开 WSL 终端，确保已安装 Java 环境，可以通过以下命令检查：

```bash
java -version
```

如果没有安装 Java 环境，可以使用以下命令在 WSL 中安装 OpenJDK：
```bash
sudo apt-get update
sudo apt-get install openjdk-8-jdk
```

下载 Elasticsearch 的 Debian 安装包。可以通过以下命令在 WSL 中下载 Elasticsearch 7.15.0 版本的安装包：
```bash
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.15.0-amd64.deb
```

安装 Elasticsearch。可以使用以下命令在 WSL 中安装 Elasticsearch：
```bash
sudo dpkg -i elasticsearch-7.15.0-amd64.deb
```

启动 Elasticsearch。可以使用以下命令在 WSL 中启动 Elasticsearch 服务：
```bash
sudo systemctl start elasticsearch
```

测试 Elasticsearch。可以使用以下命令在 WSL 中测试 Elasticsearch 是否正常工作：
```bash
curl -X GET "localhost:9200/"
```

如果 Elasticsearch 正常运行，会返回类似以下内容的 JSON 格式响应：

```json
{
  "name" : "node-1",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "WbUp8NJwTt2_Lm-sJlKjBw",
  "version" : {
    "number" : "7.15.0",
    "build_flavor" : "default",
    "build_type" : "deb",
    "build_hash" : "79d65f6e357953a5b3cbcc5e2c7c21073d89aa29",
    "build_date" : "2021-09-16T03:05:29.143308416Z",
    "build_snapshot" : false,
    "lucene_version" : "8.9.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```
这样，在 WSL 上就可以安装并运行 Elasticsearch 了。如果需要在外部访问 Elasticsearch，需要在 WSL 中的 Elasticsearch 配置文件中进行相关设置，具体可以参考 Elasticsearch 官方文档。