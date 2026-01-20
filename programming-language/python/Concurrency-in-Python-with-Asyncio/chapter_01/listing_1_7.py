import time
import requests


def read_example() -> None:
    response = requests.get('https://www.example.com')
    print(response.status_code)


sync_start = time.time()

read_example()
read_example()

sync_end = time.time()

print(f'Running synchronously took {sync_end - sync_start:.4f} seconds.')

# 200
# 200
# Running synchronously took 2.3423 seconds.