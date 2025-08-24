# coding: utf-8
from Crypto.Cipher import AES

KEY = 'testtesttesttest'  # 加密和解密时使用的通用密钥
DATA = '0123456789123456'  # 数据长度为16的倍数

def main():
    aes = AES.new(KEY)  # 生成AES类的实例
    encrypt_data = aes.encrypt(DATA)  # 加密
    print repr(encrypt_data)  # 输出至屏幕
    decrypt_data = aes.decrypt(encrypt_data)  # 解密
    print repr(decrypt_data)  # 输出至屏幕

if __name__ == '__main__':
    main()
