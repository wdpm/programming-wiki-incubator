import asyncio

import aiohttp

from chapter_04 import fetch_status
from util import async_timed


# async def main():
#     results = await asyncio.gather(delay(3), delay(1))
#     print(results)


@async_timed()
async def main():
    async with aiohttp.ClientSession() as session:
        urls = ['https://example.com', 'python://example.com']
        tasks = [fetch_status(session, url) for url in urls]
        # 报错和成功都是返回，而不是超时
        results = await asyncio.gather(*tasks, return_exceptions=True)

        exceptions = [res for res in results if isinstance(res, Exception)]
        successful_results = [res for res in results if not isinstance(res, Exception)]

        print(f'All results: {results}')
        print(f'Finished successfully: {successful_results}')
        print(f'Threw exceptions: {exceptions}')

        # print(status_codes)


asyncio.run(main())

# return_exceptions=True

# All results: [200, AssertionError()]
# Finished successfully: [200]
# Threw exceptions: [AssertionError()]

# return_exceptions=False
# 直接报错，缺乏必要的处理。不会影响其他的task运行,但会影响后续main代码的执行，因此需要改进。
