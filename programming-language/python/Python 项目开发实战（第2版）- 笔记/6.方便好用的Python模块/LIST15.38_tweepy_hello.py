# coding: utf-8
from tweepy import OAuthHandler, API

# 用户密钥（本应用的）
CONSUMER_KEY = 'Consumer key'
CONSUMER_SECRET = 'Consumer secret'
# 访问令牌（bpbook用户的）
ACCESS_TOKEN = 'Access token'
ACCESS_TOKEN_SECRET = 'Access token secret'

def main():
    # 生成OAuth的handler
    handler = OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
    # 给handler设置访问令牌
    handler.set_access_token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
    # 生成API实例
    api = API(handler)
    # 用API获取时间轴
    statuses = api.home_timeline()
    for status in statuses:
        # 将获取的状态的前15个字符输出到页面上
        print "%s: %s" % (status.user.screen_name, status.text[:15])

if __name__ == '__main__':
    main()
