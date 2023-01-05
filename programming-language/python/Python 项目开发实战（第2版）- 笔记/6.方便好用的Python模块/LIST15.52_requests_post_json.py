# coding: utf-8
import pprint
import json
import requests


def main():
    # 指定json.dumps生成的字符串之后,可以直接发送数据而不进行URL编码
    # 需要明确指定Content-Tpye
    response = requests.post(
        'http://127.0.0.1:5000/post',
        json.dumps({'foo': 'bar'}),
        headers={'Content-Type': 'application/json'})
    pprint.pprint(response.json())

if __name__ == '__main__':
    main()
