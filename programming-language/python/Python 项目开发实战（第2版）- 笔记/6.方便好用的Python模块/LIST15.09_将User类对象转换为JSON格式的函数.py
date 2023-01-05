import json

def convert_user_to_json(user):
    """获取一个User对象并返回JSON
    """
    # 生成用于转换格式的字典对象
    user_dict = {
        'user_id': user.id,  # 使用名为user_id的键
        'user_nickname': user.nickname,  # 使用名为user_nickname的键
    }
    return json.dumps(user_dict)  # 转换为JSON
