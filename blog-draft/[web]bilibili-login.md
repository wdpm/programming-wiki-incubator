# 登录 b 站

登录方式

- 手机短信登录 (需要人机验证)
- 账号密码登录 (需要人机验证)
- 扫二维码登录 (不需要人机验证)

## 人机验证流程

1. 请求验证码参数，得到登录密钥 key 与极验 id `gt` 和极验 KEY `challenge`
2. 进行滑动 or 点击验证
3. 返回验证结果 `validate` 与 `seccode` ，进行短信或密码登录

GET https://passport.bilibili.com/x/passport-login/captcha?source=main_web

```json
{
  "code": 0,
  "message": "0",
  "ttl": 1,
  "data": {
    "type": "geetest",
    "token": "15083ede9acd4d82ae0d6d13ea65d66a",
    "geetest": {
      "challenge": "46d6ad95ffb8b3102a76b1b472f8e070",
      "gt": "ac597a4506fee079629df5d8b66dd4fe"
    },
    "tencent": {
      "appid": ""
    }
  }
}
```

有关实验场，参阅 [手动验证器](https://kuresaru.github.io/geetest-validator/)
及其 [源码](https://github.com/kuresaru/geetest-validator)。

## 手机短信登录 (需要人机验证)

1. GET https://passport.bilibili.com/x/passport-login/captcha?source=main_web ,从响应中得到token、challenge、gt并记录。
2. GET https://passport.bilibili.com/web/generic/country/list => 获取国际冠字码，例如中国大陆为1。
3. 从 [手动验证器](https://kuresaru.github.io/geetest-validator/) 进行验证，获取 validate 和 seccode
4. POST https://passport.bilibili.com/x/passport-login/web/sms/send 并设置七个 key-value params，得到
   `86103 请下载最新版本哔哩哔哩App完成注册或继续登录` 的错误。

经过搜索， 根据 https://github.com/SocialSisterYi/bilibili-API-collect/issues/756 链接，得知原因在于还需要补充足够的headers信息。
此外，[这个issue](https://github.com/CuteReimu/bilibili/issues/47) 也表示了手机验证码登录困难重重。

因此，这里暂时放弃对此方式的进一步研究和探索。

## 扫二维码登录 (不需要人机验证)

这里有一个可用的[python demo实现](https://github.com/husky-666/bilibili_qrcode_login)。

说明：
- `show_img.get_cell()`: 
  - 按行扫描，找到某一行中第一个黑到白的过渡，记录为 x1（黑开始）和 x2（白结束）
  - 按列扫描，找到某一列中第一个黑到白的过渡，记录为 y1（黑开始）和 y2（白结束）
  - (x2-x1,y2-y1) 就是定位图案的坐标范围。
- `show_img.get_cell_size()`: 根据定位图案，获取二维码组成模块的像素尺寸。例子中 10 x 10 pixels 为一个单位模块。
- `show_img.print_qrcode()`: 
  - 一次扫描两行，根据上像素和下像素的黑白颜色分别替换为ascii文本字符。
  - 代码可运行，逻辑正确。但存在较大的重构优化空间。
- qrcode 库用于简化对二维码生成的处理，Pillow 用于对图像的像素级别的处理。

cookie.json 示例格式：
```json
{
  "SESSDATA": "XXXXX",
  "bili_jct": "XXX",
  "DedeUserID": "XXX",
  "DedeUserID__ckMd5": "XXX",
  "sid": "XXX"
}
```

这个方式目前是最为稳定的，推荐此方式接入b站。

## 账号密码登录 (需要人机验证)

```python
import requests

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    'Accept-Language': 'en-US,en;q=0.5',
    'Accept-Encoding': 'gzip, deflate, br',
    'Connection': 'keep-alive'
}

# 第一步，请求公钥和加密盐
res = requests.get('https://passport.bilibili.com/x/passport-login/web/key', headers=headers)
data = res.json()['data']
print('salt =', data['hash'])
print('PubKey =', data['key'])

import rsa

# 第二步，对明文密码进行一番本地处理，从而避免在网络上传输明文密码
password = 'XXXXXXXX'
pubKey = rsa.PublicKey.load_pkcs1_openssl_pem(data['key'])  # 读取 PEM 密钥
encryptedPassword = rsa.encrypt((data['hash'] + password).encode(), pubKey)  # 盐需要加在明文密码之前，一并加密
print(encryptedPassword)

import base64

b64Password = base64.b64encode(encryptedPassword).decode()
print('b64Password =', b64Password)

# 第三步，需要结合人机验证来登录。
payload = {
    'username': 'XXXXXXXXXX@XX.com',
    'password': b64Password,
    'keep': 0,
    'token': 'a2eef7b0ad1145a7b53fc94a01ceafa8',
    'challenge': '8f17c13195295bc1a6fa912c729c7829',
    'validate': '660d88e3414741b473c5fdf76428e6d4',
    'seccode': '660d88e3414741b473c5fdf76428e6d4|jordan'
}
resp = requests.post('https://passport.bilibili.com/x/passport-login/web/login', data=payload)
print(resp)
```

这个方式我在本地经过多次实验，没有成功。单是第一步就多次进入风控，总之比较繁琐。也不推荐这个方式。