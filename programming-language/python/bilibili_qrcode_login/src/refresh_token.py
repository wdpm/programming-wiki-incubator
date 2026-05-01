# code credit: https://github.com/SocialSisterYi/bilibili-API-collect/issues/524#issuecomment-1678537962

import binascii
import re
import time

import requests
from Crypto.Cipher import PKCS1_OAEP
from Crypto.Hash import SHA256
from Crypto.PublicKey import RSA

import utils

cookie_dict = utils.load_json_file(path="../cookie.json")

key = RSA.importKey('''\
-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLgd2OAkcGVtoE3ThUREbio0Eg
Uc/prcajMKXvkCKFCWhJYJcLkcM2DKKcSeFpD/j6Boy538YXnR6VhcuUJOhH2x71
nzPjfdTcqMz7djHum0qSZA0AyCBDABUqCrfNgCiJ00Ra7GmRj+YCK1NJEuewlb40
JNrRuoEUXpabUzGB8QIDAQAB
-----END PUBLIC KEY-----''')


def getCorrespondPath(key) -> str:
    """
    将 refresh_${timestamp} 作为消息体（参数 timestamp 为当前毫秒时间戳），用 PubKey 进行 RSA-OAEP 算法加密，
    之后密文通过小写 Base16 编码为字符串。
    :param key: PEM key
    :return:

    see https://socialsisteryi.github.io/bilibili-API-collect/docs/login/cookie_refresh.html#%E7%AE%97%E6%B3%95%E7%BB%86%E8%8A%82
    """
    ts = round(time.time() * 1000)
    cipher = PKCS1_OAEP.new(key, SHA256)
    encrypted = cipher.encrypt(f'refresh_{ts}'.encode())
    return binascii.b2a_hex(encrypted).decode()


def get_refresh_csrf(correspond_path, cookie_dict) -> str:
    """

    :param correspond_path: 通过 getCorrespondPath() 获取
    :param cookie_dict: 会话数据
    :return:
    """
    response = requests.get(url=f'https://www.bilibili.com/correspond/1/{correspond_path}', cookies=cookie_dict)
    if response.status_code != 200:
        raise Exception(f'correspondPath错误或过期 {response.status_code}')
    html = response.text
    pattern = r'<div\s+id="1-name">\s*(.*?)\s*</div>'
    match = re.search(pattern, html)
    if match:
        div_contents = match.group(1)
        return div_contents
    else:
        raise Exception(f'未获取到 refresh_csrf {html}')


def get_new_cookie(refresh_token, refresh_csrf, cookie_dict):
    """

    :param refresh_token: localStorage 中的 ac_time_value 字段，在登录成功后获得
    :param refresh_csrf: 通过 get_refresh_csrf() 获得
    :param cookie_dict: 登录信息
    :return:
    """
    data = {
        'csrf': cookie_dict['bili_jct'],
        'refresh_csrf': refresh_csrf,
        'source': 'main_web',
        'refresh_token': refresh_token
    }
    response = requests.post('https://passport.bilibili.com/x/passport-login/web/cookie/refresh', data=data,
                             headers={'Content-Type': 'application/x-www-form-urlencoded'},
                             cookies=cookie_dict)
    json = response.json()
    if json['code'] == 0:
        refresh_token = json['data']['refresh_token']
        cookies_dict = {}
        for name, value in response.cookies.items():
            cookies_dict[name] = value
        return refresh_token, cookies_dict
    elif json['code'] == 86095:
        raise Exception(f'refresh_csrf 错误或 refresh_token 与 cookie 不匹配')
    else:
        raise Exception(f'刷新cookie失败 {json}')


def confirm_refresh(refresh_token_old, cookie_dict):
    data = {
        'csrf': cookie_dict['bili_jct'],
        'refresh_token': refresh_token_old
    }
    response = requests.post('https://passport.bilibili.com/x/passport-login/web/confirm/refresh', data=data,
                             headers={'Content-Type': 'application/x-www-form-urlencoded'},
                             cookies=cookie_dict)
    json = response.json()
    if json['code'] != 0:
        raise Exception(f'确认刷新cookie失败 {json}')


def check_login_user_info(cookie_dict):
    response = requests.get('https://api.bilibili.com/x/web-interface/nav', cookies=cookie_dict)
    json = response.json()
    if json['code'] != 0:
        raise Exception(f'新cookie检测异常 {json} {cookie_dict}')


# https://socialsisteryi.github.io/bilibili-API-collect/docs/login/cookie_refresh.html
# refresh_token: localStorage 中的 ac_time_value
def refresh_cookie(key, refresh_token, cookie_dict):
    correspond_path = getCorrespondPath(key)
    refresh_csrf = get_refresh_csrf(correspond_path, cookie_dict)
    refresh_token_old = refresh_token
    new_refresh_token, new_cookie_dict = get_new_cookie(refresh_token_old, refresh_csrf, cookie_dict)
    confirm_refresh(refresh_token_old, new_cookie_dict)  # 这一步需要新的 Cookie 以及旧的 refresh_token
    check_login_user_info(new_cookie_dict)
    return new_refresh_token, new_cookie_dict

if __name__ == '__main__':
    # key 是 hardcode 的PEM值

    # TODO
    # 2025年似乎在local storage没有 ac_time_value 值了，进展卡在了这里。
    # refresh_token => https://nemo2011.github.io/bilibili-api/#/get-credential

    # cookie_dict 登录获取
    refresh_cookie(key,'',cookie_dict)