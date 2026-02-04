# GitHub OAuth 第三方登录示例教程 (Python 版)

## 准备
创建.env文件，填写
```
ClientID=?
ClientSecret=?
```

## 代码

项目依赖 requirements.txt，记得安装。
```
requests
flask
environs
mo-cache
```

github_api.py
```python
import requests


def get_github_auth_url(client_id, redirect_uri):
    """

    :param client_id: 告诉 GitHub 谁在请求
    :param redirect_uri: 跳转回来的网址
    :return:
    """
    authorize_uri = 'https://github.com/login/oauth/authorize'

    return f'{authorize_uri}?client_id={client_id}&redirect_uri={redirect_uri}'


def get_access_token(client_id, client_secret, code):
    """获取 access_token 此操作在后台完成"""
    url = 'https://github.com/login/oauth/access_token'
    params = {
        'client_id': client_id,
        'client_secret': client_secret,
        'code': code
    }
    headers = {
        'accept': 'application/json'
    }
    res = requests.post(url=url, params=params, headers=headers)
    return res.json()


def fetch_user(access_token):
    """获取用户信息"""
    url = 'https://api.github.com/user'
    headers = {
        'Authorization': f'Bearer {access_token}'
    }
    res = requests.get(url=url, headers=headers)
    return res.json()
```

run.py
```python
from urllib.parse import urljoin

from flask import Flask, request, url_for

from github_api import get_github_auth_url, fetch_user, get_access_token
from mo_cache import FileCache
from environs import Env

env = Env()
env.read_env()

clientID = env.str('ClientID')
clientSecret = env.str('ClientSecret')

cache = FileCache()

ACCESS_TOKEN_KEY = 'access_token_key'

app = Flask(__name__)


def full_url_for(endpoint, **values):
    return urljoin(request.host_url, url_for(endpoint, **values))


@app.route('/')
def hello_world():
    return {
        'auth_url': full_url_for('get_auth_url'),
        'get_user': full_url_for('get_user')
    }


@app.route('/auth_url')
def get_auth_url():
    """获取由后端拼接的Github第三方登录授权地址"""
    redirect_uri = full_url_for('oauth_redirect')
    auth_url = get_github_auth_url(client_id=clientID, redirect_uri=redirect_uri)
    return {'auth_url': auth_url}


@app.route('/oauth/redirect')
def oauth_redirect():
    """github验证回调地址，从请求参数中获取code"""
    code = request.args.get('code')

    # 拿到code后请求获取access_token
    res = get_access_token(client_id=clientID, client_secret=clientSecret, code=code)

    # 存储用户的access_token
    access_token = res.get('access_token')
    cache.set(ACCESS_TOKEN_KEY, access_token)
    return res


@app.route('/user')
def get_user():
    access_token = cache.get(ACCESS_TOKEN_KEY)
    print(access_token)
    res = fetch_user(access_token=access_token)
    return res


if __name__ == '__main__':
    print(app.url_map)
    app.run(port=8080, debug=True)
```

## 流程

1. A 网站让用户点击某个链接跳转到 GitHub。
   `https://github.com/login/oauth/authorize?client_id={your_client_id}&redirect_uri=http://localhost:8080/oauth/redirect`
   这个链接中，包含了客户端必要信息，以及重定向回 A 网站的 uri。

2. GitHub 要求用户登录，然后询问"A 网站要求获得 xx 权限，你是否同意？"同意后就会发生下面步骤：

- GitHub 发送授权码：GitHub 的服务器生成一个短期有效的授权码（code），然后指示浏览器跳转到 redirect_uri
  指定的地址，即 `http://localhost:8080/oauth/redirect?code=xxx` 。

- 关键的一步——浏览器处理重定向：浏览器收到来自 GitHub 的“重定向到 localhost:8080/... ”这个指令。因为它理解 localhost
  指向本机，于是它向本机的 8080 端口发起一个新的 HTTP 请求，并把包含 code 的 URL 传递过去。

3. A 网站后端特定 API 处理。

- A 网站后端会设置一个接收 `/oauth/redirect` 的 API，在这个 API 中获取授权码 code。
- A 网站使用授权码 code，向 GitHub 请求访问令牌 access_token，并妥善保存。
- A 网站使用访问令牌 access_token，向 GitHub 请求用户数据。

## demo验证

- 如果你 github app 中配置了 localhost 前缀，那么访问 http://localhost:8080 而不是 http://127.0.0.1:8080
- 点击 http://localhost:8080/auth_url
  获得 https://github.com/login/oauth/authorize?client_id=d9d39cdf5d7fe3379049&redirect_uri=http://localhost:8080/oauth/redirect
- 再次点击上面链接后，获得 "access_token": "xxxxxxxxxxx", 此时说明已成功获得访问令牌。在此应用中，该访问令牌已经被加密保存到本地文件中。
- 现在，访问 http://localhost:8080/user 即可获取 GitHub 用户数据。