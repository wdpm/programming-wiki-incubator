import asyncio
from asyncio import Semaphore
from aiohttp import ClientSession


async def get_url(url: str,
                  session: ClientSession,
                  semaphore: Semaphore):
    print('Waiting to acquire semaphore...')
    async with semaphore:
        print('Acquired semaphore, requesting...')
        response = await session.get(url)
        print('Finished requesting')
        return response.status


async def main():
    semaphore = Semaphore(10)
    async with ClientSession() as session:
        tasks = [get_url('https://www.example.com', session, semaphore)
                 for _ in range(1000)]
        await asyncio.gather(*tasks)


asyncio.run(main())

# 这段代码做到了限制任何时刻的同时请求的并发数，不能超过10。但是，有个缺点，就是某个时刻，会有10个请求的流量高峰。
# 如果想要更加细粒度的流量控制，那就是流量限制的领域了。固定窗口，移动窗口，漏斗，token漏斗都是常见的策略。
