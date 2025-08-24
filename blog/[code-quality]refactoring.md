# 重构

> 本文摘抄自《[图灵程序设计丛书]. 奔跑吧，程序员：从零开始打造产品、技术和团队》第 6 章：重构，仅做少量删减。

下面是一个 BookParser 解析类初始的样子。
```java
import java.io.*;

public class BP {
    public void cvt(File i, File o) {
        BufferedReader r = null;
        BufferedWriter w;
        String l, j = "[";
        String[] p;
        try {
            r = new BufferedReader(new FileReader(i));
        } catch (FileNotFoundException e) {}
        try {
            while ((l = r.readLine()) != null) {
                p = l.split(",");
                if (!p[3].equals("fiction")&& !p[3].equals("nonfiction")) {
                    continue;
                }
                j += "{";
                j += "title:\"" + p[0] + "\",";
                j += "author:\"" + p[1] + "\",";
                j += "pages:\"" + Integer.parseInt(p[2]) + "\",";
                j += "category:\"" + p[3] + "\"";
                j += "},";
            }
            try {
                r.close();
            } catch (IOException e) {
            }
        } catch (IOException e) {}
        j += "]";
        try {
            w = new BufferedWriter(new FileWriter(o));
            w.write(j);
            w.close();
        } catch (IOException e) {
        }
    }
}
```

这是一个叫 BP 的类，有一个叫 cvt 的方法，似乎在读入一个叫 i 的文件，并在生成一个叫 j 的 String 时对该文
件的内容进行遍历，然后把 j 写入到一个叫 o 的文件。显然，应该存在更好的变量名、函数名和类名。

## 更好的命名

```java
public void convert (File inputCsv, File out){
    BufferedReader reader = null;
    BufferedWriter writer;
    String tmp, data = "[";
    String[] parts;
```
- `cvt(File i, File o)` => 改成 `public void convert(File inputCsv, File outputJson)`
- 变量也从不太直观的字母 r、w 变成了 reader、writer 等有含义的单词。

更进一步，变量的命名还可以更加具体
```java
BufferedReader csvReader = null;
BufferedWriter jsonWriter;
String line, json = "[";
String[] fields;
```
- 变量名称变得更加具体，例如 reader -> csvReader, writer -> jsonWriter
- 变量名称变得更加贴近代码表示的事物 `tmp, data` -> `line, json`; `p -> fields`

根据代码逻辑，这个方法可以改名为 `public void convertCsvToJson(File inputCsv, File outputJson)`
String 变量可以再次修改为和 CSV 处理相关的名称
```java
String csvLine, json = "[";
String[] csvFields;
```

接着看下来，这部分代码非常灾难。
```java
if (!p[3].equals("fiction")&& !p[3].equals("nonfiction")) {
    continue;
}
j += "{";
j += "title:\"" + p[0] + "\",";
j += "author:\"" + p[1] + "\",";
j += "pages:\"" + Integer.parseInt(p[2]) + "\",";
j += "category:\"" + p[3] + "\"";
j += "},";
```
一连串的问题产生：
- csvFields 的第三个下标 csvFields[3]（即 p[3]）有什么特殊的？
- 为什么要将 csvFields[3] 分别和“fiction”及 “nonfiction”做比较？ 
- csvFields[0]、csvFields[1] 等都存放了什么？

把这些魔法数字替换成命名的常量是个不错的主意。在 Java 中定义常量的方法之一就是使用一个 enum：

```java
public enum CsvColumns {
    TITLE, AUTHOR, PAGES, CATEGORY
}
```

在一个 enum 中，每一个常量都有一个名称以及根据其定义的顺序得到的序号。
例如，CsvColumns.TITLE.ordinal()为 0 而 CsvColumns.PAGES.ordinal() 为 2。

现在代码可以变成
```java
String title = csvFields[TITLE.ordinal()];
String author = csvFields[AUTHOR.ordinal()];
Integer pages = Integer.parseInt(csvFields[PAGES.ordinal()]);
String category = csvFields[CATEGORY.ordinal()];
if (!category.equals("fiction") && !category.equals("nonfiction")) {
    continue;
}
json += "{";
json += "title:\"" + title + "\",";
json += "author:\"" + author + "\",";
json += "pages:\"" + pages + "\",";
json += "category:\"" + category + "\"";
json += "},";
```

可以更清晰地看出 CSV 文件的每一行都包含了 4 列（title、 author、pages 和 category），而 category 列的值必须是“fiction”或者“nonfiction”。这也
给我们启发，可以针对 category 列采用另一个 enum：
```java
public enum Category {
    fiction, nonfiction
}
```
可以使用 valueOf 函数将一个 String 转化为相应的 enum 值（如果匹配的值没有找到话则抛出异常）， 不再需要专门
将 category 和“fiction”“nonfiction”这两个值做比较：`Category category = Category.valueOf(csvFields[CATEGORY.ordinal()]);`

### 遵循约定
- 你会不会把变量在一个地方命名为 recordNum，在另一个地方又命名为 numRecords ？
- 你会不会一会儿把接口叫作 PaymentProcessor，一会儿又叫 IPaymentProcessor ？
- 有时把实现 命名为 CreditCardPaymentProcessor，有时又命名为 CreditCardPaymentProcessorImpl ？

### 错误处理
```java
try {
    csvReader = new BufferedReader(new FileReader(inputCsv));
} catch (FileNotFoundException e) {}
        
try {
    jsonWriter = new BufferedWriter(new FileWriter(outputJson));
    jsonWriter.write(json);
    jsonWriter.close();
} catch (IOException e) {}
```
如果我们运行这段代码而没有生成 JSON 文件，缺乏恰当的错误处理会使调试变得很困难。
我们不清楚代码是在读取 CSV 文件时失败、在 CSV 中没有有效的记录、JSON 文件的路径是无效的，还是硬盘空间已经不足。

因此，遇到异常必须抛出错误，即 throws IOException。
```
public class BP2 {
    public void convertCsvToJson(File inputCsv,
                                 File outputJson) throws IOException {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(inputCsv));
             BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(outputJson))) {}            
    }
}
```

### 数据结构抽象为数据类
表示数据的 规范做法就是创建一个类：
```java
public class Book {
    private String   title;
    private String   author;
    private int      pages;
    private Category category;
    // (省略了构造函数和 getter 方法)
}
```
于是，上面的代码重构成
```java
List<Book> books = new ArrayList<>();
while ((csvLine = csvReader.readLine()) != null) {
    csvFields = csvLine.split(",");
    String title = csvFields[TITLE.ordinal()];
    String author = csvFields[AUTHOR.ordinal()];
    Integer pages = Integer.parseInt(csvFields[PAGES.ordinal()]);
    Category category = Category.valueOf(csvFields[CATEGORY.ordinal()]);
    
    books.add(new Book(title, author, pages, category));
}
```

如果使用 Jackson 的 ObjectMapper 类，只需要两行代码就可以将上面的 Book 对象的 List 转换到 JSON 文件中：
```java
ObjectMapper mapper = new ObjectMapper();
mapper.writeValue(outputJson, books);
```

也可以使用 Apache Commons CSV 库简化 CSV 的解析过程。
CSVParser 类可以使用 parse 方法读取一个 CSV 文件并用 withHeader 方法把标签赋给每一列：
```java
List<CSVRecord> records = CSVFormat
    .DEFAULT
    .withHeader(TITLE.name(), AUTHOR.name(), PAGES.name(), CATEGORY.name())
    .parse(new FileReader(inputCsv))
    .getRecords();
```
于是，遍历 records 的代码变得更简短了
```java
for (CSVRecord record : records) {
    String title = record.get(TITLE);
    String author = record.get(AUTHOR);
    Integer pages = Integer.parseInt(record.get(PAGES));
    Category category = Category.valueOf(record.get(CATEGORY));
    books.add(new Book(title, author, pages, category));
}
```

整个方法如下：
```java
public void convertCsvToJson(File inputCsv, File outputJson) throws IOException {
    List<Book> books = new ArrayList<>();
    List<CSVRecord> records = CSVFormat
            .DEFAULT
            .withHeader(TITLE.name(), AUTHOR.name(), PAGES.name(), CATEGORY.name())
            .parse(new FileReader(inputCsv))
            .getRecords();

    for (CSVRecord record : records) {
        String title = record.get(TITLE);
        String author = record.get(AUTHOR);
        Integer pages = Integer.parseInt(record.get(PAGES));
        Category category = Category.valueOf(record.get(CATEGORY));
        books.add(new Book(title, author, pages, category));
    }
    
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(outputJson, books);
}
```

## 单一职责原则
上面的代码已足够简练，但是还存在一个问题：这个函数做了太多的事情，数据处理和文件 IO 全在一起。

首先，要把将一行 CSV 格式的数据转换为一个 Java 对象的代码拿出来放到一个单独的函数中，名为 parseBookFromCsvRecord：
```java
public Book parseBookFromCsvRecord(CSVRecord record) {
    String title = record.get(TITLE);
    String author = record.get(AUTHOR);
    Integer pages = Integer.parseInt(record.get(PAGES));
    Category category = Category.valueOf(record.get(CATEGORY));
    return new Book(title, author, pages, category);
}
```

创建一个叫 parseBooksFromCsvFile 的函数去读取 CSV 文件，并用 parseBookFromCsvRecord 将其转换为 Book 对象的 List：
```java
public List<Book> parseBooksFromCsvFile(File inputCsv) throws IOException {
    List<CSVRecord> records = CSVFormat
        .DEFAULT
        .withHeader(TITLE.name(), AUTHOR.name(), PAGES.name(), CATEGORY.name())
        .parse(new FileReader(inputCsv))
        .getRecords();
    
    List<Book> books = new ArrayList<>();
    for (CSVRecord record : records) {
        books.add(parseBookFromCsvRecord(record));
    }
    return books;
}
```

最后把将 Book 对象的 List 转换为 JSON 的代码放到一个独立函数中，名为 writeBooksAsJson：
```java
public void writeBooksAsJson(List<Book> books, File outputJson) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(outputJson, books);
}
```

万事俱备，convertCsvToJson 如今可以减少到短短两行：
```java
public void convertCsvToJson(File inputCsv, File outputJson) throws IOException {
    List<Book> books = parseBooksFromCsvFile(inputCsv);
    writeBooksAsJson(books, outputJson);
}
```

## 松耦合
下面这 2 节和上面的 BP 例子没有直接联系。

### 内部实现依赖性
NewsFeed 类之前直接从 User 类中提取用户 id ：
```java
long userId = user.data().getProfile().getDatabaseKeys().id;
```
变成在 User 类中公开 getId() 方法：
```java
public long getId() {
    return data().getProfile().getDatabaseKeys().id;
}
```
对 getId() 更高级抽象的依赖降低了耦合。

### 系统依赖性
```java
Date oneMonthAgo = new DateTime().minusDays(30).toDate();
```
它调用 new DataTime() 查询当前的日期和时间，所以 getLatestArticlesSharedByUser 不是幂等的：
每次运行代码都会有不同的表现，对系统时钟的依赖性使得代码的测试和推导变得更加困难。

更好的设计是依赖性注入，即让客户端传递依赖性，而不是把它硬编码在 getLatestArticlesSharedByUser 中，即将依赖关系反转过来。
```java
List<Article> getLatestArticlesSharedByUserSince(User user, Date since) {
    List<Article> articles = GlobalCache.get(user.getId());
    if (articles == null) {
        String query = "select * from articles where userId = ? AND date > ?";
        articles = parseArticles(DB.query(query, user.getId(), since));
        GlobalCache.put(user.getId(), articles);
    }
    return articles;
}
```

### 库依赖性
NewsFeed 类使用一个叫 DB 的库去访问数据库：
```java
String query = "select * from articles where userId = ? AND date > ?";
articles = parseArticles(DB.query(query, userId, oneMonthAgo));
```
上面的代码偏向底层实现，可以使用接口包装一层 API：
```java
public interface ArticleStore {
    List<Article> getArticlesForUserSince(long userId, Date since);
}
```

现在，代码变成了
```java
public class NewsFeed {
    private final ArticleStore articleStore;
    public NewsFeed(ArticleStore articleStore) {
        this.articleStore = articleStore;
    }
    
    List<Article> getLatestArticlesSharedByUserSince(User user, Date since) {
        List<Article> articles = GlobalCache.get(user.getId());
        if (articles == null) {
            articles = articleStore.getArticlesForUserSince(user.getId(), since);
            GlobalCache.put(user.getId(), articles);
        }
        return articles;
    }
}
```


### 全局变量
NewsFeed 类中问题最大的依赖性就是使用了 GlobalCache 类：
```java
List<Article> articles = GlobalCache.get(userId);
// ...
GlobalCache.put(userId, articles);
```

NewsFeed 类并不需要知道实现缓存的底层细节，它需要的只是缓存的高级抽象。 可以先来定义一个传递缓存的接口：
```java
public interface PassthroughCache<K, V> {
    V getOrElseUpdate(K key, Supplier<V> valueIfMissing);
}
```

将缓存抽象注入 NewsFeed 的构造函数：
```java
public class NewsFeed {
    private final ArticleStore articleStore;
    private final PassthroughCache<Long, List<Article>> cache;
    
    public NewsFeed(ArticleStore articleStore, PassthroughCache<Long, List<Article>> cache) {
        this.articleStore = articleStore;
        this.cache = cache;
    }
    
    List<Article> getLatestArticlesSharedByUserSince(User user, Date since) {
        return cache.getOrElseUpdate(
            user.getId(),
            ()-> articleStore.getArticlesForUserSince(user.getId(), since)
        );
    }
}
```

## 高内聚
考虑下面这个类：
```java
public class Util {
    void generateReport(){/* ... */}
    void connectToDb(String user, String pass) {/* ... */}
    void fireTheMissles(){/* ... */}
}
```
这是一个技术上正确，但完全没有意义的类。这些方法是毫不相关的，产生了一个低内聚的类。

类名中的单词“util”是低内聚的典型特征，名称中带有 util 的类通常是不知道要放哪里的不相关函数的大杂烩。

下面是一个隐晦的例子：
```java
public interface HttpClient {
    byte[] sendRequest(String url, Map<String, String> headers, byte[] body);

    Document getXml(String url);

    int postOnSeparateThread(String url, String body, ExecutorService executor);

    void setHeader(String headerName, String headerValue);

    boolean statusCode();
}
```
这些方法也是低内聚的，因为它们是在许多不同的抽象层次上操作的：
- sendRequest 把字节数组用在请求和响应的 body 上，postOnSeparateThread 把
String 用于请求的 body 而没有返回响应的 body（它只返回一个状态代码），
getXml 没有请求 body，它返回一个 XML Document 给响应的 body。
- postOnSeparateThread 负责底层的线程处理细节（通过 ExecutorService），但其他方法都不会这么做。
- setHeader 和 statusCode 方法暗示了 HttpClient 是可变的，可以存储下一个请求或者前一个响应的状态。
这些方法如何与其他方法交互并不清楚，特别是 sendRequest，该方法把一个 HTTP headers 的 Map 对象作为参数之一。

高内聚：所有的变量和方法都应该在同一抽象层次上操作
```java
public interface HttpClient {
    HttpResponse sendRequest(HttpRequest request);
}

public interface HttpRequest {
    URL getUrl();
    Map<String, String> getHeaders();
    byte[] getBody();
}

public interface HttpResponse {
    Map<String, String> getHeaders();
    byte[] getBody();
}
```

如果我们不只手动设置 HTTP 头部和处理请求 body 的字节数组，而是要处理更高级别的请求，可以创建一个 HttpRequestBuilder 类：
```java
public class HttpRequestBuilder {
    public HttpRequest postJson(String url, String json) throws Exception {
        return new BasicHttpRequest(
            url,
            ImmutableMap.of("Method", "POST", "Content-Type", "application/json"),
            json.getBytes("UTF-8")
        );
    }
}
```


如果要用更高级的方式去处理响应的 body 而不使用字节数组，可以创建一个 HttpResponseParser 类：
```java
public class HttpResponseParser {
    public Document asXml(HttpResponse response) {
        return DocumentBuilder.parse(new ByteArrayInputStream(response.getBody()));
    }
}
```

任何有关线程处理的任务都可以由 HttpClient 实现去处理：
```java
public class ThreadedHttpClient implements HttpClient {
    private final ExecutorService executor;
    public ThreadedHttpClient(ExecutorService executor) {
        this.executor = executor;
    }
    
    public HttpResponse sendRequest(HttpRequest request) {
        try {
            return executor.submit(()-> doSend(request)).get();
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
    }
}
```

## 代码注释
不要为糟糕的代码注释——重新写吧。

## 小结
重构的意义：重构是出色编程的精髓。

合理的重构是必不可少的，我们不可能一开始就能正确地设计。和论文的初稿一样，代码的初稿也会是凌乱、不完整、需要重写的。
任何代码实现的第一个版本总是存在问题的。随着继续编写代码，我们会更好地理解问题，重构的本质就是根据新的理解去改进它。

阅读整洁的代码比阅读难看的代码要少花一个数量级的时间，而更新整洁的代码比更新难看的代码要少花两个数量级的时间。由于阅读、
修改代码和编写新代码所花时间的比例大概是 50∶1，所以我们永远都该编写整洁的代码。

难看的代码会想方设法出现在每一个项目中，因为围绕着代码的产品、人以及生态系统都会发生变化，我们在过去做的决定可能无法满足未来的需求。
所以，我们不仅在开始时要编写整洁的代码，还必须不断对代码进行重构，让它适应新的需求。
技术债务的真正成本不在于它会导致更多的 bug 和错过最后期限，而是会让人痛苦。

编程在很大程度上就是一门手艺。你会因为选择了正确的工具、努力工作并制作出精美的东西而获得深深的成就感。
它的美不仅源于对用户而言漂亮的外观，还源于其精工细作的内部运作方式。优雅的解决方案会让程序员愉悦，丑陋的拼凑则会让程序员悲伤不已。

> 一个破碎的窗户，如果长期以来没有修好，就会给建筑中的居民逐渐带来一种被抛弃的感觉——感觉管理者并没有在意这栋建筑。
> 这样会再有一个窗户被损坏。人们也开始乱丢东西、涂鸦，建筑被严重损坏。很快，这栋建筑的损坏程度使得其主人都不想再维修它了，
> 放弃的意愿就成了现实。
>                          ——Andrew Hunt 和 David Thomas，《程序员修炼之道》
