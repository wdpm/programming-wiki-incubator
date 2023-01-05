# coding: utf-8
import pprint
import requests


def main():
    # POST参数以字典形式通过第二个传值参数指定
    response = requests.post(
        'http://127.0.0.1:5000/post',
        {'foo': 'bar'})
    # 使用响应对象的json方法可以获取转换为Python字典对象的JSON数据
    pprint.pprint(response.json())

if __name__ == '__main__':
    main()
