import time
from concurrent.futures import ThreadPoolExecutor

import requests
import logging

logger = logging.getLogger(__name__)


def get_status_code(url: str) -> int:
    try:
        response = requests.get(url)
        logger.info('no log')
        return response.status_code
    except:
        return -1


def using_thread_pool():
    start = time.time()
    # 注意这里的workers参数，默认 max_workers = min(32, (os.cpu_count() or 1) + 4)。因此性能受限。
    # 上面的例子中，是8+4=12个线程。

    # 如果激进地扩大max_workers的数量，那么性能会得到显著的提升吗？例如1000
    # 答案是不会。因为开辟thread也是需要资源和时间开销的，这就是调度成本。
    # 如果调度成本的效益越来越低，那么此时再想依赖多线程来提升性能，几乎是不可能的。
    # 从资源成本来看，线程创建和调度依旧昂贵(上下文切换thread状态)。线程的开销负担吃掉了部分多线程带来的性能提升。
    # 因此此时协程就很香了。它比线程更加轻量。
    with ThreadPoolExecutor(max_workers=1000) as pool:
        urls = ['https://www.example.com' for _ in range(1000)]
        results = pool.map(get_status_code, urls)
        for result in results:
            print(result)
    end = time.time()
    print(f'finished requests in {end - start:.4f} second(s)')


def sync_requests_baseline():
    start = time.time()
    urls = ['https://www.example.com' for _ in range(1000)]
    for url in urls:
        print(get_status_code(url))
    end = time.time()
    print(f'finished requests in {end - start:.4f} second(s)')


# sync_requests_baseline()
# finished requests in 1507.0989 second(s)
# 200 => 987; -1 => 13

using_thread_pool()
# finished requests in 198.3491 second(s)
# 200 => 996; -1 => 4
