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


def get_account():
    '''
    :return:
    '''
    url = 'https://api.bilibili.com/x/member/web/account'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")


def get_exp_reward():
    '''
    查询每日奖励状态
    :return:
    '''
    url = 'https://api.bilibili.com/x/member/web/exp/reward'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "login": true,
    #         "watch": true,
    #         "coins": 0,
    #         "share": false,
    #         "email": false,
    #         "tel": true,
    #         "safe_question": true,
    #         "identify_card": true
    #     }
    # }


def get_weekly_coin_exp():
    '''
    查询每日投币获得经验数，上限 50
    :return:
    '''
    url = 'https://api.bilibili.com/x/web-interface/coin/today/exp'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")


def get_vip_status():
    '''
    查询大会员状态
    :return:
    '''
    url = 'https://api.bilibili.com/x/vip/web/user/info'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "mid": 51351333,
    #         "vip_type": 1,
    #         "vip_status": 0,
    #         "vip_due_date": 1683129600000,
    #         "vip_pay_type": 0,
    #         "theme_type": 0,
    #         "label": {
    #             "text_color": "",
    #             "bg_style": 1,
    #             "bg_color": "",
    #             "border_color": "",
    #             "use_img_label": true,
    #             "img_label_uri_hans": "",
    #             "img_label_uri_hant": "",
    #             "img_label_uri_hans_static": "https://i0.hdslb.com/bfs/vip/d7b702ef65a976b20ed854cbd04cb9e27341bb79.png",
    #             "img_label_uri_hant_static": "https://i0.hdslb.com/bfs/activity-plat/static/20220614/e369244d0b14644f5e1a06431e22a4d5/KJunwh19T5.png"
    #         },
    #         "avatar_subscript": 0,
    #         "avatar_subscript_url": "",
    #         "nickname_color": "",
    #         "is_new_user": false,
    #         "tip_material": null,
    #         "vip_is_annual": false,
    #         "vip_is_month": false,
    #         "is_auto_renew": false,
    #         "vip_is_valid": false,
    #         "vip_is_overdue": true,
    #         "vip_keep_time": 0,
    #         "vip_expire_days": -733
    #     }
    # }


def get_user_info():
    '''
    查询账号安全情况
    :return:
    '''
    url = 'https://passport.bilibili.com/web/site/user/info'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "account_info": {
    #             "hide_tel": "***********",
    #             "bind_tel": true,
    #             "bind_mail": false,
    #             "tel_verify": true,
    #             "mail_verify": false,
    #             "unneeded_check": false,
    #             "realname_certified": true
    #         },
    #         "account_safe": {
    #             "Score": 75,
    #             "score_new": 75,
    #             "pwd_level": 3,
    #             "security": true
    #         },
    #         "account_sns": {
    #             "weibo_bind": 0,
    #             "qq_bind": 0,
    #             "wechat_bind": 0
    #         },
    #         "account_other": {
    #             "skipVerify": false
    #         }
    #     }
    # }


def get_realname_status():
    '''
    查询账号实名状态
    :return:
    '''
    url = 'https://api.bilibili.com/x/member/realname/status'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")


def get_realname_detail():
    url = 'https://api.bilibili.com/x/member/realname/apply/status'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "status": 1,
    #         "remark": "",
    #         "realname": "***",
    #         "card": "4****************6",
    #         "card_type": 0
    #     }
    # }


def get_coin_log():
    url = 'https://api.bilibili.com/x/member/web/coin/log'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "list": [
    #             {
    #                 "time": "2025-05-05 21:10:12",
    #                 "delta": -1,
    #                 "reason": "给视频 BV1URoLY6Exo 打赏"
    #             },
    #             {
    #                 "time": "2025-05-05 02:58:42",
    #                 "delta": 1,
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "time": "2025-05-04 03:12:59",
    #                 "delta": 1,
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "time": "2025-05-03 00:03:24",
    #                 "delta": 1,
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "time": "2025-05-02 00:23:43",
    #                 "delta": 1,
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "time": "2025-05-01 03:03:42",
    #                 "delta": 1,
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "time": "2025-04-30 01:49:52",
    #                 "delta": 1,
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "time": "2025-04-29 02:21:01",
    #                 "delta": 1,
    #                 "reason": "登录奖励"
    #             }
    #         ],
    #         "count": 8
    #     }
    # }


def update_sign():
    url = 'https://api.bilibili.com/x/member/web/sign/update'
    data = {
        'user_sign': '执剑向那月，划出一弯银辉。',
        'csrf': cookie_json['bili_jct']
    }
    response = requests.post(url, data=data, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")


#     {"code":0,"message":"0","ttl":1}

def get_weekly_exp():
    url = 'https://api.bilibili.com/x/member/web/exp/log'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "list": [
    #             {
    #                 "delta": 10,
    #                 "time": "2025-05-05 21:10:13",
    #                 "reason": "视频投币奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-05 00:51:55",
    #                 "reason": "观看视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-05 00:51:55",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-04 21:55:14",
    #                 "reason": "分享视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-04 01:15:51",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-04 01:15:51",
    #                 "reason": "观看视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-03 00:00:32",
    #                 "reason": "观看视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-03 00:00:32",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-02 00:44:01",
    #                 "reason": "观看视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-02 00:44:01",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 6,
    #                 "time": "2025-05-01 03:35:29",
    #                 "reason": "3.25-4.24视频av632858730投币获得奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-01 00:28:45",
    #                 "reason": "观看视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-05-01 00:28:45",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-04-30 00:11:35",
    #                 "reason": "观看视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-04-30 00:11:35",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-04-29 00:03:22",
    #                 "reason": "观看视频奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-04-29 00:03:22",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 20,
    #                 "time": "2025-04-28 01:50:14",
    #                 "reason": "视频投币奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-04-28 01:04:36",
    #                 "reason": "登录奖励"
    #             },
    #             {
    #                 "delta": 5,
    #                 "time": "2025-04-28 01:04:36",
    #                 "reason": "观看视频奖励"
    #             }
    #         ],
    #         "count": 20
    #     }
    # }


def get_weekly_moral():
    url = 'https://api.bilibili.com/x/member/web/moral/log'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {"code": 0, "message": "0", "ttl": 1, "data": {"moral": 70, "list": [], "count": 0}}


def logout():
    url = 'https://passport.bilibili.com/login/exit/v2'
    #     请求必须包含以下 cookie 项：DedeUserID bili_jct SESSDATA
    data = {
        'biliCSRF': cookie_json['bili_jct']
    }
    response = requests.post(url, data=data, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "data": {
    #         "redirectUrl": "XXXX"
    #     },
    #     "message": "0",
    #     "status": true,
    #     "ts": 1746508450
    # }


def get_login_log():
    '''
    查询登录记录
    :return:
    '''
    url = 'https://api.bilibili.com/x/safecenter/login_notice'
    response = requests.get(url, params={'mid': '51351333'}, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {"code": -101, "message": "账号未登录", "ttl": 1}

    # {"code": -400, "message": "Key: 'LoginNoticeReq.Mid' Error:Field validation for 'Mid' failed on the 'required' tag", "ttl": 1}

    # {"code": 0, "message": "0", "ttl": 1,
    #  "data": {"mid": 51351333, "device_name": "未知设备", "login_type": "扫码登录", "login_time": "2025-05-06 13:19:55",
    #           "location": "中国广东广州", "ip": "120.239.**.***"}}


def get_weekly_login_log():
    '''
    查询最近一周的登录记录
    :return:
    '''
    url = 'https://api.bilibili.com/x/member/web/login/log'
    response = requests.get(url, headers=headers, cookies=cookies)
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
    # {
    #     "code": 0,
    #     "message": "0",
    #     "ttl": 1,
    #     "data": {
    #         "count": 15,
    #         "list": [
    #             {
    #                 "ip": "120.239.*.*",
    #                 "time": 1746505532,
    #                 "time_at": "2025-05-06 12:25:32",
    #                 "status": true,
    #                 "type": 0,
    #                 "geo": "中国广东广州移动"
    #             },
    #             {
    #                 "ip": "120.239.*.*",
    #                 "time": 1746423556,
    #                 "time_at": "2025-05-05 13:39:16",
    #                 "status": true,
    #                 "type": 0,
    #                 "geo": "中国广东广州移动"
    #             },
    #             ...
    #         ]
    #     }
    # }

def get_notice_stat():
    url ='https://api.bilibili.com/x/msgfeed/unread'
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


if __name__ == '__main__':
    get_notice_stat()
