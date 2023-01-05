# coding: utf-8
from Crypto.PublicKey import RSA
from Crypto import Random

DATA = 'Hello, world!'
PUBLIC_KEY_PEM = """-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMYS234o1C1Z2fbeZazcUnEfsp
Bcs06hSmvDji+Jm5Gk6tvIHlIFFu1aCD8kBbjf2ivzmG8Dgtcn6jnLjXe3EB0H1v
h70TUsvi0ZjxZsmbv6fJmJrQzJvW1Wi3wnoBeVYQk6ha8rbfY35wErxxdTWeWm1n
SBwaFfnRFYnrkqVGlQIDAQAB
-----END PUBLIC KEY-----"""


def main():
    random_func = Random.new().read  # 产生随机数的函数
    public_key = RSA.importKey(PUBLIC_KEY_PEM)  # 输入PEM格式的公钥
    encrypted = public_key.encrypt(DATA, random_func)  # 加密数据
    print encrypted  # 输出至屏幕


if __name__ == '__main__':
    main()
