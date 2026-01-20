import asyncio
import aiohttp
from util import async_timed
from chapter_04 import fetch_status


@async_timed()
async def main():
    async with aiohttp.ClientSession() as session:
        fetchers = [fetch_status(session, 'https://example.com', 1),
                    fetch_status(session, 'https://example.com', 10),
                    fetch_status(session, 'https://example.com', 10)]

        # 这里的timeout是对于这次任务而言的总超时
        for done_task in asyncio.as_completed(fetchers, timeout=4):
            try:
                result = await done_task
                print(result)
            except asyncio.TimeoutError:
                print('We got a timeout error!')

        for task in asyncio.tasks.all_tasks():
            print(task)


asyncio.run(main())

# 200
# We got a timeout error!
# We got a timeout error!
