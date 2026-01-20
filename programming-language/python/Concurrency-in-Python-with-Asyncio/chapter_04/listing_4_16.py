import asyncio
import aiohttp
from chapter_04 import fetch_status


async def main():
    async with aiohttp.ClientSession() as session:
        api_a = asyncio.create_task(fetch_status(session, 'https://www.example.com'))
        api_b = asyncio.create_task(fetch_status(session, 'https://www.example.com', delay=4))

        done, pending = await asyncio.wait([api_a, api_b], timeout=2)
        print(f'done: {len(done)}')
        print(f'pending: {len(pending)}')

        for task in pending:
            print(type(task))
            # <class '_asyncio.Task'>
            # 因为 asyncio.wait 返回的是task类型，因此如果想要比较task的类型，例如if task is api_b:
            # 那么需要使用 asyncio.create_task() 对原来的请求进行包装

            if task is api_b:
                print('API B too slow, cancelling')
                task.cancel()

asyncio.run(main())
