## ch02

```bash
# in linux
export FLASK_APP=hello.py

# In PyCharm on Windows
# remember not `set` prefix
$env:FLASK_APP="hello.py"
# echo #env:XXX

flask run
```

- 重载器
- 调试器 => `export FLASK_DEBUG=1`

全局上下文变量 current_app g request session

请求对象

```
form  一个字典，存储请求提交的所有表单字段
args 一个字典，存储通过 URL 查询字符串传递的所有参数
values 一个字典，form 和 args 的合集
cookies 一个字典，存储请求的所有 cookie
headers 一个字典，存储请求的所有 HTTP 首部
files 一个字典，存储请求上传的所有文件
get_data() 返回请求主体缓冲的数据
get_json() 返回一个 Python 字典，包含解析请求主体后得到的 JSON
blueprint 处理请求的 Flask 蓝本的名称；蓝本在第 7 章介绍
endpoint 处理请求的 Flask 端点的名称；Flask 把视图函数的名称用作路由端点的名称
method HTTP 请求方法，例如 GET 或 POST
scheme URL 方案（http 或 https）
is_secure() 通过安全的连接（HTTPS）发送请求时返回 True
host 请求定义的主机名，如果客户端定义了端口号，还包括端口号
path URL 的路径部分
query_string URL 的查询字符串部分，返回原始二进制值
full_path URL 的路径和查询字符串部分
url 客户端请求的完整 URL
base_url 同 url，但没有查询字符串部分
remote_addr 客户端的 IP 地址
environ 请求的原始 WSGI 环境字典
```

请求钩子

```
before_request
注册一个函数，在每次请求之前运行。

before_first_request
注册一个函数，只在处理第一个请求之前运行。可以通过这个钩子添加服务器初始化任务。

after_request
注册一个函数，如果没有未处理的异常抛出，在每次请求之后运行。

teardown_request
注册一个函数，即使有未处理的异常抛出，也在每次请求之后运行
```

Flask 响应对象

```
status_code HTTP 数字状态码
headers 一个类似字典的对象，包含随响应发送的所有首部
set_cookie() 为响应添加一个 cookie
delete_cookie() 删除一个 cookie
content_length 响应主体的长度
content_type 响应主体的媒体类型
set_data() 使用字符串或字节值设定响应
get_data() 获取响应主体
```

## ch03

Jinja2 变量过滤器

```
safe 渲染值时不转义
capitalize 把值的首字母转换成大写，其他字母转换成小写
lower 把值转换成小写形式
upper 把值转换成大写形式
title 把值中每个单词的首字母都转换成大写
trim 把值的首尾空格删掉
striptags 渲染之前把值中所有的 HTML 标签都删掉
```

表 3-2：Flask-Bootstrap 基模板中定义的区块

```
doc 整个 HTML 文档
html_attribs <html> 标签的属性
html <html> 标签中的内容
head <head> 标签中的内容
title <title> 标签中的内容
metas 一组 <meta> 标签
styles CSS 声明
body_attribs <body> 标签的属性
body <body> 标签中的内容
navbar 用户定义的导航栏
content 用户定义的页面内容
scripts 文档底部的 JavaScript 声明
```

使用 flask_moment 插件来处理日期相关问题。

## ch04 web 表单

依赖：

- flask_wtf
- wtforms

表 4-1：WTForms 支持的 HTML 标准字段

```
BooleanField  复选框，值为 True 和 False
DateField  文本字段，值为 datetime.date 格式
DateTimeField 文本字段，值为 datetime.datetime 格式
DecimalField 文本字段，值为 decimal.Decimal
FileField 文件上传字段
HiddenField 隐藏的文本字段
MultipleFileField  多文件上传字段
FieldList  一组指定类型的字段
FloatField  文本字段，值为浮点数
FormField  把一个表单作为字段嵌入另一个表单
IntegerField  文本字段，值为整数
PasswordField  密码文本字段
RadioField  一组单选按钮
SelectField  下拉列表
SelectMultipleField  下拉列表，可选择多个值
SubmitField  表单提交按钮
StringField  文本字段
TextAreaField  多行文本字段
```

表 4-2：WTForms 验证函数

```
DataRequired 确保转换类型后字段中有数据
Email 验证电子邮件地址
EqualTo 比较两个字段的值；常用于要求输入两次密码进行确认的情况
InputRequired 确保转换类型前字段中有数据
IPAddress 验证 IPv4 网络地址
Length 验证输入字符串的长度
MacAddress 验证 MAC 地址
NumberRange 验证输入的值在数字范围之内
Optional 允许字段中没有输入，将跳过其他验证函数
Regexp 使用正则表达式验证输入值
URL 验证 URL
UUID 验证 UUID
AnyOf 确保输入值在一组可能的值中
NoneOf 确保输入值不在一组可能的值中
```

---

`Post/ 重定向 /Get 模式 `，避免浏览器刷新后重新提交表单。

应用可以把数据存储在用户会话中，以便在请求之间“记住”数据。 用户会话是请求上下文中的变量，名为 session，像标准的 Python
字典一样操作。

---

利用 flask 内置的 flash() 函数来实现闪现消息提示。

## ch05 数据库

表 5-1：FLask-SQLAlchemy 数据库 URL

```
MySQL => mysql://username:password@hostname/database
Postgres => postgresql://username:password@hostname/database
SQLite（Linux，macOS） => sqlite:////absolute/path/to/database
SQLite（Windows） => sqlite:///c:/absolute/path/to/database
```

表 5-2：最常用的 SQLAlchemy 列类型

```
Integer int 普通整数，通常是 32 位
SmallInteger int 取值范围小的整数，通常是 16 位
BigInteger int 或 long 不限制精度的整数
Float float 浮点数
Numeric decimal.Decimal 定点数
String str 变长字符串
Text str 变长字符串，对较长或不限长度的字符串做了优化
Unicode unicode 变长 Unicode 字符串
UnicodeText unicode 变长 Unicode 字符串，对较长或不限长度的字符串做了优化
Boolean bool 布尔值
Date datetime.date 日期
Time datetime.time 时间
DateTime datetime.datetime 日期和时间
Interval datetime.timedelta 时间间隔
Enum str 一组字符串
PickleType 任何 Python 对象 自动使用 Pickle 序列化
LargeBinary str 二进制 blob
```

---

数据库迁移：SQLAlchemy 的开发人员编写了一个迁移框架，名为 Alembic。除了直接使用 Alembic 之
外，Flask 应用还可使用 Flask-Migrate 扩展。这个扩展是对 Alembic 的轻量级包装，并与
flask 命令做了集成。

使用 Flask-Migrate 管理数据库模式变化的步骤如下。

- (1) 对模型类做必要的修改。
- (2) 执行 flask db migrate 命令，自动创建一个迁移脚本。
- (3) 检查自动生成的脚本，根据对模型的实际改动进行调整。
- (4) 把迁移脚本纳入版本控制。
- (5) 执行 flask db upgrade 命令，把迁移应用到数据库中。

---

修改数据库的步骤与创建第一个迁移 类似。

- (1) 对数据库模型做必要的修改。
- (2) 执行 flask db migrate 命令，生成迁移脚本。
- (3) 检查自动生成的脚本，改正不准确的地方。
- (4) 执行 flask db upgrade 命令，把改动应用到数据库中。

实现一个功能时，可能要多次修改数据库模型才能得到预期结果。如果前一个迁移还未提
交到源码控制系统中，可以继续在那个迁移中修改，以免创建大量无意义的小迁移脚本。

## ch06 发送邮件

- 在 python shell 中直接发送，同步阻塞方式
- 在应用中集成电子邮件发送功能，使用异步线程来执行
- send_async_email() 函数的操作发给 Celery 任务队列

## ch07 应用目录组织

更新 requirements.txt

```bash
pip install pip-upgrader
pip-upgrade
```

其他参考：https://github.com/alanhamlett/pip-update-requirements 只更新 requirements 文本，不更新依赖。

## ch08 用户身份验证

- Flask-Login：管理已登录用户的用户会话
- Werkzeug：计算密码散列值并进行核对
- itsdangerous：生成并核对加密安全令牌
- Flask-Mail：发送与身份验证相关的电子邮件
- Flask-Bootstrap：HTML 模板
- Flask-WTF：Web 表单

扩展阅读：Defuse Security 的文章 —— “Salted Password Hashing - Doing it Right”。

表 8-1：Flask-Login 要求实现的属性和方法

```
is_authentcated 如果用户提供的登录凭据有效，必须返回 True，否则返回 False
is_active 如果允许用户登录，必须返回 True，否则返回 False。如果想禁用账户，可以返回 False
is_anonymous 对普通用户必须始终返回 False，如果是表示匿名用户的特殊用户对象，应该返回 True
get_id() 必须返回用户的唯一标识符，使用 Unicode 编码字符串
```

---

简单测试 itsdangerous 的使用逻辑。

```bash
(venv) $ flask shell
>>> from itsdangerous import TimedJSONWebSignatureSerializer as Serializer
>>> s = Serializer(app.config['SECRET_KEY'], expires_in=3600)
>>> token = s.dumps({'confirm': 23})
>>> token
'eyJhbGciOiJIUzI1NiIsImV4cCI6MTM4MTcxODU1OCwiaWF0IjoxMzgxNzE0OTU4fQ.ey ...'
>>> data = s.loads(token)
>>> data
{'confirm': 23}
```

同时满足以下 3 个条件时，before_app_request 处理程序会拦截请求。

- (1) 用户已登录（current_user.is_authenticated 的值为 True）。
- (2) 用户的账户还未确认。
- (3) 请求的 URL 不在身份验证蓝本中，而且也不是对静态文件的请求。要赋予用户访问身
  份验证路由的权限，因为这些路由的作用是让用户确认账户或执行其他账户管理操作。

Fix bug:

```python
# from itsdangerous import TimedJSONWebSignatureSerializer as Serializer
from itsdangerous.url_safe import URLSafeTimedSerializer as Serializer
```

## ch09 用户权限

```python
class Role(db.Model):
    __tablename__ = 'roles'
    # ...
    default = db.Column(db.Boolean, default=False, index=True)
```

这个模型新增了 default 字段。只能有一个角色的这个字段可以设为 True，其他角色都应
该设为 False。默认角色是注册新用户时赋予用户的角色。因为应用将在 roles 表中搜索
默认角色，所以我们为这一列设置了索引，提升搜索的速度。

> 这个 default 字段改成 is_default_role 更好，或者压根不需要这个字段，从 permissions 字段衍生计算不好吗？

```python
default_role = 'User'
role.default = (role.name == default_role)
```

表 9-1：应用中的各项权限

| 操作          | 权限名      | 权限值 |
|-------------|----------|-----|
| 关注用户        | FOLLOW   | 1   |
| 在他人的文章中发表评论 | COMMENT  | 2   |
| 写文章         | WRITE    | 4   |
| 管理他人发表的评论   | MODERATE | 8   |
| 管理员权限       | ADMIN    | 16  |

使用 2 的幂表示权限值有个好处：每种不同的权限组合对应的值都是唯一的，方便存入角
色的 permissions 字段。例如，若想为一个用户角色赋予权限，使其能够关注其他用户，
并在文章中发表评论，则权限值为 FOLLOW + COMMENT = 3。通过这种方式存储各个角色的
权限特别高效。

---

表 9-2 列出了这个应用会支持的用户角色，以及定义各个角色的权限组合。

表 9-2：用户角色

| 用户角色 | 权 限                                 | 说 明                              |
|------|-------------------------------------|----------------------------------|
| 匿名   | 无                                   | 对应只读权限；这是未登录的未知用户                |
| 用户   | FOLLOW、COMMENT、WRITE                | 具有发布文章、发表评论和关注其他用户的权限；这是新用户的默认角色 |
| 协管员  | FOLLOW、COMMENT、WRITE、MODERATE       | 增加管理其他用户所发表评论的权限                 |
| 管理员  | FOLLOW、COMMENT、WRITE、MODERATE、ADMIN | 具有所有权限，包括修改其他用户所属角色的权限           |

---

```python
@main.route('/moderate')
@login_required
@permission_required(Permission.MODERATE)
def for_moderators_only():
    return "For comment moderators!"
```

注意修饰函数的执行顺序，从上往下来解析执行。

## ch10 用户资料

编辑用户资料的某个字段

```python
def validate_username(self, field):
    # 例如 A 想改为 B，而且 B 用户名已经被其他人占用
    # 仅当有变化时，才要保证新值不与其他用户的相应字段值重复；
    if field.data != self.user.username and
        User.query.filter_by(username=field.data).first():
    raise ValidationError('Username already in use.')

    # 如果字段值没有变化，那么应该跳过验证
    # 例如 A 想改为 C，而且 C 用户名没有被占用，则验证通过
```

梳理一下编辑 profile 的 links:

```html
    {% if user == current_user %}
<a class="btn btn-default" href="{{url_for('.edit_profile') }}">Edit Profile</a>
{% endif %}
{% if current_user.is_administrator() %}
<a class="btn btn-danger" href="{{url_for('.edit_profile_admin', id=user.id) }}">Edit Profile [Admin]</a>
{% endif %}
```

- 如果当前是游客状态 => 仅仅预览
- 如果当前登录用户 id 等于 url 的 user_id => 显示编辑 profile
- 如果当前登录用户 id 等于 url 的 user_id ，而且当且用户是系统管理员 => 显示高级权限的编辑 profile

---

Gravatar 是一个行业领先的头像服务，能把头像和电子邮件地址关联起来。
你在浏览器的地址栏中输入 https://secure.gravatar.com/avatar/d4c74594d8411393286-95756648b6bd6 后，将看到电子邮件地址
john@example.com 对应的头像。如果这个电子邮件地址没有关联头像，则会显示一个默认图像。

使用这个来实现 Gravatar 的显示。

## ch11 博客文章

定义 model -> 实现对应的 Form -> 实现对应的 views 函数。

生产虚假的 post 列表数据，方便开发测试。使用 faker 第三方库。

```python
pagination = Post.query.order_by(Post.timestamp.desc()).paginate(
    page=page, per_page=current_app.config['FLASKY_POSTS_PER_PAGE'],
    error_out=False)
```

> 另一个可选参数为 error_out，如果设为 True（默认值），则请求页数超出范围时返回
> 404 错误；如果设为 False，则页数超出范围时返回一个空列表。

---

表 11-1：Flask-SQLAlchemy 分页对象的属性

| 属 性      | 说 明            |
|----------|----------------|
| items    | 当前页面中的记录       |
| query    | 分页的源查询         |
| page     | 当前页数           |
| prev_num | 上一页的页数         |
| next_num | 下一页的页数         |
| has_next | 如果有下一页，值为 True |
| has_prev | 如果有上一页，值为 True |
| pages    | 查询得到的总页数       |
| per_page | 每页显示的记录数量      |
| total    | 查询返回的记录总数      |

表 11-2：Flask-SQLAlchemy 分页对象的方法

| 方 法                                                                    | 说 明                                                                                                                                                                                                                           |
|------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| iter_pages(left_edge=2, left_current=2, right_current=5, right_edge=2) | 一个迭代器，返回一个在分页导航中显示的页数列表。这个列表的最左边显示 left_edge 页，当前页的左边显示 left_current 页，当前页的右边显示 right_current 页，最右边显示 right_edge 页。例如，在一个 100 页的列表中，当前页为第 50 页，使用默认配置，这个方法会返回以下页数：1、2、None、48、49、50、51、52、53、54、55、None、99、100。None 表示页数之间的间隔 |
| prev()                                                                 | 上一页的分页对象                                                                                                                                                                                                                      |
| next()                                                                 | 下一页的分页对象                                                                                                                                                                                                                      |

---

支持 Markdown 格式编辑。 实现这个功能要用到一些新包。

- PageDown：使用 JavaScript 实现的客户端 Markdown 到 HTML 转换程序。
- Flask-PageDown：为 Flask 包装的 PageDown，把 PageDown 集成到 Flask-WTF 表单中。
- Markdown：使用 Python 实现的服务器端 Markdown 到 HTML 转换程序。
- Bleach：使用 Python 实现的 HTML 清理程序。

---

```python
db.event.listen(Post.body, 'set', Post.on_changed_body)
```

数据库监听字段变更，调用监听函数。

## ch12 关注者

> 多对多关系仍使用定义一对多关系的 db.relationship() 方法定义，但在多对多关系中，必
> 须把 secondary 参数设为关联表。多对多关系可以在任何一个类中定义，backref 参数会处理
> 好关系的另一侧。关联表就是一个简单的表，不是模型，SQLAlchemy 会自动接管这个表。

分析梳理 follower 和 followed 的关系。

```
网站        粉丝 / 关注者   关注中
github     followers    following
bilibili   fans         follow
flasky     followers    followed
```

```python
class User(UserMixin, db.Model):
    __tablename__ = 'users'
    # ...
    followed = db.relationship('Follow',
                               foreign_keys=[Follow.follower_id],
                               backref=db.backref('follower', lazy='joined'),
                               lazy='dynamic',
                               cascade='all, delete-orphan')
    followers = db.relationship('Follow',
                                foreign_keys=[Follow.followed_id],
                                backref=db.backref('followed', lazy='joined'),
                                lazy='dynamic',
                                cascade='all, delete-orphan')
```

理解：
假设对于 A 来说，要寻找 A 关注了那些人。

```
follower_id followed_id
A            ?
A            ?
A            ?
A            ?
```

在关联表中，外键是 `foreign_keys=[Follow.follower_id]`。得到的是 followed，表示 A 的关注者列表。

---

假设对于 B 来说，要寻找 B 有哪些粉丝。

```
follower_id followed_id
      ？         B
      ？         B
      ？         B
```

在关联表中，外键是 `foreign_keys=[Follow.followed_id]`。得到的是 followers 字段，表示 B 的粉丝列表。

如果某个用户关注了 100 个用户，调用 user.followed.all() 后会返回一个列表，
其中包含 100 个 Follow 实例，每一个实例的 follower 和 followed 回引属性都指向相应的用户。

> 删除对象时，默认的层叠行为是把对象连接的所有相关对象的外键设为空值。但在关联表中，删除记录后正确
> 的行为应该是把指向该记录的实体也删除，这样才能有效销毁连接。这就是层叠选项值
> delete-orphan 的作用。 设为 all, delete-orphan 的意思是启用所有默认层叠选项，而且还要删除孤儿记录。

用户自关注自己，可以查看自己的 posts。

## ch13 评论

/

## ch14 应用编程接口

14.2.3 使用 Flask-HTTPAuth 验证用户身份

14.2.4 基于令牌的身份验证

每次请求，客户端都要发送身份验证凭据。为了避免总是发送敏感信息（例如密码），我们可以使用一种基于令牌的身份验证方案。

```python
@api.route('/tokens/', methods=['POST'])
def get_token():
    if g.current_user.is_anonymous or g.token_used:
        return unauthorized('Invalid credentials')
    return jsonify({'token': g.current_user.generate_auth_token(
        expiration=3600), 'expiration': 3600})
```

为了确保这个路由使用电子邮件地址和密码验证身份，而不使用之前获取的令牌，我们检查了 g.token_used 的值，拒绝使用令牌验证身份。

表 14-3：Flasky 应用的 API 资源

> 14.2.8 使用 HTTPie 测试 Web 服务

GET

```
(venv) $ http --json --auth <email>:<password> GET \
> http://127.0.0.1:5000/api/v1/posts
```

POST

```
(venv) $ http --auth <email>:<password> --json POST \
> http://127.0.0.1:5000/api/v1/posts/ \
> "body=I'm adding a post from the *command line*."
```

如果不想使用用户名和密码验证身份，而是使用令牌，要先向 /api/v1/tokens/ 发送 POST 请求：

```
(venv) $ http --auth <email>:<password> --json POST \
> http://127.0.0.1:5000/api/v1/tokens/
```

然后

```
(venv) $ http --json --auth eyJpYXQ...: GET http://127.0.0.1:5000/api/v1/posts/
```

## ch15 测试

示例 15-3 config.py：在测试配置中禁用 CSRF 保护机制，方便测试。

```python
class TestingConfig(Config):
    # ...
    WTF_CSRF_ENABLED = False
```

> 下一步要确认账户，这里也有一个小障碍。账户确认 URL 在注册过程中通过电子邮件发
> 给用户，而在测试中无法轻松获取这个 URL。上述测试使用的解决方法忽略了注册时生成
> 的令牌，直接在 User 实例上调用方法重新生成一个新令牌。在测试环境中，Flask-Mail 会
> 保存邮件正文，所以还有一种可行的解决方法，即通过解析邮件正文来提取令牌。

思考：如何隔离发确认邮件 / 模拟发确认邮件，避免外部依赖。

---
itsdangerous 在 2.1.0+ 版本移除了对 TimedJSONWebSignatureSerializer 的支持。

Which Timed JSONWebSignature Serializer replacement for itsdangerous is better? pyjwt or authlib

> https://stackoverflow.com/questions/71292764/which-timed-jsonwebsignature-serializer-replacement-for-itsdangerous-is-better
> https://github.com/pallets/itsdangerous/blob/d1c85670cce70d81f9949619434daf8c0b9cd37e/src/itsdangerous/jws.py#L23-L30

修改后的 token 生产和验证逻辑：

```python
import jwt
import datetime


class SampleCode:
    def generate_confirmation_token(self, expiration=600):
        reset_token = jwt.encode(
            {
                "confirm": self.id,
                "exp": datetime.datetime.now(tz=datetime.timezone.utc)
                       + datetime.timedelta(seconds=expiration)
            },
            current_app.config['SECRET_KEY'],
            algorithm="HS256"
        )
        return reset_token

    def confirm(self, token):
        try:
            data = jwt.decode(
                token,
                current_app.config['SECRET_KEY'],
                leeway=datetime.timedelta(seconds=10),
                algorithms=["HS256"]
            )
        except:
            return False
        if data.get('confirm') != self.id:
            return False
        self.confirmed = True
        db.session.add(self)
        return True
```

---

15.3 使用 Selenium 进行端到端测试

主要是处理 JS 的执行。

## ch16 性能

1. Flask-SQLAlchemy 提供了一个选项，可以记录一次请求中与数据库查询有关的统计数据。=> 识别记录 slow query

2. 源码分析器

```python
@app.cli.command()
@click.option('--length', default=25,
              help='Number of functions to include in the profiler report.')
@click.option('--profile-dir', default=None,
              help='Directory where profiler data files are saved.')
def profile(length, profile_dir):
    """Start the application under the code profiler."""
    from werkzeug.contrib.profiler import ProfilerMiddleware
    app.wsgi_app = ProfilerMiddleware(app.wsgi_app, restrictions=[length],
                                      profile_dir=profile_dir)
    app.run(debug=False)
```

Python 分析器的详细信息请参阅官方文档（https://docs.python.org/2/library/profile.html ）。

## ch17 deployment

1. 建立方便的 App 升级函数入口，同时保证幂等，无副作用。
2. 把生产环境中的错误写入日志。=> 应用出错时发送电子邮件

```bash
exec gunicorn -b :5000 --access-logfile - --error-logfile - flasky:app
```

这是一段命令行代码，用于启动一个名为 "flasky" 的应用程序，并使用 Gunicorn 作为 Web 服务器。
具体来说，这个命令使用 "exec gunicorn" 来启动 Gunicorn，"-b :5000" 表示绑定到本地的 5000 端口，
"--access-logfile -" 表示将访问日志输出到标准输出，"--error-logfile -" 表示将错误日志输出到标准输出，
"flasky:app" 表示要运行的应用程序是 "flasky" 模块中的 "app" 对象。

---

docker 使用外部数据库

使用 Docker 容器部署 Flasky 有个缺点：应用默认使用的 SQLite 数据库在容器内非常难升级，因为容器一旦停止运行，数据库就不见了。
更好的方案是在应用的容器之外托管数据库服务器。这样升级应用时只需换个新容器，数据库就能轻松地保留下来。

```bash
$ docker run --name mysql -d -e MYSQL_RANDOM_ROOT_PASSWORD=yes \
-e MYSQL_DATABASE=flasky -e MYSQL_USER=flasky \
-e MYSQL_PASSWORD=<database-password> \
mysql/mysql-server:5.7
```

为了连接 MySQL 数据库，SQLAlchemy 要求安装一个被它支持的 MySQL 客户端包，例如
pymysql。你可以把这个包添加到 docker.txt 需求文件中。

```bash
$ docker run -d -p 8000:5000 --link mysql:dbserver \
-e DATABASE_URL=mysql+pymysql://flasky:<database-password>@dbserver/flasky \
-e MAIL_USERNAME=<your-gmail-username> -e MAIL_PASSWORD=<your-gmail-password> \
flasky:latest
```

- --link 选项的值是以冒号分隔的两个名称，一个是目标容器的名称或 ID，另一个是在当前容器中访问目标容器所用的别名。
- `mysql+pymysql://flasky:<database-password>@dbserver/flasky` 中 mysql+pymysql:
  // 是驱动协议，第一个 flasky 是用户名，dbserver 是当前容器内的数据库主机名，
  第二个 flasky 是数据库名。

---

```bash
docker-compose up -d --build
docker-compose logs
docker-compose logs -f
docker-compose ps

docker-compose down 
docker-compose rm --stop --force
```

有些容器会在宿主计算机中创建虚拟卷（volume），作为容器文件系统之外的存储空间。
例如，MySQL 容器映像把所有数据库文件都放在一个卷中。可以使用 docker volume ls
命令查看系统分配的全部卷。若想删除某个不再使用的卷，使用 docker volume rm 。

docker system prune --volumes 命令会删除所有不再使用的映像或卷，以及停止后依然在系统中的容器。

---
在生产环境中使用Docker，需要的考虑的点：

- 监控和提醒
- 日志记录
- 机密信息管理
- 伸缩性

通过构建在 Docker 基础上的更精巧的编排框架或其他容器运行时来克服。
Docker Swarm（现已并入 Docker）、Apache Mesos 和 Kubernetes（最后的赢家） 等框架有助于构建稳健的容器部署方案。

---

传统部署方式

17.6.1 架设服务器
在能够托管应用之前，在服务器上必须完成多项管理任务。

- 安装数据库服务器，例如 MySQL 或 Postgres。也可使用 SQLite 数据库，但由于它在修
  改现有的数据库模式方面有种种限制，不建议在生产服务器中使用。
- 安装邮件传输代理（mail transport agent，MTA），例如 Sendmail 或 Postfix，用于向用
  户发送邮件。不要妄图在线上应用中使用 Gmail，因为这个服务的配额少得可怜，而且
  服务条款明确禁止商用。
- 安装适用于生产环境的 Web 服务器，例如 Gunicorn 或 uWSGI。
- 安装一个进程监控工具，例如 Supervisor，在服务器崩溃或恢复电力后立即重启。
- 为了启用安全的 HTTP，安装并配置 SSL 证书。
- （可选，但强烈推荐）安装前端反向代理服务器，例如 nginx 或 Apache。反向代理服务
  器能直接服务于静态文件，并把其他请求转发给应用的 Web 服务器。Web 服务器监听
  localhost 中的一个私有端口。
- 提升服务器的安全性。这一过程包含多项任务，目标在于降低服务器被攻击的概率，例
  如安装防火墙、删除不用的软件和服务，等等。

不要手动执行这些任务。可以使用自动化框架（例如 Ansible、Chef 或 Puppet）编写一个部署脚本。

Unix log：应用的日志将写入配置的 syslog 消息文件，通常是 /var/log/messages 或 /var/log/syslog 。

## ch18 其他

Flask 扩展库：
- Flask-Babel：提供国际化和本地化支持。
- Marshmallow：序列化和反序列化 Python 对象，可在 API 中提供资源的不同表述。
- Celery：处理后台作业的任务队列。
- Frozen-Flask：把 Flask 应用转换成静态网站。
- Flask-DebugToolbar：在浏览器中使用的调试工具。
- Flask-Assets：用于合并、压缩及编译 CSS 和 JavaScript 静态资源文件。
- Flask-Session：使用服务器端存储实现的用户会话。
- Flask-SocketIO：实现 Socket.IO 服务器，支持 WebSocket 和长轮询。


