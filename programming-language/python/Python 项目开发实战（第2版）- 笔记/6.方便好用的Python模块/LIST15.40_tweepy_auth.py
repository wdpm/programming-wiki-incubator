# coding: utf-8
from flask import Flask, render_template, session, request, redirect
from tweepy import OAuthHandler, API

application = Flask(__name__)
# 设置用于会话的密钥
application.secret_key = 'my secret key'

# 用户密钥
CONSUMER_KEY = 'Consumer key'
CONSUMER_SECRET = 'Consumer secret'

def get_access_token():
    """从会话获取访问令牌的函数
    """
    return session.get('access_token')

def set_access_token(access_token):
    """在会话中保存访问令牌的函数
    """
    session['access_token'] = access_token

def get_request_token(key):
    """从会话获取请求令牌的函数
    """
    return session.get(key)

def set_request_token(key, token):
    """在会话中保存请求令牌的函数
    """
    session[key] = token

def get_oauth_handler():
    """返回Tweepy的OAuth handler的函数
    """
    return OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)

def get_api(access_token):
    """指定访问令牌返回API实例的函数
    """
    handler = get_oauth_handler()
    handler.set_access_token(access_token[0], access_token[1])
    api = API(handler)
    return api

def set_user(user):
    """在会话中保存用户信息的函数
    """
    # 以可JSON序列化的字典对象格式保存在会话中
    session['user'] = {'screen_name': user.screen_name}

def get_user():
    """从会话获取用户信息的函数
    """
    return session.get('user')

def is_login():
    """返回是否为登录状态
    """
    return not not get_access_token()

def clear_session():
    """删除会话的值
    """
    session.clear()

@application.route('/')
def index():
    """首页
    使用模板显示页面
    """
    # 根据模板显示页面
    return render_template('index.html', is_login=is_login(), user=get_user())

@application.route('/login')
def login():
    """登录URL
    开始Twitter认证
    """
    handler = get_oauth_handler()
    # 获取认证URL
    auth_url = handler.get_authorization_url()
    # 请求令牌保存在会话中
    set_request_token(
        handler.request_token['oauth_token'],
        handler.request_token['oauth_token_secret'])
    # 重定向到认证URL
    return redirect(auth_url)

@application.route('/callback')
def callback():
    """回调URL
    Twitter认证后的回调URL
    """
    # 用GET方法获取OAuth的回调属性
    oauth_token = request.args.get('oauth_token')
    oauth_verifier = request.args.get('oauth_verifier')
    # 取消时重定向到首页
    if not oauth_token and not oauth_verifier:
        return redirect('/')
    # 获取OAuth的handler
    handler = get_oauth_handler()
    # 从会话获取请求令牌并设置给handler
    request_token = get_request_token(oauth_token)
    handler.request_token = {
        'oauth_token': oauth_token,
        'oauth_token_secret': request_token,
    }
    # 获取访问令牌
    access_token = handler.get_access_token(oauth_verifier)
    # 将访问令牌保存在会话中
    set_access_token(access_token)
    # 获取API实例
    api = get_api(access_token)
    # 获取登录用户的信息并保存至会话
    set_user(api.me())
    # 重定向到首页
    return redirect('/')

@application.route('/logout')
def logout():
    """登出URL
    从会话中删除登录状态的信息
    """
    # 删除会话中的登录信息
    clear_session()
    # 重定向到首页
    return redirect('/')

@application.route('/timeline')
def timeline():
    """显示时间轴信息的页面
    需要认证
    """
    # 获取登录状态
    if not is_login():
        # 没有登录则重定向到首页
        return redirect('/')
    # 从会话获取访问令牌
    access_token = get_access_token()
    if not access_token:
        # 无法获取访问令牌时
        # 清除会话并重新开始
        clear_session()
        return redirect('/')
    # 获取API实例
    api = get_api(access_token)
    # 调用Twitter API获取时间轴信息
    statuses = api.home_timeline()
    # 通过模板显示页面
    return render_template('timeline.html', statuses=statuses)

if __name__ == '__main__':
    # 通过IP地址127.0.0.1的5000端口执行应用
    application.run('127.0.0.1', 5000, debug=True)
