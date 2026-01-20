import time
from concurrent.futures import ProcessPoolExecutor

import logging

logger = logging.getLogger(__name__)


def count(count_to: int) -> int:
    start = time.time()
    counter = 0
    while counter < count_to:
        counter = counter + 1
    end = time.time()
    # no log in pool.map()
    logger.info(f'Finished counting to {count_to} in {end - start}')
    # print(f'Finished counting to {count_to} in {end - start}')
    return counter


if __name__ == "__main__":
    with ProcessPoolExecutor() as process_pool:
        numbers = [1, 3, 5, 22, 100000000]
        # use map_async?
        for result in process_pool.map(count, numbers):
            print(result)

# Finished counting to 1 in 0.0
# Finished counting to 3 in 0.0
# Finished counting to 5 in 0.0
# Finished counting to 22 in 0.0
# 1
# 3
# 5
# 22
# Finished counting to 100000000 in 5.542396783828735
# 100000000

# 一样的问题，如果 100000000 这个运算是列表的开头任务，那么后面的任务都会被阻塞，直到它运行完毕。
# 现在，急需一种事件轮询机制，来灵活地并行执行任务，并且得到及早反馈。
