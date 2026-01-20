import requests


def get_status_code(url: str) -> int:
    response = requests.get(url)
    return response.status_code


url = 'https://www.example.com'
print(get_status_code(url))
print(get_status_code(url))

# 请求是阻塞的，为了提升性能，常规做法是将请求封装于thread中，避免阻塞主线程。
