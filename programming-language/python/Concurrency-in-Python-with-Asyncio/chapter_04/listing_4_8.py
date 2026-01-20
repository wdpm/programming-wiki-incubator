import asyncio

import aiohttp

from chapter_04 import fetch_status
from util import async_timed


@async_timed()
async def main():
    async with aiohttp.ClientSession() as session:
        fetchers = [
            fetch_status(session, 'https://www.example.com', 10),
            fetch_status(session, 'https://www.example.com', 1),
            fetch_status(session, 'https://www.example.com', 1),
        ]

        # results = await asyncio.gather(*fetchers, return_exceptions=True)
        # print(results)
        # 只有全部完成时，才能打印结果，尽管有部分task早就完成了。

        # however, we’re able to execute code to print the result of
        # our first request as soon as it finishes. This gives us extra time to process the result
        # of our first successfully finished coroutine while others are still waiting to finish, mak-
        # ing our application more responsive when our tasks complete

        # 从这一点来看，响应式体验比gather()要好，gather()会受到短板效应的限制
        for finished_task in asyncio.as_completed(fetchers):
            print(await finished_task)


asyncio.run(main())

# 1s的两个很快就返回，并打印结果。
# finished <function fetch_status at 0x000001D50056F130> in 1.9284 second(s)
# 200
# finished <function fetch_status at 0x000001D50056F130> in 3.5601 second(s)
# 200
# finished <function fetch_status at 0x000001D50056F130> in 10.2492 second(s)
# 200
# finished <function main at 0x000001D50056F880> in 10.2502 second(s)
