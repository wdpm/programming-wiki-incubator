import requests
import utils

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    'Accept-Language': 'en-US,en;q=0.5',
    # 'Accept-Encoding': 'gzip, deflate, br', // 或者需要手动安装brotli
    'Accept-Encoding': 'gzip, deflate',
    'Connection': 'keep-alive'
}

cookie_json = utils.load_json_file(path="../cookie.json")
# 仅需 SESSDATA 即可登录
# cookies = {
#     "SESSDATA": cookie['SESSDATA']
# }
# 但是部分接口需要完整的登录cookie数据
cookies = cookie_json


def get_nav_basic_info():
    url = "https://api.bilibili.com/x/web-interface/nav"
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")


def get_nav_stat():
    url = "https://api.bilibili.com/x/web-interface/nav/stat"
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    print(f'Content-Encoding: {response.headers.get("Content-Encoding")}')
    #     {"code":0,"message":"0","ttl":1,"data":{"following":6,"follower":393,"dynamic_count":21}}

def get_coin():
    """
    认证方式：仅可Cookie（SESSDATA）

    鉴权方式：Cookie中DedeUserID存在且不为0
    :return:
    """
    url = 'https://account.bilibili.com/site/getCoin'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
#     {"code":0,"status":true,"data":{"money":1116.6}}



if __name__ == '__main__':
    get_coin()
