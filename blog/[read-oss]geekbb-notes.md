# geekbb源代码阅读笔记

> 源仓库地址：https://github.com/hellokaton/geekbb

## 数据结构建模

> sql/geekbb.sql

- comments 评论表 => 业务逻辑
- ip_hits IP 封禁表 => 系统
- logs 系统日志表 => 系统
- nodes 节点表。含义是帖子分区。=> 业务逻辑
- notices 消息通知表。=> 业务逻辑
- profiles 个人表。根据用户注册时填写信息生成。=> 业务逻辑
- promotions 广告位置。=> 业务逻辑
- relations 关系表。用于 LOVE: 点赞 COLLECT: 收藏 FOLLOW: 关注 用户 / 帖子。=> 业务逻辑
- settings 系统配置表。=> 系统
- topic_tags 话题标签表。外键关联到 topic。这个表定义了一个帖子有哪些 tag。=> 业务逻辑
- topics 帖子表。思考：一些衍生统计信息，例如点赞数，也要设计字段。=> 业务逻辑
- users 用户表。这个 bbs 系统记录的关于个人的一些信息，例如最后一次登录时间。=> 业务逻辑

分析：

- 表名使用 users 复数形式而不是 user 单数，表示一个用户表包含多个用户。
- 一些复杂的字段，采用单下划线分割，例如 tag_name.
- 表中 ID 字段的命名，非关联表一律设计为 id，而不能是 xxxid 的形式。
    - 对于非关联表：例如 topic 表和 tag，如果按照前缀标注法，它们的 id 字段都是 tid，容易混淆。
    - 对于关联表：因为有两个 ID 字段，因此需要前缀区分。例如 post_tag 表中定义 post_id、tag_id。

### 表字段设计

- 根据经验假设，可以按照更新频率高低来分割冷字段和热字段，从而切分到冷表和热表。
    - 冷表：例如一些不太会经常访问 / 更新的用户字段，归到冷表。如果冷表还是字段太多，可以考虑进一步按逻辑切分，可以分为基础表和扩展表。
    - 热表：对于粉丝数，点赞数这些热字段，可以单独放到动态信息统计表。

其实不管是先按冷热分，然后再按逻辑分；还是先按逻辑分，再按冷热分，结果似乎都差不多。

两种策略可以结合使用，适应复杂业务需求。例如：

- 用户基本信息表（冷表）：user_cold_basic。
- 用户扩展信息表（冷表）：user_cold_extend。
- 用户统计信息表（热表）：user_hot_statistics。

下面是一个相对复杂的分割示例：

```sql
-- 冷表：基本信息
CREATE TABLE user_cold_basic (
    id INT PRIMARY KEY,                  -- 用户主键
    username VARCHAR(255) NOT NULL,     -- 用户名
    email VARCHAR(255) NOT NULL,        -- 邮箱
    profile_picture VARCHAR(255),       -- 头像
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP -- 创建时间（记录元信息）
);


-- 冷表：扩展信息
CREATE TABLE user_cold_extend (
    id INT PRIMARY KEY,                 -- 用户主键，与 user_cold_basic 关联
    bio TEXT,                           -- 自我描述
    address TEXT,                       -- 地址
    FOREIGN KEY (id) REFERENCES user_cold_basic(id) ON DELETE CASCADE
);


-- 动态统计信息表
CREATE TABLE user_hot_statistics (
    id INT PRIMARY KEY,                 -- 用户主键，与 user_cold_basic 关联
    follower_count INT DEFAULT 0,       -- 粉丝数
    like_count INT DEFAULT 0,           -- 点赞数
    comment_count INT DEFAULT 0,        -- 评论数
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    FOREIGN KEY (id) REFERENCES user_cold_basic(id) ON DELETE CASCADE
);
```

### 级联删除如何设计

- 数据库驱动（ON DELETE CASCADE）：更适合简单的多对多关联场景，保证性能和数据一致性。=> 数据一致性，关联表数据必须严格清理。
    - 注意：如果依赖数据库特性（如 ON DELETE CASCADE），那么在迁移到不支持或支持不同的数据库时，需要重新设计表结构和约束规则。
- 程序驱动：适合复杂的业务逻辑，可能需要附加操作（如记录日志、发送通知），某些关联数据可能需要保留，无法简单级联删除。=> 灵活性。
- 现代化建议：优先考虑数据库驱动的方案，并在必要时通过程序逻辑覆盖复杂场景。

## 项目文件目录说明

java：

- config 配置目录
    - StartupConfig.java 启动配置，做了大量初始化工作。
        - github OAuthService 的注入
        - JetbrickTemplateEngine 初始化
        - JDBC 初始化
        - 加载系统配置 Map
        - 加载 IP 黑名单
        - 加载 广告位 数据
    - TplFunction.java 大量的关于页面的 utils 静态工具函数。
- constants 都是一些常量配置，
    - 可以定义成 interface 的变量，也可以定义成普通类的静态变量。和 utils 包似乎有所重叠。
    - 一般使用单例模式来节约内存占用。
- controller 控制层 *。这一层业务逻辑复杂。后续单独分文件来分析说明。
- enums
    - ErrorCode.java: 以 `ErrorCode(int code, String msg)` 形式定义例如 `NOT_LOGIN(2001, "请登录后进行操作"),` ，表示错误码。
    - FavoriteType.java: 以 `FavoriteType(int type)` 形式定义例如 `LOVE(1), COLLECT(2);` ，表示某个用户对一个 topic
      是喜爱还是收藏。
    - LogAction.java: 以 `LogAction(String desc)` 形式定义例如 `DISABLE_USER("禁用用户: %s")` ，表示系统级别的动作。
    - Position.java: 以 `Position(String desc)` 形式定义例如 `RIGHT_TOP("右上角")` ，表示广告位置。
    - RelateType.java: 例如 `LOVE, COLLECT, FOLLOW` 定义关联关系。情况如下，参阅 `RelationService.java` ：
        - follow(Long uid, Long fansId) 一个用户关注某个用户
        - collectTopic(Long uid, String tid) 一个用户收藏某个 topic
        - loveTopic(Long uid, String tid) 一个用户点赞某一个 topic
    - RoleType.java: 枚举 MEMBER, ADMIN, MASTER 三种角色。
        - ADMIN 主要负责日常管理，而 MASTER 则负责系统的整体管理和决策。
        - 如果论坛规模很大，可能还需要细分一些运营角色，风纪委员等等。
    - TopicOrder.java: DEFAULT, RECENT, POPULAR 三种枚举情况。DEFAULT 和 RECENT 功能似乎重复，可以删掉
      default。一般都是时间或者热度排序。
    - TopicType.java: BLOG, TOPIC 两种枚举，分别表示博客文章或者主题。
        - BLOG 文章含有 tags
        - TOPIC 主题没有 tags
    - UserState.java: 用户状态。NORMAL(1), DISABLE(2), DELETE(3) 分别表示正常、禁用、逻辑删除（不是物理删除）。
- exception 自定义异常。
- hook 拦截器设计
    - BaseHook.java web 请求公共拦截，用于 IP 过滤，自动根据 cookie 免登录，特殊路径 `/admin` 权限检测。
- model 模型层 *。
    - db
        - comment 评论表。其实 owner 这个字段（帖子作者 username）不应该出现在此表中，而应该由 tid 推导。
        - IpHit IP 黑名单表。
        - Log 记录用户的动作行为，用于统计或者审计。
        - Node 帖子分类。注意有一个 pid 表示父级，例如 programming 下面可以分 java、go 等等。
        - Profile 用户扩展信息表。侧重的是账号相对公开的数据，例如外部第三方社交账号关联等等。更新频率低。
            - 这个表有一个字段为 followers，表示该用户的粉丝数。需要分析：
            - 如果 followers 是一个简单的展示字段，系统规模较小，粉丝数更新频率低，保留在 Profile 表中也无伤大雅。
            - 否则，将其独立为一个动态表更为合理。因为 profile 表本来是设计来保存静态属性的。
        - Promotion 广告表。
        - Relation 关系表。A action B 模式，例如 A 用户关注 B 用户。
        - Setting 配置表。本质是 key-value 映射。
        - ~~Tag 标签表 ~~。和 TopicTag 重叠了，可以删除。
        - Topic。主题或者文章。这个表含有很多字段。要注意分表，根据更新频率分离热字段和冷字段。
        - TopicTag。 帖子标签表。关键字段是 topic_id 和 tag_id。
        - User 用户基本信息表。关注用户的核心信息，更新频繁高。包含 email，注册时间、登录时间、更新时间等等。
    - param
        - GithubCallback.java 对 Github OAuth 回调结果中可以获得的字段进行建模
        - QueryTopic.java 对查询 Topic 的参数建模
        - TopicOpt.java 对设置帖子状态参数的建模，例如是否锁定帖子
    - vo 视图对象，类似前端概念的 XXUIState。目的是方便前端显示，或用作提供给前端 web API 的对象定义。
        - XXVO extend db 模型定义的方式，例如 `public class TopicVO extends Topic`, 然后添加适用于显示的字段和逻辑.
        - TopicVO.java： TopicVO extends Topic，例如这个字段 isLove 用于标志是否已经点赞，在已登录时判断。
- service 服务层 *。这一层业务逻辑复杂。后续单独分文件来分析说明。
- ~~task~~ 未使用。
- utils 杂项工具库。其实这个包一直以来都是习惯，优点是函数复用，缺点是经常会忘记 utils 还有这么一个函数，然后在 model
  层内部自己自管理实现。
    - ArrayUtils: `append(long[] arr, long element)` `remove(long[] arr, long el)` 的实现
    - GeekDevUtils: 和此 App 紧密相关的公开变量或者函数。
- validator 参数校验逻辑放于这个包。例如，对 topic 入参数据的验证。
- Application.java 唯一入口。

### Controller

```
│   AuthController.java Github OAuth 注册和登录服务
│   BlogController.java 博客服务
│   IndexController.java 首页、搜索页、主题页
│   MiscController.java 对于 /about、/faq、logout 路由的处理
│   TopicController.java 查看主题详情、点赞和取消点赞、收藏和取消收藏、评论主题、创建主题、发布主题
│   UserController.java 查看个人主页、修改个人信息页面、修改个人信息
└───admin
        AdminController.java  admin 相关 index.html、logs.html、site.html 显示
        IPHitController.java 保存一个 ip 到 ip 黑名单列表；显示 IP 黑名单列表
        NodeController.java 分类列表、新建 node、node 逻辑删除 (state 设置 0)
        PromotionController.java 广告列表数据、广告列表 JSON 数据（读取 + 转义）、保存单个广告、广告批量重顺序。
        SysController.java 见下文
        TopicController.java 帖子 lockOrDelete API，状态划分'1: 正常 2: 锁定 0: 删除'
        UserController.java 查询用户列表、更改当前用户 Role 属性、更改用户 state 状态
```

说明：

- EmojiParser.parseToAliases(text)：😊 可能会被转换为 :smiley: 这样的格式
- SysController 中有一个 reset() 函数，里面关键 SQl 如下：

```sql
"update nodes a set a.topics = (select count(*) from topics b where b.state = 1 and b.node_id = a.nid)"
"update settings set svalue = (select count(*) from topics where state = 1 and topic_type = 'TOPIC') where skey = 'count.topics'"
"update settings set svalue = (select count(*) from topics where state = 1 and topic_type = 'BLOG') where skey = 'count.blogs'"
"update settings set svalue = (select count(*) from comments) where skey = 'count.comments'"
"update settings set svalue = (select count(*) from users where state = 1) where skey = 'count.users'"
"update topics a set a.loves = (select count(*) from relations b where b.event_id = a.tid and b.relate_type = 'LOVE')"
"update topics a set a.collects = (select count(*) from relations b where b.event_id = a.tid and b.relate_type = 'COLLECT')"
```

### Service

```
CommentService.java findCommentsByTid、findRecentCommentsByUsername、sendComment
LogService.java 按页查询 logs:`findLogs(Log log, int page)`、保存 log
RelationService.java 见下文
SettingsService.java settingPlus 方法的逻辑太过于暴力冗余，批量重建不合理。
TopicService.java 见下文
UserService.java
```

RelationService.java 这个类使用了 org.mapdb 库
> MapDB is an open-source (Apache 2.0 licensed), embedded Java database engine and collection framework.
> 它有内存模式和文件模式，可以当成一个数据库来使用，相当于增强了 Java 的集合库。

- follow(Long uid, Long fansId): uid 被 fansId 关注 => 更新 uid 的粉丝 Map、更新 Relation 表、更新 fansId 的关注 Map
- unfollow(Long uid, Long fansId): uid 被 fansId 取关 => 更新 uid 的粉丝 Map、更新 Relation 表、更新 fansId 的关注 Map
- collectTopic(Long uid, String tid): uid 收藏 tid
- unCollectTopic(Long uid, String tid): uid 取消收藏 tid
- loveTopic(Long uid, String tid)
- unLoveTopic(Long uid, String tid)
- sync(String to): 将 *.db 文件中 relation 表数据同步到对应的关系数据库，例如 MySQL。即 *.db -> MySQL；或者 MySQL -> *
  .db，将关系数据库数据读取到 *.db 中。

---

TopicService.java
- findTopics(QueryTopic queryTopic) 查看帖子列表
- List<TopicVO> recentTopic(String username) 查询某个用户最近发布的帖子
- List<Topic> findHotTopics(TopicType topicType) 查看热门帖子
- TopicVO findTopic(Long uid, String tid) 单个帖子信息。这个方法中，也会根据 uid 判断此用户是否已点赞 / 收藏这个 tid。
- String createTopic(Topic topic) 新建帖子。这里做一个发帖频率的限流，以及更新帖子统计计数器、通知被 @用户等等。
- saveTags(String tid, String[] tags) 这是一个私有方法。不过对于 tag 数组的添加方式是先删除之前所有 tags，然后再写入新 tags。
- Integer favoriteTopic(Long uid, String tid, FavoriteType favoriteType) 点赞 / 取消点赞、收藏 / 取消收藏
- List<TopicVO> queryTopics(final String q) 根据搜索词查询帖子列表, 使用 `"<font color=red>" + q + "</font>"` 对结果中关键词高亮。

---

UserService.java
- ProfileVO findProfile(String username) 这个方法的结果 VO 包含了用户的基本信息和扩展信息。
- void register(GithubCallback githubCallback) 用户注册同时写入 user 表（基本表）和 profile 表（扩展表）
- Page<User> findUsers(Integer page, String q) 后台分页查询用户列表

### src/main/resources
文件目录组织如下：
```
static/
  css/
  fonts/
  img/
  js/
  ...
templates/
  admin/
  layout/
  index.html
  faq.html
  ...  
```

- static/ 前端静态资源文件
- templates/ HTML 页面模本。后端渲染的方式，代码可读性很差。新项目一定要避免这种模式。

### 前端 html 模板使用后端变量

以 `header.html` 为例进行说明：
```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="keywords" content="${siteKeywords(keywords ?! '')}"/>
    <meta name="description" content="${siteDescription(description ?! '')}"/>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"  name="viewport" />
    <link rel="shortcut icon" href="/favicon.ico" />
    <link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
    #if(useCdn)
    <link rel="stylesheet" href="//cdn.bootcss.com/nprogress/0.2.0/nprogress.min.css" />
    <link rel="stylesheet" href="//cdn.bootcss.com/sweetalert/1.1.3/sweetalert.min.css"/>
    <link rel="stylesheet" href="//cdn.bootcss.com/highlight.js/9.12.0/styles/androidstudio.min.css">
    <script src="//cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    #else
    <link rel="stylesheet" href="/static/cdn/nprogress.min.css" />
    <link rel="stylesheet" href="/static/cdn/sweetalert.min.css"/>
    <link rel="stylesheet" href="/static/cdn/androidstudio.min.css">
    <script src="/static/cdn/jquery.min.js"></script>
    #end
    <link rel="stylesheet" href="/static/css/main.css?v=${version()}" />
    <title>${siteTitle(title ?! '')}</title>
</head>
<body>
<nav class="navbar navbar-expand-lg fixed-top box-shadow">
    <div class="container">
        <a class="navbar-brand" href="/" title="${siteConfig('site.title')}" alt="Geek Dev"></a>
        <button class="navbar-toggler text-dark" data-toggle="collapse" data-target="#navbar-content">
            <i class="czs-menu-l"></i>
        </button>

        <div class="col-4">
            <div class="search-from">
                <form class="form-inline" method="get" action="/search">
                    <input name="q" class="form-control mr-sm-2" type="text" placeholder="搜索..." style="border: none; border-radius: 0;"/>
                </form>
            </div>
        </div>

        <div id="navbar-content" class="collapse navbar-collapse">
            <div class="ml-auto">
                #if(null == GEEK_DEV_USER)
                <button class="btn btn-primary" data-toggle="modal" data-target="#signinModalPopovers">
                    <i class="czs-github-logo"></i> 登录
                </button>
                #else
                <div class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-secondary" href="#" id="navbarDropdownMenuLink"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="czs-user-l"></i> ${emoji(GEEK_DEV_USER.name)}
                    </a>
                    <div class="dropdown-menu"
                         style="min-width: auto; position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;"
                         aria-labelledby="navbarDropdownMenuLink" x-placement="bottom-start">

                        #if(GEEK_DEV_USER.role != 'MEMBER')
                        <a class="dropdown-item" href="/admin/index"><i class="czs-setting-l"></i> 管理 </a>
                        #end
                        <a class="dropdown-item" href="/u/${GEEK_DEV_USER.username}"><i class="czs-home"></i> 主页 </a>
                        <a class="dropdown-item" href="/settings"><i class="czs-setting"></i> 设置 </a>
                        <a class="dropdown-item" href="/logout"><i class="czs-out-l"></i> 注销 </a>
                    </div>
                </div>
                #end
            </div>
        </div>
    </div>
</nav>
```
meta 标签中的 `siteKeywords(keywords ?! '')` 函数由 `src/main/java/io/github/biezhi/geekbb/config/TplFunction.java`
定义，在 ·io.github.biezhi.geekbb.config.StartupConfig.load` 方法中全局注册，关键代码片段如下：
```java
// Template
JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
GlobalResolver         globalResolver = templateEngine.getGlobalResolver();
globalResolver.registerFunctions(TplFunction.class);
```

而 GEEK_DEV_USER 这种变量是定义在 Session 中的，位置 `io.github.biezhi.geekbb.utils.GeekDevUtils.addLogin`
```java
    public static void addLogin(User user) {
    Request request = WebContext.request();
    request.session().attribute(GeekDevConst.LOGIN_SESSION_KEY, user);

    Response response = WebContext.response();
    // 7 天
    int    maxAge = 7 * 24 * 60 * 60;
    String cookie = encodeId(user.getUid());
    response.cookie(GeekDevConst.LOGIN_COOKIE_KEY, cookie, maxAge);
}
```
- 服务端：将用户数据保存在 session 这个位于内存的Map结构中。
- 前端：将用户 Id 保存到响应的 cookie 数据中。