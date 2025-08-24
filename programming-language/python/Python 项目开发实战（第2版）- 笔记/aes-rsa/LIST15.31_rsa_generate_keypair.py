# coding: utf-8
from Crypto.PublicKey import RSA
from Crypto import Random

INPUT_SIZE = 1024


def main():
    random_func = Random.new().read  # 产生随机数的函数
    key_pair = RSA.generate(INPUT_SIZE, random_func)  # 生成密钥对
    private_pem = key_pair.exportKey()  # 获取PEM格式的私钥
    public_pem = key_pair.publickey().exportKey()  # 获取PEM格式的公钥
    print private_pem  # 输出至屏幕
    print public_pem  # 输出至屏幕


if __name__ == '__main__':
    main()
