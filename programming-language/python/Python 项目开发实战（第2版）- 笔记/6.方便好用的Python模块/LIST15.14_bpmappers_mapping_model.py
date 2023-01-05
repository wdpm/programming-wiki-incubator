# coding: utf-8
import json
from bpmappers import Mapper, RawField, DelegateField

class User(object):
    "省略"

class Comment(object):
    "省略"

def get_user(user_id):
    "省略"

def get_comment(comment_id):
    "省略"

class UserMapper(Mapper):
    """User模型与API的映射
    """
    user_id = RawField('id')
    user_nickname = RawField('nickname')

class UserMapper2(UserMapper):
    """User模型与API的映射2
    """
    user_age = RawField('age')

class CommentMapper(Mapper):
    """Comment模型与API的映射
    """
    user = DelegateField(UserMapper)
    text = RawField()

def api_user_json(user_id):
    """以JSON格式返回用户数据的API
    """
    user = get_user(user_id)  # 获取User对象
    user_dict = UserMapper(user).as_dict()  # 映射到字典
    return json.dumps(user_dict, indent=2)  # 以JSON格式返回

def api_user_detail_json(user_id):
    """以JSON格式返回用户详细数据的API
    """
    user = get_user(user_id)  # 获取User对象
    user_dict = UserMapper2(user).as_dict()  # 映射到字典
    return json.dumps(user_dict, indent=2)  # 以JSON格式返回

def api_comment_json(comment_id):
    """以JSON格式返回留言数据的API
    """
    comment = get_comment(comment_id)  # 获取Comment对象
    comment_dict = CommentMapper(comment).as_dict()  # 映射到字典
    return json.dumps(comment_dict, indent=2)  # 以JSON格式返回

def main():
    "省略"

if __name__ == '__main__':
    main()
