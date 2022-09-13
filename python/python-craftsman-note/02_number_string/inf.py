users = {"tom": 19, "jenny": 13, "jack": None, "andrew": 43}

def sort_users_inf(users):
    def key_func(username):
        age = users[username]
        # 当年龄为空时，返回正无穷大做为 key，因此就会被排到最后面
        return age if age is not None else float('inf')

    return sorted(users.keys(), key=key_func)

print(sort_users_inf(users))
