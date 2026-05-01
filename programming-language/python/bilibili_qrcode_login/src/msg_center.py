import requests
import utils

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    'Accept-Language': 'en-US,en;q=0.5',
    # 'Accept-Encoding': 'gzip, deflate, br', // 或者需要手动安装 brotli
    'Accept-Encoding': 'gzip, deflate',
    'Connection': 'keep-alive'
}

cookie_json = utils.load_json_file(path="../cookie.json")
cookies = cookie_json


def get_notice_stat():
    url = 'https://api.bilibili.com/x/msgfeed/unread'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "at": 0,
    #         "chat": 0,
    #         "coin": 0,
    #         "danmu": 0,
    #         "favorite": 0,
    #         "like": 0,
    #         "recv_like": 0,
    #         "recv_reply": 0,
    #         "reply": 0,
    #         "sys_msg": 0,
    #         "up": 0
    #     }
    # }


def get_single_unread():
    # 地址
    url = 'https://api.vc.bilibili.com/session_svr/v1/session_svr/single_unread'

    # 请求参数
    params = {
        'unread_type': 0,
        'show_unfollow_list': 1,
        'show_dustbin': 1,
        'build': 0,
        'mobi_app': 'web'
    }
    # 需要 headers
    # 需要 cookies
    response = requests.get(url, params=params, headers=headers, cookies=cookies)

    # http 响应代码
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")

    # 对响应数据结构建模，注意部分字段为可选的
    # {
    #     "code": 0,
    #     "msg": "0",
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "unfollow_unread": 0,
    #         "follow_unread": 0,
    #         "unfollow_push_msg": 0,
    #         "dustbin_push_msg": 0,
    #         "dustbin_unread": 0,
    #         "biz_msg_unfollow_unread": 0,
    #         "biz_msg_follow_unread": 0,
    #         "custom_unread": 0
    #     }
    # }


if __name__ == '__main__':
    get_single_unread()
