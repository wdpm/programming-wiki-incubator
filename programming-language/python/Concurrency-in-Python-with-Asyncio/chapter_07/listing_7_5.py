import functools
import requests
import asyncio
from concurrent.futures import ThreadPoolExecutor
from util import async_timed

import logging

logger = logging.getLogger(__name__)

def get_status_code(url: str) -> int:
    response = requests.get(url)
    logger.info('func')
    return response.status_code


@async_timed()
async def main():
    loop = asyncio.get_running_loop()
    with ThreadPoolExecutor() as pool:
        urls = ['https://www.example.com' for _ in range(1000)]
        tasks = [loop.run_in_executor(pool, functools.partial(get_status_code, url)) for url in urls]
        results = await asyncio.gather(*tasks)
        print(results)


asyncio.run(main())

# finished <function main at 0x0000019B4AACD990> in 92.5911 second(s)
