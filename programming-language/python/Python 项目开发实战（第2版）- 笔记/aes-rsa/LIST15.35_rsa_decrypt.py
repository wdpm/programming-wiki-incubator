# coding: utf-8
from Crypto.PublicKey import RSA

DATA = ('\x05 a\\\xb9U\x88/El\x1a\x02\xe6\xb4\xede\xf2\xe6\xe3\xa6&~\x9e\x180[K%i\x02k\xdd\xd5%\xfd\x1a\xc6\xd7\xc4\xa8\xcf\x86\x07\xdck\x7f\xb4\xb5_,I\x80\xe9\x83\x00*q\xce\xacA\x9a\xe3$]\xe5*\x9e\x91F\xd2\xe3P\xb8+\xa6\xc1R\xde\xf2G\xf1\x185\xcd\x8f\x82\x1a\xa4c\xf5\x9c\xd8\xe0\xd1g \xfdw\xa0\xe6\xca\xf7\x9f\xde\xbf(\xa2\xd5\xdb\xd5}\xe5\xaf\x99\xf9\x90\x1cx\n\xe8\xda\x14\x9cJ\xd7\xe4\x96S',)
PRIVATE_KEY_PEM = """-----BEGIN RSA PRIVATE KEY-----
MIICXgIBAAKBgQDMYS234o1C1Z2fbeZazcUnEfspBcs06hSmvDji+Jm5Gk6tvIHl
IFFu1aCD8kBbjf2ivzmG8Dgtcn6jnLjXe3EB0H1vh70TUsvi0ZjxZsmbv6fJmJrQ
zJvW1Wi3wnoBeVYQk6ha8rbfY35wErxxdTWeWm1nSBwaFfnRFYnrkqVGlQIDAQAB
AoGBAKJZ39Ne6A/bWOa4inA/XQl4QyeHLrDN8bGxew7xpEtiFnX0dMrqLUX59RRb
b7xKwtxxQuVqFXYkqWyWpk6mBFGcRH1yH888Cgu+mSbsKvMAGOW/oTl7XLV8hc4T
m0iT/gEUsCHFcE6mstkUIEMlZCWmnuoijprDbehh1OSEZPQBAkEA1IFgXqMGIC/x
CYwrizFgJVAa/o4IF183CocfqPaYlotKCeNovnPXeSCmAX1d0GhCHKBIQmkmL7YU
TZ1DxiWL1QJBAPY2CWyA26GKGu1WzURJa7guizaqGJpghF30U5VdvdKmetYU2gXA
rhHQ9LxdjG09L9BWSxg5Y1Zl02b8f2Qf78ECQQCNr3VBpBCBhXWAmCSwOcuRFUfq
UWizrJhWPKGvVjuGpHhI/4bm9PXFnS8R7zSNr/XkgDmtjc4YIZ6H4UM+6enBAkBi
yC9jvxdfan9/NdJJUYPMc7AbEIeqeIri/0IBrYiZWX3zIo6OvE2ajFGEuau7sE7c
saKTZ4L5iQUWTrv1ufKBAkEAis4KsI4Inxz01ZPRcmPlUVKULvVqyquqsfKP+NFG
PTurYiXOc2kXPbBNxyhTDQ6Dw3OB0GhARHSGiuhQQicA2w==
-----END RSA PRIVATE KEY-----"""


def main():
    # 输入PEM格式的私钥
    private_key = RSA.importKey(PRIVATE_KEY_PEM)
    # 解密数据
    decrypted = private_key.decrypt(DATA)
    # 输出至屏幕
    print(decrypted)


if __name__ == '__main__':
    main()
