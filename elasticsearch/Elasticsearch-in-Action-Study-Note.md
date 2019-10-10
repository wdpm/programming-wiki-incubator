# 《Elasticsearch in Action》study note

## 第一章:简介

1. 词频-逆文档频率算法。

- 词频：某单次在文档中出现次数越多，得分越高。

- 逆文档词频：如果某个单词在所有文档中比较少见，那么该词的权重越高，得分越高。

2. Elasticsearch和另一个数据库在同一个系统:

![](assets\E和另一个数据库在同一个系统.PNG)

信息的插入，更新，删除仍在“主”数据库执行，仅使用Elasticsearch来查询。同步机制决定了两个数据库之间信息的一致性是否及时。

3. HTTP连接使用JSON，配置文件使用YAML。
4. Elasticsearch和Solr的对比：两者功能相似，但Elasticsearch生于2010，Solr生于2004。

5. Elasticsearch以文档的形式保存数据。
6. 端口9300用于节点之间的通信，称为transport，适用于本地Java API；端口9200用于HTTP的通信，适用于REST API。

7. Elasticsearch是构建于Apache Lucene之上的开源分布式引擎，常用于索引大规模的数据。全文搜索和实时数据统计。
8. Elasticsearch自动将数据划分为分片，在集群的服务器上作负载均衡。



## 第二章：深入功能

1. Elasticsearch逻辑设计和物理设计：

   ![](assets\Elasticsearch逻辑设计和物理设计.PNG)

2. 某个文档：索引-类型-ID的组合唯一确定。

![](assets\数据的组织.PNG)

3. 映射类型将文档进行逻辑划分。所以，在索引数据之前，先定义所需的映射。

![](assets\节点与集群与分片.PNG)

4. 分片：技术上说，是某一个目录下的文件，用于存储索引数据；也是节点之间数据迁移的最小单位。上图中，主分片为深色，副本分片为浅色。数字相同代表数据相同，只是主副分片的关系。

5. 索引一篇文档的过程：

![](assets\文档索引的过程.PNG)

- 根据文档ID的散列值选择一个主分片，并将文档发送到该主分片。该主分片可能位于其他节点，例如上图的节点2中的主分片（分片1）
- 然后，文档被发送到该主分片的所有副本分片进行索引，使得分片1的主分片和副本分片之间可以保持数据同步。

6. 分片的理解：Elasticsearch索引被分解为多个分片，一个分片是一个Lucene索引。Lucene索引中的词条字典：

   ![](assets\Lucene索引中的词条字典.PNG)

​       例如，denver在文档id1和id3中出现，并且在id1文档中频率为1，在id3文档中频率为2。

7. 分片数量的考虑：分片过少，限制可扩展性；分片过多，会影响性能。默认分片为5开始。

8. 水平扩展：在同一个集群中增加更多的节点，分摊工作负载。垂直扩展：为节点增加更多的硬件资源。

   ![](assets\水平扩展和垂直扩展.PNG)

9. URI示例：http://localhost:9200/{index_name}/{type_name}/{doc_ID}

10. Elasticsearch浏览器图形化界面工具。
    - Elasticsearch Head - 监控工具
    - Elasticsearch kopf - 以文件系统的网页运行
    - Marvel - 监控解决方案

11. PUT例子：

    ```bash
    curl -XPUT 'localhost:9200/get-together/group/1?pretty' -d'{
      "name":"Elasticsearch Denver",
      "organizer":"Lee"
    }'
    ```

    获取对应生产的映射类型：

    ```bash
    curl 'localhost:9200/get-together/_mapping/group?pretty'
    {
      "get-together":{
        "mappings":{
          "group":{
            "properties":{
              "name":{
                "type":"string"//字符串类型
              },
              "organizer":{
                "type":"string" //字符串类型
              }
            }
          }
        }
      }
    }
    ```

12. cURL基本查询操作：

    - 1个索引，2个类型中搜索

      ```bash
      curl 'localhost:9200/get-together/group.event/_search\
      ?q=elasticsearch&pretty'
      ```

    - 1个索引，不限类型中搜索

      ```bash
      curl 'localhost:9200/get-together/_search?q=sample&pretty'
      ```

    - 2个索引，不限类型中搜索

      ```bash
      curl 'localhost:9200/get-together,other-index/_search\
      ?q=sample&pretty'
      ```

    - 不限索引，不限类型中搜索
    
      ```bash
      curl 'localhost:9200/_search?q=elasticseach&pretty'
      ```
      
    - 不限索引，单独类型中搜索
      
      ```bash
      curl 'localhost:9200/_all/event/_search?q=elasticseach&pretty'
      ```

13. JSON查询：

    ```bash
    curl 'localhost:9200/get-together/group/_search?pretty' -d'{
      "query":{
        "query_string":{
          "query":"elasticsearch"
        }
      }
    }'
    ```

14. 配置Elasticsearch：

    - elasticsearch.yml 指定集群名称

      ```bash
      nano /etc/elasticsearch.yml
      cluster.name: elasticserach
      # 注意可能需要重新写入数据到新集群
      ```

      ```bash
      systemctl restart elasticsearch
      ```

    - log4j2.properties 指定详细日志记录

      日志位于`/var/log/elasticsearch`

      - cluster-name.log -> 主要日志
      - cluster-name_index_search_slowlog.log -> 慢搜索日志，用于记录很慢的查询操作。
      - cluster-name_index_indexing_slowlog.log -> 慢查询日志，用于记录很慢的索引操作。

    - 调整JVM内存。配置文件位于/etc/sysconfig/elasticsearch

15. 导入本书配套代码的ES数据：

    ```bash
    git clone https://github.com/dakrone/elasticsearch-in-action.git -b 6.x
    elasticsearch-in-action/populate.sh
    ```

    ```bash
    # curl 'localhost:9200/_cat/shards?v'
    index                  shard prirep state      docs  store ip           node
    get-together           1     p      STARTED      16 18.1kb 192.168.1.12 Of8Qrql
    get-together           1     r      UNASSIGNED                          
    get-together           0     p      STARTED       4 10.6kb 192.168.1.12 Of8Qrql
    get-together           0     r      UNASSIGNED 
    ```

    - 集群名称为在elasticsearch.yml定义的。
    - 只有1个节点
    - get-together索引有2个主分片，为激活状态。未分配的分片为副分片。

16. 索引请求在主分片中分发，然后复制到这些主分片的副本分片。

## 第三章：索引、更新和删除数据

1.字段有三种类型：字符串和数值型，数组和多元字段，预定义元数据（例如_timestamp)。

![](assets\使用类型划分同一个索引的数据.PNG)

获取已存在的映射类型：

```bash
curl 'localhost:9200/get-together/_mapping/?pretty'
```

- 定义新的索引：

```
curl -XPUT 'localhost:9200/new-get-together'
```

- 在新索引创建映射类型：

```bash
curl -H "Content-Type:application/json" -XPUT 'localhost:9200/new-get-together/_mapping/new-events' -d'{
  "new-events":{
    "properties":{
      "host":{
        "type":"text"
      }
    }
  }
}'
```

2. 字段的核心类型有：字符串，数值，日期，布尔。

3. index可以设置为analyzed（转小写，拆解单词），not_analyzed（精准匹配），no（略过）。

4. 数值类型有：byte，short，int，long，float，double。

5. 日期类型：2013-10-11T10:32:45.453-03:00

6. 布尔类型：true、false

7. 预定义字段以_开头：
   - _source: 原JSON文档
   - _uid：由 _id 和 _type 组成
   - _id
   - _type
   - _index
   - _size：原始JSON内容的大小
   - _timestamp：时间
   - _routing
   - _parent

8. 默认情况下，include_in_all为true，每个字段都被包含于_all之中。

9. _id 和 _ type的默认设置：

   ![](assets\_id和_type.PNG)

   

10. 文档ID可以在PUT的URI中指定，也可以省略让ES自动生成。

11. 可以使用_index字段在文档中保存索引名称。

12. 文档的更新：发送部分文档（例如对某字段更新值），upsert创建不存在的文档，脚本更新文档。

13.脚本更新文档：

```bash
curl -XPOST 'localhost:9200/online-shop/shirts/1' -d'{
  "caption":"Learning Elasticsearch",
  "price":15
}'

curl -XPOST 'localhost:9200/online-shop/shirts/1/_update' -d'{
  "scripts":"ctx._source.price+=price_diff",
  "params":{
    "price_diff":10
  }
}'
```

14. ES通过为文档设置版本号（1,2,...)实现并发控制。

15. 乐观锁：允许并行操作并假设冲突很少出现，一旦出现就抛出错误；悲观锁：假设冲突经常出现，第一时间预防冲突。

16. 处理冲突的方法：
    - 冲突发生时自动重试更新操作 retry_on_conflict=3
    - 使用内部版本号 version=3
    - 使用外部版本号 version=10&version_type=external

17. 删除数据：删除单一文档或多个文档（byID/byQuery)，删除整个索引，关闭索引。

18. 映射定义了文档中的字段以及索引的配置。映射是自动扩展的。

19. Lucene分段一旦创建就不会删除，更新文档意味着检索现有文档，将修改应用到新文档，然后删除旧的索引文档。

## 第四章：搜索数据

1. ES的搜索基于JSON文档或者基于URL的请求。

2. 基于URL的请求：可使用from和size字段指定分页。_source 字段指定保留的字段作为返回结果。

3. 基于JSON文档的查询：

   ```bash
   curl 'localhost:9200/get-together/_search' -d'{
     "query":{
       "match_all":{}
     },
     "_source":{
       "include":["location.*","date"],//要返回的字段
       "exclude":["location.geolocation"]//忽略的字段
     }
   }'
   ```

   范围、分页、字段、排序：

   ```bash
   curl 'localhost:9200/get-together/group/_search' -d'{
     "query":{
       "match_all":{}
     },
     "from":0,
     "size":10,
     "_source":["name","organizer","description"],
     "sort":[{"created_on":"desc"}]
   }'
   ```

   ```bash
   curl 'localhost:9200/_search?q=title:elasticsearch&_source=title,data'
   ```

   ```json
   {
      "took":110,//查询用了110ms
      "timed_out":false,
      "_shards":{
         "total":2,//分片总数量
         "successful":2,//成功响应该请求的分片数量
         "skipped":0,
         "failed":0
      },
      "hits":{
         "total":2,//该搜索请求所有匹配结果的数量
         "max_score":1.0128567,//这个搜索结果中的最大得分
         "hits":[
            {
               "_index":"get-together",//文档索引
               "_type":"_doc",//文档类型
               "_id":"103",//文档ID
               "_score":1.0128567,//文档得分
               "_routing":"2",
               "_source":{
                  "title":"Introduction to Elasticsearch"
               }
            },
            {
               "_index":"get-together",
               "_type":"_doc",
               "_id":"105",
               "_score":1.0128567,
               "_routing":"2",
               "_source":{
                  "title":"Elasticsearch and Logstash"
               }
            },
            {
               "_index":"get-together",
               "_type":"_doc",
               "_id":"112",
               "_score":1.0128567,
               "_routing":"5",
               "_source":{
                  "title":"real-time Elasticsearch"
               }
            }
         ]
      }
   }
   ```

4. 搜索中的查询和过滤器DSL。过滤只是为“文档是否匹配”返回简单的YES/NO，但查询会计算文档得分。

5. query查询

- ```
  "query":{
    "match_all":{} //匹配所有文档
  }
  ```

- ```json
  "query":{
    "filtered":{
      "query":{
        "match_all":{}
       },
      "filter":{
        ...filter detail //添加过滤器
       }
     }
  }
  ```

- ```json
  "query":{
    "query_string":{
      "default_field":"desc",//指定查询的字段
      "query":"nosql"
    }
  }
  ```

6. term、terms查询/过滤器。

   ```json
   "query":{
     "term":{
       "tags":"elasticsearch"
     }
   }
   ```

   ```json
   "query":{
     "terms":{
       "tags":["java","python","c++"],
       "minimum_should_match":2
     }
   }
   ```

7. match查询

   - 布尔查询

   ```json
   "query":{
     "match":{
       "name":{
         "query":"elasticsearch denver",
         "operator":"and" //or
       }
     }
   }
   ```

   - 词组查询

   ```json
   "query":{
     "match":{
       "name":{
         "type":"phrase",//词组查询
         "query":"enterprise london",
         "slop":1 //表示enterprise和london之间可以有一个单词的距离
       }
     }
   }
   ```
   
8. multi_match查询

   ```json
   "query":{
     "multi_match":{
       "query":"es hadoop",
       "fields":["name","description"]//搜索name,description字段中的值
     }
   }
   ```

9. 组合查询：must等价于AND，must_not等价于NOT，should等价于OR。

10. bool查询：

    ```json
    "query":{
      "bool":{
        "must":{},
        "should":[{},{}],
        "must_not":[],
        "minimum_should_match":1 //should必须有1个或以上子句匹配
      }
    }
    ```

11. bool过滤器

    ```json
    "query":{
      "bool":{
        "must":[
          {...condition1},
          {...condition2},
          {...condition3}
        ]
      }
    }
    ```

    如果指定了must子句，minimum_should_match默认值为0；如果没有指定must子句，默认为1。

12. 范围查询

    ```json
    "query":{
      "range":{
        "created_on":{
          "gt":"2012-06-01",
          "lt":"2012-09-01"
        }
      }
    }
    ```

    一般而言，选择过滤器会更快，因为查询要计算得分，会相对较慢。

13. 前缀查询

    ```json
    "query"：{
      "prefix":{
        "title":"liber"
      }
    }
    ```

14. wildcard查询

    ```json
    "query"：{
      "wildcard":{
        "title":{
          "wildcard":"ba*n"
        }
      }
    }
    ```

15. Exists/Missing过滤器

    - exists

      ```json
      "query":{
        "filtered":{
          "query":{
            "match_all":{}
          },
          "filter":{
            "exists":{
              "fields":"location.geolocation"
              }
          }
        }
      }
      ```

    - missing

      ```json
      "query":{
        "filtered":{
          "query":{
            "match_all":{}
          },
          "filter":{
            "missing":{
              "fields":"reviews",
              "existence":true,
              "null_value":true
            }
          }
        }
      }
      ```

16. 常用案例。

    | 用例                                                       | 查询类型                                  |
    | ---------------------------------------------------------- | ----------------------------------------- |
    | 类似Google界面                                             | match查询 simple_query_string查询         |
    | 将输入作为词组并搜索包含这个词组的文档，词组之间允许有间隔 | match type=phrase查询，设置一定的slop数量 |
    | 想在not_analyzed字段中搜索单个关键词                       | term查询                                  |
    | 组合不同的查询                                             | bool查询                                  |
    | 某个文档中多个字段中搜索特定的单词                         | multi_match查询                           |
    | 一次搜索返回所有的文档                                     | match_all查询                             |
    | 在字段中搜索一定范围的值                                   | range查询                                 |
    | 在字段中搜索特定字符串开头的取值                           | prefix查询                                |
    | 特定字段没有取值的文档                                     | missing过滤器                             |

## 第五章：分析数据

1. 分析是在文档被发送并加入倒排索引之前，ES在其主体上执行的操作。

原始输入：

```
share your experience with NoSQL & big data technologies
```

经过【字符过滤器】转义，纠错)：& -> and

```
share your experience with NoSQL and big data technologies
```

经过【分词器】进行切分：

```
share、your、experience、with、NoSQL、and、big、data、technologies
```

经过【分词过滤器】（例如转为小写）:删掉了and

```
share、your、experience、with、nosql、big、data、technologies
```

2. 在索引创建时增加分析器

   ```json
   "settings":{
     "number_of_shards":2,//2份主分片
     "number_of_replicas":1//1份副本分片
     "index":{
       "analysis":{
         "analyzer":{ //分析器
           "myCustomAnalyzer":{
              "type":"custom",//自定义类型
              "tokenizer":"myCustomTokenizer",
              "filter":["myCustomFilter1","myCustomFilter2"],
              "char_filter":["myCustomCharFilter"]
           }
         },
         "tokenizer":{//分词器
            "myCustomTokenizer":{
                "type":"letter"//字母
            }
         },
         "filter":{//分词过滤器
             "myCustomFilter1":{
                 "type":"lowercase"//小写
             },
             "myCustomFilter2":{
                 "type":"kstem"//习俗？
             }
         },
         "char_filter":{//字符过滤器
           "myCustomCharFilter":{
               "type":"mapping",
               "mappings":["ph=>f","u=>you"]//字符矫正，纠错
           }
         }
       
       }
     }
   },
   "mappings":{
     ...
   }
   ```

3. 在ES配置中添加分析器

   ```yml
   index:
     analysis:
       analyzer:
         ...
       tokenizer:
         ...
       filter:
         ...
       char_filter:
         ...
   ```

4. 在映射中指定某个字段的分析器

   ```json
   "description"：{
     "type":"string",
     "analyzer":"myCustomAnalyzer"
   }
   ```

5. 使用分析API帮助分析文本

   ```bash
   curl -X GET "localhost:9200/_analyze?pretty" -H 'Content-Type: application/json' -d'
   {
     "analyzer" : "standard",
     "text" : "this is a test"
   }
   '
   ```

   ```json
   {
     "tokens" : [
       {
         "token" : "this",
         "start_offset" : 0,
         "end_offset" : 4,
         "type" : "<ALPHANUM>",
         "position" : 0
       },
       {
         "token" : "is",
         "start_offset" : 5,
         "end_offset" : 7,
         "type" : "<ALPHANUM>",
         "position" : 1
       },
       {
         "token" : "a",
         "start_offset" : 8,
         "end_offset" : 9,
         "type" : "<ALPHANUM>",
         "position" : 2
       },
       {
         "token" : "test",
         "start_offset" : 10,
         "end_offset" : 14,
         "type" : "<ALPHANUM>",
         "position" : 3
       }
     ]
   }
   ```

6. 词条向量API：_termvector

7. 一个分析器包含：一个可选的字符过滤器，一个分词器，0或多个分词过滤器。

8. 分析器有：

   标准；简单；空白；停用词；关键词；模式；语言和多语言；雪球。

9. 分词器：

   标准；关键字；字母；小写；空白；模式；UAX URL；路径层次。

10. 分词过滤器：

    标准；小写；长度；停用词；截断、修剪、限制分词数量；颠倒；唯一；同义词。

11. N元语法和侧边N元语法、滑动窗口分词过滤器。

12. 算法提取词干（过滤器）：snowball，porter_stem，kstem.

13. 使用字典提取词干（过滤器）。
14.  分析器：字符过滤器 -> 分词器 -> 分词过滤器

## 第六章：使用相关性进行搜索

二元打分机制“匹配”或者“不匹配”有时不足以表述查询结果中每个文档具体的相关性，于是需要更为具体的文档打分机制。

1. 逆文档频率只检查一个词条是否出现在某个文档中，而不检查出现多少次。

2. Lucene评分公式：

   ![](assets\lucene-score-formula.PNG)

   一个词条的得分=该词在文档d中的词频的平方根 * 该词逆文档频率的平方和 * 该文档字段归一化因子 *该词的提升权重

3. BM25相似度打分模型：k1,b,discount_overlaps参数配置

4. 查询期间，使用match查询进行boosting

   ```json
   {
     "match":{
       "description":{
         "query":"elasticsearch big data",
         "boost":2.5
       }
     }
   }
   ```

   单独对某字段boost：

   ```json
       "query":{
         "multi_match":{
           "query":"elasticsearch big data",
           "fields":["name^3","description"] //^3表示3倍
         }
       }
   ```

5. 使用查询再打分可以减小评分操作的性能影响。

6. 使用function_score定制得分

   ```json
   "query"：{
     "function_score":{
     	"query":{
     	    "match":{
           "description":"elasticsearch"
           }
     	},
     	"functions":[]//函数列表，可使用weight函数
     }
   }
   ```

7. 合并得分。
   - score_mode（打分模式）: multiply、sum、avg、first、max、min
   - boost_mode（提升权重的模式）: sum、avg、max、min、replace

8. field_value_factor函数

   ```json
   {
     "field_value_factor":{
       "field":"reviews",
       "factor":2.5,
       "modifier":"In"
     }
   }
   ```


9. 脚本（script_score)可以完全控制得分。
10. 随机（random_score)可以对文档进行随机排序。
11. 衰减函数有：线性，高斯曲线，指数。
12.  字段数据：某个字段的全部唯一值。

13. 管理字段数据的方式：
    - 限制字段数据使用的内存
    - 字段数据断路器
    - 使用文档值(doc values)避免内存的使用

## 第七章：使用聚集来探索数据

1. 聚集分类：度量型（最大值、最小值、标准差等）；桶型（标签组），允许嵌套子聚集。

2. 使用terms聚集来获取流行标签

   ```bash
   curl -H 'Content-Type:application/json' 'localhost:9200/get-together/group/_search?pretty' -d'{
     "query":{
       "match":{
         "location":"Denver"
       }
     },
     "aggs":{
       "top_tags":{
         "terms":{
           "field":"tags.verbatim"
         }
       }
     }
   }'
   ```

3. 统计数据

   enable fielddata on `text` fields: <https://www.elastic.co/guide/en/elasticsearch/reference/6.4/fielddata.html>

4. 高级统计：extended_stats，可以计算方差，平方值，标准差

5. 近似统计：百分比区间，基数

6. 多桶聚集

   ![](assets\多桶聚集的类型.PNG)

7. 多桶聚集分为：terms聚集、range聚集和histogram聚集。

8. terms聚集

9. range/date_range聚集

   ```json
   "ranges":{
     {"to":4},
     {"from":4,"to":6},
     {"from":6}
   }
   ```

10. histogram/date_histogram聚集

    定义一个固定的间距，例如10，它将划分[0,10),[10,20)，...区间。

    ```json
    "histogram":{
      "script":"XXX.length",
      "internal":1
    }
    ```

11. 嵌套聚集：aggs里面嵌套aggs。例如在attendees字段上应用term聚集，并在其中嵌套top_hits聚集。

12. 单桶聚集：global聚集，filter聚集，missing聚集。

## 第八章：文档间的关系

1. 对象类型 -> 类似于一个JSON对象
2. 嵌套类型 -> 类似于切分一个JSON对象为多个JSON对象（在创建映射时指定type为nested）

3. 反规范化：NoSQL常用于将一篇文档包含所有相关的数据，而不是SQL的表连接查询。这种技术的优点在于将数据进行复制，从而避免高成本的关系处理；缺点就是数据存在冗余，不够简洁。

4. include_in_root使得可以同时支持嵌套映射和普通查询。
5. include_in_parent使得支持多层级嵌套文档。

6. 查询计算得分，过滤不计算得分，因此过滤更快。
7. 在_parent字段指定父辈ID来指定文档父子关系。
8. has_parent和has_child查询和过滤器。
9. 对象映射，用于一对一关系；嵌套文档和父子结构，用于一对多关系；反规范化，用于多对多关系。

## 第九章：向外扩展

1. 广播或单播来发现节点，加入集群。
2. 主节点和非主节点之间通过心跳ping机制间隔性地维持集群的健康状态。
3. Elasticsearch有一种停用节点的方式，叫decommission，先待停用节点的全部分片转移到集群其他节点。
4. 轮流重启是一种升级ES节点的方式。
5. curl 'localhost:9200/_cat'可以查看 _cat API接口清单。
6. 可以为索引创建别名alias。
7. 可以使用自定义的路由值来索引文档，查询文档。

## 第十章：提升性能

1. bulk批量API接口操作：index/create,update,delete。
2. 影响ES分段的处理：刷新和冲刷，合并的策略，存储和存储限流。

3. rpm/deb包安装的的ES，数据存储在/var/lib/elasticsearch/data。
4. 不需要重用的东西不需要进行缓存，因为没有意义。

5. 分片缓存作用于分片级别上。
6. 对于JVM缓存，堆太小，会引起频繁的垃圾回收和OOM；堆太大，会占用更多的计算资源。可以考虑“一半”法则分配给ES。
7. 缓存预热器可以激活缓存。
8. 通配符查询，前缀查询，侧边N元语法查询。
9. 滑动窗口应用于词条，N元语法应用于字符。
10. 分片中如果没有均衡的文档频率，请使用dfs_query_then_fetch。

## 第十一章：管理集群

1. 将索引模板应用于新建的索引有两种方式：REST API，配置文件。

2. logstash-XX-XX-2019这种索引名称有利于按时间查找日志。
3. 不同区域节点的分配感知。
4. 集群状态为黄色，一般为主副分片在同一个节点，解决方法为添加新节点，让副分片可以分配到新节点。

5. CPU：慢查询日志和慢索引日志，集群健康API，线程信息。
6. 内存：最多50%的系统内存，节点级别的过滤器缓存使用的是LRU算法。

7. 快照API可以备份数据，每个后续快照都是前一个版本快照的差量。快照可以备份到共享的文件系统或者URL。资料库插件有 Amazon S3和Hadoop HDFS，用于扩展云服务。

## 附录A：地理位置

1. 判断某个点属于某个形状? 
   - 边界盒（左上角点，右下角点）
   - 多边形
   - 地理散列（单元格匹配比较）

## 附录B：插件

1. 分类：站点插件和代码插件。站点插件只是提供了由ES服务支持的网页。代码插件包含了JVM代码。
2. bin/plugin -install XXXX 形式来安装插件。
3. http://localhost:9200/_plugin/<name> 形式来访问站点插件。

## 附录C：高亮

1. 高亮查询

   ```json
"query":{
       "match":{
           "title":"elasticsearch"
       }
   },
   "hightlight":{
     "no_match_size":100,//没有匹配的字段显示最多100个字符
     "required_field_match":true,//仅高亮和查询匹配的片段
     "fields":{
         "title":{},
         "description":{}
     }
   }
   ```
   
2. 高亮的其它选项：
   - 调整碎片大小（fragment_size）和数量（number_of_fragments）
   - 修改高亮标签（指定pre_tags和post_tags）和编码（指定encoder为html）

3. 后高亮器原理是将高亮字段的index_options设为offsets，存储了每个词条在索引中的位置和偏移量。

## 附录D: ES的监控插件

## 附录E: 使用渗透器将搜索颠倒

1. 步骤：

   ![](assets\precolator-1570610362702.PNG)

   

## 附录F：为自动完成和“你是指”功能使用建议器

1. ES通过建议器，提供“你是指”自动补全的功能。
2. 建议器有四类：词条（term），词组（phrase），补全，上下文。